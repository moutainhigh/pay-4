package com.pay.app.validator;

import org.apache.commons.lang.StringUtils;

import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.base.dto.SuggestDto;
import com.pay.util.CheckUtil;
/**
 * Copyright 2009 , Inc. All rights reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * Projet Name:	 注册表单验证控制器
 * File Name:	RegisterValidator.java
 *
 * @Author:     single_zhang
 * RegisterValidator
 * Create Date: 2010-11-30
 * @Version:		1.0
 * Modification:<br><br>
 */


public class SuggestValidator {
	  public static ValidatorDto validate(Object obj){
		  ValidatorDto vd=new ValidatorDto();
		  SuggestDto s=(SuggestDto)obj;
		  
		  if(!CheckUtil.checkStringLength(s.getTitle(),100)){
	            vd.rejectValue(ErrorCodeEnum.MEMBER_SUGGEST_TITLE.getMessage());
	      }
		  else if(!CheckUtil.checkStringLength(s.getContent(),500)){
	            vd.rejectValue(ErrorCodeEnum.MEMBER_SUGGEST_CONTENT.getMessage());
	      }
		  else if(!CheckUtil.checkStringLength(s.getEmail(),36)){
	            vd.rejectValue(ErrorCodeEnum.MEMBER_SUGGEST_EMAIL.getMessage());
	      }
		  else if(StringUtils.isNotBlank(s.getMobile()) &&  !CheckUtil.checkStringLength(s.getMobile(),11)){
	        	vd.rejectValue(ErrorCodeEnum.MEMBER_SUGGEST_MOBILE.getMessage());
	      }
		  return vd;
	  }
}
