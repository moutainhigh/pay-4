package com.pay.poss.controller.fi.crosspay;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.client.CrosspayWebsiteConfigService;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.MapUtil;
import com.pay.util.NumberUtil;
import com.pay.util.StringUtil;

/**
 * 网站管理poss 后台
 * 
 * @Description
 * @file SitesetController
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2012 hnapayay Corporation. All rights reserved.
 *          Date
 */
public class SitesetController extends MultiActionController {

	private String queryInit;
	private String addView;
	private String recordList;
	private String siteSetPage;
	private String reviewed;
	private MemberService memberService;
	private CrosspayWebsiteConfigService crosspayWebsiteConfigService;

	public void setReviewed(String reviewed) {
		this.reviewed = reviewed;
	}

	public void setQueryInit(String queryInit) {
		this.queryInit = queryInit;
	}

	public void setRecordList(String recordList) {
		this.recordList = recordList;
	}

	public String getSiteSetPage() {
		return siteSetPage;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setSiteSetPage(String siteSetPage) {
		this.siteSetPage = siteSetPage;
	}

	public void setAddView(String addView) {
		this.addView = addView;
	}

	public void setCrosspayWebsiteConfigService(
			CrosspayWebsiteConfigService crosspayWebsiteConfigService) {
		this.crosspayWebsiteConfigService = crosspayWebsiteConfigService;
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
		String partnerId = StringUtil.null2String(request
				.getParameter("partnerId"));
		String siteId = StringUtil.null2String(request.getParameter("siteId"));
		String status = StringUtil.null2String(request.getParameter("status"));
		String statusIn = StringUtil.null2String(request.getParameter("statusIn"));
		Map map=new HashMap();
		map.put("partnerId", partnerId);
		map.put("siteId", siteId);
		map.put("status", status);
		map.put("statusIn", statusIn);
		return new ModelAndView(queryInit).addObject("map", map);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toAdd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(addView);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toAudit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView view = new ModelAndView(addView);
		String id = request.getParameter("id");
		String partnerId = request.getParameter("partnerId");
		String siteId = request.getParameter("siteId");
		String status = request.getParameter("status");

		view.addObject("id", id);
		view.addObject("partnerId", partnerId);
		view.addObject("siteId", siteId);
		view.addObject("status", status);
		logger.info("siteId:" + siteId);
		logger.info("id:" + id + ",partnerId:" + partnerId);
		return view;
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
		response.setCharacterEncoding("utf-8");
		String partnerId = StringUtil.null2String(request
				.getParameter("partnerId"));
		String siteId = StringUtil.null2String(request.getParameter("siteId"));
		String remark = StringUtil.null2String(request.getParameter("remark"));
		String operator = SessionUserHolderUtil.getLoginId();

		if (StringUtil.isEmpty(partnerId) || !NumberUtil.isNumber(partnerId)) {
			response.getWriter().print("会员号不合法！");
			return null;
		}

		MemberDto memberDto = memberService.queryMemberByMemberCode(Long
				.valueOf(partnerId));
		if (memberDto == null) {
			response.getWriter().print("会员不存在！");
			return null;
		}
		
		Map resultMap = crosspayWebsiteConfigService.crosspayMerchantWebsiteQuery(partnerId, null, null, null,siteId,null);
		List<Map> websiteConfigs = (List<Map>) resultMap.get("result");
		if(websiteConfigs!=null){
			Iterator<Map> iterator = websiteConfigs.iterator();
			while (iterator.hasNext()) {
				Map next = iterator.next();
				if(next.get("status").equals("4")){
					iterator.remove();
				}
			}
			if(!websiteConfigs.isEmpty()){
				response.getWriter().print("域名已存在,请勿重复添加！");
				return null;
			}
		}
		
		boolean f = crosspayWebsiteConfigService.merchantWebsiteAdd(partnerId,
				siteId, operator, remark);
		if (f) {
			response.getWriter().print(1);
		} else {
			response.getWriter().print("添加失败！");
		}
		return null;
	}

	public ModelAndView del(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String id = StringUtil.null2String(request.getParameter("id"));
		boolean f = crosspayWebsiteConfigService.merchantWebsiteDel(id);
		if (f) {
			response.getWriter().print(1);
		} else {
			response.getWriter().print(0);
		}
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
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String partnerId = StringUtil.null2String(request
				.getParameter("partnerId"));
		String siteId = StringUtil.null2String(request.getParameter("siteId"));
		String status = StringUtil.null2String(request.getParameter("status"));
		String statusIn = StringUtil.null2String(request.getParameter("statusIn"));

		Page page = PageUtils.getPage(request);
		
		Map resultMap = crosspayWebsiteConfigService.crosspayMerchantWebsiteQuery(partnerId, siteId, status, page,null,statusIn);
		

		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		List<Map> websiteConfigs = (List<Map>) resultMap.get("result");

		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		
		return new ModelAndView(recordList).addObject("websiteConfigs",
				websiteConfigs).addObject("page",page);

	}

	/**
	 * 查看网站信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toSiteSetPage(HttpServletRequest request,
			HttpServletResponse response, Long id) throws Exception {
		return new ModelAndView(siteSetPage);
	}

	/**
	 * 提交审核信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView updateSiteSetStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = StringUtil.null2String(request.getParameter("id"));
		String partnerId = StringUtil.null2String(request
				.getParameter("partnerId"));
		String siteId = StringUtil.null2String(request.getParameter("siteId"));
		String status = StringUtil.null2String(request.getParameter("status"));
		String remark = StringUtil.null2String(request.getParameter("remark"));
		String sendCredorax = StringUtil.null2String(request.getParameter("sendCredorax"));
		String category=StringUtil.null2String(request.getParameter("category"));
		String operator = SessionUserHolderUtil.getLoginId();
		boolean isUpdate = crosspayWebsiteConfigService.merchantWebsiteUpdate(
				id, partnerId, status, siteId, operator, remark , sendCredorax,category);
		if (isUpdate) {
			response.getWriter().print(1);
		} else {
			response.getWriter().print(2);
		}
		return null;
	}
	
	/**
	 * 批量审核页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView bacthReviewed(HttpServletRequest request,HttpServletResponse response){
		String siteIds = request.getParameter("siteIds");
		String partnerId = StringUtil.null2String(request
				.getParameter("partnerId"));
		String siteId = StringUtil.null2String(request.getParameter("siteId"));
		String status = StringUtil.null2String(request.getParameter("status"));
		Map map=new HashMap();
		map.put("partnerId", partnerId);
		map.put("siteId", siteId);
		map.put("status", status);
		String[] split = siteIds.split(",");
		return new ModelAndView(reviewed).addObject("siteIds", siteIds).addObject("batchCount",split.length).addObject("map", map);
	}
	
	
	
	/**
	 * 批量审核
	 * @return
	 * @Date 2016年6月3日 20:46:39
	 * @author delin.dong
	 * @throws IOException 
	 */
	public ModelAndView updateBacthSiteSetStatus(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String status=request.getParameter("status1");
		String id=request.getParameter("id");
		String category=request.getParameter("category");
		String sendCredorax=request.getParameter("sendCredorax1");
		String remark=request.getParameter("remark");
		String operator = SessionUserHolderUtil.getLoginId();
		Map map=new HashMap();
		map.put("status",status);
		map.put("id", id);
		map.put("category", category);
		map.put("sendCredorax", sendCredorax);
		map.put("remark", remark);
		map.put("operator", operator);
		map.put("bacth", "1");//bacth代表批量更新状态
		boolean isUpdate=crosspayWebsiteConfigService.updateBacthSiteSetStatus(map);
		if (isUpdate) {
			response.getWriter().print(1);
		} else {
			response.getWriter().print(2);
		}
		return null;
	}
	/**
	 * 工作面板 查询待审核域名
	 * @author delin.dong
	 * @date 2016年6月8日14:57:47
	 * @param response
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	public ModelAndView workPanels(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,String> map=new HashMap<String,String>();
		map.put("status", "2,5,6");
		Map pendingMap=crosspayWebsiteConfigService.countWebsiteByStatus(map);
		String pendingCount=String.valueOf(pendingMap.get("pendingCount"));
		response.getWriter().print(pendingCount);
		return null;
	}
}
