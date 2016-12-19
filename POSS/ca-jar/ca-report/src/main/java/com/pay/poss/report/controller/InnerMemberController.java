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
import com.pay.util.StringUtil;

/**
 * 分子公司维护
 * 
 * @Description
 * @file MemberFlowController
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 */
public class InnerMemberController extends MultiActionController {

	private String viewList;

	private String toView;

	private String addView;
	
	private String toRemove;
	
	private String toModify;

	private InnerMemberService innerMemberService;
	
	private SubMemberService subMemberService;

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
	
	public void setInnerMemberService(InnerMemberService innerMemberService) {
		this.innerMemberService = innerMemberService;
	}

	public void setSubMemberService(SubMemberService subMemberService) {
		this.subMemberService = subMemberService;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(toView);
	}

	/**
	 * 查询分子公司列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView query(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String memberCode = StringUtil.null2String(request.getParameter("memberCode"));
		String name = StringUtil.null2String(request.getParameter("memberName"));
		
		if (!StringUtil.isEmpty(memberCode)) {
			map.put("memberCode", memberCode.trim());
		}
		if (!StringUtil.isEmpty(name)) {
			map.put("memberName", name.trim());
		}
		Page<InnerMemberDTO> page = PageUtils.getPage(request);
		page = innerMemberService.queryInnerMember(page, map);
		return new ModelAndView(viewList).addObject("page", page)
			.addObject("optType", 1);
	}

	/**
	 * 准备添加分子公司
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ModelAndView addInit(HttpServletRequest req, HttpServletResponse res)
			throws ServletRequestBindingException, IOException {
		return new ModelAndView(addView);
	}

	/**
	 * 添加分子公司
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
		String memberCode =request.getParameter("memberCode");
		InnerMemberDTO innerMemberDTO= new InnerMemberDTO();
		innerMemberDTO.setMemberCode(memberCode);
		innerMemberDTO.setMemberName(request.getParameter("memberName"));
		innerMemberDTO.setOperator(SessionUserHolderUtil.getLoginId());
		innerMemberDTO.setCreateTime(new Date());

		InnerMemberDTO innerMemberDTO2=innerMemberService.findByParentId(Long.parseLong(memberCode));
		if(innerMemberDTO2 != null){
			model.put("dto",innerMemberDTO);
			model.put("message","无法保存，该分子公司会员号已存在！");
			return new ModelAndView(addView, model);
		}
		
		SubMemberDTO subMemberDTO2=subMemberService.findByMemberCode(Long.parseLong(memberCode));
		if(subMemberDTO2 != null){
			model.put("dto",innerMemberDTO);
			model.put("message","无法保存，分子公司会员号不能与普通商户会员号重复！");
			return new ModelAndView(addView, model);
		}

		SubMemberDTO subMemberDTO= new SubMemberDTO();
		subMemberDTO.setMemberCode(memberCode);
		subMemberDTO.setMemberName(innerMemberDTO.getMemberName());
		subMemberDTO.setParentId(Long.parseLong(memberCode));
		subMemberDTO.setOperator(SessionUserHolderUtil.getLoginId());
		subMemberDTO.setCreateTime(new Date());
		innerMemberService.createInnerMember(innerMemberDTO);
		subMemberService.createSubMember(subMemberDTO);
		return new ModelAndView(toView, model).addObject("message", "添加成功"); 
	}

	/**
	 * 准备删除分子公司
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
		InnerMemberDTO innerMemberDTO=innerMemberService.findById(Long.parseLong(req.getParameter("id")));
		model.put("memberToDelete",innerMemberDTO);
		return new ModelAndView(toRemove, model);
	}

	/**
	 * 删除分子公司
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ModelAndView delete(HttpServletRequest req,
			HttpServletResponse response)
			throws ServletRequestBindingException, IOException {
		long id = Long.parseLong(req.getParameter("id"));
		InnerMemberDTO innerMemberDTO=innerMemberService.findById(id);
		subMemberService.deleteByParentId(Long.parseLong(innerMemberDTO.getMemberCode()));
		innerMemberService.deleteById(id);
		return new ModelAndView(toView).addObject("message", "删除成功");
	}

	/**
	 * 准备更新分子公司
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
		InnerMemberDTO innerMemberDTO=innerMemberService.findById(Long.parseLong(req.getParameter("id")));
		model.put("memberToUpdate",innerMemberDTO);
		return new ModelAndView(toModify, model);
	}
	
	/**
	 * 更新分子公司
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ModelAndView update(HttpServletRequest req, HttpServletResponse res)
			throws ServletRequestBindingException,
			IOException {
		String memberCode=(req.getParameter("memberCode"));
		InnerMemberDTO innerMemberDTO= innerMemberService.findById(Long.parseLong(req.getParameter("id")));
		innerMemberDTO.setMemberCode(memberCode);
		innerMemberDTO.setMemberName(req.getParameter("memberName"));
		innerMemberDTO.setOperator(SessionUserHolderUtil.getLoginId());
		innerMemberDTO.setUpdateTime(new Date());
		innerMemberService.updateInnerMember(innerMemberDTO);
		
//		SubMemberDTO subMemberDTO=subMemberService.findByMemberCode(Long.parseLong(memberCode));
//		subMemberDTO.setMemberName(innerMemberDTO.getMemberName());
//		subMemberDTO.setOperator(SessionUserHolderUtil.getLoginId());
//		subMemberDTO.setUpdateTime(new Date());
//		subMemberService.updateSubMember(subMemberDTO);
		return new ModelAndView(toView).addObject("message", "修改成功");
	}
}
