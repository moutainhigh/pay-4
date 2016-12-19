package com.pay.base.service.matrixcard.change.impl;

import com.pay.base.common.helper.matrixcard.ErrorCodeMatrixExceptionEnum;
import com.pay.base.common.helper.matrixcard.MatrixCardToken;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.ResultDto;
import com.pay.base.dto.matrixcard.MatrixCardVfyDto;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.base.service.matrixcard.change.MatrixCardChangeService;
import com.pay.base.service.matrixcard.change.MatrixCardChangeTxService;

/**
 * @author jim_chen
 * @version 
 * 2010-9-19 
 */
public class MatrixCardChangeServiceImpl implements MatrixCardChangeService {
	 MatrixCardChangeTxService matrixCardChangeTxService;
	


	@Override
    public ResultDto change(Long uId, MatrixCardVfyDto oldMatrixCardVfyDto, MatrixCardVfyDto newMatrixCardVfyDto) {
	    ResultDto resultDto=new ResultDto();
	    try{
	    	this.matrixCardChangeTxService.changeRdTx(uId, oldMatrixCardVfyDto, newMatrixCardVfyDto);
	    	resultDto.setObject(true);
	    }catch(MatrixCardException maException){
	    	resultDto.setErrorCode(maException.getErrorEnum().getErrorCode());
	    	resultDto.setErrorMsg(maException.getErrorEnum().getMessage());
	    }catch(Exception e){
	    	resultDto.setErrorCode(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getErrorCode());
	    	resultDto.setErrorMsg(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getMessage());
	    }
	    return resultDto;
    }

	@Override
    public ResultDto requestNewToken(MatrixCardVfyRequest mcVfyRequest, OperatorInfo operatorInfo) {
	    ResultDto resultDto=new ResultDto();
	    try{
	    	MatrixCardToken token=this.matrixCardChangeTxService.requestNewTokenRdTx(mcVfyRequest, operatorInfo);
	    	resultDto.setObject(token);
	    }catch(MatrixCardException maException){
	    	resultDto.setErrorCode(maException.getErrorEnum().getErrorCode());
	    	resultDto.setErrorMsg(maException.getErrorEnum().getMessage());
	    }catch(Exception e){
	    	resultDto.setErrorCode(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getErrorCode());
	    	resultDto.setErrorMsg(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getMessage());
	    }
	    return resultDto;
    }

	@Override
    public ResultDto requestOldToken(MatrixCardVfyRequest mcVfyRequest, OperatorInfo operatorInfo) {
	    ResultDto resultDto=new ResultDto();
	    try{
	    	MatrixCardToken token=this.matrixCardChangeTxService.requestOldTokenRdTx(mcVfyRequest, operatorInfo);
	    	resultDto.setObject(token);
	    }catch(MatrixCardException maException){
	    	resultDto.setErrorCode(maException.getErrorEnum().getErrorCode());
	    	resultDto.setErrorMsg(maException.getErrorEnum().getMessage());
	    }catch(Exception e){
	    	resultDto.setErrorCode(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getErrorCode());
	    	resultDto.setErrorMsg(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getMessage());
	    }
	    return resultDto;
    }

	@Override
    public ResultDto startChangeTrans(OperatorInfo operatorInfo) {
	    ResultDto resultDto=new ResultDto();
	    try{
	    	MatrixCardTransInfoDto matrixcardTransInfo=this.matrixCardChangeTxService.startChangeTransRdTx(operatorInfo);
	    	resultDto.setObject(matrixcardTransInfo);
	    }catch(MatrixCardException maException){
	    	resultDto.setErrorCode(maException.getErrorEnum().getErrorCode());
	    	resultDto.setErrorMsg(maException.getErrorEnum().getMessage());
	    }catch(Exception e){
	    	resultDto.setErrorCode(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getErrorCode());
	    	resultDto.setErrorMsg(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getMessage());
	    }
	    return resultDto;
    }
	
	public MatrixCardChangeTxService getMatrixCardChangeTxService() {
    	return matrixCardChangeTxService;
    }

	public void setMatrixCardChangeTxService(MatrixCardChangeTxService matrixCardChangeTxService) {
    	this.matrixCardChangeTxService = matrixCardChangeTxService;
    }

}
