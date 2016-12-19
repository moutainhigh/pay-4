package com.pay.acc.operatelog.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.pay.acc.operatelog.dao.OperateLogDao;
import com.pay.acc.operatelog.model.OperateLog;
import com.pay.acc.operatelog.service.OperateLogService;

public class OperateLogServiceImpl implements OperateLogService {
	private OperateLogDao operateLogDAO;

	@Override
    public void insertOperateLog(OperateLog operateLog) {
	   this.operateLogDAO.create(operateLog);	    
    }

	
	public void setOperateLogDAO(OperateLogDao operateLogDAO) {
    	this.operateLogDAO = operateLogDAO;
    }


	@Override
    public boolean updateOperateLog(String acctCode, Integer status) {
		Map<String,Object> paramMap=new HashMap<String,Object>(2);
		paramMap.put("acctCode", acctCode);
		paramMap.put("status", status);
	    return this.operateLogDAO.updateOperateLog(paramMap);
    }


	@Override
    public void addOperateLog(Long type, String objectCode, String actionUrl,Long status) {
		OperateLog operateLog=new OperateLog();
		operateLog.setObjectCode(objectCode);	
		//登陆名 此方法为API接口调用，默认为API
		operateLog.setLoginName("api");
		//登陆IP现在默认
		operateLog.setLoginIp("127.0.0.1");
		operateLog.setType(type);
		operateLog.setStatus(status);
		operateLog.setActionUrl(actionUrl);
		operateLog.setBrowserVer(null);
		this.insertOperateLog(operateLog);
	    
    }
}
