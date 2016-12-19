package com.pay.poss.dao.ordercenter.fundout.backfundout;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
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
public interface OrderCenterBackFundoutDao extends BaseDAO {

	Page<OrderCenterResultDTO> queryBackFundout(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto);

	/**
	 * 订单详情
	 * 
	 * @param id
	 * @return OrderDetailDTO
	 */
	OrderDetailDTO queryOrderDetail(Long orderKy);

	/**
	 * 查询提现关联订单列表
	 * 
	 * @param sqlId
	 * @param dto
	 * @return
	 */
	List<OrderRelationDTO> queryBackFundoutRelationList(String sqlId,
			OrderCenterQueryDTO dto);
}
