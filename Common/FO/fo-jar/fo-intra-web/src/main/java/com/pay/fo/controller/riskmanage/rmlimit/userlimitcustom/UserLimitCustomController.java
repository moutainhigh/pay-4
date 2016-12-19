package com.pay.fo.controller.riskmanage.rmlimit.userlimitcustom;

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
import com.pay.rm.service.dto.rmlimit.userlimitcustom.RcUserLimitCustomDTO;
import com.pay.rm.service.model.rmlimit.userlimitcustom.RcUserLimitCustom;
import com.pay.rm.service.service.rmlimit.userlimitcustom.RmUserLimitCustomService;
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
public class UserLimitCustomController extends RiskConctrolBaseController {
	
	private RmUserLimitCustomService rmUserLimitCustomService;
	private MemberService memberService;

	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return new ModelAndView(URL("userLimitCustomInit"), this.loadDropDownList("BR"));
	}
	
	public ModelAndView readonly(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return new ModelAndView(URL("userLimitCustomInitReadonly"), this.loadDropDownList("BR"));
	}

	public ModelAndView businessLimitCustomForm(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return new ModelAndView(URL("userLimitCustomCreate"), this.loadDropDownList("BR"));
	}

	public ModelAndView businessLimitCustomCreate(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		RcUserLimitCustomDTO rcBusinessLimitCustomDTO = new RcUserLimitCustomDTO();
		bind(request, rcBusinessLimitCustomDTO, "RcBusinessLimitCustomDTO",null);
		
		//判断会员存在
		MemberDto memberDto = memberService.queryMemberByMemberCode(rcBusinessLimitCustomDTO.getMemberCode());
		if(null == memberDto){
			return new ModelAndView(URL("userLimitCustomCreate"), this.loadDropDownList("BR")).addObject("msg", "该会员无效.请在编辑中修改！");
		}
		if(MemberTypeEnum.MERCHANT.getCode()==memberDto.getType()) {
			return new ModelAndView(URL("userLimitCustomCreate"), this.loadDropDownList("BR")).addObject("msg", "只能添加个人用户！");
		}
		
		rcBusinessLimitCustomDTO.setSingleLimit(rcBusinessLimitCustomDTO.getSingleLimit()*RmLimitConstants.RMYUAN1000);
		rcBusinessLimitCustomDTO.setDayLimit(rcBusinessLimitCustomDTO.getDayLimit()*RmLimitConstants.RMYUAN1000);
		rcBusinessLimitCustomDTO.setMonthLimit(rcBusinessLimitCustomDTO.getMonthLimit()*RmLimitConstants.RMYUAN1000);
		
		
		RcUserLimitCustom rcBusinessLimitCustom = new RcUserLimitCustom();
		BeanUtils.copyProperties(rcBusinessLimitCustomDTO, rcBusinessLimitCustom);
		
		Integer count = rmUserLimitCustomService.serachById(rcBusinessLimitCustom);
		
		if(count > 0) {
			return new ModelAndView(URL("userLimitCustomCreate"), this.loadDropDownList("BR")).addObject("msg", "该会员已经存在相应的配置,有可能被该配置设置了无效.请在编辑中修改！");
		}
		
		rmUserLimitCustomService.save(rcBusinessLimitCustomDTO);
		return new ModelAndView(URL("userLimitCustomCreate"), this.loadDropDownList("BR")).addObject("msg", "新增成功！");
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
		map.put("memberCode", request.getParameter("memberCode"));
		map.put("memberName", request.getParameter("memberName"));
		map.put("businessType", request.getParameter("businessType"));
		map.put("status", request.getParameter("status"));
		
		Page<RcUserLimitCustomDTO> page = PageUtils.getPage(request);
		page = rmUserLimitCustomService.search(page, map);
		Map<String, Object> model = this.loadDropDownList("BRCS");
		model.put("page", page);
		return new ModelAndView(URL("userLimitCustomList"), model);
	}
	
	public ModelAndView searchBusinessLimitCustomsReadonly(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberCode", request.getParameter("memberCode"));
		map.put("memberName", request.getParameter("memberName"));
		map.put("businessType", request.getParameter("businessType"));
		map.put("status", request.getParameter("status"));
		
		Page<RcUserLimitCustomDTO> page = PageUtils.getPage(request);
		page = rmUserLimitCustomService.search(page, map);
		Map<String, Object> model = this.loadDropDownList("BRCS");
		model.put("page", page);
		return new ModelAndView(URL("userLimitCustomListReadonly"), model);
	}

	public ModelAndView businessLimitCustomDelete(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		StringBuffer results = new StringBuffer();
		try{
			if(StringUtils.isNotEmpty(id)){
				rmUserLimitCustomService.delete(Long.valueOf(id));
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
		RcUserLimitCustomDTO rcBusinessLimitCustomDTO = new RcUserLimitCustomDTO();
		bind(request, rcBusinessLimitCustomDTO, "RcBusinessLimitCustomDTO", null);
		rcBusinessLimitCustomDTO.setSingleLimit(rcBusinessLimitCustomDTO.getSingleLimit()*RmLimitConstants.RMYUAN1000);
		rcBusinessLimitCustomDTO.setDayLimit(rcBusinessLimitCustomDTO.getDayLimit()*RmLimitConstants.RMYUAN1000);
		rcBusinessLimitCustomDTO.setMonthLimit(rcBusinessLimitCustomDTO.getMonthLimit()*RmLimitConstants.RMYUAN1000);
		StringBuffer results = new StringBuffer();
		if(rmUserLimitCustomService.update(rcBusinessLimitCustomDTO))
			results.append("success");
		
		SpringControllerUtils.renderText(response, results.toString());
		return null;
	}

	public ModelAndView businessLimitCustomUpdateForm(HttpServletRequest request, HttpServletResponse response)throws Exception {
		long id = Long.parseLong(request.getParameter("id"));
		RcUserLimitCustom pojo = rmUserLimitCustomService.get(id);
		
		pojo.setSingleLimit(pojo.getSingleLimit()/RmLimitConstants.RMYUAN1000);
		pojo.setDayLimit(pojo.getDayLimit()/RmLimitConstants.RMYUAN1000);
		pojo.setMonthLimit(pojo.getMonthLimit()/RmLimitConstants.RMYUAN1000);
		Map<String, Object> model = this.loadDropDownList("BR");
		model.put("businesslimitcustom", pojo);
		return new ModelAndView(URL("userLimitCustomUpdate"), model);
	}

	/**
	 * @param rmUserLimitCustomService the rmUserLimitCustomService to set
	 */
	public void setRmUserLimitCustomService(
			RmUserLimitCustomService rmUserLimitCustomService) {
		this.rmUserLimitCustomService = rmUserLimitCustomService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

}
