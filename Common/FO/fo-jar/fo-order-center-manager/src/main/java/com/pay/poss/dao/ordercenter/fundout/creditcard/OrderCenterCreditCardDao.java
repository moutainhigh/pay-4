package com.pay.poss.dao.ordercenter.fundout.creditcard;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
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
 * @since 2010-11-26
 * @see
 */
public interface OrderCenterCreditCardDao extends BaseDAO {

	/**
	 * 信用卡还款 分页
	 * 
	 * @param page
	 * @param dto
	 * @return Page<OrderCenterResultDTO>
	 */
	Page<OrderCenterResultDTO> queryCreditCard(Page<OrderCenterResultDTO> page,
			OrderCenterQueryDTO dto);

	/**
	 * 信用卡还款 详情
	 * 
	 * @param orderKy
	 * @return OrderDetailDTO
	 */
	OrderDetailDTO queryCreditCardDetail(Long orderKy);

	/**
	 * 关联订单列表
	 * 
	 * @param dto
	 * @return List<OrderRelationDTO>
	 */
	List<OrderRelationDTO> queryCreditCardList(OrderCenterQueryDTO dto);
}
