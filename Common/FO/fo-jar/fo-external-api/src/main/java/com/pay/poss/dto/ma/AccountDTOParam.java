/**
 * 
 */
package com.pay.poss.dto.ma;

import java.io.Serializable;
import java.util.List;

/**
 * 更新余额相关参数
 * @author zliner
 *
 */
public class AccountDTOParam implements Serializable {
	//序号
	private static final long serialVersionUID = -1495128656375994565L;
	//更新余额参数
	private List<AccountDTO> accountDtoList;
	//财务指定的记账规则表中定制的dealCode    (算费时必须设置)
	private Long dealCode;                                                 
	//财务指定的记账规则表中定制的orderCode   (算费时必须设置)
	private Long orderCode;                               
	//支付方式,1为直接到帐,默认用1            (算费时必须设置)
	private Integer payMethod;                                                         
	//订单金额
    private Long orderAmount;                                                 
    //提交订单号
    private Long submitAcctCode;                           
    //付款方membercode                                                                                                  
    private Long payMemberCode;                                                
    //付款方acct_code              
    private String payAcctCode;                                 
    //付款方payerAcctType                                                                                               
    private Integer payAcctType;   
    //付款方Orgtype                 
    private Integer payOrgType;                                                       
    //付款方orgcode                 
    private Long payOrgCode;                                                         
    //收款方membercode                                                                                                  
    private Long revMemberCode;                                   
    //收款方acct_code              
    private String revAcctCode;                                                         
    //收款方payeeAcctType                                                                                               
    private Integer revAcctType;                                                         
    //收款方Orgtype                   
    private Integer revOrgType;                                                          
    //收款方orgcode                   
    private Long revOrgCode;
	public List<AccountDTO> getAccountDtoList() {
		return accountDtoList;
	}
	public void setAccountDtoList(List<AccountDTO> accountDtoList) {
		this.accountDtoList = accountDtoList;
	}
	public Long getDealCode() {
		return dealCode;
	}
	public void setDealCode(Long dealCode) {
		this.dealCode = dealCode;
	}
	public Long getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(Long orderCode) {
		this.orderCode = orderCode;
	}
	public Integer getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}
	public Long getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Long getSubmitAcctCode() {
		return submitAcctCode;
	}
	public void setSubmitAcctCode(Long submitAcctCode) {
		this.submitAcctCode = submitAcctCode;
	}
	public Long getPayMemberCode() {
		return payMemberCode;
	}
	public void setPayMemberCode(Long payMemberCode) {
		this.payMemberCode = payMemberCode;
	}
	public String getPayAcctCode() {
		return payAcctCode;
	}
	public void setPayAcctCode(String payAcctCode) {
		this.payAcctCode = payAcctCode;
	}
	public Integer getPayAcctType() {
		return payAcctType;
	}
	public void setPayAcctType(Integer payAcctType) {
		this.payAcctType = payAcctType;
	}
	public Integer getPayOrgType() {
		return payOrgType;
	}
	public void setPayOrgType(Integer payOrgType) {
		this.payOrgType = payOrgType;
	}
	public Long getPayOrgCode() {
		return payOrgCode;
	}
	public void setPayOrgCode(Long payOrgCode) {
		this.payOrgCode = payOrgCode;
	}
	public Long getRevMemberCode() {
		return revMemberCode;
	}
	public void setRevMemberCode(Long revMemberCode) {
		this.revMemberCode = revMemberCode;
	}
	public String getRevAcctCode() {
		return revAcctCode;
	}
	public void setRevAcctCode(String revAcctCode) {
		this.revAcctCode = revAcctCode;
	}
	public Integer getRevAcctType() {
		return revAcctType;
	}
	public void setRevAcctType(Integer revAcctType) {
		this.revAcctType = revAcctType;
	}
	public Integer getRevOrgType() {
		return revOrgType;
	}
	public void setRevOrgType(Integer revOrgType) {
		this.revOrgType = revOrgType;
	}
	public Long getRevOrgCode() {
		return revOrgCode;
	}
	public void setRevOrgCode(Long revOrgCode) {
		this.revOrgCode = revOrgCode;
	}   

}
