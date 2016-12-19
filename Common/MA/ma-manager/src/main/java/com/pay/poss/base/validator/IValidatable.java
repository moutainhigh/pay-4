package com.pay.poss.base.validator;

import com.pay.poss.base.exception.ValidationException;

public interface IValidatable extends Cloneable{
	/**
	 * 参数校验
	 * 
	 * @param paramValue
	 *            代校验参数值
	 * @return 如果校验通过返回true,否则返回false
	 * @throws ValidationException
	 *             校验异常
	 */
	public String validate(Object paramValue) throws ValidationException;

	public void checkAfterPropertySet();

	public void addParam(String key, String value);
	public String getMessageTemplate();
	public void setMessageTemplate(String msgTemplate);
	public String getErrorTip();
	public void setErrorTip(String errorTip);
	/**
	 * 获取validator的注册类型名称
	 * 
	 * @return validator注册类型名
	 */
	public String getType();
	public void setType(String type);
	/**
	 * 获取校验字段名称
	 * 
	 * @return 校验字段名称
	 */
	public String getFieldName();

	public void setFieldName(String fieldName);

}
