/**
 * 
 */
package com.pay.txncore.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.dto.DayRateDTO;



/**
 * @author Jiangbo.Peng
 *
 */
public interface DayRateService {

	/**
	 * 查询日利率列表
	 * @param hMap
	 * @return
	 */
	List<DayRateDTO> queryDayRateList(Map<String, Object> hMap) ;
	
	/**
	 * 查询日利率列表
	 * @param hMap
	 * @return
	 */
	List<DayRateDTO> queryDayRateList(Map<String, Object> hMap, Page<?> page) ;
	
	/**
	 * 创建日利率
	 * @param dayRateDTO
	 * @return
	 */
	Long createDayRate(DayRateDTO dayRateDTO) ;
	
	/**
	 * 修改
	 * @param dayRateDTO
	 * @return
	 */
	boolean update(DayRateDTO dayRateDTO) ;
	
	/**
	 * 删除
	 * @param dayRateDTO
	 * @return
	 */
	boolean delete(DayRateDTO dayRateDTO) ;
	
	/**
	 * 批量删除
	 * @param sqlId
	 * @param listObject
	 * @return
	 */
	Integer deleteBatch(String sqlId, List<Object> listObject) ;
	
}
