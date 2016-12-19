/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.service.acct;

import java.util.List;
import java.util.Map;

import com.pay.base.dto.ResultDto;
import com.pay.base.model.Acct;
import com.pay.base.model.AcctInfo;

/**
 * 账户信息服务
 * @author wangzhi
 * @version $Id: AcctAttribService.java, v 0.1 2010-10-2 上午11:25:48 wangzhi Exp $
 */
public interface AcctService {
    /**
     * 创建账户信息
     *
     * @param acct
     * @return
     */
    public abstract Long createAcct(Acct acct);
    
    /**
     * 创建账户属性信息
     *
     * @param memberCode
     * @return
     */
    public abstract Long createAcctAttrib(Long memberCode);
    
    /**
     * 根据ID获取账户号
     *
     * @param acctAttrId
     * @return
     */
    public String getAccCodeById(Long acctAttrId);
    
    /**
     * 根据会员号获取账户信息
     * @author wangzhi
     * @param memberCode
     * @param acctTypeId
     * @return
     */
    public Acct getByMemberCode(long memberCode, int acctTypeId);
    
    /**
     * 根据父会员获取子交易商账户信息
     * @param fatherMemberCode
     * @param acctTypeId
     * @param sonMemberName
     * @return
     */
    public Acct getSonAcctByParentMember(long fatherMemberCode, int acctTypeId,String sonMemberName);
    
	/**
	 * 设置支付密码
	 * @param memberCode 会员号
	 * @param acctType 账户类型
	 * @param newPayPwd 支付密码
	 * @return
	 */
	public ResultDto doResetPayPwdRnTx(Long memberCode, Integer acctType, String newPayPwd,Long operatorId);
	
	/**
	 * 验证支付密码
	 * @param memberCode 会员号
	 * @param acctType 账户类型
	 * @param payPwd 支付密码
	 * @return
	 */
	public ResultDto doVerifyPayPasswordRnTx(Long memberCode, Integer acctType, String payPwd,Long operatorId);
    
    
	/**
	 * 是否设置支付密码
	 * @param memberCode
	 * @param acctType
	 * @return
	 */
	public boolean isHavePayPwd(Long memberCode,int acctType);
	
	/**
	 * 查询商户名下所有的账户
	 * @param memberCode
	 * @return
	 */
	public List<Acct> getAcctByMemberCode(Long memberCode);
	
	/**
	 * 查询商户名下所有的账户信息
	 * @param memberCode
	 * @return
	 */
	public Map<String,List<AcctInfo>> getAcctInfByMemberCode(Long memberCode);
	
	/**
	 * 查询单个账户信息
	 * @param memberCode
	 * @return
	 */
	public AcctInfo getAcctInfByAcctCode(String acctCode);
	
	/**
	 * 设置失败
	 */
	public static final int SET_FAILED = 0;//失败
	/**
	 * 验证失败
	 */
	public static final int VERIFY_FAILED = 0;//失败
	/**
	 * 设置密码成功
	 */
	public static final int SET_PASSWORD_SUCCEED = 1;
	/**
	 * 验证密码成功
	 */
	public static final int VERIFY_PASSWORD_SUCCEED = 1;
	/**
	 * 验证问题成功
	 */
	public static final int VERIFY_QUESTION_SUCCEED = 1;
	
	/**
     * 根据会员号和货币类型获取账户信息
     * @param memberCode
     * @param currency
     * @return
     */
    List<Acct> queryAcctByMemberCodeAndCurrency(Long memberCode, String currency) ;
}
