package com.pay.pe.manualbooking.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.pe.manualbooking.model.VouchData;
import com.pay.pe.manualbooking.util.VouchSearchCriteria;
/**
 * 手工记帐申请数据访问
 */
//extends DaoSupport
public interface VouchDataDao extends BaseDAO<VouchData> {
	
	VouchData saveVouchData(VouchData vouchData);
	
	VouchData getVouchDataById(Long id);

	VouchData updateVouchData(VouchData vouchData);
	
//	public <T> Page<T> findByQuery(Page<T> page, Object... params) throws PlatformDaoException;
	
	
	/**
	 * @param page
	 * @param vouchCriteria
	 * @return
	 */
//	List<VouchData> findVouchDatasWithPage(Page page, VouchSearchCriteria vouchCriteria);
	List<VouchData> findVouchDatasWithPage(VouchSearchCriteria vouchCriteria);
	

	
}
