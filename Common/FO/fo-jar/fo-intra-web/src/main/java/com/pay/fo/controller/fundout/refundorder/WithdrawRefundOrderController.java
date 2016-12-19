/**
 *  File: WithdrawController.java
 *  Description: APP用户申请提现Controller
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-10      Sandy_Yang      Changes
 *  
 *
 */
package com.pay.fo.controller.fundout.refundorder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.pay.fo.controller.fundout.WithdrawBaseController;
import com.pay.fo.order.dto.base.FundoutRefundOrderDTO;
import com.pay.fo.order.model.bankrefund.BankRefundOrderQueryModel;
import com.pay.fo.order.service.fundoutrefund.FundoutRefundOrderProcessService;
import com.pay.fo.order.service.fundoutrefund.FundoutRefundOrderQueryService;
import com.pay.fundout.service.OrderStatus;
import com.pay.fundout.withdraw.dto.operatorlog.OperatorlogDTO;
import com.pay.fundout.withdraw.service.bankrefund.BankRefundOrderService;
import com.pay.fundout.withdraw.service.bankrefund.BankRefundProcessService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.inf.dto.Bank;
import com.pay.inf.service.BankService;
import com.pay.poss.base.common.Constants;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;
import com.pay.util.UUIDUtil;

/**
 * 提现退款用到的 提现订单的Controller
 * 
 * @author Sandy_Yang
 */
public class WithdrawRefundOrderController extends WithdrawBaseController {

	private BankRefundOrderService bankRefundOrderService;

	private BankRefundProcessService bankRefundProcessService;

	private BankService bankService;
	
	private FundoutRefundOrderQueryService fundoutRefundOrderQueryService;
	
	private FundoutRefundOrderProcessService fundoutRefundOrderProcessService;
	
	
	public void setFundoutRefundOrderProcessService(
			FundoutRefundOrderProcessService fundoutRefundOrderProcessService) {
		this.fundoutRefundOrderProcessService = fundoutRefundOrderProcessService;
	}
	
	public void setFundoutRefundOrderQueryService(
			FundoutRefundOrderQueryService fundoutRefundOrderQueryService) {
		this.fundoutRefundOrderQueryService = fundoutRefundOrderQueryService;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public void setBankRefundOrderService(BankRefundOrderService bankRefundOrderService) {
		this.bankRefundOrderService = bankRefundOrderService;
	}

	public void setBankRefundProcessService(BankRefundProcessService bankRefundProcessService) {
		this.bankRefundProcessService = bankRefundProcessService;
	}

	/**
	 * 提现订单的初始化的查询页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView init(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = urlMap.get("init");
		List<Bank> bankList = this.bankService.getWithdrawBanks();
		return new ModelAndView(viewName).addObject("bankList", bankList);
	}

	/**
	 * 列出所有的订单order
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sequenceId = request.getParameter("sequenceId");
		String createTime = request.getParameter("createTime");
		String bankKy = request.getParameter("bankKy");
		String bankAcct = request.getParameter("bankAcct");
		String orderSeqId = request.getParameter("orderSeqId");
		String busiType = request.getParameter("busiType");

		Map<String,Object> query = new HashMap<String,Object>();
		try {
			if (sequenceId != null && sequenceId.trim().length() > 0) {
				query.put("sequenceId",new Long(sequenceId));
			}
			if (createTime != null && createTime.trim().length() > 0) {
				query.put("createTime",DateUtil.strToDate(createTime, "yyyy-MM-dd"));
			}
			if (bankKy != null && bankKy.trim().length() > 0 && !bankKy.equals("-1")) {
				query.put("bankKy",bankKy);
			}
			if (bankAcct != null && bankAcct.trim().length() > 0) {
				query.put("bankAcct",bankAcct);
			}
			if (orderSeqId != null && orderSeqId.trim().length() > 0) {
				query.put("orderSeqId",orderSeqId);
			}
			if (!StringUtil.isNull(busiType)) {
				query.put("busiType",Integer.valueOf(busiType));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String viewName = urlMap.get("search");
		Page<BankRefundOrderQueryModel> page = PageUtils.getPage(request);

		page = fundoutRefundOrderQueryService.queryAllowRefundList(page, query);
		return new ModelAndView(viewName).addObject("page", page);
	}

	/**
	 *得到特定的订单
	 */
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().setAttribute("uuidaddFundOrder", UUIDUtil.uuid());
		String viewName = urlMap.get("detail");
		String id = request.getParameter("id");
		Map<String,Object> query = new HashMap<String,Object>();
		query.put("sequenceId",id);
		BankRefundOrderQueryModel order = this.fundoutRefundOrderQueryService.queryRefundDetail(query);
		List<Map<String,String>> failDescList = bankRefundOrderService.getFailDesc();
		return new ModelAndView(viewName).addObject("order", order).addObject("failDescList",failDescList);
	}

	/**
	 *产生回退订单 WithdrawOrderAppDTO
	 */
	public ModelAndView addFundOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		FundoutRefundOrderDTO fundoutRefundOrderDTO = new FundoutRefundOrderDTO();
		BankRefundOrderQueryModel order = null;
		String viewName = urlMap.get("success");
		String remarks = StringUtil.null2String(request.getParameter("remarks"));
		String uuid = StringUtil.null2String(request.getParameter("uuid"));
		String uuidSession = StringUtil.null2String(request.getSession().getAttribute("uuidaddFundOrder"));
		request.getSession().removeAttribute("uuidaddFundOrder");
		try {
			if (!StringUtil.isNull(uuid) && !StringUtil.isNull(uuidSession)
					&& uuid.equalsIgnoreCase(uuidSession)) {

				String id = request.getParameter("id");
				Map<String,Object> query = new HashMap<String,Object>();
				query.put("sequenceId",id);
				order = this.fundoutRefundOrderQueryService.queryRefundDetail(query);
				if (order != null) {
					fundoutRefundOrderDTO.setBankOrderId(order.getBankOrderId());
					fundoutRefundOrderDTO.setCreateDate(new Date());
					fundoutRefundOrderDTO.setCreator(SessionUserHolderUtil.getLoginId());//订单创建者
					fundoutRefundOrderDTO.setOrderStatus(OrderStatus.INIT.getValue());
					fundoutRefundOrderDTO.setRefundReason(remarks);
					order.setRefundReason(remarks);
					fundoutRefundOrderDTO.setSrcOrderId(order.getOrderId());
					fundoutRefundOrderDTO.setSrcOrderType(order.getOrderType());
					fundoutRefundOrderDTO.setUniqueKey(order.getOrderId().toString());
					fundoutRefundOrderProcessService.createOrderRnTx(fundoutRefundOrderDTO);
				}
				
			}else{
				return new ModelAndView(urlMap.get("result")).addObject("result", "退款申请已被处理，请勿重复提交！").addObject("toUrl",
						request.getContextPath() + "/fundout-withdraw-withdrawrefund.do?method=init").addObject("hrefStr",
						"返回");
			}
		} catch (Exception e) {
			log.error("启动工作流失败", e);
			return new ModelAndView(urlMap.get("result")).addObject("result", "申请退款失败！").addObject("toUrl",
					request.getContextPath() + "/fundout-withdraw-withdrawrefund.do?method=init").addObject("hrefStr",
					"返回");
		}

		return new ModelAndView(viewName).addObject("order", order).addObject("remarks", remarks);
	}

	/**
	 * 查询申请的退款订单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView query(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Bank> bankList = this.bankService.getWithdrawBanks();
		request.getSession().setAttribute("uuidVerify", UUIDUtil.uuid());
		return new ModelAndView(URL("query")).addObject("bankList", bankList);
	}

	public ModelAndView queryHistory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Bank> bankList = this.bankService.getWithdrawBanks();
		return new ModelAndView(URL("verifyedRefundOrderInit")).addObject("bankList", bankList);
	}

	public ModelAndView queryRefundOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sequenceId = request.getParameter("sequenceId");
		String createTime = request.getParameter("createTime");
		String bankKy = request.getParameter("bankKy");
		String bankAcct = request.getParameter("bankAcct");
		String orderSeqId = request.getParameter("orderSeqId");
		String bankRefundOrderId = request.getParameter("bankRefundOrderId");
		String busiType = request.getParameter("busiType");

		Map<String,Object> query = new HashMap<String,Object>();
		try {
			if (sequenceId != null && sequenceId.trim().length() > 0) {
				query.put("sequenceId",new Long(sequenceId));
			}
			if (createTime != null && createTime.trim().length() > 0) {
				query.put("createTime",DateUtil.strToDate(createTime, "yyyy-MM-dd"));
			}
			if (bankKy != null && bankKy.trim().length() > 0 && !bankKy.equals("-1")) {
				query.put("bankKy",bankKy);
			}
			if (bankAcct != null && bankAcct.trim().length() > 0) {
				query.put("bankAcct",bankAcct);
			}
			if (orderSeqId != null && orderSeqId.trim().length() > 0) {
				query.put("orderSeqId",orderSeqId);
			}
			if (bankRefundOrderId != null && bankRefundOrderId.trim().length() > 0) {
				query.put("bankRefundOrderId",bankRefundOrderId);
			}
			if (!StringUtil.isNull(busiType)) {
				query.put("busiType",Integer.valueOf(busiType));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Page<BankRefundOrderQueryModel> page = PageUtils.getPage(request);
		String url = "";
		if (request.getParameter("verifyed") != null) {
			url = "verifyedRefundOrderList";
			query.put("hasRefund",113);//状态为已经退票
		} else {
			url = "queryRefundOrder";
		}
		page = fundoutRefundOrderQueryService.queryHasRefundList(page, query);
		return new ModelAndView(URL(url)).addObject("page", page);
	}

	/**
	 * 待审核详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView refundOrderdetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().setAttribute("uuidVerify", UUIDUtil.uuid());
		String viewName = urlMap.get("refundOrderDetail");
		String id = request.getParameter("id");
		Map<String,Object> query = new HashMap<String,Object>();
		query.put("status", Constants.ORDER_STATUS_INIT);
		query.put("bankRefundOrderId",id);
		try {
			BankRefundOrderQueryModel order = this.fundoutRefundOrderQueryService.queryHasRefundDetail(query);
			return new ModelAndView(viewName).addObject("order", order);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView(urlMap.get("result")).addObject("result", "请求失败！").addObject("toUrl",
					request.getContextPath() + "/fundout-withdraw-withdrawrefund.do?method=query").addObject("hrefStr",
					"返回");
		}
	}


	/**
	 * 批量审核
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView batchVerify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ids = request.getParameter("ids");
		String remarks = request.getParameter("remarks");
		String auditor = SessionUserHolderUtil.getLoginId();
		remarks=java.net.URLDecoder.decode(remarks); 
		String flag = request.getParameter("flag");
		String uuid = request.getParameter("uuidVerify");
		String uuidSession = StringUtil.null2String(request.getSession().getAttribute("uuidVerify"));
		request.getSession().removeAttribute("uuidVerify");
		try {
			if (!StringUtil.isNull(uuidSession) && !StringUtil.isNull(uuidSession)
					&& uuid.equalsIgnoreCase(uuidSession)) {
				
				String[] idArr = ids.split("[,]");
				for (int i = 0; i < idArr.length; i++) {
					String id = idArr[i];
					if ("yes".equalsIgnoreCase(flag)) { // 通过
						fundoutRefundOrderProcessService.auditPassRequestRdTx(Long.valueOf(id), auditor, remarks);
					} else if ("no".equalsIgnoreCase(flag)) { // 拒绝
						fundoutRefundOrderProcessService.auditRejectRequestRdTx(Long.valueOf(id), auditor, remarks);
					}
				}
			}else{
				return new ModelAndView(urlMap.get("result")).addObject("result", "审核请求已被处理，请勿重复提交！").addObject("toUrl",
						request.getContextPath() + "/fundout-withdraw-withdrawrefund.do?method=query").addObject("hrefStr",
						"返回");
			}

			return query(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView(urlMap.get("result")).addObject("result", "审核失败！").addObject("toUrl",
					request.getContextPath() + "/fundout-withdraw-withdrawrefund.do?method=query").addObject("hrefStr",
					"返回");
		}
	}

	@Override
	public OperatorlogDTO buildOperatorLog() {
		// TODO Auto-generated method stub
		return null;
	}
}
