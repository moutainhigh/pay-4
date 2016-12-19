/**
 * 
 */
package com.pay.gateway.commons;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.util.StringUtil;
import com.pay.util.ValidatorUtil;

/**
 * @author min.lin
 * 校验实体对象参数是否符合规则,实体对象需要写入Annotation
 */
public class VaildateBean {
	public static boolean process(Object obj) throws BusinessException{
		boolean bool = false;
		String msg = null;
		Set<ConstraintViolation<Object>> set = ValidatorUtil.validator(obj);
		for (ConstraintViolation<Object> c : set) {
			msg = c.getPropertyPath().toString()
					+ " is invaildate, the value is : "
					+ c.getInvalidValue() + ". reason is : " + c.getMessage();
		}
		if(!StringUtil.isEmpty(msg)) {
			bool = false;
			throw new BusinessException(msg, ExceptionCodeEnum.REQUEST_PARAM_ERROR);
		}
		return bool;
	}
}
