package com.pay.poss.dao.ordercenter.fundout.backfundout.impl;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.dao.ordercenter.fundout.backfundout.OrderCenterBackFundoutDao;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;
import com.pay.poss.service.ordercenter.dto.relationorder.OrderRelationDTO;

/**
 * 出款失败
 * <p>
 * </p>
 * 
 * @author Volcano.Wu
 * @since 2010-12-3
 * @see
 */
@SuppressWarnings("unchecked")
public class OrderCenterBackFundoutDaoImpl extends BaseDAOImpl implements
		OrderCenterBackFundoutDao {

	@Override
	public Page<OrderCenterResultDTO> queryBackFundout(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		return super.findByQuery("context_fundout_backfundout", page, dto);
	}

	/**
	 * 关联出款退款
	 */
	@Override
	public List<OrderRelationDTO> queryBackFundoutRelationList(String sqlId,
			OrderCenterQueryDTO dto) {
		return super.findByQuery(sqlId, dto);
	}

	/**
	 * 订单详情
	 */
	@Override
	public OrderDetailDTO queryOrderDetail(Long orderKy) {
		return (OrderDetailDTO) super.findObjectByCriteria("context_fundout_backfundout_orderdetail",
				orderKy);
	}

}
