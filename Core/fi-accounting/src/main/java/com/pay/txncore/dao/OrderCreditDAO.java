/**
 * 
 */
package com.pay.txncore.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.dto.OrderCreditDTO;
import com.pay.txncore.dto.OrderCreditDetailDTO;

/**
 * @author Jiangbo.Peng
 *
 */
@SuppressWarnings("rawtypes")
public interface OrderCreditDAO extends BaseDAO {

	/**
	 * 创建融资订单
	 */
	Long createOrderCredit(OrderCreditDTO oc) ;
	
	/**
	 * 创建融资订单详情
	 */
	Long createOrderCreidtDetail(OrderCreditDTO orderCreditDTO) ;
	
	/**
	 * 批量创建融资订单详情
	 */
	List<Object> createOrderCreditDetailBatch(List<OrderCreditDetailDTO> ocs) ;
	
	/**
	 * 创建融资订单及融资订单详情
	 */
	void createOrder(OrderCreditDTO oc, List<OrderCreditDetailDTO> ocds) ;
	
	
} 
