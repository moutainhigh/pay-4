package com.pay.poss.userrelation.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.userrelation.dto.UserRelationDto;
import com.pay.poss.userrelation.service.IUserRelationService;
import com.pay.util.StringUtil;


/**
 * 
 *  File: UserRelationController.java
 *  Description:
 *  Copyright 2006-2012 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2014-5-7   liuqinghe     Create
 *
 */
public class UserRelationController extends MultiActionController {

	private static final Log log = LogFactory.getLog(UserRelationController.class);
	private static final String rootNode = "root";
	
	private IUserRelationService userRelationService;

	private String addView;
	
	private String updateView;
	
	private String searchView;
	
	private String listView;
	
	
	
	public void setAddView(String addView) {
		this.addView = addView;
	}


	public void setSearchView(String searchView) {
		this.searchView = searchView;
	}


	public void setListView(String listView) {
		this.listView = listView;
	}
	
	


	public void setUpdateView(String updateView) {
		this.updateView = updateView;
	}


	public void setUserRelationService(IUserRelationService userRelationService) {
		this.userRelationService = userRelationService;
	}
	
	
	public ModelAndView toSearchPage(HttpServletRequest request,
			HttpServletResponse response){
		return new ModelAndView(searchView);
	}
	
	public ModelAndView queryList(HttpServletRequest request,
			HttpServletResponse response, UserRelationDto dto)
			throws Exception {
		Page page = PageUtils.getPage(request);
		List<UserRelationDto> info = this.userRelationService.queryUserRelationByCondition(dto,page);//memberRelationService.queryMemberRelationByCondition(dto, page);
		page.setResult(info);
		return new ModelAndView(listView).addObject("page", page);
	}
	
	
	public ModelAndView toAddPage(HttpServletRequest request,
			HttpServletResponse response){
		return new ModelAndView(addView);
	}
	
	public ModelAndView doAdd(HttpServletRequest request,
			HttpServletResponse response,UserRelationDto dto){
		Map<String,Object> resMap = new HashMap<String, Object>();
		try{
			String pLoginId = dto.getPloginId();
			String loginId = dto.getLoginId();
			UserRelationDto p_userRelationDto = null;
			UserRelationDto c_userRelationDto = null;
			if(!StringUtil.isEmpty(pLoginId) && !rootNode.equals(pLoginId)){
				p_userRelationDto = userRelationService.findUserRelatoinByLoginId(pLoginId);
				if(null == p_userRelationDto){
					p_userRelationDto = new UserRelationDto();
					p_userRelationDto.setLoginId(pLoginId);
					p_userRelationDto.setName(dto.getPname());
					p_userRelationDto.setPloginId(rootNode);
					p_userRelationDto.setPname("");
					userRelationService.createUserRelation(p_userRelationDto);
				}
			}
			c_userRelationDto = userRelationService.findUserRelatoinByLoginId(loginId);
			if(null == c_userRelationDto)
				userRelationService.createUserRelation(dto);
			
			resMap.put("msg", "增加信息成功");
			if(null != p_userRelationDto && null != c_userRelationDto){
				resMap.put("msg", "关联信息已经存在");
			}
			
		}catch(Exception e){
			log.error("add user relation fail :",e);
			
			resMap.put("msg", "增加信息失败");
		}
		return new ModelAndView(addView).addAllObjects(resMap);
	}
	
	public ModelAndView doDelete(HttpServletRequest request,
			HttpServletResponse response){
		String id = request.getParameter("id");
		Map<String,Object> resMap = new HashMap<String, Object>();
		try{
			if(!StringUtil.isEmpty(id)){
				Long t_id = Long.parseLong(id);
				if(log.isDebugEnabled()){
					log.debug("delete user relation id = " +id);
				}
				userRelationService.deleteUserRelation(t_id);
			}else{
				if(log.isDebugEnabled()){
					log.debug("delete user relation id is null");
				}
			}
		
			resMap.put("msg", "删除信息成功");
		}catch(Exception e){
			log.error("delete user relation fail :",e);
			
			resMap.put("msg", "删除信息失败");
		}
		return new ModelAndView(searchView).addAllObjects(resMap);
	}
	
	public ModelAndView toUpdatePage(HttpServletRequest request,
			HttpServletResponse response){
		String id = request.getParameter("id");
		UserRelationDto relationDto = null;
		try{
			if(!StringUtil.isEmpty(id)){
				Long t_id = Long.parseLong(id);
				if(log.isDebugEnabled()){
					log.debug("delete user relation id = " +id);
				}
				relationDto = userRelationService.findById(t_id);
			}else{
				if(log.isDebugEnabled()){
					log.debug("delete user relation id is null");
				}
			}
		
		}catch(Exception e){
			log.error("delete user relation fail :",e);
		}
		
		return new ModelAndView(updateView).addObject("relationDto",relationDto);
	}
	
	public ModelAndView doUpdate(HttpServletRequest request,
			HttpServletResponse response,UserRelationDto dto){
		Map<String,Object> resMap = new HashMap<String, Object>();
		try{
			String pLoginId = dto.getPloginId();
			UserRelationDto p_userRelationDto = null;
			if(!StringUtil.isEmpty(pLoginId) && !rootNode.equals(pLoginId)){
				p_userRelationDto = userRelationService.findUserRelatoinByLoginId(pLoginId);
				if(null == p_userRelationDto){
					p_userRelationDto = new UserRelationDto();
					p_userRelationDto.setLoginId(pLoginId);
					p_userRelationDto.setName(dto.getPname());
					p_userRelationDto.setPloginId(rootNode);
					p_userRelationDto.setPname("");
					userRelationService.createUserRelation(p_userRelationDto);
				}
			}
			
			userRelationService.updateUserRelation(dto);
			resMap.put("msg", "修改信息成功");

		}catch(Exception e){
			log.error("update user relation fail :",e);
			resMap.put("msg", "修改信息失败");
		}
		return new ModelAndView(updateView).addAllObjects(resMap);
	}
	
	public ModelAndView isExistUser(HttpServletRequest request,
			HttpServletResponse response){
		String loginId = request.getParameter("loginId");
		try {
		boolean bool = userRelationService.isExistUser(loginId);
		if(bool){
			response.getWriter().write("1");
		  }else
			response.getWriter().write("0");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	

}
