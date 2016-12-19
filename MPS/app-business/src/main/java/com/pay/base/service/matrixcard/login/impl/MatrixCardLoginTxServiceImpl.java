package com.pay.base.service.matrixcard.login.impl;

import java.util.Date;

import com.pay.base.common.helper.matrixcard.MatrixCardToken;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequestType;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyType;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.matrixcard.MatrixCardDto;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.base.service.matrixcard.IMatrixCardService;
import com.pay.base.service.matrixcard.login.MatrixCardLoginTxService;
import com.pay.base.service.matrixcard.vfy.IMatrixCardVfyService;

public class MatrixCardLoginTxServiceImpl implements MatrixCardLoginTxService {

	private IMatrixCardService matrixCardService;
	private IMatrixCardVfyService matrixCardVfyService;


	@Override
	public MatrixCardDto findByBindMemberCode(Long memberCode) {
		return matrixCardService.findByBindMemberCode(memberCode);
	}

	@Override
	public MatrixCardToken processLoginRdTx(MatrixCardVfyRequest mcVfyRequest, OperatorInfo operatorInfo) throws MatrixCardException {
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
	public void verifyRdTx(String serialNo, MatrixCardToken matrixCardToken, OperatorInfo operatorInfo) throws MatrixCardException {
		matrixCardVfyService.verify(serialNo, matrixCardToken, operatorInfo);

	}
	

	public IMatrixCardService getMatrixCardService() {
    	return matrixCardService;
    }

	public void setMatrixCardService(IMatrixCardService matrixCardService) {
    	this.matrixCardService = matrixCardService;
    }

	public IMatrixCardVfyService getMatrixCardVfyService() {
    	return matrixCardVfyService;
    }

	public void setMatrixCardVfyService(IMatrixCardVfyService matrixCardVfyService) {
    	this.matrixCardVfyService = matrixCardVfyService;
    }

}
