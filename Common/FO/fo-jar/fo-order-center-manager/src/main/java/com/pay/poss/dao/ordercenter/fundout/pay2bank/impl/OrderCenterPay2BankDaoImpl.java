package com.pay.poss.dao.ordercenter.fundout.pay2bank.impl;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.dao.ordercenter.fundout.pay2bank.OrderCenterPay2BankDao;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;
import com.pay.poss.service.ordercenter.dto.relationorder.OrderRelationDTO;

/**
 * 付款到银行卡
 * <p>
 * </p>
 * 
 * @author Volcano.Wu
 * @since 2010-11-29
 * @see
 */
@SuppressWarnings("unchecked")
public class OrderCenterPay2BankDaoImpl extends BaseDAOImpl implements
		OrderCenterPay2BankDao {

	@Override
	public OrderDetailDTO queryPay2BankDetail(Long orderKy) {
		return (OrderDetailDTO) super.findObjectByCriteria("context_fundout_ordercenter_orderdetail",
				orderKy);
	}

	@Override
	public Page<OrderCenterResultDTO> queryPay2Bank(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		return super.findByQuery("context_fundout_ordercenter_pay2bank", page,
				dto);
	}

	@Override
	public List<OrderRelationDTO> queryPay2BankList(OrderCenterQueryDTO dto) {
		return super.findByQuery("withdraw_ordercenter_relation_query", dto);
	}

}
