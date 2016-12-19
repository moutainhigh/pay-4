/**
 *Copyright (c) 2006-2010 pay.com,Inc. All Rights Reserved.
 *@Project_Name app-business 
 *@CreateDate 下午06:01:25 2010-12-6
 */
package com.pay.base.dao.translog.impl;


import java.sql.SQLException;
import java.util.List;

import com.pay.base.dao.translog.TransLogDAO;
import com.pay.base.model.TransLog;
import com.pay.inf.dao.impl.BaseDAOImpl;


/**
 * @author Sunny Ying
 * @description TODO
 * @version 下午06:01:25 & 2010-12-6
 */

public class TransLogDAOImpl extends BaseDAOImpl<TransLog> implements TransLogDAO{


	public TransLog queryTransLogWithSerialNo(Long arg0) {
		return null;
	}

	public Long createTransLong(TransLog transLog){
		return (Long)super.create(transLog);
	}

	public List<TransLog> queryTransLogBySerialNo(Long serialNo) throws SQLException {
		return (List<TransLog>)this.getSqlMapClientTemplate().queryForList(namespace.concat("getTransLogBySerialNo"), serialNo);
	}

	public int editLinkId(TransLog transLog) {
		return this.getSqlMapClientTemplate().update(namespace.concat("updateLinkId"), transLog);
	}
}
