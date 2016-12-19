/** @Description 
 * @project 	fo-securitycheck
 * @file 		AbstractVoter.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-28		Rick_lv			Create 
 */
package com.pay.fundout.securitycheck.voter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.pay.fundout.securitycheck.common.Constants;
import com.pay.fundout.securitycheck.common.VoterFamilyEnum;
import com.pay.fundout.securitycheck.model.DenyVoteMsg;
import com.pay.fundout.securitycheck.model.Principal;

/**
 * 
 * @author Rick_lv
 * @since 2010-10-28
 * @see
 */
public abstract class AbstractVoter implements Voter, InitializingBean {
	protected Log logger = LogFactory.getLog(getClass());
	protected int familyCode = 100;
	protected int priority = 5;
	protected List<Integer> supportBusiness = new ArrayList<Integer>();// 支持业务
	protected StringBuffer name = new StringBuffer(getClass().getSimpleName());
	protected final DecimalFormat decimalFormat = new DecimalFormat("0.00");

	public void setSupportBusiness(List<Integer> supportBusiness) {
		this.supportBusiness = supportBusiness;
	}

	@Override
	public boolean supports(int busiType) {
		return supportBusiness.contains(busiType);
	}

	@Override
	public int vote(Principal principal, DenyVoteMsg denyVoteMsg) {
		Assert.notNull(principal, "验证主体必须不为空");

		String strBusiType = principal.getBusiType();
		try {
			int busiType = Integer.parseInt(strBusiType);
			if (supports(busiType) == false) {
				return ABSTAIN;
			} else {
				return doVote(busiType, principal, denyVoteMsg);
			}
		} catch (Exception e) {
			logger.error("投票过程出现异常 [" + principal + "]", e);
			denyVoteMsg.setCode(Constants.DENY_CODE_SYSTEM_ERROR);
			denyVoteMsg.setTips(new String[] { principal.toString() });
			return DENIED;
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (supportBusiness.isEmpty()) {
			throw new IllegalArgumentException("必须为投票器指定支持的业务类型编码");
		}

		name.append("[family=").append(VoterFamilyEnum.valueof(familyCode).getDescription()).append(", supports=(");
		for (Iterator iterator = supportBusiness.iterator(); iterator.hasNext();) {
			Integer busiType = (Integer) iterator.next();
			name.append(busiType).append(",");
		}
		name.deleteCharAt(name.length() - 1);
		name.append(")]");
	}

	protected abstract int doVote(int busiType, Principal principal, DenyVoteMsg denyVoteMsg);

	public int getFamilyCode() {
		return familyCode;
	}

	public String getDescript() {
		return name.toString();
	}
}
