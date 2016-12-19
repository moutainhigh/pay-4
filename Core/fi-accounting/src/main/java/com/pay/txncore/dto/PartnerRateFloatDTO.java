package com.pay.txncore.dto;


/**
 * 商户费率浮动
 * @author qiyun10
 *
 */
public class PartnerRateFloatDTO {
	
	private String partnerId;
	
	private String startPoint;
	
	private String endPoint;
	
	private String createDate;
	
	private String operator;

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Override
	public String toString() {
		return "PartnerRateFloat [partnerId=" + partnerId + ", startPoint="
				+ startPoint + ", endPoint=" + endPoint + ", createDate="
				+ createDate + ", operator=" + operator + "]";
	}
}
