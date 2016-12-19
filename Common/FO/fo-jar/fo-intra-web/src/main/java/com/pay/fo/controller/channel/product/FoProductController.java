/**
 *  File: FoBusinessController.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-28      Sunsea.Li      Changes
 *  
 */
package com.pay.fo.controller.channel.product;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.pay.fo.controller.channel.AbstractChannelController;
import com.pay.fundout.channel.dto.product.FundoutProductDTO;
import com.pay.fundout.channel.service.product.FoProductService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;

/**出款产品管理控制器
 * @author Sunsea.Li
 *
 */
public class FoProductController extends AbstractChannelController {
	private FoProductService foProductService;

	public void setFoProductService(FoProductService foProductService) {
		this.foProductService = foProductService;
	}
	
	/**
	 * 新增出款产品初始化页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView index(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		String viewName = urlMap.get("addFoProduct");
		return new ModelAndView(viewName).addObject("statusList", super.channelStatus);
	}
	
	/**
	 * 新增出款产品动作
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView addFoProduct(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		String viewName = urlMap.get("addFoProduct");
		FundoutProductDTO dto = new FundoutProductDTO();
		bind(request, dto, "dto", null);
		try{
			Map<String,Object> model = foProductService.createFoProduct(dto);
			model.put("info", "添加成功,对应的产品名称是:【"+dto.getCode()+"="+dto.getName()+"】");
			model.put("statusList", super.channelStatus);
			return new ModelAndView(viewName,model);
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ModelAndView(viewName).addObject("info","添加失败,对应的产品已存在是:"+dto.getName()).addObject("statusList", super.channelStatus);
		}
		
	}
	
	/**
	 * 出款产品查询初始化页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView initSearch(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		String viewName = urlMap.get("searchProductInit");
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
		String viewName = urlMap.get("searchProductList");
		FundoutProductDTO dto = new FundoutProductDTO();
		bind(request, dto, "dto", null);
		Page<FundoutProductDTO> page = PageUtils.getPage(request);
		Map<String,Object> model = foProductService.queryFoProductInfo(page, dto);
		model.put("statusList", super.channelStatus);
		return new ModelAndView(viewName,model);
	}
	
	/**
	 * init修改出款产品信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView initModify(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		
		ModelAndView modelAndView = new ModelAndView();
		FundoutProductDTO fundoutProductDTO =  foProductService.queryFoProductInfoByCode(request.getParameter("code"));
		modelAndView.addObject("statusList", super.channelStatus);
		modelAndView.addObject("dto", fundoutProductDTO);
		modelAndView.setViewName(urlMap.get("modify"));
		return modelAndView;
		
	}
	/**
	 * execute修改出款产品信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView modifyFundoutProductInfo(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		
		FundoutProductDTO dto = new FundoutProductDTO();
	
		bind(request, dto, "", null);
		if(log.isInfoEnabled()){
			log.info("dto :"+dto);
		}
		
		foProductService.updateFoProductInfo(dto);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("statusList", super.channelStatus);
		modelAndView.addObject("dto", dto);
		modelAndView.setViewName(urlMap.get("searchProductInit"));
		return modelAndView;
	}
	
	
}
