package com.pay.gateway.controller.bankadaptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.service.HessianInvokeService;

/**
 * @Title: BankBackResponseController
 * @Package com.pay.gateway.controller.bankadaptor
 * @Description: 支付账户充值后台回调
 * @author Gavin_Song(foxdog888@gmail.com)
 * @date 2011-4-9 上午10:39:27
 * @version V1.0
 */
@Service("bankHandler")
public class BankBackResponseController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(BankBackResponseController.class);

	private String bankChannel;
	private HessianInvokeService invokeService;

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}

	public void setBankChannel(String bankChannel) {
		this.bankChannel = bankChannel;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) {

		return null;

	}

}
