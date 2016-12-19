package com.pay.pe.model;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

import com.pay.inf.model.Model;



public class PaymentService implements Model{

	private String description;
	private Long fixedPayee;
	private Long fixedPayer;
	private Integer getMethod;
	private Integer payeeAcctTypeCode;
	private Integer payeeType;
	private Integer payerAcctTypeCode;
	private Integer payerType;
	private Integer paymentServiceCode;
	private String paymentServiceName;
	private Integer paymentServiceType;
	private Collection paymentSrvPkgAssignList;
	private Integer payMethod;
	private Collection PostingRuleCollection;
	private Collection pricingStrategyList;
	private Integer takeOn;
	private Integer reservedCodeType;
	
	/**
	 * 付费依据方
	 */
	private Integer dependOn;
	

	public Integer getReservedCodeType() {
		return reservedCodeType;
	}

	public void setReservedCodeType(Integer reservedCodeType) {
		this.reservedCodeType = reservedCodeType;
	}

	public PaymentService() {
		super();
		this.paymentSrvPkgAssignList = new Vector();
		this.PostingRuleCollection = new Vector();
		this.pricingStrategyList = new Vector();
	}

	public void addToPaymentSrvPkgAssignList(
			PaymentSrvPkgAssignment aPaymentSrvPkgAssignment) {
		this.paymentSrvPkgAssignList.add(aPaymentSrvPkgAssignment);
	}

	public void addToPostingRuleCollection(PostingRule aPostingRule) {
		this.PostingRuleCollection.add(aPostingRule);
		aPostingRule.setPaymentService(this);
	}

//	public void addToPricingStrategyList(PricingStrategy aPricingStrategy) {
//		this.pricingStrategyList.add(aPricingStrategy);
//		aPricingStrategy.setPaymentService(this);
//	}

	public String getDescription() {
		return this.description;
	}

	public Integer getPaymentServiceType() {
		return this.paymentServiceType;
	}

	public Collection getPaymentSrvPkgAssignList() {
		return this.paymentSrvPkgAssignList;
	}

	public Collection getPostingRuleCollection() {
		return this.PostingRuleCollection;
	}

	public Collection getPricingStrategyList() {
		return this.pricingStrategyList;
	}

	public Integer getTakeon() {
		return this.takeOn;
	}

	// public void handleEvent(DescriptorEvent aDescriptorEvent) {
	// try
	// {
	// AuditLog
	// al=(AuditLog)AppContextCollector.getContext("mainContext").getBean("auditLog");
	// al.saveLog(aDescriptorEvent.getObject(),aDescriptorEvent.getEventCode());
	// }
	// catch(Exception ex)
	// {
	// System.out.println("*audit event error*="+ex.toString());
	// }
	// }

	public void removeFromPaymentSrvPkgAssignList(
			PaymentSrvPkgAssignment aPaymentSrvPkgAssignment) {
		this.paymentSrvPkgAssignList.remove(aPaymentSrvPkgAssignment);
	}

	public void removeFromPostingRuleCollection(PostingRule aPostingRule) {
		this.PostingRuleCollection.remove(aPostingRule);
		aPostingRule.setPaymentService(null);
	}

//	public void removeFromPricingStrategyList(PricingStrategy aPricingStrategy) {
//		this.pricingStrategyList.remove(aPricingStrategy);
//		aPricingStrategy.setPaymentService(null);
//	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPayeeAcctTypeCode() {
		return payeeAcctTypeCode;
	}

	public void setPayeeAcctTypeCode(Integer payeeAcctTypeCode) {
		this.payeeAcctTypeCode = payeeAcctTypeCode;
	}

	public Integer getPayerAcctTypeCode() {
		return payerAcctTypeCode;
	}

	public void setPayerAcctTypeCode(Integer payerAcctTypeCode) {
		this.payerAcctTypeCode = payerAcctTypeCode;
	}

	public void setPaymentServiceType(Integer paymentServiceType) {
		this.paymentServiceType = paymentServiceType;
	}

	protected void setPaymentSrvPkgAssignList(Collection paymentSrvPkgAssignList) {
		this.paymentSrvPkgAssignList = paymentSrvPkgAssignList;
	}

	protected void setPostingRuleCollection(Collection PostingRuleCollection) {
		this.PostingRuleCollection = PostingRuleCollection;
	}

	protected void setPricingStrategyList(Collection pricingStrategyList) {
		this.pricingStrategyList = pricingStrategyList;
	}

	public void setTakeon(Integer takeon) {
		this.takeOn = takeon;
	}

	public Long getFixedPayee() {
		return fixedPayee;
	}

	public void setFixedPayee(Long fixedPayee) {
		this.fixedPayee = fixedPayee;
	}

	public Long getFixedPayer() {
		return fixedPayer;
	}

	public void setFixedPayer(Long fixedPayer) {
		this.fixedPayer = fixedPayer;
	}

	public Integer getGetMethod() {
		return getMethod;
	}

	public void setGetMethod(Integer getMethod) {
		this.getMethod = getMethod;
	}

	public Integer getPayeeType() {
		return payeeType;
	}

	public void setPayeeType(Integer payeeType) {
		this.payeeType = payeeType;
	}

	public Integer getPayerType() {
		return payerType;
	}

	public void setPayerType(Integer payerType) {
		this.payerType = payerType;
	}

	public Integer getPaymentServiceCode() {
		return paymentServiceCode;
	}

	public void setPaymentServiceCode(Integer paymentServiceCode) {
		this.paymentServiceCode = paymentServiceCode;
	}

	public String getPaymentServiceName() {
		return paymentServiceName;
	}

	public void setPaymentServiceName(String paymentServiceName) {
		this.paymentServiceName = paymentServiceName;
	}

	public Integer getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}
	
	

	public Integer getDependOn() {
		return dependOn;
	}

	public void setDependOn(Integer dependOn) {
		this.dependOn = dependOn;
	}


	// public String toString() {
	// StringBuffer sb = new StringBuffer();
	// sb.append("description->" + description + ";");
	// sb.append("fixedpayee->" + fixedpayee + ";");
	// sb.append("fixedpayer->" + fixedpayer + ";");
	// sb.append("getmethod->" + getmethod + ";");
	// sb.append("payeeAcctType->" + payeeAcctType + ";");
	// sb.append("payeetype->" + payeetype + ";");
	// sb.append("payerAcctType->" + payerAcctType + ";");
	// sb.append("payertype->" + payertype + ";");
	// sb.append("paymentservicecode->" + paymentservicecode + ";");
	// sb.append("paymentservicename->" + paymentservicename + ";");
	// sb.append("paymentServiceType->" + paymentServiceType + ";");
	// sb.append("paymethod->" + paymethod + ";");
	// sb.append("takeon->" + takeon + ";");
	// return sb.toString();
	// }

}
