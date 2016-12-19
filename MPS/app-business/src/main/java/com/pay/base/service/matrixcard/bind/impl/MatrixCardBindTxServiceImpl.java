package com.pay.base.service.matrixcard.bind.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.base.common.helper.matrixcard.ErrorCodeMatrixExceptionEnum;
import com.pay.base.common.helper.matrixcard.MatrixCardStatus;
import com.pay.base.common.helper.matrixcard.MatrixCardToken;
import com.pay.base.common.helper.matrixcard.MatrixCardTransInfoValStatus;
import com.pay.base.common.helper.matrixcard.MatrixCardTransType;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequestType;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyType;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.matrixcard.MatrixCardDto;
import com.pay.base.dto.matrixcard.MatrixCardVfyDto;
import com.pay.base.dto.matrixcard.MatrixCardVfyDtoUtil;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransMgrDto;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.base.service.matrixcard.IMatrixCardService;
import com.pay.base.service.matrixcard.bind.MatrixCardBindTxService;
import com.pay.base.service.matrixcard.bind.rules.IMatrixCardBindRuleService;
import com.pay.base.service.matrixcard.checkcode.CheckCodeEmailService;
import com.pay.base.service.matrixcard.transmgr.IMatrixCardTransInfoService;
import com.pay.base.service.matrixcard.transmgr.MatrixCardTransMgrService;
import com.pay.base.service.matrixcard.vfy.IMatrixCardVfyService;

public class MatrixCardBindTxServiceImpl implements MatrixCardBindTxService {
	private IMatrixCardService matrixCardService;
	private MatrixCardTransMgrService matrixCardTransMgrService;
	private IMatrixCardVfyService matrixCardVfyService;
	private IMatrixCardBindRuleService matrixCardBindRuleService;
	private IMatrixCardTransInfoService matrixCardTransInfoService;
	private CheckCodeEmailService checkCodeEmailService;

	private Log log = LogFactory.getLog(MatrixCardBindTxServiceImpl.class);
	
	@Override
    public boolean validateEmail(Long memberCode, String checkCode) throws MatrixCardException {
		// 不是口令卡用户
		if (matrixCardService.isBindByMemberCode(memberCode)) {
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.RESET_UN_BIND_ERROR, "membercode unbind");
		}
		return checkCodeEmailService.checkEmail(checkCode);	    
		
    }
	
	@Override
	public void bindRdTx(MatrixCardVfyDto matrixCardVfyDto) throws MatrixCardException {
		OperatorInfo operatorInfo = new OperatorInfo();
		operatorInfo.setIp(matrixCardVfyDto.getVfyIp());
		operatorInfo.setMemberCode(matrixCardVfyDto.getVfyMemberCode());
		operatorInfo.setU_id(matrixCardVfyDto.getRequestUid());
		int transType = MatrixCardTransType.BIND.getValue();
		MatrixCardTransMgrDto transMgrDto = matrixCardTransMgrService.findValidTransByMemberCode(matrixCardVfyDto.getRequestMemberCode(), transType);
		if (transMgrDto == null) {
			log.debug("绑定事务未启动不能进行绑定");
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.BIND_TX_NO_STARTING_ERROR, "bind trancetion");
		}
		MatrixCardTransInfoDto transInfoDto = matrixCardTransInfoService.selectMatrixCardTransInfoByTransId(transMgrDto.getId());
		if (null == transInfoDto) {
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.UNBIND_TRANS_INFO_NOT_FOUND, ErrorCodeMatrixExceptionEnum.UNBIND_TRANS_INFO_NOT_FOUND.getMessage());
		}
		try {
			MatrixCardToken matrixCardToken = MatrixCardVfyDtoUtil.createMatrixCardTokenDto(matrixCardVfyDto);
			// 验证绑定规则
			matrixCardBindRuleService.validate(matrixCardVfyDto);
			// 验证token
			matrixCardVfyService.verify(matrixCardVfyDto.getSerialNo(), matrixCardToken, operatorInfo);
			// 绑定
			bind(matrixCardVfyDto.getSerialNo(), operatorInfo);
			transInfoDto.setValStatus(MatrixCardTransInfoValStatus.FINISHED.getValue());
		}
		catch (MatrixCardException e) {
			transInfoDto.setValStatus(MatrixCardTransInfoValStatus.FAIL.getValue());
			log.debug(e);
			throw e;
		}
		finally {
			// 结束绑定事务
			matrixCardTransMgrService.finishTransInIsolatedRnTx(transMgrDto.getId(), transInfoDto);
		}
	}

	public void bind(String serialNo, OperatorInfo operatorInfo) throws MatrixCardException {
		// 找到卡
		MatrixCardDto matrixCardDto = matrixCardService.findBySerialNo(serialNo);
		// 判断是否状态正确
		if (matrixCardDto.getStatus() != MatrixCardStatus.CREATE.getValue()) {
			log.debug("口令卡的状态不正确");
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.BIND_MATRIXCARD_STATUS_ERROR, "matrixcard status error");
		}
		// 更新绑定时间
		matrixCardDto.setBindDate(new Date());
		// 更新绑定人
		matrixCardDto.setBindIp(operatorInfo.getIp());
		matrixCardDto.setBindMemberCode(operatorInfo.getMemberCode());
		matrixCardDto.setBindUid(operatorInfo.getU_id());
		// 更新卡状态
		matrixCardDto.setStatus(MatrixCardStatus.BIND.getValue());
		matrixCardService.updateMatrixCard(matrixCardDto);
	}

	@Override
	public MatrixCardToken processLoginRnTx(MatrixCardVfyRequest mcVfyRequest, OperatorInfo operatorInfo) throws MatrixCardException {
		mcVfyRequest.setRequestIp(operatorInfo.getIp());
		mcVfyRequest.setRequestMemberCode(operatorInfo.getMemberCode());
		mcVfyRequest.setRequestType(MatrixCardVfyRequestType.LOGIN.getValue());
		mcVfyRequest.setRequestUid(operatorInfo.getU_id());
		mcVfyRequest.setVfyType(MatrixCardVfyType.NORMALCARD_VFY.getValue());
		mcVfyRequest.setRequestDate(new Date());
		MatrixCardToken mcToken = matrixCardVfyService.requestToken(mcVfyRequest);
		return mcToken;
	}

	@Override
	public MatrixCardToken processBindRnTx(MatrixCardVfyRequest mcVfyRequest, OperatorInfo operatorInfo) throws MatrixCardException {
		Long memberCode = operatorInfo.getMemberCode();
		// 判断该会员是否已有绑定卡，有则抛出异常提示
		if (matrixCardService.isBindByMemberCode(memberCode)) {
			log.debug("已经有绑定卡，不能重复绑定");
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.BIND_STATUS_ALREADY_BIND, "matrixcard already bind");
		}
		Long transId = null;
		int transType = MatrixCardTransType.BIND.getValue();
		MatrixCardTransMgrDto transMgrDto = matrixCardTransMgrService.findValidTransByMemberCode(memberCode, transType);
		if (transMgrDto != null) {
			// 如果有未结束事务,则先结束掉老的绑定事务
			matrixCardTransMgrService.finishTransInIsolatedRnTx(transMgrDto.getId());
		}
		MatrixCardTransInfoDto transInfoDto = createRequestData(operatorInfo, transType);

		transInfoDto = matrixCardTransMgrService.beginTransInIsolatedRdTx(transInfoDto);

		transId = transInfoDto.getTransId();
		mcVfyRequest.setTransId(transId);
		mcVfyRequest.setRequestId(transId);
		mcVfyRequest.setRequestIp(operatorInfo.getIp());
		mcVfyRequest.setRequestMemberCode(operatorInfo.getMemberCode());
		mcVfyRequest.setRequestType(MatrixCardVfyRequestType.BIND.getValue());
		mcVfyRequest.setRequestUid(operatorInfo.getU_id());
		mcVfyRequest.setVfyType(MatrixCardVfyType.NEWCARD_VFY.getValue());
		mcVfyRequest.setRequestDate(new Date());
		MatrixCardToken mcToken = matrixCardVfyService.requestToken(mcVfyRequest);
		return mcToken;
	}

	public MatrixCardTransInfoDto createRequestData(OperatorInfo operatorInfo, int transType) {
		MatrixCardTransInfoDto transInfoDto = new MatrixCardTransInfoDto();
		transInfoDto.setCreationDate(new Date());
		transInfoDto.setIp(operatorInfo.getIp());
		transInfoDto.setMemberCode(operatorInfo.getMemberCode());
		transInfoDto.setU_id(operatorInfo.getU_id());
		transInfoDto.setSessionId(operatorInfo.getSessionId());
		transInfoDto.setTransType(transType);
		return transInfoDto;
	}

	public IMatrixCardService getMatrixCardService() {
		return matrixCardService;
	}

	public void setMatrixCardService(IMatrixCardService matrixCardService) {
		this.matrixCardService = matrixCardService;
	}

	public MatrixCardTransMgrService getMatrixCardTransMgrService() {
		return matrixCardTransMgrService;
	}

	public void setMatrixCardTransMgrService(MatrixCardTransMgrService matrixCardTransMgrService) {
		this.matrixCardTransMgrService = matrixCardTransMgrService;
	}

	public IMatrixCardVfyService getMatrixCardVfyService() {
		return matrixCardVfyService;
	}

	public void setMatrixCardVfyService(IMatrixCardVfyService matrixCardVfyService) {
		this.matrixCardVfyService = matrixCardVfyService;
	}

	public IMatrixCardBindRuleService getMatrixCardBindRuleService() {
		return matrixCardBindRuleService;
	}

	public void setMatrixCardBindRuleService(IMatrixCardBindRuleService matrixCardBindRuleService) {
		this.matrixCardBindRuleService = matrixCardBindRuleService;
	}

	public void setMatrixCardTransInfoService(IMatrixCardTransInfoService matrixCardTransInfoService) {
		this.matrixCardTransInfoService = matrixCardTransInfoService;
	}

	@Override
	public void unBind(String serialNo, OperatorInfo operatorInfo) throws MatrixCardException {
		// 找到卡
		MatrixCardDto matrixCardDto = null;
		if (serialNo != null && serialNo.length() > 0) {
			matrixCardDto = matrixCardService.findBySerialNo(serialNo);
		}
		else {
			matrixCardDto = matrixCardService.findByBindMemberCode(operatorInfo.getMemberCode());
		}
		Long memberCode = operatorInfo.getMemberCode();

		// 判断卡所属人
		if (!memberCode.equals(matrixCardDto.getBindMemberCode())) {
			log.debug("不是该卡的绑定人");
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.VFY_TOKEN_NOTCARDOWNER, "vfy token not cardowner");
		}
		// 判断是否状态正确
		if (matrixCardDto.getStatus() != MatrixCardStatus.BIND.getValue()) {
			log.debug("卡状态异常");
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.VFY_CARD_STATUS_ERROR, "vfy card status error");
		}

		matrixCardDto.setUnBindDate(new Date());
		matrixCardDto.setUnBindIp(operatorInfo.getIp());
		matrixCardDto.setUnBindOperator(operatorInfo.getOperator());

		// 更新卡状态
		matrixCardDto.setStatus(MatrixCardStatus.UNBIND.getValue());
		matrixCardService.updateMatrixCard(matrixCardDto);
	}


	public void setCheckCodeEmailService(CheckCodeEmailService checkCodeEmailService) {
    	this.checkCodeEmailService = checkCodeEmailService;
    }

}
