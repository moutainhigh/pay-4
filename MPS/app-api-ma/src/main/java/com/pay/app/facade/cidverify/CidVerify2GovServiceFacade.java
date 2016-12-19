package com.pay.app.facade.cidverify;

import java.util.Map;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.service.account.dto.AccountBalanceChangeDto;
import com.pay.acc.service.account.dto.CalFeeReponseDto;
import com.pay.acc.service.account.dto.MaResultDto;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.pe.service.CalFeeReponse;
import com.pay.pe.service.CalFeeRequest;

/**
 * @author lei.jiangl
 * @version
 * @data 2010-9-15 下午07:11:59
 */
public interface CidVerify2GovServiceFacade {

	// 根据membercode查询当前账户是否可以实名认证
	public boolean checkQueryCidVerify(String memberCode);

	/**
	 * 查询会员账户可用余额
	 * 
	 * @param memberCode
	 * @param accountType
	 * @return Long
	 */
	public Long getAccountBalance(Long memberCode, Integer accountType);

	/**
	 * 查询会员账户状态(止入,止出)
	 * 
	 * @param memberCode
	 * @param accountType
	 * @return boolean
	 */
	public boolean getAccountState(Long memberCode, Integer accountType);

	/**
	 * 查询会员的支付密码是否正确
	 * 
	 * @param memberCode
	 * @param accountType
	 * @param newPayPwd
	 * @return int (1 表示支付密码正确,0 不匹配)
	 */
	public int checkPayPassword(Long memberCode, Integer accountType,
			String newPayPwd);

	/**
	 * 公安网扣费
	 * 
	 * @param calFeeRequest
	 * @return boolean
	 */
	public CalFeeReponse caculateFee(CalFeeRequest calFeeRequest);

	/**
	 * 更新 用户账户余额
	 * 
	 * @author Sunny Ying
	 * @description TODO
	 * @param accountBalanceChangeDto
	 * @throw null
	 * @return Integer
	 */
	public Integer updateAccountBalance(CalFeeReponseDto calFeeReponseDto,
			Integer dealType);

	/**
	 * 查询 账户属性
	 * 
	 * @author Sunny Ying
	 * @description TODO
	 * @param memberCode
	 *            ,accountType
	 * @throw null
	 * @return AcctAttribDto
	 */
	public AcctAttribDto getAccountAcctAttrib(Long memberCode,
			Integer accountType);

	/**
	 * 更新 用户账户余额(老版本)
	 * 
	 * @author Sunny Ying
	 * @description TODO
	 * @param
	 * @throw null
	 * @return Integer
	 */
	public Integer updateAccountBalance(
			AccountBalanceChangeDto accountBalanceChangeDto);

	/**
	 * 根据登录名查询
	 * 
	 * @author Sunny Ying
	 * @description TODO
	 * @param
	 * @throw null
	 * @return MemberInfoDto
	 */
	public MemberInfoDto doQueryMemberInfoNsTx(String loginName);

	/**
	 * 对账户的上锁操作
	 * 
	 * @author Sunny Ying
	 * @param memberCode
	 * @param acctType
	 * @param acctLockType
	 * @throw null
	 * @return boolean
	 */
	public boolean doHandlerAccountLockRnTx(String memberCode);

	/**
	 * 封装 计费CalFeeRequest
	 * 
	 * @author Sunny Ying
	 * @throw null
	 * @return CalFeeReponseDto
	 */
	public CalFeeRequest setCaculateFee(AcctAttribDto acctAttribDto,
			String memberCode, String orderId);

	/**
	 * 封装 计费 CalFeeReponseDto
	 * 
	 * @author Sunny Ying
	 * @param calFeeRespone
	 * @throw null
	 * @return CalFeeReponseDto
	 */
	public CalFeeReponseDto setCalFeeReponseDto(CalFeeReponse calFeeRespone);

	/**
	 * 实名认证公安网退费， 计费
	 * 
	 * @author Sunny Ying
	 * @param acctAttribDto
	 * @param memberCode
	 * @param orderId
	 * @throw null
	 * @return CalFeeRequest
	 */
	public CalFeeRequest setBackCaculateFee(AcctAttribDto acctAttribDto,
			String memberCode, String orderId);

	/**
	 * 
	 * @author Sunny Ying
	 * @param acctAttribDto
	 * @param memberCode
	 * @param orderId
	 * @throw null
	 * @return Map
	 */
	public Map refundMember(AcctAttribDto acctAttribDto, String memberCode,
			String orderId);

	/**
	 * 查询用户支付密码剩余输入次数
	 * 
	 * @author Sunny Ying
	 * @param memberCode
	 * @param accountType
	 * @param payPassward
	 * @throw null
	 * @return int
	 */
	public int getPayPwdLeavingTime(String memberCode, int accountType,
			String payPassward, Long operatorId);

	/**
	 * 密码验证
	 * 
	 * @param memberCode
	 * @param accountType
	 * @param payPassward
	 * @return
	 */
	public MaResultDto getPayPwdResultDto(String memberCode, int accountType,
			String payPassward, Long operatorId);

	/**
	 * 公安网中间科目收入到 其他业务账户
	 * 
	 * @author Sunny Ying
	 * @param orderId
	 * @throw null
	 * @return Map
	 */
	public Map doFeeToBusinessAccount(String orderId);

	/**
	 * 支付账户打钱到公安网账户
	 * 
	 * @author Sunny Ying
	 * @param orderId
	 * @throw null
	 * @return Map
	 */
	public Map doFeeToGovAccount(String orderId);

}
