/**
 *  File: WithdrawMemberFacadeService.java
 *  Description:会员相关服务facade
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-9-16   Sandy_Yang  create
 *  
 *
 */
package com.pay.poss.service.ma.member;

import java.util.Date;

import com.pay.acc.service.member.dto.MemberInfoDto;

/**
 * 会员相关服务facade
 * 
 * @author Sandy_Yang
 */
public interface WithdrawMemberFacadeService {

	/**
	 * 检索该用户是否可以提现
	 * 
	 * @param memberCode
	 *            会员号
	 * @param accountType
	 *            账户类型
	 * @return
	 */
	public boolean canWithdraw(Long memberCode, int accountType);

	/**
	 * 检索用户可提现金额
	 * 
	 * @param memberCode
	 * @return
	 */
	public Long getWithdrawAmount(Long memberCode, int accountType);

	/**
	 * 检索用户余额
	 * 
	 * @param memberCode
	 * @param accountType
	 * @return
	 */
	public Long getBalance(Long memberCode, int accountType);

	/**
	 * 查询会员是否实名认证通过
	 * 
	 * @param memberCode
	 * @return
	 */
	public boolean doQueryRealNameVerify(Long memberCode);

	/**
	 * 查询会员信息
	 * 
	 * @param memberCode
	 * @return MemberInfoDto
	 * @author Jonathen Ni 2010-10-28
	 */
	public MemberInfoDto qyeryMember(Long memberCode);

	public Long doQueryBalanceByEntryRntx(Long memberCode, Integer accountType);
	/**
	 * 获取指定时间的可提现余额
	 * @param memberCode
	 * @param acctType
	 * @param date
	 * @return
	 */
	public Long getTheDateWithdrawBalance(Long memberCode, int acctType,Date date);
}
