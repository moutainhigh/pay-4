 /** @Description 
 * @project 	poss-base
 * @file 		CodeTableDefinitionDTO.java 
 * Copyright © 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-27		Henry.Zeng			Create 
*/
package com.pay.rm.base.tags.codetable.dto;

import com.pay.inf.model.BaseObject;

/**
 * <p>Bocp基础代码定义基类。</p>
 * @author Henry.Zeng
 * @since 2010-10-27
 * @see 
 */
public class CodeTableDefinitionDTO extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * id的Db字段名称
	 */
	private String idColumnName;

	/**
	 * 描述的Db字段名称
	 */
	private String descColumnName;

	/**
	 * 是否需要缓存
	 * TODO 默认false 暂未开发实现
	 */
	private boolean needCache = false;

	/**
	 * 排序条件
	 */
	private String orderClause;

	/**
	 * 过滤条件
	 */
	private String whereClause;
	
	/**
	 * 获取描述的Db字段名称
	 * @return descColumnName
	 */
	public String getDescColumnName() {
		return descColumnName;
	}
	/**
	 * 设置描述的Db字段名称
	 * @param descColumnName
	 */
	public void setDescColumnName(String descColumnName) {
		this.descColumnName = descColumnName;
	}
	/**
	 * 获取id的Db字段名称
	 * @return idColumnName
	 */
	public String getIdColumnName() {
		return idColumnName;
	}
	
	/**
	 * 设置id的Db字段名称 
	 * @param idColumnName
	 */
	public void setIdColumnName(String idColumnName) {
		this.idColumnName = idColumnName;
	}

	/**
	 * 获取是否需要缓存
	 * @return needCache
	 */
	public boolean isNeedCache() {
		return needCache;
	}
	/**
	 * 设置是否需要缓存
	 * @param needCache
	 */
	public void setNeedCache(boolean needCache) {
		this.needCache = needCache;
	}
	
	/**
	 * 获取排序条件
	 * @return orderClause
	 */
	public String getOrderClause() {
		return orderClause;
	}

	/**
	 * 设置排序条件
	 * @param orderClause
	 */
	public void setOrderClause(String orderClause) {
		this.orderClause = orderClause;
	}

	/**
	 * 获取表名
	 * @return tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * 设置表名
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 获取过滤条件
	 * @return whereClause
	 */
	public String getWhereClause() {
		return whereClause;
	}

	/**
	 * 设置过滤条件
	 * @param whereClause
	 */
	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

}
