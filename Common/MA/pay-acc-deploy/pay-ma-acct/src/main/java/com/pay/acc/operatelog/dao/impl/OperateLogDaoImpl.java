package com.pay.acc.operatelog.dao.impl;

import java.util.Map;

import com.pay.acc.operatelog.dao.OperateLogDao;
import com.pay.acc.operatelog.model.OperateLog;
import com.pay.inf.dao.impl.BaseDAOImpl;



@SuppressWarnings("unchecked")
public class OperateLogDaoImpl  extends BaseDAOImpl<OperateLog> implements OperateLogDao {

	@Override
    public boolean updateOperateLog(Map<String, Object> paramMap) {
		return this.getSqlMapClientTemplate().update(namespace.concat("updateOperateLog"), paramMap)==1 ? true:false;
    }

}
