package com.pay.poss.dao.ordercenter.fundout.batchpay2account;

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
 * 查询批量付款到账户订单结果列表Dao
 * </p>
 * 
 * @author Volcano.Wu
 * @since 2010-11-6
 * @see
 */
public interface OrderCenterBatchPay2AccountDao extends BaseDAO {
	/**
	 * 查询批量付款到账户交易分页列表
	 * 
	 * @param page
	 * @param dto
	 * @return
	 */
	Page<OrderCenterResultDTO> queryBatchPay2AccountResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto);

	/**
	 * 查询批量付款到账户关联订单列表
	 * 
	 * @param dto
	 * @return
	 */
	List<OrderRelationDTO> queryBatchPay2AccountRelationList(
			OrderCenterQueryDTO dto);

	/**
	 * 订单详情
	 * 
	 * @param id
	 * @return OrderDetailDTO
	 */
	OrderDetailDTO queryOrderDetail(Long orderKy);
}
