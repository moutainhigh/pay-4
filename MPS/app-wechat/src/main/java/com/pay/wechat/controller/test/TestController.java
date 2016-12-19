/**
 * 
 */
package com.pay.wechat.controller.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * @author PengJiangbo
 *
 */
public class TestController extends MultiActionController {
	private String toPageTest ;
	
	private String abcPage ;
	
	public ModelAndView toPageTest(HttpServletRequest request, HttpServletResponse response){
		
		String username = "TestName" ;
		
		return new ModelAndView(toPageTest).addObject("username", username) ;
	}
	
	public ModelAndView abcPage(HttpServletRequest request, HttpServletResponse response){
		
		String pwd = "bosun123456" ;
		
		return new ModelAndView(abcPage).addObject("pwd", pwd) ;
	}
	
	public void setToPageTest(String toPageTest) {
		this.toPageTest = toPageTest;
	}
	public void setAbcPage(String abcPage) {
		this.abcPage = abcPage;
	}
	
}
