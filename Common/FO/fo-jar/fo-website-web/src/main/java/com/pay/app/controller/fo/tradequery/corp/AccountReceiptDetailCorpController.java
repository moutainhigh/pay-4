/**
 *  File: PayDetailPersonalController
 *  Description:个人单笔记录查询
 *  Date        Author      Changes
 *  2011-4-14   Sandy		create
 *  
 */
package com.pay.app.controller.fo.tradequery.corp;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.app.base.api.annotation.HasFeature;
import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.api.common.constans.CutsConstants;
import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.controller.fo.tradequery.BaseTradeQueryController;
import com.pay.inf.dao.Page;
import com.pay.tradequery.service.PayDetailService;

/**
 * @Description:企业账户收款记录查询
 * @author Sandy
 */
@HasFeature( { CutsConstants.FEATURE_MAXTRIX, CutsConstants.FEATURE_NORMAL })
public class AccountReceiptDetailCorpController extends BaseTradeQueryController {

	private PayDetailService payDetailService;
	
	/**
	 * 默认页
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_FO_RECEIPT_ACC")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return queryReceiptRecordFormAcc(request, response);
	}

	/**
	 * 企业账户收款记录查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_FO_RECEIPT_ACC")
	public ModelAndView queryReceiptRecordFormAcc(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView payDetailView = new ModelAndView(receiptRecordDetailCorp);
		Map<String,Object> map = validatQueryPara(request);
		//页面返回查询条件
		payDetailView.addObject("startTime", map.get("startTime"));
		payDetailView.addObject("endTime", map.get("endTime"));
		payDetailView.addObject("status", map.get("status"));
		payDetailView.addObject("serialNo", map.get("serialNo"));
		
		String act = request.getParameter("act");
		String export = request.getParameter("export");
		if (StringUtils.isBlank(act)) {
			payDetailView.addObject("startTime", getDate(-2));
			payDetailView.addObject("endTime", getDate(1));
			return payDetailView;
		}
		int pager_offset=1;
		if (StringUtils.isNumeric(request.getParameter("pager_offset"))) {
			pager_offset=Integer.parseInt(request.getParameter("pager_offset"));
		}
		String serialNo = request.getParameter("serialNo");
		if (StringUtils.isNotBlank(serialNo) && !StringUtils.isNumeric(serialNo)) {
			payDetailView.addObject("serialNo", serialNo);
			return payDetailView;
		}
		Page<Map<String,Object>> payDetailPage = payDetailService.queryReceiptRecordFormAccList(map, pager_offset, pageSize);
		if (StringUtils.isNotBlank(export)) {
			Page<Map<String,Object>> dateList = payDetailService.queryReceiptRecordFormAccList(map,1,payDetailPage.getTotalCount());
			return exportResult(request, response,dateList.getResult());
		}
		PageUtil pageUtil = new PageUtil(pager_offset,pageSize,payDetailPage.getTotalCount());
		payDetailView.addObject("payDetailList", payDetailPage.getResult());
		payDetailView.addObject("pu", pageUtil);
		
		return payDetailView;
	}
	
	/**
	 * 验证查询参数
	 * @param request
	 * @return
	 */
	private Map<String,Object> validatQueryPara(HttpServletRequest request) {
		LoginSession loginSession = SessionHelper.getLoginSession();
		Map<String,Object> map = new HashMap<String,Object>();
		String memberCode = loginSession.getMemberCode();
		String serialNo = request.getParameter("serialNo");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String status = request.getParameter("status");
		if (StringUtils.isNotBlank(serialNo) && StringUtils.isNumeric(serialNo)) {
			map.put("serialNo", serialNo);
		}
		if (StringUtils.isNotBlank(status)) {
			map.put("status", status);
		}
		map.put("startTime",getStartTime(startTime));
		map.put("endTime", getEndTime(endTime));
		map.put("memberCode", memberCode);
		return map;
	}
	
	/**
	 * 导出查询结果(excel)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView exportResult(HttpServletRequest request,
			HttpServletResponse response, Object dateList) throws Exception {
		setResonseHeader(request, response);
		return new ModelAndView(exportReceiptRecordDetailCorp).addObject("incomeDetailList", dateList);
	}
	/** set **/
	public void setPayDetailService(PayDetailService payDetailService) {
		this.payDetailService = payDetailService;
	}
}
