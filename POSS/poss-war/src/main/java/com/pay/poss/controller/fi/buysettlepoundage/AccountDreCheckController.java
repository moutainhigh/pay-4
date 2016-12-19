package com.pay.poss.controller.fi.buysettlepoundage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.acct.service.AcctService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acct.buySettleParities.model.AccApplyCheck;
import com.pay.acct.buySettleParities.service.AccApplyCheckService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.enterprisemanager.service.IEnterpriseService;
import com.pay.poss.security.util.SessionUserHolderUtil;

/****
 * 开通账户审核
 * 
 * @author delin
 * @date 2016年8月4日11:39:50
 */
public class AccountDreCheckController extends MultiActionController {

	private String accountIndex;

	private String accountList;

	private AccApplyCheckService accApplyCheckService;
	
	private IEnterpriseService enterpriseService;

	private AcctService acctService;
	
	
	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	public void setAccountIndex(String accountIndex) {
		this.accountIndex = accountIndex;
	}

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	public void setAccountList(String accountList) {
		this.accountList = accountList;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		String status=request.getParameter("status");
		return new ModelAndView(accountIndex).addObject("status", status);
	}
	public void setAccApplyCheckService(AccApplyCheckService accApplyCheckService) {
		this.accApplyCheckService = accApplyCheckService;
	}

	/***
	 * 查询账户开通审核
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) {
		String partnerId = request.getParameter("partnerId");
		String reason = request.getParameter("reason");
		String status = request.getParameter("status");
		Page<Object> page = PageUtils.getPage(request);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("partnerId", partnerId);
		params.put("reason", reason);
		params.put("status", status);
		params.put("id","0");
		List<AccApplyCheck> accApplyChecks = accApplyCheckService
				.queryByCriteria(params,page);
		return new ModelAndView(accountList).addObject("page", page).addObject("accApplyChecks", accApplyChecks);
	}
	
	/**
	 * 审核
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView check(HttpServletRequest request,HttpServletResponse response){
		String status=request.getParameter("status");  //批量审核开通 和 单个审核开通
		if("1".equals(status)){
			String partnerIds=request.getParameter("partnerId");
			String currencyCodes=request.getParameter("currencyCode");
			String[] partnerId = partnerIds.split(",");
			String[] currencyCode = currencyCodes.split(",");
			for (int i = 0; i < partnerId.length; i++) {
				String currencyBzj = String.valueOf(AcctTypeEnum.getGuaranteeAcctTypeByCurrency(currencyCode[i]));//保证金户
				String currencyBase =  String.valueOf(AcctTypeEnum.getBasicAcctTypeByCurrency(currencyCode[i])); //基本户
				String[] accountOfEnterprise=new String[]{currencyBzj,currencyBase};
				Boolean accountOfEnterpriseEditTrans = enterpriseService.accountOfEnterpriseEditTrans(partnerId[i], accountOfEnterprise);
			}
		}
		String ids=request.getParameter("id");
		String[] split = ids.split(",");
	//	String loginId = SessionUserHolderUtil.getLoginId();
		List<AccApplyCheck> accApplyChecks=new ArrayList<AccApplyCheck>();
			for (String id : split) {
				AccApplyCheck accApplyCheck=new AccApplyCheck();
				accApplyCheck.setId(Long.valueOf(id));
				accApplyCheck.setStatus(status);	
		//		accApplyCheck.setOperator(loginId);
				accApplyCheck.setCheckDate(new Date());
				accApplyChecks.add(accApplyCheck);
			}
		accApplyCheckService.check(accApplyChecks);
		return new ModelAndView(accountIndex);
	}
	
	/**
	 * 工作面板 展示 未审核数量
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView workPanels(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", "0");
		params.put("id","0");
		Integer count= accApplyCheckService.queryByCriteriaCount(params);
		String pendingCount=String.valueOf(count);
		try {
			response.getWriter().print(pendingCount);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
}
