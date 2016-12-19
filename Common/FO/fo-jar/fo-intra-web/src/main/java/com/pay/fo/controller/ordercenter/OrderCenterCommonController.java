/** @Description 
 * @project 	order-center-web
 * @file 		OrderCenterCommonController.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-6		Henry.Zeng			Create 
 */
package com.pay.fo.controller.ordercenter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.ModelAndView;

import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.fo.controller.ordercenter.util.OrderCenterUtil;
import com.pay.fundout.channel.dto.channel.FundoutChannelQueryDTO;
import com.pay.fundout.channel.service.channel.FundoutChannelService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.controller.AbstractBaseController;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.service.inf.input.BankInfoFacadeService;
import com.pay.poss.service.ordercenter.OrderCenterCommonService;
import com.pay.poss.service.ordercenter.common.AbstractOrderCenterCommonService;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;
import com.pay.util.DateUtil;
import com.pay.util.NumberUtil;
import com.pay.util.StringUtil;
import com.pay.util.TimeUtil;

/**
 * <p>
 * 订单中心Controller
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-11-6
 * @see
 */
public class OrderCenterCommonController extends AbstractBaseController {
	/**
	 * 应于配置对应的订单交易子类Service
	 */
	private transient Map<String, OrderCenterCommonService> orderCenterCommonServiceMap;
	private BankInfoFacadeService bankFacadeService;
	private MemberQueryService memberQueryService;
	private FundoutChannelService fundoutChannelService;
	private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

	public void setOrderCenterCommonServiceMap(
			final Map<String, OrderCenterCommonService> param) {
		this.orderCenterCommonServiceMap = param;
	}

	public void setBankFacadeService(BankInfoFacadeService bankFacadeService) {
		this.bankFacadeService = bankFacadeService;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	public void setFundoutChannelService(FundoutChannelService fundoutChannelService) {
		this.fundoutChannelService = fundoutChannelService;
	}

	/**
	 * index 进入订单查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderStatus", OrderCenterUtil.getOrderStatus());
		map.put("tradeStatus", OrderCenterUtil.getTradeStatus());
		String orderType = request.getParameter("orderType");
		map.put("orderType", orderType);
		map.put("startTime", TimeUtil.getDate(-3));
		map.put("endTime", TimeUtil.getDate());
		List<FundoutChannelQueryDTO> list = fundoutChannelService.getFundoutChannelList();
		return new ModelAndView(URL("ordercentermanagerInit"), map).addObject("fundoutChannelList", list);
	}
	
	public ModelAndView exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Workbook workbook = null;
		XLSTransformer transformer = new XLSTransformer();
		
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		OrderCenterQueryDTO orderCenterQueryDTO = new OrderCenterQueryDTO();
		bind(request, orderCenterQueryDTO, "orderCenterQueryDTO", null);

		if (StringUtils.isNotEmpty(startTime)) {
			orderCenterQueryDTO.setStartTime(sf.parse(startTime));
		}
		if (StringUtils.isNotEmpty(endTime)) {
			orderCenterQueryDTO.setEndTime(sf.parse(endTime));
		}
		
		String orderType = orderCenterQueryDTO.getOrderType();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderStatus", OrderCenterUtil.getOrderStatus());
		map.put("tradeStatus", OrderCenterUtil.getTradeStatus());
		map.put("bankList", bankFacadeService.getWithdrawBankList());
		map.put("orderType", orderType);
		
		String memberCode = orderCenterQueryDTO.getMemberCode();
		if(!StringUtil.isEmpty(memberCode)&&!NumberUtil.isDouble(memberCode)){
			map.put("errorMsg", "会员必须为数字！");
			return new ModelAndView(URL(orderType), map);
		}
		
		String orderKy = orderCenterQueryDTO.getOrderKy();
		if(!StringUtil.isEmpty(orderKy)&&!NumberUtil.isDouble(orderKy)){
			map.put("errorMsg", "系统交易号必须为数字！");
			return new ModelAndView(URL(orderType), map);
		}
		
		String bankOrderKy = orderCenterQueryDTO.getBankOrderKy();
		if(!StringUtil.isEmpty(bankOrderKy)&&!NumberUtil.isDouble(bankOrderKy)){
			map.put("errorMsg", "银行订单号必须为数字！");
			return new ModelAndView(URL(orderType), map);
		}
		
		String merchantOrderKy = orderCenterQueryDTO.getMerchantOrderKy();
		if(!StringUtil.isEmpty(merchantOrderKy)&&!NumberUtil.isDouble(merchantOrderKy)){
			map.put("errorMsg", "商家订单号必须为数字！");
			return new ModelAndView(URL(orderType), map);
		}
		
		OrderCenterCommonService orderCenterCommonService = orderCenterCommonServiceMap.get(orderType);

		Page<OrderCenterResultDTO> page = PageUtils.getPage(request);
		page.setPageSize(3000);
		
		Map<String, Page<OrderCenterResultDTO>> outMap = null;
		if (null != orderCenterCommonService) {
			outMap = orderCenterCommonService.queryResultList(page,orderCenterQueryDTO);
		}
		


		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("list", outMap.get("page").getResult());
		if("WITHDRAW".equals(orderType)){
			workbook = transformer.transformXLS(getClass().getResourceAsStream("/properties/withdrawTemplate.xls"), beans);
		}else if("PAY2BANK".equals(orderType)){
			workbook = transformer.transformXLS(getClass().getResourceAsStream("/properties/pay2bankTemplate.xls"), beans);
		}else if("BATCH2BANK".equals(orderType)){
			workbook = transformer.transformXLS(getClass().getResourceAsStream("/properties/batchPay2bankTemplate.xls"), beans);
		}
		String currentDate = DateUtil.getNowDate("yyyyMMdd");
		response.setContentType("application/octet-stream; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(("orderCenterDataFile" + currentDate + "-" + System.currentTimeMillis() + ".xls").getBytes(),
						"ISO8859_1"));
		ServletOutputStream outputStream = response.getOutputStream();
		try {
			workbook.write(outputStream);
			response.setStatus(HttpServletResponse.SC_OK);
			outputStream.flush();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
		return null;
	}

	public ModelAndView processOrderCenter(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		/**
		 * 查询订单交易类型父类Service
		 */
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		OrderCenterQueryDTO orderCenterQueryDTO = new OrderCenterQueryDTO();
		bind(request, orderCenterQueryDTO, "orderCenterQueryDTO", null);

		if (StringUtils.isNotEmpty(startTime)) {
			orderCenterQueryDTO.setStartTime(sf.parse(startTime));
		}
		if (StringUtils.isNotEmpty(endTime)) {
			orderCenterQueryDTO.setEndTime(sf.parse(endTime));
		}
		
		String orderType = orderCenterQueryDTO.getOrderType();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderStatus", OrderCenterUtil.getOrderStatus());
		map.put("tradeStatus", OrderCenterUtil.getTradeStatus());
		map.put("bankList", bankFacadeService.getWithdrawBankList());
		map.put("orderType", orderType);
		
		String memberCode = orderCenterQueryDTO.getMemberCode();
		if(!StringUtil.isEmpty(memberCode)&&!NumberUtil.isDouble(memberCode)){
			map.put("errorMsg", "会员必须为数字！");
			return new ModelAndView(URL(orderType), map);
		}
		
		String orderKy = orderCenterQueryDTO.getOrderKy();
		if(!StringUtil.isEmpty(orderKy)&&!NumberUtil.isDouble(orderKy)){
			map.put("errorMsg", "系统交易号必须为数字！");
			return new ModelAndView(URL(orderType), map);
		}
		
		String bankOrderKy = orderCenterQueryDTO.getBankOrderKy();
		if(!StringUtil.isEmpty(bankOrderKy)&&!NumberUtil.isDouble(bankOrderKy)){
			map.put("errorMsg", "银行订单号必须为数字！");
			return new ModelAndView(URL(orderType), map);
		}
		
		String merchantOrderKy = orderCenterQueryDTO.getMerchantOrderKy();
//		if(!StringUtil.isEmpty(merchantOrderKy)){
//			map.put("errorMsg", "商家订单号必须为数字、字母、下划线！");
//			return new ModelAndView(URL(orderType), map);
//		}
		
		OrderCenterCommonService orderCenterCommonService = orderCenterCommonServiceMap
				.get(orderType);

		Page<OrderCenterResultDTO> page = PageUtils.getPage(request);
		
		
		Map<String, Page<OrderCenterResultDTO>> outMap = null;
		if (null != orderCenterCommonService) {
			outMap = orderCenterCommonService.queryResultList(page,
					orderCenterQueryDTO);
			map.put("page", outMap.get("page"));
		}


		return new ModelAndView(URL(orderType), map);
	}

	
	/**
	 * 订单详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView processOrderDetail(HttpServletRequest request,
			HttpServletResponse response) {

		OrderCenterQueryDTO orderCenterQueryDTO = new OrderCenterQueryDTO();
		bind(request, orderCenterQueryDTO, "orderCenterQueryDTO", null);
		String orderType = orderCenterQueryDTO.getOrderType();
		OrderCenterCommonService orderCenterCommonService = orderCenterCommonServiceMap
				.get(orderType);
		Map<String, Object> map = new HashMap<String, Object>();
//		Map<String, Object> entrieMap = orderCenterCommonService
//				.queryOrderEntrie(orderCenterQueryDTO);
//		Map<String, Object> relationMap = orderCenterCommonService
//				.queryOrderRelation(orderCenterQueryDTO);
		Map<String, Object> historyMap = orderCenterCommonService
				.queryOrderHistory(orderCenterQueryDTO);
		OrderDetailDTO orderDetailDTO = orderCenterCommonService
				.queryOrderDetail(orderCenterQueryDTO);
		log.info("----------出款渠道分类号：" + orderCenterQueryDTO.getPaymentCatagory() + "----------");
		log.info("----------订单状态为：" + orderDetailDTO.getOrderStatus() + "----------");
//		if((String.valueOf(PaymentCatagoryEnum.CONSUME_CARD.getCode())).equals(orderCenterQueryDTO.getPaymentCatagory()) && orderDetailDTO.getOrderStatus().equals("111")){
//			// 如果是消费卡支付类型并且明细里面是成功的，那么去查是否有结转金额订单
//			Map carryOverOrderMap = orderDetailDTO.getCarryOverOrderMap();
//			// 如果结转金额实体不为空，则返回至前台页面
//			if(carryOverOrderMap != null && carryOverOrderMap.size() > 0){
//				map.put("carryOverOrderMap", carryOverOrderMap);
//			}
//		}

		orderDetailDTO.setOrderType(orderCenterQueryDTO.getOrderType());
		map.put("orderDetailDTO", orderDetailDTO);
		map.put("orderCenterQueryDTO", orderCenterQueryDTO);
		map.put("historyList", historyMap != null ? historyMap
				.get("historyList") : null);
//		map.put("relationList", relationMap != null ? relationMap
//				.get("relationList") : null);
//		map.put("entrieList", entrieMap != null ? entrieMap.get("entrieList")
//				: null);

		map.put("accountTypes", OrderCenterUtil.getAccountTypes());
		map.put("orderStatus", OrderCenterUtil.getOrderStatus());
		map.put("tradeStatus", OrderCenterUtil.getTradeStatus());
		map.put("bankList", bankFacadeService.getWithdrawBankList());
		return new ModelAndView(URL("orderdetailtab"), map);
	}
	
	/**
	 * 结转金额详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView processCarryOverOrderDetail(HttpServletRequest request, HttpServletResponse response) {
		String successAmount = request.getParameter("successAmount"); // 成功金额
		String surplusAmount = request.getParameter("surplusAmount"); // 结转充值金额
		String convertFee = request.getParameter("convertFee"); // 结转手续费金额
		
		Map result = new HashMap();
		result.put("successAmount", successAmount);
		result.put("surplusAmount", surplusAmount);
		result.put("convertFee", convertFee);
		
		return new ModelAndView(URL("carryoverorderdetail"), result);
	}
	
	/**
	 * 获取订单类型支持的所有出款渠道
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView getFundoutChannelListByProductCode(HttpServletRequest request,HttpServletResponse response) { 
		String productCode = request.getParameter("productCode");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("productCode", productCode);
		List<FundoutChannelQueryDTO> list = fundoutChannelService.getFundoutChannelListByProductCode(params);
		return new ModelAndView(URL("fundoutchannelSelect")).addObject("fundoutChannelList", list);
	}
	
	/**
	 * 获取用户信息MemberCode
	 * 
	 * @param loginName
	 * @return loginName
	 */
	protected String getMemberCode(String loginName) {
		MemberInfoDto memberInfoDto = null;
		String memberCode = "";
		try {
			if (StringUtils.isNotEmpty(loginName)) {
				memberInfoDto = memberQueryService.doQueryMemberInfoNsTx(
						loginName, null, null, null);
				memberCode = memberInfoDto != null ? String
						.valueOf(memberInfoDto.getMemberCode()) : "";
			}
		} catch (MaMemberQueryException e) {
			LogUtil.error(AbstractOrderCenterCommonService.class, "获取用户信息",
					OPSTATUS.EXCEPTION, "", "", e.getMessage(), "", e
							.getMessage());
		}
		return memberCode;
	}

	/**
	 * 获取用户信息MemberCode
	 * 
	 * @param memberCode
	 * @return String
	 */
	protected String getLoginName(String memberCode) {
		MemberInfoDto memberInfoDto = null;
		String loginName = "";
		try {
			if (StringUtils.isNotEmpty(memberCode)) {
				memberInfoDto = memberQueryService.doQueryMemberInfoNsTx(null,
						Long.valueOf(memberCode), null, null);
				loginName = memberInfoDto == null ? "" : memberInfoDto
						.getLoginName();
			}
		} catch (MaMemberQueryException e) {
			LogUtil.error(AbstractOrderCenterCommonService.class, "获取用户信息",
					OPSTATUS.EXCEPTION, "", "", e.getMessage(), "", e
							.getMessage());
		} catch (NumberFormatException e) {
			LogUtil.error(AbstractOrderCenterCommonService.class, "获取用户信息",
					OPSTATUS.EXCEPTION, "", "", e.getMessage(), "", e
							.getMessage());
		}
		return loginName;
	}
	
	
}
