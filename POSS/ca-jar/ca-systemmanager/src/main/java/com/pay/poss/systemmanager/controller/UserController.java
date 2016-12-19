package com.pay.poss.systemmanager.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.security.model.SessionUserHolder;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.poss.systemmanager.formbean.UserFormBean;
import com.pay.poss.systemmanager.formbean.UserRelation;
import com.pay.poss.systemmanager.formbean.UserSearchFormBean;
import com.pay.poss.systemmanager.service.IUserService;

/**
 * 用户管理
 * 
 * @Description
 * @project poss-systemmanager
 * @file UserController.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-7-27 Volcano.Wu Create
 */
public class UserController extends MultiActionController {

	private Log logger = LogFactory.getLog(getClass());
	private String viewName;
	private String toView;
	private String addView;
	private String editView;

	private IUserService userService;

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	// 依赖注入viewName的参数,例如一个JSP文件，作为展示model的视图
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public void setToView(String toView) {
		this.toView = toView;
	}

	public void setAddView(String addView) {
		this.addView = addView;
	}

	public void setEditView(String editView) {
		this.editView = editView;
	}

	// userInit
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView(toView).addObject("sessionUser",
				SessionUserHolderUtil.getSessionUserHolder());
	}

	// 用户列表
	public ModelAndView search(HttpServletRequest request,
			HttpServletResponse response, UserSearchFormBean userSearchFormBean)
			throws Exception {

		Map<String, Page<UserFormBean>> model = new HashMap<String, Page<UserFormBean>>();

		String userId = userSearchFormBean.getUserId();
		String userName = userSearchFormBean.getUserName();
		String status = userSearchFormBean.getStatus();

		Map<String, String> searchMap = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(userId))
			searchMap.put("userId", userId);
		if (StringUtils.isNotEmpty(userName))
			searchMap.put("userName", userName);
		if (StringUtils.isNotEmpty(status) && !"2".equals(status))
			searchMap.put("status", status);

		Page<UserFormBean> page = PageUtils.getPage(request); // 分页
		page = userService.search(page, searchMap);

		// List<UserFormBean> users = userService.queryAll(searchMap);

		model.put("page", page);
		return new ModelAndView(viewName, model);
	}

	// 初始化新增
	public ModelAndView init(HttpServletRequest req, HttpServletResponse res)
			throws ServletRequestBindingException, IOException {
		Map model = new HashMap();
		model.put("dataList", "新增数据...");
		return new ModelAndView(addView, model);
	}

	public ModelAndView insert(HttpServletRequest request,
			HttpServletResponse response, UserFormBean user)
			throws ServletRequestBindingException, IOException {
		Map model = new HashMap();
		model.put("dataList", "新增数据...");
		return new ModelAndView(viewName, model);
	}

	public ModelAndView ajaxUpdate(HttpServletRequest req,
			HttpServletResponse res, UserFormBean user)
			throws ServletRequestBindingException, IOException {
		try {

			PrintWriter out = res.getWriter();
			if ((Boolean) userService.updateUser(user))
				out.print("success");
			else
				out.print("error");
		} catch (Exception e) {
			e.getMessage();
		}
		return null;
	}

	public ModelAndView update(HttpServletRequest request, HttpServletResponse res,
			UserFormBean user) throws ServletRequestBindingException,
			IOException {
		UserRelation userR=null;
		String ifBD=request.getParameter("ifBD"); //是否是市场部  1 否 2是
		String ifLeader=request.getParameter("ifLeader"); //是否是 leader  1 否 2是
		String superior=request.getParameter("superior");//上级领导id
		String userCode = request.getParameter("userCode");
		String userName = request.getParameter("userName");
		if(ifBD.equals("2")){//市场部
			userR=new UserRelation();
			userR.setLoginId(userCode);
			userR.setName(userName);
			userR.setCreateDate(new Timestamp(new Date().getTime()));
			if(ifLeader.equals("1")){//不是leader
				if(StringUtils.isEmpty(superior)){//没有输入上级领导
					return  new ModelAndView(toView).addObject("error","请输入上级领导");	
				}else{//输入了上级领导
					userR.setLayer(superior);
				}
			}else{//是上级领导
				userR.setLayer(null);   ///上级为空则为leader
			}
		}
		if(userR!=null){
			UserRelation userRelation=	userService.findUserRelation(userR.getLoginId());
			if(userRelation!=null){
				userService.updateUserRelation(userR);
			}else{
				userService.saveUserRelation(userR);
			}
		}
		try {
			userService.updateUser(user);
		} catch (Exception e) {
			e.getMessage();
		}
		return new ModelAndView(toView);
	}

	
	
	
	public ModelAndView updateInit(HttpServletRequest req,
			HttpServletResponse res) throws ServletRequestBindingException,
			IOException {

		String id = req.getParameter("id");
		UserFormBean user = userService.queryUserByKy(id);
		UserRelation userRelation=	userService.findUserRelation(user.getUserCode());
		Map model = userService.queryAllDutyAndOrg();
			if(userRelation!=null){
				String layer = userRelation.getLayer();//层级
				UserRelation layerUser=userService.findUseRelationById(layer);
				model.put("userRelation", userRelation);
				model.put("layerUser", layerUser);
			}else{
				model.put("userRelation", null);
				model.put("layerUser", null);
			}
		model.put("user", user);
		return new ModelAndView(editView, model);
	}

	public ModelAndView delete(HttpServletRequest req,
			HttpServletResponse response)
			throws ServletRequestBindingException, IOException {

		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		String id = req.getParameter("id");
		try {
			PrintWriter out = response.getWriter();
			SessionUserHolder loginUser = SessionUserHolderUtil
					.getSessionUserHolder();
			if (loginUser != null && id.equals(loginUser.getUserKy())) {
				out.print("不能删除自己");
			} else if ((Boolean) userService.deleteUser(id))
				out.print("success");
			else
				out.print("删除出现问题");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
