package com.pay.pe.manualbooking.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.pe.manualbooking.dao.VouchDataLogDao;
import com.pay.pe.manualbooking.model.VouchDataLog;

/**
 * 手工记账申请日志数据访问实现
 */
public class VouchDataLogDaoImpl extends BaseDAOImpl<VouchDataLog> implements
		VouchDataLogDao {	
	private static final Log LOG = LogFactory.getLog(VouchDataLogDaoImpl.class);
	
	public VouchDataLog saveVouchDataLog(VouchDataLog vouchDataLog) {
		LOG.info("Start");
//		VouchDataLog obj = (VouchDataLog) super.save(vouchDataLog);
		super.create(vouchDataLog);
		LOG.info("End");
//		return obj;
		return null ;
	}

	public Class getModelClass() {
		return VouchDataLog.class;
	}

}
