/**
 * 
 */
package com.pay.acc.service.member;

import java.util.List;

import com.pay.acc.exception.MaMemberBankException;
import com.pay.acc.service.member.dto.MemberBankDto;

/**
 * 会员银行卡绑定
 * 
 * @author jeffrey_teng
 * 
 * @date 2010-9-23
 */
public interface MemberBankService {

	/**
	 * 绑定银行卡
	 * 
	 * @param bankDto
	 */
	public void doBindingBankCard(MemberBankDto bankDto);

	/**
	 * 查询银行卡绑定信息（状态为已经验证）
	 * 
	 * @param memberCode
	 *            会员号
	 * @return 返回所有银行卡绑定信息列表
	 * @throws MaMemberBankException
	 *             银行卡查询异常
	 */
	public List<MemberBankDto> doQueryMemberBankNsTx(Long memberCode)
			throws MaMemberBankException;
	
	/**
	 * 查询银行卡绑定信息（状态为已经验证）
	 * 
	 * @param memberCode
	 *            会员号
	 * @return 返回所有银行卡绑定信息列表
	 * @throws MaMemberBankException
	 *             银行卡查询异常
	 */
	public MemberBankDto doQueryMemberBankNsTx(Long memberCode,String bankAcct);

	/**
	 * 修改银行卡信息
	 * 
	 * @param bankDto
	 */
	public void doModifyBankCard(MemberBankDto bankDto);
}
