package com.pay.poss.base.calendar.dao.impl;

import java.util.Date;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.base.calendar.dao.CalendarDAO;
import com.pay.poss.base.model.CalendarDto;

@SuppressWarnings("unchecked")
public class CalendarDAOImpl extends BaseDAOImpl implements CalendarDAO {

	@Override
	public CalendarDto findByDate(Date date) {
		return (CalendarDto) this.findObjectByCriteria("findByDate", date);
	}

	@Override
	public Page<CalendarDto> getCalendarList(Page<CalendarDto> page,
			Map<String, Object> params) {
		return this.findByQuery("getFundoutCalendarList", page, params);
	}

	@Override
	public Date getActualWorkDay(Date date) {
		return (Date) this.findObjectByCriteria("getActualWorkDay", date);
	}

}
