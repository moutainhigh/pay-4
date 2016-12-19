/**
 *
 */
package com.pay.credit.dao.dayrate;

import java.util.List;

import com.pay.credit.model.dayrate.DayRate;
import com.pay.inf.dao.BaseDAO;

/**
 * @author zhixiang.deng
 *
 * @date 2015年8月1日
 */
public interface IDayRateDao extends BaseDAO<DayRate> {

	/**
	 * 更新日利率
	 *
	 * @param dayRate
	 * @return
	 */
	int updateDayRate(DayRate dayRate);


	/**
	 * 查询日利率
	 *
	 * @return
	 */
	List<DayRate> queryDayRate();

}
