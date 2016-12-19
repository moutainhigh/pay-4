package com.pay.poss.base.validator.impl;

import java.util.regex.Pattern;

public class EmailValidator extends RegexValidator {
	public EmailValidator() {
		exprePattern = Pattern.compile("\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b");
		caseSensitive = true;
		doTrim = true;
	}
}
