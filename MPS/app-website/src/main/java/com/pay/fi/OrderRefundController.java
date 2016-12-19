package com.pay.fi;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.SessionHelper;
import com.pay.fi.dto.BatchRefundParamDTO;
import com.pay.fi.dto.ChargeBackOrder;
import com.pay.fi.dto.QueryDetailDTO;
import com.pay.fi.dto.QueryDetailParaDTO;
import com.pay.fi.dto.RefundParamDTO;
import com.pay.fi.hession.RefundOrderService;
import com.pay.fi.service.OrderQueryService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.util.DateUtil;
import com.pay.util.ExcelUtils;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;


/**
 * 订单退款
 * @author peiyu.yang
 *
 */
public class OrderRefundController extends MultiActionController{
	
	private OrderQueryService orderQueryService;
	private final Log log = LogFactory
			.getLog(OrderRefundController.class);
	private String queryView;
	private String excelView ;
	private String applyView;
	private String resultView;
	private String payLinkResultView ;
	private String batchErrorDetailView ;
	
	private String singleIncomeDetailCorp;
	private RefundOrderService refundOrderService;
	/**
	 * 退款申请页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView applyRefund(final HttpServletRequest request,
			final HttpServletResponse response){
		
		ModelAndView singleView = new ModelAndView(applyView);
		String memberCode = (String) request.getSession().getAttribute(
				"memberCode");
		// 获取参数
		Map<String, Object> map = new HashMap<String, Object>();
		String serialNo = request.getParameter("serialNo");
		map.put("memberCode", memberCode);
		map.put("serialNo", serialNo);
		// 查询
		Map<String, Object> resultMap = orderQueryService
				.querySingleIncomeDetailForRefund(map);
		singleView.addObject("map", resultMap);
		return singleView;
	}
	
	
	/**
	 * 退款
	 * @param request
	 * @param response
	 * @return
	 */
	//申请退款的时候判断是否有拒付记录，如果没有申请退款成功
	public ModelAndView refund(final HttpServletRequest request,
			final HttpServletResponse response) {
		String payLinkFlag = StringUtil.null2String(request.getParameter("payLinkFlag")) ;
		ModelAndView singleView = new ModelAndView(applyView);
		String partnerId = (String) request.getSession().getAttribute(
				"memberCode");
		// 获取查询条件
		String tradeOrderNo = request.getParameter("tradeOrderNo");
		Page page = PageUtils.getPage(request);
		ChargeBackOrder chargeBackCondition = new ChargeBackOrder();
		chargeBackCondition.setTradeOrderNo(Long.valueOf(tradeOrderNo));
		Map result = refundOrderService.queryChargeBackOrder(
				chargeBackCondition, page);
		List<Map> chargeBackOrders = (List<Map>) result.get("result");
		// 判断拒付网关流水号是否存在，如果存在并返回需要提示的消息
		if (CollectionUtils.isEmpty(chargeBackOrders)) {
			String orderId = request.getParameter("orderId");
			String refundAbleAmount = request.getParameter("refundAbleAmount");
			String refundAmount = request.getParameter("refundAmount");
			RefundParamDTO paramDTO = new RefundParamDTO();
			BigDecimal refundAbleAmount_ = new BigDecimal(refundAbleAmount);
			BigDecimal refundAmount_ = new BigDecimal(refundAmount);
			if (refundAbleAmount_.compareTo(refundAmount_) == 0) {
				paramDTO.setRefundType("1");
			} else if (refundAbleAmount_.compareTo(refundAmount_) > 0) {
				paramDTO.setRefundType("2");
			} else {
				singleView.addObject("msg", "退款金额不能超过可退金额");
				ConstructMsgView(payLinkFlag, singleView);
				return singleView;
			}
			refundAmount_ = refundAmount_.multiply(new BigDecimal("100"));
			long ra = refundAmount_.setScale(0, BigDecimal.ROUND_HALF_UP)
					.longValue();
			String refundOrderId = "601" + System.currentTimeMillis();
			paramDTO.setOrderId(orderId);
			paramDTO.setPartnerId(partnerId);
			paramDTO.setRefundAmount(String.valueOf(ra));
			paramDTO.setRefundTime(getDateStr(System.currentTimeMillis()));
			paramDTO.setRefundOrderId(refundOrderId);
			paramDTO.setDestType("1");
			paramDTO.setTradeOrderNo(tradeOrderNo);
			paramDTO.setNoticeUrl("mps.ipaylinks.com");
			Map resultMap = refundOrderService.refund(paramDTO);
			String responseCode = (String) resultMap.get("responseCode");
			String responseDesc = (String) resultMap.get("responseDesc");
			singleView.addObject("msg", responseDesc);
			ConstructMsgView(payLinkFlag, singleView);
			return singleView;

		} else {
			singleView.addObject("msg", "该笔订单已发生拒付，请不要重复退款!");
			ConstructMsgView(payLinkFlag, singleView);
			return singleView;
		}
	}
	/**
	 * 退款--ajax请求
	 * @param request
	 * @param response
	 * @return
	 */
	//申请退款的时候判断是否有拒付记录，如果没有申请退款成功
	public ModelAndView refundForAjax(final HttpServletRequest request,
			final HttpServletResponse response) {
		//String payLinkFlag = StringUtil.null2String(request.getParameter("payLinkFlag")) ;
		//ModelAndView singleView = new ModelAndView(applyView);
		JSONObject jsonObject = new JSONObject() ;
		PrintWriter writer = null ;
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			writer = response.getWriter() ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		String partnerId = (String) request.getSession().getAttribute(
				"memberCode");
		// 获取查询条件
		String tradeOrderNo = request.getParameter("tradeOrderNo");
		Page page = PageUtils.getPage(request);
		ChargeBackOrder chargeBackCondition = new ChargeBackOrder();
		chargeBackCondition.setTradeOrderNo(Long.valueOf(tradeOrderNo));
		Map result = refundOrderService.queryChargeBackOrder(
				chargeBackCondition, page);
		List<Map> chargeBackOrders = (List<Map>) result.get("result");
		// 判断拒付网关流水号是否存在，如果存在并返回需要提示的消息
		if (CollectionUtils.isEmpty(chargeBackOrders)) {
			String orderId = request.getParameter("orderId");
			String refundAbleAmount = request.getParameter("refundAbleAmount");
			String refundAmount = request.getParameter("refundAmount");
			RefundParamDTO paramDTO = new RefundParamDTO();
			BigDecimal refundAbleAmount_ = new BigDecimal(refundAbleAmount);
			BigDecimal refundAmount_ = new BigDecimal(refundAmount);
			if (refundAbleAmount_.compareTo(refundAmount_) == 0) {
				paramDTO.setRefundType("1");
			} else if (refundAbleAmount_.compareTo(refundAmount_) > 0) {
				paramDTO.setRefundType("2");
			} else {
				jsonObject.put("resultMsg", "退款金额不能超过可退金额") ;
				if(null != writer){
					writer.print(jsonObject);
					writer.flush();
					writer.close(); 
				}
				return null ;
			}
			refundAmount_ = refundAmount_.multiply(new BigDecimal("100"));
			long ra = refundAmount_.setScale(0, BigDecimal.ROUND_HALF_UP)
					.longValue();
			String refundOrderId = "601" + System.currentTimeMillis();
			paramDTO.setOrderId(orderId);
			paramDTO.setPartnerId(partnerId);
			paramDTO.setRefundAmount(String.valueOf(ra));
			paramDTO.setRefundTime(getDateStr(System.currentTimeMillis()));
			paramDTO.setRefundOrderId(refundOrderId);
			paramDTO.setDestType("1");
			paramDTO.setTradeOrderNo(tradeOrderNo);
			paramDTO.setNoticeUrl("mps.ipaylinks.com");
			Map resultMap = refundOrderService.refund(paramDTO);
			String responseCode = (String) resultMap.get("responseCode");
			String responseDesc = (String) resultMap.get("responseDesc");
			jsonObject.put("resultMsg", responseDesc) ;
			if(null != writer){
				writer.print(jsonObject);
				writer.flush();
				writer.close(); 
			}
			return null ;
			
		} else {
			jsonObject.put("resultMsg", "该笔订单已发生拒付，请不要重复退款!") ;
			if(null != writer){
				writer.print(jsonObject);
				writer.flush();
				writer.close(); 
			}
			return null ;
		}
	}


	/**
	 * @param payLinkFlag
	 * @param singleView
	 */
	private void ConstructMsgView(final String payLinkFlag, final ModelAndView singleView) {
		if(StringUtils.isEmpty(payLinkFlag)){
			singleView.setViewName(resultView);
		}else{
			singleView.setViewName(payLinkResultView);
		}
	}
	
	/**
	 * 默认进index
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(final HttpServletRequest request,
			final HttpServletResponse response) {
		ModelAndView view = new ModelAndView(queryView);
		view.addObject("beginTime", new SimpleDateFormat("yyyy-MM-dd 00:00")
				.format(DateUtil.skipDateTime(new Date(), -2)));
		view.addObject("endTime", new SimpleDateFormat("yyyy-MM-dd 00:00")
				.format(DateUtil.skipDateTime(new Date(), 1)));
		return view;
	}
	
	
	/** 退款列表查询
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView list(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		/*if(StringUtil.isEmpty(request.getParameter("payStartTime")) || StringUtil.isEmpty(request.getParameter("payEndTime")))
		{
			ModelAndView view = new ModelAndView(queryView);
			view.addObject("beginTime", new SimpleDateFormat("yyyy-MM-dd 00:00")
					.format(DateUtil.skipDateTime(new Date(), -2)));
			view.addObject("endTime", new SimpleDateFormat("yyyy-MM-dd 00:00")
					.format(DateUtil.skipDateTime(new Date(), 1)));
			return view;
		}*/
		//商户退款订单号
		String partnerRefundOrderId = StringUtil.null2String(request.getParameter("partnerRefundOrderId"));
		String orderId = StringUtil.null2String(request.getParameter("orderId"));//原交易商户订单号查询
		String refundOrderNo = StringUtil.null2String(request.getParameter("refundOrderNo"));//退款流水号		
		String tradeOrderNo = StringUtil.null2String(request.getParameter("tradeOrderNo"));	//网关订单流水				
		String partnerId = (String) request.getSession().getAttribute("memberCode");			
		String paymentOrderNo = StringUtil.null2String(request.getParameter("paymentOrderNo"));//用户会员号
		String currencyCode = StringUtil.null2String(request.getParameter("currencyCode"));//交易币种

		//退款类型状态
		String status = StringUtil.null2String(request.getParameter("status"));
		//				
		String startTime = StringUtil.null2String(request.getParameter("startTime"));
		
		String endTime = StringUtil.null2String(request.getParameter("endTime"));
		
		// 页面参数
		int pager_offset = 1; // 当前页码
		int page_size = 20; // 每页条数
		pager_offset = request.getParameter("pager_offset") == null ? 1: Integer.parseInt(request.getParameter("pager_offset")); // 获取页面传过来的页码

		if (request.getParameter("numCount") != null
				&& !"".equals(request.getParameter("numCount"))) {// 获取页面传入的每页显示记录条数
			page_size = Integer.parseInt(request.getParameter("numCount"));
			if (page_size == 0)
				page_size = 10;// 此处为避免分页计算时出现被0除的情况
		}
		
		Map paraMap = new HashMap();
		paraMap.put("paymentOrderNo", paymentOrderNo);
		paraMap.put("tradeOrderNo", tradeOrderNo);
		paraMap.put("partnerId", partnerId);
		paraMap.put("refundOrderNo", refundOrderNo);
		paraMap.put("partnerRefundOrderId", partnerRefundOrderId);
		//交易币种
		paraMap.put("currencyCode", currencyCode);		
		paraMap.put("status", status);
		paraMap.put("beginTime", startTime);
		paraMap.put("endTime", endTime);
		//根据原交易商户订单号查询
		paraMap.put("orderId",orderId);	
		
		Page page = new Page();
		page.setPageNo(pager_offset);
		page.setPageSize(page_size);
		
		paraMap.put("page",page);
		
		Map<String,Object> resultMap = orderQueryService.queryRefundOrderList(paraMap);
		List<Map> channelOrders = (List<Map>) resultMap.get("result");

		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		paraMap.put("page", page);
		
		log.info("page: "+page);
		
		
		
		for(int i=0;i<channelOrders.size();i++){
			Map map = channelOrders.get(i);  
			if(map.containsKey("createDate")){
				Long tmp = (Long) map.get("createDate");
				if(tmp!=null){
				     map.put("createDate",this.getDateStr(tmp));
				}
			}
			if(map.containsKey("complateDate")){
				Long tmp = (Long) map.get("complateDate");
				if(tmp!=null){
				     map.put("complateDate",this.getDateStr(tmp));
				}
			}
            
			channelOrders.set(i, map);
		}
		
		PageUtil pu = new PageUtil(page.getPageNo(),page.getPageSize(),page.getTotalCount());// 分页处理
		
		return new ModelAndView(queryView).addObject("list", channelOrders).addObject("pu",pu).addObject("status",status).addObject("partnerRefundOrderId",partnerRefundOrderId).addObject("orderId",orderId).addObject("beginTime", startTime).addObject("endTime", endTime);
	}
	/** 
	 * 导出Excel
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView excelView(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {		
		//商户退款订单号
		String partnerRefundOrderId = StringUtil.null2String(request.getParameter("partnerRefundOrderId"));		
		String orderId = StringUtil.null2String(request.getParameter("orderId"));//原交易商户订单号查询
		String refundOrderNo = StringUtil.null2String(request.getParameter("refundOrderNo"));//退款流水号		
		String tradeOrderNo = StringUtil.null2String(request.getParameter("tradeOrderNo"));	//网关订单流水				
		String partnerId = (String) request.getSession().getAttribute("memberCode");			
		String paymentOrderNo = StringUtil.null2String(request.getParameter("paymentOrderNo"));//用户会员号
		String currencyCode = StringUtil.null2String(request.getParameter("currencyCode"));//交易币种
		
		//退款类型状态
		String status = StringUtil.null2String(request.getParameter("status"));
		//				
		String startTime = StringUtil.null2String(request
				.getParameter("startTime"));
		String endTime = StringUtil
				.null2String(request.getParameter("endTime"));
		
		Map paraMap = new HashMap();
		paraMap.put("paymentOrderNo", paymentOrderNo);
		paraMap.put("tradeOrderNo", tradeOrderNo);
		paraMap.put("partnerId", partnerId);
		paraMap.put("refundOrderNo", refundOrderNo);
		paraMap.put("partnerRefundOrderId", partnerRefundOrderId);
		//交易币种
		paraMap.put("currencyCode", currencyCode);		
		paraMap.put("status", status);
		paraMap.put("beginTime", startTime);
		paraMap.put("endTime", endTime);
		//根据原交易商户订单号查询
		paraMap.put("orderId",orderId);	
		
		Page page = new Page();
		
		Map<String,Object> resultMap = orderQueryService.queryRefundOrderList(paraMap);
		
		List<Map> channelOrders = (List<Map>) resultMap.get("result");
		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		paraMap.put("page", page);
		
		log.info("page: "+page);
		
		
		
		for(int i=0;i<channelOrders.size();i++){
			Map map = channelOrders.get(i);  
			if(map.containsKey("createDate")){
				Long tmp = (Long) map.get("createDate");
				if(tmp!=null){
					map.put("createDate",this.getDateStr(tmp));
				}
			}
			if(map.containsKey("complateDate")){
				Long tmp = (Long) map.get("complateDate");
				if(tmp!=null){
					map.put("complateDate",this.getDateStr(tmp));
				}
			}
			
			channelOrders.set(i, map);
		}
		
		response.setHeader("Expires", "0");
		response.setHeader("Pragma" ,"public");
		response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Cache-Control", "public");

		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(("退款订单明细列表.xls").getBytes("UTF-8"),
						"ISO8859_1"));
		
		return new ModelAndView(excelView).addObject("list", channelOrders).addObject("nowDate", new Date()).addObject("partnerRefundOrderId",partnerRefundOrderId).addObject("orderId",orderId) ; //.addObject("pu",pu);
	}
	
	/**
	 * 退款单条查询 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView querySingleIncomeDetail(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		ModelAndView singleView = new ModelAndView(singleIncomeDetailCorp);
		String memberCode = (String) request.getSession().getAttribute(
				"memberCode");

		// 获取参数
		Map<String, Object> map = new HashMap<String, Object>();
		String serialNo = request.getParameter("serialNo");
		 
//		String orderSeq = request.getParameter("orderSeq");
		String orderId = StringUtil.null2String(request.getParameter("orderId"));
		
		// String partnerId = request.getParameter("partnerId"); FI 提供的参数,暂时不使用
		if (StringUtils.isBlank(serialNo) && !StringUtils.isNumeric(serialNo)) {
			return singleView;
		}
		map.put("memberCode", memberCode);
		map.put("serialNo", serialNo);
		// 查询
		
		Map<String, Object> resultMap = orderQueryService
				.querySingleIncomeDetail(map);
		
		Long createDate = (Long) resultMap.get("createDate");
		Long updateDate = (Long) resultMap.get("updateDate");
		
		if(createDate!=null)
			resultMap.put("createDate",getDateStr(createDate));
		
		if(updateDate!=null)
			resultMap.put("updateDate", getDateStr(updateDate));
		
		singleView.addObject("map", resultMap);
		
		return singleView;
	}
	
	/**
	 * 批量退款模板下载
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView downRefundTemplate(final HttpServletRequest request, final HttpServletResponse response) throws Exception{
		
		request.setCharacterEncoding("UTF-8");
		String path = getServletContext().getRealPath("/ftl/fi/tradequery/corp") ;
		String fileName = request.getParameter("fileName") ;
		FileInputStream fis = new FileInputStream(path + "//" + fileName) ;
		byte[] bt = new byte[fis.available()] ;
		fis.read(bt) ;
		
		response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		fileName = URLEncoder.encode(fileName, "UTF-8") ;
		response.setHeader("Content-Disposition", "attactment;filename="
				+ new String(fileName.getBytes("UTF-8"), "GBK"));
		response.setContentLength(bt.length);
		ServletOutputStream sos = response.getOutputStream();
		sos.write(bt);
		return null ;
		
	}
	
	/**
	 * 批量退款操作
	 * @param request
	 * @param response
	 * @return
	 * 
	 */
	public ModelAndView batchRefund(HttpServletRequest request, HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter() ;
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request ;
			//上传的文件
			CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file_batchRefund");
			Workbook book = Workbook.getWorkbook(file.getInputStream());
			@SuppressWarnings("unchecked")
			List<BatchRefundParamDTO> list = ExcelUtils.getListByReadShell(
					book.getSheet(0), 1, 0, 4, new String[] { "orderId",
							"orderAmount", "currencyCode", "refundAmount" },
					BatchRefundParamDTO.class);
			//对批量数据进行处理
			String memberCode = SessionHelper.getLoginSession().getMemberCode() ;
			List<BatchRefundParamDTO> batchErrorList = new ArrayList<BatchRefundParamDTO>() ;
			List<BatchRefundParamDTO> batchSuccessList = new ArrayList<BatchRefundParamDTO>() ;
			String reg = "^([0-9]{1})(.[0-9]{1,2})?$" ;
			String reg2 = "^([1-9][0-9]*)+(.[0-9]{1,2})?$" ;
			if(CollectionUtils.isNotEmpty(list)){
				for(BatchRefundParamDTO batchRefundParamDTO : list){
					String orderId = batchRefundParamDTO.getOrderId() ;
					//String orderAmount = batchRefundParamDTO.getOrderAmount() ;
					//String currencyCode = batchRefundParamDTO.getCurrencyCode() ;
					String refundAmount = batchRefundParamDTO.getRefundAmount() ;
					//商户订单号和退款金额不能为空
					if(StringUtils.isNotEmpty(orderId) || StringUtils.isNotEmpty(refundAmount)){
						//退款金额格式校验
						if(refundAmount.matches(reg) || refundAmount.matches(reg2) && refundAmount.compareTo("0") > 0){
							QueryDetailParaDTO queryDetailParaDTO = new QueryDetailParaDTO() ;
							queryDetailParaDTO.setOrderSeq(orderId);
							queryDetailParaDTO.setMemberCode(memberCode);
							//查询
							Map<String, Object> summRes = orderQueryService.countIncomeDetailList(queryDetailParaDTO);
							List<QueryDetailDTO> dateList = orderQueryService.queryIncomeDetailList(queryDetailParaDTO, 1,((Integer) summRes.get("listSize")).intValue());
							if(CollectionUtils.isNotEmpty(dateList)){
								for(QueryDetailDTO queryDetailDTO : dateList){
									//网关订单有效状态：0，3，4，5  （貌似1，2，6都是没有用的 ）
									//是否拒付
									String tradeOrderNo = queryDetailDTO.getTradeOrderNo() ;
									ChargeBackOrder chargeBackCondition = new ChargeBackOrder();
									chargeBackCondition.setTradeOrderNo(Long.valueOf(tradeOrderNo));
									Map result = refundOrderService.queryChargeBackOrder(
											chargeBackCondition, null);
									List<Map> chargeBackOrders = (List<Map>) result.get("result");
									if(CollectionUtils.isEmpty(chargeBackOrders)){
										//具备条件的订单进行退款
										String refundAbleAmount = queryDetailDTO.getRefundAmount() ;
										//可退款金额必须大于0
										if(StringUtils.isNotEmpty(refundAbleAmount) && refundAbleAmount.compareTo("0") > 0){
											//0：未付款，不能退款(该笔订单未支付成功)
											String orderStatus = queryDetailDTO.getOrderStatus() ;
											if("0".equals(orderStatus)){
												batchRefundParamDTO.setRemark("该笔订单未支付成功");
												batchErrorList.add(batchRefundParamDTO) ;
											}
											//4：交易成功，如果可退金额大于0－>可退款；如果可退金额小于等于0->不可退款
											//3：交易完成，如果可退金额大于0->可退款
											//5:交易失败，如果可退金额大于0->可退款
											if("4".equals(orderStatus) || "3".equals(orderStatus) || "5".equals(orderStatus)){
												//退款
												RefundParamDTO paramDTO = new RefundParamDTO();
												//可退金额取未乘1000转换的真实值
												BigDecimal refundAbleAmount_ = new BigDecimal(refundAbleAmount).divide(new BigDecimal(1000));
												BigDecimal refundAmount_ = new BigDecimal(refundAmount);
												if (refundAbleAmount_.compareTo(refundAmount_) < 0){
													batchRefundParamDTO.setRemark("退款金额大于可退金额");
													batchErrorList.add(batchRefundParamDTO) ;
												}else{
													if (refundAbleAmount_.compareTo(refundAmount_) == 0) {
														paramDTO.setRefundType("1");
													} else if (refundAbleAmount_.compareTo(refundAmount_) > 0) {
														paramDTO.setRefundType("2");
													}
													refundAmount_ = refundAmount_.multiply(new BigDecimal("100"));
													long ra = refundAmount_.setScale(0, BigDecimal.ROUND_HALF_UP)
															.longValue();
													String refundOrderId = "601" + System.currentTimeMillis();
													paramDTO.setOrderId(orderId);
													paramDTO.setPartnerId(memberCode);
													paramDTO.setRefundAmount(String.valueOf(ra));
													paramDTO.setRefundTime(getDateStr(System.currentTimeMillis()));
													paramDTO.setRefundOrderId(refundOrderId);
													paramDTO.setDestType("1");
													paramDTO.setTradeOrderNo(tradeOrderNo);
													paramDTO.setNoticeUrl("mps.ipaylinks.com");
													Map resultMap = refundOrderService.refund(paramDTO); 
													String responseCode = (String) resultMap.get("responseCode");
													String responseDesc = (String) resultMap.get("responseDesc");
													if(null != responseCode && responseCode.equals(ResponseCodeEnum.SUCCESS.getCode())){
														batchRefundParamDTO.setRemark("成功");
														batchSuccessList.add(batchRefundParamDTO) ;
													}else{
														batchRefundParamDTO.setRemark(responseDesc);
														batchErrorList.add(batchRefundParamDTO) ;
													}
												}
											}
										}else{
											//可退金额必须大于0
											batchRefundParamDTO.setRemark("该笔订单已全额退款");
											batchErrorList.add(batchRefundParamDTO) ;
										}
									}else{
										//该订单已发生拒付，不允许退款
										batchRefundParamDTO.setRemark("该笔订单发生过拒付");
										batchErrorList.add(batchRefundParamDTO) ;
									}
								}
							}else{
								//该笔订单不存在
								batchRefundParamDTO.setRemark("该笔订单不存在");
								batchErrorList.add(batchRefundParamDTO) ;
							}
						}else{
							//退款金额格式不正确
							batchRefundParamDTO.setRemark("退款金额格式不正确");
							batchErrorList.add(batchRefundParamDTO) ;
						}
					}else{
						//商户订单号、退款金额必填
						batchRefundParamDTO.setRemark("商户订单号、退款金额必填");
						batchErrorList.add(batchRefundParamDTO) ;
					}
				}
				//处理后面
				if(CollectionUtils.isEmpty(batchErrorList)){
					out.println("<script>parent.callback('Y|确定批量退款操作成功，我们会尽快为你处理！')</script>");
				}else{
					//存在批处理失败, 需要展示详情
					int successSize = batchSuccessList.size() ;
					JSONArray jsonArray = JSONArray.fromObject(batchErrorList) ;
					String jStr = jsonArray.toString() ;
					out.println("<script>parent.callback('D|"+ jStr +"|"+ successSize +"')</script>");
				}
			}else{
				out.println("<script>parent.callback('N|批量批量文件不能为空！')</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().println("<script>parent.callback('N|批量退款异常')</script>");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 批量退款失败详情
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toBatchErrorDetail(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView(batchErrorDetailView);
		try {
			String success = StringUtil.null2String(request.getParameter("success")) ;
			String batchErrorDetail = "[]";
			try {
				batchErrorDetail = URLDecoder.decode(URLDecoder.decode(StringUtil.null2String(request.getParameter("batchErrorDetail")), "UTF-8"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			JSONArray jsonArray = JSONArray.fromObject(batchErrorDetail) ;
			@SuppressWarnings("unchecked")
			List<BatchRefundParamDTO> list = JSONArray.toList(jsonArray, BatchRefundParamDTO.class) ;
			mv.addObject("success", success).addObject("list", list) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv ;
	}
	
	private  String getDateStr(final long millis) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);
		Date date = cal.getTime();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
		return sdf.format(date);
	}
	
	public void setQueryView(final String queryView) {
		this.queryView = queryView;
	}
	public void setApplyView(final String applyView) {
		this.applyView = applyView;
	}

	public void setOrderQueryService(final OrderQueryService orderQueryService) {
		this.orderQueryService = orderQueryService;
	}

	public void setRefundOrderService(final RefundOrderService refundOrderService) {
		this.refundOrderService = refundOrderService;
	}

	public void setResultView(final String resultView) {
		this.resultView = resultView;
	}

	public void setExcelView(final String excelView) {
		this.excelView = excelView;
	}

	public void setPayLinkResultView(final String payLinkResultView) {
		this.payLinkResultView = payLinkResultView;
	}

	public void setBatchErrorDetailView(String batchErrorDetailView) {
		this.batchErrorDetailView = batchErrorDetailView;
	}
	
	
}
