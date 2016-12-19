package com.pay.poss.enterprisemanager.dto;

import java.util.Date;
import java.util.List;

import com.pay.poss.merchantmanager.model.LiquidateInfo;

/**
 * 
 * @Description
 * @project poss-membermanager
 * @file MemberDto.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-9-19 gungun_zhang Create
 */
public class MerchantDto {
	// t_member
	private Long memberCode;// 会员号
	 private String merchantType; // 会员类型 1个人会员，2企业会员
	private String serviceLevelCode;// 企业服务等级(企业等级)
	private String memberStatus;//
	// t_enterpriseBase
	private Long merchantCode;
	private Integer enterpriseType;// 个人商户,企业商户
	private String zhName;// 中文名
	private String enName;// 英文名
	private String website;// 企业主网站
	private Integer audiStatus;// 审核状态 0.未通过1.通过2.未审核
	private String nation;// 国家
	private String region;// 地区(省)
	private Long city;// 城市
	private String cityName;
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
	private String signLoginId;// 签约人帐号 2014/5/12

	private String signDepart;// 签约人部门
	private String continueSign;// 到期后是否自动续签，0为到期终止，1到期自动续签
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
	// t_liquidateInfo
	private String bankName;// 分行名称
	private String bankAcct;// 商户结算银行账户
	private String acctName;// 商户结算账户名称

	private Integer settlementCycle;// 1日结，2周结，3月结
	// 结算百分比
	private Integer percentage;
	// 第二结算周期
	private Integer secondSettlementCycle;

	private Integer assurePercentage;

	private Integer assureSettlementCycle;

	private String regionBank;// 地区(省)
	private String cityBank;// 城市
	private String cityBankName;
	private String bankId;// 商户结算银行
	private String bankAddress;
	private Long liquidateId;
	private Long branchBankId;
	private int autoFundout = 0;// 自动绑定提现
	private String bigBankName;// 大银行名称
	private String marketLink; // 联系方式
	private Integer allowAdvanceMoney;// 是否垫资 0否，1是
	private  String swiftCode;
	List<LiquidateInfo> liquidates;

	// 当前操作员
	private String userId;
	private String userName;
	private String userDept;

	private String goBackRemark;// 退回理由

	private Long withdrawFee;

	private String signProcessFee;

	private String riskFee;
	
	private String refundFee;
	
    private String refundFeeCurrency;
	
	private String chargebackFee;

	private Long enterpriseId;// 主键
	private String searchKey;// 中文名拼音首字母（查询用）
	private String industryKind;// 行业门类
	private String industryType;// 行业大类
	private Date createDate;// 数据创建时间
	private Date updateDate;// 数据修改时间

	private Integer identity; // '商户身份标识 0-普通商户 1-交易中心管理员 2-交易商';
	
	private String withdrawFeeCurrency ;	//提现手续费币种
	
	private Long batchpayFee ;		//批量出款手续费
	
	private String batchpayFeeCurrency ;	//批量出款手续费币种
	//2016-05-12
	private Long refundPerFeeConf;  //=1 表示退款时要退百分比手续费
	private Long refundFixedFeeConf;//=1 表示退款时要要退固定手续费
	
	private String chargeBackFeeCurCode; //拒付罚款币种add delin 2016年7月18日15:58:39
	
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

	public Long getRefundPerFeeConf() {
		return refundPerFeeConf;
	}

	public void setRefundPerFeeConf(Long refundPerFeeConf) {
		this.refundPerFeeConf = refundPerFeeConf;
	}

	public Long getRefundFixedFeeConf() {
		return refundFixedFeeConf;
	}

	public void setRefundFixedFeeConf(Long refundFixedFeeConf) {
		this.refundFixedFeeConf = refundFixedFeeConf;
	}

	public String getRefundFee() {
		return refundFee;
	}

	public String getSwiftCode() {
		return swiftCode;
	}

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	public void setRefundFee(String refundFee) {
		this.refundFee = refundFee;
	}

	public String getRefundFeeCurrency() {
		return refundFeeCurrency;
	}

	public void setRefundFeeCurrency(final String refundFeeCurrency) {
		this.refundFeeCurrency = refundFeeCurrency;
	}

	public String getSignLoginId() {
		return signLoginId;
	}

	public void setSignLoginId(final String signLoginId) {
		this.signLoginId = signLoginId;
	}

	public Integer getAllowAdvanceMoney() {
		return allowAdvanceMoney;
	}

	public void setAllowAdvanceMoney(final Integer allowAdvanceMoney) {
		this.allowAdvanceMoney = allowAdvanceMoney;
	}

	public Long getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(final Long merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getGoBackRemark() {
		return goBackRemark;
	}

	public void setGoBackRemark(final String goBackRemark) {
		this.goBackRemark = goBackRemark;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(final Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getServiceLevelCode() {
		return serviceLevelCode;
	}

	public void setServiceLevelCode(final String serviceLevelCode) {
		this.serviceLevelCode = serviceLevelCode;
	}

	public Integer getEnterpriseType() {
		return enterpriseType;
	}

	public void setEnterpriseType(final Integer enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	public String getZhName() {
		return zhName;
	}

	public String getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(final String memberStatus) {
		this.memberStatus = memberStatus;
	}

	public String getContinueSign() {
		return continueSign;
	}

	public Integer getAudiStatus() {
		return audiStatus;
	}

	public void setAudiStatus(final Integer audiStatus) {
		this.audiStatus = audiStatus;
	}

	public void setContinueSign(final String continueSign) {
		this.continueSign = continueSign;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(final String cityName) {
		this.cityName = cityName;
	}

	public void setZhName(final String zhName) {
		this.zhName = zhName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(final String enName) {
		this.enName = enName;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(final String website) {
		this.website = website;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(final String nation) {
		this.nation = nation;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(final String region) {
		this.region = region;
	}

	public Long getCity() {
		return city;
	}

	public void setCity(final Long city) {
		this.city = city;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(final String industry) {
		this.industry = industry;
	}

	public String getBizLicenceCode() {
		return bizLicenceCode;
	}

	public void setBizLicenceCode(final String bizLicenceCode) {
		this.bizLicenceCode = bizLicenceCode;
	}

	public String getExpire() {
		return expire;
	}

	public void setExpire(final String expire) {
		this.expire = expire;
	}

	public String getGovCode() {
		return govCode;
	}

	public void setGovCode(final String govCode) {
		this.govCode = govCode;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(final String taxCode) {
		this.taxCode = taxCode;
	}

	public String getRiskLeveCode() {
		return riskLeveCode;
	}

	public void setRiskLeveCode(final String riskLeveCode) {
		this.riskLeveCode = riskLeveCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(final String fax) {
		this.fax = fax;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(final String tel) {
		this.tel = tel;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(final String zip) {
		this.zip = zip;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(final String legalName) {
		this.legalName = legalName;
	}

	public String getLegalLink() {
		return legalLink;
	}

	public void setLegalLink(final String legalLink) {
		this.legalLink = legalLink;
	}

	public String getFinanceName() {
		return financeName;
	}

	public void setFinanceName(final String financeName) {
		this.financeName = financeName;
	}

	public String getFinanceLink() {
		return financeLink;
	}

	public void setFinanceLink(final String financeLink) {
		this.financeLink = financeLink;
	}

	public String getCompayLinkerName() {
		return compayLinkerName;
	}

	public void setCompayLinkerName(final String compayLinkerName) {
		this.compayLinkerName = compayLinkerName;
	}

	public String getCompayLinkerTel() {
		return compayLinkerTel;
	}

	public void setCompayLinkerTel(final String compayLinkerTel) {
		this.compayLinkerTel = compayLinkerTel;
	}

	public String getTechName() {
		return techName;
	}

	public void setTechName(final String techName) {
		this.techName = techName;
	}

	public String getTechLink() {
		return techLink;
	}

	public void setTechLink(final String techLink) {
		this.techLink = techLink;
	}

	public String getWebName1() {
		return webName1;
	}

	public void setWebName1(final String webName1) {
		this.webName1 = webName1;
	}

	public String getWebAddr1() {
		return webAddr1;
	}

	public void setWebAddr1(final String webAddr1) {
		this.webAddr1 = webAddr1;
	}

	public String getWebName2() {
		return webName2;
	}

	public void setWebName2(final String webName2) {
		this.webName2 = webName2;
	}

	public String getWebAddr2() {
		return webAddr2;
	}

	public void setWebAddr2(final String webAddr2) {
		this.webAddr2 = webAddr2;
	}

	public String getWebName3() {
		return webName3;
	}

	public void setWebName3(final String webName3) {
		this.webName3 = webName3;
	}

	public String getWebAddr3() {
		return webAddr3;
	}

	public void setWebAddr3(final String webAddr3) {
		this.webAddr3 = webAddr3;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(final String signName) {
		this.signName = signName;
	}

	public String getSignDepart() {
		return signDepart;
	}

	public void setSignDepart(final String signDepart) {
		this.signDepart = signDepart;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(final String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(final String endDate) {
		this.endDate = endDate;
	}

	public String getOpenFee() {
		return openFee;
	}

	public void setOpenFee(final String openFee) {
		this.openFee = openFee;
	}

	public String getYearFee() {
		return yearFee;
	}

	public void setYearFee(final String yearFee) {
		this.yearFee = yearFee;
	}

	public String getFactOpenFee() {
		return factOpenFee;
	}

	public void setFactOpenFee(final String factOpenFee) {
		this.factOpenFee = factOpenFee;
	}

	public String getFactYearFee() {
		return factYearFee;
	}

	public void setFactYearFee(final String factYearFee) {
		this.factYearFee = factYearFee;
	}

	public String getFactStartDate() {
		return factStartDate;
	}

	public void setFactStartDate(final String factStartDate) {
		this.factStartDate = factStartDate;
	}

	public String getFactEndDate() {
		return factEndDate;
	}

	public void setFactEndDate(final String factEndDate) {
		this.factEndDate = factEndDate;
	}

	public String getAssureFee() {
		return assureFee;
	}

	public void setAssureFee(final String assureFee) {
		this.assureFee = assureFee;
	}

	public String getAssureDesc() {
		return assureDesc;
	}

	public void setAssureDesc(final String assureDesc) {
		this.assureDesc = assureDesc;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getUserDept() {
		return userDept;
	}

	public void setUserDept(final String userDept) {
		this.userDept = userDept;
	}

	public List<LiquidateInfo> getLiquidates() {
		return liquidates;
	}

	public void setLiquidates(final List<LiquidateInfo> liquidates) {
		this.liquidates = liquidates;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(final String bankName) {
		this.bankName = bankName;
	}

	public String getBankAcct() {
		return bankAcct;
	}

	public void setBankAcct(final String bankAcct) {
		this.bankAcct = bankAcct;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(final String acctName) {
		this.acctName = acctName;
	}

	public String getRegionBank() {
		return regionBank;
	}

	public void setRegionBank(final String regionBank) {
		this.regionBank = regionBank;
	}

	public String getCityBank() {
		return cityBank;
	}

	public void setCityBank(final String cityBank) {
		this.cityBank = cityBank;
	}

	public String getCityBankName() {
		return cityBankName;
	}

	public void setCityBankName(final String cityBankName) {
		this.cityBankName = cityBankName;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(final String bankId) {
		this.bankId = bankId;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(final String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public Long getLiquidateId() {
		return liquidateId;
	}

	public void setLiquidateId(final Long liquidateId) {
		this.liquidateId = liquidateId;
	}

	public Long getBranchBankId() {
		return branchBankId;
	}

	public void setBranchBankId(final Long branchBankId) {
		this.branchBankId = branchBankId;
	}

	public int getAutoFundout() {
		return autoFundout;
	}

	public void setAutoFundout(final int autoFundout) {
		this.autoFundout = autoFundout;
	}

	public String getBigBankName() {
		return bigBankName;
	}

	public void setBigBankName(final String bigBankName) {
		this.bigBankName = bigBankName;
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
	public void setMarketLink(final String marketLink) {
		this.marketLink = marketLink;
	}

	public Integer getSettlementCycle() {
		return settlementCycle;
	}

	public void setSettlementCycle(final Integer settlementCycle) {
		this.settlementCycle = settlementCycle;
	}

	public Integer getPercentage() {
		return percentage;
	}

	public void setPercentage(final Integer percentage) {
		this.percentage = percentage;
	}

	public Integer getSecondSettlementCycle() {
		return secondSettlementCycle;
	}

	public void setSecondSettlementCycle(final Integer secondSettlementCycle) {
		this.secondSettlementCycle = secondSettlementCycle;
	}

	public Integer getAssurePercentage() {
		return assurePercentage;
	}

	public Integer getAssureSettlementCycle() {
		return assureSettlementCycle;
	}

	public void setAssureSettlementCycle(final Integer assureSettlementCycle) {
		this.assureSettlementCycle = assureSettlementCycle;
	}

	public void setAssurePercentage(final Integer assurePercentage) {
		this.assurePercentage = assurePercentage;
	}

	/**
	 * @return the withdrawFee
	 */
	public Long getWithdrawFee() {
		return withdrawFee;
	}

	/**
	 * @param withdrawFee the withdrawFee to set
	 */
	public void setWithdrawFee(final Long withdrawFee) {
		this.withdrawFee = withdrawFee;
	}

	public String getSignProcessFee() {
		return signProcessFee;
	}

	public void setSignProcessFee(final String signProcessFee) {
		this.signProcessFee = signProcessFee;
	}

	public String getRiskFee() {
		return riskFee;
	}

	public void setRiskFee(final String riskFee) {
		this.riskFee = riskFee;
	}

	public String getChargebackFee() {
		return chargebackFee;
	}

	public void setChargebackFee(final String chargebackFee) {
		this.chargebackFee = chargebackFee;
	}

	/**
	 * @return the withdrawFeeCurrency
	 */
	public String getWithdrawFeeCurrency() {
		return withdrawFeeCurrency;
	}

	/**
	 * @param withdrawFeeCurrency the withdrawFeeCurrency to set
	 */
	public void setWithdrawFeeCurrency(final String withdrawFeeCurrency) {
		this.withdrawFeeCurrency = withdrawFeeCurrency;
	}

	/**
	 * @return the batchpayFee
	 */
	public Long getBatchpayFee() {
		return batchpayFee;
	}

	/**
	 * @param batchpayFee the batchpayFee to set
	 */
	public void setBatchpayFee(final Long batchpayFee) {
		this.batchpayFee = batchpayFee;
	}

	/**
	 * @return the batchpayFeeCurrency
	 */
	public String getBatchpayFeeCurrency() {
		return batchpayFeeCurrency;
	}

	/**
	 * @param batchpayFeeCurrency the batchpayFeeCurrency to set
	 */
	public void setBatchpayFeeCurrency(final String batchpayFeeCurrency) {
		this.batchpayFeeCurrency = batchpayFeeCurrency;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

}
