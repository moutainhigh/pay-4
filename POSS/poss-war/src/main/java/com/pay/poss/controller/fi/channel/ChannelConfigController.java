package com.pay.poss.controller.fi.channel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pay.inf.enums.ResponseCodeEnum;
import jxl.read.biff.BiffException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.fi.helper.ChannelItemOrgCodeEnum;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.client.ChannelClientService;
import com.pay.poss.dto.ChannelConfig;
import com.pay.poss.dto.PaymentChannelItemDto;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.ExcelUtils;
import com.pay.util.MapUtil;
import com.pay.util.SimpleExcelGenerator;

/**
 * 入款通道管理(新)
 *
 * @author sandy
 *
 */
public class ChannelConfigController extends MultiActionController {

	private final Log logger = LogFactory.getLog(ChannelConfigController.class);
	private ChannelClientService channelClientService;
	private String indexView;
	private String resultView;
	private String addView;
	private String updateView;
	private String batchAddView;

	/**
	 * 获取通道
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request,
							  HttpServletResponse response) {

		ModelAndView view = new ModelAndView(indexView);
		/** 注释 delin 2016年6月24日16:03:37 不取通道名称 取渠道名称 */
		/*
		 * PaymentChannelItemDto paymentChannelItemDto = new
		 * PaymentChannelItemDto();
		 */
		/*
		 * List channelItems = channelClientService
		 * .queryChannelItem(paymentChannelItemDto);
		 */
		ChannelItemOrgCodeEnum[] channelItems = ChannelItemOrgCodeEnum.values();
		CurrencyCodeEnum[] currencyCodeEnum = CurrencyCodeEnum.values();
		view.addObject("currencyCodeEnum", currencyCodeEnum);
		return view.addObject("channelItems", channelItems);
	}

	public ModelAndView viewAucrPool(HttpServletRequest request,
							  HttpServletResponse response) {

		ModelAndView view = new ModelAndView("channel/channelconfig/indexFreePay");
		/** 注释 delin 2016年6月24日16:03:37 不取通道名称 取渠道名称 */
		/*
		 * PaymentChannelItemDto paymentChannelItemDto = new
		 * PaymentChannelItemDto();
		 */
		/*
		 * List channelItems = channelClientService
		 * .queryChannelItem(paymentChannelItemDto);
		 */
		ChannelItemOrgCodeEnum[] channelItems = ChannelItemOrgCodeEnum.values();
		CurrencyCodeEnum[] currencyCodeEnum = CurrencyCodeEnum.values();
		view.addObject("currencyCodeEnum", currencyCodeEnum);
		return view.addObject("channelItems", channelItems);
	}

	/**
	 * 查询通道
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView query(HttpServletRequest request,
							  HttpServletResponse response) {

		String orgCode = request.getParameter("orgCode");
		String orgMerchantCode = request.getParameter("orgMerchantCode");
		String status = request.getParameter("status");
		String mcc = request.getParameter("mcc");
		String currencyCode = request.getParameter("currencyCode");
		String merchantBillName = request.getParameter("merchantBillName");
		String fitMerchantType = request.getParameter("fitMerchantType");
		String aucrFlag = request.getParameter("aucrFlag");

		Page page = PageUtils.getPage(request);

		Map results = channelClientService.queryChannelConfig(null, orgCode,
				orgMerchantCode, status, mcc, currencyCode, merchantBillName,fitMerchantType,aucrFlag,
				page);
		List result = (List) results.get("channelConfigs");
		Map pageMap = (Map) results.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		/** 注释 delin 2016年6月24日16:03:37 不取通道名称 取渠道名称 */
		/*
		 * PaymentChannelItemDto paymentChannelItemDto = new
		 * PaymentChannelItemDto();
		 */
		/*
		 * List channelItems = channelClientService
		 * .queryChannelItem(paymentChannelItemDto);
		 */
		ChannelItemOrgCodeEnum[] channelItems = ChannelItemOrgCodeEnum.values();
		ModelAndView view = new ModelAndView(resultView);
		if("1".equals(aucrFlag)){
			view = new ModelAndView("channel/channelconfig/resultFreeList");
		}

		view.addObject("resultList", result);
		view.addObject("channelItems", channelItems);
		view.addObject("page", page);

		// add by Mack 2016年5月27日

		return view.addObject("orgMerchantCode", orgMerchantCode);
	}

	/**
	 * 初始化
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView initAdd(HttpServletRequest request,
								HttpServletResponse response) {
		ModelAndView view = new ModelAndView(addView);
		/** 注释 delin 2016年6月24日16:03:37 不取通道名称 取渠道名称 */
		/*
		 * PaymentChannelItemDto paymentChannelItemDto = new
		 * PaymentChannelItemDto();
		 */
		/*
		 * List channelItems = channelClientService
		 * .queryChannelItem(paymentChannelItemDto);
		 */
		ChannelItemOrgCodeEnum[] channelItems = ChannelItemOrgCodeEnum.values();
		CurrencyCodeEnum[] currencyCodeEnum = CurrencyCodeEnum.values();
		view.addObject("currencyCodeEnum", currencyCodeEnum);
		view.addObject("channelItems", channelItems);
		return view;
	}



	/**
	 * 添加
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView add(HttpServletRequest request,
							HttpServletResponse response) throws Exception {

		response.setCharacterEncoding("utf-8");
		String operator = SessionUserHolderUtil.getLoginId();
		String orgCode = request.getParameter("orgCode");
		String orgMerchantCode = request.getParameter("orgMerchantCode");
		String orgKey = request.getParameter("orgKey");
		String terminalCode = request.getParameter("terminalCode");
		String accessCode = request.getParameter("accessCode");
		String transType = request.getParameter("transType");
		String keyFilePath = request.getParameter("keyFilePath");
		String mcc = request.getParameter("mcc");
		String merchantBillName = request.getParameter("merchantBillName");
		String currencyCode = request.getParameter("currencyCode");
		String pattern = request.getParameter("pattern");
		String requestMerchantName = request
				.getParameter("requestMerchantName");
		String supportWebsite = request.getParameter("supportWebsite");//add by davis.guo at 2016-08-16 映射网址
		String fitMerchantType = request
				.getParameter("fitMerchantType");

		Map resultMap = channelClientService.queryChannelConfig(null, orgCode,
				orgMerchantCode, null, null, null, null, null,null,null);
		List results = (List) resultMap.get("channelConfigs");
		boolean havingDupli = false;
		if(results != null && results.size() > 0){
			for(int i =0; i < results.size(); i++){
				Map map = (Map)results.get(i);
				if(map != null && map.containsKey("orgMerchantCode")){
					havingDupli = orgMerchantCode.equals(map.get("orgMerchantCode"));
					if(havingDupli) break;
				}
			}
		}
		/* if(results!=null && results.size()>0){ */
		// add by Mack 农行和CTV允许相同的二级商户号
		if (havingDupli
				&& !orgCode.equals("10002001") && !orgCode.equals("10078001")) {
			response.getWriter().print("同一个二级商户号只能配置一次！！！");
			return null;
		}
		String result = channelClientService.addChannelConfig(orgCode,
				orgMerchantCode, orgKey, operator, keyFilePath, terminalCode,
				transType, accessCode, mcc, currencyCode, pattern,
				requestMerchantName, merchantBillName, fitMerchantType, supportWebsite);

		if (null == result) {
			response.getWriter().print(1);
		} else {
			response.getWriter().print(result);
		}

		return null;
	}

	/**
	 * 初始化修改页面
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView initUpdate(HttpServletRequest request,
								   HttpServletResponse response) {

		ModelAndView view = new ModelAndView(updateView);
		String id = request.getParameter("id");
		String mcc = request.getParameter("mcc");
		String currencyCode = request.getParameter("currencyCode");
		Map resultMap = channelClientService.queryChannelConfig(id, null, null,
				null, mcc, currencyCode, null, null,null,null);
		List list = (List) resultMap.get("channelConfigs");
		CurrencyCodeEnum[] currencyCodeEnum = CurrencyCodeEnum.values();
		view.addObject("currencyCodeEnum", currencyCodeEnum);
		view.addObject("config", list.get(0));
		return view;
	}

	/**
	 * 修改渠道成本费率
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView update(HttpServletRequest request,
							   HttpServletResponse response) throws IOException {

		response.setCharacterEncoding("utf-8");

		String operator = SessionUserHolderUtil.getLoginId();
		String id = request.getParameter("id");
		String orgCode = request.getParameter("orgCode");
		String orgMerchantCode = request.getParameter("orgMerchantCode");
		String orgKey = request.getParameter("orgKey");
		String terminalCode = request.getParameter("terminalCode");
		String accessCode = request.getParameter("accessCode");
		String transType = request.getParameter("transType");
		String keyFilePath = request.getParameter("keyFilePath");
		String status = request.getParameter("status");
		String mcc = request.getParameter("mcc");
		String currencyCode = request.getParameter("currencyCode");
		String pattern = request.getParameter("pattern");
		String requestMerchantName = request
				.getParameter("requestMerchantName");
		String merchantBillName = request.getParameter("merchantBillName");
		String supportWebsite = request.getParameter("supportWebsite");//add by davis.guo at 2016-08-16 映射网址
		String fitMerchantType = request
				.getParameter("fitMerchantType");
		/*
		 * Map resultMap = channelClientService.queryChannelConfig(null, null,
		 * orgMerchantCode, null, null, null,null,null); List results= (List)
		 * resultMap.get("channelConfigs"); if(results!=null &&
		 * results.size()>0){ response.getWriter().print("同一个二级商户号只能配置一次！！！");
		 * return null; }
		 */
		String result = channelClientService.updateChannelConfig(id, orgCode,
				orgMerchantCode, orgKey, operator, keyFilePath, status,
				terminalCode, transType, accessCode, mcc, currencyCode,
				pattern, requestMerchantName, merchantBillName, fitMerchantType, supportWebsite);

		if (null == result) {
			response.getWriter().print(1);
		} else {
			response.getWriter().print(result);
		}

		return null;
	}

	public ModelAndView del(HttpServletRequest request,
							HttpServletResponse response) throws IOException {

		response.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		String orgCode = request.getParameter("orgCode");
		String orgMerchantCode = request.getParameter("orgMerchantCode");
		String result = channelClientService.delChannelConfig(id);
		if (null == result) {
			response.getWriter().print(1);
		} else {
			response.getWriter().print(result);
		}

		return null;
	}

	public ModelAndView findConnect(HttpServletRequest request,
							HttpServletResponse response) throws IOException {

		response.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		List resultList = channelClientService.queryMemberConChannelConfigHandler(
				null, null, null,id);
		if (null != resultList && resultList.size() > 0) {
			response.getWriter().print(1);
		} else {
			response.getWriter().print(0);
		}

		return null;
	}

	public ModelAndView pollOutFreePool(HttpServletRequest request,
							HttpServletResponse response) throws IOException {

		response.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		boolean result = channelClientService.pollOutFreePoolHandler(id);
		if (result) {
			response.getWriter().print(1);
		} else {
			response.getWriter().print("操作失败");
		}

		return null;
	}

	public ModelAndView addToFree(HttpServletRequest request,
						  HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		String[] ids = request.getParameterValues("ids");
		if (channelClientService.addToFree(ids)) {
			response.getWriter().print(1);
		} else {
			response.getWriter().print(0);
		}

		return null;
	}

	/**
	 * 批量新增
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws BiffException
	 */
	public ModelAndView batchAdd(HttpServletRequest request,
								 HttpServletResponse response) throws Exception {
		ModelAndView index = new ModelAndView(batchAddView);
		String orgCode = request.getParameter("orgCode");
		String operator = SessionUserHolderUtil.getLoginId();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		DefaultMultipartHttpServletRequest dmRequest = (DefaultMultipartHttpServletRequest) request;
		MultipartFile multiPartFile = dmRequest.getFile("batchAdd");
		if (multiPartFile != null) {
			jxl.Workbook book = jxl.Workbook.getWorkbook(multiPartFile
					.getInputStream());
			@SuppressWarnings("unchecked")
			List<ChannelConfig> channelConfigs = ExcelUtils.getListByReadShell(
					book.getSheet(0), 1, 0, 11,
					new String[] { "orgMerchantCode", "merchantBillName",
							"transType", "pattern", "terminalCode",
							"accessCode", "currencyCode", "mcc", "orgKey",
							"supportWebsite","fitMerchantType"}, ChannelConfig.class);
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String batchNo = "M_" + format.format(new Date());
			for (ChannelConfig channelConfig : channelConfigs) {
				channelConfig.setBatchNo(batchNo);
				channelConfig.setOrgCode(orgCode);
				channelConfig.setOperator(operator);
				String pattern = channelConfig.getPattern();
				if (StringUtils.isNotEmpty(pattern)) {
					if (pattern.equals("MOTO")) {
						channelConfig.setPattern("M");
					} else if (pattern.equals("3D")) {
						channelConfig.setPattern("E");
					} else {
						channelConfig.setPattern("A");
					}
				}
			}
			Map results = channelClientService
					.addBatchChannelConfig(channelConfigs);
			List<Map> list = (List<Map>) results.get("list");
			if (results.get("responseCode").equals("0000")) {
				index.addObject("msg", "上传成功！！");
			}
			if (list != null && list.size() > 0) {
				index.addObject("msg", "上传失败！！");
				index.addObject("list", list);
			}
		}
		ChannelItemOrgCodeEnum[] channelItems = ChannelItemOrgCodeEnum.values();
		return index.addObject("channelItems", channelItems);
	}

	/***
	 * 批量新增页面
	 *
	 * @date 2016年6月21日15:18:06
	 * @author delin
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView initBatchAdd(HttpServletRequest request,
									 HttpServletResponse response) {
		/** 注释 delin 2016年6月24日16:03:37 不取通道名称 取渠道名称 */
		/*
		 * PaymentChannelItemDto paymentChannelItemDto = new
		 * PaymentChannelItemDto(); List channelItems = channelClientService
		 * .queryChannelItem(paymentChannelItemDto);
		 */
		ChannelItemOrgCodeEnum[] channelItems = ChannelItemOrgCodeEnum.values();
		return new ModelAndView(batchAddView).addObject("channelItems",
				channelItems);
	}

	/**
	 * 下载模版
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView dowloadTemp(HttpServletRequest request,
									HttpServletResponse response) {

		List<ChannelConfig> channelConfig = new ArrayList<ChannelConfig>();
		ChannelConfig cc = new ChannelConfig();
		cc.setOrgMerchantCode("772112515");
		cc.setMerchantBillName("xzm");
		cc.setTransType("EDC");
		cc.setPattern("全部");
		cc.setTerminalCode("sda");
		cc.setAccessCode("sda");
		cc.setCurrencyCode("SGD");
		cc.setMcc("5611");
		cc.setOrgKey("GmqYmrQ3eTgcn5bsF3Qc");
		cc.setSupportWebsite("www.bobby.com");
		cc.setFitMerchantType("500");
		channelConfig.add(cc);
		try {
			String[] headers = new String[] { "机构商户号", "商户账单名", "交易类型", "模式",
					"终端号", "授权号", "币种", "MCC", "机构密钥", "映射网址","商户类型(500/800代表iPayLinks/GCPayment)"};
			String[] fields = new String[] { "orgMerchantCode",
					"merchantBillName", "transType", "pattern", "terminalCode",
					"accessCode", "currencyCode", "mcc", "orgKey",
					"supportWebsite","fitMerchantType" };
			Workbook grid = SimpleExcelGenerator.generateGrid("通道二级商户号模版",
					headers, fields, channelConfig);
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

	public void setUpdateView(String updateView) {
		this.updateView = updateView;
	}

	public void setBatchAddView(String batchAddView) {
		this.batchAddView = batchAddView;
	}

	public void setChannelClientService(
			ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}

	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	public void setResultView(String resultView) {
		this.resultView = resultView;
	}

	public void setAddView(String addView) {
		this.addView = addView;
	}

}

