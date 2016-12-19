package com.pay.poss.dao.ordercenter.fundout.backrefund;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;

/**
 * 
 * <p>
 * 查询充退退款(冲正)结果列表Dao
 * </p>
 * 
 * @author Volcano.Wu
 * @since 2010-11-6
 * @see
 */
public interface OrderCenterBackRefundDao extends BaseDAO {
	/**
	 * 查询充退退款(冲正)交易分页列表
	 * 
	 * @param page
	 * @param dto
	 * @return
	 */
	Page<OrderCenterResultDTO> queryBackRefundResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto);
}
