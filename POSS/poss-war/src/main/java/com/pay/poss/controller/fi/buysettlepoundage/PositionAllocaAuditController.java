package com.pay.poss.controller.fi.buysettlepoundage;

import java.text.SimpleDateFormat;
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
import com.pay.poss.client.CapitalPoolManageService;
import com.pay.util.MapUtil;

/**
 * 头寸调拨审核
 * @author delin
 * @Date 2016年8月9日18:20:53
 */
public class PositionAllocaAuditController extends MultiActionController {

	private CapitalPoolManageService capitalPoolManageService;

	private String positionIndex;

	private String positionList;
	
	public void setPositionIndex(String positionIndex) {
		this.positionIndex = positionIndex;
	}
	public void setPositionList(String positionList) {
		this.positionList = positionList;
	}

	public void setCapitalPoolManageService(
			CapitalPoolManageService capitalPoolManageService) {
		this.capitalPoolManageService = capitalPoolManageService;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(positionIndex);
	}
	/**
	 * 查询头寸调拨审核
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response){
			String allotSequence=request.getParameter("allotSequence");
			String beginCreateDate=request.getParameter("beginCreateDate");
			String endCreateDate=request.getParameter("endCreateDate");
			String status=request.getParameter("status");
			Page<Object> page = PageUtils.getPage(request);
		    Map<String,Object> param=new HashMap<String, Object>();
		    if(StringUtils.isNotEmpty(allotSequence)){
		    	param.put("allotSequence", Long.valueOf(allotSequence));
		    }
		    param.put("beginCreateDate",beginCreateDate);
		    param.put("endCreateDate", endCreateDate);
		    param.put("status", status);
		    param.put("page", page);
		    Map<String,Object> resultMap=capitalPoolManageService.queryPosition(param);
		    List<Map> list=(List<Map>) resultMap.get("list");
		    for (Map map : list) {
		    	  Long creDate= (Long) map.get("createDate");
		    	  if(creDate!=null){
					   Date date = new Date(creDate);
					   SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					   map.put("createDate", sd.format(date));
				   }
			}
			Map pageMap = (Map) resultMap.get("page");
			page = MapUtil.map2Object(Page.class, pageMap);
		return new ModelAndView(positionList).addObject("list", list).addObject("list", list).addObject("page", page);
	}
	
	/**
	 * 	调拨 审核  1 . 通过   2 . 拒绝
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView update(HttpServletRequest request,HttpServletResponse response){
		ModelAndView index = new ModelAndView(positionIndex);
		String status=request.getParameter("status");
		String allotSequence=request.getParameter("allotSequence");
		Map<String,Object> paraMap=new HashMap<String, Object>();
		paraMap.put("status", status);
		paraMap.put("allotSequence", allotSequence);
		Map<String,Object> resultMap=capitalPoolManageService.update(paraMap);
		if("0000".equals(resultMap.get("responseCode"))){
			index.addObject("error", "操作成功！！！");	
		}
		return new ModelAndView(positionIndex);
	}
	
}
