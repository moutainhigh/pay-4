/**
 *  File: WithdrawRuleController.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-17     jason.li    Changes
 *  
 *
 */
package com.pay.fo.controller.fundout.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.fo.controller.fundout.WithdrawBaseController;
import com.pay.fundout.withdraw.dto.bank.WithdrawBankConfigDTO;
import com.pay.fundout.withdraw.dto.busitype.WithdrawBusinessDTO;
import com.pay.fundout.withdraw.dto.operatorlog.OperatorlogDTO;
import com.pay.fundout.withdraw.dto.rule.WithdrawRuleDTO;
import com.pay.fundout.withdraw.dto.type.WithdrawTypeDTO;
import com.pay.fundout.withdraw.model.bank.WithdrawBankConfig;
import com.pay.fundout.withdraw.model.busitype.WithdrawBusiness;
import com.pay.fundout.withdraw.model.rule.WithdrawRule;
import com.pay.fundout.withdraw.model.type.WithdrawType;
import com.pay.fundout.withdraw.service.withdrawrule.WithdrawRuleService;

/**
 * @author jason.li 
 *
 */
public class WithdrawRuleController extends WithdrawBaseController {
	private WithdrawRuleService withdrawRuleService;
	private Log log = LogFactory.getLog(WithdrawRuleController.class);
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		return new ModelAndView(urlMap.get("typeInit"));
	}
	public ModelAndView typeInit(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		return new ModelAndView(urlMap.get("typeInit"));
	}
	private Map<String,Object> loadDropDownList(String liststr){
		Map<String,Object> model = new HashMap<String, Object>();
		Map<String,String> map = new HashMap<String,String>();
		if(liststr.indexOf("t")>-1){
			List<WithdrawTypeDTO> list = withdrawRuleService.searchTypes(map);
			model.put("typelist",list);
		}
		if(liststr.indexOf("b")>-1){
			List<WithdrawBusinessDTO> list2 = withdrawRuleService.searchBusinesses(map);
			model.put("businesslist",list2);
		}	
		if(liststr.indexOf("k")>-1){
			List<WithdrawBankConfigDTO> list3 = withdrawRuleService.searchBankConfigs(map);
			model.put("banklist",list3);
		}
		if(liststr.indexOf("r")>-1){
			List<WithdrawRuleDTO> list4 = withdrawRuleService.searchRules(map);
			model.put("rulelist",list4);
		}
		return model;
	}
	public ModelAndView bankInit(HttpServletRequest request,
			HttpServletResponse response) throws Exception{

		return new ModelAndView(urlMap.get("bankInit"),loadDropDownList("tb"));
	}
	public ModelAndView businessInit(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		//调用servcie query List
		List<WithdrawTypeDTO> list = withdrawRuleService.searchTypes(map);
		Map<String,Object> model = new HashMap<String, Object>();
		model.put("typelist",list);

		return new ModelAndView(urlMap.get("businessInit"),loadDropDownList("t"));
	}
	public ModelAndView ruleInit(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		return new ModelAndView(urlMap.get("ruleInit"),loadDropDownList("tbkr"));
	}
	public ModelAndView typeForm(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		return new ModelAndView(urlMap.get("typeCreate"));
	}
	public ModelAndView businessForm(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		//调用servcie query List
		List<WithdrawTypeDTO> list = withdrawRuleService.searchTypes(map);
		Map<String,Object> model = new HashMap<String, Object>();
		model.put("typelist",list);

		return new ModelAndView(urlMap.get("businessCreate"),loadDropDownList("t"));
	}
	public ModelAndView bankForm(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		return new ModelAndView(urlMap.get("bankCreate"),loadDropDownList("tb"));
	}
	public ModelAndView ruleForm(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		return new ModelAndView(urlMap.get("ruleCreate"),loadDropDownList("tbk"));
	}	
	
	
	public ModelAndView createType(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.error("createType----------------");
		//1 获取页面参数
	
		WithdrawTypeDTO withdrawTypeDTO = new WithdrawTypeDTO();
		bind(request, withdrawTypeDTO, "withdrawTypeDTO", null);
		// 调用service Create
		WithdrawType wt = new WithdrawType();
		BeanUtils.copyProperties(withdrawTypeDTO, wt);
		long wtid = withdrawRuleService.createWithdrawType(wt);
		
		//调用servcie query List

		return new ModelAndView(urlMap.get("typeInit"));
		
	}
	public ModelAndView updateTypeStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WithdrawType wt= new WithdrawType();
		wt.setSequenceId(Integer.parseInt(request.getParameter("id")));
		wt.setStatus(Integer.parseInt(request.getParameter("status")));
		withdrawRuleService.updateWithdrawType(wt);
		Map<String,String> map = new HashMap<String,String>();
		map.put("sequenceId", request.getParameter("id"));
		map.put("status", request.getParameter("status"));
		//调用servcie query List
		List<WithdrawTypeDTO> list = withdrawRuleService.searchTypes(map);
		Map<String,Object> model = new HashMap<String, Object>();
		model.put("typelist",list);
		return new ModelAndView(urlMap.get("typeList"),model);
	}
	public ModelAndView searchTypes(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("sequenceId", request.getParameter("id"));
		map.put("typeName", request.getParameter("name"));
		map.put("status", request.getParameter("status"));
		//调用servcie query List
		List<WithdrawTypeDTO> list = withdrawRuleService.searchTypes(map);
		Map<String,Object> model = new HashMap<String, Object>();
		model.put("typelist",list);
		return new ModelAndView(urlMap.get("typeList"),model);
		
	}
	public ModelAndView createBusiness(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//1 获取页面参数
	
		WithdrawBusinessDTO WithdrawBusinessDTO = new WithdrawBusinessDTO();
		bind(request, WithdrawBusinessDTO, "WithdrawBusinessDTO", null);
		// 调用service Create
		WithdrawBusiness wt = new WithdrawBusiness();
		BeanUtils.copyProperties(WithdrawBusinessDTO, wt);
		withdrawRuleService.createWithdrawBusiness(wt);

		return new ModelAndView(urlMap.get("businessInit"),loadDropDownList("t"));
	}
	public ModelAndView updateBusinessStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WithdrawBusiness wb = new WithdrawBusiness();

		wb.setSequenceId(Integer.parseInt(request.getParameter("id")));
		wb.setStatus(Integer.parseInt(request.getParameter("status")));
		withdrawRuleService.updateWithdrawBusiness(wb);
		//调用servcie query List
		Map<String,String> map = new HashMap<String,String>();
		map.put("sequenceId", request.getParameter("id"));
		map.put("status", request.getParameter("status"));
		//调用servcie query List
		List<WithdrawBusinessDTO> list = withdrawRuleService.searchBusinesses(map);
		Map<String,Object> model = new HashMap<String, Object>();
		model.put("businesslist",list);
		return new ModelAndView(urlMap.get("businessList"),model);
	}
	public ModelAndView searchBusinesses(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("sequenceId", request.getParameter("id"));
		map.put("businessName", request.getParameter("name"));
		map.put("fundOutId", request.getParameter("fundOutId"));
		map.put("status", request.getParameter("status"));
		//调用servcie query List
		List<WithdrawBusinessDTO> list = withdrawRuleService.searchBusinesses(map);
		Map<String,Object> model = new HashMap<String, Object>();
		model.put("businesslist",list);
		return new ModelAndView(urlMap.get("businessList"),model);
		
	}
	public ModelAndView createBank(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//1 获取页面参数
	
		WithdrawBankConfigDTO WithdrawBankConfigDTO = new WithdrawBankConfigDTO();
		bind(request, WithdrawBankConfigDTO, "WithdrawBankConfigDTO", null);
		// 调用service Create
		WithdrawBankConfig wt = new WithdrawBankConfig();
		BeanUtils.copyProperties(WithdrawBankConfigDTO, wt);
		withdrawRuleService.createWithdrawBankConfig(wt);
		
		//调用servcie query List
		return new ModelAndView(urlMap.get("bankInit"),loadDropDownList("tb"));
		
	}
	public ModelAndView updateBankStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 调用service Create
		WithdrawBankConfig wbc = new WithdrawBankConfig();
		wbc.setSequenceId(Integer.parseInt(request.getParameter("id")));
		wbc.setStatus(Integer.parseInt(request.getParameter("status")));
		withdrawRuleService.updateWithdrawBankConfig(wbc);
		//调用servcie query List
		Map<String,String> map = new HashMap<String,String>();
		map.put("sequenceId", request.getParameter("id"));
		map.put("status", request.getParameter("status"));
		//调用servcie query List
		List<WithdrawBankConfigDTO> list = withdrawRuleService.searchBankConfigs(map);
		Map<String,Object> model = new HashMap<String, Object>();
		model.put("banklist",list);
		return new ModelAndView(urlMap.get("bankList"),model);
	}
	public ModelAndView searchBanks(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("sequenceId", request.getParameter("id"));
		map.put("bankName", request.getParameter("name"));
		map.put("withdrawBusiId", request.getParameter("businessid"));
		map.put("withdrawTypeId", request.getParameter("typeid"));
		map.put("status", request.getParameter("status"));
		//调用servcie query List
		List<WithdrawBankConfigDTO> list = withdrawRuleService.searchBankConfigs(map);
		Map<String,Object> model = new HashMap<String, Object>();
		model.put("banklist",list);
		return new ModelAndView(urlMap.get("bankList"),model);
		
	}
	
	public ModelAndView createRule(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//1 获取页面参数
	
		WithdrawRuleDTO WithdrawRuleDTO = new WithdrawRuleDTO();
		bind(request, WithdrawRuleDTO, "WithdrawRuleDTO", null);
		// 调用service Create
		WithdrawRule wt = new WithdrawRule();
		BeanUtils.copyProperties(WithdrawRuleDTO, wt);
		withdrawRuleService.createWithdrawRule(wt);
		
		return new ModelAndView(urlMap.get("ruleInit"),loadDropDownList("tbk"));
		
	}
	public ModelAndView updateRuleStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 调用service Create
		WithdrawRule wr = new WithdrawRule();
		wr.setSequenceId(Integer.parseInt(request.getParameter("id")));
		wr.setStatus(Integer.parseInt(request.getParameter("status")));
		withdrawRuleService.updateWithdrawRule(wr);
		//调用servcie query List
		Map<String,String> map = new HashMap<String,String>();
		map.put("sequenceId", request.getParameter("id"));
		map.put("status", request.getParameter("status"));
		//调用servcie query List
		List<WithdrawRuleDTO> list = withdrawRuleService.searchRules(map);
		Map<String,Object> model = new HashMap<String, Object>();
		model.put("rulelist",list);
		return new ModelAndView(urlMap.get("ruleList"),model);
	}
	public ModelAndView searchRules(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("withdrawType", request.getParameter("withdrawType"));
		map.put("bankAcctType", request.getParameter("bankAcctType"));
		map.put("status", request.getParameter("status"));
		map.put("priority", request.getParameter("priority"));
		map.put("toBankCode", request.getParameter("toBankCode"));
		map.put("withdrawBankId", request.getParameter("withdrawBankId"));
		//调用servcie query List
		List<WithdrawRuleDTO> list =  withdrawRuleService.searchRules( map);

		Map<String,Object> model = new HashMap<String, Object>();
		model.put("rulelist",list);
		return new ModelAndView(urlMap.get("ruleList"),model);
		
	}

	/**
	 * @param withdrawRuleService the withdrawRuleService to set
	 */
	public void setwithdrawRuleService(WithdrawRuleService withdrawRuleService) {
		this.withdrawRuleService = withdrawRuleService;
	}
	@Override
	public OperatorlogDTO buildOperatorLog() {
		// TODO Auto-generated method stub
		return null;
	}
}
