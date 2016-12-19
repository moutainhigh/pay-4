package com.pay.poss.dao.ordercenter.fundin.paygateway.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.dao.ordercenter.fundin.paygateway.OrderCenterPayGatewayDao;
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
public class OrderCenterPayGatewayDaoImpl extends BaseDAOImpl implements
		OrderCenterPayGatewayDao {

	@Override
	public Page<OrderCenterResultDTO> queryPayGatewayResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		return super.findByQuery("paygateway_ordercenter_query", page, dto);
	}

	@Override
	public OrderDetailDTO queryOrderDetail(Long id) {
		return (OrderDetailDTO) super.findObjectByCriteria("detail_paygateway_ordercenter_query", id);
	}

	@Override
	public List<OrderRelationDTO> queryRefundRelationList(
			OrderCenterQueryDTO dto) {
		return super.findByQuery("paygateway_ordercenter_relation_query", dto);
	}

	@Override
	public Map<String, Object> queryPaymentExpandInfo(Long paymentOrderNo) {
		return (Map<String, Object>) super.findObjectByCriteria("detail_consume_order_expand_info",
				paymentOrderNo);
	}

}
