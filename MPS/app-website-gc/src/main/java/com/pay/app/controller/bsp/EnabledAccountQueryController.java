/**
 *Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
 */
package com.pay.app.controller.bsp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.base.common.enums.MemberStatusEnum;
import com.pay.base.dto.MemberBalancesDto;
import com.pay.base.service.member.MemberService;
import com.pay.util.FormatDate;
import com.pay.util.StringUtil;

/**
 * 开户对账单查询，下载
 * @author fjl
 * @date 2011-7-8
 */
public class EnabledAccountQueryController extends MultiActionController {
	private static Pattern datePattern = Pattern.compile("^(19|20)\\d{2}-(0?\\d|1[012])-(0?\\d|[12]\\d|3[01])$");
	
	private static final String EXCEL = "excel";
	private static final int MAX_DOWNLOAD_COUNT = 5000;
	
	private MemberService memberService;

	/**
	 * 查询页面
	 */
	private String querypage;
	
	/**
	 * excel页面
	 */
	private String excelpage;
	
	/**
	 * 首页页面
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		ModelAndView mv =  new ModelAndView(querypage);
		String nowMonth=FormatDate.formatDate(new Date(),"yyyy-MM");
		String nowDate=FormatDate.formatDate(new Date(),"yyyy-MM-dd");
		mv.addObject("nowMonth",nowMonth)
		  .addObject("nowDate",nowDate);
		return mv;
	}
	
	/**
	 * 下载
	 */
	public ModelAndView excel(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		ModelAndView view = null;
		request.setAttribute("operate", "excel");
		
		view = queryMethod(request,response,excelpage,querypage);
		
		Object errorMsg = view.getModelMap().get("errorMsg");
		if(errorMsg == null){
			response.setHeader("Expires", "0");
			response.setHeader("Pragma" ,"public");
			response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Cache-Control", "public");
	
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(("acc"+FormatDate.getDay()+".xls").getBytes("UTF-8"),"ISO8859_1"));
			
			return view;
		}else{
			return view;
		}
	
	}
	
	/*
	 * 查询账户信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ModelAndView queryMethod(HttpServletRequest request,
			HttpServletResponse response,String destView,String srcView) throws Exception {
		ModelAndView mv =  new ModelAndView(destView);
		//(1) 拼条件
		LoginSession session = SessionHelper.getLoginSession();
		String memberCodeStr = session.getMemberCode();
		Long fatherMemberCode =0L;
		if(StringUtils.isNotBlank(memberCodeStr)){
			fatherMemberCode = Long.valueOf(memberCodeStr);
		}
		
	//	String bussType = ServletRequestUtils.getStringParameter(request, "bussType");
		String timeType = ServletRequestUtils.getStringParameter(request, "checkTime");
		Object operate = request.getAttribute("operate");
		
		Date sDate = null;
		Date eDate = null;
		Date date = null;
		
		if(! StringUtil.isEmpty(timeType) && "monthCheck".equals(timeType)){
			String queryTime = ServletRequestUtils.getStringParameter(request, "monthTime") + "-01";
			
			if(!datePattern.matcher(queryTime).matches() ){
				queryTime = FormatDate.getDay();
			}
			
			date = FormatDate.formatStr(queryTime);
			sDate =FormatDate.getMonthFirstDay(date);
			eDate =FormatDate.getMonthLastDay(date);
			
		}else{
			String queryTime = ServletRequestUtils.getStringParameter(request, "dayTime") ;
			if(!datePattern.matcher(queryTime).matches() ){
				queryTime = FormatDate.getDay();
			}
			date = FormatDate.formatStr(queryTime);
			sDate = date;
			eDate = FormatDate.getLastestTimeOfDay(date);
		}
		
	    int pager_offset=1;  //当前页码
		int page_size=10;    //每页条数
		/*try{
			pager_offset=request.getParameter("pager_offset")==null?1:Integer.parseInt(request.getParameter("pager_offset")); //获取页面传过来的页码	
		}catch(NumberFormatException e){
			pager_offset = 1;//如果页面过来的页面不是数字，则默认1
		}*/
		if(EXCEL.equals(operate)){
			pager_offset=1;
		}
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("fatherMemberCode", fatherMemberCode);
		param.put("startDate", sDate);
		param.put("endDate", eDate);
	
		Integer[] status = new Integer[]{
				MemberStatusEnum.NORMAL.getCode(),
				MemberStatusEnum.FROZEEN.getCode(),
				MemberStatusEnum.NO_ACTIVE.getCode()
				};
		param.put("status", status);
		//(2)记录条数
		int count = memberService.queryMemberCountByParent(param);
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("nowMonth", FormatDate.formatDate(new Date(),"yyyy-MM"));
		model.put("nowDate", FormatDate.formatDate(new Date(),"yyyy-MM-dd"));
		
		//判断下载情况
		if(EXCEL.equals(operate)){
			if(count == 0){
				model.put("errorMsg","你选择下载的记录为0条，请选择过滤条件,重新查询！");
				return  new ModelAndView(srcView,model);
			}
			page_size = count;
			//如果是下载EXCEL动作，并且总数据条数大于阀值
			if( MAX_DOWNLOAD_COUNT<page_size){
				page_size = 10;
				model.put("errorMsg","你选择下载的记录已经超过"+MAX_DOWNLOAD_COUNT+"条，请选择过滤条件,重新查询！");
				return  new ModelAndView(srcView,model);
			}
		}
		
	//	PageUtil pu=new PageUtil(pager_offset,page_size,count);//分页处理
		param.put("pageStartRow", (pager_offset-1)*page_size);
		param.put("pageEndRow", pager_offset*page_size);
		
		//(3) 查询当前交易中心的交易商
		List<MemberBalancesDto> memberDtos = memberService.queryMemberByParent(param);
		
		model.put("maxSize",MAX_DOWNLOAD_COUNT);
		model.put("sDate",sDate);
		model.put("eDate",eDate);
		model.put("canDownload",String.valueOf(MAX_DOWNLOAD_COUNT>=count));
	//	model.put("pu",pu);
		model.put("mbdList",memberDtos);
		
		//(4) 返回页面
		return mv.addAllObjects(model);
	}
	
	/**
	 * @param querypage
	 *            the querypage to set
	 */
	public void setQuerypage(String querypage) {
		this.querypage = querypage;
	}

	/**
	 * @return the querypage
	 */
	public String getQuerypage() {
		return querypage;
	}

	/**
	 * @return the excelpage
	 */
	public String getExcelpage() {
		return excelpage;
	}

	/**
	 * @param excelpage the excelpage to set
	 */
	public void setExcelpage(String excelpage) {
		this.excelpage = excelpage;
	}

	/**
	 * @param memberService the memberService to set
	 */
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

}
