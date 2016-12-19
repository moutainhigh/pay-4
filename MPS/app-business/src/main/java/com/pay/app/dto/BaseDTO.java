package com.pay.app.dto;

/**
 * 给分页用
 * @author zengjin
 * @date 2010-8-5
 * @param 
 */
public class BaseDTO {
	
	private int beginNum;
	private int endNum;
	
	public int getBeginNum() {
		return beginNum;
	}
	public void setBeginNum(int beginNum) {
		this.beginNum = beginNum;
	}
	public int getEndNum() {
		return endNum;
	}
	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}
}
