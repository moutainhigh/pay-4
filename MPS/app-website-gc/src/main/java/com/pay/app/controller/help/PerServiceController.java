/**
*Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.app.controller.help;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *第二版 个人服务
 * @author 戴德荣
 * @date 2012-2-17
 */
public class PerServiceController extends MultiActionController {

	private String indexView;
	/**
	 * 人个服务页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return new ModelAndView(indexView);
	}
	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}
	
	
	
}
