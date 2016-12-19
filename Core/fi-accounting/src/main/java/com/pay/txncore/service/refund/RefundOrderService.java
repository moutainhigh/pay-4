/**
 * 
 */
package com.pay.txncore.service.refund;

import java.util.List;
import java.util.Map;

import com.pay.fi.exception.BusinessException;
import com.pay.inf.dao.Page;
import com.pay.txncore.commons.RefundStatusEnum;
import com.pay.txncore.dto.refund.RefundOrderDTO;
import com.pay.txncore.model.RefundExceptionMonitor;
import com.pay.txncore.model.RefundPayLinkOrder;

/**
 * 退款订单请求，从fi-ordercenter移植过来的 
 * @author davis.guo at 2016-08-17
 */
public interface RefundOrderService {

	/**
	 * 修改退款订单状态
	 * 
	 * @param refundOrderNo
	 * @return
	 */
	public boolean updateRefundOrderState(Long refundOrderNo,
			RefundStatusEnum state, String errorCode, Long depositBackNo);

	public boolean updateRefundOrderState(Long refundOrderNo,
			RefundStatusEnum state, Long depositBackNo);

	public boolean lockedRefundForUpdate(Long refundOrderNo);

	public Map<String, Long> getRefundFeeByPaymentOrderNo(Long paymentOrderNo);

	public Long getRefundPayeeFee(Long paymentOrderNo);

	public Long getRefundPayerFee(Long paymentOrderNo);

	public boolean isExistByPIdAndOrderId(String partnerId, String orderId);

	public Long queryPartnerRefundAmount(Long paymentOrderNo);

	/**
	 * 保存退款请求订单
	 * 
	 * @param refundOrderDTO
	 * @return no
	 */
	public Long create(RefundOrderDTO refundOrderDTO);

	/**
	 * 创建退款订单(独立事务)
	 * 
	 * @param refundOrderDTO
	 * @return no
	 */
	public Long createRefundOrderRnTx(RefundOrderDTO refundOrderDTO);

	/**
	 * 根据合作伙伴查询退款请求订单集
	 * 
	 * @param refundOrderInfo
	 * @return
	 */
	public List<RefundOrderDTO> queryByPartner(String parnterID);

	/**
	 * 根据合作伙伴+订单号查询退款请求订单集
	 * 
	 * @param String
	 *            parnterID,String partnerRefundOrderId
	 * @return RefundOrderDTO
	 */
	public RefundOrderDTO queryByPartnerAndPartnerRefundOrderId(
			String parnterID, String partnerRefundOrderId);

	/**
	 * 查询退款请求订单
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public RefundOrderDTO queryByPk(Long id);

	/**
	 * 更新退款请求订单状态
	 * 
	 * @param refundOrderInfo
	 * @return
	 */
	public boolean updateRefundOrderByPk(RefundOrderDTO refundOrderDTO);

	/**
	 * 更新退款请求订单状态
	 * 
	 * @param refundOrderInfo
	 * @return
	 */
	public boolean updateRefundOrderByPkRnTx(RefundOrderDTO refundOrderDTO);

	/**
	 * 计算订单退款总金额
	 * 
	 * @param tradeOrderInfoId
	 *            ID
	 * @return Long amount
	 */
	public Long calculationRefundAmount(Long tradeOrderInfoId,
			RefundStatusEnum statusEnum) throws BusinessException;

	/**
	 * 查询退款订单（tradeOrderInfoId，status）
	 * 
	 * @param tradeOrderInfoId
	 * @param status
	 * @return List<RefundOrderInfo>
	 */
	public List<RefundOrderDTO> queryRefundOrderInfosByTradeNoAndStatus(
			String tradeOrderInfoId, Integer status) throws BusinessException;

	/**
	 * 按网关订单号查询退款订单
	 * 
	 * @param tradeOrderNo
	 * @return List<RefundOrderDTO>
	 */
	public List<RefundOrderDTO> findByTradeOrderNo(Long tradeOrderNo);

	/**
	 * 查询退款订单
	 * 
	 * @param tradeOrderNo
	 * @return List<RefundOrderDTO>
	 */
	public List<RefundOrderDTO> findRefundOrder(RefundOrderDTO refundOrderDTO,
			Page page);

	long getMaxChannelSerialNo();

	/**
	 * 判断是否最后一次退款
	 * 
	 * @param tradeOrderNo
	 * @param refundOrderNo
	 * @return
	 */
	boolean isLastRefundOrder(Long tradeOrderNo, Long refundOrderNo);

	/**
	 * 根据支付订单号查询退款订单
	 * 
	 * @param paymentOrderNo
	 * @param status
	 * @return
	 */
	RefundOrderDTO queryRefundOrderByPaymentOrder(Long paymentOrderNo,
			Integer status);
	

	public List<RefundOrderDTO> findRefundOrder(RefundOrderDTO refundOrderDTO);
	
	//note by sch 2016-06-16 上面的函数,实际上返回的是 List<RefundOrder> 
	public List<RefundOrderDTO> findRefundOrderDTOs(RefundOrderDTO refundOrderDTO) ;
	
	List<RefundExceptionMonitor> getRefundExceptionMonitorList(Map param,Page page);
	
	List<RefundPayLinkOrder> queryRefundPayLinkList(Map param,Page page);
}
