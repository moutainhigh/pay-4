/**
 * 
 */
package com.pay.batchpay.dao.bulkpayment;

import java.util.List;
import java.util.Map;

import com.pay.batchpay.dto.BulkPaymentOrder;
import com.pay.batchpay.dto.OrderTemplateUnion;

/**
 * 批量付款订单DAO
 * @author PengJiangbo
 *
 */
public interface BulkpayOrderDao {
	
	/**
	 * 批次订单新增
	 * @param bulkPaymentOrder
	 * @return
	 */
	public long insertBulkpayOrder(BulkPaymentOrder bulkPaymentOrder) ;
	
	/**
	 * 批次记录查询
	 * @param map
	 * @return
	 */
	public List<OrderTemplateUnion> findBulkpayOrder(Map<String, Object> map) ;
	
	/**
	 * 变更批次记录状态
	 * @param map
	 * @return
	 */
	public int updateBulkordedrStatus(Map<String, Object> map) ;
	
	/**
	 * 查询模板文件保存路径
	 * @param map
	 * @return
	 */
	public String findBulkorderFilePath(Map<String, Object> map) ;
	
	
}
