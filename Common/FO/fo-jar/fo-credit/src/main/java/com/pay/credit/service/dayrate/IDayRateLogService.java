/**
 *
 */
package com.pay.credit.service.dayrate;

import java.util.List;

import com.pay.credit.dto.dayrate.DayRateLogDTO;

/**
 * @author zhixiang.deng
 *
 * @date 2015年8月3日
 */
public interface IDayRateLogService {


	/**
	 * 查询日利率变更记录
	 *
	 * @param queryTime
	 * 			查询时间
	 * @return 日利率变更列表
	 */
	public List<DayRateLogDTO> queryDayRateLog(String queryTime);


	/**
	 * 插入日利率变更记录
	 *
	 * @param dayRateLogDTO
	 * @return
	 */
	public long inertDayRateLog(DayRateLogDTO dayRateLogDTO);

}
