 /** @Description 
 * @project 	poss-withdraw
 * @file 		QuerySubmitBnakFileController.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-7		Henry.Zeng			Create 
*/
package com.pay.fo.controller.fundout.fileservice;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.pay.fo.controller.fundout.WithdrawBaseController;
import com.pay.fundout.withdraw.dto.fileservice.WebQueryWithDrawDTO;
import com.pay.fundout.withdraw.dto.fileservice.WithdrawBatchInfoDTO;
import com.pay.fundout.withdraw.dto.operatorlog.OperatorlogDTO;
import com.pay.fundout.withdraw.service.fileservice.QueryBatchFileService;
import com.pay.fundout.withdraw.service.reviewfofile.ReviewFoFileService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.service.inf.input.BankInfoFacadeService;
import com.pay.util.DateUtil;

/**
 * <p>查询提交银行文件</p>
 * @author Henry.Zeng
 * @since 2010-9-7
 * @see 
 */
public class QuerySubmitBnakFileController extends WithdrawBaseController {
	
	
	private transient Map<String,ReviewFoFileService> reviewFoMap;
	
	/**
	 * @param reviewFoMap the reviewFoMap to set
	 */
	public void setReviewFoMap(Map<String, ReviewFoFileService> reviewFoMap) {
		this.reviewFoMap = reviewFoMap;
	}

	private QueryBatchFileService queryBatchFileService;
	
	public void setQueryBatchFileService(QueryBatchFileService queryBatchFileService) {
		this.queryBatchFileService = queryBatchFileService;
	}
	
	private BankInfoFacadeService bankFacadeService;
	
	public void setBankFacadeService(BankInfoFacadeService bankFacadeService) {
		this.bankFacadeService = bankFacadeService;
	}
	
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) {
		return new ModelAndView(urlMap.get("initBankFile"),DateUtil.getInitTime()).
		addObject("withdrawBankList", bankFacadeService.getWithdrawBankList());
	}
	
	/**
	 * 查询提交银行文件信息
	 * @param request
	 * @param response
	 * @return
	 * @author Henry.Zeng
	 * @throws ServletException 
	 * @see
	 */
	public ModelAndView querySubmitBankFile(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		
		WebQueryWithDrawDTO webQueryWithDrawDTO = new WebQueryWithDrawDTO();
		
		bind(request, webQueryWithDrawDTO, "webQueryWithDrawDTO", null);
		
		Page<WithdrawBatchInfoDTO> page = PageUtils.getPage(request);
		
		page = queryBatchFileService.queryBankFile(page, webQueryWithDrawDTO);
		
		Map<String,Page<WithdrawBatchInfoDTO>> model = new HashMap<String, Page<WithdrawBatchInfoDTO>>();
		model.put("page", page);
		return new ModelAndView(urlMap.get("listBankFile"), model).addObject("withdrawBankList", bankFacadeService.getWithdrawBankList());
	}
	/**
	 * 进入出款复核页面
	 * @param request
	 * @param response
	 * @return
	 * @author Henry.Zeng
	 * @see
	 */
	public ModelAndView openReviewPage(HttpServletRequest request , HttpServletResponse response){
		Map<String,String> paramMap = initRequestToMap(request);
		return new ModelAndView(urlMap.get("reviewFoFile"),paramMap).addObject("withdrawBankList", bankFacadeService.getWithdrawBankList())
			;
	}
	
	
	private Map<String,String> initRequestToMap(HttpServletRequest request){
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("batchNum", request.getParameter("batchNum"));
		paramMap.put("bankCode", request.getParameter("bankCode"));
		paramMap.put("fileType", request.getParameter("fileType"));
		paramMap.put("channelCode", request.getParameter("channelCode"));
		paramMap.put("fileBusiType", request.getParameter("fileBusiType"));
		return paramMap;
	}
	
	private Map<String,Object> initRequestToObjectMap(HttpServletRequest request){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("batchNum", request.getParameter("batchNum"));
		paramMap.put("bankCode", request.getParameter("bankCode"));
		paramMap.put("fileType", request.getParameter("fileType"));
		paramMap.put("channelCode", request.getParameter("channelCode"));
		paramMap.put("fileBusiType", request.getParameter("fileBusiType"));
		return paramMap;
	}
	
	public OperatorlogDTO buildOperatorLog() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
