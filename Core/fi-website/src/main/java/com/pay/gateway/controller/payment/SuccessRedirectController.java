/**
 * 
 */
package com.pay.gateway.controller.payment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * @author
 *
 */
public class SuccessRedirectController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(SuccessRedirectController.class);
	private String successView;
	private String successDirectView;

	public void setSuccessView(String successView) {
		this.successView = successView;
	}

	public void setSuccessDirectView(String successDirectView) {
		this.successDirectView = successDirectView;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("<<支付完成-进行跳转>>");

		try {

			return new ModelAndView(successView);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return null;
		}
	}

}
