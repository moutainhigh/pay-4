package com.pay.base.service.matrixcard.reset.impl;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.checkcode.dto.CheckCodeDto;
import com.pay.acc.comm.CheckCodeOriginEnum;
import com.pay.app.service.mail.MailService;
import com.pay.base.common.helper.matrixcard.ErrorCodeMatrixExceptionEnum;
import com.pay.base.common.helper.matrixcard.MatrixCardToken;
import com.pay.base.common.helper.matrixcard.MatrixCardTransInfoValStatus;
import com.pay.base.common.helper.matrixcard.MatrixCardTransType;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.common.util.matrixcard.MatrixCardTransInfoRstStatus;
import com.pay.base.dto.matrixcard.MatrixCardDto;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransMgrDto;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.base.service.matrixcard.IMatrixCardService;
import com.pay.base.service.matrixcard.bind.MatrixCardBindTxService;
import com.pay.base.service.matrixcard.checkcode.CheckCodeEmailService;
import com.pay.base.service.matrixcard.reset.MatrixCardResetTxService;
import com.pay.base.service.matrixcard.transmgr.IMatrixCardTransInfoService;
import com.pay.base.service.matrixcard.transmgr.MatrixCardTransMgrService;
import com.pay.base.service.matrixcard.vfy.IMatrixCardVfyService;
import com.pay.util.StringUtil;

public class MatrixCardResetTxServiceImpl implements MatrixCardResetTxService {

	private MatrixCardTransMgrService matrixCardTransMgrService;
	private IMatrixCardService matrixCardService;
	private IMatrixCardTransInfoService matrixCardTransInfoService;
	private IMatrixCardVfyService matrixCardVfyService;
	private MatrixCardBindTxService matrixCardBindTxService;
	private Log log = LogFactory.getLog(MatrixCardResetTxServiceImpl.class);
	private MailService checkCodeService;
	private CheckCodeEmailService checkCodeEmailService;

	@Override
	public MatrixCardTransInfoDto begnResetTransRdTx(MatrixCardTransInfoDto transDto) throws MatrixCardException {
		MatrixCardTransInfoDto transInfoDto = null;
		try {
			transInfoDto = matrixCardTransMgrService.beginTransInIsolatedRdTx(transDto);
		}
		catch (MatrixCardException e) {
			log.error(e);
			throw e;
		}
		return transInfoDto;
	}

	@Override
	public MatrixCardTransInfoDto validateEmail(Long memberCode, int transType, String checkCode) throws MatrixCardException {
		// 不是口令卡用户
		if (!matrixCardService.isBindByMemberCode(memberCode)) {
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.RESET_UN_BIND_ERROR, "membercode unbind");
		}
		checkCodeEmailService.checkEmail(checkCode);
		MatrixCardTransInfoDto infoDto = this.getValidResetTrans(memberCode, transType);
		return infoDto;
	}

	@Override
	public MatrixCardTransInfoDto getValidResetTrans(Long memberCode, int transType) throws MatrixCardException {
		MatrixCardTransInfoDto transInfoDto = null;
		MatrixCardTransMgrDto transDto = matrixCardTransMgrService.findValidTransByMemberCode(memberCode, transType);
		if (transDto != null) {
			transInfoDto = matrixCardTransInfoService.selectMatrixCardTransInfoByTransId(transDto.getId());
			// 判断重置事务是否开户
			if (transInfoDto == null) {
				log.debug(ErrorCodeMatrixExceptionEnum.RESET_TF_NOT_FOUND_TRANS.getMessage());
				throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.RESET_TF_NOT_FOUND_TRANS, "matrixcardtransmgr is null");
			}
			try {
				validResetMatrixCardTransMgr(transInfoDto);
			}
			catch (MatrixCardException e) {
				// 将事务置为完成	
				matrixCardTransMgrService.finishTransInIsolatedRnTx(transDto.getId());				
				log.error(e);
				throw e;
			}
		}
		return transInfoDto;
	}

	void validResetMatrixCardTransMgr(MatrixCardTransInfoDto transInfoDto) throws MatrixCardException {
		// 判断重置验证状态
		if (transInfoDto.getValStatus() != MatrixCardTransInfoValStatus.NEW.getValue() && transInfoDto.getValStatus() != MatrixCardTransInfoRstStatus.VALIDATED.getValue()) {
			log.debug("重置验证状态不正确!");
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.RESET_TF_CODE_INVALIDATE, "reset status invalidate");
		}
		// 验证码有效期
		Calendar cal = Calendar.getInstance();
		cal.add(java.util.Calendar.DATE, VALID_DATE);
		if (cal.before(Calendar.getInstance())) {
			log.debug(ErrorCodeMatrixExceptionEnum.RESET_VALID_DATE_ERROR.getMessage());
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.RESET_VALID_DATE_ERROR, "reset validate error");
		}
		// 最大重试次数
		int wrongTime = transInfoDto.getWrongTime();
		if (wrongTime >= MAX_WRONG_TIME) {
			log.debug(ErrorCodeMatrixExceptionEnum.RESET_MAX_WRONG_TIME_ERROR.getMessage());
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.RESET_MAX_WRONG_TIME_ERROR, "reset max wrong time");
		}
	}

	@Override
	public boolean isSendSmsCode(Long uId) throws MatrixCardException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MatrixCardToken requestRstBindRdTx(MatrixCardVfyRequest mcVfyRequest) throws MatrixCardException {
		MatrixCardToken token = null;
		token = matrixCardVfyService.requestToken(mcVfyRequest);
		return token;
	}

	@Override
	public void sendValidateCode(Long uId) throws MatrixCardException {
	// TODO Auto-generated method stub

	}

	@Override
	public void toResetRdTx(MatrixCardToken matrixCardToken, OperatorInfo operatorInfo, String serialNo) throws MatrixCardException {
		MatrixCardTransInfoDto infoDto = this.getValidResetTrans(operatorInfo.getMemberCode(), MatrixCardTransType.RESET.getValue());
		boolean vfyValue = matrixCardVfyService.verify(serialNo, matrixCardToken, operatorInfo);
		// 验证填写的值
		if (!vfyValue) {
			log.debug(ErrorCodeMatrixExceptionEnum.VFY_TOKEN_ERROR.getMessage());
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.VFY_TOKEN_ERROR, "validate token error");
		}
		// 执行绑定
		MatrixCardDto matrixCardDto = matrixCardService.findByBindMemberCode(operatorInfo.getMemberCode());
		if (matrixCardDto != null) {
			matrixCardBindTxService.unBind(matrixCardDto.getSerialNo(), operatorInfo);
		}
		matrixCardBindTxService.bind(serialNo, operatorInfo);
		// 完成该事务
		infoDto.setValStatus(MatrixCardTransInfoValStatus.FINISHED.getValue());
		matrixCardTransInfoService.updateMatrixCardTransInfo(infoDto);
		matrixCardTransMgrService.finishTransInIsolatedRnTx(infoDto.getTransId());
	}

	@Override
	public MatrixCardTransInfoDto validateSmsCode(Long uId, String validateCode, Long memberCode, int transType) throws MatrixCardException {
		// 得到有效的验证对象
		if (StringUtil.isEmpty(validateCode)) { // 验证码为空
			log.debug(ErrorCodeMatrixExceptionEnum.VFY_TOKEN_ERROR.getMessage());
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.RESET_VALIDATE_CODE_ERROR, "validate code is not null");
		}

		MatrixCardTransInfoDto infoDto = this.getValidResetTrans(uId, transType);
		CheckCodeDto checkCode=null;
		if(transType==MatrixCardTransType.RESET.getValue()){//重置
			checkCode= checkCodeService.getByMemerCode(memberCode.toString(),CheckCodeOriginEnum.MATRIX_RESET.getValue());
		}else{//解绑
			checkCode= checkCodeService.getByMemerCode(memberCode.toString(),CheckCodeOriginEnum.MATRIX_UNBIND.getValue());
		}
		if (null == checkCode || StringUtils.isBlank(checkCode.getCheckCode())) {
			log.debug(ErrorCodeMatrixExceptionEnum.VFY_TOKEN_ERROR.getMessage());
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.RESET_VALIDATE_CODE_ERROR, "validate code is not null");
		}
		int status=checkCode.getStatus();
		if (status != 1) {
			log.debug("此链接地址已经失效");
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.CHECK_CODE_ALREADY_USED_ERROR,"check code aleary used");
		}
		// 验证码不匹配
		if (!checkCode.getCheckCode().equals(validateCode)) {
			if(null!=infoDto){
				// 将重置的错误次数+1
				infoDto.setLastValidateTime(new Date());
				infoDto.setWrongTime(infoDto.getWrongTime() + 1);
				matrixCardTransInfoService.updateMatrixCardTransInfo(infoDto);
			}			
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.RESET_VALIDATE_CODE_MATCHING_ERROR, "validate code is not matching");
		}
		//验证验证码是否失效
		checkCodeEmailService.checkEmail(checkCode.getCheckCode());		
		Date createDate = checkCode.getCreateTime();
		Calendar cal = Calendar.getInstance();
		Date calTmp = cal.getTime();
		cal.setTime(createDate);
		cal.add(Calendar.MINUTE, 30);
		// 验证码三十分钟内验证才有效
		if (calTmp.after(cal.getTime())) {
			log.debug(ErrorCodeMatrixExceptionEnum.CHECK_CODE_TIME_OUT_ERROR.getMessage());
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.CHECK_CODE_TIME_OUT_ERROR, "check code time out");
		}

		return infoDto;
	}

	public void setMatrixCardTransMgrService(MatrixCardTransMgrService matrixCardTransMgrService) {
		this.matrixCardTransMgrService = matrixCardTransMgrService;
	}

	public void setMatrixCardService(IMatrixCardService matrixCardService) {
		this.matrixCardService = matrixCardService;
	}

	public void setMatrixCardTransInfoService(IMatrixCardTransInfoService matrixCardTransInfoService) {
		this.matrixCardTransInfoService = matrixCardTransInfoService;
	}

	public void setMatrixCardVfyService(IMatrixCardVfyService matrixCardVfyService) {
		this.matrixCardVfyService = matrixCardVfyService;
	}

	public void setMatrixCardBindTxService(MatrixCardBindTxService matrixCardBindTxService) {
		this.matrixCardBindTxService = matrixCardBindTxService;
	}

	public void setCheckCodeService(MailService checkCodeService) {
		this.checkCodeService = checkCodeService;
	}

	public void setCheckCodeEmailService(CheckCodeEmailService checkCodeEmailService) {
		this.checkCodeEmailService = checkCodeEmailService;
	}
}
