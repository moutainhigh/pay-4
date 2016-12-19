/**
 *
 */
package com.pay.credit.dao.dayrate.impl;

import java.util.List;
import java.util.Map;

import com.pay.credit.dao.dayrate.IDayRateLogDao;
import com.pay.credit.model.dayrate.DayRateLog;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author zhixiang.deng
 *
 * @date 2015年8月3日
 */
public class DayRateLogDaoImpl extends BaseDAOImpl implements IDayRateLogDao {


	/* (non-Javadoc)
	 * @see com.pay.credit.dao.dayrate.IDayRateLogDao#queryDayRateLog(java.util.Map)
	 */
	public List<DayRateLog> queryDayRateLog(final Map<String, Object> map) {
		return this.getSqlMapClientTemplate().queryForList(this.getNamespace().concat("queryDayRateLog"), map);
	}

	/* (non-Javadoc)
	 * @see com.pay.credit.dao.dayrate.IDayRateLogDao#inertDayRateLog(com.pay.credit.model.dayrate.DayRateLog)
	 */
	public long inertDayRateLog(final DayRateLog dayRateLog) {
		return  (Long) this.getSqlMapClientTemplate().insert(this.getNamespace().concat("inertDayRateLog"), dayRateLog);
	}

}
