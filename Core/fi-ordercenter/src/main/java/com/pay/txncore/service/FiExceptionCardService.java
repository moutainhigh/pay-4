/**
 * 
 */
package com.pay.txncore.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.dto.ExceptionCardDetailMsgDTO;

/**
 * @author Jiangbo.peng
 *
 */
public interface FiExceptionCardService {

	/**
	 * 异常卡详细信息查询
	 * @param hMap
	 * @return
	 */
	List<ExceptionCardDetailMsgDTO> queryExceptionCardDetailMsg(Map<String, Object> hMap) ;
	
	/**
	 * 异常卡详细信息查询
	 * @param hMap
	 * @return
	 */
	List<ExceptionCardDetailMsgDTO> queryExceptionCardDetailMsg(Map<String, Object> hMap, Page page) ;
	
	
}
