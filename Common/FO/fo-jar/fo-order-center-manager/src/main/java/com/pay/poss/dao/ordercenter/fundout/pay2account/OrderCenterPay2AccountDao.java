package com.pay.poss.dao.ordercenter.fundout.pay2account;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;

/**
 * 
 * <p>
 * 查询付款到账户订单结果列表Dao
 * </p>
 * 
 * @author Volcano.Wu
 * @since 2010-11-6
 * @see
 */
public interface OrderCenterPay2AccountDao extends BaseDAO {
	/**
	 * 查询付款到账户交易分页列表
	 * 
	 * @param page
	 * @param dto
	 * @return
	 */
	Page<OrderCenterResultDTO> queryPay2AccountResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto);

	/**
	 * 订单详情
	 * 
	 * @param id
	 * @return OrderDetailDTO
	 */
	OrderDetailDTO queryOrderDetail(Long orderKy);
}
