package com.pay.acc.member.dto;

public class MemberInfoDto extends MemberDto {

	// 操作员主键
	private Long operatorId;
	// 操作员登录名
	private String identity;
	// 操作员姓名
	private String name;

	private String payPassWord;
	private String merchantCode;
	private String enterpriseType;// 个人商户,企业商户
	private String zhName;// 中文名
	private String enName;// 英文名
	private String website;// 企业主网站
	private String audiStatus;// 审核状态 0.未通过1.通过2.未审核
	private String nation;// 国家
	private String region;// 地区(省)
	private String city;// 城市
	private String cityName;

	private String industry;// 所属行业
	// t_liquidateInfo
	private String bankId;// 商户结算银行
	private String bankName;// 分行名称
	private String bankAcct;// 商户结算银行账户
	private String acctName;// 商户结算账户名称
	private String regionBank;// 地区(省)
	private String cityBank;// 城市
	private String branchBankId;// 分行id号
	private String riskLeveCode;// 风险等级号码

	private String loginName;

	private String password;

	private String conformPwd;

	// 法人姓名
	private String legalName;

	// 公司法人联系电话
	private String legalLink;

	// 公司电话
	private String tel;

	// 公司地址
	private String address;

	// 开户行银行名称
	private String bigBankName;

	private String link;
	private String fax;
	private String qq;
	private String msn;
	private String province;
	private String addr;
	private String zip;
	private Integer cerType;
	private String cerCode;
	private String email;
	private String mobile;
	private String nickname;
	// 商户号注册前缀
	private String merchantCodePrefix;

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPayPassWord() {
		return payPassWord;
	}

	public void setPayPassWord(String payPassWord) {
		this.payPassWord = payPassWord;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
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

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Integer getCerType() {
		return cerType;
	}

	public void setCerType(Integer cerType) {
		this.cerType = cerType;
	}

	public String getCerCode() {
		return cerCode;
	}

	public void setCerCode(String cerCode) {
		this.cerCode = cerCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getRiskLeveCode() {
		return riskLeveCode;
	}

	public void setRiskLeveCode(String riskLeveCode) {
		this.riskLeveCode = riskLeveCode;
	}

	public String getZhName() {
		return zhName;
	}

	public void setZhName(String zhName) {
		this.zhName = zhName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getAudiStatus() {
		return audiStatus;
	}

	public void setAudiStatus(String audiStatus) {
		this.audiStatus = audiStatus;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getEnterpriseType() {
		return enterpriseType;
	}

	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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

	public String getBranchBankId() {
		return branchBankId;
	}

	public void setBranchBankId(String branchBankId) {
		this.branchBankId = branchBankId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConformPwd() {
		return conformPwd;
	}

	public void setConformPwd(String conformPwd) {
		this.conformPwd = conformPwd;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBigBankName() {
		return bigBankName;
	}

	public void setBigBankName(String bigBankName) {
		this.bigBankName = bigBankName;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getMerchantCodePrefix() {
		return merchantCodePrefix;
	}

	public void setMerchantCodePrefix(String merchantCodePrefix) {
		this.merchantCodePrefix = merchantCodePrefix;
	}

	@Override
	public String toString() {

		return super.toString() + " ; MemberInfoDto [operatorId=" + operatorId
				+ ", identity=" + identity + ", name=" + name
				+ ", payPassWord=" + payPassWord + "]";
	}
}
