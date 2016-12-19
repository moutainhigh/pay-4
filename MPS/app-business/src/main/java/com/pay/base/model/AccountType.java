package com.pay.base.model;

import java.util.Date;

public class AccountType {

	/**
	 * 主键
	 */
	private int acctTypeId;
	
	/**
	 * 账户类型名称
	 */
	private String name = null;
	
	/**
	 * 服务等级描述
	 */
	private String description = null;
	
	/**
	 * 数据创建时间
	 */
	private Date create_date = null;
	
	/**
	 * 数据更新时间
	 */
	private Date update_date = null;
	
	/**
	 * 1为正常 2为内部使用
	 */
	private int type;

	public int getAcctTypeId() {
		return acctTypeId;
	}

	public void setAcctTypeId(int acctTypeId) {
		this.acctTypeId = acctTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date createDate) {
		create_date = createDate;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date updateDate) {
		update_date = updateDate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
