/**
 *  File: Pay2BankValidateSuccessAction.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-4      bill_peng     Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.masspaytobank.validate.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.withdraw.service.masspaytobank.validate.rule.MassPaytobankDTO;
import com.pay.inf.rule.AbstractAction;

/**
 * @author bill_peng
 *
 */
public class MassPaytobankValidateSuccessAction extends AbstractAction {
	
	protected transient Log log = LogFactory.getLog(getClass());
	/* (non-Javadoc)
	 * @see com.pay.ruleengine.AbstractAction#doExecute(java.lang.Object)
	 */
	@Override
	protected void doExecute(Object validateBean) throws Exception {
		MassPaytobankDTO dto = (MassPaytobankDTO)validateBean;
		log.info(dto.getPayerMemberCode() + " request mass pay2bank validate success!");
	}

}
