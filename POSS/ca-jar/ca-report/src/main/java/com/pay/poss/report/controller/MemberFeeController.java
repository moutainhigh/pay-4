package com.pay.poss.report.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.report.dto.InnerMemberDTO;
import com.pay.poss.report.dto.MemberFeeDTO;
import com.pay.poss.report.service.InnerMemberService;
import com.pay.poss.report.service.MemberFeeService;
import com.pay.util.StringUtil;

/**
 * 会员手续费收入查询
 * 
 * @Description
 * @file MemberFeeController
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 */
public class MemberFeeController extends MultiActionController {

	private String viewList;
	
	private String toView;
	
	private String memberFeeExcel;

	private MemberFeeService memberFeeService;
	
	private InnerMemberService innerMemberService;

	public void setMemberFeeService(MemberFeeService memberFeeService) {
		this.memberFeeService = memberFeeService;
	}

	public void setInnerMemberService(InnerMemberService innerMemberService) {
		this.innerMemberService = innerMemberService;
	}
	public void setViewList(String viewList) {
		this.viewList = viewList;
	}
	public void setToView(String toView) {
		this.toView = toView;
	}
	public void setMemberFeeExcel(String memberFeeExcel) {
		this.memberFeeExcel = memberFeeExcel;
	}
	
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Calendar time = Calendar.getInstance();
		time.add(Calendar.DATE, -1);
		Date startDate = time.getTime();
		Date endDate = startDate;
		List<InnerMemberDTO> list = innerMemberService.findAllInnerMember();

		return new ModelAndView(toView).addObject("startDate", startDate)
				.addObject("endDate", endDate).addObject("list", list);
	}

	public ModelAndView queryMemberFee(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String startDate = StringUtil.null2String(request.getParameter("startDate"));
		String endDate = StringUtil.null2String(request.getParameter("endDate"));
		String memberType =StringUtil.null2String(request.getParameter("memberType"));
		String innerMemberCode =StringUtil.null2String(request.getParameter("innerMemberCode"));
		String subMemberCode =StringUtil.null2String(request.getParameter("subMemberCode"));
		String optType =StringUtil.null2String(request.getParameter("optType"));
		if(!StringUtil.isEmpty(startDate)){
			map.put("startDate", startDate.trim());
		}
		if(!StringUtil.isEmpty(endDate)){
			map.put("endDate", endDate.trim());
		}
		if(StringUtils.isEmpty(optType) || "2".equalsIgnoreCase(optType.trim())){
			map.put("isInnerMember", "Y");
			if(!StringUtil.isEmpty(innerMemberCode)){
				map.put("memberCode", innerMemberCode.trim());
			}
		}else{
			if(!StringUtil.isEmpty(memberType)){
				map.put("memberType", memberType.trim());
			}
			if(!StringUtil.isEmpty(subMemberCode)){
				map.put("memberCode", subMemberCode.trim());
			}
		}
		Page<MemberFeeDTO> page = PageUtils.getPage(request);
		List<MemberFeeDTO> result = new ArrayList<MemberFeeDTO>();
		String export = StringUtil.null2String(request.getParameter("export"));
		//手续费查询
		if (!StringUtils.isEmpty(export)) {
			String fileName = "手续费下载";
			result = memberFeeService.queryMemberFee(map);
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Cache-Control", "public");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes("GBK"), "ISO8859_1"));
			return new ModelAndView(memberFeeExcel).addObject("result", result);
		} else {
			page = memberFeeService.queryMemberFee(page, map);
			return new ModelAndView(viewList).addObject("page", page);
		}
	}
}
