/**
 * 
 */
package com.pay.txncore.service;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.txncore.dto.OrderCreditDTO;
import com.pay.txncore.dto.OrderCreditDetailDTO;

/**
 * @author Jiangbo.Peng
 *
 */
public interface OrderCreditService {

	
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
	
	
	/**
	 * 根据指定的条件返回查询结果，全匹配
	 * 
	 * @param data
	 * @return
	 */
	List<?> findByCriteria(final String sqlId, final Object criteria);

	/**
	 * 
	 * @param page
	 * @return
	 */
	List<?> findByCriteria(final String sqlId, final Object criteria, Page<?> page);
	
	/**
	 * 订单授信明细更新
	 * @param orderCreditDetailDTO
	 * 2016年7月7日   mmzhang     add
	 */
	boolean updateDetail(OrderCreditDetailDTO orderCreditDetailDTO);
}
