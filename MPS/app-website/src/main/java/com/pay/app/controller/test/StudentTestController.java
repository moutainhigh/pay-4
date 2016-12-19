/**
 * 
 */
package com.pay.app.controller.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.batchpay.dto.StudentTest;
import com.pay.batchpay.dto.WithdrawWorkOrder;
import com.pay.batchpay.service.bulkpayment.WithdrawWorkOrderService;
import com.pay.batchpay.service.studenttest.StudentTestService;

/**
 * @author PengJiangbo
 *
 */
public class StudentTestController extends MultiActionController {

	private StudentTestService studentTestService ;
	
	private WithdrawWorkOrderService withdrawWorkOrderService ;
	
	public ModelAndView studentTestAdd(HttpServletRequest request, HttpServletResponse response){
		StudentTest studentTest = new StudentTest() ;
		studentTest.setUsername("Hello 彭…………");
		studentTest.setPassword("Hi123456");
		this.studentTestService.insertUser(studentTest);
		return null ;
	}

	public ModelAndView withdrawWorkOrderAdd(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> hMap = new HashMap<String, Object>();
		List<WithdrawWorkOrder> aList = new ArrayList<WithdrawWorkOrder>() ;
		
		WithdrawWorkOrder wwo = null ;
		for(int i=0; i<3; i++){
			aList.add(new WithdrawWorkOrder(new Random().nextLong(), new Random().nextLong(), new Date())) ;
		}
//		hMap.put("workorderKy", new Random().nextLong()) ;
//		hMap.put("orderSeq", new Random().nextInt(1234532890)) ;
//		hMap.put("createTime", new Date()) ;
		hMap.put("aList", aList) ;
		this.withdrawWorkOrderService.insertWithdrawWorkOrder(hMap);
		return null ;
	}

	public void setStudentTestService(StudentTestService studentTestService) {
		this.studentTestService = studentTestService;
	}

	public void setWithdrawWorkOrderService(
			WithdrawWorkOrderService withdrawWorkOrderService) {
		this.withdrawWorkOrderService = withdrawWorkOrderService;
	}
	
}
