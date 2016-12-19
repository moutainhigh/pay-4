package com.pay.poss.personmanager.dto;

import java.io.Serializable;
import java.sql.Date;

public class PersonalAcctInfoSearchDto implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private String  memberCode;
	private String  acctCode; 			//账户编号
	private String  typeName;     		//账户名称
	
	private String  status;   			//账户状态
	private String  balance; 			//可用余额
	private String  curCode;            //货币类型
	
	private String  frozen; 			//冻结
	private String  allowTransferIn; 	//转账入
	private String  allowTransferOut; 	//转账出
	private String  allowIn; 			//止入
	private String  allowOut; 			//止出
	private String  createDate; 		//创建时间
	private Date  orderDate; 		    //创建时间 
	private String userName;            //个人会员名称
	
	
	private String loginName;            //用户登录名
	private String type;                //账户类型
	private String frozenAmount;        //冻结余额
	private String debitBalance;        //收入金额
	private String creditBalance;       //支出金额
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	private Integer pageEndRow;			// 结束行
	private Integer pageStartRow;		// 起始行

	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getCurCode() {
		return curCode;
	}
	public void setCurCode(String curCode) {
		this.curCode = curCode;
	}
	public String getAcctCode() {
		return acctCode;
	}
	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getFrozen() {
		return frozen;
	}
	public void setFrozen(String frozen) {
		this.frozen = frozen;
	}
	public String getAllowTransferIn() {
		return allowTransferIn;
	}
	public void setAllowTransferIn(String allowTransferIn) {
		this.allowTransferIn = allowTransferIn;
	}
	public String getAllowTransferOut() {
		return allowTransferOut;
	}
	public void setAllowTransferOut(String allowTransferOut) {
		this.allowTransferOut = allowTransferOut;
	}
	public String getAllowIn() {
		return allowIn;
	}
	public void setAllowIn(String allowIn) {
		this.allowIn = allowIn;
	}
	public String getAllowOut() {
		return allowOut;
	}
	public void setAllowOut(String allowOut) {
		this.allowOut = allowOut;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Integer getPageEndRow() {
		return pageEndRow;
	}
	public void setPageEndRow(Integer pageEndRow) {
		this.pageEndRow = pageEndRow;
	}
	public Integer getPageStartRow() {
		return pageStartRow;
	}
	public void setPageStartRow(Integer pageStartRow) {
		this.pageStartRow = pageStartRow;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFrozenAmount() {
		return frozenAmount;
	}
	public void setFrozenAmount(String frozenAmount) {
		this.frozenAmount = frozenAmount;
	}
	public String getDebitBalance() {
		return debitBalance;
	}
	public void setDebitBalance(String debitBalance) {
		this.debitBalance = debitBalance;
	}
	public String getCreditBalance() {
		return creditBalance;
	}
	public void setCreditBalance(String creditBalance) {
		this.creditBalance = creditBalance;
	}
}
