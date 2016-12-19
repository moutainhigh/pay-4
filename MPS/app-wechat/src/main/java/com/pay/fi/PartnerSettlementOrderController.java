package com.pay.fi;

import java.lang.reflect.InvocationTargetException;
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

import com.pay.app.base.common.pagination.PageUtil;
import com.pay.fi.dto.PartnerSettlementOrder;
import com.pay.fi.hession.SettlementOrderService;
import com.pay.inf.dao.Page;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.DateUtil;
import com.pay.util.MapUtil;

/**
 * 运单管理
 * 
 * @author Steven Lee
 *
 */
public class PartnerSettlementOrderController extends MultiActionController {

	private final Log log = LogFactory
			.getLog(PartnerSettlementOrderController.class);
	private String queryView;
	/** 每页显示记录数 **/
	protected int pageSize; 
	private String queryNoSettView;
	private SettlementOrderService settlementOrderService;

	public void setQueryView(String queryView) {
		this.queryView = queryView;
	}

	public void setSettlementOrderService(
			SettlementOrderService settlementOrderService) {
		this.settlementOrderService = settlementOrderService;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView view = new ModelAndView(queryView);
		view.addObject("startTime", new SimpleDateFormat(
				"yyyy-MM-dd 00:00").format(DateUtil
				.skipDateTime(new Date(), -3)));
		view.addObject("endTime",
				new SimpleDateFormat("yyyy-MM-dd 00:00").format(DateUtil
						.skipDateTime(new Date(), 1)));
		return view;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param expressTracking
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response,
			PartnerSettlementOrder partnerSettlementOrder)
			throws IllegalAccessException, InvocationTargetException {
		String memberCode = (String) request.getSession().getAttribute(
				"memberCode");
		
		String applyStart = request.getParameter("startTime");
		String applyEnd = request.getParameter("endTime");
		String settleStart = request.getParameter("settleStart");
		String settleEnd = request.getParameter("settleEnd");
		String settlementFlag = request.getParameter("settlementFlag");
		
        if(applyEnd!=null&&applyEnd.length()>0)
        	partnerSettlementOrder.setEndTime(applyEnd);
        if(applyStart!=null&&applyStart.length()>0)
        	partnerSettlementOrder.setStartTime(applyStart);
        
        if(settleStart!=null&&settleStart.length()>0)
        	partnerSettlementOrder.setSettleStart(settleStart);
		if(settleEnd!=null
				&&settleEnd.length()>0){
            partnerSettlementOrder.setSettleEnd(settleEnd);
		}
		
		/*if(settlementFlag!=null&&settlementFlag.length()>0){
			partnerSettlementOrder.setSettlementFlg(Integer.valueOf(settlementFlag));
		}*/
		
		partnerSettlementOrder.setSettlementFlg(1);
		
		ModelAndView mv = new ModelAndView(queryView);
		mv.addObject("startTime", applyStart);
		mv.addObject("endTime", applyEnd);
		partnerSettlementOrder.setPartnerId(Long.valueOf(memberCode));
		
		// 页面参数
		int pager_offset = 1; // 当前页码
		int page_size = 15; // 每页条数
		pager_offset = request.getParameter("pager_offset") == null ? 1
				: Integer.parseInt(request.getParameter("pager_offset")); // 获取页面传过来的页码

		if (request.getParameter("numCount") != null
				&& !"".equals(request.getParameter("numCount"))) {// 获取页面传入的每页显示记录条数
			page_size = Integer.parseInt(request.getParameter("numCount"));
			if (page_size == 0)
				page_size = 15;// 此处为避免分页计算时出现被0除的情况
		}
		
		try {
			
			
			Page page = new Page();
			page.setPageNo(pager_offset);
			page.setPageSize(page_size);
			
			Map resultMap = settlementOrderService.settlementQuery(partnerSettlementOrder, page);
			
			String responseCode = (String) resultMap.get("responseCode");
			String responseDesc = (String) resultMap.get("responseDesc");
			List<Map> returnMap = (List<Map>) resultMap.get("list");
			Map pageMap = (Map) resultMap.get("page");
			page = MapUtil.map2Object(Page.class, pageMap);
			
			List<PartnerSettlementOrder> list = MapUtil.map2List(
					PartnerSettlementOrder.class, returnMap);
			
			Map resultMap_ = new HashMap();
			resultMap_.putAll(MapUtil.bean2map(partnerSettlementOrder));
			resultMap_.put("resultList",list);
			
			PageUtil pu = new PageUtil(page.getPageNo(), 
					page.getPageSize(),page.getTotalCount());// 分页处理
			resultMap_.put("pu", pu);
			
			CurrencyCodeEnum[] currencyCodeEnum = CurrencyCodeEnum.values();
			
			return mv.addAllObjects(resultMap_).addObject(
					"currencyCodeEnum", currencyCodeEnum);
		} catch (Exception e) {
			e.printStackTrace();
			String errorCode = "";
			mv.addObject("errorMsg", e.getMessage() + errorCode);
			PageUtil pu = new PageUtil(0, 0, 0);// 分页处理
			mv.addObject("pu", pu);
			return mv;
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param expressTracking
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView queryNosett(HttpServletRequest request,
			HttpServletResponse response,
			PartnerSettlementOrder partnerSettlementOrder)
			throws IllegalAccessException, InvocationTargetException {
		String memberCode = (String) request.getSession().getAttribute(
				"memberCode");
		ModelAndView mv = new ModelAndView(queryNoSettView);
		
		mv.addObject("trade_startTime", new SimpleDateFormat(
				"yyyy-MM-dd 00:00").format(DateUtil
				.skipDateTime(new Date(), -3)));
		mv.addObject("trade_endTime",
				new SimpleDateFormat("yyyy-MM-dd 00:00").format(DateUtil
						.skipDateTime(new Date(), 1)));
		
		String applyStart = request.getParameter("applyStart");
		String applyEnd = request.getParameter("applyEnd");
		
		if(applyStart!=null&&applyEnd!=null
				&&applyStart.length()>0&&applyEnd.length()>0){
			partnerSettlementOrder.setStartTime(applyStart);
			partnerSettlementOrder.setEndTime(applyEnd);
			mv.addObject("trade_startTime", applyStart);
			mv.addObject("trade_endTime", applyEnd);
		}

		// 页面参数
		int pager_offset = 1; // 当前页码
		int page_size = 15; // 每页条数
		pager_offset = request.getParameter("pager_offset") == null ? 1
				: Integer.parseInt(request.getParameter("pager_offset")); // 获取页面传过来的页码

		if (request.getParameter("numCount") != null
				&& !"".equals(request.getParameter("numCount"))) {// 获取页面传入的每页显示记录条数
			page_size = Integer.parseInt(request.getParameter("numCount"));
			if (page_size == 0)
				page_size = 15;// 此处为避免分页计算时出现被0除的情况
		}
		
		
		partnerSettlementOrder.setPartnerId(Long.valueOf(memberCode));
		partnerSettlementOrder.setSettlementFlg(0);
		try {
			Page page = new Page();
			page.setPageNo(pager_offset);
            page.setPageSize(page_size);
            
            partnerSettlementOrder.setSettlementFlg(0);
			Map resultMap = settlementOrderService.settlementQuery(partnerSettlementOrder, page);
			
			String responseCode = (String) resultMap.get("responseCode");
			String responseDesc = (String) resultMap.get("responseDesc");
			List<Map> returnMap = (List<Map>) resultMap.get("list");
			Map pageMap = (Map) resultMap.get("page");
			page = MapUtil.map2Object(Page.class, pageMap);
			
			List<PartnerSettlementOrder> list = MapUtil.map2List(
					PartnerSettlementOrder.class, returnMap);
			
			Map resultMap_ = new HashMap();
			resultMap_.putAll(MapUtil.bean2map(partnerSettlementOrder));
			resultMap_.put("resultList", list);
			
			
			PageUtil pu = new PageUtil(page.getPageNo(), 
					page.getPageSize(),page.getTotalCount());// 分页处理
			resultMap_.put("pu", pu);
			
			CurrencyCodeEnum[] currencyCodeEnum = CurrencyCodeEnum.values();
			return mv.addObject(
					"currencyCodeEnum", currencyCodeEnum).addAllObjects(resultMap_);
		} catch (Exception e) {
			e.printStackTrace();
			String errorCode = "";
			mv.addObject("errorMsg", e.getMessage() + errorCode);
			PageUtil pu = new PageUtil(0, 0, 0);// 分页处理
			mv.addObject("pu", pu);
			return mv;
		}
	}

	public void setQueryNoSettView(String queryNoSettView) {
		this.queryNoSettView = queryNoSettView;
	}
	
	

}
