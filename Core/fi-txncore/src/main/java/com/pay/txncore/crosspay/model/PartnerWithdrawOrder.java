package com.pay.txncore.crosspay.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商户提现记录
 */
public class PartnerWithdrawOrder implements Serializable {
    private Long id;

    /**
     * 商户ID
     */
    private String partnerId;

    /**
     * 订单金额
     */
    private Long amount;

    /**
     * 商户显示名 [可选] 以此处优先于注册名称
     */
    private String partnerDisplayName;

    /**
     * 交易状态 [0:未付款;1:交易关闭;2:已付款;3:交易完成（含退款）;4:交易成功;5:交易失败(担保交易)]
     */
    private Integer status;

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
    private Date clearDate;

    /**
     * 修改时间
     */
    private Date auditDate;

    private String operator;
    
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
     * <b>获取</b> 订单金额
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * <b>设置</b> 订单金额
     */
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    /**
     * <b>获取</b> 商户显示名 [可选] 以此处优先于注册名称
     */
    public String getPartnerDisplayName() {
        return partnerDisplayName;
    }

    /**
     * <b>设置</b> 商户显示名 [可选] 以此处优先于注册名称
     */
    public void setPartnerDisplayName(String partnerDisplayName) {
        this.partnerDisplayName = partnerDisplayName;
    }

    /**
     * <b>获取</b> 交易状态 [0:未付款;1:交易关闭;2:已付款;3:交易完成（含退款）;4:交易成功;5:交易失败(担保交易)]
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * <b>设置</b> 交易状态 [0:未付款;1:交易关闭;2:已付款;3:交易完成（含退款）;4:交易成功;5:交易失败(担保交易)]
     */
    public void setStatus(Integer status) {
        this.status = status;
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
    public Date getClearDate() {
        return clearDate;
    }

    /**
     * <b>设置</b> 修改时间
     */
    public void setClearDate(Date clearDate) {
        this.clearDate = clearDate;
    }

    /**
     * <b>获取</b> 修改时间
     */
    public Date getAuditDate() {
        return auditDate;
    }

    /**
     * <b>设置</b> 修改时间
     */
    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

	public String getAmountStr() {
		return new BigDecimal(amount).divide(new BigDecimal("1000")).toString();
	}
	
	public String getStatusName(){
	    switch (this.status) {
	    	case 0:
	    		return "可提现";
			case 1:
				return "待审核";
			case 2:
				return "审核未通过";
			case 3:
				return "审核通过";
			case 4:
				return "完成";
			default:
				return "未知状态";
		}
		
	}

}