package com.pay.pe.account.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.pe.account.dto.QueryEntryDTO;
import com.pay.pe.account.service.EntryQueryService;
import com.pay.util.StringUtil;

public class OrderEntryQueryController extends MultiActionController {
	
	private static final Log log = LogFactory
			.getLog(OrderEntryQueryController.class);
	private String indexView;

	private String listView;

	private EntryQueryService entryQueryService;

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView(indexView, model);
	}

	public ModelAndView query(HttpServletRequest request,
			HttpServletResponse response, SubjectBalanceQueryCommand command)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		String orderId = request.getParameter("orderId");
		if (StringUtil.isEmpty(orderId)) {
			String errorMsg = "订单号不可以为空!";
			model.put("error", errorMsg);
			return new ModelAndView(listView, model);
		}
		List<QueryEntryDTO> resultList = entryQueryService
				.getEntrieListByOrderId(orderId);
		model.put("resultList", resultList);
		return new ModelAndView(listView, model);
	}

	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	public void setListView(String listView) {
		this.listView = listView;
	}

	public EntryQueryService getEntryQueryService() {
		return entryQueryService;
	}

	public void setEntryQueryService(EntryQueryService entryQueryService) {
		this.entryQueryService = entryQueryService;
	}

	private Date parseDate(String strDate) {
		Date date = null;
		try {
			date = DateUtils.parseDate(strDate, new String[] { "yyyy-MM-dd" });
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

}
