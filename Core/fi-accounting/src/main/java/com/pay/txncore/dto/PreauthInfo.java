/**
 * 
 */
package com.pay.txncore.dto;

/**
 * 预授权订单对象
 * @author chaoyue
 *
 */
public class PreauthInfo extends PaymentInfo{
	
	private String dealId;

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
    
	
}
