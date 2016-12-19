/**
 *  File: WithdrawOrderAuditServicce.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-11      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.flowprocess;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dto.flowprocess.WithdrawAuditDTO;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawAuditQueryDTO;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawResManualProcQueryDTO;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawWFParameter;
import com.pay.fundout.withdraw.dto.flowprocess.WorkFlowHistory;
import com.pay.fundout.withdraw.model.flowprocess.export.ExportWithdrawModel;
import com.pay.fundout.withdraw.model.work.WithdrawWorkorder;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;

/**
 * @Title: WithdrawOrderAuditService.java
 * @Package com.pay.fundout.withdraw.service.flowprocess
 * @Description: 提现服务类
 * @author Jonathen Ni(Jonathen_Ni@staff.hnacom)
 * @date 2010-9-11 上午10:53:08
 * @version V1.0
 */
public interface WithdrawOrderAuditService {
	/**
	 * 条件查询提现审核信息
	 * @param page
	 * @param map
	 * @return Page<WithdrawQueryOrderDTO>
	 * @author Jonathen Ni
	 * @since 2010-09-14
	 */
	public Page<WithdrawAuditDTO> search(String userId,String nodeName,Page<WithdrawAuditDTO> page, WithdrawAuditQueryDTO auditQueryDTO); 
	
	/**
	 * 查找风控待审核数据(全部，非分页方式)
	 * @param auditQueryDTO
	 * @return
	 */
	public List<WithdrawAuditDTO> search(WithdrawAuditQueryDTO auditQueryDTO);
	
	/**
	 * 根据提现申请ID,获取指定订单数据
	 * @Auther Jonathen Ni
	 */
	public WithdrawAuditDTO queryOrderById(Long orderId);
	
	/**
	 * <p>审核提交工作流并更新工单状态</p>
	 * @Auther Jonathen Ni
	 * @param WithdrawWFParameter wfPara 工作流参数类
	 */
	public String firstAudit(WithdrawWFParameter wfPara,String[] wkKeys,int auditStatus,String auditRemark) throws Exception;
	
	/**
	 * <p>滞留流程</p>
	 * @param wfPara
	 * @param wkKeys
	 * @param auditStatus
	 * @param auditRemark
	 * @return
	 * @throws Exception
	 */
	public String delay(WithdrawWFParameter wfPara,String[] wkKeys,String auditRemark) throws Exception;
	
	
	/**
	 * <p>复核提交工作流并更新工单状态</p>
	 * @Auther Jonathen Ni
	 *  @param variables: 参数列表【
	 * userId 用户
	 * nodeName 节点名称
	 * processKey 流程名称
	 * processInstanceId 工作流程实例ID
	 * assigner 指定授权人
	 * previousUser 上一个节点操作 人
	 * taskMessage  批注 】
	 * @param wkKeys: 工单主键
	 * @param auditStatus 状态
	 * @param status 审核状态
	 */
	public String secondAudit(Map<String,Object> variables,String[] wkKeys,int auditStatus,String auditRemark) throws Exception;
	
	/**
	 * <p>结算工作流并更新工单状态</p>
	 * @Auther Jonathen Ni
	 *  @param variables: 参数列表【
	 * userId 用户
	 * nodeName 节点名称
	 * processKey 流程名称
	 * processInstanceId 工作流程实例ID
	 * assigner 指定授权人
	 * previousUser 上一个节点操作 人
	 * taskMessage  批注 】
	 * @param wkKeys: 工单主键
	 * @param auditStatus 状态
	 * @param status 审核状态
	 */
	public String liquidateAudit(Map<String,Object> variables,String[] wkKeys,int auditStatus,String auditRemark) ;
	
	/**
	 * <p>提现分配任务工作流方法</p>
	 * @Auther Jonathen Ni
	 * @param auditStatus 节点状态
	 * @param String[] processKeys 工作流实例ID数组
	 * @param assigner 分配人
	 * @param loginNane 当前登录者 
	 */
	@Deprecated
	public void allocateTaskRdTx(String auditStatus,String[] processKeys,String assigner,String loginNane) throws PossException;
	
	/**
	 * <p>根据订单ID获得工单信息</p>
	 * @Auther Jonathen Ni
	 */
	public WithdrawWorkorder queryWorkorderByOrderId(Long id) throws PossException;
	
	/**
	 * <p>启动工作流</p>
	 * @Auther Jonathen Ni
	 * @param orderId 工单主键
	 */
	public Integer startWorkFlowRdTx(String orderId)throws PossException;
	
	/**
	 * <p>按角色号查找用户</p>
	 * @Auther Jonathen Ni
	 * @param 节点名称
	 */
	//public List<String> queryUser(String auditStatus)throws PossException;
	
	/**
	 * <p>查询手工处理提现结果信息</p>
	 * @Auther Jonathen Ni
	 */
	public Page<WithdrawAuditDTO> searchWithdrawResProcManualInfo( Page<WithdrawAuditDTO> page,
			WithdrawResManualProcQueryDTO resManualProcQueryDto,String nodeName);
	
	/**
	 * <p>手工处理提现结果初审</p>
	 * @Auther Jonathen Ni
	 * @param workOrderId 工单主键
	 * @param auditFailReason 初审失败原因
	 * @param auditStatus 审核结果
	 */
	public String withdrawResManualProcAuditRdTx(Long workOrderId,String auditFailReason,int auditStatus,Map<String, Object> map)throws PossException;
	
	/**
	 * <p>手工处理提现结果复审</p>
	 * @Auther Jonathen Ni
	 * @param workOrderId 工单主键
	 * @param reAuditBackReason 复审退回原因
	 * @param auditStatus 复审结果
	 */
	public String withdrawResManualProcReAudit(Long workOrderId,String reAuditBackReason,int auditStatus,Map<String, Object> map);
	
	/**
	 * <p>导出审核、复核数据</p>
	 * @Auther Jonathen Ni
	 * @param useId 当前登陆名
	 * @param nodeName 需要导出数据的节点名
	 * @param auditQuery 页面传入的查询条件
	 */
	public List<ExportWithdrawModel> queryExportAuditInfo(String userId,String nodeName,WithdrawAuditQueryDTO auditQueryDTO)throws Exception;
	
	/**
	 * <p>展示订单详细信息</p>
	 * @param orderId
	 * @return
	 * @throws PossException
	 */
	public WithdrawAuditDTO showOrderInfo(Long orderId)throws PossException;
	
	/**
	 * 获取审核和复核用户列表
	 * @return
	 * @throws PossException
	 */
	//public Map<String, Object> getAuditAndReAuditUsers()throws PossException;
	
	/**
	 * 转发任务
	 * @param processKeys
	 * @param assigner
	 * @return
	 * @throws PossException
	 */
	public boolean transmitTask(String[] processKeys,String assigner)throws PossException;
	/**
	 * <p>查询工作流历史信息</p>
	 * @Auther Jonathen Ni
	 * @param workKy 工单主键
	 * @return WorkFlowHistory
	 */
	public List<WorkFlowHistory> queryWorkFlowHisInfoByWorkKy(String workKy);
	
	/**
	 * <p>查询工作流历史信息</p>
	 * @Auther Jonathen Ni
	 * @param orderKy 订单主键
	 * @return WorkFlowHistory
	 */
	public List<WorkFlowHistory> queryWorkFlowHisInfoByorderkKy(String orderKy);
	
	/**
	 * 创建工单和启动工作流
	 * @param orderId
	 * @throws PossException
	 */
	public void startWfAndCreateOrderRdTx(String orderId) throws PossException;
}
