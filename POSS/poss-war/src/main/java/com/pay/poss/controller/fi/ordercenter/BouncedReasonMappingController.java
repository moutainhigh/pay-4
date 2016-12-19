package com.pay.poss.controller.fi.ordercenter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.util.JSONUtils;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.client.CrosspayTxncoreClientService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 拒付关系映射查询和新增
 * 
 * @date  2016年5月24日14:34:30
 * @author delin.dong
 */
public class BouncedReasonMappingController extends MultiActionController{

	private String index;
	
	private String list;
	
	private CrosspayTxncoreClientService txncoreClientService; 
	
	public void setTxncoreClientService(
			CrosspayTxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}

	public void setList(String list) {
		this.list = list;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView(index);
	}
	/**
	 *  分页查询
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
		Page page = PageUtils.getPage(request);
		final String orgCode = StringUtil.null2String(request
				.getParameter("orgCode"));
		final String reasonCode = StringUtil.null2String(request
				.getParameter("reasonCode"));
		final String visableCode = StringUtil.null2String(request
				.getParameter("visableCode"));

		Map param=new HashMap();
		param.put("orgCode", orgCode);
		param.put("reasonCode",reasonCode);
		param.put("visableCode", visableCode);
		param.put("page", page);
			
		Map map=txncoreClientService.queryBouncedReasonMapping(param);
		Map pageMap = (Map) map.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		List<Map> bouncedReasonMappings = (List<Map>) map.get("result");
		return new ModelAndView(list).addObject("bouncedReasonMappings", bouncedReasonMappings).addObject("page", page).addObject("serialNumber",bouncedReasonMappings.size()+1);
	}
	
	/**
	 * 新增拒付显示原因映射
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView addBouncedReasonMapping(HttpServletRequest request,HttpServletResponse response){
		final String orgCode = StringUtil.null2String(request
				.getParameter("orgCode"));
		final String bouncedReason = StringUtil.null2String(request
				.getParameter("bouncedReason"));
		final String reasonCode = StringUtil.null2String(request
				.getParameter("reasonCode"));
		final String visableName = StringUtil.null2String(request
				.getParameter("visableName"));
		final String visableCode = StringUtil.null2String(request
				.getParameter("visableCode"));
		
		Map<String, String> param=new HashMap<String, String>();
		param.put("orgCode", orgCode);
		param.put("reasonCode",reasonCode);
		param.put("visableCode", visableCode);
		param.put("visableName", visableName.substring(3,visableName.length()));
		param.put("status", "1");
		param.put("bouncedReason", bouncedReason);
		Map map=txncoreClientService.addBouncedReasonMapping(param);
		String responseCode=map.get("responseCode")+"";
		if(responseCode.equals("0000")){
			return new ModelAndView(index).addObject("info", "添加成功！！");
		}
		return new ModelAndView(index).addObject("info", "添加失败！！");
	}
	
	/**
	 * 查询拒付原因CODE和显示原因CODE
	 * @param request
	 * @param response
	 */
	public void queryCode(HttpServletRequest request,HttpServletResponse response){
		Page page = PageUtils.getPage(request);
		page.setPageSize(30000);
		final String orgCode = StringUtil.null2String(request
				.getParameter("orgCode"));
		final String reasonCode = StringUtil.null2String(request
				.getParameter("reasonCode"));
		Map param=new HashMap();
		param.put("status", "1");
		param.put("orgCode", orgCode);
		param.put("page", page);
		param.put("reasonCode", reasonCode);
		Map map=txncoreClientService.queryBouncedReasonMapping(param);
		List<Map> bouncedReasonMappings = (List<Map>) map.get("result");
		this.removeDuplicate(bouncedReasonMappings);
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(JSonUtil.toJSonString(bouncedReasonMappings));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void removeDuplicate(List<Map> list) { 
		for ( int i = 0 ; i < list.size() - 1 ; i ++ ) { 
			for ( int j = list.size() - 1 ; j > i; j -- ) { 
				if (list.get(j).get("reasonCode").equals(list.get(i).get("reasonCode"))) { 
					list.remove(j); 
				} 
			} 
		} 
		System.out.println(list); 
	} 

}
