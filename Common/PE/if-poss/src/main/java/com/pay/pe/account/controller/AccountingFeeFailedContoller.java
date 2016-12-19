/**
 * 
 */
package com.pay.pe.account.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.pe.account.dto.AccountingFeeFailedDto;
import com.pay.pe.account.dto.AccountingFeeFailedParamDto;
import com.pay.pe.account.service.AccountingFeeFailedService;
/**
 * 
 * @author 戴德荣
 *
 */
public class AccountingFeeFailedContoller extends MultiActionController {
	
	
	private String indexView;
	private String listView;
	
	private AccountingFeeFailedService AccountingFeeFailedService;

	/**
	 * @param indexView the indexView to set
	 */
	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	/**
	 * @param listView the listView to set
	 */
	public void setListView(String listView) {
		this.listView = listView;
	}


	
	
	
	
	public void setAccountingFeeFailedService(
			AccountingFeeFailedService AccountingFeeFailedService) {
		this.AccountingFeeFailedService = AccountingFeeFailedService;
	}

	/**
	 * 到导航页
	 * @param request
	 * @param response
	 * @param bindParamDto
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request,
            HttpServletResponse response){
		return new ModelAndView(indexView);
	}
	
	
	/**
	 * 到查询页
	 * @param request
	 * @param response
	 * @param bindParamDto
	 * @return
	 */
	public ModelAndView search(HttpServletRequest request,
            HttpServletResponse response,AccountingFeeFailedParamDto dto){
		Page pageParam  = PageUtils.getPage(request);
		Page<AccountingFeeFailedDto> page = AccountingFeeFailedService.searchPage(dto,pageParam);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		return new ModelAndView(listView,map);
	}
	
	/**
	 * 修改
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws IOException
	 */
	public ModelAndView chargeback(HttpServletRequest request,
            HttpServletResponse response) throws IOException{
		Long voucherCode = ServletRequestUtils.getLongParameter(request, "voucherCode",-1);
		int dealType = ServletRequestUtils.getIntParameter(request, "dealType",-1);
		
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		if(voucherCode==-1L || dealType==-1){
			response.getWriter().write("参数不正确");
			return null;
		}
		boolean isOk = false;
		String error = "";
		try{
			 isOk =  AccountingFeeFailedService.chargeback(voucherCode, dealType);
			 if(! isOk){
				 error  = "扣费失败！";
			 }
		}catch (Exception e) {
			error = "后台扣费时异常,"+e.getMessage();
			logger.error(e);
			e.printStackTrace();
		}
		
		response.getWriter().write(isOk?"S":error);
		return null;
	}
	
	
	
	
	

}
