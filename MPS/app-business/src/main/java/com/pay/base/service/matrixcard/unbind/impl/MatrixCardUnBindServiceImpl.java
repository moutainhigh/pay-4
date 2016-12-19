package com.pay.base.service.matrixcard.unbind.impl;

import com.pay.base.common.helper.matrixcard.ErrorCodeMatrixExceptionEnum;
import com.pay.base.common.helper.matrixcard.MatrixCardTransType;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.ResultDto;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.base.service.matrixcard.reset.MatrixCardResetTxService;
import com.pay.base.service.matrixcard.unbind.MatrixCardUnBindService;
import com.pay.base.service.matrixcard.unbind.MatrixCardUnBindTxService;

public class MatrixCardUnBindServiceImpl implements MatrixCardUnBindService {

	private MatrixCardResetTxService matrixCardResetTxService;
	private MatrixCardUnBindTxService matrixCardUnBindTxService;

	@Override
	public ResultDto unbind(String serialNo, OperatorInfo operatorInfo) {
		ResultDto resultDto = new ResultDto();
		try {
			matrixCardUnBindTxService.unBindRdTx(serialNo, operatorInfo);
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
	public ResultDto beginUnBindTrans(MatrixCardTransInfoDto transDto) {
		ResultDto resultDto = new ResultDto();
		try {
			MatrixCardTransInfoDto matrixCardTransInfoDto = matrixCardUnBindTxService.processUnBindRnTx(transDto);
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
	public ResultDto getValidUnBindTrans(Long memberCode) {
		ResultDto resultDto = new ResultDto();
		try {
			MatrixCardTransInfoDto matrixCardTransInfoDto = matrixCardResetTxService.getValidResetTrans(memberCode, MatrixCardTransType.UNBIND.getValue());
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
	public ResultDto validateEmailUnBind(Long memberCode,String checkCode) {
		ResultDto resultDto = new ResultDto();
		try {
			MatrixCardTransInfoDto matrixCardTransInfoDto = matrixCardResetTxService.validateEmail(memberCode, MatrixCardTransType.UNBIND.getValue(),checkCode);
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
	public ResultDto validateSmsCode(Long uId, String validateCode, Long memberCode) {
		ResultDto resultDto = new ResultDto();
		try {
			MatrixCardTransInfoDto matrixCardTransInfoDto = matrixCardResetTxService.validateSmsCode(uId, validateCode, memberCode, MatrixCardTransType.UNBIND.getValue());
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

	
	public void setMatrixCardResetTxService(MatrixCardResetTxService matrixCardResetTxService) {
    	this.matrixCardResetTxService = matrixCardResetTxService;
    }

	public void setMatrixCardUnBindTxService(MatrixCardUnBindTxService matrixCardUnBindTxService) {
    	this.matrixCardUnBindTxService = matrixCardUnBindTxService;
    }
}
