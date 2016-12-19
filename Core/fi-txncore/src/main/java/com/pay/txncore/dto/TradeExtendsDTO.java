package com.pay.txncore.dto;

import java.io.Serializable;

public class TradeExtendsDTO implements Serializable {

	private static final long serialVersionUID = 6358263726336920697L;
	private Long tradeExtendsNo;
	private Long tradeOrderNo;

	private String cardHolderAcct;
	private String cardHolderName;
	private String cardHolderCertType;
	private String cardHolderCertNo;
	private String cardHolderCardNo;
	private String cardHolderMobile;

	private String extOrderInfo3;
	private String extOrderInfo8;
	private String extOrderInfo9;
	
	private String extOrderInfo1;
	private String extOrderInfo2;
	private String extOrderInfo4;
	private String extOrderInfo5;
	private String extOrderInfo6;
	private String extOrderInfo7;
	private String extOrderInfo10;
	private String ordersIp;
	private String remark1;
	private String remark2;
	private String registerUserId;

 
	private String idCardNo;
	
	public String getRegisterUserId() {
		return registerUserId;
	}

	public void setRegisterUserId(String registerUserId) {
		this.registerUserId = registerUserId;
	}

	public Long getTradeExtendsNo() {
		return tradeExtendsNo;
	}

	public void setTradeExtendsNo(Long tradeExtendsNo) {
		this.tradeExtendsNo = tradeExtendsNo;
	}

	public Long getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(Long tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public String getExtOrderInfo3() {
		return extOrderInfo3;
	}

	public void setExtOrderInfo3(String extOrderInfo3) {
		this.extOrderInfo3 = extOrderInfo3;
	}

	public String getExtOrderInfo8() {
		return extOrderInfo8;
	}

	public String getExtOrderInfo9() {
		return extOrderInfo9;
	}

	public void setExtOrderInfo9(String extOrderInfo9) {
		this.extOrderInfo9 = extOrderInfo9;
	}

	public void setExtOrderInfo8(String extOrderInfo8) {
		this.extOrderInfo8 = extOrderInfo8;
	}

	public String getCardHolderAcct() {
		return cardHolderAcct;
	}

	public void setCardHolderAcct(String cardHolderAcct) {
		this.cardHolderAcct = cardHolderAcct;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getCardHolderCertType() {
		return cardHolderCertType;
	}

	public void setCardHolderCertType(String cardHolderCertType) {
		this.cardHolderCertType = cardHolderCertType;
	}

	public String getCardHolderCertNo() {
		return cardHolderCertNo;
	}

	public void setCardHolderCertNo(String cardHolderCertNo) {
		this.cardHolderCertNo = cardHolderCertNo;
	}

	public String getCardHolderCardNo() {
		return cardHolderCardNo;
	}

	public void setCardHolderCardNo(String cardHolderCardNo) {
		this.cardHolderCardNo = cardHolderCardNo;
	}

	public String getCardHolderMobile() {
		return cardHolderMobile;
	}

	public void setCardHolderMobile(String cardHolderMobile) {
		this.cardHolderMobile = cardHolderMobile;
	}

	public String getExtOrderInfo1() {
		return extOrderInfo1;
	}

	public void setExtOrderInfo1(String extOrderInfo1) {
		this.extOrderInfo1 = extOrderInfo1;
	}

	public String getExtOrderInfo2() {
		return extOrderInfo2;
	}

	public void setExtOrderInfo2(String extOrderInfo2) {
		this.extOrderInfo2 = extOrderInfo2;
	}

	public String getExtOrderInfo4() {
		return extOrderInfo4;
	}

	public void setExtOrderInfo4(String extOrderInfo4) {
		this.extOrderInfo4 = extOrderInfo4;
	}

	public String getExtOrderInfo5() {
		return extOrderInfo5;
	}

	public void setExtOrderInfo5(String extOrderInfo5) {
		this.extOrderInfo5 = extOrderInfo5;
	}

	public String getExtOrderInfo6() {
		return extOrderInfo6;
	}

	public void setExtOrderInfo6(String extOrderInfo6) {
		this.extOrderInfo6 = extOrderInfo6;
	}

	public String getExtOrderInfo7() {
		return extOrderInfo7;
	}

	public void setExtOrderInfo7(String extOrderInfo7) {
		this.extOrderInfo7 = extOrderInfo7;
	}

	public String getExtOrderInfo10() {
		return extOrderInfo10;
	}

	public void setExtOrderInfo10(String extOrderInfo10) {
		this.extOrderInfo10 = extOrderInfo10;
	}

	public String getOrdersIp() {
		return ordersIp;
	}

	public void setOrdersIp(String ordersIp) {
		this.ordersIp = ordersIp;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
	

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	@Override
	public String toString() {
		return "TradeExtendsDTO [tradeExtendsNo=" + tradeExtendsNo
				+ ", tradeOrderNo=" + tradeOrderNo + ", cardHolderAcct="
				+ cardHolderAcct + ", cardHolderName=" + cardHolderName
				+ ", cardHolderCertType=" + cardHolderCertType
				+ ", cardHolderCertNo=" + cardHolderCertNo
				+ ", cardHolderCardNo=" + cardHolderCardNo
				+ ", cardHolderMobile=" + cardHolderMobile + ", extOrderInfo3="
				+ extOrderInfo3 + ", extOrderInfo8=" + extOrderInfo8
				+ ", extOrderInfo9=" + extOrderInfo9 + ", extOrderInfo1="
				+ extOrderInfo1 + ", extOrderInfo2=" + extOrderInfo2
				+ ", extOrderInfo4=" + extOrderInfo4 + ", extOrderInfo5="
				+ extOrderInfo5 + ", extOrderInfo6=" + extOrderInfo6
				+ ", extOrderInfo7=" + extOrderInfo7 + ", extOrderInfo10="
				+ extOrderInfo10 + ", ordersIp=" + ordersIp + ", remark1="
				+ remark1 + ", remark2=" + remark2 + "]";
	}
}
