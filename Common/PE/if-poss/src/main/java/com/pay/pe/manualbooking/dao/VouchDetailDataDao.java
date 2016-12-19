package com.pay.pe.manualbooking.dao;

import java.sql.SQLException;
import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.pe.manualbooking.dto.VouchDataDetailSearchDto;
import com.pay.pe.manualbooking.model.VouchData;
import com.pay.pe.manualbooking.model.VouchDetailData;
import com.pay.pe.manualbooking.util.VouchDetailSearchCriteria;

/**
 * 
 * 手工记账申请明细访问
 */
// extends DaoSupport
public interface VouchDetailDataDao extends BaseDAO<VouchDetailData> {

//	public <T> Page<T> findByQuery(String stmtId, final Page<T> page,
//			Object... params) throws PlatformDaoException;

	List<VouchDataDetailSearchDto> findVouchDetailDatasWithPage(
			VouchDetailSearchCriteria vouchDetailSearchCriteria)
			throws SQLException;

	public void createVouchDetail(VouchDetailData vdd);

	public VouchData insertVouchData(VouchData vd);
}
