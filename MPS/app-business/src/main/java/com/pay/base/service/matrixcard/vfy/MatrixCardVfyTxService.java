package com.pay.base.service.matrixcard.vfy;

import com.pay.base.common.helper.matrixcard.ErrorCodeMatrixExceptionEnum;
import com.pay.base.common.helper.matrixcard.MatrixCardToken;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.base.model.matrixcard.MatrixCardVfy;

public interface MatrixCardVfyTxService {

	/**
	 * 验证口令卡，失败也记录登陆的次数
	 * 
	 * @param serialNo
	 * @param matrixCardToken
	 * @param operatorInfo
	 * @return
	 * @throws MatrixCardException
	 *             返回错误枚举
	 */
	ErrorCodeMatrixExceptionEnum verifyRnTx(String serialNo, MatrixCardToken matrixCardToken, OperatorInfo operatorInfo) throws MatrixCardException;

	/**
	 * 验证矩阵卡token的正确性
	 * 
	 * @param maxtrixVfy
	 * @param serialNo
	 * @param matrixCardToken
	 * @param operatorInfo
	 * @return
	 * @throws MatrixCardException
	 */
	MatrixCardVfy getMatrixCardVfyRnTx(MatrixCardVfy maxtrixVfy, String serialNo, MatrixCardToken matrixCardToken, OperatorInfo operatorInfo) throws MatrixCardException;

}
