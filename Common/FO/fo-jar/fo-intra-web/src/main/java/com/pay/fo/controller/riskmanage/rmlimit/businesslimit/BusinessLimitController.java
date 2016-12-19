package com.pay.fo.controller.riskmanage.rmlimit.businesslimit;

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
import com.pay.rm.service.dto.rmlimit.businesslimit.RcBusinessLimitDTO;
import com.pay.rm.service.model.rmlimit.businesslimit.RcBusinessLimit;
import com.pay.rm.service.service.rmlimit.businesslimit.RmBusinesslimitService;
import com.pay.util.SpringControllerUtils;



/**
 * 商户限额
 * @Description 
 * @project 	rm-web
 * @file 		BusinessLimitController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2010-10-21		Volcano.Wu			Create
 */
public class BusinessLimitController extends  RiskConctrolBaseController {
	
	
	private RmBusinesslimitService rmBusinesslimitService;
	
	public void setRmBusinesslimitService(RmBusinesslimitService rmBusinesslimitService) {
		this.rmBusinesslimitService = rmBusinesslimitService;
	}
	
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return new ModelAndView(URL("businessLimitInit"),this.loadDropDownList("BR"));
	}
	
	public ModelAndView readonly(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return new ModelAndView(URL("businessLimitInitReadonly"),this.loadDropDownList("BR"));
	}
	
	public ModelAndView businessLimitForm(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return new ModelAndView(URL("businessLimitCreate"),this.loadDropDownList("BR"));
	}
	
	public ModelAndView businessLimitCreate(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		RcBusinessLimitDTO rcBusinessLimitDTO = new RcBusinessLimitDTO();
		bind(request, rcBusinessLimitDTO, "RcBusinessLimitDTO", null);
		if(rcBusinessLimitDTO.getStatus()==1){
			rcBusinessLimitDTO.setSingleLimit(rcBusinessLimitDTO.getSingleLimit()*RmLimitConstants.RMYUAN1000);
			rcBusinessLimitDTO.setDayLimit(rcBusinessLimitDTO.getDayLimit()*RmLimitConstants.RMYUAN1000);
			rcBusinessLimitDTO.setMonthLimit(rcBusinessLimitDTO.getMonthLimit()*RmLimitConstants.RMYUAN1000);
		}
		String reMsg = "";
		try{
			rmBusinesslimitService.save(rcBusinessLimitDTO);
			reMsg = "新增成功！";
		}catch(PossException e){
			//e.printStackTrace();
			if(e.getCode().getCode().equals("0003"))
				reMsg ="录入失败,已存在同一业务类型限额";
		}
		
		return new ModelAndView(URL("businessLimitCreate"),this.loadDropDownList("BR")).addObject("msg", reMsg);
	}
	
	public ModelAndView searchBusinessLimits(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		map.put("sequenceId", request.getParameter("sequenceId"));
		map.put("mcc", request.getParameter("mcc"));
		map.put("businessType", request.getParameter("businessType"));
		map.put("riskLevel", request.getParameter("riskLevel"));
		map.put("status", request.getParameter("status"));
		
		Page<RcBusinessLimitDTO> page = PageUtils.getPage(request); 
		page = rmBusinesslimitService.search(page, map);
		Map<String,Object> model = this.loadDropDownList("CSL");
		model.put("page",page);
		return new ModelAndView(URL("businessLimitList"),model);
	}
	
	public ModelAndView searchBusinessLimitsReadonly(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		map.put("sequenceId", request.getParameter("sequenceId"));
		map.put("mcc", request.getParameter("mcc"));
		map.put("businessType", request.getParameter("businessType"));
		map.put("riskLevel", request.getParameter("riskLevel"));
		map.put("status", request.getParameter("status"));
		
		Page<RcBusinessLimitDTO> page = PageUtils.getPage(request); 
		page = rmBusinesslimitService.search(page, map);
		Map<String,Object> model = this.loadDropDownList("CSL");
		model.put("page",page);
		return new ModelAndView(URL("businessLimitListReadonly"),model);
	}
	
	public ModelAndView businessLimitDelete(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		StringBuffer results = new StringBuffer();
		try{
			if(StringUtils.isNotEmpty(id)){
				rmBusinesslimitService.delete(Long.valueOf(id));
				results.append("success");
			}
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}finally{
			SpringControllerUtils.renderText(response, results.toString());
		}
		return null;
	}
	
	public ModelAndView businessLimitUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception{
		RcBusinessLimitDTO rcBusinessLimitDTO = new RcBusinessLimitDTO();
		bind(request, rcBusinessLimitDTO, "RcBusinessLimitDTO", null);
		if(rcBusinessLimitDTO.getStatus()==1){
			rcBusinessLimitDTO.setSingleLimit(rcBusinessLimitDTO.getSingleLimit()*RmLimitConstants.RMYUAN1000);
			rcBusinessLimitDTO.setDayLimit(rcBusinessLimitDTO.getDayLimit()*RmLimitConstants.RMYUAN1000);
			rcBusinessLimitDTO.setMonthLimit(rcBusinessLimitDTO.getMonthLimit()*RmLimitConstants.RMYUAN1000);
		}
		StringBuffer results = new StringBuffer();
		try{
			if(rmBusinesslimitService.update(rcBusinessLimitDTO))
				results.append("success");
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}finally{
			SpringControllerUtils.renderText(response, results.toString());
		}
		return null;
	}
	
	public ModelAndView businessLimitUpdateForm(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		long id = Long.parseLong(request.getParameter("id"));
		RcBusinessLimit pojo = rmBusinesslimitService.get(id);
		if(pojo.getStatus()==1){
			pojo.setSingleLimit(pojo.getSingleLimit()/RmLimitConstants.RMYUAN1000);
			pojo.setDayLimit(pojo.getDayLimit()/RmLimitConstants.RMYUAN1000);
			pojo.setMonthLimit(pojo.getMonthLimit()/RmLimitConstants.RMYUAN1000);
		}
		Map<String,Object> model = this.loadDropDownList("BR");
		model.put("businesslimit",pojo);
		return new ModelAndView(URL("businessLimitUpdate"),model);
	}
}
