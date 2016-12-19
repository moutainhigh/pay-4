/**
 *  File: WithdrawOrderAuditServicce.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-23      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.flowprocess;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dto.flowprocess.WithdrawWFParameter;
import com.pay.fundout.withdraw.dto.flowprocess.WorkFlowHistory;
import com.pay.fundout.withdraw.dto.workflowlog.WithdrawWfLogDTO;
import com.pay.fundout.withdraw.model.order.WithdrawOrder;
import com.pay.fundout.withdraw.model.work.WithdrawWorkorder;
import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.poss.base.exception.PossException;

public interface WithdrawService {

	/**
	 * <p>
	 * 风控流程审核工单&工作流处理
	 * </p>
	 * 
	 * @param workOrder
	 * @param auditStatus
	 * @param nodeName
	 * @param map
	 *            工作流对象MAP
	 * @throws PossException
	 */
	public int firstAuditProcessRdTx(WithdrawWorkorder workOrder, int auditStatus, String nodeName, Map<String, Object> map,String auditRemark) throws PossException;

	/**
	 * <p>
	 * 风控流程复核工单&工作流处理
	 * </p>
	 * 
	 * @param workOrder
	 * @param handleType
	 * @param map
	 * @throws PossException
	 */
	public int secondAuditProcessRdTx(WithdrawWorkorder workOrder, String handleType, Map<String, Object> map,String auditRemark) throws PossException;

	/**
	 * <p>
	 * 清结算流程处理
	 * </p>
	 * 
	 * @param order
	 * @param isNeedAccount
	 * @param workOrder
	 * @param map
	 * @param handleType
	 * @throws PossException
	 */
	public int liquidateProcessRdTx(WithdrawOrder order, boolean isNeedAccount, WithdrawWorkorder workOrder, Map<String, Object> map,
			String handleType) throws PossException;

	/**
	 * <p>手工处理体现结果复核流程</p>
	 * @param workOrder
	 * @param order
	 * @param needAccount
	 * @param isSuccAccounting
	 * @throws PossException
	 */
	public boolean ResManualProcessReAuditRdTx(WithdrawWorkorder workOrder,WithdrawOrder order,boolean needAccount,boolean isSuccAccounting) throws PossException;
	
	/**
	 * <p>获得省份DTO</p>
	 * @param provinceId
	 * @return
	 */
	public ProvinceDTO getProvince(Integer provinceId);
	
	/**
	 * <p>获得城市DTO</p>
	 * @param cityId
	 * @return
	 */
	public CityDTO getCity(Integer cityId);
	
	/**
	 * 结束工作流
	 * @param workFlowId
	 * @param wfParam
	 * @param handleType
	 * @author Jonathen Ni
	 * @return true or false
	 */
	public boolean endWorkFlow(WithdrawWFParameter wfParam);
	
	/**
	 * 确认导入更新工单以经济记账方法
	 * @param workOrder
	 * @param isSucce
	 * @throws PossException
	 */
	public boolean importConfirmProcessRdTx(WithdrawWorkorder workOrder, String isSucce) throws PossException;
	
	/**
	 * 直连银行返回结果更新工单以及记账方法
	 * @param map：
	 * 			orderId：订单号
	 * 			isSuccess："1",成功；"2",失败
	 * 			failReason:失败原因
	 * @throws PossException
	 */
	public boolean bankBackRdTx(Map<String,String> map) throws PossException;
	
	/**
	 * 构建本地工作流日志对象
	 * @param wfMap
	 * @return
	 */
	public WithdrawWfLogDTO buildWFLogDTO(Map<String,Object> wfMap);
	
	/**
	 * @param hisList
	 * @return
	 */
	public List<WorkFlowHistory> transforHisToDisp(List<WorkFlowHistory> hisList);
	
	/**
	 * <p>查询工作流日志数据</p>
	 * @param workFlowId
	 * @return
	 */
	public List<WorkFlowHistory> queryWorkFlowHistoryInfo(String workFlowId);
	
	/**
	 * <p>本地工作流日志记录</p>
	 * @param map
	 */
	public void withdrawLogWFLogger(Map<String ,Object> map);
	/**
	 * 初始到滞留节点
	 * @param workOrder
	 * @param auditStatus
	 * @param nodeName
	 * @param map
	 * @param auditRemark
	 * @return
	 * @throws PossException
	 */
	public int firstAudit2DelayRdTx(WithdrawWorkorder workOrder, int auditStatus, String nodeName, Map<String, Object> map, String auditRemark) throws PossException ;
	
	
	/**
	 * <p>查询工作流日志数据</p>
	 * @param workKy
	 * @return
	 */
	public List<WorkFlowHistory> queryWorkFlowHistoryInfoByWorkKy(String workKy);
	
	/**
	 * <p>查询工作流日志数据</p>
	 * @param orderKy
	 * @return
	 */
	public List<WorkFlowHistory> queryWorkFlowHistoryInfoByOrderKy(String orderKy);
}
