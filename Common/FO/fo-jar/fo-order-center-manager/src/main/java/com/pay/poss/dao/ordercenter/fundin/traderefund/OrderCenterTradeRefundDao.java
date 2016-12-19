package com.pay.poss.dao.ordercenter.fundin.traderefund;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;
import com.pay.poss.service.ordercenter.dto.relationorder.OrderRelationDTO;

/**
 * 
 * <p>
 * 查询网关交易退款结果列表Dao
 * </p>
 * 
 * @author Andy.Zhao
 * @since 2010-11-10
 * @see
 */
public interface OrderCenterTradeRefundDao extends BaseDAO {
	/**
	 * 查询网关交易退款分页列表
	 * 
	 * @param page
	 * @param dto
	 * @return
	 */
	Page<OrderCenterResultDTO> queryTradeRefundResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto);

	/**
	 * 网关交易退款订单详情
	 * 
	 * @param id
	 * @return OrderDetailDTO
	 */
	OrderDetailDTO queryOrderDetail(Long id);

	/**
	 * 查询网关交易退款关联订单列表
	 * 
	 * @param dto
	 * @return
	 */
	List<OrderRelationDTO> queryRefundRelationList(OrderCenterQueryDTO dto);
}
