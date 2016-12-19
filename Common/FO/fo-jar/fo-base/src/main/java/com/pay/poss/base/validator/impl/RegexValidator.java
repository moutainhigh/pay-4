package com.pay.poss.base.validator.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pay.poss.base.exception.ValidationException;
import com.pay.poss.base.validator.BaseValidator;

public class RegexValidator extends BaseValidator {
	protected Pattern exprePattern;
	protected boolean caseSensitive;
	protected boolean doTrim;

	@Override
	protected String doValidate(Object paramValue) {
		if (paramValue instanceof String) {
			String compare = (String) paramValue;

			if (doTrim) {
				compare = compare.trim();
			}

			Matcher matcher = exprePattern.matcher(compare);

			if (matcher.matches()) {
				return "true";
			} else {
				return getErrorTip() + " 字段[" + getFieldName() + "]值为" + compare;
			}
		} else {
			throw new ValidationException("RegexValidator不支持的类型校验 [支持:String,实际:" + paramValue.getClass().getName() + "]");
		}
	}

	@Override
	protected void doInit() {
		if (caseSensitive) {
			exprePattern = Pattern.compile(params.get("0"));
		} else {
			exprePattern = Pattern.compile(params.get("0"), Pattern.CASE_INSENSITIVE);
		}
	}

}
