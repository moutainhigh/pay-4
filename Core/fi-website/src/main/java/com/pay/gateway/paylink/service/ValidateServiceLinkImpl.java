/**
 * 
 */
package com.pay.gateway.paylink.service;

import com.pay.inf.rule.RuleEngine;
import com.pay.inf.service.ValidateService;

/**
 * @author PengJiangbo
 *
 */
public class ValidateServiceLinkImpl implements ValidateService {

	public ValidateServiceLinkImpl()
    {
    }

    public void validate(Object request)
        throws Exception
    {
        ruleEngine.processRequest(request);
    }

    public void setRuleEngine(RuleEngine ruleEngine)
    {
        this.ruleEngine = ruleEngine;
    }

    private RuleEngine ruleEngine;

}
