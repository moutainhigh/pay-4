package com.pay.base.service.matrixcard.login.impl;

import com.pay.base.common.helper.matrixcard.ErrorCodeMatrixExceptionEnum;
import com.pay.base.common.helper.matrixcard.MatrixCardToken;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.ResultDto;
import com.pay.base.dto.matrixcard.MatrixCardDto;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.base.service.matrixcard.IMatrixCardService;
import com.pay.base.service.matrixcard.login.MatrixCardLoginService;
import com.pay.base.service.matrixcard.login.MatrixCardLoginTxService;

public class MatrixCardLoginServiceImpl implements MatrixCardLoginService {
	private IMatrixCardService matrixCardService;
	private MatrixCardLoginTxService matrixCardLoginTxService;



	@Override
	public MatrixCardDto findByBindMemberCode(Long memberCode) {
		return this.matrixCardService.findByBindMemberCode(memberCode);
	}

	@Override
	public ResultDto processRequest(MatrixCardVfyRequest mcVfyRequest, OperatorInfo operatorInfo) {
		ResultDto resultDto = new ResultDto();
		try {
			MatrixCardToken token = matrixCardLoginTxService.processLoginRdTx(mcVfyRequest, operatorInfo);
			resultDto.setObject(token);
		}
		catch (MatrixCardException mae) {
			resultDto.setErrorCode(mae.getErrorEnum().getErrorCode());
			resultDto.setErrorMsg(mae.getErrorEnum().getMessage());
		}
		catch (Exception e) {
			resultDto.setErrorCode(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getErrorCode());
			resultDto.setErrorMsg(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getMessage());
		}
		return resultDto;
	}

	@Override
	public ResultDto verifyLogin(String serialNo, MatrixCardToken matrixCardToken, OperatorInfo operatorInfo) {
		ResultDto resultDto = new ResultDto();
		try {
			matrixCardLoginTxService.verifyRdTx(serialNo, matrixCardToken, operatorInfo);
			resultDto.setObject(true);
		}
		catch (MatrixCardException mae) {
			resultDto.setErrorCode(mae.getErrorEnum().getErrorCode());
			resultDto.setErrorMsg(mae.getErrorEnum().getMessage());
		}
		catch (Exception e) {
			resultDto.setErrorCode(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getErrorCode());
			resultDto.setErrorMsg(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getMessage());
		}
		return resultDto;
	}

	public IMatrixCardService getMatrixCardService() {
    	return matrixCardService;
    }

	public void setMatrixCardService(IMatrixCardService matrixCardService) {
    	this.matrixCardService = matrixCardService;
    }

	public MatrixCardLoginTxService getMatrixCardLoginTxService() {
    	return matrixCardLoginTxService;
    }

	public void setMatrixCardLoginTxService(MatrixCardLoginTxService matrixCardLoginTxService) {
    	this.matrixCardLoginTxService = matrixCardLoginTxService;
    }
}
