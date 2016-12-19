package com.pay.base.service.matrixcard.unbind;

import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.ResultDto;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;

/**
 * @author jim_chen
 * @version 
 * 2010-9-21 
 */
public interface MatrixCardUnBindService {


	/**解绑
	 * @param serialNo
	 * @param operatorInfo
	 * @return
	 */
	ResultDto unbind(String serialNo, OperatorInfo operatorInfo);
	
	/**
	 * 开启申请解绑事务
	 * 
	 * @param transDto
	 * @return obj:MatrixCardTransInfoDto
	 */
	public ResultDto beginUnBindTrans(MatrixCardTransInfoDto transDto);

	/**
	 * 得到有效的解绑事务
	 * 
	 * @param memberCode
	 * @return MatrixCardTransInfoDto
	 */
	public ResultDto getValidUnBindTrans(Long memberCode);

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
	public ResultDto validateEmailUnBind(Long memberCode,String checkCode);
	
	
}
