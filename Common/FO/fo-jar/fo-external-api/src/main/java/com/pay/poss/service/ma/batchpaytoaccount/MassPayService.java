/**
 *  File: MassPayService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-8     darv      Changes
 *  
 *
 */
package com.pay.poss.service.ma.batchpaytoaccount;

import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.exception.MaAccountQueryUntxException;
import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.exception.MaMemberVerifyException;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.acc.service.member.dto.MemberVerifyDto;
import com.pay.acc.service.member.dto.MemberVerifyResult;
import com.pay.poss.base.exception.PossUntxException;

/**
 * @author darv
 * 
 */
public interface MassPayService {
	/**
	 * 查询会员信息
	 * 
	 * @param loginName
	 * @param memberCode
	 * @param memberType
	 * @param acctType
	 * @return
	 * @throws PossUntxException
	 */
	public MemberInfoDto getMemberInfo(String loginName, Long memberCode, Integer memberType, Integer acctType) throws PossUntxException, MaMemberQueryException;

	/**
	 * 查询余额 包括：可用余额，可提现余额，冻结余额
	 * 
	 * @param memberCode
	 *            账户号
	 * @param accountType
	 *            账户类型
	 * @return
	 * @throws MaAccountQueryUntxException
	 */
	public BalancesDto getBalancesInfo(Long memberCode, Integer accountType) throws PossUntxException, MaAccountQueryUntxException;

	/**
	 * 查询账户属性：是否冻结、止入、止出、可转出、可转入
	 * 
	 * @param memberCode
	 * @param accountType
	 * @return
	 * @throws MaAccountQueryUntxException
	 */
	public AcctAttribDto getAcctAttrInfo(Long memberCode, Integer accountType) throws MaAccountQueryUntxException;

	/**
	 * 验证支付密码是否正确
	 * 
	 * @param memberCode
	 * @param accountType
	 * @param payPwd
	 * @return
	 */
	public boolean payPwdIsOrder(long memberCode, int accountType, String payPwd) throws Exception;

	/**
	 * 根据会员编号查询实名认证成功的记录
	 * 
	 * @param memberCode
	 *            会员编号
	 * @return MemberVerifyDto 实名认证记录 ,找不到记录返回NULL
	 */

	public MemberVerifyDto getMemberVerify(Long memberCode);

	/**
	 * 根据会员号、账户类型 查询账户信息
	 * 
	 * @param memberCode
	 *            会员号
	 * @param acctTypeId
	 *            账户类型
	 * @return AcctDto 账户信息
	 */
	public AcctDto getAcctInfo(Long memberCode, Integer acctTypeId);

	public boolean verifyRealName(Long memberCode) throws MaMemberVerifyException;
	
	public MemberBaseInfoBO queryMemberBaseInfoByMemberCode(Long membercode) throws MaMemberQueryException;
	
	public MemberVerifyResult getMemberVerifyResult(Long memberCode) throws Exception;
}
