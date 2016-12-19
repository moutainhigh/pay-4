package com.pay.poss.controller.bounced;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.pay.poss.client.BouncedQueryService;
import com.pay.util.MapUtil;
/**
 * 拒付罚款查询
 * @author delin
 * @date 2016年7月21日09:58:53
 */
public class BouncedFineController  extends MultiActionController{

	private String index;
	
	private String list;
	
	private String memBalaNotEnough;
	
	private BouncedQueryService bouncedQueryService;
	
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView(index);
	}

	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
		String memberCode=request.getParameter("memberCode");
		String memberName=request.getParameter("memberName");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		Page page = PageUtils.getPage(request);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		map.put("memberName", memberName);
		map.put("page", page);
		Date date=new Date();
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.MONTH,-6);
		Date dt1=rightNow.getTime();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM");   
		if(StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime) ){
			map.put("startTime",df.format(dt1));
			map.put("endTime",df.format(date));
		}else{
			map.put("startTime", startTime);
			map.put("endTime", endTime);
		}
		Map resultMap=bouncedQueryService.queryBouncedFine(map);
		List<Map> result= (List<Map>) resultMap.get("result");
		for (Map map2 : result) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			  SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
			 String bouncedDate=(String)map2.get("bouncedDate");
				 try {
					Date parse = sdf.parse(bouncedDate);
					map2.put("bouncedDate",sdf2.format(parse));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			 Long creDate= (Long) map2.get("createDate");
			 Long executeDate= (Long) map2.get("executeDate");//转为日期格式
			   if(creDate!=null){
				   Date d1 = new Date(creDate);
				   SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				   map2.put("createDate", sd.format(d1));
			   }
			   if(executeDate!=null){
				   Date d2 = new Date(executeDate);
				   SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				   map2.put("executeDate", sd.format(d2));
			   }
		}
		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		return new ModelAndView(list).addObject("page", page).addObject("result", result);
	}
	
	/**
	 * 查询余额不足商户
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView memberBalanceNotEnough(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map=new HashMap<String, Object>();
		Map resultMap=bouncedQueryService.memberBalanceNotEnough(map);
		List<Map> result= (List<Map>) resultMap.get("result");
		return new ModelAndView(memBalaNotEnough).addObject("result", result);
	}

	public void setMemBalaNotEnough(String memBalaNotEnough) {
		this.memBalaNotEnough = memBalaNotEnough;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public void setList(String list) {
		this.list = list;
	}

	public void setBouncedQueryService(BouncedQueryService bouncedQueryService) {
		this.bouncedQueryService = bouncedQueryService;
	}
}
