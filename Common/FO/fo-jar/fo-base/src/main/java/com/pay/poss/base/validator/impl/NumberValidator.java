package com.pay.poss.base.validator.impl;

import com.pay.poss.base.exception.ValidationException;
import com.pay.poss.base.validator.BaseValidator;

public class NumberValidator extends BaseValidator {

	@Override
	protected void doInit() {

	}

	@Override
	protected String doValidate(Object paramValue) {
		if (paramValue instanceof String) {
			String compare = ((String) paramValue).trim();

			for (int i = compare.length(); --i >= 0;) {
				if (Character.isDigit(compare.charAt(i)) == false) {
					return "必须为数字. 字段[" + getFieldName() + "]当前值" + compare;
				}
			}
			return "true";
		} else {
			throw new ValidationException("PhoneValidator不支持的类型校验 [支持:String,实际:" + paramValue.getClass().getName() + "]");
		}
	}

}
