/**
 * 
 */
package com.pay.fi.chain.condition.paylink;

import com.pay.fi.chain.condition.common.QueryCondition;

/**
 * 支付链查询条件
 * @author PengJiangbo
 *
 */
public class PayLinkCondition extends QueryCondition {

	/** 商户会员号 */
	private Long memberCode ;
	/** 支付链ID */
	private Long payLinkNo ;
	/** 支付链接 */
	private String payLinkName ;
	/** 商品名称 */
	private String productName ;
	/** 创建时间 开始 */
	private String createTimeStart ;
	/** 创建时间结束 */
	private String createTimeEnd ;
	private String createTimeBetween = "" ;
	/** 失效时间开始 */
	private String invalidTimeStart ;
	/** 失效时间结束 */
	private String invalidTimeEnd ;
	private String invalidTimeBetween = "" ;
	/** 状态 */
	private Integer status = null ;
	/** 后台隐藏条件：失效时间 */
	private String invalidTime ;
	public String getPayLinkName() {
		return payLinkName;
	}
	public void setPayLinkName(final String payLinkName) {
		this.payLinkName = payLinkName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(final String productName) {
		this.productName = productName;
	}
	public String getCreateTimeStart() {
		return createTimeStart;
	}
	public void setCreateTimeStart(final String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}
	public String getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(final String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	public String getInvalidTimeStart() {
		return invalidTimeStart;
	}
	public void setInvalidTimeStart(final String invalidTimeStart) {
		this.invalidTimeStart = invalidTimeStart;
	}
	public String getInvalidTimeEnd() {
		return invalidTimeEnd;
	}
	public void setInvalidTimeEnd(final String invalidTimeEnd) {
		this.invalidTimeEnd = invalidTimeEnd;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(final Integer status) {
		this.status = status;
	}
	public String getCreateTimeBetween() {
		return createTimeBetween;
	}
	public void setCreateTimeBetween(final String createTimeBetween) {
		this.createTimeBetween = createTimeBetween;
	}
	public String getInvalidTimeBetween() {
		return invalidTimeBetween;
	}
	public void setInvalidTimeBetween(final String invalidTimeBetween) {
		this.invalidTimeBetween = invalidTimeBetween;
	}
	public String getInvalidTime() {
		return invalidTime;
	}
	public void setInvalidTime(final String invalidTime) {
		this.invalidTime = invalidTime;
	}
	public Long getPayLinkNo() {
		return payLinkNo;
	}
	public void setPayLinkNo(final Long payLinkNo) {
		this.payLinkNo = payLinkNo;
	}
	
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(final Long memberCode) {
		this.memberCode = memberCode;
	}
	@Override
	public String toString() {
		return "PayLinkCondition [memberCode=" + memberCode + ", payLinkNo="
				+ payLinkNo + ", payLinkName=" + payLinkName + ", productName="
				+ productName + ", createTimeStart=" + createTimeStart
				+ ", createTimeEnd=" + createTimeEnd + ", createTimeBetween="
				+ createTimeBetween + ", invalidTimeStart=" + invalidTimeStart
				+ ", invalidTimeEnd=" + invalidTimeEnd
				+ ", invalidTimeBetween=" + invalidTimeBetween + ", status="
				+ status + ", invalidTime=" + invalidTime + "]";
	}
	
}
