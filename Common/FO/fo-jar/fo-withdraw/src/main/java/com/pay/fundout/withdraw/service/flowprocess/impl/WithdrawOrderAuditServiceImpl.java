/**
 *  File: WithdrawOrderAuditServicceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-11      Jonathen Ni      Changes
 */
package com.pay.fundout.withdraw.service.flowprocess.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.acc.service.account.BookkeepingService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.constantenum.PayForEnum;
import com.pay.acc.service.member.MemberProductService;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.service.base.FundoutOrderService;
import com.pay.fo.order.validate.ValidateService;
import com.pay.fundout.channel.model.channel.ChannelRequest;
import com.pay.fundout.channel.service.channel.ChannelService;
import com.pay.fundout.withdraw.common.util.WithDrawConstants;
import com.pay.fundout.withdraw.dao.export.ExportWithdrawAuditDao;
import com.pay.fundout.withdraw.dao.flowprocess.WithdrawAuditDao;
import com.pay.fundout.withdraw.dao.flowprocess.WithdrawAuditOrderDao;
import com.pay.fundout.withdraw.dao.flowprocess.WithdrawAuditWorkOrderDao;
import com.pay.fundout.withdraw.dao.flowprocess.WithdrawResProcManualDao;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawAuditDTO;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawAuditQueryDTO;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawResManualProcQueryDTO;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawWFParameter;
import com.pay.fundout.withdraw.dto.flowprocess.WorkFlowHistory;
import com.pay.fundout.withdraw.model.flowprocess.WithdrawAuditQuery;
import com.pay.fundout.withdraw.model.flowprocess.WithdrawQueryOrder;
import com.pay.fundout.withdraw.model.flowprocess.WithdrawResManualProcQuery;
import com.pay.fundout.withdraw.model.flowprocess.export.ExportWithdrawModel;
import com.pay.fundout.withdraw.model.order.WithdrawOrder;
import com.pay.fundout.withdraw.model.work.WithdrawWorkorder;
import com.pay.fundout.withdraw.service.autorisk.AutoRiskService;
import com.pay.fundout.withdraw.service.failproc.OrderAfterFailProcAlertService;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawOrderAuditService;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawService;
import com.pay.fundout.withdraw.service.order.WithdrawOrderBusiType;
import com.pay.fundout.withdraw.service.workflowlog.WithdrawWfLogService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.exception.PlatformDaoException;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.service.inf.input.BankInfoFacadeService;
import com.pay.poss.service.ma.member.WithdrawMemberFacadeService;

/**
 * <p>
 * 提现服务实现类
 * </p>
 * 
 * @author Jonathen Ni
 * @date 2010-09-11
 * 
 */
public class WithdrawOrderAuditServiceImpl implements WithdrawOrderAuditService {
	protected transient Log log = LogFactory.getLog(getClass());
	private WithdrawAuditDao withdrawAuditDao;
	private WithdrawAuditOrderDao withdrawOrderDao;
	private WithdrawAuditWorkOrderDao withdrawWorkDao;
	private WithdrawResProcManualDao resProcManualDao;
	private ExportWithdrawAuditDao exportWitndrawDao;
	// private IUserService userService;
	private BankInfoFacadeService bankInfoService;
	private WithdrawMemberFacadeService memberService;
	//银企直连
	private ValidateService bankcorporateService;
	private FundoutOrderService fundoutOrderService;
	private WithdrawService withdrawService;
	private WithdrawWfLogService wfLogService;
	private BookkeepingService bookkeepingService;
	private OrderAfterFailProcAlertService orderAfterFailProcAlertService;
	// 出款自动过风控规则map
	private Map<String, AutoRiskService> riskMap;
	private ChannelService channelService;
	protected MemberProductService memberProductService;
	private Map<Integer,AccountingService> accountingServiceMap;
	
	public void setAccountingServiceMap(
			Map<Integer, AccountingService> accountingServiceMap) {
		this.accountingServiceMap = accountingServiceMap;
	}

	public void setChannelService(ChannelService channelService){
		this.channelService = channelService;
	}
	
	public void setMemberProductService(MemberProductService memberProductService){
		this.memberProductService = memberProductService;
	}
	
	public void setRiskMap(Map<String, AutoRiskService> riskMap){
		this.riskMap = riskMap;
	}

	public void setOrderAfterFailProcAlertService(
			OrderAfterFailProcAlertService orderAfterFailProcAlertService) {
		this.orderAfterFailProcAlertService = orderAfterFailProcAlertService;
	}

	public void setBookkeepingService(BookkeepingService bookkeepingService) {
		this.bookkeepingService = bookkeepingService;
	}

	public void setWfLogService(WithdrawWfLogService wfLogService) {
		this.wfLogService = wfLogService;
	}

	public void setBankcorporateService(ValidateService bankcorporateService) {
		this.bankcorporateService = bankcorporateService;
	}

	public void setFundoutOrderService(FundoutOrderService fundoutOrderService) {
		this.fundoutOrderService = fundoutOrderService;
	}

	// set注入
	public void setWithdrawService(WithdrawService withdrawService) {
		this.withdrawService = withdrawService;
	}

	public void setExportWitndrawDao(ExportWithdrawAuditDao exportWitndrawDao) {
		this.exportWitndrawDao = exportWitndrawDao;
	}

	/*
	 * public void setUserService(IUserService userService) { this.userService =
	 * userService; }
	 */

	public void setWithdrawOrderDao(WithdrawAuditOrderDao withdrawOrderDao) {
		this.withdrawOrderDao = withdrawOrderDao;
	}

	public void setWithdrawAuditDao(WithdrawAuditDao withdrawAuditDao) {
		this.withdrawAuditDao = withdrawAuditDao;
	}

	public void setWithdrawWorkDao(WithdrawAuditWorkOrderDao withdrawWorkDao) {
		this.withdrawWorkDao = withdrawWorkDao;
	}

	public void setResProcManualDao(WithdrawResProcManualDao resProcManualDao) {
		this.resProcManualDao = resProcManualDao;
	}

	public void setBankInfoService(BankInfoFacadeService bankInfoService) {
		this.bankInfoService = bankInfoService;
	}

	public void setMemberService(WithdrawMemberFacadeService memberService) {
		this.memberService = memberService;
	}
	
	@Override
	public List<WithdrawAuditDTO> search(WithdrawAuditQueryDTO auditQueryDTO) {
		final WithdrawAuditQuery auditQuery = new WithdrawAuditQuery();
		BeanUtils.copyProperties(auditQueryDTO, auditQuery);
		List<WithdrawQueryOrder> list = withdrawAuditDao.findByQuery("WF.withdrawAuditQuery", auditQuery);
		return transferOrderInfo(list);
	}

	@Override
	public Page<WithdrawAuditDTO> search(String userId, String nodeName,
			Page<WithdrawAuditDTO> page, WithdrawAuditQueryDTO auditQueryDTO) {
		final WithdrawAuditQuery auditQuery = new WithdrawAuditQuery();
		Page<WithdrawQueryOrder> pageService = new Page<WithdrawQueryOrder>();
		PageUtils.setServicePage(pageService, page);
		BeanUtils.copyProperties(auditQueryDTO, auditQuery);
		// 根据审核,复审,分控任务分配,获取不同的提现订单信息
		if (nodeName.equals(WithDrawConstants.AUDIT_NODE))
			pageService = withdrawAuditDao.findWdOrderPage(
					"WF.withdrawAuditQuery", pageService, auditQuery);
		else if (nodeName.equals(WithDrawConstants.SECOND_AUDIT_NODE))
			pageService = withdrawAuditDao.findWdOrderPage(
					"WF.withdrawSecondAuditQuery", pageService, auditQuery);
		else if (nodeName.equals(WithDrawConstants.LIQUIDATE_NODE))
			pageService = withdrawAuditDao.findWdOrderPage(
					"WF.withdrawLiquidateQuery", pageService, auditQuery);
		else if (nodeName.equals(WithDrawConstants.PROCESSING_NODE))
			pageService = withdrawAuditDao.findWdOrderPage(
					"WF.withdrawProcessingQuery", pageService, auditQuery);
		else if (nodeName.equals(WithDrawConstants.TASK_ASSIGN_AUDIT_NODE)
				|| nodeName
						.equals(WithDrawConstants.TASK_REASSIGN_REAUDIT_NODE)) {
			pageService = withdrawAuditDao.findWdOrderPage(
					"WF.withdrawAllocateQuery", pageService, auditQuery);
		}
		PageUtils.setServicePage(page, pageService);
		List<WithdrawQueryOrder> resultList = pageService.getResult();
		List<WithdrawAuditDTO> list = transferOrderInfo(resultList);
		if (list != null)
			page.setResult(list);
		return page;
	}

	public WithdrawOrder queryWithdrawOrderById(Long orderId) {
		return this.withdrawOrderDao.queryOrderById(
				"WF.queryWithdrawOrderById", orderId);
	}

	@Override
	public WithdrawAuditDTO queryOrderById(Long orderId) {
		WithdrawOrder orderModel = this.withdrawOrderDao.queryOrderById(
				"WF.queryWithdrawOrderById", orderId);
		WithdrawAuditDTO dto = null;
		if (orderModel != null)
			dto = this.copyToWithdrawAuditDTO(orderModel);
		WithdrawWorkorder workorder = null;
		try {
			workorder = queryWorkorderByOrderId(Long.valueOf(orderId));
			dto.setWorkOrderky(workorder.getWorkOrderky());
			ProvinceDTO privinceDto = withdrawService.getProvince(dto
					.getBankProvince() == null ? null : Integer.valueOf(dto
					.getBankProvince().intValue()));
			CityDTO cityDto = withdrawService
					.getCity(dto.getBankCity() == null ? null : Integer
							.valueOf(dto.getBankCity().intValue()));
			dto.setBankCityStr(cityDto == null ? "" : cityDto.getCityname());
			dto.setBankProvinceStr(privinceDto == null ? "" : privinceDto
					.getProvincename());
		} catch (PossException e) {
			log.error("find workorder error,orderid is:" + orderId, e);
		}
		return dto;
	}

	@Override
	public WithdrawAuditDTO showOrderInfo(Long orderId) {
		WithdrawOrder orderModel = this.withdrawOrderDao.queryOrderById(
				"WF.showOrderDetailQueryById", orderId);
		WithdrawAuditDTO dto = null;
		if (orderModel != null)
			dto = this.copyToWithdrawAuditDTO(orderModel);
		WithdrawWorkorder workorder = null;
		try {
			workorder = queryWorkorderByOrderId(Long.valueOf(orderId));
			dto.setWorkOrderky(workorder.getWorkOrderky());

			ProvinceDTO privinceDto = withdrawService.getProvince(dto
					.getBankProvince() == null ? null : Integer.valueOf(dto
					.getBankProvince().intValue()));
			CityDTO cityDto = withdrawService
					.getCity(dto.getBankCity() == null ? null : Integer
							.valueOf(dto.getBankCity().intValue()));
			dto.setBankCityStr(cityDto == null ? "" : cityDto.getCityname());
			dto.setBankProvinceStr(privinceDto == null ? "" : privinceDto
					.getProvincename());

			MemberInfoDto memberInfoDto = this.memberService.qyeryMember(dto.getMemberCode());
			dto.setPayer(memberInfoDto == null ? "" : memberInfoDto.getMemberName());
		} catch (PossException e) {
			log.error("find workorder error,orderid is:" + orderId, e);
		}
		return dto;
	}

	// 审核提交
	@Override
	public String firstAudit(WithdrawWFParameter wfPara, String[] wkKeys,
			int auditStatus, String auditRemark) throws Exception {
		WithdrawWorkorder workPara = new WithdrawWorkorder();
		StringBuffer seqId = new StringBuffer();
		List<String> subList = new ArrayList<String>();
		for (int i = 0; i < wkKeys.length; i++)
			subList.add(wkKeys[i]);
		workPara.setSubList(subList);

		List<WithdrawWorkorder> workOrderList = this.withdrawWorkDao
				.findByQuery("WF.queryWorkOrderListById", workPara);
		// 获得此批提交审核工单的实例流程号
		for (WithdrawWorkorder workOrder : workOrderList) {
			// 组装工作流信息
			Map<String, Object> map = new HashMap<String, Object>();
			String nodeName = wfPara.getNodeName();
			// 如果状态为3工作流提交的时候节点为滞留
			if (3 == workOrder.getStatus()) {
				nodeName = "tempTask";
			}

			map.put(WithdrawWFParameter.WF_PARAMETER_KEY_ASSIGNER, wfPara
					.getAssigner());
			map.put(WithdrawWFParameter.WF_PARAMETER_KEY_NODENAME, nodeName);
			map.put(WithdrawWFParameter.WF_PARAMETER_KEY_PREVIOUSUSER, wfPara
					.getPreviousUser());
			map.put(WithdrawWFParameter.WF_PARAMETER_KEY_PROCESSINSTANCEID,
					workOrder.getWorkflowKy());
			map.put(WithdrawWFParameter.WF_PARAMETER_KEY_PROCESSKEY, wfPara
					.getProcessKey());
			map.put(WithdrawWFParameter.WF_PARAMETER_KEY_TASKMESSAGE, wfPara
					.getTaskMessage());
			map.put(WithdrawWFParameter.WF_PARAMETER_KEY_USERID, wfPara
					.getUserId());

			// 更新工单并且推动工作流
			int count = 0;
			try {
				count = this.withdrawService.firstAuditProcessRdTx(workOrder,
						auditStatus, WithDrawConstants.AUDIT_NODE, map,
						auditRemark);
			} catch (Exception e) {
				log
						.error("update workorderorder error...,maybe this order have been updated,workorderId is:"
								+ workOrder.getWorkOrderky());
			}
			if (count != 0)
				seqId.append(workOrder.getOrderSeq()).append(",");
		}
		return seqId.toString();
	}

	@Override
	public WithdrawWorkorder queryWorkorderByOrderId(Long id)
			throws PossException {
		return this.withdrawWorkDao.queryWorkOrderById(
				"WF.queryWorkOrderByOrderId", id);
	}

	// 查询工作流历史信息
	@Override
	public List<WorkFlowHistory> queryWorkFlowHisInfoByWorkKy(String workKy) {
		List<WorkFlowHistory> wfHisList = null;
		if (workKy != null) {
			// 获得节点名称
			wfHisList = this.withdrawService
					.queryWorkFlowHistoryInfoByWorkKy(workKy);
			return withdrawService.transforHisToDisp(wfHisList);
		}
		return null;
	}

	// 查询工作流历史信息
	@Override
	public List<WorkFlowHistory> queryWorkFlowHisInfoByorderkKy(String orderKy) {
		List<WorkFlowHistory> wfHisList = null;
		if (orderKy != null) {
			// 获得节点名称
			wfHisList = this.withdrawService
					.queryWorkFlowHistoryInfoByOrderKy(orderKy);
			return withdrawService.transforHisToDisp(wfHisList);
		}
		return null;
	}

	// 复审提交
	@Override
	public String secondAudit(Map<String, Object> variables, String[] wkKeys,
			int auditStatus, String auditRemark) throws Exception {
		String workFlowKey = null;
		StringBuffer seqId = new StringBuffer();
		WithdrawWorkorder workPara = new WithdrawWorkorder();
		List<String> subList = new ArrayList<String>();
		for (int i = 0; i < wkKeys.length; i++)
			subList.add(wkKeys[i]);
		workPara.setSubList(subList);
		List<WithdrawWorkorder> workOrderList = this.withdrawWorkDao
				.findByQuery("WF.queryWorkOrderListById", workPara);
		for (WithdrawWorkorder workOrder : workOrderList) {
			try {
				workFlowKey = workOrder.getWorkflowKy();
				String handleType = "";
				int secondAuditStatus = 0;
				if (auditStatus == WithDrawConstants.SECOND_AUDIT_AGREE) {
					// 根据审核状态判断复核状态
					if (workOrder.getStatus().intValue() == WithDrawConstants.AUDIT_SUCCESS)
						secondAuditStatus = WithDrawConstants.SECOND_AUDIT_SUCCESS;
					else if (workOrder.getStatus().intValue() == WithDrawConstants.AUDIT_REJECT)
						secondAuditStatus = WithDrawConstants.SECOND_AUDIT_REJECT;
					// 工作流继续往下
					handleType = WithDrawConstants.SECOND_AUDIT_AGREE_HANDLE_TYPE;
				} else {
					// 复核退回 根据审核状态判断复核状态 复核拒绝，
					// 回到初始状态当节点>=复核的时候,批次状态不是未出批次，工作流不能回退
					boolean isRoolBack = this.checkRoolBack(workOrder,
							auditStatus, WithDrawConstants.SECOND_AUDIT_NODE);
					if (isRoolBack) {
						secondAuditStatus = WithDrawConstants.AUDIT_INIT;
						// 工作流设置回滚
						handleType = WithDrawConstants.SECOND_AUDIT_BACK_HANDLE_TYPE;
					}
				}
				// 获得当前工作流实例ID,并将其加入工作流变量集合中
				variables.put("processInstanceId", workFlowKey);
				workOrder.setStatus(Integer.valueOf(secondAuditStatus));
				workOrder.setPreStatus("a.status in(1,2)");
				// 更新工单并且推动工作流
				int count = 0;
				try {
					count = this.withdrawService.secondAuditProcessRdTx(
							workOrder, handleType, variables, auditRemark);
				} catch (Exception e) {
					log.error("update workorderorder drror...,workorderId is:"
							+ workOrder.getWorkOrderky(), e);
				}
				if (count != 1)
					seqId.append(workOrder.getOrderSeq()).append(",");
			} catch (Exception e) {
				log.error("secondAuditRdTx error...,workOrderKy is: "
						+ workOrder.getWorkOrderky(), e);
			}
		}
		return seqId.toString();
	}

	@Override
	public String liquidateAudit(Map<String, Object> variables,
			String[] wkKeys, int auditStatus, String auditRemark) {
		StringBuffer seqId = new StringBuffer();
		boolean needAccount = false;
		if (auditStatus == WithDrawConstants.LIQUIDATE_REJECT)
			needAccount = true;
		String workFlowKey = null;
		WithdrawWorkorder workPara = new WithdrawWorkorder();
		List<String> subList = new ArrayList<String>();
		for (int i = 0; i < wkKeys.length; i++)
			subList.add(wkKeys[i]);
		workPara.setSubList(subList);
		List<WithdrawWorkorder> workOrderList = this.withdrawWorkDao
				.findByQuery("WF.queryWorkOrderListById", workPara);
		for(WithdrawWorkorder workOrder : workOrderList){
			System.out.println(workOrder);
		}
		
		// 获得订单
		List<WithdrawOrder> orderList = null;
		Map<Long, WithdrawOrder> orderMap = null;
		if (needAccount) {
			orderList = this.withdrawOrderDao.findByQuery(
					"WF.queryWithdrawOrderListByWorkKy", workPara);
			
			
			if (orderList != null) {
				orderMap = new HashMap<Long, WithdrawOrder>();
				for (WithdrawOrder order : orderList) {
					orderMap.put(order.getSequenceId(), order);
				}
			}
		}
		// 获得此批提交审核工单的工作流实例流程号
		for (WithdrawWorkorder workOrder : workOrderList) {
			try {
				// 工作流实例ID
				workFlowKey = workOrder.getWorkflowKy();
				String handleType = "";
				int secondAuditStatus = 0;
				if (needAccount) {
					// 结算拒绝
					secondAuditStatus = WithDrawConstants.LIQUIDATE_AUDIT_REJECT;
					// 结算拒绝，工作流结束
					handleType = WithDrawConstants.LIQUIDATE_AUDIT_REJECT_HANDLE_TYPE;
				} else {
					// 当节点>=复核的时候,批次状态不是未出批次，工作流不能回退
					boolean isRoolBack = this.checkRoolBack(workOrder,
							auditStatus, WithDrawConstants.LIQUIDATE_NODE);
					if (isRoolBack) {
						// 结算退回，返回到初始状态
						secondAuditStatus = WithDrawConstants.AUDIT_INIT;
						// 工作流返回到初始节点
						handleType = WithDrawConstants.LIQUIDATE_AUDIT_BACK_HANDLE_TYPE;
						// 获得审核节点人员
						String assigner = this.getassigner(
								WithDrawConstants.AUDIT_NODE, workFlowKey);
					}
				}
				// 获得当前工作流实例ID,并将其加入工作流变量集合中
				variables.put("processInstanceId", workFlowKey);
				variables.put("workOrderKy", workOrder.getWorkOrderky());
				workOrder.setStatus(Integer.valueOf(secondAuditStatus));
				workOrder.setPreStatus("a.status in(4,5)");
				WithdrawOrder order = null;
				if (needAccount){
					order = orderMap.get(workOrder.getOrderSeq());
					order.setErrorMessage(auditRemark);
					//added pjb--
					Integer payerAcctType = Integer.valueOf(order.getMemberAccType().toString()) ;
					order.setPayerAcctType(payerAcctType);
					order.setPayeeAcctType(payerAcctType);
					order.setPayerCurrencyCode(AcctTypeEnum.getAcctCurrencyByCode(payerAcctType));
					order.setPayeeCurrencyCode(AcctTypeEnum.getAcctCurrencyByCode(payerAcctType));
					
					//added pjb--
				}
				// 更新工单并且记账
				int count = 0;
				try {
					count = this.withdrawService.liquidateProcessRdTx(order,
							needAccount, workOrder, variables, handleType);
				} catch (Exception e) {
					log.error("update workorderorder drror...,workorderId is:"
							+ workOrder.getWorkOrderky(), e);
				}
				if (count == 1) {
					this.withdrawService.withdrawLogWFLogger(variables);
				} else
					seqId.append(workOrder.getOrderSeq()).append(",");

			} catch (Exception e) {
				log.error("liquidateAuditRdTx error...,workOrderKy is: "
						+ workOrder.getWorkOrderky(), e);
			}
		}
		return seqId.toString().substring(0, seqId.toString().length());
	}

	private WithdrawAuditDTO copyToWithdrawAuditDTO(WithdrawOrder order) {
		WithdrawAuditDTO dto = new WithdrawAuditDTO();
		if (order != null) {
			dto.setAccountName(order.getAccountName());
			dto.setAmount(order.getAmount());
			dto.setBankAcct(order.getBankAcct());
			dto.setBankBranch(order.getBankBranch());
			dto.setBankCity(order.getBankCity());
			dto.setBankProvince(order.getBankProvince());
			dto.setCreateTime(order.getCreateTime());
			dto.setMemberAccType(order.getBankAcctType());
			dto.setMemberCode(order.getMemberCode());
			dto.setSequenceId(order.getSequenceId());
			dto.setStatus(Integer.parseInt(String.valueOf(order.getStatus())));
			dto.setMemberType(order.getMemberType());
			dto.setPrioritys(order.getPrioritys());
			dto.setOrderRemarks(order.getOrderRemarks());
			dto.setBankKy(order.getBankKy());
			dto.setBatchStatus(order.getBatchStatus());
			dto.setWebAuditTime(order.getWebAuditTime());
		}
		return dto;
	}

	/**
	 * 复核创建出款工单
	 * 
	 * @return
	 */
	private boolean reviewCreateFundoutWorkerOrder(String orderId) {

		WithdrawOrder withdrawOrder = this.queryWithdrawOrderById(Long
				.valueOf(orderId));
		// 对于批量付款到银行如果子订单已经产生。该笔订单已经记账
		if (4 == withdrawOrder.getBusiType())
			return true;
		int dealType = PayForEnum.MERCHANT_WITHDRAW_APPLY.getCode();
		// TODO 复核创建出款工单 ，交易类型与MA同步
		if (withdrawOrder != null) {
			if (withdrawOrder.getBusiType().intValue() == WithdrawOrderBusiType.PAY2BANK
					.getCode()) {
				dealType = PayForEnum.MERCHANT_WITHDRAW_APPLY.getCode();
			} else if (withdrawOrder.getBusiType().intValue() == WithdrawOrderBusiType.MASSPAY2BANK
					.getCode()) {
				dealType = PayForEnum.MERCHANT_WITHDRAW_APPLY.getCode();
			}
			
			AccountingService accountingService = accountingServiceMap.get(withdrawOrder.getBusiType());
			if(null != accountingService){
				boolean resultFlag = bookkeepingService.isChargeSuccess(orderId,
						accountingService.getDealCode(), dealType);
				return resultFlag;
			}
			
		}
		return true;
	}

	@Override
	public Integer startWorkFlowRdTx(String orderId) throws PossException {
		try {
			if (reviewCreateFundoutWorkerOrder(orderId)) {
				// 启动工作流，得到工作流实例ID
				String workFlowKey = null;
				// try{
				// workFlowKey =
				// this.bpmService.startProcessInstanceByKeyForGroup(WithDrawConstants.PROCESS_NAME,
				// null);
				// }catch(Exception e){
				// log.error("generate workflow_ky  error...",e);
				// }
				// 生成工单，并更新工单的工作流实例ID
				WithdrawWorkorder workOrder = new WithdrawWorkorder();

				workOrder.setOrderSeq(Long.valueOf(orderId));
				workOrder.setWorkflowKy(workFlowKey == null ? "" : workFlowKey);
				
				// 出款自动过风控规则植入修改
				FundoutOrderDTO order = (FundoutOrderDTO) fundoutOrderService.getOrder(Long.valueOf(orderId));
				AutoRiskService autoRiskService = riskMap.get(String.valueOf(order.getPayerMemberType()));
				
				// 判断订单是否走银企直连渠道
				boolean isBankCorporate = false;
				ChannelRequest request = new ChannelRequest();
				request.setProductCode(order.getOrderType().toString());
				request.setTargetBank(order.getPayeeBankCode());
				request.setChannelFlag(0);
				String channelId = channelService.getFundoutChannel(request);
				if(channelId != null){
					boolean isHaveProduct = memberProductService.isHaveProduct(order.getPayerMemberCode(), "BANK_CORPORATE");
					if(isHaveProduct){
						isBankCorporate = true;
					}
				}
				workOrder.setStatus(WithDrawConstants.AUDIT_INIT);
				//关闭人工自动过风控
//				// 判断是否开通银企直连结束 
//				
//				if(isBankCorporate){
//					// 直连情况保持原有逻辑，不参与自动过风控规则
//					workOrder.setStatus(WithDrawConstants.AUDIT_INIT);
//				}else{
//					if(autoRiskService.autorisk(orderId)){
//						// 自动过风控规则过了则工单原来逻辑状态
//						workOrder.setStatus(WithDrawConstants.AUDIT_INIT);
//					}else{
//						// 自动过风控规则不过则工单状态为等待风险审核处理状态
//						workOrder.setStatus(WithDrawConstants.AUTO_RISK_TO_DEAL);
//					}
//				}
				// 出款自动过风控规则植入修改结束

				// 批次状态，插入默认为0
				workOrder.setBatchStatus(0);
				// 生成工单
				this.withdrawWorkDao.create("WF.create", workOrder);
				
				order.setWorkOrderStatus(workOrder.getStatus());
				bankcorporateService.validate(order);
				return order.getWorkOrderStatus();
			} else {
				// 记录报警日志
				orderAfterFailProcAlertService.saveOrderAfterFailProcRnTx(Long.valueOf(orderId), "后台工单");
				return null;
			}
		} catch (Exception e) {
			// 有异常通知JMS，让JMS之后进行补单处理
			log.error("startWorkFlowRdTx is error...,orderId=" + orderId, e);
			throw new PossException("Start workorder error...",
					ExceptionCodeEnum.SEND_WORKFLOW_LIQUIDATE_EXCEPTION, e);
			
		}
	}

	// 查询相应角色的用户
	/*
	 * @Override public List<String> queryUser(String auditStatus) throws
	 * PossException { List<String> userList = null; String nodeName = ""; try {
	 * if (Integer.parseInt(auditStatus) == WithDrawConstants.AUDIT_INIT ||
	 * Integer.parseInt(auditStatus) == WithDrawConstants.AUDIT_WAIT) nodeName =
	 * WithDrawConstants.AUDIT_NODE; else if (Integer.parseInt(auditStatus) ==
	 * WithDrawConstants.AUDIT_SUCCESS || Integer.parseInt(auditStatus) ==
	 * WithDrawConstants.AUDIT_REJECT) nodeName =
	 * WithDrawConstants.SECOND_AUDIT_NODE;
	 * 
	 * userList =
	 * userService.findLoginIdByFlowName(WithDrawConstants.PROCESS_NAME,
	 * nodeName); } catch (Exception e) { throw new
	 * PossException("get user information by roleCode error,audit node is:" +
	 * auditStatus, ExceptionCodeEnum.ALLOCATE_WORKFLOW_EXCEPTION,e); } return
	 * userList; }
	 */

	// 工作流分配任务接口
	@Override
	public void allocateTaskRdTx(String auditStatus, String[] processKeys,
			String assigner, String loginNane) throws PossException {
		String nodeName = "";
		// 初始跟滞留
		if (Integer.parseInt(auditStatus) == WithDrawConstants.AUDIT_WAIT
				|| Integer.parseInt(auditStatus) == WithDrawConstants.AUDIT_INIT) {
			nodeName = WithDrawConstants.TASK_ASSIGN_AUDIT_NODE;
		} else if (Integer.parseInt(auditStatus) == WithDrawConstants.AUDIT_SUCCESS
				|| Integer.parseInt(auditStatus) == WithDrawConstants.AUDIT_REJECT) {
			nodeName = WithDrawConstants.TASK_REASSIGN_REAUDIT_NODE;
		}

		if (assigner != null && auditStatus != null && processKeys != null) {
			Map<String, Object> map = null;
			for (int i = 0; i < processKeys.length; i++) {
				map = new HashMap<String, Object>();
				map.put("userId", loginNane);
				map.put("nodeName", nodeName);
				map.put("processKey", WithDrawConstants.PROCESS_NAME);
				map.put("assigner", assigner);
				map.put("taskMessage", "");
				map.put("processInstanceId", processKeys[i]);
				try {
					this.withdrawService.withdrawLogWFLogger(map);
				} catch (Exception e) {
					log.error("Allocate Task error,workflow_ky is:"
							+ processKeys[i] + ",assigner is:" + assigner
							+ ",nodename is:" + nodeName, e);
				}
				log.info("Allocate Task,workflow_ky is:" + processKeys[i]
						+ ",assigner is:" + assigner + ",nodename is:"
						+ nodeName);
			}
		}
	}

	// 查询手工处理提现结果数据
	@Override
	public Page<WithdrawAuditDTO> searchWithdrawResProcManualInfo(
			Page<WithdrawAuditDTO> page,
			WithdrawResManualProcQueryDTO resManualProcQueryDto, String nodeName) {

		WithdrawResManualProcQuery query = new WithdrawResManualProcQuery();
		Page<WithdrawQueryOrder> pageService = new Page<WithdrawQueryOrder>();
		PageUtils.setServicePage(pageService, page);
		BeanUtils.copyProperties(resManualProcQueryDto, query);
		// 手工审核节点
		if (nodeName
				.equals(WithDrawConstants.WITHDRAW_RESULT_PROCESS_MANUAL_AUDIT_NODE))
			pageService = this.resProcManualDao.queryWithdrawResManualProcList(
					"WF.withdrawResultProcessManualQuery", pageService, query);
		else if (nodeName
				.equals(WithDrawConstants.WITHDRAW_RESULT_PROCESS_MANUAL_REAUDIT_NODE)) {
			// 手工复核节点
			pageService = this.resProcManualDao.queryWithdrawResManualProcList(
					"WF.reAuditWithdrawResultProcessManualQuery", pageService,
					query);
		}
		List<WithdrawQueryOrder> resultList = pageService.getResult();
		List<WithdrawAuditDTO> list = transferOrderInfo(resultList);

		PageUtils.setServicePage(page, pageService);

		if (list != null)
			page.setResult(list);
		return page;
	}

	/**
	 * <p>
	 * 为工单跟订单表在页面显示将两表合成一张表的转换操作
	 * </p>
	 * 
	 * @Auther Jonathen Ni
	 * @since 2010-09-20
	 */
	private List<WithdrawAuditDTO> transferOrderInfo(
			List<WithdrawQueryOrder> resultList) {
		List<WithdrawAuditDTO> list = null;
		String bankCityStr = null;
		String bankProvinceStr = null;
		if (resultList != null) {
			list = new ArrayList<WithdrawAuditDTO>();
			for (int i = 0; i < resultList.size(); i++) {
				WithdrawAuditDTO dto = new WithdrawAuditDTO();
				WithdrawQueryOrder order = resultList.get(i);
				BeanUtils.copyProperties(order.getOrderDto(), dto);
				BeanUtils.copyProperties(order.getWorkOrderDto(), dto);
				// 由于工单表中增加了createDate跟updateDate字段，在拼装WithdrawAuditDTO时候要单独把订单的创建时间取出来
				dto.setCreateTime(order.getOrderDto().getCreateTime());

				ProvinceDTO privinceDto = withdrawService.getProvince(dto
						.getBankProvince() == null ? null : Integer.valueOf(dto
						.getBankProvince().intValue()));
				CityDTO cityDto = withdrawService
						.getCity(dto.getBankCity() == null ? null : Integer
								.valueOf(dto.getBankCity().intValue()));
				dto
						.setBankCityStr(cityDto == null ? "" : cityDto
								.getCityname());
				dto.setBankProvinceStr(privinceDto == null ? "" : privinceDto
						.getProvincename());
				list.add(dto);
			}
		}
		return list;
	}

	// 手工处理提现结果审核提交
	@Override
	public String withdrawResManualProcAuditRdTx(Long workOrderId,
			String auditFailReason, int auditStatus, Map<String, Object> map)
			throws PossException {
		String seqId = null;
		WithdrawWorkorder workOrder = null;
		try {
			workOrder = new WithdrawWorkorder();
			String preStatus = "a.STATUS = 7 and a.batch_status = 1";
			workOrder.setPreStatus(preStatus);
			workOrder.setWorkOrderky(workOrderId);
			if (auditStatus == 1)
				workOrder
						.setStatus(WithDrawConstants.WITHDRAW_RESULT_PROCESS_MANUAL_AUDIT_SUCCESS);
			else if ((auditStatus == 0)) {
				workOrder
						.setStatus(WithDrawConstants.WITHDRAW_RESULT_PROCESS_MANUAL_AUDIT_FAIL);
				workOrder.setAuditFailReason(auditFailReason == null ? ""
						: auditFailReason);
			}
			int count = 0;
			try {
				boolean b = this.withdrawWorkDao.update("WF.auditUpdate_2",
						workOrder);
				if (!b) {
					log
							.error("update workorderorder error...,maybe this order have been updated,workorderId is:"
									+ workOrder.getWorkOrderky());
					WithdrawWorkorder wkorder = (WithdrawWorkorder) this.withdrawWorkDao
							.findObjectByCriteria("WF.queryWorkOrderById",
									workOrderId);
					seqId = String.valueOf(wkorder.getOrderSeq());
				} else
					this.withdrawService.withdrawLogWFLogger(map);
			} catch (Exception e) {
				log.error("update workorderorder error...,workorderId is:"
						+ workOrder.getWorkOrderky(), e);
				WithdrawWorkorder wkorder = (WithdrawWorkorder) this.withdrawWorkDao
						.findObjectByCriteria("WF.queryWorkOrderById", workOrderId);
				seqId = String.valueOf(wkorder.getOrderSeq());

			}
		} catch (Exception e) {
			log.error("withdraw Result Manual Process error...workOrderKy is; "
					+ workOrder.getWorkOrderky(), e);
			throw new PossException("withdraw Result Manual Process error...",
					ExceptionCodeEnum.UN_KNOWN_EXCEPTION, e);
		}
		return seqId;
	}

	// 手工处理提现结果复审节点
	@Override
	public String withdrawResManualProcReAudit(Long workOrderId,
			String reAuditBackReason, int auditStatus, Map<String, Object> map) {
		boolean isSuccAccounting = false;
		String seqId = "";
		int status = 0;
		WithdrawWorkorder workOrder = null;
		try {
			workOrder = new WithdrawWorkorder();
			workOrder = this.withdrawWorkDao.queryWorkOrderById(
					"WF.queryWorkOrderById", workOrderId);
			// 获得订单
			WithdrawWorkorder workPara = new WithdrawWorkorder();
			List<WithdrawOrder> orderList = null;
			Map<Long, WithdrawOrder> orderMap = null;

			List<String> paraList = new ArrayList<String>();
			paraList.add(workOrderId.toString());
			workPara.setSubList(paraList);

			orderList = this.withdrawOrderDao.findByQuery(
					"WF.queryWithdrawOrderListByWorkKy", workPara);
			boolean needAccount = false;
			// 1.确定，0退回
			if (auditStatus == 1) {
				// 人工审核通过，确认成功。人工审核拒绝，转为工单失败状态
				if (workOrder.getStatus() == WithDrawConstants.WITHDRAW_RESULT_PROCESS_MANUAL_AUDIT_FAIL)
					status = WithDrawConstants.WORKORD_ERROR;
				else if (workOrder.getStatus() == WithDrawConstants.WITHDRAW_RESULT_PROCESS_MANUAL_AUDIT_SUCCESS) {
					status = WithDrawConstants.CONFIRM_SUCCESS;
					isSuccAccounting = true;
				}
				needAccount = true;
			} else if (auditStatus == 0) {
				// 回到生成批次成功状态
				status = WithDrawConstants.GENERATE_BATCHNUM_SUCCESS;
			}
			workOrder.setReAuditBackReason(reAuditBackReason);
			workOrder.setStatus(status);
			WithdrawOrder order = orderList.get(0);
			// 更新工单并且记账
			boolean resultFlag = false;
			try {
				String preStatus = "a.status in(8,9) and a.batch_status = 1	";
				workOrder.setPreStatus(preStatus);
				resultFlag = this.withdrawService.ResManualProcessReAuditRdTx(
						workOrder, order, needAccount, isSuccAccounting);
				if (!resultFlag) {
					WithdrawWorkorder wkorder = (WithdrawWorkorder) this.withdrawWorkDao
							.findObjectByCriteria("WF.queryWorkOrderById",
									workOrderId);
					seqId = String.valueOf(wkorder.getOrderSeq());
					log
							.error("update workorderorder error...,maybe this order have been updated,workorderId is:"
									+ workOrder.getWorkOrderky());
				} else
					this.withdrawService.withdrawLogWFLogger(map);
			} catch (Exception e) {
				log.error("update workorderorder error...,workorderId is:"
						+ workOrder.getWorkOrderky(), e);
				WithdrawWorkorder wkorder = (WithdrawWorkorder) this.withdrawWorkDao
						.findObjectByCriteria("WF.queryWorkOrderById", workOrderId);
				seqId = String.valueOf(wkorder.getOrderSeq());

			}
			if (needAccount && resultFlag) {
				this.withdrawService
						.endWorkFlow(generateWFParameter(workOrder));
			}
		} catch (Exception e) {
			log.error(
					"withdraw Result Manual Process error... workOrderKy is: "
							+ workOrder.getWorkOrderky(), e);
		}
		return seqId;
	}

	private WithdrawWFParameter generateWFParameter(WithdrawWorkorder workOrder) {
		WithdrawWFParameter wfParam = new WithdrawWFParameter();
		wfParam.setAssigner("");
		wfParam.setNodeName(WithDrawConstants.LIQUIDATE_NODE);
		wfParam.setPreviousUser("");
		wfParam.setProcessInstanceId(workOrder.getWorkflowKy());
		wfParam.setProcessKey(WithDrawConstants.PROCESS_NAME);
		wfParam.setTaskMessage("");
		wfParam.setUserId("adminA");
		return wfParam;
	}

	// 确认工作流是否能回滚，当节点>=复核的时候,工作流不能回退
	private boolean checkRoolBack(WithdrawWorkorder workOrder, int auditStatus,
			String nodeName) throws PossException {
		boolean canRoolBack = false;
		if (workOrder.getStatus().intValue() < WithDrawConstants.SECOND_AUDIT_SUCCESS) {
			return true;
		}

		if (workOrder.getStatus().intValue() >= WithDrawConstants.SECOND_AUDIT_SUCCESS) {
			boolean isRoolBack = false;
			if (nodeName.equals(WithDrawConstants.SECOND_AUDIT_NODE)
					&& auditStatus == WithDrawConstants.SECOND_AUDIT_BACK)
				isRoolBack = true;
			else if (nodeName.equals(WithDrawConstants.LIQUIDATE_NODE)
					&& auditStatus == WithDrawConstants.LIQUIDATE_BACK)
				isRoolBack = true;

			if (isRoolBack) {
				if (WithDrawConstants.BATCH_NUM_UNGENERATE != workOrder
						.getBatchStatus().intValue()
						&& WithDrawConstants.BATCH_NUM_REVOKE != workOrder
								.getBatchStatus().intValue()) {
					throw new PossException(
							"workflow can't roolback,STATUS is: "
									+ workOrder.getStatus().intValue()
									+ " and BATCH_STATUS is:"
									+ workOrder.getBatchStatus().intValue(),
							ExceptionCodeEnum.SEND_WORKFLOW_LIQUIDATE_EXCEPTION);
				} else
					canRoolBack = true;
			}
		}
		return canRoolBack;
	}

	@Override
	public List<ExportWithdrawModel> queryExportAuditInfo(String userId,
			String nodeName, WithdrawAuditQueryDTO auditQueryDTO)
			throws Exception {
		final WithdrawAuditQuery auditQuery = new WithdrawAuditQuery();
		BeanUtils.copyProperties(auditQueryDTO, auditQuery);
		List<ExportWithdrawModel> list = null;

		try {
			// 根据审核,复审,分控任务分配,获取不同的提现订单信息
			if (nodeName.equals(WithDrawConstants.AUDIT_NODE))
				list = this.exportWitndrawDao.findByQuery(
						"WF.exportWithdrawAudit", auditQuery);
			else if (nodeName.equals(WithDrawConstants.SECOND_AUDIT_NODE))
				list = this.exportWitndrawDao.findByQuery(
						"WF.exportWithdrawReAudit", auditQuery);
			String bankName = "";
			ProvinceDTO privinceDto = null;
			CityDTO cityDto = null;
			for (ExportWithdrawModel exportModel : list) {
				exportModel.setBankKyStr(this.bankInfoService
						.getBankNameById(matcher(exportModel
								.getBankKy())));

				privinceDto = withdrawService.getProvince(exportModel
						.getBankProvince() == null ? null : Integer
						.valueOf(exportModel.getBankProvince().intValue()));
				cityDto = withdrawService
						.getCity(exportModel.getBankCity() == null ? null
								: Integer.valueOf(exportModel.getBankCity()
										.intValue()));
				exportModel.setBankCityStr(cityDto == null ? "" : cityDto
						.getCityname());
				exportModel.setBankProvinceStr(privinceDto == null ? ""
						: privinceDto.getProvincename());
			}
		} catch (PlatformDaoException e) {
			log.error("export excel error", e);
			throw new Exception("export excel error");
		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fundout.withdraw.service.flowprocess.WithdrawOrderAuditService
	 * #getAuditAndReAuditUsers()
	 */
	/*
	 * @Override public Map<String, Object> getAuditAndReAuditUsers() {
	 * List<String> userList =
	 * userService.findLoginIdByFlowName(WithDrawConstants.PROCESS_NAME,
	 * WithDrawConstants.AUDIT_NODE); userList = (null != userList ? userList :
	 * new ArrayList<String>());
	 * 
	 * List<String> tempList =
	 * userService.findLoginIdByFlowName(WithDrawConstants.PROCESS_NAME,
	 * WithDrawConstants.SECOND_AUDIT_NODE); tempList = (null != tempList ?
	 * tempList : new ArrayList<String>()); if (!tempList.isEmpty()) { for
	 * (String temp : tempList) { if (userList.contains(temp)) { continue; }
	 * else { userList.add(temp); } } } Map<String, Object> result = new
	 * HashMap<String, Object>(); result.put("userInfo", userList); return
	 * result; }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fundout.withdraw.service.flowprocess.WithdrawOrderAuditService
	 * #transmitTask(java.lang.String[], java.lang.String)
	 */
	@Override
	public boolean transmitTask(String[] processKeys, String assigner)
			throws PossException {
		if (null == processKeys || StringUtils.isEmpty(assigner)) {
			log.info("参数为空!...[操作人：" + assigner + ";工作流流程实例ID:" + processKeys
					+ "]");
			return false;
		}

		return true;
	}

	private String createSerchWFFilter(List<String> wfKyList) {
		StringBuffer sb = new StringBuffer();
		if (wfKyList != null) {
			sb.append("(");
			for (String wfId : wfKyList) {
				if (sb.length() != 1)
					sb.append(" or ");
				sb.append("workflow_ky='").append(wfId).append("'");
			}
			sb.append(")");
		}
		return sb.toString();
	}

	private String getassigner(String node, String workFlowKey) {
		String assigner = "";
		List<WorkFlowHistory> hisList = this.withdrawService
				.queryWorkFlowHistoryInfo(workFlowKey);
		for (WorkFlowHistory his : hisList) {
			if (his.getNodeName() != null && his.getNodeName().equals(node)) {
				assigner = his.getAssignee();
				break;
			} else
				continue;
		}
		return assigner;
	}

	private String matcher(String str) {
		Pattern pattern = Pattern.compile("(\\d)+");
		Matcher matcher = pattern.matcher(str);

		if (matcher.find()) {
			return str.substring(matcher.start(), matcher.end());
		} else {
			return null;
		}
	}

	@Override
	public String delay(WithdrawWFParameter wfPara, String[] wkKeys,
			String auditRemark) throws Exception {
		WithdrawWorkorder workPara = new WithdrawWorkorder();
		StringBuffer seqId = new StringBuffer();
		List<String> subList = new ArrayList<String>();
		for (int i = 0; i < wkKeys.length; i++)
			subList.add(wkKeys[i]);
		workPara.setSubList(subList);

		List<WithdrawWorkorder> workOrderList = this.withdrawWorkDao
				.findByQuery("WF.queryWorkOrderListById", workPara);
		// 获得此批提交审核工单的实例流程号
		for (WithdrawWorkorder workOrder : workOrderList) {
			// 组装工作流信息
			Map<String, Object> map = new HashMap<String, Object>();

			map.put(WithdrawWFParameter.WF_PARAMETER_KEY_ASSIGNER, wfPara
					.getAssigner());
			map.put(WithdrawWFParameter.WF_PARAMETER_KEY_NODENAME,
					WithDrawConstants.AUDIT_NODE);
			map.put(WithdrawWFParameter.WF_PARAMETER_KEY_PREVIOUSUSER, wfPara
					.getPreviousUser());
			map.put(WithdrawWFParameter.WF_PARAMETER_KEY_PROCESSINSTANCEID,
					workOrder.getWorkflowKy());
			map.put(WithdrawWFParameter.WF_PARAMETER_KEY_PROCESSKEY, wfPara
					.getProcessKey());
			map.put(WithdrawWFParameter.WF_PARAMETER_KEY_TASKMESSAGE, wfPara
					.getTaskMessage());
			map.put(WithdrawWFParameter.WF_PARAMETER_KEY_USERID, wfPara
					.getUserId());

			// 更新工单并且推动工作流
			int count = 0;
			try {
				count = this.withdrawService.firstAudit2DelayRdTx(workOrder, 3,
						WithDrawConstants.AUDIT_NODE, map, auditRemark);
			} catch (Exception e) {
				log
						.error("update workorderorder error...,maybe this order have been updated,workorderId is:"
								+ workOrder.getWorkOrderky());
			}
			if (count != 1)
				seqId.append(workOrder.getOrderSeq()).append(",");
		}
		String result = seqId.toString();
		if (result.length() > 0)
			result = result.substring(0, seqId.toString().length() - 1);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fundout.withdraw.service.flowprocess.WithdrawOrderAuditService
	 * #startWfAndCreateOrderRdTx(java.lang.String)
	 */
	@Override
	public void startWfAndCreateOrderRdTx(String orderId) throws PossException {
		try {
			// 启动工作流，得到工作流实例ID
			String workFlowKey = null;
			// 生成工单，并更新工单的工作流实例ID
			WithdrawWorkorder workOrder = new WithdrawWorkorder();

			workOrder.setOrderSeq(Long.valueOf(orderId));
			workOrder.setWorkflowKy(workFlowKey == null ? "" : workFlowKey);
			workOrder.setStatus(WithDrawConstants.AUDIT_INIT);

			// 批次状态，插入默认为0
			workOrder.setBatchStatus(0);
			// 生成工单
			this.withdrawWorkDao.create("WF.create", workOrder);
		} catch (Exception e) {
			// 有异常通知JMS，让JMS之后进行补单处理
			log.error("startWorkFlowRdTx is error...,orderId=" + orderId, e);
			throw new PossException("Start workorder error...",
					ExceptionCodeEnum.SEND_WORKFLOW_LIQUIDATE_EXCEPTION, e);
		}

	}
}
