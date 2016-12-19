package com.pay.base.service.matrixcard.reset.impl;

import com.pay.base.common.helper.matrixcard.ErrorCodeMatrixExceptionEnum;
import com.pay.base.common.helper.matrixcard.MatrixCardToken;
import com.pay.base.common.helper.matrixcard.MatrixCardTransType;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.ResultDto;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.base.service.matrixcard.reset.MatrixCardResetService;
import com.pay.base.service.matrixcard.reset.MatrixCardResetTxService;

public class MatrixCardResetServiceImpl implements MatrixCardResetService {
	private MatrixCardResetTxService matrixCardResetTxService;

	@Override
	public ResultDto begnResetTrans(MatrixCardTransInfoDto transDto) {
		ResultDto resultDto = new ResultDto();
		try {
			MatrixCardTransInfoDto matrixCardTransInfoDto = matrixCardResetTxService.begnResetTransRdTx(transDto);
			resultDto.setObject(matrixCardTransInfoDto);
		}
		catch (MatrixCardException e) {
			e.printStackTrace();
			resultDto.setErrorCode(e.getErrorEnum().getErrorCode());
			resultDto.setErrorMsg(e.getErrorEnum().getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			resultDto.setErrorCode(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getErrorCode());
			resultDto.setErrorMsg(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getMessage());
		}
		return resultDto;
	}

	@Override
	public ResultDto getValidResetTrans(Long memberCode) {
		ResultDto resultDto = new ResultDto();
		try {
			MatrixCardTransInfoDto matrixCardTransInfoDto = matrixCardResetTxService.getValidResetTrans(memberCode, MatrixCardTransType.RESET.getValue());
			resultDto.setObject(matrixCardTransInfoDto);
		}
		catch (MatrixCardException e) {
			e.printStackTrace();
			resultDto.setErrorCode(e.getErrorEnum().getErrorCode());
			resultDto.setErrorMsg(e.getErrorEnum().getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			resultDto.setErrorCode(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getErrorCode());
			resultDto.setErrorMsg(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getMessage());
		}
		return resultDto;
	}

	@Override
	public ResultDto requestRstBind(MatrixCardVfyRequest mcVfyRequest) {
		ResultDto resultDto = new ResultDto();
		try {
			MatrixCardToken matrixCardToken = matrixCardResetTxService.requestRstBindRdTx(mcVfyRequest);
			resultDto.setObject(matrixCardToken);
		}
		catch (MatrixCardException e) {
			e.printStackTrace();
			resultDto.setErrorCode(e.getErrorEnum().getErrorCode());
			resultDto.setErrorMsg(e.getErrorEnum().getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			resultDto.setErrorCode(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getErrorCode());
			resultDto.setErrorMsg(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getMessage());
		}
		return resultDto;
	}

	@Override
	public ResultDto toReset(MatrixCardToken matrixCardToken, OperatorInfo operatorInfo, String serialNo) {
		ResultDto resultDto = new ResultDto();
		try {
			 matrixCardResetTxService.toResetRdTx(matrixCardToken, operatorInfo, serialNo);
			resultDto.setObject(true);
		}
		catch (MatrixCardException e) {
			e.printStackTrace();
			resultDto.setErrorCode(e.getErrorEnum().getErrorCode());
			resultDto.setErrorMsg(e.getErrorEnum().getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			resultDto.setErrorCode(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getErrorCode());
			resultDto.setErrorMsg(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getMessage());
		}
		return resultDto;
	}

	@Override
	public ResultDto validateEmailReset(Long memberCode,String checkCode) {
		ResultDto resultDto = new ResultDto();
		try {
			MatrixCardTransInfoDto matrixCardTransInfoDto= matrixCardResetTxService.validateEmail(memberCode, MatrixCardTransType.RESET.getValue(),checkCode);
			resultDto.setObject(matrixCardTransInfoDto);
		}
		catch (MatrixCardException e) {
			e.printStackTrace();
			resultDto.setErrorCode(e.getErrorEnum().getErrorCode());
			resultDto.setErrorMsg(e.getErrorEnum().getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			resultDto.setErrorCode(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getErrorCode());
			resultDto.setErrorMsg(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getMessage());
		}
		return resultDto;
	}

	@Override
	public ResultDto validateSmsCode(Long uId, String validateCode,Long memberCode) {
		ResultDto resultDto = new ResultDto();
		try {
			MatrixCardTransInfoDto matrixCardTransInfoDto= matrixCardResetTxService.validateSmsCode(uId, validateCode,memberCode, MatrixCardTransType.RESET.getValue());
			resultDto.setObject(matrixCardTransInfoDto);
		}
		catch (MatrixCardException e) {
			e.printStackTrace();
			resultDto.setErrorCode(e.getErrorEnum().getErrorCode());
			resultDto.setErrorMsg(e.getErrorEnum().getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			resultDto.setErrorCode(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getErrorCode());
			resultDto.setErrorMsg(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getMessage());
		}
		return resultDto;
	}
	
	public MatrixCardResetTxService getMatrixCardResetTxService() {
    	return matrixCardResetTxService;
    }

	public void setMatrixCardResetTxService(MatrixCardResetTxService matrixCardResetTxService) {
    	this.matrixCardResetTxService = matrixCardResetTxService;
    }

}
