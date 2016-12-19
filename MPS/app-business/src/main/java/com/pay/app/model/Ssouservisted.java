package com.pay.app.model;

import java.util.Date;

/**
 * @author lei.jiangl 
 * @version 
 * @data 2010-8-20 下午04:20:44
 * SSO访问模板
 */
public class Ssouservisted implements Model{
	
	private Long id;
	private String userId;
	private int vistedstatus; 
	private Date vistedtime; 
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getVistedstatus() {
		return vistedstatus;
	}

	public void setVistedstatus(int vistedstatus) {
		this.vistedstatus = vistedstatus;
	}

	public Date getVistedtime() {
		return vistedtime;
	}

	public void setVistedtime(Date vistedtime) {
		this.vistedtime = vistedtime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public void setPrimaryKey(Long id) {
		setId(id);
	}

}
