package com.pay.base.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用于存储
 * Acct 与AcctAttrib 查询结果的返回
 * @author peiyu.yang
 *
 */
public class AcctInfo implements Serializable{
	private static final long serialVersionUID = 7084197663135788956L;
	/**
	 * 账户号
	 */
	private String acctCode;
	
	/**
	 * 商户会员号
	 */
	private Long memberCode;
	
	/**
	 * 币种号
	 */
	private String curCode;
	
	/**
	 * 货币名
	 */
	private String curName;
    
	/**
	 * 账户类型
	 */
	private Integer acctType;
	
	/**
	 * 是否是基本账户
	 */
	private int isBasicAcct;
	
	/**
	 * 是否是保证金账户
	 */
	private int isFrozenAcct;
	
	/**
	 * 清算金额
	 */
	private BigDecimal settBalance;
	
	/**
	 * 未清算金额
	 */
	private BigDecimal noSettBalance;
	
	/**
	 * 结汇转金额
	 */
	private BigDecimal zhzBalance;
	/**
	 * 借方余额
	 */
	private BigDecimal creditBalance;
	
	/**
	 * 贷方余额
	 */
	private BigDecimal debitBalance;
	
	/**
	 * 可用余额
	 */
	private BigDecimal balance;
	
	/**
	 * 保证金可用余额
	 */
	private BigDecimal depBalance;
	
	/**
	 * 保证金冻结金额
	 */
	private BigDecimal depFrozenAmount;
	
	/**
	 * 账户状态
	 */
	private int status;
	
	/**
	 * 冻结资金
	 */
	private BigDecimal frozenAmount;
	
	/**
	 * 账户名称
	 */
	private String acctName;
	
	/**
	 * 状态是否允许
	 * 提现
	 */
	private int allowWithdrawal;
	/**
	 * 是否允许充值
	 */
	private int allowDeposit;
	
	/**
	 * 是否允许转入
	 */
	private int allowTransferIn;
	/**
	 * 是否允许转成
	 */
	private int allowTransferOut;
	
	private BigDecimal totalTradeAmount;
	
	public BigDecimal getTotalTradeAmount() {
		return totalTradeAmount;
	}

	public void setTotalTradeAmount(BigDecimal totalTradeAmount) {
		this.totalTradeAmount = totalTradeAmount;
	}

	public String getAcctCode() {
		return acctCode;
	}

	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getCurCode() {
		return curCode;
	}

	public void setCurCode(String curCode) {
		this.curCode = curCode;
	}

	public Integer getAcctType() {
		return acctType;
	}

	public void setAcctType(Integer acctType) {
		this.acctType = acctType;
	}

	public BigDecimal getCreditBalance() {
		return creditBalance;
	}

	public void setCreditBalance(BigDecimal creditBalance) {
		this.creditBalance = creditBalance;
	}

	public BigDecimal getDebitBalance() {
		return debitBalance;
	}

	public void setDebitBalance(BigDecimal debitBalance) {
		this.debitBalance = debitBalance;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public BigDecimal getFrozenAmount() {
		return frozenAmount;
	}

	public void setFrozenAmount(BigDecimal frozenAmount) {
		this.frozenAmount = frozenAmount;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public int getAllowWithdrawal() {
		return allowWithdrawal;
	}

	public void setAllowWithdrawal(int allowWithdrawal) {
		this.allowWithdrawal = allowWithdrawal;
	}

	public int getAllowDeposit() {
		return allowDeposit;
	}

	public void setAllowDeposit(int allowDeposit) {
		this.allowDeposit = allowDeposit;
	}

	public int getAllowTransferIn() {
		return allowTransferIn;
	}

	public void setAllowTransferIn(int allowTransferIn) {
		this.allowTransferIn = allowTransferIn;
	}

	public int getAllowTransferOut() {
		return allowTransferOut;
	}

	public void setAllowTransferOut(int allowTransferOut) {
		this.allowTransferOut = allowTransferOut;
	}

	public String getCurName() {
		return curName;
	}

	public void setCurName(String curName) {
		this.curName = curName;
	}	

	public int getIsBasicAcct() {
		return isBasicAcct;
	}

	public void setIsBasicAcct(int isBasicAcct) {
		this.isBasicAcct = isBasicAcct;
	}

	public int getIsFrozenAcct() {
		return isFrozenAcct;
	}

	public void setIsFrozenAcct(int isFrozenAcct) {
		this.isFrozenAcct = isFrozenAcct;
	}

	public BigDecimal getDepBalance() {
		return depBalance;
	}

	public void setDepBalance(BigDecimal depBalance) {
		this.depBalance = depBalance;
	}

	public BigDecimal getDepFrozenAmount() {
		return depFrozenAmount;
	}

	public void setDepFrozenAmount(BigDecimal depFrozenAmount) {
		this.depFrozenAmount = depFrozenAmount;
	}
	
	public BigDecimal getSettBalance() {
		return settBalance;
	}

	public void setSettBalance(BigDecimal settBalance) {
		this.settBalance = settBalance;
	}

	public BigDecimal getZhzBalance() {
		return zhzBalance;
	}

	public void setZhzBalance(BigDecimal zhzBalance) {
		this.zhzBalance = zhzBalance;
	}
	
	

	public BigDecimal getNoSettBalance() {
		return noSettBalance;
	}

	public void setNoSettBalance(BigDecimal noSettBalance) {
		this.noSettBalance = noSettBalance;
	}

	@Override
	public String toString() {
		return "AcctInfo [acctCode=" + acctCode + ", memberCode=" + memberCode
				+ ", curCode=" + curCode + ", curName=" + curName
				+ ", acctType=" + acctType + ", isBasicAcct=" + isBasicAcct
				+ ", isFrozenAcct=" + isFrozenAcct + ", settBalance="
				+ settBalance + ", noSettBalance=" + noSettBalance
				+ ", zhzBalance=" + zhzBalance + ", creditBalance="
				+ creditBalance + ", debitBalance=" + debitBalance
				+ ", balance=" + balance + ", depBalance=" + depBalance
				+ ", depFrozenAmount=" + depFrozenAmount + ", status=" + status
				+ ", frozenAmount=" + frozenAmount + ", acctName=" + acctName
				+ ", allowWithdrawal=" + allowWithdrawal + ", allowDeposit="
				+ allowDeposit + ", allowTransferIn=" + allowTransferIn
				+ ", allowTransferOut=" + allowTransferOut
				+ ", totalTradeAmount=" + totalTradeAmount + "]";
	}

    
}
