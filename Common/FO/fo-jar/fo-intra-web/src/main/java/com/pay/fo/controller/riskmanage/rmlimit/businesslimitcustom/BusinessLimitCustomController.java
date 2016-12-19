package com.pay.fo.controller.riskmanage.rmlimit.businesslimitcustom;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.fo.controller.riskmanage.rmlimit.RiskConctrolBaseController;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.rm.service.common.RmLimitConstants;
import com.pay.rm.service.dto.rmlimit.businesslimitcustom.RcBusinessLimitCustomDTO;
import com.pay.rm.service.model.rmlimit.businesslimitcustom.RcBusinessLimitCustom;
import com.pay.rm.service.service.rmlimit.businesslimitcustom.RmBusinessLimitCustomService;
import com.pay.util.SpringControllerUtils;

/**
 * 商户定制限额
 * @Description 
 * @project 	rm-web
 * @file 		BusinessLimitCustomController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2010-10-21		Volcano.Wu			Create
 */
public class BusinessLimitCustomController extends RiskConctrolBaseController {
	
	private RmBusinessLimitCustomService rmBusinessLimitCustomService;
	private MemberService memberService;

	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return new ModelAndView(URL("businessLimitCustomInit"), this.loadDropDownList("BR"));
	}
	
	public ModelAndView readonly(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return new ModelAndView(URL("businessLimitCustomInitReadonly"), this.loadDropDownList("BR"));
	}

	public ModelAndView businessLimitCustomForm(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return new ModelAndView(URL("businessLimitCustomCreate"), this.loadDropDownList("BR"));
	}

	public ModelAndView businessLimitCustomCreate(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		RcBusinessLimitCustomDTO rcBusinessLimitCustomDTO = new RcBusinessLimitCustomDTO();
		bind(request, rcBusinessLimitCustomDTO, "RcBusinessLimitCustomDTO",null);
		
		//判断会员存在
		MemberDto memberDto = memberService.queryMemberByMemberCode(rcBusinessLimitCustomDTO.getBusinessId());
		if(null == memberDto){
			return new ModelAndView(URL("businessLimitCustomCreate"), this.loadDropDownList("BR")).addObject("msg", "该商户无效.请在编辑中修改！");
		}
		if(MemberTypeEnum.INDIVIDUL.getCode()==memberDto.getType()) {
			return new ModelAndView(URL("businessLimitCustomCreate"), this.loadDropDownList("BR")).addObject("msg", "只能添加企业商户！");
		}
		rcBusinessLimitCustomDTO.setSingleLimit(rcBusinessLimitCustomDTO.getSingleLimit()*RmLimitConstants.RMYUAN1000);
		rcBusinessLimitCustomDTO.setDayLimit(rcBusinessLimitCustomDTO.getDayLimit()*RmLimitConstants.RMYUAN1000);
		rcBusinessLimitCustomDTO.setMonthLimit(rcBusinessLimitCustomDTO.getMonthLimit()*RmLimitConstants.RMYUAN1000);
		
		
		RcBusinessLimitCustom rcBusinessLimitCustom = new RcBusinessLimitCustom();
		BeanUtils.copyProperties(rcBusinessLimitCustomDTO, rcBusinessLimitCustom);
		
		Integer count = rmBusinessLimitCustomService.serachById(rcBusinessLimitCustom);
		
		if(count > 0) {
			return new ModelAndView(URL("businessLimitCustomCreate"), this.loadDropDownList("BR")).addObject("msg", "该会员已经存在相应的配置,有可能被该配置设置了无效.请在编辑中修改！");
		}
		
		rmBusinessLimitCustomService.save(rcBusinessLimitCustomDTO);
		return new ModelAndView(URL("businessLimitCustomCreate"), this.loadDropDownList("BR")).addObject("msg", "新增成功！");
	}

	/**
	 * 商户定制限额查询
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView searchBusinessLimitCustoms(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("businessId", request.getParameter("businessId"));
		map.put("bMemberName", request.getParameter("bMemberName"));
		map.put("businessType", request.getParameter("businessType"));
		map.put("status", request.getParameter("status"));
		
		Page<RcBusinessLimitCustomDTO> page = PageUtils.getPage(request);
		page = rmBusinessLimitCustomService.search(page, map);
		Map<String, Object> model = this.loadDropDownList("BRCS");
		model.put("page", page);
		return new ModelAndView(URL("businessLimitCustomList"), model);
	}
	
	public ModelAndView searchBusinessLimitCustomsReadonly(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("businessId", request.getParameter("businessId"));
		map.put("bMemberName", request.getParameter("bMemberName"));
		map.put("businessType", request.getParameter("businessType"));
		map.put("status", request.getParameter("status"));
		
		Page<RcBusinessLimitCustomDTO> page = PageUtils.getPage(request);
		page = rmBusinessLimitCustomService.search(page, map);
		Map<String, Object> model = this.loadDropDownList("BRCS");
		model.put("page", page);
		return new ModelAndView(URL("businessLimitCustomListReadonly"), model);
	}

	public ModelAndView businessLimitCustomDelete(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		StringBuffer results = new StringBuffer();
		try{
			if(StringUtils.isNotEmpty(id)){
				rmBusinessLimitCustomService.delete(Long.valueOf(id));
				results.append("success");
			}
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}finally{
			SpringControllerUtils.renderText(response, results.toString());
		}
		return null;
	}

	/**
	 * 编辑商户定制限额
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView businessLimitCustomUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception {
		RcBusinessLimitCustomDTO rcBusinessLimitCustomDTO = new RcBusinessLimitCustomDTO();
		bind(request, rcBusinessLimitCustomDTO, "RcBusinessLimitCustomDTO", null);
		rcBusinessLimitCustomDTO.setSingleLimit(rcBusinessLimitCustomDTO.getSingleLimit()*RmLimitConstants.RMYUAN1000);
		rcBusinessLimitCustomDTO.setDayLimit(rcBusinessLimitCustomDTO.getDayLimit()*RmLimitConstants.RMYUAN1000);
		rcBusinessLimitCustomDTO.setMonthLimit(rcBusinessLimitCustomDTO.getMonthLimit()*RmLimitConstants.RMYUAN1000);
		StringBuffer results = new StringBuffer();
		if(rmBusinessLimitCustomService.update(rcBusinessLimitCustomDTO))
			results.append("success");
		
		SpringControllerUtils.renderText(response, results.toString());
		return null;
	}

	public ModelAndView businessLimitCustomUpdateForm(HttpServletRequest request, HttpServletResponse response)throws Exception {
		long id = Long.parseLong(request.getParameter("id"));
		RcBusinessLimitCustom pojo = rmBusinessLimitCustomService.get(id);
		
		pojo.setSingleLimit(pojo.getSingleLimit()/RmLimitConstants.RMYUAN1000);
		pojo.setDayLimit(pojo.getDayLimit()/RmLimitConstants.RMYUAN1000);
		pojo.setMonthLimit(pojo.getMonthLimit()/RmLimitConstants.RMYUAN1000);
		Map<String, Object> model = this.loadDropDownList("BR");
		model.put("businesslimitcustom", pojo);
		return new ModelAndView(URL("businessLimitCustomUpdate"), model);
	}

	public void setRmBusinessLimitCustomService(RmBusinessLimitCustomService rmBusinessLimitCustomService) {
		this.rmBusinessLimitCustomService = rmBusinessLimitCustomService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

}
