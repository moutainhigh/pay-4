/**
 * 
 */
package com.pay.fi.chain.condition.common;

import java.sql.Timestamp;

import com.pay.inf.dao.Page;

/**
 * @author PengJiangbo
 *
 */
public abstract class QueryCondition {
	
	/** 分页条件  */
	public Page page;
	/** 分页条件  */
	public Timestamp updateTime;


	public Page getPage() {
		return page;
	}
	public void setPage(final Page page) {
		this.page = page;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(final Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
}
