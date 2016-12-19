package com.pay.poss.base.validator;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.poss.base.exception.ValidationException;

public abstract class BaseValidator implements IValidatable {
	public Log logger = LogFactory.getLog(getClass());
	public static Pattern pattern = Pattern.compile("\\$\\{[0-9]+\\}");

	// 字符串型，按字典表升序，故index在0-9是准确的
	protected Map<String, String> params = new TreeMap<String, String>();

	private String type;
	private String fieldName;
	private String errorTip;
	private String msgTemplate;

	public String getType() {
		return type;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessageTemplate() {
		return msgTemplate;
	}

	@Override
	public void setMessageTemplate(String msgTemplate) {
		this.msgTemplate = msgTemplate;
	}

	@Override
	public String getErrorTip() {
		return errorTip;
	}
	@Override
	public void setErrorTip(String errorTip) {
		this.errorTip = errorTip;
	}

	@Override
	public void addParam(String key, String value) {
		params.put(key, value);
	}

	@Override
	public String validate(Object paramValue) throws ValidationException {
		if (paramValue == null) {
			return getFieldName() + "不允许为空";
		}

		return doValidate(paramValue);
	}

	@Override
	public void checkAfterPropertySet() {
		// 预生成出错提示
		String msgTemplate = getMessageTemplate();
		if (StringUtils.isNotEmpty(msgTemplate)) {
			StringBuffer bf = new StringBuffer();
			Matcher matcher = pattern.matcher(msgTemplate);
			while (matcher.find()) {
				String matchString = matcher.group();
				String paramName = matchString.substring(2, matchString.length() - 1);// 去除${，故索引值为1
				String paramValue = params.get(paramName);
				if (StringUtils.isNotEmpty(paramValue)) {
					matcher.appendReplacement(bf, paramValue);
				}
			}
			matcher.appendTail(bf);
			errorTip = bf.toString();
		}

		// 预处理参数
		doInit();
	}

	public BaseValidator clone() {
		BaseValidator target = null;
		try {
			target = (BaseValidator) super.clone();
			for (String key : params.keySet()) {
				target.addParam(key, params.get(key));
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return target;
	}

	protected abstract void doInit();

	protected abstract String doValidate(Object paramValue);

}
