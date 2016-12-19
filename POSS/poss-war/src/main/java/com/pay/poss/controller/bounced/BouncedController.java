package com.pay.poss.controller.bounced;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.fi.exception.BusinessException;
import com.pay.file.parser.dto.FileParseResult;
import com.pay.file.service.FileService;
import com.pay.fo.controller.fundout.common.MultiActionAndLogProcController;
import com.pay.fundout.withdraw.dto.operatorlog.OperatorlogDTO;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.pe.dto.BeanUtils;
import com.pay.poss.client.BouncedQueryService;
import com.pay.poss.client.ChargeBackService;
import com.pay.poss.client.CrosspayTxncoreClientService;
import com.pay.poss.controller.fi.commons.BouncedEnum;
import com.pay.poss.controller.fi.dto.BouncedFlowVO;
import com.pay.poss.controller.fi.dto.BouncedOrderVO;
import com.pay.poss.controller.fi.dto.BouncedResultDTO;
import com.pay.poss.controller.fi.dto.ChargeBackOrder;
import com.pay.poss.ftp.Ftp;
import com.pay.poss.ftp.FtpUtil;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.DateUtil;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.SimpleExcelGenerator;
import com.pay.util.StringUtil;

/**
 * 单项拒付登记 File: BouncedController.java Description: Copyright 2016-2030
 * IPAYLINKS Corporation. All rights reserved. Date Author Changes 2016年5月10日
 * mmzhang Create
 *
 */
public class BouncedController extends MultiActionAndLogProcController {

	private BouncedQueryService bouncedQueryService;
	private ChargeBackService chargeBackService;
	private EnterpriseBaseService enterpriseBaseService;
	private FileService fileService;
	private CrosspayTxncoreClientService txncoreClientService;
	// 下载申诉资料
	private String appealDataFilePath;
	// ftp连接用户名
	private String ftpUserName;

	// ftp连接密码
	private String ftpPassword;
	// 批量状态
	private static String batchstatus = "0";

	public void setAppealDataFilePath(String appealDataFilePath) {
		this.appealDataFilePath = appealDataFilePath;
	}

	public BouncedQueryService getBouncedQueryService() {
		return bouncedQueryService;
	}

	public void setBouncedQueryService(BouncedQueryService bouncedQueryService) {
		this.bouncedQueryService = bouncedQueryService;
	}

	/**
	 * 初始化页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {

		return new ModelAndView(urlMap.get("init"));
	}

	/**
	 * 初始化页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView batchinit(HttpServletRequest request, HttpServletResponse response) {
		Calendar time = Calendar.getInstance();
		Date todayDate = time.getTime();
		return new ModelAndView(urlMap.get("batchinit")).addObject("todayDate", todayDate);
	}

	/**
	 * 查询单项登记
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws UnsupportedEncodingException
	 */
	public ModelAndView query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Calendar time = Calendar.getInstance();
		Date todayDate = time.getTime();
		String tradeOrderNo = request.getParameter("tradeOrderNo");
		String channelOrderNo = request.getParameter("channelOrderNo");
		String cardNo = request.getParameter("cardNo");
		String authorisation = request.getParameter("authorisation");
		String tranDate = request.getParameter("tranDate");

		Map paraMap = new HashMap();
		paraMap.put("tradeOrderNo", tradeOrderNo);
		paraMap.put("channelOrderNo", channelOrderNo);
		paraMap.put("cardNo", cardNo);
		paraMap.put("authorisation", authorisation);
		paraMap.put("tranDate", tranDate);

		List<Map> map = bouncedQueryService.bouncedQuery(paraMap);
		return new ModelAndView(urlMap.get("list")).addObject("page", map).addObject("todayDate",
				todayDate);

	}

	private double getTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int min = c.get(Calendar.MINUTE);
		double s = min / 100.0;
		double rst = hour + s;

		return rst;
	}

	/**
	 * 拒付登记界面 //<option value='5'>IPAY</option> //<option value='8'>GC</option>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 *             2016年5月12日 mmzhang add
	 * @throws UnsupportedEncodingException
	 */
	public ModelAndView register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Calendar time = Calendar.getInstance();
		Date todayDate = time.getTime();
		String tradeOrderNo = StringUtil.null2String(request.getParameter("tradeOrderNo"));
		String channelOrderNo = request.getParameter("channelOrderNo");
		String authorisation = request.getParameter("authorisation");
		String tranDate = request.getParameter("tranDate");
		String partnerId = request.getParameter("partnerId");
		String cardholderCardno = request.getParameter("cardholderCardno");
		String orgCode = request.getParameter("orgCode");
		String orderAmount = StringUtil.null2StringAmount(request.getParameter("orderAmount"));
		String payAmount = StringUtil.null2StringAmount(request.getParameter("payAmount"));
		String transferRate = request.getParameter("transferRate");
		String transferCurrencyCode = request.getParameter("transferCurrencyCode");
		String canBouncedAmount = StringUtil.null2StringAmount(request
				.getParameter("canBouncedAmount"));
		String doingBouncedAmount = StringUtil.null2StringAmount(request
				.getParameter("doingBouncedAmount"));
		String cardOrg = request.getParameter("cardOrg");
		String batchNo = request.getParameter("batchNo");
		String refNo = request.getParameter("refNo");
		String settlementCurrencyCode = StringUtil.null2String(request
				.getParameter("settlementCurrencyCode"));
		String settlementRate = StringUtil
				.null2StringAmount(request.getParameter("settlementRate"));
		String orderId = StringUtil.null2String(request.getParameter("orderId"));
		String currencyCode = StringUtil.null2String(request.getParameter("currencyCode"));
		String floatValue = StringUtil.null2String(request.getParameter("floatValue"));
		String remark = StringUtil.null2String(request.getParameter("remark"));
		String overBouncedAmount = StringUtil.null2StringAmount(request
				.getParameter("overBouncedAmount"));
		String assureSettlementFlg = StringUtil.null2String(request
				.getParameter("assureSettlementFlg"));
		String settlementFlg = StringUtil.null2String(request.getParameter("settlementFlg"));
		String merchantCode = StringUtil.null2String(request.getParameter("merchantCode"));
		String bouncedRemark = StringUtil.null2String(request.getParameter("bouncedRemark"));
		String checkFlag = StringUtil.null2String(request.getParameter("checkFlag"));

		// 重新查询网关订单金额
		Map paraMap = new HashMap();

		paraMap.put("channelOrderNo", channelOrderNo);

		List<Map> map1 = bouncedQueryService.bouncedQuery(paraMap);

		if (map1 != null && map1.get(0) != null) {
			Map vo = map1.get(0);
			overBouncedAmount = getMapValueByAmount(vo, "overBouncedAmount");
			doingBouncedAmount = getMapValueByAmount(vo, "doingBouncedAmount");
		}
				
		CurrencyCodeEnum[] currencyCodeEnum = CurrencyCodeEnum.values();
		BouncedResultDTO resultdto = new BouncedResultDTO();
		resultdto.setTradeOrderNo(tradeOrderNo);
		resultdto.setChannelOrderNo(channelOrderNo);
		resultdto.setAuthorisation(authorisation);
		resultdto.setCardholderCardno(cardholderCardno);
		resultdto.setStranDate(tranDate);
		resultdto.setPartnerId(partnerId);
		resultdto.setOrgCode(orgCode);
		resultdto.setTransferCurrencyCode(transferCurrencyCode);
		resultdto.setSorderAmount(orderAmount);
		resultdto.setStransferRate(transferRate);
		resultdto.setSpayAmount(payAmount);
		resultdto.setScanBouncedAmount(canBouncedAmount);
		resultdto.setSdoingBouncedAmount(doingBouncedAmount);
		resultdto.setCardOrg(cardOrg);
		resultdto.setBatchNo(batchNo);
		resultdto.setRefNo(refNo);
		resultdto.setSettlementRate(new BigDecimal(settlementRate));
		resultdto.setSettlementCurrencyCode(settlementCurrencyCode);
		resultdto.setOrderId(orderId);
		resultdto.setCurrencyCode(currencyCode);
		resultdto.setFloatValue(floatValue);
		resultdto.setRemark(remark);
		resultdto.setSettlementFlg(settlementFlg);
		resultdto.setAssureSettlementFlg(assureSettlementFlg);
		resultdto.setSoverBouncedAmount(overBouncedAmount);
		resultdto.setMerchantCode(merchantCode);
		resultdto.setCheckFlag(checkFlag);
		resultdto.setBouncedRemark(bouncedRemark);
		return new ModelAndView(urlMap.get("register"))
				.addObject("currencyCodeEnum", currencyCodeEnum).addObject("dto", resultdto)
				.addObject("todayDate", todayDate);

	}

	/**
	 * 初始化页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView batch(HttpServletRequest request, HttpServletResponse response) {
		Calendar time = Calendar.getInstance();
		Date todayDate = time.getTime();
		return new ModelAndView(urlMap.get("batch")).addObject("todayDate", todayDate);
	}

	/**
	 * 拒付批次查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 *             2016年5月12日 mmzhang add
	 * @throws UnsupportedEncodingException
	 */
	public ModelAndView batchQuery(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Calendar time = Calendar.getInstance();
		Date todayDate = time.getTime();
		String bouncedType = request.getParameter("bouncedType");
		String batchNo = request.getParameter("batchNo");
		String beginCreateDate = request.getParameter("beginCreateDate");
		String endCreateDate = request.getParameter("endCreateDate");
		String beginLastDate = request.getParameter("beginLastDate");
		String endLastDate = request.getParameter("endLastDate");
		String operator = SessionUserHolderUtil.getLoginId();
		Map paraMap1 = new HashMap();
		paraMap1.put("batchNo", batchNo);
		paraMap1.put("bouncedType", bouncedType);
		paraMap1.put("beginCreateDate", beginCreateDate);
		paraMap1.put("endCreateDate", endCreateDate);
		paraMap1.put("beginLastDate", beginLastDate);
		paraMap1.put("endLastDate", endLastDate);
		paraMap1.put("operator", operator);

		// 查询今日批次有多少个，生成批次号
		Map resultMap = bouncedQueryService.bouncedResultQuery(paraMap1);
		List<Map> map1 = (List<Map>) resultMap.get("bouncedResults");
		return new ModelAndView(urlMap.get("batchList")).addObject("page", map1).addObject(
				"todayDate", todayDate);

	}

	/**
	 * 批量登记查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView batchRegister(HttpServletRequest request, HttpServletResponse response) {
		Calendar time = Calendar.getInstance();
		Date todayDate = time.getTime();
		return new ModelAndView(urlMap.get("batchRegister")).addObject("todayDate", todayDate);
	}

	/**
	 * 拒付登记界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 *             2016年5月12日 mmzhang add
	 * @throws UnsupportedEncodingException
	 */
	public ModelAndView batchRegisterQuery(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Calendar time = Calendar.getInstance();
		Date todayDate = time.getTime();

		String batchNo = request.getParameter("batchNo");
		String channelOrderNo = StringUtil.null2String(request.getParameter("channelOrderNo"))
				.trim();
		String refNo = StringUtil.null2String(request.getParameter("refNo"));
		String tranDate = request.getParameter("tranDate");
		String authorisation = StringUtil.null2String(request.getParameter("authorisation"));
		String bouncedType = request.getParameter("bouncedType");
		String registerFlag = StringUtil.null2String(request.getParameter("registerFlag"));
		String beginCreateDate = request.getParameter("beginCreateDate");
		String endCreateDate = request.getParameter("endCreateDate");
		Page page = PageUtils.getPage(request);
		Map paraMap1 = new HashMap();
		paraMap1.put("batchNo", batchNo);
		paraMap1.put("channelOrderNo", channelOrderNo);
		paraMap1.put("refNo", refNo);
		paraMap1.put("tranDate", tranDate);
		paraMap1.put("authorisation", authorisation);
		paraMap1.put("bouncedType", bouncedType);
		paraMap1.put("registerFlag", registerFlag);
		paraMap1.put("beginCreateDate", beginCreateDate);
		paraMap1.put("endCreateDate", endCreateDate);
		paraMap1.put("page", page);

		// 查询今日批次有多少个，生成批次号
		Map resultMap = bouncedQueryService.bouncedResultQuery(paraMap1);
		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		List<Map> map1 = (List<Map>) resultMap.get("bouncedResults");
		return new ModelAndView(urlMap.get("batchQueryResult")).addObject("result", map1)
				.addObject("todayDate", todayDate).addObject("page", page);

	}

	/**
	 * 拒付查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView bouncedQuery(HttpServletRequest request, HttpServletResponse response) {
		Calendar time = Calendar.getInstance();
		Date todayDate = time.getTime();
		String status = request.getParameter("status");
		return new ModelAndView(urlMap.get("bouncedQuery")).addObject("todayDate", todayDate)
				.addObject("statuss", status);
	}

	/**
	 * 拒付处理审核
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView bouncedApprove(HttpServletRequest request, HttpServletResponse response) {
		Calendar time = Calendar.getInstance();
		Date todayDate = time.getTime();
		return new ModelAndView(urlMap.get("bouncedApprove")).addObject("todayDate", todayDate);
	}

	/**
	 * 拒付处理
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws UnsupportedEncodingException
	 *             2016年5月25日 mmzhang add
	 */
	public ModelAndView bouncedQueryList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Calendar time = Calendar.getInstance();
		Date todayDate = time.getTime();

		String batchNo = request.getParameter("batchNo");
		String partnerId = request.getParameter("partnerId");
		String channelOrderNo = StringUtil.null2String(request.getParameter("channelOrderNo"))
				.trim();
		String refNo = StringUtil.null2String(request.getParameter("refNo"));
		String orderNo = StringUtil.null2String(request.getParameter("orderNo"));
		String orgCode = StringUtil.null2String(request.getParameter("orgCode"));
		String bouncedType = StringUtil.null2String(request.getParameter("bouncedType"));
		String status = StringUtil.null2String(request.getParameter("status"));
		String statusIn = StringUtil.null2String(request.getParameter("statuss"));
		String beginCreateDate = request.getParameter("beginCreateDate");
		String endCreateDate = request.getParameter("endCreateDate");
		String orderId = StringUtil.null2String(request.getParameter("orderId"));

		BouncedOrderVO bouncedOrderVO = new BouncedOrderVO();
		bouncedOrderVO.setBatchNo(batchNo);
		bouncedOrderVO.setMemberCode(partnerId);
		bouncedOrderVO.setStatusIn(statusIn);
		bouncedOrderVO.setOrderNo(orderNo);
		
		if (!StringUtil.isEmpty(channelOrderNo)) {
			bouncedOrderVO.setChannelOrderId(Long.valueOf(channelOrderNo));
		}
		if (!StringUtil.isEmpty(orderId)) {
			bouncedOrderVO.setOrderId(Long.valueOf(orderId));
		}
		bouncedOrderVO.setRefNo(refNo);
		bouncedOrderVO.setOrgCode(orgCode);
		if (!StringUtil.isEmpty(bouncedType)) {
			bouncedOrderVO.setCpType(Integer.valueOf(bouncedType));
		}
		if (!StringUtil.isEmpty(status)) {
			bouncedOrderVO.setStatus(Integer.valueOf(status));
		}

		bouncedOrderVO.setCreateBeginTime(beginCreateDate);
		bouncedOrderVO.setCreateEndTime(endCreateDate);
		Page page = PageUtils.getPage(request);
		Map resultMap = chargeBackService.queryBouncedOrder(bouncedOrderVO, page);
		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		List<BouncedOrderVO> bouncedOrderVOs = (List<BouncedOrderVO>) resultMap.get("result");
		return new ModelAndView(urlMap.get("bouncedQueryList"))
				.addObject("result", bouncedOrderVOs).addObject("todayDate", todayDate)
				.addObject("page", page);

	}

	/**
	 * 拒付处理 下载
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public ModelAndView download(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String batchNo = request.getParameter("batchNo");
		String partnerId = request.getParameter("partnerId");
		String channelOrderNo = StringUtil.null2String(request.getParameter("channelOrderNo"))
				.trim();
		String refNo = StringUtil.null2String(request.getParameter("refNo"));
		String orgCode = StringUtil.null2String(request.getParameter("orgCode"));
		String bouncedType = StringUtil.null2String(request.getParameter("bouncedType"));
		String status = StringUtil.null2String(request.getParameter("status"));
		String statusIn = StringUtil.null2String(request.getParameter("statuss"));
		String beginCreateDate = request.getParameter("beginCreateDate");
		String endCreateDate = request.getParameter("endCreateDate");
		BouncedOrderVO bouncedOrderVO = new BouncedOrderVO();
		bouncedOrderVO.setBatchNo(batchNo);
		bouncedOrderVO.setMemberCode(partnerId);
		bouncedOrderVO.setStatusIn(statusIn);
		if (!StringUtil.isEmpty(channelOrderNo)) {
			bouncedOrderVO.setChannelOrderId(Long.valueOf(channelOrderNo));
		}
		bouncedOrderVO.setRefNo(refNo);
		bouncedOrderVO.setOrgCode(orgCode);
		if (!StringUtil.isEmpty(bouncedType)) {
			bouncedOrderVO.setCpType(Integer.valueOf(bouncedType));
		}
		if (!StringUtil.isEmpty(status)) {
			bouncedOrderVO.setStatus(Integer.valueOf(status));
		}

		bouncedOrderVO.setCreateBeginTime(beginCreateDate);
		bouncedOrderVO.setCreateEndTime(endCreateDate);
		Map resultMap = chargeBackService.queryBouncedOrder(bouncedOrderVO, null);
		List<Map> bouncedOrderVOs = (List<Map>) resultMap.get("result");
		List<BouncedOrderVO> bouncedOrder = MapUtil.map2List(BouncedOrderVO.class, bouncedOrderVOs);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		for (BouncedOrderVO bouncedOrderVO2 : bouncedOrder) {
			Date tradeDate = bouncedOrderVO2.getTradeDate();
			Date createDate = bouncedOrderVO2.getCreateDate();
			Date latestAnswerDate = bouncedOrderVO2.getLatestAnswerDate();
			if (tradeDate != null && !tradeDate.equals("")) {
				bouncedOrderVO2.setTradeDate(sd.parse(sd.format(tradeDate)));
			}
			if (createDate != null && !createDate.equals("")) {
				bouncedOrderVO2.setCreateDate(sd.parse(sd.format(createDate)));
			}
			if (latestAnswerDate != null && !latestAnswerDate.equals("")) {
				bouncedOrderVO2.setLatestAnswerDate(sd.parse(sd.format(latestAnswerDate)));
			}
			if (bouncedOrderVO2.getCpType().equals(0)) {
				bouncedOrderVO2.setCpTypeDesc("拒付");
			} else if (bouncedOrderVO2.getCpType().equals(1)) {
				bouncedOrderVO2.setCpTypeDesc("银行调单");
			} else if (bouncedOrderVO2.getCpType().equals(2)) {
				bouncedOrderVO2.setCpTypeDesc("内部调单");
			}
			bouncedOrderVO2.setTradeAmount(bouncedOrderVO2.getTradeAmount().divide(
					new BigDecimal(1000)));
			bouncedOrderVO2.setPayAmount(bouncedOrderVO2.getPayAmount()
					.divide(new BigDecimal(1000)));
			bouncedOrderVO2.setChargeBackAmount(new BigDecimal(bouncedOrderVO2
					.getChargeBackAmount()).divide(new BigDecimal(1000)).toString());
			if (bouncedOrderVO2.getBouncedAmount() != null
					&& !bouncedOrderVO2.getBouncedAmount().equals("")) {
				bouncedOrderVO2.setBouncedAmount(bouncedOrderVO2.getBouncedAmount().divide(
						new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP));

			}
			// System.out.println("bouncedOrderVO2.getBouncedAmount()="+bouncedOrderVO2.getBouncedAmount());
			if (bouncedOrderVO2.getStatus().equals(0)) {
				bouncedOrderVO2.setStatusDesc("未处理");
			} else if (bouncedOrderVO2.getStatus().equals(1)) {
				bouncedOrderVO2.setStatusDesc("已上传资料");
			} else if (bouncedOrderVO2.getStatus().equals(2)) {
				bouncedOrderVO2.setStatusDesc("已递交银行");
			} else if (bouncedOrderVO2.getStatus().equals(3)) {
				bouncedOrderVO2.setStatusDesc("申诉失败待复核");
			} else if (bouncedOrderVO2.getStatus().equals(4)) {
				bouncedOrderVO2.setStatusDesc("申诉成功待复核");
			} else if (bouncedOrderVO2.getStatus().equals(5)) {
				bouncedOrderVO2.setStatusDesc("申诉失败");
			} else if (bouncedOrderVO2.getStatus().equals(6)) {
				bouncedOrderVO2.setStatusDesc("申诉成功");
			} else if (bouncedOrderVO2.getStatus().equals(7)) {
				bouncedOrderVO2.setStatusDesc("不申诉");
			}
		}

		try {
			String[] headers = new String[] { "批次号", "拒付订单号", "商户会员号", "渠道订单号", "档案号", "交易日期",
					"交易金额", "支付金额", "支付币种", "拒付类型", "登记时间", "银行拒付金额", "银行拒付币种", "拒付状态", "最晚回复日期",
					"拒付原因", "备注" };
			String[] fields = new String[] { "batchNo", "orderId", "memberCode", "channelOrderId",
					"refNo", "tradeDate", "tradeAmount", "payAmount", "currencyCode", "cpTypeDesc",
					"createDate", "bouncedAmount", "cpCurrencyCode", "statusDesc",
					"latestAnswerDate", "reasonCode", "remark" };
			Workbook grid = SimpleExcelGenerator
					.generateGrid("拒付处理", headers, fields, bouncedOrder);
			Date myDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			String dd = sdf.format(myDate);
			response.setContentType("application/force-download");
			response.setContentType("applicationnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + dd + ".xls");
			grid.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 拒付处理
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws UnsupportedEncodingException
	 *             2016年5月25日 mmzhang add
	 */
	public ModelAndView queryDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Calendar time = Calendar.getInstance();
		Date todayDate = time.getTime();

		String orderId = StringUtil.null2String(request.getParameter("orderId"));
		BouncedOrderVO bouncedOrderVO = new BouncedOrderVO();
		bouncedOrderVO.setOrderId(Long.valueOf(orderId));
		Page page = PageUtils.getPage(request);
		Map resultMap = chargeBackService.queryBouncedOrder(bouncedOrderVO, null);
		// 查询审批流水
		BouncedFlowVO bouncedFlowVO = new BouncedFlowVO();
		bouncedFlowVO.setOrderId(orderId);
		Map resultMapFlow = chargeBackService.queryBouncedFlow(bouncedFlowVO);
		List<BouncedFlowVO> bouncedFlowVOs = (List<BouncedFlowVO>) resultMapFlow.get("result");
		List<BouncedOrderVO> bouncedOrderVOs = (List<BouncedOrderVO>) resultMap.get("result");
		return new ModelAndView(urlMap.get("queryDetail"))
				.addObject("bouncedFlows", bouncedFlowVOs)
				.addObject("mDto", bouncedOrderVOs.get(0)).addObject("todayDate", todayDate);

	}

	/**
	 * 拒付登记详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws UnsupportedEncodingException
	 *             2016年6月2日 mmzhang add
	 */
	public ModelAndView bouncedResultDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String id = StringUtil.null2String(request.getParameter("id"));
		Map paraMap1 = new HashMap();
		paraMap1.put("id", id);
		Map resultMap = bouncedQueryService.bouncedResultQuery(paraMap1);
		List<Map> map1 = (List<Map>) resultMap.get("bouncedResults");
		return new ModelAndView(urlMap.get("bouncedResultDetail")).addObject("result", map1.get(0));

	}

	/**
	 * 拒付处理审核
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws UnsupportedEncodingException
	 *             2016年5月25日 mmzhang add
	 */
	public ModelAndView queryApproveDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Calendar time = Calendar.getInstance();
		Date todayDate = time.getTime();

		String orderId = StringUtil.null2String(request.getParameter("orderId"));
		BouncedOrderVO bouncedOrderVO = new BouncedOrderVO();
		bouncedOrderVO.setOrderId(Long.valueOf(orderId));
		Page page = PageUtils.getPage(request);
		Map resultMap = chargeBackService.queryBouncedOrder(bouncedOrderVO, null);
		List<BouncedOrderVO> bouncedOrderVOs = (List<BouncedOrderVO>) resultMap.get("result");

		// 查询审批流水
		BouncedFlowVO bouncedFlowVO = new BouncedFlowVO();
		bouncedFlowVO.setOrderId(orderId);
		Map resultMapFlow = chargeBackService.queryBouncedFlow(bouncedFlowVO);
		List<BouncedFlowVO> bouncedFlowVOs = (List<BouncedFlowVO>) resultMapFlow.get("result");
		return new ModelAndView(urlMap.get("queryApproveDetail"))
				.addObject("mDto", bouncedOrderVOs.get(0))
				.addObject("bouncedFlows", bouncedFlowVOs).addObject("todayDate", todayDate);

	}

	
	
	/**
	 * 批量处理
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws UnsupportedEncodingException
	 * 2016年7月26日   mmzhang     add
	 * @throws ParseException 
	 */
	public ModelAndView handerLoadBatchApp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, UnsupportedEncodingException, ParseException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Calendar time = Calendar.getInstance();
		Date todayDate = time.getTime();
		String handleDatas = request.getParameter("handleDatas");
		String[] split = handleDatas.split("@@");
		String[] statuss={"3","5"};
		Map paraMap= new HashMap();
		paraMap.put("ids", split);
		paraMap.put("registerFlag", "0");
		paraMap.put("statuss", statuss);
		Map maps = bouncedQueryService.bouncedResultQuery(paraMap);
		List<Map> batchmaps = (List<Map>) maps.get("bouncedResults");
		if (batchmaps != null && batchmaps.size() > 0) {
		// 导入结果的统计
		int status0Count = 0;
		int status1Count = 0;
		int status2Count = 0;
		int status3Count = 0;
		int status4Count = 0;
		int status5Count = 0;
		List<BouncedResultDTO> listadd = new ArrayList<BouncedResultDTO>();
		List<BouncedOrderVO> batchBouncedOrders = new ArrayList<BouncedOrderVO>();
		BouncedResultDTO countvo= new BouncedResultDTO();
		countvo.setStatus0Count(status0Count);
		countvo.setStatus1Count(status1Count);
		countvo.setStatus2Count(status2Count);
		countvo.setStatus3Count(status3Count);
		countvo.setStatus4Count(status4Count);
		countvo.setStatus5Count(status5Count);
		batchBouncedBuild(listadd,"", batchBouncedOrders,batchmaps, countvo,true);
		status0Count = countvo.getStatus0Count();
		status1Count = countvo.getStatus1Count();
		status2Count = countvo.getStatus2Count();
		status3Count = countvo.getStatus3Count();
		status4Count = countvo.getStatus4Count();
		status5Count = countvo.getStatus5Count();
		// 拒付匹配结果保存
		if (!listadd.isEmpty()) {
			bouncedQueryService.updateBouncedResult(listadd);
		}
		//拒付订单保存
		if (!batchBouncedOrders.isEmpty()) {
			chargeBackService.addChargeBackOrderNew(batchBouncedOrders);
		}

		return new ModelAndView(urlMap.get("batchResult")).addObject("message", "拒付批量导入成功！")
				.addObject("todayDate", todayDate).addObject("page", listadd)
				.addObject("status0Count", status0Count)
				.addObject("status1Count", status1Count)
				.addObject("status2Count", status2Count)
				.addObject("status3Count", status3Count)
				.addObject("status4Count", status4Count)
				.addObject("status5Count", status5Count);
		}else{
			return new ModelAndView(urlMap.get("batchResult")).addObject("message", "没有需要批量导入的数据！");
		}
		
	}
	/**
	 * 批量删除
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws UnsupportedEncodingException
	 * 2016年7月26日   mmzhang     add
	 */
	public ModelAndView handerDeleteBatchApp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Calendar time = Calendar.getInstance();
		Date todayDate = time.getTime();
		String handleDatas = request.getParameter("handleDatas");
		String[] split = handleDatas.split("＃＃");
		//状态为6的为全额退款的可删除
		Map paraMap= new HashMap();
		paraMap.put("ids", split);
		paraMap.put("registerFlag", "0");
		paraMap.put("canBouncedAmount", "0");
		Map maps = bouncedQueryService.bouncedResultQuery(paraMap);
		List<Map> batchmaps = (List<Map>) maps.get("bouncedResults");
		List<String> idlist=new ArrayList<String>();
		if (batchmaps != null && batchmaps.size() > 0) {
			for (Map vo : batchmaps) {
				String id = getMapValueByString(vo, "id");
				idlist.add(id);
			}
			
		}
		if (idlist != null && idlist.size() > 0) {
			String []a =new String[idlist.size()];
		String [] ids=(String[]) idlist.toArray(a);
		BouncedResultDTO vo= new BouncedResultDTO();
		vo.setIds(ids);
		vo.setRegisterFlag(BouncedEnum.registerFlag2.getType());
		bouncedQueryService.updateBouncedResultById(vo);
		
		return new ModelAndView(urlMap.get("batchQueryResult")).addObject("message", "拒付订批量删除成功！");
		}else{
			return new ModelAndView(urlMap.get("batchQueryResult")).addObject("message", "只有对于交易金额-已退金额-已拒付金额-拒付中金额=0的订单才能进行【删除】操作");	
		}
		
	}

	/**
	 * 拒付处理
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws UnsupportedEncodingException
	 *             2016年5月25日 mmzhang add
	 */
	public ModelAndView handerAuditBatch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		List<ChargeBackOrder> chargeBackOrders = new ArrayList<ChargeBackOrder>();
		List<BouncedFlowVO> bouncedFlowVOs = new ArrayList<BouncedFlowVO>();
		String handleDatas = request.getParameter("handleDatas");
		String status = StringUtil.null2String(request.getParameter("status"));
		String status0 = status;
		String remark = StringUtil.null2String(request.getParameter("remark"));
		String operator = SessionUserHolderUtil.getLoginId();
		String[] split = handleDatas.split("＃＃");
		List<String> statuss = new ArrayList<String>();
		//${dto.orderId},${dto.status},${dto.cpType},${dto.merchantCode}
		Map<String, String> statusMap = new HashMap<String, String>();
		for (int i = 0; i < split.length; i++) {
			String[] data = split[i].split(",");
			statuss.add(data[1]);
			statusMap.put(data[0], data[1]);
		}
		for (Entry<String, String> entry : statusMap.entrySet()) {
			ChargeBackOrder bouncedOrderVO = new ChargeBackOrder();
			BouncedFlowVO bouncedFlowVO = new BouncedFlowVO();
			
			bouncedOrderVO.setStatus(Integer.valueOf(status0));
			bouncedOrderVO.setAuditOperator(operator);
			bouncedOrderVO.setAuditDate(new Date());
			bouncedOrderVO.setOrderId(Long.valueOf(entry.getKey()));
			bouncedOrderVO.setAuditMsg(remark);
			chargeBackOrders.add(bouncedOrderVO);
			bouncedFlowVO.setOperator(operator);
			bouncedFlowVO.setCreateDate(new Date());
			bouncedFlowVO.setOrderId(entry.getKey());
			bouncedFlowVO.setStatus(status0);
			bouncedFlowVO.setRemark(remark);
			bouncedFlowVOs.add(bouncedFlowVO);
		}

		Map resultMap = chargeBackService.updateChargeBackOrder(chargeBackOrders, null);
		chargeBackService.updateBouncedFlow(bouncedFlowVOs);
		return new ModelAndView(urlMap.get("bouncedQueryList")).addObject("message", "拒付订单处理成功！");

	}
	/**
	 * 拒付处理审核
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws UnsupportedEncodingException
	 *             2016年5月25日 mmzhang add
	 */
	public ModelAndView handerAuditBatchApp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		List<ChargeBackOrder> chargeBackOrders = new ArrayList<ChargeBackOrder>();
		List<BouncedFlowVO> bouncedFlowVOs = new ArrayList<BouncedFlowVO>();
		String handleDatas = request.getParameter("handleDatas");
		String status = StringUtil.null2String(request.getParameter("status"));
		String status0 = status;
		String remark = StringUtil.null2String(request.getParameter("remark"));
		String operator = SessionUserHolderUtil.getLoginId();
		String[] split = handleDatas.split("＃＃");
		List<String> orderIds = new ArrayList<String>();
		List<String> statuss = new ArrayList<String>();
		Map<String, String> statusMap = new HashMap<String, String>();
		for (int i = 0; i < split.length; i++) {
			String[] data = split[i].split(",");
			orderIds.add(data[0]);
			statuss.add(data[1]);
			statusMap.put(data[0], data[1] +"," + data[2] +"," + data[data.length - 1]);
		}
		for (Entry<String, String> entry : statusMap.entrySet()) {
			ChargeBackOrder bouncedOrderVO = new ChargeBackOrder();
			BouncedFlowVO bouncedFlowVO = new BouncedFlowVO();
			String[] oldStatuss = entry.getValue().split(",");
			String oldStatus = oldStatuss[0];
			String cpType = oldStatuss[1];
			String gcFlag = oldStatuss[2];
			if ("1".equals(status)) {
				if ("3".equals(oldStatus)) {
					status0 = "5";
				} else if ("4".equals(oldStatus)) {
					status0 = "6";
				}
			}
			bouncedOrderVO.setStatus(Integer.valueOf(status0));
			// 如果拒付申诉失败，直接扣款
			if (BouncedEnum.statusAudit5.getType().equals(status0)
					&& (BouncedEnum.bouncedType0.getType().equals(cpType) || 
					(BouncedEnum.gcflag8.getType().equals(gcFlag) && BouncedEnum.bouncedType1
							.getType().equals(cpType)))){

				bouncedOrderVO.setAmountType(BouncedEnum.amountType7.getType());

				// 如果调单申诉失败，继续冻结

				// 如果申诉成功，解冻
			} else if (BouncedEnum.statusAudit6.getType().equals(status0)) {

				bouncedOrderVO.setAmountType(BouncedEnum.amountType5.getType());
			}
			bouncedOrderVO.setAuditOperator(operator);
			bouncedOrderVO.setAuditDate(new Date());
			bouncedOrderVO.setOrderId(Long.valueOf(entry.getKey()));
			bouncedOrderVO.setAuditMsg(remark);
			chargeBackOrders.add(bouncedOrderVO);
			bouncedFlowVO.setOperator(operator);
			bouncedFlowVO.setCreateDate(new Date());
			bouncedFlowVO.setOrderId(entry.getKey());
			bouncedFlowVO.setStatus(status0);
			bouncedFlowVO.setRemark(remark);
			bouncedFlowVOs.add(bouncedFlowVO);
		}

		Map resultMap = chargeBackService.updateChargeBackOrder(chargeBackOrders, "true");
		chargeBackService.updateBouncedFlow(bouncedFlowVOs);
		return new ModelAndView(urlMap.get("bouncedApproveList")).addObject("message", "拒付订单处理成功！");

	}

	/**
	 * 拒付处理
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws UnsupportedEncodingException
	 *             2016年5月25日 mmzhang add
	 */
	public ModelAndView auditBatch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		List<ChargeBackOrder> chargeBackOrders = new ArrayList<ChargeBackOrder>();
		List<BouncedFlowVO> bouncedFlowVOs = new ArrayList<BouncedFlowVO>();
		String operator = SessionUserHolderUtil.getLoginId();
		String status = StringUtil.null2String(request.getParameter("status"));
		String orderId = StringUtil.null2String(request.getParameter("orderId"));
		String remark = StringUtil.null2String(request.getParameter("remark"));
		String bouncedType = StringUtil.null2String(request.getParameter("bouncedType"));
		ChargeBackOrder bouncedOrderVO = new ChargeBackOrder();
		BouncedFlowVO bouncedFlowVO = new BouncedFlowVO();
		bouncedOrderVO.setStatus(Integer.valueOf(status));
		bouncedOrderVO.setAuditOperator(operator);
		bouncedOrderVO.setAuditDate(new Date());
		bouncedOrderVO.setAuditMsg(remark);
		bouncedOrderVO.setOrderId(Long.valueOf(orderId));

		if (!StringUtil.isEmpty(bouncedType)) {
			bouncedOrderVO.setCpType(Integer.valueOf(bouncedType));
		}
		if (!StringUtil.isEmpty(status)) {
			bouncedOrderVO.setStatus(Integer.valueOf(status));
		}
		chargeBackOrders.add(bouncedOrderVO);
		bouncedFlowVO.setOperator(operator);
		bouncedFlowVO.setCreateDate(new Date());
		bouncedFlowVO.setOrderId(orderId);
		bouncedFlowVO.setStatus(status);
		bouncedFlowVO.setRemark(remark);
		bouncedFlowVOs.add(bouncedFlowVO);
		Map resultMap = chargeBackService.updateChargeBackOrder(chargeBackOrders, null);
		chargeBackService.updateBouncedFlow(bouncedFlowVOs);
		return new ModelAndView(urlMap.get("bouncedQueryList")).addObject("message", "拒付订单处理成功！");

	}

	/**
	 * 复核审批
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws UnsupportedEncodingException
	 *             2016年5月25日 mmzhang add
	 */
	public ModelAndView auditBatchApp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		List<ChargeBackOrder> chargeBackOrders = new ArrayList<ChargeBackOrder>();
		List<BouncedFlowVO> bouncedFlowVOs = new ArrayList<BouncedFlowVO>();
		String operator = SessionUserHolderUtil.getLoginId();
		String status = StringUtil.null2String(request.getParameter("status"));
		String orderId = StringUtil.null2String(request.getParameter("orderId"));
		String gcFlag = StringUtil.null2String(request.getParameter("gcFlag"));
		String remark = StringUtil.null2String(request.getParameter("remark"));
		String bouncedType = StringUtil.null2String(request.getParameter("bouncedType"));
		ChargeBackOrder bouncedOrderVO = new ChargeBackOrder();
		BouncedFlowVO bouncedFlowVO = new BouncedFlowVO();
		bouncedOrderVO.setStatus(Integer.valueOf(status));
		bouncedOrderVO.setAuditOperator(operator);
		bouncedOrderVO.setAuditDate(new Date());
		bouncedOrderVO.setAuditMsg(remark);
		bouncedOrderVO.setOrderId(Long.valueOf(orderId));

		// 如果拒付申诉失败，直接扣款
		if (BouncedEnum.statusAudit5.getType().equals(status)
				&& (BouncedEnum.bouncedType0.getType().equals(bouncedType) || 
						(BouncedEnum.gcflag8.getType().equals(gcFlag) && BouncedEnum.bouncedType1
								.getType().equals(bouncedType)))) {

			bouncedOrderVO.setAmountType(BouncedEnum.amountType7.getType());

			// 如果调单申诉失败，继续保持冻结

			// 如果申诉成功，解冻
		} else if (BouncedEnum.statusAudit6.getType().equals(status)) {

			bouncedOrderVO.setAmountType(BouncedEnum.amountType5.getType());
		}

		if (!StringUtil.isEmpty(bouncedType)) {
			bouncedOrderVO.setCpType(Integer.valueOf(bouncedType));
		}
		if (!StringUtil.isEmpty(status)) {
			bouncedOrderVO.setStatus(Integer.valueOf(status));
		}
		chargeBackOrders.add(bouncedOrderVO);
		bouncedFlowVO.setOperator(operator);
		bouncedFlowVO.setCreateDate(new Date());
		bouncedFlowVO.setOrderId(orderId);
		bouncedFlowVO.setStatus(status);
		bouncedFlowVO.setRemark(remark);
		bouncedFlowVOs.add(bouncedFlowVO);
		Map resultMap = chargeBackService.updateChargeBackOrder(chargeBackOrders, "true");
		chargeBackService.updateBouncedFlow(bouncedFlowVOs);
		return new ModelAndView(urlMap.get("bouncedApproveList")).addObject("message", "拒付订单处理成功！");

	}

	/**
	 * 拒付处理审核
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 *             2016年5月12日 mmzhang add
	 * @throws UnsupportedEncodingException
	 */
	public ModelAndView bouncedApproveList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Calendar time = Calendar.getInstance();
		Date todayDate = time.getTime();

		String batchNo = request.getParameter("batchNo");
		String partnerId = request.getParameter("partnerId");
		String channelOrderNo = StringUtil.null2String(request.getParameter("channelOrderNo"))
				.trim();
		String refNo = StringUtil.null2String(request.getParameter("refNo"));
		String orderNo = StringUtil.null2String(request.getParameter("orderNo"));
		String orgCode = StringUtil.null2String(request.getParameter("orgCode"));
		String bouncedType = StringUtil.null2String(request.getParameter("bouncedType"));
		String status = StringUtil.null2String(request.getParameter("status"));
		String beginCreateDate = request.getParameter("beginCreateDate");
		String endCreateDate = request.getParameter("endCreateDate");
		String orderId = StringUtil.null2String(request.getParameter("orderId"));
		BouncedOrderVO bouncedOrderVO = new BouncedOrderVO();
		bouncedOrderVO.setBatchNo(batchNo);
		bouncedOrderVO.setMemberCode(partnerId);
		if (!StringUtil.isEmpty(channelOrderNo)) {
			bouncedOrderVO.setChannelOrderId(Long.valueOf(channelOrderNo));
		}
		bouncedOrderVO.setRefNo(refNo);
		bouncedOrderVO.setOrgCode(orgCode);
		if (!StringUtil.isEmpty(orderId)) {
			bouncedOrderVO.setOrderId(Long.valueOf(orderId));
		}
		
		if (!StringUtil.isEmpty(bouncedType)) {
			bouncedOrderVO.setCpType(Integer.valueOf(bouncedType));
		}
		if (!StringUtil.isEmpty(status)) {
			bouncedOrderVO.setStatus(Integer.valueOf(status));
		}
		bouncedOrderVO.setOrderNo(orderNo);
		bouncedOrderVO.setCreateBeginTime(beginCreateDate);
		bouncedOrderVO.setCreateEndTime(endCreateDate);
		bouncedOrderVO.setStatusIn("3,4");
		Page page = PageUtils.getPage(request);
		Map resultMap = chargeBackService.queryBouncedOrder(bouncedOrderVO, page);
		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		List<BouncedOrderVO> bouncedOrderVOs = (List<BouncedOrderVO>) resultMap.get("result");
		return new ModelAndView(urlMap.get("bouncedApproveList"))
				.addObject("result", bouncedOrderVOs).addObject("todayDate", todayDate)
				.addObject("page", page);

	}

	/**
	 * 录入数据库的银行拒付金额≤银行拒付可录入金额=（可拒付金额/交易金额）*支付金额/(交易当日银行拒付币种对支付币种的交易汇率)
	 * 若银行拒付币种=支付币种，交易当日银行拒付币种对支付币种的交易汇率=1)
	 * 0≤银行拒付金额≤银行拒付可录入金额。若填入数值不在该区间，失去焦点时清空输入框
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public ModelAndView setMaxBankAmount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bankCurrencyCode = request.getParameter("bankCurrencyCode");
		String orderAmount = request.getParameter("orderAmount");
		String canBouncedAmount = request.getParameter("canBouncedAmount");
		String payAmount = request.getParameter("payAmount");
		String transferRate = request.getParameter("transferRate");
		String transferCurrencyCode = request.getParameter("transferCurrencyCode");
		Date createDate = DateUtil.string2Timestamp(request.getParameter("stranDate"));
		String cardOrg = request.getParameter("cardOrg");
		String partnerId = request.getParameter("partnerId");
		String orgCode = request.getParameter("orgCode");
		String screateDate = request.getParameter("stranDate");
		String cardholderCardno = request.getParameter("cardholderCardno");
		String floatValue = request.getParameter("floatValue");

		if (StringUtil.isEmpty(orderAmount)) {
			throw new BusinessException("订单金额不能为0！");
		} else {
			orderAmount = orderAmount.replaceAll(",", "");
		}
		if (!StringUtil.isEmpty(canBouncedAmount)) {
			canBouncedAmount = canBouncedAmount.replaceAll(",", "");
		} else {
			canBouncedAmount = "0";
		}
		if (!StringUtil.isEmpty(payAmount)) {
			payAmount = payAmount.replaceAll(",", "");
		} else {
			payAmount = "0";
		}

		logger.info("orgCode" + orgCode + "sourceCurrency" + bankCurrencyCode + "targetCurrency"
				+ "orderAmount" + orderAmount + "canBouncedAmount" + canBouncedAmount
				+ "createDate" + createDate);
		// add by Mack to control if ignore markup.1=ignore
		String isIgnoreMarkup = "1";

		BigDecimal rate = getTrantionRate(bankCurrencyCode, orderAmount, transferCurrencyCode,
				createDate, cardOrg, partnerId, screateDate, cardholderCardno, floatValue,
				isIgnoreMarkup);
		if (null == rate) {
			response.getWriter().write("");
		} else {
			BigDecimal bankAmount = new BigDecimal(canBouncedAmount)
					.divide(new BigDecimal(orderAmount), 9, BigDecimal.ROUND_HALF_UP)
					.multiply(new BigDecimal(payAmount)).divide(rate, 2, BigDecimal.ROUND_HALF_UP)
					.setScale(2, BigDecimal.ROUND_HALF_UP);
			BouncedResultDTO bouncedvo = new BouncedResultDTO();
			bouncedvo.setSbankAmount(bankAmount.toString());
			bouncedvo.setRate(rate.toString());
			response.getWriter().write(JSonUtil.toJSonString(bouncedvo));
		}
		return null;
	}

	/**
	 * 求拒付币种到支付币种的交易汇率
	 * 
	 * @param bankCurrencyCode
	 * @param orderAmount
	 * @param transferCurrencyCode
	 * @param createDate
	 * @param cardOrg
	 * @param partnerId
	 * @param screateDate
	 * @return 2016年5月30日 mmzhang add
	 */
	private BigDecimal getTrantionRate(String bankCurrencyCode, String orderAmount,
			String transferCurrencyCode, Date createDate, String cardOrg, String partnerId,
			String screateDate, String cardNo, String floatValue, String isIgnoreMarkup) {
		BigDecimal rate = BigDecimal.ONE;

		if (!bankCurrencyCode.equals(transferCurrencyCode)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sourceCurrency", bankCurrencyCode);
			param.put("targetCurrency", transferCurrencyCode);
			// status为1是当天的汇率，置为有效，取某一天的这字段不传，传交易日期
			param.put("memberCode", partnerId);
			if (!StringUtil.isEmpty(cardOrg)) {
				param.put("cardOrg", cardOrg);
			}
			param.put("orderAmount", orderAmount);
			param.put("ltaCurrencyCode", "USD");
			param.put("point", getTime(createDate));
			param.put("currentDate", screateDate);
			param.put("cardNo", cardNo);
			param.put("isIgnoreMarkup", isIgnoreMarkup);// add by Mack to
														// control ignore markup
			if (null == floatValue || "".equals(floatValue)) {
				floatValue = "0";
			}

			Map<String, Object> map = bouncedQueryService.transactionRateBounced(param);
			Map transactionRate = (Map) map.get("transactionRate");
			if (null == transactionRate) {
				logger.info("未找到对应交易汇率，请确认是否配置了对应的汇率.....sourceCurrencyCode: " + bankCurrencyCode
						+ " ,targetCurrencyCode: " + transferCurrencyCode);
				rate = null;

			} else {
				BigDecimal rate0 = new BigDecimal(transactionRate.get("exchangeRate").toString());
				rate = rate0.add(rate0.multiply(new BigDecimal(floatValue)));
				logger.info("找到对应交易汇率为rate=" + rate + "，请确认是否配置了对应的汇率.....sourceCurrencyCode: "
						+ bankCurrencyCode + " ,targetCurrencyCode: " + transferCurrencyCode
						+ " ,floatValue: " + floatValue);
			}
		}
		return rate;
	}

	/**
	 * 拒付登记保存
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 *             2016年5月16日 mmzhang addg
	 */
	public ModelAndView save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String tradeOrderNo = StringUtil.null2String(request.getParameter("tradeOrderNo")).trim();
		String channelOrderNo = StringUtil.null2String(request.getParameter("channelOrderNo"))
				.trim();
		String refNo = StringUtil.null2String(request.getParameter("refNo"));
		Date tranDate = DateUtil.string2Timestamp(request.getParameter("tranDate"));
		String partnerId = StringUtil.null2String(request.getParameter("partnerId"));
		String batchNo = StringUtil.null2String(request.getParameter("batchNo"));
		String cardholderCardno = StringUtil.null2String(request.getParameter("cardholderCardno"));
		String authorisation = StringUtil.null2String(request.getParameter("authorisation"));
		String cpdDate = StringUtil.null2String(request.getParameter("cpdDate"));
		String canBouncedAmount = StringUtil.null2StringAmount(request
				.getParameter("canBouncedAmount"));
		String doingBouncedAmount = StringUtil.null2StringAmount(request
				.getParameter("doingBouncedAmount"));
		String bankCurrencyCode = StringUtil.null2String(request.getParameter("bankCurrencyCode"));
		String transferCurrencyCode = StringUtil.null2String(request
				.getParameter("transferCurrencyCode"));
		String bankAmount = StringUtil.null2StringAmount(request.getParameter("bankAmount"));
		String maxAmount = StringUtil.null2StringAmount(request.getParameter("maxAmount"));
		String visableCode = StringUtil.null2String(request.getParameter("visableCode"));
		String reasonCode = StringUtil.null2String(request.getParameter("reasonCode"));

		Date lastDate = DateUtil.string2Timestamp(request.getParameter("lastDate"),
				DateUtil.SIMPLE_DATE_FROMAT);
		String bouncedType = StringUtil.null2String(request.getParameter("bouncedType"));
		String remark = StringUtil.null2String(request.getParameter("remark"));
		String orgCode = StringUtil.null2String(request.getParameter("orgCode"));
		String orderAmount = StringUtil.null2StringAmount(request.getParameter("orderAmount"));
		String payAmount = StringUtil.null2StringAmount(request.getParameter("payAmount"));
		String cpFalg = StringUtil.null2String(request.getParameter("cpFalg"));
		String assureSettlementFlg = StringUtil.null2String(request
				.getParameter("assureSettlementFlg"));
		String settlementFlg = StringUtil.null2String(request.getParameter("settlementFlg"));
		String settlementCurrencyCode = StringUtil.null2String(request
				.getParameter("settlementCurrencyCode"));
		String settlementRate = StringUtil.null2String(request.getParameter("settlementRate"));
		String orderId = StringUtil.null2String(request.getParameter("orderId"));
		String currencyCode = StringUtil.null2String(request.getParameter("currencyCode"));
		String tranRate = StringUtil.null2String(request.getParameter("stransferRate"));
		String floatValue = StringUtil.null2StringAmount(request.getParameter("floatValue"));
		String overBouncedAmount = StringUtil.null2StringAmount(request
				.getParameter("overBouncedAmount"));
		String operator = SessionUserHolderUtil.getLoginId();
		String merchantCode = StringUtil.null2String(request.getParameter("merchantCode"));
		String bouncedRemark = StringUtil.null2String(request.getParameter("bouncedRemark"));
		String checkFlag = StringUtil.null2String(request.getParameter("checkFlag"));
		if (StringUtils.isNotEmpty(batchNo)) {
			Map paraMap1 = new HashMap();
			paraMap1.put("batchNo", batchNo);
			Map resultMap = bouncedQueryService.bouncedResultQuery(paraMap1);
			List<Map> map1 = (List<Map>) resultMap.get("bouncedResults");
			if (map1 == null || map1.isEmpty()) {
				return new ModelAndView(urlMap.get("scuess")).addObject("message",
						"保存拒付订单失败批次号不存在！！");
			}
		}

		if(BouncedEnum.checkflag1.getType().equals(checkFlag)&& (BouncedEnum.bouncedType1.getType().equals(bouncedType) || BouncedEnum.bouncedType2.getType().equals(bouncedType) ))
		{
			return new ModelAndView(urlMap.get("scuess")).addObject("message",
					"不能重复调单，该订单已做过调单，订单号="+bouncedRemark);
		}
		
		// 保存拒付登记结果表
		List<BouncedResultDTO> resultdtoList = new ArrayList<BouncedResultDTO>();
		BouncedResultDTO resultdto = new BouncedResultDTO();
		if (null == batchNo || "".equals(batchNo)) {
			batchNo = concatBatchNo(orgCode, bouncedType).concat("01");
		}
		resultdto.setBatchNo(batchNo);
		resultdto.setTradeOrderNo(tradeOrderNo);
		resultdto.setChannelOrderNo(channelOrderNo);
		resultdto.setRefNo(refNo);
		resultdto.setAuthorisation(authorisation);
		resultdto.setCardholderCardno(cardholderCardno);
		resultdto.setTranDate(tranDate);
		resultdto.setBouncedType(bouncedType);
		resultdto.setReasonCode(reasonCode);
		resultdto.setVisableCode(visableCode);
		resultdto.setOrgCode(orgCode);
		resultdto.setCpdDate(cpdDate);
		resultdto.setOperator(operator);
		resultdto.setBankAmount(new BigDecimal(bankAmount).multiply(new BigDecimal("1000"))
				.setScale(0, BigDecimal.ROUND_HALF_UP));
		resultdto.setBankCurrencyCode(bankCurrencyCode);
		resultdto.setCreateDate(new Date());
		Date lastDate3 = getLastDate(lastDate);
		resultdto.setLastDate(lastDate3);
		resultdto.setSorderAmount(orderAmount);
		resultdto.setScanBouncedAmount(canBouncedAmount);
		resultdto.setSdoingBouncedAmount(doingBouncedAmount);
		resultdto.setSettlementRate(new BigDecimal(settlementRate));
		resultdto.setSettlementCurrencyCode(settlementCurrencyCode);
		resultdto.setRegisterFlag(BouncedEnum.registerFlag1.getType());
		resultdto.setOrderId(orderId);
		resultdto.setCurrencyCode(currencyCode);
		resultdto.setFloatValue(floatValue);
		resultdto.setPartnerId(partnerId);
		resultdto.setSettlementFlg(settlementFlg);
		resultdto.setStatus(BouncedEnum.status1.getType());
		resultdto.setAssureSettlementFlg(assureSettlementFlg);
		resultdto.setMerchantCode(merchantCode);

		// 重新查询网关订单金额
		Map paraMap = new HashMap();

		paraMap.put("channelOrderNo", channelOrderNo);

		List<Map> map1 = bouncedQueryService.bouncedQuery(paraMap);

		if (map1 != null && map1.get(0) != null) {
			Map vo = map1.get(0);
			overBouncedAmount = getMapValueByAmount(vo, "overBouncedAmount");
			doingBouncedAmount = getMapValueByAmount(vo, "doingBouncedAmount");
		}

		// 银行来的数据是保留两位小数，这里的payAmount是三位小数，要保留两位
		BigDecimal payAmount2 = new BigDecimal(payAmount).divide(new BigDecimal("1000"), 3,
				BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("1000"));
		// 获取拒付比例
		BigDecimal bouncedRate = resultdto.getBankAmount().multiply(new BigDecimal(tranRate))
				.divide(payAmount2, 7, BigDecimal.ROUND_HALF_UP);
		logger.info("计算拒付比例：bouncedRate:" + bouncedRate + ",交易汇率tranRate:" + tranRate
				+ ",支付金额payAmount2:" + payAmount2);
		if (bouncedRate.compareTo(BigDecimal.ONE) == 1) {
			bouncedRate = BigDecimal.ONE;
		}
		if (bouncedRate.compareTo(new BigDecimal("0.999")) == 1) {
			bouncedRate = BigDecimal.ONE;
		}

		// 拒付金额币种为交易币种
		BigDecimal bankAmount0 = new BigDecimal(orderAmount).multiply(bouncedRate).divide(
				new BigDecimal("1000"), 4, BigDecimal.ROUND_HALF_UP);

		BigDecimal totalAmount = new BigDecimal(overBouncedAmount).add(
				new BigDecimal(doingBouncedAmount)).add(
				bankAmount0.multiply(new BigDecimal("1000")));

		// 如果拒付判断金额 已拒付金额+拒付中金额+拒付金额>交易金额
		if (BouncedEnum.bouncedType0.getType().equals(bouncedType)
				|| (BouncedEnum.gcflag8.getType().equals(merchantCode) && BouncedEnum.bouncedType1
						.getType().equals(bouncedType))) {
			if (totalAmount.longValue() - ((new BigDecimal(orderAmount)).longValue()) > 1) {
				return new ModelAndView(urlMap.get("scuess")).addObject(
						"message",
						"已拒付金额+拒付中金额+拒付金额大于交易金额【"
								+ totalAmount.divide(new BigDecimal("1000"), 3,
										BigDecimal.ROUND_HALF_UP)
								+ ">"
								+ (new BigDecimal(orderAmount)).divide(new BigDecimal("1000"), 3,
										BigDecimal.ROUND_HALF_UP) + "】;不能拒付!");
			}
		}
		if (bankAmount0.multiply(new BigDecimal("1000")).longValue() < (new BigDecimal(orderAmount))
				.longValue()) {
			cpFalg = "2";
		} else {
			cpFalg = "1";
		}

		resultdtoList.add(resultdto);
		logger.info("保存拒付登记结果开始：tradeOrderNo:" + tradeOrderNo + ",bouncedType:" + bouncedType
				+ ",lastDate:" + lastDate + ",canBouncedAmount:" + canBouncedAmount + ",batchNo:"
				+ batchNo + ",bankCurrencyCode:" + bankCurrencyCode + ",transferCurrencyCode:"
				+ transferCurrencyCode + ",reasonCode:" + reasonCode + ",doingBouncedAmount"
				+ doingBouncedAmount + ",overBouncedAmount" + overBouncedAmount + ",orderAmount"
				+ orderAmount + ",bouncedRate" + bouncedRate);
		Map<String, Object> map = bouncedQueryService.addBouncedResult(resultdtoList);
		
		//获取数据主键
		Long bouncedkey=bouncedQueryService.bouncedKeyQuery();
		// 登记拒付订单
		// cpFalg：1-全额，2-部分
		List<BouncedOrderVO> chargeBackOrders = new ArrayList<BouncedOrderVO>();
		BouncedOrderVO chargeBackOrder = buildBouncedOrder(bouncedkey,tradeOrderNo, channelOrderNo, refNo,
				tranDate, partnerId, batchNo, cardholderCardno, authorisation, cpdDate,
				canBouncedAmount, bankCurrencyCode, transferCurrencyCode, bankAmount0.toString(),
				visableCode, reasonCode, bouncedType, remark, orgCode, orderAmount, payAmount,
				cpFalg, operator, lastDate3, assureSettlementFlg, settlementCurrencyCode,
				settlementRate, orderId, currencyCode, tranRate, bouncedRate,
				resultdto.getBankAmount(), floatValue, merchantCode);

		if (BouncedEnum.bouncedType0.getType().equals(bouncedType)
				|| (BouncedEnum.gcflag8.getType().equals(merchantCode) && BouncedEnum.bouncedType1
						.getType().equals(bouncedType))) {
			logger.info("拒付单项登记时开始更新拒付中金额；商户订单号为" + orderId + ",tradeOrderNo=" + tradeOrderNo);
			// 更新网关订单
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("tradeOrderNo", tradeOrderNo);
			paramMap.put(
					"doingBouncedAmount",
					new BigDecimal(doingBouncedAmount)
							.add(bankAmount0.multiply(new BigDecimal("1000")))
							.setScale(0, BigDecimal.ROUND_HALF_UP).toString());
			if(BouncedEnum.bouncedType1.getType().equals(bouncedType))
			{
				paramMap.put("checkFlag", "1");
				paramMap.put("bouncedRemark", bouncedkey.toString());
			}
			bouncedQueryService.updateTradeOrder(paramMap);
			logger.info("拒付单项登记时结束；商户订单号为" + orderId + ",tradeOrderNo=" + tradeOrderNo
					+ ",merchantCode=" + merchantCode);
		}else if(BouncedEnum.bouncedType1.getType().equals(bouncedType) || BouncedEnum.bouncedType2.getType().equals(bouncedType))
		{
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("tradeOrderNo", tradeOrderNo);
			paramMap.put("checkFlag", "1");
			paramMap.put("bouncedRemark", bouncedkey.toString());
			bouncedQueryService.updateTradeOrder(paramMap);
			logger.info("拒付单项登记时结束;更新调单标志为1（已调单）");
			
		}
		chargeBackOrders.add(chargeBackOrder);
		logger.info("保存拒付订单开始：tradeOrderNo:" + tradeOrderNo + ",bouncedType:" + bouncedType
				+ ",lastDate:" + lastDate3 + ",canBouncedAmount:" + canBouncedAmount + ",batchNo:"
				+ batchNo + ",bankCurrencyCode:" + bankCurrencyCode + ",transferCurrencyCode:"
				+ transferCurrencyCode + ",reasonCode:" + reasonCode);
		Map<String, String> resultMap = chargeBackService.addChargeBackOrderNew(chargeBackOrders);

		String resultCode = resultMap.get("responseCode");
		String resultMsg = resultMap.get("responseDesc");
		logger.info("保存拒付订单结束：tradeOrderNo:" + tradeOrderNo + "resultMsg:" + resultMsg);
		return new ModelAndView(urlMap.get("scuess")).addObject("message", "保存拒付订单成功");
	}

	private Date getLastDate(Date lastDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(lastDate);
		c.set(Calendar.HOUR, 18);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date lastDate2 = c.getTime();
		Date lastDate3 = DateUtil.string2Timestamp(DateUtil.formatDateTime(
				DateUtil.DEFAULT_DATE_FROMAT, lastDate2));
		return lastDate3;
	}

	private BouncedOrderVO buildBouncedOrder(Long bouncedkey,String tradeOrderNo, String channelOrderNo,
			String refNo, Date tranDate, String partnerId, String batchNo, String cardholderCardno,
			String authorisation, String cpdDate, String canBouncedAmount, String bankCurrencyCode,
			String transferCurrencyCode, String bankAmount, String visableCode, String reasonCode,
			String bouncedType, String remark, String orgCode, String orderAmount,
			String payAmount, String cpFalg, String operator, Date lastDate3,
			String assureSettlementFlg, String settlementCurrencyCode, String settlementRate,
			String orderNo, String currencyCode, String tranRate, BigDecimal bouncedRate,
			BigDecimal bouncedAmount, String floatValue, String merchantCode) {
		// cpFalg：1-全额，2-部分
		BouncedOrderVO chargeBackOrder = new BouncedOrderVO();
		chargeBackOrder.setOrderId(bouncedkey);
		chargeBackOrder.setTradeOrderNo(Long.valueOf(tradeOrderNo));
		chargeBackOrder.setChannelOrderId(Long.valueOf(channelOrderNo));
		chargeBackOrder.setMemberCode(partnerId);
		chargeBackOrder.setRefNo(refNo);
		chargeBackOrder.setOperator(operator);
		chargeBackOrder.setCpType(Integer.valueOf(bouncedType));
		// 拒付金额
		BigDecimal bankAmount1000 = new BigDecimal(bankAmount).multiply(new BigDecimal("1000"))
				.setScale(0, BigDecimal.ROUND_HALF_UP);
		chargeBackOrder.setChargeBackAmount(bankAmount1000.toString());
		chargeBackOrder.setCpCurrencyCode(bankCurrencyCode);
		chargeBackOrder.setLatestAnswerDate(lastDate3);
		chargeBackOrder.setCpdDate(cpdDate);
		chargeBackOrder.setTradeDate(tranDate);
		chargeBackOrder.setCreateDate(new Date());
		chargeBackOrder.setReasonCode(reasonCode);
		chargeBackOrder.setVisableCode(visableCode);
		chargeBackOrder.setOrgCode(orgCode);
		// 支付币种
		chargeBackOrder.setCurrencyCode(transferCurrencyCode);
		chargeBackOrder.setTradeAmount(new BigDecimal(orderAmount));
		chargeBackOrder.setPayAmount(new BigDecimal(payAmount));
		chargeBackOrder.setCardNo(cardholderCardno);
		chargeBackOrder.setAuthorisation(authorisation);
		chargeBackOrder.setCpFlg(Integer.valueOf(cpFalg));
		chargeBackOrder.setBatchNo(batchNo);
		chargeBackOrder.setRemark(remark);
		chargeBackOrder.setStatus(0);
		chargeBackOrder.setSettlementCurrencyCode(settlementCurrencyCode);

		// 交易汇率
		chargeBackOrder.setTransRate(tranRate);
		// 清算汇率
		chargeBackOrder.setSettlementRate(settlementRate);
		// 拒付比例
		chargeBackOrder.setBouncedRate(bouncedRate);
		// 清算汇率
		chargeBackOrder.setSettlementRate(settlementRate);
		// 拒付金额
		chargeBackOrder.setBouncedAmount(bouncedAmount);
		// 交易币种
		chargeBackOrder.setTranCurrencyCode(currencyCode);
		// 商户订单号
		chargeBackOrder.setOrderNo(orderNo);
		// 浮动点数
		chargeBackOrder.setFloatValue(floatValue);
		chargeBackOrder.setAccountingFlg(0);
		chargeBackOrder.setMerchantCode(merchantCode);
		chargeBackOrder.setGcFlag(merchantCode);

		/**
		 * 查询保证金比例
		 */
		EnterpriseBaseDto enterpriseBaseDto = enterpriseBaseService
				.queryEnterpriseBaseByMemberCode(Long.valueOf(partnerId));

		Integer assurePercentage = enterpriseBaseDto.getAssurePercentage();

		// 根据拒付类型，拒付金额类型判断拒付的记账逻辑
		possAccountByBouncedType(channelOrderNo, bankAmount1000.toString(), visableCode,
				bouncedType, cpFalg, chargeBackOrder, assurePercentage, assureSettlementFlg,
				settlementRate,merchantCode);

		return chargeBackOrder;
	}

	/**
	 * 批次号命名规则：M_+录入时间 +文件类型+渠道代码+序号 录入时间：年月日，例：20160512 文件类型：拒付单J,调查单D,内部调单N
	 * 渠道代码：渠道的数字代码 序号：00-99 拒付类型”字段,0拒付1调单2内部调单
	 * 
	 * @param orgCode
	 * @return 2016年5月16日 mmzhang add
	 */
	private String concatBatchNo(String orgCode, String bouncedType) {
		String flag = "";
		if (BouncedEnum.bouncedType0.getType().equals(bouncedType)) {
			flag = "J";
		} else if (BouncedEnum.bouncedType1.getType().equals(bouncedType)) {
			flag = "D";
		} else {
			flag = "N";
		}
		String batchNo;
		batchNo = "M_".concat(DateUtil.dateToStr(new Date(), DateUtil.PATTERN)).concat(flag)
				.concat(orgCode);
		return batchNo;
	}

	private static String setStatus() {
		batchstatus = "1";
		return batchstatus;
	}

	private static String resetStatus() {
		batchstatus = "0";
		return batchstatus;
	}

	private void  setbatchStatusMethod() {
		Map<String, Object> batchMap1= new HashMap<String, Object>();
		batchMap1.put("batchType", "bounced");
		batchMap1.put("status", BouncedEnum.batchStatus1.getType());
		this.bouncedQueryService
				.batchStatusUpdate(batchMap1);
	}
	/**
	 * 任务处理完成更新
	 */
	private void resetbatchStatusMethod() {
		// 任务处理完成更新
		Map<String, Object> paraMap2 = new HashMap<String, Object>();
		paraMap2.put("batchType", "bounced");
		paraMap2.put("status", BouncedEnum.batchStatus0.getType());
		this.bouncedQueryService.batchStatusUpdate(paraMap2);
	}
	
	/**
	 * 拒付文件解析方法，把拒付文件解析成后，插入到bounced_result
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             2016年7月20日 mmzhang add
	 */
	public synchronized ModelAndView submitUploade(final HttpServletRequest request,
			final HttpServletResponse response)  {
		try {
			Map<String, Object> batchMap0 = new HashMap<String, Object>();
			batchMap0.put("batchType", "bounced");
			Map result0 = this.bouncedQueryService
					.batchStatusQuery(batchMap0);
			String status = result0.get("status").toString();
			
			//如果批量状态为0，可确认申请
			if (status.equals(BouncedEnum.batchStatus0.getType())) {
				setbatchStatusMethod();

			// 取导入文件公共数据：拒付类型、拒付时间、渠道号、最晚回复日期
			Page page = PageUtils.getPage(request);
			
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			Calendar time = Calendar.getInstance();
			Date todayDate = time.getTime();
			String bouncedType = StringUtil.null2String(request.getParameter("bouncedType"));
			Date lastDate = DateUtil.string2Timestamp(request.getParameter("lastDate"),
					DateUtil.SIMPLE_DATE_FROMAT);
			String cpdDate = StringUtil.null2String(request.getParameter("cpdDate"));
			String orgCode = StringUtil.null2String(request.getParameter("orgCode"));
			Date lastDate3 = getLastDate(lastDate);

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
			String operator = SessionUserHolderUtil.getLoginId();

			FileParseResult fileParseResult = fileService.parserFileInfo(file.getInputStream(),
					file.getName(), "BOUNCED_".concat(orgCode));
			if (null == fileParseResult) {
				logger.error("解析拒付文件报错," + orgCode);
				resetbatchStatusMethod();
				return new ModelAndView(urlMap.get("batchscuess")).addObject("message",
						"解析拒付文件报错，无法获取解析器或格式文件错误！");
			}
			List<BouncedResultDTO> list = fileParseResult.getList();
			if (null == list || list.isEmpty()) {
				resetbatchStatusMethod();
				return new ModelAndView(urlMap.get("batchscuess")).addObject("message", "没有合法的记录");
			}

			// 批次号的生成
			int conunt = getBatchNo(bouncedType, orgCode);
			String batchNo = concatBatchNo(orgCode, bouncedType);
			if (conunt < 10) {
				batchNo = batchNo.concat("0" + conunt);
			} else {
				batchNo = batchNo.concat("" + conunt);
			}

			// 对解析好的导入文件进行检查，不满足条件，现在异常页面
			// 对解析出的每笔拒付数据进行匹配
			boolean parseFalg = true;
			for (BouncedResultDTO order : list) {
				order.setBatchNo(batchNo);
				order.setOperator(operator);
				order.setRegisterFlag(BouncedEnum.registerFlag0.getType());
				order.setOrgCode(orgCode);
				if (order.getStranDate().length() > 8) {
					order.setTranDate(new SimpleDateFormat("yyyy-MM-dd").parse(order.getStranDate()));
				} else {
					String yyyy = order.getStranDate().substring(0, 4);
					String mm = order.getStranDate().substring(4, 6);
					String dd = order.getStranDate().substring(6, 8);
					Date datef = new SimpleDateFormat("yyyyMMdd").parse(order.getStranDate());
					order.setTranDate(datef);
					order.setStranDate(yyyy.concat("-").concat(mm).concat("-").concat(dd));
				}
				order.setCreateDate(new Date());
				order.setAuthorisation(order.getAuthorisation());
				order.setBouncedType(bouncedType);
				order.setLastDate(lastDate3);
				if (order.getSorderAmount() != null) {
					order.setOrderAmount(new BigDecimal(order.getSorderAmount()).multiply(
							new BigDecimal("1000")).setScale(2));
				}
				order.setBankAmount(new BigDecimal(order.getSbankAmount()).multiply(
						new BigDecimal("1000")).setScale(2));
				order.setCpdDate(cpdDate);

				if ("10076001".equals(orgCode) || "10002001".equals(orgCode) || "10078001".equals(orgCode)) {
					order.setBankCurrencyCode("CNY");
				}
				order.setRegisterFlag(BouncedEnum.registerFlag0.getType());
				String cardNo = order.getCardholderCardno();
				// 如果卡号有星号替换，则用模糊查询
				if (cardNo.indexOf("*") != -1) {
					cardNo = cardNo.replaceAll("\\*", "_");
					order.setCardholderCardno(cardNo);
				}
				// 拒付文件解析检查 CREDORAX要检测渠道订单号
				checkBouncedResult(parseFalg, order, orgCode);

			}
			// 如果解析文件失败，跳转到解析错误界面
			if (!parseFalg) {
				resetbatchStatusMethod();
				return new ModelAndView(urlMap.get("error")).addObject("list", list);
			} else {
				// 导入结果的统计
				int status0Count = 0;
				int status1Count = 0;
				int status2Count = 0;
				int status3Count = 0;
				int status4Count = 0;
				int status5Count = 0;
				// 判断list长度,按500分组
				List<List<BouncedResultDTO>> groupList = groupListByQuantity(list, 500);
				//拒付登记结果表
				List<BouncedResultDTO> listall = new ArrayList<BouncedResultDTO>();
				//先把临时结果表的数据删除
				Map params = new HashMap();
				bouncedQueryService.deleteBouncedTemp(params);
				if (groupList != null && groupList.size() > 0) {
					int i = 0;
					for (List<BouncedResultDTO> list2 : groupList) {
						//拒付登记结果表
						List<BouncedResultDTO> listadd = new ArrayList<BouncedResultDTO>();
						// 批量登记的数据
						List<BouncedOrderVO> batchBouncedOrders = new ArrayList<BouncedOrderVO>();
					
						// 分组是批次号加1
						String batchNogroup = batchNo.concat("_" + i);
						for (BouncedResultDTO bouncedResultDTO : list2) {
							bouncedResultDTO.setBatchNo(batchNogroup);
						}

						// 拒付匹配结果保存
						if (list2 != null && !list2.isEmpty()) {
							bouncedQueryService.addBouncedResultTemp(list2);
							i++;
						}
						//数据插入完成释放内存
						for (BouncedResultDTO dto : list2) {
							dto=null;
						}
						// 对导入结果表的批量数据进行处理 1批量配对拒付数据2生存拒付订单
						Map paraMap = new HashMap();
						paraMap.put("batchNO", batchNogroup);
						List<Map> batchmaps = bouncedQueryService.batchBouncedQuery(paraMap);

						if (batchmaps != null && batchmaps.size() > 0) {
							// 对配对后的批量数据进行匹配状态更新
							batchProcess(batchmaps);
							BouncedResultDTO countvo= new BouncedResultDTO();
							countvo.setStatus0Count(status0Count);
							countvo.setStatus1Count(status1Count);
							countvo.setStatus2Count(status2Count);
							countvo.setStatus3Count(status3Count);
							countvo.setStatus4Count(status4Count);
							countvo.setStatus5Count(status5Count);
							batchBouncedBuild(listadd, batchNo,batchBouncedOrders,batchmaps, countvo,false);
							status0Count = countvo.getStatus0Count();
							status1Count = countvo.getStatus1Count();
							status2Count = countvo.getStatus2Count();
							status3Count = countvo.getStatus3Count();
							status4Count = countvo.getStatus4Count();
							status5Count = countvo.getStatus5Count();
						}
						// 拒付匹配结果保存
						if (!listadd.isEmpty()) {
							bouncedQueryService.addBouncedResult(listadd);
						}
						//拒付订单保存
						if (!batchBouncedOrders.isEmpty()) {
							chargeBackService.addChargeBackOrderNew(batchBouncedOrders);
						}
						//处理完成清除内存
						for (BouncedOrderVO vo : batchBouncedOrders) {
							vo=null;
						}
						for (Map map : batchmaps) {
							map=null;
						}
						listall.addAll(listadd);
					}
				}
				resetbatchStatusMethod();
				
				return new ModelAndView(urlMap.get("batchResult"))
				.addObject("todayDate", todayDate).addObject("page", listall)
				.addObject("status0Count", status0Count)
				.addObject("status1Count", status1Count)
				.addObject("status2Count", status2Count)
				.addObject("status3Count", status3Count)
				.addObject("status4Count", status4Count)
				.addObject("status5Count", status5Count);
			}
		}else{
			return new ModelAndView(urlMap.get("scuess")).addObject("message", "批量导入正在运行，不能重复提交");
		}
		} catch (Exception e) {
			resetbatchStatusMethod();
			e.printStackTrace();
			return new ModelAndView(urlMap.get("scuess")).addObject("message", "批量导入发生异常！");
		}

	}

	/**
	 * 
	 * @param listadd
	 * @param batchmaps
	 * @param status0Count
	 * @param status1Count
	 * @param status2Count
	 * @param status3Count
	 * @param status4Count
	 * @param status5Count
	 * @throws ParseException
	 * 2016年7月26日   mmzhang     add
	 */
	private void batchBouncedBuild(
			List<BouncedResultDTO> listadd,String batchNo, List<BouncedOrderVO> batchBouncedOrders,List<Map> batchmaps
			,BouncedResultDTO countvo,Boolean flag) throws ParseException {
		int status0Count = countvo.getStatus0Count();
		int status1Count = countvo.getStatus1Count();
		int status2Count = countvo.getStatus2Count();
		int status3Count = countvo.getStatus3Count();
		int status4Count = countvo.getStatus4Count();
		int status5Count = countvo.getStatus5Count();
		for (Map vo : batchmaps) {
			
				BouncedResultDTO order = MapUtil.map2Object(BouncedResultDTO.class,
						vo);
				if(flag)
				{
				batchNo=order.getBatchNo();
				}
				Date lastDate=order.getLastDate();
				String orgCode=order.getOrgCode();
				String bouncedType=order.getBouncedType();
				String cpdDate=order.getCpdDate();
				String operator=order.getOperator();
				Date lastDate3=order.getLastDate();
				
				String status = getMapValueByString(vo, "status");
				String registerFlag = getMapValueByString(vo, "registerFlag");

				if (BouncedEnum.status0.getType().equals(status)) {
					order.setBatchNo(batchNo);
					order.setOverRefundAmount(BigDecimal.ZERO); // 已退金额
					order.setDoingRefundAmount(BigDecimal.ZERO); // 退款中金额
					order.setOverBouncedAmount(BigDecimal.ZERO); // 已拒付金额
					order.setCanBouncedAmount(BigDecimal.ZERO); // 可拒付金额
					order.setCreateDate(new Date());
					order.setSettlementFlg(null);
					order.setAssureSettlementFlg(null);
					order.setStatus(status);
					order.setRegisterFlag(BouncedEnum.registerFlag2.getType());
					listadd.add(order);
					status0Count++;
					logger.info("保存拒付登记结果开始：tradeOrderNo:"
							+ order.getTradeOrderNo() + ",bouncedType:"
							+ order.getBouncedType() + ",lastDate:" + lastDate
							+ ",canBouncedAmount:" + order.getCanBouncedAmount()
							+ ",batchNo:" + batchNo + ",bankCurrencyCode:"
							+ order.getBankCurrencyCode()
							+ ",transferCurrencyCode:"
							+ order.getTransferCurrencyCode() + ",reasonCode:"
							+ order.getReasonCode());

				} else if (BouncedEnum.status3.getType().equals(status)) {
					order.setStatus(status);
					buildBouncedresultByMap(batchNo, vo, order);
					logger.info("保存拒付登记结果开始：tradeOrderNo:"
							+ order.getTradeOrderNo() + ",bouncedType:"
							+ order.getBouncedType() + ",lastDate:" + lastDate
							+ ",canBouncedAmount:" + order.getCanBouncedAmount()
							+ ",batchNo:" + batchNo + ",bankCurrencyCode:"
							+ order.getBankCurrencyCode()
							+ ",transferCurrencyCode:"
							+ order.getTransferCurrencyCode() + ",reasonCode:"
							+ order.getReasonCode());
					listadd.add(order);
					status3Count++;
				} else if(!BouncedEnum.registerFlag1.getType().equals(order.getRegisterFlag())){
					order.setStatus(BouncedEnum.status1.getType());
					order.setRegisterFlag(BouncedEnum.registerFlag1.getType());
					buildBouncedresultByMap(batchNo, vo, order);
					logger.info("更新拒付登记结果开始：tradeOrderNo:"
							+ order.getTradeOrderNo() + ",bouncedType:"
							+ order.getBouncedType() + ",lastDate:" + lastDate
							+ ",canBouncedAmount:"
							+ order.getCanBouncedAmount() + ",batchNo:"
							+ batchNo + ",bankCurrencyCode:"
							+ order.getBankCurrencyCode()
							+ ",transferCurrencyCode:"+ order.getTransferCurrencyCode()
							+ ",settlementFlg:"+ order.getSettlementFlg()
							+ ",checkFlag:"+ order.getCheckFlag()
							+ ",visableCode:"+ order.getVisableCode()
							+",reasonCode:"+ order.getReasonCode());
					String tradeOrderNo = (String) vo.get("tradeOrderNo");
					String channelOrderNo = (String) vo.get("channelOrderNo");
					String transferCurrencyCode = (String) vo
							.get("transferCurrencyCode");
					String doingRefundAmount = getMapValueByAmount(vo,
							"doingRefundAmount");
						
					String canBouncedAmount = getMapValueByAmount(vo,
							"refundAmount");
					if(flag){
						if(null==canBouncedAmount || "".equals(canBouncedAmount) || "0".equals(canBouncedAmount))
						{
							canBouncedAmount = getMapValueByAmount(vo,
									"canBouncedAmount");
						}
					}
					String partnerId = getMapValueByAmount(vo, "partnerId");
					String cardOrg = getMapValueByString(vo, "cardOrg");
					String orderAmount = getMapValueByAmount(vo, "orderAmount");
					String assureSettlementFlg = getMapValueByString(vo,
							"assureSettlementFlg");
					String settlementCurrencyCode = getMapValueByString(vo,
							"settlementCurrencyCode");
					String settlementRate = getMapValueByAmount(vo,
							"settlementRate");
					String floatValue = getMapValueByAmount(vo, "floatValue");
					String orderNo = getMapValueByString(vo, "orderId");
					// 生成拒付结果vo

					String bankCurrencyCode = order.getBankCurrencyCode();
					// 交易日期tranDate和网关订单createDate的生成日期相同
					String screateDate = order.getStranDate();
					Date createDate = order.getTranDate();
					String refNo = order.getRefNo();
					String cardholderCardno = order.getCardholderCardno();
					String authorisation = order.getAuthorisation();
					String reasonCode = order.getReasonCode();
					String currencyCode = order.getCurrencyCode();
					// 通过拒付原因和渠道查询显示原因
					String remark = getMapValueByString(vo, "remark");
					String cpFalg = BouncedEnum.cpFalg1.getType();
					String visableCode = order.getVisableCode();
					String payAmount = getMapValueByAmount(vo, "payAmount");

					// 得到交易日的汇率
					// add by Mack to control if ignore
					// markup.1=ignore
					String isIgnoreMarkup = "1";
					BigDecimal rate = null;
					/**
					 * 农行拒付文件录入问题：农行批量登记算法修正：拒付比例=拒付汇总表中金额/
					 * 渠道订单支付金额， 单项登记及其他逻辑不修改。
					 * 业务部门对GC商户的部分拒付和调单不用处理直接登记拒付即可，
					 * 对于ipay商户的部分拒付业务部门需人工计算
					 */
					if ("10002001".equals(orgCode) || "10078001".equals(orgCode)) {
						rate = BigDecimal.ONE;
					} else {
						rate = getTrantionRate(bankCurrencyCode, orderAmount,
								transferCurrencyCode, createDate, cardOrg,
								partnerId, screateDate, cardholderCardno,
								floatValue, isIgnoreMarkup);
					}
					if (rate == null) {
						order.setRemark("未找到对应交易汇率，请确认是否配置了对应的汇率" + "原币种: "
								+ bankCurrencyCode + " ," + "目标币种: "
								+ transferCurrencyCode);
						order.setStatus(BouncedEnum.status5.getType());
						order.setRegisterFlag(BouncedEnum.registerFlag0.getType());
						status5Count++;
						listadd.add(order);
						continue;
					}
					// 银行来的数据是保留两位小数，这里的payAmount是三位小数，要保留两位
					BigDecimal payAmount2 = new BigDecimal(payAmount).divide(
							new BigDecimal("1000"), 3, BigDecimal.ROUND_HALF_UP)
							.multiply(new BigDecimal("1000"));
					BigDecimal bankAmount0 = order.getBankAmount();
					BigDecimal bouncedRate = bankAmount0.multiply(rate).divide(
							payAmount2, 7, BigDecimal.ROUND_HALF_UP);
					// 如果拒付比例大于1，按1结算，为全额拒付
					if (bouncedRate.compareTo(BigDecimal.ONE) == 1) {
						bouncedRate = BigDecimal.ONE;
						cpFalg = BouncedEnum.cpFalg1.getType();
					} else if (bouncedRate.compareTo(new BigDecimal("0.999")) == 1) {
						bouncedRate = BigDecimal.ONE;
						cpFalg = BouncedEnum.cpFalg1.getType();
					} else if (bouncedRate.compareTo(BigDecimal.ONE) == 0) {
						cpFalg = BouncedEnum.cpFalg1.getType();
					} else {
						cpFalg = BouncedEnum.cpFalg2.getType();
					}
					BigDecimal bankAmount = new BigDecimal(orderAmount)
							.multiply(bouncedRate).divide(new BigDecimal("1000"))
							.setScale(4, BigDecimal.ROUND_HALF_UP);

					String doingBouncedAmount = getMapValueByAmount(vo,
							"doingBouncedAmount");
					String overRefundAmount = getMapValueByAmount(vo,
							"overRefundAmount");
					String overBouncedAmount = getMapValueByAmount(vo,
							"overBouncedAmount");
					BigDecimal totalAmount = new BigDecimal(overBouncedAmount).add(
							new BigDecimal(overRefundAmount)).add(
							new BigDecimal(doingBouncedAmount)).add(
							bankAmount.multiply(new BigDecimal("1000")));
					logger.info("已退款金额="+overRefundAmount+"已拒付金额="+overBouncedAmount
							+"拒付中金额="+doingBouncedAmount+"拒付金额="+bankAmount.multiply(new BigDecimal("1000")));
					String merchantCode = getMapValueByString(vo, "merchantCode");

					
					if (Double.parseDouble(doingRefundAmount) > 0) {
						order.setStatus(BouncedEnum.status2.getType());
						order.setRegisterFlag(BouncedEnum.registerFlag0.getType());
						// 从网关订单中取数据
						logger.info("保存拒付登记结果开始：tradeOrderNo:"
								+ order.getTradeOrderNo() + ",bouncedType:"
								+ order.getBouncedType() + ",lastDate:" + lastDate
								+ ",canBouncedAmount:"
								+ order.getCanBouncedAmount() + ",batchNo:"
								+ batchNo + ",bankCurrencyCode:"
								+ order.getBankCurrencyCode()
								+ ",transferCurrencyCode:"
								+ order.getTransferCurrencyCode() + ",reasonCode:"
								+ order.getReasonCode());
						listadd.add(order);
						status2Count++;
						continue;
					}
					if (!"00000000".equals(orgCode) && flag) {
						Map param = new HashMap();
						param.put("orgCode", orgCode);
						param.put("reasonCode", order.getReasonCode());
						Map visablemap = txncoreClientService.queryBouncedReasonMapping(param);
						List<Map> BouncedReasonMappings = (List<Map>) visablemap.get("result");
						if (BouncedReasonMappings != null && BouncedReasonMappings.size() > 0) {
							order.setVisableCode(BouncedReasonMappings.get(0).get("visableCode").toString());
							order.setRemark("");
						} else {
							order.setRemark("没有对应的显示原因");
						}
					} 
					// 拒付原因没有对应上或原因码为空，手工登记
					if (null == order.getVisableCode()
							|| "".equals(order.getVisableCode())) {
						order.setStatus(BouncedEnum.status5.getType());
						order.setRegisterFlag(BouncedEnum.registerFlag0.getType());
						order.setRemark("没有对应的显示原因");
						status5Count++;
						listadd.add(order);
						continue;
					}
					// 未清算订单
					if ("0".equals(order.getSettlementFlg())
							|| "2".equals(order.getSettlementFlg())) {
						order.setStatus(BouncedEnum.status4.getType());
						order.setRegisterFlag(BouncedEnum.registerFlag0.getType());
						order.setRemark("未清算或清算失败");
						status4Count++;
						listadd.add(order);
						continue;
					}
					//拒付和调单都要判断 如果拒付判断金额 已拒付金额+已退款金额+拒付中金额+拒付金额>交易金额
					// 批量处理时不能判断拦截,在登记时有此逻辑
					logger.info("已拒付金额+已退款金额+拒付中金额+拒付金额"+orderAmount+"交易金额="+orderAmount);
						if (totalAmount.longValue()
								- ((new BigDecimal(orderAmount)).longValue()) > 1) {
							order.setRemark("已退款金额+已拒付金额+拒付中金额+拒付金额大于交易金额【"
									+ totalAmount.divide(new BigDecimal("1000"), 3,
											BigDecimal.ROUND_HALF_UP)
									+ ">"
									+ (new BigDecimal(orderAmount)).divide(
											new BigDecimal("1000"), 3,
											BigDecimal.ROUND_HALF_UP) + "】;不能拒付!");
							order.setStatus(BouncedEnum.status5.getType());
							order.setRegisterFlag(BouncedEnum.registerFlag0
									.getType());
							status5Count++;
							listadd.add(order);
							continue;
						}
					//此情况包含全额拒付，这时可拒付金额填0，手工登记	
					if (Double.parseDouble(canBouncedAmount) <= 0) {
						order.setRemark("可拒付金额等于0，不能拒付");
						order.setStatus(BouncedEnum.status6.getType());
						order.setRegisterFlag(BouncedEnum.registerFlag0.getType());
						// 从网关订单中取数据
						listadd.add(order);
						status5Count++;
						continue;
					}
					//如果发生过调单，不能重复调单
					if("1".equals(order.getCheckFlag()))
					{
						order.setRemark("发生过调单不能重复调单，订单号="+order.getBouncedRemark());
						order.setStatus(BouncedEnum.status5.getType());
						order.setRegisterFlag(BouncedEnum.registerFlag1.getType());
						listadd.add(order);
						status5Count++;
						continue;
					}
					listadd.add(order);
					status1Count++;
					//获取数据主键
					Long bouncedkey=bouncedQueryService.bouncedKeyQuery();
					// 登记拒付订单
					// cpFalg：1-全额，2-部分
					BouncedOrderVO bouncedOrderVO = buildBouncedOrder(bouncedkey,tradeOrderNo,
							channelOrderNo, refNo, createDate, partnerId, batchNo,
							cardholderCardno, authorisation, cpdDate, canBouncedAmount,
							bankCurrencyCode, transferCurrencyCode, bankAmount.toString(),
							visableCode, reasonCode, bouncedType, remark, orgCode, orderAmount,
							payAmount, cpFalg, operator, lastDate3, assureSettlementFlg,
							settlementCurrencyCode, settlementRate, orderNo, currencyCode,
							rate.toString(), bouncedRate, bankAmount0, floatValue, merchantCode);
					batchBouncedOrders.add(bouncedOrderVO);
					logger.info("构建拒付订单：OrderId="+bouncedkey+"tradeOrderNo="+tradeOrderNo+"authorisation="+authorisation
							+"bankAmount="+bankAmount.toString());
					if ((BouncedEnum.bouncedType0.getType().equals(bouncedType) || (BouncedEnum.gcflag8
							.getType().equals(merchantCode) && BouncedEnum.bouncedType1
							.getType().equals(bouncedType)))
							&& BouncedEnum.status1.getType().equals(
									order.getStatus())
							&& BouncedEnum.registerFlag1.getType().equals(
									order.getRegisterFlag())) {
							logger.info("拒付批量登记时开始更新拒付中金额；orderNo=" + orderNo + ",tradeOrderNo="
									+ tradeOrderNo+"doingBouncedAmount="+new BigDecimal(doingBouncedAmount).add(
											bankAmount.multiply(new BigDecimal("1000")).setScale(0,
													BigDecimal.ROUND_HALF_UP)).toString());
							// 更新网关订单
							Map<String, String> paramMap = new HashMap<String, String>();
							paramMap.put("tradeOrderNo", tradeOrderNo);
							paramMap.put(
									"doingBouncedAmount",
									new BigDecimal(doingBouncedAmount).add(
											bankAmount.multiply(new BigDecimal("1000")).setScale(0,
													BigDecimal.ROUND_HALF_UP)).toString());
							
							if(BouncedEnum.bouncedType1.getType().equals(bouncedType))
							{
								paramMap.put("checkFlag", "1");
								paramMap.put("bouncedRemark", bouncedkey.toString());
							}
							bouncedQueryService.updateTradeOrder(paramMap);
							logger.info("拒付批量登记更新调单信息；orderNo=" + orderNo + ",checkFlag=1"
									+ "bouncedRemark="+bouncedkey.toString());
							
							order.setDoingBouncedAmount(new BigDecimal(doingBouncedAmount).add(
											bankAmount.multiply(new BigDecimal("1000")).setScale(0,
													BigDecimal.ROUND_HALF_UP)));
						}else if(BouncedEnum.bouncedType1.getType().equals(bouncedType) || BouncedEnum.bouncedType2.getType().equals(bouncedType))
						{
							Map<String, String> paramMap = new HashMap<String, String>();
							paramMap.put("tradeOrderNo", tradeOrderNo);
							paramMap.put("checkFlag", "1");
							paramMap.put("bouncedRemark", bouncedkey.toString());
							bouncedQueryService.updateTradeOrder(paramMap);
							logger.info("拒付批量登记时结束;更新调单标志为1（已调单）");
							
						}

			} 
		}
		countvo.setStatus0Count(status0Count);
		countvo.setStatus1Count(status1Count);
		countvo.setStatus2Count(status2Count);
		countvo.setStatus3Count(status3Count);
		countvo.setStatus4Count(status4Count);
		countvo.setStatus5Count(status5Count);
	}

	/**
	 * 对配对后的批量数据进行匹配状态更新 1.判断无匹配的 2.判断多匹配的 3.判断匹配一条的
	 * 
	 * @param batchmaps
	 *            2016年7月22日 mmzhang add
	 */
	private void batchProcess(List<Map> batchmaps) {
		Map<String, Map> bouncedmap = new HashMap<String, Map>();

		for (Map vo : batchmaps) {
			String cardholderCardno = getMapValueByString(vo, "cardholderCardno");
			String authorisation = getMapValueByString(vo, "authorisation");
			String channelOrderNo = getMapValueByString(vo, "channelOrderNo");
			String tradeOrderNo = getMapValueByString(vo, "tradeOrderNo");
			String tranDate = getMapValueByString(vo, "tranDate");

			// 无匹配
			if ("".equals(cardholderCardno) || "".equals(authorisation)
					|| "".equals(channelOrderNo) || "".equals(tradeOrderNo) || "".equals(tranDate)) {

				vo.put("status", BouncedEnum.status0.getType());
				vo.put("registerFlag", BouncedEnum.registerFlag1.getType());

			} else {
				if (bouncedmap.containsKey(tradeOrderNo)) {
					// 匹配多笔
					bouncedmap.get(tradeOrderNo).put("status", BouncedEnum.status3.getType());
					bouncedmap.get(tradeOrderNo).put("registerFlag",
							BouncedEnum.registerFlag0.getType());
					vo.put("status", BouncedEnum.status3.getType());
					vo.put("registerFlag", BouncedEnum.registerFlag0.getType());

				} else {
					bouncedmap.put(tradeOrderNo, vo);
					vo.put("status", BouncedEnum.status1.getType());
					vo.put("registerFlag", BouncedEnum.registerFlag0.getType());
				}
			}
		}

	}

	/**
	 * 
	 * @param list
	 * @param quantity
	 * @return 2016年7月22日 mmzhang add
	 */
	public List<List<BouncedResultDTO>> groupListByQuantity(List<BouncedResultDTO> list,
			int quantity) {
		if (list == null || list.size() == 0) {
			return null;
		}

		if (quantity <= 0) {
			new IllegalArgumentException("Wrong quantity.");
		}

		List<List<BouncedResultDTO>> wrapList = new ArrayList<List<BouncedResultDTO>>();
		int count = 0;
		while (count < list.size()) {
			wrapList.add(list.subList(count, (count + quantity) > list.size() ? list.size() : count
					+ quantity));
			count += quantity;
		}

		return wrapList;
	}

	/**
	 * 生存批次序列号
	 * 
	 * @param bouncedType
	 * @param orgCode
	 * @return 2016年7月21日 mmzhang add
	 */
	private int getBatchNo(String bouncedType, String orgCode) {
		Map paraMap1 = new HashMap();
		paraMap1.put("orgCode", orgCode);
		paraMap1.put("bouncedType", bouncedType);
		paraMap1.put("beginCreateDate", DateUtil.getNowDate(DateUtil.SIMPLE_DATE_FROMAT));
		paraMap1.put("endCreateDate", DateUtil.getNowDate(DateUtil.SIMPLE_DATE_FROMAT));

		// 查询今日批次有多少个，生成批次号
		Map resultMap = bouncedQueryService.bouncedResultQuery(paraMap1);
		List<Map> map1 = (List<Map>) resultMap.get("bouncedResults");
		int conunt = 0;
		if (map1 != null && !map1.isEmpty()) {
			conunt = map1.size() + 1;
		}
		return conunt;
	}

	/**
	 * 按渠道解析上传文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public synchronized ModelAndView submitUploade1(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		// 如果批量状态为0，可确认申请
		if (batchstatus.equals(BouncedEnum.batchStatus0.getType())) {
			setStatus();

			Page page = PageUtils.getPage(request);
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			Calendar time = Calendar.getInstance();
			Date todayDate = time.getTime();
			String bouncedType = StringUtil.null2String(request.getParameter("bouncedType"));
			Date lastDate = DateUtil.string2Timestamp(request.getParameter("lastDate"),
					DateUtil.SIMPLE_DATE_FROMAT);
			String cpdDate = StringUtil.null2String(request.getParameter("cpdDate"));
			String orgCode = StringUtil.null2String(request.getParameter("orgCode"));

			Date lastDate3 = getLastDate(lastDate);
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
			String operator = SessionUserHolderUtil.getLoginId();

			FileParseResult fileParseResult = fileService.parserFileInfo(file.getInputStream(),
					file.getName(), "BOUNCED_".concat(orgCode));

			if (null == fileParseResult) {
				logger.error("解析拒付文件报错," + orgCode);
				return new ModelAndView(urlMap.get("batchscuess")).addObject("message",
						"解析拒付文件报错，无法获取解析器或格式文件错误！");
			}

			List<BouncedResultDTO> list = fileParseResult.getList();

			if (null == list || list.isEmpty()) {
				return new ModelAndView(urlMap.get("batchscuess")).addObject("message", "没有合法的记录");
			}

			List<BouncedResultDTO> listadd = new ArrayList<BouncedResultDTO>();
			// 批量登记的数据

			boolean parseFalg = true;
			// 导入结果的统计
			int status0Count = 0;
			int status1Count = 0;
			int status2Count = 0;
			int status3Count = 0;
			int status4Count = 0;
			int status5Count = 0;

			// 对解析出的每笔拒付数据进行匹配
			for (BouncedResultDTO order : list) {
				order.setOperator(operator);
				order.setRegisterFlag(BouncedEnum.registerFlag0.getType());
				order.setOrgCode(orgCode);
				if (order.getStranDate().length() > 8) {
					order.setTranDate(new SimpleDateFormat("yyyy-MM-dd").parse(order.getStranDate()));
				} else {
					String yyyy = order.getStranDate().substring(0, 4);
					String mm = order.getStranDate().substring(4, 6);
					String dd = order.getStranDate().substring(6, 8);
					Date datef = new SimpleDateFormat("yyyyMMdd").parse(order.getStranDate());
					order.setTranDate(datef);
					order.setStranDate(yyyy.concat("-").concat(mm).concat("-").concat(dd));
				}
				order.setCreateDate(new Date());
				order.setAuthorisation(order.getAuthorisation());
				order.setBouncedType(bouncedType);
				order.setLastDate(lastDate3);
				if (order.getSorderAmount() != null) {
					order.setOrderAmount(new BigDecimal(order.getSorderAmount()).multiply(
							new BigDecimal("1000")).setScale(2));
				}
				order.setBankAmount(new BigDecimal(order.getSbankAmount()).multiply(
						new BigDecimal("1000")).setScale(2));
				order.setCpdDate(cpdDate);
				int conunt = getBatchNo(bouncedType, orgCode);
				String batchNo = concatBatchNo(orgCode, bouncedType);
				if (conunt < 10) {
					batchNo = batchNo.concat("0" + conunt);
				} else {
					batchNo = batchNo.concat("" + conunt);
				}

				if ("10076001".equals(orgCode) || "10002001".equals(orgCode) || "10078001".equals(orgCode)) {
					order.setBankCurrencyCode("CNY");
				}
				// 拒付文件解析检查 CREDORAX要检测渠道订单号
				checkBouncedResult(parseFalg, order, orgCode);

				Map paraMap = new HashMap();
				// CREDORAX 直接用渠道订单号匹配,内部调单用网关订单号
				if ("00000000".equals(orgCode)) {
					paraMap.put("tradeOrderNo", order.getTradeOrderNo());
				} else if ("10075001".equals(orgCode)) {
					paraMap.put("channelOrderNo", order.getChannelOrderNo());
				} else {
					String cardNo = order.getCardholderCardno();
					// 如果卡号有星号替换，则用模糊查询
					if (cardNo.indexOf("*") != -1) {
						cardNo = cardNo.replaceAll("\\*", "_");
						paraMap.put("cardNoLike", cardNo);
					} else {
						paraMap.put("cardNo", cardNo);
					}

					paraMap.put("authorisation", order.getAuthorisation());
					paraMap.put("tranDate", order.getStranDate());
				}

				paraMap.put("orgCode", orgCode);
				List<Map> map = bouncedQueryService.bouncedQuery(paraMap);
				// 匹配失败
				if (map == null || CollectionUtils.isEmpty(map)) {

					order.setStatus(BouncedEnum.status0.getType());
					order.setTradeOrderNo(null);
					order.setTransferCurrencyCode(null);
					if (StringUtil.isEmpty(order.getChannelOrderNo())) {
						order.setChannelOrderNo(null);
					}
					order.setOverRefundAmount(BigDecimal.ZERO); // 已退金额
					order.setDoingRefundAmount(BigDecimal.ZERO); // 退款中金额
					order.setOverBouncedAmount(BigDecimal.ZERO); // 已拒付金额
					order.setCanBouncedAmount(BigDecimal.ZERO); // 可拒付金额
					order.setCreateDate(new Date());
					order.setRegisterFlag(BouncedEnum.registerFlag0.getType());
					order.setSettlementFlg(null);
					order.setAssureSettlementFlg(null);
					order.setBatchNo(batchNo);
					listadd.add(order);
					status0Count++;
					logger.info("保存拒付登记结果开始：tradeOrderNo:" + order.getTradeOrderNo()
							+ ",bouncedType:" + order.getBouncedType() + ",lastDate:" + lastDate
							+ ",canBouncedAmount:" + order.getCanBouncedAmount() + ",batchNo:"
							+ batchNo + ",bankCurrencyCode:" + order.getBankCurrencyCode()
							+ ",transferCurrencyCode:" + order.getTransferCurrencyCode()
							+ ",reasonCode:" + order.getReasonCode());

					// 匹配异常
				} else if (map.size() > 1) {
					order.setStatus(BouncedEnum.status3.getType());
					order.setRegisterFlag(BouncedEnum.registerFlag0.getType());
					for (Map vo : map) {
						BouncedResultDTO resultAdd = new BouncedResultDTO();
						BeanUtils.copyProperties(order, resultAdd);
						buildBouncedresultByMap(batchNo, vo, resultAdd);
						logger.info("保存拒付登记结果开始：tradeOrderNo:" + resultAdd.getTradeOrderNo()
								+ ",bouncedType:" + resultAdd.getBouncedType() + ",lastDate:"
								+ lastDate + ",canBouncedAmount:" + resultAdd.getCanBouncedAmount()
								+ ",batchNo:" + batchNo + ",bankCurrencyCode:"
								+ resultAdd.getBankCurrencyCode() + ",transferCurrencyCode:"
								+ resultAdd.getTransferCurrencyCode() + ",reasonCode:"
								+ resultAdd.getReasonCode());

						listadd.add(resultAdd);
					}
					status3Count++;
					// 匹配成功
				} else if (map.size() == 1) {
					Map vo = map.get(0);
					String tradeOrderNo = (String) vo.get("tradeOrderNo");
					String channelOrderNo = (String) vo.get("channelOrderNo");
					String transferCurrencyCode = (String) vo.get("transferCurrencyCode");
					String doingRefundAmount = getMapValueByAmount(vo, "doingRefundAmount");
					String canBouncedAmount = getMapValueByAmount(vo, "refundAmount");
					// 退款中订单不能做拒付
					if (Double.parseDouble(doingRefundAmount) > 0) {
						order.setStatus(BouncedEnum.status2.getType());
						order.setRegisterFlag(BouncedEnum.registerFlag0.getType());
						// 从网关订单中取数据
						buildBouncedresultByMap(batchNo, vo, order);
						logger.info("保存拒付登记结果开始：tradeOrderNo:" + order.getTradeOrderNo()
								+ ",bouncedType:" + order.getBouncedType() + ",lastDate:"
								+ lastDate + ",canBouncedAmount:" + order.getCanBouncedAmount()
								+ ",batchNo:" + batchNo + ",bankCurrencyCode:"
								+ order.getBankCurrencyCode() + ",transferCurrencyCode:"
								+ order.getTransferCurrencyCode() + ",reasonCode:"
								+ order.getReasonCode());
						listadd.add(order);
						status2Count++;
					} else {
						order.setStatus(BouncedEnum.status1.getType());
						order.setRegisterFlag(BouncedEnum.registerFlag1.getType());
						String partnerId = getMapValueByAmount(vo, "partnerId");
						String cardOrg = getMapValueByString(vo, "cardOrg");
						String orderAmount = getMapValueByAmount(vo, "orderAmount");
						String assureSettlementFlg = getMapValueByString(vo, "assureSettlementFlg");
						String settlementCurrencyCode = getMapValueByString(vo,
								"settlementCurrencyCode");
						String settlementRate = getMapValueByAmount(vo, "settlementRate");
						String floatValue = getMapValueByAmount(vo, "floatValue");
						String orderNo = getMapValueByString(vo, "orderId");
						// 生成拒付结果vo
						buildBouncedresultByMap(batchNo, vo, order);

						String bankCurrencyCode = order.getBankCurrencyCode();
						// 交易日期tranDate和网关订单createDate的生成日期相同
						String screateDate = order.getStranDate();
						Date createDate = order.getTranDate();
						String refNo = order.getRefNo();
						String cardholderCardno = order.getCardholderCardno();
						String authorisation = order.getAuthorisation();
						String reasonCode = order.getReasonCode();
						String currencyCode = order.getCurrencyCode();
						// 通过拒付原因和渠道查询显示原因
						String remark = getMapValueByString(vo, "remark");
						String cpFalg = BouncedEnum.cpFalg1.getType();
						String visableCode = order.getVisableCode();
						String payAmount = getMapValueByAmount(vo, "payAmount");

						// 得到交易日的汇率
						// add by Mack to control if ignore markup.1=ignore
						String isIgnoreMarkup = "1";
						BigDecimal rate = null;
						/**
						 * 农行拒付文件录入问题：农行批量登记算法修正：拒付比例=拒付汇总表中金额/渠道订单支付金额，
						 * 单项登记及其他逻辑不修改。 业务部门对GC商户的部分拒付和调单不用处理直接登记拒付即可，
						 * 对于ipay商户的部分拒付业务部门需人工计算
						 */
						if ("10002001".equals(orgCode) || "10078001".equals(orgCode)) {
							rate = BigDecimal.ONE;
						} else {
							rate = getTrantionRate(bankCurrencyCode, orderAmount,
									transferCurrencyCode, createDate, cardOrg, partnerId,
									screateDate, cardholderCardno, floatValue, isIgnoreMarkup);
						}
						if (rate == null) {
							order.setRemark("未找到对应交易汇率，请确认是否配置了对应的汇率" + "原币种: " + bankCurrencyCode
									+ " ," + "目标币种: " + transferCurrencyCode);
							parseFalg = false;
							continue;
						}
						// 银行来的数据是保留两位小数，这里的payAmount是三位小数，要保留两位
						BigDecimal payAmount2 = new BigDecimal(payAmount).divide(
								new BigDecimal("1000"), 3, BigDecimal.ROUND_HALF_UP).multiply(
								new BigDecimal("1000"));
						BigDecimal bankAmount0 = order.getBankAmount();
						BigDecimal bouncedRate = bankAmount0.multiply(rate).divide(payAmount2, 7,
								BigDecimal.ROUND_HALF_UP);
						// 如果拒付比例大于1，按1结算，为全额拒付
						if (bouncedRate.compareTo(BigDecimal.ONE) == 1) {
							bouncedRate = BigDecimal.ONE;
							cpFalg = BouncedEnum.cpFalg1.getType();
						} else if (bouncedRate.compareTo(new BigDecimal("0.999")) == 1) {
							bouncedRate = BigDecimal.ONE;
							cpFalg = BouncedEnum.cpFalg1.getType();
						} else if (bouncedRate.compareTo(BigDecimal.ONE) == 0) {
							cpFalg = BouncedEnum.cpFalg1.getType();
						} else {
							cpFalg = BouncedEnum.cpFalg2.getType();
						}
						BigDecimal bankAmount = new BigDecimal(orderAmount).multiply(bouncedRate)
								.divide(new BigDecimal("1000"))
								.setScale(4, BigDecimal.ROUND_HALF_UP);

						String doingBouncedAmount = getMapValueByAmount(vo, "doingBouncedAmount");
						String overRefundAmount = getMapValueByAmount(vo, "overRefundAmount");
						String overBouncedAmount = getMapValueByAmount(vo, "overBouncedAmount");
						BigDecimal totalAmount = new BigDecimal(overBouncedAmount).add(
								new BigDecimal(doingBouncedAmount)).add(
								bankAmount.multiply(new BigDecimal("1000")));
						String merchantCode = getMapValueByString(vo, "merchantCode");
						// 拒付原因没有对应上或原因码为空，手工登记
						if (null == order.getVisableCode() || "".equals(order.getVisableCode())) {
							order.setStatus(BouncedEnum.status5.getType());
							order.setRegisterFlag(BouncedEnum.registerFlag0.getType());
							order.setRemark("没有对应的显示原因");
							status5Count++;
							listadd.add(order);
							continue;
						}
						// 未清算订单
						if ("0".equals(order.getSettlementFlg())) {
							order.setStatus(BouncedEnum.status4.getType());
							order.setRegisterFlag(BouncedEnum.registerFlag1.getType());
							status4Count++;
							listadd.add(order);
							continue;
						}
						// 如果拒付判断金额 已拒付金额+拒付中金额+拒付金额>交易金额
						if (BouncedEnum.bouncedType0.getType().equals(bouncedType)) {
							// 批量处理时不能判断拦截,在登记时有此逻辑
							if (totalAmount.longValue()
									- ((new BigDecimal(orderAmount)).longValue()) > 1) {
								order.setRemark("已拒付金额+拒付中金额+拒付金额大于交易金额【"
										+ totalAmount.divide(new BigDecimal("1000"), 3,
												BigDecimal.ROUND_HALF_UP)
										+ ">"
										+ (new BigDecimal(orderAmount)).divide(new BigDecimal(
												"1000"), 3, BigDecimal.ROUND_HALF_UP) + "】;不能拒付!");
								order.setStatus(BouncedEnum.status5.getType());
								order.setRegisterFlag(BouncedEnum.registerFlag0.getType());
								status5Count++;
								listadd.add(order);
								continue;
							}
						}
						if (bankAmount.compareTo(new BigDecimal(canBouncedAmount).divide(
								new BigDecimal("1000"), 3, BigDecimal.ROUND_HALF_UP)) == 1) {
							order.setRemark("拒付金额大于可拒付金额【"
									+ bankAmount
									+ ">"
									+ new BigDecimal(canBouncedAmount).divide(
											new BigDecimal("1000"), 3, BigDecimal.ROUND_HALF_UP)
									+ "】;不能拒付和调单!");
							order.setStatus(BouncedEnum.status5.getType());
							order.setRegisterFlag(BouncedEnum.registerFlag0.getType());
							status5Count++;
							listadd.add(order);
							continue;
						}
						if (Double.parseDouble(canBouncedAmount) <= 0) {
							order.setRemark("");
							order.setStatus(BouncedEnum.status5.getType());
							order.setRegisterFlag(BouncedEnum.registerFlag0.getType());
							// 从网关订单中取数据
							buildBouncedresultByMap(batchNo, vo, order);
							logger.info("保存拒付登记结果开始：tradeOrderNo:" + order.getTradeOrderNo()
									+ ",bouncedType:" + order.getBouncedType() + ",lastDate:"
									+ lastDate + ",canBouncedAmount:" + order.getCanBouncedAmount()
									+ ",batchNo:" + batchNo + ",bankCurrencyCode:"
									+ order.getBankCurrencyCode() + ",transferCurrencyCode:"
									+ order.getTransferCurrencyCode() + ",reasonCode:"
									+ order.getReasonCode());
							listadd.add(order);
							status5Count++;
						}
						status1Count++;
						listadd.add(order);
						logger.info("保存拒付登记结果开始：tradeOrderNo:" + order.getTradeOrderNo()
								+ ",bouncedType:" + order.getBouncedType() + ",lastDate:"
								+ lastDate + ",canBouncedAmount:" + order.getCanBouncedAmount()
								+ ",batchNo:" + batchNo + ",bankCurrencyCode:"
								+ order.getBankCurrencyCode() + ",transferCurrencyCode:"
								+ order.getTransferCurrencyCode() + ",reasonCode:"
								+ order.getReasonCode());

						logger.info("交易日的汇率为：rate:" + rate + "拒付比例：bouncedrate:" + bouncedRate
								+ "拒付金额：bankAmount:" + bankAmount);
						
						//获取数据主键
						Long bouncedkey=bouncedQueryService.bouncedKeyQuery();
						// 登记拒付订单
						// cpFalg：1-全额，2-部分
						BouncedOrderVO bouncedOrderVO = buildBouncedOrder(bouncedkey,tradeOrderNo,
								channelOrderNo, refNo, createDate, partnerId, batchNo,
								cardholderCardno, authorisation, cpdDate, canBouncedAmount,
								bankCurrencyCode, transferCurrencyCode, bankAmount.toString(),
								visableCode, reasonCode, bouncedType, remark, orgCode, orderAmount,
								payAmount, cpFalg, operator, lastDate3, assureSettlementFlg,
								settlementCurrencyCode, settlementRate, orderNo, currencyCode,
								rate.toString(), bouncedRate, bankAmount0, floatValue, merchantCode);

						// 批量登记的数据
						List<BouncedOrderVO> batchBouncedOrders = new ArrayList<BouncedOrderVO>();
						batchBouncedOrders.add(bouncedOrderVO);

						if (BouncedEnum.bouncedType0.getType().equals(bouncedType)
								&& BouncedEnum.status1.getType().equals(order.getStatus())
								&& BouncedEnum.registerFlag1.getType().equals(
										order.getRegisterFlag()) && parseFalg) {
							logger.info("拒付单项登记时开始更新拒付中金额；orderNo=" + orderNo + ",tradeOrderNo="
									+ tradeOrderNo);
							// 更新网关订单
							Map<String, String> paramMap = new HashMap<String, String>();
							paramMap.put("tradeOrderNo", tradeOrderNo);
							paramMap.put(
									"doingBouncedAmount",
									new BigDecimal(doingBouncedAmount).add(
											bankAmount.multiply(new BigDecimal("1000")).setScale(0,
													BigDecimal.ROUND_HALF_UP)).toString());
							bouncedQueryService.updateTradeOrder(paramMap);
							logger.info("拒付单项登记时结束；orderNo=" + orderNo + ",tradeOrderNo="
									+ tradeOrderNo);
						}
						if (!batchBouncedOrders.isEmpty()) {
							chargeBackService.addChargeBackOrderNew(batchBouncedOrders);
						}

						/**
						 * 更新好拒付订单信息后，会把现在的拒付金额更新到已拒付金额，这里要重新取一下
						 */
						Map paraMap2 = new HashMap();
						paraMap2.put("tradeOrderNo", order.getTradeOrderNo());
						List<Map> map2 = bouncedQueryService.bouncedQuery(paraMap2);
						if (map2 == null || CollectionUtils.isEmpty(map)) {
							if (map2.size() == 1) {
								Map vo2 = map2.get(0);
								String doingBouncedAmount2 = getMapValueByAmount(vo2,
										"doingBouncedAmount");
								String overBouncedAmount2 = getMapValueByAmount(vo2,
										"overBouncedAmount");
								String canBouncedAmount2 = getMapValueByAmount(vo2, "refundAmount");
								order.setOverBouncedAmount(new BigDecimal(overBouncedAmount2)
										.setScale(2)); // 已拒付金额
								order.setCanBouncedAmount(new BigDecimal(canBouncedAmount2)
										.setScale(2)); // 可拒付金额
								order.setDoingBouncedAmount(new BigDecimal(doingBouncedAmount2)
										.setScale(2));// 拒付中金额
							}
						}

						// 拒付匹配结果保存
						if (!listadd.isEmpty()) {
							bouncedQueryService.addBouncedResult(listadd);
						}

					}

				}

			}
			resetbatchStatusMethod();
			if (!parseFalg) {
				return new ModelAndView(urlMap.get("error")).addObject("list", list);
			} else {
				return new ModelAndView(urlMap.get("batchResult"))
						.addObject("todayDate", todayDate).addObject("page", listadd)
						.addObject("status0Count", status0Count)
						.addObject("status1Count", status1Count)
						.addObject("status2Count", status2Count)
						.addObject("status3Count", status3Count)
						.addObject("status4Count", status4Count)
						.addObject("status5Count", status5Count);
			}
		} else {
			return new ModelAndView(urlMap.get("scuess")).addObject("message", "批量导入正在运行，不能重复提交");
		}
	}

	private void buildBouncedresultByMap(String batchNo, Map vo, BouncedResultDTO resultAdd)
			throws ParseException {
		String tradeOrderNo = (String) vo.get("tradeOrderNo");
		String channelOrderNo = (String) vo.get("channelOrderNo");
		String transferCurrencyCode = (String) vo.get("transferCurrencyCode");
		String doingRefundAmount = getMapValueByAmount(vo, "doingRefundAmount");
		String overRefundAmount = getMapValueByAmount(vo, "overRefundAmount");
		String overBouncedAmount = getMapValueByAmount(vo, "overBouncedAmount");
		String canBouncedAmount = getMapValueByAmount(vo, "refundAmount");
		if(null==canBouncedAmount || "".equals(canBouncedAmount) || "0".equals(canBouncedAmount))
		{
			canBouncedAmount = getMapValueByAmount(vo,
					"canBouncedAmount");
		}
		String partnerId = getMapValueByAmount(vo, "partnerId");
		String cardOrg = getMapValueByString(vo, "cardOrg");
		String transferRate = getMapValueByAmount(vo, "transferRate");
		String payAmount = getMapValueByAmount(vo, "payAmount");
		String orderAmount = getMapValueByAmount(vo, "orderAmount");
		String settlementFlg = getMapValueByString(vo, "settlementFlg");
		String assureSettlementFlg = getMapValueByString(vo, "assureSettlementFlg");
		String settlementCurrencyCode = getMapValueByString(vo, "settlementCurrencyCode");
		String settlementRate = getMapValueByAmount(vo, "settlementRate");
		String orderId = getMapValueByAmount(vo, "orderId");
		String currencyCode = getMapValueByAmount(vo, "currencyCode");
		String floatValue = getMapValueByAmount(vo, "floatValue");
		String remark = getMapValueByString(vo, "remark");
		String createDate = getMapValueByString(vo, "tranDate");
		String doingBouncedAmount = getMapValueByAmount(vo, "doingBouncedAmount");
		String merchantCode = getMapValueByString(vo, "merchantCode");
		String checkFlag = getMapValueByString(vo, "checkFlag");
		String bouncedRemark = getMapValueByString(vo, "bouncedRemark");

		// 从网关订单中取数据
		resultAdd.setTradeOrderNo(tradeOrderNo);
		resultAdd.setTransferCurrencyCode(transferCurrencyCode);
		resultAdd.setChannelOrderNo(channelOrderNo);
		resultAdd.setOverRefundAmount(new BigDecimal(overRefundAmount).setScale(2)); // 已退金额
		resultAdd.setDoingRefundAmount(new BigDecimal(doingRefundAmount).setScale(2)); // 退款中金额
		resultAdd.setOverBouncedAmount(new BigDecimal(overBouncedAmount).setScale(2)); // 已拒付金额
		resultAdd.setCanBouncedAmount(new BigDecimal(canBouncedAmount).setScale(2)); // 可拒付金额
		resultAdd.setDoingBouncedAmount(new BigDecimal(doingBouncedAmount).setScale(2));
		resultAdd.setCreateDate(new Date());
		resultAdd.setSettlementFlg(settlementFlg);
		resultAdd.setAssureSettlementFlg(assureSettlementFlg);
		resultAdd.setBatchNo(batchNo);
		resultAdd.setStranDate(getDateForLong(createDate));
		Date datef = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(resultAdd.getStranDate());
		logger.info("buildBouncedresultByMap:交易时间StranDate=" + resultAdd.getStranDate()
				+ ";转换后交易时间tranDate=" + datef);
		resultAdd.setTranDate(datef);
		resultAdd.setPartnerId(partnerId);
		resultAdd.setCardOrg(cardOrg);
		resultAdd.setTransferRate(new BigDecimal(transferRate));
		resultAdd.setPayAmount(new BigDecimal(payAmount).setScale(2));
		resultAdd.setOrderAmount(new BigDecimal(orderAmount).setScale(2));
		resultAdd.setSettlementCurrencyCode(settlementCurrencyCode);
		resultAdd.setSettlementRate(new BigDecimal(settlementRate));
		resultAdd.setOrderId(orderId);
		resultAdd.setCurrencyCode(currencyCode);
		resultAdd.setFloatValue(floatValue);
		resultAdd.setRemark(remark);
		resultAdd.setMerchantCode(merchantCode);
		resultAdd.setCheckFlag(checkFlag);
		resultAdd.setBouncedRemark(bouncedRemark);
	}

	/**
	 * 把long时间转换为date
	 * 
	 * @param createDate
	 * @return 2016年6月12日 mmzhang add
	 */
	private String getDateForLong(String createDate) {
		String vv = "" + createDate;
		long time = Long.valueOf(vv);
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = dateformat.format(c.getTime());
		return s;
	}

	private String getMapValueByAmount(Map vo, String name) {
		String doingRefundAmount = "0";
		if (vo.get(name) != null && !"".equals(vo.get(name))) {
			doingRefundAmount = vo.get(name).toString();
		}
		return doingRefundAmount;
	}

	private String getMapValueByString(Map vo, String name) {
		String doingRefundAmount = "";
		if (vo.get(name) != null && !"".equals(vo.get(name))) {
			doingRefundAmount = vo.get(name).toString();
		}
		return doingRefundAmount;
	}

	/**
	 * 拒付文件解析检查
	 * 
	 * @param parseFalg
	 * @param order
	 * @return 2016年5月19日 mmzhang add
	 */
	private void checkBouncedResult(boolean parseFalg, BouncedResultDTO order, String orgCode) {

		if ("10075001".equals(orgCode)) {
			if ("".equals(order.getChannelOrderNo())) {
				order.setRemark("渠道订单号为空");
				parseFalg = false;
			}
		}
		if ("00000000".equals(orgCode)) {
			if ("".equals(order.getTradeOrderNo())) {
				order.setRemark("网关订单号为空");
				parseFalg = false;
			}
		}
		if ("".equals(order.getCardholderCardno())) {
			order.setRemark("银行卡为空");
			parseFalg = false;
		}
		if ("".equals(order.getAuthorisation())) {
			order.setRemark("授权码为空");
			parseFalg = false;
		}
		if ("".equals(order.getStranDate())) {
			order.setRemark("交易日期为空");
			parseFalg = false;
		}
		if ("".equals(order.getReasonCode())) {
			order.setRemark("拒付原因为空");
			parseFalg = false;
		}
		if ("".equals(order.getBankCurrencyCode())) {
			order.setRemark("拒付币种为空");
			parseFalg = false;
		}
		if ("".equals(order.getSbankAmount())) {
			order.setRemark("拒付金额为空");
			parseFalg = false;
		} else if (Double.parseDouble(order.getSbankAmount()) < 0) {

			order.setRemark("拒付金额不能小于0");
			parseFalg = false;
		}
		if ("10076001".equals(orgCode)) {
			if ("".equals(order.getRefNo())) {
				order.setRemark("档案号为空");
				parseFalg = false;
			}
		}
		if (!"00000000".equals(orgCode)) {
			Map param = new HashMap();
			param.put("orgCode", orgCode);
			param.put("reasonCode", order.getReasonCode());
			Map visablemap = txncoreClientService.queryBouncedReasonMapping(param);
			List<Map> BouncedReasonMappings = (List<Map>) visablemap.get("result");
			if (BouncedReasonMappings != null && BouncedReasonMappings.size() > 0) {
				order.setVisableCode(BouncedReasonMappings.get(0).get("visableCode").toString());
			} else {
				order.setRemark("没有对应的显示原因");
			}
		} else {
			order.setVisableCode(order.getReasonCode());
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param request
	 * @param response
	 * @param websiteConfig
	 * @return
	 * @throws Exception
	 */
	public ModelAndView downLoadFile(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String path = getServletContext().getRealPath("/Template");

		String fileName = request.getParameter("fileName");
		FileInputStream fi = new FileInputStream(path + "//" + fileName);
		byte[] bt = new byte[fi.available()];
		fi.read(bt);
		response.setContentType("application/msdownload;charset=UTF-8");

		// System.out.print(response.getContentType());
		response.setCharacterEncoding("UTF-8");
		fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		response.setHeader("Content-Disposition",
				"attachment; filename=" + new String(fileName.getBytes("UTF-8"), "GBK"));
		response.setContentLength(bt.length);
		ServletOutputStream sos = response.getOutputStream();
		sos.write(bt);
		return null;
	}

	/**
	 * 根据拒付类型，拒付金额类型判断拒付的记账逻辑，设计资金状态，根据资金状态记账
	 * 资金状态是1时，按拒付订单上的基本户扣款，和保证金扣款字段，记帐，判断保证金和基本户够扣，则扣款记账，记账成功后状态改为2。不够t+1继续扣款。
	 * 资金状态是3时，按拒付订单上的基本户扣款，和保证金扣款字段记帐，判断保证金和基本户够扣，则扣款记账，记账成功后状态改为4。不够t+1继续冻结。
	 * 资金状态是5时，解冻记账。
	 * 资金状态是7时，按拒付订单上的基本户扣款，和保证金扣款字段，记帐，判断保证金和基本户够扣，则扣款记账，记账成功后状态改为8。不够t+1继续扣款
	 * 
	 * @param channelOrderNo
	 * @param bankAmount
	 * @param reasonCode
	 * @param bouncedType
	 * @param cpFalg
	 * @param chargeBackOrder
	 * @param assurePercentage
	 *            2016年5月16日 mmzhang add
	 */
	private void possAccountByBouncedType(String channelOrderNo, String bankAmount,
			String visableCode, String bouncedType, String cpFalg, BouncedOrderVO chargeBackOrder,
			Integer assurePercentage, String assureSettlementFlg, String settlementRate,String merchantCode) {
		// 资金状态的判断，欺诈和未授权,以前是调单，现在是拒付，按照包装原因来判断
		// 资金状态：1直接扣款，2已扣款，3冻结，4已冻结，5解冻，6已解冻，7申诉失败扣款，8申诉失败扣款成功
		// 1.拒付类型为拒付的，并且显示原因为欺诈和未授权，直接扣款
		if ((BouncedEnum.bouncedType0.getType().equals(bouncedType) || (BouncedEnum.gcflag8
				.getType().equals(merchantCode) && BouncedEnum.bouncedType1.getType().equals(
				bouncedType)))
				&& ("3".equals(visableCode) || "5".equals(visableCode))) {
			chargeBackOrder.setAmountStatus(BouncedEnum.amountType1.getType());

		} else {
			BouncedOrderVO chargeBackOrder2 = new BouncedOrderVO();
			chargeBackOrder2.setChannelOrderId(Long.valueOf(channelOrderNo));
			chargeBackOrder2.setCpTypes(BouncedEnum.bouncedType1.getType() + ","
					+ BouncedEnum.bouncedType2.getType());
			chargeBackOrder2.setAmountStatus(BouncedEnum.amountType4.getType());
			// 2如果该订单，以前为银行调单或内部调单，现在为冻结后扣款
			Map chargeBackOrdermap = chargeBackService.queryBouncedOrder(chargeBackOrder2, null);
			List<Map> lists = (List<Map>) chargeBackOrdermap.get("result");
			if (null != lists && !lists.isEmpty()) {
				List<ChargeBackOrder> chargeBackOrders = new ArrayList<ChargeBackOrder>();
				chargeBackOrder.setAmountStatus(BouncedEnum.amountType1.getType());
				for (Map vo1 : lists) {
					ChargeBackOrder vo2 = new ChargeBackOrder();
					vo2.setAccountingFlg(Integer.valueOf(BouncedEnum.accountingFlg2.getType()));
					vo2.setOrderId(Long.parseLong(vo1.get("orderId").toString()));
					chargeBackOrders.add(vo2);
				}
				chargeBackService.updateChargeBackOrder(chargeBackOrders, "true");
			} else {
				chargeBackOrder.setAmountStatus(BouncedEnum.amountType3.getType());
			}

		}
		BigDecimal assureAmount = BigDecimal.ZERO;
		BigDecimal baseAmount = new BigDecimal(bankAmount);

		// 拒绝金额类型cpFalg：1-全额，2-部分
		// 部分拒付扣基本户 或者 保证金清算状态为1已归还，直接扣基本户
		if ("2".equals(cpFalg) || "1".equals(assureSettlementFlg)) {
			chargeBackOrder.setBaseAmount(baseAmount);
			chargeBackOrder.setAssureAmount(assureAmount);
			chargeBackOrder.setCpFlg(2);
		} else {
			assureAmount = baseAmount.multiply(new BigDecimal(assurePercentage)).divide(
					new BigDecimal("100"));
			baseAmount = baseAmount.subtract(assureAmount);
			chargeBackOrder.setBaseAmount(baseAmount);
			chargeBackOrder.setAssureAmount(assureAmount);
		}
	}

	/**
	 * 下载申诉资料
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public void downloadData(HttpServletRequest request, HttpServletResponse response) {
		try {
			Ftp f = this.getFtpConnection();
			String orderIds = request.getParameter("orderIds");
			Map map = new HashMap();
			map.put("orderIds", orderIds.substring(0, orderIds.length() - 1));
			Map queryBouncedOrder = bouncedQueryService.queryBouncedOrder(map);
			List<Map> returnMap = (List<Map>) queryBouncedOrder.get("result");
			List<String> path = new ArrayList<String>();
			String nowDate = DateUtil.dateToStr(new Date(), DateUtil.PATTERN1);
			String localBaseDir = "/chargeback/" + nowDate + "/";
			File toGetAbsoultePathFile = new File(localBaseDir);
			String absolutePath = toGetAbsoultePathFile.getAbsolutePath();
			logger.info("downloadData absolutePath:" + absolutePath);
			// absolutePath.
			List<String> paths = new ArrayList<String>();
			if (returnMap != null && !returnMap.isEmpty()) {
				for (Map map2 : returnMap) {
					String remoteDir = String.valueOf(map2.get("appealDbRelativePath"));
					int index = remoteDir.lastIndexOf("/");
					String remoteBaseDir = remoteDir.substring(0, index);
					String f_fileName = remoteDir.substring(index + 1, remoteDir.length());
					FtpUtil.startDown(f, localBaseDir, remoteBaseDir, f_fileName);// 下载ftp文件测试
																					// /xxtest
					// System.out.println("ok");
				}
			}
			File dirFile = new File(absolutePath);
			this.download(response, paths, dirFile);
			// return new
			// ModelAndView(this.urlMap.get("bouncedQuery")).addObject("downLoadPath",
			// "下载成功！") ; //"下载文件存储路径为：" + absolutePath.replace("\\",
			// File.separator + '/')
		} catch (Exception e) {
			e.printStackTrace();
			// return new
			// ModelAndView(this.urlMap.get("bouncedQuery")).addObject("downLoadError",
			// "申诉资料批量导出出错了！") ;
		}
		// return new
		// ModelAndView(this.urlMap.get("bouncedQuery")).addObject("downLoadError",
		// "申诉资料批量导出出错了！") ;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	private Ftp getFtpConnection() throws Exception {
		Ftp f = new Ftp();
		// f.setIpAddr("172.168.1.82");
		// f.setUserName("roftp");
		// f.setPwd("roftp");
		// f.setPort(10051);
		String ftpUrl = this.appealDataFilePath;
		int beforeIpIndex = ftpUrl.indexOf("//");
		int afterIpIndex = ftpUrl.lastIndexOf(":");
		String ipAddr = ftpUrl.substring(beforeIpIndex + 2, afterIpIndex);
		String port = ftpUrl.substring(afterIpIndex + 1, ftpUrl.length());
		String userName = this.ftpUserName;
		String pwd = this.ftpPassword;

		f.setIpAddr(ipAddr);
		f.setUserName(userName);
		f.setPwd(pwd);
		f.setPort(Integer.valueOf(port));
		FtpUtil.connectFtp(f);
		return f;
	}

	/**
	 * 下载文件
	 * 
	 * @param path
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public void download(HttpServletResponse response, List<String> list, File dirFile) {

		try {
			response.setContentType("Content-type: text/zip");
			response.setHeader("Content-Disposition", "attachment; filename=chargebackfile.zip");

			// List<File> files = new ArrayList<File>();

			// for (String path : list) {
			// files.add(new File(path));
			// }
			ServletOutputStream out = response.getOutputStream();
			ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(out));
			File[] listFiles = dirFile.listFiles();
			for (File f : listFiles) {
				zos.putNextEntry(new ZipEntry(f.getName()));

				FileInputStream fis = null;
				try {
					fis = new FileInputStream(f);

				} catch (FileNotFoundException fnfe) {
					fnfe.printStackTrace();
					zos.write(("ERROR: Could not find file " + f.getName()).getBytes());
					zos.closeEntry();
					continue;
				}

				BufferedInputStream fif = new BufferedInputStream(fis);

				int data = 0;
				while ((data = fif.read()) != -1) {
					zos.write(data);
				}
				fif.close();

				zos.closeEntry();
			}
			zos.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 工作面板
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView workPanels(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Map params = new HashMap();
		params.put("status", "1,2");// 待处理申诉
		Map pendingMap = this.chargeBackService.countChargeBackOrderByStatus(params);
		params.put("status", "3,4");// 拒付处理复核
		Map pendingReviewMap = this.chargeBackService.countChargeBackOrderByStatus(params);
		String pendingCount = pendingMap.get("count") + "";
		String pendingReviewCount = pendingReviewMap.get("count") + "";
		Map map = new HashMap();
		map.put("pendingCount", pendingCount);
		map.put("pendingReviewCount", pendingReviewCount);
		String json = JSonUtil.bean2json(map);
		response.getWriter().print(json);
		return null;
	}

	@Override
	public OperatorlogDTO buildOperatorLog() {
		return null;
	}

	public void setChargeBackService(ChargeBackService chargeBackService) {
		this.chargeBackService = chargeBackService;
	}

	public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

	public void setTxncoreClientService(CrosspayTxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}

	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}
}
