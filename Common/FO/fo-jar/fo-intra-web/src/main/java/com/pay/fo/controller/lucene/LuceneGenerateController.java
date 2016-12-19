/**
 *  File: LuceneGenerateController.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-8-22   terry     Create
 *
 */
package com.pay.fo.controller.lucene;

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
import com.pay.inf.dto.Bank;
import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.inf.service.BankService;
import com.pay.inf.service.CityService;
import com.pay.inf.service.ProvinceService;
import com.pay.lucene.dto.BankBrancheInfoDto;
import com.pay.lucene.service.BankBrancheInfoService;
import com.pay.util.StringUtil;

/**
 * 
 */
public class LuceneGenerateController extends MultiActionController {

	private Log logger = LogFactory.getLog(LuceneGenerateController.class);
	private static final int PAGE_SIZE = 40;
	private BankBrancheInfoService bankBrancheInfoService;
	private BankService bankService;
	private ProvinceService provinceService;
	private CityService cityService;
	private String toView;
	private String resultView;
	private String cityView;
	private String listView;
	private String toUpdateView;

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map paraMap = new HashMap();
		// 获取银行列表
		List<Bank> bankList = bankService.getWithdrawBanks();
		paraMap.put("bankList", bankList);

		List<ProvinceDTO> provinceList = provinceService.findAll();
		paraMap.put("provinceList", provinceList);

		return new ModelAndView(toView, paraMap);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 * @throws Exception
	 */
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response, BankBrancheInfoDto command)
			throws Exception {

		BankBrancheInfoDto orderInfo = (BankBrancheInfoDto) command;
		String province = orderInfo.getProvince();

		try {
			orderInfo.setAddress(orderInfo.getBankKaihu());
			orderInfo.setProvince(provinceService.findById(
					Integer.parseInt(province)).getProvincename());
			bankBrancheInfoService.addBankBrancheRnTx(orderInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return index(request, response).addObject("errorMsg", "添加失败！")
					.addObject("orderInfo", orderInfo);
		}

		return new ModelAndView(resultView).addObject("errorMsg", "添加成功！");
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 * @throws Exception
	 */
	public ModelAndView findByCondition(HttpServletRequest request,
			HttpServletResponse response, BankBrancheInfoDto command)
			throws Exception {

		BankBrancheInfoDto orderInfo = (BankBrancheInfoDto) command;
		String province = orderInfo.getProvince();
		Page<BankBrancheInfoDto> page = PageUtils.getPage(request);
		page.setPageSize(PAGE_SIZE);
		try {

			if (!StringUtil.isEmpty(province)) {
				orderInfo.setProvince(provinceService.findById(
						Integer.parseInt(province)).getProvincename());
			}

			List<BankBrancheInfoDto> bankBrancheInfos = bankBrancheInfoService
					.findByCondition(page, orderInfo);
			return new ModelAndView(listView).addObject("result",
					bankBrancheInfos).addObject("page", page);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView(listView);
		}
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView del(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");

		try {
			boolean result = bankBrancheInfoService.delBankBrancheRnTx(Long
					.valueOf(id));
			if (result) {
				return new ModelAndView(resultView).addObject("errorMsg",
						"删除成功！");
			} else {
				return new ModelAndView(resultView).addObject("errorMsg",
						"删除失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return index(request, response).addObject("errorMsg", "删除失败！");
		}
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toUpdate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		Map paraMap = new HashMap();
		// 获取银行列表
		List<Bank> bankList = bankService.getWithdrawBanks();
		paraMap.put("bankList", bankList);

		List<ProvinceDTO> provinceList = provinceService.findAll();
		paraMap.put("provinceList", provinceList);

		BankBrancheInfoDto result = bankBrancheInfoService.findById(Long
				.valueOf(id));

		paraMap.put("result", result);
		return new ModelAndView(toUpdateView, paraMap);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 * @throws Exception
	 */
	public ModelAndView update(HttpServletRequest request,
			HttpServletResponse response, BankBrancheInfoDto command)
			throws Exception {

		BankBrancheInfoDto orderInfo = (BankBrancheInfoDto) command;
		try {
			String province = orderInfo.getProvince();
			orderInfo.setAddress(orderInfo.getBankKaihu());
			orderInfo.setProvince(provinceService.findById(
					Integer.parseInt(province)).getProvincename());
			boolean result = bankBrancheInfoService
					.updateBankBrancheRnTx(orderInfo);
			if (result) {
				return new ModelAndView(resultView).addObject("errorMsg",
						"更新成功！");
			} else {
				return new ModelAndView(resultView).addObject("errorMsg",
						"更新失败！").addObject("orderInfo", orderInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return index(request, response).addObject("errorMsg", "更新失败！")
					.addObject("orderInfo", orderInfo);
		}

	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView getCity(HttpServletRequest request,
			HttpServletResponse response) {

		String provinceId = request.getParameter("provinceId");
		List<CityDTO> cityDtos = cityService.findByProvinceId(Integer
				.parseInt(provinceId));
		return new ModelAndView(cityView).addObject("citys", cityDtos);
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	public void setListView(String listView) {
		this.listView = listView;
	}

	public void setToView(String toView) {
		this.toView = toView;
	}

	public void setResultView(String resultView) {
		this.resultView = resultView;
	}

	public void setToUpdateView(String toUpdateView) {
		this.toUpdateView = toUpdateView;
	}

	public void setBankBrancheInfoService(
			BankBrancheInfoService bankBrancheInfoService) {
		this.bankBrancheInfoService = bankBrancheInfoService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public void setCityView(String cityView) {
		this.cityView = cityView;
	}
}
