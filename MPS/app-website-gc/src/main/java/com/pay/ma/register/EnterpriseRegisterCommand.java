/**
 * 
 */
package com.pay.ma.register;

/**
 * @author chaoyue
 *
 */
public class EnterpriseRegisterCommand {

	private String loginName;

	private String loginPwd;

	private String loginPwdConfirm;

	// 法人姓名
	private String legalName;
	// 联系邮箱
	private String email;

	// 公司法人联系电话
	private String legalLink;

	// 公司电话
	private String tel;
	// 企业名称
	private String zhName;

	// 公司所属地区
	private String region;
	// 公司所属城市
	private String city;

	// 公司地址
	private String address;

	private String regionBank;

	private String cityBank;

	// 开户行银行名称
	private String branchBankId;
	private String bigBankName;

	private String bankId;

	private String bankAcct;

	private String acctName;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getLoginPwdConfirm() {
		return loginPwdConfirm;
	}

	public void setLoginPwdConfirm(String loginPwdConfirm) {
		this.loginPwdConfirm = loginPwdConfirm;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLegalLink() {
		return legalLink;
	}

	public void setLegalLink(String legalLink) {
		this.legalLink = legalLink;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getZhName() {
		return zhName;
	}

	public void setZhName(String zhName) {
		this.zhName = zhName;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRegionBank() {
		return regionBank;
	}

	public void setRegionBank(String regionBank) {
		this.regionBank = regionBank;
	}

	public String getCityBank() {
		return cityBank;
	}

	public void setCityBank(String cityBank) {
		this.cityBank = cityBank;
	}

	public String getBigBankName() {
		return bigBankName;
	}

	public void setBigBankName(String bigBankName) {
		this.bigBankName = bigBankName;
	}

	public String getBankAcct() {
		return bankAcct;
	}

	public void setBankAcct(String bankAcct) {
		this.bankAcct = bankAcct;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getBranchBankId() {
		return branchBankId;
	}

	public void setBranchBankId(String branchBankId) {
		this.branchBankId = branchBankId;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

}
