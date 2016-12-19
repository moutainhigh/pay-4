package com.pay.acc.service.cidverify.dto;

import com.pay.inf.dto.MutableDto;

/**
 * @author lei.jiangl 
 * @version 
 * @data 2010-9-13 下午05:54:17
 */
public class Cid2GovDTO implements MutableDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String no;
	private String name;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
