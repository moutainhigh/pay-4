package com.pay.fundout.securitycheck.voter.spi.busi.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.fundout.securitycheck.common.Constants;
import com.pay.fundout.securitycheck.model.DenyVoteMsg;
import com.pay.fundout.securitycheck.model.OrderModel;
import com.pay.fundout.securitycheck.model.Principal;
import com.pay.fundout.securitycheck.voter.spi.busi.BaseBusi4FOVoter;
import com.pay.inf.dao.BaseDAO;

/**
 * <p>
 * 出款条件投票器，目前检查出款条件为收付双方
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-12-14
 */
public class FundoutConditionVoter extends BaseBusi4FOVoter {
	private BaseDAO daoService;
	private List<String> successBusiness;
	private Map<String, String> tableName = new HashMap<String, String>();

	public FundoutConditionVoter() {
		tableName
				.put("14", "securitycheck.backfund.queryOrderSrcOfFail4Refund");
		tableName
				.put("15", "securitycheck.backfund.queryOrderSrcOfFail4Refund");
		tableName
				.put("16", "securitycheck.backfund.queryOrderSrcOfFail4Refund");
		tableName.put("20",
				"securitycheck.backfund.queryOrderSrcOfFail4Pay2Acct");
		tableName.put("21",
				"securitycheck.backfund.queryOrderSrcOfFail4Pay2Acct");
		tableName.put("22",
				"securitycheck.backfund.queryOrderSrcOfFail4Pay2Acct");
		tableName.put("23",
				"securitycheck.backfund.queryOrderSrcOfFail4Pay2Acct");
		tableName.put("24",
				"securitycheck.backfund.queryOrderSrcOfFail4Pay2Acct");
	}

	@Override
	protected int doVote(int busiType, Principal principal,
			DenyVoteMsg denyVoteMsg) {
		String sqlId;
		Map params = new HashMap(2);
		params.put("ORDER_ID", principal.getOrderSeqSrc());
		params.put("BUSI_TYPE", busiType);
		params.put("MEMBER_CODE", principal.getPayerMemberCode());
		if (successBusiness.contains(busiType + "")) {
			sqlId = "securitycheck.backfund.queryOrderSrcOfSuccess";
		} else {
			sqlId = "securitycheck.backfund.queryOrderSrcOfFail4Withdraw";
			if (tableName.containsKey(busiType + "")) {
				sqlId = tableName.get(busiType);
			}
		}
		try {
			OrderModel order = (OrderModel) daoService.findObjectByCriteria(
					sqlId, params);
			// 找不到原订单，不允许退款
			if (order == null) {
				denyVoteMsg.setCode(Constants.DENY_CODE_BUSI_NOORDERSRC);
				denyVoteMsg.setTips(new String[] { principal.getOrderSeq(),
						principal.getOrderSeqSrc() });
				return DENIED;
			}
			return GRANTED;
		} catch (Exception e) {
			logger.error("查询订单错误 [" + principal + "]", e);
			denyVoteMsg.setCode(Constants.DENY_CODE_SYSTEM_ERROR);
			denyVoteMsg.setTips(new String[] { principal.toString(),
					this.toString() });
			return DENIED;
		}
	}

	public void setDaoService(BaseDAO daoService) {
		this.daoService = daoService;
	}

	public void setSuccessBusiness(List<String> successBusiness) {
		this.successBusiness = successBusiness;
	}

}
