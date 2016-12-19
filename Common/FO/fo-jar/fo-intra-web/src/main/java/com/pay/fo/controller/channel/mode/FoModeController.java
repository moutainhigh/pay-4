/**
 *  File: FoModeController.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-28      Sunsea.Li      Changes
 *  
 */
package com.pay.fo.controller.channel.mode;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.pay.fo.controller.channel.AbstractChannelController;
import com.pay.fundout.channel.dto.mode.FundoutModeDTO;
import com.pay.fundout.channel.service.mode.FoModeService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.security.util.SessionUserHolderUtil;

/**出款方式管理控制器
 * @author Sunsea.Li
 *
 */
public class FoModeController extends AbstractChannelController {
	private FoModeService foModeService;

	public void setFoModeService(FoModeService foModeService) {
		this.foModeService = foModeService;
	}
	
	/**
	 * 新增出款方式初始化页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView index(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		String viewName = urlMap.get("addFoMode");
		return new ModelAndView(viewName).addObject("statusList", super.channelStatus);
	}
	
	/**
	 * 新增出款方式动作
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView addFoMode(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		String viewName = urlMap.get("addFoMode");
		FundoutModeDTO dto = new FundoutModeDTO();
		bind(request, dto, "dto", null);
		
		if(log.isDebugEnabled()){
			log.debug("dto:"+dto);
		}
		
		dto.setOperator(SessionUserHolderUtil.getLoginId());
		try{
			Map<String,Object> model = foModeService.createFoMode(dto);
			model.put("info", "添加成功,对应的出款方式名称是:【"+dto.getCode()+"="+dto.getName()+"】");
			model.put("statusList", super.channelStatus);
			return new ModelAndView(viewName,model);
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ModelAndView(viewName).addObject("info","添加失败,对应的出款方式已存在是:"+dto.getName()).addObject("statusList", super.channelStatus);
		}
	}
	
	/**
	 * 出款方式查询初始化页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView initSearch(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		String viewName = urlMap.get("searchModeInit");
		return new ModelAndView(viewName).addObject("statusList", super.channelStatus);
	}
	
	/**
	 * 出款方式分页列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView search(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		String viewName = urlMap.get("searchModeList");
		FundoutModeDTO dto = new FundoutModeDTO();
		bind(request, dto, "dto", null);
		Page<FundoutModeDTO> page = PageUtils.getPage(request);
		Map<String,Object> model = foModeService.queryFoModeInfo(page, dto);
		model.put("statusList", super.channelStatus);
		return new ModelAndView(viewName,model);
	}
	
	/**
	 * init修改出款方式信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView initModify(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		
		ModelAndView modelAndView = new ModelAndView();
		FundoutModeDTO fundoutBusinessDTO =  foModeService.queryFoModeInfoById(Long.valueOf(request.getParameter("modeId")));
		modelAndView.addObject("statusList", super.channelStatus);
		modelAndView.addObject("dto", fundoutBusinessDTO);
		modelAndView.setViewName(urlMap.get("modify"));
		return modelAndView;
		
	}
	/**
	 * execute修改出款方式信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView modifyFundoutModeInfo(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		
		FundoutModeDTO dto = new FundoutModeDTO();
	
		bind(request, dto, "", null);
		if(log.isInfoEnabled()){
			log.info("dto :"+dto);
		}
		dto.setOperator(SessionUserHolderUtil.getLoginId());
		foModeService.updateFoModeInfo(dto);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("statusList", super.channelStatus);
		modelAndView.addObject("dto", dto);
		modelAndView.setViewName(urlMap.get("searchModeInit"));
		return modelAndView;
	}
}
