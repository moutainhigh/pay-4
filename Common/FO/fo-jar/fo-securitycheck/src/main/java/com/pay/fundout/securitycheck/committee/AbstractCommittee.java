/** @Description 
 * @project 	fo-securitycheck
 * @file 		AbstractCommittee.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-28		Rick_lv			Create 
 */
package com.pay.fundout.securitycheck.committee;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.pay.fundout.securitycheck.chain.VoterChain;
import com.pay.fundout.securitycheck.chainfactory.VoterChainFactory;
import com.pay.fundout.securitycheck.common.Constants;
import com.pay.fundout.securitycheck.exception.DeniedException;
import com.pay.fundout.securitycheck.model.Principal;
import com.pay.fundout.securitycheck.tool.ErrorTipUtil;
import com.pay.inf.service.ValidateMessageService;

/**
 * <p>
 * 委员会基础实现
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-10-28
 * @see
 */
public abstract class AbstractCommittee implements Committee {
	protected Log logger = LogFactory.getLog(getClass());
	private VoterChainFactory voterChainFactory;
	private ValidateMessageService pageMsgService;

	private boolean allowIfAllAbstainVoters = true;

	@Override
	public final void decide(Principal principal) throws DeniedException {
		Assert.notNull(principal, "验证主体必须不为空");

		VoterChain chain = voterChainFactory.fetchVoterChain(principal
				.getBusiType());
		if (chain == null) {
			throw new DeniedException(principal.getBusiType(),
					Constants.DENY_CODE_SYSTEM_ERROR, new String[] {
							"不存在业务类型对应投票器链", principal.toString() });
		}

		doDecide(principal, chain);

		// 到此说明所有投票者都弃权
		checkAllowIfAllAbstainVoters(principal);

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			ErrorTipUtil.addErrorTip(pageMsgService
					.getPageMsgByPagecodeAndScenarioId("securitycheck",
							"validateError"));
		} catch (Exception e) {
			logger.error("加载错误提示信息出现异常 [securitycheck,validateError]", e);
		}
	}

	protected final void checkAllowIfAllAbstainVoters(Principal principal) {
		if (this.isAllowIfAllAbstainVoters() == false) {
			throw new DeniedException(
					principal.getBusiType(),
					Constants.DENY_CODE_SYSTEM_ERROR,
					new String[] { "所有投票器均缺席,系统默认为此情况为投票不通过.请检查allowIfAllAbstainVoters参数" });
		}
	}

	public boolean isAllowIfAllAbstainVoters() {
		return allowIfAllAbstainVoters;
	}

	public void setAllowIfAllAbstainVoters(boolean allowIfAllAbstainVoters) {
		this.allowIfAllAbstainVoters = allowIfAllAbstainVoters;
	}

	public void setVoterChainFactory(VoterChainFactory voterChainFactory) {
		this.voterChainFactory = voterChainFactory;
	}

	public void setPageMsgService(ValidateMessageService pageMsgService) {
		this.pageMsgService = pageMsgService;
	}

	protected abstract void doDecide(Principal principal, VoterChain chain)
			throws DeniedException;
}
