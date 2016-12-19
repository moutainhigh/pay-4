package com.pay.poss.base.validator.impl;

import com.pay.poss.base.exception.ValidationException;
import com.pay.poss.base.validator.BaseValidator;

public class StringLengthValidator extends BaseValidator {
	private boolean doTrim = true;
	private int min = 0;
	private int max = 0;

	public void setDoTrim(boolean doTrim) {
		this.doTrim = doTrim;
	}

	@Override
	protected String doValidate(Object paramValue) {
		if (paramValue instanceof String) {
			String temp = (String) paramValue;

			if (doTrim) {
				temp = temp.trim();
			}

			int length = temp.length();

			if (min <= length && length <= max) {
				return "true";
			} else {
				return getErrorTip() + " .字段[" + getFieldName() + "]当前长度" + length;
			}
		} else {
			throw new ValidationException("StringLengthValidator不支持的类型校验 [支持:String,实际:" + paramValue.getClass().getName() + "]");
		}
	}

	@Override
	protected void doInit() {
		try {
			min = Integer.parseInt(params.get("0"));
			max = Integer.parseInt(params.get("1"));
		} catch (Exception e) {
			logger.error("解析上下限出现错误,系统将设置默认上下限。 [" + params + "]", e);
		}
	}

}
