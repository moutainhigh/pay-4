/** @Description 
 * @project 	poss-refund
 * @file 		RD4MaServiceApi.java 
 * Copyright (c) 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-27		sunsea.li		Create 
 */
package com.pay.poss.service.ma;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.exception.MaAccountQueryUntxException;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.poss.base.exception.PossUntxException;

/**
 * <p>
 * 对外调用ma服务接口
 * </p>
 * 
 * @author sunsea.li
 * @since 2010-9-27
 * @see
 */
public interface RD4MaServiceApi {
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
	public MemberInfoDto doQueryMemberInfoNsTx(String loginName,
			Long memberCode, Integer memberType, Integer acctType)
			throws PossUntxException;

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
	public BalancesDto doQueryBalancesNsTx(Long memberCode, Integer accountType)
			throws PossUntxException;

	/**
	 * 查询账户属性
	 * 
	 * @param memberCode
	 *            账户号
	 * @param accountType
	 *            账户类型
	 * @return
	 * @throws MaAccountQueryUntxException
	 */
	public AcctAttribDto doQueryAcctAttribNsTx(Long memberCode,
			Integer accountType) throws PossUntxException;
}
