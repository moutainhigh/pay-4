/**
 * 
 */
package com.pay.wechat.model.template;

/**
 * @author PengJiangbo
 *
 */
public class First {
	
	private String value ;
	
	private String color ;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "First [value=" + value + ", color=" + color + "]";
	}
	
	
}
