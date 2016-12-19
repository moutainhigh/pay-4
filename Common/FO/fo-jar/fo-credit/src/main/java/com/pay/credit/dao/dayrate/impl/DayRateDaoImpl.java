/**
 *
 */
package com.pay.credit.dao.dayrate.impl;

import java.util.List;

import com.pay.credit.dao.dayrate.IDayRateDao;
import com.pay.credit.dto.dayrate.DayRateDTO;
import com.pay.credit.model.dayrate.DayRate;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author zhixiang.deng
 *
 * @date 2015年8月1日
 */
public class DayRateDaoImpl extends BaseDAOImpl<DayRate> implements IDayRateDao {

	/* (non-Javadoc)
	 * @see com.pay.credit.dao.dayrate.IDayRateDao#updateDayRate(com.pay.credit.model.dayrate.DayRate)
	 */
	public int updateDayRate(final DayRate dayRate) {
		return this.getSqlMapClientTemplate().update(getNamespace().concat("updateDayRate"), dayRate);
	}


	/* (non-Javadoc)
	 * @see com.pay.credit.dao.dayrate.IDayRateDao#queryDayRate()
	 */
	public List<DayRate> queryDayRate() {
		return  this.getSqlMapClientTemplate().queryForList(getNamespace().concat("queryDayRate"));
	}



	public List<DayRate> queryDayRate(DayRateDTO dayRateDTO) {
		return this.getSqlMapClientTemplate().queryForList(getNamespace().concat("queryDayRateByData"),dayRateDTO);
	}


}
