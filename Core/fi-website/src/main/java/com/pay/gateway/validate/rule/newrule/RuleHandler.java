package com.pay.gateway.validate.rule.newrule;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuber.huang on 2016/7/21.
 */
public class RuleHandler {
    private Map<String, InterfaceRule> handlers = new HashMap<String, InterfaceRule>();

    public Map<String, InterfaceRule> getHandlers() {
        return handlers;
    }

    public void setHandlers(Map<String, InterfaceRule> handlers) {
        this.handlers = handlers;
    }
}
