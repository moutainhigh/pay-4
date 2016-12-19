package com.pay.acc.member.service;

import java.util.List;

import com.pay.acc.member.dto.MemberBankAcctDto;
import com.pay.acc.member.exception.MemberException;
import com.pay.acc.member.exception.MemberUnknowException;
import com.pay.acc.service.member.dto.MemberBankDto;

public interface MemberBankAcctService {

	/**
	 * 根据会员代码查询MemberBankAcct列表（状态为已经验证）
	 * 
	 * @param memberCode
	 *            会员号
	 * @return 会员已经的银行卡绑定列表
	 */
	public List<MemberBankAcctDto> queryMemberBankAcct(Long memberCode)
			throws MemberException, MemberUnknowException;

	/**
	 * 根据会员代码查询MemberBankAcct列表（状态为已经验证）
	 * 
	 * @param memberCode
	 *            会员号
	 * @return 会员已经的银行卡绑定列表
	 */
	public List<MemberBankAcctDto> queryMemberBankAcctByMemberCode(
			Long memberCode) throws MemberException, MemberUnknowException;

	/**
	 * 绑定银行卡
	 * 
	 * @param bankDto
	 */
	public void doBindingBankCard(MemberBankDto bankDto);

	/**
	 * 修改银行卡信息
	 * 
	 * @param bankDto
	 */
	public void doModifyBankCard(MemberBankDto bankDto);
}
