/**
 * 
 */
package com.pay.ma.chargeup.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.ma.chargeup.model.BalanceEntry;

/**
 * @author Administrator
 * 
 */
public interface BalanceEntryDAO extends BaseDAO<BalanceEntry> {

	/**
	 * 根据流水号查询分录信息
	 * 
	 * @param serialNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BalanceEntry> queryBalanceEntryInfoWithSerialNo(Map paramMap);

}
