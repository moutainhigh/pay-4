/**
 *Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
 */
package com.pay.base.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.pay.app.base.session.RequestLocal;
import com.pay.app.common.util.ImgUrlUtil;
import com.pay.base.common.enums.EffectiveTypeEnum;
import com.pay.base.model.ContextPicture;
import com.pay.util.FormatDate;



/**
 * 支付链付款信息Dto发送邮件,支付链支付用
 * 
 * @author fjl
 * @date 2011-9-20
 */
public class PayChainPayInfo {

	private String payChainId;//支付链id
	private String payChainCode;// 支付链编号
	private String orderNo; // 订单号，支付收款流水号
	private String payeeMemberCode;//收款方会员号
	private String payeeName;// 收款方名称
	private String payeeAddr;// 收款方地址
	private String payeeTel;// 收款方电话
	private String receiptDesc;// 收款项目描述
	private String amount;// 金额单位元保留两位小数
	
	private String payerEmail;//付款方邮件
	private String payerName;//付款方名称
	private String payDesc;//付款备注
	
	private Date createDate;//生成时间
	private Date overdueDate;//过期时间
	private Integer effectiveDate;//有效期类型
	
	private String payChainName; //支付链名称
	
	private List<ContextPicture> picList;//支付链对应的图片
	
	private String mcc;//支付链收款方mcc
	
	
	public String getPayChainId() {
		return payChainId;
	}

	public void setPayChainId(String payChainId) {
		this.payChainId = payChainId;
	}

	public String getPayChainCode() {
		return payChainCode;
	}

	public void setPayChainCode(String payChainCode) {
		this.payChainCode = payChainCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getPayeeAddr() {
		return payeeAddr;
	}

	public void setPayeeAddr(String payeeAddr) {
		this.payeeAddr = payeeAddr;
	}

	public String getPayeeTel() {
		return payeeTel;
	}

	public void setPayeeTel(String payeeTel) {
		this.payeeTel = payeeTel;
	}

	public String getReceiptDesc() {
		return receiptDesc;
	}

	public void setReceiptDesc(String receiptDesc) {
		this.receiptDesc = receiptDesc;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayDesc() {
		return payDesc;
	}

	public void setPayDesc(String payDesc) {
		this.payDesc = payDesc;
	}

	public List<ContextPicture> getPicList() {
		return picList;
	}

	public void setPicList(List<ContextPicture> picList) {
		this.picList = picList;
	}

	public Map<String,String> toMap(){
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("payDate", FormatDate.formatDate(new Date()));
		map.put("merchantName", payeeName);
		map.put("payAmount", amount);
		map.put("payChainNo", payChainCode);
		map.put("payTransId", orderNo);
		map.put("merchantAddress", payeeAddr);
		map.put("merchantContactTel", payeeTel);
		map.put("payChainName", payChainName);
		map.put("payDescription", receiptDesc);
		map.put("payerName", payerName);
		map.put("payDemo", payDesc);
		if(picList != null && picList.size() > 0){
			ContextPicture pic = null;
			for(int i = 0;i< picList.size() ;i++){
				pic = picList.get(i);
				if(pic.getPictureStatus() != null && pic.getPictureStatus().intValue() != 2){
					HttpServletRequest request = (HttpServletRequest) RequestLocal.getRequest();
					map.put("payChainImg"+(i+1), ImgUrlUtil.getContextPath(request) + pic.getPictureUrl());
				}
			}
		}
		return map;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the overdueDate
	 */
	public Date getOverdueDate() {
		return overdueDate;
	}

	/**
	 * @param overdueDate the overdueDate to set
	 */
	public void setOverdueDate(Date overdueDate) {
		this.overdueDate = overdueDate;
	}

	public String getPayeeMemberCode() {
		return payeeMemberCode;
	}

	public void setPayeeMemberCode(String payeeMemberCode) {
		this.payeeMemberCode = payeeMemberCode;
	}

	public String getPayerEmail() {
		return payerEmail;
	}

	public void setPayerEmail(String payerEmail) {
		this.payerEmail = payerEmail;
	}
	
	
	
	
	/**
	 * @return the effectiveDate
	 */
	public Integer getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(Integer effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getEffectiveDesc(){
		EffectiveTypeEnum em =  EffectiveTypeEnum.getEffectiveEnum(effectiveDate);
		if(em!=null){
			return em.getMemo();
		}
		return null;
	}

	public String getPayChainName() {
		return payChainName;
	}

	public void setPayChainName(String payChainName) {
		this.payChainName = payChainName;
	}

	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
		
}
