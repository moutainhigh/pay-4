package com.pay.fundout.bsp.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.account.constantenum.PayForEnum;
import com.pay.common.WorkorderAuditStatus;
import com.pay.fundout.bsp.service.BspWithdrawAuditService;
import com.pay.fundout.service.OrderStatus;
import com.pay.fundout.withdraw.dao.bsp.BspWithdrawAuditDao;
import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.dto.bsp.BspWithdrawAuditResultDTO;
import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.fundout.withdraw.service.workorder.WorkorderService;
import com.pay.inf.dao.Page;
import com.pay.pe.dto.AccountingDto;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.poss.dto.withdraw.order.OrderFailProcAlertModel;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.OrderCallBackService;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;
import com.pay.poss.service.withdraw.orderafterproc.OrderAfterProcService;
import com.pay.poss.service.withdraw.orderafterproc.impl.OrderAfterFailProcAlertHandler;
import com.pay.util.DateUtil;
import com.pay.util.JSonUtil;

/**
 * 提现审核
 * <p>
 * </p>
 * 
 * @author wucan
 * @since 2011-6-30
 * @see
 */
public class BspWithdrawAuditServiceImpl implements BspWithdrawAuditService {

	private Log logger = LogFactory.getLog(BspWithdrawAuditServiceImpl.class);
	private BspWithdrawAuditDao bspwithdrawauditdao;
	// 通知消息服务
	private NotifyFacadeService notifyFacadeService;
	// 队列名称
	private String queueName;
	// 提现订单服务
	private WithdrawOrderService withdrawOrderService;
	// 更新余额
	private OrderAfterProcService orderAfterProcService;
	private OrderCallBackService orderCallBackService;
	private AccountingService accountingService;
	// 更新余额失败记录报警日志
	private OrderAfterFailProcAlertHandler orderAfterFailProcAlertHandler;
	private WorkorderService workorderService;

	// set注入
	public void setBspwithdrawauditdao(BspWithdrawAuditDao bspwithdrawauditdao) {
		this.bspwithdrawauditdao = bspwithdrawauditdao;
	}

	public void setNotifyFacadeService(NotifyFacadeService notifyFacadeService) {
		this.notifyFacadeService = notifyFacadeService;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public void setWorkorderService(WorkorderService workorderService) {
		this.workorderService = workorderService;
	}

	public void setWithdrawOrderService(
			WithdrawOrderService withdrawOrderService) {
		this.withdrawOrderService = withdrawOrderService;
	}

	public void setOrderAfterProcService(
			OrderAfterProcService orderAfterProcService) {
		this.orderAfterProcService = orderAfterProcService;
	}

	public void setOrderCallBackService(
			OrderCallBackService orderCallBackService) {
		this.orderCallBackService = orderCallBackService;
	}

	public void setAccountingService(AccountingService accountingService) {
		this.accountingService = accountingService;
	}

	public void setOrderAfterFailProcAlertHandler(
			OrderAfterFailProcAlertHandler orderAfterFailProcAlertHandler) {
		this.orderAfterFailProcAlertHandler = orderAfterFailProcAlertHandler;
	}

	@Override
	public Page<Map<String, Object>> queryResultList(Map<String, Object> map,
			Integer pageNo, Integer pageSize) {
		return bspwithdrawauditdao.queryResultList(map, pageNo, pageSize);
	}

	@Override
	public BspWithdrawAuditResultDTO view(Map<String, Object> map) {
		BspWithdrawAuditResultDTO resultDTO = new BspWithdrawAuditResultDTO();
		Map<String, Object> resultMap = bspwithdrawauditdao.view(map);
		resultDTO.setOrderSeq(String.valueOf(resultMap.get("ORDERSEQ")));
		resultDTO.setOrderType(String.valueOf(resultMap.get("ORDERTYPE")));
		resultDTO.setCreateDate(DateUtil.parse("yyyy-MM-dd", String
				.valueOf(resultMap.get("CREATEDATE"))));
		resultDTO.setMemberName(String.valueOf(resultMap.get("MEMBERNAME")));
		resultDTO.setAmount(new BigDecimal(String.valueOf(resultMap
				.get("AMOUNT"))));
		return resultDTO;
	}

	/**
	 * 通过
	 */
	@Override
	public boolean approved(Map<String, Object> map) {
		boolean bl = true;
		try {
			// 更新Bsp工单
			map.put("status", WorkorderAuditStatus.PASS.getValue());
			if (bspwithdrawauditdao.updateBspWorkorder(map)) {
				// 发送消息,创建后台工单
				String jsonStr = JSonUtil.toJSonString(map.get("orderSeq"));
				notifyFacadeService
						.sendRequest(buildNotify2QueueRequest(jsonStr));
			}
		} catch (Exception e) {
			e.printStackTrace();
			bl = false;
		}
		return bl;
	}

	// 构建对象产生
	private Notify2QueueRequest buildNotify2QueueRequest(String jsonStr) {
		Notify2QueueRequest request = new Notify2QueueRequest();
		request.setQueueName(queueName);
		request.setTargetObject(jsonStr);
		return request;
	}

	/**
	 * 拒绝
	 */
	@Override
	public boolean refuse(Map<String, Object> map) {

		// 更新Bsp工单
		map.put("status", WorkorderAuditStatus.REJECT.getValue());
		boolean flag = false;

		//更新到工单进行后续处理
		if (workorderService.updateBspWorkorderRnTx(map)) {
			HandlerParam handlerParam = new HandlerParam();
			// 这里只传进了个人.企业和个人记账规则相同.
			Long orderSeq = Long.valueOf(String.valueOf(map.get("orderSeq")));
			// 设置订单状态为112提现失败
			AccountingDto accountingDto = constructAccountingVo(handlerParam,
					orderSeq);
			handlerParam.setAccountingDto(accountingDto);
			try {
				flag = orderAfterProcService.process(handlerParam,
						orderCallBackService, accountingService);
				if (!flag) {
					OrderFailProcAlertModel result = new OrderFailProcAlertModel();
					result.setOrderSeq(orderSeq);
					result.setOrderStatus(OrderStatus.INIT.getValue());
					result.setFailReason("更新余额失败");
					result.setAppFrom("提现");
					result.setUpdateTime(new Date());
					orderAfterFailProcAlertHandler.execute(result);
				}
			} catch (Exception e) {
				logger.error("do update work_order error:", e);
				flag = false;
			}
		}

		return flag;
	}

	private AccountingDto constructAccountingVo(HandlerParam handlerParam,
			Long orderSeq) {
		WithdrawOrderAppDTO withdrawOrderAppDTO = new WithdrawOrderAppDTO();
		withdrawOrderAppDTO.setSequenceId(orderSeq);
		withdrawOrderAppDTO.setStatus(112L);
		handlerParam.setBaseOrderDto(withdrawOrderAppDTO);
		WithdrawOrderAppDTO orderDto = withdrawOrderService
				.getWithdrawOrder(orderSeq);
		AccountingDto accountingDto = new AccountingDto();
		accountingDto.setAmount(orderDto.getAmount());
		accountingDto.setBankCode(orderDto.getWithdrawBankCode());
		accountingDto.setHasCaculatedPrice(true);
		accountingDto.setOrderAmount(orderDto.getOrderAmount());
		accountingDto.setOrderId(orderDto.getSequenceId());
		accountingDto.setPayee(orderDto.getMemberCode());
		accountingDto.setPayeeFee(Math.abs(orderDto.getFee()));
		return accountingDto;
	}

}
