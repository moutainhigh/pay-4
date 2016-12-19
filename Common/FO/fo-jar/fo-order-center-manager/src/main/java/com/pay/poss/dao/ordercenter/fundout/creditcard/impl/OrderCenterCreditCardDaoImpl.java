package com.pay.poss.dao.ordercenter.fundout.creditcard.impl;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.dao.ordercenter.fundout.creditcard.OrderCenterCreditCardDao;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;
import com.pay.poss.service.ordercenter.dto.relationorder.OrderRelationDTO;

/**
 * 信用卡还款
 * <p>
 * </p>
 * 
 * @author Volcano.Wu
 * @since 2010-11-29
 * @see
 */
@SuppressWarnings("unchecked")
public class OrderCenterCreditCardDaoImpl extends BaseDAOImpl implements
		OrderCenterCreditCardDao {

	@Override
	public OrderDetailDTO queryCreditCardDetail(Long orderKy) {
		return (OrderDetailDTO) super.findObjectByCriteria("context_fundout_ordercenter_orderdetail",
				orderKy);
	}

	@Override
	public Page<OrderCenterResultDTO> queryCreditCard(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		return super.findByQuery("context_fundout_ordercenter_creditcard",
				page, dto);
	}

	@Override
	public List<OrderRelationDTO> queryCreditCardList(OrderCenterQueryDTO dto) {
		return super.findByQuery("withdraw_ordercenter_relation_query", dto);
	}

}
