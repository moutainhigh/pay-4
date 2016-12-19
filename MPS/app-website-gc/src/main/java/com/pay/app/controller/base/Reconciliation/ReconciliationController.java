package com.pay.app.controller.base.Reconciliation;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class ReconciliationController extends MultiActionController{
	
	private String reconciliView;
	
	public ModelAndView index(HttpServletRequest req,
			HttpServletResponse resp) throws Exception{
		java.text.SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		java.text.SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
		String nowMonth=sdf.format(new Date());
		String nowDate=sdf1.format(new Date());
		return new ModelAndView(reconciliView).addObject("nowMonth",nowMonth).addObject("nowDate",nowDate);
	}

	public void setReconciliView(String reconciliView) {
		this.reconciliView = reconciliView;
	}
	
	
	
}
