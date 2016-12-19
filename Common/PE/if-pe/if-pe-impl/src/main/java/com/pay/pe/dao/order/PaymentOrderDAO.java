/**
 * Created on Wed Dec 06 18:09:11 CST 2006.
 */
package com.pay.pe.dao.order;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.SqlParameterValue;

import com.pay.pe.dto.PaymentOrderDto;
import com.pay.pe.model.PaymentOrder;

/**
 * @Company: 
 * g
 * @version 1.0
 */
public interface PaymentOrderDAO  {
	/**
	 * 查找所有的收款方标识为参数payeeIdentity的订单.
	 * @param payeeIdentity String
	 * @return List
	 */
	List <PaymentOrder> findAllPaymentOrder(String payeeIdentity);

	/**
	 * 查找所有关联到orderId的payment order.
	 * 即PaymentOrder实体中relatedSequenceID值等于参数orderId值的对象.
	 * @param orderId
	 * @return List
	 */
	List <PaymentOrder> findAllRelatedOrder(String orderId, int relatedType);
	
	/**
	 * 查询订单状态为参数orderStatus的所有订单.
	 * 如果orderType为空，则返回所有状态为orderStatus的订单,返回的订单集合中回包含不同订单类型的订单.
	 * 如果orderStatus为空，则返回所有订单类型为orderType的订单,返回的订单集合中包含不同订单状态的订单.
	 * 两者不能同时为空,同时为空时返回NULL.
	 * @param orderStatus
	 * @param orderType
	 * @return List
	 */
	List <PaymentOrder> findAllPaymentOrder(Integer orderStatus, Integer orderType);
	
	/**
	 * 查询订单状态为orderStatus,订单类型为orderType,订单号码为orderNo的订单.
	 * 如果orderNo为空，则返回的结果为调用findAllPaymentOrder(int orderStatus, int orderType)的结果.
	 * 否则为查询orderNo的订单状态.
	 * @param orderStatus
	 * @param orderType
	 * @param orderNo
	 * @return List
	 */
	List <PaymentOrder> findAllPaymentOrder(Integer orderStatus, Integer orderType, String orderNo);
	
	/**
	 * 根据order中的属性查询所有的PaymentOrder.
	 * @param orderCriteria
	 * @return
	 */
	List <PaymentOrder> findAllPaymentOrder(PaymentOrder orderCriteria);
	
	/**
	 * 查询所有指定的定单号的定单.
	 * @param orderIds List
	 * @return List
	 */
	List <PaymentOrder> getAllPaymentOrders(List <String> orderIds);
	
	PaymentOrder findByReferenceNum(int orderCode, String referenceNum);
	
	/**
	 * 修改订单签名.
	 * @param orderId
	 * @param digest
	 */
	void updateOrderDigest(String orderId, String digest);
	
	/**
	 * 计算在指定的收款会员和付款会员之间的在特定的交易类型上的一定时间之内的交易总额.
	 * @param payerMemberCode String
	 * @param payeeMemberCode String
	 * @param orderCode Integer
	 * @param begin Date
	 * @param end Date
	 * @return long
	 */
	long caculateTotalPayAmount(
			String payerMemberCode, 
			String payeeMemberCode, 
			Integer orderCode, 
			Date begin, 
			Date end);
			
	/**
	 * 以悲观锁的形式获得order
	 * @param id
	 * @param status TODO
	 * @return
	 */
	public PaymentOrder getOerderAndLock(Object id, Map<String, SqlParameterValue> status);
	
	PaymentOrder findById(Long orderSeqId);
	
	/**
	 * @param orderCode
	 * @param orderid
	 * @return
	 */
	PaymentOrder findByOrderIdAndOrderCode(Integer orderCode, String orderid);
}
