/**
 * 
 */
package com.pay.fo.order.service.batchpayment.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.dao.batchpayment.BatchPaymentOrderDAO;
import com.pay.fo.order.dto.Order;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.dto.base.PayToAcctOrderDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentOrderDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentReqBaseInfoDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentToAcctReqDetailDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentToBankReqDetailDTO;
import com.pay.fo.order.model.base.FundoutOrder;
import com.pay.fo.order.model.base.PayToAcctOrder;
import com.pay.fo.order.model.batchpayment.BatchPaymentOrder;
import com.pay.fo.order.model.batchpayment.BatchPaymentOrderExample;
import com.pay.fo.order.model.batchpayment.BatchPaymentTotalInfo;
import com.pay.fo.order.service.base.FundoutOrderProcessService;
import com.pay.fo.order.service.base.PayToAcctOrderProcessService;
import com.pay.fo.order.service.batchpay2acct.BatchPay2AcctOrderService;
import com.pay.fo.order.service.batchpayment.BatchPaymentOrderProcessService;
import com.pay.fo.order.service.batchpayment.BatchPaymentReqBaseInfoService;
import com.pay.fo.order.service.batchpayment.BatchPaymentToAcctReqDetailService;
import com.pay.fo.order.service.batchpayment.BatchPaymentToBankReqDetailService;
import com.pay.fundout.helper.BatchPaymenttoacctreqdetailStatus;
import com.pay.fundout.helper.BatchPaymenttobankreqdetailStatus;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * @author NEW
 * 
 */
public class BatchPaymentOrderServiceImpl extends BatchPaymentOrderCommService {

	private BatchPaymentOrderDAO batchPaymentOrderDAO;

	/**
	 * 批量付款订单服务类
	 */
	private BatchPaymentOrderProcessService batchPaymentOrderProcessService;

	/**
	 * 批量付款请求基本信息服务类
	 */
	private BatchPaymentReqBaseInfoService batchPaymentReqBaseInfoService;

	/**
	 * 批付到账户请求明细服务类
	 */
	private BatchPaymentToAcctReqDetailService batchPaymentToAcctReqDetailService;

	/**
	 * 付款到账户订单处理服务类
	 */
	private PayToAcctOrderProcessService payToAcctOrderProcessService;

	/**
	 * 批量付款到账户订单服务类
	 */
	private BatchPay2AcctOrderService batchPay2AcctOrderService;

	/**
	 * 批付到银行请求明细服务类
	 */
	private BatchPaymentToBankReqDetailService batchPaymentToBankReqDetailService;

	/**
	 * 出款订单处理服务类
	 */
	private FundoutOrderProcessService fundoutOrderProcessService;

	/**
	 * 生成工单通知队列
	 */
	private String queueName;

	/**
	 * 消息通知服务类
	 */
	private NotifyFacadeService notifyFacadeService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fo.order.service.OrderService#createOrder(com.pay.fo.order
	 * .dto.Order)
	 */
	@Override
	public Long createOrder(Order order) {
		if (order instanceof BatchPaymentOrderDTO) {
			BatchPaymentOrderDTO dto = (BatchPaymentOrderDTO) order;
			BatchPaymentOrder model = new BatchPaymentOrder();
			BeanUtils.copyProperties(dto, model);
			return batchPaymentOrderDAO.insert(model);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fo.order.service.OrderService#updateOrder(com.pay.fo.order
	 * .dto.Order)
	 */
	@Override
	public boolean updateOrder(Order upOrder) {
		if (upOrder instanceof BatchPaymentOrderDTO) {
			BatchPaymentOrderDTO dto = (BatchPaymentOrderDTO) upOrder;
			BatchPaymentOrder model = new BatchPaymentOrder();
			BeanUtils.copyProperties(dto, model);

			return batchPaymentOrderDAO.updateByPrimaryKeySelective(model) == 1 ? true
					: false;

		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fo.order.service.OrderService#updateOrderStatus(com.pay
	 * .fo.order.dto.Order, int)
	 */
	@Override
	public boolean updateOrderStatus(Order upOrder, int oldStatus) {
		if (upOrder instanceof BatchPaymentOrderDTO) {
			BatchPaymentOrderDTO dto = (BatchPaymentOrderDTO) upOrder;
			BatchPaymentOrder model = new BatchPaymentOrder();
			model.setOrderStatus(dto.getOrderStatus());
			model.setUpdateDate(dto.getUpdateDate());

			BatchPaymentOrderExample example = new BatchPaymentOrderExample();
			example.createCriteria().andOrderIdEqualTo(dto.getOrderId())
					.andOrderStatusEqualTo(oldStatus);

			return batchPaymentOrderDAO
					.updateByExampleSelective(model, example) == 1 ? true
					: false;

		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.fo.order.service.OrderService#getOrder(java.lang.Long)
	 */
	@Override
	public Order getOrder(Long orderId) {
		BatchPaymentOrder model = batchPaymentOrderDAO
				.selectByPrimaryKey(orderId);
		if (model != null) {
			BatchPaymentOrderDTO dto = new BatchPaymentOrderDTO();
			BeanUtils.copyProperties(model, dto);
			return dto;
		}
		return null;
	}

	/**
	 * @param batchPaymentOrderDAO
	 *            the batchPaymentOrderDAO to set
	 */
	public void setBatchPaymentOrderDAO(
			BatchPaymentOrderDAO batchPaymentOrderDAO) {
		this.batchPaymentOrderDAO = batchPaymentOrderDAO;
	}

	public void setBatchPaymentOrderProcessService(
			BatchPaymentOrderProcessService batchPaymentOrderProcessService) {
		this.batchPaymentOrderProcessService = batchPaymentOrderProcessService;
	}

	public void setBatchPaymentReqBaseInfoService(
			BatchPaymentReqBaseInfoService batchPaymentReqBaseInfoService) {
		this.batchPaymentReqBaseInfoService = batchPaymentReqBaseInfoService;
	}

	public void setBatchPaymentToAcctReqDetailService(
			BatchPaymentToAcctReqDetailService batchPaymentToAcctReqDetailService) {
		this.batchPaymentToAcctReqDetailService = batchPaymentToAcctReqDetailService;
	}

	public void setPayToAcctOrderProcessService(
			PayToAcctOrderProcessService payToAcctOrderProcessService) {
		this.payToAcctOrderProcessService = payToAcctOrderProcessService;
	}

	public void setBatchPaymentToBankReqDetailService(
			BatchPaymentToBankReqDetailService batchPaymentToBankReqDetailService) {
		this.batchPaymentToBankReqDetailService = batchPaymentToBankReqDetailService;
	}

	public void setBatchPay2AcctOrderService(
			BatchPay2AcctOrderService batchPay2AcctOrderService) {
		this.batchPay2AcctOrderService = batchPay2AcctOrderService;
	}

	public void setFundoutOrderProcessService(
			FundoutOrderProcessService fundoutOrderProcessService) {
		this.fundoutOrderProcessService = fundoutOrderProcessService;
	}

	@Override
	public List getCompleteBatchPay2AcctOrders() {
		return batchPaymentOrderDAO.getCompleteBatchPay2AcctOrders();
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public void setNotifyFacadeService(NotifyFacadeService notifyFacadeService) {
		this.notifyFacadeService = notifyFacadeService;
	}

	@Override
	public List getCompleteBatchPay2BankOrders() {
		return batchPaymentOrderDAO.getCompleteBatchPay2BankOrders();
	}

	@Override
	public BatchPaymentTotalInfo getTotalComplateBatchPay2BankOrder(
			Long parentOrderId) {
		return batchPaymentOrderDAO
				.getTotalComplateBatchPay2BankOrder(parentOrderId);
	}

	@Override
	public BatchPaymentTotalInfo getTotalComplateBatchPay2AcctOrder(
			Long parentOrderId) {
		return batchPaymentOrderDAO
				.getTotalComplateBatchPay2AcctOrder(parentOrderId);
	}

	@Override
	public BatchPaymentOrderDTO getOrder(Long memberCode, Integer orderType,
			String businessBatchNo) {
		BatchPaymentOrderExample example = new BatchPaymentOrderExample();
		example.createCriteria().andPayerMemberCodeEqualTo(memberCode)
				.andOrderTypeEqualTo(orderType).andBusinessBatchNoEqualTo(
						businessBatchNo);
		List results = batchPaymentOrderDAO.selectByExample(example);
		if (null != results && results.size() > 0) {
			BatchPaymentOrder model = (BatchPaymentOrder) results.get(0);
			BatchPaymentOrderDTO dto = new BatchPaymentOrderDTO();
			BeanUtils.copyProperties(model, dto);
			return dto;
		}
		return null;
	}

	@Override
	public void createDetailOrder(final Long batchPaymentOrderId,
			final Integer orderType) {

		BatchPaymentOrderDTO order = batchPaymentOrderProcessService
				.getOrder(batchPaymentOrderId);
		Assert.notNull(order, "没有找到相匹配的批量付款订单信息:" + batchPaymentOrderId);

		BatchPaymentReqBaseInfoDTO base = batchPaymentReqBaseInfoService
				.getBatchPaymentReqBaseInfo(order.getPayerMemberCode(),
						orderType, order.getBusinessBatchNo());
		Assert.notNull(base, "没有找到相匹配的批量付款请求:" + order.getBusinessBatchNo());

		List details = null;
		if (OrderType.BATCHPAY2ACCT.getValue() == orderType) {
			details = batchPaymentToAcctReqDetailService
					.getCreateOrderDetailList(base.getRequestSeq(),
							BatchPaymenttoacctreqdetailStatus.UNCREATE.getValue());

			if (null != details && !details.isEmpty()) {
				for (int i = 0; i < details.size(); i++) {
					BatchPaymentToAcctReqDetailDTO detail = (BatchPaymentToAcctReqDetailDTO) details
							.get(i);
					try {
						doProcess(order, detail);
					} catch (Exception e) {
						LogUtil
								.error(getClass(), e.toString(),
										OPSTATUS.EXCEPTION, "batchNum:"
												+ base.getBusinessBatchNo(),
										"detailId:" + detail.getDetailSeq(),
										"", "", "");
					}
				}
			}

		} else if (OrderType.BATCHPAY2BANK.getValue() == orderType) {
			details = batchPaymentToBankReqDetailService
					.getCreateOrderDetailList(base.getRequestSeq(),BatchPaymenttobankreqdetailStatus.UNCREATE.getValue());
			if (null != details && !details.isEmpty()) {
				for (int i = 0; i < details.size(); i++) {
					BatchPaymentToBankReqDetailDTO detail = (BatchPaymentToBankReqDetailDTO) details
							.get(i);
					try {
						doProcess(order, detail);
					} catch (Exception e) {
						LogUtil
								.error(getClass(), e.toString(),
										OPSTATUS.EXCEPTION, "batchNum:"
												+ base.getBusinessBatchNo(),
										"detailId:" + detail.getDetailSeq(),
										"", "", "");
					}
				}
			}
		}
	}

	/**
	 * 创建明细订单
	 * 
	 * @param parentOrderId
	 */
	public void createDetailOrder(BatchPaymentOrderDTO order,
			BatchPaymentToAcctReqDetailDTO detail) {

		Assert.notNull(order, "BatchPaymentOrderDTO must be not null");
		Assert.notNull(detail,
				"BatchPaymentToAcctReqDetailDTO must be not null");
		try {
			doProcess(order, detail);
		} catch (Exception e) {
			LogUtil.error(getClass(), e.toString(), OPSTATUS.EXCEPTION,
					"batchNum:" + order.getBusinessBatchNo(), "detailId:"
							+ detail.getDetailSeq(), "", "", "");
		}
	}

	/**
	 * 创建批付到银行明细订单
	 * 
	 * @param parentOrderId
	 */
	public void createDetailOrder(BatchPaymentOrderDTO order,
			BatchPaymentToBankReqDetailDTO detail) {

		try {
			doProcess(order, detail);
		} catch (Exception e) {
			LogUtil.error(getClass(), e.toString(), OPSTATUS.EXCEPTION,
					"batchNum:" + order.getBusinessBatchNo(), "detailId:"
							+ detail.getDetailSeq(), "", "", "");
		}
	}

	private void doProcess(BatchPaymentOrderDTO order,
			BatchPaymentToAcctReqDetailDTO detail) throws Exception {

		detail.setOrderStatus(BatchPaymenttoacctreqdetailStatus.CREATED
				.getValue());
		detail.setUpdateDate(new Date());
		PayToAcctOrderDTO dto = buildDetailOrder(order, detail);
		if (batchPaymentToAcctReqDetailService.updateStatus(detail,
				BatchPaymenttoacctreqdetailStatus.UNCREATE.getValue())) {
			Long detaiOrderId = payToAcctOrderProcessService
					.createOrderRnTx(dto);
			dto.setOrderId(detaiOrderId);
			batchPay2AcctOrderService.foProcessSuccess(dto);
		}
	}

	private void doProcess(BatchPaymentOrderDTO order,
			BatchPaymentToBankReqDetailDTO detail) throws Exception {

		detail.setOrderStatus(1);
		detail.setUpdateDate(new Date());
		FundoutOrderDTO dto = buildDetailOrder(order, detail);
		if (batchPaymentToBankReqDetailService.updateStatus(detail,
				BatchPaymenttobankreqdetailStatus.UNCREATE.getValue())) {
			Long detaiOrderId = fundoutOrderProcessService.createOrderRnTx(dto);
			// 发送通知生成工单
			if (!StringUtil.isNull(detaiOrderId)) {
				String jsonStr = JSonUtil.toJSonString(detaiOrderId);
				Notify2QueueRequest request = new Notify2QueueRequest();
				request.setQueueName(queueName);
				request.setTargetObject(jsonStr);
				notifyFacadeService.sendRequest(request);
			}
		}
	}
	
	/**
	 * 根据总订单号获取到账户子订单
	 * @param parentId
	 * @return
	 */
	public List<PayToAcctOrder> getAcctDetailOrderByParentId(final Long parentId) {
		return batchPaymentOrderDAO.getAcctDetailOrderByParentId(parentId);
	}
	
	/**
	 * 根据总订单号获取到银行子订单
	 * @param parentId
	 * @return
	 */
	public List<FundoutOrder> getBankDetailOrderByParentId(final Long parentId) {
		return batchPaymentOrderDAO.getBankDetailOrderByParentId(parentId);
	}
}
