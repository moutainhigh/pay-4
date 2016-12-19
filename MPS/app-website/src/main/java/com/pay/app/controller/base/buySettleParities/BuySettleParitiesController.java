package com.pay.app.controller.base.buySettleParities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pay.acc.acct.service.AcctService;
import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.acctattrib.service.AcctAttribService;
import com.pay.acct.buySettleParities.model.BuysettlePoundageConfig;
import com.pay.acct.buySettleParities.service.BuySettleForeignCurrencyService;
import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.client.SettleCoreClientService;
import com.pay.app.controller.base.vo.MapVO;
import com.pay.fi.dto.BuysettleOrder;
import com.pay.fo.order.service.validate.PaymentValidateService;
import com.pay.inf.dao.Page;
import com.pay.util.DateUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 购结汇
 * 
 * @author 蒋文哲
 *
 */
@Controller
public class BuySettleParitiesController {

	Log logger = LogFactory.getLog(BuySettleParitiesController.class);
	@Autowired
	@Qualifier(value = "acc-acctService")
	private AcctService acctService;

	@Autowired
	@Qualifier(value = "settleCoreClientService")
	private SettleCoreClientService settleCoreClientService;

	@Autowired
	@Qualifier(value = "buySettleForeignCurrencyService")
	private BuySettleForeignCurrencyService buySettleForeignCurrencyServiceImpl;

	@Autowired
	@Qualifier(value = "fo-order-paymentValidateService")
	private PaymentValidateService paymentValidateService;

	@Autowired
	@Qualifier(value = "acc-acctAttribService")
	private AcctAttribService acctAttribService;

	/**
	 * 购汇申请into
	 * 
	 * @param result
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/corp/BuySettleParitiesController/buyParities/into.do")
	public String buyParitiesInto(Map<String, Object> result, HttpSession session) {
		try {
			initAcctount(result, session);
			result.put("cuuryCodeList", capitalPoolManageQueryByStatus(session));
		} catch (Exception e) {
			logger.error("购汇申请into错误", e);
		}
		return "/base/buySettleParities/buyParities";
	}

	/**
	 * 结汇申请into
	 * 
	 * @return
	 */
	@RequestMapping(value = "/corp/BuySettleParitiesController/settleParitiesApply/into.do")
	public String settleParitiesApplyInto(Map<String, Object> result, HttpSession session) {
		try {
			initAcctount(result, session);
			result.put("cuuryCodeList", capitalPoolManageQueryByStatus(session));
		} catch (Exception e) {
			logger.error("结汇申请into", e);
		}
		return "/base/buySettleParities/settleParitiesApply";
	}

	/**
	 * 购结汇查询into
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/corp/BuySettleParitiesController/buySettleParitiesQuery/into.do")
	public String buySettleParitiesQueryInto(HttpServletRequest request, Map<String, Object> result,
			HttpSession session) {
		try {
			initAcctount(result, session);
			String pageCurrent = request.getParameter("pager_offset");
			int pageSizeNum = 20;
			int pageCurrentNum = 1;
			if (StringUtils.isNotBlank(pageCurrent)) {
				pageCurrentNum = Integer.valueOf(pageCurrent);
			}
			Map<String, Object> params = new HashMap<String, Object>();
			Page<BuysettleOrder> page = new Page<BuysettleOrder>();
			page.setPageNo(pageCurrentNum);
			page.setPageSize(pageSizeNum);
			Map<String, Object> buysettleOrderVoMap = new HashMap<String, Object>();
			buysettleOrderVoMap.put("exchangeNo", "0");
			buysettleOrderVoMap.put("partnerId",session.getAttribute("memberCode"));
			params.put("page", page);
			params.put("BuysettleOrder", buysettleOrderVoMap);
			Map<String, Object> BuySettleOrderMap = settleCoreClientService.findBuySettleOrderByMap(params);
			Page<Object> newPage = MapUtil.map2Object(Page.class, (Map<?, ?>) BuySettleOrderMap.get("page"));
			List<Map<String, Object>> infoList = (List<Map<String, Object>>) BuySettleOrderMap.get("result");
			List<BuysettleOrder> buysettleOrders = new ArrayList<BuysettleOrder>();
			for (Map<String, Object> map : infoList) {
				buysettleOrders.add(MapUtil.map2Object(BuysettleOrder.class, map));
			}
			PageUtil pu = new PageUtil(pageCurrentNum, pageSizeNum, newPage.getTotalCount());// 分页处理
			result.put("infoList", buysettleOrders);
			result.put("pu", pu);
			result.put("cuuryCodeList", capitalPoolManageQueryByStatus(session));
		} catch (Exception e) {
			logger.error("购结汇查询into错误", e);
		}

		return "/base/buySettleParities/buySettleParitiesQuery";
	}

	/**
	 * 购结汇查询
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/BuySettleParitiesController/buySettleParitiesQuery/query.do")
	public String buySettleParitiesQueryInto(MapVO mapVO, HttpServletRequest request, Map<String, Object> result,
			HttpSession session) {
		try {
			initAcctount(result, session);
			String pageCurrent = request.getParameter("pager_offset");
			int pageSizeNum = 20;
			int pageCurrentNum = 1;
			if (StringUtils.isNotBlank(pageCurrent)) {
				pageCurrentNum = Integer.valueOf(pageCurrent);
			}
			Map<String, Object> params = new HashMap<String, Object>();
			Page<BuysettleOrder> page = new Page<BuysettleOrder>();
			page.setPageNo(pageCurrentNum);
			page.setPageSize(pageSizeNum);
			Map<String, Object> buysettleOrderVoMap = new HashMap<String, Object>();
			buysettleOrderVoMap.putAll(mapVO.getData());
			buysettleOrderVoMap.put("exchangeNo","0");
			buysettleOrderVoMap.put("partnerId",session.getAttribute("memberCode"));
			params.put("page", page);
			params.put("BuysettleOrder", buysettleOrderVoMap);
			Map<String, Object> BuySettleOrderMap = settleCoreClientService.findBuySettleOrderByMap(params);
			Page<Object> newPage = MapUtil.map2Object(Page.class, (Map<?, ?>) BuySettleOrderMap.get("page"));
			List<Map<String, Object>> infoList = (List<Map<String, Object>>) BuySettleOrderMap.get("result");
			List<BuysettleOrder> buysettleOrders = new ArrayList<BuysettleOrder>();
			for (Map<String, Object> map : infoList) {
				buysettleOrders.add(MapUtil.map2Object(BuysettleOrder.class, map));
			}
			PageUtil pu = new PageUtil(pageCurrentNum, pageSizeNum, newPage.getTotalCount());// 分页处理
			result.put("infoList", buysettleOrders);
			result.put("pu", pu);
			result.put("cuuryCodeList", capitalPoolManageQueryByStatus(session));
		} catch (Exception e) {
			logger.error("购结汇查询", e);
		}

		return "/base/buySettleParities/buySettleParitiesQuery";
	}

	/**
	 * 结汇查询账号余额
	 */
	@RequestMapping(value = "/BuySettleParitiesController/queryAcctount.do")
	@ResponseBody
	public Map<String, Object> queryAcctount(HttpSession session, String curCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("memberCode", session.getAttribute("memberCode"));
			params.put("curCode", curCode);
			Map<String, Object> acct = acctService.queryAcctBycurCodeAndmenberCode(params);
			result.put("data", acct);
		} catch (Exception e) {
			logger.error("结汇查询账号余额", e);
		}

		return result;
	}

	/**
	 * 查询汇率
	 * 
	 * @param currency
	 * @param targetCurrency
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/BuySettleParitiesController/queryExchange.do")
	@ResponseBody
	public Map<String, String> exchangeQuery(String currency, String targetCurrency, String type) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			result = settleCoreClientService.CurrencyBaseRateQuery(currency, targetCurrency, type, "1",
					DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
		} catch (Exception e) {
			logger.error("查询汇率错误", e);
		}
		return result;
	}

	/**
	 * 结汇查询手续费汇率(汇率中间价)
	 */
	@RequestMapping(value = "/BuySettleParitiesController/querysettleFeeFactorage.do")
	@ResponseBody
	public Map<String, Object> querysettleFeeFactorage(String targetCurrency) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, String> tradeResult = settleCoreClientService.CurrencyBaseRateQuery("USD", targetCurrency, "1",
					"1", DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
			Map<String, String> settleresult = settleCoreClientService.CurrencyBaseRateQuery("USD", targetCurrency, "2",
					"1", DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
			BigDecimal settleFeeFactorage = (BigDecimal.valueOf(Double.valueOf(tradeResult.get("exchangeRate")))
					.add((BigDecimal.valueOf(Double.valueOf(settleresult.get("exchangeRate")))))
					.divide(BigDecimal.valueOf(2)));
			result.put("settleFeeFactorage", settleFeeFactorage);
		} catch (Exception e) {
			logger.error("结汇查询手续费汇率(汇率中间价)错误", e);
		}
		return result;
	}

	/**
	 * 查询手续费
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/BuySettleParitiesController/queryFactorage.do")
	@ResponseBody
	public Map<String, Object> queryFactorage(HttpSession session, String status) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("partnerId", session.getAttribute("memberCode"));
			param.put("status", status);
			BuysettlePoundageConfig buysettlePoundageConfig = buySettleForeignCurrencyServiceImpl
					.queryBuySettleForeignCurrency(param);
			if (buysettlePoundageConfig == null) {
				param.put("partnerId", "0");
				buysettlePoundageConfig = buySettleForeignCurrencyServiceImpl.queryBuySettleForeignCurrency(param);
			}
			result.put("factorage", buysettlePoundageConfig);
		} catch (Exception e) {
			logger.error("查询手续费错误", e);
		}
		return result;
	}

	/**
	 * 购汇申请支付
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/BuySettleParitiesController/buyParitiesPay.do")
	@ResponseBody
	public Map<String, Object> buyParitiesPay(MapVO mapVO, final HttpServletRequest request, HttpSession session,
			String payPassword) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, String> reqparams = mapVO.getData();
			logger.debug("前台参数"+reqparams);
			LoginSession loginSession = SessionHelper.getLoginSession(request);
			reqparams.put("partnerId", session.getAttribute("memberCode").toString());
			String message = paymentValidateService.validatePaymentPassword(
					Long.valueOf(session.getAttribute("memberCode").toString()), 101, loginSession.getOperatorId(),
					payPassword);
			if (StringUtil.isNull(message)) {
				Map<String, Object> resultMap = settleCoreClientService.findcapitalPool(reqparams);
				List<Map<String, Object>> resultList = (List<Map<String, Object>>) resultMap.get("list");
				Map<String, Object> map = resultList.get(0);
				BigDecimal buyAmount = new BigDecimal(map.get("buyAmount").toString()).multiply(new BigDecimal(1000));
				logger.debug("资金池账户资金"+buyAmount);
				int compareTo = buyAmount.compareTo(new BigDecimal(reqparams.get("orderAmount")));
				logger.debug("金额对比"+compareTo);
				if (compareTo == -1) {
					result.put("repCode", 5);
					result.put("message", "风控拒绝");
				} else {
					String repCode = saveBuySettleTrade(reqparams);
					result.put("repCode", repCode);
				}
			} else {
				result.put("repCode", -2);
				result.put("message", message);
			}
		} catch (Exception e) {
			logger.error("购汇申请支付错误", e);
			result.put("repCode", -3);
			result.put("message", "购汇申请支付错误");
		}

		return result;
	}

	/**
	 * 结汇申请支付
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/BuySettleParitiesController/settleParitiesPay.do")
	@ResponseBody
	public Map<String, Object> settleParitiesPay(MapVO mapVO, final HttpServletRequest request, HttpSession session,
			String payPassword) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, String> reqparams = mapVO.getData();
			logger.debug("前台参数"+reqparams);
			LoginSession loginSession = SessionHelper.getLoginSession(request);
			reqparams.put("partnerId", session.getAttribute("memberCode").toString());
			String message = paymentValidateService.validatePaymentPassword(
					Long.valueOf(session.getAttribute("memberCode").toString()), 101, loginSession.getOperatorId(),
					payPassword);
			if (StringUtil.isNull(message)) {
				Map<String, Object> resultMap = settleCoreClientService.findcapitalPool(reqparams);
				List<Map<String, Object>> resultList = (List<Map<String, Object>>) resultMap.get("list");
				Map<String, Object> map = resultList.get(0);
				BigDecimal buyAmount = new BigDecimal(map.get("buyAmount").toString()).multiply(new BigDecimal(1000));
				logger.debug("资金池账户资金"+buyAmount);
				int compareTo = buyAmount.compareTo(new BigDecimal(reqparams.get("orderAmount")));
				logger.debug("金额对比"+compareTo);
				if (compareTo == -1) {
					result.put("repCode", 5);
					result.put("message", "风控拒绝");
				} else {
					String repCode = saveSettleTrade(reqparams);
					result.put("repCode", repCode);
				}
			} else {
				result.put("repCode", -2);
				result.put("message", message);
			}
		} catch (Exception e) {
			logger.error("结汇申请支付错误", e);
			result.put("repCode", -3);
			result.put("message", "购汇申请支付错误");
		}
		return result;
	}

	/**
	 * 查询资金池账户是否启用
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/BuySettleParitiesController/getCuuryCode.do")
	@ResponseBody
	public Map<String, Object> getCuuryCode() {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> CapitalPoolManageMap = settleCoreClientService
					.capitalPoolManageQueryByStatus(new HashMap<String, Object>());
			List<Map<String, Object>> CapitalPoolManageList = (List<Map<String, Object>>) CapitalPoolManageMap
					.get("list");
			result.put("data", CapitalPoolManageList);
		} catch (Exception e) {
			logger.error("查询资金池账户错误", e);
		}
		return result;
	}
	
	/**
	 * 导出查询结果(excel)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/BuySettleParitiesController/buySettleParitiesQuery/buySettleParitiesDownload.do")
	public String exportResults(Map<String, Object> resultMap, HttpServletRequest request, HttpServletResponse response,
			MapVO mapVO,HttpSession session) throws Exception {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			Map<String, Object> buysettleOrderVoMap = new HashMap<String, Object>();
			buysettleOrderVoMap.putAll(mapVO.getData());
			buysettleOrderVoMap.put("exchangeNo", "0");
			buysettleOrderVoMap.put("partnerId",session.getAttribute("memberCode"));
			params.put("BuysettleOrder", buysettleOrderVoMap);
			Map<String, Object> BuySettleOrderMap = settleCoreClientService.findBuySettleOrderByMapDownload(params);
			List<Map<String, Object>> infoList = (List<Map<String, Object>>) BuySettleOrderMap.get("result");
			List<BuysettleOrder> buysettleOrders = new ArrayList<BuysettleOrder>();
			for (Map<String, Object> map : infoList) {
				buysettleOrders.add(MapUtil.map2Object(BuysettleOrder.class, map));
			}
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Cache-Control", "public");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(("购结汇下载.xls").getBytes("UTF-8"), "ISO8859_1"));
			resultMap.put("incomeDetailList", buysettleOrders);
			resultMap.put("nowDate", new Date());
		} catch (Exception e) {
			logger.error("购结汇下载错误", e);
		}
		return "/base/buySettleParities/downloadBuySettleParities";
	}

	/**
	 * 查询资金池账户状态
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<String> capitalPoolManageQueryByStatus(HttpSession session) {
		List<String> result = new ArrayList<String>();
		List<String> currencyCodeVOByB = new ArrayList<String>();
		List<String> currencyCodeVOByC = new ArrayList<String>();
		Map<String, Object> CapitalPoolManageMap = settleCoreClientService
				.capitalPoolManageQueryByStatus(new HashMap<String, Object>());
		List<AcctAttribDto> accattribDtoList = acctAttribService
				.QueryByMemberCodeAndAcctTypeNsTx(Long.valueOf(session.getAttribute("memberCode").toString()));
		List<Map<String, Object>> CapitalPoolManageList = (List<Map<String, Object>>) CapitalPoolManageMap.get("list");
		for (AcctAttribDto item : accattribDtoList) {
			currencyCodeVOByB.add(item.getCurCode());
		}
		for (Map<String, Object> item : CapitalPoolManageList) {
			currencyCodeVOByC.add(item.get("currencyCode").toString());
		}
		result.addAll(currencyCodeVOByB);
		result.retainAll(currencyCodeVOByC);
		return result;
	}

	/**
	 * 保存购汇订单流水
	 * 
	 * @param reqparams
	 * @return
	 */
	private String saveBuySettleTrade(Map<String, String> reqparams) {
		Map<String, String> params = new HashMap<String, String>();
		params.putAll(reqparams);
		params.put("source", "mps");
		params.put("status", "0");
		params.put("type", "0");
		Map<String, String> result = settleCoreClientService.saveBuyOrder(params);
		return result.get("repCode");
	}

	/**
	 * 保存结汇订单流水
	 * 
	 * @param reqparams
	 * @return
	 */
	private String saveSettleTrade(Map<String, String> reqparams) {
		Map<String, String> params = new HashMap<String, String>();
		params.putAll(reqparams);
		params.put("source", "mps");
		params.put("status", "0");
		params.put("type", "1");
		Map<String, String> result = settleCoreClientService.saveSettleOrder(params);
		return result.get("repCode");
	}

	/**
	 * 初始化账号
	 * 
	 * @param result
	 * @param session
	 * @param params
	 */
	private void initAcctount(Map<String, Object> result, HttpSession session) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberCode", session.getAttribute("memberCode"));
		params.put("curCode", "CNY");
		Map<String, Object> acct = acctService.queryAcctBycurCodeAndmenberCode(params);
		result.put("data", acct);
	}

}
