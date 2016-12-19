 /** @Description 
 * @project 	fo-channel-web
 * @file 		ConfigBankController.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-2		Henry.Zeng			Create 
*/
package com.pay.fo.controller.channel.configbank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.fo.controller.channel.AbstractChannelController;
import com.pay.fundout.channel.dto.channel.FundoutChannelQueryDTO;
import com.pay.fundout.channel.dto.configbank.FundoutConfigBankDTO;
import com.pay.fundout.channel.model.channel.FundoutChannel;
import com.pay.fundout.channel.model.configbank.FundoutConfigBank;
import com.pay.fundout.channel.service.channel.FundoutChannelService;
import com.pay.fundout.channel.service.configbank.ConfigBankService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.security.util.SessionUserHolderUtil;

/**
 * <p>配置目到行与出款行的映射</p>
 * @author Henry.Zeng
 * @since 2010-11-2
 * @see 
 */
public class ConfigBankController extends AbstractChannelController {
	
	private transient ConfigBankService configBankService;

	private FundoutChannelService channelService;
	
	public void setConfigBankService(ConfigBankService configBankService) {
		this.configBankService = configBankService;
	}
	
	public void setChannelService(final FundoutChannelService channelService) {
		this.channelService = channelService;
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
		List targetBankList = super.bankInfoFacadeService.getWithdrawBankList();
		modelAndView.addObject("targetBankList",  targetBankList);
		modelAndView.addObject("channelList",  channelService.queryFoChannelList(null));
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
		FundoutConfigBankDTO dto = new FundoutConfigBankDTO();
		bind(request, dto, "dto", null);
		dto.setOperator(SessionUserHolderUtil.getLoginId());
		if(log.isInfoEnabled()){
			log.info("dto :"+dto);
		}
		Map<String,Object> outMap = configBankService.addConfigBank(dto);
		modelAndView.setViewName(urlMap.get("add"));
		modelAndView.addObject("statusList", super.channelStatus);
		modelAndView.addObject("targetBankList",  super.bankInfoFacadeService.getWithdrawBankList());
		modelAndView.addObject("channelList",  channelService.queryFoChannelList(null));
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
		FundoutConfigBankDTO dto =  configBankService.queryConfigBankById(Long.valueOf(request.getParameter("configId")));
		modelAndView.addObject("statusList", super.channelStatus);
		modelAndView.addObject("dto", dto);
		FundoutChannel fundoutChannel = channelService.getFundoutChannelById(dto.getChannelId());
		if(fundoutChannel.getModeCode().equals("0")){
			String targetBankName = super.bankInfoFacadeService.getBankNameById(fundoutChannel.getBankId());
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", fundoutChannel.getBankId().toString());
			map.put("text", targetBankName);
			List<Map<String, String>> targetBankList = new ArrayList<Map<String, String>>();
			targetBankList.add(map);
			modelAndView.addObject("targetBankList",  targetBankList);
		}else{
			modelAndView.addObject("targetBankList",  super.bankInfoFacadeService.getWithdrawBankList());
		}
		modelAndView.addObject("channelList",  channelService.queryFoChannelList(null));
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
		FundoutConfigBankDTO dto = new FundoutConfigBankDTO();
		bind(request, dto, "", null);
		if(log.isInfoEnabled()){
			log.info("dto :"+dto);
		}
		dto.setOperator(SessionUserHolderUtil.getLoginId());
		configBankService.modifyConfigBankRdTx(dto);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("statusList", super.channelStatus);
		modelAndView.addObject("dto", dto);
		List list = configBankService.getAllConfigBankList();
		modelAndView.addObject("targetBankList",  list);
//		modelAndView.addObject("targetBankList",  super.bankInfoFacadeService.getWithdrawBankList());
		modelAndView.addObject("channelList",  channelService.queryFoChannelList(null));
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
		modelAndView.addObject("statusList", super.channelStatus);
		List list = configBankService.getAllConfigBankList();
		modelAndView.addObject("targetBankList",  list);
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
		FundoutConfigBankDTO dto = new FundoutConfigBankDTO();
		bind(request, dto, "dto", null);
		Page<FundoutConfigBankDTO> page = PageUtils.getPage(request);
		Map<String,Object> model = configBankService.queryConfigBank(page, dto);
		model.put("statusList", super.channelStatus);
		model.put("targetBankList",  super.bankInfoFacadeService.getWithdrawBankList());
		model.put("dto", dto);
		return new ModelAndView(viewName,model);
	}
	
	/**
	 * 出款方式和出款业务级联出款渠道
	 * @param request
	 * @param response
	 * @return response.print
	 */
	public ModelAndView linkChannel(HttpServletRequest request , HttpServletResponse response){
		String modeId = request.getParameter("modeId");
		String businessId = request.getParameter("businessId");
		
		String innerHtml = getChannelByModeId(modeId,businessId);
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(innerHtml);
		} catch (IOException e) {
			log.info("获取输出流出错");
		}
		return null;
	}
	
	/**
	 * 根据渠道编号取得目的银行
	 * @param request
	 * @param response
	 * @return response.print
	 */
	public ModelAndView findChannel(HttpServletRequest request , HttpServletResponse response){
		String channelId = request.getParameter("channelId");
		String modeCode = request.getParameter("modeCode");
		
		String innerHtml = "";
		if(StringUtils.isNotBlank(modeCode)){
			if("0".equals(modeCode)){
				innerHtml = getTargetBankByChannelId(channelId);
			}else{
				innerHtml = getTargetBankList();
			}
		}else{
			innerHtml = getTargetBankList();
		}
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(innerHtml);
		} catch (IOException e) {
			log.info("获取输出流出错");
		}
		return null;
	}
	
	/**
	 * 根据出款渠道级联出款业务和出款方式
	 * @param request
	 * @param response
	 * @return response.print
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView linkModeAndBusiness(HttpServletRequest request , HttpServletResponse response){
		String channelId = request.getParameter("channelId");
		Map param = new HashMap();
		param.put("code", channelId);
		FundoutChannelQueryDTO channel = channelService.getFundoutChannelByCode(param);
		String returnHtml = "";
		if (channel != null) {
			returnHtml = channel.getModeCode() + "&" + channel.getBusinessCode();
		}
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(returnHtml);
		} catch (IOException e) {
			log.info("获取输出流出错");
		}
		return null;
	}
	
	/**
	 * 检查同一银行同一业务是否存在重复
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView checkRepeat(HttpServletRequest request , HttpServletResponse response){
		
		String targetBankId = request.getParameter("targetBankId"); //出款银行
		String businessId = request.getParameter("businessId"); //出款 业务
		String modeId = request.getParameter("modeId"); //出款方式
		String channelId = request.getParameter("channelId"); //出款渠道
		String configId = request.getParameter("configId");
		FundoutConfigBank configBank = new FundoutConfigBank();
		configBank.setStatus(1l);
		if (targetBankId != null) {
			configBank.setTargetBankId(Long.valueOf(targetBankId));
		}
		if (businessId != null) {
			configBank.setBusinessId(Long.valueOf(businessId));
		}
		if (modeId != null) {
			configBank.setModeId(Long.valueOf(modeId));
		}
		if (channelId != null){
			configBank.setChannelId(Long.valueOf(channelId));
		}
		List<FundoutConfigBank> list = configBankService.queryConfigBank(configBank);
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
	 * 检查同一目的银行是否配置多个手工出款渠道
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView checkChannel(HttpServletRequest request , HttpServletResponse response){
		String targetBankId = request.getParameter("targetBankId"); //目的银行
		String configId = request.getParameter("configId");
		String modeCode = request.getParameter("modeCode");
		FundoutConfigBank configBank = new FundoutConfigBank();
		configBank.setStatus(1l);
		if(StringUtils.isNotBlank(targetBankId)){
			configBank.setTargetBankId(Long.valueOf(targetBankId));
		}
		List<FundoutConfigBank> list = configBankService.queryConfigBank(configBank);
		String isExist = "0";
		if (list != null && list.size() > 0) {
			for(FundoutConfigBank dto : list){
				Long channelId = dto.getChannelId();
				// 修改情况,并且是对手工渠道渠道进行修改
				if(StringUtils.isNotBlank(configId)){
					if(modeCode.equals("1") && channelService.getFundoutChannelById(channelId).getModeCode().equals("1") && dto.getConfigId().longValue() != Long.parseLong(configId)){
						isExist = dto.getConfigId().toString();
						break;
					}
				}else{
					// 新增情况，并且新增的是手工渠道
					if(modeCode.equals("1") && channelService.getFundoutChannelById(channelId).getModeCode().equals("1")){
						isExist = dto.getConfigId().toString();
						break;
					}
				}
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
	 * 根据出款渠道或者出款业务获取出款方式
	 * @param modeId
	 * @return
	 */
	private String getChannelByModeId(String modeId,String businessId) {
		StringBuilder sb = new StringBuilder();
		Map<String,String> params = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(modeId)) {
			params.put("modeCode", modeId);
		}
		if (StringUtils.isNotEmpty(businessId)) {
			params.put("businessCode", businessId);
		}
		params.put("status", "1");
		List<FundoutChannelQueryDTO> channelList = channelService.queryFoChannelList(params);
		sb.append("<option value=''>请选择</option>");
		for (FundoutChannelQueryDTO fundoutChannel : channelList) {
			sb.append("<option value='"+fundoutChannel.getCode()+"'>"+fundoutChannel.getChannelName()+"</option>");
		}
		return sb.toString();
	}
	
	/**
	 * 获取所有的目的银行列表,作为下拉
	 * @return
	 */
	public String getTargetBankList(){
		StringBuilder sb = new StringBuilder();
		List<Map<String,String>> targetBankList = super.bankInfoFacadeService.getWithdrawBankList();
		sb.append("<option value=''>请选择</option>");
		for (Map<String,String> map : targetBankList) {
			sb.append("<option value='"+map.get("value")+"'>"+map.get("text")+"</option>");
		}
		return sb.toString();
	}
	
	/**
	 * 根据出款渠道编号获得目的银行信息
	 * @param modeId
	 * @return
	 */
	private String getTargetBankByChannelId(String channelId){
		StringBuilder sb = new StringBuilder();
		FundoutChannel channel = channelService.getFundoutChannelById(Long.valueOf(channelId));
		String targetBankName = super.bankInfoFacadeService.getBankNameById(channel.getBankId());
		sb.append("<option value=''>请选择</option>");
		sb.append("<option value='" + channel.getBankId() +"'>" + targetBankName + "</option>");
		return sb.toString();
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
		FundoutConfigBankDTO dto =  configBankService.queryConfigBankById(configId);
		log.info("config bank delete by "+ SessionUserHolderUtil.getLoginId() +"target bank:"+dto.getTargetBankId());
		configBankService.delConfigBankRdTx(dto.getConfigId());
		return initSearch(request,response);
	}
}
