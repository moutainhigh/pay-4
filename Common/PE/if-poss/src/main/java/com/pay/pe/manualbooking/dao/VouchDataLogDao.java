package com.pay.pe.manualbooking.dao;

import com.pay.inf.dao.BaseDAO;
import com.pay.pe.manualbooking.model.VouchDataLog;

/**
 * 
 * 手工记账申请日志数据访问
 */
// extends DaoSupport
public interface VouchDataLogDao extends BaseDAO<VouchDataLog> {

	/**
	 * @param vouchDataLog
	 * @return
	 */
	VouchDataLog saveVouchDataLog(VouchDataLog vouchDataLog);
}
