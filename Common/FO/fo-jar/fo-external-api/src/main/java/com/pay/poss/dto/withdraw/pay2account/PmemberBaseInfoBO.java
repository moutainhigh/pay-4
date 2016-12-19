/**
 *  File: MemberBaseInfoBO.java
 *  Description:withdrawOrder model
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-9      jack.liu_liu      Changes
 *  
 *
 */
package com.pay.poss.dto.withdraw.pay2account;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author jack.liu_liu
 */
public class PmemberBaseInfoBO  implements Serializable{

	 public PmemberBaseInfoBO()
	    {
	    }

	    public Long getMemberCode()
	    {
	        return memberCode;
	    }

	    public void setMemberCode(Long memberCode)
	    {
	        this.memberCode = memberCode;
	    }

	    public Integer getServiceLevelCode()
	    {
	        return serviceLevelCode;
	    }

	    public void setServiceLevelCode(Integer serviceLevelCode)
	    {
	        this.serviceLevelCode = serviceLevelCode;
	    }

	    public Integer getMemberType()
	    {
	        return memberType;
	    }

	    public void setMemberType(Integer memberType)
	    {
	        this.memberType = memberType;
	    }

	    public String getName()
	    {
	        return name;
	    }

	    public void setName(String name)
	    {
	        this.name = name;
	    }

	    public String getGreeting()
	    {
	        return greeting;
	    }

	    public void setGreeting(String greeting)
	    {
	        this.greeting = greeting;
	    }

	    public Integer getStatus()
	    {
	        return status;
	    }

	    public void setStatus(Integer status)
	    {
	        this.status = status;
	    }

	    public String getSecurityQuestion()
	    {
	        return securityQuestion;
	    }

	    public void setSecurityQuestion(String securityQuestion)
	    {
	        this.securityQuestion = securityQuestion;
	    }

	    public String getSecurityAnswer()
	    {
	        return securityAnswer;
	    }

	    public void setSecurityAnswer(String securityAnswer)
	    {
	        this.securityAnswer = securityAnswer;
	    }

	    public Date getCreationDate()
	    {
	        return creationDate;
	    }

	    public void setCreationDate(Date creationDate)
	    {
	        this.creationDate = creationDate;
	    }

	    public Date getUpdateDate()
	    {
	        return updateDate;
	    }

	    public void setUpdateDate(Date updateDate)
	    {
	        this.updateDate = updateDate;
	    }

	    public String getParnterUserid()
	    {
	        return parnterUserid;
	    }

	    public void setParnterUserid(String parnterUserid)
	    {
	        this.parnterUserid = parnterUserid;
	    }

	    public String getRegType()
	    {
	        return regType;
	    }

	    public void setRegType(String regType)
	    {
	        this.regType = regType;
	    }

	    public String getLoginName()
	    {
	        return loginName;
	    }

	    public void setLoginName(String loginName)
	    {
	        this.loginName = loginName;
	    }

	    private static final long serialVersionUID = 1L;
	    private Long memberCode;
	    private Integer serviceLevelCode;
	    private Integer memberType;
	    private String name;
	    private String greeting;
	    private Integer status;
	    private String securityQuestion;
	    private String securityAnswer;
	    private Date creationDate;
	    private Date updateDate;
	    private String parnterUserid;
	    private String regType;
	    private String loginName;
	
	

}

