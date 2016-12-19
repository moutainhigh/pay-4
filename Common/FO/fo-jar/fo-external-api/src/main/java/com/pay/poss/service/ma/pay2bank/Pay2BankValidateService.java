/**
 *  File: RealNameValidateService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-24      bill_peng     Changes
 *  
 *
 */
package com.pay.poss.service.ma.pay2bank;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.acc.service.member.dto.MemberVerifyResult;


/**
 * @author bill_peng
 *
 */
public interface Pay2BankValidateService {
	
	
	
	
	/**
	 * 验证该用户是否允许付款到银行账户
	 * @param memberCode    付款会员号
	 * @param accountType;  付款账户类型
	 */
	String validatePayerAccount(long memberCode,int accountType);
	
	
	
	/**
	 * 验证银行卡卡号是否有效
	 * @param bankCardCode  银行卡卡号 
	 * @param orgCode       银行机构号
	 * @return
	 */
	String validateCardBin(String bankCardCode,String orgCode);
	
	/**
	 * 获取余额
	 * @param memberCoee   会员号
	 * @param acctType     账户类型
	 * @return
	 */
	Long getBalance(Long memberCoee, int acctType);
	
	/**
	 * 根据会员号获取会员信息
	 * @param memberCode
	 * @return
	 */
	public MemberInfoDto getMemberByMemberCode(Long memberCode);
	

	/**
	 * 根据会员获取会员验证信息
	 * @param memberCode
	 * @return
	 */
	public MemberVerifyResult getMemberVerifyInfo(Long memberCode);
	
	
	AcctAttribDto getAcctAttributeDto(Long memberCode,int acctType);
	
	


}
