package com.pay.base.service.matrixcard.request;

import com.pay.base.dto.matrixcard.MatrixCardDto;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;
import com.pay.base.exception.matrixcard.MatrixCardException;

public interface IMatrixCardReqTxService {

	/**
	 * 口令卡事务
	 * @param transInfoDto
	 * @return
	 * @throws MatrixCardException
	 */
	public MatrixCardDto requestMatrixCardRdTx(MatrixCardTransInfoDto transInfoDto)throws MatrixCardException;

	/**
	 * @param memberCode
	 * @return
	 */
	MatrixCardDto showRequestMatrixCard(Long memberCode)throws MatrixCardException;
}
