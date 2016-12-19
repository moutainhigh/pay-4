/**
 * 
 */
package com.pay.acc.service.account;

import com.pay.acc.exception.MaMemberException;
import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.service.account.dto.MaResultDto;

/**
 * 账户信息服务，账户信息查询，会员信息查询，安全问题设置，密码设置，密码验证。
 * <p>
 * 其中密码验证，安全问题验证，采用签名和摘要算法
 * <p>
 * 
 * @author jeffrey_teng 
 * 
 * @date 2010-9-26
 */
public interface AccountInfoService {
	
	
	
	
	/**
	 * 查询会员是否有支付密码
	 * @param memberCode 会员号
	 * @param acctType 账户类型
	 * @return
	 * @throws MaMemberQueryException 
	 * @throws MaMemberException 
	 */
	public boolean doQueryIsHavePayPwd(Long memberCode, Integer acctType) throws MaMemberQueryException, MaMemberException;


	/**
	 * 账户支付密码重置
	 * 
	 * @param memberCode
	 *            会员号
	 * @param newPayPwd
	 *            新密码
	 * @return 1表示成功，其他表示失败
	 * @throws MaMemberException
	 *             会员操作异常
	 */
	public int doResetPayPwdRnTx(long memberCode, int acctType, String newPayPwd) throws MaMemberException;


	/**
	 * 支付密码验证接口
	 * 
	 * @param memberCode
	 *            会员号
	 * @param accountType
	 *            账户类型
	 * @param payPwd
	 *            支付密码
	 * @return 1表示成功，其他表示失败
	 * @throws MaMemberException
	 *             会员操作异常
	 */
	public int doVerifyPayPasswordNsTx(long memberCode, int accountType, String payPwd) throws MaMemberQueryException;
	
	
	/**
	 * 验证支付密码(账户锁定，解锁)
	 * @param memberCode 会员号
	 * @param accountType账户类型
	 * @param payPwd支付密码
	 * @param operatorId 操作员Id --请调用该方法SessionHelper.getOperatorIdBySession()
	 *  * @return MaResultDto ：
	 * 	<li>	resultStatus 
	 * 	<ul>		1:成功:</ul>
	 * 	<ul>		2:账户没有锁定，密码出错</ul>
	 * 	<ul>		3:账户锁定</ul>
	 * 	<ul>		4:会员不存在,会员被冻结，运行时异常</ul>
	 * 	<ul>		5:账户后台锁定</ul>
	 *	<li>	errorCode 错误编号</li>
	 * 	<li>	errorMsg  错误信息</li>
	 * 	<li>Object object 返回结果对象: VerifyResultDto
	 * 				leavingTime 剩余次数		
	 * 				totalTime 总的次数	
	 * 				leavingMinute 剩余分钟</li>
	 * @see com.pay.acc.service.account.dto.VerifyResultDto	 
	 */
	MaResultDto doVerifyPayPassword(long memberCode, int accountType, String payPwd,Long operatorId);
	

	/**
	* @Title: doVerifyPayPassword
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param memberCode
	* @param @param accountType
	* @param @param payPwd
	* @param @param operatorId
	* @param @param isVerificationCertUser 是否需要使用证书支付密码　true需要:false
	* @return MaResultDto    返回类型
	* @see com.pay.acc.service.account.dto.VerifyResultDto	 
	* @throws
	*/ 	
	MaResultDto doVerifyPayPassword(long memberCode, int accountType, String payPwd,Long operatorId,boolean isVerificationCertUser);

	/**
	 * 验证支付密码
	 * @param loginName 帐号
	 * @param operatorIdentity 操作员登录名
	 * @param accountType 账户类型
	 * @param payPwd 支付密码
	 * @return
	 */
	MaResultDto doVerifyPayPassword(String loginName, String operatorIdentity, int accountType, String payPwd);
	
	
	/**
	 * 支付安全问题验证接口(账户锁定/解锁)
	 * 
	 * @param memberCode
	 *            :long 会员号
	 * @param securQuestionId
	 *            :int 安全问题ID
	 * @param answer
	 *            :String 安全问题答案
	 * @return 1 表示成功，其他表示失败
	 * @throws MaMemberQueryException
	 *             会员操作异常
	 * 
	 */
	public int doVerifySecurityQuestionNsTx(long memberCode, int securQuestionId, String answer) throws MaMemberQueryException;


	/**新增日志
	 * @param type 类型
	 * @param objectCode 会员号/账户
	 * @param actionUrl 动作
	 * @throws MaMemberQueryException
	 */
	public void addOperateLog(Long type,String objectCode,String actionUrl)throws MaMemberQueryException;
	
	/**
	 * 解密数字证书控件加密的密码
	 * @param encryptPwd 密码
	 * @return 明文
	 */
	public String decryptCertPwd(String encryptPwd);

	/**
	 * 设置失败
	 */
	public static final int SET_FAILED = 0;//失败

	/**
	 * 验证失败
	 */
	public static final int VERIFY_FAILED = 0;//失败
	
	
	/**
	 * 设置密码成功
	 */
	public static final int SET_PASSWORD_SUCCEED = 1;

	/**
	 * 验证密码成功
	 */
	public static final int VERIFY_PASSWORD_SUCCEED = 1;

	
	/**
	 * 验证问题成功
	 */
	public static final int VERIFY_QUESTION_SUCCEED = 1;


}
