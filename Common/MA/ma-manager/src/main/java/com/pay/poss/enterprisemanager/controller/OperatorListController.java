package com.pay.poss.enterprisemanager.controller;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.controller.AbstractBaseController;
import com.pay.poss.enterprisemanager.dto.OperatorSearchDto;
import com.pay.poss.enterprisemanager.dto.OperatorSearchListDto;
import com.pay.poss.enterprisemanager.enums.OperatorEnum;
import com.pay.poss.enterprisemanager.service.IOperatorService;

/**
 * @Description 
 * @project 	ma-manager
 * @file 		OperatorListController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 */
public class OperatorListController extends AbstractBaseController {
	private Log log = LogFactory.getLog(OperatorListController.class);
	
	private IOperatorService operatorService;
	
	//查询页面
	public ModelAndView index(HttpServletRequest request , HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName(urlMap.get("index"));
		return modelAndView;
	}
	
	//详情
	public ModelAndView gotoDetail(HttpServletRequest request , HttpServletResponse response) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(urlMap.get("detail"));
		
		String operatorId = request.getParameter("operatorId");
		OperatorSearchListDto dto= operatorService.queryOperator(operatorId);
		modelAndView.addObject("operator", dto);
		
		OperatorEnum[] operatorEnums = OperatorEnum.values();
		modelAndView.addObject("operatorEnums", operatorEnums);
		
		return modelAndView;
	}

	//查询结果
	public ModelAndView doQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("operatorService() is running...");
		
		OperatorSearchDto OperatorSearchDto = new OperatorSearchDto();
		OperatorSearchDto.setMemberCode(request.getParameter("memberCode")== null ? "" :request.getParameter("memberCode"));
		OperatorSearchDto.setMerchantCode(request.getParameter("merchantCode")== null ? "" :request.getParameter("merchantCode"));
		OperatorSearchDto.setLoginName(request.getParameter("loginName")== null ? "" : request.getParameter("loginName"));
		
		Page<OperatorSearchListDto> page = PageUtils.getPage(request);
		OperatorSearchDto.setPageEndRow((page.getPageNo()*page.getPageSize()));
		if((page.getPageNo()-1)==0){
			OperatorSearchDto.setPageStartRow(0);
		}else{
			OperatorSearchDto.setPageStartRow((page.getPageNo()-1)*page.getPageSize());
		}
		List<OperatorSearchListDto> operatorList = operatorService.queryOperator(OperatorSearchDto);
		Integer operatorCount = operatorService.queryOperatorCount(OperatorSearchDto);
		
		page.setResult(operatorList);
		page.setTotalCount(operatorCount);
		
		Map<String, Object> dataMap = new Hashtable<String, Object>();
		OperatorEnum[] operatorEnums = OperatorEnum.values();
		dataMap.put("operatorEnums", operatorEnums);
		dataMap.put("page",page);
		return new ModelAndView(urlMap.get("result")).addAllObjects(dataMap);
	}

	/**
	 * @param operatorService the operatorService to set
	 */
	public void setOperatorService(IOperatorService operatorService) {
		this.operatorService = operatorService;
	}
	
}
