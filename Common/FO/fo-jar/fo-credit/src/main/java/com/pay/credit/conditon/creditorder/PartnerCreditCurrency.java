package com.pay.credit.conditon.creditorder;

import java.util.Date;

/***
 * 授信币种配置
 * @author ddl
 *
 */
public class PartnerCreditCurrency {
	/**主键**/
	private Long id;
	/**会员号**/
	private String partnerid;
	/**币种**/
	private String currency;
	/**创建时间**/
	private Date createDate;
	/**更新时间**/
	private Date updateDate;
	/**状态**/
	private String status;
	/**操作员**/
	private String operator;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPartnerid() {
		return partnerid;
	}
	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}
