/** @Description 
 * @project 	fo-securitycheck
 * @file 		Voter.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-28		Rick_lv			Create 
 */
package com.pay.fundout.securitycheck.voter;

import com.pay.fundout.securitycheck.model.DenyVoteMsg;
import com.pay.fundout.securitycheck.model.Principal;

/**
 * <p>
 * 投票器
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-10-28
 * @see
 */
public interface Voter {
	public final int GRANTED = 1;
	public final int ABSTAIN = 0;
	public final int DENIED = -1;

	public int vote(Principal principal, DenyVoteMsg denyVoteMsg);

	public boolean supports(int busiType);

	public String getDescript();

}
