/**
 * 
 */
package com.pay.poss.controller.fi.ordercompletion;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.fi.fill.condition.orderfillbatch.OrderFillBatchCondition;
import com.pay.fi.fill.model.FillRecordInfo;
import com.pay.fi.fill.model.OrderFillBatch;
import com.pay.fi.fill.service.OrderFillBatchService;
import com.pay.fi.fill.service.OrderFillRecordInfoService;
import com.pay.fi.fill.util.OrderFillBatchStatusEnum;
import com.pay.fi.fill.util.OrderFillRecordStatusEnum;
import com.pay.fi.fill.util.OrderFillResultDesEnum;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.client.ChannelClientService;
import com.pay.poss.dto.PaymentChannelItemDto;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.ExcelUtils;
import com.pay.util.StringUtil;

/**
 * 补单审核
 * 
 * @author PengJiangbo
 *
 */
public class OrderCompletionCheckController extends MultiActionController {

	private static final Log logger = LogFactory.getLog(OrderCompletionCheckController.class) ;
	
	private String indexView;
	private String listView;
	private String toCheckView;
	private String checkResultShowView ;
	private String checkPassDetailView ;
	private String checkRefuseDetailView ;

	private ChannelClientService channelClientService;
	private OrderFillBatchService orderFillBatchService;
	private OrderFillRecordInfoService orderFillRecordInfoService;

	/**
	 * 默认页面
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		List paymentChannelItems = extractPaymentChannelItems();
		return new ModelAndView(indexView).addObject("paymentChannelItems",
				paymentChannelItems);
	}

	@SuppressWarnings("rawtypes")
	private List extractPaymentChannelItems() {
		PaymentChannelItemDto paymentChannelItem = new PaymentChannelItemDto();
		List paymentChannelItems = channelClientService
				.queryChannelItem(paymentChannelItem);
		return paymentChannelItems;
	}

	/**
	 * 补单批次查询列表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView searchOrderFillBatch(HttpServletRequest request, HttpServletResponse response){
		String startTime = StringUtil.null2String(request.getParameter("startTime")) ;
		String endTime = StringUtil.null2String(request.getParameter("endTime")) ;
		String auditStatus = StringUtil.null2String(request.getParameter("auditStatus")) ;
		String orgCode = StringUtil.null2String(request.getParameter("orgCode"));
		OrderFillBatchCondition orderFillBatchCondition = new OrderFillBatchCondition() ;
		if(!"".equals(auditStatus)){
			orderFillBatchCondition.setAuditStatus(Integer.parseInt(auditStatus));
		}
		orderFillBatchCondition.setEndTime(endTime);
		orderFillBatchCondition.setOrgCode(orgCode);
		orderFillBatchCondition.setStartTime(startTime);
		if(!"".equals(startTime) && !"".equals(endTime)){
			orderFillBatchCondition.setTimeBetween(true);
		}
		Page<?> page = PageUtils.getPage(request) ;
		List<OrderFillBatch> resultList = this.orderFillBatchService
				.queryOrderFillBatchByCondition(orderFillBatchCondition, page);
		List<?> paymentChannelItems = extractPaymentChannelItems();
		return new ModelAndView(listView).addObject("resultList", resultList)
				.addObject("page", page)
				.addObject("paymentChannelItems", paymentChannelItems);
	}
	/**
	 * 到达审核明细列表页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toCheck(HttpServletRequest request,
			HttpServletResponse response) {

		String reqBatchNo = StringUtil.null2String(request
				.getParameter("reqBatchNo"));
		String orgCode = StringUtil.null2String(request.getParameter("orgCode")) ;
		if (null == reqBatchNo || "".equals(reqBatchNo)) {
			return new ModelAndView(listView).addObject(
					"reqBatchNoCanNotBeNull", "补单批次不能为空！");
		}
		List<FillRecordInfo> fillRecordInfos = this.orderFillRecordInfoService
				.findOrderFillRecordByReqBatchNo(Long.valueOf(reqBatchNo));
		JSONArray jArray = JSONArray.fromObject(fillRecordInfos) ;
		List<?> paymentChannelItems = extractPaymentChannelItems();
		return new ModelAndView(toCheckView).addObject("fillRecordInfos",
				fillRecordInfos).addObject("jArray", jArray)
				.addObject("reqBatchNo", reqBatchNo)
				.addObject("paymentChannelItems", paymentChannelItems)
				.addObject("orgCode", orgCode);
	}

	/**
	 * 审核文件上传
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws BiffException 
	 */
	@SuppressWarnings({ "unchecked"})
	public ModelAndView doCheck(HttpServletRequest request,
			HttpServletResponse response) throws BiffException, IOException {
		String jArray = URLDecoder.decode(StringUtil.null2String(request.getParameter("jArray")), "UTF-8") ;
		String reqBatchNo = StringUtil.null2String(request.getParameter("reqBatchNo")) ;
		String orgCode = StringUtil.null2String(request.getParameter("orgCode")) ;
		logger.info("doCheck request param reqBatchNo:" + reqBatchNo);
		if(null == jArray || "".equals(jArray)){
			List<FillRecordInfo> fillRecordInfos = fillRecordsBackView(jArray);
			return new ModelAndView(toCheckView).addObject("recordInfoNull",
					"该批次补单明细记录为空！").addObject("fillRecordInfos",
					fillRecordInfos);
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request ;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file") ;
		Workbook book = Workbook.getWorkbook(file.getInputStream()) ;
		List<FillRecordInfo> list = ExcelUtils.getListByReadShell(book.getSheet(0), 1, 0, 5,
				new String[] { "channelOrderNo", "returnNo", "currencyCode",
						"amount", "authorization" }, FillRecordInfo.class);
		if(list.size() == 0){
			List<FillRecordInfo> fillRecordInfos = fillRecordsBackView(jArray);
			return new ModelAndView(toCheckView).addObject("checkFileNull",
					"审核文件为空").addObject("fillRecordInfos", fillRecordInfos);
		}
		//审核文件记录和申请文件明细记录比对
		JSONArray jsonArray = JSONArray.fromObject(jArray) ;
		List<FillRecordInfo> fillRecordInfoReqs = JSONArray.toList(jsonArray, FillRecordInfo.class) ;
		int compareSize = fillRecordInfoReqs.size() ;
		int checkSize = list.size() ;
		if(compareSize > checkSize){
			compareSize = checkSize ;
		}
		List<FillRecordInfo> meetList = new ArrayList<FillRecordInfo>();
		List<FillRecordInfo> disMeetList = new ArrayList<FillRecordInfo>();
		for(int i=0; i<compareSize; i++){
			if(list.get(i).equals(fillRecordInfoReqs.get(i))){
				meetList.add(fillRecordInfoReqs.get(i)) ;
			}else{
				disMeetList.add(fillRecordInfoReqs.get(i)) ;
			}
		}
		//申请文件记录数大于审核文件
		if(compareSize == checkSize){
			int index = 0;
			for(int i=0; i<fillRecordInfoReqs.size()-compareSize; i++){
				disMeetList.add(fillRecordInfoReqs.get(compareSize + index)) ;
				index ++ ;
			}
		}
		JSONArray meetListJArray = JSONArray.fromObject(meetList) ;
		JSONArray disMeetJArray = JSONArray.fromObject(disMeetList) ;
		List<?> paymentChannelItems = extractPaymentChannelItems();
		return new ModelAndView(checkResultShowView)
				.addObject("meetList", meetList)
				.addObject("disMeetList", disMeetList)
				.addObject("reqBatchNo", reqBatchNo)
				.addObject("meetListJArrayStr", meetListJArray)
				.addObject("disMeetJArrayStr", disMeetJArray)
				.addObject("paymentChannelItems", paymentChannelItems)
				.addObject("orgCode", orgCode);
	}

	/**
	 * @param jArray
	 */
	private List<FillRecordInfo> fillRecordsBackView(String jArray) {
		JSONArray jArrayBack = JSONArray.fromObject(jArray) ;
		@SuppressWarnings("unchecked")
		List<FillRecordInfo> fillRecordInfos = JSONArray.toList(jArrayBack, FillRecordInfo.class) ;
		return fillRecordInfos ;
	}
	
	/**
	 * 审核[通过]
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView checkPass(HttpServletRequest request, HttpServletResponse response){
		String meetListJArrayStr = "" ;
		String disMeetJArrayStr = "" ;
		try {
			meetListJArrayStr = URLDecoder.decode(StringUtil
					.null2String(request.getParameter("meetListJArrayStr")),
					"UTF-8");
			disMeetJArrayStr = URLDecoder.decode(StringUtil.null2String(request
					.getParameter("disMeetJArrayStr")), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("URL解码出错了：" + e.getMessage());
		}
		//审核人
		int auditStatus = OrderFillBatchStatusEnum.auditPass.getStatus() ;
		Map<String, Object> hMap = constructCheckParam(request, auditStatus);
		int result = this.orderFillBatchService.updateAuditStatusByReqBatchNo(hMap) ;
		if(result > 0){
			//TODO 补单批次审核通过之后，对批次明细记录后续一系列校验
			//匹配系统渠道订单进行校验
			JSONArray meetListJArray = JSONArray.fromObject(meetListJArrayStr) ;
			JSONArray disMeetJArray = JSONArray.fromObject(disMeetJArrayStr) ;
			List<FillRecordInfo> recordMeetList = JSONArray.toList(meetListJArray, FillRecordInfo.class) ;
			List<FillRecordInfo> recordDisMeetList = JSONArray.toList(disMeetJArray, FillRecordInfo.class) ;
			//处理不匹配的记录，更新这些补单记录的状态为[补单失败]，失败原因为[审核记录不符]
			//批量更新
			int disMeetListSize = recordDisMeetList.size() ;
			if(disMeetListSize > 0){
				for(FillRecordInfo fri : recordDisMeetList){
					fri.setRecordStatus(OrderFillRecordStatusEnum.failed.getCode());
					fri.setFailReason(OrderFillResultDesEnum.audit_record_not_match.getReason());
				}
				this.orderFillRecordInfoService.updateFillRecordBatch(recordDisMeetList) ;
			}
			//处理匹配的记录
			//批量比较并更新状态
			int meetListSize = recordMeetList.size() ;
			if(meetListSize > 0){
				
			}
			//判断网关订单是否为成功
			
			//判断是否超过订单有效时间
			
		}
		return null ;
	}

	/**
	 * 审核拒绝
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView checkRefuse(HttpServletRequest request, HttpServletResponse response){
		int auditStatus = OrderFillBatchStatusEnum.auditRefuse.getStatus() ;
		Map<String, Object> hMap = constructCheckParam(request, auditStatus) ;
		int updateResult = this.orderFillBatchService.updateAuditStatusByReqBatchNo(hMap) ;
		if(updateResult>0){
			logger.info("更新补单批次记录状态成功：" + hMap.get("reqBatchNo")) ;
			Map<String, Object> paraMap = new HashMap<String, Object>() ;
			paraMap.put("reqBatchNo", hMap.get("reqBatchNo")) ;
			paraMap.put("failReason", OrderFillResultDesEnum.auditRefused.getReason()) ;
			paraMap.put("recordStatus", OrderFillRecordStatusEnum.failed.getCode()) ;
			this.orderFillRecordInfoService.updateRecordStatusByReqBatchNo(paraMap) ;
		}
		return new ModelAndView("redirect:/orderCompletionCheck.do?method=index") ;
	}
	
	/**
	 * 审核参数构造
	 * @param request
	 * @param auditStatus
	 * @return
	 */
	private Map<String, Object> constructCheckParam(HttpServletRequest request,
			int auditStatus) {
		String reqBatchNo = StringUtil.null2String(request.getParameter("reqBatchNo"));
		logger.info("构造审核参数..request para reqBatchNo:" + reqBatchNo);
		String remark = null;
		try {
			remark = StringUtil.null2String(new String(request.getParameter("remark").getBytes("iso-8859-1"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("不支持的编码！");
		}
		String auditor = SessionUserHolderUtil.getLoginId();
		Date auditTime = new Date() ;
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		hMap.put("reqBatchNo", reqBatchNo) ;
		hMap.put("auditor", auditor) ;
		hMap.put("auditTime", auditTime) ;
		hMap.put("auditStatus", auditStatus) ;
		hMap.put("remark", remark) ;
		return hMap;
	}
	
	/**
	 * 详情处理
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView viewDetail(HttpServletRequest request,
			HttpServletResponse response) {
		String reqBatchNo = StringUtil.null2String(request.getParameter("reqBatchNo")) ;
		String auditStatus = StringUtil.null2String(request.getParameter("auditStatus")) ;
		String orgCode = StringUtil.null2String(request.getParameter("orgCode")) ;
		OrderFillBatch orderFillBatchDto = this.orderFillBatchService.findOrderFillBatchByReqBatchNo(Long.valueOf(reqBatchNo)) ;
		List<?> paymentChannelItems = extractPaymentChannelItems();
		if(auditStatus.equals("2")){//审核拒绝
			return checkRefusedData(request, response, reqBatchNo)
					.addObject("orderFillBatchDto", orderFillBatchDto)
					.addObject("orgCode", orgCode)
					.addObject("paymentChannelItems", paymentChannelItems);
		}else if(auditStatus.equals("1")){//审核通过
			return checkPassedData(request, response, reqBatchNo).addObject(
					"orderFillBatchDto", orderFillBatchDto)
					.addObject("orgCode", orgCode)
					.addObject("paymentChannelItems", paymentChannelItems);
		}
		return new ModelAndView("redirect:/orderCompletionCheck.do?method=index") ;
	}

	/**
	 * 审核拒绝详情数据处理
	 * @param reqBatchNo
	 * @return
	 */
	public ModelAndView checkRefusedData(HttpServletRequest request, HttpServletResponse response, String reqBatchNo){
		List<FillRecordInfo> refusedList = this.orderFillRecordInfoService.findOrderFillRecordByReqBatchNo(Long.valueOf(reqBatchNo)) ;
		return new ModelAndView(checkRefuseDetailView).addObject("refusedList", refusedList) ;
	}
	/**
	 * 审核审核通过详情数据处理
	 * @param reqBatchNo
	 * @param auditStatus
	 * @return
	 */
	public ModelAndView checkPassedData(HttpServletRequest request, HttpServletResponse response, String reqBatchNo){
		List<FillRecordInfo> resultList = this.orderFillRecordInfoService.findOrderFillRecordByReqBatchNo(Long.valueOf(reqBatchNo)) ;
		List<FillRecordInfo> passSuccessList = new ArrayList<FillRecordInfo>() ;
		List<FillRecordInfo> passFailList = new ArrayList<FillRecordInfo>() ;
		for(FillRecordInfo fillRecordInfo : resultList){
			if(fillRecordInfo.getRecordStatus() == 1){
				passSuccessList.add(fillRecordInfo) ;
			}else{
				passFailList.add(fillRecordInfo) ;
			}
		}
		return new ModelAndView(checkPassDetailView).addObject(
				"passSuccessList", passSuccessList).addObject("passFailList",
				passFailList);
	}
	
	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	public void setListView(String listView) {
		this.listView = listView;
	}

	public void setChannelClientService(
			ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}

	public void setOrderFillBatchService(
			OrderFillBatchService orderFillBatchService) {
		this.orderFillBatchService = orderFillBatchService;
	}

	public void setOrderFillRecordInfoService(
			OrderFillRecordInfoService orderFillRecordInfoService) {
		this.orderFillRecordInfoService = orderFillRecordInfoService;
	}

	public void setToCheckView(String toCheckView) {
		this.toCheckView = toCheckView;
	}

	public void setCheckResultShowView(String checkResultShowView) {
		this.checkResultShowView = checkResultShowView;
	}

	public void setCheckPassDetailView(String checkPassDetailView) {
		this.checkPassDetailView = checkPassDetailView;
	}

	public void setCheckRefuseDetailView(String checkRefuseDetailView) {
		this.checkRefuseDetailView = checkRefuseDetailView;
	}

}
