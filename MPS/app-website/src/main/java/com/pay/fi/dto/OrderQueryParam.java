package com.pay.fi.dto;

public class OrderQueryParam {

	private String version; 	// 版本	version  查询接口3.4.1
	private String serialID;    // 请求序列号 serialID
	private String mode;		// 查询模式	mode
	private String type;		// 查询类型	type
	private String orderID;		// 商户订单号	orderID
	private String beginTime;	// 查询开始时间	beginTime
	private String endTime;		// 查询结束时间	endTime
	private String partnerID;	// 商户ID	partnerID
	private String remark;		// 扩展字段	remark
	private String charset;		// 编码方式	charset
	private String signType;	// 签名类型	signType
	private String signMsg;		// 签名字符串	signMsg
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
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
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	@Override
	public String toString() {
		return "OrderQueryParamDTO [version=" + version + ", serialID="
				+ serialID + ", mode=" + mode + ", type=" + type + ", orderID="
				+ orderID + ", beginTime=" + beginTime + ", endTime=" + endTime
				+ ", partnerID=" + partnerID + ", remark=" + remark
				+ ", charset=" + charset + ", signType=" + signType
				+ ", signMsg=" + signMsg + "]";
	}
	
	
	
	
}
