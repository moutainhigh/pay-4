/**
*Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.base.dao.external.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.base.common.enums.ExternalProcessStatus;
import com.pay.base.common.enums.ProcessStatus;
import com.pay.base.dao.external.ExternalLogDao;
import com.pay.base.model.ExternalLog;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author fjl
 * @date 2011-9-19
 */
public class ExternalLogDaoImpl extends BaseDAOImpl<ExternalLog> implements ExternalLogDao {

	@Override
	public ExternalLog findByOrderNo(String orderNo) {
		return (ExternalLog) getSqlMapClientTemplate().queryForObject(namespace.concat("findByOrderNo"), orderNo);
	}

	@Override
	public int updateExternalProcessStatus(Long id, ExternalProcessStatus status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("externalProcessStatus", status.getValue());
		return getSqlMapClientTemplate().update(namespace.concat("updateExternalProcessStatus"),map);
	}

	@Override
	public int updateProcessStatus(Long id, ProcessStatus status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("processStatus", status.getValue());
		return getSqlMapClientTemplate().update(namespace.concat("updateProcessStatus"),map);
	}
	

	@Override
	public ExternalLog selectExernalTransByStatus(Long id, int processStatus,
			int externalProcessStatus) {
			Map<String,Object> paramMap=new HashMap<String,Object>(3);
			paramMap.put("id", id);
			paramMap.put("processStatus", processStatus);
			paramMap.put("externalProcessStatus", externalProcessStatus);
		return (ExternalLog) getSqlMapClientTemplate().queryForObject(namespace.concat("selectExernalTransByStatus"),paramMap);
	}

	@Override
	public List<ExternalLog> getExernalTransListByStatus(int processStatus,
			int externalProcessStatus) {
		Map<String,Object> paramMap=new HashMap<String,Object>(3);
		paramMap.put("processStatus", processStatus);
		paramMap.put("externalProcessStatus", externalProcessStatus);
		return getSqlMapClientTemplate().queryForList(namespace.concat("selectExernalTransByStatus"),paramMap);
	}
	
	
	@Override
	public String selectOrderId() {
		return (String) getSqlMapClientTemplate().queryForObject(namespace.concat("selectOrderId"));
	}

	@Override
	public String selectOrderNo(String preNo) {
		if(preNo == null){
			preNo = "";
		}
		
		return preNo.concat(selectOrderId());
	}
}
