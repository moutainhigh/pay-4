/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.model;

import java.util.Date;

/**
 * 企业会员基本信息
 * 
 * @author wangzhi
 * @version $Id: EnterpriseBase.java, v 0.1 2010-9-30 下午12:41:18 wangzhi Exp $
 */
public class EnterpriseBase {

	/** 主键 */
	private long enterpriseId;
	/** 会员号，关联到t_member表主键 */
	private long memberCode;
	/** 商户号 (企业会员号编号) */
	private long merchantCode;
	/** 企业会员类型 */
	private Integer enterpriseType;
	/** 企业会员的审核状态，0审核未通过，1审核通过 */
	private Integer audiStatus;
	/** 中文名 */
	private String zhName;
	/** 英文名 */
	private String enName;
	/** 企业网站 */
	private String website;
	/** 国家 */
	private String nation;
	/** 地区 */
	private String region;
	/** 城市 */
	private String city;
	/** 行业门类 */
	private String industryKind;
	/** 行业大类 */
	private String industryType;
	/** 所属行业 */
	private String inIndustry;
	/** 企业营业执照号码 */
	private String bizLicenceCode;
	/** 企业执照有效期 */
	private Date expire;
	/** 企业机构证件号码 */
	private String govCode;
	/** 企业税务等级证件号码 */
	private String taxCode;
	/** 风险等级号码 */
	private String riskLeveCode;
	/** 数据创建时间 */
	private Date createDate;
	/** 数据修改时间 */
	private Date updateDate;
	/** 中文拼音搜索关键字 */
	private String searchKey;
	/** 商户身份标识 */
	private int identity = 0;

	/** 商户结算周期 */
	private Integer settlementCycle ;
	
	// 保证金结算周期
	private Integer assureSettlementCycle;

	public long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(final long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(final long memberCode) {
		this.memberCode = memberCode;
	}

	public long getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(final long merchantCode) {
		this.merchantCode = merchantCode;
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

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
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

	public String getInIndustry() {
		return inIndustry;
	}

	public void setInIndustry(final String inIndustry) {
		this.inIndustry = inIndustry;
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

	public int getIdentity() {
		return identity;
	}

	public void setIdentity(final int identity) {
		this.identity = identity;
	}

	public Integer getAssureSettlementCycle() {
		return assureSettlementCycle;
	}

	public void setAssureSettlementCycle(final Integer assureSettlementCycle) {
		this.assureSettlementCycle = assureSettlementCycle;
	}

	public Integer getSettlementCycle() {
		return settlementCycle;
	}

	public void setSettlementCycle(final Integer settlementCycle) {
		this.settlementCycle = settlementCycle;
	}
	
}
