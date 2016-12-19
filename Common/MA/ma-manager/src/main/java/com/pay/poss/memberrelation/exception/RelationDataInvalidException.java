package com.pay.poss.memberrelation.exception;

import java.util.ArrayList;
import java.util.List;


/**
 * 关联名单数据不正确异常
 */
public class RelationDataInvalidException extends RelationDataException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2069390321097629715L;
	
	private List<VouchRelationValidateMessage> messages = new ArrayList<VouchRelationValidateMessage>();
	
	public RelationDataInvalidException(final List<VouchRelationValidateMessage> messages) {
		super();
		this.messages = messages;
	}
	
	public List<VouchRelationValidateMessage> getMessages() {
		return messages;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(this.getMessage()).append("\n");
		for (VouchRelationValidateMessage message : messages) {
			sb.append(message.toString()).append("\n");
		}
		return sb.toString();
	}
}
