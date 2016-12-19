package com.pay.poss.dao.ordercenter.fundout.pay2bank;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
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
 * @since 2010-11-26
 * @see
 */
public interface OrderCenterPay2BankDao extends BaseDAO {
	/**
	 * 付款到银行卡 分页
	 * 
	 * @param page
	 * @param dto
	 * @return Page<OrderCenterResultDTO>
	 */
	Page<OrderCenterResultDTO> queryPay2Bank(Page<OrderCenterResultDTO> page,
			OrderCenterQueryDTO dto);

	/**
	 * 付款到银行卡 详情
	 * 
	 * @param orderKy
	 * @return OrderDetailDTO
	 */
	OrderDetailDTO queryPay2BankDetail(Long orderKy);

	/**
	 * 关联订单列表
	 * 
	 * @param dto
	 * @return List<OrderRelationDTO>
	 */
	List<OrderRelationDTO> queryPay2BankList(OrderCenterQueryDTO dto);
}
