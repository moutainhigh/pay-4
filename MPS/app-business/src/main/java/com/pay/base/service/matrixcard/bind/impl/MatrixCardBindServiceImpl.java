package com.pay.base.service.matrixcard.bind.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.base.common.helper.matrixcard.ErrorCodeMatrixExceptionEnum;
import com.pay.base.common.helper.matrixcard.MatrixCardToken;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.ResultDto;
import com.pay.base.dto.matrixcard.MatrixCardVfyDto;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.base.service.matrixcard.bind.MatrixCardBindService;
import com.pay.base.service.matrixcard.bind.MatrixCardBindTxService;

/**
 * @author jim_chen
 * @version 2010-9-17
 */
public class MatrixCardBindServiceImpl implements MatrixCardBindService {

	private MatrixCardBindTxService matrixCardBindTxService;
	private Log log = LogFactory.getLog(MatrixCardBindServiceImpl.class);


	@Override
    public ResultDto validateEmail(Long memberCode,String checkCode) {
		ResultDto rsDto = new ResultDto();
		try {
			boolean flag=this.matrixCardBindTxService.validateEmail(memberCode, checkCode);
			rsDto.setObject(flag);
		}
		catch (MatrixCardException mae) {
			log.debug(mae);
			rsDto.setErrorCode(mae.getErrorEnum().getErrorCode());
			rsDto.setErrorMsg(mae.getErrorEnum().getMessage());
		}
		catch (Exception e) {
			log.debug(e);
			rsDto.setErrorCode(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getErrorCode());
			rsDto.setErrorMsg(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getMessage());
		}
		return rsDto;
    }

	
	@Override
	public ResultDto bind(MatrixCardVfyDto matrixCardVfyDto) {
		ResultDto rsDto = new ResultDto();
		try {
			this.matrixCardBindTxService.bindRdTx(matrixCardVfyDto);
			rsDto.setObject(true);
		}
		catch (MatrixCardException mae) {
			mae.printStackTrace();
			log.debug(mae);
			rsDto.setErrorCode(mae.getErrorEnum().getErrorCode());
			rsDto.setErrorMsg(mae.getErrorEnum().getMessage());
		}
		catch (Exception e) {
			log.debug(e);
			e.printStackTrace();
			rsDto.setErrorCode(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getErrorCode());
			rsDto.setErrorMsg(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getMessage());
		}
		return rsDto;
	}

	@Override
	public ResultDto processRequest(MatrixCardVfyRequest mcVfyRequest, OperatorInfo operatorInfo) {
		ResultDto rsDto = new ResultDto();
		try {
			MatrixCardToken token = this.matrixCardBindTxService.processBindRnTx(mcVfyRequest, operatorInfo);
			rsDto.setObject(token);
		}
		catch (MatrixCardException mae) {		
			rsDto.setErrorCode(mae.getErrorEnum().getErrorCode());
			rsDto.setErrorMsg(mae.getErrorEnum().getMessage());
		}
		catch (Exception e) {
			rsDto.setErrorCode(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getErrorCode());
			rsDto.setErrorMsg(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getMessage());
		}
		return rsDto;
	}

	public MatrixCardBindTxService getMatrixCardBindTxService() {
		return matrixCardBindTxService;
	}

	public void setMatrixCardBindTxService(MatrixCardBindTxService matrixCardBindTxService) {
		this.matrixCardBindTxService = matrixCardBindTxService;
	}

}
