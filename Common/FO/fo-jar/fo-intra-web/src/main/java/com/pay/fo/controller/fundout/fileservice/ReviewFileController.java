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
import com.pay.poss.service.inf.input.BankInfoFacadeService;
import com.pay.util.DateUtil;

/**		
 *  @author lIWEI
 *  @Date 2011-4-22
 *  @Description 提交银行出款文件复核相关操作控制器
 *  @Copyright Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
 */
public class ReviewFileController extends WithdrawBaseController {

	private ParserFileHandlerService fileHandlerService;
	private QueryBatchFileService queryBatchFileService;
	private WdReconcileResultService reconcileResultService;
	private BankInfoFacadeService bankFacadeService;

	/**
	 * @param fileHandlerService the fileHandlerService to set
	 */
	public void setFileHandlerService(ParserFileHandlerService fileHandlerService) {
		this.fileHandlerService = fileHandlerService;
	}
	
	public void setQueryBatchFileService(
			final QueryBatchFileService queryBatchFileService) {
		this.queryBatchFileService = queryBatchFileService;
	}
	
	public void setReconcileResultService(
			final WdReconcileResultService reconcileResultService) {
		this.reconcileResultService = reconcileResultService;
	}
	
	public void setBankFacadeService(BankInfoFacadeService bankFacadeService) {
		this.bankFacadeService = bankFacadeService;
	}
	
	/**
	 * 初始化导入页面(进入出款复核文件导入页面)
	 * @param request
	 * @param response
	 * @return
	 * @author LIWEI
	 */
	public ModelAndView initImport(HttpServletRequest request , HttpServletResponse response){
		WithdrawImportFileDTO importFileDTO = new WithdrawImportFileDTO();
		bind(request, importFileDTO, "importFileDTO", null);
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("importFileDTO", importFileDTO);
		model.put("withdrawBankList", bankFacadeService.getWithdrawBankList());
		return new ModelAndView(urlMap.get("initImport"),model);
	}
	
	/**
	 * 导入银行复核文件,进行文件匹配对比操作，返回匹对结果
	 * @param request
	 * @param response
	 * @return
	 * @author LIWEI
	 * @throws ServletException 
	 * @see
	 */
	public ModelAndView importReviewFile(HttpServletRequest request , HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		final MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		final CommonsMultipartFile orginalFile = (CommonsMultipartFile) multipartRequest.getFile("orginalFile");
		boolean flag = FileHandler.getFileHandler().uploadFile(orginalFile);
		WithdrawImportFileDTO importFileDTO = new WithdrawImportFileDTO();
		importFileDTO.setFileName(orginalFile.getOriginalFilename());
		bind(request, importFileDTO, "importFileDTO", null);
		String viewName = "";
		if(flag){
			try{
				importFileDTO.setOperators(SessionUserHolderUtil.getLoginId());
				importFileDTO.setFilePath(FileHandler.getFileHandler().getFilePath());
				importFileDTO.setCategory(1);
				Map<String,Object> validata = fileHandlerService.parserFileRdTx(importFileDTO);
				if("scuess".equals(validata.get("validataInfo"))){
					//查询导入结果文件汇总信息
					Map<String,Object> param = new HashMap<String, Object>();
					param.put("batchNum", importFileDTO.getBatchNum());
					param.put("bankCode", importFileDTO.getBankCode());
					param.put("category", 1);
					param.put("fileBusiType", importFileDTO.getTradeType());
					WithdrawRcResultSummaryDTO dto = reconcileResultService.queryRcResultSummaryInfo(param);
					viewName = urlMap.get("reviewInit");
					model.put("resultSummaryDto", dto);
				}else{
					viewName = urlMap.get("initImport");
					model.put("errorMsg", validata.get("validataInfo"));
				}
			}catch (Exception e) {
				log.error("import file failure"+e.getMessage(),e);
				viewName = urlMap.get("initImport");
				model.put("errorMsg", e.getMessage());
			}
		}else{
			viewName = urlMap.get("initImport");
			model.put("errorMsg", "系统出现异常,导入文件失败!");
		}
//		super.saveOperatorLog();
		model.put("importFileDTO", importFileDTO);
		return new ModelAndView(viewName,model);
	}
	
	/**
	 * 查询银行复核文件匹配成功信息
	 * @param request
	 * @param response
	 * @return
	 * @author LIWEI
	 * @see
	 */
	public ModelAndView queryReviewFileListScuess(HttpServletRequest request , HttpServletResponse response){
		String batchNum = request.getParameter("batchNum");
		String bankCode = request.getParameter("bankCode");
		Integer busiFlag = Integer.valueOf(request.getParameter("busiFlag"));
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("batchNum",batchNum);
		param.put("bankCode",bankCode);
		param.put("busiFlag",busiFlag);
		param.put("category", "1");
		param.put("fileBusiType", request.getParameter("tradeType"));
		Page<WithdrawReconcileResultDTO> page = PageUtils.getPage(request);
		page = reconcileResultService.queryRcResultListInfo(page,param);
		ModelAndView modelAndView = new ModelAndView(urlMap.get("reviewScuess"));
		modelAndView.addObject("page", page);
		return modelAndView;
	}
	
	/**
	 * 查询银行复核文件不匹配信息
	 * @param request
	 * @param response
	 * @return
	 * @author LIWEI
	 * @see
	 */
	public ModelAndView queryReviewFileListNoMatch(HttpServletRequest request , HttpServletResponse response){
		String batchNum = request.getParameter("batchNum");
		String bankCode = request.getParameter("bankCode");
		Integer busiFlag = Integer.valueOf(request.getParameter("busiFlag"));
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("batchNum",batchNum);
		param.put("bankCode",bankCode);
		param.put("busiFlag",busiFlag);
		param.put("category", "1");
		param.put("fileBusiType", request.getParameter("tradeType"));
		Page<WithdrawReconcileResultDTO> page = PageUtils.getPage(request);
		page = reconcileResultService.queryRcResultListInfo(page,param);
		ModelAndView modelAndView = new ModelAndView(urlMap.get("reviewNoMatch"));
		modelAndView.addObject("page", page);
		return modelAndView;
	}

	/**
	 * 复核文件导入结果展示初始化页面
	 * @param request
	 * @param response
	 * @author LIWEI
	 * @return
	 */
	public ModelAndView initReview(HttpServletRequest request , HttpServletResponse response){
		String batchNum = request.getParameter("batchNum");
		String bankCode = request.getParameter("bankCode");
		Map<String,Object> param = new HashMap<String, Object>(2);
		param.put("batchNum", batchNum);
		param.put("bankCode", bankCode);
		param.put("category", 1);
		param.put("fileBusiType", request.getParameter("tradeType"));
		WithdrawRcResultSummaryDTO withdrawRcResultSummaryDTO =  reconcileResultService.queryRcResultSummaryInfo(param);
		WithdrawImportFileDTO importFileDTO = new WithdrawImportFileDTO();
		bind(request, importFileDTO, "importFileDTO", null); 
		param.put("importFileDTO", importFileDTO);
		param.put("resultSummaryDto", withdrawRcResultSummaryDTO);
		return new ModelAndView(urlMap.get("reviewInit"),param);
	}
	/**
	 * 查询复核导入文件初始化页面
	 * @param request
	 * @param response
	 * @author LIWEI
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request , HttpServletResponse response){
		return new ModelAndView(urlMap.get("queryReviewInit"),DateUtil.getInitTime());
	}
	
	/**
	 * 查询复核导入文件列表
	 * @param request
	 * @param response
	 * @return
	 * @author LIWEI
	 * @throws ServletException 
	 */
	public ModelAndView queryImportedFileList(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		WebQueryWithDrawDTO webQueryWithDrawDTO = new WebQueryWithDrawDTO();
		String paramStatus = WithDrawConstants.BATCH_FILE_STATUS_8 + "," + WithDrawConstants.BATCH_FILE_STATUS_9 + ","
								+WithDrawConstants.BATCH_FILE_STATUS_4 + "," + WithDrawConstants.BATCH_FILE_STATUS_5;
		webQueryWithDrawDTO.setParamStatus(paramStatus);
		bind(request, webQueryWithDrawDTO, "webQueryWithDrawDTO", null);
		Page<WithdrawBatchInfoDTO> page = PageUtils.getPage(request);
		Map<String,Object> outMap = queryBatchFileService.queryImportedFileList(page, webQueryWithDrawDTO);
		return new ModelAndView(urlMap.get("queryReviewList"),outMap);
	}
	
	/**
	 * 复核文件退回导入操作
	 * @param request
	 * @param response
	 * @return
	 * @author LIWEI
	 * @throws PossException 
	 */
	public ModelAndView refuseImport(HttpServletRequest request , HttpServletResponse response) throws PossException{
		WithdrawImportFileDTO importFileDTO = new WithdrawImportFileDTO();
		bind(request, importFileDTO, "importFileDTO", null);
		String errorMsg = "";
		try{
			reconcileResultService.refuseReviewImportRdTx(importFileDTO);
			errorMsg = "操作成功！";
		}catch(Exception e){
			errorMsg = e.getMessage();
		}
		return new ModelAndView(urlMap.get("queryReviewInit"),DateUtil.getInitTime()).addObject("errorMsg", errorMsg);
	}

	
	/**
	 * 强制复核通过操作
	 * @param request
	 * @param response
	 * @return
	 * @author LIWEI
	 * @throws Exception 
	 */
	public ModelAndView sureImport(HttpServletRequest request , HttpServletResponse response){
		WithdrawImportFileDTO importFileDTO = new WithdrawImportFileDTO();
		bind(request, importFileDTO, "importFileDTO", null);
		String errorMsg = "";
		try {
			queryBatchFileService.processorReviewFoFile(importFileDTO);
			errorMsg = "操作成功！";
		} catch (Exception e) {
			errorMsg = e.getMessage();
		}
		return new ModelAndView(urlMap.get("queryReviewInit"), DateUtil.getInitTime()).addObject("errorMsg", errorMsg);
	}
	
	/**
	 * 批量强制复核通过操作
	 * @param request
	 * @param response
	 * @return
	 * @author LIWEI
	 * @throws PossException 
	 */
	public ModelAndView batchSureImport(HttpServletRequest request , HttpServletResponse response) throws PossException{
		String batImportPrms = request.getParameter("batImportPrms");
		reconcileResultService.batchSureImport(batImportPrms);
		return new ModelAndView(urlMap.get("queryReviewInit"),DateUtil.getInitTime());
	}
	
	/* (non-Javadoc)
	 * @see com.pay.fundout.withdraw.controller.WithdrawBaseController#buildOperatorLog()
	 */
	@Override
	public OperatorlogDTO buildOperatorLog() {
		// TODO Auto-generated method stub
		return null;
	}

}
