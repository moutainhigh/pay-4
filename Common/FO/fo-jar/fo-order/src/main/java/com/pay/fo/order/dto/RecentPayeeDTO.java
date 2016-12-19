package com.pay.fo.order.dto;

import java.util.Date;

/**
 * 近期收款人
 * 
 * @author Administrator
 * 
 */
public class RecentPayeeDTO {

	private Long contactsId; // 流水号
	private Long orderId;// 订单号
	private String payeeName; // 收款人户名
	private int tradeType; // 交易类型 0 付款到个人 1 付款到企业
	private String payeeBankCode;// 收款人银行编号
	private String payeeBankName;// 收款方开户行银行名称
	private String payeeBankprovince;// 收款方开户行所属省份代码
	private String payeeBankcity;// 收款方开户行所属城市代码
	private String payeeOpeningbankname;// 收款方开户行名称
	private String payeeBankacctcode;// 收款方银行账号
	private String payeeLoginname; // 收款方登录名
	private int type; // 1、企业付款到银行 2、付款到账户
	private Long payerMembercode; // 付款方会员号
	private Date createDate; // 创建日期

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getPayerMembercode() {
		return payerMembercode;
	}

	public void setPayerMembercode(Long payerMembercode) {
		this.payerMembercode = payerMembercode;
	}

	public int getTradeType() {
		return tradeType;
	}

	public void setTradeType(int tradeType) {
		this.tradeType = tradeType;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Long getContactsId() {
		return contactsId;
	}

	public void setContactsId(Long contactsId) {
		this.contactsId = contactsId;
	}

	public String getPayeeBankCode() {
		return payeeBankCode;
	}

	public void setPayeeBankCode(String payeeBankCode) {
		this.payeeBankCode = payeeBankCode;
	}

	public String getPayeeLoginname() {
		return payeeLoginname;
	}

	public void setPayeeLoginname(String payeeLoginname) {
		this.payeeLoginname = payeeLoginname;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getPayeeBankName() {
		return payeeBankName;
	}

	public void setPayeeBankName(String payeeBankName) {
		this.payeeBankName = payeeBankName;
	}

	public String getPayeeBankprovince() {
		return payeeBankprovince;
	}

	public void setPayeeBankprovince(String payeeBankprovince) {
		this.payeeBankprovince = payeeBankprovince;
	}

	public String getPayeeBankcity() {
		return payeeBankcity;
	}

	public void setPayeeBankcity(String payeeBankcity) {
		this.payeeBankcity = payeeBankcity;
	}

	public String getPayeeOpeningbankname() {
		return payeeOpeningbankname;
	}

	public void setPayeeOpeningbankname(String payeeOpeningbankname) {
		this.payeeOpeningbankname = payeeOpeningbankname;
	}

	public String getPayeeBankacctcode() {
		return payeeBankacctcode;
	}

	public void setPayeeBankacctcode(String payeeBankacctcode) {
		this.payeeBankacctcode = payeeBankacctcode;
	}

}
