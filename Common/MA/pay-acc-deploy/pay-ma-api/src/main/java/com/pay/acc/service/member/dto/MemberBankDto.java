/**
 * 
 */
package com.pay.acc.service.member.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author jeffrey_teng
 * 
 * @date 2010-9-23
 */
public class MemberBankDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;// 主键
	@NotNull
	private String bankId;// 开户银行名称标识,外键标识

	private String bankName;

	@NotNull
	private Long memberCode;// 会员代码
	@NotNull
	private Integer isPrimaryBankacct;// 是否为主银行账户 是否为主银行账户标识：0、非主
										// 1、主地址;同一会员主银行账户只能有一个

	private String branchBankName;// 开户行支行名称
	private Date creationDate;// 创建日期
	private String name;// 姓名
	private Integer status;// 验证状态标识 0、未验证;1、已验证;2、验证中（未打款） ;3.验证中 ; 4 鉴权验证中
	@NotNull
	@Size(min = 11, max = 19)
	private String bankAcctId;// 银行帐户
	private Long province;// 开户银行所在省份代码9999：代表其它省份，用户选择非中国大陆省份代码为9999';
	private Long city;// 开户银行所在城市 9999999：代表其它城市，用户选择非中国大陆城市代码为9999999

	private Long branchBankId;// 分行Id

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public Integer getIsPrimaryBankacct() {
		return isPrimaryBankacct;
	}

	public void setIsPrimaryBankacct(Integer isPrimaryBankacct) {
		this.isPrimaryBankacct = isPrimaryBankacct;
	}

	public String getBranchBankName() {
		return branchBankName;
	}

	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBankAcctId() {
		return bankAcctId;
	}

	public void setBankAcctId(String bankAcctId) {
		this.bankAcctId = bankAcctId;
	}

	public Long getProvince() {
		return province;
	}

	public void setProvince(Long province) {
		this.province = province;
	}

	public Long getCity() {
		return city;
	}

	public void setCity(Long city) {
		this.city = city;
	}

	public Long getBranchBankId() {
		return branchBankId;
	}

	public void setBranchBankId(Long branchBankId) {
		this.branchBankId = branchBankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

}
