package com.pay.acc.operatelog.service;

import com.pay.acc.operatelog.model.OperateLog;

/**
 * @author jim_chen
 * @version 
 * 2010-12-15 
 */
public interface OperateLogService {
	
	/**创建操作日志
	 * @param operateLog
	 */
	public void insertOperateLog(OperateLog operateLog);
	
	/**更新API操作日志的记录
	 * @param acctCode
	 * @param Status
	 */
	public boolean updateOperateLog(String acctCode,Integer Status);
	
	

	/** 新增日志 此方法为接口API调用
	 * @param type类型
	 * @param objectCode会员号/账户
	 * @param actionUrl动作
	 * @param status 状态
	 */
	public void addOperateLog(Long type,String objectCode,String actionUrl,Long status);

}
