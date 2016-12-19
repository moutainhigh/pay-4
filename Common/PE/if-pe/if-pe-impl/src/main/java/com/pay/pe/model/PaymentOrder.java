package com.pay.pe.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pay.inf.model.Model;



public class PaymentOrder implements Model{

	private String version;

	private String returnUrl;

	private Date lastUpdateTime;

	private Integer orderStatus;

	private Date orderTime;

	private Integer payeeOrgType;

	private String payeeOrgCode;

	private Integer payeeAcctType;

	private String payeeAcctCode;

	private Integer payerOrgType;

	private String payerOrgCode;

	private Integer payerAcctType;

	private String payerAcctCode;

	private String couponNumber;

	private Long discountAmount;

	private Long orderAmount;

	private Integer productNum;

	private String productName;

	private String orgOrderId;

	private Integer payMethod;

	private Integer orderType;
	
//	private Integer orderCode;

	private String submitAcctCode;

	private String orderId;

	private Long sequenceId;
	
    private String memo;
    			   
    private String relatedSequenceId;
    			   
    private Integer relatedType;
    
    private String payeeIdentity;
    
    private String orderDigest;
    
    private Integer terminalTypeCode;//终端类型 1为web 2为wap 3为api 4为pos
    
    private Boolean isReversed = Boolean.FALSE; 
    
    private String ip;//IP 地址
    
    private String referenceNum; //
    
    private String loyalCardNumber;
    
    private Date merchantOrderTime;
    
    private Integer opVersion;
    
    private String payerDisplayName;
	private String payeeDisplayName;
	
	  /**
     * 为了兼容APP_MDB中新版本而设置
     */
    private String currencyCode;
	
	public String getPayerDisplayName() {
		return payerDisplayName;
	}

	public void setPayerDisplayName(String payerDisplayName) {
		this.payerDisplayName = payerDisplayName;
	}

	public String getPayeeDisplayName() {
		return payeeDisplayName;
	}

	public void setPayeeDisplayName(String payeeDisplayName) {
		this.payeeDisplayName = payeeDisplayName;
	}
    
    public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public void setOpVersion(Integer opversion){
    	this.opVersion = opversion  ;
    }
    public Integer getOpVersion(){
    	return this.opVersion  ;
    }
    
	private static List <String> pk = new ArrayList <String> ();
	static {
		pk.add("sequenceId");
	}
	
	/**
	 * @return Returns the terminalTypeCode.
	 */
	public final Integer getTerminalTypeCode() {
		return terminalTypeCode;
	}

	/**
	 * @param terminalTypeCode The terminalTypeCode to set.
	 */
	public final void setTerminalTypeCode(Integer terminalTypeCode) {
		this.terminalTypeCode = terminalTypeCode;
	}

	public PaymentOrder() {
		super();
	}

	public String getCouponNumber() {
		return this.couponNumber;
	}

	public Long getDiscountAmount() {
		return this.discountAmount;
	}

	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public Long getOrderAmount() {
		return this.orderAmount;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public Integer getOrderStatus() {
		return this.orderStatus;
	}

	public Date getOrderTime() {
		return this.orderTime;
	}

	public Integer getOrderType() {
		return this.orderType;
	}

	public String getOrgOrderId() {
		return this.orgOrderId;
	}

	public Integer getPayMethod() {
		return this.payMethod;
	}

	public String getPayeeAcctCode() {
		return this.payeeAcctCode;
	}

	public Integer getPayeeAcctType() {
		return this.payeeAcctType;
	}

	public String getPayeeOrgCode() {
		return this.payeeOrgCode;
	}

	public Integer getPayeeOrgType() {
		return this.payeeOrgType;
	}

	public String getPayerAcctCode() {
		return this.payerAcctCode;
	}

	public Integer getPayerAcctType() {
		return this.payerAcctType;
	}

	public String getPayerOrgCode() {
		return this.payerOrgCode;
	}

	public Integer getPayerOrgType() {
		return this.payerOrgType;
	}

	public String getProductName() {
		return this.productName;
	}

	public Integer getProductNum() {
		return this.productNum;
	}

	public String getReturnUrl() {
		return this.returnUrl;
	}

	public Long getSequenceId() {
		return this.sequenceId;
	}

	public String getSubmitAcctCode() {
		return this.submitAcctCode;
	}

	public String getVersion() {
		return this.version;
	}

	public void setCouponNumber(String couponNumber) {
		this.couponNumber = couponNumber;
	}

	public void setDiscountAmount(Long discountAmount) {
		this.discountAmount = discountAmount;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public void setOrgOrderId(String orgOrderId) {
		this.orgOrderId = orgOrderId;
	}

	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}

	public void setPayeeAcctCode(String payeeAcctCode) {
		this.payeeAcctCode = payeeAcctCode;
	}

	public void setPayeeAcctType(Integer payeeAcctType) {
		this.payeeAcctType = payeeAcctType;
	}

	public void setPayeeOrgCode(String payeeOrgCode) {
		this.payeeOrgCode = payeeOrgCode;
	}

	public void setPayeeOrgType(Integer payeeOrgType) {
		this.payeeOrgType = payeeOrgType;
	}

	public void setPayerAcctCode(String payerAcctCode) {
		this.payerAcctCode = payerAcctCode;
	}

	public void setPayerAcctType(Integer payerAcctType) {
		this.payerAcctType = payerAcctType;
	}

	public void setPayerOrgCode(String payerOrgCode) {
		this.payerOrgCode = payerOrgCode;
	}

	public void setPayerOrgType(Integer payerOrgType) {
		this.payerOrgType = payerOrgType;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}

	public void setSubmitAcctCode(String submitAcctCode) {
		this.submitAcctCode = submitAcctCode;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	/**
	 * @return Returns the payeeIdentity.
	 */
	public String getPayeeIdentity() {
		return payeeIdentity;
	}

	/**
	 * @param payeeIdentity The payeeIdentity to set.
	 */
	public void setPayeeIdentity(String payeeIdentity) {
		this.payeeIdentity = payeeIdentity;
	}

	/**
	 * @return Returns the orderDigest.
	 */
	public String getOrderDigest() {
		return orderDigest;
	}

	/**
	 * @param orderDigest The orderDigest to set.
	 */
	public void setOrderDigest(String orderDigest) {
		this.orderDigest = orderDigest;
	}

	public Object getPrimaryKey() {
		Object[] obj = new Object[]{sequenceId};
		return obj;
	}

	public void setPrimaryKey(Object key) {
		if (null != key) {
			Object[] obj = (Object[])key;
			setSequenceId((Long)obj[0]);
		}
	}

	public List getPrimaryKeyFields() {
		return pk;
	}

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getRelatedSequenceID() {
        return relatedSequenceId;
    }

    public void setRelatedSequenceID(String relatedSequenceID) {
        this.relatedSequenceId = relatedSequenceID;
    }

    public Integer getRelatedType() {
        return relatedType;
    }

    public void setRelatedType(Integer relatedType) {
        this.relatedType = relatedType;
    }

	/**
	 * @return Returns the isReversed.
	 */
	public Boolean getIsReversed() {
		return isReversed;
	}

	/**
	 * @param isReversed The isReversed to set.
	 */
	public void setIsReversed(Boolean isReversed) {
		this.isReversed = isReversed;
	}

	/**
	 * @return Returns the ip.
	 */
	public final String getIp() {
		return ip;
	}

	/**
	 * @param ip The ip to set.
	 */
	public final void setIp(String ip) {
		this.ip = ip;
	}

	public final String getReferenceNum() {
		return referenceNum;
	}

	public final void setReferenceNum(String referenceNum) {
		this.referenceNum = referenceNum;
	}

	/**
	 * @return Returns the loyalCardNumber.
	 */
	public String getLoyalCardNumber() {
		return loyalCardNumber;
	}

	/**
	 * @param loyalCardNumber The loyalCardNumber to set.
	 */
	public void setLoyalCardNumber(String loyalCardNumber) {
		this.loyalCardNumber = loyalCardNumber;
	}

	/**
	 * @return Returns the merchantOrderTime.
	 */
	public Date getMerchantOrderTime() {
		return merchantOrderTime;
	}

	/**
	 * @param merchantOrderTime The merchantOrderTime to set.
	 */
	public void setMerchantOrderTime(Date merchantOrderTime) {
		this.merchantOrderTime = merchantOrderTime;
	}

	public Integer getOrderCode() {
		return orderType;
	}

	public void setOrderCode(Integer orderCode) {
		this.orderType = orderCode;
	}
	
	
}
