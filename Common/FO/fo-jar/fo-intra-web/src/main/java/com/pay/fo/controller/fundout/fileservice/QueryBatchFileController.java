 /** @Description 
 * @project 	poss-withdraw
 * @file 		QueryBatchFileController.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-7		Henry.Zeng			Create 
*/
package com.pay.fo.controller.fundout.fileservice;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.ModelAndView;

import com.pay.fo.controller.fundout.WithdrawBaseController;
import com.pay.fundout.channel.service.channel.FundoutChannelService;
import com.pay.fundout.withdraw.dto.fileservice.WebQueryWithDrawDTO;
import com.pay.fundout.withdraw.dto.fileservice.WithdrawBatchInfoDTO;
import com.pay.fundout.withdraw.dto.operatorlog.OperatorlogDTO;
import com.pay.fundout.withdraw.model.fileservice.WithdrawBatchInfo;
import com.pay.fundout.withdraw.service.fileservice.QueryBatchFileService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
//import com.pay.poss.enterprisemanager.controller.SimpleExcelGenerator;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.DateUtil;
import com.pay.util.JSonUtil;
import com.pay.util.SimpleExcelGenerator;

/**
 * <p>批次文件查询</p>
 * @author Henry.Zeng
 * @since 2010-9-7
 * @see 
 */
public class QueryBatchFileController extends WithdrawBaseController {
	
	private QueryBatchFileService queryBatchFileService;
	
	private FundoutChannelService channelService;
	
	public void setQueryBatchFileService(QueryBatchFileService queryBatchFileService) {
		this.queryBatchFileService = queryBatchFileService;
	}
	
	public void setChannelService(FundoutChannelService channelService) {
		this.channelService = channelService;
	}

	/**
	 * 进入批次文件下载页面
	 * @param request
	 * @param response
	 * @return
	 * @author Henry.Zeng
	 * @see
	 */
	public ModelAndView entryBatchFile(HttpServletRequest request , HttpServletResponse response ){
		String batchNum = request.getParameter("batchNum")==null?"":request.getParameter("batchNum");
		return new ModelAndView(urlMap.get("initBatchFile"), DateUtil.getInitTime()).addObject("batchNum", batchNum).addObject("channelList",  channelService.queryFoChannelList(null));
	}
	
	/**
	 * 进入批次文件下载页面
	 * @param request
	 * @param response
	 * @return
	 * @author Henry.Zeng
	 * @see
	 */
	public ModelAndView entryBatch(HttpServletRequest request , HttpServletResponse response ){
		return new ModelAndView(urlMap.get("initBatch"), DateUtil.getInitTime()).addObject("list", queryBatchFileService.queryBatchRuleConfigList());
	}
	/**
	 * 进入银行批次文件下载页面
	 * @param request
	 * @param response
	 * @return
	 * @author Henry.Zeng
	 * @see
	 */
	public ModelAndView entryBankFile(HttpServletRequest request , HttpServletResponse response ){
		return new ModelAndView(urlMap.get("initBankFile"), DateUtil.getInitTime());
	}
	
	/**
	 * 查询批次文件
	 * @param request
	 * @param response
	 * @return
	 * @author Henry.Zeng
	 * @throws ServletException 
	 * @see
	 */
	public ModelAndView queryBatch(HttpServletRequest request , HttpServletResponse response ) throws ServletException{
		
		WebQueryWithDrawDTO webQueryWithDrawDTO = new WebQueryWithDrawDTO();
		
		bind(request, webQueryWithDrawDTO, "webQueryWithDrawDTO", null);
		
		Page<WithdrawBatchInfoDTO> page = PageUtils.getPage(request);
		
		page = queryBatchFileService.queryBatchInfo(page, webQueryWithDrawDTO);
		
		Map<String,Page<WithdrawBatchInfoDTO>> model = new HashMap<String, Page<WithdrawBatchInfoDTO>>();
		
		model.put("page", page); 
		
		return new ModelAndView(urlMap.get("listBatch"), model);
	}
	/**
	 * 查询批次文件
	 * @param request
	 * @param response
	 * @return
	 * @author Henry.Zeng
	 * @throws ServletException 
	 * @see
	 */
	public ModelAndView queryBatchFile(HttpServletRequest request , HttpServletResponse response ) throws ServletException{ 
		
		WebQueryWithDrawDTO webQueryWithDrawDTO = new WebQueryWithDrawDTO();
		
		bind(request, webQueryWithDrawDTO, "webQueryWithDrawDTO", null);
		
		Page<WithdrawBatchInfoDTO> page = PageUtils.getPage(request);
		
		page = queryBatchFileService.queryBatchFile(page, webQueryWithDrawDTO);
		
		Map<String,Page<WithdrawBatchInfoDTO>> model = new HashMap<String, Page<WithdrawBatchInfoDTO>>();
		
		model.put("page", page);
		
		return new ModelAndView(urlMap.get("listBatchFile"), model);
	}
	
	/**
	 * 废除批次文件
	 * @param request
	 * @param response
	 * @return
	 * @author Henry.Zeng
	 * @throws IOException 
	 * @see
	 */
	public ModelAndView invalidBatchFile(HttpServletRequest request , HttpServletResponse response) throws IOException{
		
		String batchNum = request.getParameter("batchNum");
			
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Map<String,Object> outMap = new HashMap<String, Object>(2);
		outMap.put("batchNum", batchNum);
		String jsonString = "";
		try{
			outMap.put("userId", SessionUserHolderUtil.getLoginId());
			queryBatchFileService.invalidBatchFileRdTx(outMap);
			jsonString = JSonUtil.toJSonString(outMap);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			outMap.put("infos", e.getMessage());
			jsonString = JSonUtil.toJSonString(outMap);
		}
		out.write(jsonString);
		out.flush();
		out.close();
		return null;
	}
	/**
	 * 生成批次文件,批次号已经产生,文件没有产生
	 * @param request
	 * @param response
	 * @return
	 * @author Henry.Zeng
	 * @throws IOException 
	 * @see
	 */
	public ModelAndView generateBatchFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String batchNum = request.getParameter("batchNum");
		Map<String,Object> outMap = new HashMap<String, Object>(2);
		outMap.put("batchNum", batchNum);
		queryBatchFileService.generateBatchFile(outMap);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String results = JSonUtil.toJSonString(outMap);
		out.write(results);
		out.flush();
		out.close();

		return null;
	}
	/**
	 * 重成批次文件
	 * @param request
	 * @param response
	 * @return
	 * @author Henry.Zeng
	 * @throws IOException 
	 * @see
	 */
	public ModelAndView regenerateBatchFile(HttpServletRequest request , HttpServletResponse response) throws IOException{
		String oldBatchNum = request.getParameter("batchNum");
		String batchName = request.getParameter("batchName");
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Map<String,Object> outMap = new HashMap<String, Object>(1);
		outMap.put("batchNum", oldBatchNum);
		outMap.put("batchName", batchName);
		String jsonString = "";
		try{
			queryBatchFileService.regenerateBatchFile(outMap);
			jsonString = JSonUtil.toJSonString(outMap);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			outMap.put("infos", e.getMessage());
			jsonString = JSonUtil.toJSonString(outMap);
		}
		out.write(jsonString);
		out.flush();
		out.close();
		return null;
	}

	
	/**
	 * 重成批次文件
	 * @param request
	 * @param response
	 * @return
	 * @author Henry.Zeng
	 * @throws IOException 
	 * @see
	 */
	public ModelAndView regenerateBatch(HttpServletRequest request , HttpServletResponse response) throws IOException{
		String ruleId = request.getParameter("ruleId");
		Map<String,Object> outMap = new HashMap<String, Object>(2);
		outMap.put("ruleId", ruleId);
		queryBatchFileService.regenerateBatch(outMap);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String results = JSonUtil.toJSonString(outMap);
		out.write(results);
		out.flush();
		out.close();
		return null;
	}
	
	
	public ModelAndView downloadBatchFile(HttpServletRequest request,HttpServletResponse response){
		WebQueryWithDrawDTO webQueryWithDrawDTO = new WebQueryWithDrawDTO();
		bind(request, webQueryWithDrawDTO, "webQueryWithDrawDTO", null);
		 List<WithdrawBatchInfo> queryBatchInfo = queryBatchFileService.queryBatchInfo(webQueryWithDrawDTO);
		 		for (WithdrawBatchInfo withdrawBatchInfo : queryBatchInfo) {
		 			BigDecimal allAmount = withdrawBatchInfo.getAllAmount();
		 			BigDecimal divide = allAmount.divide(new BigDecimal(1000));
		 			withdrawBatchInfo.setAllAmount(divide);
		 			Date generateTime = withdrawBatchInfo.getGenerateTime();
		 			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					 withdrawBatchInfo.setGenerateTimes(sd.format(generateTime));
					 String finStatus = withdrawBatchInfo.getFinStatus();
					 if(finStatus.equals("4")){
						 withdrawBatchInfo.setFinStatus("未处理");
					 }else if(finStatus.equals("10")){
						 withdrawBatchInfo.setFinStatus("处理失败");
					 }else if(finStatus.equals("12")){
						 withdrawBatchInfo.setFinStatus("处理成功");
					 }else if(finStatus.equals("7")||finStatus.equals("8")||finStatus.equals("9")){
						 withdrawBatchInfo.setFinStatus("处理中");
					 }
		 		}
		 try {
				String[] headers = new String[] { "批次号", "出款流水号", "批次规则名称", "总笔数","总金额","产生时间","批次文件状态","处理状态"};
				String[] fields = new String[] { "batchNum", "tradeSeq",
						"ruleName", "allCount", "allAmount", "generateTimes","batchFileStatusDesc","finStatus"};
				Workbook grid = SimpleExcelGenerator.generateGrid("批 次 文 件 查 询",
						headers, fields,queryBatchInfo);
				Date myDate = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
				String dd = sdf.format(myDate);
				response.setContentType("application/force-download");
				response.setContentType("applicationnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment;filename="
						+ dd + ".xls");
				grid.write(response.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}
	
	
	@Override
	public OperatorlogDTO buildOperatorLog() {
		// TODO Auto-generated method stub
		return null;
	}
	 
	
}
