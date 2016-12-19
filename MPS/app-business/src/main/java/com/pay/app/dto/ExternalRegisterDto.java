/**
 * 
 */
package com.pay.app.dto;

import com.pay.inf.dto.MutableDto;

/**
 * 商户注册的dto
 * @author 戴德荣
 *
 */
public class ExternalRegisterDto  implements MutableDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String originCode;//交易中心管理员商户号（交易中心商户在开通后会给出）
	private String corpName; //商户全称
	private String corpAddr; //商户经营地址
	private String bizLicenceCode; //商户营业执照号码
	private String taxCode;//商户税务登记证号码
	private String govCode;//企业机构证件号码
	private String corpEmail;//企业邮箱地址
	private String bspId; //“你的钢网 ”会员ID
	private String usteelName;//广钢账号
	private String legalName;//商户法人姓名
	private String corpLinkerName;//商户业务联系人姓名
	private String corpLinkerTel;//商户业务联系人电话
	private String bankName;//商户结算银行名称
	private String oaBankName;//具体支行名称
	private String bankAcct; //商户企业银行帐号信息
	private String signMsg;		//RSA加密摘要的密文
	private String openAccountDate;	//	string	是	开户日期
	private String contractValidity;	//string	是	合同有效期


	
	public String getCorpName() {
		return corpName;
	}
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	public String getCorpAddr() {
		return corpAddr;
	}
	public void setCorpAddr(String corpAddr) {
		this.corpAddr = corpAddr;
	}
	public String getBizLicenceCode() {
		return bizLicenceCode;
	}
	public void setBizLicenceCode(String bizLicenceCode) {
		this.bizLicenceCode = bizLicenceCode;
	}
	public String getTaxCode() {
		return taxCode;
	}
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	public String getGovCode() {
		return govCode;
	}
	public void setGovCode(String govCode) {
		this.govCode = govCode;
	}
	public String getCorpEmail() {
		return corpEmail;
	}
	public void setCorpEmail(String corpEmail) {
		this.corpEmail = corpEmail;
	}
	public String getBspId() {
		return bspId;
	}
	public void setBspId(String bspId) {
		this.bspId = bspId;
	}
	public String getLegalName() {
		return legalName;
	}
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}
	public String getCorpLinkerName() {
		return corpLinkerName;
	}
	public void setCorpLinkerName(String corpLinkerName) {
		this.corpLinkerName = corpLinkerName;
	}
	public String getCorpLinkerTel() {
		return corpLinkerTel;
	}
	public void setCorpLinkerTel(String corpLinkerTel) {
		this.corpLinkerTel = corpLinkerTel;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getOaBankName() {
		return oaBankName;
	}
	public void setOaBankName(String oaBankName) {
		this.oaBankName = oaBankName;
	}
	public String getBankAcct() {
		return bankAcct;
	}
	public void setBankAcct(String bankAcct) {
		this.bankAcct = bankAcct;
	}
	public String getOriginCode() {
		return originCode;
	}
	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}
	/**
	 * @return the usteelName
	 */
	public String getUsteelName() {
		return usteelName;
	}
	/**
	 * @param usteelName the usteelName to set
	 */
	public void setUsteelName(String usteelName) {
		this.usteelName = usteelName;
	}
	/**
	 * @return the signMsg
	 */
	public String getSignMsg() {
		return signMsg;
	}
	/**
	 * @param signMsg the signMsg to set
	 */
	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}
	/**
	 * @return the openAccountDate
	 */
	public String getOpenAccountDate() {
		return openAccountDate;
	}
	/**
	 * @param openAccountDate the openAccountDate to set
	 */
	public void setOpenAccountDate(String openAccountDate) {
		this.openAccountDate = openAccountDate;
	}
	/**
	 * @return the contractValidity
	 */
	public String getContractValidity() {
		return contractValidity;
	}
	/**
	 * @param contractValidity the contractValidity to set
	 */
	public void setContractValidity(String contractValidity) {
		this.contractValidity = contractValidity;
	}
	
	
	

}
