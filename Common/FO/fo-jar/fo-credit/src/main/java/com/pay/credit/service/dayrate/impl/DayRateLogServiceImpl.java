/**
 *
 */
package com.pay.credit.service.dayrate.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.credit.dao.dayrate.IDayRateLogDao;
import com.pay.credit.dto.dayrate.DayRateLogDTO;
import com.pay.credit.model.dayrate.DayRateLog;
import com.pay.credit.service.dayrate.IDayRateLogService;
import com.pay.util.BeanConvertUtil;

/**
 * @author zhixiang.deng
 *
 * @date 2015年8月3日
 */
public class DayRateLogServiceImpl implements IDayRateLogService {

	private IDayRateLogDao dayRateLogDao;

	/* (non-Javadoc)
	 * @see com.pay.credit.service.dayrate.IDayRateLogBiz#queryDayRateLog(java.sql.Date)
	 */
	public List<DayRateLogDTO> queryDayRateLog(final String queryTime) {
		final Map<String, Object> map = new HashMap<String,Object>();
		map.put("queryTime", queryTime);
		final List<DayRateLog> dayRateLogs = dayRateLogDao.queryDayRateLog(map);
		return (List<DayRateLogDTO>) BeanConvertUtil.convert(DayRateLogDTO.class,dayRateLogs);
	}


	/* (non-Javadoc)
	 * @see com.pay.credit.service.dayrate.IDayRateLogBiz#inertDayRateLog(com.pay.credit.dto.dayrate.DayRateLogDTO)
	 */
	public long inertDayRateLog(final DayRateLogDTO dayRateLogDTO) {
		final DayRateLog dayRateLog = BeanConvertUtil.convert(DayRateLog.class, dayRateLogDTO);
		return dayRateLogDao.inertDayRateLog(dayRateLog);
	}

	/**
	 * @param dayRateLogDao
	 */
	public void setDayRateLogDao(final IDayRateLogDao dayRateLogDao) {
		this.dayRateLogDao = dayRateLogDao;
	}
}
