/**
 * 
 */
package com.pay.wechat.model.template;

/**
 * @author PengJiangbo
 *
 */
public class Data {
	
	private First first ;

	private Keyword keyword1 ;
	
	private Keyword keyword2 ;
	
	private Remark remark ;
	
	public First getFirst() {
		return first;
	}

	public void setFirst(First first) {
		this.first = first;
	}

	

	public Keyword getKeyword1() {
		return keyword1;
	}

	public void setKeyword1(Keyword keyword1) {
		this.keyword1 = keyword1;
	}

	public Keyword getKeyword2() {
		return keyword2;
	}

	public void setKeyword2(Keyword keyword2) {
		this.keyword2 = keyword2;
	}

	public Remark getRemark() {
		return remark;
	}

	public void setRemark(Remark remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Data [first=" + first + ", keyword1=" + keyword1
				+ ", keyword2=" + keyword2 + ", remark=" + remark + "]";
	}

	
}
