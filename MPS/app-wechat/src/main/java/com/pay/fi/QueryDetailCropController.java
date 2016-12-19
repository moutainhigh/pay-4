package com.pay.fi;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.fi.dto.QueryDetailDTO;
import com.pay.fi.dto.QueryDetailParaDTO;
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
	// private static int pageSize = 5;
	private String queryView;

	private String exportinDetailCorp;

	private String incomeDetailCorp;

	private String singleIncomeDetailCorp;

	public void setExportinDetailCorp(String exportinDetailCorp) {
		this.exportinDetailCorp = exportinDetailCorp;
	}

	public OrderQueryService getOrderQueryService() {
		return orderQueryService;
	}

	public void setOrderQueryService(OrderQueryService orderQueryService) {
		this.orderQueryService = orderQueryService;
	}

	public void setIncomeDetailCorp(String incomeDetailCorp) {
		this.incomeDetailCorp = incomeDetailCorp;
	}

	public void setSingleIncomeDetailCorp(String singleIncomeDetailCorp) {
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
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return queryIncomeDetail(request, response);
	}

	protected Date getDate(int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.DATE, offset);

		return DateUtil.skipDateTime(DateUtil.getTheDate(new Date()), offset);
	}

	/**
	 * 查询收款明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView queryIncomeDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView tradeSumaryView = new ModelAndView(incomeDetailCorp);

		String act = request.getParameter("act");
		String export = request.getParameter("export");
		if (StringUtils.isBlank(act)) {
			tradeSumaryView.addObject("startTime", getDate(-2));
			tradeSumaryView.addObject("endTime", getDate(1));
			return tradeSumaryView;
		}
		// 页面参数
		int pager_offset = 1; // 当前页码
		int page_size = 5; // 每页条数
		pager_offset = request.getParameter("pager_offset") == null ? 1
				: Integer.parseInt(request.getParameter("pager_offset")); // 获取页面传过来的页码

		if (request.getParameter("numCount") != null
				&& !"".equals(request.getParameter("numCount"))) {// 获取页面传入的每页显示记录条数
			page_size = Integer.parseInt(request.getParameter("numCount"));
			if (page_size == 0)
				page_size = 5;// 此处为避免分页计算时出现被0除的情况
		}
		QueryDetailParaDTO queryDetailParaDTO = validateForm(request);
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

		if (queryDetailParaDTO.getStartTime() == null
				|| queryDetailParaDTO.getEndTime() == null) {
			// 如果是高级查询且设置了支付时间查询，重置查询条件
			tradeSumaryView.addObject("startTime", getStartTime(null));
			tradeSumaryView.addObject("endTime", getEndTime(null));
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
		// if (StringUtils.isNotBlank(isAdvance)) {
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
		// }

		return tradeSumaryView;
	}

	/**
	 * 查询单条收入详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView querySingleIncomeDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView singleView = new ModelAndView(singleIncomeDetailCorp);
		String memberCode = (String) request.getSession().getAttribute(
				"memberCode");

		// 获取参数
		Map<String, Object> map = new HashMap<String, Object>();
		String serialNo = request.getParameter("serialNo");
		
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
		String currencyCode = request.getParameter("currencyCode");
		
		resultMap.put("currencyCode",currencyCode);
		
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
	public ModelAndView exportResult(HttpServletRequest request,
			HttpServletResponse response, Object dateList) throws Exception {
		setResonseHeader(request, response);
		return new ModelAndView(exportinDetailCorp).addObject(
				"incomeDetailList", dateList);
	}

	/**
	 * 校验查询参数
	 * 
	 * @param request
	 * @return
	 */
	private QueryDetailParaDTO validateForm(HttpServletRequest request) {

//		String memberCode = (String) request.getSession().getAttribute(
//				"memberCode");
		String memberCode = "" ;
		LoginSession loginSession = SessionHelper.getLoginSession() ;
		if(null != loginSession){
			memberCode = loginSession.getMemberCode(); //商户号
		}
		logger.info(">>>>>>>>>>>>>>>>>>>>>memberCode.................." + memberCode);
		
		// 获取入参
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String orderStatus = request.getParameter("orderStatus");
		String orderSeq = request.getParameter("orderSeq") == null ? ""
				: request.getParameter("orderSeq").trim();

		// add by huanghuajun 2011-06-16 协议流水号
		String protocolNo = request.getParameter("protocolNo") == null ? ""
				: request.getParameter("protocolNo").trim(); // 协议流水号

		// 高级查询入参
		String serialNo = request.getParameter("serialNo") == null ? ""
				: request.getParameter("serialNo").trim();
		; // 交易流水号
		String sOrderAmount = request.getParameter("sOrderAmount");// 开始金额范围
		String eOrderAmount = request.getParameter("eOrderAmount");// 结束金额范围

		// add by fenghong at 2011-12-26
		String payStartTime = request.getParameter("payStartTime");
		String payEndTime = request.getParameter("payEndTime");

		// 组装查询参数
		QueryDetailParaDTO queryDetailParaDTO = new QueryDetailParaDTO();
		queryDetailParaDTO.setMemberCode(memberCode);
		queryDetailParaDTO.setStartTime(getStartTime(startTime));
		queryDetailParaDTO.setEndTime(getEndTime(endTime));
		if (!StringUtils.isBlank(orderStatus)) {
			queryDetailParaDTO.setOrderStatus(orderStatus);
		}

		if (!StringUtil.isEmpty(payStartTime)
				|| !StringUtil.isEmpty(payEndTime)) {
			queryDetailParaDTO.setStartTime(null);
			queryDetailParaDTO.setEndTime(null);

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
				queryDetailParaDTO.setEndTime(null);
			}
		}
		return queryDetailParaDTO;
	}

	public boolean isDouble(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?\\d+(\\.\\d*)?|\\.\\d+$");
		return pattern.matcher(str).matches();
	}
	
	private  String getDateStr(long millis) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);
		Date date = cal.getTime();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

}
