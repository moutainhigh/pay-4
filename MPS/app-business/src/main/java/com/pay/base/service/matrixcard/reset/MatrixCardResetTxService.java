package com.pay.base.service.matrixcard.reset;

import com.pay.base.common.helper.matrixcard.MatrixCardToken;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;
import com.pay.base.exception.matrixcard.MatrixCardException;

public interface MatrixCardResetTxService {
	// 有效期1天
	public static final int VALID_DATE = 1;

	// 短消息最大重试次数
	public static final int MAX_WRONG_TIME = 5;

	public void sendValidateCode(Long uId) throws MatrixCardException;

	public boolean isSendSmsCode(Long uId) throws MatrixCardException;

	/**
	 * 开启申请重置事务
	 * 
	 * @param transDto
	 * @param mobile
	 * @return
	 * @throws MatrixCardException
	 */
	public MatrixCardTransInfoDto begnResetTransRdTx(MatrixCardTransInfoDto transDto) throws MatrixCardException;

	/**
	 * 得到有效的重置事务
	 * 
	 * @param uId
	 * @return
	 * @throws MatrixCardException
	 */
	public MatrixCardTransInfoDto getValidResetTrans(Long memberCode,int transType) throws MatrixCardException;

	/**
	 * 验证手机码
	 * 
	 * @param uId
	 * @param validateCode
	 * @return
	 * @throws MatrixCardException
	 */
	public MatrixCardTransInfoDto validateSmsCode(Long uId, String validateCode, Long memberCode,int transType) throws MatrixCardException;

	/**
	 * 验证EMAIL发起的重置
	 * 
	 * @param memberCode
	 */
	public MatrixCardTransInfoDto validateEmail(Long memberCode,int transType,String checkCode) throws MatrixCardException;

	/**
	 * 重置申请单
	 * 
	 * @param mcVfyRequest
	 * @return
	 */
	public MatrixCardToken requestRstBindRdTx(MatrixCardVfyRequest mcVfyRequest) throws MatrixCardException;

	/**
	 * 验证并重新绑定
	 * 
	 * @param matrixCardToken
	 * @param operatorInfo
	 * @param serialNo
	 * @throws MatrixCardException
	 */
	public void toResetRdTx(MatrixCardToken matrixCardToken, OperatorInfo operatorInfo, String serialNo) throws MatrixCardException;
}
