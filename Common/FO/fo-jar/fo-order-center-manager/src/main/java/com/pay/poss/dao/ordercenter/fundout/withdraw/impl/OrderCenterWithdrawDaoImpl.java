package com.pay.poss.dao.ordercenter.fundout.withdraw.impl;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.dao.ordercenter.fundout.withdraw.OrderCenterWithdrawDao;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;
import com.pay.poss.service.ordercenter.dto.relationorder.OrderRelationDTO;

/**
 * 
 * <p>
 * 查询提现订单结果列表Dao
 * </p>
 * 
 * @author Volcano.Wu
 * @since 2010-11-6
 * @see
 */
@SuppressWarnings("unchecked")
public class OrderCenterWithdrawDaoImpl extends BaseDAOImpl implements
		OrderCenterWithdrawDao {

	@Override
	public Page<OrderCenterResultDTO> queryWithdrawResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		return super.findByQuery("context_fundout_ordercenter_withdraw", page,
				dto);
	}

	@Override
	public OrderDetailDTO queryOrderDetail(Long orderKy) {
		return (OrderDetailDTO) super.findObjectByCriteria("context_fundout_ordercenter_orderdetail",
				orderKy);
	}

	@Override
	public List<OrderRelationDTO> queryWithdrawRelationList(
			OrderCenterQueryDTO dto) {
		return super.findByQuery("withdraw_ordercenter_relation_query", dto);
	}

}
