package com.pay.rm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.tree.BackedList;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.security.model.SessionUserHolder;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.rm.blacklistcheck.dto.BlackChecklistDTO;
import com.pay.rm.blacklistcheck.service.BlacklistCheckService;
/**
 * 黑名单 审核
 * @date 2016年6月14日11:42:37
 * @author delin.dong
 */
public class BlacklistCheckController extends MultiActionController{
	
	private final Log log = LogFactory.getLog(BlacklistCheckController.class);
	
	private BlacklistCheckService blacklistCheckService;

	private String index;
	
	private String list;
	
	public void setList(String list) {
		this.list = list;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public void setBlacklistCheckService(BlacklistCheckService blacklistCheckService) {
		this.blacklistCheckService = blacklistCheckService;
	}
	/**
	 * 查询初始化
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView(index);
	}

	/***
	 * 查询带check的黑名单
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView query(HttpServletRequest request,HttpServletResponse response){
		String memberCode=request.getParameter("memberCode");
		String businessTypeId=request.getParameter("businessTypeId");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String status=request.getParameter("status");
		Page<BlackChecklistDTO> page = PageUtils.getPage(request);
		Map map = new HashMap();
		map.put("memberCode",memberCode);
		map.put("businessTypeId",businessTypeId);
		map.put("status", status);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		page =blacklistCheckService.queryBlacklistCheck(map,page);
		return new ModelAndView(list).addObject("page", page);
	}
	
	/**
	 * 处理黑名单
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView dispose(HttpServletRequest request,HttpServletResponse response){
		ModelAndView indexView = new ModelAndView(index);
		Page<BlackChecklistDTO> page = PageUtils.getPage(request);
		String ids=request.getParameter("ids");
		String loginId = SessionUserHolderUtil.getLoginId();
		Map map=new HashMap();
		map.put("ids", ids.substring(0, ids.length()-1));
		page =blacklistCheckService.queryBlacklistCheck(map,page);
		List<BlackChecklistDTO> result = page.getResult(); // 给大爷的参数
		BlackChecklistDTO blackCheck=new BlackChecklistDTO();
		blackCheck.setIds(ids.substring(0, ids.length()-1));
		blackCheck.setStatus(1);
		boolean flag=blacklistCheckService.updateBlacklistCheckStatus(blackCheck,result,loginId);
		if(!flag){
			indexView.addObject("msg", "加入黑名单失败！！黑名单已存在。");
		}
		return indexView;
	}
	/**
	 * 致为已处理
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView processed(HttpServletRequest request,HttpServletResponse response){
		String ids=request.getParameter("ids");
		BlackChecklistDTO blackCheck=new BlackChecklistDTO();
		String loginId = SessionUserHolderUtil.getLoginId();
		blackCheck.setIds(ids.substring(0, ids.length()-1));
		blackCheck.setStatus(2);
		boolean flag=blacklistCheckService.updateBlacklistCheckStatus(blackCheck,null,loginId);
		return new ModelAndView(index);
	}
}
