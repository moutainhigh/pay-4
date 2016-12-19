 /** @Description 
 * @project 	fo-channel-web
 * @file 		ConfigBankController.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-2		Henry.Zeng			Create 
*/
package com.pay.fo.controller.channel.configchannelrelation;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.pay.fo.controller.channel.AbstractChannelController;
import com.pay.fundout.channel.dto.configchannelrelation.FundoutConfigChannelRelationDTO;
import com.pay.fundout.channel.model.configchannelrelation.FundoutConfigChannelRelation;
import com.pay.fundout.channel.service.configchannelrelation.ConfigChannelRelationService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.security.util.SessionUserHolderUtil;

/**
 * <p>配置出款产品与出款渠道的映射</p>
 * @author Henry.Zeng
 * @since 2010-11-2
 * @see 
 */
public class ConfigChannelRelationController extends AbstractChannelController {
	
	private ConfigChannelRelationService configChannelRelationService;

	public void setConfigChannelRelationService(ConfigChannelRelationService configChannelRelationService) {
		this.configChannelRelationService = configChannelRelationService;
	}

	/**
	 * 初始化新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request , HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("statusList", super.channelStatus);
		modelAndView.setViewName(urlMap.get("add"));
		return modelAndView;
	}
	
	/**
	 * 新增配置
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException 
	 */
	public ModelAndView addConfig(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		ModelAndView modelAndView = new ModelAndView();
		FundoutConfigChannelRelationDTO dto = new FundoutConfigChannelRelationDTO();
		bind(request, dto, "dto", null);
		dto.setOperator(SessionUserHolderUtil.getLoginId());
		if(log.isInfoEnabled()){
			log.info("dto :"+dto);
		}
		Map<String,Object> outMap = configChannelRelationService.addConfigChannelRelation(dto);
		modelAndView.setViewName(urlMap.get("add"));
		modelAndView.addObject("statusList", super.channelStatus);
//		modelAndView.addObject("targetBankList",  super.bankInfoFacadeService.getWithdrawBankList());
		modelAndView.addAllObjects(outMap);
		return modelAndView;
	}
	
	/**
	 * 初始化修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView initModify(HttpServletRequest request , HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView();
		FundoutConfigChannelRelationDTO dto =  configChannelRelationService.queryConfigChannelRelationById(Long.valueOf(request.getParameter("configId")));
		modelAndView.addObject("statusList", super.channelStatus);
		modelAndView.addObject("dto", dto);
//		modelAndView.addObject("targetBankList",  super.bankInfoFacadeService.getWithdrawBankList());
		modelAndView.setViewName(urlMap.get("modify"));
		return modelAndView;
	}
	/**
	 * 修改操作
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException 
	 */
	public ModelAndView modify(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		FundoutConfigChannelRelationDTO dto = new FundoutConfigChannelRelationDTO();
		bind(request, dto, "", null);
		if(log.isInfoEnabled()){
			log.info("dto :"+dto);
		}
		dto.setOperator(SessionUserHolderUtil.getLoginId());
		configChannelRelationService.modifyConfigChannelRelationRdTx(dto);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("statusList", super.channelStatus);
		modelAndView.addObject("dto", dto);
//		modelAndView.addObject("targetBankList",  super.bankInfoFacadeService.getWithdrawBankList());
		modelAndView.setViewName(urlMap.get("searchInit"));
		return modelAndView;

	}
	/**
	 * 初始化查询页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView initSearch(HttpServletRequest request , HttpServletResponse response){
		String viewName = urlMap.get("searchInit");
		ModelAndView modelAndView = new ModelAndView();
		List list = super.channelStatus;
		modelAndView.addObject("statusList", super.channelStatus);
		modelAndView.setViewName(viewName);
		return modelAndView;
	}
	/**
	 * 查询操作
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView search(HttpServletRequest request , HttpServletResponse response){
		String viewName = urlMap.get("searchList");
		FundoutConfigChannelRelationDTO dto = new FundoutConfigChannelRelationDTO();
		bind(request, dto, "dto", null);
		Page<FundoutConfigChannelRelationDTO> page = PageUtils.getPage(request);
		Map<String,Object> model = configChannelRelationService.queryConfigChannelRelation(page, dto);
		model.put("statusList", super.channelStatus);
//		model.put("targetBankList",  super.bankInfoFacadeService.getWithdrawBankList());
		model.put("dto", dto);
		return new ModelAndView(viewName,model);
	}
	
	/**
	 * 检查同一产品同一渠道是否存在重复
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView checkRepeat(HttpServletRequest request , HttpServletResponse response){
		String productCode = request.getParameter("productCode"); //出款银行
		String channelId = request.getParameter("channelId"); //出款渠道
		String configId = request.getParameter("configId");
		FundoutConfigChannelRelation configChannelRelation = new FundoutConfigChannelRelation();
		configChannelRelation.setStatus(1l);
		if (channelId != null){
			configChannelRelation.setChannelId(Long.valueOf(channelId));
		}
		if(productCode != null){
			configChannelRelation.setProductCode(productCode);
		}
		List<FundoutConfigChannelRelation> list = configChannelRelationService.queryConfigChannelRelation(configChannelRelation);
		String isExist = "0";
		if (list != null && list.size() > 0) {
			if(configId != null){
				// 修改情况
				if(!(list.get(0).getConfigId().toString().equals(configId))){
					isExist = list.get(0).getConfigId().toString();
				}
			}else{
				// 新增情况
				isExist = list.get(0).getConfigId().toString();
			}
		}
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(isExist);
		} catch (IOException e) {
			log.info("获取输出流出错");
		}
		return null;
	}
	
	
	/**
	 * 修改操作
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException 
	 */
	public ModelAndView del(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		Long configId = Long.valueOf(request.getParameter("configId"));
		FundoutConfigChannelRelationDTO dto =  configChannelRelationService.queryConfigChannelRelationById(configId);
		configChannelRelationService.delConfigChannelRelationRdTx(dto.getConfigId());
		return initSearch(request,response);
	}
}
