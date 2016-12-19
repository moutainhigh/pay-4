/**
 *
 */
package com.pay.app.controller.base.credit;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.client.SettleCoreClientService;
import com.pay.base.model.EnterpriseBase;
import com.pay.base.service.enterprise.EnterpriseBaseService;
import com.pay.commons.OrderCreditEnum;
import com.pay.commons.Trans2RMB2Li;
import com.pay.fi.credit.OrderCreditDTO;
import com.pay.fi.credit.OrderCreditDetailDTO;
import com.pay.fi.dto.PartnerSettlementOrder;
import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.jms.notification.request.MerchantWebsiteCheckRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.poss.base.util.Trans2RMB;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 订单授信Controller
 * @author Jiangbo.Peng
 * 
 */
public class CreditOrderyController extends MultiActionController {

	private final Log log = LogFactory.getLog(CreditOrderyController.class);
	//private static final String TRADEORDER_COMPLETE_TIME_NOT_NULL = "网关完成起止时间不能为空！" ;
	private static final String CREDTIDATA_NOT_BE_NULL = "申请授信数据不能为空！" ;
	private static final String CREDIT_CURRENCYCODE = "CNY" ;
	//批量状态
	private static String  batchstatus="0";

	/** 订单授信页面 */
	private String creditOrderView;
	/** 融资信息查询页面 */
	private String creditOrderQueryInfo;
	/** 订单授信查询页面详情 */
	private String creditOrderQueryDetail;
	/** 登陆页面 */
	private String login;
	
	/** 授信申请确认页面 */
	private String applyConfirmView ;

	/** 企业会员基本信息服务 */
	private EnterpriseBaseService enterpriseBaseService;
	private JmsSender jmsSender;
	private SettleCoreClientService settleCoreClientService;
	
	/**
	 * 授信默认页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(final HttpServletRequest request,
			final HttpServletResponse response) {
		ModelAndView view = new ModelAndView(creditOrderView);
		int minDateOffser = this.getMinDateOffser(Long.valueOf(SessionHelper.getMemeberCodeBySession())) ;
		String memberCode = SessionHelper.getLoginSession().getMemberCode() ;
		//日利率查询
		String dayRate = getDayRate(memberCode);
		view.addObject("minDateOffser", minDateOffser) ;
		view.addObject("dayRate", dayRate) ;
		view.addObject("creditCurrencyCode", CREDIT_CURRENCYCODE) ;
		return view;
	}

	/**
	 * 取日利率
	 * @param memberCode
	 * @return
	 * 2016年7月7日   mmzhang     add
	 */
	private String getDayRate(String memberCode) {
		String dayRate="0";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("partnerId", memberCode);
		paraMap.put("status", "1");
		Map map = this.settleCoreClientService.dayRateQuery(paraMap) ;
		List<Map> listMap = (List<Map>) map.get("result") ;
		if (null !=listMap && listMap.size()>0) {
			Map rateMap = listMap.get(0);
			if (rateMap != null && null != rateMap.get("rate")) {
				dayRate = rateMap.get("rate").toString();
			}
		}
		if("0".equals(dayRate))
		{
				paraMap.put("partnerId", "0");
				Map map2 = this.settleCoreClientService.dayRateQuery(paraMap);
				List<Map> listMap2 = (List<Map>) map2.get("result");
				if (null !=listMap2 && listMap2.size()>0) {
				Map rateMap2 = listMap2.get(0);
				if (rateMap2 != null && null != rateMap2.get("rate"))
					dayRate = rateMap2.get("rate").toString();
				}
		}
		
		return dayRate;
	}
	
	/**
	 * 授信确认申请页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toApplyView(HttpServletRequest request, HttpServletResponse response){
		ModelAndView view = new ModelAndView(this.applyConfirmView) ;
		try {
			String memberCode = SessionHelper.getLoginSession().getMemberCode() ;
			String applyDataAttr = StringUtil.null2String(request.getParameter("applyDataAttr")) ;
			applyDataAttr = URLDecoder.decode(URLDecoder.decode(applyDataAttr, "UTF-8"), "UTF-8") ;
			JSONArray jsonArray = JSONArray.fromObject(applyDataAttr) ;
			//List<OrderCreditDetailDTO> detailParamList = JSONArray.toList(jsonArray, OrderCreditDetailDTO.class) ;
			List<PartnerSettlementOrder> partnerSettlementOrders = JSONArray.toList(jsonArray, PartnerSettlementOrder.class) ;
			//立即申请，算出服务费，授信金额
			
			//日利率查询
			String dayRate=getDayRate(memberCode);
			String settlementCurrencyCode = "" ;	//暂时空
			
			Map resultMap = this.settleCoreClientService.orderCreditApply(partnerSettlementOrders, dayRate, settlementCurrencyCode) ;
			String responseCode = (String) resultMap.get("responseCode") ;
			List<Map> listMap = (List<Map>) resultMap.get("list") ;
			@SuppressWarnings("unchecked")
			List<PartnerSettlementOrder> partnerSettlementOrders2 = MapUtil.map2List(PartnerSettlementOrder.class, listMap) ;
			BigDecimal creditAmount = new BigDecimal("0") ;
			BigDecimal charge = new BigDecimal("0") ;
			for(PartnerSettlementOrder pso : partnerSettlementOrders2){
				creditAmount = creditAmount.add(new BigDecimal(pso.getRecordedAmount()).divide(new BigDecimal("1000"),BigDecimal.ROUND_HALF_UP,3)) ;
				charge = charge.add(new BigDecimal(pso.getCharge()).divide(new BigDecimal("1000"),BigDecimal.ROUND_HALF_UP,3)) ;
			}
			String jsting=jsonArray.fromObject(partnerSettlementOrders2).toString();
			//企业会员基本信息
		    EnterpriseBase enterpriseBase = enterpriseBaseService.findByMemberCode(Long.valueOf(memberCode));
		    //人民币大写， 精确到厘
		    String creditAmount2RMB = "零" ;
		    if(null != creditAmount){
		    	String tempArr[] = creditAmount.toString().split("\\.") ;
			    if(null != tempArr && tempArr.length != 0){
			    	int len = tempArr.length ;
			    	if(1 == len){
			    		creditAmount2RMB = Trans2RMB2Li.processNum(tempArr[0]) ;
			    	}
			    	if(2 == len){
			    		String pointStr = tempArr[1] ;
			    		pointStr = pointStr.length() > 3 ? pointStr.substring(0,3) : pointStr ;
			    		creditAmount2RMB = Trans2RMB2Li.processNum(tempArr[0] + "." + pointStr) ;
			    	}
			    }
		    }
			view.addObject("creditCurrencyCode", CREDIT_CURRENCYCODE) ;
			view.addObject("creditOrderTotalSize", partnerSettlementOrders.size())
				.addObject("applyDataAttr", URLEncoder.encode(URLEncoder.encode(jsting, "UTF-8"), "UTF-8"))
				.addObject("creditAmount", creditAmount)
				.addObject("charge", charge)
				.addObject("dayRate", dayRate)
				.addObject("enterpriseBase", enterpriseBase)
				.addObject("creditAmount2RMB", creditAmount2RMB);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return view ;
	}
	
	private static String setStatus()
	{
		batchstatus="1";
		return batchstatus;
	}
	private static String resetStatus()
	{
		batchstatus="0";
		return batchstatus;
	}
	/**
	 * 订单授信确认申请
	 * @throws ParseException 
	 */
	@SuppressWarnings({"unchecked"})
	public ModelAndView applyCreditOrder(final HttpServletRequest request, final HttpServletResponse response) throws ParseException{
		String successMessage = "" ;
		try {
			
			String applyDataAttr = StringUtil.null2String(request.getParameter("applyDataAttr")) ;
			applyDataAttr = URLDecoder.decode(URLDecoder.decode(applyDataAttr, "UTF-8"), "UTF-8") ;
			JSONArray jsonArray = JSONArray.fromObject(applyDataAttr) ;
			List<PartnerSettlementOrder> partnerSettlementOrders = JSONArray.toList(jsonArray, PartnerSettlementOrder.class) ;
			if(CollectionUtils.isEmpty(partnerSettlementOrders)){
				return this.index(request, response).addObject("errorMsg", CREDTIDATA_NOT_BE_NULL) ;
			}
			String memberCode = SessionHelper.getLoginSession().getMemberCode() ;
			EnterpriseBase enterpriseBase = this.enterpriseBaseService.findByMemberCode(Long.valueOf(memberCode)) ;
			String partnerName = enterpriseBase.getZhName() ;
			List<OrderCreditDetailDTO> detailParamList = new ArrayList<OrderCreditDetailDTO>() ;
			BigDecimal orderAmount = new BigDecimal("0") ;
			for(PartnerSettlementOrder pso : partnerSettlementOrders){
				OrderCreditDetailDTO ocd = new OrderCreditDetailDTO() ;
				orderAmount = orderAmount.add(new BigDecimal(pso.getOrderAmount())) ;
				ocd.setAccountAmount(pso.getRecordedAmount() - pso.getCharge());
				ocd.setApplyDate(pso.getApplyDate());
				ocd.setCharge(pso.getCharge());
				ocd.setCreditAmount(pso.getRecordedAmount());
				ocd.setOrderAmount(pso.getOrderAmount());
				ocd.setPartnerId(Long.valueOf(memberCode));
				ocd.setPartnerName(partnerName);
				ocd.setPartnerSettlementOrderId(pso.getId());
				ocd.setTradeOrderId(pso.getTradeOrderNo());
				Date settlementDate =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(pso.getSsettlementDate());
				ocd.setSettlementDate(settlementDate);
				ocd.setStatus("0"); //刚创建
				ocd.setSettlementFlg(0); //未清算
				ocd.setOrderId(pso.getOrderId());
				detailParamList.add(ocd) ;
			}
			
			synchronized (this) {
				logger.info("批量提交状态为batchStatus:"
						+ batchstatus);
				//如果批量状态为0，可确认申请
				if (batchstatus.equals(OrderCreditEnum.batchStatus0.getType())) {
					//置批量状态为运行中
					setStatus();
					logger.info("批量提交状态为batchStatus:"
							+ batchstatus);
					// 构建融资订单
					OrderCreditDTO orderCreditDTO = this
							.constructOrderCreditDTO(request,
									detailParamList.size(), memberCode,
									partnerName, orderAmount.longValue());
					Map<String, Object> paraMap = new HashMap<String, Object>();
					paraMap.put("orderCreditDTO", orderCreditDTO);
					paraMap.put("list", detailParamList);
					Map result = this.settleCoreClientService
							.creditOrderCreate(paraMap);
					String responseCode = (String) result.get("responseCode");
					String responseDesc = (String) result.get("responseDesc");
					
					if(ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)){
						successMessage = "订单授信申请提交成功！" ;
					}
					if (!"0000".equals(responseCode)) {
						resetStatus();
						return new ModelAndView(this.creditOrderView).addObject(
								"message", responseDesc);
					} else {

						/****
						 * 发送提前清算申请
						 */
						if (null != partnerSettlementOrders
								&& partnerSettlementOrders.size() > 0) {
							for (PartnerSettlementOrder partnerSettlementOrder : partnerSettlementOrders) {
								PartnerSettlementOrder order = new PartnerSettlementOrder();
								order.setCreditFlag(OrderCreditEnum.creditFlag4
										.getType());
								order.setAdvanceFlag(OrderCreditEnum.advanceFlag1
										.getType());
								order.setId(partnerSettlementOrder.getId());
								Map<String, Object> paraMap2 = new HashMap<String, Object>();
								paraMap.put("partnerSettlementOrder", order);
								logger.info("清算订单的状态修改，清算订单:"
										+ partnerSettlementOrder.getId());
								Map resultMap = this.settleCoreClientService
										.creditsettlementUpdate(paraMap);
							}

							
							try {
								String reqMsg = JSonUtil
										.toJSonString(partnerSettlementOrders); // 转json发送保存的数据
								MerchantWebsiteCheckRequest notifyRequest = new MerchantWebsiteCheckRequest();
								Map<String, String> notifyMap = new HashMap<String, String>();
								notifyMap.put("creditCurrencyCode", "CNY");
								notifyMap.put("list", reqMsg);
								notifyRequest.setData(notifyMap);
								notifyRequest.setMerchantCode(memberCode);
								jmsSender.send("notify.forpay.ordercredit",
										notifyRequest);
								resetStatus();
								logger.info("批量提交状态为batchStatus:"
										+ batchstatus);
							} catch (Exception e) {
								resetStatus();
								logger.info("批量提交状态为batchStatus:"
										+ batchstatus);
								logger.info("订单授信提前清算异常！memberCode："
										+ memberCode);
								e.printStackTrace();
							}
						}
						
					}
				}
				}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ModelAndView(creditOrderView).addObject("successMessage", successMessage) ;
	}

	/**
	 * 任务处理完成更新
	 */
	private void batchStatusMethod() {
		//任务处理完成更新
		Map<String, Object> paraMap2= new HashMap<String, Object>();
		paraMap2.put("batchType", "ordercredit");
		paraMap2.put("status", OrderCreditEnum.batchStatus0.getType());
		this.settleCoreClientService
				.batchStatusUpdate(paraMap2);
	}
	
	
	
	private OrderCreditDTO constructOrderCreditDTO(HttpServletRequest request, Integer creditCount, String memberCode, String zhName, Long orderAmount){
		OrderCreditDTO orderCreditDTO = new OrderCreditDTO() ;
		String charge = StringUtil.null2String(request.getParameter("charge")) ;
		String creditAmount = StringUtil.null2String(request.getParameter("creditAmount")) ;
		String dayRate = StringUtil.null2String(request.getParameter("dayRate")) ;
		orderCreditDTO.setOrderAmount(orderAmount);
		orderCreditDTO.setApplyDate(new Date());
		orderCreditDTO.setCharge(new BigDecimal(charge).multiply(new BigDecimal(1000)).longValue());
		orderCreditDTO.setCreditAmount(new BigDecimal(creditAmount).multiply(new BigDecimal(1000)).longValue());
		orderCreditDTO.setAccountAmount(orderCreditDTO.getCreditAmount() - orderCreditDTO.getCharge());
		orderCreditDTO.setCreditCount(creditCount);
		orderCreditDTO.setCreditType("A");	//A：订单授信
		orderCreditDTO.setCurrencyCode(CREDIT_CURRENCYCODE);
		orderCreditDTO.setDayRate(dayRate);
		orderCreditDTO.setPartnerId(Long.valueOf(memberCode));
		orderCreditDTO.setPartnerName(zhName);
		orderCreditDTO.setPartnerRegisterName(zhName);
		return orderCreditDTO ;
	}
	
	
	/**
	 * @date 
	 * @author 订单授信查询
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public ModelAndView queryCreditOrder(final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		
		String beginDate = StringUtil.null2String(request.getParameter("beginDate")) ;
		String endDate = StringUtil.null2String(request.getParameter("endDate")) ;
		
		//add by mmzhang 添加判断“原清算时间距task执行时间小于2天的订单，不予授信”
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar c1 = Calendar.getInstance();
		c1.setTime(new Date());
		c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 2);
		String settleStart=df.format(c1.getTime());
		//end by mmzhang add
		
		String memberCode = (String) request.getSession().getAttribute(
				"memberCode");
		if (StringUtils.isBlank(memberCode)) {
			return new ModelAndView(login);
		}
		//日利率查询
		String dayRate = getDayRate(memberCode);
		// 页面参数
		int pager_offset = 1; // 当前页码
		int page_size = 100; // 每页条数
		pager_offset = request.getParameter("pager_offset") == null ? 1
				: Integer.parseInt(request.getParameter("pager_offset")); // 获取页面传过来的页码
		if (request.getParameter("numCount") != null
				&& !"".equals(request.getParameter("numCount"))) {// 获取页面传入的每页显示记录条数
			page_size = Integer.parseInt(request.getParameter("numCount"));
			if (page_size == 0)
				page_size = 100;// 此处为避免分页计算时出现被0除的情况
		}
		final Map<String, Object> map = new HashMap<String, Object>();
		try {
			Page<Object> page = new Page<Object>();
			page.setPageNo(pager_offset);
			page.setPageSize(page_size);
			
			//清算对象参数构建
			PartnerSettlementOrder partnerSettlementOrder = new PartnerSettlementOrder() ;
			partnerSettlementOrder.setPartnerId(Long.valueOf(memberCode));
			partnerSettlementOrder.setBeginDate(beginDate);
			partnerSettlementOrder.setEndDate(endDate);
			partnerSettlementOrder.setSettleStart(settleStart);
			partnerSettlementOrder.setCreditFlag(OrderCreditEnum.creditFlag3.getType());
			partnerSettlementOrder.setSettlementFlg(NumberConstants.ZERO);// 未结算的
			
			Map resultMap= settleCoreClientService.settlementOrderQuery(partnerSettlementOrder,page);
			List<Map> list = (List<Map>) resultMap.get("list");
			Page<Object> newPage = MapUtil.map2Object(Page.class,(Map<?, ?>) resultMap.get("page"));
			PageUtil pu = new PageUtil(newPage.getPageNo(),newPage.getPageSize(), newPage.getTotalCount());// 分页处理
			List<PartnerSettlementOrder> infoList = MapUtil.map2List(PartnerSettlementOrder.class,list);
			
			//添加订单授信总笔数和总金额展示 add by mmzhang 
			PartnerSettlementOrder partnerSettlementOrder2=new PartnerSettlementOrder();
			partnerSettlementOrder2.setPartnerId(Long.valueOf(memberCode));
			partnerSettlementOrder2.setSettlementFlg(NumberConstants.ZERO);// 未结算的
			partnerSettlementOrder2.setCreditFlag("3");
			partnerSettlementOrder2.setSettleStart(settleStart);
			Map map2= settleCoreClientService.settlementOrderQuery(partnerSettlementOrder2,null);
			List<Map> maplist2 = (List<Map>) map2.get("list");
			List<PartnerSettlementOrder> totalList = MapUtil.map2List(
					PartnerSettlementOrder.class, maplist2);
			int totalCredit=0;
			String totalCreditAmount="";
			if(null!=totalList && totalList.size()>0)
			{
				totalCredit=totalList.size();
				totalCreditAmount= getAmount(totalList);	
			}
			
			map.put("startTime", partnerSettlementOrder.getStartTime());
			map.put("endTime", partnerSettlementOrder.getEndTime());
			map.put("memberCode", memberCode);
			map.put("dayRate", dayRate);
			map.put("pu", pu);
			map.put("COUNTDATA", totalCredit);
			map.put("AMOUNT", totalCreditAmount);
			map.put("infoList", infoList);
			
			return new ModelAndView(creditOrderView, map);
		} catch (final Exception e) {
			log.error("商户[" + memberCode + "]授信申请时查询授信订单列表出错！");
			log.error(e.getMessage());
			log.error(e);
			map.put("errorMessage", e.getMessage());
			return new ModelAndView(creditOrderView, map).addObject("endDate",endDate).addObject("beginDate", beginDate);
		}
	}
	/**
	 * 融资信息查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView queryCreditOrderInfo(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		
		String creditId = StringUtil.null2String(request.getParameter("creditId")) ;
		String startTime = StringUtil.null2String(request.getParameter("startTime")) ;
		String endTime = StringUtil.null2String(request.getParameter("endTime")) ;
		String creditType = StringUtil.null2String(request.getParameter("creditType")) ;
		String partnerId = SessionHelper.getLoginSession().getMemberCode() ;
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
		Page page = new Page();
		page.setPageNo(pager_offset);
		page.setPageSize(page_size);
		
		Map<String, Object> paraMap = new HashMap<String, Object>() ;
		paraMap.put("creditId", creditId);
		paraMap.put("startTime", startTime);
		paraMap.put("endTime", endTime);
		paraMap.put("partnerId", partnerId);
		paraMap.put("creditType", creditType) ;
		paraMap.put("page", page) ;
		
		Map resultMap = this.settleCoreClientService.orderCreditOrderQuery(paraMap) ;
		Map pageMap = (Map) resultMap.get("page") ;
		page = MapUtil.map2Object(Page.class, pageMap) ;
		List<Map> listMap = (List<Map>) resultMap.get("result") ;
		PageUtil pu = new PageUtil(page.getPageNo(), page.getPageSize(), page.getTotalCount()) ;
		List<OrderCreditDTO> list = MapUtil.map2List(OrderCreditDTO.class, listMap) ;
		return new ModelAndView(creditOrderQueryInfo).addObject("pu", pu)
				.addObject("orderCredits", list)
				.addObject("creditId", creditId)
				.addObject("creditType", creditType)
				.addObject("startTime", startTime)
				.addObject("endTime", endTime);
		
	}
	
	/**
	 * add by tom
	 * 获取授信总金额
	 * @param infoList
	 * @return
	 */
	private String getAmount(final List<PartnerSettlementOrder> infoList) {
		Long amount = 0L;
		if (CollectionUtils.isEmpty(infoList))
			return amount+"";
		for (final PartnerSettlementOrder partnerSettlementOrder : infoList) {
				amount+=partnerSettlementOrder.getRecordedAmount();
			}
		return amountFormat2Str(amount);
	}
	/**
	 * 金额格式化，以字符串形式返回
	 * @param amount
	 * @return
	 */
	private String amountFormat2Str(final Long amount){
		if(null != amount){
			BigDecimal bigDecimal = new BigDecimal(amount).divide(new BigDecimal(1000)) ;
			//不四舍五入的方法  
			//,代表分隔符    
			//.后面的##代表位数 如果换成0 效果就是位数不足0补齐
		    DecimalFormat df =new DecimalFormat("#,##0.000");
		    // 设置舍入模式
		    df.setRoundingMode(RoundingMode.FLOOR); 
		    String formatStr = df.format(bigDecimal) ;
		  	return formatStr ;
		}
		return "0.000" ;
	}
	/**
	 * 融资订单详情
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView creditOrderDetailQuery(final HttpServletRequest request, final HttpServletResponse response){
		String creditId = StringUtil.null2String(request.getParameter("creditId")) ;
		String accountAmount = StringUtil.null2String(request.getParameter("accountAmount")) ;
		String creidtCurrencyCode = StringUtil.null2String(request.getParameter("creditCurrencyCode")) ;
		Map<String, Object> paraMap = new HashMap<String, Object>() ;
		paraMap.put("creditId", creditId) ;
		Map<String, Object> resultMap = this.settleCoreClientService.orderCreditOrderDetailQuery(paraMap) ;
		String responseCode = (String) resultMap.get("responseCode") ;
		List<Map> listMap = (List<Map>) resultMap.get("result") ;
		List<OrderCreditDetailDTO> orderCreditDetails = MapUtil.map2List(OrderCreditDetailDTO.class, listMap) ;
		return new ModelAndView(creditOrderQueryDetail)
			.addObject("orderCreditDetails", orderCreditDetails)
			.addObject("creditId", creditId)
			.addObject("accountAmount", accountAmount)
			.addObject("creidtCurrencyCode", creidtCurrencyCode);
	}
	
	/**
	 * 根据商户结算周期获得最小时间偏移范围
	 * @param memberCode
	 * @return
	 */
	private int getMinDateOffser(final Long memberCode){
		int minDateOffser = 0 ;
	    EnterpriseBase enterpriseBase = this.enterpriseBaseService.findByMemberCode(memberCode) ;
	    Integer settlementCycle = enterpriseBase.getSettlementCycle() ;
	    if(null != settlementCycle && 0 != settlementCycle && 2<=settlementCycle){
	    	minDateOffser = settlementCycle - 2 ;
	    }
	    return minDateOffser ;
	}
	
	/**
	 * 获取为商户配置的可授信币种
	 * @return
	 */
//	private List<PartnerCreditCurrencyCode> getPartnerCreditcurrency(){
//		List<PartnerCreditCurrencyCode> partnerCreditCurrencyCodes = null ;
//		try {
//			partnerCreditCurrencyCodes = this.creditOrderService.queryPartnerCreditCurrency(Long.valueOf(SessionHelper.getMemeberCodeBySession())) ;
//		} catch (NumberFormatException e) {
//			if(logger.isInfoEnabled()){
//				logger.info("query queryPartnerCreditCurrency[PartnerCurrencyCode] error...");
//			}
//			e.printStackTrace();
//		}
//		//if(CollectionUtils.)
//		return partnerCreditCurrencyCodes ;
//	}
	//-----------------------setter------------------------------------------
	
	public void setLogin(final String login) {
		this.login = login;
	}
	public void setEnterpriseBaseService(
			final EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}
	public void setCreditOrderQueryDetail(final String creditOrderQueryDetail) {
		this.creditOrderQueryDetail = creditOrderQueryDetail;
	}
	public void setCreditOrderView(final String creditOrderView) {
		this.creditOrderView = creditOrderView;
	}
	public void setCreditOrderQueryInfo(final String creditOrderQueryInfo) {
		this.creditOrderQueryInfo = creditOrderQueryInfo;
	}
	public void setSettleCoreClientService(
			SettleCoreClientService settleCoreClientService) {
		this.settleCoreClientService = settleCoreClientService;
	}
	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

	public String getApplyConfirmView() {
		return applyConfirmView;
	}

	public void setApplyConfirmView(String applyConfirmView) {
		this.applyConfirmView = applyConfirmView;
	}
	
	
}
