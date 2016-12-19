package com.pay.gateway.controller.bankdeatil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.fi.commons.HTTPProtocolHandleUtil;
import com.pay.gateway.model.BankDetail;
import com.pay.gateway.service.BankDetailService;
import com.pay.util.SpringControllerUtils;

public class BankDetailController extends MultiActionController {

	private BankDetailService bankDetailService;

	public void setBankDetailService(BankDetailService bankDetailService) {
		this.bankDetailService = bankDetailService;
	}

	/**
	 * 获得银行信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView getBankDetail(HttpServletRequest request,
			HttpServletResponse response) {
		// 去除缓存信息，设置字符集
		HTTPProtocolHandleUtil.setAll(request, response);
		String orgCode = request.getParameter("orgCode");
		BankDetail bankDetail = bankDetailService.queryBankDetail(orgCode);
		if (null != bankDetail) {
			SpringControllerUtils.renderText(response,
					bankDetail.getBankDetail());
		}
		return null;
	}

}
