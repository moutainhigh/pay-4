/**
 * 
 */
package com.pay.wechat.model;

/**
 * 普通按钮（子按钮）
 * @author PengJiangbo
 *
 */
public class CommonButton extends Button {
	//添加构造函数 add by davis at 2016-07-04
	public CommonButton(){}
	public CommonButton(String name, String key)
	{
		this(name, "click", key);
	}
	public CommonButton(String name, String type, String key)
	{
		this.setName(name);
		this.type = type;
		this.key = key;
	}
	
	private String type ;
	private String key ;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	@Override
	public String toString() {
		return "CommonButton [type=" + type + ", key=" + key + "]";
	}
	
}
