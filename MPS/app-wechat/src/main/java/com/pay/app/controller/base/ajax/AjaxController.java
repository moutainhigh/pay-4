package com.pay.app.controller.base.ajax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;


public class AjaxController extends MultiActionController {
	private String toView;
	private String loadAjaxView;
	private String dialogView;
	

	
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(toView);
	}
	
	public ModelAndView dialogView(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(dialogView);
	}
	
	public ModelAndView loadIndex(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		int pn=1;
		final int n=10;
		pn=request.getParameter("pn")==null?1:Integer.parseInt(request.getParameter("pn"));
		String searchParam=request.getParameter("searchParam")==null?"":request.getParameter("searchParam");
		List<Integer> listStr=new ArrayList<Integer>(10);
		if(!"".equals(searchParam)){
			try {
				listStr.add(Integer.parseInt(searchParam));
			} catch (Exception e) {
				for(int i=1;i<n+1;i++){
					listStr.add((pn-1)*n+i);
				}
			}
		}else{
			for(int i=1;i<n+1;i++){
				listStr.add((pn-1)*n+i);
			}
		}
		paraMap.put("listStr", listStr);
		paraMap.put("pn", pn);
		paraMap.put("searchParam", searchParam);
		return new ModelAndView(loadAjaxView, paraMap);
	
		//return index(request,response);
	}
	
	public void setDialogView(String dialogView) {
		this.dialogView = dialogView;
	}

	public String getToView() {
		return toView;
	}

	public void setToView(String toView) {
		this.toView = toView;
	}

	public String getLoadAjaxView() {
		return loadAjaxView;
	}

	public void setLoadAjaxView(String loadAjaxView) {
		this.loadAjaxView = loadAjaxView;
	}
	
	public String dialogView() {
		return dialogView;
	}

}
