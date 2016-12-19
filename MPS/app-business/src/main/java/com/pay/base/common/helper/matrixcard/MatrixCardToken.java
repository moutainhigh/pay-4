
package com.pay.base.common.helper.matrixcard;

import java.io.Serializable;
import java.util.Date;


/**
 * @author jim_chen
 * @version 
 * 2010-9-15 
 */
public class MatrixCardToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3240230655952313196L;

	public String token;
	public String requestId;
	public String xy0;
	public String value0;
	public String xy1;
	public String value1;
	public String xy2;
	public String value2;
	public Date requestDate;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getXy0() {
		return xy0;
	}

	public void setXy0(String xy0) {
		this.xy0 = xy0;
	}

	public String getValue0() {
		return value0;
	}

	public void setValue0(String value0) {
		this.value0 = value0;
	}

	public String getXy1() {
		return xy1;
	}

	public void setXy1(String xy1) {
		this.xy1 = xy1;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getXy2() {
		return xy2;
	}

	public void setXy2(String xy2) {
		this.xy2 = xy2;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	/**
	 * @return the requestDate
	 */
	public Date getRequestDate() {
		return requestDate;
	}

	/**
	 * @param requestDate
	 *            the requestDate to set
	 */
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
}
