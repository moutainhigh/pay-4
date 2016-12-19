package com.pay.poss.controller.fi.refund;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.fi.helper.ChannelItemOrgCodeEnum;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.poss.client.CrosspayTxncoreClientService;
import com.pay.poss.client.GatewayOrderQueryService;
import com.pay.poss.client.RefundExceptionBatchTxncoreClientService;
import com.pay.poss.controller.fi.commons.RefundStatusEnum;
import com.pay.poss.controller.fi.dto.RefundExceptionBatchDetailDTO;
import com.pay.poss.dto.RefundParamDTO;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.DateUtil;
import com.pay.util.ExcelUtils;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 网站管理poss 后台
 * 
 * @Description
 * @file SitesetController
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @auth:  peng jiangbo
 * @modify history:
 * 2016-07-25 nico.shao 修改批量处理摸版，支持106订单。
 * 2016-07-29 nico.shao 仿照 mm.zhang 在拒付这块的处理，对refundExceptionStatusUpdateBatch 函数增加了 synchronized 和 状态处理
 */
public class RefundController extends MultiActionController {

	private String initView;
	private String listView;
	private String detailView;
	private String resultView;
	private String applyView;
	private String queryInit;
	private String recordList;
	private String refundExceptionMonitor;
	private String refundExceptionMonitorList;
	private String refundExceptionBatchInitView;
	private String refundExceptionBatchListView ;
	private String refundExceptionBatchDetailListView ;
	
	private GatewayOrderQueryService gatewayOrderQueryService;
	
	private CrosspayTxncoreClientService crosspayTxncoreClientService;
	
	private RefundExceptionBatchTxncoreClientService refundExceptionBatchTxncoreClientService ;
	
	private EnterpriseBaseService enterpriseBaseService;
	
	//add by nico.shao 2016-07-29
	// 最好是加一个时间限制，可以在两个小时后，自动复原。如果出问题的话，比如某一个异常没有抓住，可以在两个小时后还原
	// 这样的话，可以无需重启poss 
	// 批量处理状态： 简单处理， 只允许一个批量处理状态在执行过程中  ，防止nginx 重发 
	
	private static String batchstatus = "0";
	private static long lastBatchSetTime = 0;
	
	/*
	 *  上一次设置的时间 和当前时间相比，是否超时了 
	 */
	private static boolean checkBatchProcessOverTime(long deltaSecond){
		long nowTime = new Date().getTime();
		long delta = nowTime - lastBatchSetTime;
		if((Math.abs(delta))> deltaSecond){
			return true;
		}
		return false;
	}
	
	private static String setBatchStatus() {
		batchstatus = "1";
		lastBatchSetTime = new Date().getTime();   
		return batchstatus;
	}

	private static String resetBatchStatus() {
		batchstatus = "0";
		return batchstatus;
	}
	
	/*
	 * 返回为 true ,表示在批处理过程中
	 */
	private static boolean checkInBatchStatus(){
		if(batchstatus.equals("1")){
			if(checkBatchProcessOverTime(7200)){
				resetBatchStatus();
				return false;
			}
			return true;
		}	
		
		return false;
	}
	//end 2016-07-29
	
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
	 * 退款申请页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView applyRefund(HttpServletRequest request,
			HttpServletResponse response){
		
		ModelAndView singleView = new ModelAndView(applyView);
 
		// 获取参数
		Map<String, Object> map = new HashMap<String, Object>();
		String serialNo = request.getParameter("serialNo");
		String partnerId = request.getParameter("partnerId");
		
		map.put("memberCode",partnerId); 
		map.put("serialNo", serialNo);
	
		// 查询
		
		Map<String, Object> resultMap = crosspayTxncoreClientService.queryTradeOrderDetail(map);
		
		String tradeOrderNo = String.valueOf(resultMap.get("tradeOrderNo"));
		String orderId=(String)resultMap.get("orderId");
		String refundAmount = String.valueOf(resultMap.get("refundAmount"));
		String  orderAmount = String.valueOf(resultMap.get("orderAmount"));
		
		singleView.addObject("tradeOrderNo", tradeOrderNo)
		          .addObject("orderId", orderId)
		          .addObject("refundAmount",refundAmount)
		          .addObject("orderAmount",orderAmount)
		          .addObject("partnerId",partnerId);
		
		return singleView;
	}
	
	
	/**
	 * 添加
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView singleView = new ModelAndView(initView);

		// 获取参数
		Map<String, Object> map = new HashMap<String, Object>();
		String tradeOrderNo = request.getParameter("tradeOrderNo");
		String orderId = request.getParameter("orderId");
		String partnerId = request.getParameter("partnerId");
		String orderAmount = request.getParameter("orderAmount");
		String refundAbleAmount = request.getParameter("refundAbleAmount");
		String refundAmount = request.getParameter("refundAmount");
		//交易币种
		String currencyCode = request.getParameter("currencyCode");
		map.put("currencyCode",currencyCode);
		
		RefundParamDTO paramDTO = new RefundParamDTO();
		BigDecimal refundAbleAmount_=new BigDecimal(refundAbleAmount);
		BigDecimal refundAmount_=new BigDecimal(refundAmount);
		
		if(refundAbleAmount_.compareTo(refundAmount_)==0){
			paramDTO.setRefundType("1");
		}else if(refundAbleAmount_.compareTo(refundAmount_)>0){
			paramDTO.setRefundType("2");
		}else {
			singleView.addObject("msg","退款金额不能超过可退金额");
			singleView.setViewName(resultView);
			return singleView;
		}
		
		refundAmount_ = refundAmount_.multiply(new BigDecimal("100"));
		
		long ra = refundAmount_.setScale(0,BigDecimal.ROUND_HALF_UP).longValue();
		
		String refundOrderId="701"+System.currentTimeMillis();
		
		paramDTO.setOrderId(orderId);
		paramDTO.setPartnerId(partnerId);
		paramDTO.setRefundAmount(String.valueOf(ra));
		paramDTO.setRefundTime(getDateStr(System.currentTimeMillis()));
		paramDTO.setRefundOrderId(refundOrderId);
		paramDTO.setDestType("1");
		paramDTO.setTradeOrderNo(tradeOrderNo);
		paramDTO.setNoticeUrl("mps.ipaylinks.com");
		paramDTO.setRefundSource("POSS");
		
		Map resultMap = crosspayTxncoreClientService.refund(paramDTO);
		
		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		
		logger.info("refund responseCode: "+responseCode+" ,responseDesc: "+responseDesc);
		
		singleView.addObject("msg",responseDesc);
		singleView.setViewName(resultView);
		return singleView;

	}
	
	private  String getDateStr(long millis) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);
		Date date = cal.getTime();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
		return sdf.format(date);
	}
	

	/**
	 * 查询网站信息列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String partnerId = StringUtil.null2String(request
				.getParameter("partnerId"));
		String orderId = StringUtil
				.null2String(request.getParameter("orderId"));
		String tradeOrderNo = StringUtil.null2String(request
				.getParameter("tradeOrderNo"));
		String status = StringUtil.null2String(request.getParameter("status"));
		String startTime = StringUtil.null2String(request
				.getParameter("startTime"));
		String endTime = StringUtil
				.null2String(request.getParameter("endTime"));

		Map paraMap = new HashMap();

		paraMap.put("partnerId", partnerId);
		paraMap.put("orderId", orderId);
		paraMap.put("tradeOrderNo", tradeOrderNo);
		paraMap.put("status", status);
		paraMap.put("beginTime", startTime);
		paraMap.put("endTime", endTime);

		Page page = PageUtils.getPage(request);
		paraMap.put("page", page);

		Map resultMap = gatewayOrderQueryService.queryTradeOrder(paraMap);
		List<Map> tradeOrders = (List<Map>) resultMap.get("result");

		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		return new ModelAndView(recordList).addObject("list", tradeOrders)
				.addObject("page", page);

	}
	/**
	 * 退款订单监控
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView refundExceptionMonitor (HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		ChannelItemOrgCodeEnum[] channelItems = ChannelItemOrgCodeEnum.values();
		return new ModelAndView(refundExceptionMonitor).addObject("paymentChannels",channelItems);
	}
	
	public ModelAndView refundExceptionMonitorList(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		ModelAndView view = new ModelAndView(refundExceptionMonitorList);
		Map paraMap = new HashMap();
		String merchantOrderNo = StringUtil.null2String(request.getParameter("merchantOrderNo"));
		String tradeOrderNo = StringUtil.null2String(request.getParameter("tradeOrderNo"));
		String refundOrderNo = StringUtil.null2String(request.getParameter("refundOrderNo"));
		String partnerId = StringUtil.null2String(request.getParameter("partnerId"));
		String status = StringUtil.null2String(request.getParameter("status"));
		String startTime = StringUtil.null2String(request.getParameter("startTime"));
		String endTime = StringUtil.null2String(request.getParameter("endTime"));
		String channelOrderNo = StringUtil.null2String(request.getParameter("channelOrderNo")) ;
		String reqOrgCode = StringUtil.null2String(request.getParameter("orgCode")) ; //渠道
		paraMap.put("merchantOrderNo", merchantOrderNo);
		paraMap.put("tradeOrderNo", tradeOrderNo);
		paraMap.put("refundOrderNo", refundOrderNo);
		paraMap.put("partnerId", partnerId);
		paraMap.put("status", status);
		paraMap.put("startTime", startTime);
		paraMap.put("endTime", endTime);
		paraMap.put("channelOrderNo", channelOrderNo) ;
		paraMap.put("orgCode", reqOrgCode) ;
		Page page = PageUtils.getPage(request);
		paraMap.put("page", page);
		Map map = crosspayTxncoreClientService.getRefundExceptionMonitorList(paraMap);
		List<Map> data = (List<Map>)map.get("result");
		for(Map m:data){
			String orgCode=String.valueOf(m.get("orgCode"));
			if(StringUtil.isEmpty(orgCode)){
				//处理渠道名称
				m.put("channelName","");
			}else{
				ChannelItemOrgCodeEnum channelItemOrgCodeEnum = ChannelItemOrgCodeEnum.getChannelItemByCode(orgCode);
				m.put("channelName", channelItemOrgCodeEnum!=null?channelItemOrgCodeEnum.getDesc():"");
			}
			String memberCode = String.valueOf(m.get("partnerId"));
			if(StringUtil.isEmpty(memberCode)){
				//处理商户名称
				m.put("merchantName", "");
			}else{
				m.put("merchantName", enterpriseBaseService.queryEnterpriseBaseByMemberCode(Long.valueOf(memberCode)).getZhName());
			}
			
			////处理日期格式
			Date date = Calendar.getInstance().getTime();
			date.setTime(Long.valueOf(m.get("createDate").toString()));
			m.put("createDate", date);
			//处理金额
			double refundAmount = Double.valueOf(m.get("refundAmount").toString());
			m.put("refundAmount", refundAmount/1000);
		}
		view.addObject("list", data);
		Map pageMap = (Map) map.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		view.addObject("page", page);
		return view;
	}
	
	public ModelAndView refundExceptionMonitorDownload(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String excelname= "TKYCJK_".concat(today);
		StringBuilder datesb = new StringBuilder(today);
		
		Map paraMap = new HashMap();
		String[] refundOrderNos = StringUtil.null2String(request.getParameter("refundOrderNos")).split(",");
		List<String> refundOrderList = new ArrayList<String>();
		Collections.addAll(refundOrderList, refundOrderNos);
		paraMap.put("refundOrderNos", refundOrderList);
		paraMap.put("page", new Page());
		paraMap.put("status", "0,1,3,4,5");
		Map map = crosspayTxncoreClientService.getRefundExceptionMonitorList(paraMap);
		List<Map> data = (List<Map>)map.get("result");
		
		for(Map m:data){
			String orgCode=String.valueOf(m.get("orgCode"));
			if(StringUtil.isEmpty(orgCode)){
				//处理渠道名称
				m.put("channelName","");
			}else{
				ChannelItemOrgCodeEnum channelItemOrgCodeEnum = ChannelItemOrgCodeEnum.getChannelItemByCode(orgCode);
				m.put("channelName", channelItemOrgCodeEnum!=null?channelItemOrgCodeEnum.getDesc():"");
			}
			String memberCode = String.valueOf(m.get("partnerId"));
			if(StringUtil.isEmpty(memberCode)){
				//处理商户名称
				m.put("merchantName", "");
			}else{
				m.put("merchantName", enterpriseBaseService.queryEnterpriseBaseByMemberCode(Long.valueOf(memberCode)).getZhName());
			}
			
			////处理日期格式
			Date date = Calendar.getInstance().getTime();
			date.setTime(Long.valueOf(m.get("createDate").toString()));
			m.put("createDate", new SimpleDateFormat("yyyy-MM-dd").format(date));
			//处理金额
			double refundAmount = Double.valueOf(m.get("refundAmount").toString());
			m.put("refundAmount", refundAmount/1000);
			
			String status = m.get("status") + "";
			if(StringUtil.isEmpty(status)){
				//处理渠道名称
				m.put("status","");
			}else{
				m.put("status", RefundStatusEnum.getByCode(Integer.parseInt(status)).getDescription());
			}
		}
		
		try {
			String[] headers = new String[] { "会员号", "商户名称","退款订单号", "商户订单号", "网关订单号",
					"渠道","渠道订单号","退款金额","退款币种","退款状态","创建时间"};
			String[] fields = new String[] { "partnerId", "merchantName", "refundOrderNo","orderId",
					"tradeOrderNo", "channelName","channelOrderNo","refundAmount","currencyCode",
					"status","createDate"};
				
			org.apache.poi.ss.usermodel.Workbook grid = RefundExcelGenerator.generateGridByMap("退款异常监控报表",
					headers, fields, data, today, datesb.toString());
			
			response.setContentType("application/force-download");
			response.setContentType("applicationnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ excelname + ".xls");
			grid.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  null;
	}
	
	public void refundExceptionHandle(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String operate = request.getParameter("operate");
		String refundOrderNo = request.getParameter("id");
		Map result = null ;
		String message = null;
		if("reTry".equalsIgnoreCase(operate)){
			//重新发起退款
			Map param = new HashMap();
			param.put("refundOrderNo", refundOrderNo);
			//重新发起退款不用再审核了直接传4
			param.put("processFlg", "4");
			result = crosspayTxncoreClientService.resendRefund(param);
		}else if("queryStatus".equalsIgnoreCase(operate)){
			//查询退款状态，这个要看渠道是否有退款查询接口
			//message = "暂无退款查询接口";
			/* add by LIBO at 20161012 begin */
			Map param = new HashMap();
			param.put("refundOrderNo", refundOrderNo);
			result = crosspayTxncoreClientService.queryRefundStatus(param);
			/* add by LIBO at 20161012 end */
		}else if("setSuccess".equalsIgnoreCase(operate)){
			//置为成功
			String additional = request.getParameter("additional");
			Map map = new HashMap();
			map.put("refundOrderNo", refundOrderNo);
			map.put("refundStatus", "2");
			map.put("refundChannelReturnNo", additional);
			result = crosspayTxncoreClientService.setRefundSuccess(map);
		}else if("setFail".equalsIgnoreCase(operate)){
			//置为失败
			String failReason = request.getParameter("additional");
			Map map = new HashMap();
			map.put("refundOrderNo", refundOrderNo);
			map.put("refundStatus", "6");
			map.put("failReason", failReason);
			result = crosspayTxncoreClientService.setRefundStatus(map);
		}else if("manual".equalsIgnoreCase(operate)){
			//人工处理
			/*Map map = new HashMap();
			map.put("refundOrderNo", refundOrderNo);
			map.put("refundStatus", "5");
			result = crosspayTxncoreClientService.setRefundStatus(map);*/
			result = manual(refundOrderNo);
		}
		if(result!=null && ResponseCodeEnum.SUCCESS.getCode().equals(result.get("responseCode"))){
			message = "操作成功";
		}else{
			if(StringUtil.isEmpty(message))message = "操作失败";
		}
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().println(message);
	}

	/**
	 * 退款置为人工处理
	 * @param refundOrderNo
	 * @return
	 */
	private Map manual(String refundOrderNo) {
		Map result;
		Map map = new HashMap();
		map.put("refundOrderNo", refundOrderNo);
		map.put("refundStatus", "5");
		result = crosspayTxncoreClientService.setRefundStatus(map);
		return result;
	}
	/**
	 * 农行批量退款状态更新模板下载
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView refundTemplateDown(HttpServletRequest request, HttpServletResponse response){
		StringBuilder errMsg = new StringBuilder("|") ;
		PrintWriter writer = null ;
		try {
			
			request.setCharacterEncoding("UTF-8") ;
			response.setCharacterEncoding("UTF-8");
			String path = this.getServletContext().getRealPath("/WEB-INF/jsp/refund/refundexception") ;
			String filename = request.getParameter("filename") ;
			FileInputStream fis = new FileInputStream(path + File.separator + filename) ;
			byte[] bt = new byte[fis.available()] ;
			fis.read(bt) ;
			response.setHeader("Content-Disposition", "attachment; filename=" + new String(filename.getBytes("UTF-8"), "GBK"));
			response.setContentLength(bt.length);
			ServletOutputStream sos = response.getOutputStream() ;
			sos.write(bt);
			sos.close();
			fis.close();
			//return null ;
		} catch (Exception e) {
			try {
				writer = response.getWriter() ;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			errMsg.append(e.getMessage()).append("|") ;
			e.printStackTrace();
			writer.print("批量模板下载失败:["+ errMsg.toString() + "]");
		}finally{
			if(null != writer) writer.close();
			errMsg = null ;
		}
		return null ;
	}
	
	/**
	 * 农行退款批量状态更新
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public synchronized ModelAndView refundExceptionStatusUpdateBatch(HttpServletRequest request, HttpServletResponse response){
		
		logger.info("refundExceptionStatusUpdateBatch begin");
		//(1)  检查批处理状态
		//add by nico.shao 2016-07-29
		if(checkInBatchStatus()){
			logger.error("在批次过程中，无法执行新的批处理过程");
			return new ModelAndView(refundExceptionMonitor).addObject("fileEmpty", "正在跑批处理中，请确认是否nginx自动重发。或者稍微再试。请查询跑批结果") ;
		}
		
		setBatchStatus();
		//end 2016-07-29 
		
		//(2) 解析文件   插入批次记录  以及明细记录   ，  对每一条明细，调用更新状态 
		String batchNo = "" ;
		try {
			MultipartHttpServletRequest multiPartHttpServletRequest = (MultipartHttpServletRequest) request ;
			CommonsMultipartFile file = (CommonsMultipartFile) multiPartHttpServletRequest.getFile("file") ;
			Workbook book = Workbook.getWorkbook(file.getInputStream()) ;
			List<RefundExceptionBatchDetailDTO> lists = ExcelUtils
					.getListByReadShell(book.getSheet(0), 1, 0, 2,
							new String[] { "channelOrderNo", "refundResult" },
							RefundExceptionBatchDetailDTO.class);
			//RefundExceptionBatchDTO refundExceptionBatchDTO = new RefundExceptionBatchDTO() ;
			if(CollectionUtils.isNotEmpty(lists)){
				try {
					batchNo = "M_" + DateUtil.formatDateTime("yyyyMMddHHsss", new Date()) ;
					Map paraMap = new HashMap() ;
					paraMap.put("batchNo", batchNo) ;
					paraMap.put("batchTotal", Long.valueOf(lists.size())) ;
					paraMap.put("creator", SessionUserHolderUtil.getSessionUserHolder().getUsername()) ;
					paraMap.put("lists", lists) ; 
					Map insertBatchResult = this.refundExceptionBatchTxncoreClientService.insertRefundExceptionBatch(paraMap) ;
					//批次及批次详情保存
					batchProcessRefundStatus(insertBatchResult);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("error:" + e);
				}
			}else{
				//文件空处理
				resetBatchStatus();
				logger.info("refundExceptionStatusUpdateBatch end");
				return new ModelAndView(refundExceptionMonitor).addObject("fileEmpty", "上传不件数据不能为空！") ;
			}
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		//批次，批次详情处理成功， 退款状态批量更新成功,批次详情更新结果updateResult更新成功，整个操作ok
		resetBatchStatus();  //整个操作已经结束了，所以，其他的操作，可以执行这个动作了 
		logger.info("refundExceptionStatusUpdateBatch reset");
		//(3)返回批次处理结果，主要是返回失败的明细订单
		Map<String, Object> map = new HashMap<String, Object>() ;
		Page page = PageUtils.getPage(request) ;
		map.put("batchNo", batchNo) ;
		map.put("updateResult", "F") ;
		map.put("page", page) ;
		Map resultMap = this.refundExceptionBatchTxncoreClientService.queryRefundExceptionBatchDetail(map) ;
		List<Map> refundExceptionBatchDetails = (List<Map>) resultMap.get("result") ;
		Map pageMap = (Map) resultMap.get("page") ;
		page = MapUtil.map2Object(Page.class, pageMap) ;
		if(CollectionUtils.isNotEmpty(refundExceptionBatchDetails)){
			for(Map m : refundExceptionBatchDetails){
				String createTime =(String) m.get("createTime").toString() ;
				String updateTime =(String) m.get("updateTime").toString() ;
				if(StringUtils.isNotEmpty(createTime)){
					m.put("createTime", processDate(createTime)) ;
				}
				if(StringUtils.isNotEmpty(updateTime)){
					m.put("updateTime", updateTime) ;
				}
			}
			logger.info("refundExceptionStatusUpdateBatch end");
			return new ModelAndView(refundExceptionBatchDetailListView).addObject("refundExceptionBatchDetails", refundExceptionBatchDetails)
					.addObject("page", page);
		}
		logger.info("refundExceptionStatusUpdateBatch end");
		return new ModelAndView(refundExceptionMonitor) ;
	}

	/*
	 * 批处理中，单笔退款订单状态的改变
	 * refundResult:   退款订单状态
	 * refundOrderNo:  退款订单号
	 * bChannelOrderQuery: 这个退款订单号，是否是根据渠道订单找到的。 true 表示是， false 表示直接根据退款订单号来的 
	 * (如果直接输入退款订单号，这里没有先去查询是否存在，所以使用这个变量，来修改提示值）
	 */
	private void batch_Signle_RefundStatus(String refundResult,String refundOrderNo,
			String batchDetailNo,
			boolean bChannelOrderQuery){
		if("S".equals(refundResult)){
			this.manual(refundOrderNo) ;	//先设置为人工处理状态，然后设置为成功
			//退款订单状态更改为成功
			Map map = new HashMap();
			map.put("refundOrderNo", refundOrderNo);
			map.put("refundStatus", "2");
			//map.put("refundChannelReturnNo", additional);
			Map result = this.crosspayTxncoreClientService.setRefundSuccess(map) ;
			//refund_exception_batch_detail表中处理结果置为成功
			if(result!=null && ResponseCodeEnum.SUCCESS.getCode().equals(result.get("responseCode"))){
				//更新结果
				String updateResult = "S" ;
				this.refundExceptionBatchDetailUpdate(updateResult, null, batchDetailNo);
			}else{
				String remark = "更改退款订单退款结果失败" ;
				if(!bChannelOrderQuery){
					remark = "更改退款订单退款结果失败，请确定订单是否存在" ;
				}
				String updateResult = "F" ;
				this.refundExceptionBatchDetailUpdate(updateResult, remark, batchDetailNo);
			}
		}
		else if("F".equals(refundResult)){
			this.manual(refundOrderNo) ;
			//退款订单状态更改为失败
			Map map = new HashMap();
			map.put("refundOrderNo", refundOrderNo);
			//map.put("refundStatus", "2");
			map.put("refundStatus", "6");  //6 表示设置为退款失败状态
			map.put("failReason", "批量设置退款失败");
			Map result = this.crosspayTxncoreClientService.setRefundStatus(map) ;
			//refund_exception_batch_detail表中处理结果置为失败
			if(result!=null && ResponseCodeEnum.SUCCESS.getCode().equals(result.get("responseCode"))){
				//更新结果
				String updateResult = "S" ;
				this.refundExceptionBatchDetailUpdate(updateResult, null, batchDetailNo);
			}else{
				String remark = "更改退款订单退款结果失败" ;
				if(!bChannelOrderQuery){
					remark = "更改退款订单退款结果失败，请确定订单是否存在" ;
				}
				String updateResult = "F" ;
				this.refundExceptionBatchDetailUpdate(updateResult, remark, batchDetailNo);
			}
		}
		else{
			//状态不存在， 暂不处理
			String remark = "无法识别状态" ;
			String updateResult = "F" ;
			this.refundExceptionBatchDetailUpdate(updateResult, remark, batchDetailNo);
		}
	}
	/**
	 * 批量处理退款结果  
	 * @param lists
	 * @param paraMap
	 * @param insertBatchResult
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	private void batchProcessRefundStatus(Map insertBatchResult) throws Exception{
		
		if(null != insertBatchResult && ResponseCodeEnum.SUCCESS.getCode().equals(insertBatchResult.get("responseCode"))){
			List<RefundExceptionBatchDetailDTO> lists = new ArrayList<RefundExceptionBatchDetailDTO>() ;
			List<Map> resultMap = (List<Map>) insertBatchResult.get("result") ;
			for(Map m : resultMap){
				//String createTime =(String) m.get("createTime") ;
				m.remove("createTime") ;
				m.remove("updateTime") ;
				RefundExceptionBatchDetailDTO refundExceptionBatchDetailDTO = MapUtil.map2Object(RefundExceptionBatchDetailDTO.class, m) ;
				lists.add(refundExceptionBatchDetailDTO) ;
			}
			//进行退款状态更新
			for(RefundExceptionBatchDetailDTO r : lists){
				Long channelOrderNo = r.getChannelOrderNo() ;
				//退款结果
				String refundResult = r.getRefundResult() ;
				//批次详情编码
				String batchDetailNo = r.getBatchDetailNo()+"" ;
				
				//add by nico.shao 2016-07-25 
				if(channelOrderNo == null){
					continue;
				}
				String strChannelOrderNo = channelOrderNo.toString();
				
				if(strChannelOrderNo.startsWith("106")){		//退款订单号
					String refundOrderNo = strChannelOrderNo;	
					//是否先去查找一下退款订单是否存在，比较好一些？  
					//没有现成的接口，所以，暂时就不查询了。 直接在结果里提醒一下
					batch_Signle_RefundStatus(refundResult, refundOrderNo, batchDetailNo,false);
					continue;
				}
				//end nico.shao 2016-07-25
				
				//配对渠道订单号
				Map channelMap = new HashMap<String, Object>() ;
				channelMap.put("channelOrderNo", channelOrderNo) ;
				Map channelResultMap = gatewayOrderQueryService.queryChannelOrder(channelMap);
				List<Map> channelOrders = (List<Map>) channelResultMap.get("result");
				//
				if(CollectionUtils.isNotEmpty(channelOrders)){
					//Map searchChannelMap = channelOrders.get(0) ;
					//查询退款订单
					Map refundParaMap = new HashMap() ;
					refundParaMap.put("channelOrderNo",  channelOrderNo) ;
					Map refundResultMap = this.gatewayOrderQueryService.queryRefundOrder(refundParaMap) ;
					List<Map> refundOrders = (List<Map>) refundResultMap.get("result") ;
					if(CollectionUtils.isNotEmpty(refundOrders)){
						int refundOrderSize = refundOrders.size() ;
						if(1 == refundOrderSize){
							Map refundOrder = refundOrders.get(0) ;
							//String refundOrderNo = (String) refundOrder.get("refundOrderNo") ;
							String refundOrderNo = refundOrder.get("refundOrderNo").toString() ;
							
							//处理单笔退款订单的数据  一大段代码改成下面的函数调用了  modify by nico.shao 2016-07-25
							batch_Signle_RefundStatus(refundResult, refundOrderNo, batchDetailNo,true);
							
						}else{
							//一条渠道订单号匹配到多条退款订单号，   视为异常情况（产品文档）
							String remark = "匹配到多笔退款订单" ;
							String updateResult = "F" ;
							this.refundExceptionBatchDetailUpdate(updateResult, remark, batchDetailNo);
						}
					}else{
						//退款订单不存在
						String remark = "匹配退款订单失败" ;
						String updateResult = "F" ;
						this.refundExceptionBatchDetailUpdate(updateResult, remark, batchDetailNo);
					}
				}else{
					//渠道订单不存在
					String remark = "找不到渠道订单号" ;
					String updateResult = "F" ;
					this.refundExceptionBatchDetailUpdate(updateResult, remark, batchDetailNo);
				}
			}
		}else{
			//退款状态更新批次及批次详情添加失败
			//System.out.println("pptv....");
			logger.error("退款状态更新批次及批次详情添加失败!");
			
		}
	}
	
	
	/**
	 * 更新fi.refund_exception_batch_detail
	 */
	private void refundExceptionBatchDetailUpdate(String updateResult, String remark, String batchDetailNo) {
		Map<String, Object> detailUpdateParaMap = new HashMap<String, Object>() ;
		detailUpdateParaMap.put("updateResult", updateResult) ;
		detailUpdateParaMap.put("remark", remark) ;
		detailUpdateParaMap.put("batchDetailNo", batchDetailNo) ;
		this.refundExceptionBatchTxncoreClientService.updateRefundExceptionBatchDetail(detailUpdateParaMap) ;
	}
	/**
	 * 批次首页
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView queryRefundExceptionBatchInit(HttpServletRequest request, HttpServletResponse response){
		ModelAndView view = new ModelAndView(refundExceptionBatchInitView) ;
		return view ;
	}
	/**
	 * 查询批次列表
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public ModelAndView queryRefundExceptionBatchList(HttpServletRequest request, HttpServletResponse response){
		
		ModelAndView view = new ModelAndView(refundExceptionBatchListView) ;
		String createTimeStart = StringUtil.null2String(request.getParameter("createTimeStart")) ;
		String createTimeEnd = StringUtil.null2String(request.getParameter("createTimeEnd")) ;
		Map<String, Object> paraMap = new HashMap<String, Object>() ;
		paraMap.put("createTimeStart", createTimeStart) ;
		paraMap.put("createTimeEnd", createTimeEnd) ;
		Page page = PageUtils.getPage(request);
		paraMap.put("page", page);
		Map resultMap = this.refundExceptionBatchTxncoreClientService.queryRefundExceptionBatch(paraMap) ;
		List<Map> refundExceptionBatchs = (List<Map>) resultMap.get("result") ;
		for(Map m : refundExceptionBatchs){
			String createTime = m.get("createTime").toString() ;
			if(StringUtils.isNotEmpty(createTime)){
				m.put("createTime", processDate(createTime)) ;
			}
		}
		Map pageMap = (Map) resultMap.get("page") ;
		page = MapUtil.map2Object(Page.class, pageMap) ;
		view.addObject("refundExceptionBatchs", refundExceptionBatchs);
		view.addObject("page", page) ;
		return view ;
	}
	/**
	 * 查询批次详情列表
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public ModelAndView queryRefundExceptionBatchSingleDetail(HttpServletRequest request, HttpServletResponse response){
		ModelAndView view = new ModelAndView(refundExceptionBatchDetailListView) ;
		String batchNo = StringUtil.null2String(request.getParameter("batchNo")) ;
		Page page = PageUtils.getPage(request) ;
		Map<String, Object> paraMap = new HashMap<String, Object>() ;
		paraMap.put("batchNo", batchNo) ;
		paraMap.put("page", page) ;
		Map resultMap = this.refundExceptionBatchTxncoreClientService.queryRefundExceptionBatchDetail(paraMap) ;
		List<Map> refundExceptionBatchDetails =(List<Map>) resultMap.get("result") ;
		for(Map m : refundExceptionBatchDetails){
			String createTime =(String) m.get("createTime").toString() ;
			String updateTime =(String) m.get("updateTime").toString() ;
			if(StringUtils.isNotEmpty(createTime)){
				m.put("createTime", processDate(createTime)) ;
			}
			if(StringUtils.isNotEmpty(updateTime)){
				m.put("updateTime", updateTime) ;
			}
		}
		Map pageMap =(Map) resultMap.get("page") ;
		page = MapUtil.map2Object(Page.class, pageMap) ;
		view.addObject("refundExceptionBatchDetails", refundExceptionBatchDetails) ;
		view.addObject("page", page) ;
		view.addObject("batchNo", batchNo);
		return view ;
	}
	
	/**
	 * 处理日期格式
	 * @param initView
	 */
	private Date processDate(String dateStr){
		Date date = null ;
		if(StringUtils.isNotEmpty(dateStr)){
			date = Calendar.getInstance().getTime() ;
			date.setTime(Long.valueOf(dateStr));
		}
		return date ;
	}
	
	public void setInitView(String initView) {
		this.initView = initView;
	}

	public void setListView(String listView) {
		this.listView = listView;
	}

	public void setDetailView(String detailView) {
		this.detailView = detailView;
	}

	public void setGatewayOrderQueryService(
			GatewayOrderQueryService gatewayOrderQueryService) {
		this.gatewayOrderQueryService = gatewayOrderQueryService;
	}
	
	public void setResultView(String resultView) {
		this.resultView = resultView;
	}

	public void setApplyView(String applyView) {
		this.applyView = applyView;
	}

	public void setQueryInit(String queryInit) {
		this.queryInit = queryInit;
	}

	public void setRecordList(String recordList) {
		this.recordList = recordList;
	}
	
	public String getRefundExceptionMonitor() {
		return refundExceptionMonitor;
	}

	public void setRefundExceptionMonitor(String refundExceptionMonitor) {
		this.refundExceptionMonitor = refundExceptionMonitor;
	}

	public void setCrosspayTxncoreClientService(
			CrosspayTxncoreClientService crosspayTxncoreClientService) {
		this.crosspayTxncoreClientService = crosspayTxncoreClientService;
	}

	public EnterpriseBaseService getEnterpriseBaseService() {
		return enterpriseBaseService;
	}

	public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

	public String getRefundExceptionMonitorList() {
		return refundExceptionMonitorList;
	}

	public void setRefundExceptionMonitorList(String refundExceptionMonitorList) {
		this.refundExceptionMonitorList = refundExceptionMonitorList;
	}

	public void setRefundExceptionBatchTxncoreClientService(
			RefundExceptionBatchTxncoreClientService refundExceptionBatchTxncoreClientService) {
		this.refundExceptionBatchTxncoreClientService = refundExceptionBatchTxncoreClientService;
	}

	public void setRefundExceptionBatchInitView(String refundExceptionBatchInitView) {
		this.refundExceptionBatchInitView = refundExceptionBatchInitView;
	}

	public void setRefundExceptionBatchListView(String refundExceptionBatchListView) {
		this.refundExceptionBatchListView = refundExceptionBatchListView;
	}

	public void setRefundExceptionBatchDetailListView(
			String refundExceptionBatchDetailListView) {
		this.refundExceptionBatchDetailListView = refundExceptionBatchDetailListView;
	}
	
}
