/**
 *
 */
package com.pay.credit.conditon.creditorder;

import com.pay.credit.conditon.common.QueryCondition;

/**
 * 融资订单查询条件
 *
 * @author zhixiang.deng
 *
 * @date 2015年8月4日
 */
public class CreditOrderQueryConditon extends QueryCondition {

	/** 订单授信，融资订单流水号 */
	private String creditOrderId ;
	/** 会员号 */
	private String partnerId;
	/** 申请开始时间 */
	private String applyStartTime;
	/** 申请结束时间 */
	private String applyEndTime;
	/** 融资方式*/
	private String interestType;
	/** 商户名称 **/
	private String  merchantName;
	/**状态*/
	private String status;

	public String getStatus() {
		return status;
	}
	public void setStatus(final String status) {
		this.status = status;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(final String partnerId) {
		this.partnerId = partnerId;
	}
	public String getApplyStartTime() {
		return applyStartTime;
	}
	public void setApplyStartTime(final String applyStartTime) {
		this.applyStartTime = applyStartTime;
	}
	public String getApplyEndTime() {
		return applyEndTime;
	}
	public void setApplyEndTime(final String applyEndTime) {
		this.applyEndTime = applyEndTime;
	}
	public String getInterestType() {
		return interestType;
	}
	public void setInterestType(final String interestType) {
		this.interestType = interestType;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(final String merchantName) {
		this.merchantName = merchantName;
	}
	public String getCreditOrderId() {
		return creditOrderId;
	}
	public void setCreditOrderId(final String creditOrderId) {
		this.creditOrderId = creditOrderId;
	}
	
}
