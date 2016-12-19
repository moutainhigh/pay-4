
package com.pay.fo.controller.riskmanage.rmlimit.userlimit;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.fo.controller.riskmanage.rmlimit.RiskConctrolBaseController;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.rm.base.exception.PossException;
import com.pay.rm.service.common.RmLimitConstants;
import com.pay.rm.service.dto.rmlimit.userlimit.RcUserLimitDTO;
import com.pay.rm.service.model.rmlimit.userlimit.RcUserLimit;
import com.pay.rm.service.service.rmlimit.userlimit.RmUserLimitService;
import com.pay.util.SpringControllerUtils;



/**
 * 用户限额
 * @Description 
 * @project 	rm-web
 * @file 		UserLimitController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2010-10-21		Volcano.Wu			Create
 */
public class UserLimitController extends   RiskConctrolBaseController {
	
	private RmUserLimitService rmUserLimitService;

	public void setRmUserLimitService(RmUserLimitService rmUserLimitService) {
		this.rmUserLimitService = rmUserLimitService;
	}
	
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return new ModelAndView(URL("userLimitInit"),this.loadDropDownList("BU"));
	}
	
	public ModelAndView readonly(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return new ModelAndView(URL("userLimitInitReadonly"),this.loadDropDownList("BU"));
	}

	public ModelAndView userLimitForm(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return new ModelAndView(URL("userLimitCreate"),loadDropDownList("BU"));
	}
	
	public ModelAndView userLimitCreate(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		RcUserLimitDTO rcUserLimitDTO = new RcUserLimitDTO();
		bind(request, rcUserLimitDTO, "RcUserLimitDTO", null);
		rcUserLimitDTO.setSingleLimit(rcUserLimitDTO.getSingleLimit()*RmLimitConstants.RMYUAN1000);
		rcUserLimitDTO.setDayLimit(rcUserLimitDTO.getDayLimit()*RmLimitConstants.RMYUAN1000);
		rcUserLimitDTO.setMonthLimit(rcUserLimitDTO.getMonthLimit()*RmLimitConstants.RMYUAN1000);
		
		
		String reMsg = "";
		try{
			rmUserLimitService.save(rcUserLimitDTO);
			reMsg = "新增成功！";
		}catch(PossException e){
			//e.printStackTrace();
			if(e.getCode().getCode().equals("0003"))
				reMsg ="录入失败,已存在同一业务类型限额";
		}
		return new ModelAndView(URL("userLimitCreate"),loadDropDownList("BU")).addObject("msg", reMsg);
	}
	
	public ModelAndView searchUserLimits(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		map.put("userLevel", request.getParameter("userLevel"));
		map.put("businessType", request.getParameter("businessType"));
		map.put("sequenceId", request.getParameter("sequenceId"));
		map.put("status", request.getParameter("status"));
		
		Page<RcUserLimitDTO> page = PageUtils.getPage(request); 
		page = rmUserLimitService.search(page, map);
		Map<String,Object> model = loadDropDownList("USCA");
		model.put("page",page);
		return new ModelAndView(URL("userLimitList"),model);
	}
	
	public ModelAndView searchUserLimitsReadonly(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		map.put("userLevel", request.getParameter("userLevel"));
		map.put("businessType", request.getParameter("businessType"));
		map.put("sequenceId", request.getParameter("sequenceId"));
		map.put("status", request.getParameter("status"));
		
		Page<RcUserLimitDTO> page = PageUtils.getPage(request); 
		page = rmUserLimitService.search(page, map);
		Map<String,Object> model =  loadDropDownList("USCA");
		model.put("page",page);
		return new ModelAndView(URL("userLimitListReadonly"),model);
	}
	
	public ModelAndView userLimitDelete(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		StringBuffer results = new StringBuffer();
		try{
			if(StringUtils.isNotEmpty(id)){
				rmUserLimitService.delete(Long.valueOf(id));
				results.append("success");
			}
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}finally{
			SpringControllerUtils.renderText(response, results.toString());
		}
		return null;
	}
	
	public ModelAndView userLimitUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception{
		RcUserLimitDTO rcUserLimitDTO = new RcUserLimitDTO();
		bind(request, rcUserLimitDTO, "RcUserLimitDTO", null);
		rcUserLimitDTO.setSingleLimit(rcUserLimitDTO.getSingleLimit()*RmLimitConstants.RMYUAN1000);
		rcUserLimitDTO.setDayLimit(rcUserLimitDTO.getDayLimit()*RmLimitConstants.RMYUAN1000);
		rcUserLimitDTO.setMonthLimit(rcUserLimitDTO.getMonthLimit()*RmLimitConstants.RMYUAN1000);
		StringBuffer results = new StringBuffer();
		try{
			if(rmUserLimitService.update(rcUserLimitDTO))
				results.append("success");
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}finally{
			SpringControllerUtils.renderText(response, results.toString());
		}
		return null;
	}
	
	public ModelAndView userLimitUpdateForm(HttpServletRequest request,HttpServletResponse response) throws Exception{
		long id = Long.parseLong(request.getParameter("id"));
		RcUserLimit pojo = rmUserLimitService.get(id);
		pojo.setSingleLimit(pojo.getSingleLimit()/RmLimitConstants.RMYUAN1000);
		pojo.setDayLimit(pojo.getDayLimit()/RmLimitConstants.RMYUAN1000);
		pojo.setMonthLimit(pojo.getMonthLimit()/RmLimitConstants.RMYUAN1000);
		Map<String,Object>  model = loadDropDownList("BU");
		model.put("userlimit",pojo);
		return new ModelAndView(URL("userLimitUpdate"),model);
	}
	
}
