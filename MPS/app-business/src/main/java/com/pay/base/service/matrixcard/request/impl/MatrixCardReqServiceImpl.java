package com.pay.base.service.matrixcard.request.impl;

import com.pay.base.common.helper.matrixcard.ErrorCodeMatrixExceptionEnum;
import com.pay.base.common.util.matrixcard.MatrixCardViewer;
import com.pay.base.dto.ResultDto;
import com.pay.base.dto.matrixcard.MatrixCardDto;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.base.service.matrixcard.IMatrixCardService;
import com.pay.base.service.matrixcard.request.IMatrixCardReqService;
import com.pay.base.service.matrixcard.request.IMatrixCardReqTxService;

/**
 * @author jim_chen
 * @version 2010-9-16
 */
public class MatrixCardReqServiceImpl implements IMatrixCardReqService {

	IMatrixCardService matrixCardService;
	IMatrixCardReqTxService matrixCardReqTxService;

	@Override
    public ResultDto showRequestMatrixCard(Long memberCode) {
		ResultDto resultDto = new ResultDto();
		try {
			MatrixCardDto matrixCardDto = matrixCardReqTxService.showRequestMatrixCard(memberCode);
			resultDto.setObject(matrixCardDto);
		}
		catch (MatrixCardException e) {
			resultDto.setErrorCode(e.getErrorEnum().getErrorCode());
			resultDto.setErrorMsg(e.getErrorEnum().getMessage());
		}
		catch (Exception e) {
			resultDto.setErrorCode(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getErrorCode());
			resultDto.setErrorMsg(ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR.getMessage());
		}
		return resultDto;
    }

	@Override
	public ResultDto processRequest(MatrixCardTransInfoDto transInfoDto) {
		ResultDto resultDto = new ResultDto();
		try {
			MatrixCardDto matrixCardDto = matrixCardReqTxService.requestMatrixCardRdTx(transInfoDto);
			resultDto.setObject(matrixCardDto);
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

	public byte[] download(String id, String sessionId) throws MatrixCardException {

		MatrixCardDto matrixCardDto = matrixCardService.findById(Long.valueOf(id));

		if (matrixCardDto == null) {
			return null;
		}
		return MatrixCardViewer.showUserCard(matrixCardDto);

	}

	public IMatrixCardService getMatrixCardService() {
		return matrixCardService;
	}

	public void setMatrixCardService(IMatrixCardService matrixCardService) {
		this.matrixCardService = matrixCardService;
	}

	public IMatrixCardReqTxService getMatrixCardReqTxService() {
    	return matrixCardReqTxService;
    }

	public void setMatrixCardReqTxService(IMatrixCardReqTxService matrixCardReqTxService) {
    	this.matrixCardReqTxService = matrixCardReqTxService;
    }


}
