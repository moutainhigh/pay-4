package com.pay.poss.dao.ordercenter.fundin.traderefund.impl;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.dao.ordercenter.fundin.traderefund.OrderCenterTradeRefundDao;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;
import com.pay.poss.service.ordercenter.dto.relationorder.OrderRelationDTO;

/**
 * <p>
 * </p>
 * 
 * @author Andy.Zhao
 * @since 2010-11-10
 * @see
 */
@SuppressWarnings("unchecked")
public class OrderCenterTradeRefundDaoImpl extends BaseDAOImpl implements
		OrderCenterTradeRefundDao {
	@Override
	public Page<OrderCenterResultDTO> queryTradeRefundResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		return super.findByQuery("traderefund_ordercenter_query", page, dto);
	}

	@Override
	public OrderDetailDTO queryOrderDetail(Long id) {
		return (OrderDetailDTO) super.findObjectByCriteria("detail_traderefund_ordercenter_query", id);
	}

	@Override
	public List<OrderRelationDTO> queryRefundRelationList(
			OrderCenterQueryDTO dto) {
		return super
				.findByQuery("traderefund_ordercenter_relation_query", dto);
	}

}
