package com.pay.poss.base.validator.impl;

import com.pay.poss.base.exception.ValidationException;
import com.pay.poss.base.validator.BaseValidator;

public class RequiredValidator extends BaseValidator {

	@Override
	protected void doInit() {
	}

	@Override
	protected String doValidate(Object paramValue) {
		if (paramValue instanceof String) {
			String compare = (String) paramValue;

			if (compare.trim().length() > 0) {
				return "true";
			} else {
				return "字段["+getFieldName() + "] 不允许为空";
			}
		} else {
			throw new ValidationException("RequiredValidator不支持的类型校验 [支持:String,实际:" + paramValue.getClass().getName() + "]");
		}
	}
}
