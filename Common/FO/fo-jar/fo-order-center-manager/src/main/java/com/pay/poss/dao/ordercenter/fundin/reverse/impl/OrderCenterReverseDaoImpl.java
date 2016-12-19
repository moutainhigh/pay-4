package com.pay.poss.dao.ordercenter.fundin.reverse.impl;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.dao.ordercenter.fundin.reverse.OrderCenterReverseDao;
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
public class OrderCenterReverseDaoImpl extends BaseDAOImpl implements
		OrderCenterReverseDao {

	@Override
	public Page<OrderCenterResultDTO> queryRechargeResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		return super.findByQuery(namespace.concat("reverse_ordercenter_query"),
				page, dto);
	}

	@Override
	public OrderDetailDTO queryOrderDetail(Long id) {
		return (OrderDetailDTO) super.findObjectByCriteria(
				namespace.concat("detail_reverse_ordercenter_query"), id);
	}

	@Override
	public List<OrderRelationDTO> queryRefundRelationList(
			OrderCenterQueryDTO dto) {
		return super.findByQuery(
				namespace.concat("reverse_ordercenter_relation_query"), dto);
	}

}
