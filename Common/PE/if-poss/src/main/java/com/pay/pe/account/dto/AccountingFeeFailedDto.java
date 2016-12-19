/**
 * 
 */
package com.pay.pe.account.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description  累计收费失败
 * Date			Author			Changes
 * 2012-3-8		DDR				Create
 */
public class AccountingFeeFailedDto {
	
	private Date createDate;  			//时间 
	private Long memberCode;   		//会员号
	private String acctName;			//账户 名
	private String serviceName;			//服务名
	private String priceStrategyName;	//策略名
	private String calcuMethod;			//计算方式名称;默认是每月计费
	private BigDecimal fee;				//扣费 ，手续费
	private String errorMsg;			//失败原因
	private String dealType;		//交易类型
	private String voucherCode; 	//v号
	
	
	
	
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public String getAcctName() {
		return acctName;
	}
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getPriceStrategyName() {
		return priceStrategyName;
	}
	public void setPriceStrategyName(String priceStrategyName) {
		this.priceStrategyName = priceStrategyName;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getCalcuMethod() {
		return calcuMethod;
	}
	public void setCalcuMethod(String calcuMethod) {
		this.calcuMethod = calcuMethod;
	}
	public String getDealType() {
		return dealType;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	public String getVoucherCode() {
		return voucherCode;
	}
	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
