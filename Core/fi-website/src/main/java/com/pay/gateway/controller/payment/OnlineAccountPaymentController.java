/**
 * 
 */
package com.pay.gateway.controller.payment;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.fi.commons.HTTPProtocolHandleUtil;
import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.controller.wrapper.SafeControllerWrapper;
import com.pay.util.IPUtil;

/**
 * 在线账户支付
 * 
 * @author huhb
 *
 */
public class OnlineAccountPaymentController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(OnlineAccountPaymentController.class);
	private TxncoreClientService txncoreClientService;
	private String failView;
	private String successView;
	private String fromView;

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		// 设置请求回应的字符编码UTF-8，清缓存
		HTTPProtocolHandleUtil.setAll(request, response);
		String ip = IPUtil.getClientIP(request);
		// 验证基本信息
		SafeControllerWrapper req = new SafeControllerWrapper(request,
				new String[] { "password" });
		String loginName = request.getParameter("loginName");
		String password = req.getParameter("password");

		return null;
	}

	public void setFailView(String failView) {
		this.failView = failView;
	}

	public void setSuccessView(String successView) {
		this.successView = successView;
	}

	public void setFromView(String fromView) {
		this.fromView = fromView;
	}

	public void setTxncoreClientService(
			TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}
}