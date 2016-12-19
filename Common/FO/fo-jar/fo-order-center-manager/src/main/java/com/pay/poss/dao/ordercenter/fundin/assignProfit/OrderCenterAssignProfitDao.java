package com.pay.poss.dao.ordercenter.fundin.assignProfit;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;
import com.pay.poss.service.ordercenter.dto.relationorder.OrderRelationDTO;

/**
 * <p>
 * </p>
 * 
 * @author Andy.Zhao
 * @since 2010-12-21
 * @see
 */
public interface OrderCenterAssignProfitDao extends BaseDAO {
	/**
	 * 查询网关分账结果分页列表
	 * 
	 * @param page
	 * @param dto
	 * @return
	 */
	Page<OrderCenterResultDTO> queryAssignProfitResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto);

	/**
	 * 查询网关分账关联订单列表
	 * 
	 * @param dto
	 * @return
	 */
	List<OrderRelationDTO> queryAssignProfitRelationList(OrderCenterQueryDTO dto);

	/**
	 * 网关分账订单详情
	 * 
	 * @param id
	 * @return OrderDetailDTO
	 */
	OrderDetailDTO queryOrderDetail(Long id);

}
