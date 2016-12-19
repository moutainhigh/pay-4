package com.pay.gateway.validate.rule.newrule;

import com.pay.gateway.dto.ValidateResponse;
import com.pay.gateway.model.ValidateObj;

/**
 * Created by cuber.huang on 2016/7/21.
 */
public interface InterfaceRule {
    ValidateResponse validate(Object bean, ValidateObj validateObj);
}
