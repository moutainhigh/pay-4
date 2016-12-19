package com.pay.txncore.crosspay.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 补充结算记录(seq:159)
 */
public class ReclearOrder implements Serializable {
    private BigDecimal id;

    /**
     * 网关订单基础信息流水号 [外键]
     */
    private BigDecimal tradeBaseNo;

    /**
     * 网关订单流水号 [主键]
     */
    private BigDecimal tradeOrderNo;

    /**
     * 请求序列号
     */
    private String requestSerialId;

    /**
     * 商户ID
     */
    private String partnerId;

    /**
     * 商户订单号
     */
    private String orderId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 0：未结算<br>
	 * 1：已结算
     */
    private String status;

    /**
     * 0：解冻<br>
	 * 1：运单状态变更
     */
    private String reason;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date updateDate;

    private static final long serialVersionUID = 1L;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     * <b>获取</b> 网关订单基础信息流水号 [外键]
     */
    public BigDecimal getTradeBaseNo() {
        return tradeBaseNo;
    }

    /**
     * <b>设置</b> 网关订单基础信息流水号 [外键]
     */
    public void setTradeBaseNo(BigDecimal tradeBaseNo) {
        this.tradeBaseNo = tradeBaseNo;
    }

    /**
     * <b>获取</b> 网关订单流水号 [主键]
     */
    public BigDecimal getTradeOrderNo() {
        return tradeOrderNo;
    }

    /**
     * <b>设置</b> 网关订单流水号 [主键]
     */
    public void setTradeOrderNo(BigDecimal tradeOrderNo) {
        this.tradeOrderNo = tradeOrderNo;
    }

    /**
     * <b>获取</b> 请求序列号
     */
    public String getRequestSerialId() {
        return requestSerialId;
    }

    /**
     * <b>设置</b> 请求序列号
     */
    public void setRequestSerialId(String requestSerialId) {
        this.requestSerialId = requestSerialId;
    }

    /**
     * <b>获取</b> 商户ID
     */
    public String getPartnerId() {
        return partnerId;
    }

    /**
     * <b>设置</b> 商户ID
     */
    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    /**
     * <b>获取</b> 商户订单号
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * <b>设置</b> 商户订单号
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * <b>获取</b> 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * <b>设置</b> 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * <b>获取</b> 0：未结算<br>
	 * 1：已结算
     */
    public String getStatus() {
        return status;
    }

    /**
     * <b>设置</b> 0：未结算<br>
	 * 1：已结算
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * <b>获取</b> 0：解冻<br>
	 * 1：运单状态变更
     */
    public String getReason() {
        return reason;
    }

    /**
     * <b>设置</b> 0：解冻<br>
	 * 1：运单状态变更
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * <b>获取</b> 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <b>设置</b> 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * <b>获取</b> 修改时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * <b>设置</b> 修改时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}