package com.pay.pe.reconciliation.service;

/**
 * 
 * @ClassName: ReconciliationService
 * @Description: 易卡对账
 * @author cf
 * @date Mar 30, 2012 2:10:30 PM
 * 
 */
public interface ReconciliationService {

	//public String getFileNameByDate(String startDate, String endDate);

	
	/**
	* @Title: generateLocalFilePath
	* @Description: 生成本地文件
	* @param @param name
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	*/ 
	public String generateLocalFilePath(String name);
}
