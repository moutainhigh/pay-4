 /** @Description 
 * @project 	poss-external-api
 * @file 		AbstractExternalService.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-1		Henry.Zeng			Create 
*/
package com.pay.poss.service.adapter;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.MemberRelationQueryService;
import com.pay.acc.service.account.AccountInfoService;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.member.BankCardBindService;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.MemberVerifyService;
import com.pay.cardbin.service.CardBinInfoFactoryService;
import com.pay.inf.service.BankService;

/**
 * <p>适配外部接口</p>
 * @author Henry.Zeng
 * @since 2010-9-1
 * @see 
 */
public abstract class AbstractExternalAdapter {

	protected transient Log log = LogFactory.getLog(getClass());
	
	protected AccountQueryService accountQueryService;
	
	protected AccountInfoService accountInfoService;
	
	protected MemberVerifyService memberVerifyService;
	
	
	//ma提供给充退 查会员信息service liwei 20100928
	protected MemberQueryService memberQueryService;
	/**
	 * 添加卡Bin验证服务
	 */
	//protected CardBinInfoFactoryService cardBinService;
	
	/**
	 * 银行卡绑定服务类
	 */
	protected BankCardBindService bankCardBindService;
	
	/**
	 * 会员关联查询服务
	 */
	protected MemberRelationQueryService memberRelationQueryService;
	
	
	
	/**
	 * 银行服务类
	 */
	protected BankService bankService;
	
	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	public void setMemberVerifyService(MemberVerifyService memberVerifyService) {
		this.memberVerifyService = memberVerifyService;
	}
	
	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

	public void setAccountInfoService(AccountInfoService accountInfoService) {
		this.accountInfoService = accountInfoService;
	}

	protected <T> String printObjToString(T obj){
		return ToStringBuilder.reflectionToString(obj,ToStringStyle.MULTI_LINE_STYLE); 
	}
	
	/*public void setCardBinService(CardBinInfoFactoryService cardBinService) {
		this.cardBinService = cardBinService;
	}*/
	
	

	/**
	 * @param bankCardBindService the bankCardBindService to set
	 */
	public void setBankCardBindService(BankCardBindService bankCardBindService) {
		this.bankCardBindService = bankCardBindService;
	}


	/**
	 * @param bankService the bankService to set
	 */
	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}
	
	


	public void setMemberRelationQueryService(
			MemberRelationQueryService memberRelationQueryService) {
		this.memberRelationQueryService = memberRelationQueryService;
	}

	protected <T> String printLog(Object obj ,Integer flag){
		String logMsg = "";
		if(flag == 1){
			logMsg = "【"+obj.getClass().getName()+"调用GateWay入参】  ";
		}
		else if(flag == 2){
			logMsg = "【"+obj.getClass().getName()+"调用GateWay返回值】  ";
		}else if(flag ==3){
			logMsg = "【"+obj.getClass().getName()+"调用GateWay异常信息】  ";
		}
		return logMsg;
		
	}
}
