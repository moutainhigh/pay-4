/**
 * 
 */
package com.pay.txncore.service.refund;

import java.util.Date;
import java.util.List;

import com.pay.txncore.commons.RefundConfirmStatusEnum;
import com.pay.txncore.dto.refund.RefundOrderConfirmDTO;

/**
 * @author LIBO 退款确认订单请求
 */
public interface RefundOrderConfirmService {

	/**
	 * 修改退款确认订单状态
	 * 
	 * @param refundOrderNo
	 * @return
	 */
	public boolean updateRefundOrderState(Long refundConfirmId, RefundConfirmStatusEnum state);
	
	/**
	 * 修改退款确认订单状态
	 * 
	 * @param refundOrderNo
	 * @return
	 */
	public boolean updateRefundOrderStateAfter(Long refundConfirmId, RefundConfirmStatusEnum state, Date preStartTime);

	/**
	 * 保存退款确认请求订单
	 * 
	 * @param refundOrderConfirmDTO
	 * @return no
	 */
	public Long create(RefundOrderConfirmDTO refundOrderConfirmDTO);

	/**
	 * 查询退款确认请求订单
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public RefundOrderConfirmDTO queryByPk(Long id);

	/**
	 * 更新退款确认请求订单状态
	 * 
	 * @param refundOrderConfirmDTO
	 * @return
	 */
	public boolean updateRefundOrderConfirmByPk(RefundOrderConfirmDTO refundOrderConfirmDTO);
	
	/**
	 * 查找待确认的退款订单
	 * 
	 * @param currentDate
	 * @return
	 */
	public List<RefundOrderConfirmDTO> queryConfirmRefundList(Date currentDate);
	
	/**
	 * 根据退款订单号查找确认订单
	 * 
	 * @param refundOrderNo
	 * @return
	 */
	public List<RefundOrderConfirmDTO> findByRefundOrderNo(Long refundOrderNo);

}
