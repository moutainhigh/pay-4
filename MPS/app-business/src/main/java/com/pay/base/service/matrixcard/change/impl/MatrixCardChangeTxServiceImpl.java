package com.pay.base.service.matrixcard.change.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.base.common.helper.matrixcard.ErrorCodeMatrixExceptionEnum;
import com.pay.base.common.helper.matrixcard.MatrixCardToken;
import com.pay.base.common.helper.matrixcard.MatrixCardTransInfoValStatus;
import com.pay.base.common.helper.matrixcard.MatrixCardTransType;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequestType;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyType;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.matrixcard.MatrixCardVfyDto;
import com.pay.base.dto.matrixcard.MatrixCardVfyDtoUtil;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransMgrDto;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.base.service.matrixcard.bind.MatrixCardBindTxService;
import com.pay.base.service.matrixcard.change.MatrixCardChangeTxService;
import com.pay.base.service.matrixcard.transmgr.IMatrixCardTransInfoService;
import com.pay.base.service.matrixcard.transmgr.MatrixCardTransMgrService;
import com.pay.base.service.matrixcard.vfy.IMatrixCardVfyService;

public class MatrixCardChangeTxServiceImpl implements MatrixCardChangeTxService {
	private MatrixCardTransMgrService matrixCardTransMgrService;
	private IMatrixCardTransInfoService matrixCardTransInfoService;
	private IMatrixCardVfyService matrixCardVfyService;
	private MatrixCardBindTxService matrixCardBindTxService;

	private Log log = LogFactory.getLog(MatrixCardChangeTxServiceImpl.class);

	@Override
	public MatrixCardTransInfoDto startChangeTransRdTx(OperatorInfo operatorInfo) throws MatrixCardException {
		Long memberCode = operatorInfo.getMemberCode();
		int transType = MatrixCardTransType.CHANGE.getValue();
		MatrixCardTransMgrDto transMgrDto = matrixCardTransMgrService.findValidTransByMemberCode(memberCode, transType);
		if (transMgrDto != null) {
			// 如果有未结束事务, 则先结束掉老的绑定事务
			MatrixCardTransInfoDto transInfo = matrixCardTransInfoService.selectMatrixCardTransInfoById(transMgrDto.getId());
			transInfo.setValStatus(MatrixCardTransInfoValStatus.FAIL.getValue());
//			matrixCardTransInfoService.updateMatrixCardTransInfo(transInfo);
//			matrixCardTransMgrService.finishTransInIsolatedRnTx(transMgrDto.getId());
			matrixCardTransMgrService.finishTransInIsolatedRnTx(transMgrDto.getId(), transInfo);
		}
		MatrixCardTransInfoDto transInfoDto = new MatrixCardTransInfoDto();
		transInfoDto.setCreationDate(new Date());
		transInfoDto.setIp(operatorInfo.getIp());
		transInfoDto.setMemberCode(memberCode);
		transInfoDto.setU_id(operatorInfo.getU_id());
		transInfoDto.setSessionId(operatorInfo.getSessionId());
		transInfoDto.setTransType(transType);
		transInfoDto.setValStatus(MatrixCardTransInfoValStatus.NEW.getValue());
		transInfoDto.setLastValidateTime(new Date());
		transInfoDto = matrixCardTransMgrService.beginTransInIsolatedRdTx(transInfoDto);
		return transInfoDto;
	}

	@Override
	public MatrixCardToken requestOldTokenRdTx(MatrixCardVfyRequest mcVfyRequest, OperatorInfo operatorInfo) throws MatrixCardException {
		Long memberCode = operatorInfo.getMemberCode();
		int transType = MatrixCardTransType.CHANGE.getValue();
		MatrixCardTransMgrDto transMgrDto = matrixCardTransMgrService.findValidTransByMemberCode(memberCode, transType);
		if (transMgrDto == null) {
			log.debug("换卡事务未启动不能进行换卡");
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.CHANGE_TX_NO_STARTING_ERROR, "change matrixcard error");
		}
		return requestToken(transMgrDto.getId(), mcVfyRequest, operatorInfo, MatrixCardVfyType.NORMALCARD_VFY.getValue());
	}

	@Override
	public MatrixCardToken requestNewTokenRdTx(MatrixCardVfyRequest mcVfyRequest, OperatorInfo operatorInfo) throws MatrixCardException {
		Long memberCode = operatorInfo.getMemberCode();

		int transType = MatrixCardTransType.CHANGE.getValue();
		MatrixCardTransMgrDto transMgrDto = matrixCardTransMgrService.findValidTransByMemberCode(memberCode, transType);
		if (transMgrDto == null) {
			log.debug("换卡事务未启动不能进行换卡");
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.CHANGE_TX_NO_STARTING_ERROR, "change matrixcard error");
		}
		return requestToken(transMgrDto.getId(), mcVfyRequest, operatorInfo, MatrixCardVfyType.NEWCARD_VFY.getValue());
	}

	private MatrixCardToken requestToken(Long transId, MatrixCardVfyRequest mcVfyRequest, OperatorInfo operatorInfo, int vfyType) throws MatrixCardException {
		mcVfyRequest.setTransId(transId);
		mcVfyRequest.setRequestId(transId);
		mcVfyRequest.setRequestIp(operatorInfo.getIp());
		mcVfyRequest.setRequestMemberCode(operatorInfo.getMemberCode());
		mcVfyRequest.setRequestType(MatrixCardVfyRequestType.CHANGE.getValue());
		mcVfyRequest.setRequestUid(operatorInfo.getU_id());
		mcVfyRequest.setVfyType(vfyType);
		mcVfyRequest.setRequestDate(new Date());
		MatrixCardToken mcToken = matrixCardVfyService.requestToken(mcVfyRequest);

		return mcToken;
	}

	public MatrixCardTransMgrService getMatrixCardTransMgrService() {
		return matrixCardTransMgrService;
	}

	public void setMatrixCardTransMgrService(MatrixCardTransMgrService matrixCardTransMgrService) {
		this.matrixCardTransMgrService = matrixCardTransMgrService;
	}

	public IMatrixCardTransInfoService getMatrixCardTransInfoService() {
		return matrixCardTransInfoService;
	}

	public void setMatrixCardTransInfoService(IMatrixCardTransInfoService matrixCardTransInfoService) {
		this.matrixCardTransInfoService = matrixCardTransInfoService;
	}

	@Override
	public void changeRdTx(Long uId, MatrixCardVfyDto oldMatrixCardVfyDto, MatrixCardVfyDto newMatrixCardVfyDto) throws MatrixCardException {
		OperatorInfo operatorInfo = new OperatorInfo();
		Long memberCode=oldMatrixCardVfyDto.getVfyMemberCode();
		operatorInfo.setIp(oldMatrixCardVfyDto.getVfyIp());
		operatorInfo.setMemberCode(memberCode);
		operatorInfo.setU_id(uId);

		int transType = MatrixCardTransType.CHANGE.getValue();
		MatrixCardTransMgrDto transMgrDto = matrixCardTransMgrService.findValidTransByMemberCode(memberCode, transType);
		if (transMgrDto == null) {
			log.debug("换卡事务未启动不能进行换卡");
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.CHANGE_TX_NO_STARTING_ERROR, "change matrixcard error");
		}
		MatrixCardTransInfoDto transInfo = matrixCardTransInfoService.selectMatrixCardTransInfoByTransId(transMgrDto.getId());
		try {
			// 验证旧卡
			MatrixCardToken oldMatrixCardToken = MatrixCardVfyDtoUtil.createMatrixCardTokenDto(oldMatrixCardVfyDto);
			matrixCardVfyService.verify(oldMatrixCardVfyDto.getSerialNo(), oldMatrixCardToken, operatorInfo);
			// 验证新卡
			MatrixCardToken newMatrixCardToken = MatrixCardVfyDtoUtil.createMatrixCardTokenDto(newMatrixCardVfyDto);
			matrixCardVfyService.verify(newMatrixCardVfyDto.getSerialNo(), newMatrixCardToken, operatorInfo);
			// 旧卡解绑
			matrixCardBindTxService.unBind(oldMatrixCardVfyDto.getSerialNo(), operatorInfo);
			// 新卡绑定
			matrixCardBindTxService.bind(newMatrixCardVfyDto.getSerialNo(), operatorInfo);

			transInfo.setValStatus(MatrixCardTransInfoValStatus.SUCCESS.getValue());
		}
		catch (MatrixCardException e) {
			transInfo.setValStatus(MatrixCardTransInfoValStatus.FAIL.getValue());
			log.debug(e);
			throw e;
		}
		finally {
			// 结束事务
			matrixCardTransMgrService.finishTransInIsolatedRnTx(transMgrDto.getId(), transInfo);
		}
	}

	public IMatrixCardVfyService getMatrixCardVfyService() {
		return matrixCardVfyService;
	}

	public void setMatrixCardVfyService(IMatrixCardVfyService matrixCardVfyService) {
		this.matrixCardVfyService = matrixCardVfyService;
	}

	public MatrixCardBindTxService getMatrixCardBindTxService() {
		return matrixCardBindTxService;
	}

	public void setMatrixCardBindTxService(MatrixCardBindTxService matrixCardBindTxService) {
		this.matrixCardBindTxService = matrixCardBindTxService;
	}

}
