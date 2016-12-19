/**
 * 
 */
package com.pay.poss.dto;

import java.util.Date;

/**
 * @author Jiangbo.Peng
 *
 */
public class DayRateDTO {
	
	private Long id ;
	private String ids[] ;
	
	private String partnerId ;
	
	private String partnerName ;
	
	private Date createDate ;
	private String createDateStr ;
	
	private Date updateDate ;
	private String updateDateStr ;
	
	/** 使用标志：0停用1启用 */
	private String status ;
	
	private String operator ;
	
	private String rate ;

	
	/**
	 * 
	 */
	public DayRateDTO() {
		super();
	}

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
	 * @return the partnerId
	 */
	public String getPartnerId() {
		return partnerId;
	}

	/**
	 * @param partnerId the partnerId to set
	 */
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	/**
	 * @return the partnerName
	 */
	public String getPartnerName() {
		return partnerName;
	}

	/**
	 * @param partnerName the partnerName to set
	 */
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return the rate
	 */
	public String getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(String rate) {
		this.rate = rate;
	}

	/**
	 * @return the createDateStr
	 */
	public String getCreateDateStr() {
		return createDateStr;
	}

	/**
	 * @param createDateStr the createDateStr to set
	 */
	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	/**
	 * @return the updateDateStr
	 */
	public String getUpdateDateStr() {
		return updateDateStr;
	}

	/**
	 * @param updateDateStr the updateDateStr to set
	 */
	public void setUpdateDateStr(String updateDateStr) {
		this.updateDateStr = updateDateStr;
	}

	

	/**
	 * @return the ids
	 */
	public String[] getIds() {
		return ids;
	}

	/**
	 * @param ids the ids to set
	 */
	public void setIds(String[] ids) {
		this.ids = ids;
	}

	@Override
	public String toString() {
		return "DayRateDTO [id=" + id + ", partnerId=" + partnerId
				+ ", partnerName=" + partnerName + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + ", status=" + status
				+ ", operator=" + operator + ", rate=" + rate + "]";
	}
	
	
}
