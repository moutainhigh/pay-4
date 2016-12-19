/**
 * 
 */
package com.pay.acc.service.member;

import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.exception.MaMemberVerifyException;
import com.pay.acc.service.member.dto.MemberVerifyDto;
import com.pay.acc.service.member.dto.MemberVerifyResult;
/**
 * @author jeffrey_teng 
 * 
 * @date 2010-9-23
 */
public interface MemberVerifyService {

	
	/**
	 * 根据会员编号查询是否实名认证
	 * @param memberCode  会员编号
	 * @return   true 实名认证，false或者其他表示没有实名认证
	 * @throws MaMemberVerifyException  异常
	 */

	public boolean doQueryRealNameVerifyNsTx(Long memberCode)throws MaMemberVerifyException;
	
	/**
	 * 根据memberCode查询实名认证信息
	 * @param memberCode
	 * @return MemberVerifyResult
	 * @exception Exception
	 * @author lei.jiangl
	 */
	public MemberVerifyResult QueryMemberVerifyByMemberCode(Long memberCode) throws Exception;
	
	/**
	 * 根据会员编号查询实名认证成功的记录
	 * @param memberCode  会员编号
	 * @return   MemberVerifyDto 实名认证记录 ,找不到记录返回NULL
	 * @throws MaMemberVerifyException  异常
	 */

	public MemberVerifyDto doQueryMemberVerifyInfoNsTx(Long memberCode)throws MaMemberVerifyException;
	
	/**
	 * 支付安全问题验证接口
	 * 
	 * @param memberCode
	 *            :Long 会员号
	 * @param securQuestionId
	 *            :Integer 安全问题ID
	 * @param answer
	 *            :String 安全问题答案
	 * @return true 表示成功，false其他表示失败
	 * @throws MaMemberQueryException
	 *             会员操作异常
	 * 
	 */
	public boolean doVerifyMemberSecurityQuestionNsTx(Long memberCode, Integer securQuestionId, String answer) throws MaMemberVerifyException;
	
	
	
	/**
	 * 会员激活
	 * @param memberCode
	 * @return
	 */
	public boolean active(Long memberCode);
	
	/**
	 * 修改登录密码
	 * @param memberCode
	 * @param loginPwd
	 * @return
	 */
	public boolean updateLoginPwd(Long memberCode,String loginPwd);
	
	/**
	 * 验证失败
	 */
	public static final boolean  VERIFY_FAIL= false;
	
	/**
	 * 验证成功
	 */
	public static final boolean VERIFY_SUCCESS = true;
	
	
	/**
	 * 0没有实名认证记录 
	 */
	public static final int QUERY_NO_RECORD = 0;
	
	/**
	 *  1存在失败的实名认证记录，还可以继续实名认证
	 */
	public static final int QUERY_EXIST_RECORD = 1;
	
	/**
	 * 2已经实名认证 不可继续实名认证
	 */
	public static final int QUERY_ALREADY_VERIFY= 2;
	
	
	/**
	 * 0创建失败
	 */
	public static final int CREATE_FAIL = 0;
	/**
	 * 1创建成功
	 */
	public static final int CREATE_SUCCESS = 1;
}
