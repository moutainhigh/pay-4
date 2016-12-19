/**
 *  <p>File: AbstractQueryController.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.controller.fo.query;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.SessionHelper;
import com.pay.fundout.dto.OrderCondition;
import com.pay.fundout.dto.OrderInfo;
import com.pay.fundout.service.query.CommonQueryService;
import com.pay.inf.dao.Page;
import com.pay.util.StringUtil;

public abstract class AbstractQueryController extends MultiActionController {

	private final static String EXCEL_EXPORT_FILENAME = "充值提现记录查询结果";
	protected CommonQueryService commonQueryService;

	public ModelAndView downLoad(HttpServletRequest req,
			HttpServletResponse resp,OrderCondition condition) {

		String dealType = req.getParameter("dealType");
		//OrderCondition condition = new OrderCondition();
		condition.setMemberCode(SessionHelper.getMemeberCodeBySession());
		if (!StringUtil.isEmpty(dealType)) {
			condition.setDealType(Integer.parseInt(dealType));
		}
		//bind(req, condition, "queryCondition", null);
		try {
			resp.setContentType("application/vnd.ms-excel;charset=UTF-8");
			resp.setHeader("Content-Transfer-Encoding", "binary");
			resp.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			resp.setHeader("Pragma", "public");
			String agent = req.getHeader("User-Agent");
			if (agent.contains("MSIE")) {
				resp.setHeader("Content-Disposition", "attachment;filename="
						+ URLEncoder.encode(EXCEL_EXPORT_FILENAME, "UTF-8")
						+ ".xls");
			} else {
				resp.setHeader("Content-Disposition", "attachment;filename="
						+ new String((EXCEL_EXPORT_FILENAME + ".xls")
								.getBytes("UTF-8"), "ISO8859_1"));
			}

			List<OrderInfo> orderList = commonQueryService
					.queryOrdersByCondition(condition);

			return null;
		} catch (Exception e) {

			PageUtil pu = new PageUtil(0, 0, 0);// 分页处理 mv.addObject("pu", pu);
			// mv.addObject("qlHidden", "queryList_all");
			return null;
		}
	}

	protected Map<String, Object> contructPageObject(
			HttpServletRequest request, Page<OrderInfo> page) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		PageUtil pageResult = new PageUtil(page.getPageNo(), page.getPageSize(), page
				.getTotalCount());// 分页处理
		resultMap.put("page", page);
		resultMap.put("pu", pageResult);

		return resultMap;
	}

	public void setCommonQueryService(CommonQueryService commonQueryService) {
		this.commonQueryService = commonQueryService;
	}

}
