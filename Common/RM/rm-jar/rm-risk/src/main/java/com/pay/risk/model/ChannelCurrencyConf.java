package com.pay.risk.model;

import java.util.Date;


/**
 * 送渠道币种转换配置
 * @author peiyu.yang
 *
 */
public class ChannelCurrencyConf {
	
	private Long id;//
	private Long partnerId;//会员号
	private String orgCode;//渠道编号
	private String currencyCode;//交易币种
	private String orgCurrencyCode;//送渠道币种
	private String status;//状态
	private Date createDate;//创建日期
    private Date updateDate;//更新日期
    private String Operator;//操作者
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getOrgCurrencyCode() {
		return orgCurrencyCode;
	}
	public void setOrgCurrencyCode(String orgCurrencyCode) {
		this.orgCurrencyCode = orgCurrencyCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getOperator() {
		return Operator;
	}
	public void setOperator(String operator) {
		Operator = operator;
	}
	
	@Override
	public String toString() {
		return "ChannelCurrencyConf [id=" + id + ", partnerId=" + partnerId
				+ ", orgCode=" + orgCode + ", currencyCode=" + currencyCode
				+ ", orgCurrencyCode=" + orgCurrencyCode + ", status=" + status
				+ ", createDate=" + createDate + ", updateDate=" + updateDate
				+ ", Operator=" + Operator + "]";
	}
}
