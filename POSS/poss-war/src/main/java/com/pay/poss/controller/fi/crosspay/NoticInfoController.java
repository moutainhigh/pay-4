package com.pay.poss.controller.fi.crosspay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.util.StringUtil;

/**
 * 公共设置
 * 
 * @Description
 * @file NoticInfoController
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2012 hnapayay Corporation. All rights reserved.
 *          Date
 */
public class NoticInfoController extends MultiActionController {

	private String queryInit;
	//private NoticeInfoService noticeInfoService;

	public void setQueryInit(String queryInit) {
		this.queryInit = queryInit;
	}

	private String recordList;

	public void setRecordList(String recordList) {
		this.recordList = recordList;
	}

	public String addPage;

	public void setAddPage(String addPage) {
		this.addPage = addPage;
	}

	/**
	 * 默认查询页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(queryInit);
	}

	/**
	 * 查询公共列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String title = StringUtil.null2String(request.getParameter("title"));

		return new ModelAndView(recordList);
	}

	/**
	 * 添加跳转
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toAdd(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(addPage);
	}

	/**
	 * 添加
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			// NoticeInfo noticeInfo = new NoticeInfo();
			// noticeInfo.setTitle(title);
			// noticeInfo.setContent(content);
			// noticeInfo.setCreateDate(new Date());
			// noticeInfo.setStatus("1");
			// Authentication authentication =
			// SecurityContextHolder.getContext().getAuthentication();
			// SessionUserHolder sessionUserHolder = (SessionUserHolder)
			// authentication.getPrincipal();
			// String userName = sessionUserHolder.getUsername();
			// noticeInfo.setOperator(userName);
			//
			// noticeInfoService.createNoticeInfo(noticeInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView(queryInit);
	}

	/**
	 * 添加跳转
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toUpdate(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		// NoticeInfo noticeInfo =
		// this.noticeInfoService.findById(Long.valueOf(id));
		return new ModelAndView(addPage).addObject("result", null);
	}

	/**
	 * 修改
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView update(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String id = request.getParameter("id");
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			// NoticeInfo noticeInfo = new NoticeInfo();
			// noticeInfo.setId(Long.valueOf(id));
			// noticeInfo.setTitle(title);
			// noticeInfo.setContent(content);
			// noticeInfo.setCreateDate(new Date());
			// Authentication authentication =
			// SecurityContextHolder.getContext().getAuthentication();
			// SessionUserHolder sessionUserHolder = (SessionUserHolder)
			// authentication.getPrincipal();
			// String userName = sessionUserHolder.getUsername();
			// noticeInfo.setOperator(userName);
			// noticeInfoService.updateNoticeInfo(noticeInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView(queryInit);
	}

	/**
	 * 审核公告
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView audit(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String id = request.getParameter("id");
			String status = request.getParameter("status");
			// NoticeInfo noticeInfo = new NoticeInfo();
			// noticeInfo.setId(Long.valueOf(id));
			// noticeInfo.setStatus(status);
			// Authentication authentication =
			// SecurityContextHolder.getContext().getAuthentication();
			// SessionUserHolder sessionUserHolder = (SessionUserHolder)
			// authentication.getPrincipal();
			// String userName = sessionUserHolder.getUsername();
			// noticeInfo.setOperator(userName);
			// noticeInfoService.updateNoticeInfo(noticeInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView(queryInit);
	}

	/**
	 * 删除公告
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String id = request.getParameter("id");

			// NoticeInfo noticeInfo = new NoticeInfo();
			// noticeInfo.setId(Long.valueOf(id));
			// noticeInfoService.deleteNoticeInfo(Long.parseLong(id));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView(queryInit);
	}

	public ModelAndView updateStatus(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String id = request.getParameter("id");

			// NoticeInfo noticeInfo = new NoticeInfo();
			// noticeInfo.setId(Long.valueOf(id));
			// noticeInfo.setStatus("0");
			// noticeInfoService.updateStatus(noticeInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView(queryInit);
	}
}
