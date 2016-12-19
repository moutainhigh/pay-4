/**
 * 
 */
package com.pay.acc.service.member;
		
import java.util.List;

import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.service.account.dto.MaResultDto;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.acc.service.member.dto.MemberInfoDto;

public interface MemberQueryService {

	/**
	 * 会员查询
	 * 
	 * @param loginName
	 *            登陆名
	 * @param memberCode
	 *            帐号
	 * @param memberType
	 *            会员类型
	 * @param acctType
	 *            帐号类型
	 * @return
	 * @throws MaMemberQueryException
	 */
	public MemberInfoDto doQueryMemberInfoNsTx(String loginName,
			Long memberCode, Integer memberType, Integer acctType)
			throws MaMemberQueryException;

	/**
	 * 查询会员基本信息
	 * 
	 * @param memberCode
	 * @return
	 * @throws MaMemberQueryException
	 */
	public MemberBaseInfoBO queryMemberBaseInfoByMemberCode(Long memberCode)
			throws MaMemberQueryException;

	/**
	 * 查询会员基本信息 根据userId查询（userId是通过sso得到）
	 * 
	 * @param userId
	 *            sso 用户ID
	 * @return 在储值系统会员基本信息
	 * @throws MaMemberQueryException
	 *             查询异常
	 */
	public MemberBaseInfoBO queryMemberBaseInfo(String userId)
			throws MaMemberQueryException;

	/**
	 * 验证用户名登录密码，但不记录登录时间
	 * 
	 * @param loginName
	 * @param passWord
	 * @return MaResultDto ： <li>resultStatus
	 *         <ul>
	 *         1:成功:
	 *         </ul>
	 *         <ul>
	 *         2:账户没有锁定，密码出错
	 *         </ul>
	 *         <ul>
	 *         3:账户锁定
	 *         </ul>
	 *         <ul>
	 *         4:会员不存在
	 *         </ul>
	 *         <ul>
	 *         5:运行时异常
	 *         </ul>
	 *         <ul>
	 *         7:验证失败，但是会员状态异常
	 *         </ul>
	 *         <ul>
	 *         6:验证成功，但是会员状态异常
	 *         </ul>
	 *         <ul>
	 *         * 当返回值为5时，errorMsg异常信息， 当返回值为6和7时，errorCode会员状态代码errorMsg会员状态描述
	 *         </ul>
	 *         </li> <li>errorCode 错误编号</li> <li>errorMsg 错误信息</li> <li>Object
	 *         object
	 *         <ul>
	 *         返回值1,6,7 object返回对象 Member
	 *         </ul>
	 *         <ul>
	 *         返回值2-3 object返回对象: VerifyResultDto: leavingTime 剩余次数 , totalTime
	 *         总的次数, leavingMinute 剩余分钟
	 *         </ul>
	 *         <ul>
	 *         返回值4,5 object都为null
	 *         </ul>
	 *         </li>
	 * @see com.pay.acc.service.account.dto.VerifyResultDto
	 * @see com.pay.acc.member.memberenum.MemberStatusEnum
	 * @see com.pay.acc.member.model.Member
	 * @see com.pay.acc.common.MaConstant 结果常量(resultStatus)
	 * 
	 */
	public MaResultDto doVerifyLoginPassword(String loginName, String passWord);

	/**
	 * 验证用户名密码并记录登录时间
	 * 
	 * @param loginName
	 * @param passWord
	 * @return MaResultDto ： <li>resultStatus
	 *         <ul>
	 *         1:成功:
	 *         </ul>
	 *         <ul>
	 *         2:账户没有锁定，密码出错
	 *         </ul>
	 *         <ul>
	 *         3:账户锁定
	 *         </ul>
	 *         <ul>
	 *         4:会员不存在
	 *         </ul>
	 *         <ul>
	 *         5:运行时异常
	 *         </ul>
	 *         <ul>
	 *         7:验证失败，但是会员状态异常
	 *         </ul>
	 *         <ul>
	 *         6:验证成功，但是会员状态异常
	 *         </ul>
	 *         <ul>
	 *         * 当返回值为5时，errorMsg异常信息， 当返回值为6和7时，errorCode会员状态代码errorMsg会员状态描述
	 *         </ul>
	 *         </li> <li>errorCode 错误编号</li> <li>errorMsg 错误信息</li> <li>Object
	 *         object
	 *         <ul>
	 *         返回值1,6,7 object返回对象 Member
	 *         </ul>
	 *         <ul>
	 *         返回值2-3 object返回对象: VerifyResultDto: leavingTime 剩余次数 , totalTime
	 *         总的次数, leavingMinute 剩余分钟
	 *         </ul>
	 *         <ul>
	 *         返回值4,5 object都为null
	 *         </ul>
	 *         </li>
	 * @see com.pay.acc.service.account.dto.VerifyResultDto
	 * @see com.pay.acc.member.memberenum.MemberStatusEnum
	 * @see com.pay.acc.member.model.Member
	 * @see com.pay.acc.common.MaConstant 结果常量(resultStatus)
	 */
	public MaResultDto doLogin(String loginName, String passWord);

	/**
	 * 查询上一次登录时间
	 * 
	 * @param memberCode
	 *            会员号
	 * @return 登录时间字符串,如果memberCode不存在，则返回nulll
	 */
	public String queryLastLoginTime(Long memberCode);

	/**
	 * 查询企业会员风控等级
	 * 
	 * @param memberCode
	 *            : Long 会员号
	 * @return Long 风控等级
	 */
	public Long queryEnterpriseRiskLeveCode(Long memberCode);
	
	/**
	 * 
	 * @param memberCode
	 * @return
	 */
	public Long queryEnterpriseMccCode(Long memberCode);

	/**
	 * @Title: queryMemberByName
	 * @Description: 由名称查询会员号
	 * @param @param name
	 * @param @return 设定文件
	 * @return List<Long> 返回类型
	 * @throws
	 */
	public List<Long> queryMemberByName(String name);

}
