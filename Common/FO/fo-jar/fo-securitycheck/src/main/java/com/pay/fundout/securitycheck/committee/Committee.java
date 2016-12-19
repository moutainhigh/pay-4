/** @Description 
 * @project 	fo-securitycheck
 * @file 		Committee.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-28		Rick_lv			Create 
 */
package com.pay.fundout.securitycheck.committee;

import org.springframework.beans.factory.InitializingBean;

import com.pay.fundout.securitycheck.exception.DeniedException;
import com.pay.fundout.securitycheck.model.Principal;

/**
 * <p>
 * 投票委员会
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-10-28
 * @see
 */
public interface Committee extends InitializingBean {
	public void decide(Principal principal) throws DeniedException;
}
