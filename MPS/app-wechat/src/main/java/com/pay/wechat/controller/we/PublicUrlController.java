/**
 * 
 */
package com.pay.wechat.controller.we;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * @author PengJiangbo
 *
 */
public class PublicUrlController extends MultiActionController {
	
	public ModelAndView toCompanyIntroduction(HttpServletRequest request, HttpServletResponse response){
		
		return new ModelAndView("/wechat/company_introduction") ;
	}
	
	public ModelAndView toPartners(HttpServletRequest request, HttpServletResponse response){
		
		return new ModelAndView("/wechat/partners") ;
	}
	
}
