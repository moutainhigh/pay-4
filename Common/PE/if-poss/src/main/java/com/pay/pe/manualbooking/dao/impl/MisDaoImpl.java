package com.pay.pe.manualbooking.dao.impl;

import java.util.List;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.pe.manualbooking.dao.MisDao;
import com.pay.pe.manualbooking.model.VouchData;

public class MisDaoImpl extends BaseDAOImpl implements MisDao<VouchData> {

	public static final int FIRST_PAGE_OFFSET = 0;

	public static final String COUNT_SUBFIX = "_COUNT";

	public List getVouchInfo(VouchData vd) {
		return (List) super.findByTemplate("getdataInfo", vd);
	}

	public List getVouchDetail() {
		return (List) super.findByTemplate("getVouchDetail", null);
	}

	public List getVouchData(VouchData vd) {
		return (List) super.findByTemplate("getVouchData", vd);
	}

	public void insertVouchData(VouchData vd) {
		super.findByTemplate("insertVouchData", vd);
	}

	public void updateVouchstatus(VouchData vd) {
		super.getSqlMapClientTemplate().update("PE-MIS.updateVouchstatus", vd);
	}

	public List getQueryVouchDetailInfo(long id) {
		return (List) super.findByTemplate("PE-MIS.getAllVouchDetailInfo", id);
	}

	public boolean deleteVouchDetail(long id) {
		return this.delete(id);
	}

	// public <T> Page<T> findByQuery(String stmtId, final Page<T> page,
	// Object... params) throws PlatformDaoException {
	// int offset = page.getFirst();
	// int pageSize = page.getPageSize();
	// QueryResult temp = findByQuery(stmtId, offset, pageSize, params);
	// page.setResult(temp.getResult());
	// if (offset == FIRST_PAGE_OFFSET) {
	// page.setTotalCount(temp.getTotalCount());
	// }
	// return page;
	// }

	// public <T> QueryResult<T> findByQuery(String stmtId, int offset, int
	// size,
	// Object... params) throws PlatformDaoException {
	// QueryResult<T> queryResult = new QueryResult<T>();
	// Object param = (params != null) ? params[0] : null;
	// try {
	// // 当offset值为FIRST_PAGE_OFFSET时，被默认为需要查询总数
	// if (offset == FIRST_PAGE_OFFSET) {
	// String countSqlId = stmtId + COUNT_SUBFIX;
	// if (logger.isDebugEnabled()) {
	// logger.debug("自动调用  [" + countSqlId + "]");
	// }
	// int totalSize = (Integer) super.getSqlMapClientTemplate()
	// .queryForObject(countSqlId, param);
	// queryResult.setTotalCount(totalSize);
	// }
	// queryResult.setResult(getSqlMapClientTemplate().queryForList(
	// stmtId, param, offset, size));
	// } catch (Exception e) {
	// logger.error("查询错误 [语句编号=" + stmtId + ",查询参数=" + param + "]", e);
	// throw new PlatformDaoException("查询错误 [语句编号=" + stmtId + ",查询参数="
	// + param + "]", ExceptionCodeEnum.DATA_ACCESS_EXCEPTION, e);
	// }
	// return queryResult;
	// }

}
