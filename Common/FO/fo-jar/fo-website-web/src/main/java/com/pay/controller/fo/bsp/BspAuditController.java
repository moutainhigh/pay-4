/**
 * 交易中心审核控制器
 *  File: BspAuditController.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  Jun 29, 2011   terry ma      create
 */
package com.pay.controller.fo.bsp;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.service.MemberRelationQueryService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.api.wrapper.SafeControllerWrapper;
import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.fo.order.dto.audit.WorkOrderDto;
import com.pay.fo.order.service.audit.WorkorderService;
import com.pay.fo.order.service.bsp.BspWithdrawAuditService;
import com.pay.fo.order.service.bspwithdraw.BSPWithdrawOrderService;
import com.pay.fo.order.service.validate.PaymentValidateService;
import com.pay.inf.dao.Page;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;

public class BspAuditController extends MultiActionController {

	private static final String TIME_FMT = "yyyy-MM-dd";

	protected transient Log log = LogFactory.getLog(getClass());

	/** 每页显示记录数 **/
	protected int pageSize;
	private Map<String, String> urlMap;
	// 提现审核服务
	private BspWithdrawAuditService bspwithdrawauditservice;
	private BSPWithdrawOrderService bspWithdrawOrderService;
	private MemberRelationQueryService memberRelationQueryService;
	private PaymentValidateService paymentValidateService;
	private WorkorderService workorderService;

	public void setUrlMap(Map<String, String> urlMap) {
		this.urlMap = urlMap;
	}

	public void setMemberRelationQueryService(
			MemberRelationQueryService memberRelationQueryService) {
		this.memberRelationQueryService = memberRelationQueryService;
	}

	public void setWorkorderService(WorkorderService workorderService) {
		this.workorderService = workorderService;
	}

	public void setBspwithdrawauditservice(
			BspWithdrawAuditService bspwithdrawauditservice) {
		this.bspwithdrawauditservice = bspwithdrawauditservice;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	

	/**
	 * @param paymentValidateService the paymentValidateService to set
	 */
	public void setPaymentValidateService(
			PaymentValidateService paymentValidateService) {
		this.paymentValidateService = paymentValidateService;
	}

	/**
	 * 列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "AUDITOR_FO_WITHDRAW")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView listView = new ModelAndView(urlMap.get("bspList"));
		String sDate = DateUtil.getTime(TIME_FMT, -30);
		String endDate = DateUtil.getNowDate(TIME_FMT);
		return listView.addObject("startDate", sDate).addObject("endDate",
				endDate);
	}

	/**
	 * 列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "AUDITOR_FO_WITHDRAW")
	public ModelAndView search(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView listView = new ModelAndView(urlMap.get("bspList"));
		int pager_offset = 1;
		if (StringUtils.isNumeric(request.getParameter("pager_offset"))) {
			pager_offset = Integer.parseInt(request
					.getParameter("pager_offset"));
		}
		Map<String, Object> map = validatBatchQueryPara(request);

		Page<Map<String, Object>> bspWithdrawAuditPage = bspwithdrawauditservice
				.queryResultList(map, pager_offset, pageSize);
		PageUtil pageUtil = new PageUtil(pager_offset, pageSize,
				bspWithdrawAuditPage.getTotalCount());
		listView.addObject("bspWithdrawAuditList", bspWithdrawAuditPage
				.getResult());
		listView.addObject("pu", pageUtil);

		String startDate = request.getParameter("startDate");// 起始时间
		String endDate = request.getParameter("endDate"); // 结束时间
		String bspMemberName = request.getParameter("bspMemberName"); // 交易商名称

		listView.addObject("startDate", startDate);
		listView.addObject("endDate", endDate);
		listView.addObject("bspMemberName", bspMemberName != null ? StringUtils
				.trim(bspMemberName) : bspMemberName);
		return listView;
	}

	/**
	 * 查看
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "AUDITOR_FO_WITHDRAW")
	public ModelAndView view(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView view = new ModelAndView(urlMap.get("bspView"));
		String startDate = request.getParameter("startDate");// 起始时间
		String endDate = request.getParameter("endDate"); // 结束时间
		String bspMemberName = request.getParameter("bspMemberName"); // 交易商名称

		Map<String, Object> map = validatBatchQueryPara(request);
		String amount = request.getParameter("amount");
		view.addObject("orderSeq", map.get("orderSeq"));
		view.addObject("orderType", map.get("orderType"));
		view.addObject("createDate", map.get("createDate"));
		view.addObject("memberName", map.get("memberName"));
		view.addObject("amount", new BigDecimal(amount));
		view.addObject("status", map.get("status"));
		view.addObject("remark", map.get("remark"));

		view.addObject("startDate", startDate);
		view.addObject("endDate", endDate);
		view.addObject("bspMemberName", bspMemberName != null ? StringUtils
				.trim(bspMemberName) : bspMemberName);
		request.getSession().setAttribute("bspWithdrawAuditFlag",
				"withdrawConfirm");
		return view;
	}

	/**
	 * 审核通过
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "AUDITOR_FO_WITHDRAW")
	public ModelAndView approved(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView view = new ModelAndView(urlMap.get("bspView"));

		String startDate = request.getParameter("startDate");// 起始时间
		String endDate = request.getParameter("endDate"); // 结束时间
		String bspMemberName = request.getParameter("bspMemberName"); // 交易商名称
		String amount = request.getParameter("amount");

		LoginSession loginSession = SessionHelper.getLoginSession(request);
		String memberCode = loginSession.getMemberCode();

		Map<String, Object> map = validatBatchQueryPara(request);
		map.put("auditOperator", loginSession.getOperatorIdentity());

		view.addObject("orderSeq", map.get("orderSeq"));
		view.addObject("orderType", map.get("orderType"));
		view.addObject("createDate", map.get("createDate"));
		view.addObject("memberName", map.get("memberName"));
		view.addObject("amount", new BigDecimal(amount));
		view.addObject("remark", map.get("remark"));
		view.addObject("startDate", startDate);
		view.addObject("endDate", endDate);
		view.addObject("bspMemberName", bspMemberName != null ? StringUtils
				.trim(bspMemberName) : bspMemberName);

		// 防刷新
		synchronized (memberCode) {
			
			String token = (String) request.getSession().getAttribute(
					"bspWithdrawAuditFlag");
			if (null == token || !"withdrawConfirm".equals(token)) {
				return view.addObject("msg", "不允许重复提交！").addObject("status",
						'3');
			}
			request.getSession().removeAttribute("bspWithdrawAuditFlag");
		
			SafeControllerWrapper safeControllerWrapper = new SafeControllerWrapper(
					request, new String[] { "payPwd" });
			String payPwd = safeControllerWrapper.getParameter("payPwd");
			String checkPayPwd = paymentValidateService
					.validatePaymentPassword(Long.valueOf(memberCode),
							AcctTypeEnum.BASIC_CNY.getCode(), loginSession
									.getOperatorId(), payPwd);
	
			if (!StringUtil.isNull(checkPayPwd)) {
				view.addObject("status", map.get("status"));
				view.addObject("errorMsg", checkPayPwd);
				request.getSession().setAttribute("bspWithdrawAuditFlag",
						"withdrawConfirm");
				return view;
			}
	
			WorkOrderDto workOrderDto = workorderService.findByOrderSeq(Long
					.valueOf(String.valueOf(map.get("orderSeq"))));
			// 判断交易中心与交易商关系
			if (null != workOrderDto) {
				boolean isValid = memberRelationQueryService.isBePartOfTheBourse(
						memberCode, String.valueOf(workOrderDto
								.getCreateMembercode()));
				if (!isValid) {
					view.addObject("status", map.get("status"));
					view.addObject("errorMsg", "无效的请求!");
					return view;
				}
				
				if(workOrderDto.getStatus()!=0){
					view.addObject("status", map.get("status"));
					view.addObject("errorMsg", "请求已被处理!");
					return view;
				}
			}
			WorkOrderDto upWorkOrderDto = new WorkOrderDto();
			upWorkOrderDto.setSequenceId(workOrderDto.getSequenceId());
			upWorkOrderDto.setUpdateDate(new Date());
			upWorkOrderDto.setAuditOperator(loginSession.getOperatorIdentity());
			upWorkOrderDto.setAuditMembercode(Long.valueOf(loginSession.getMemberCode()));
			upWorkOrderDto.setAuditRemark(StringUtil.null2String(map.get("remark")));
			upWorkOrderDto.setStatus(1);
			upWorkOrderDto.setOrderSeq(workOrderDto.getOrderSeq());
			try{
				bspWithdrawOrderService.auditPass(upWorkOrderDto);
				view.addObject("status", '1');
				view.addObject("msg", "审核通过提交成功!");
			}catch(Exception e){
				log.error(e.getMessage(), e);
				view.addObject("status", map.get("status"));
				view.addObject("msg", "审核通过提交失败!");
				
			}
			
			return view;
		
		}
	}

	/**
	 * 审核拒绝
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "AUDITOR_FO_WITHDRAW")
	public ModelAndView refuse(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView view = new ModelAndView(urlMap.get("bspView"));
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		String memberCode = loginSession.getMemberCode();

		String startDate = request.getParameter("startDate");// 起始时间
		String endDate = request.getParameter("endDate"); // 结束时间
		String bspMemberName = request.getParameter("bspMemberName"); // 交易商名称
		String amount = request.getParameter("amount");

		Map<String, Object> map = validatBatchQueryPara(request);
		map.put("auditOperator", loginSession.getOperatorIdentity());

		view.addObject("orderSeq", map.get("orderSeq"));
		view.addObject("orderType", map.get("orderType"));
		view.addObject("createDate", map.get("createDate"));
		view.addObject("memberName", map.get("memberName"));
		view.addObject("amount", new BigDecimal(amount));
		view.addObject("remark", map.get("remark"));

		view.addObject("startDate", startDate);
		view.addObject("endDate", endDate);
		view.addObject("bspMemberName", bspMemberName != null ? StringUtils
				.trim(bspMemberName) : bspMemberName);

		// 防刷新
		synchronized (memberCode) {
			String token = (String) request.getSession().getAttribute(
					"bspWithdrawAuditFlag");
			if (null == token || !"withdrawConfirm".equals(token)) {
				return view.addObject("msg", "不允许重复提交！").addObject("status",
						'3');
			}
			request.getSession().removeAttribute("bspWithdrawAuditFlag");
		
			SafeControllerWrapper safeControllerWrapper = new SafeControllerWrapper(
					request, new String[] { "payPwd" });
			String payPwd = safeControllerWrapper.getParameter("payPwd");
			String checkPayPwd = paymentValidateService
					.validatePaymentPassword(Long.valueOf(memberCode),
							AcctTypeEnum.BASIC_CNY.getCode(), loginSession
									.getOperatorId(), payPwd);
	
			if (!StringUtil.isNull(checkPayPwd)) {
				view.addObject("status", map.get("status"));
				view.addObject("errorMsg", checkPayPwd);
				request.getSession().setAttribute("bspWithdrawAuditFlag",
						"withdrawConfirm");
				return view;
			}
	
			WorkOrderDto workOrderDto = workorderService.findByOrderSeq(Long
					.valueOf(String.valueOf(map.get("orderSeq"))));
			// 判断交易中心与交易商关系
			if (null != workOrderDto) {
				boolean isValid = memberRelationQueryService.isBePartOfTheBourse(
						memberCode, String.valueOf(workOrderDto
								.getCreateMembercode()));
				if (!isValid) {
					view.addObject("status", map.get("status"));
					view.addObject("errorMsg", "无效的请求!");
					return view;
				}
				
				if(workOrderDto.getStatus()!=0){
					view.addObject("status", map.get("status"));
					view.addObject("errorMsg", "请求已被处理!");
					return view;
				}
				
			}
	
			WorkOrderDto upWorkOrderDto = new WorkOrderDto();
			upWorkOrderDto.setSequenceId(workOrderDto.getSequenceId());
			upWorkOrderDto.setUpdateDate(new Date());
			upWorkOrderDto.setAuditOperator(loginSession.getOperatorIdentity());
			upWorkOrderDto.setAuditMembercode(Long.valueOf(loginSession.getMemberCode()));
			upWorkOrderDto.setAuditRemark(StringUtil.null2String(map.get("remark")));
			upWorkOrderDto.setStatus(2);
			upWorkOrderDto.setOrderSeq(workOrderDto.getOrderSeq());
			try{
				bspWithdrawOrderService.auditReject(upWorkOrderDto);
				view.addObject("status", '1');
				view.addObject("msg", "审核拒绝提交成功!");
			}catch(Exception e){
				log.error(e.getMessage(), e);
				view.addObject("status", map.get("status"));
				view.addObject("msg", "审核拒绝提交失败!");
			}
			return view;
		}
	}

	/**
	 * 提现审核
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, Object> validatBatchQueryPara(HttpServletRequest request) {

		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);

		// 获取入参
		String startDate = request.getParameter("startDate");// 起始时间
		String endDate = request.getParameter("endDate"); // 结束时间
		String bspMemberName = request.getParameter("bspMemberName"); // 交易商名称

		String orderSeq = request.getParameter("orderSeq");
		String orderType = request.getParameter("orderType");
		String createDate = request.getParameter("createDate");
		String amount = request.getParameter("amount");
		String remark = request.getParameter("remark");
		String status = request.getParameter("status");
		String memberName = request.getParameter("memberName");

		if (!StringUtils.isBlank(startDate)) {
			map.put("startDate", validateDate(startDate));
		}
		if (!StringUtils.isBlank(endDate)) {
			map.put("endDate", validateDate(endDate));
		}
		if (!StringUtils.isBlank(memberName)) {
			map.put("memberName", memberName);
		}
		if (!StringUtils.isBlank(bspMemberName)) {
			map.put("bspMemberName", bspMemberName != null ? StringUtils
					.trim(bspMemberName) : bspMemberName);
		}
		if (!StringUtils.isBlank(orderSeq)) {
			map.put("orderSeq", orderSeq);
		}
		if (!StringUtils.isBlank(orderType)) {
			map.put("orderType", orderType);
		}
		if (!StringUtils.isBlank(createDate)) {
			map.put("createDate", createDate);
		}
		if (!StringUtils.isBlank(amount)) {
			map.put("amount", amount);
		}
		if (!StringUtils.isBlank(remark)) {
			map.put("remark", remark);
		}
		if (!StringUtils.isBlank(status)) {
			map.put("status", status);
		}
		return map;
	}

	/**
	 * 验证时间格式,格式错误返回当天时间,null返回null
	 * 
	 * @param date
	 * @return
	 */
	protected Date validateDate(String date) {
		if (StringUtils.isBlank(date)) {
			return null;
		}
		Date returnDate = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			returnDate = dateFormat.parse(date);
		} catch (ParseException e) {
			dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				returnDate = dateFormat.parse(date);
			} catch (ParseException e1) {
				dateFormat = new SimpleDateFormat(TIME_FMT);
				try {
					returnDate = dateFormat.parse(date);
				} catch (ParseException e2) {
					log.error("格式化时间出错,使用默认时间");
					returnDate = new Date();
				}
			}
		}
		return returnDate;
	}

	/**
	 * @param bspWithdrawOrderService the bspWithdrawOrderService to set
	 */
	public void setBspWithdrawOrderService(
			BSPWithdrawOrderService bspWithdrawOrderService) {
		this.bspWithdrawOrderService = bspWithdrawOrderService;
	}
	
	
	
}
