/** @Description 
 * @project 	poss-refund
 * @file 		RD4MaServiceMock.java 
 * Copyright (c) 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-27		sunsea.li		Create 
 */
package com.pay.poss.service.ma.impl;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.service.ma.RD4MaServiceApi;

/**
 * <p>
 * 对外调用ma服务接口 Mock实现
 * </p>
 * @author sunsea.li
 * @since 2010-9-27
 * @see
 */
public class RD4MaServiceMock implements RD4MaServiceApi {

	/* (non-Javadoc)
	 * @see com.pay.poss.service.ma.RD4MaServiceApi#doQueryMemberInfoNsTx(java.lang.String, java.lang.Long, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public MemberInfoDto doQueryMemberInfoNsTx(String loginName,
			Long memberCode, Integer memberType, Integer acctType)
			throws PossUntxException {
		MemberInfoDto memberInfoDto = new MemberInfoDto();
		memberInfoDto.setLoginName("liwei_851227@163.com");
		memberInfoDto.setMemberCode(10000000050L);
		memberInfoDto.setMemberType(new Integer(2));
		memberInfoDto.setMemberName("李威");
		//memberInfoDto.setAcctType("人民币");
		return memberInfoDto;
	}

	/* (non-Javadoc)
	 * @see com.pay.poss.service.ma.RD4MaServiceApi#doQueryBalancesNsTx(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public BalancesDto doQueryBalancesNsTx(Long memberCode, Integer accountType)
			throws PossUntxException {
		BalancesDto balancesDto = new BalancesDto();
		balancesDto.setBalance(500L);
		return balancesDto;
	}

	/* (non-Javadoc)
	 * @see com.pay.poss.service.ma.RD4MaServiceApi#doQueryAcctAttribNsTx(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public AcctAttribDto doQueryAcctAttribNsTx(Long memberCode,
			Integer accountType) throws PossUntxException {
		AcctAttribDto acctAttribDto = new AcctAttribDto();
		return acctAttribDto;
	}
	
}
