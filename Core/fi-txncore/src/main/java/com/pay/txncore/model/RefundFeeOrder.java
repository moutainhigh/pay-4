package com.pay.txncore.model;


import java.util.Date;

//退款手续费订单
public class RefundFeeOrder {
   
    private Long id;		//主键


    private Long refundOrderNo;		//退款订单号

    
    private Long tradeOrderNo;		//网关订单号
    

    private String merchantRefundOrderId;	//商户退款订单号
    
    private String merchantOrderId;	//商户原始订单号
    
    private String feeCurrencyCode;		//退款服务费币种


    private String feeAmount;		//退款手续费


    private Long partnerId;		//商户会员号

    private Date createDate;
    
    
    private Date updateDate;

    /**
     * 0-未收取风控服务费，1-已收取服务费
     */
    private Integer feeFlg;
    
    private String settlementCurrencyCode;	//清算币种
    
    private String exchangeRate;			//汇率
    
    private Long settlementAmount;			//清算币种

    public String getMerchantRefundOrderId() {
		return merchantRefundOrderId;
	}

	public void setMerchantRefundOrderId(String merchantRefundOrderId) {
		this.merchantRefundOrderId = merchantRefundOrderId;
	}
	
	public String getMerchantOrderId(){
		return merchantOrderId;
	}

	public void setMerchantOrderId(String merchantOrderId){
		this.merchantOrderId=merchantOrderId;
	}
	
    public Long getId() {
        return id;
    }

    /**
     *
     */
    public void setId(Long id) {
        this.id = id;
    }


    public Long getTradeOrderNo() {
        return tradeOrderNo;
    }

    /**
     *
     */
    public void setTradeOrderNo(Long tradeOrderNo) {
        this.tradeOrderNo = tradeOrderNo;
    }

    public Long getRefundOrderNo() {
        return refundOrderNo;
    }

    /**
     *
     */
    public void setRefundOrderNo(Long refundOrderNo) {
        this.refundOrderNo = refundOrderNo;
    }
    

    public String getFeeCurrencyCode() {
        return feeCurrencyCode;
    }

    /**
     *
     */
    public void setFeeCurrencyCode(String feeCurrencyCode) {
        this.feeCurrencyCode = feeCurrencyCode;
    }


    public String getFeeAmount() {
        return feeAmount;
    }

    /**
     *
     */
    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    /**
     *
     * @return the value of T_RISK_ORDER.PARTNER_ID
     */
    public Long getPartnerId() {
        return partnerId;
    }

    /**
     *
     */
    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    /**
     *
     * @return the value of T_RISK_ORDER.CREATE_DATE
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     *
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     *
     * @return the value of T_RISK_ORDER.FEE_FLG
     */
    public Integer getFeeFlg() {
        return feeFlg;
    }

    /**
     *
     */
    public void setFeeFlg(Integer feeFlg) {
        this.feeFlg = feeFlg;
    }

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}

	public void setSettlementCurrencyCode(String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public Long getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(Long settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
}

