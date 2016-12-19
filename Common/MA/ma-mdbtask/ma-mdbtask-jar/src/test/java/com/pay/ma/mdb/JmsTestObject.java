package com.pay.ma.mdb;

import java.io.Serializable;

import com.pay.pe.service.CalFeeReponse;

public class JmsTestObject implements Serializable {
	
//	private CalFeeReponse calFeeReponse;
	
	public String getMessage(){
		return "hello this is a message";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "hello this is a message=========================";
	}

//	/**
//	 * @return the calFeeReponse
//	 */
//	public CalFeeReponse getCalFeeReponse() {
//		return calFeeReponse;
//	}
//
//	/**
//	 * @param calFeeReponse the calFeeReponse to set
//	 */
//	public void setCalFeeReponse(CalFeeReponse calFeeReponse) {
//		this.calFeeReponse = calFeeReponse;
//	}
	
	
	

}
