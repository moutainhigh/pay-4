/**
 *  File: ValidatePaymentPasswordException.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-3      bill_peng     Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.payment;

/**
 * @author bill_peng
 *
 */
public class ValidatePaymentPasswordException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6078155736417362739L;

	public ValidatePaymentPasswordException(){
		super();
	}
	
	public ValidatePaymentPasswordException(String message){
		super(message);
	}
}
