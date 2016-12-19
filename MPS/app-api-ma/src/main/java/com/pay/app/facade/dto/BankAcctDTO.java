/**
 * 
 */
package com.pay.app.facade.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Sunny.Ying
 * 银行卡绑定 对象
 */
public class BankAcctDTO implements Serializable{
	
	private Long id; 
	private String bankId; //开户银行 Id
	private Long memberCode; //会员代码 Fk
	private Integer isPrimaryBankAcct;//是否是主卡,默认0 为主卡
	private String branchBankName;//开户行支行名称
	private Date creationDate;//创建日期
	private String name;//申请人姓名
	private Integer status;// 绑定银行卡状态
	private String bankAcctId;//银行账户
	private String province;//银行所在省
	private String city;//银行所在市
	
	public Long getId() {
		return id;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public Integer getIsPrimaryBankAcct() {
		return isPrimaryBankAcct;
	}
	public void setIsPrimaryBankAcct(Integer isPrimaryBankAcct) {
		this.isPrimaryBankAcct = isPrimaryBankAcct;
	}
	public String getBranchBankName() {
		return branchBankName;
	}
	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getBankAcctId() {
		return bankAcctId;
	}
	public void setBankAcctId(String bankAcctId) {
		this.bankAcctId = bankAcctId;
	}
	

}
