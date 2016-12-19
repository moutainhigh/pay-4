package com.pay.app.validator;



import org.apache.commons.lang.StringUtils;

import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.base.dto.MemberInfoDto;
import com.pay.util.CheckUtil;

/**
 * Copyright 2009 , Inc. All rights reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * Projet Name:	 注册表单验证控制器
 * File Name:	RegisterValidator.java
 *
 * @Author:     zengjin
 * RegisterValidator
 * Create Date: 2010-11-6
 * @Version:		1.0
 * Modification:<br><br>
 */
public class RegisterValidator {
    
    public static ValidatorDto validate(Object obj)
    {
        ValidatorDto vd=new ValidatorDto();
        MemberInfoDto m=(MemberInfoDto)obj;
        if(!CheckUtil.checkStringLength(m.getRealName(),16)){
            vd.rejectValue(ErrorCodeEnum.MEMBER_REALNAME_INDEXBUNDOF.getMessage());
        }
        else if(!CheckUtil.checkStringLength(m.getLoginName(),60)){
            vd.rejectValue(ErrorCodeEnum.MEMBER_LOGINNAME_INDEXBUNDOF.getMessage());
        }
        else if(StringUtils.isNotBlank(m.getCertificateNo()) && !CheckUtil.checkStringLength(m.getCertificateNo(),30)){
            vd.rejectValue(ErrorCodeEnum.MEMBER_CER_INDEXBUNDOF.getMessage());
        }
        else if(!CheckUtil.checkStringLength(m.getPassword(),20)){
            vd.rejectValue(ErrorCodeEnum.MEMBER_LOGINNAME_PAYPASSWORD.getMessage());
        }
        else if(!CheckUtil.checkLoginPwd(m.getPassword())){
        	vd.rejectValue(ErrorCodeEnum.MEMBER_LOGINNAME_PAYPASSWORD.getMessage());
        }
        else if(!CheckUtil.checkStringLength(m.getSecurityAnswer(),40)){
        	vd.rejectValue(ErrorCodeEnum.MEMBER_REALNAME_SECURITYANSWER.getMessage());
        }
        else if(StringUtils.isNotBlank(m.getGreeting()) &&  !CheckUtil.checkStringLength(m.getGreeting(),50)){
        	vd.rejectValue(ErrorCodeEnum.MEMBER_REALNAME_GREETING.getMessage());
        }
        else if(StringUtils.isNotBlank(m.getTel()) && !CheckUtil.checkStringLength(m.getTel(),15)){
        	vd.rejectValue(ErrorCodeEnum.MEMBER_REALNAME_TELPHONE.getMessage());
        }
        else if(StringUtils.isNotBlank(m.getAddr()) &&! CheckUtil.checkStringLength(m.getAddr(),64)){
        	vd.rejectValue(ErrorCodeEnum.MEMBER_REALNAME_ADDRESS.getMessage());
        }
        else if(StringUtils.isNotBlank(m.getMsn()) && !CheckUtil.checkStringLength(m.getMsn(),26)){
        	vd.rejectValue(ErrorCodeEnum.MEMBER_REALNAME_MSN.getMessage());
        }
        else if(StringUtils.isNotBlank(m.getQq()) && !CheckUtil.checkStringLength(m.getQq(),16)){
        	vd.rejectValue(ErrorCodeEnum.MEMBER_REALNAME_QQ.getMessage());
        }
        else if(StringUtils.isNotBlank(m.getFax()) && !CheckUtil.checkStringLength(m.getFax(),15)){
        	vd.rejectValue(ErrorCodeEnum.MEMBER_REALNAME_FAX.getMessage());
        }
        return vd;
    }
    
 
    
   
    
}
