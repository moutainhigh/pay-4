package com.pay.gateway.controller.cashier;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * 在线企业账户支付控制器
 * 
 * @author lei.jiangl
 * @date 2011-12-16 下午03:51:20
 * @version 0.1
 *
 */
public class OnlineBusinessPayController extends MultiActionController {

	private String retView;

	private final static String PRODUCT_CODE_ACCOUNT = "ACCOUNT_PAY";

	/**
	 * 验证码确认
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView checkCode(HttpServletRequest request,
			HttpServletResponse response) {
		String flag = "";
		String randCode = request.getSession().getAttribute("rand") == null ? ""
				: request.getSession().getAttribute("rand").toString();
		String code = request.getParameter("randCode") == null ? "" : request
				.getParameter("randCode");
		String loginName = request.getParameter("acctb1");// 账号
		String author = request.getParameter("acctb2");// 操作员

		Map resultMap = (Map) request.getSession(false).getAttribute(
				"cashierMap");
		// 清理原来的MSG
		resultMap.remove("errorMsg");
		resultMap.remove("verifyMsg");
		resultMap.put("handler", "ACCT");
		resultMap.put("CHECK_ACCTB_LN", loginName);
		resultMap.put("CHECK_ACCTB_AH", author);

		return new ModelAndView(retView, resultMap).addObject(
				"CHECK_ACCTB_FLAG", flag);
	}

	/**
	 * 组装信息到付款接口进行账户余额支付
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView checkSub(HttpServletRequest request,
			HttpServletResponse response) {

		return new ModelAndView(retView);
	}

	public void setRetView(String retView) {
		this.retView = retView;
	}

}
