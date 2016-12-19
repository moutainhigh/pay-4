package com.pay.poss.enterprisemanager.model;

import java.util.Date;

import com.pay.inf.model.BaseObject;
/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		Account.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-9-21		gungun_zhang			Create
 */
public class Account extends BaseObject{
	 
	private static final long serialVersionUID = 1L;
	private Long acctId;//主键
    private String acctCode;//账户号
    private Integer acctTypeId;//账户类型
    private Long memberCode;//会员号
    private Long balance;//可用余额
    private Integer status;//账户状态 0:无效  1：有效,2冻结(账户冻结)
    private Long frozenAmount;//冻结余额
    private Date createDate;//数据创建时间
    private Date updateDate;//数据更新时间
    private Long creditBalance;//借方发生额
    private Long  debitBalance;//贷方发生额

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }
    
    public String getAcctCode() {
		return acctCode;
	}

	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	public Long getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(Long memberCode) {
        this.memberCode = memberCode;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    

    public Integer getAcctTypeId() {
		return acctTypeId;
	}

	public void setAcctTypeId(Integer acctTypeId) {
		this.acctTypeId = acctTypeId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(Long frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

	public Long getCreditBalance() {
		return creditBalance;
	}

	public void setCreditBalance(Long creditBalance) {
		this.creditBalance = creditBalance;
	}

	public Long getDebitBalance() {
		return debitBalance;
	}

	public void setDebitBalance(Long debitBalance) {
		this.debitBalance = debitBalance;
	}
    
    
}