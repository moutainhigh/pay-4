package com.pay.fundout.withdraw.dto.enforce;

/**
 * 
 * <p>
 * 强制提现DTO
 * </p>
 * 
 * @author wucan
 * @since 2011-5-3
 * @see
 */
public class EnforceWithdrawDTO implements java.io.Serializable {

	private static final long serialVersionUID = 8048974846471671980L;

	/** 用户名 **/
	private String loginName;
	/** 账户类型 **/
	private Long memberAccType;
	/** 银行名称 **/
	private String bankNameStr;
	/** 银行编码 **/
	private String bankName;
	/** 开户行所在省份 **/
	private Short bankProvince;
	/** 开户行所在省份 **/
	private String bankProvinceName;
	/** 开户行所在城市 **/
	private Short bankCity;
	/** 开户行所在城市 **/
	private String bankCityName;
	/** 开户行名称 **/
	private String bankBranch;
	/** 开户人名称 **/
	private String accHolder;
	/** 银行账号 **/
	private String bankAcct;
	/** 提现金额 **/
	private String amount;
	/** 备注 **/
	private String remark;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Long getMemberAccType() {
		return memberAccType;
	}

	public void setMemberAccType(Long memberAccType) {
		this.memberAccType = memberAccType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Short getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(Short bankProvince) {
		this.bankProvince = bankProvince;
	}

	public Short getBankCity() {
		return bankCity;
	}

	public void setBankCity(Short bankCity) {
		this.bankCity = bankCity;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getAccHolder() {
		return accHolder;
	}

	public void setAccHolder(String accHolder) {
		this.accHolder = accHolder;
	}

	public String getBankAcct() {
		return bankAcct;
	}

	public void setBankAcct(String bankAcct) {
		this.bankAcct = bankAcct;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBankNameStr() {
		return bankNameStr;
	}

	public void setBankNameStr(String bankNameStr) {
		this.bankNameStr = bankNameStr;
	}

	public String getBankProvinceName() {
		return bankProvinceName;
	}

	public void setBankProvinceName(String bankProvinceName) {
		this.bankProvinceName = bankProvinceName;
	}

	public String getBankCityName() {
		return bankCityName;
	}

	public void setBankCityName(String bankCityName) {
		this.bankCityName = bankCityName;
	}

}
