package com.pay.fo.controller.calendar;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import com.pay.fundout.withdraw.service.autocheck.AutoCheckService;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawOrderAuditService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.calendar.service.CalendarService;
import com.pay.poss.base.controller.AbstractBaseController;
import com.pay.poss.base.model.CalendarDto;
import com.pay.util.DateUtil;

/**
 * 日历管理器，主要是用来设置节假日的
 * @author meng.li
 *
 */
public class CalendarController extends AbstractBaseController {
	
	protected transient Log log = LogFactory.getLog(getClass());
	
	private CalendarService calendarService;
	
	private WithdrawOrderAuditService withdrawOrderAuditService;
	
	private AutoCheckService autoCheckService;
	
	public void setWithdrawOrderAuditService(WithdrawOrderAuditService withdrawOrderAuditService) {
		this.withdrawOrderAuditService = withdrawOrderAuditService;
	}
	
	public void setAutoCheckService(AutoCheckService autoCheckService) {
		this.autoCheckService = autoCheckService;
	}
	
	public void setCalendarService(CalendarService calendarService) {
		this.calendarService = calendarService;
	}
	
	/**
	 * 日历查询初始化页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		return new ModelAndView(URL("query"));
	}
	
	/**
	 * 日历查询列表页面
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response, CalendarDto dto) throws ServletException {
		Map<String, Object> params = new HashMap<String, Object>();
		String dateStr = request.getParameter("dateStr");
		if (StringUtils.isNotEmpty(dto.getCdateWeekdy())) {
			params.put("cdateWeekdy", dto.getCdateWeekdy());
		}
		if (StringUtils.isNotEmpty(dto.getCdateHolidy())) {
			params.put("cdateHolidy", dto.getCdateHolidy());
		}
		if (StringUtils.isNotEmpty(dateStr)) {
			params.put("cdateTbsdy", DateUtil.parse("yyyy-MM-dd", dateStr));
		}
		Page<CalendarDto> page = PageUtils.getPage(request);
		page = calendarService.getCalendarList(page, params);
		return new ModelAndView(URL("list")).addObject("page", page);
	}
	
	/**
	 * ajax更新日期是否假期属性操作
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CalendarDto dto = new CalendarDto();
		String cdateTbsdy = request.getParameter("cdateTbsdy");
		String cdateHolidy = request.getParameter("cdateHolidy");
		dto.setCdateTbsdy(DateUtil.parse("yyyy-MM-dd", cdateTbsdy));
		dto.setCdateHolidy(cdateHolidy);
		String retValue = "";
		try {
			calendarService.update(dto);
			retValue="1";
		} catch (Exception e) {
			log.error("日历状态更新失败", e);
		}
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(retValue);
		return null;
	}
	
}
