package com.pay.acc.member.service;

import com.pay.acc.member.dto.LinkerDto;
import com.pay.acc.member.exception.MemberException;
import com.pay.acc.member.exception.MemberUnknowException;

public interface LinkerService {

	/**
	 * 根据双方的联系人会员号查询是否互为联系人
	 * 
	 * @param orgMemberCode
	 *            我的会员号
	 * @param linkMemberCode
	 *            联系人会员号
	 * @return
	 * @throws MemberException 
	 * @throws MemberUnknowException 
	 */
	public LinkerDto queryCheckLinkerWithMemberCode(Long orgMemberCode, Long linkMemberCode) throws MemberException, MemberUnknowException;

}
