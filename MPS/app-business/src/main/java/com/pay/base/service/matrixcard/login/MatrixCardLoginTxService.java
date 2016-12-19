package com.pay.base.service.matrixcard.login;

import com.pay.base.common.helper.matrixcard.MatrixCardToken;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.matrixcard.MatrixCardDto;
import com.pay.base.exception.matrixcard.MatrixCardException;

public interface MatrixCardLoginTxService {

	/**
	 * 根据会员查询
	 * 
	 * @param memberCode
	 * @return
	 */
	MatrixCardDto findByBindMemberCode(Long memberCode);

	/**
	 * 生成绑定显示的随机码
	 * 
	 * @param mcVfyRequest
	 * @param operatorInfo
	 * @return
	 */
	MatrixCardToken processLoginRdTx(MatrixCardVfyRequest mcVfyRequest, OperatorInfo operatorInfo) throws MatrixCardException;

	/**
	 * @param serialNo
	 * @param matrixCardToken
	 * @param operatorInfo
	 * @return
	 * @throws MatrixCardException
	 */
	void verifyRdTx(String serialNo, MatrixCardToken matrixCardToken, OperatorInfo operatorInfo) throws MatrixCardException;
}
