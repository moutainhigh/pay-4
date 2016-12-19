package com.pay.pe.service.order;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.SqlParameterValue;

import com.pay.inf.dto.MutableDto;
import com.pay.inf.service.BaseService;
import com.pay.pe.dto.PaymentOrderDto;

/**
 * 
 */
public interface PaymentOrderService extends BaseService {
	/**
	 * 查找所有的收款方标识为参数payeeIdentity的订单. 如果参数payeeIdentity为空，则返回Null.
	 * 
	 * @param payeeIdentity
	 *            String
	 * @return List
	 */
	List<PaymentOrderDto> findAllPaymentOrder(String payeeIdentity);

	/**
	 * 查找所有关联到orderId的payment order.
	 * 即PaymentOrder实体中relatedSequenceID值等于参数orderId值的对象.
	 * 
	 * @param orderId
	 * @return List
	 */
	List<PaymentOrderDto> findAllRelatedOrder(String orderId,
			Integer relatedType);

	/**
	 * 查询订单状态为参数orderStatus的所有订单.
	 * 如果orderType为空，则返回所有状态为orderStatus的订单,返回的订单集合中回包含不同订单类型的订单.
	 * 如果orderStatus为空，则返回所有订单类型为orderType的订单,返回的订单集合中包含不同订单状态的订单.
	 * 两者不能同时为空,同时为空时返回NULL.
	 * 
	 * @param orderStatus
	 * @param orderType
	 * @return List
	 */
	List<PaymentOrderDto> findAllPaymentOrder(Integer orderStatus,
			Integer orderType);

	/**
	 * 查询订单状态为orderStatus,订单类型为orderType,订单号码为orderNo的订单.
	 * 如果orderNo为空，则返回的结果为调用findAllPaymentOrder(int orderStatus, int
	 * orderCode)的结果. 否则为查询orderNo的订单状态.
	 * 
	 * @param orderStatus
	 * @param orderType
	 * @param orderCode
	 * @return List
	 */
	List<PaymentOrderDto> findAllPaymentOrder(Integer orderStatus,
			Integer orderType, String orderNo);

	/**
	 * 根据order中的属性查询所有的PaymentOrder.
	 * 
	 * @param orderCriteria
	 * @return
	 */
	List<PaymentOrderDto> findAllPaymentOrder(PaymentOrderDto orderCriteria);

	/**
	 * 查询所有参数orderIds中制定的订单号的订单.
	 * 
	 * @param orderIds
	 *            List
	 * @return List
	 */
	List<PaymentOrderDto> getAllPaymentOrders(List<String> orderIds);

	/**
	 * 查询订单状态.
	 * 
	 * @param orderId
	 * @return
	 */
	int getOrderStatus(String orderId);

	/**
	 * 修改订单状态.
	 * 
	 * @param orlderId
	 * @param oldStatus
	 * @param newStauts
	 */
	void changeOrderStatus(String orderId, int oldStatus, int newStatus);

	/**
	 * 修改订单签名.
	 * 
	 * @param orderId
	 * @param digest
	 */
	void updateOrderDigest(String orderId, String digest);

	/**
	 * 根据 订单类型 和 关联业务编号 取对应订单
	 * 
	 * @param orderCode
	 * @param referenceNum
	 * @return
	 */
	PaymentOrderDto findByReferenceNum(int orderCode, String referenceNum);

	PaymentOrderDto findByOrderIdAndOrderCode(Integer orderCode, String orderid);

	/**
	 * 在单独事务中修改订单信息.
	 * 
	 * @param paymentOrder
	 * @return
	 */
	PaymentOrderDto updatePayementOrderInIsolatedTx(
			final PaymentOrderDto paymentOrder);

	MutableDto update(final MutableDto dto);

	/**
	 * 用悲观锁的方式获得Order.
	 * 
	 * @param seqId
	 * @param status
	 * @return
	 */
	public PaymentOrderDto getOerderAndLock(Object seqId,
			Map<String, SqlParameterValue> status);

}
