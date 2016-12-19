 /** @Description 
 * @project 	poss-withdraw
 * @file 		ImportWithdraw.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-6		Henry.Zeng			Create 
*/
package com.pay.fo.controller.fundout.fileservice;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.pay.fo.controller.fundout.WithdrawBaseController;
import com.pay.fo.controller.fundout.common.utils.WithdrawStaticDataUtils;
import com.pay.fundout.withdraw.common.util.WithDrawConstants;
import com.pay.fundout.withdraw.dto.fileservice.WebQueryWithDrawDTO;
import com.pay.fundout.withdraw.dto.fileservice.WithdrawBatchInfoDTO;
import com.pay.fundout.withdraw.dto.operatorlog.OperatorlogDTO;
import com.pay.fundout.withdraw.dto.result.WithdrawImportFileDTO;
import com.pay.fundout.withdraw.dto.result.WithdrawRcResultSummaryDTO;
import com.pay.fundout.withdraw.dto.result.WithdrawReconcileResultDTO;
import com.pay.fundout.withdraw.service.fileservice.QueryBatchFileService;
import com.pay.fundout.withdraw.service.result.ParserFileHandlerService;
import com.pay.fundout.withdraw.service.result.WdReconcileResultService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.common.fileprocess.io.FileHandler;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.DateUtil;
import com.pay.util.SpringControllerUtils;

/**
 * <p>导入提现结果文件控制器</p>
 * @author Henry.Zeng
 * @since 2010-9-6
 * @see 
 */
public class ImportWithdrawResultController extends WithdrawBaseController {
	
	private ParserFileHandlerService fileHandlerService;
	
	/**
	 * @param fileHandlerService the fileHandlerService to set
	 */
	public void setFileHandlerService(ParserFileHandlerService fileHandlerService) {
		this.fileHandlerService = fileHandlerService;
	}

	private QueryBatchFileService queryBatchFileService ;
	
	public void setQueryBatchFileService(
			final QueryBatchFileService queryBatchFileService) {
		this.queryBatchFileService = queryBatchFileService;
	}
	
	private WdReconcileResultService reconcileResultService;
	
	public void setReconcileResultService(
			final WdReconcileResultService reconcileResultService) {
		this.reconcileResultService = reconcileResultService;
	}
	
	
	/**
	 * 初始化导入页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView initImport(HttpServletRequest request , HttpServletResponse response){
		
		ModelAndView modelAndView = new ModelAndView(urlMap.get("initImport"));
		
		WithdrawImportFileDTO withdrawImportFileDTO = new WithdrawImportFileDTO();
		
		bind(request, withdrawImportFileDTO, "withdrawImportFileDTO", null);
		
		modelAndView.addObject("dto", withdrawImportFileDTO);
		
		return modelAndView;
	}
	
	
	/**
	 * 导入提现结果文件
	 * @param request
	 * @param response
	 * @return
	 * @author Henry.Zeng
	 * @throws ServletException 
	 * @see
	 */
	public ModelAndView importWdResult(HttpServletRequest request , HttpServletResponse response) throws ServletException {
		
		final MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		
		final CommonsMultipartFile orginalFile = (CommonsMultipartFile) multipartRequest.getFile("orginalFile");
		
		boolean flag = FileHandler.getFileHandler().uploadFile(orginalFile);
		
		ModelAndView modelAndView = new ModelAndView();
		
		WithdrawImportFileDTO importFileDTO = new WithdrawImportFileDTO();
		importFileDTO.setFileName(orginalFile.getOriginalFilename());
		bind(request, importFileDTO, "importFileDTO", null);
		modelAndView.addObject("dto", importFileDTO);
		String viewName = "";
		if(flag){
			try{
				importFileDTO.setOperators(SessionUserHolderUtil.getLoginId());
				importFileDTO.setFilePath(FileHandler.getFileHandler().getFilePath());
				importFileDTO.setCategory(2);
				Map<String,Object> validata = fileHandlerService.parserFileRdTx(importFileDTO);
				if("scuess".equals(validata.get("validataInfo"))){
					//查询导入结果文件汇总信息
					Map<String,Object> param = new HashMap<String, Object>();
					param.put("batchNum", importFileDTO.getBatchNum());
					param.put("bankCode", importFileDTO.getBankCode());
					param.put("category", 2);
					param.put("fileBusiType", importFileDTO.getTradeType());
					WithdrawRcResultSummaryDTO dto = reconcileResultService.queryRcResultSummaryInfo(param);
					viewName = urlMap.get("resultInit");
					modelAndView.addObject("resultSummaryDto", dto);
					modelAndView.addObject("batchNum", importFileDTO.getBatchNum());
					modelAndView.addObject("bankCode", importFileDTO.getBankCode());
					modelAndView.addObject("tradeType", importFileDTO.getTradeType());
					modelAndView.addObject("statusList", WithdrawStaticDataUtils.getResultStatus());
					modelAndView.addObject("bankStatusList", WithdrawStaticDataUtils.getBankStatus());	
				}else{
					viewName = urlMap.get("initImport");
					modelAndView.addObject("errorMsg", validata.get("validataInfo"));
				}
			}catch (Exception e) {
				log.error("import file failure"+e.getMessage(),e);
				viewName = urlMap.get("initImport");
				modelAndView.addObject("errorMsg", e.getMessage());
			}
		}else{
			viewName = urlMap.get("initImport");
			modelAndView.addObject("errorMsg", "系统出现异常,导入文件失败!");
		}
//		super.saveOperatorLog();
		modelAndView.setViewName(viewName);
		return modelAndView;
	}
	
	/**
	 * 查询提现结果文件匹配成功信息
	 * @param request
	 * @param response
	 * @return
	 * @author Henry.Zeng
	 * @see
	 */
	public ModelAndView queryResultFileListScuess(HttpServletRequest request , HttpServletResponse response){
		
		String batchNum = request.getParameter("batchNum");
		String bankCode = request.getParameter("bankCode");
		Integer busiFlag = Integer.valueOf(request.getParameter("busiFlag"));
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("batchNum",batchNum);
		param.put("bankCode",bankCode);
		param.put("busiFlag",busiFlag);
		param.put("category", "2");
		param.put("fileBusiType", request.getParameter("tradeType"));
		Page<WithdrawReconcileResultDTO> page = PageUtils.getPage(request);
		
		page = reconcileResultService.queryRcResultListInfo(page,param);
		
		ModelAndView modelAndView = new ModelAndView(urlMap.get("resultScuess"));
		
		modelAndView.addObject("page", page);
		modelAndView.addObject("statusList", WithdrawStaticDataUtils.getResultStatus());		
		modelAndView.addObject("bankStatusList", WithdrawStaticDataUtils.getBankStatus());		
		return modelAndView;
	}
	/**
	 * 查询提现结果文件匹配银行返回失败信息
	 * @param request
	 * @param response
	 * @return
	 * @author Henry.Zeng
	 * @see
	 */
	public ModelAndView queryResultFileListFailure(HttpServletRequest request , HttpServletResponse response){
		String batchNum = request.getParameter("batchNum");
		String bankCode = request.getParameter("bankCode");
		Integer busiFlag = Integer.valueOf(request.getParameter("busiFlag"));
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("batchNum",batchNum);
		param.put("bankCode",bankCode);
		param.put("busiFlag",busiFlag);
		param.put("category", "2");
		param.put("fileBusiType", request.getParameter("tradeType"));
		Page<WithdrawReconcileResultDTO> page = PageUtils.getPage(request);
		
		page = reconcileResultService.queryRcResultListInfo(page,param);
		
		ModelAndView modelAndView = new ModelAndView(urlMap.get("resultFail"));
		modelAndView.addObject("statusList", WithdrawStaticDataUtils.getResultStatus());
		modelAndView.addObject("bankStatusList", WithdrawStaticDataUtils.getBankStatus());	
		modelAndView.addObject("page", page);
		
		return modelAndView;
	}
	/**
	 * 查询提现结果文件匹配银行返回进行中信息
	 * @param request
	 * @param response
	 * @return
	 * @author Henry.Zeng
	 * @see
	 */
	public ModelAndView queryResultFileListIng(HttpServletRequest request , HttpServletResponse response){
		String batchNum = request.getParameter("batchNum");
		String bankCode = request.getParameter("bankCode");
		Integer busiFlag = Integer.valueOf(request.getParameter("busiFlag"));
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("batchNum",batchNum);
		param.put("bankCode",bankCode);
		param.put("busiFlag",busiFlag);
		param.put("category", "2");
		param.put("fileBusiType", request.getParameter("tradeType"));
		Page<WithdrawReconcileResultDTO> page = PageUtils.getPage(request);
		
		page = reconcileResultService.queryRcResultListInfo(page,param);
		
		ModelAndView modelAndView = new ModelAndView(urlMap.get("resultIng"));
		modelAndView.addObject("statusList", WithdrawStaticDataUtils.getResultStatus());
		modelAndView.addObject("bankStatusList", WithdrawStaticDataUtils.getBankStatus());	
		modelAndView.addObject("page", page);
		
		return modelAndView;
	}
	/**
	 * 查询提现结果文件不匹配
	 * @param request
	 * @param response
	 * @return
	 * @author Henry.Zeng
	 * @see
	 */
	public ModelAndView queryResultFileListNoMatch(HttpServletRequest request , HttpServletResponse response){
		String batchNum = request.getParameter("batchNum");
		String bankCode = request.getParameter("bankCode");
		Integer busiFlag = Integer.valueOf(request.getParameter("busiFlag"));
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("batchNum",batchNum);
		param.put("bankCode",bankCode);
		param.put("busiFlag",busiFlag);
		param.put("category", "2");
		param.put("fileBusiType", request.getParameter("tradeType"));
		Page<WithdrawReconcileResultDTO> page = PageUtils.getPage(request);
		
		page = reconcileResultService.queryRcResultListInfo(page,param);
		
		ModelAndView modelAndView = new ModelAndView(urlMap.get("resultNoMatch"));
		modelAndView.addObject("statusList", WithdrawStaticDataUtils.getResultStatus());
		modelAndView.addObject("bankStatusList", WithdrawStaticDataUtils.getBankStatus());	
		modelAndView.addObject("page", page);
		
		return modelAndView;
	}

	@Override
	public OperatorlogDTO buildOperatorLog() {
		OperatorlogDTO operatorlogDTO = new OperatorlogDTO();
		operatorlogDTO.setBusiOrderId("wd_import_file_error");
		return null;
	}
	
	public ModelAndView initResult(HttpServletRequest request , HttpServletResponse response){
		String batchNum = request.getParameter("batchNum");
		String bankCode = request.getParameter("bankCode");
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("batchNum", batchNum);
		param.put("bankCode", bankCode);
		param.put("category",2);
		param.put("fileBusiType", request.getParameter("tradeType"));
		param.put("tradeType", request.getParameter("tradeType"));
		WithdrawRcResultSummaryDTO withdrawRcResultSummaryDTO =  reconcileResultService.queryRcResultSummaryInfo(param);
		param.put("resultSummaryDto", withdrawRcResultSummaryDTO);
		return new ModelAndView(urlMap.get("resultInit"),param);
	}
	
	/**
	 * 查询导入结果初始化页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request , HttpServletResponse response){
		return new ModelAndView(urlMap.get("queryResultInit"),DateUtil.getInitTime());
	}
	/**
	 * 查询导入结果初始化页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException 
	 */
	public ModelAndView queryImportedFileList(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		
		WebQueryWithDrawDTO webQueryWithDrawDTO = new WebQueryWithDrawDTO();
		
		webQueryWithDrawDTO.setBatchStatus(4);
		String paramStatus = WithDrawConstants.BATCH_FILE_STATUS_4 + "," + WithDrawConstants.BATCH_FILE_STATUS_5;
//		webQueryWithDrawDTO.setParamStatus(paramStatus);
		webQueryWithDrawDTO.setParamStatus(paramStatus);
		
		bind(request, webQueryWithDrawDTO, "webQueryWithDrawDTO", null);
		
		Page<WithdrawBatchInfoDTO> page = PageUtils.getPage(request);
		
		Map<String,Object> outMap = queryBatchFileService.queryImportedFileList(page, webQueryWithDrawDTO);
		
		return new ModelAndView(urlMap.get("queryResultList"),outMap);
	}
	
	/**
	 * 退回导入操作
	 * @param request
	 * @param response
	 * @return
	 * @throws PossException 
	 */
	public ModelAndView refuseImport(HttpServletRequest request , HttpServletResponse response) throws PossException{
		String batchNum = request.getParameter("batchNum");
		String bankCode = request.getParameter("bankCode");
		String fileKy = request.getParameter("gFileKy");
		String tradeType = request.getParameter("tradeType");
		HashMap<String,String> params = new HashMap<String, String>();
		params.put("batchNum", batchNum);
		params.put("bankCode", bankCode);
		params.put("fileKy", fileKy);
		params.put("tradeType", tradeType);
		String errorMsg = "";
		try{
			reconcileResultService.refuseImportRdTx(params);
			SpringControllerUtils.renderText(response, "success");
		}catch(Exception e){
			errorMsg = e.getMessage();
			SpringControllerUtils.renderText(response, errorMsg);
		}
		return null;
	}
	/**
	 * 
	 *  @author Jonathen Ni
	 */
	public ModelAndView importConfirm(HttpServletRequest request , HttpServletResponse response) throws PossException{
		String orderId = request.getParameter("orderId");
		String issucc = request.getParameter("issucc");
		String resultKy = request.getParameter("resultKy");
		this.reconcileResultService.singleImportConfirmRdTx(orderId, issucc, resultKy);
		
		return new ModelAndView(urlMap.get("resultIng"));
	}
	
	/**
	 * 确认导入操作
	 * @param request
	 * @param response
	 * @return
	 * @throws PossException 
	 */
	public ModelAndView sureImport(HttpServletRequest request , HttpServletResponse response){
		String batchNum = request.getParameter("batchNum");
		String bankCode = request.getParameter("bankCode");
		String fileKy = request.getParameter("gFileKy");
		HashMap<String,String> params = new HashMap<String, String>();
		params.put("batchNum", batchNum);
		params.put("bankCode", bankCode);
		params.put("fileKy", fileKy);
		String errorMsg = "";
		try {
			reconcileResultService.sureImportRdTx(params);
			SpringControllerUtils.renderText(response, "success");
		} catch (PossException e) {
			errorMsg = e.getMessage();
			SpringControllerUtils.renderText(response, errorMsg);
		}
		return null;
	}
	
	/**
	 * 退回导入
	 * @param request
	 * @param response
	 * @return
	 * @throws PossException 
	 */
	public ModelAndView batchSureImport(HttpServletRequest request , HttpServletResponse response){
		String batImportPrms = request.getParameter("batImportPrms");
		String errorMsg = "";
		try {
			reconcileResultService.batchSureImport(batImportPrms);
			errorMsg = "文件确认成功，具体处理结果请到“订单中心”进行查询！";
		} catch (PossException e) {
			errorMsg = e.getMessage();
		}
		return new ModelAndView(urlMap.get("queryResultInit"),DateUtil.getInitTime()).addObject("errorMsg", errorMsg);
	}
	
	// 查看批次结果详情
	public ModelAndView queryBatchDetail(HttpServletRequest request , HttpServletResponse response)throws ServletException{
		String batchNum = request.getParameter("batchNum");
		WithdrawRcResultSummaryDTO wdRcResSummaryDto = this.queryBatchFileService.showBatchDetail(batchNum);
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("wdRcResSummaryDto", wdRcResSummaryDto);
		return new ModelAndView(urlMap.get("showBatchDetail"), model);
	}
}
