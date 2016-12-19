/**
*Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.app.controller.about;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * @author fjl
 * @date 2011-5-3
 */
public class AboutBaseController extends MultiActionController {
	/* 页面路径 */
	private String index;
	private String contact;
	private String privacy;
	private String contact1;
		
	
	/**
	 * 关于我们
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return new ModelAndView(index);
	}

	/**
	 * 联系我们
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView contact(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView(contact);
	}
	
	public ModelAndView contact1(HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		
		return new ModelAndView(contact1);
	}
	
	/**
	 * 隐私条款
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView privacy(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return new ModelAndView(privacy);
	}
	
	
	/**
	 * @param index the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	
	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * @param privacy the privacy to set
	 */
	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	
}
