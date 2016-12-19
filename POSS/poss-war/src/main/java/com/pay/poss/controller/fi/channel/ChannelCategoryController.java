package com.pay.poss.controller.fi.channel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.poss.client.ChannelClientService;
import com.pay.poss.security.model.SessionUserHolder;
import com.pay.poss.security.util.SessionUserHolderUtil;


/**
 * 默认通道管理
 * 
 * @author sandy
 *
 */
public class ChannelCategoryController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(ChannelCategoryController.class);
	private ChannelClientService channelClientService;
	private String indexView;
	private String addView;
	private String updateView;
	private String resultView;

	public void setChannelClientService(
			ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}

	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	public void setAddView(String addView) {
		this.addView = addView;
	}

	public void setUpdateView(String updateView) {
		this.updateView = updateView;
	}

	public void setResultView(String resultView) {
		this.resultView = resultView;
	}

	/**
	 * 获取渠道类别
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {

		return new ModelAndView(indexView);
	}

	/**
	 * 查询渠道类别
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView query(HttpServletRequest request,
			HttpServletResponse response) {
		String itemName=request.getParameter("itemName");
		String alias=request.getParameter("alias"); // 别名不知道什么意思 贊不实现 delin.dong  update date 2016年5月6日 15:53:25
		Map<String,String> paraMap=new HashMap<String,String>();
		paraMap.put("name", itemName);
		List result = channelClientService.queryChannelCategory(paraMap);
		return new ModelAndView(resultView).addObject("resultList", result);
	}
	
	
	/***
	 * 删除渠道类别
	 * @param request
	 * @param response
	 * @return
	 * @author delin.dong
	  * @date 2016年5月6日 16:52:34
	 */
	public ModelAndView remove(HttpServletRequest request,
			HttpServletResponse response){
		String id=request.getParameter("id");
		Map resMap=channelClientService.removeChannelCategory(id);
		return new ModelAndView(indexView);
	}
	 
	/**
	 * 初始化修改页面
	 * @param request
	 * @param response
	 * @return
	 * @author delin.dong 
	 * @date 2016年5月6日 16:52:34
	 */
	public ModelAndView initUpdate(HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("id");
		Map<String,String> paraMap=new HashMap<String,String>();
		paraMap.put("id", id);
		List result = channelClientService.queryChannelCategory(paraMap);
		return new ModelAndView(updateView).addObject("channelCategory", result.get(0));
	}

	/***
	 *初始化添加页面 
	 * @param request
	 * @param response
	 * @return
	 * @author delin.dong 
	 * @date 2016年5月6日 16:52:34
	 */
	public ModelAndView initAdd(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView(addView);
	}
	
	/**
	 * 新增功能
	 * @param request
	 * @param response
	 * @return
	 * @author delin.dong 
	 * @date 2016年5月6日 16:52:34
	 */
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response){
	
		String code=request.getParameter("code");
		String name=request.getParameter("name");
		String desciption=request.getParameter("description");
		SessionUserHolder sessionUserHolder = SessionUserHolderUtil
				.getSessionUserHolder();
		Map<String,String> map=new HashMap<String,String>();
		map.put("code", code);
		map.put("name", name);
		map.put("description", desciption);
		map.put("operator", sessionUserHolder.getUsername());
		channelClientService.addChannelCategory(map);
		return new ModelAndView(indexView);
	}
	/***
	 * 修改功能
	 * @param request
	 * @param response
	 * @return
	 * @author delin.dong 
	 * @date 2016年5月6日 16:52:34
	 */
	public ModelAndView update(HttpServletRequest request,HttpServletResponse response){
		String code=request.getParameter("code");
		String id=request.getParameter("id");
		String name=request.getParameter("name");
		String desciption=request.getParameter("description");
		SessionUserHolder sessionUserHolder = SessionUserHolderUtil
				.getSessionUserHolder();
		Map<String,String> map=new HashMap<String,String>();
		map.put("code", code);
		map.put("name", name);
		map.put("description", desciption);
		map.put("status", "1");
		map.put("operator", sessionUserHolder.getUsername());
		map.put("id", id);
		channelClientService.updateChannelCategory(map);
		return new ModelAndView(indexView);
	}
	

}
