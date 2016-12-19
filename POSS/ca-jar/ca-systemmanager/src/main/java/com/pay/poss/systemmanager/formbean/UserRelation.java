package com.pay.poss.systemmanager.formbean;

import java.sql.Timestamp;


/**
 * BD 
 * 
 * ddl
 * **/
public class UserRelation {
	
	/**主键**/
	private Long id;
	/**登入名称**/
	private String loginId;
	/**中文名**/
	private String name;
	/**上级登入名称**/
	private String pLoginId;
	/**上级中文名**/
	private String pName;
	/**左值**/
	private String lv;
	/**右值**/
	private String ev;
	/**层级**/
	private String layer;
	/**创建日期**/
	private Timestamp createDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getpLoginId() {
		return pLoginId;
	}
	public void setpLoginId(String pLoginId) {
		this.pLoginId = pLoginId;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getLv() {
		return lv;
	}
	public void setLv(String lv) {
		this.lv = lv;
	}
	public String getEv() {
		return ev;
	}
	public void setEv(String ev) {
		this.ev = ev;
	}
	public String getLayer() {
		return layer;
	}
	public void setLayer(String layer) {
		this.layer = layer;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
	
}
