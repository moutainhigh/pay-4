/**
 * 
 */
package com.pay.txncore.service.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.dao.FiExceptionCardDao;
import com.pay.txncore.dto.ExceptionCardDetailMsgDTO;
import com.pay.txncore.service.FiExceptionCardService;

/**
 * @author Jiangbo.peng
 *
 */
public class FiExceptionCardServiceImpl implements FiExceptionCardService {

	//注入
	private FiExceptionCardDao fiExceptionCardDao ;
	
	/**
	 * @param fiExceptionCardDao the fiExceptionCardDao to set
	 */
	public void setFiExceptionCardDao(FiExceptionCardDao fiExceptionCardDao) {
		this.fiExceptionCardDao = fiExceptionCardDao;
	}

	@Override
	public List<ExceptionCardDetailMsgDTO> queryExceptionCardDetailMsg(
			Map<String, Object> hMap) {
		return this.fiExceptionCardDao.findByCriteria(hMap) ;
	}

	@Override
	public List<ExceptionCardDetailMsgDTO> queryExceptionCardDetailMsg(
			Map<String, Object> hMap, Page page) {
		return this.fiExceptionCardDao.findByCriteria(hMap, page) ;
	}

}
