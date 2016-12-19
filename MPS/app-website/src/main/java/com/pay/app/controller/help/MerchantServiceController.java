/**
*Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.app.controller.help;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * 帮助中心--商户服务
 * @author fjl
 * @date 2011-5-10
 */
public class MerchantServiceController extends MultiActionController {
	private String serviceExplain;
	private String swithcOnService;
	private String accManage;
	private String accSafe;
	private String fundsManage;
	private String serviceSwitch;
	private String tradeManage;


	/**
	  * 服务说明
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView serviceExplain (HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			return new ModelAndView(serviceExplain);
	}
	
	/**
	  * 接入服务
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView switchOnService (HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			return new ModelAndView(swithcOnService);
	}
	
	/**
	  * 账户管理
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView accountManage(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			return new ModelAndView(accManage);
	}
	
	/**
	  * 账户安全
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView accountSafe (HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			return new ModelAndView(accSafe);
	}
	
	/**
	  * 资金管理
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView fundsManage (HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			return new ModelAndView(fundsManage);
	}
	
	/**
	  * 服务接入
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView serviceSwitch (HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			return new ModelAndView(serviceSwitch);
	}
	
	/**
	  * 交易管理
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView tradeManage (HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			return new ModelAndView(tradeManage);
	}

	/**
	 * @param serviceExplain the serviceExplain to set
	 */
	public void setServiceExplain(String serviceExplain) {
		this.serviceExplain = serviceExplain;
	}

	/**
	 * @param swithcOnService the swithcOnService to set
	 */
	public void setSwithcOnService(String swithcOnService) {
		this.swithcOnService = swithcOnService;
	}

	/**
	 * @param accManage the accManage to set
	 */
	public void setAccManage(String accManage) {
		this.accManage = accManage;
	}

	/**
	 * @param accSafe the accSafe to set
	 */
	public void setAccSafe(String accSafe) {
		this.accSafe = accSafe;
	}

	/**
	 * @param fundsManage the fundsManage to set
	 */
	public void setFundsManage(String fundsManage) {
		this.fundsManage = fundsManage;
	}

	/**
	 * @param serviceSwitch the serviceSwitch to set
	 */
	public void setServiceSwitch(String serviceSwitch) {
		this.serviceSwitch = serviceSwitch;
	}

	/**
	 * @param tradeManage the tradeManage to set
	 */
	public void setTradeManage(String tradeManage) {
		this.tradeManage = tradeManage;
	}
	
	
}
