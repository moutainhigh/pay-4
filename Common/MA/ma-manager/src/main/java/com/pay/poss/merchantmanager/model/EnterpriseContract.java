package com.pay.poss.merchantmanager.model;

import java.util.Date;
/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		EnterpriseContract.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-9-19		gungun_zhang			Create
 */
public class EnterpriseContract {
	
    private Long contractId;//主键
    private Long memberCode;//会员号
    private String contractCode;//合同编号
    private String signName;//签约人
    private String signLoginId;//签约人帐号  2014/5/12
    private String signDepart;//签约人部门
    private Integer continueSign;//到期后是否自动续签，0为到期终止，1到期自动续签
    private Integer contractStatus;//合同状态
    private String marketName;//市场人员姓名
    private String marketLink;//市场人员联系方式
    private Date startDate;//合同起始时间
    private Date endDate;//合同结束时间
    private Long openFee;//开通费用
    private Long yearFee;//年服务费
    private Long factOpenFee;//折扣开通服务费用
    private Long factYearFee;//折扣年服务费用
    private Date factStartDate;//实际开始时间
    private Date factEndDate;//时间结束时间
    private Long assureFee;//应收保证金
    private Long factAssureFee;//实收保证金
    private String assureDesc;//保证金说明
    private Date createDate;//数据创建数据
    private Date updateDate;//数据更新时间
    
    
	public String getSignLoginId() {
		return signLoginId;
	}
	public void setSignLoginId(String signLoginId) {
		this.signLoginId = signLoginId;
	}
	public Long getContractId() {
		return contractId;
	}
	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
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
	public Integer getContinueSign() {
		return continueSign;
	}
	public void setContinueSign(Integer continueSign) {
		this.continueSign = continueSign;
	}
	public Integer getContractStatus() {
		return contractStatus;
	}
	public void setContractStatus(Integer contractStatus) {
		this.contractStatus = contractStatus;
	}
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public String getMarketLink() {
		return marketLink;
	}
	public void setMarketLink(String marketLink) {
		this.marketLink = marketLink;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Long getOpenFee() {
		return openFee;
	}
	public void setOpenFee(Long openFee) {
		this.openFee = openFee;
	}
	public Long getYearFee() {
		return yearFee;
	}
	public void setYearFee(Long yearFee) {
		this.yearFee = yearFee;
	}
	public Long getFactOpenFee() {
		return factOpenFee;
	}
	public void setFactOpenFee(Long factOpenFee) {
		this.factOpenFee = factOpenFee;
	}
	public Long getFactYearFee() {
		return factYearFee;
	}
	public void setFactYearFee(Long factYearFee) {
		this.factYearFee = factYearFee;
	}
	public Date getFactStartDate() {
		return factStartDate;
	}
	public void setFactStartDate(Date factStartDate) {
		this.factStartDate = factStartDate;
	}
	public Date getFactEndDate() {
		return factEndDate;
	}
	public void setFactEndDate(Date factEndDate) {
		this.factEndDate = factEndDate;
	}
	public Long getAssureFee() {
		return assureFee;
	}
	public void setAssureFee(Long assureFee) {
		this.assureFee = assureFee;
	}
	public Long getFactAssureFee() {
		return factAssureFee;
	}
	public void setFactAssureFee(Long factAssureFee) {
		this.factAssureFee = factAssureFee;
	}
	public String getAssureDesc() {
		return assureDesc;
	}
	public void setAssureDesc(String assureDesc) {
		this.assureDesc = assureDesc;
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

    
}