package com.pay.poss.service.ordercenter.fundout.impl.batchpay2bank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.withdraw.dto.flowprocess.WorkFlowHistory;
import com.pay.inf.dao.Page;
import com.pay.poss.dao.ordercenter.fundout.batchpay2bank.OrderCenterBatchPay2BankDao;
import com.pay.poss.service.ordercenter.common.AbstractOrderCenterCommonService;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;
import com.pay.poss.service.ordercenter.fundout.impl.withdraw.OrderCenterWithdrawServiceImpl;

/**
 * 
 * <p>查询批量付款到银行订单结果列表</p>
 * @author Volcano.Wu
 * @since 2010-11-6
 * @see
 */
public class OrderCenterBatchPay2BankServiceImpl extends AbstractOrderCenterCommonService{
	
	private static Log logger = LogFactory.getLog(OrderCenterWithdrawServiceImpl.class);
	private OrderCenterBatchPay2BankDao orderCenterBatchPay2BankDao;
	
	public void setOrderCenterBatchPay2BankDao(
			final OrderCenterBatchPay2BankDao orderCenterBatchPay2BankDao) {
		this.orderCenterBatchPay2BankDao = orderCenterBatchPay2BankDao;
	}

	@Override
	public Map<String, Page<OrderCenterResultDTO>> queryResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		Map<String,Page<OrderCenterResultDTO>> map = new HashMap<String,Page<OrderCenterResultDTO>>();
		map.put("page", orderCenterBatchPay2BankDao.queryBatchPay2BankResultList(page, dto));
		return map;
	}

	@Override
	public OrderDetailDTO queryOrderDetail(OrderCenterQueryDTO queryDto) {

		Long orderKy = Long.valueOf(queryDto.getOrderKy());
		OrderDetailDTO orderDetailDTO = orderCenterBatchPay2BankDao
				.queryOrderDetail(orderKy);
		// 获得工作流审核节点历史数据
		List<WorkFlowHistory> wfHisList = this.withdrawOrderAuditService.queryWorkFlowHisInfoByorderkKy(String.valueOf(orderKy));
		orderDetailDTO.setWfHisList(wfHisList);
		return orderDetailDTO;
	}

	@Override
	public Map<String, Object> queryOrderRelation(OrderCenterQueryDTO queryDto) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("relationList", orderCenterBatchPay2BankDao.queryBatchPay2BankRelationList(queryDto));
		return map;
	}

}
