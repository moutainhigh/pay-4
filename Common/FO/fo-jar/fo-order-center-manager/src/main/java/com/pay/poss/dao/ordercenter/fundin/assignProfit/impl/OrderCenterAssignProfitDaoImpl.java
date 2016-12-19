package com.pay.poss.dao.ordercenter.fundin.assignProfit.impl;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.dao.ordercenter.fundin.assignProfit.OrderCenterAssignProfitDao;
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
public class OrderCenterAssignProfitDaoImpl extends BaseDAOImpl implements
		OrderCenterAssignProfitDao {

	@Override
	public Page<OrderCenterResultDTO> queryAssignProfitResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		return super.findByQuery(
				namespace.concat("assignProfit_ordercenter_query"), page, dto);
	}

	@Override
	public List<OrderRelationDTO> queryAssignProfitRelationList(
			OrderCenterQueryDTO dto) {
		return super.findByQuery(
				namespace.concat("assignProfit_ordercenter_relation_query"),
				dto);
	}

	@Override
	public OrderDetailDTO queryOrderDetail(Long id) {
		return (OrderDetailDTO) super.findObjectByCriteria(
				namespace.concat("detail_assignProfit_ordercenter_query"), id);
	}

}
