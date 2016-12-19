package com.pay.app.validator;

import org.apache.commons.lang.StringUtils;

import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.util.CheckUtil;

public class GreetingValidator {
	
	 public static ValidatorDto validate(Object obj) {
	        ValidatorDto vd=new ValidatorDto();
	        if(StringUtils.isNotBlank(obj.toString()) &&  !CheckUtil.checkStringLength(obj.toString(),50)){
	        		vd.rejectValue(ErrorCodeEnum.MEMBER_REALNAME_GREETING.getMessage());
	        }
	        return vd;
	 }
}
