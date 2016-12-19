package com.pay.pe.manualbooking.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 手工记帐申请域模型
 */
//implements Model
public class VouchData {
	/**
	 * 申请号
	 */
	private String applicationCode;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 审核日期
	 */
	private Date auditDate;
	
	/**
	 * 记账日期
	 */
	private Date accountingDate;
	
	/**
	 * 创建人
	 */
	private String creator;
	
	/**
	 * 凭证号
	 */
	private String vouchCode;
	
	/**
	 * 创建日期
	 */
	private Date createDate;
	
	/**
	 * 审核人
	 */
	private String auditor;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 物理主键
	 */
	private Long vouchDataId;
	
	private BigDecimal amount;
	
	/*
	 * 摘要
	 */
	
	
	

	/**
	 * 版本标识，乐观锁
	 */
	private Long version;
	
	/**
	 * 明细列表
	 */
	private List<VouchDetailData> vouchDetails;
	
	private Date startTime;//起始日期
	
	private Date endTime ;//终止日期
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	private static List<String> pk = new ArrayList<String>(1);
	static {
		pk.add("vouchDataId");
	}
	
	public VouchData() {
		super();
		vouchDetails = new ArrayList<VouchDetailData>();
	}

	public Object getPrimaryKey() {
		return new Object[] { vouchDataId };
	}

	public List getPrimaryKeyFields() {
		return pk;
	}

	public void setPrimaryKey(Object key) {
		if (null != key) {
			Object[] obj = (Object[]) key;
			setVouchDataId((Long) obj[0]);
		}
	}

	public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public Date getAccountingDate() {
		return accountingDate;
	}

	public void setAccountingDate(Date accountingDate) {
		this.accountingDate = accountingDate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getVouchCode() {
		return vouchCode;
	}

	public void setVouchCode(String vouchCode) {
		this.vouchCode = vouchCode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getVouchDataId() {
		return vouchDataId;
	}

	public void setVouchDataId(Long vouchDataId) {
		this.vouchDataId = vouchDataId;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public List<VouchDetailData> getVouchDetails() {
		return vouchDetails;
	}

	public void setVouchDetails(List<VouchDetailData> vouchDetails) {
		this.vouchDetails = vouchDetails;
	}
	
	public void addVouchDetail(VouchDetailData vouchDetail) {
		this.vouchDetails.add(vouchDetail);
		vouchDetail.setVouchData(this);
	}
	
	public void removeVouchDetail(VouchDetailData vouchDetail) {
		this.vouchDetails.remove(vouchDetail);
		vouchDetail.setVouchData(null);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((applicationCode == null) ? 0 : applicationCode.hashCode());
		result = prime * result + ((vouchDataId == null) ? 0 : vouchDataId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final VouchData other = (VouchData) obj;
		if (applicationCode == null) {
			if (other.applicationCode != null)
				return false;
		} else if (!applicationCode.equals(other.applicationCode))
			return false;
		if (vouchDataId == null) {
			if (other.vouchDataId != null)
				return false;
		} else if (!vouchDataId.equals(other.vouchDataId))
			return false;
		return true;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
