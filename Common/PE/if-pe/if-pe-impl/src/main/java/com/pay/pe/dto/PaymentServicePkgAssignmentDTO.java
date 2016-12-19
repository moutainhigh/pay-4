
package com.pay.pe.dto;

import java.io.Serializable;



public class PaymentServicePkgAssignmentDTO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6676699750572891116L;
	private Integer paymentService;
    private Integer paymentServicePKG;
    public Integer getPaymentService() {
        return paymentService;
    }
    public void setPaymentService(Integer paymentService) {
        this.paymentService = paymentService;
    }
    public Integer getPaymentServicePKG() {
        return paymentServicePKG;
    }
    public void setPaymentServicePKG(Integer paymentServicePKG) {
        this.paymentServicePKG = paymentServicePKG;
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
	    
	    retValue.append("PaymentServicePkgAssignmentDTO ( ")
	        .append(super.toString()).append(TAB)
	        .append("paymentService = ").append(this.paymentService).append(TAB)
	        .append("paymentServicePKG = ").append(this.paymentServicePKG).append(TAB)
	        .append(" )"); 
	    
	    return retValue.toString(); 
	}
	

}
