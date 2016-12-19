/**
 *  File: OperatorlogServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-14      zliner      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.operatorlog.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.withdraw.dao.operatorlog.OperatorlogDao;
import com.pay.fundout.withdraw.dto.flowprocess.WorkFlowHistory;
import com.pay.fundout.withdraw.dto.operatorlog.OperatorlogDTO;
import com.pay.fundout.withdraw.dto.operatorlog.OperatorlogDTOUtil;
import com.pay.fundout.withdraw.model.operatorlog.OperatorLog;
import com.pay.fundout.withdraw.service.operatorlog.OperatorlogService;

/**
 * 操作员日志服务实现类
 * @author zliner
 *
 */
public class OperatorlogServiceImpl implements OperatorlogService {
	//日志类
	private static final Log log = LogFactory.getLog(OperatorlogServiceImpl.class);
	//操作员日志服务DAO
	private OperatorlogDao<OperatorLog> operatorlogDao;
	//set注入
	public void setOperatorlogDao(final OperatorlogDao<OperatorLog> param) {
		this.operatorlogDao = param;
	}
	/**
	 * 保存相关操作员日志服务
	 * @param dto           待保存操作员日志对象
	 */
	public void saveOperatorLog(OperatorlogDTO dto) {
		try {
			operatorlogDao.saveOperatorLog(OperatorlogDTOUtil.dto2Model(dto));
		}catch(Exception e) {
			log.error(e);
		}
	}
	@Override
	public void saveWFOpreatorLog(WorkFlowHistory dto) {
		// TODO Auto-generated method stub
		
	}
	
	public List<WorkFlowHistory> queryWFHistory(String workFlowId){
		// TODO
		return null;
	}
}
