/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.dto;

/**
 * 企业会员激活补全信息对像
 * @author zhi.wang
 * @version $Id: EnterpriseInfo.java, v 0.1 2010-11-16 下午02:41:29 zhi.wang Exp $
 */
public class EnterpriseInfo {
    /** 会员号 */
    private long memberCode;
    /** 激活码 */
    private String checkCode;
    /** 登录名 */
    private String loginName;
    /** 操作员旧的登录名 */
    private String oldIdentity;
    /** 操作员登录名 */
    private String identity;
    /** 登录密码 */
    private String password;
    /** 支付密码 */
    private String payPassword;
    /** 安全问题 */
    private String securityQuestion;
    /** 安全问题答案 */
    private String securityAnswer;
    /** 问候语 */
    private String greeting;
    
    
    public long getMemberCode() {
        return memberCode;
    }
    public void setMemberCode(long memberCode) {
        this.memberCode = memberCode;
    }
    public String getCheckCode() {
        return checkCode;
    }
    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }
    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public String getOldIdentity() {
        return oldIdentity;
    }
    public void setOldIdentity(String oldIdentity) {
        this.oldIdentity = oldIdentity;
    }
    public String getIdentity() {
        return identity;
    }
    public void setIdentity(String identity) {
        this.identity = identity;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPayPassword() {
        return payPassword;
    }
    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }
    public String getSecurityQuestion() {
        return securityQuestion;
    }
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }
    public String getSecurityAnswer() {
        return securityAnswer;
    }
    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }
    public String getGreeting() {
        return greeting;
    }
    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
    
    
}
