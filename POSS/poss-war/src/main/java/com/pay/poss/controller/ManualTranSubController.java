package com.pay.poss.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.read.biff.BiffException;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.artificial.trade.model.ArtificialTradeBaseConf;
import com.pay.artificial.trade.model.AutoTradeQuery;
import com.pay.artificial.trade.model.AutomaticTrade;
import com.pay.artificial.trade.model.CardInfo;
import com.pay.artificial.trade.service.ManualTranSubService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.client.GatewayOrderQueryService;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.ExcelUtils;
import com.pay.util.MapUtil;
import com.pay.util.SimpleExcelGenerator;

public class ManualTranSubController extends MultiActionController {

	private String index;

	private String cardInfQuery;

	private String cardInfQueryList;

	private String basicConfig;

	private String manualTransList;

	private ManualTranSubService manualTranSubService;

	private GatewayOrderQueryService gatewayOrderQueryService;

	private String tradeResult;

	private String tradeResultList;

	public void setGatewayOrderQueryService(
			GatewayOrderQueryService gatewayOrderQueryService) {
		this.gatewayOrderQueryService = gatewayOrderQueryService;
	}

	public void setTradeResultList(String tradeResultList) {
		this.tradeResultList = tradeResultList;
	}

	public void setTradeResult(String tradeResult) {
		this.tradeResult = tradeResult;
	}

	public void setManualTransList(String manualTransList) {
		this.manualTransList = manualTransList;
	}

	public void setManualTranSubService(
			ManualTranSubService manualTranSubService) {
		this.manualTranSubService = manualTranSubService;
	}

	public void setCardInfQueryList(String cardInfQueryList) {
		this.cardInfQueryList = cardInfQueryList;
	}

	public void setBasicConfig(String basicConfig) {
		this.basicConfig = basicConfig;
	}

	public void setCardInfQuery(String cardInfQuery) {
		this.cardInfQuery = cardInfQuery;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(index);
	}

	public ModelAndView cardInfQuery(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(cardInfQuery);
	}

	public ModelAndView basicConfig(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(basicConfig);
	}

	public ModelAndView tradeQuery(HttpServletRequest request,
			HttpServletResponse response) {
		String batchNumber = request.getParameter("batchNumber");
		return new ModelAndView(tradeResult).addObject("batchNumber",
				batchNumber);
	}

	/***
	 * 交易查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView tradeQueryResult(HttpServletRequest request,
			HttpServletResponse response) {

		String partnerId = request.getParameter("partnerId");
		String batchNumber = request.getParameter("batchNum");
		String tradeStatus = request.getParameter("tradeStatus");
		String tradeOrderNo = request.getParameter("tradeOrderNo");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		Page page = PageUtils.getPage(request);
		AutoTradeQuery autoTradeQuery = new AutoTradeQuery();
		autoTradeQuery.setPartnerId(partnerId);
		autoTradeQuery.setBatchNumber(batchNumber);
		autoTradeQuery.setTradeOrderNo(tradeOrderNo);

		AutoTradeQuery tradeQuery = new AutoTradeQuery();
		Map paraMap = new HashMap();
		paraMap.put("beginTime", startTime);
		paraMap.put("endTime", endTime);
		paraMap.put("tradeOrderNos", tradeOrderNo);
		paraMap.put("statusList", tradeStatus);
		paraMap.put("batchNumber", batchNumber);
		paraMap.put("page", page);
		Map resultMap = gatewayOrderQueryService.queryAutoTradeOrder(paraMap);
		List<Map> tradeOrders = (List<Map>) resultMap.get("result");
		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		return new ModelAndView(tradeResultList).addObject("page", page)
				.addObject("tradeOrders", tradeOrders);
	}

	/**
	 * 查询人工交易的信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView queryManualTran(HttpServletRequest request,
			HttpServletResponse response) {

		String partnerId = request.getParameter("partnerId");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String status = request.getParameter("status");
		Page page = PageUtils.getPage(request);
		AutomaticTrade automaticTrade = new AutomaticTrade();
		automaticTrade.setPartnerId(partnerId);
		automaticTrade.setStatus(status);
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isNotEmpty(startTime)) {
			try {
				automaticTrade.setStartDate(fmt.parse(startTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (StringUtils.isNotEmpty(endTime)) {
			try {
				automaticTrade.setEndDate(fmt.parse(endTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		List<AutomaticTrade> automaticTrades = this.manualTranSubService
				.queryManualTran(automaticTrade, page);
		return new ModelAndView(manualTransList).addObject("automaticTrades",
				automaticTrades).addObject("page", page);
	}

	/**
	 * 人工交易提交
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView manualTransSub(HttpServletRequest request,
			HttpServletResponse response) {
		String partnerId = request.getParameter("partnerId");// 会员号
		String cardNum = request.getParameter("cardNum");// 卡数量
		String cardOrg = request.getParameter("cardOrg");// 卡组织
		String estimatedTime=request.getParameter("estimatedTime");
		String loginId = SessionUserHolderUtil.getLoginId();
		Date currentTime = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		AutomaticTrade automaticTrade = new AutomaticTrade();
		automaticTrade.setCardCount(cardNum.trim());
		automaticTrade.setCardOrg(cardOrg);
		automaticTrade.setPartnerId(partnerId);
		automaticTrade.setSubDate(currentTime);
		automaticTrade.setOperator(loginId);
		automaticTrade.setEstimatedTime(estimatedTime);
		automaticTrade.setStatus("1");
		automaticTrade.setBatchNumber("M_" + format.format(currentTime));
		manualTranSubService.crateAutomaticTrade(automaticTrade);
		return new ModelAndView(index);
	}

	/***
	 * 更改卡信息的状态
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView updateStatus(HttpServletRequest request,
			HttpServletResponse response) {
		String status = request.getParameter("status");
		String id = request.getParameter("id");
		String ids = request.getParameter("ids");
		String pageNo = request.getParameter("pageNo");
		CardInfo cardInfo = new CardInfo();
		if (StringUtils.isNotEmpty(id)) {
			cardInfo.setId(id);
		}
		if (StringUtils.isNotEmpty(ids)) {
			String substring = ids.substring(0, ids.length() - 1);
			cardInfo.setId(substring);
		}
		cardInfo.setStatus(status);
		cardInfo.setUpdateDate(new Date());
		manualTranSubService.updateStatus(cardInfo);
		return new ModelAndView(cardInfQuery).addObject("pageNo", pageNo);
	}
	
	/***
	 * 删除卡片
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView deletecard(HttpServletRequest request,
			HttpServletResponse response) {
		
		String id = request.getParameter("id");
		
		String pageNo = request.getParameter("pageNo");
		
		if (StringUtils.isNotEmpty(id)) {
			manualTranSubService.deletecard(id);
		}
		
		
		return new ModelAndView(cardInfQuery).addObject("pageNo", pageNo);
	}

	/**
	 * 模版下载
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView templateDownload(HttpServletRequest request,
			HttpServletResponse response) {

		List<CardInfo> cardInfos = new ArrayList<CardInfo>();
		CardInfo cardInfo = new CardInfo();
		cardInfo.setEffectiveMonth("11");
		cardInfo.setEffectiveYear("15");
		cardInfo.setCvv("779");
		cardInfo.setCardCode("4111111111111111");
		cardInfos.add(cardInfo);

		try {
			String[] headers = new String[] { "卡号", "有效期月", "有效期年", "CVV" };
			String[] fields = new String[] { "cardCode", "effectiveMonth",
					"effectiveYear", "cvv" };
			Workbook grid = SimpleExcelGenerator.generateGrid("卡片信息上传模版",
					headers, fields, cardInfos);
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

	/**
	 * 查询卡片信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView queryCardInfo(HttpServletRequest request,
			HttpServletResponse response) {
		Page page = PageUtils.getPage(request);
		String cardCode = request.getParameter("cardCode");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String cardOrg = request.getParameter("cardOrg");
		String status = request.getParameter("status");
		CardInfo cardInfo = new CardInfo();
		cardInfo.setCardCode(cardCode);
		cardInfo.setCardOrg(cardOrg);
		cardInfo.setStatus(status);
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isNotEmpty(startTime)) {
			try {
				cardInfo.setStartTime(fmt.parse(startTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (StringUtils.isNotEmpty(endTime)) {
			try {
				cardInfo.setEndTime(fmt.parse(endTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		List<CardInfo> cardInfos = manualTranSubService.queryCardInfo(cardInfo,
				page);
		return new ModelAndView(cardInfQueryList).addObject("cardInfos",
				cardInfos).addObject("page", page);
	}

	/**
	 * 上传 卡的信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws BiffException
	 * @throws IOException
	 */
	public ModelAndView uploadCardInfo(HttpServletRequest request,
			HttpServletResponse response)  {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String batch =multipartRequest.getParameter("pici");
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
				.getFile("orginalFile");
		String loginId = SessionUserHolderUtil.getLoginId();
		jxl.Workbook book=null;
		try {
			book = jxl.Workbook.getWorkbook(file.getInputStream());
		}catch (Exception e) {
			e.printStackTrace();
		}
		List<CardInfo> list = ExcelUtils.getListByReadShell(book.getSheet(0),
				1, 0, 4, new String[] { "cardCode", "effectiveMonth",
						"effectiveYear", "cvv" }, CardInfo.class);
		for (CardInfo cardInfo : list) {
			cardInfo.setStatus("1");
			cardInfo.setCreateDate(new Date());
			cardInfo.setUpdateDate(new Date());
			cardInfo.setOperator(loginId);
			if(batch!=null){
				cardInfo.setBatch(batch);
			}
			String cardOrgCode = cardInfo.getCardCode().substring(0, 1);
			if (cardOrgCode.equals("3")) {
				cardInfo.setCardOrg("JCB");
			} else if (cardOrgCode.equals("4")) {
				cardInfo.setCardOrg("Visa");
			} else if (cardOrgCode.equals("5")) {
				cardInfo.setCardOrg("MasterCard");
			}
		}
		manualTranSubService.createCardInfo(list);
		return new ModelAndView(cardInfQuery);
	}

	/**
	 * 刷单基本信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView saveArtificialTradeBaseConf(HttpServletRequest request,
			HttpServletResponse response) {

		String currencyCode = request.getParameter("currencyCode");
		String amountMax = request.getParameter("AmountMax");
		String amountMin = request.getParameter("AmountMin");
		/*String tradingTimes = request.getParameter("tradingTimes");*/
		String sleepStartTime = request.getParameter("sleepStartTime");
		String sleepEndTime = request.getParameter("sleepEndTime");
		ArtificialTradeBaseConf artificialTradeBaseConf = new ArtificialTradeBaseConf();
		artificialTradeBaseConf.setAmountMax(amountMax);
		artificialTradeBaseConf.setAmountMin(amountMin);
	/*	artificialTradeBaseConf.setTradingTimes(tradingTimes);*/
		artificialTradeBaseConf.setCurrencyCode(currencyCode);
		artificialTradeBaseConf.setSleepStartTime(sleepStartTime);
		artificialTradeBaseConf.setSleepEndTime(sleepEndTime);
		String filename = "/opt/pay/config/inf/artificialTrade/artificialTradeBaseConf.properties";
		try {
			this.writePropertiesFile(filename, artificialTradeBaseConf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ModelAndView(index);
	}

	/**
	 * 写入配置文件
	 * 
	 * @param filename
	 * @param artificialTradeBaseConf
	 * @throws IOException
	 */
	public void writePropertiesFile(String filename,
			ArtificialTradeBaseConf artificialTradeBaseConf) throws IOException {
		Properties properties = new Properties();
		OutputStream outputStream = new FileOutputStream(filename);
		properties.setProperty("currencyCode",
				artificialTradeBaseConf.getCurrencyCode());
		properties.setProperty("amountMax",
				artificialTradeBaseConf.getAmountMax());
		properties.setProperty("amountMin",
				artificialTradeBaseConf.getAmountMin());
	/*	properties.setProperty("tradingTimes",
				artificialTradeBaseConf.getTradingTimes());*/
		properties.setProperty("sleepStartTime",
				artificialTradeBaseConf.getSleepStartTime());
		properties.setProperty("sleepEndTime",
				artificialTradeBaseConf.getSleepEndTime());
		properties.store(outputStream, "author: delin.dong");
		outputStream.close();
	}

	/**
	 * 交易停止
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView tradeShutdown(HttpServletRequest request,
			HttpServletResponse response) {
		String batchNumber = request.getParameter("batchNumber");
		AutomaticTrade automaticTrade=new AutomaticTrade();
		automaticTrade.setBatchNumber(batchNumber);
		automaticTrade.setEndDate(new Date());
		manualTranSubService.tradeShutdown(automaticTrade);
		return new ModelAndView(index);
	}
	
	/**
	 * 开启人工交易
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView start(HttpServletRequest request,
			HttpServletResponse response){
		manualTranSubService.start();
		return new ModelAndView(index);
	}
	
}
