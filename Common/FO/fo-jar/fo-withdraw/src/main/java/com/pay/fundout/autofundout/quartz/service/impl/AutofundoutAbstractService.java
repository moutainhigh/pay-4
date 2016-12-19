/**
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
 */
package com.pay.fundout.autofundout.quartz.service.impl;

import java.util.List;

import com.pay.acc.exception.MaBankCardBindException;
import com.pay.acc.service.member.BankCardBindService;
import com.pay.acc.service.member.MemberProductService;
import com.pay.acc.service.member.dto.BankCardBindBO;
import com.pay.fundout.autofundout.custom.model.AutoFundoutResult;
import com.pay.fundout.autofundout.custom.service.AutoWithdrawService;
import com.pay.fundout.autofundout.quartz.service.AutofundoutQuartzService;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;

/**
 * @author fangzhang
 * 
 */
public abstract class AutofundoutAbstractService implements
		AutofundoutQuartzService {

	private BankCardBindService bankCardBindService;
	private NotifyFacadeService notifyFacadeService;
	private AutoWithdrawService autoWithdrawService;
	/** 会员产品开通权限**/
	private MemberProductService memberProductService;
	/**
	 * @param memberProductService the memberProductService to set
	 */
	public void setMemberProductService(MemberProductService memberProductService) {
		this.memberProductService = memberProductService;
	}

	private String queueName;

	protected boolean verifyBindBankCard(final Long memberCode,
			final String bankAcctCode) {

		try {
			List<BankCardBindBO> bankCards = bankCardBindService
					.doQueryBankCardBindInfoForVerifyNsTx(memberCode);
			if (null != bankCards && !bankCards.isEmpty()) {
				for (BankCardBindBO bank : bankCards) {
					if (bank.getBankAcctId().equals(bankAcctCode)) {
						return true;
					}
				}
			}

		} catch (MaBankCardBindException e) {
			LogUtil.error(this.getClass(), "企业自动提现卡bin验证", OPSTATUS.EXCEPTION, String.valueOf(memberCode),
					"企业自动提现卡bin验证", e.getMessage(),
					"0011", e.getMessage());
		}
		return false;
	}
	
	
	//用户产品开通权限
	protected boolean isHaveProduct(Long memberCode){
		return memberProductService.isHaveProduct(Long.valueOf(memberCode), "AUTO_WITHDRAW");
	}
	
	//构建对象产生
	protected Notify2QueueRequest buildNotify2QueueRequest(AutoFundoutResult result) {
		Notify2QueueRequest request = new Notify2QueueRequest();
		request.setQueueName(queueName);
		request.setTargetObject(result);
		return request;
	}
	
	public void setBankCardBindService(BankCardBindService bankCardBindService) {
		this.bankCardBindService = bankCardBindService;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public void setNotifyFacadeService(NotifyFacadeService notifyFacadeService) {
		this.notifyFacadeService = notifyFacadeService;
	}

	public NotifyFacadeService getNotifyFacadeService() {
		return notifyFacadeService;
	}

	public AutoWithdrawService getAutoWithdrawService() {
		return autoWithdrawService;
	}

	public void setAutoWithdrawService(AutoWithdrawService autoWithdrawService) {
		this.autoWithdrawService = autoWithdrawService;
	}
}
