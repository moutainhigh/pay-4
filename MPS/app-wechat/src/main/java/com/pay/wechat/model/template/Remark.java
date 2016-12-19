/**
 * 
 */
package com.pay.wechat.model.template;

/**
 * @author PengJiangbo
 *
 */
public class Remark {
	
	private String color ;
	
	private String value ;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Remark [color=" + color + ", value=" + value + "]";
	}
	
}
