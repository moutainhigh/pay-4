/**
 *  <p>File: ChargeQueryController.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.controller.fo.query;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.fundout.dto.OrderCondition;
import com.pay.fundout.dto.OrderInfo;
import com.pay.fundout.dto.OrderInfoDetail;
import com.pay.inf.dao.Page;
import com.pay.inf.comm.PageUtils;

public class ChargeQueryController extends AbstractQueryController {

	private Integer dealType;
	private String detailView;
	private String indexView;

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse resp,OrderCondition condition) {

		//OrderCondition condition = new OrderCondition();
		condition.setMemberCode(SessionHelper.getMemeberCodeBySession());
		condition.setDealType(dealType);
		//bind(request, condition, "queryCondition", null);
		String pager_offset = request.getParameter("pager_offset");
		Page<OrderInfo> page = PageUtils.getPage(request);
		page.setPageNo(pager_offset);
		Map<String, Object> resultMap = commonQueryService.queryOrderReturnMap(page, condition);
		PageUtil pageResult = new PageUtil(page.getPageNo(), page.getPageSize(), page
				.getTotalCount());// 分页处理
		resultMap.put("pu", pageResult);
		resultMap.put("queryCondition", condition);
		return new ModelAndView(indexView, resultMap);
	}

	public ModelAndView detail(HttpServletRequest request,
			HttpServletResponse resp) {

		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();
		String dealId = request.getParameter("dealId");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("dealId", dealId);
		map.put("memberCode", memberCode);
		OrderInfoDetail orderDetail = commonQueryService
				.queryOrderDetailByOrderId(map);

		return new ModelAndView(detailView).addObject("detail", orderDetail);
	}

	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}

	public void setDetailView(String detailView) {
		this.detailView = detailView;
	}

	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

}
