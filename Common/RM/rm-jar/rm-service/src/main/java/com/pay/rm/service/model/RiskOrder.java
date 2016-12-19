package com.pay.rm.service.model;

import java.util.Date;

public class RiskOrder {
    /**
     * 主键
     */
    private Long id;

    /**
     * 网关订单号
     */
    private Long tradeOrderNo;
    
    /**
     * 商户订单号
     */
    private String merchantOrderId;

    /**
     * 风控服务费币种
     */
    private String feeCurrencyCode;

    /**
     * 风控手续费
     */
    private String feeAmount;

    /**
     * 商户会员号
     */
    private Long partnerId;

    /**
     * null
     */
    private Date createDate;
    
    
    private Date updateDate;

    /**
     * 0-未收取风控服务费，1-已收取服务费
     */
    private Integer feeFlg;
    
    private String settlementCurrencyCode;
    
    private String exchangeRate;
    
    private Long settlementAmount;

    public String getMerchantOrderId() {
		return merchantOrderId;
	}

	public void setMerchantOrderId(String merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}

	/**
     *
     * @return the value of T_RISK_ORDER.ID
     */
    public Long getId() {
        return id;
    }

    /**
     *
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return the value of T_RISK_ORDER.TRADE_ORDER_NO
     */
    public Long getTradeOrderNo() {
        return tradeOrderNo;
    }

    /**
     *
     */
    public void setTradeOrderNo(Long tradeOrderNo) {
        this.tradeOrderNo = tradeOrderNo;
    }

    /**
     *
     * @return the value of T_RISK_ORDER.FEE_CURRENCY_CODE
     */
    public String getFeeCurrencyCode() {
        return feeCurrencyCode;
    }

    /**
     *
     */
    public void setFeeCurrencyCode(String feeCurrencyCode) {
        this.feeCurrencyCode = feeCurrencyCode;
    }

    /**
     *
     * @return the value of T_RISK_ORDER.FEE_AMOUNT
     */
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