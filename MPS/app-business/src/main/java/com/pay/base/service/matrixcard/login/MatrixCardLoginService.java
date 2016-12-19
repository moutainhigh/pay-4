package com.pay.base.service.matrixcard.login;

import com.pay.base.common.helper.matrixcard.MatrixCardToken;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.ResultDto;
import com.pay.base.dto.matrixcard.MatrixCardDto;

public interface MatrixCardLoginService {
	
	/**根据会员查询
	 * @param memberCode
	 * @return
	 */
	MatrixCardDto findByBindMemberCode(Long memberCode);
	
	
	/**
	 * 生成绑定显示的随机码
	 * @param mcVfyRequest
	 * @param operatorInfo
	 * @return
	 */
	ResultDto processRequest(MatrixCardVfyRequest mcVfyRequest, OperatorInfo operatorInfo);
	
	/**
	 * @param serialNo
	 * @param matrixCardToken
	 * @param operatorInfo
	 * @return
	 */
	ResultDto  verifyLogin(String serialNo, MatrixCardToken matrixCardToken, OperatorInfo operatorInfo);
	
	
}
