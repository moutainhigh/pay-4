package com.pay.poss.service.ordercenter.fundout.impl.withdraw;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.withdraw.dto.flowprocess.WorkFlowHistory;
import com.pay.inf.dao.Page;
import com.pay.poss.dao.ordercenter.fundout.withdraw.OrderCenterWithdrawDao;
import com.pay.poss.service.ordercenter.common.AbstractOrderCenterCommonService;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;

/**
 * 
 * <p>查询提现订单结果列表</p>
 * @author Volcano.Wu
 * @since 2010-11-6
 * @see
 */
public class OrderCenterWithdrawServiceImpl extends AbstractOrderCenterCommonService{

	private static Log logger = LogFactory.getLog(OrderCenterWithdrawServiceImpl.class);
	private OrderCenterWithdrawDao orderCenterWithdrawDao;
	
	public void setOrderCenterWithdrawDao(
			OrderCenterWithdrawDao orderCenterWithdrawDao) {
		this.orderCenterWithdrawDao = orderCenterWithdrawDao;
	}
	

	@Override
	public Map<String, Page<OrderCenterResultDTO>> queryResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		Map<String,Page<OrderCenterResultDTO>> map = new HashMap<String,Page<OrderCenterResultDTO>>();
		map.put("page", orderCenterWithdrawDao.queryWithdrawResultList(page, dto));
		return map;
	}
	
	@Override
	public OrderDetailDTO queryOrderDetail(OrderCenterQueryDTO queryDto) {

		Long orderKy = Long.valueOf(queryDto.getOrderKy());
		OrderDetailDTO orderDetailDTO = orderCenterWithdrawDao
				.queryOrderDetail(orderKy);
		// 获得工作流审核节点历史数据
		List<WorkFlowHistory> wfHisList = this.withdrawOrderAuditService.queryWorkFlowHisInfoByorderkKy(String.valueOf(orderKy));
		orderDetailDTO.setWfHisList(wfHisList);
		return orderDetailDTO;
	}

	@Override
	public Map<String, Object> queryOrderRelation(OrderCenterQueryDTO queryDto) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("relationList", orderCenterWithdrawDao.queryWithdrawRelationList(queryDto));
		return map;
	}

}
