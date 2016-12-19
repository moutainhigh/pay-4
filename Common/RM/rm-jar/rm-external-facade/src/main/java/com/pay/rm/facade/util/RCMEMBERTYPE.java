/**
 *  <p>File: RCMEMBERTYPE.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.rm.facade.util;

/**
 * <p></p>
 * @author zengli
 * @since 2011-5-12
 * @see 
 */
public enum RCMEMBERTYPE {
	PERSONAL(1,"个人会员"),
    ENTERPRISE(2,"企业会员");
	
	private Integer value;
	
	private String desc;
	
	RCMEMBERTYPE(Integer value , String desc){
		this.value = value;
		this.desc = desc;
	}

	/**
	 * @return the value
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	
	
}
