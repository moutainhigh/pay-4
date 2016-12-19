package com.pay.app.controller.base.province;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.facade.bankacct.BankAcctServiceFacade;
import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;

public class ProvinceController extends MultiActionController {

	private BankAcctServiceFacade bankAcctService;
	private String provinceView;
	private String cityView;
	

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		List<ProvinceDTO> provinceList=bankAcctService.getAllProvince();
		int defaultProvince = 0;
		if(StringUtils.isNotBlank(request.getParameter("defaultProvince"))){
		    try{
		        defaultProvince = Integer.parseInt(request.getParameter("defaultProvince"));
		    }catch(NumberFormatException ex){
		        logger.warn("Get defaultProvince For Integer Error.",ex);
		    }
		}
		return new ModelAndView(provinceView)
				.addObject("defaultProvince",defaultProvince)
				.addObject("provinceList",provinceList);
	}
	
	public ModelAndView city(HttpServletRequest request,
			HttpServletResponse response) {
		int provinceId=StringUtils.isEmpty(request.getParameter("provinceId"))
			?0:Integer.parseInt(request.getParameter("provinceId"));
			
		List<CityDTO> cityList=bankAcctService.getAllCity(provinceId);
		int defaultCity=StringUtils.isEmpty(request.getParameter("defaultCity"))
				?0:Integer.parseInt(request.getParameter("defaultCity"));
		return new ModelAndView(cityView)
				.addObject("cityList",cityList)
				.addObject("defaultCity",defaultCity);
	}
	
	public void setBankAcctService(BankAcctServiceFacade bankAcctService) {
		this.bankAcctService = bankAcctService;
	}

	public void setProvinceView(String provinceView) {
		this.provinceView = provinceView;
	}

	public void setCityView(String cityView) {
		this.cityView = cityView;
	}

}
