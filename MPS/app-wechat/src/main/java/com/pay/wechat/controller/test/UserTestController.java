/**
 * 
 */
package com.pay.wechat.controller.test;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.wechat.model.UserTest;
import com.pay.wechat.service.usertest.UserTestService;

/**
 * @author PengJiangbo
 *
 */
public class UserTestController extends MultiActionController{
	
	//注入
	private UserTestService userTestService ;
	
	public ModelAndView addUserTest(HttpServletRequest request, HttpServletResponse response, UserTest userTest){
		
		String isSubmit = request.getParameter("isSubmit");
		if(null == isSubmit || "".equalsIgnoreCase(isSubmit)){
			return new ModelAndView("/wechat/test") ;
		}else{
			System.out.println("...................userTest.....................");
			System.out.println(userTest);
			//this.userTestService.insertUser(userTest);
			long result = this.userTestService.insertUserWidthBack(userTest) ;
			System.out.println("result==========>>>>>>>>>>>>>>" + result );
			
			return new ModelAndView("/wechat/successAdd") ;
		}
		
	}

	public ModelAndView insertUserBatchTest(HttpServletRequest reqeust, HttpServletResponse response){
		System.out.println(".........insertUserBatchTest...........................");
		UserTest userTest1 = new UserTest("张强2222", "111112222") ;
		UserTest userTest2 = new UserTest("李时2222", "222222222") ;
		UserTest userTest3 = new UserTest("申明2222", "333332222") ;
 		List<UserTest> list = new ArrayList<UserTest>() ;
 		
 		list.add(userTest1) ;
 		list.add(userTest2) ;
 		list.add(userTest3) ;
 		
 		this.userTestService.insertUserBatch(list);
 		
 		
		return null ;
	}
	
	//==========================setter.......
	public void setUserTestService(UserTestService userTestService) {
		this.userTestService = userTestService;
	}
	
}
