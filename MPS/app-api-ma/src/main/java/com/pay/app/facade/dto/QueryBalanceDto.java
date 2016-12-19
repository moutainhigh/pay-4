package com.pay.app.facade.dto;

import java.math.BigDecimal;



/**
 * @author lei.jiangl 
 * @version 
 * @data 2010-7-28 下午04:07:51
 * 账户余额明细DTO
 */
public class QueryBalanceDto {
	//日期，支付流水号，资金流向（付款、收款，充值，充退、提现），收入分、支出分，余额分	
	
	/**
	 * 时间
	 */
	private String balanceDate;
	/**
	 * 支付流水号
	 */
	private String  payNo;
	/**
	 * 资金流向
	 */
	private String fundTrace;
	
	/**
	 * 交易类型
	 */
	private int fundTraceType;
	
	/**
	 * 收入
	 */
	private BigDecimal revenue;
	
	/**
	 *支出 
	 */
	private BigDecimal pay;
	
	
	/**
	 * 余额
	 */
	private BigDecimal balance;
	
	
	/**
	 * 收入币种
	 */
	private String revenueCode;
	/**
	 * 支出币种
	 */
	private String payCode;
	/**
	 * 余额对应币种
	 */
	private String balanceCurCode;
	
	/**
	 * 费用
	 */
	private BigDecimal fee;
	
	/**
	 *备注 
	 */
	private String remark;
	/**
	 * 商户订单号
	 */
	private String merchantOrderId;
	
	
	

	public String getRevenueCode() {
		return revenueCode;
	}

	public void setRevenueCode(String revenueCode) {
		this.revenueCode = revenueCode;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public String getBalanceCurCode() {
		return balanceCurCode;
	}

	public void setBalanceCurCode(String balanceCurCode) {
		this.balanceCurCode = balanceCurCode;
	}

	public String getMerchantOrderId() {
		return merchantOrderId;
	}

	public void setMerchantOrderId(String merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	
	public String getBalanceDate() {
		return balanceDate;
	}
	public void setBalanceDate(String balanceDate) {
		this.balanceDate = balanceDate;
	}
	public String getPayNo() {
		return payNo;
	}
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}
	public String getFundTrace() {
		return fundTrace;
	}
	public void setFundTrace(String fundTrace) {
		this.fundTrace = fundTrace;
	}
	public BigDecimal getRevenue() {
		return revenue;
	}
	public void setRevenue(BigDecimal revenue) {
		this.revenue = revenue;
	}
	public BigDecimal getPay() {
		return pay;
	}
	public void setPay(BigDecimal pay) {
		this.pay = pay;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	/**
	 * @return the fundTraceType
	 */
	public int getFundTraceType() {
		return fundTraceType;
	}
	/**
	 * @param fundTraceType the fundTraceType to set
	 */
	public void setFundTraceType(int fundTraceType) {
		this.fundTraceType = fundTraceType;
	}
	
	/**
	 * 企业交易是否有查看页面
	 * @return
	 */
	public boolean isCorpHaveView(){
		boolean haveView = true;
		
		switch (fundTraceType) {
		case 109:
			//交易退款
		case 110:
			//对账
		case 111:
			//充值退款
		case 34:
			//手续费用
		case 300:
			//手工账
		case 32:
			//金额冻结
		case 33:
			//金额解冻
		case 204:
			//付款到银行退票
		case 207:
			//批量付款到银行退票
		case 211:
			//提现退票
		case 214:
			//银行代扣
		case 215:
			//银行代扣退款
		case 216:
			//银行代扣退票
			haveView = false;
			break;
			
		case 104:
			//B2C网关支付
		case 105:
			//B2B网关支付
		case 106:
			//大额网关支付
		//case 107:
			//账户支付
		case 108:
			//易卡支付
		case 120:
		  //信用卡大额支付
		  
			//支付如果是支出方不显示查看
			double payd = pay == null ? 0 : pay.doubleValue();
			haveView = payd > 0 ? false : true;
			break;	
		case 118://支付链收款记录
			haveView = false;
			break;
		default:
			haveView = true;
			break;
		}
		
		return haveView;
	}
	
	/**
	 * 企业收支汇总查看方式
	 * @return
	 */
	public String getCorpViewFlag(){
		String flag = "";
		
		switch (fundTraceType) {
		case 100:
			//B2C网关充值
		case 101:
			//B2B网关充值
		case 102:
			//大额网关充值
		case 103:
			//易卡充值
		case 104:
			//B2C网关支付
		case 105:
			//B2B网关支付
		case 106:
			//大额网关支付
		case 107:
			//账户支付
		case 108:
			//易卡支付
		case 119:
			//信用卡大额充值
		case 401:
			//预付卡充值
		case 402:
			//预付卡消费
		case 501:
			//分账
		case 502:
			//分账退款
		case 601:
			//神州行网关充值
		case 602:
			//神舟行交易
		case 603:
			//销卡渠道损益记账
		case 120:
			//信用卡大额支付
			flag = "fi";
			break;
		default:
			flag = "fo";
			break;
		}
		return flag;
	}
	
	/**
	 * 个人交易是否有查看页面
	 * @return
	 */
	public boolean isAppHaveView(){
		boolean haveView = false;
		
		switch (fundTraceType) {
		case 100:
			//B2C网关充值
		case 101:
			//B2B网关充值
		case 102:
			//大额网关充值
		case 103:
			//易卡充值
			
		case 104:
			//B2C网关支付
		case 105:
			//B2B网关支付
		case 106:
			//大额网关支付
		case 107:
			//账户支付
		case 108:
			//易卡支付
		case 200:
			//提现
		case 201:
			//提现退款
		case 202:
			//付款到银行
		case 203:
			//付款到银行退款
		case 208:
			//单笔付款到账户
		case 119:
			//信用卡大额充值
		case 120:
			//信用卡大额支付
		case 401:
			//预付卡充值
		case 402:
			//预付卡消费	
			haveView = true;
			break;
		default:
			haveView = false;
			break;
		}
		return haveView;
	}
	
	/**
	 * 个人收支汇总明细查看方式
	 * @return
	 */
	public String getAppViewFlag(){
		String flag = "";
		
		switch (fundTraceType) {
		case 100:
			//B2C网关充值
		case 101:
			//B2B网关充值
		case 102:
			//大额网关充值
		case 103:
			//易卡充值
		case 104:
			//B2C网关支付
		case 105:
			//B2B网关支付
		case 106:
			//大额网关支付
		case 107:
			//账户支付
		case 108:
			//易卡支付
		case 119:
			//信用卡大额充值
		case 120:
			//信用卡大额支付
		case 401:
			//预付卡充值
		case 402:
			//预付卡消费	
		case 601:
			//神州行网关充值
		case 602:
			//神舟行交易
		case 603:
			//销卡渠道损益记账
			flag = "fi";
			break;
		case 200:
			//提现
		case 201:
			//提现退款
		case 202:
			//付款到银行
		case 203:
			//付款到银行退款
		case 208:
			//单笔付款到账户
			flag = "fo";
			break;
		default:
			
			break;
		}
		return flag;
	}
	
	/**
	 * 
	 * bsp 交易类型名称
	 *
	 * @return the fundTraceName
	 */
	public String getFundTraceName() {
		String fundTraceName = "";
		switch (fundTraceType) {
		case 100:
		case 101:
		default:
			fundTraceName = fundTrace;
			break;
		}
		return fundTraceName;
	}
	
	/**
	 * BSP 商户是否有查看页面
	 * @return
	 */
	public boolean isBspHaveView(){
		boolean haveView = false;
		switch (fundTraceType) {
		
		case 100:
		case 101:
		case 102:
			//入金(B2C网关充值,B2B网关充值,FI_BIGINCOME)
			haveView = true;
			break;
		case 112:
			//担保交易
			haveView = true;
			break;
		case 200:
			//出金（提现）
		case 201:
			//出金退款(提现退款)
			haveView = true;
			break;
		case 212:
			//资金调拨
			haveView = true;
			break;
		case 213:
			//资金调拨退款
			haveView = true;
			break;
			
		default:
			haveView = false;
			break;
		}
		return haveView;
	}
	
	
	/**
	 * @return
	 */
	public String getBspViewFlag(){
		String flag = "";
		
		switch (fundTraceType) {
		
		case 100:
		case 101:
		case 102:
			//入金(B2C网关充值,B2B网关充值,FI_BIGINCOME)
			flag = "fi";
			break;
		case 112:
			//担保交易
			flag = "fi";
			break;
		case 200:
			//出金（提现）
		case 201:
			//出金退款(提现退款)
			flag = "fo_200";
			break;
		case 212:
			//资金调拨
		case 213:
			//资金调拨退款
			flag = "fo_212";
			break;
			
		default:
			flag = "";
			break;
		}
		return flag;
	}
	
}
