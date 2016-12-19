package com.pay.acc.member.dto;

import java.io.Serializable;
import java.util.Date;

public class EnterpriseBaseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long enterpriseId;// 主键
	private Long memberCode;// 会员号，关联到t_member表主键
	private Long merchantCode;// 商户号 (企业会员号编号)
	private Integer enterpriseType;// 企业会员类型 1表示..(暂时未的确)
	private Integer audiStatus;// 企业会员的审核状态，0审核未通过，1审核通过
	private String zhName;// 中文名
	private String searchKey;// 中文名拼音首字母（查询用）
	private String enName;// 英文名
	private String website;// 企业主网站
	private String nation;// 国家
	private String region;// 地区(省)
	private String city;// 城市
	private String industryKind;// 行业门类
	private String industryType;// 行业大类
	private String industry;// 所属行业
	private String bizLicenceCode;// 企业营业执照号码
	private Date expire;// 企业执照有效期
	private String govCode;// 企业机构证件号码
	private String taxCode;// 企业税务等级证件号码
	private String riskLeveCode;// 风险等级号码
	private Date createDate;// 数据创建时间
	private Date updateDate;// 数据修改时间
	private Integer assurePercentage;// 保证金比例

	// 保证金结算周期
	private Integer assureSettlementCycle;

	private Integer identity; // '商户身份标识 0-普通商户 1-交易中心管理员 2-交易商';

	// 结算周期
	private Integer settlementCycle;
	// 结算百分比
	private Integer percentage;
	// 第二结算周期
	private Integer secondSettlementCycle;
	
	private String withdrawFee;

	private String signProcessFee;

	private String riskFee;

	private String chargebackFee;
	
	/**
	 * 手续费币种编号
	 */
	private String feeCurrencyCode;
	
	private String riskFeeCurrency = "USD";//风控处理费币种 add by davis.guo 2016-08-10
	
	public String getRiskFeeCurrency() {
		return riskFeeCurrency;
	}

	public void setRiskFeeCurrency(String riskFeeCurrency) {
		this.riskFeeCurrency = riskFeeCurrency;
	}
	
	public String getFeeCurrencyCode() {
		return feeCurrencyCode;
	}

	public void setFeeCurrencyCode(String feeCurrencyCode) {
		this.feeCurrencyCode = feeCurrencyCode;
	}

	public Long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public Long getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(Long merchantCode) {
		this.merchantCode = merchantCode;
	}

	public Integer getEnterpriseType() {
		return enterpriseType;
	}

	public void setEnterpriseType(Integer enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	public Integer getAudiStatus() {
		return audiStatus;
	}

	public void setAudiStatus(Integer audiStatus) {
		this.audiStatus = audiStatus;
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

	public String getIndustryKind() {
		return industryKind;
	}

	public void setIndustryKind(String industryKind) {
		this.industryKind = industryKind;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getBizLicenceCode() {
		return bizLicenceCode;
	}

	public void setBizLicenceCode(String bizLicenceCode) {
		this.bizLicenceCode = bizLicenceCode;
	}

	public Date getExpire() {
		return expire;
	}

	public void setExpire(Date expire) {
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

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public Integer getSettlementCycle() {
		return settlementCycle;
	}

	public void setSettlementCycle(Integer settlementCycle) {
		this.settlementCycle = settlementCycle;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public Integer getIdentity() {
		return identity;
	}

	public void setIdentity(Integer identity) {
		this.identity = identity;
	}

	public Integer getPercentage() {
		return percentage;
	}

	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}

	public Integer getSecondSettlementCycle() {
		return secondSettlementCycle;
	}

	public void setSecondSettlementCycle(Integer secondSettlementCycle) {
		this.secondSettlementCycle = secondSettlementCycle;
	}

	public Integer getAssurePercentage() {
		return assurePercentage;
	}

	public void setAssurePercentage(Integer assurePercentage) {
		this.assurePercentage = assurePercentage;
	}

	public Integer getAssureSettlementCycle() {
		return assureSettlementCycle;
	}

	public void setAssureSettlementCycle(Integer assureSettlementCycle) {
		this.assureSettlementCycle = assureSettlementCycle;
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
}