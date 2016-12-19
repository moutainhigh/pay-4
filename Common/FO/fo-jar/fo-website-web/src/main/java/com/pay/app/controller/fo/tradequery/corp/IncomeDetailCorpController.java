/**
 *  File: TradeSummaryController.java
 *  Description:收支汇总查询
 *  Date        Author      Changes
 *  2011-4-11   Sandy		create
 *  
 */
package com.pay.app.controller.fo.tradequery.corp;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.app.base.api.annotation.HasFeature;
import com.pay.app.base.api.common.constans.CutsConstants;
import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.controller.fo.tradequery.BaseTradeQueryController;
import com.pay.inf.dao.Page;
import com.pay.poss.dto.fi.IncomeDetailParaDTO;
import com.pay.poss.service.gateway.IncomeDetailServiceFacade;

/**
 * @Description:企业收款明细查询
 * @author Sandy
 */
@HasFeature( { CutsConstants.FEATURE_MAXTRIX, CutsConstants.FEATURE_NORMAL })
public class IncomeDetailCorpController extends BaseTradeQueryController {

	private IncomeDetailServiceFacade incomeDetailServiceFacade;
	
	/**
	 * 默认页
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return queryIncomeDetail(request, response);
	}

	/**
	 * 查询收款明细
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
			tradeSumaryView.addObject("startTime", getStartTime(null));
			tradeSumaryView.addObject("endTime", getEndTime(null));
			return tradeSumaryView;
		}
		int pager_offset=1;
		if (StringUtils.isNumeric(request.getParameter("pager_offset"))) {
			pager_offset=Integer.parseInt(request.getParameter("pager_offset"));
		}
		IncomeDetailParaDTO incomeDetailDto = validateForm(request);
		Page<Map<String,Object>> incomeDetailList = incomeDetailServiceFacade.queryIncomeDetailList(incomeDetailDto,pager_offset,pageSize);
		if (StringUtils.isNotBlank(export)) {
			Page<Map<String,Object>> dateList = incomeDetailServiceFacade.queryIncomeDetailList(incomeDetailDto,1,incomeDetailList.getTotalCount());
			return exportResult(request, response,dateList.getResult());
		}
		PageUtil pageUtil = new PageUtil(pager_offset,pageSize,incomeDetailList.getTotalCount());
		//基本查询页面参数回传
		tradeSumaryView.addObject("incomeDetailList", incomeDetailList.getResult());
		tradeSumaryView.addObject("pu", pageUtil);
		tradeSumaryView.addObject("startTime", incomeDetailDto.getStartTime());
		tradeSumaryView.addObject("endTime", incomeDetailDto.getEndTime());
		tradeSumaryView.addObject("payStatus", incomeDetailDto.getPayStatus());
		tradeSumaryView.addObject("payChannel", incomeDetailDto.getPayChannel());
		tradeSumaryView.addObject("orderSeq", incomeDetailDto.getOrderSeq());
		tradeSumaryView.addObject("orderStatus", incomeDetailDto.getOrderStatus());
		
		//是否高级查询
		String isAdvance = request.getParameter("isAdvance");
//		if (StringUtils.isNotBlank(isAdvance)) {
		tradeSumaryView.addObject("isAdvance", isAdvance);
		//高级查询页面参数回传
		tradeSumaryView.addObject("serialNo", incomeDetailDto.getSerialNo());
		tradeSumaryView.addObject("notifyStatus", incomeDetailDto.getNotifyStatus());
		tradeSumaryView.addObject("sOrderAmount", incomeDetailDto.getsOrderAmount());
		tradeSumaryView.addObject("eOrderAmount", incomeDetailDto.geteOrderAmount());
//		}
		
		return tradeSumaryView;
	}
	
	/**
	 * 查询单条收入详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView querySingleIncomeDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView singleView = new ModelAndView(singleIncomeDetailCorp);
		LoginSession loginSession = SessionHelper.getLoginSession();
		//获取参数
		Map<String,Object> map = new HashMap<String,Object>();
		String memberCode = loginSession.getMemberCode();
		String serialNo = request.getParameter("serialNo");
//		String partnerId = request.getParameter("partnerId"); FI 提供的参数,暂时不使用
		if (StringUtils.isBlank(serialNo) && !StringUtils.isNumeric(serialNo)) {
			return singleView;
		}
		map.put("memberCode", memberCode);
		map.put("serialNo", serialNo);
		//查询
		Map<String,Object> resultMap = incomeDetailServiceFacade.querySingleIncomeDetail(map);
		singleView.addObject("map", resultMap);
		return singleView; 
	}

	/**
	 * 校验查询参数
	 * @param request
	 * @return
	 */
	private IncomeDetailParaDTO validateForm(HttpServletRequest request) {
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();

		// 获取入参
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String orderStatus = request.getParameter("orderStatus");
		String orderSeq = request.getParameter("orderSeq");
		
		//高级查询入参
		String serialNo = request.getParameter("serialNo");		   //交易流水号
		String sOrderAmount = request.getParameter("sOrderAmount");//开始金额范围
		String eOrderAmount = request.getParameter("eOrderAmount");//结束金额范围

		// 组装查询参数
		IncomeDetailParaDTO incomeDetailDto = new IncomeDetailParaDTO();
		incomeDetailDto.setMemberCode(memberCode);
		incomeDetailDto.setStartTime(getStartTime(startTime));
		incomeDetailDto.setEndTime(getEndTime(endTime));
		if (!StringUtils.isBlank(orderStatus)) {
			incomeDetailDto.setOrderStatus(orderStatus);
		}
		if (!StringUtils.isBlank(orderSeq)) {
			incomeDetailDto.setOrderSeq(orderSeq);
		}
		if (StringUtils.isNotBlank(serialNo) && StringUtils.isNumeric(serialNo)) {
			incomeDetailDto.setSerialNo(serialNo);
		}
		if (StringUtils.isNotBlank(sOrderAmount) && this.isDouble(sOrderAmount)) {
			incomeDetailDto.setsOrderAmount(Double.parseDouble(sOrderAmount)*1000);
		}
		if (StringUtils.isNotBlank(eOrderAmount) && this.isDouble(eOrderAmount)) {
			incomeDetailDto.seteOrderAmount(Double.parseDouble(eOrderAmount)*1000);
		}
		
		if (incomeDetailDto.getStartTime() != null && incomeDetailDto.getEndTime() != null) {
			if (incomeDetailDto.getStartTime().getTime() > incomeDetailDto.getEndTime().getTime()) {
				incomeDetailDto.setEndTime(null);
			}
		}
		return incomeDetailDto;
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
		return new ModelAndView(exportincomeDetailCorp).addObject("incomeDetailList", dateList);
	}

	/** set **/
	public void setIncomeDetailServiceFacade(
			IncomeDetailServiceFacade incomeDetailServiceFacade) {
		this.incomeDetailServiceFacade = incomeDetailServiceFacade;
	}

	public boolean isDouble(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?\\d+(\\.\\d*)?|\\.\\d+$");
		return pattern.matcher(str).matches();
	}
}
