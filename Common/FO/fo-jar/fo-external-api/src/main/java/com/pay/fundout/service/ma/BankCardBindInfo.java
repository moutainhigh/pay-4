/**
 * 
 */
package com.pay.fundout.service.ma;



/**
 * @author NEW
 *
 */
public class BankCardBindInfo {
	  private Long 		id;//主键
	  private String  	bankId;//开户银行名称标识,外键标识
	  private String    bankName;
	  private Long 		memberCode;//会员代码
	  private Integer 	isPrimaryBankacct;//是否为主银行账户 	是否为主银行账户标识：0、非主 1、主地址;同一会员主银行账户只能有一个
	  										  //商户会员：null
	  private String  	branchBankName ;//开户行支行名称
	  private String 	name;//姓名
	  private Integer 	status; //个人会员：验证状态标识 0、未验证;1、已验证;2、验证中（未打款）	;3.验证中 ; 4 鉴权验证中
	  								//商户会员：T_LIQUIDATE_INFO： ACCOUNT_MODE  1日结，2周结，3月结
	  private String 	bankAcctId;//银行帐户
	  private Long 		province;//开户银行所在省份代码9999：代表其它省份，用户选择非中国大陆省份代码为9999';
	  private String    provinceName;
	  private Long 		city;//开户银行所在城市 9999999：代表其它城市，用户选择非中国大陆城市代码为9999999
	  private String    cityName;
	  private String    validateCode;
	  private Long      branchBankId;//分行Id
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the bankId
	 */
	public String getBankId() {
		return bankId;
	}
	/**
	 * @param bankId the bankId to set
	 */
	public void setBankId(String bankId) {
		this.bankId = bankId;
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
	 * @return the isPrimaryBankacct
	 */
	public Integer getIsPrimaryBankacct() {
		return isPrimaryBankacct;
	}
	/**
	 * @param isPrimaryBankacct the isPrimaryBankacct to set
	 */
	public void setIsPrimaryBankacct(Integer isPrimaryBankacct) {
		this.isPrimaryBankacct = isPrimaryBankacct;
	}
	/**
	 * @return the branchBankName
	 */
	public String getBranchBankName() {
		return branchBankName;
	}
	/**
	 * @param branchBankName the branchBankName to set
	 */
	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return the bankAcctId
	 */
	public String getBankAcctId() {
		return bankAcctId;
	}
	/**
	 * @param bankAcctId the bankAcctId to set
	 */
	public void setBankAcctId(String bankAcctId) {
		this.bankAcctId = bankAcctId;
	}
	/**
	 * @return the province
	 */
	public Long getProvince() {
		return province;
	}
	/**
	 * @param province the province to set
	 */
	public void setProvince(Long province) {
		this.province = province;
	}
	/**
	 * @return the city
	 */
	public Long getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(Long city) {
		this.city = city;
	}
	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}
	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	/**
	 * @return the provinceName
	 */
	public String getProvinceName() {
		return provinceName;
	}
	/**
	 * @param provinceName the provinceName to set
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public Long getBranchBankId() {
		return branchBankId;
	}
	public void setBranchBankId(Long branchBankId) {
		this.branchBankId = branchBankId;
	}
	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	
	/**
	 * @return the validateCode
	 */
	public String getValidateCode() {
		return validateCode;
	}
	/**
	 * @param validateCode the validateCode to set
	 */
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	@Override
	public String toString() {
		StringBuffer sbf  = new StringBuffer();
		sbf.append(this.getBankAcctId()).append("|");
		sbf.append(this.getBankId()).append("|");
		sbf.append(this.getBankName()).append("|");
		sbf.append(this.getBranchBankName()).append("|");
		sbf.append(this.getProvince()).append("|");
		sbf.append(this.getCity()).append("|");
		sbf.append(this.memberCode).append("|");
		sbf.append("www.pay.com").append("|");
		return sbf.toString();
	}
}
