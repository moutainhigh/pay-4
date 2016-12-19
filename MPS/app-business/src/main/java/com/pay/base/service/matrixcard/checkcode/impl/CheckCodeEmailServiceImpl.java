package com.pay.base.service.matrixcard.checkcode.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.checkcode.CheckCodeService;
import com.pay.base.common.helper.matrixcard.ErrorCodeMatrixExceptionEnum;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.base.service.matrixcard.checkcode.CheckCodeEmailService;

public class CheckCodeEmailServiceImpl implements CheckCodeEmailService {
	private CheckCodeService checkCodeService;
	private static final Log logger = LogFactory.getLog(CheckCodeEmailServiceImpl.class);

	@Override
	public boolean checkEmail(String checkCode)throws MatrixCardException {
		boolean codeFlag=false;
		int status = checkCodeService.findStatesByCheckCode(checkCode);
		if (status == 1) {
			codeFlag=true;
		}else{
			logger.debug("此链接地址已经失效");
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.CHECK_CODE_ALREADY_USED_ERROR,"check code aleary used");
		}
		checkCodeService.updateCheckCodeStates(checkCode);
		return codeFlag;
	}

	public void setCheckCodeService(CheckCodeService checkCodeService) {
		this.checkCodeService = checkCodeService;
	}

}
