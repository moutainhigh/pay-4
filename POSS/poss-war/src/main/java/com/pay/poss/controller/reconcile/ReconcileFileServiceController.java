/** @Description 
 * @project 	fo-reconcile-web
 * @file 		ReconcileFileServiceController.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-2		Henry.Zeng			Create 
 * 2016-08-01		nico.shao			针对手续费，把负数去掉了（需求来自交易日报表模块）
 *                                      （不同的对账单，有负有正，不统一）
 */
package com.pay.poss.controller.reconcile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.pay.file.parser.dto.FileParseResult;
import com.pay.file.parser.dto.GatewayReconciliationParserMode;
import com.pay.file.service.FileService;
import com.pay.fundout.channel.dto.channel.FundoutChannelQueryDTO;
import com.pay.fundout.channel.service.channel.FundoutChannelService;
import com.pay.fundout.reconcile.common.util.ReconcileConstants;
import com.pay.fundout.reconcile.model.fileservice.ReconcileImportFile;
import com.pay.fundout.reconcile.model.fileservice.ReconcileImportRecord;
import com.pay.fundout.reconcile.model.fileservice.WebQueryFile;
import com.pay.fundout.reconcile.service.fileservice.ReconcileFileService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.pe.dto.BeanUtils;
import com.pay.poss.client.ChannelClientService;
import com.pay.poss.client.ReconciliationService;
import com.pay.poss.dto.ChannelOrderDTO;
import com.pay.poss.dto.PaymentChannelItemDto;
import com.pay.poss.dto.ReconciliationDto;
import com.pay.poss.dto.ReconciliationMessage;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.DateUtil;
import com.pay.util.JSonUtil;

/**
 * 
 *  File: ReconcileFileServiceController.java
 *  Description:
 *  Copyright 2016-2030 IPAYLINKS Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2016年4月25日   mmzhang     Changes添加退款对账的逻辑，退款对账取退款订单号，核对金额 要乘以汇率
 *
 */
public class ReconcileFileServiceController extends ReconcileBaseController {

	private Log logger = LogFactory.getLog(getClass());
	private ReconcileFileService reconcileFileService;
	private ChannelClientService channelClientService;
	private ReconciliationService reconciliationService;
	private FileService fileService;
	private FundoutChannelService fundoutChannelService;

	/**
	 * 初始化上传文件页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView initReconcileFileUpload(HttpServletRequest request,
			HttpServletResponse response) {

		String viewName = this.urlMap.get("reconcileFileUpload");
		CurrencyCodeEnum[] currencyCodes = CurrencyCodeEnum.values();

		String startDate = DateUtil.skipDateStr(new Date(), -1);
		String endDate = DateUtil.skipDateStr(new Date(), -1);

		return new ModelAndView(viewName)
				.addObject("currencyCodes", currencyCodes)
				.addObject("startDate", startDate)
				.addObject("endDate", endDate);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView getFundinChannels(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		PaymentChannelItemDto channelItem = new PaymentChannelItemDto();
		List channelItems = channelClientService.queryChannelItem(channelItem);
		String channelJSonStr = JSonUtil.toJSonString(channelItems);
		response.getWriter().print(channelJSonStr);
		return null;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView getFundoutChannels(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setCharacterEncoding("utf-8");
		List<FundoutChannelQueryDTO> channels = fundoutChannelService
				.getFundoutChannelList();

		String channelJSonStr = JSonUtil.toJSonString(channels);
		response.getWriter().print(channelJSonStr);
		return null;
	}

	/**
	 * 上传操作,2016-08-15 之后，就应该没有用了。
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView upload(HttpServletRequest request,
			HttpServletResponse response) {

		logger.info("upload ");
		String viewName = this.urlMap.get("reconcileResult");
		final MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		final CommonsMultipartFile orginalFile = (CommonsMultipartFile) multipartRequest
				.getFile("orginalFile");
		String name = orginalFile.getOriginalFilename();
		
		String orgCode = request.getParameter("orgCode");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String user = SessionUserHolderUtil.getLoginId();
		String[] currencys = request.getParameterValues("settlementCurrency");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgCode", orgCode);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("fileInfo", orginalFile);
		params.put("user", user);

		ModelAndView view = new ModelAndView(viewName, params);

		String operator = SessionUserHolderUtil.getLoginId();
		try {
			FileParseResult fileParseResult = fileService.parserFileInfo(
					orginalFile.getInputStream(), orginalFile.getName(),
					orgCode);
			view.addObject("fileParseResult", fileParseResult);
			List<GatewayReconciliationParserMode> list = fileParseResult.getList();
			
			view.addObject("totalRecord", fileParseResult.getTotalRecord());
			view.addObject("totalDealAmount", fileParseResult.getTotalDealAmount());

			List<ReconciliationDto> recList = new ArrayList<ReconciliationDto>();
			
			if (null != list && !list.isEmpty()) { 
				int nListSize = list.size();		//这个变量主要用于控制下面的日志，没别的用
				Iterator<GatewayReconciliationParserMode> iterator = list.iterator();
				while (iterator.hasNext()) {
					GatewayReconciliationParserMode model =iterator
							.next();
					String channelOrderNo = model.getChannelOrderNo(); //获取渠道订单号判断是否为空。
					if(StringUtils.isEmpty(channelOrderNo)){
						iterator.remove(); //渠道订单号为空 直接删除
						continue;
					}
					ReconciliationDto reconciliationDto = new ReconciliationDto();
					BeanUtils.copyProperties(model, reconciliationDto);
					reconciliationDto.setChannelCode(orgCode);
					reconciliationDto.setChannelOrderNo(model.getChannelOrderNo());
					reconciliationDto.setSettlementRate(model.getSettlementRate());
					reconciliationDto.setSettlementCurrencyCode(model.getSettlementCurrency() );
					
					String status = model.getStatus();
					if ("1".equals(status)) {
						reconciliationDto.setResultCode("0000");
					}
					
					//去掉手续费中单 "-" add by nico.shao 2016-08-01
					String transFee = reconciliationDto.getTransFee();
					if(!StringUtils.isEmpty(transFee)){
						if(transFee.startsWith("-")){
							transFee=transFee.replaceAll("-", "");
							reconciliationDto.setTransFee(transFee);
						}
					}
					
					//手续费
					String fixedFee = reconciliationDto.getFixedFee();
					if(!StringUtils.isEmpty(fixedFee)){
						if(fixedFee.startsWith("-")){
							fixedFee=fixedFee.replaceAll("-", "");
							reconciliationDto.setFixedFee(fixedFee);
						}
					}
					 
					String perFee = reconciliationDto.getPerFee();
					if(!StringUtils.isEmpty(perFee)){
						if(perFee.startsWith("-")){
							perFee=perFee.replaceAll("-", "");
							reconciliationDto.setPerFee(perFee);
						}
					}
					
					//end 2016-08-01 
					//reconciliationDto.setSettlementCurrencyCodes(currencys);	
					if(nListSize <20){	//这个日志很消耗时间，所以加了一个控制，联调的时候，会打印出来.生产环境就不打印了
						logger.info("###############recored properties:" + model);
						logger.info("&&&&&&&&&&&" + reconciliationDto.toString());
					}
					
					recList.add(reconciliationDto);
				}
				
				List<String> settlmentCurrencyCodes = new ArrayList<String>();
				if(currencys != null){
					for(int i=0;i<currencys.length;i++){
						settlmentCurrencyCodes.add(currencys[i]);
					}
				}
				
				List<ChannelOrderDTO> channelOrders = reconciliationService
						.reconciliation(startDate, endDate, recList, operator,name,
								orgCode,settlmentCurrencyCodes);
				int success=0;
				for (ChannelOrderDTO channelOrderDTO : channelOrders) {
						if(channelOrderDTO.getReconciliationFlg()==1){
							success++;
						}
				}
				view.addObject("channelOrders", channelOrders).addObject("successCount", success);
			}
		} catch (Exception e) {
			logger.error("parse error:", e);
		}

		PaymentChannelItemDto channelItem = new PaymentChannelItemDto();
		List channelItems = channelClientService.queryChannelItem(channelItem);
		CurrencyCodeEnum[] currencyCodes = CurrencyCodeEnum.values();
		view.addObject("channelItems", channelItems).addObject("currencyCodes",
				currencyCodes);
		return view;
	}

	
	/**
	 * 上传操作,新流程，2016-08-15. 这个流程触发的是异步流程 
	 * settlementCurrency 参数作为 参数传递了 
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView upload_new(HttpServletRequest request,
			HttpServletResponse response) {

		logger.info("upload new fileservice ");
		String viewName = this.urlMap.get("reconcileResult_new");
		final MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		/*final CommonsMultipartFile orginalFile = (CommonsMultipartFile) multipartRequest
				.getFile("orginalFile");*/
		
		List<MultipartFile> files = multipartRequest.getFiles("orginalFile") ;
		ModelAndView view = new ModelAndView(viewName) ;
		CommonsMultipartFile orginalFile = null ;
		List<ReconciliationMessage> messageList = new ArrayList<ReconciliationMessage>() ; 
		//jsp页面js做了文件必须选择校验， 此处不在判断文件是否为空
		for(int index=0; index<files.size(); index++){
			ReconciliationMessage reconciliationMessage = new ReconciliationMessage() ;
			orginalFile = (CommonsMultipartFile) files.get(index);
			String name = orginalFile.getOriginalFilename();
			
			String orgCode = request.getParameter("orgCode");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String user = SessionUserHolderUtil.getLoginId();
			String[] currencys = request.getParameterValues("settlementCurrency");
			reconciliationMessage.setOrginalFileName(name);
			String operator = SessionUserHolderUtil.getLoginId();
			try {
				FileParseResult fileParseResult = fileService.parserFileInfo(
						orginalFile.getInputStream(), orginalFile.getName(),
						orgCode);
				reconciliationMessage.setFileParseResult(fileParseResult);
				List<GatewayReconciliationParserMode> list = fileParseResult.getList();
				
				reconciliationMessage.setTotalRecord(fileParseResult.getTotalRecord());

				//打印一下错误日志，如果解析出错，可以通过这种方式来把错误行数找出来
				{
					List<String> errorList = fileParseResult.getErrorMsg();
					if((errorList != null ) && (!errorList.isEmpty())){
						logger.info("errorList:" + errorList);
					}
				}
				
				//构造对账单文件 
				List<ReconciliationDto> recList = new ArrayList<ReconciliationDto>();
				
				if (null != list && !list.isEmpty()) { 
					int nListSize = list.size();		//这个变量主要用于控制下面的日志，没别的用
					
					logger.info("list size=" + nListSize);
					boolean isAllError = true;//判断是否全部记录异常add by davis.guo at 2016-09-06
					Iterator<GatewayReconciliationParserMode> iterator = list.iterator();
					while (iterator.hasNext()) {
						GatewayReconciliationParserMode model =iterator
								.next();
						String channelOrderNo = model.getChannelOrderNo(); //获取渠道订单号判断是否为空。
						if(StringUtils.isEmpty(channelOrderNo)){
							iterator.remove(); //渠道订单号为空 直接删除
							logger.info("remove because channelOrderNO is null");
							logger.info("model:"+ model.toString());
							continue;
						}
						if(!NumberUtils.isDigits(channelOrderNo)){//excel表格的对账单中，如果渠道订单号是数字类型，那么显示的格式不对（1.11E+18），要做判断。add by davis.guo at 2016-09-06
							iterator.remove(); //渠道订单号格式不对 直接删除
							logger.error("@@@remove because channelOrderNO formate error!");
							logger.info("model:"+ model.toString());
							continue;
						}
						isAllError = false;
						ReconciliationDto reconciliationDto = new ReconciliationDto();
						BeanUtils.copyProperties(model, reconciliationDto);
						reconciliationDto.setChannelCode(orgCode);
						reconciliationDto.setChannelOrderNo(model.getChannelOrderNo());
						reconciliationDto.setSettlementRate(model.getSettlementRate());
						reconciliationDto.setSettlementCurrencyCode(model.getSettlementCurrency() );
						
						//参考号和授权码，tradeType，在  copy properties 已经做掉了？ 
						
						String status = model.getStatus();
						if ("1".equals(status)) {
							reconciliationDto.setResultCode("0000");
						}
						
						//去掉手续费中单 "-" add by nico.shao 2016-08-01
						String transFee = reconciliationDto.getTransFee();
						if(!StringUtils.isEmpty(transFee)){
							if(transFee.startsWith("-")){
								transFee=transFee.replaceAll("-", "");
								reconciliationDto.setTransFee(transFee);
							}
						}
						
						//手续费
						String fixedFee = reconciliationDto.getFixedFee();
						if(!StringUtils.isEmpty(fixedFee)){
							if(fixedFee.startsWith("-")){
								fixedFee=fixedFee.replaceAll("-", "");
								reconciliationDto.setFixedFee(fixedFee);
							}
						}
						 
						String perFee = reconciliationDto.getPerFee();
						if(!StringUtils.isEmpty(perFee)){
							if(perFee.startsWith("-")){
								perFee=perFee.replaceAll("-", "");
								reconciliationDto.setPerFee(perFee);
							}
						}
						
						//end 2016-08-01 
						//reconciliationDto.setSettlementCurrencyCodes(currencys);	
						if(nListSize <20){	//这个日志很消耗时间，所以加了一个控制，联调的时候，会打印出来.生产环境就不打印了
							logger.info("###############recored properties:" + model);
							logger.info("&&&&&&&&&&&" + reconciliationDto.toString());
						}
						
						recList.add(reconciliationDto);
					}
					if(isAllError)
					{//add by davis.guo at 2016-09-06
						reconciliationMessage.setResponseCode("8888");
						reconciliationMessage.setResponseDesc("对账单数据格式错误，请确认!");
						//return view;
						messageList.add(reconciliationMessage) ;
					}
					
					String command =  fileParseResult.getCommand();
					if(command==null){
						command= "process";
					}
					List<String> settlmentCurrencyCodes = new ArrayList<String>();
					if(currencys != null){
						for(int i=0;i<currencys.length;i++){
							settlmentCurrencyCodes.add(currencys[i]);
						}
					}
									
					
					logger.info("reconciliation_new startDate=" + startDate + ",endDate=" + endDate + 
							",command="+ command + ",name=" + name + 
						    ",settlmentCurrencyCodes=" +settlmentCurrencyCodes );
					Map<String ,String> resultMap = reconciliationService
							.reconciliation_new(startDate, endDate, recList, operator,name,
							command,orgCode,settlmentCurrencyCodes);
					
					reconciliationMessage.setResponseCode(resultMap.get("responseCode"));
					reconciliationMessage.setResponseDesc(resultMap.get("responseDesc"));
					messageList.add(reconciliationMessage) ;
				}
			} catch (Exception e) {
				logger.error("parse error:", e);
				reconciliationMessage.setResponseCode("8888");
				reconciliationMessage.setResponseDesc("文件解析错误！");
				messageList.add(reconciliationMessage) ;
			}
			
		}
		
		/*
		PaymentChannelItemDto channelItem = new PaymentChannelItemDto();
		List channelItems = channelClientService.queryChannelItem(channelItem);
		CurrencyCodeEnum[] currencyCodes = CurrencyCodeEnum.values();
		view.addObject("channelItems", channelItems).addObject("currencyCodes",
				currencyCodes);
		*/
		
		return view.addObject("messageList", messageList);
	}
	
	/**
	 * 查询银行对账文件信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView initQueryReconcileFileUploadInfo(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		String viewName = this.urlMap.get("initQueryUploadInfo");
		List<Map<String, String>> uploadStatus = this.getSelectListInfoService
				.getUploadStatus();

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("uploadStatus", uploadStatus);
		return new ModelAndView(viewName, result);
	}

	/**
	 * 下载操作
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView download(HttpServletRequest request,
			HttpServletResponse response) {
		String fileId = request.getParameter("fileId");
		String fileName = request.getParameter("fileName");
		String uploadDate = request.getParameter("uploadDate");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fileName", fileName);
		params.put("fileId", fileId);
		params.put("uploadDate", uploadDate);

		InputStream is = reconcileFileService.downloadReconcileFile(params);
		response.setContentType("application/octet-stream; charset=UTF-8");
		OutputStream out = null;
		try {
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ URLEncoder.encode(fileName, "UTF-8") + "\"");

			out = response.getOutputStream();
			byte[] bytes = new byte[1024];
			while (is.read(bytes) != -1) {
				out.write(bytes, 0, bytes.length);
			}
			out.flush();
		} catch (UnsupportedEncodingException e) {

		} catch (Exception e) {

		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {

				}
			}
		}
		response.setStatus(HttpServletResponse.SC_OK);
		return null;
	}

	/**
	 * 查看上传银行对账文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView queryUploadFile(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		String viewName = this.urlMap.get("queryUploadInfoList");
		final WebQueryFile dto = new WebQueryFile();
		this.bind(request, dto, "webQueryFile", null);
		Page<ReconcileImportFile> resultPage = PageUtils.getPage(request);
		Map<String, Object> model = reconcileFileService
				.queryReconcileUploadFile(resultPage, dto);
		return new ModelAndView(viewName, model);
	}

	/**
	 * 废除上传银行对账文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView revokeUploadFile(HttpServletRequest request,
			HttpServletResponse response) {
		String fileId = request.getParameter("fileId");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fileId", fileId);

		params = reconcileFileService.revokeReconcileFile(params);
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			os.write(((String) params.get("resultMsg")).getBytes());
			os.flush();
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			if (null != os) {
				try {
					os.close();
				} catch (IOException e1) {

				}
			}
		}
		return null;
	}

	/**
	 * 初始化对账详细信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView initQueryUploadFileDetail(HttpServletRequest request,
			HttpServletResponse response) {
		String viewName = this.urlMap.get("reconcileFileDetail");
		Map<String, Object> model = new HashMap<String, Object>(4);
		model.put("fileId", request.getParameter("fileId"));
		model.put("busiDate", request.getParameter("busiDate"));
		model.put("orgCode", request.getParameter("withdrawBankId"));
		model.put("status", request.getParameter("status"));
		return new ModelAndView(viewName, model);
	}

	/**
	 * 查询上传文件详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView queryUploadFileDetail(HttpServletRequest request,
			HttpServletResponse response) {
		String viewName = this.urlMap.get("reconcileDetailList");
		String fileId = request.getParameter("fileId");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fileId", fileId);

		Page<ReconcileImportRecord> resultPage = PageUtils.getPage(request);
		params = reconcileFileService.queryReconcileFileDetailInfo(resultPage,
				params);
		params.put("fileId", request.getParameter("fileId"));
		params.put("busiDate", request.getParameter("busiDate"));
		params.put("orgCode", request.getParameter("orgCode"));
		params.put("status", request.getParameter("status"));
		return new ModelAndView(viewName, params);
	}

	/**
	 * 批量对账
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView batchReconcileInfo(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		String viewName = this.urlMap.get("queryUploadInfoList");
		final WebQueryFile dto = new WebQueryFile();
		this.bind(request, dto, "webQueryFile", null);
		Page<ReconcileImportFile> resultPage = PageUtils.getPage(request);
		Map<String, Object> result = reconcileFileService.batchReconcileInfos(
				resultPage, dto);
		return new ModelAndView(viewName, result);
	}

	/**
	 * 单笔对账
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView singleReconcileInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String fileId = request.getParameter("fileId");
		String busiDate = request.getParameter("busiDate");
		String orgCode = request.getParameter("orgCode");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fileId", fileId);
		params.put("startTime", busiDate + ReconcileConstants.START_TIME);
		params.put("endTime", busiDate + ReconcileConstants.END_TIME);
		params.put("busiType", ReconcileConstants.WITHDRAW_BUSI_TYPE);
		params.put("orgCode", orgCode);
		params.put("busiDate", busiDate);
		params.put("resVal", "");

		params = reconcileFileService.singleReconcileInfo(params);

		OutputStream os = null;
		try {
			os = response.getOutputStream();
			os.write(((String) params.get("resultMsg")).getBytes());
			os.flush();
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			if (null != os) {
				try {
					os.close();
				} catch (IOException e1) {

				}
			}
		}
		return null;
	}
	public void setReconcileFileService(
			ReconcileFileService reconcileFileService) {
		this.reconcileFileService = reconcileFileService;
	}

	public void setChannelClientService(
			ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}

	public void setReconciliationService(
			ReconciliationService reconciliationService) {
		this.reconciliationService = reconciliationService;
	}

	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

	public void setFundoutChannelService(
			FundoutChannelService fundoutChannelService) {
		this.fundoutChannelService = fundoutChannelService;
	}

}
