package com.pay.acc.operatelog.dao;

import java.util.Map;

import com.pay.acc.operatelog.model.OperateLog;
import com.pay.inf.dao.BaseDAO;

public interface OperateLogDao extends BaseDAO<OperateLog>{
	
	public boolean updateOperateLog(Map<String,Object> paramMap);

}
