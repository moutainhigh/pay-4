/**
 * 
 */
package com.pay.channel.model;

import java.util.Date;

/**
 * @author huhb
 *
 */
public class PaymentChannelItemConfig implements java.io.Serializable {
	private static final long serialVersionUID = -1196200213106912357L;

	private Long id;

	private Long memberCode;

	private Integer paymentType;

	private Long paymentChannelItemId;

	private String operator;

	private Date createDate;
	/**渠道优先级**/
	private String channelPriority;

	public String getChannelPriority() {
		return channelPriority;
	}

	public void setChannelPriority(String channelPriority) {
		this.channelPriority = channelPriority;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public Long getPaymentChannelItemId() {
		return paymentChannelItemId;
	}

	public void setPaymentChannelItemId(Long paymentChannelItemId) {
		this.paymentChannelItemId = paymentChannelItemId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

}
