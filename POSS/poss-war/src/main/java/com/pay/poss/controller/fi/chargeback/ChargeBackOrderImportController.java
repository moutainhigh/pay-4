package com.pay.poss.controller.fi.chargeback;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.file.parser.dto.ChargeBackDTO;
import com.pay.file.parser.dto.FileParseResult;
import com.pay.file.service.FileService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.poss.client.ChannelClientService;
import com.pay.poss.client.ChargeBackService;
import com.pay.poss.controller.fi.dto.ChargeBackOrder;
import com.pay.poss.dto.PaymentChannelItemDto;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.StringUtil;

/**
 * 拒付订单管理
 * 
 * @Description
 * @file ChargeBackOrderController
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2012 Corporation. All rights reserved. Date
 */
public class ChargeBackOrderImportController extends MultiActionController {

	private final Log logger = LogFactory.getLog(getClass());
	private String resultView;
	private String initView;
	private FileService fileService;
	private ChargeBackService chargeBackService;
	private ChannelClientService channelClientService;

	public void setResultView(String resultView) {
		this.resultView = resultView;
	}

	public void setInitView(String initView) {
		this.initView = initView;
	}

	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

	public void setChargeBackService(ChargeBackService chargeBackService) {
		this.chargeBackService = chargeBackService;
	}

	public void setChannelClientService(
			ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}

	/**
	 * 默认查询页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PaymentChannelItemDto paymentChannelItem = new PaymentChannelItemDto();
		List paymentChannelItems = channelClientService
				.queryChannelItem(paymentChannelItem);
		return new ModelAndView(initView).addObject("paymentChannelItems",
				paymentChannelItems);
	}

	/**
	 * 批量导入文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView importFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String cpType = StringUtil.null2String(request.getParameter("cpType"));
		String orgCode = StringUtil
				.null2String(request.getParameter("orgCode"));

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
				.getFile("file");
		String operator = SessionUserHolderUtil.getLoginId();

		FileParseResult fileParseResult = fileService.parserFileInfo(
				file.getInputStream(), file.getName(), "CHARGEBACK");

		if (null == fileParseResult) {
			logger.error("解析拒付文件报错," + orgCode);
			return new ModelAndView(resultView).addObject("resultMsg",
					"解析拒付文件报错，无法获取解析器或格式文件错误！");
		}

		List<ChargeBackDTO> list = fileParseResult.getList();

		if (null == list || list.isEmpty()) {
			return new ModelAndView(resultView).addObject("resultMsg",
					"没有合法的记录");
		}

		List<ChargeBackOrder> chargeBackOrders = new ArrayList<ChargeBackOrder>();

		for (ChargeBackDTO order : list) {
			ChargeBackOrder chargeBackOrder = new ChargeBackOrder();
			chargeBackOrder.setOperator(operator);
			chargeBackOrder.setStatus(0);
			chargeBackOrder.setOrgCode(orgCode);
			chargeBackOrder.setTradeDate(new SimpleDateFormat("yyyy-MM-dd")
					.parse(order.getTradeDate()));
			chargeBackOrder.setAuthorisation(order.getAuthorisation());
			chargeBackOrder.setCpType(Integer.valueOf(cpType));// 拒付单
			chargeBackOrder.setCpFlg(order.getCpFlg());
			chargeBackOrder.setChargeBackAmount(order.getChargeBackAmount());
			chargeBackOrder.setChargeBackMsg(order.getChargeBackMsg());
			chargeBackOrder.setChargeBackMsg1(order.getChargeBackMsg1());
			chargeBackOrder.setCpCurrencyCode(order.getCpCurrencyCode());
			chargeBackOrders.add(chargeBackOrder);
		}

		Map<String, String> resultMap = chargeBackService
				.importChargeBackOrder(chargeBackOrders);
		String resultCode = resultMap.get("responseCode");
		String resultMsg = resultMap.get("responseDesc");

		return new ModelAndView(resultView).addObject("resultCode", resultCode)
				.addObject("resultMsg", resultMsg);
	}

	/**
	 * 下载批量模版
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView downloadTemplate(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");

		String fileName = "chargeback_template.xlsx";

		String path = getServletContext()
				.getRealPath("/WEB-INF/jsp/chargeback");

		FileInputStream fi = new FileInputStream(path + "//" + fileName);
		byte[] bt = new byte[fi.available()];
		fi.read(bt);
		response.setContentType("application/msdownload;charset=UTF-8");

		// System.out.print(response.getContentType());
		response.setCharacterEncoding("UTF-8");
		fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(fileName.getBytes("UTF-8"), "GBK"));
		response.setContentLength(bt.length);
		ServletOutputStream sos = response.getOutputStream();
		sos.write(bt);
		return null;
	}

	/**
	 * 拒付审核
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView audit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String[] orderIds = request.getParameterValues("orderId");
		if (orderIds == null) {
			String orderId = request.getParameter("orderId");
			orderIds = new String[1];
			orderIds[0] = orderId;
		}

		String cpType = StringUtil.null2String(request.getParameter("cpType"));

		String status = StringUtil.null2String(request.getParameter("status"));
		String auditMsg = StringUtil.null2String(request
				.getParameter("auditMsg"));

		String operator = SessionUserHolderUtil.getLoginId();

		List<ChargeBackOrder> chargeBackOrders = new ArrayList<ChargeBackOrder>();
		for (String orderId : orderIds) {
			ChargeBackOrder chargeBackOrder = new ChargeBackOrder();
			chargeBackOrder.setOperator(operator);
			chargeBackOrder.setCpType(Integer.valueOf(cpType));
			chargeBackOrder.setStatus(Integer.valueOf(status));
			chargeBackOrder.setOrderId(Long.valueOf(orderId));
			chargeBackOrder.setAuditOperator(operator);
			chargeBackOrder.setAuditMsg(auditMsg);
			chargeBackOrders.add(chargeBackOrder);
		}

		Map<String, String> resultMap = chargeBackService
				.updateChargeBackOrder(chargeBackOrders,null);
		String resultCode = resultMap.get("responseCode");
		String resultMsg = resultMap.get("responseDesc");

		if (ResponseCodeEnum.SUCCESS.getCode().equals(resultCode)) {
			response.getWriter().print(1);
		} else {
			response.getWriter().print(resultMsg);
		}

		return null;
	}

}
