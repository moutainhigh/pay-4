package com.pay.poss.base.calendar.service;

import java.util.Date;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.base.model.CalendarDto;

/**
 * poss日历接口
 * @author meng.li
 *
 */
public interface CalendarService {

	/**
	 * 根据主键(日期)查找对应的日历实体
	 * @param date
	 * @return
	 */
	public CalendarDto findByDate(Date date);
	
	/**
	 * 修改日历信息
	 * @param dto
	 */
	public void update(CalendarDto dto);
	
	/**
	 * 查询日历分页数据
	 * @param page 分页参数
	 * @param params 查询参数
	 * @return
	 */
	public Page<CalendarDto> getCalendarList(Page<CalendarDto> page, Map<String, Object> params);
	
	/**
	 * 获取实际的工作日，如果传递过来的非节假日，那么返回当天精确到天的数据，如果是节假日，那么返回下个工作日
	 * @param date
	 * @param extendFlag 是否顺延，true：是；false：否
	 * @return
	 */
	public Date getActualWorkDay(Date date, boolean extendFlag);

	
}
