/**
 *  File: OperatorlogService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-14      zliner      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.operatorlog;

import java.util.List;

import com.pay.fundout.withdraw.dto.flowprocess.WorkFlowHistory;
import com.pay.fundout.withdraw.dto.operatorlog.OperatorlogDTO;

/**
 * 操作员日志服务
 * @author zliner
 *
 */
@Deprecated
public interface OperatorlogService {
	/**
	 * 保存相关操作员日志服务
	 * @param dto           待保存操作员日志对象
	 */
	public void saveOperatorLog(OperatorlogDTO dto);
	
	/**
	 * 保存工作流操作日志
	 * @param dto	待保存工作流日志对象
	 */
	public void saveWFOpreatorLog(WorkFlowHistory dto);
	
	/**
	 * 工作流历史信息
	 * @param workFlowId
	 * @return
	 */
	public List<WorkFlowHistory> queryWFHistory(String workFlowId);
}
