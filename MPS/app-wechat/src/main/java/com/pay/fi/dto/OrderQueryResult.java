/**
*
* auto generated by ibatis tools 
*
**/
package com.pay.fi.dto;

import java.util.List;

/**
 * 
 * @author hhj
 *
 */

public class OrderQueryResult  implements java.io.Serializable {

	private String serialID;			//请求序列号	serialID  订单查询结果3.4.2
	private String mode;				//查询模式	mode
	private String type;				//查询类型	type
	private String resultCode;			//处理结果码	resultCode
	private String queryDetailsSize;	//结果集长度	queryDetailsSize
	private String queryDetails;		//查询结果集	queryDetails
	private String partnerID;			//商户ID		partnerID
	private String remark;				//扩展字段	remark
	private String charset;				//编码方式	charset
	private String signType;			//签名类型	signType
	private String signMsg;				//签名字符串	signMsg

	private List<OrderQueryResultDetail> orderQueryResultDetails;
	private List<RefundOrderQueryResultDetail> refundOrderQueryResultDetails;
	public String getSerialID() {
		return serialID;
	}
	public void setSerialID(String serialID) {
		this.serialID = serialID;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getQueryDetailsSize() {
		return queryDetailsSize;
	}
	public void setQueryDetailsSize(String queryDetailsSize) {
		this.queryDetailsSize = queryDetailsSize;
	}
	public String getQueryDetails() {
		return queryDetails;
	}
	public void setQueryDetails(String queryDetails) {
		this.queryDetails = queryDetails;
	}
	public String getPartnerID() {
		return partnerID;
	}
	public void setPartnerID(String partnerID) {
		this.partnerID = partnerID;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getSignMsg() {
		return signMsg;
	}
	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}
	public List<OrderQueryResultDetail> getOrderQueryResultDetails() {
		return orderQueryResultDetails;
	}
	public void setOrderQueryResultDetails(
			List<OrderQueryResultDetail> orderQueryResultDetails) {
		this.orderQueryResultDetails = orderQueryResultDetails;
	}
	public List<RefundOrderQueryResultDetail> getRefundOrderQueryResultDetails() {
		return refundOrderQueryResultDetails;
	}
	public void setRefundOrderQueryResultDetails(
			List<RefundOrderQueryResultDetail> refundOrderQueryResultDetails) {
		this.refundOrderQueryResultDetails = refundOrderQueryResultDetails;
	}
	@Override
	public String toString() {
		return "OrderQueryResult [serialID=" + serialID + ", mode=" + mode
				+ ", type=" + type + ", resultCode=" + resultCode
				+ ", queryDetailsSize=" + queryDetailsSize + ", queryDetails="
				+ queryDetails + ", partnerID=" + partnerID + ", remark="
				+ remark + ", charset=" + charset + ", signType=" + signType
				+ ", signMsg=" + signMsg + ", orderQueryResultDetails="
				+ orderQueryResultDetails + ", refundOrderQueryResultDetails="
				+ refundOrderQueryResultDetails + "]";
	}
}