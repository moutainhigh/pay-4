/**
 * 
 */
package com.pay.acc.balancelog.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 表结构
 * FROZEN_LOG_ID	NUMBER(19)	N			主键Id
	ORDER_SEQ_NO	VARCHAR2(128)	Y			订单流水号
	FROZEN_TYPE	NUMBER(1)	N			操作类型(1 冻结 2 解冻)
	MEMBER_CODE	NUMBER(11)	Y			被冻结会员号
	ACCT_CODE	VARCHAR2(32)	N			账户号
	ACCT_TYPE	NUMBER(3)	N	10		账户类型 10 人民币
	AMOUNT	NUMBER(19)	Y			发生金额（厘）
	CREATE_DATE	DATE	N	sysdate		创建时间
	BALANCE	NUMBER(19)	N			冻结或解冻时 可用余额
	VALUE1	VARCHAR2(256)	Y			备用字段
	VALUE2	VARCHAR2(256)	Y			备用字段
 * @author ddr
 *
 */
public class FrozenOperatorLog {

	public static final int FROZEN_TYPE_FROZEN = 1;
	public static final int FROZEN_TYPE_UNFROZEN = 2;
	private Long frozenLogId;	//NUMBER(19)	N			主键Id
	private String orderSeqNo;	// 	VARCHAR2(128)	Y			订单流水号
	private Integer frozenType;//	NUMBER(1)	N			操作类型(1 冻结 2 解冻)
	private Long  memberCode;//	NUMBER(11)	Y			被冻结会员号
	private String  acctCode;//	VARCHAR2(32)	N			账户号
	private Integer acctType;//	NUMBER(3)	N	10		账户类型 10 人民币
	private BigDecimal amount;	//	NUMBER(19)	Y			发生金额（厘）
	private Date createDate		;//DATE	N	sysdate		创建时间
	private BigDecimal balance;	//	NUMBER(19)	N			冻结或解冻时 可用余额
	private String value1	;//VARCHAR2(256)	Y			备用字段
	private String value2	;//VARCHAR2(256)	Y			备用字段
	public Long getFrozenLogId() {
		return frozenLogId;
	}
	/**
	 * 主键
	 * @param frozenLogId
	 */
	public void setFrozenLogId(Long frozenLogId) {
		this.frozenLogId = frozenLogId;
	}
	public String getOrderSeqNo() {
		return orderSeqNo;
	}
	/**
	 * 申请冻结生成的流水号
	 * @param orderSeqNo
	 */
	public void setOrderSeqNo(String orderSeqNo) {
		this.orderSeqNo = orderSeqNo;
	}
	public Integer getFrozenType() {
		return frozenType;
	}
	/**
	 * 类型
	 * 操作类型(1 冻结 2 解冻)
	 * @see#FrozenOperatorLog.FROZEN_TYPE_FROZEN 冻结
	 * @see#FrozenOperatorLog.FROZEN_TYPE_UNFROZEN 解冻
	 * @param frozenType
	 */
	public void setFrozenType(Integer frozenType) {
		this.frozenType = frozenType;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	/**
	 * 要冻结的用户会员号
	 * @param memberCode
	 */
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public String getAcctCode() {
		return acctCode;
	}
	/**
	 * 用户冻结钱的账户号
	 * @param acctCode
	 */
	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}
	public Integer getAcctType() {
		return acctType;
	}
	/**
	 * 默认是10人民币账户
	 * @param acctType
	 */
	public void setAcctType(Integer acctType) {
		this.acctType = acctType;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	/**
	 * 金额以厘为单位
	 * @param amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}
	/**
	 * 冻结时账户可用额度
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	
	
	
}
