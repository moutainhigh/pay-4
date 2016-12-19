 /** @Description 
 * @project 	fo-channel-web
 * @file 		FundoutBankController.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-27		Henry.Zeng			Create 
*/
package com.pay.fo.controller.channel.bank;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.pay.fo.controller.channel.AbstractChannelController;
import com.pay.fundout.channel.dto.bank.FundoutBankDTO;
import com.pay.fundout.channel.service.bank.FundoutBankService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.security.util.SessionUserHolderUtil;

/**
 * <p></p>
 * @author Henry.Zeng
 * @since 2010-10-27
 * @see 
 */
public class FundoutBankController extends AbstractChannelController {
	
	private FundoutBankService fundoutBankService;
	
	public void setFundoutBankService(final FundoutBankService fundoutBankService) {
		this.fundoutBankService = fundoutBankService;
	}
	
	/**
	 * 添加一个出款银行
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView addFundoutBankInfo(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		
		FundoutBankDTO dto = new FundoutBankDTO();
		
		bind(request, dto, "", null);
		
		dto.setOperator(SessionUserHolderUtil.getLoginId());
		
		try{
			fundoutBankService.addFundoutBankInfo(dto);
			return new ModelAndView(urlMap.get("add")).
			addObject("statusList", super.channelStatus).
			addObject("bankInfoList", super.bankInfoFacadeService.getWithdrawBankList()).addObject("info", "保存成功对应银行编号是:【"+dto.getBankId()+"="+dto.getBankName()+"】");
		}catch (PossUntxException e) {
			return new ModelAndView(urlMap.get("add")).
			addObject("statusList", super.channelStatus).
			addObject("bankInfoList", super.bankInfoFacadeService.getWithdrawBankList()).addObject("info", "保存失败,对应银行编号已存在:【"+dto.getBankId()+"="+dto.getBankName()+"】");
		}
	}
	
	/**
	 * init修改出款银行信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView initModify(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		
		ModelAndView modelAndView = new ModelAndView();
		FundoutBankDTO fundoutBankDTO =  fundoutBankService.queryFundoutBank(request.getParameter("bankId"));
		modelAndView.addObject("statusList", super.channelStatus);
		modelAndView.addObject("bankInfoList", super.bankInfoFacadeService.getWithdrawBankList());
		modelAndView.addObject("dto", fundoutBankDTO);
		modelAndView.setViewName(urlMap.get("modify"));
		return modelAndView;
		
	}
	/**
	 * execute修改出款银行信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView modifyFundoutBankInfo(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		
		FundoutBankDTO dto = new FundoutBankDTO();
		
		bind(request, dto, "", null);
		
		dto.setOperator(SessionUserHolderUtil.getLoginId());
		
		fundoutBankService.updateFundoutBankInfo(dto);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("statusList", super.channelStatus);
		modelAndView.addObject("bankInfoList", super.bankInfoFacadeService.getWithdrawBankList());
		modelAndView.addObject("dto", dto);
		modelAndView.setViewName(urlMap.get("init"));
		return modelAndView;
	}
	/**
	 * 根据主键查询显示详细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView searchByBankId(HttpServletRequest request , HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView();
		FundoutBankDTO fundoutBankDTO =  fundoutBankService.queryFundoutBank(request.getParameter("bankId"));
		modelAndView.addObject("statusList", super.channelStatus);
		modelAndView.addObject("bankInfoList", super.bankInfoFacadeService.getWithdrawBankList());
		modelAndView.addObject("dto", fundoutBankDTO);
		modelAndView.setViewName(urlMap.get("detail"));
		return modelAndView;
	}
	
	
	
	/**
	 * 根据页面的条件分页查询
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView initSearch(HttpServletRequest request , HttpServletResponse response) throws ServletException {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("statusList", super.channelStatus);
		modelAndView.addObject("bankInfoList", super.bankInfoFacadeService.getWithdrawBankList());
		modelAndView.setViewName(urlMap.get("init"));
		return modelAndView;
	}
	/**
	 * 根据页面的条件分页查询
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView search(HttpServletRequest request , HttpServletResponse response) throws ServletException {
		
		Page<FundoutBankDTO> page = PageUtils.getPage(request);
		FundoutBankDTO dto = new FundoutBankDTO();
		bind(request, dto, "", null);
		page = fundoutBankService.queryFundoutBankPage(page, dto);
		return new ModelAndView(urlMap.get("list")).addObject("page", page).addObject("statusList", super.channelStatus);
	}
	
	/**
	 * 初始化页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView index(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("statusList", super.channelStatus);
		modelAndView.addObject("bankInfoList", super.bankInfoFacadeService.getWithdrawBankList());
		modelAndView.setViewName(urlMap.get("add"));
		return modelAndView;
	}
	
}
