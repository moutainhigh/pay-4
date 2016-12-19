/**
 *
 */
package com.pay.credit.dao.dayrate;

import java.util.List;
import java.util.Map;

import com.pay.credit.model.dayrate.DayRateLog;
import com.pay.inf.dao.BaseDAO;

/**
 * 日利率变更
 *
 * @author zhixiang.deng
 *
 * @date 2015年8月3日
 */
public interface IDayRateLogDao extends BaseDAO {

	/**
	 * 查询日利率变更历史
	 *
	 * @param map
	 * 			查询参数
	 * @return 日利率变更列表
	 */
	public List<DayRateLog>  queryDayRateLog(Map<String, Object> map);


	/**
	 * 插入日利率变更记录
	 *
	 * @param dayRateLog
	 * @return
	 */
	public long inertDayRateLog(DayRateLog dayRateLog);
}
