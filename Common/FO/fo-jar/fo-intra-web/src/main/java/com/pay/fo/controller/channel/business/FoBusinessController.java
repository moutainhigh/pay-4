/**
 *  File: FoBusinessController.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-28      Sunsea.Li      Changes
 *  
 */
package com.pay.fo.controller.channel.business;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.pay.fo.controller.channel.AbstractChannelController;
import com.pay.fundout.channel.dto.business.FundoutBusinessDTO;
import com.pay.fundout.channel.service.business.FoBusinessService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.security.util.SessionUserHolderUtil;

/**出款业务管理控制器
 * @author Sunsea.Li
 *
 */
public class FoBusinessController extends AbstractChannelController {
	private FoBusinessService foBusinessService;

	public void setFoBusinessService(FoBusinessService foBusinessService) {
		this.foBusinessService = foBusinessService;
	}
	
	/**
	 * 新增出款业务初始化页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView index(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		String viewName = urlMap.get("addFoBusiness");
		return new ModelAndView(viewName).addObject("statusList", super.channelStatus);
	}
	
	/**
	 * 新增出款业务动作
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView addFoBusiness(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		String viewName = urlMap.get("addFoBusiness");
		FundoutBusinessDTO dto = new FundoutBusinessDTO();
		bind(request, dto, "dto", null);
		dto.setOperator(SessionUserHolderUtil.getLoginId());
		try{
			Map<String,Object> model = foBusinessService.createFoBusiness(dto);
			model.put("info", "添加成功,对应的业务名称是:【"+dto.getCode()+"="+dto.getName()+"】");
			model.put("statusList", super.channelStatus);
			return new ModelAndView(viewName,model);
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ModelAndView(viewName).addObject("info","添加失败,对应的业务已存在是:"+dto.getName()).addObject("statusList", super.channelStatus);
		}
		
	}
	
	/**
	 * 出款业务查询初始化页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView initSearch(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		String viewName = urlMap.get("searchBusinessInit");
		return new ModelAndView(viewName).addObject("statusList", super.channelStatus);
	}
	
	/**
	 * 出款业务分页列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView search(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		String viewName = urlMap.get("searchBusinessList");
		FundoutBusinessDTO dto = new FundoutBusinessDTO();
		bind(request, dto, "dto", null);
		Page<FundoutBusinessDTO> page = PageUtils.getPage(request);
		Map<String,Object> model = foBusinessService.queryFoBusinessInfo(page, dto);
		model.put("statusList", super.channelStatus);
		return new ModelAndView(viewName,model);
	}
	
	/**
	 * init修改出款业务信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView initModify(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		
		ModelAndView modelAndView = new ModelAndView();
		FundoutBusinessDTO fundoutBusinessDTO =  foBusinessService.queryFoBusinessInfoById(Long.valueOf(request.getParameter("businessId")));
		modelAndView.addObject("statusList", super.channelStatus);
		modelAndView.addObject("dto", fundoutBusinessDTO);
		modelAndView.setViewName(urlMap.get("modify"));
		return modelAndView;
		
	}
	/**
	 * execute修改出款业务信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView modifyFundoutBusinessInfo(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		
		FundoutBusinessDTO dto = new FundoutBusinessDTO();
	
		bind(request, dto, "", null);
		if(log.isInfoEnabled()){
			log.info("dto :"+dto);
		}
		dto.setOperator(SessionUserHolderUtil.getLoginId());
		
		foBusinessService.updateFoBusinessInfo(dto);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("statusList", super.channelStatus);
		modelAndView.addObject("dto", dto);
		modelAndView.setViewName(urlMap.get("searchBusinessInit"));
		return modelAndView;
	}
	
	
}
