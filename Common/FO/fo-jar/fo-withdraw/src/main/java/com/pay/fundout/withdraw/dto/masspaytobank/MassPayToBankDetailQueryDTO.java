/**
 *  File: MassPayToBankDetailQueryDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-9     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dto.masspaytobank;

import java.text.DecimalFormat;

import com.pay.fundout.util.AmountUtils;
import com.pay.inf.model.BaseObject;

/**
 * @author darv
 * 
 */
public class MassPayToBankDetailQueryDTO extends BaseObject {
	private String businessOrderId; // 商家订单号

	/**
	 * 错误描叙
	 */
	private String errorMessage;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 城市名称
	 */
	private String cityName;

	/**
	 * 省份代码
	 */
	private Integer provinceCode;

	/**
	 * 城市代码
	 */
	private Integer cityCode;

	/**
	 * 银行
	 */
	private String bankName;

	/**
	 * 省份
	 */
	private String provinceName;

	/**
	 * 上传状态是否有效
	 */
	private Integer validateStatus;

	/**
	 * 流水
	 */
	private Long detailSeq;

	/**
	 * 银行代码
	 */
	private String bankCode;

	/**
	 * 金额
	 */
	private Long amount;

	/**
	 * 上传金额
	 */
	private String uploadAmount;

	/**
	 * 收款人姓名
	 */
	private String payeeName;

	/**
	 * 上传汇总ID
	 */
	private Long uploadSeq;

	/**
	 * 开户行
	 */
	private String openingBankName;

	/**
	 * 银行账号
	 */
	private String payeeBankAcct;
	
	private Long fee;
	private Integer orderStatus;
	private String tradeType;

	private DecimalFormat format = new DecimalFormat("0.00");

	public String getBusinessOrderId() {
		return businessOrderId;
	}

	public void setBusinessOrderId(String businessOrderId) {
		this.businessOrderId = businessOrderId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(Integer provinceCode) {
		this.provinceCode = provinceCode;
	}

	public Integer getCityCode() {
		return cityCode;
	}

	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Integer getValidateStatus() {
		return validateStatus;
	}

	public void setValidateStatus(Integer validateStatus) {
		this.validateStatus = validateStatus;
	}

	public Long getDetailSeq() {
		return detailSeq;
	}

	public void setDetailSeq(Long detailSeq) {
		this.detailSeq = detailSeq;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public Long getUploadSeq() {
		return uploadSeq;
	}

	public void setUploadSeq(Long uploadSeq) {
		this.uploadSeq = uploadSeq;
	}

	public String getOpeningBankName() {
		return openingBankName;
	}

	public void setOpeningBankName(String openingBankName) {
		this.openingBankName = openingBankName;
	}

	public String getPayeeBankAcct() {
		return payeeBankAcct;
	}

	public void setPayeeBankAcct(String payeeBankAcct) {
		this.payeeBankAcct = payeeBankAcct;
	}

	public String getUploadAmount() {
		return uploadAmount;
	}

	public void setUploadAmount(String uploadAmount) {
		this.uploadAmount = uploadAmount;
	}

	public String getAmountStr() {
		return AmountUtils.numberFormat(amount);
	}

	public Long getFee() {
		return fee;
	}

	public void setFee(Long fee) {
		this.fee = fee;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	
	
}
