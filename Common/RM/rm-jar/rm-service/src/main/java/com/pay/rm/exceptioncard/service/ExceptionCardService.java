/**
 * 
 */
package com.pay.rm.exceptioncard.service;

import java.util.List;
import java.util.Map;

import com.pay.rm.exceptioncard.dto.AweekCount;
import com.pay.rm.exceptioncard.dto.ExceptionCardDTO;

/**
 * @author Jiangbo.peng
 *
 */
public interface ExceptionCardService {
	
	/**
	 * 新增
	 * @param exceptionCardDTO
	 * @return
	 */
	Long createExceptionCardTrans(ExceptionCardDTO exceptionCardDTO) ;
	
	/**
	 * 异常卡对象查询
	 * @param hMap
	 * @return
	 */
	ExceptionCardDTO queryExceptionCardDTO(Map<String, Object> hMap) ;
	
	/**
	 * 新增或修改
	 * @param exceptionCardDTO
	 */
	void saveOrUpdate(ExceptionCardDTO exceptionCardDTO) ;
	
	/**
	 * 修改
	 * @param exceptionCardDTO
	 * @return
	 */
	boolean updateExceptionCardTrans(ExceptionCardDTO exceptionCardDTO) ;
	
	/**
	 * 异常卡列表查询
	 * @param hMap
	 * @return
	 */
	List<ExceptionCardDTO> queryExceptionCardList(Map<String, Object> hMap) ;
	
	/**
	 * 前七日异常卡笔数， 失败订单笔数统计
	 * @param hMap
	 * @return
	 */
	AweekCount queryAweekCount(Map<String, Object> hMap) ;
	
}
