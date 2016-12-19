/**
 *  File: OperatorlogDaoImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-14      zliner      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.operatorlog.impl;

import com.pay.fundout.withdraw.dao.operatorlog.OperatorlogDao;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 操作员日志DAO实现类
 * 
 * @author zliner
 * 
 */
public class OperatorlogDaoImpl<E> extends BaseDAOImpl<E> implements
		OperatorlogDao<E> {
	/**
	 * 保存相关操作员日志服务
	 * 
	 * @param log
	 *            待保存操作员日志对象
	 */
	public void saveOperatorLog(E log) {
		this.create("operatorLog.create", log);
	}
}
