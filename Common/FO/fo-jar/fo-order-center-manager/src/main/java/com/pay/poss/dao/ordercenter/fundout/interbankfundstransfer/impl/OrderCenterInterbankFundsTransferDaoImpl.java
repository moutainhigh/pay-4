package com.pay.poss.dao.ordercenter.fundout.interbankfundstransfer.impl;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.dao.ordercenter.fundout.interbankfundstransfer.OrderCenterInterbankFundsTransferDao;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;
import com.pay.poss.service.ordercenter.dto.relationorder.OrderRelationDTO;

/**
 * 跨行转账
 * <p>
 * </p>
 * 
 * @author Volcano.Wu
 * @since 2010-11-29
 * @see
 */
@SuppressWarnings("unchecked")
public class OrderCenterInterbankFundsTransferDaoImpl extends BaseDAOImpl
		implements OrderCenterInterbankFundsTransferDao {

	@Override
	public OrderDetailDTO queryInterbankFundsTransferDetail(Long orderKy) {
		return (OrderDetailDTO) super.findObjectByCriteria("context_fundout_ordercenter_orderdetail",
				orderKy);
	}

	@Override
	public Page<OrderCenterResultDTO> queryInterbankFundsTransfer(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		return super.findByQuery("context_fundout_ordercenter_interbankfundstransfer",
				page, dto);
	}

	@Override
	public List<OrderRelationDTO> queryInterbankFundsTransferList(
			OrderCenterQueryDTO dto) {
		return super.findByQuery("withdraw_ordercenter_relation_query", dto);
	}
}
