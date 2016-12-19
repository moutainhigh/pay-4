package com.pay.fundout.securitycheck.voter.spi.acct.impl;

import java.util.HashMap;
import java.util.Map;

import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.fundout.securitycheck.common.Constants;
import com.pay.fundout.securitycheck.model.DenyVoteMsg;
import com.pay.fundout.securitycheck.model.Principal;
import com.pay.fundout.securitycheck.voter.spi.acct.BaseAcctAttributeVoter;

/**
 * <p>
 * 收款方会员状态投票
 * </p>
 * 
 * @author rick_lv
 * 
 */
public class PayeeMemberStatusVoter extends BaseAcctAttributeVoter {
	private Map<Integer, String> status = new HashMap<Integer, String>();

	public PayeeMemberStatusVoter() {
		status.put(0, "创建");
		status.put(1, "正常状态");
		status.put(2, "冻结状态");
		status.put(3, "未激活");
		status.put(4, "删除状态");
		status.put(5, "临时账号");
	}

	@Override
	protected int doVote(int busiType, Principal principal, DenyVoteMsg denyVoteMsg) {
		if (payeeUndo.contains(busiType)) {
			return ABSTAIN;
		}

		int memberStatus = principal.getPayeeMemberStatus();
		if (memberStatus != -1) {
			if (memberStatus == 1) {
				return GRANTED;
			} else {
				denyVoteMsg.setCode(Constants.DENY_CODE_MEMBER_PAYEE_STATUS);
				denyVoteMsg.setTips(new String[] { status.get(Integer.valueOf(memberStatus)) });
				return DENIED;
			}
		}

		try {
			MemberBaseInfoBO memberInfo = cachePayeeMemberIfNecessary(principal);

			if (memberInfo == null) {// 找不到会员，说明不需要校验会员
				return ABSTAIN;
			}

			memberStatus = memberInfo.getStatus();
			if (memberStatus == 1) {
				return GRANTED;
			} else {
				denyVoteMsg.setCode(Constants.DENY_CODE_MEMBER_PAYEE_STATUS);
				denyVoteMsg.setTips(new String[] { status.get(Integer.valueOf(memberStatus)) });
				return DENIED;
			}
		} catch (Exception e) {
			denyVoteMsg.setCode(Constants.DENY_CODE_SYSTEM_ERROR);
			denyVoteMsg.setTips(new String[] { principal.toString(), this.toString() });
			return DENIED;
		}
	}
}
