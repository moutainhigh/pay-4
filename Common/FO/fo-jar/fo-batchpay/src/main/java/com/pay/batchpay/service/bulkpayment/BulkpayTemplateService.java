/**
 * 
 */
package com.pay.batchpay.service.bulkpayment;

import java.util.List;
import java.util.Map;

import com.pay.batchpay.dto.BulkPaymentTemplate;

/**
 * @author PengJiangbo
 *
 */
public interface BulkpayTemplateService {

	/**
	 * 模板文件内容批量插入
	 * @param bulkPaymentTemplate
	 */
	public void insertBulkpayBatch(Map<String, Object> map) ;
	
	/**
	 * 模板文件内容批量插入进[fundout_order]
	 * @param map
	 */
	public void insertBulkpayFundoutBatch(Map<String, Object> map) ;
	
	/**
	 * 根据批次号查询该批次记录
	 * @param bulkorderNo
	 * @return
	 */
	public List<BulkPaymentTemplate> findBulkpayByBulkorderNo(String bulkorderNo) ;
	
}
