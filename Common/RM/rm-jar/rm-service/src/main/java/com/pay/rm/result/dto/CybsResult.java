package com.pay.rm.result.dto;

import java.io.Serializable;

public class CybsResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8770912194434666241L;
	private String  billtoCity; //下单城市
	private String 	billtoCountry; //下单国家
	private String 	billtoCustomerId; //下单客户Id
	private String  billtoDateOfBirih; //下单的时间日期
	private String 	billtoEmail; //下单的email
	private String billtoFirstName; //下单人的名字
	private String billtoLastName;
	private String billtoIPaddress; //下单人的ip地址
	private String billtoHostName; //下单人的主机名
	private String billtoCustomerPassword; //下单的 客户密码
	private String billtoHttpBrowCookieSacct; 
	private String billtoHttpBrowSertype;
	private String billtoPhoneNumber;//下单电话
	private String billtoState;
	private String billtoPostAlcode; //邮政编码
	private String billtoStreet1; //下单的 详细地址
	private String billtoStreet2; //.
	private String cardAccountNumber;
	private String cardExpirationMonth;//卡账户数
	private String cardExpirationYear;//卡到期月
	private String cardCardType;//卡的到期年
	private String cardBin;
	private String shiptoCountry;
	private String shiptostate;
	private String shiptoCity;
	private String shiptoStreet1;
	private String shiptoStreet2;
	private String shiptoPostAlCode;
	private String shiptoPhoneNumber;
	private String shiptoFirstName;
	private String shiptoLastName;
	private String shiptoshippingMethod;
	private String deviceFingerPrintId;
	private String merchantReferenceCode;//商户订单号
	private String purchaseTotalsCurrency;
	private String merchantDefinedData1; //会员号
	private String merchantDefinedData2;
	private String merchantDefinedData3;
	private String afsreplyAfsreUSED;
	private String afsreplyAfsresult;//风险评分
	private String afsreplyInternetInfoCode;
	private String requestToken;
	private String afsreplyAfsfactorCode;
	private String afsreplySuspiciousInfoCode;
	private String afsreplyDfPd;
	private String reasonCode;//cybs返回码
	private String afsreplyAddressInfoCode;
	private String afsreplyIpState;
	private String decision;//风险结果
	private String afsreplyIpCountry;
	private String afsreplyIpCity;
	private String decisionreplyCasepriority;
	private String afsreplyReasonCode;
	private String afsreplyIdentityInfoCode;
	private String requestId;
	private String afsreplyBincountry;
	private String afsreplyConsumerlocalTime;
	private String ext1;//制空excel
	private String afsreplyHostSeveRity;
	private String startDate;//查询风险订单的时间段 开始
	private String endDate;//结束
	private String createDate;//创建时间
	private String merchantDefinedData4;//交易网址
	private String merchantDefinedData6;//发卡国
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getBilltoLastName() {
		return billtoLastName;
	}
	public void setBilltoLastName(String billtoLastName) {
		this.billtoLastName = billtoLastName;
	}
	public String getCardAccountNumber() {
		return cardAccountNumber;
	}
	public void setCardAccountNumber(String cardAccountNumber) {
		this.cardAccountNumber = cardAccountNumber;
	}
	public String getAfsreplyIdentityInfoCode() {
		return afsreplyIdentityInfoCode;
	}
	public void setAfsreplyIdentityInfoCode(String afsreplyIdentityInfoCode) {
		this.afsreplyIdentityInfoCode = afsreplyIdentityInfoCode;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getMerchantDefinedData4() {
		return merchantDefinedData4;
	}
	public void setMerchantDefinedData4(String merchantDefinedData4) {
		this.merchantDefinedData4 = merchantDefinedData4;
	}
	public String getMerchantDefinedData6() {
		return merchantDefinedData6;
	}
	public void setMerchantDefinedData6(String merchantDefinedData6) {
		this.merchantDefinedData6 = merchantDefinedData6;
	}
	public String getBilltoCity() {
		return billtoCity;
	}
	public void setBilltoCity(String billtoCity) {
		this.billtoCity = billtoCity;
	}
	public String getBilltoCountry() {
		return billtoCountry;
	}
	public void setBilltoCountry(String billtoCountry) {
		this.billtoCountry = billtoCountry;
	}
	public String getBilltoCustomerId() {
		return billtoCustomerId;
	}
	public void setBilltoCustomerId(String billtoCustomerId) {
		this.billtoCustomerId = billtoCustomerId;
	}
	public String getBilltoDateOfBirih() {
		return billtoDateOfBirih;
	}
	public void setBilltoDateOfBirih(String billtoDateOfBirih) {
		this.billtoDateOfBirih = billtoDateOfBirih;
	}
	public String getBilltoEmail() {
		return billtoEmail;
	}
	public void setBilltoEmail(String billtoEmail) {
		this.billtoEmail = billtoEmail;
	}
	public String getBilltoFirstName() {
		return billtoFirstName;
	}
	public void setBilltoFirstName(String billtoFirstName) {
		this.billtoFirstName = billtoFirstName;
	}
	public String getBilltoIPaddress() {
		return billtoIPaddress;
	}
	public void setBilltoIPaddress(String billtoIPaddress) {
		this.billtoIPaddress = billtoIPaddress;
	}
	public String getBilltoHostName() {
		return billtoHostName;
	}
	public void setBilltoHostName(String billtoHostName) {
		this.billtoHostName = billtoHostName;
	}
	public String getBilltoCustomerPassword() {
		return billtoCustomerPassword;
	}
	public void setBilltoCustomerPassword(String billtoCustomerPassword) {
		this.billtoCustomerPassword = billtoCustomerPassword;
	}
	public String getBilltoHttpBrowCookieSacct() {
		return billtoHttpBrowCookieSacct;
	}
	public void setBilltoHttpBrowCookieSacct(String billtoHttpBrowCookieSacct) {
		this.billtoHttpBrowCookieSacct = billtoHttpBrowCookieSacct;
	}
	public String getBilltoHttpBrowSertype() {
		return billtoHttpBrowSertype;
	}
	public void setBilltoHttpBrowSertype(String billtoHttpBrowSertype) {
		this.billtoHttpBrowSertype = billtoHttpBrowSertype;
	}
	public String getBilltoPhoneNumber() {
		return billtoPhoneNumber;
	}
	public void setBilltoPhoneNumber(String billtoPhoneNumber) {
		this.billtoPhoneNumber = billtoPhoneNumber;
	}
	public String getBilltoState() {
		return billtoState;
	}
	public void setBilltoState(String billtoState) {
		this.billtoState = billtoState;
	}
	public String getBilltoPostAlcode() {
		return billtoPostAlcode;
	}
	public void setBilltoPostAlcode(String billtoPostAlcode) {
		this.billtoPostAlcode = billtoPostAlcode;
	}
	public String getBilltoStreet1() {
		return billtoStreet1;
	}
	public void setBilltoStreet1(String billtoStreet1) {
		this.billtoStreet1 = billtoStreet1;
	}
	public String getBilltoStreet2() {
		return billtoStreet2;
	}
	public void setBilltoStreet2(String billtoStreet2) {
		this.billtoStreet2 = billtoStreet2;
	}
	public String getCardExpirationMonth() {
		return cardExpirationMonth;
	}
	public void setCardExpirationMonth(String cardExpirationMonth) {
		this.cardExpirationMonth = cardExpirationMonth;
	}
	public String getCardExpirationYear() {
		return cardExpirationYear;
	}
	public void setCardExpirationYear(String cardExpirationYear) {
		this.cardExpirationYear = cardExpirationYear;
	}
	public String getCardCardType() {
		return cardCardType;
	}
	public void setCardCardType(String cardCardType) {
		this.cardCardType = cardCardType;
	}
	public String getCardBin() {
		return cardBin;
	}
	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}
	public String getShiptoCountry() {
		return shiptoCountry;
	}
	public void setShiptoCountry(String shiptoCountry) {
		this.shiptoCountry = shiptoCountry;
	}
	public String getShiptostate() {
		return shiptostate;
	}
	public void setShiptostate(String shiptostate) {
		this.shiptostate = shiptostate;
	}
	public String getShiptoCity() {
		return shiptoCity;
	}
	public void setShiptoCity(String shiptoCity) {
		this.shiptoCity = shiptoCity;
	}
	public String getShiptoStreet1() {
		return shiptoStreet1;
	}
	public void setShiptoStreet1(String shiptoStreet1) {
		this.shiptoStreet1 = shiptoStreet1;
	}
	public String getShiptoStreet2() {
		return shiptoStreet2;
	}
	public void setShiptoStreet2(String shiptoStreet2) {
		this.shiptoStreet2 = shiptoStreet2;
	}
	public String getShiptoPostAlCode() {
		return shiptoPostAlCode;
	}
	public void setShiptoPostAlCode(String shiptoPostAlCode) {
		this.shiptoPostAlCode = shiptoPostAlCode;
	}
	public String getShiptoPhoneNumber() {
		return shiptoPhoneNumber;
	}
	public void setShiptoPhoneNumber(String shiptoPhoneNumber) {
		this.shiptoPhoneNumber = shiptoPhoneNumber;
	}
	public String getShiptoFirstName() {
		return shiptoFirstName;
	}
	public void setShiptoFirstName(String shiptoFirstName) {
		this.shiptoFirstName = shiptoFirstName;
	}
	public String getShiptoLastName() {
		return shiptoLastName;
	}
	public void setShiptoLastName(String shiptoLastName) {
		this.shiptoLastName = shiptoLastName;
	}
	public String getShiptoshippingMethod() {
		return shiptoshippingMethod;
	}
	public void setShiptoshippingMethod(String shiptoshippingMethod) {
		this.shiptoshippingMethod = shiptoshippingMethod;
	}
	public String getDeviceFingerPrintId() {
		return deviceFingerPrintId;
	}
	public void setDeviceFingerPrintId(String deviceFingerPrintId) {
		this.deviceFingerPrintId = deviceFingerPrintId;
	}
	public String getMerchantReferenceCode() {
		return merchantReferenceCode;
	}
	public void setMerchantReferenceCode(String merchantReferenceCode) {
		this.merchantReferenceCode = merchantReferenceCode;
	}
	public String getPurchaseTotalsCurrency() {
		return purchaseTotalsCurrency;
	}
	public void setPurchaseTotalsCurrency(String purchaseTotalsCurrency) {
		this.purchaseTotalsCurrency = purchaseTotalsCurrency;
	}
	public String getMerchantDefinedData1() {
		return merchantDefinedData1;
	}
	public void setMerchantDefinedData1(String merchantDefinedData1) {
		this.merchantDefinedData1 = merchantDefinedData1;
	}
	public String getMerchantDefinedData2() {
		return merchantDefinedData2;
	}
	public void setMerchantDefinedData2(String merchantDefinedData2) {
		this.merchantDefinedData2 = merchantDefinedData2;
	}
	public String getMerchantDefinedData3() {
		return merchantDefinedData3;
	}
	public void setMerchantDefinedData3(String merchantDefinedData3) {
		this.merchantDefinedData3 = merchantDefinedData3;
	}
	public String getAfsreplyAfsreUSED() {
		return afsreplyAfsreUSED;
	}
	public void setAfsreplyAfsreUSED(String afsreplyAfsreUSED) {
		this.afsreplyAfsreUSED = afsreplyAfsreUSED;
	}
	public String getAfsreplyAfsresult() {
		return afsreplyAfsresult;
	}
	public void setAfsreplyAfsresult(String afsreplyAfsresult) {
		this.afsreplyAfsresult = afsreplyAfsresult;
	}
	public String getAfsreplyInternetInfoCode() {
		return afsreplyInternetInfoCode;
	}
	public void setAfsreplyInternetInfoCode(String afsreplyInternetInfoCode) {
		this.afsreplyInternetInfoCode = afsreplyInternetInfoCode;
	}
	public String getRequestToken() {
		return requestToken;
	}
	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}
	public String getAfsreplyAfsfactorCode() {
		return afsreplyAfsfactorCode;
	}
	public void setAfsreplyAfsfactorCode(String afsreplyAfsfactorCode) {
		this.afsreplyAfsfactorCode = afsreplyAfsfactorCode;
	}
	public String getAfsreplySuspiciousInfoCode() {
		return afsreplySuspiciousInfoCode;
	}
	public void setAfsreplySuspiciousInfoCode(String afsreplySuspiciousInfoCode) {
		this.afsreplySuspiciousInfoCode = afsreplySuspiciousInfoCode;
	}
	public String getAfsreplyDfPd() {
		return afsreplyDfPd;
	}
	public void setAfsreplyDfPd(String afsreplyDfPd) {
		this.afsreplyDfPd = afsreplyDfPd;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getAfsreplyAddressInfoCode() {
		return afsreplyAddressInfoCode;
	}
	public void setAfsreplyAddressInfoCode(String afsreplyAddressInfoCode) {
		this.afsreplyAddressInfoCode = afsreplyAddressInfoCode;
	}
	public String getAfsreplyIpState() {
		return afsreplyIpState;
	}
	public void setAfsreplyIpState(String afsreplyIpState) {
		this.afsreplyIpState = afsreplyIpState;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public String getAfsreplyIpCountry() {
		return afsreplyIpCountry;
	}
	public void setAfsreplyIpCountry(String afsreplyIpCountry) {
		this.afsreplyIpCountry = afsreplyIpCountry;
	}
	public String getAfsreplyIpCity() {
		return afsreplyIpCity;
	}
	public void setAfsreplyIpCity(String afsreplyIpCity) {
		this.afsreplyIpCity = afsreplyIpCity;
	}
	public String getDecisionreplyCasepriority() {
		return decisionreplyCasepriority;
	}
	public void setDecisionreplyCasepriority(String decisionreplyCasepriority) {
		this.decisionreplyCasepriority = decisionreplyCasepriority;
	}
	public String getAfsreplyReasonCode() {
		return afsreplyReasonCode;
	}
	public void setAfsreplyReasonCode(String afsreplyReasonCode) {
		this.afsreplyReasonCode = afsreplyReasonCode;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getAfsreplyBincountry() {
		return afsreplyBincountry;
	}
	public void setAfsreplyBincountry(String afsreplyBincountry) {
		this.afsreplyBincountry = afsreplyBincountry;
	}
	public String getAfsreplyConsumerlocalTime() {
		return afsreplyConsumerlocalTime;
	}
	public void setAfsreplyConsumerlocalTime(String afsreplyConsumerlocalTime) {
		this.afsreplyConsumerlocalTime = afsreplyConsumerlocalTime;
	}
	public String getAfsreplyHostSeveRity() {
		return afsreplyHostSeveRity;
	}
	public void setAfsreplyHostSeveRity(String afsreplyHostSeveRity) {
		this.afsreplyHostSeveRity = afsreplyHostSeveRity;
	}
}
