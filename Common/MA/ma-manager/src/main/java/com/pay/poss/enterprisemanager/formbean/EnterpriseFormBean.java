package com.pay.poss.enterprisemanager.formbean;

import java.util.Date;
import java.util.List;

import com.pay.poss.merchantmanager.model.LiquidateInfo;

/**
 * 
 * @Description
 * @project ma-manager
 * @file EnterpriseFormBean.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-12-21 gungun_zhang Create
 */
public class EnterpriseFormBean {

	// t_member
	private Long memberCode;
	// private String merchantType; // 会员类型 1个人会员，2企业会员
	private String serviceLevelCode;// 商户服务等级
	// t_enterpriseBase
	private Long merchantCode;// 商户号
	private Integer enterpriseType;// 个人商户,企业商户企业会员类型 1表示..(暂时未的确)
	private String zhName;// 中文名
	private String enName;// 英文名
	private String website;// 企业主网站
	private String nation;// 国家
	private String region;// 地区(省)
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

	List<LiquidateInfo> liquidates;
	private String goBackRemark;// 退回理由
	private String marketLink;
	private Integer allowAdvanceMoney;// 是否垫资 0否，1是

	private String signLoginId; // 签约人帐号 2014/5/12

	private String withdrawFee;

	private String signProcessFee;

	private String riskFee;

	private String chargebackFee;

	private Long liquidateId;// 主键
	private String bankName;// 商户结算银行名称
	private String bankAcct;// 商户结算银行账户
	private String acctName;// 商户结算账户名称
	private Date createDate;// 数据创建时间
	private Date updateDate;// 数据更新时间
	// private Integer accountMode;// 1日结，2周结，3月结
	private Long province;// 开户行省份
	private Long city;// 开户行城市
	private String bankId;// 大银行id号
	private String bankAddress; // 开户行地址
	private Long branchBankId;// 分行ID号
	/** 银行的名称，非开户行名称 ，如中国银行，中国工商银行 **/
	private String bigBankName;

	private String refundFee; // 退款手续费
	private  String swiftCode;
	
	private String refundFeeCurrency; //退款手续费币种
	
    private String chargeBackFeeCurCode; //拒付罚款币种 add delin 2016年7月18日16:50:24
    private String riskFeeCurrency = "USD";//风控处理费币种 add by davis.guo 2016-08-10
	
	public String getRiskFeeCurrency() {
		return riskFeeCurrency;
	}

	public void setRiskFeeCurrency(String riskFeeCurrency) {
		this.riskFeeCurrency = riskFeeCurrency;
	}
	
	public String getChargeBackFeeCurCode() {
		return chargeBackFeeCurCode;
	}

	public void setChargeBackFeeCurCode(String chargeBackFeeCurCode) {
		this.chargeBackFeeCurCode = chargeBackFeeCurCode;
	}

	public String getSwiftCode() {
		return swiftCode;
	}

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	public String getRefundFeeCurrency() {
		return refundFeeCurrency;
	}

	public void setRefundFeeCurrency(String refundFeeCurrency) {
		this.refundFeeCurrency = refundFeeCurrency;
	}

	public String getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(String refundFee) {
		this.refundFee = refundFee;
	}

	public String getSignLoginId() {
		return signLoginId;
	}

	public void setSignLoginId(String signLoginId) {
		this.signLoginId = signLoginId;
	}

	public Integer getAllowAdvanceMoney() {
		return allowAdvanceMoney;
	}

	public void setAllowAdvanceMoney(Integer allowAdvanceMoney) {
		this.allowAdvanceMoney = allowAdvanceMoney;
	}

	public String getGoBackRemark() {
		return goBackRemark;
	}

	public void setGoBackRemark(String goBackRemark) {
		this.goBackRemark = goBackRemark;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
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

	public Integer getEnterpriseType() {
		return enterpriseType;
	}

	public void setEnterpriseType(Integer enterpriseType) {
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

	public Long getCity() {
		return city;
	}

	public void setCity(Long city) {
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

	public Long getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(Long merchantCode) {
		this.merchantCode = merchantCode;
	}

	public List<LiquidateInfo> getLiquidates() {
		return liquidates;
	}

	public void setLiquidates(List<LiquidateInfo> liquidates) {
		this.liquidates = liquidates;
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

	public String getWithdrawFee() {
		return withdrawFee;
	}

	public void setWithdrawFee(String withdrawFee) {
		this.withdrawFee = withdrawFee;
	}

	public String getSignProcessFee() {
		return signProcessFee;
	}

	public void setSignProcessFee(String signProcessFee) {
		this.signProcessFee = signProcessFee;
	}

	public String getRiskFee() {
		return riskFee;
	}

	public void setRiskFee(String riskFee) {
		this.riskFee = riskFee;
	}

	public String getChargebackFee() {
		return chargebackFee;
	}

	public void setChargebackFee(String chargebackFee) {
		this.chargebackFee = chargebackFee;
	}

	public Long getLiquidateId() {
		return liquidateId;
	}

	public void setLiquidateId(Long liquidateId) {
		this.liquidateId = liquidateId;
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

	public Long getProvince() {
		return province;
	}

	public void setProvince(Long province) {
		this.province = province;
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

	public Long getBranchBankId() {
		return branchBankId;
	}

	public void setBranchBankId(Long branchBankId) {
		this.branchBankId = branchBankId;
	}

	public String getBigBankName() {
		return bigBankName;
	}

	public void setBigBankName(String bigBankName) {
		this.bigBankName = bigBankName;
	}

}
