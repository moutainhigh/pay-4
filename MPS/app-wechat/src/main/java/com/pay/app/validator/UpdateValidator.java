package com.pay.app.validator;

import org.apache.commons.lang.StringUtils;

import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.base.dto.MemberCriteria;
import com.pay.base.dto.MemberInfoDto;
import com.pay.util.CheckUtil;


/**
 * Copyright 2009 , Inc. All rights reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * Projet Name:	 修改信息表单验证控制器
 * File Name:	UpdateValidator.java
 *
 * @Author:    zhangfengying
 * RegisterValidator
 * Create Date: 2010-11-8
 * @Version:		1.0
 * Modification:<br><br>
 */

public class UpdateValidator {
	  public static ValidatorDto validate(Object obj){
		  ValidatorDto vd=new ValidatorDto();
		  MemberCriteria m=(MemberCriteria)obj;
		  
		  if(StringUtils.isNotBlank(m.getCardNo()) && !CheckUtil.checkStringLength(m.getCardNo(),30)){
	            vd.rejectValue(ErrorCodeEnum.MEMBER_CER_INDEXBUNDOF.getMessage());
	      }
		  else if(StringUtils.isNotBlank(m.getAptoticPhone()) &&  !CheckUtil.checkStringLength(m.getAptoticPhone(),15)){
	            vd.rejectValue(ErrorCodeEnum.MEMBER_REALNAME_TELPHONE.getMessage());
	      }
		  else if(StringUtils.isNotBlank(m.getQq()) && !CheckUtil.checkStringLength(m.getQq(),16)){
	            vd.rejectValue(ErrorCodeEnum.MEMBER_REALNAME_QQ.getMessage());
	      }
		  else if(StringUtils.isNotBlank(m.getMsn()) && !CheckUtil.checkStringLength(m.getMsn(),32)){
	            vd.rejectValue(ErrorCodeEnum.MEMBER_REALNAME_MSN.getMessage());
	      }
		  else if(StringUtils.isNotBlank(m.getFaxes()) && !CheckUtil.checkStringLength(m.getFaxes(),26)){
	            vd.rejectValue(ErrorCodeEnum.MEMBER_REALNAME_FAX.getMessage());
	      }
		  else if(StringUtils.isNotBlank(m.getAddress()) && !CheckUtil.checkStringLength(m.getAddress(),64)){
	            vd.rejectValue(ErrorCodeEnum.MEMBER_REALNAME_ADDRESS.getMessage());
	      }
	      return vd;
	  }
	  
	  public static ValidatorDto ValidatorTempDto(Object obj){
		  ValidatorDto vd=new ValidatorDto();
		  MemberInfoDto m=(MemberInfoDto)obj;
		  
		  if(StringUtils.isNotBlank(m.getCertificateNo()) && !CheckUtil.checkStringLength(m.getCertificateNo(),30)){
	            vd.rejectValue(ErrorCodeEnum.MEMBER_CER_INDEXBUNDOF.getMessage());
	      }
		  else if(StringUtils.isNotBlank(m.getTel()) &&  !CheckUtil.checkStringLength(m.getTel(),15)){
	            vd.rejectValue(ErrorCodeEnum.MEMBER_REALNAME_TELPHONE.getMessage());
	      }
		  else if(StringUtils.isNotBlank(m.getQq()) && !CheckUtil.checkStringLength(m.getQq(),16)){
	            vd.rejectValue(ErrorCodeEnum.MEMBER_REALNAME_QQ.getMessage());
	      }
		  else if(StringUtils.isNotBlank(m.getMsn()) && !CheckUtil.checkStringLength(m.getMsn(),32)){
	            vd.rejectValue(ErrorCodeEnum.MEMBER_REALNAME_MSN.getMessage());
	      }
		  else if(StringUtils.isNotBlank(m.getFax()) && !CheckUtil.checkStringLength(m.getFax(),26)){
	            vd.rejectValue(ErrorCodeEnum.MEMBER_REALNAME_FAX.getMessage());
	      }
		  else if(StringUtils.isNotBlank(m.getAddr()) && !CheckUtil.checkStringLength(m.getAddr(),64)){
	            vd.rejectValue(ErrorCodeEnum.MEMBER_REALNAME_ADDRESS.getMessage());
	      }
	      return vd;
	  }
	  
	  public static ValidatorDto validatePayPwd(String payPwd,String payPwdConfirm){
		  ValidatorDto vd=new ValidatorDto();
		  if(StringUtils.isBlank(payPwd) || StringUtils.isBlank(payPwdConfirm)){
	          vd.rejectValue(ErrorCodeEnum.MEMBER_VERIFY_PASSWORD_EMPTY.getMessage());
	      }else if(!payPwd.equals(payPwdConfirm)){
	    	  vd.rejectValue(ErrorCodeEnum.MEMBER_VERIFY_PASSWORD_DIFFERENT.getMessage());
	      }else if(!CheckUtil.checkPayPwd(payPwd)){
	    	  vd.rejectValue(ErrorCodeEnum.MEMBER_VERIFY_PASSWORD_FORMAT.getMessage());
	      }
		  return vd;
	  }

	public static ValidatorDto validateLoginPwd(String loginPassword,
			String loginPasswordConfirm) {
		 ValidatorDto vd=new ValidatorDto();
		  
		  if(StringUtils.isBlank(loginPassword) || StringUtils.isBlank(loginPasswordConfirm)){
	          vd.rejectValue(ErrorCodeEnum.ACCT_VERIFY_PASSWORD_EMPTY.getMessage());
	      }else if(!loginPassword.equals(loginPasswordConfirm)){
	    	  vd.rejectValue(ErrorCodeEnum.ACCT_VERIFY_PASSWORD_DIFFERENT.getMessage());
	      }else if(!CheckUtil.checkLoginPwd(loginPassword)){
	    	  vd.rejectValue(ErrorCodeEnum.ACCT_VERIFY_PASSWORD_FORMAT.getMessage());
	      }
		  return vd;
	}
	  
	  
	  
}
