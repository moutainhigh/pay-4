package com.pay.pe.manualbooking.exception;

import java.util.ArrayList;
import java.util.List;


/**
 * 手工记账申请数据不正确异常
 */
public class VouchDataInvalidException extends VouchDataException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2069390321097629715L;
	
	private List<VouchValidateMessage> messages = new ArrayList<VouchValidateMessage>();
	
	public VouchDataInvalidException(final List<VouchValidateMessage> messages) {
		super();
		this.messages = messages;
	}
	
	public List<VouchValidateMessage> getMessages() {
		return messages;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(this.getMessage()).append("\n");
		for (VouchValidateMessage message : messages) {
			sb.append(message.toString()).append("\n");
		}
		return sb.toString();
	}
}
