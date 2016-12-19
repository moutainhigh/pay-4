package com.pay.app.model;

import java.util.Date;

/**
 * @author lei.jiangl 
 * @version 
 * @data 2010-10-13 下午04:32:19
 */
public class IdcBankVerify implements Model {
	
	private Long id;//ID
	private Long idcVerifyBaseId;//关联ID
	private String name;//银行卡用户名
	private String bankAcctId;//银行卡号
	private String bankId;//银行代码
	private String branchBankName;//支行名称
	private Long branchBankId;//支行代码
	private Long province;//所在省
	private Long city;//所在城市
    private Date createdDate;//创建时间
    private String errorCode;
    private String errorMsg;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdcVerifyBaseId() {
		return idcVerifyBaseId;
	}

	public void setIdcVerifyBaseId(Long idcVerifyBaseId) {
		this.idcVerifyBaseId = idcVerifyBaseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBankAcctId() {
		return bankAcctId;
	}

	public void setBankAcctId(String bankAcctId) {
		this.bankAcctId = bankAcctId;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBranchBankName() {
		return branchBankName;
	}

	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}

	public Long getBranchBankId() {
		return branchBankId;
	}

	public void setBranchBankId(Long branchBankId) {
		this.branchBankId = branchBankId;
	}

	public Long getProvince() {
		return province;
	}

	public void setProvince(Long province) {
		this.province = province;
	}

	public Long getCity() {
		return city;
	}

	public void setCity(Long city) {
		this.city = city;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public void setPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		
	}
}
