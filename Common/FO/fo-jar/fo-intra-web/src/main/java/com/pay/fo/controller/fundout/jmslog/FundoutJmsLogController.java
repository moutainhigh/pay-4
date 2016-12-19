/**
 *  File: FundoutJmsLogController.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-16     darv      Changes
 *  
 *
 */
package com.pay.fo.controller.fundout.jmslog;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.controller.AbstractBaseController;
import com.pay.poss.jmslog.dto.FundoutJmsLogDTO;
import com.pay.poss.jmslog.service.FundoutJmsLogService;

/**
 * @author darv
 * 
 */
public class FundoutJmsLogController extends AbstractBaseController {
	private FundoutJmsLogService fundoutJmsLogService;

	public void setFundoutJmsLogService(FundoutJmsLogService fundoutJmsLogService) {
		this.fundoutJmsLogService = fundoutJmsLogService;
	}

	/**
	 * 查询消息日志列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView jmsLogInit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView(URL("jmsLogInit"));
	}

	public ModelAndView jmsLogList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Page<FundoutJmsLogDTO> page = PageUtils.getPage(request);
		page = fundoutJmsLogService.getJmsLogList(page, null);
		return new ModelAndView(URL("jmsLogList")).addObject("page", page);
	}

	/**
	 * 重新发送
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView reSend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] ids = request.getParameter("ids").split(",");
		for (String id : ids) {
			sendMessage(id);
		}
		return new ModelAndView(new RedirectView(request.getContextPath() + URL("toJmsLogInit")));
	}

	/**
	 * 取得对象并重发消息
	 * 
	 * @param id
	 *            消息ID
	 */
	@SuppressWarnings("unchecked")
	private void sendMessage(String id) {
		Map params = new HashMap();
		params.put("sequenceId", id);
		fundoutJmsLogService.redoSendMessage(params);
			
	}
}
