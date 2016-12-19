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

	/** 融资订单流水号 */
	private String creditOrderId;
	/** 申请开始时间 */
	private String applyStartTime;
	/** 申请结束时间 */
	private String applyEndTime;
	/** 融资方式*/
	private String interestType;
	/** 状态 */
	private String status;
	/** 商户号 */
	private String merchantCode;


	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(final String merchantCode) {
		this.merchantCode = merchantCode;
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
	public String getCreditOrderId() {
		return creditOrderId;
	}
	public void setCreditOrderId(final String creditOrderId) {
		this.creditOrderId = creditOrderId;
	}
	public String getInterestType() {
		return interestType;
	}
	public void setInterestType(final String interestType) {
		this.interestType = interestType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(final String status) {
		this.status = status;
	}
}
