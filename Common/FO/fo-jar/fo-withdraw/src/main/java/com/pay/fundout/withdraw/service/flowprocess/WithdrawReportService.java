/**
 *  File: WithdrawReportService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-9      Jason_wang      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.flowprocess;

import java.util.Map;

import com.pay.fundout.withdraw.dto.flowprocess.WithdrawAuditDTO;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawReportQueryDTO;
import com.pay.inf.dao.Page;


/**
 * @author Jason_wang
 *
 */
public interface WithdrawReportService {

	/**
	 * 查询风控未处理数据
	 * @param page
	 * @param queryDto
	 * @return
	 */
	Map<String,Object> queryNoDisposeData(Page<WithdrawAuditDTO> page,WithdrawReportQueryDTO queryDto);
	
	/**
	 * 查询风控已处理数据
	 * @param page
	 * @param queryDto
	 * @return
	 */
	Map<String,Object> queryDisposedData(Page<WithdrawAuditDTO> page,WithdrawReportQueryDTO queryDto);
	
	/**
	 * 查询风控已处理数据（下载）
	 * @param queryDto
	 * @return
	 */
	Map<String,Object> queryDisposedDataForDownload(WithdrawReportQueryDTO queryDto);
}
