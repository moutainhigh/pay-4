package com.pay.txncore.crosspay.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 结算历史记录(seq:160)
 */
public class ClearLog implements Serializable {
    private BigDecimal id;

    /**
     * 商户ID
     */
    private String partnerId;

    /**
     * 可结金额
     */
    private BigDecimal mayClearAmount;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 拒付金额
     */
    private BigDecimal refusePaymentAmount;

    /**
     * 冻结金额
     */
    private BigDecimal frozenAmount;

    /**
     * 解冻金额
     */
    private BigDecimal unfrozenAmount;

    /**
     * 押金
     */
    private BigDecimal pledgeAmount;

    /**
     * 结算金额
     */
    private BigDecimal clearAmount;

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
     * 交易日期
     */
    private Date tradeDate;

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
     * <b>获取</b> 可结金额
     */
    public BigDecimal getMayClearAmount() {
        return mayClearAmount;
    }

    /**
     * <b>设置</b> 可结金额
     */
    public void setMayClearAmount(BigDecimal mayClearAmount) {
        this.mayClearAmount = mayClearAmount;
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
     * <b>获取</b> 解冻金额
     */
    public BigDecimal getUnfrozenAmount() {
        return unfrozenAmount;
    }

    /**
     * <b>设置</b> 解冻金额
     */
    public void setUnfrozenAmount(BigDecimal unfrozenAmount) {
        this.unfrozenAmount = unfrozenAmount;
    }

    /**
     * <b>获取</b> 押金
     */
    public BigDecimal getPledgeAmount() {
        return pledgeAmount;
    }

    /**
     * <b>设置</b> 押金
     */
    public void setPledgeAmount(BigDecimal pledgeAmount) {
        this.pledgeAmount = pledgeAmount;
    }

    /**
     * <b>获取</b> 结算金额
     */
    public BigDecimal getClearAmount() {
        return clearAmount;
    }

    /**
     * <b>设置</b> 结算金额
     */
    public void setClearAmount(BigDecimal clearAmount) {
        this.clearAmount = clearAmount;
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
     * <b>获取</b> 交易日期
     */
    public Date getTradeDate() {
        return tradeDate;
    }

    /**
     * <b>设置</b> 交易日期
     */
    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }
}