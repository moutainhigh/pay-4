package com.pay.poss.controller.fi.currency;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.pe.dto.CurrencyDTO;
import com.pay.pe.service.currency.CurrencyService;
import com.pay.util.JSonUtil;

/**
 * 清算基础汇率
 * 
 * @Description
 * @file SettlementBaseRateController
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2012 hnapayay Corporation. All rights reserved.
 *          Date
 */
public class CurrencyController extends MultiActionController {
	private final Log logger = LogFactory.getLog(getClass());
	private String queryInit;
	private String recordList;
	private CurrencyService currencyService;

	public void setQueryInit(String queryInit) {
		this.queryInit = queryInit;
	}
	public void setRecordList(String recordList) {
		this.recordList = recordList;
	}

	public CurrencyService getCurrencyService() {
		return currencyService;
	}

	public void setCurrencyService(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	/**
	 * 默认查询页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(queryInit);
	}

	/**
	 * 添加
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
         
		String currencyName = request.getParameter("currencyName");
		String currencyCode = request.getParameter("currencyCode");
		String currencyNum = request.getParameter("currencyNum");
		String flag = request.getParameter("flag");
		
		logger.info("currencyName: "+currencyName+
				    "currencyCode: "+currencyCode+
				    "currencyNum: "+currencyNum+
				    "flag: "+flag);

		CurrencyDTO currencyDTO = new CurrencyDTO();
		
		if(currencyCode!=null&&currencyCode.trim().length()>0){
			currencyDTO.setCurrencyCode(currencyCode);
		}
		if(currencyName!=null&&currencyName.trim().length()>0){
			currencyDTO.setCurrencyName(currencyName);
		}
		if(currencyNum!=null&&currencyNum.trim().length()>0){
			currencyDTO.setCurrencyNum(currencyNum);
		}
		if(flag!=null&&flag.trim().length()>0){
			currencyDTO.setFlag(flag);
		}
		
		currencyDTO.setCreateDate(new Date());
		
		currencyDTO.setStatus("1");
		
		Long result = currencyService.create(currencyDTO);
		
		if(result!=null)
			 response.getWriter().print(1);
		else 
			 response.getWriter().print(0);
		
		return null;

	}
	
	/**
	 * 添加
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sequenceId = request.getParameter("id");
		boolean result = currencyService.deleteByPrimaryKey(Long.valueOf(sequenceId));
		if(result)
			response.getWriter().print(1);
		else
			response.getWriter().print(0);
		
		return null;
	}

	/**
	 * 查询网站信息列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView query(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Page page = PageUtils.getPage(request);
		String currencyName = request.getParameter("currencyName");
		String currencyCode = request.getParameter("currencyCode");
		String currencyNum = request.getParameter("currencyNum");
		String flag = request.getParameter("flag");

		CurrencyDTO currencyDTO = new CurrencyDTO();
		
		if(currencyCode!=null&&currencyCode.trim().length()>0){
			currencyDTO.setCurrencyCode(currencyCode);
		}
		if(currencyName!=null&&currencyName.trim().length()>0){
			currencyDTO.setCurrencyName(currencyName);
		}
		if(currencyNum!=null&&currencyNum.trim().length()>0){
			currencyDTO.setCurrencyNum(currencyNum);
		}
		if(flag!=null&&flag.trim().length()>0){
			currencyDTO.setFlag(flag);
		}
		
		List<CurrencyDTO> returnList = currencyService.findByCriteria(currencyDTO, page);
		
		return new ModelAndView(recordList).addObject("currencyList", returnList).addObject("page",page);

	}
}
