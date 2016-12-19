package com.pay.poss.service.ordercenter.fundout.impl.pay2bank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.withdraw.dto.flowprocess.WorkFlowHistory;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawOrderAuditService;
import com.pay.inf.dao.Page;
import com.pay.poss.dao.ordercenter.fundout.pay2bank.OrderCenterPay2BankDao;
import com.pay.poss.service.ordercenter.common.AbstractOrderCenterCommonService;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;
import com.pay.poss.service.ordercenter.fundout.impl.withdraw.OrderCenterWithdrawServiceImpl;

/**
 * 付款到银行卡
 * <p></p>
 * @author Volcano.Wu
 * @since 2010-11-26
 * @see
 */
public class OrderCenterPay2BankServiceImpl extends AbstractOrderCenterCommonService{

	private static Log logger = LogFactory.getLog(OrderCenterWithdrawServiceImpl.class);
	private  OrderCenterPay2BankDao orderCenterPay2BankDao;
	
	public void setOrderCenterPay2BankDao(
			OrderCenterPay2BankDao orderCenterPay2BankDao) {
		this.orderCenterPay2BankDao = orderCenterPay2BankDao;
	}

	@Override
	public OrderDetailDTO queryOrderDetail(OrderCenterQueryDTO queryDto) {
		
		Long orderKy = Long.valueOf(queryDto.getOrderKy());
		OrderDetailDTO orderDetailDTO = orderCenterPay2BankDao
				.queryPay2BankDetail(orderKy);
		// 获得工作流审核节点历史数据
		List<WorkFlowHistory> wfHisList = this.withdrawOrderAuditService.queryWorkFlowHisInfoByorderkKy(String.valueOf(orderKy));
		orderDetailDTO.setWfHisList(wfHisList);

		return orderDetailDTO;
	}

	@Override
	public Map<String, Object> queryOrderRelation(OrderCenterQueryDTO queryDto) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("relationList", orderCenterPay2BankDao.queryPay2BankList(queryDto));
		return map;
	}

	@Override
	public Map<String, Page<OrderCenterResultDTO>> queryResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		Map<String, Page<OrderCenterResultDTO>> map = new HashMap<String,Page<OrderCenterResultDTO>>();
		map.put("page", orderCenterPay2BankDao.queryPay2Bank(page, dto));
		return map;
	}

}
