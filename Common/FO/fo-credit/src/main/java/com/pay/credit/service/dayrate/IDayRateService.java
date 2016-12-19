/**
 *
 */
package com.pay.credit.service.dayrate;

import com.pay.credit.dto.dayrate.DayRateDTO;


/**
 * 日利率维护
 *
 * @author zhixiang.deng
 *
 * @date 2015年8月1日
 */
public interface IDayRateService {

	/**
	 * 更新日利率
	 *
	 * @param dayRateDto
	 * @return
	 */
	public boolean updateDayRate(DayRateDTO dayRateDto);

	/**
	 * 查询日利率
	 *
	 * @return
	 */
	public DayRateDTO queryDayRate();

}
