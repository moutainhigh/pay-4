package com.pay.base.service.matrixcard.reset;

import com.pay.base.common.helper.matrixcard.MatrixCardToken;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.ResultDto;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;

/**
 * @author jim_chen
 * @version 2010-9-20
 */
public interface MatrixCardResetService {

	/**
	 * 开启申请重置事务
	 * 
	 * @param transDto
	 * @return obj:MatrixCardTransInfoDto
	 */
	public ResultDto begnResetTrans(MatrixCardTransInfoDto transDto);

	/**
	 * 得到有效的重置事务
	 * 
	 * @param uId
	 * @return MatrixCardTransInfoDto
	 */
	public ResultDto getValidResetTrans(Long uId);

	/**
	 * 验证手机码
	 * 
	 * @param uId
	 * @param validateCode
	 * @param memberCode
	 * @return MatrixCardTransInfoDto
	 */
	public ResultDto validateSmsCode(Long uId, String validateCode,Long memberCode);

	/**
	 * 验证EMAIL发起的重置
	 * 
	 * @param operatorInfo
	 * @param serialNo
	 * @return boolean
	 */
	public ResultDto validateEmailReset(Long memberCode,String checkCode);

	/**
	 * 重置申请单
	 * 
	 * @param mcVfyRequest
	 * @return MatrixCardToken
	 */
	public ResultDto requestRstBind(MatrixCardVfyRequest mcVfyRequest);

	/**
	 * 验证并重新绑定
	 * 
	 * @param matrixCardToken
	 * @param operatorInfo
	 * @param serialNo
	 * @return boolean
	 */

	public ResultDto toReset(MatrixCardToken matrixCardToken, OperatorInfo operatorInfo, String serialNo);
}
