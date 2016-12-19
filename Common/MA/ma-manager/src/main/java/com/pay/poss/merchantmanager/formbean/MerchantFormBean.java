package com.pay.poss.merchantmanager.formbean;

/**
 * 
 * @Description
 * @project poss-membermanager
 * @file PersonalMemberSearchFormBean.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-8-2 gungun_zhang Create
 */
public class MerchantFormBean {

	// t_member
	private String memberCode;
	 private String merchantType; // 会员类型 1个人会员，2企业会员
	private String serviceLevelCode;// 商户服务等级
	// t_enterpriseBase
	private String merchantCode;// 商户号
	private String enterpriseType;// 个人商户,企业商户企业会员类型 1表示..(暂时未的确)
	private String zhName;// 中文名
	private String enName;// 英文名
	private String website;// 企业主网站
	private String nation;// 国家
	private String region;// 地区(省)
	private String city;// 城市
	private String industry;// 所属行业
	private String bizLicenceCode;// 企业营业执照号码
	private String expire;// 企业执照有效期
	private String govCode;// 企业机构证件号码
	private String taxCode;// 企业税务等级证件号码
	private String riskLeveCode;// 风险等级号码
	// t_enterpriseContact
	private String address;// 企业会员地址
	private String fax;// 企业传真
	private String tel;// 企业联系电话
	private String zip;// 邮编
	private String email;// 邮件地址
	private String legalName;// 企业法人姓名
	private String legalLink;// 手机或者电话
	private String financeName;// 财务联系人
	private String financeLink;// 财务联系手机或者电话
	private String compayLinkerName;// 企业联系人
	private String compayLinkerTel;// 企业联系人电话
	private String techName;// 公司技术人姓名
	private String techLink;// 公司技术联系手机或者电话
	private String webName1;// 网站名称1
	private String webAddr1;// 网站地址1
	private String webName2;// 网站名称2
	private String webAddr2;// 网站地址2
	private String webName3;// 网站名称3
	private String webAddr3;// 网站地址3
	// t_enterpriseContract
	private String signName;// 签约人
	private String signDepart;// 签约人部门
	private String startDate;// 合同起始时间
	private String endDate;// 合同结束时间
	private String openFee;// 开通费用
	private String yearFee;// 年服务费
	private String factOpenFee;// 折扣开通服务费用
	private String factYearFee;// 折扣年服务费用
	private String factStartDate;// 实际开始时间
	private String factEndDate;// 时间结束时间
	private String assureFee;// 应收保证金
	private String assureDesc;// 保证金说明
	private String continueSign;// 到期后是否自动续签，0为到期终止，1到期自动续签
	// t_liquidateInfo
	private String bankName;// 商户结算银行名称
	private String bankAcct;// 商户结算银行账户
	private String acctName;// 商户结算账户名称
	private String accountMode;// 1日结，2周结，3月结
	private String regionBank;// 地区(省)
	private String cityBank;// 城市
	private String bankId;// 商户结算银行
	private String bankAddress;
	private String branchBankId;// 分行号

	private String goBackRemark;// 退回理由
	private String allowAdvanceMoney;// 是否垫资 0否，1是

	private String marketLink;

	private String signLoginId; // 签约人帐号

	private String merchantCodePrefix;

	public String getMerchantCodePrefix() {
		return merchantCodePrefix;
	}

	public void setMerchantCodePrefix(String merchantCodePrefix) {
		this.merchantCodePrefix = merchantCodePrefix;
	}

	public String getSignLoginId() {
		return signLoginId;
	}

	public void setSignLoginId(String signLoginId) {
		this.signLoginId = signLoginId;
	}

	public String getAllowAdvanceMoney() {
		return allowAdvanceMoney;
	}

	public void setAllowAdvanceMoney(String allowAdvanceMoney) {
		this.allowAdvanceMoney = allowAdvanceMoney;
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

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getGoBackRemark() {
		return goBackRemark;
	}

	public void setGoBackRemark(String goBackRemark) {
		this.goBackRemark = goBackRemark;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getServiceLevelCode() {
		return serviceLevelCode;
	}

	public void setServiceLevelCode(String serviceLevelCode) {
		this.serviceLevelCode = serviceLevelCode;
	}

	public String getCompayLinkerName() {
		return compayLinkerName;
	}

	public void setCompayLinkerName(String compayLinkerName) {
		this.compayLinkerName = compayLinkerName;
	}

	public String getContinueSign() {
		return continueSign;
	}

	public void setContinueSign(String continueSign) {
		this.continueSign = continueSign;
	}

	public String getCompayLinkerTel() {
		return compayLinkerTel;
	}

	public void setCompayLinkerTel(String compayLinkerTel) {
		this.compayLinkerTel = compayLinkerTel;
	}

	public String getEnterpriseType() {
		return enterpriseType;
	}

	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getBizLicenceCode() {
		return bizLicenceCode;
	}

	public void setBizLicenceCode(String bizLicenceCode) {
		this.bizLicenceCode = bizLicenceCode;
	}

	public String getExpire() {
		return expire;
	}

	public void setExpire(String expire) {
		this.expire = expire;
	}

	public String getGovCode() {
		return govCode;
	}

	public void setGovCode(String govCode) {
		this.govCode = govCode;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getRiskLeveCode() {
		return riskLeveCode;
	}

	public void setRiskLeveCode(String riskLeveCode) {
		this.riskLeveCode = riskLeveCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getEmail() {
		// 输入的email如果是大写则转换为小写added by tianqing_wang 2010/12/27
		if (email != null && !"".equals(email)) {
			email = email.toLowerCase();
		}
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getFinanceName() {
		return financeName;
	}

	public void setFinanceName(String financeName) {
		this.financeName = financeName;
	}

	public String getFinanceLink() {
		return financeLink;
	}

	public void setFinanceLink(String financeLink) {
		this.financeLink = financeLink;
	}

	public String getTechName() {
		return techName;
	}

	public void setTechName(String techName) {
		this.techName = techName;
	}

	public String getTechLink() {
		return techLink;
	}

	public void setTechLink(String techLink) {
		this.techLink = techLink;
	}

	public String getWebName1() {
		return webName1;
	}

	public void setWebName1(String webName1) {
		this.webName1 = webName1;
	}

	public String getWebAddr1() {
		return webAddr1;
	}

	public void setWebAddr1(String webAddr1) {
		this.webAddr1 = webAddr1;
	}

	public String getWebName2() {
		return webName2;
	}

	public void setWebName2(String webName2) {
		this.webName2 = webName2;
	}

	public String getWebAddr2() {
		return webAddr2;
	}

	public void setWebAddr2(String webAddr2) {
		this.webAddr2 = webAddr2;
	}

	public String getWebName3() {
		return webName3;
	}

	public void setWebName3(String webName3) {
		this.webName3 = webName3;
	}

	public String getWebAddr3() {
		return webAddr3;
	}

	public void setWebAddr3(String webAddr3) {
		this.webAddr3 = webAddr3;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public String getSignDepart() {
		return signDepart;
	}

	public void setSignDepart(String signDepart) {
		this.signDepart = signDepart;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getOpenFee() {
		return openFee;
	}

	public void setOpenFee(String openFee) {
		this.openFee = openFee;
	}

	public String getYearFee() {
		return yearFee;
	}

	public void setYearFee(String yearFee) {
		this.yearFee = yearFee;
	}

	public String getFactOpenFee() {
		return factOpenFee;
	}

	public void setFactOpenFee(String factOpenFee) {
		this.factOpenFee = factOpenFee;
	}

	public String getFactYearFee() {
		return factYearFee;
	}

	public void setFactYearFee(String factYearFee) {
		this.factYearFee = factYearFee;
	}

	public String getFactStartDate() {
		return factStartDate;
	}

	public void setFactStartDate(String factStartDate) {
		this.factStartDate = factStartDate;
	}

	public String getFactEndDate() {
		return factEndDate;
	}

	public void setFactEndDate(String factEndDate) {
		this.factEndDate = factEndDate;
	}

	public String getAssureFee() {
		return assureFee;
	}

	public void setAssureFee(String assureFee) {
		this.assureFee = assureFee;
	}

	public String getAssureDesc() {
		return assureDesc;
	}

	public void setAssureDesc(String assureDesc) {
		this.assureDesc = assureDesc;
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

	public String getAccountMode() {
		return accountMode;
	}

	public void setAccountMode(String accountMode) {
		this.accountMode = accountMode;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getBranchBankId() {
		return branchBankId;
	}

	public void setBranchBankId(String branchBankId) {
		this.branchBankId = branchBankId;
	}

	/**
	 * @return the marketLink
	 */
	public String getMarketLink() {
		return marketLink;
	}

	/**
	 * @param marketLink
	 *            the marketLink to set
	 */
	public void setMarketLink(String marketLink) {
		this.marketLink = marketLink;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

}
