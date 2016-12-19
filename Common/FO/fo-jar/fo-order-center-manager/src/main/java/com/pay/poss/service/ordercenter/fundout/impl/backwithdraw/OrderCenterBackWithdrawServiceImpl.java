package com.pay.poss.service.ordercenter.fundout.impl.backwithdraw;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.poss.dao.ordercenter.fundout.backwithdraw.OrderCenterBackWithdrawDao;
import com.pay.poss.service.ordercenter.common.AbstractOrderCenterCommonService;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;
import com.pay.poss.service.ordercenter.fundout.impl.withdraw.OrderCenterWithdrawServiceImpl;

/**
 * 
 * <p>查询提现退款订单结果列表</p>
 * @author Volcano.Wu
 * @since 2010-11-6
 * @see
 */
public class OrderCenterBackWithdrawServiceImpl extends AbstractOrderCenterCommonService{

	private static Log logger = LogFactory.getLog(OrderCenterWithdrawServiceImpl.class);
	private OrderCenterBackWithdrawDao orderCenterBackWithdrawDao;
	
	public void setOrderCenterBackWithdrawDao(
			final OrderCenterBackWithdrawDao param) {
		this.orderCenterBackWithdrawDao = param;
	}
	@Override
	public Map<String, Page<OrderCenterResultDTO>> queryResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		Map<String,Page<OrderCenterResultDTO>> map = new HashMap<String,Page<OrderCenterResultDTO>>();
		map.put("page", orderCenterBackWithdrawDao.queryBackWithdrawResultList(page, dto));
		return map;
	}
	
	@Override
	public OrderDetailDTO queryOrderDetail(OrderCenterQueryDTO queryDto) {

		Long orderKy = Long.valueOf(queryDto.getOrderKy());

		OrderDetailDTO orderDetailDTO = orderCenterBackWithdrawDao
				.queryOrderDetail(orderKy);

		return orderDetailDTO;
	}
	
	@Override
	public Map<String, Object> queryOrderRelation(OrderCenterQueryDTO queryDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("relationList", orderCenterBackWithdrawDao.queryRelationList(queryDto));
		return map;
	}
	


}
