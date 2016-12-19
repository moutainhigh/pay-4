/**
 *  <p>File: AutoFundoutConfigDto.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.fundout.autofundout.dto;

import java.util.Date;

public class AutoFundoutConfigDto {

    private Long sequenceid;
    private Long retainedAmount;
    //银行编号
    private String bankAccCode;
    private String remark;
    //有效标识  0有效 1无效
    private Integer status;
    //会员类型 1 个人会员 2企业会员
    private Integer memberType;
    private String bankName;
    private Date updateDate;
    private String bankCode;
    private String createUser;
    private Integer busiType;
    private Date createDate;
    //自动提现类型  1 定期   2 定额
    private Integer autoType;
    private Long memberCode;
    private String updateUser;
    //账户类型 10人民币
    private Integer accType;
	public Long getSequenceid() {
		return sequenceid;
	}
	public void setSequenceid(Long sequenceid) {
		this.sequenceid = sequenceid;
	}
	public Long getRetainedAmount() {
		return retainedAmount;
	}
	public void setRetainedAmount(Long retainedAmount) {
		this.retainedAmount = retainedAmount;
	}
	public String getBankAccCode() {
		return bankAccCode;
	}
	public void setBankAccCode(String bankAccCode) {
		this.bankAccCode = bankAccCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Integer getBusiType() {
		return busiType;
	}
	public void setBusiType(Integer busiType) {
		this.busiType = busiType;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getAutoType() {
		return autoType;
	}
	public void setAutoType(Integer autoType) {
		this.autoType = autoType;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Integer getAccType() {
		return accType;
	}
	public void setAccType(Integer accType) {
		this.accType = accType;
	}
    
}
