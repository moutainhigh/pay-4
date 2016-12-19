/**
*Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.base.dao.external;

import java.util.List;

import com.pay.base.common.enums.ExternalProcessStatus;
import com.pay.base.common.enums.ProcessStatus;
import com.pay.base.model.ExternalLog;
import com.pay.inf.dao.BaseDAO;


/**
 * @author fjl
 * @date 2011-9-19
 */
public interface ExternalLogDao extends BaseDAO<ExternalLog>{
	
	/**
	 * 根据订单流水号查找
	 * @param OrderNo
	 * @return
	 */
	public ExternalLog findByOrderNo(String OrderNo);
	
	/**
	 * 更新处理状态
	 * @param id
	 * @param status
	 * @return
	 */
	public int updateProcessStatus(Long id,ProcessStatus status);
	
	/**
	 * 更新服务器状态
	 * @param id
	 * @param status
	 * @return
	 */
	public int updateExternalProcessStatus(Long id,ExternalProcessStatus status);
	
	/**
	 * 根据id 和状态查询
	 * @param id
	 * @param processStatus
	 * @param externalProcessStatus
	 * @return
	 */
	public ExternalLog selectExernalTransByStatus(Long id,int processStatus,int externalProcessStatus);

	/**
	 * 根据状态查询
	 * @param processStatus
	 * @param externalProcessStatus
	 * @return
	 */
	public List<ExternalLog> getExernalTransListByStatus(int processStatus,int externalProcessStatus);
	
	
	/**
	 * 根据时间+seq 生成订单流水号
	 * @return
	 */
	public String selectOrderId();
	
	/**
	 * 根据 preCode  + 时间 +seq 生成订单流水号
	 * @return
	 */
	public String selectOrderNo(String preNo);
	
}
