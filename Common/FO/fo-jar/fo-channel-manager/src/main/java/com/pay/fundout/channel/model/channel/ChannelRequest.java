package com.pay.fundout.channel.model.channel;

import com.pay.inf.model.BaseObject;

/**
 * 渠道服务查询参数
 * @author Administrator
 *
 */
public class ChannelRequest extends BaseObject {

	/*
	 * 产品编号
	 */
	private String productCode;
	
	/*
	 * 目的行
	 */
	private String targetBank;
	
	/*
	 * 出款方式，0代表银企直连，1代表手工
	 */
	private Integer channelFlag;
	
	/*
	 * 金额
	 */
	private Long amount;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getTargetBank() {
		return targetBank;
	}

	public void setTargetBank(String targetBank) {
		this.targetBank = targetBank;
	}

	public Integer getChannelFlag() {
		return channelFlag;
	}

	public void setChannelFlag(Integer channelFlag) {
		this.channelFlag = channelFlag;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}
	
}
