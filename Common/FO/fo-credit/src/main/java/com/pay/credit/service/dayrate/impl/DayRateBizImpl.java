/**
 *
 */
package com.pay.credit.service.dayrate.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.pay.credit.dao.dayrate.IDayRateDao;
import com.pay.credit.dto.dayrate.DayRateDTO;
import com.pay.credit.model.dayrate.DayRate;
import com.pay.credit.service.dayrate.IDayRateService;
import com.pay.util.BeanConvertUtil;

/**
 * @author zhixiang.deng
 *
 * @date 2015年8月1日
 */
public class DayRateBizImpl implements IDayRateService {

	private IDayRateDao dayRateDao;



	/* (non-Javadoc)
	 * @see com.pay.credit.service.dayrate.IDayRateBiz#updateDayRate(com.pay.credit.dto.dayrate.DayRateDTO)
	 */
	public boolean updateDayRate(final DayRateDTO dayRateDto) {
		final DayRate dayRate = BeanConvertUtil.convert(DayRate.class, dayRateDto);
		if(dayRateDao.updateDayRate(dayRate) == 1)
		{
			return true;
		}
		return false;
	}



	/* (non-Javadoc)
	 * @see com.pay.credit.service.dayrate.IDayRateBiz#queryDayRate()
	 */
	public DayRateDTO queryDayRate() {
		final List<DayRate> dayRateList = dayRateDao.queryDayRate();
		if(CollectionUtils.isEmpty(dayRateList)){
			return null;
		}
		return BeanConvertUtil.convert(DayRateDTO.class, dayRateList.get(0));
	}



	/**
	 * @param dayRateDao
	 */
	public void setDayRateDao(final IDayRateDao dayRateDao) {
		this.dayRateDao = dayRateDao;
	}


}
