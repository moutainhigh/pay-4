package com.pay.poss.controller.fi.crosspay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.client.CardBindService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

public class TokenpayQueryController extends MultiActionController {

	private static Log log = LogFactory.getLog(TokenpayQueryController.class);

	private String queryInit;

	private String queryView;

	private String detailView;
	
	 private CardBindService cardBindService;

	public void setQueryInit(String queryInit) {
		this.queryInit = queryInit;
	}

	public void setQueryView(String queryView) {
		this.queryView = queryView;
	}

	public void setDetailView(String detailView) {
		this.detailView = detailView;
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
		return new ModelAndView(queryInit);
	}

	/**
	 * 查询交易信息列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");

		String partnerId = request.getParameter("partnerId");
		String cardNo = request.getParameter("cardNo");
		String registerUserId = request.getParameter("registerUserId");
		String status = request.getParameter("status");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");

		ModelAndView mv = new ModelAndView(queryView);

		Map<String, Object> queryDetailPara = new HashMap<String, Object>();
		if(!StringUtil.isEmpty(partnerId)) {
			queryDetailPara.put("partnerId", partnerId);
		}
		if(!StringUtil.isEmpty(registerUserId)) {
			queryDetailPara.put("registerUserId", registerUserId);
		}
		if(!StringUtil.isEmpty(status)) {
			queryDetailPara.put("status", status);
		}
		if(!StringUtil.isEmpty(cardNo)) {
			queryDetailPara.put("cardNo", cardNo);
		}
		if(!StringUtil.isEmpty(beginTime)) {
			queryDetailPara.put("beginTime", beginTime);
		}
		if(!StringUtil.isEmpty(endTime)) {
			queryDetailPara.put("endTime", endTime);
		}
		
		Page page = PageUtils.getPage(request);
		queryDetailPara.put("page", page);
		
		Map<String, Object> boundTokenInfo = null;
		try {
			boundTokenInfo = this.cardBindService.queryTokenPayInfo(queryDetailPara);
			Map pageMap = (Map)boundTokenInfo.get("page");
//			page.setOrder(pageMap.get("asc").toString());
			page.setPageSize(Integer.parseInt(pageMap.get("pageSize").toString()));
			page.setTotalCount(Integer.parseInt(pageMap.get("totalCount").toString()));
			page.setTotalRecord(Integer.parseInt(pageMap.get("totalRecord").toString()));
			page.setPageNo(Integer.parseInt(pageMap.get("pageNo").toString()));
			
			
//			Map resultMap = bouncedQueryService.bouncedResultQuery(paraMap1);
			page = MapUtil.map2Object(Page.class, pageMap);
//			List<Map> map1 = (List<Map>) resultMap.get("bouncedResults");
//			return new ModelAndView(urlMap.get("batchQueryResult")).addObject("result", map1)
//					.addObject("todayDate", todayDate).addObject("page", page);
			
			
		}  catch(Exception e) {
			logger.info("Error when find card information by token", e);
		}
		
		List list = (List)boundTokenInfo.get("list");
		formatTime(list);
		formatCriteria(queryDetailPara);
		return mv.addObject("page", page).addObject("result", list).addObject("criteria", queryDetailPara);
	}
	
	private void formatCriteria(Map<String, Object> queryDetailPara) {
		queryDetailPara.put("partnerId", queryDetailPara.get("partnerId") == null ? "" : queryDetailPara.get("partnerId").toString());
		queryDetailPara.put("registerUserId", queryDetailPara.get("registerUserId") == null ? "" : queryDetailPara.get("registerUserId").toString());
		queryDetailPara.put("status", queryDetailPara.get("status") == null ? "" : queryDetailPara.get("status").toString());
		queryDetailPara.put("cardNo", queryDetailPara.get("cardNo") == null ? "" : queryDetailPara.get("cardNo").toString());
		queryDetailPara.put("beginTime", queryDetailPara.get("beginTime") == null ? "" : queryDetailPara.get("beginTime").toString());
		queryDetailPara.put("endTime", queryDetailPara.get("endTime") == null ? "" : queryDetailPara.get("endTime").toString());
	}
	
	private void formatTime(List result) {
		if(result == null || result.isEmpty()) return;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(Object map : result) {
			Map entry = (Map) map;
			String createDate = entry.get("createDate") == null ? null : entry.get("createDate").toString();
			String updateDate = entry.get("updateDate") == null ? null : entry.get("updateDate").toString();
			
			if(!StringUtil.isEmpty(createDate)) {
				entry.put("createDate", sdf.format(new Date(Long.parseLong(createDate))));
			}
			
			if(!StringUtil.isEmpty(updateDate)) {
				entry.put("updateDate", sdf.format(new Date(Long.parseLong(updateDate))));
			}
		}
		
	}

	public ModelAndView unbindCard(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String cardNo = request.getParameter("cardNo");
		String token = request.getParameter("token");
		String registerUserId = request.getParameter("registerUserId");
		String tradeType = "7002";
		
		Map<String, String> params = new HashMap<String, String>();
//		params.put("cardNo", cardNo);
		params.put("token", token);
//		params.put("registerUserId", registerUserId);
//		params.put("tradeType", tradeType);
		
		Map result = cardBindService.unbindCardByToken(params);
		String resultString = null;
		if(result != null) {
			resultString = JSonUtil.bean2json(result);
		} else {
			resultString = "{}";
		}
		
		response.setContentType("application/json;charset=utf-8");
		response.getOutputStream().write(resultString.getBytes());
		return null;
	}
	
	/**
	 * 查询订单信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView viewOrder(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		String tradeOrderNo = request.getParameter("tradeOrderNo");
		log.info("订单号tradeOrderNo：" + tradeOrderNo);
		Map<String, String> queryDetailPara = new HashMap<String, String>();
		queryDetailPara.put("tradeOrderNo", tradeOrderNo);
		Map resultMap = new HashMap();
		Map result = null;// crossPayOrderQueryService.queryOrderInBackGround(queryDetailPara);

		// ExpressTracking expressTracking = expressTrackingService
		// .findByTradeOrderNo(tradeOrderNo);
		//
		//
		// resultMap.put("result", result);

		return new ModelAndView(this.detailView, resultMap);
	}


	public CardBindService getCardBindService() {
		return cardBindService;
	}

	public void setCardBindService(CardBindService cardBindService) {
		this.cardBindService = cardBindService;
	}

	public String getQueryInit() {
		return queryInit;
	}

	public String getQueryView() {
		return queryView;
	}

	public String getDetailView() {
		return detailView;
	}

}
