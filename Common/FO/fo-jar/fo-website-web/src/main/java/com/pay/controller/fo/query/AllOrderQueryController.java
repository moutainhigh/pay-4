/**
 *  <p>File: AllOrderQueryController.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.controller.fo.query;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.SessionHelper;
import com.pay.fundout.dto.OrderCondition;
import com.pay.fundout.dto.OrderInfo;
import com.pay.inf.dao.Page;
import com.pay.inf.comm.PageUtils;
import com.pay.util.TimeUtil;

public class AllOrderQueryController extends AbstractQueryController {

	private Integer dealType;
	private String indexView;
	
	@OperatorPermission(operatPermission = "OPERATOR_FO_CHARGEWITHDRAW")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse resp,OrderCondition condition) {

		//OrderCondition condition = new OrderCondition();
		condition.setMemberCode(SessionHelper.getMemeberCodeBySession());
		condition.setDealType(dealType);
		//bind(request, condition, "queryCondition", null);
		
		if (StringUtils.isBlank(condition.getStartTime())) {
			condition.setStartTime(TimeUtil.getTime(-3).substring(0,16));
		}
		if (StringUtils.isBlank(condition.getEndTime())) {
			condition.setEndTime(TimeUtil.getTime().substring(0,16));
		}

		Page<OrderInfo> page = PageUtils.getPage(request);
		
		String pager_offset = request.getParameter("pager_offset");
		page.setPageNo(pager_offset);
		Map<String, Object> resultMap = commonQueryService.queryOrderReturnMap(page, condition);
		PageUtil pageResult = new PageUtil(page.getPageNo(), page.getPageSize(), page
				.getTotalCount());// 分页处理
		resultMap.put("pu", pageResult);
		resultMap.put("queryCondition", condition);
		return new ModelAndView(indexView, resultMap);
	}

	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}

	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

}
