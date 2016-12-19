package com.pay.fi;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.common.pagination.PageUtil;
import com.pay.base.model.AcctInfo;
import com.pay.base.model.EnterpriseBase;
import com.pay.base.service.acct.AcctService;
import com.pay.base.service.enterprise.EnterpriseBaseService;
import com.pay.fi.dto.PartnerSettlementOrder;
import com.pay.fi.hession.SettlementOrderService;
import com.pay.inf.dao.Page;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.DateUtil;
import com.pay.util.MapUtil;

/**
 * 
 * 
 * @author Steven Lee
 *
 */
public class PartnerSettlementOrderController extends MultiActionController {

	private final Log log = LogFactory.getLog(PartnerSettlementOrderController.class);
	private String queryView;
	
	private String excelView ;
	/** 每页显示记录数 **/
	protected int pageSize; 
	private String queryNoSettView;
	private SettlementOrderService settlementOrderService;

	 /** 企业会员基本信息服务 */
   private EnterpriseBaseService  enterpriseBaseService;

   private AcctService acctService;
   
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
		//会员号
		String memberCode = (String) request.getSession().getAttribute("memberCode");
		 // 企业会员基本信息
	    EnterpriseBase enterpriseBase = enterpriseBaseService.findByMemberCode(Long.valueOf(memberCode));
	    view.addObject("enterpriseBase", enterpriseBase);
	    
	    //获取商户名下所有的交易账户
	    List<AcctInfo> acctList =null; 
	    
	    Map<String,List<AcctInfo>> acctMap = acctService.getAcctInfByMemberCode(Long.valueOf(memberCode));
	    
	    if(acctMap!=null&&acctMap.containsKey("all")){
	    	 acctList = acctMap.get("all");
	    	view.addObject("acctList", acctList);
	    }
		view.addObject("startTime", new SimpleDateFormat("yyyy-MM-dd 00:00").format(DateUtil.skipDateTime(new Date(), -2)));
		view.addObject("endTime",new SimpleDateFormat("yyyy-MM-dd 00:00").format(DateUtil.skipDateTime(new Date(), 1)));
		view.addObject("settleStart", new SimpleDateFormat("yyyy-MM-dd 00:00").format(DateUtil.skipDateTime(new Date(), -2)));
		view.addObject("settleEnd",	new SimpleDateFormat("yyyy-MM-dd 00:00").format(DateUtil.skipDateTime(new Date(), 1)));
		return view;
	}

	/** 清算查询
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
		//会员号
		String memberCode = (String) request.getSession().getAttribute("memberCode");
		//交易开始时间
		String applyStart = request.getParameter("startTime");
		//交易结束时间
		String applyEnd = request.getParameter("endTime");		
		//清算开始时间
		String settleStart = request.getParameter("settleStart");
		//清算结束时间
		String settleEnd = request.getParameter("settleEnd");		
		String settlementFlag = request.getParameter("settlementFlag");
		//结算标识
		String SettlementFlg = request.getParameter("SettlementFlg");		
		
		//选择币种
		String curCode = request.getParameter("curCode");		
		
		String feeRate = request.getParameter("feeRate");		
		//交易手续费
		String riskFee = request.getParameter("riskFee");		
		//商户订单号
		String orderId = request.getParameter("orderId");
		//风控手续费
//				String feeRate =request.getParameter("feeRate");
		//交易金额
//				String orderAmount = request.getParameter("orderAmount");
//				double orderAmount = double(request.getParameter("orderAmount"));
//				double orderAmount = Double.parseDouble(request.getParameter("orderAmount"));
		
		//交易汇率
//				String rate = request.getParameter("rate");
//				String settlementRate =request.getParameter("settlementRate");
//				double settlementRate =Double.parseDouble(request.getParameter("settlementRate"));
		//结算金额
		String amount =request.getParameter("amount");
//				double amount =Double.parseDouble(request.getParameter("amount"));
//				String assureAmount = request.getParameter("assureAmount");

//				String settlementFlg = request.getParameter("settlementFlg");
				
//				partnerSettlementOrder.setSettlementFlg(Integer.valueOf(settlementFlag));		
        if(applyEnd!=null&&applyEnd.length()>0)
        	partnerSettlementOrder.setEndTime(applyEnd);
        if(applyStart!=null&&applyStart.length()>0)
        	partnerSettlementOrder.setStartTime(applyStart);
        
        if(settleStart!=null&&settleStart.length()>0)
        	partnerSettlementOrder.setSettleStart(settleStart);
		if(settleEnd!=null&&settleEnd.length()>0){
            partnerSettlementOrder.setSettleEnd(settleEnd);
		}
		/*if(null != applyEnd && !"".equals(applyEnd)){
			partnerSettlementOrder.setStartTime(strToDate(applyEnd));
		}
		
		if(null != applyStart && !"".equals(applyStart)){
			partnerSettlementOrder.setEndTime(strToDate(applyStart));
		}*/
		if(settlementFlag!=null&&settlementFlag.length()>0){
			partnerSettlementOrder.setSettlementFlg(Integer.valueOf(settlementFlag));
		
//			partnerSettlementOrder.setSettlementFlg(settlementFlag);
		}				
		//判断交易手续费是否为空
		/*if(riskFee!=null && riskFee.length()>0)
		{
			partnerSettlementOrder.setRate(riskFee);
		}
		//判断风控手续费是否为空
		if(feeRate!=null && feeRate.length()>0)
		{
			partnerSettlementOrder.setRate(feeRate);
		}*/
//		if("0".equals(partnerSettlementOrder.getSettlementFlg()))
//		{
//			partnerSettlementOrder.setSettlementFlg(2);
//		}
//		if("0".equals(partnerSettlementOrder.getSettlementFlg()))
//		{
//			partnerSettlementOrder.setSettlementFlg(0);
//		}
		 // 企业会员基本信息
	    EnterpriseBase enterpriseBase = enterpriseBaseService.findByMemberCode(Long.valueOf(memberCode));
	   
		
		if(curCode!=null&&curCode.length()>0){
            partnerSettlementOrder.setSettlementCurrencyCode(curCode);
		}
		//获取商户名下所有的交易账户
	    List<AcctInfo> acctList =null; 
	    Map<String,List<AcctInfo>> acctMap = acctService.getAcctInfByMemberCode(Long.valueOf(memberCode));
	    
	   
		ModelAndView mv = new ModelAndView(queryView);
		
		mv.addObject("startTime", applyStart);
		mv.addObject("endTime", applyEnd);		
		mv.addObject("settleStart", settleStart);
		mv.addObject("settleEnd", settleEnd);
		
		mv.addObject("feeRate", feeRate);
		mv.addObject("riskFee", riskFee);
		mv.addObject("SettlementFlg", SettlementFlg);
		//20160419   Mack   Add below 1 one
		mv.addObject("curCode", curCode) ;
		mv.addObject("enterpriseBase", enterpriseBase);
		 if(acctMap!=null&&acctMap.containsKey("all")){
	    	 acctList = acctMap.get("all");
	    	 mv.addObject("acctList", acctList);
	    }
		
		partnerSettlementOrder.setPartnerId(Long.valueOf(memberCode));
		// 页面参数
		int pager_offset = 1; // 当前页码
		int page_size = 20; // 每页条数
		pager_offset = request.getParameter("pager_offset") == null ? 1
				: Integer.parseInt(request.getParameter("pager_offset")); // 获取页面传过来的页码

		if (request.getParameter("numCount") != null
				&& !"".equals(request.getParameter("numCount"))) {// 获取页面传入的每页显示记录条数
			page_size = Integer.parseInt(request.getParameter("numCount"));
			if (page_size == 0)
				page_size = 20;// 此处为避免分页计算时出现被0除的情况
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
			//20160419 - Mack - Add return Curcode
			return mv.addAllObjects(resultMap_).addObject(
					"currencyCodeEnum", currencyCodeEnum).addObject("orderId", orderId).addObject("settleStart", settleStart).addObject("settleEnd", settleEnd).addObject("curCode", curCode);
		} catch (Exception e) {
			e.printStackTrace();
			String errorCode = "";
			mv.addObject("errorMsg", e.getMessage() + errorCode);
			PageUtil pu = new PageUtil(0, 0, 0);// 分页处理
			mv.addObject("pu", pu);
			return mv;
		}
	}
	
	public Date strToDate(String str) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date sdata = null;
		try {
			sdata = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return sdata;
	}

	/**
	 * 下载excel
	 * @param request
	 * @param response
	 * @param partnerSettlementOrder
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView excelQuery(HttpServletRequest request,
			HttpServletResponse response,
			PartnerSettlementOrder partnerSettlementOrder)
			throws IllegalAccessException, Exception {
		String memberCode = (String) request.getSession().getAttribute(
				"memberCode");
		//获取清算查询下载标示的请求参数 by tom.wang 2016年4月21日16:05:15
		String acctName=request.getParameter("acctName");//获取选中的账户
		if(null==acctName||"".equals(acctName)){
			acctName="全部";
		}
		//交易时间
		String applyStart = request.getParameter("startTime");
		String applyEnd = request.getParameter("endTime");

		//清算时间
		String settleStart = request.getParameter("settleStart");
		String settleEnd = request.getParameter("settleEnd");
		String settlementFlag = request.getParameter("settlementFlag");
		String SettlementFlg = request.getParameter("SettlementFlg");		
		
		String feeRate = request.getParameter("feeRate");		
		//交易手续费
		String riskFee = request.getParameter("riskFee");
		//风控手续费
//		String feeRate =request.getParameter("feeRate");
		//交易金额
//		String orderAmount = request.getParameter("orderAmount");
//		double orderAmount = double(request.getParameter("orderAmount"));
//		double orderAmount = Double.parseDouble(request.getParameter("orderAmount"));		
		//交易汇率
//		String rate = request.getParameter("rate");
//		String settlementRate =request.getParameter("settlementRate");
//		double settlementRate =Double.parseDouble(request.getParameter("settlementRate"));	
		//结算金额
		String amount =request.getParameter("amount");
//		double amount =Double.parseDouble(request.getParameter("amount"));
//		String assureAmount = request.getParameter("assureAmount");
//		String settlementFlg = request.getParameter("settlementFlg");		
//		partnerSettlementOrder.setSettlementFlg(Integer.valueOf(settlementFlag));
		
        if(applyEnd!=null&&applyEnd.length()>0)
        	partnerSettlementOrder.setEndTime(applyEnd);
        if(applyStart!=null&&applyStart.length()>0)
        	partnerSettlementOrder.setStartTime(applyStart);
        
//        if(applyEnd2!=null&&applyEnd2.length()>0)
//        	partnerSettlementOrder.setEndTime(applyEnd2);
//        if(applyStart2!=null&&applyStart2.length()>0)
//        	partnerSettlementOrder.setStartTime(applyStart2);
        
        if(settleStart!=null&&settleStart.length()>0)
        	partnerSettlementOrder.setSettleStart(settleStart);
		if(settleEnd!=null
				&&settleEnd.length()>0){
            partnerSettlementOrder.setSettleEnd(settleEnd);
		}		
		/*if(settlementFlag!=null&&settlementFlag.length()>0){
			partnerSettlementOrder.setSettlementFlg(Integer.valueOf(settlementFlag));
//			partnerSettlementOrder.setSettlementFlg(settlementFlag);
		}*/
//		if("0".equals(partnerSettlementOrder.getSettlementFlg()))
//		{
//			partnerSettlementOrder.setSettlementFlg(0);
//		}		
		ModelAndView mv = new ModelAndView(excelView);
		mv.addObject("nowDate", new Date()) ;
		mv.addObject("startTime", applyStart);
		mv.addObject("endTime", applyEnd);
		
//		mv.addObject("clearStartTime", applyStart2);
//		mv.addObject("clearEndTime", applyEnd2);
		
		mv.addObject("feeRate", feeRate);
		mv.addObject("riskFee", riskFee);
		mv.addObject("SettlementFlg", SettlementFlg);
		partnerSettlementOrder.setPartnerId(Long.valueOf(memberCode));
		
		
		try {
			
			
			List list = settlementOrderService.settlementQuery2(partnerSettlementOrder);
			Map resultMap_ = new HashMap();
			resultMap_.putAll(MapUtil.bean2map(partnerSettlementOrder));
			resultMap_.put("resultList",list);
			
			CurrencyCodeEnum[] currencyCodeEnum = CurrencyCodeEnum.values();
			response.setHeader("Expires", "0");
			response.setHeader("Pragma" ,"public");
			response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Cache-Control", "public");
	
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			//2016年4月21日15:47:14  修改下载文件名称为清算明细  by tom.wang
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(("清算明细.xls").getBytes("UTF-8"),
							"ISO8859-1"));
			return mv.addAllObjects(resultMap_).addObject("currencyCodeEnum", currencyCodeEnum).addObject("acctName", acctName);//添加账户名称 by tom.wang 2016年4月21日16:08:23
		} catch (Exception e) {
			e.printStackTrace();
			String errorCode = "";
			return mv;
		}
	}
	
	
	/**账户余额明细——待清算交易查询
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
		int page_size = 20; // 每页条数
		pager_offset = request.getParameter("pager_offset") == null ? 1
				: Integer.parseInt(request.getParameter("pager_offset")); // 获取页面传过来的页码

		if (request.getParameter("numCount") != null
				&& !"".equals(request.getParameter("numCount"))) {// 获取页面传入的每页显示记录条数
			page_size = Integer.parseInt(request.getParameter("numCount"));
			if (page_size == 0)
				page_size = 20;// 此处为避免分页计算时出现被0除的情况
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

	public void setExcelView(String excelView) {
		this.excelView = excelView;
	}

	public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	
}
