package com.pay.fi;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.client.TrueTxncoreClientService;
import com.pay.app.client.TxncoreClientService;
import com.pay.fi.dto.QueryDetailDTO;
import com.pay.fi.dto.QueryDetailParaDTO;
import com.pay.fi.helper.ChannelItemOrgCodeEnum;
import com.pay.fi.service.OrderQueryService;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;

/**
 * 网关订单查询
 * 
 * @author hhj
 *
 */
public class QueryDetailCropController extends QueryBaseTradeController {

	private OrderQueryService orderQueryService;
	private String queryView;

	private String exportinDetailCorp;

	private String incomeDetailCorp;

	private String singleIncomeDetailCorp;

	private TrueTxncoreClientService txncoreClientServiceImpl;
	
	public void setExportinDetailCorp(final String exportinDetailCorp) {
		this.exportinDetailCorp = exportinDetailCorp;
	}

	public OrderQueryService getOrderQueryService() {
		return orderQueryService;
	}

	public void setOrderQueryService(final OrderQueryService orderQueryService) {
		this.orderQueryService = orderQueryService;
	}

	public void setIncomeDetailCorp(final String incomeDetailCorp) {
		this.incomeDetailCorp = incomeDetailCorp;
	}

	public void setSingleIncomeDetailCorp(final String singleIncomeDetailCorp) {
		this.singleIncomeDetailCorp = singleIncomeDetailCorp;
	}

	/**
	 * 默认页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {

		return queryIncomeDetail(request, response);
	}

	@Override
	protected Date getDate(final int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.DATE, offset);

		return DateUtil.skipDateTime(DateUtil.getTheDate(new Date()), offset);
	}
	/**
	 * 预授权撤销
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView authTradeRepeal(final HttpServletRequest request,
			final HttpServletResponse response,HttpSession httpSession) throws Exception{
		String origOrderId=request.getParameter("origOrderId");
		String partnerId=httpSession.getAttribute("memberCode").toString();
		Map<String, Object> paraMap=new HashMap<String, Object>();
		paraMap.put("orderId",partnerId+System.currentTimeMillis()+new Random().nextInt(1000));
		paraMap.put("origOrderId", origOrderId);
		paraMap.put("partnerId", partnerId);
		txncoreClientServiceImpl.authTradeRepeal(paraMap);
		return queryIncomeDetail(request,response);
	}
	/**
	 * 查询收款明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView queryIncomeDetail(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		ModelAndView tradeSumaryView = new ModelAndView(incomeDetailCorp);
		String act = request.getParameter("act");
		String export = request.getParameter("export");
		String orderStatus = request.getParameter("orderStatus");
		String suffer = " 00:00";
		//商户订单号
		String orderSeq = request.getParameter("orderSeq");
		if (StringUtils.isBlank(act)) {
			tradeSumaryView.addObject("startTime", DateUtil.formatDateTime("yyyy-MM-dd",DateUtil.skipDateTime(new Date(),-2))+ suffer );
			tradeSumaryView.addObject("endTime", DateUtil.formatDateTime("yyyy-MM-dd",DateUtil.skipDateTime(new Date(),1))+ suffer);
			return tradeSumaryView;
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
		//validateForm
		QueryDetailParaDTO queryDetailParaDTO = validateForm(request);
		//状态为4时取3.4
		if("3".equals(queryDetailParaDTO.getOrderStatus())){
			queryDetailParaDTO.setOrderStatus("3,4");
		}
		
			List<QueryDetailDTO> incomeDetailList = orderQueryService
				.queryIncomeDetailList(queryDetailParaDTO, pager_offset,
						pageSize);
		Map<String, Object> summRes = orderQueryService
				.countIncomeDetailList(queryDetailParaDTO);
		
		tradeSumaryView.addObject("incomeDetailList", incomeDetailList);
		
		if (StringUtils.isNotBlank(export)) {
			List<QueryDetailDTO> dateList = orderQueryService
					.queryIncomeDetailList(queryDetailParaDTO, 1,
							((Integer) summRes.get("listSize")).intValue());
			return exportResult(request, response, dateList);
		}
		PageUtil pageUtil = new PageUtil(pager_offset, pageSize,
				((Integer) summRes.get("listSize")).intValue());
		// 基本查询页面参数回传
		
		tradeSumaryView.addObject("pu", pageUtil);
		tradeSumaryView.addObject("orderStatus", orderStatus);

		if (queryDetailParaDTO.getStartTime() == null
				|| queryDetailParaDTO.getEndTime() == null) {
			// 如果是高级查询且设置了支付时间查询，重置查询条件
			tradeSumaryView.addObject("orderStatus", "");
		} else {
			tradeSumaryView.addObject("startTime",
					queryDetailParaDTO.getStartTime());
			tradeSumaryView.addObject("endTime",
					queryDetailParaDTO.getEndTime());
			tradeSumaryView.addObject("orderStatus",
					queryDetailParaDTO.getOrderStatus());
		}

		tradeSumaryView.addObject("payStartTime",
				queryDetailParaDTO.getPayStartTime());
		tradeSumaryView.addObject("payEndTime",
				queryDetailParaDTO.getPayEndTime());
		tradeSumaryView.addObject("payStatus",
				queryDetailParaDTO.getPayStatus());
		tradeSumaryView.addObject("payChannel",
				queryDetailParaDTO.getPayChannel());
		tradeSumaryView.addObject("orderSeq", queryDetailParaDTO.getOrderSeq());
		

		Object totalAmount = summRes.get("listAmount");
		BigDecimal tAmount = new BigDecimal(String.valueOf(totalAmount==null?0:totalAmount));
		if (null != totalAmount) {
			tradeSumaryView.addObject(
					"listAmount",
					tAmount.divide(new BigDecimal(1000))
							.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		} else {
			tradeSumaryView.addObject("listAmount", "0.00");
		}
		tradeSumaryView.addObject("listSize", summRes.get("listSize"));
		
		// 是否高级查询
		String isAdvance = request.getParameter("isAdvance");
		tradeSumaryView.addObject("isAdvance", isAdvance);
		// 高级查询页面参数回传
		tradeSumaryView.addObject("serialNo", queryDetailParaDTO.getSerialNo());
		tradeSumaryView.addObject("protocolNo",
				queryDetailParaDTO.getProtocolNo());
		tradeSumaryView.addObject("notifyStatus",
				queryDetailParaDTO.getNotifyStatus());
		tradeSumaryView.addObject("sOrderAmount",
				queryDetailParaDTO.getsOrderAmount());
		tradeSumaryView.addObject("eOrderAmount",
				queryDetailParaDTO.geteOrderAmount());
		if(StringUtils.isEmpty(queryDetailParaDTO.getTradeType())){
			tradeSumaryView.addObject("tradeType","0");//如果交易类型为空给个值为0 标识全部的交易
		}else{
			tradeSumaryView.addObject("tradeType",
					queryDetailParaDTO.getTradeType());
		}
		return tradeSumaryView.addObject("startTime", queryDetailParaDTO.getStartTime())
				.addObject("endTime", queryDetailParaDTO.getEndTime())
				.addObject("orderStatus", orderStatus);
	}

	/**
	 * 查询单条收入详情
	 * 
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
		//String orderStatus = StringUtil.null2String(request.getParameter("orderStatus")) ;
		// 获取参数
		Map<String, Object> map = new HashMap<String, Object>();
		String serialNo = request.getParameter("serialNo");
		
		//String partnerId = request.getParameter("partnerId"); // FI 提供的参数,暂时不使用
		if (StringUtils.isBlank(serialNo) && !StringUtils.isNumeric(serialNo)) {
			return singleView;
		}
		
		map.put("memberCode", memberCode);
		map.put("tradeOrderNo", serialNo);
		// 查询
		
		Map<String, Object> resultMap = orderQueryService.querySingleIncomeDetail(map);


		String orgCode = (String)resultMap.get("orgCode");
		String tradeType = (String)resultMap.get("tradeType");
		Long tradeCreateDate = (Long) resultMap.get("tradeCreateDate");
		Long tradeCompleteDate = (Long) resultMap.get("tradeCompleteDate");
		Long paymentCreateDate = (Long) resultMap.get("paymentCreateDate") ;
		String cardNo = (String) resultMap.get("cardNo") ;
		Map<String,ChannelItemOrgCodeEnum> enumChannels = new HashMap<String,ChannelItemOrgCodeEnum>();
		for(ChannelItemOrgCodeEnum enumChannel:ChannelItemOrgCodeEnum.values()){
			enumChannels.put(enumChannel.getCode(), enumChannel);
		}
		if("4000".equals(tradeType) || "4001".equals(tradeType) || "4002".equals(tradeType) ){
			if(enumChannels.containsKey(orgCode)){
				String channelDesc = enumChannels.get(orgCode).getDesc();
				if(channelDesc != null)
					resultMap.put("payTypeDisplay",channelDesc.replace("CT_",""));
			}
		}
		if(StringUtils.isNotEmpty(cardNo) && cardNo.length() >= 16){
			String beforeStr = cardNo.substring(0,6) ;
			String endStr = cardNo.substring(12, cardNo.length()) ;
			cardNo = new StringBuilder(beforeStr).append("******").append(endStr).toString() ;
			resultMap.put("cardNo", cardNo) ;
		}
		
		if(tradeCreateDate!=null)
			resultMap.put("tradeCreateDate",getDateStr2(tradeCreateDate));
		
		if(tradeCompleteDate!=null)
			resultMap.put("tradeCompleteDate", getDateStr2(tradeCompleteDate));
		if(paymentCreateDate!=null)
			resultMap.put("paymentCreateDate", getDateStr2(paymentCreateDate));
		
		
		
		singleView.addObject("map", resultMap);
		
		
		
		return singleView;
	}

	/**
	 * 导出查询结果(excel)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView exportResult(final HttpServletRequest request,
			final HttpServletResponse response, final Object dateList) throws Exception {
		setResonseHeader(request, response);
		return new ModelAndView(exportinDetailCorp).addObject(
				"incomeDetailList", dateList).addObject("nowDate", new Date());
	}

	/**
	 * 校验查询参数
	 * 
	 * @param request
	 * @return
	 */
	private QueryDetailParaDTO validateForm(final HttpServletRequest request) {
		String memberCode = (String) request.getSession().getAttribute(
				"memberCode");

		// 获取入参
		String startTime = request.getParameter("startTime");
		
		String endTime = request.getParameter("endTime");
		String orderStatus = request.getParameter("orderStatus");
		String orderSeq = request.getParameter("orderSeq") == null ? ""
				: request.getParameter("orderSeq").trim();

		String tradeType = request.getParameter("tradeType") == null ? ""
				: request.getParameter("tradeType").trim();

		// add by huanghuajun 2011-06-16 协议流水号
		String protocolNo = request.getParameter("protocolNo") == null ? ""
				: request.getParameter("protocolNo").trim(); // 协议流水号

		// 高级查询入参
		String serialNo = request.getParameter("serialNo") == null ? ""
				: request.getParameter("serialNo").trim();
		// 交易流水号
		String sOrderAmount = request.getParameter("sOrderAmount");// 开始金额范围
		String eOrderAmount = request.getParameter("eOrderAmount");// 结束金额范围
		
		// add by fenghong at 2011-12-26
		String payStartTime = request.getParameter("payStartTime");
		String payEndTime = request.getParameter("payEndTime");

		// 组装查询参数
		QueryDetailParaDTO queryDetailParaDTO = new QueryDetailParaDTO();
		queryDetailParaDTO.setMemberCode(memberCode);
		
		if(!StringUtils.isEmpty(tradeType)){
			queryDetailParaDTO.setTradeType(tradeType);
		}
		
		if(null != startTime && !"".equals(startTime)){
			queryDetailParaDTO.setStartTime(strToDate(startTime));
		}
		
		if(null != endTime && !"".equals(endTime)){
			queryDetailParaDTO.setEndTime(strToDate(endTime));
		}
		
//		queryDetailParaDTO.setStartTime(getStartTime(startTime));
//		queryDetailParaDTO.setEndTime(getEndTime(endTime));
		
		if (!StringUtils.isBlank(orderStatus)) {
			queryDetailParaDTO.setOrderStatus(orderStatus);
		}else{
			queryDetailParaDTO.setOrderStatus("3,4,5,21,22,23,24");
		}

		if (!StringUtil.isEmpty(payStartTime)
				|| !StringUtil.isEmpty(payEndTime)) {
			queryDetailParaDTO.setStartTime(null);
//			queryDetailParaDTO.setEndTime(null);

			if (!StringUtil.isEmpty(payStartTime))
				queryDetailParaDTO.setPayStartTime(getStartTime(payStartTime));
			if (!StringUtil.isEmpty(payEndTime))
				queryDetailParaDTO.setPayEndTime(getStartTime(payEndTime));
			queryDetailParaDTO.setOrderStatus("3,4");
		}
		if (!StringUtils.isBlank(orderSeq)) {
			queryDetailParaDTO.setOrderSeq(orderSeq);
		}
		if (StringUtils.isNotBlank(protocolNo)
				&& StringUtils.isNumeric(protocolNo)) {
			queryDetailParaDTO.setProtocolNo(protocolNo);
		}
		if (StringUtils.isNotBlank(serialNo) && StringUtils.isNumeric(serialNo)) {
			queryDetailParaDTO.setSerialNo(serialNo);
		}
		if (StringUtils.isNotBlank(sOrderAmount) && this.isDouble(sOrderAmount)) {
			queryDetailParaDTO
					.setsOrderAmount(Double.parseDouble(sOrderAmount) * 1000);
		}
		if (StringUtils.isNotBlank(eOrderAmount) && this.isDouble(eOrderAmount)) {
			queryDetailParaDTO
					.seteOrderAmount(Double.parseDouble(eOrderAmount) * 1000);
		}

		if (queryDetailParaDTO.getStartTime() != null
				&& queryDetailParaDTO.getEndTime() != null) {
			if (queryDetailParaDTO.getStartTime().getTime() > queryDetailParaDTO
					.getEndTime().getTime()) {
//				queryDetailParaDTO.setEndTime(null);
			}
		}
		return queryDetailParaDTO;
	}

	public boolean isDouble(final String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?\\d+(\\.\\d*)?|\\.\\d+$");
		return pattern.matcher(str).matches();
	}
	
	private  String getDateStr(final long millis) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);
		Date date = cal.getTime();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	private  String getDateStr2(final long millis) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);
		Date date = cal.getTime();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	public Date strToDate(final String str){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") ;
		Date dDate = null;
		try {
			dDate = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dDate ;
	}

	public TrueTxncoreClientService getTxncoreClientServiceImpl() {
		return txncoreClientServiceImpl;
	}

	public void setTxncoreClientServiceImpl(TrueTxncoreClientService txncoreClientServiceImpl) {
		this.txncoreClientServiceImpl = txncoreClientServiceImpl;
	}


}
