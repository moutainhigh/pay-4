package com.pay.poss.dao.ordercenter.fundout.interbankfundstransfer;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
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
 * @since 2010-11-26
 * @see
 */
public interface OrderCenterInterbankFundsTransferDao extends BaseDAO {
	/**
	 * 跨行转账 分页
	 * 
	 * @param page
	 * @param dto
	 * @return Page<OrderCenterResultDTO>
	 */
	Page<OrderCenterResultDTO> queryInterbankFundsTransfer(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto);

	/**
	 * 跨行转账 详情
	 * 
	 * @param orderKy
	 * @return OrderDetailDTO
	 */
	OrderDetailDTO queryInterbankFundsTransferDetail(Long orderKy);

	/**
	 * 关联订单列表
	 * 
	 * @param dto
	 * @return List<OrderRelationDTO>
	 */
	List<OrderRelationDTO> queryInterbankFundsTransferList(
			OrderCenterQueryDTO dto);
}
