package com.pay.poss.merchantmanager.model;

import java.util.Date;

/**
 * 
 * @Description
 * @project poss-membermanager
 * @file EnterpriseBase.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-9-19 gungun_zhang Create
 */

public class EnterpriseBase {

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
	private Long city;// 城市
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

	private Integer identity; // '商户身份标识 0-普通商户 1-交易中心管理员 2-交易商';

	// 结算周期
	private Integer settlementCycle;
	// 结算百分比
	private Integer percentage;
	// 第二结算周期
	private Integer secondSettlementCycle;

	// 保证金结算周期
	private Integer assureSettlementCycle;

	private Long withdrawFee;

	private String signProcessFee;

	private String riskFee;

	private String chargebackFee;

	private String refundFee; // 退款手续费
	
	private String refundFeeCurrency;//退款手续费币种
	
	private String rateJumpStart;//汇率浮动起始点
	
	private String rateJumpEnd;//汇率浮动截止点
	
	private String withdrawFeeCurrency ;	//提现手续费币种
	
	private Long batchpayFee ;			//批量出款手续费
	
	private String batchpayFeeCurrency ;	//批量出款手续费币种
	
	//2016-05-13
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

	public String getRateJumpStart() {
		return rateJumpStart;
	}

	public void setRateJumpStart(final String rateJumpStart) {
		this.rateJumpStart = rateJumpStart;
	}

	public String getRateJumpEnd() {
		return rateJumpEnd;
	}

	public void setRateJumpEnd(final String rateJumpEnd) {
		this.rateJumpEnd = rateJumpEnd;
	}

	public String getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(final String refundFee) {
		this.refundFee = refundFee;
	}

	public String getRefundFeeCurrency() {
		return refundFeeCurrency;
	}

	public void setRefundFeeCurrency(final String refundFeeCurrency) {
		this.refundFeeCurrency = refundFeeCurrency;
	}

	public Long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(final Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(final Long memberCode) {
		this.memberCode = memberCode;
	}

	public Integer getEnterpriseType() {
		return enterpriseType;
	}

	public void setEnterpriseType(final Integer enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	public Integer getAudiStatus() {
		return audiStatus;
	}

	public void setAudiStatus(final Integer audiStatus) {
		this.audiStatus = audiStatus;
	}

	public String getZhName() {
		return zhName;
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

	public String getIndustryKind() {
		return industryKind;
	}

	public void setIndustryKind(final String industryKind) {
		this.industryKind = industryKind;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(final String industryType) {
		this.industryType = industryType;
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

	public Date getExpire() {
		return expire;
	}

	public void setExpire(final Date expire) {
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(final Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(final String searchKey) {
		this.searchKey = searchKey;
	}

	public Long getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(final Long merchantCode) {
		this.merchantCode = merchantCode;
	}

	public Integer getIdentity() {
		return identity;
	}

	public void setIdentity(final Integer identity) {
		this.identity = identity;
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

	public void setAssurePercentage(final Integer assurePercentage) {
		this.assurePercentage = assurePercentage;
	}

	public Integer getAssureSettlementCycle() {
		return assureSettlementCycle;
	}

	public void setAssureSettlementCycle(final Integer assureSettlementCycle) {
		this.assureSettlementCycle = assureSettlementCycle;
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
	
}