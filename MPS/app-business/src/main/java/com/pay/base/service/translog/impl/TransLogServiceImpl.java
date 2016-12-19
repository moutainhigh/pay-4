/**
 *Copyright (c) 2006-2010 pay.com,Inc. All Rights Reserved.
 *@Project_Name app-business 
 *@CreateDate 上午09:25:55 2010-12-7
 */
package com.pay.base.service.translog.impl;

import java.sql.SQLException;
import java.util.List;

import com.pay.base.dao.translog.TransLogDAO;
import com.pay.base.model.TransLog;
import com.pay.base.service.translog.TransLogService;

/**
 * @author Sunny Ying
 * @description TODO
 * @version 上午09:25:55 & 2010-12-7
 */

public class TransLogServiceImpl implements TransLogService{

	private TransLogDAO transLogDAO;

	public Long createTransLog(TransLog transLog) {
		if(transLog != null){
			return this.transLogDAO.createTransLong(transLog);
		}
		return null;
	}

	public void setTransLogDAO(TransLogDAO transLogDAO) {
		this.transLogDAO = transLogDAO;
	}


	public List<TransLog> queryTransLogBySerialNo(String serialNo) throws NumberFormatException, SQLException {
		if(serialNo != null && !"".equals(serialNo)){
			return transLogDAO.queryTransLogBySerialNo(Long.parseLong(serialNo));
		}
		return null;
	}

	public int editTransLogForLinkId(List<TransLog> transLogList, Long baseId) {
		if(transLogList != null && baseId != null){
			for(TransLog transLog : transLogList){
				transLog.setLinkId(baseId);
				return transLogDAO.editLinkId(transLog);
			}
		}
		return 0;
	}

	
}
