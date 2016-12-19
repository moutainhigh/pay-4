package com.pay.pe.manualbooking.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.pay.pe.helper.CRDRType;


/**
 * 手工记账申请数据传输对象
 */
public class VouchDataDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7158642451824060563L;

	/**
	 * 物理主键
	 */
	private Long vouchDataId;
	
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
	 * 凭证
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
	 * 版本号，共享锁
	 */
	private Long version;
	
	/**
	 * 明细列表
	 */
	private List<VouchDataDetailDto> vouchDataDetails = new ArrayList<VouchDataDetailDto>();

	public VouchDataDto() {
		super();
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

	public List<VouchDataDetailDto> getVouchDataDetails() {
		return vouchDataDetails;
	}

	public void setVouchDataDetails(List<VouchDataDetailDto> vouchDataDetails) {
		this.vouchDataDetails = Collections.unmodifiableList(vouchDataDetails);
	}
	
	public BigDecimal getCrTotalAmount() {
		BigDecimal  crTotal = new BigDecimal("0.000");
		
		for (VouchDataDetailDto detail : vouchDataDetails) {
			if (detail.getCrdr().intValue() == CRDRType.CREDIT.getValue()) {
				BigDecimal amount = new BigDecimal(detail.getAmount());
				crTotal = crTotal.add(amount);
			}
		}
		
		return crTotal = crTotal.setScale(2, BigDecimal.ROUND_HALF_DOWN);
	}
	
	public String getCrTotalAmountStr() {
		BigDecimal b = this.getCrTotalAmount();
		
		DecimalFormat df = new DecimalFormat("#,##0.00");
		String s = df.format(b);
		
		return s;
	}
	
	public BigDecimal getDrTotalAmount() {
		BigDecimal drTotal = new BigDecimal("0.000");
		
		for (VouchDataDetailDto detail : vouchDataDetails) {
			if (detail.getCrdr().intValue() == CRDRType.DEBIT.getValue()) {
				BigDecimal amount = new BigDecimal(detail.getAmount());
				drTotal = drTotal.add(amount);
			}
		}
		
		return drTotal = drTotal.setScale(2, BigDecimal.ROUND_HALF_DOWN);
	}
	
	public String getDrTotalAmountStr() {
		BigDecimal b = this.getDrTotalAmount();
		
		DecimalFormat df = new DecimalFormat("#,##0.00");
		String s = df.format(b);
		
		return s;
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

	@Deprecated
	public void setVersion(Long version) {
		this.version = version;
	}
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("vouchDataId= "+vouchDataId);
		buffer.append("applicationCode= "+applicationCode);
		buffer.append("status= "+status);
		buffer.append("auditDate= "+auditDate);
		buffer.append("accountingDate= "+accountingDate);
		buffer.append("creator= "+creator);
		buffer.append("vouchCode= "+vouchCode);
		buffer.append("createDate= "+createDate);
		buffer.append("auditor= "+auditor);
		buffer.append("remark= "+remark);
		if(vouchDataDetails!=null){
			for(VouchDataDetailDto vouchDataDetail :vouchDataDetails){
				buffer.append("\n"+vouchDataDetail);
			}
		}else{
			buffer.append("vouchDataDetails= "+vouchDataDetails);
		}
		return buffer.toString() ;
	}
	
	
}
