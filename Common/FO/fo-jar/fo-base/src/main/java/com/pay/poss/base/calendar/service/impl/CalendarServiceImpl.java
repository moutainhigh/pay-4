package com.pay.poss.base.calendar.service.impl;

import java.util.Date;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.base.calendar.dao.CalendarDAO;
import com.pay.poss.base.calendar.service.CalendarService;
import com.pay.poss.base.model.CalendarDto;
import com.pay.util.DateUtil;

public class CalendarServiceImpl implements CalendarService {

	private CalendarDAO calendarDAO;
	
	public void setCalendarDAO(CalendarDAO calendarDAO) {
		this.calendarDAO = calendarDAO;
	}
	
	@Override
	public CalendarDto findByDate(Date date) {
		return this.calendarDAO.findByDate(date);
	}
	
	@Override
	public Page<CalendarDto> getCalendarList(Page<CalendarDto> page, Map<String, Object> params) {
		return this.calendarDAO.getCalendarList(page, params);
	}
	
	@Override
	public void update(CalendarDto dto) {
		this.calendarDAO.update(dto);
	}
	
	@Override
	public Date getActualWorkDay(Date date, boolean extendFlag) {
		Date paramDate = DateUtil.getTheDate(date);
		if (extendFlag) {
			CalendarDto dto = this.findByDate(paramDate);
			if (dto != null && "0".equals(dto.getCdateHolidy())) {
				return paramDate;
			}
			return this.calendarDAO.getActualWorkDay(paramDate);
		}
		return paramDate;
	}
	
}
