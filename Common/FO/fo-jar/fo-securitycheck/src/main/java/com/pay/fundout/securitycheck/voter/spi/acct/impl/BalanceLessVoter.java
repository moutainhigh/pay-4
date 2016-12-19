package com.pay.fundout.securitycheck.voter.spi.acct.impl;

import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;

import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.fundout.securitycheck.common.Constants;
import com.pay.fundout.securitycheck.model.DenyVoteMsg;
import com.pay.fundout.securitycheck.model.Principal;
import com.pay.fundout.securitycheck.voter.spi.acct.BaseAcctAttributeVoter;

/**
 * <p>
 * 余额不足投票器
 * </p>
 * 
 * @author darv
 * 
 */
public class BalanceLessVoter extends BaseAcctAttributeVoter {

	private NumberFormat numberFormat;

	public BalanceLessVoter() {
		numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);// 最大2位小数
		numberFormat.setMinimumFractionDigits(2);// 最小2为小数
	}

	@Override
	protected int doVote(int busiType, Principal principal, DenyVoteMsg denyVoteMsg) {
		if (payerUndo.contains(busiType)) {
			return ABSTAIN;
		}

		try {
			BalancesDto balancesDto = getBalancesInfo(principal.getPayerMemberCode(), principal.getPayerAcctType());
			if (balancesDto == null) {// 找不到账户，说明不需要校验该账户余额
				return ABSTAIN;
			}

			if (balancesDto.getBalance().longValue() < principal.getAmount()) {
				// 付款账户余额不足
				denyVoteMsg.setCode(Constants.DENY_CODE_BALANCE_LESS);
				denyVoteMsg.setTips(new String[] { numberFormat.format(balancesDto.getBalance() / 1000.00), numberFormat.format(principal.getAmount() / 1000.00) });
				return DENIED;
			} else {
				return GRANTED;
			}
		} catch (Exception e) {
			denyVoteMsg.setCode(Constants.DENY_CODE_SYSTEM_ERROR);
			denyVoteMsg.setTips(new String[] { principal.toString(), this.toString() });
			return DENIED;
		}
	}

	private BalancesDto getBalancesInfo(String memberCode, String acctType) throws Exception {
		// 如果会员号或账户类型为空，则不进行账户信息查询 比如：中间科目情况
		if (StringUtils.isEmpty(memberCode) || StringUtils.isEmpty(acctType) || memberCode.length() == 13) {
			return null;
		}

		return massPayService.getBalancesInfo(new Long(memberCode), new Integer(acctType));
	}

}
