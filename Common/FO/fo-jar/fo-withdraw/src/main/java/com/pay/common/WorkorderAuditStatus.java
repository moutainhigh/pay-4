/**
 * 
 */
package com.pay.common;

/**
 * @author chaohu
 *
 */
public enum WorkorderAuditStatus {

	INIT(0),
	PASS(1),
	REJECT(2);
	
	private int value;
	
	private WorkorderAuditStatus(int value){
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
