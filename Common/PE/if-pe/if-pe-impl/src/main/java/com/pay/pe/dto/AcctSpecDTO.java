package com.pay.pe.dto;

import java.io.Serializable;

import com.pay.inf.dao.Page;



public class AcctSpecDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4247246672047851700L;

	private String acctCode;
	private Integer acctContent;
	private Integer acctLevel;
	private String acctName;
	private Integer acctType;
	private Integer balanceBy;
	private Integer childBeorg;
	private Integer childOrgType;
	private String currencyCode;
	private String describe;
	private Integer isMemberAcct;
	private String nonCurrencyUnit;
	private Long orgCode;
	private Integer orgType;
	private Integer precision;
	private Integer referable;
	private String summarizeTo;
	private Integer negativeBalance;
	private Integer acctDiaryUpdateMethod;
	private Integer sumAcctUpdateMethod;
	private Integer status;

    private Page pagination;


    /**
     * 0 未生成 1 已生成
     */
    private String acctStatus;
    /**
     * 余额允许负数 0-不允许;1－允许.
     */
   
    /**
     * 日记账更新方式 1-延时;2-即时.
     */
  
    /**
     * 汇总账更新方式.
     */
    
    
    public Integer getAcctDiaryUpdateMethod() {
        return acctDiaryUpdateMethod;
    }

    public void setAcctDiaryUpdateMethod(Integer acctDiaryUpdateMethod) {
        this.acctDiaryUpdateMethod = acctDiaryUpdateMethod;
    }

    public Integer getNegativeBalance() {
        return negativeBalance;
    }

    public void setNegativeBalance(Integer negativeBalance) {
        this.negativeBalance = negativeBalance;
    }

    public Integer getSumAcctUpdateMethod() {
        return sumAcctUpdateMethod;
    }

    public void setSumAcctUpdateMethod(Integer sumAcctUpdateMethod) {
        this.sumAcctUpdateMethod = sumAcctUpdateMethod;
    }

    /**
     * @return Returns the acctStatus.
     */
    public String getAcctStatus() {
        return acctStatus;
    }

    /**
     * @param acctStatus
     *            The acctStatus to set.
     */
    public void setAcctStatus(String acctStatus) {
        this.acctStatus = acctStatus;
    }

    /**
     * Default constructor.
     * 
     */
    public AcctSpecDTO() {
        super();
    }

    
    public String getDescribe() {
        return this.describe;
    }

   
    public Integer getPrecision() {
        return this.precision;
    }

    public Integer getReferable() {
        return this.referable;
    }

    public String getSummarizeTo() {
        return this.summarizeTo;
    }

    
    public void setDescribe(String describe) {
        this.describe = describe;
    }

   

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    public void setReferable(Integer referable) {
        this.referable = referable;
    }

    public void setSummarizeTo(String summarizeTo) {
        this.summarizeTo = summarizeTo;
    }

    /**
     * @return Returns the pagination.
     */
    public Page getPagination() {
        return pagination;
    }

    /**
     * @param pagination
     *            The pagination to set.
     */
    public void setPagination(Page pagination) {
        this.pagination = pagination;
    }

	public String getAcctCode() {
		return acctCode;
	}

	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	public Integer getAcctContent() {
		return acctContent;
	}

	public void setAcctContent(Integer acctContent) {
		this.acctContent = acctContent;
	}

	public Integer getAcctLevel() {
		return acctLevel;
	}

	public void setAcctLevel(Integer acctLevel) {
		this.acctLevel = acctLevel;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public Integer getAcctType() {
		return acctType;
	}

	public void setAcctType(Integer acctType) {
		this.acctType = acctType;
	}

	public Integer getBalanceBy() {
		return balanceBy;
	}

	public void setBalanceBy(Integer balanceBy) {
		this.balanceBy = balanceBy;
	}

	public Integer getChildBeorg() {
		return childBeorg;
	}

	public void setChildBeorg(Integer childBeorg) {
		this.childBeorg = childBeorg;
	}

	public Integer getChildOrgType() {
		return childOrgType;
	}

	public void setChildOrgType(Integer childOrgType) {
		this.childOrgType = childOrgType;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Integer getIsMemberAcct() {
		return isMemberAcct;
	}

	public void setIsMemberAcct(Integer isMemberAcct) {
		this.isMemberAcct = isMemberAcct;
	}

	public String getNonCurrencyUnit() {
		return nonCurrencyUnit;
	}

	public void setNonCurrencyUnit(String nonCurrencyUnit) {
		this.nonCurrencyUnit = nonCurrencyUnit;
	}

	public Long getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(Long orgCode) {
		this.orgCode = orgCode;
	}

	public Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

    /**
     * @return Returns the acctlevelDesc.
     */
//    public String getAcctlevelDesc() {
//        if (this.getAcctlevel() == null) {
//            return null;
//        }
//        return ACCTLEVEL.ACCTLEVELMAP.get(Helper.getACCTLEVELMAPKey(this
//                .getAcctlevel().intValue()));
//    }

    /**
     * @return Returns the accttypeDesc.
     */
//    public String getAccttypeDesc() {
//        if (this.getAccttype() == null) {
//            return null;
//        }
//        return ACCTTYPE.ACCTTYPEMAP.get(Helper.getACCTTYPEMAPKey(this
//                .getAccttype().intValue()));
//    }

    /**
     * @return Returns the balancebyDesc.
     */
//    public String getBalancebyDesc() {
//        if (this.getBalanceby() == null) {
//            return null;
//        }
//        return CRDBTYPE.CRDBTYPEMAP.get(Helper.getCRDBTYPEMAPKey(this
//                .getBalanceby().intValue()));
//    }

    /**
     * @return Returns the acctcontentDesc.
     */
//    public String getAcctcontentDesc() {
//        if (this.getAcctcontent() == null) {
//            return null;
//        }
//        return ACCTCONTENT.ACCTCONTENTMAP.get(Helper.getACCTCONTENTMAPKey(this
//                .getAcctcontent().intValue()));        
//    }


	/**
	 * Constructs a <code>String</code> with all attributes
	 * in name = value format.
	 *
	 * @return a <code>String</code> representation 
	 * of this object.
	 */
//	public String toString()
//	{
//	    final String TAB = "    ";
//	    
//	    StringBuffer retValue = new StringBuffer();
//	    
//	    retValue.append("AcctSpecDTO ( ")
//	        .append(super.toString()).append(TAB)
//	        .append("acctcode = ").append(this.acctcode).append(TAB)
//	        .append("acctcontent = ").append(this.acctcontent).append(TAB)
//	        .append("acctlevel = ").append(this.acctlevel).append(TAB)
//	        .append("acctname = ").append(this.acctname).append(TAB)
//	        .append("accttype = ").append(this.accttype).append(TAB)
//	        .append("balanceby = ").append(this.balanceby).append(TAB)
//	        .append("childbeorg = ").append(this.childbeorg).append(TAB)
//	        .append("childorgtype = ").append(this.childorgtype).append(TAB)
//	        .append("currencycode = ").append(this.currencycode).append(TAB)
//	        .append("describe = ").append(this.describe).append(TAB)
//	        .append("ismemberacct = ").append(this.ismemberacct).append(TAB)
//	        .append("noncurrencyunit = ").append(this.noncurrencyunit).append(TAB)
//	        .append("orgcode = ").append(this.orgcode).append(TAB)
//	        .append("orgtype = ").append(this.orgtype).append(TAB)
//	        .append("precision = ").append(this.precision).append(TAB)
//	        .append("referable = ").append(this.referable).append(TAB)
//	        .append("summarizeTo = ").append(this.summarizeTo).append(TAB)
//	        .append("pagination = ").append(this.pagination).append(TAB)
//	        .append("acctStatus = ").append(this.acctStatus).append(TAB)
//	        .append("negativeBalance = ").append(this.negativeBalance).append(TAB)
//	        .append("acctDiaryUpdateMethod = ").append(this.acctDiaryUpdateMethod).append(TAB)
//	        .append("sumAcctUpdateMethod = ").append(this.sumAcctUpdateMethod).append(TAB)
//	        .append(" )");
//	     
//	    return retValue.toString();
//	}


}
