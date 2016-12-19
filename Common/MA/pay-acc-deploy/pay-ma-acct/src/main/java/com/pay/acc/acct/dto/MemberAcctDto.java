/**
 * 
 */
package com.pay.acc.acct.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员-当前账户 信息信息
 * @author ddr
 *
 */
public class MemberAcctDto  {
	private Long memberCode;
	private String loginName;
	private String acctCode;//账户号
	private Integer memberType;//1个人会员，2企业会员
	private Integer memberStatus;//0为创建，1正常状态（激活成功），2冻结状态，3.未激活，4删除状态 5临时账号
	private BigDecimal  balance;//当前可用金额（厘为单位）
	private Integer acctType;//10为人民币
	private BigDecimal frozenAmount;//冻结金额（厘为单位）
	private Date lastBlanceUpdateDate;//最后账户更新时间  
	
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
	public Integer getMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(Integer memberStatus) {
		this.memberStatus = memberStatus;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public Integer getAcctType() {
		return acctType;
	}
	public void setAcctType(Integer acctType) {
		this.acctType = acctType;
	}
	public BigDecimal getFrozenAmount() {
		return frozenAmount;
	}
	public void setFrozenAmount(BigDecimal frozenAmount) {
		this.frozenAmount = frozenAmount;
	}
	public Date getLastBlanceUpdateDate() {
		return lastBlanceUpdateDate;
	}
	public void setLastBlanceUpdateDate(Date lastBlanceUpdateDate) {
		this.lastBlanceUpdateDate = lastBlanceUpdateDate;
	}
	public String getAcctCode() {
		return acctCode;
	}
	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}
	
	
	
	
}
