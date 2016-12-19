package com.pay.pe.dto;

import java.io.Serializable;
import java.util.List;



/**
 *
 *
 *<p>PaymentServicePKGDTO.java</p>
 *
 */
public class PaymentServicePKGDTO implements Serializable {
    /**
     * @序列化id
     */
    private static final long serialVersionUID = 1L;
    private Integer paymentservicepackagecode;
    private String paymentservicepackagename;
    private String description;
//    private Page page;
    private List pkgAssignDTOList;
    /**
     * 自动合并分路，0否；1是
     */
    public Integer autoMergeEntries;
    
    public Integer getAutoMergeEntries() {
        return autoMergeEntries;
    }
    public void setAutoMergeEntries(Integer autoMergeEntries) {
        this.autoMergeEntries = autoMergeEntries;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPaymentservicepackagecode() {
        return paymentservicepackagecode;
    }
    
    public void setPaymentservicepackagecode(Integer paymentservicepackagecode) {
        this.paymentservicepackagecode = paymentservicepackagecode;
    }
    
    public String getPaymentservicepackagename() {
        return paymentservicepackagename;
    }
    
    public void setPaymentservicepackagename(String paymentservicepackagename) {
        this.paymentservicepackagename = paymentservicepackagename;
    }
   
    /* (non-Javadoc)
     * 
     */
//    public void setPage(Page page) {
//        this.page = page;
//    }
//    /* (non-Javadoc)
//     * 
//     */
//    public Page getPage() {
//        // TODO Auto-generated method stub
//        return this.page;
//    }
    public List getPkgAssignDTOList() {
        return pkgAssignDTOList;
    }
    public void setPkgAssignDTOList(List pkgAssignDTOList) {
        this.pkgAssignDTOList = pkgAssignDTOList;
    }

	/**
	 * Constructs a <code>String</code> with all attributes
	 * in name = value format.
	 *
	 * @return a <code>String</code> representation 
	 * of this object.
	 */
	public String toString()
	{
	    final String TAB = "    ";
	    
	    StringBuffer retValue = new StringBuffer();
	    
	    retValue.append("PaymentServicePKGDTO ( ")
	        .append(super.toString()).append(TAB)
	        .append("paymentservicepackagecode = ").append(this.paymentservicepackagecode).append(TAB)
	        .append("paymentservicepackagename = ").append(this.paymentservicepackagename).append(TAB)
	        .append("description = ").append(this.description).append(TAB)
//	        .append("page = ").append(this.page).append(TAB)
	        .append("pkgAssignDTOList = ").append(this.pkgAssignDTOList).append(TAB)
	        .append("autoMergeEntries = ").append(this.autoMergeEntries).append(TAB)
	        .append(" )");  
	    
	    return retValue.toString();
	}


}
