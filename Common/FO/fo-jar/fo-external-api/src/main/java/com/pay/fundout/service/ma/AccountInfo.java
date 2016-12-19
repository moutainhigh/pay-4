/**
 * 
 */
package com.pay.fundout.service.ma;

/**
 * @author NEW
 *
 */
public class AccountInfo {
	/**
	 * 账号
	 */
	private String acctCode;
	/**
	 * 货币类型 CNY 人民币,USD 美元,JPY 日元,EUR 欧元
	 */
	 private String curCode;
	/**
	 * 允许充值 0不允许 1是正常
	 */
    private Integer allowDeposit;
    /**
     * 允许提现 0不允许 1是正常
     */
    private Integer allowWithdrawal;
    /**
     * 允许转账入 0不允许 1是正常
     */
    private Integer allowTransferIn;
    /**
     * 允许转账出 0不允许 1是正常
     */
    private Integer allowTransferOut;
    /**
     * 是否止入 0是止入 1未止入
     */
    private Integer allowIn;
    /**
     * 是否止出 0是止出 1未止出
     */
    private Integer allowOut;
    /**
     * 是否冻结 0是冻结 1未冻结
     */
    private Integer frozen;
    /**
     * 会员号
     */
    private Long memberCode;
    /**
     * 允许支付0-否;1-是
     */
    private Integer payAble;
    /**
     * 允许透支0-否;1-是
     */
    private Integer allowOverdraft;
	/**
	 * @return the acctCode
	 */
	public String getAcctCode() {
		return acctCode;
	}
	/**
	 * @param acctCode the acctCode to set
	 */
	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}
	/**
	 * @return the curCode
	 */
	public String getCurCode() {
		return curCode;
	}
	/**
	 * @param curCode the curCode to set
	 */
	public void setCurCode(String curCode) {
		this.curCode = curCode;
	}
	/**
	 * @return the allowDeposit
	 */
	public Integer getAllowDeposit() {
		return allowDeposit;
	}
	/**
	 * @param allowDeposit the allowDeposit to set
	 */
	public void setAllowDeposit(Integer allowDeposit) {
		this.allowDeposit = allowDeposit;
	}
	/**
	 * @return the allowWithdrawal
	 */
	public Integer getAllowWithdrawal() {
		return allowWithdrawal;
	}
	/**
	 * @param allowWithdrawal the allowWithdrawal to set
	 */
	public void setAllowWithdrawal(Integer allowWithdrawal) {
		this.allowWithdrawal = allowWithdrawal;
	}
	/**
	 * @return the allowTransferIn
	 */
	public Integer getAllowTransferIn() {
		return allowTransferIn;
	}
	/**
	 * @param allowTransferIn the allowTransferIn to set
	 */
	public void setAllowTransferIn(Integer allowTransferIn) {
		this.allowTransferIn = allowTransferIn;
	}
	/**
	 * @return the allowTransferOut
	 */
	public Integer getAllowTransferOut() {
		return allowTransferOut;
	}
	/**
	 * @param allowTransferOut the allowTransferOut to set
	 */
	public void setAllowTransferOut(Integer allowTransferOut) {
		this.allowTransferOut = allowTransferOut;
	}
	/**
	 * @return the allowIn
	 */
	public Integer getAllowIn() {
		return allowIn;
	}
	/**
	 * @param allowIn the allowIn to set
	 */
	public void setAllowIn(Integer allowIn) {
		this.allowIn = allowIn;
	}
	/**
	 * @return the allowOut
	 */
	public Integer getAllowOut() {
		return allowOut;
	}
	/**
	 * @param allowOut the allowOut to set
	 */
	public void setAllowOut(Integer allowOut) {
		this.allowOut = allowOut;
	}
	/**
	 * @return the frozen
	 */
	public Integer getFrozen() {
		return frozen;
	}
	/**
	 * @param frozen the frozen to set
	 */
	public void setFrozen(Integer frozen) {
		this.frozen = frozen;
	}
	/**
	 * @return the memberCode
	 */
	public Long getMemberCode() {
		return memberCode;
	}
	/**
	 * @param memberCode the memberCode to set
	 */
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	/**
	 * @return the payAble
	 */
	public Integer getPayAble() {
		return payAble;
	}
	/**
	 * @param payAble the payAble to set
	 */
	public void setPayAble(Integer payAble) {
		this.payAble = payAble;
	}
	/**
	 * @return the allowOverdraft
	 */
	public Integer getAllowOverdraft() {
		return allowOverdraft;
	}
	/**
	 * @param allowOverdraft the allowOverdraft to set
	 */
	public void setAllowOverdraft(Integer allowOverdraft) {
		this.allowOverdraft = allowOverdraft;
	}
    
    
}
