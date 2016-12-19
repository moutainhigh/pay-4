package com.pay.app.facade.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OrderQueryParamDTO {

	/**
	 * 版本 
	 */
	@Size(max=4)
	@NotNull
	private String version; 	
	/**
	 * 请求序列号
	 */
	@Size(max=32)
	@NotNull
	private String serialID;    
	/**
	 * 查询模式
	 */
	@Size(max=2)
	@NotNull
	private String mode;		
	/**
	 * 查询类型
	 */
	@Size(max=2)
	@NotNull
	private String type;		
	/**
	 * 商户订单号
	 */
	@Size(max=32)
	private String orderID;		
	/**
	 * 查询开始时间
	 */
	@Size(max=14)
	private String beginTime;	
	/**
	 * 查询结束时间
	 */
	@Size(max=14)
	private String endTime;		
	/**
	 * 商户ID
	 */
	@Size(max=32)
	@NotNull
	private String partnerID;	
	/**
	 * 扩展字段
	 */
	@Size(max=256)
	@NotNull
	private String remark;		
	/**
	 * 编码方式
	 */
	@Size(max=1)
	@NotNull
	private String charset;		
	/**
	 * 签名类型
	 */
	@Size(max=1)
	@NotNull
	private String signType;	
	/**
	 * 签名字符串
	 */
	@Size(max=256)
	@NotNull
	private String signMsg;		
	
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
	
	public String toMsg() {
		return "serialID="
				+serialID+"&mode="+mode+"&type="
				+type+"&beginTime="+beginTime+"&endTime="+endTime
				+"&partnerID="+partnerID+"&remark="+remark
				+"&charset="+charset+"&signType="+signType
				+"&signMsg="+signMsg;
	}
	
}
