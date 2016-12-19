/**
 * 
 */
package com.pay.acc.service.member.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 实名认证
 * 
 * @author jeffrey_teng
 * 
 * @date 2010-9-23
 */
public class MemberVerifyDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id; // idcVerifyBaseId 主键
	private Long memberCode; // 会员号
	private String name; // 真实姓名
	private String paperNo; // 证件号码
	private String linkNo; // 联系号码，手机或者电话号码
	private Integer verifyFlag; // 认证类型1公安网，2银行认证
	private Integer paperType; // 证件类型 1 身份证
	private Integer isPaperFile; // 是否上传文件 Y 是 N 否
	private String photo; // 照片字符串
	private Integer status; // 是否认证成功，0表示失败，1成功
	private String errorCode; // 错误代码
	private String errorMsg; // 错误信息
	private Date createdDate; // 创建时间

	// 银行
	private String bankAcctId;// 银行卡号
	private String bankId;// 银行代码
	private String branchBankName;// 支行名称
	private Long branchBankId;// 支行代码
	private Long province;// 所在省
	private Long city;// 所在城市

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPaperNo() {
		return paperNo;
	}

	public void setPaperNo(String paperNo) {
		this.paperNo = paperNo;
	}

	public String getLinkNo() {
		return linkNo;
	}

	public void setLinkNo(String linkNo) {
		this.linkNo = linkNo;
	}

	public Integer getVerifyFlag() {
		return verifyFlag;
	}

	public void setVerifyFlag(Integer verifyFlag) {
		this.verifyFlag = verifyFlag;
	}

	public Integer getPaperType() {
		return paperType;
	}

	public void setPaperType(Integer paperType) {
		this.paperType = paperType;
	}

	public Integer getIsPaperFile() {
		return isPaperFile;
	}

	public void setIsPaperFile(Integer isPaperFile) {
		this.isPaperFile = isPaperFile;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getBankAcctId() {
		return bankAcctId;
	}

	public void setBankAcctId(String bankAcctId) {
		this.bankAcctId = bankAcctId;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBranchBankName() {
		return branchBankName;
	}

	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}

	public Long getBranchBankId() {
		return branchBankId;
	}

	public void setBranchBankId(Long branchBankId) {
		this.branchBankId = branchBankId;
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

}
