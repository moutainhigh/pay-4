package com.pay.poss.report.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.report.dto.InnerMemberDTO;
import com.pay.poss.report.dto.SubMemberDTO;
import com.pay.poss.report.service.InnerMemberService;
import com.pay.poss.report.service.SubMemberService;
import com.pay.poss.security.util.SessionUserHolderUtil;

/**
 * 商户维护
 * 
 * @Description
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved.
 *          Date
 */
public class SubMemberController extends MultiActionController {

	private String viewList;

	private String toView;

	private String addView;
	
	private String toRemove;
	
	private String toModify;

	private SubMemberService subMemberService;
	
	private InnerMemberService innerMemberService;

	public void setToView(String toView) {
		this.toView = toView;
	}

	public void setViewList(String viewList) {
		this.viewList = viewList;
	}

	public void setAddView(String addView) {
		this.addView = addView;
	}

	public void setToRemove(String toRemove) {
		this.toRemove = toRemove;
	}
	
	public void setToModify(String toModify) {
		this.toModify = toModify;
	}
	
	public void setSubMemberService(SubMemberService subMemberService) {
		this.subMemberService = subMemberService;
	}

	public void setInnerMemberService(InnerMemberService innerMemberService) {
		this.innerMemberService = innerMemberService;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		InnerMemberDTO innerMemberDTO=innerMemberService.findById(Long.parseLong(request.getParameter("id")));
		model.put("memberToView",innerMemberDTO);
		return new ModelAndView(toView, model);
	}

	/**
	 * 查询商户列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView query(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Long parentId = Long.parseLong(request.getParameter("parentId"));
		map.put("parentId", parentId);
		Page<SubMemberDTO> page = PageUtils.getPage(request);
		page = subMemberService.querySubMember(page, map);
		request.setAttribute("parentId", parentId);
		return new ModelAndView(viewList).addObject("page", page).addObject("optType", 1);
	}

	/**
	 * 准备添加商户
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView addInit(HttpServletRequest req, HttpServletResponse res)
			throws ServletRequestBindingException, IOException {
		Map<String, Object> model = new HashMap<String, Object>();
		InnerMemberDTO innerMemberDTO=innerMemberService.findById(Long.parseLong(req.getParameter("id")));
		model.put("innerMemberAddTo",innerMemberDTO);
		return new ModelAndView(addView, model);
	}

	/**
	 * 添加商户
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletRequestBindingException, IOException {
		Map<String, Object> model = new HashMap<String, Object>();
		SubMemberDTO subMemberDTO= new SubMemberDTO();
		subMemberDTO.setMemberCode(request.getParameter("subMemberCode"));
		subMemberDTO.setMemberName(request.getParameter("subMemberName"));
		subMemberDTO.setParentId(Long.parseLong(request.getParameter("innerMemberCode")));
		subMemberDTO.setOperator(SessionUserHolderUtil.getLoginId());
		subMemberDTO.setCreateTime(new Date());
		InnerMemberDTO innerMemberDTO=innerMemberService.findById(Long.parseLong(request.getParameter("id")));

		SubMemberDTO subMemberDTO2=subMemberService.findByMemberCode(Long.parseLong(request.getParameter("subMemberCode")));
		if(subMemberDTO2 != null){
			model.put("innerMemberAddTo",innerMemberDTO);
			model.put("dto",subMemberDTO);
			model.put("message","无法保存，该商户会员号已存在！");
			return new ModelAndView(addView, model);
		}

		InnerMemberDTO innerMemberDTO2=innerMemberService.findByParentId(Long.parseLong(request.getParameter("subMemberCode")));
		if(innerMemberDTO2 != null){
			model.put("innerMemberAddTo",innerMemberDTO);
			model.put("dto",subMemberDTO);
			model.put("message","无法保存，商户会员号不能与分子公司会员号重复！");
			return new ModelAndView(addView, model);
		}
		
		subMemberService.createSubMember(subMemberDTO);
		model.put("memberToView",innerMemberDTO);
		model.put("message", "添加成功");
		return new ModelAndView(toView, model);
	}

	/**
	 * 准备删除商户
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toDelete(HttpServletRequest req,
			HttpServletResponse response)
			throws ServletRequestBindingException, IOException {
		Map<String, Object> model = new HashMap<String, Object>();
		SubMemberDTO subMemberDTO=subMemberService.findById(Long.parseLong(req.getParameter("id")));
		InnerMemberDTO innerMemberDTO=innerMemberService.findByParentId(subMemberDTO.getParentId());
		model.put("memberToDelete",subMemberDTO);
		model.put("innerMember", innerMemberDTO);
		return new ModelAndView(toRemove, model);
	}
	
	/**
	 * 删除商户
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ModelAndView delete(HttpServletRequest req,
			HttpServletResponse response)
			throws ServletRequestBindingException, IOException {
		String id = req.getParameter("id");
		subMemberService.deleteById(Long.parseLong(id));
		Map<String, Object> model = new HashMap<String, Object>();
		InnerMemberDTO innerMemberDTO=innerMemberService.findById(Long.parseLong(req.getParameter("parentSequenceId")));
		model.put("memberToView",innerMemberDTO);
		model.put("message", "删除成功");
		return new ModelAndView(toView, model);
	}

	/**
	 * 准备更新商户
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toUpdate(HttpServletRequest req,
			HttpServletResponse response)
			throws ServletRequestBindingException, IOException {
		Map<String, Object> model = new HashMap<String, Object>();
		SubMemberDTO subMemberDTO=subMemberService.findById(Long.parseLong(req.getParameter("id")));
		InnerMemberDTO innerMemberDTO=innerMemberService.findByParentId(subMemberDTO.getParentId());
		model.put("memberToUpdate",subMemberDTO);
		model.put("innerMember", innerMemberDTO);
		return new ModelAndView(toModify, model);
	}
	
	/**
	 * 更新商户
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ModelAndView update(HttpServletRequest req, HttpServletResponse res)
			throws ServletRequestBindingException,
			IOException {
		
		Map<String, Object> model = new HashMap<String, Object>();
		String memberCode = req.getParameter("subMemberCode");
		boolean flg = false;
		SubMemberDTO subMemberDTO= subMemberService.findById(Long.parseLong(req.getParameter("id")));
		if(!subMemberDTO.getMemberCode().equals(memberCode)){
			flg=true;
		}
		subMemberDTO.setMemberCode(memberCode);
		subMemberDTO.setMemberName(req.getParameter("subMemberName"));
		subMemberDTO.setOperator(SessionUserHolderUtil.getLoginId());
		subMemberDTO.setUpdateTime(new Date());
		InnerMemberDTO innerMemberDTO=innerMemberService.findByParentId(subMemberDTO.getParentId());

		if(flg){
			SubMemberDTO subMemberDTO2=subMemberService.findByMemberCode(Long.parseLong(memberCode));
			if(subMemberDTO2 != null){
				model.put("memberToUpdate",subMemberDTO);
				model.put("innerMember", innerMemberDTO);
				model.put("message","无法保存，该会员号已存在！");
				return new ModelAndView(toModify, model);
			}

			InnerMemberDTO innerMemberDTO2=innerMemberService.findByParentId(Long.parseLong(memberCode));
			if(innerMemberDTO2 != null){
				model.put("memberToUpdate",subMemberDTO);
				model.put("innerMember", innerMemberDTO);
				model.put("message","无法保存，商户会员号不能与分子公司会员号重复！");
				return new ModelAndView(toModify, model);
			}
		}
		
		subMemberService.updateSubMember(subMemberDTO);
		model.put("memberToView",innerMemberDTO);
		model.put("message", "修改成功");
		return new ModelAndView(toView, model);
	}
}
