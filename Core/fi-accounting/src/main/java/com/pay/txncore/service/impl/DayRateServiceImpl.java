/**
 * 
 */
package com.pay.txncore.service.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.dao.DayRateDAO;
import com.pay.txncore.dto.DayRateDTO;
import com.pay.txncore.service.DayRateService;

/**
 * @author Jiangbo.Peng
 *
 */
public class DayRateServiceImpl implements DayRateService {

	//注入
	private DayRateDAO dayRateDAO ;
	
	/**
	 * @param dayRateDAO the dayRateDAO to set
	 */
	public void setDayRateDAO(DayRateDAO dayRateDAO) {
		this.dayRateDAO = dayRateDAO;
	}
	
	@Override
	public Long createDayRate(DayRateDTO dayRateDTO) {
		return (Long) this.dayRateDAO.create(dayRateDTO) ;
	}

	@Override
	public boolean update(DayRateDTO dayRateDTO) {
		return this.dayRateDAO.update(dayRateDTO) ;
	}

	@Override
	public boolean delete(DayRateDTO dayRateDTO) {
		return this.dayRateDAO.delete(dayRateDTO) ;
	}

	@Override
	public Integer deleteBatch(String sqlId, List<Object> listObject) {
		return this.dayRateDAO.deleteBatch(sqlId, listObject) ;
	}

	@Override
	public List<DayRateDTO> queryDayRateList(Map<String, Object> hMap) {
		return this.dayRateDAO.findByCriteria(hMap) ;
	}

	@Override
	public List<DayRateDTO> queryDayRateList(Map<String, Object> hMap,
			Page<?> page) {
		return this.dayRateDAO.findByCriteria(hMap, page) ;
	}

	
}
