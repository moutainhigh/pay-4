/**
 * 
 */
package com.pay.txncore.dao;

import java.util.List;
import java.util.Map;

/**
 * @author chaoyue
 *
 */
public interface ReportDAO {

	/**
	 * 查询商户报表数据
	 * 
	 * @param paraMap
	 * @return
	 */
	List<Map> queryParnterReport(Map<String, String> paraMap);
}
