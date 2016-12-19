package com.pay.pe.dto;

import java.sql.Timestamp;

import com.pay.pe.helper.CRDRType;
import com.pay.pe.helper.OrgType;
import com.pay.pe.helper.Transactor;
import com.pay.util.MfDate;
import com.pay.util.ObjectUtil;
import com.pay.util.StringUtil;

public class PostingRuleDTO extends Object implements java.io.Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = -7683043658514432143L;
	private Long acctcode;
	private Long acctAliasAcctType;
	private Integer crdr;
	private String crdrdesc;
	private Timestamp invaliddate;
	private Integer party;
	private String partydesc;
	private PaymentServiceDTO paymentServiceDTO;
	private Integer postingrulecode;
	private Integer status;
	private Timestamp validdate;
	private Integer orgType;
	private String acctAliasOrgTypedesc;
	// 账户别名－个人会员账户类型
	private Long acctAliasIndMbr;
	// 账户别名－单位会员账户类型
	private Long acctAliasBizMbr;
	// 异步过帐 1-异步;0－同步
	private Integer asynchronousPosting;
	/**
	 * 是否指定会员科目,1指定;0不指定.
	 */
	private Integer mbrAcctSpecific;

	public String getAcctAliasOrgTypedesc() {
		if (!StringUtil.isNull(this.getOrgType())) {
			// return Helper.getORGTYPEDesc(
			// this.getAcctAliasOrgType().intValue());
			return OrgType.ORGTYPEMAP.get(OrgType.getORGTYPEMAPKey(this
					.getOrgType().intValue()));
		}
		return null;

	}

	public String getCrdrdesc() {
		if (!StringUtil.isNull(this.getCrdr())) {
			// return Helper.getCRDBTYPEDesc(this.getCrdr().intValue());
			CRDRType.CRDBTYPEMAP.get(CRDRType.getCRDBTYPEMAPKey(this.getCrdr()
					.intValue()));
		}
		return null;
	}

	public Long getAcctAliasAcctType() {
		return acctAliasAcctType;
	}

	public void setAcctAliasAcctType(Long acctAliasAcctType) {
		this.acctAliasAcctType = acctAliasAcctType;
	}

	public Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}

	public void setAcctAliasOrgTypedesc(String acctAliasOrgTypedesc) {
		this.acctAliasOrgTypedesc = acctAliasOrgTypedesc;
	}

	public Integer getCrdr() {
		return crdr;
	}

	public void setCrdr(Integer crdr) {
		this.crdr = crdr;
	}

	public Timestamp getInvaliddate() {
		return invaliddate;
	}

	public void setInvaliddate(Timestamp invaliddate) {
		this.invaliddate = invaliddate;
	}

	public Integer getParty() {
		return party;
	}

	public void setParty(Integer party) {
		this.party = party;
	}

	public PaymentServiceDTO getPaymentServiceDTO() {
		return paymentServiceDTO;
	}

	public void setPaymentServiceDTO(PaymentServiceDTO paymentServiceDTO) {
		this.paymentServiceDTO = paymentServiceDTO;
	}

	public Integer getPostingrulecode() {
		return postingrulecode;
	}

	public void setPostingrulecode(Integer postingrulecode) {
		this.postingrulecode = postingrulecode;
	}

	// 返回生效的状态,1生效;0,未生效;9,失效
	public Integer getStatus() {
		if (new MfDate(this.getInvaliddate()).equals(new MfDate(1900, 1, 1))) {
			if (new MfDate(this.getValiddate()).compareTo(new MfDate()) <= 0) {
				return new Integer(1);
			} else {
				return new Integer(0);
			}
		}
		if (new MfDate(this.getInvaliddate()).compareTo(new MfDate()) <= 0) {
			return new Integer(9);
		}
		if ((new MfDate(this.getInvaliddate()).compareTo(new MfDate()) > 0 && new MfDate(
				this.getValiddate()).compareTo(new MfDate()) <= 0)) {
			return new Integer(1);
		}
		return new Integer(0);
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getValiddate() {
		return validdate;
	}

	public void setValiddate(Timestamp validdate) {
		this.validdate = validdate;
	}

	public String getPartydesc() {
		if (!StringUtil.isNull(this.getParty())) {
			// return Helper.getTRANSACTORDesc(this.getParty().intValue());
			return Transactor.transactorMap.get(Transactor
					.getTRANSACTORMAPKey(this.getParty().intValue()));
		}
		return null;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof PostingRuleDTO)) {
			return false;
		}
		PostingRuleDTO dto = (PostingRuleDTO) obj;

		if ((this.paymentServiceDTO == null && dto.getPaymentServiceDTO() != null)
				|| this.paymentServiceDTO != null
				&& dto.getPaymentServiceDTO() == null) {
			return false;
		}

		if (this.paymentServiceDTO != null) {
			if (!(ObjectUtil.equalsContainNull(this.paymentServiceDTO
					.getPaymentservicecode(), dto.getPaymentServiceDTO()
					.getPaymentservicecode()))) {
				return false;
			}
		}

		if (ObjectUtil.equalsContainNull(this.acctAliasAcctType,
				dto.getAcctAliasAcctType())
				&& ObjectUtil.equalsContainNull(this.getOrgType(),
						dto.getOrgType())
				&& ObjectUtil.equalsContainNull(this.crdr, dto.getCrdr())
				&& ObjectUtil.equalsContainNull(this.party, dto.getParty())
				&& ObjectUtil.equalsContainNull(this.getAcctcode(),
						dto.getAcctcode())) {
			return true;
		}
		return false;
	}

	public Long getAcctcode() {
		return acctcode;
	}

	public void setAcctcode(Long acctcode) {
		this.acctcode = acctcode;
	}

	public Long getAcctAliasBizMbr() {
		return acctAliasBizMbr;
	}

	public void setAcctAliasBizMbr(Long acctAliasBizMbr) {
		this.acctAliasBizMbr = acctAliasBizMbr;
	}

	public Long getAcctAliasIndMbr() {
		return acctAliasIndMbr;
	}

	public void setAcctAliasIndMbr(Long acctAliasIndMbr) {
		this.acctAliasIndMbr = acctAliasIndMbr;
	}

	public Integer getAsynchronousPosting() {
		return asynchronousPosting;
	}

	public void setAsynchronousPosting(Integer asynchronousPosting) {
		this.asynchronousPosting = asynchronousPosting;
	}

	public Integer getMbrAcctSpecific() {
		return mbrAcctSpecific;
	}

	public void setMbrAcctSpecific(Integer mbrAcctSpecific) {
		this.mbrAcctSpecific = mbrAcctSpecific;
	}

	public void setCrdrdesc(String crdrdesc) {
		this.crdrdesc = crdrdesc;
	}

	public void setPartydesc(String partydesc) {
		this.partydesc = partydesc;
	}
}
