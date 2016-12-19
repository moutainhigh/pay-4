/**
 *  File: PayDetailPersonalController
 *  Description:个人单笔记录查询
 *  Date        Author      Changes
 *  2011-4-14   Sandy		create
 *  
 */
package com.pay.app.controller.fo.tradequery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.acc.service.member.MemberQueryService;
import com.pay.app.base.api.annotation.HasFeature;
import com.pay.app.base.api.common.constans.CutsConstants;
import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.inf.dao.Page;
import com.pay.inf.service.BankService;
import com.pay.tradequery.service.PayDetailService;

/**
 * @Description 个人交易明细、提现查询
 * @author Sandy
 */
@HasFeature( { CutsConstants.FEATURE_MAXTRIX, CutsConstants.FEATURE_NORMAL })
public class PersonTradeDetailController extends BaseTradeQueryController {

	private PayDetailService payDetailService;
	
	private WithdrawOrderService withdrawOrderService;
	
	/** 查询银行名称 **/
	private BankService bankService;
	
	/** 查询会员账号 **/
	private MemberQueryService memberQueryService;
	
	/**
	 * 默认页
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return queryPersonTradeDetailList(request, response);
	}

	/**
	 * 个人交易明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView queryPersonTradeDetailList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView payDetailView = new ModelAndView(tradeInfoDetail);
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();
		String act = request.getParameter("act");
		if (StringUtils.isBlank(act)) {
			return payDetailView;
		}
		int pager_offset=getPagerOffset(request);
		Map<String,Object> map = new HashMap<String, Object>();
		String flagIn = request.getParameter("flagIn");
		String flagOut = request.getParameter("flagOut");
		String flag = "1";
		String startAmount = request.getParameter("startAmount");
		String endAmount = request.getParameter("endAmount");
		String timeScope = request.getParameter("timeScope");
		
		if(StringUtils.isNotBlank(flagIn) || StringUtils.isNotBlank(flagOut)){
			if(StringUtils.isNotBlank(flagIn) && StringUtils.isNumeric(flagIn)) {
				flag = flagIn;
			}
			if(StringUtils.isNotBlank(flagOut) && StringUtils.isNumeric(flagOut)) {
				flag = flag.equals("1")?flagOut:"1";
			}
		}
		if (isNumber(startAmount)) {
			map.put("startAmount", Double.parseDouble(startAmount)*1000);
			payDetailView.addObject("startAmount", startAmount);
		}
		if (isNumber(endAmount)) {
			map.put("endAmount", Double.parseDouble(endAmount)*1000);
			payDetailView.addObject("endAmount", endAmount);
		}
		if (StringUtils.isNumeric(timeScope)) {//按时间段查询
			int beforDays = Integer.parseInt(timeScope);
			map.put("startTime",getDate( -1 * beforDays));
			map.put("endTime", getDate(0));
			payDetailView.addObject("timeScope", timeScope);
		}
		map.put("flag", flag);
		map.put("memberCode", memberCode);
		payDetailView.addObject("memberCode", memberCode);
		Page<Map<String,Object>> payDetailPage = payDetailService.queryPersonTradeDetailList(map, pager_offset, pageSize);
		String export = request.getParameter("export");
		if (StringUtils.isNotBlank(export)) {
			Page<Map<String,Object>> dateList = payDetailService.queryPersonTradeDetailList(map,1,payDetailPage.getTotalCount());
			return exportResult(request, response,dateList.getResult(),exportTradeInfoDetail);
		}
		PageUtil pageUtil = new PageUtil(pager_offset,pageSize,payDetailPage.getTotalCount());
		payDetailView.addObject("flagIn", flagIn);
		payDetailView.addObject("flagOut", flagOut);
		payDetailView.addObject("dateList", payDetailPage.getResult());
		payDetailView.addObject("pu", pageUtil);
		return payDetailView;
	}
	
	/**
	 * 个人提现查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView queryPersonWithdrawDetailList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView withdrawView = new ModelAndView(withdrawInfoDetail);
		String act = request.getParameter("act");
		if (StringUtils.isBlank(act)) {
			withdrawView.addObject("startTime", getStartTime(null));
			withdrawView.addObject("endTime", getEndTime(null));
			return withdrawView;
		}
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();
		int pager_offset=getPagerOffset(request);
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String status = request.getParameter("status");
		Map<String,Object> map  = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(startTime)) {
			map.put("startTime", getStartTime(startTime));
			withdrawView.addObject("startTime", getStartTime(startTime));
		}
		if (StringUtils.isNotBlank(startTime)) {
			map.put("endTime", getEndTime(endTime));
			withdrawView.addObject("endTime", getEndTime(endTime));
		}
		if (StringUtils.isNotBlank(status) && StringUtils.isNumeric(status)) {
			map.put("status", status);
			withdrawView.addObject("status", status);
		}
		map.put("memberCode", memberCode);
		Page<Map<String,Object>> page = payDetailService.queryWithdrawList(map, pager_offset, pageSize);
		String export = request.getParameter("export");
		if (StringUtils.isNotBlank(export)) {
			Page<Map<String,Object>> dateList = payDetailService.queryWithdrawList(map,1,page.getTotalCount());
			return exportResult(request, response,dateList.getResult(),exportWithdrawInfoDetail);
		}
		List<Map<String,Object>> list = page.getResult();
		PageUtil pageUtil = new PageUtil(pager_offset,pageSize,page.getTotalCount());
		withdrawView.addObject("dateList", list);
		withdrawView.addObject("pu", pageUtil);
		return withdrawView;
	}
	
	/**
	 * 个人单条交易记录详情查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView querySingleTradeDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView(singleTradeInfoDetail);
		String serialNo = request.getParameter("serialNo");
		String busiType = request.getParameter("busiType");
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("serialNo", serialNo);
		map.put("busiType", busiType);
		map.put("memberCode", memberCode);
		
		Map<String,Object> resultMap = payDetailService.queryPersonSingleTradeDetail(map);
		String payerMemberCode = null;
		String payeeMemberCode = null;
		String payerAcc = null;
		String payeeAcc = null;
		if (resultMap.get("payerMember") != null) {
			payerMemberCode = resultMap.get("payerMember").toString();
			payerAcc = memberQueryService.queryMemberBaseInfoByMemberCode(Long.parseLong(payerMemberCode)).getLoginName();
		}
		if (resultMap.get("payeeMember") != null && !"".equals(resultMap.get("payeeMember"))) {
			payeeMemberCode = resultMap.get("payeeMember").toString();
			payeeAcc = memberQueryService.queryMemberBaseInfoByMemberCode(Long.parseLong(payeeMemberCode)).getLoginName();
		}
		 
		modelAndView.addObject("payerAcc", payerAcc);
		modelAndView.addObject("payeeAcc", payeeAcc);
		modelAndView.addObject("map", resultMap);
		modelAndView.addObject("memberCode", memberCode);
		return modelAndView;
	}
	/**
	 * 个人单条提现记录详情查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView querySingleWithdrawDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();
		ModelAndView withdrawView = new ModelAndView(singleWithdrawInfoDetail);
		String serialNo = request.getParameter("serialNo");
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> resultMap = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		if (StringUtils.isNotBlank(serialNo) && StringUtils.isNumeric(serialNo)) {
			map.put("serialNo", serialNo);
			resultMap = payDetailService.queryWithdrawDetail(map);
		}
		withdrawView.addObject("date", resultMap);
		return withdrawView;
	}
	
	/**
	 * 导出查询结果(excel)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView exportResult(HttpServletRequest request,
			HttpServletResponse response, Object dateList,String view) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();
		setResonseHeader(request, response);
		return new ModelAndView(view).addObject("incomeDetailList", dateList).addObject("memberCode", memberCode);
	}
	
	/** set **/
	public void setPayDetailService(PayDetailService payDetailService) {
		this.payDetailService = payDetailService;
	}
	
	public void setWithdrawOrderService(
			WithdrawOrderService withdrawOrderService) {
		this.withdrawOrderService = withdrawOrderService;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}
}
