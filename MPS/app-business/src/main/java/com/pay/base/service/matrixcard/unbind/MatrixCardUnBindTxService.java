package com.pay.base.service.matrixcard.unbind;

import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;
import com.pay.base.exception.matrixcard.MatrixCardException;

public interface MatrixCardUnBindTxService {

	/**
	 * 解绑事务
	 * 
	 * @param memberCode
	 * @throws MatrixCardException
	 */
	void unBindRdTx(String serialNo, OperatorInfo operatorInfo) throws MatrixCardException;

	/**
	 * 开户一个解绑定的事务
	 * 
	 * @param transDto
	 * @return
	 * @throws MatrixCardException
	 */
	MatrixCardTransInfoDto processUnBindRnTx(MatrixCardTransInfoDto transDto) throws MatrixCardException;
}
