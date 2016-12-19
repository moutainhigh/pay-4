package com.pay.txncore.model;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 交易查询统计
 * @author peiyu.yang
 *
 */
public class TradeOrderCount implements Serializable{
	private static final long serialVersionUID = -933807074211962876L;
    
	private String amount;
	
	private String curencyCode;

    

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurencyCode() {
		return curencyCode;
	}

	public void setCurencyCode(String curencyCode) {
		this.curencyCode = curencyCode;
	}

	@Override
	public String toString() {
		return "TradeOrderCount [amount=" + amount + ", curencyCode="
				+ curencyCode + "]";
	}

        
	
}
