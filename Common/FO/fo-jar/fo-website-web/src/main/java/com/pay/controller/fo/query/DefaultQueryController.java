/**
 *  <p>File: DefaultQueryController.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.controller.fo.query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.fundout.dto.OrderCondition;
import com.pay.fundout.dto.OrderInfo;
import com.pay.inf.dao.Page;
import com.pay.inf.comm.PageUtils;
import com.pay.util.DateUtil;

public class DefaultQueryController extends AbstractQueryController {

	private String indexView;
	
	protected Date getDate(int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.DATE, offset);

		return DateUtil.skipDateTime(DateUtil.getTheDate(new Date()), offset);
	}
	protected String getDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}
	
	@OperatorPermission(operatPermission = "OPERATOR_FO_CHARGEWITHDRAW")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse resp) {

		Page<OrderInfo> page = PageUtils.getPage(request);
		Map<String, Object> resultMap = contructPageObject(request, page);// 分页处理
		OrderCondition condition = new OrderCondition();
		condition.setStartTime(getDate(getDate(-2)));
		condition.setEndTime(getDate(getDate(1)));
		condition.setDealType(0);
		condition.setOrderStatus(9);
		resultMap.put("queryCondition", condition);
		return new ModelAndView(indexView, resultMap);
	}

	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

}
