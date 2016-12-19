package com.pay.acc.deal.service.impl;

import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.deal.service.BalanceEntryService;
import com.pay.acc.deal.service.WithdrawalsRuleService;

public class WithdrawalsRuleServiceImpl implements WithdrawalsRuleService {
	
	private BalanceEntryService balanceEntryService;

	@Override
	public Long withdrawRuleConfig(int rule, Long memberCode, AcctDto acctDto, Long date) {
		switch (rule) {
		case 1:
			return this.sumWithdrawals(memberCode, acctDto);
		case 2:
			return this.twoWeekWithdrawals(memberCode, acctDto, date);
		case 3:
			return this.noWithdrawals(memberCode, acctDto);
		}
		return 0L;
	}

	/**
	 * 不能提现
	 * @param memberCode
	 * @param acctDto
	 * @return
	 */
	private Long noWithdrawals(Long memberCode, AcctDto acctDto) {
		return 0L;
	}

	@Override
	public Long sumWithdrawals(Long memberCode, AcctDto acctDto) {
		return acctDto.getBalance();
	}

	@Override
	public Long twoWeekWithdrawals(Long memberCode, AcctDto acctDto, Long date) {
		String acctCode = acctDto.getAcctCode();
		// 两周内所有入款统计
		Long addAmount = balanceEntryService.querySumTwoWeekAddAmount(acctCode, date);
		// 两周内除提现外所有的出款统计
		Long minusAmount = balanceEntryService.querySumTwoWeekMinusValue(acctCode, date);
		// 14天内所有的出款金额统计
		long payAccount = balanceEntryService.sumTwoWeekMinusValue(acctCode, date);

		// 14天内所有的提现退款统计
		long withdrawRefund = balanceEntryService.queryWithdrawalRefund(acctCode, date);

		// 14天内的入款统计－提现退款
		addAmount -= withdrawRefund;

		// 14天内提现的金额=14天内所有的出款金额统计-14天内除提现外所有的出款统计
		Long balance = java.lang.Math.abs(payAccount) - java.lang.Math.abs(minusAmount);
		// 14天内所有除去提现的所有出款-14天内所有的入款
		Long amount = java.lang.Math.abs(minusAmount) - addAmount;
		// 14天前的余额（可提现金额）
		Long withdrawBalance = balanceEntryService.queryWithdrawalBanalce(acctCode, date);
		if (amount < 0) {// 当14天出款(不包含提现)总计-14天入款总计 < 0 时，可提现金额=14天前余额-提现金额总计。
			withdrawBalance = withdrawBalance - balance;
		}
		else {// 提现金额公式 = 14天前余额-|当14天出款(不包含提现)总计-14天入款总计|-提现金额总计
			withdrawBalance = withdrawBalance - amount - balance;
		}

		// 可提现金额增加14天内的提现
		withdrawBalance += withdrawRefund;

		// 以前的数据可能导致提现的金额大于14天前的余额，可提金额为负，则返回0，这种情况以后不会出现
		if (withdrawBalance < 0) {
			withdrawBalance = 0L;
		}
		return withdrawBalance;
	}

	public void setBalanceEntryService(BalanceEntryService balanceEntryService) {
		this.balanceEntryService = balanceEntryService;
	}

}
