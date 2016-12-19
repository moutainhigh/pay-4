package com.pay.app.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class ErrorController extends MultiActionController{
	private String view_error;
	private String view_404;
	private String view_500;
	private String view_403;
	private String view_time_out;
	private String view_no_feature;	
	private String view_individual;
	private String view_no_cert;
	private String view_illegal;


    public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String errorCode=request.getParameter("errorCode")==null?"500":request.getParameter("errorCode");
		String methodParams="error500";
		if("timeout".equals(errorCode)){
			methodParams=errorCode;
		}else if(!"".equals(errorCode)){
			methodParams="error"+errorCode;
		}
		return new ModelAndView(view_error).addObject("methodParams",methodParams);
	}
	
	

	public ModelAndView error404(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(view_404);
	}
	
	public ModelAndView error403(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(view_403);
	}
	
	public ModelAndView error500(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(view_500);
	}

	public ModelAndView timeout(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(view_time_out);
	}
	
	public ModelAndView noFeature(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return new ModelAndView(view_no_feature);
    }
	
	public ModelAndView noCert(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return new ModelAndView(view_no_cert);
    }
	
	public ModelAndView illegal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return new ModelAndView(view_illegal);
    }
	
	public ModelAndView individual(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return new ModelAndView(view_individual);
    }
	
	public void setView_404(String view_404) {
		this.view_404 = view_404;
	}

	public void setView_500(String view_500) {
		this.view_500 = view_500;
	}

	public void setView_403(String view_403) {
		this.view_403 = view_403;
	}
	
	public void setView_time_out(String viewTimeOut) {
		view_time_out = viewTimeOut;
	}
	public void setView_error(String viewError) {
		view_error = viewError;
	}
	public void setView_no_feature(String viewNoFeature) {
	        view_no_feature = viewNoFeature;
    }

	public void setView_individual(String viewIndividual) {
		view_individual = viewIndividual;
	}

	public void setView_no_cert(String viewNoCert) {
		view_no_cert = viewNoCert;
	}

	public void setView_illegal(String view_illegal) {
		this.view_illegal = view_illegal;
	}
	
}
