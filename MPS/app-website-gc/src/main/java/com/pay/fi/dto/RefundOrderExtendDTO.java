package com.pay.fi.dto;

import java.util.Date;

//退款订单的扩展信息 
public class RefundOrderExtendDTO {

	private Long refundExtendNo;		//自增长 Id;
	
	private Long refundOrderNo;

	private Long settlementOrderNo;
	
	private Long settlementFlg;
	
	private Long assureSettlementFlg;
	
	private Long settlementAmount;
	
	private Long assureSettlementAmount;
	
	private Long tradeRefundAmount;
	
	private Long fixedFeeAmount;
	
	private Long  perFeeAmount;
	
	private Long feeAmount;
	
	private String settlementCurrency;
	
	private Long baseAcctDelta;		//标价币种，  baseAcctDelta * 清算汇率 - feeDelta 才是真正扣除的钱
	
	private Long assureAcctDelta;
	
	private Long feeDelta;

	private Long feeFlg;
	
	private java.util.Date createDate;

	private Long policyVersion;		//策略版本号，=1 2,3 在处理的时候，分别有所不同
	
	private Long refundFlg;		//0=未清算 全额退款   1=未清算部分退款   2=已清算全额退款  3=已清算部分退款 
	
	private String settlementRate;
	
	private Long refundSettlementFlg;
	
	//private Long refundFeeAmount;	//退款手续费
	
	//private Long refundFeePolicy;
	
	//private String refundFeeRate;

	//public Long getRefundFeeAmount(){
	//	return refundFeeAmount;
	//}
	
	//public void setRefundFeeAmount(Long refundFeeAmount){
	//	this.refundFeeAmount = refundFeeAmount;
	//}
	
	//public Long getRefundFeePolicy(){
	//	return refundFeePolicy;
	//}
	
	//public void setRefundFeePolicy(Long refundFeePolicy){
	//	this.refundFeePolicy = refundFeePolicy;
	//}
	
	//public String getRefundFeeRate(){
	//	return refundFeeRate;
	//}
	
	//public void setRefundFeeRate(String refundFeeRate){
	//	this.refundFeeRate = refundFeeRate;
	//}
	
	public String getSettlementRate(){
		return settlementRate;
	}
	
	public void setSettlementRate(String settlementRate){
		this.settlementRate = settlementRate;
	}
	
	public Long getRefundSettlementFlg(){
		return refundSettlementFlg;
	}
	public void setRefundSettlementFlg(Long refundSettlementFlg){
		this.refundSettlementFlg = refundSettlementFlg;
	}
	
	public Long getRefundExtendNo(){
		return refundExtendNo;
	}
	
	public void setRefundExtendNo(Long refundExtendNo){
		this.refundExtendNo = refundExtendNo;
	}
	
	public Long getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(Long refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}
	
	public Long getSettlementOrderNo() {
		return settlementOrderNo;
	}

	public void setSettlementOrderNo(Long settlementOrderNo) {
		this.settlementOrderNo = settlementOrderNo;
	}
	
	public Long getSettlementFlg() {
		return settlementFlg;
	}

	public void setSettlementFlg(Long settlementFlg) {
		this.settlementFlg = settlementFlg;
	}
	
	public Long getAssureSettlementFlg() {
		return assureSettlementFlg;
	}

	public void setAssureSettlementFlg(Long assureSettlementFlg) {
		this.assureSettlementFlg = assureSettlementFlg;
	}	
	
	public Long getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(Long settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	
	public Long getAssureSettlementAmount() {
		return assureSettlementAmount;
	}

	public void setAssureSettlementAmount(Long assureSettlementAmount) {
		this.assureSettlementAmount = assureSettlementAmount;
	}
	
	public Long getTradeRefundAmount() {
		return tradeRefundAmount;
	}

	public void setTradeRefundAmount(Long tradeRefundAmount) {
		this.tradeRefundAmount = tradeRefundAmount;
	}

	public Long getFixedFeeAmount() {
		return fixedFeeAmount;
	}

	public void setFixedFeeAmount(Long fixedFeeAmount) {
		this.fixedFeeAmount = fixedFeeAmount;
	}
	
	public Long getPerFeeAmount() {
		return perFeeAmount;
	}

	public void setPerFeeAmount(Long perFeeAmount) {
		this.perFeeAmount = perFeeAmount;
	}
	
	public Long getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(Long feeAmount) {
		this.feeAmount = feeAmount;
	}
	
	public Long getBaseAcctDelta() {
		return baseAcctDelta;
	}

	public void setBaseAcctDelta(Long baseAcctDelta ) {
		this.baseAcctDelta = baseAcctDelta;
	}

	public Long getAssureAcctDelta() {
		return assureAcctDelta;
	}

	public void setAssureAcctDelta(Long assureAcctDelta) {
		this.assureAcctDelta = assureAcctDelta;
	}
	
	public String getSettlementCurrency() {
		return settlementCurrency;
	}

	public void setSettlementCurrency(String settlementCurrency) {
		this.settlementCurrency = settlementCurrency;
	}
	
	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	
	public Long getPolicyVersion(){
		return policyVersion;
	}
	
	public void setPolicyVersion(Long policyVersion){
		this.policyVersion = policyVersion;
	}
	
	public Long getRefundFlg(){
		return refundFlg;
	}
	
	public void setRefundFlg(Long refundFlg){
		this.refundFlg = refundFlg;
	}
	
	public Long getFeeDelta() {
		return feeDelta;
	}

	public void setFeeDelta(Long feeDelta) {
		this.feeDelta = feeDelta;
	}

	public Long getFeeFlg() {
		return feeFlg;
	}

	public void setFeeFlg(Long feeFlg) {
		this.feeFlg = feeFlg;
	}
	
	@Override
	public String toString() {
		
		return "RefundOrderDTO [refundOrderNo=" + refundOrderNo
				+ ", settlementOrderNo=" + settlementOrderNo + ", settlementFlg="
				+ settlementFlg + ", assureSettlementFlg=" + assureSettlementFlg
				+ ", settlementAmount=" + settlementAmount + ", assureSettlementAmount=" + assureSettlementAmount
				+ ", tradeRefundAmount=" + tradeRefundAmount + ", fixedFeeAmount=" + fixedFeeAmount
				+ ", perFeeAmount=" + perFeeAmount + ", feeAmount=" + feeAmount 
				+ ", settlementCurrency=" + settlementCurrency
				+ ", baseAcctDelta=" + baseAcctDelta + ", assureAcctDelta=" + assureAcctDelta
				+ ", createDate=" + createDate 
				+ ",policyVersion=" + policyVersion + ",refundFlg=" + refundFlg 
				+ ",settlementRate="+ settlementRate + ",refundSettlementFlg=" + refundSettlementFlg
				+ "]"; 
		
		/*
		return "insert into refund_order_extend (refund_Order_No,settlement_Order_No, settlement_Flg "+
				",assureSettlement_Flg , settlement_Amount, assureSettlement_Amount, trade_Refund_Amount , fixed_Fee_Amount"
				+ ", per_Fee_Amount, fee_Amount , settlement_Currency , base_Acct_Delta, assure_Acct_Delta, create_Date,"  
				+ "policy_Version ,refund_Flg,settlement_Rate ,refund_Settlement_Flg , refund_Fee_Amount,refund_Fee_Policy" 
				+ ",refund_Fee_Rate,refund_extend_no,fee_flg) values(" + refundOrderNo+ "," + settlementOrderNo + ","+ settlementFlg 
				+ ","+ assureSettlementFlg + ","+ settlementAmount + ","+ assureSettlementAmount+ ","+ tradeRefundAmount + ","+ fixedFeeAmount
				+ ","+ perFeeAmount + ","+ feeAmount + ","+ settlementCurrency + ","+ baseAcctDelta+ ","+ assureAcctDelta + ","+ createDate 
						+ ","+ policyVersion  + ","+ refundFlg + ","+ settlementRate + ","+ refundSettlementFlg  + "," + refundFeeAmount 
						+ ","+refundFeePolicy + ","+  refundFeeRate + ","+  feeFlg +")";
						*/
	}
}
