package com.pay.fo.controller.riskmanage.rmlimit.innerlimit;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.fo.controller.riskmanage.rmlimit.RiskConctrolBaseController;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.rm.base.exception.PossException;
import com.pay.rm.service.common.RmLimitConstants;
import com.pay.rm.service.dto.rmlimit.innerlimit.RcInnerLimitDTO;
import com.pay.rm.service.model.rmlimit.innerlimit.RcInnerLimit;
import com.pay.rm.service.service.rmlimit.innerlimit.RmInnerLimitService;
import com.pay.util.SpringControllerUtils;

/**
 * 直营限额
 * 
 * @Description
 * @project rm-web
 * @file InnerLimitController.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved.
 *          Date Author Changes 2010-10-21 Volcano.Wu Create
 */
public class InnerLimitController extends RiskConctrolBaseController {

	private RmInnerLimitService rmInnerLimitService;

	public void setRmInnerLimitService(RmInnerLimitService rmInnerLimitService) {
		this.rmInnerLimitService = rmInnerLimitService;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(URL("innerLimitInit"), this
				.loadDropDownList("BU"));
	}

	public ModelAndView readonly(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(URL("innerLimitInitReadonly"), this
				.loadDropDownList("BU"));
	}

	public ModelAndView sysLimitForm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(URL("innerLimitCreate"), this
				.loadDropDownList("BU"));
	}

	public ModelAndView sysLimitCreate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RcInnerLimitDTO rcInnerLimitDTO = new RcInnerLimitDTO();
		bind(request, rcInnerLimitDTO, "RcSysLimitDTO", null);
		rcInnerLimitDTO.setSingleLimit(rcInnerLimitDTO.getSingleLimit()
				* RmLimitConstants.RMYUAN1000);
		rcInnerLimitDTO.setDayLimit(rcInnerLimitDTO.getDayLimit()
				* RmLimitConstants.RMYUAN1000);
		rcInnerLimitDTO.setMonthLimit(rcInnerLimitDTO.getMonthLimit()
				* RmLimitConstants.RMYUAN1000);
		String reMsg = "";
		try {
			rmInnerLimitService.save(rcInnerLimitDTO);
			reMsg = "新增成功！";
		} catch (PossException e) {
			// e.printStackTrace();
			if (e.getCode().getCode().equals("0003"))
				reMsg = "录入失败,已存在同一业务类型限额";
		}
		return new ModelAndView(URL("innerLimitCreate"), this
				.loadDropDownList("BU")).addObject("msg", reMsg);

	}

	public ModelAndView searchSysLimits(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userLevel", request.getParameter("userLevel"));
		map.put("sysBusiness", request.getParameter("sysBusiness"));
		map.put("status", request.getParameter("status"));
		map.put("sequenceId", request.getParameter("sequenceId"));

		Page<RcInnerLimitDTO> page = PageUtils.getPage(request);
		page = rmInnerLimitService.search(page, map);
		Map<String, Object> model = this.loadDropDownList("ASC");
		model.put("page", page);
		return new ModelAndView(URL("innerLimitList"), model);
	}

	public ModelAndView searchSysLimitsReadonly(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userLevel", request.getParameter("userLevel"));
		map.put("sysBusiness", request.getParameter("sysBusiness"));
		map.put("status", request.getParameter("status"));
		map.put("sequenceId", request.getParameter("sequenceId"));

		Page<RcInnerLimitDTO> page = PageUtils.getPage(request);
		page = rmInnerLimitService.search(page, map);
		Map<String, Object> model = this.loadDropDownList("ASC");
		model.put("page", page);
		return new ModelAndView(URL("innerLimitListReadonly"), model);
	}

	public ModelAndView sysLimitDelete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		StringBuffer results = new StringBuffer();
		try {
			if (StringUtils.isNotEmpty(id)) {
				rmInnerLimitService.delete(Long.valueOf(id));
				results.append("success");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			SpringControllerUtils.renderText(response, results.toString());
		}
		return null;
	}

	public ModelAndView sysLimitUpdate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RcInnerLimitDTO rcInnerLimitDTO = new RcInnerLimitDTO();
		bind(request, rcInnerLimitDTO, "RcSysLimitDTO", null);

		rcInnerLimitDTO.setSingleLimit(rcInnerLimitDTO.getSingleLimit()
				* RmLimitConstants.RMYUAN1000);
		rcInnerLimitDTO.setDayLimit(rcInnerLimitDTO.getDayLimit()
				* RmLimitConstants.RMYUAN1000);
		rcInnerLimitDTO.setMonthLimit(rcInnerLimitDTO.getMonthLimit()
				* RmLimitConstants.RMYUAN1000);
		StringBuffer results = new StringBuffer();
		try {
			if (rmInnerLimitService.update(rcInnerLimitDTO))
				results.append("success");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			SpringControllerUtils.renderText(response, results.toString());
		}
		return null;
	}

	public ModelAndView sysLimitUpdateForm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = Long.parseLong(request.getParameter("id"));
		RcInnerLimit pojo = rmInnerLimitService.get(id);
		pojo
				.setSingleLimit(pojo.getSingleLimit()
						/ RmLimitConstants.RMYUAN1000);
		pojo.setDayLimit(pojo.getDayLimit() / RmLimitConstants.RMYUAN1000);
		pojo.setMonthLimit(pojo.getMonthLimit() / RmLimitConstants.RMYUAN1000);
		Map<String, Object> model = this.loadDropDownList("BU");
		model.put("syslimit", pojo);
		return new ModelAndView(URL("innerLimitUpdate"), model);
	}

}
