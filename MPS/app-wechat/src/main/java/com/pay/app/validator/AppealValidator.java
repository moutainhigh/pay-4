package com.pay.app.validator;

import org.apache.commons.lang.StringUtils;

import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.base.dto.AppealDto;
import com.pay.util.CheckUtil;

public class AppealValidator {
	  public static ValidatorDto validate(Object obj){
		  ValidatorDto vd=new ValidatorDto();
		  AppealDto a = (AppealDto)obj; 
		  if(!CheckUtil.checkStringLength(a.getCustomerName(),32)){
			  vd.rejectValue(ErrorCodeEnum.MEMBER_APPEAL_CUSTOMERNAME.getMessage());
		  }
		  else if(!CheckUtil.checkStringLength(a.getAppealBody(),1312)){
			  vd.rejectValue(ErrorCodeEnum.MEMBER_APPEAL_BODY.getMessage());
		  }
		  else if(StringUtils.isNotBlank(a.getLinkEmail()) && !CheckUtil.checkStringLength(a.getLinkEmail(),36)){
			  vd.rejectValue(ErrorCodeEnum.MEMBER_SUGGEST_EMAIL.getMessage());
		  }
		  else if(!CheckUtil.checkStringLength(a.getLinkPhone(),11)){
			  vd.rejectValue(ErrorCodeEnum.MEMBER_SUGGEST_MOBILE.getMessage());
		  }
		  return vd;
	  }
}
