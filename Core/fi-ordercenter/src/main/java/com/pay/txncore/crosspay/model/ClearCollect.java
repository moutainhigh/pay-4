package com.pay.txncore.crosspay.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 结算汇总记录(seq:161)
 */
public class ClearCollect implements Serializable {
    private BigDecimal id;

    /**
     * 商户ID
     */
    private String partnerId;

    /**
     * 总交易金额
     */
    private BigDecimal tradeAmount;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 退款扣费
     */
    private BigDecimal refundFee;

    /**
     * 冻结金额
     */
    private BigDecimal frozenAmount;

    /**
     * 拒付金额
     */
    private BigDecimal refusePaymentAmount;

    /**
     * 拒付费金额
     */
    private BigDecimal refuseFee;

    /**
     * 已结算金额
     */
    private BigDecimal clearAmount;

    /**
     * 手续费金额
     */
    private BigDecimal feeAmount;

    /**
     * 未返还押金
     */
    private BigDecimal pledgeAmount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 未结算金额
     */
    private BigDecimal unclearAmount;

    /**
     * 快递费金额
     */
    private BigDecimal expressFee;

    private static final long serialVersionUID = 1L;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
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
     * <b>获取</b> 总交易金额
     */
    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    /**
     * <b>设置</b> 总交易金额
     */
    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    /**
     * <b>获取</b> 退款金额
     */
    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    /**
     * <b>设置</b> 退款金额
     */
    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    /**
     * <b>获取</b> 退款扣费
     */
    public BigDecimal getRefundFee() {
        return refundFee;
    }

    /**
     * <b>设置</b> 退款扣费
     */
    public void setRefundFee(BigDecimal refundFee) {
        this.refundFee = refundFee;
    }

    /**
     * <b>获取</b> 冻结金额
     */
    public BigDecimal getFrozenAmount() {
        return frozenAmount;
    }

    /**
     * <b>设置</b> 冻结金额
     */
    public void setFrozenAmount(BigDecimal frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    /**
     * <b>获取</b> 拒付金额
     */
    public BigDecimal getRefusePaymentAmount() {
        return refusePaymentAmount;
    }

    /**
     * <b>设置</b> 拒付金额
     */
    public void setRefusePaymentAmount(BigDecimal refusePaymentAmount) {
        this.refusePaymentAmount = refusePaymentAmount;
    }

    /**
     * <b>获取</b> 拒付费金额
     */
    public BigDecimal getRefuseFee() {
        return refuseFee;
    }

    /**
     * <b>设置</b> 拒付费金额
     */
    public void setRefuseFee(BigDecimal refuseFee) {
        this.refuseFee = refuseFee;
    }

    /**
     * <b>获取</b> 已结算金额
     */
    public BigDecimal getClearAmount() {
        return clearAmount;
    }

    /**
     * <b>设置</b> 已结算金额
     */
    public void setClearAmount(BigDecimal clearAmount) {
        this.clearAmount = clearAmount;
    }

    /**
     * <b>获取</b> 手续费金额
     */
    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    /**
     * <b>设置</b> 手续费金额
     */
    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    /**
     * <b>获取</b> 未返还押金
     */
    public BigDecimal getPledgeAmount() {
        return pledgeAmount;
    }

    /**
     * <b>设置</b> 未返还押金
     */
    public void setPledgeAmount(BigDecimal pledgeAmount) {
        this.pledgeAmount = pledgeAmount;
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

    /**
     * <b>获取</b> 未结算金额
     */
    public BigDecimal getUnclearAmount() {
        return unclearAmount;
    }

    /**
     * <b>设置</b> 未结算金额
     */
    public void setUnclearAmount(BigDecimal unclearAmount) {
        this.unclearAmount = unclearAmount;
    }

    /**
     * <b>获取</b> 快递费金额
     */
    public BigDecimal getExpressFee() {
        return expressFee;
    }

    /**
     * <b>设置</b> 快递费金额
     */
    public void setExpressFee(BigDecimal expressFee) {
        this.expressFee = expressFee;
    }
}