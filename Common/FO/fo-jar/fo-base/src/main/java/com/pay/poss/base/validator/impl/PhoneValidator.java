package com.pay.poss.base.validator.impl;

import java.util.HashSet;
import java.util.Set;

import com.pay.poss.base.exception.ValidationException;
import com.pay.poss.base.validator.BaseValidator;

public class PhoneValidator extends BaseValidator {
	private Set allows = new HashSet();
	private StringBuffer allowPhones = new StringBuffer(" 支持手机号格式：");

	@Override
	protected void doInit() {
		for (String index : params.keySet()) {
			String prefix = params.get(index);
			allows.add(prefix);
			allowPhones.append(prefix);
			for (int i = 0; i < 11 - prefix.length(); i++) {
				allowPhones.append("X");
			}
			allowPhones.append("  ");
		}
	}

	@Override
	protected String doValidate(Object paramValue) {
		if (paramValue instanceof String) {
			String temp = ((String) paramValue).trim();
			if (temp.length() != 11) {
				return "手机号必须为11位,当前手机号为[" + temp + "]";
			}

			String compare = temp.substring(0, 3);

			if (allows.contains(compare)) {
				return "true";
			} else {
				return getErrorTip() + allowPhones.toString() + "字段[" + getFieldName() + "]当前值为" + temp;
			}
		} else {
			throw new ValidationException("PhoneValidator不支持的类型校验 [支持:String,实际:" + paramValue.getClass().getName() + "]");
		}
	}

}
