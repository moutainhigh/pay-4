/**
*Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.base.service.external;

import com.pay.base.common.enums.ProcessStatus;
import com.pay.base.model.ExternalLog;

/**
 * @author fjl
 * @date 2011-9-19
 */
public interface ExternalLogService {

	/**
	 * 创建一个外部交易日志
	 * @param externalLog
	 * @return
	 */
	public Long createExternalLogRdTx(ExternalLog externalLog) throws Exception;
	
	/**
	 * 根据id更新状态,同时更新外部,本地状态
	 * @param externalLog
	 * @return
	 * @throws Exception
	 */
	public boolean updateStatusRdTx(ExternalLog externalLog) throws Exception;
	
	/**
	 * 网关更新状态 付款成功
	 * @param id id号
	 * @param remark 备注
	 * @return true 更新的状态成功或是失败
	 */
	public boolean rechargeGetWaySuccess(Long id,String getWayOrderNo,String remark);
	
	/**
	 * 网关更新状态 付款失败
	 * @param id id号
	 * @param remark 备注
	 * @return true/false 更新的状态成功或是失败
	 */
	public boolean rechargeGetWayFailed(Long id,String getWayOrderNo,String remark);
	
	
	/**
	 * 查询日志通过订单号，金额的单位是元
	 * @param orderNo
	 * @return ExternalLog 对象
	 */
	public ExternalLog findByOrderNo(String orderNo);
	

	/**
	 * @param id
	 * @return
	 */
	ExternalLog selectExernalTransByStatus(Long id);
	
	
	/**
	 * @param id
	 * @param status
	 * @return
	 */
	int updateProcessStatus(Long id,ProcessStatus status);
	
	/**
	 * 生成交易流水号,每次都会得到唯一的流水号码
	 * @return string 每次都会得到唯一的流水号码
	 */
	String getOrderNo(); 
	
	/**
	 *  生成交易流水号,每次都会得到唯一的流水号码
	 * @param prefixCode
	 * @return
	 */
	public  String getOrderNo(String prefixCode);

}
