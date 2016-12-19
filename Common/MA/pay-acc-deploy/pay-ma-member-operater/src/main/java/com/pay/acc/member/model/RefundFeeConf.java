/* 退款时的一些手续费配置，  类似于 WithdrawUnionBatchpayFee.java
 * author: sch 
 * modify history
 * 2016-05-07  创建  
 */
package com.pay.acc.member.model;

public class RefundFeeConf {
		
		private Long memberCode;
	
		private String refundFee;		//退款手续费
		
		private String refundFeeCurrency;	//退款收费币种
			
		private Integer refundFixedFeeConf;		//退款时，是否退固定手续费
		
		private Integer refundPerFeeConf;		//退款时，是否退百分比手续费
						
		public Long getMemberCode() {
			return memberCode;
		}
		
		public void setMemberCode(final Long memberCode) {
			this.memberCode = memberCode;
		}
		
		public String getRefundFee(){
			return refundFee;
		}
		
		public void setRefundFee(String refundFee){
			this.refundFee = refundFee;
		}
		
		public String getRefundFeeCurrency(){
			return refundFeeCurrency;
		}
		
		public void setRefundFeeCurrency(String refundFeeCurrency){
			this.refundFeeCurrency = refundFeeCurrency;
		}
		
		public Integer getRefundFixedFeeConf(){
			return refundFixedFeeConf;
		}
		
		public void setRefundFixedFeeConf(Integer refundFixedFeeConf){
			this.refundFixedFeeConf = refundFixedFeeConf;
		}
		
		public Integer getRefundPerFeeConf(){
			return refundPerFeeConf;
		}
		
		public void setRefundPerFeeConf(Integer refundPerFeeConf){
			this.refundPerFeeConf = refundPerFeeConf;
		}
		//end 2016-05-07
		
		public String toString(){
			return "RefundFeeConf [MemberCode=" + memberCode
					+ ", RefundFee=" + refundFee
					+ ", RefundFeeCurrency=" + refundFeeCurrency
					+ ", refundFixedFeeConf=" + refundFixedFeeConf 
					+ ", refundPerFeeConf=" + refundPerFeeConf +
					"]";					
		}
}
