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

import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.constantenum.PayForEnum;
import com.pay.app.base.api.common.enums.BspIdentityEnum;
import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.facade.dto.MaSumDto;
import com.pay.app.facade.dto.QueryBalanceDto;
import com.pay.base.model.Acct;
import com.pay.base.service.acct.AcctService;
import com.pay.base.service.queryhistory.QueryBalanceService;
import com.pay.util.CheckUtil;
import com.pay.util.DateUtil;
import com.pay.util.FormatDate;
import com.pay.util.StringUtil;

public class BalanceQueryController extends MultiActionController {

	private static Pattern datePattern = Pattern.compile("^(19|20)\\d{2}-(0?\\d|1[012])-(0?\\d|[12]\\d|3[01])$");
	private static final String EXCEL = "excel";
	private static final int MAX_DOWNLOAD_COUNT = 5000;
	private static final String[][] DEFAULT_COLUMNS = new String[][]{{"bdeal_id","desc"}};
	
	/**
	 * 查询页面
	 */
	private String querypage;
	private String querycenterpage;
	
	/**
	 * 下载excel页面
	 */
	private String excelpage;
	
	private AcctService acctService;
	private QueryBalanceService queryBalanceService;
	
	/**
	 * 查询
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		LoginSession session = SessionHelper.getLoginSession();
		if(BspIdentityEnum.CORP_TRADINGMANAGER.getIdentity() == session.getBspIdentity()){
			//交易中心，收支明细查询
			return queryMethod(request,response,querycenterpage,querycenterpage);
		}else{
			//交易商，收支明细查询
			return queryMethod(request,response,querypage,querypage);
		}
	}
	
	/**
	 * 下载
	 */
	public ModelAndView excel(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		ModelAndView view = null;
		request.setAttribute("operate", "excel");
		LoginSession session = SessionHelper.getLoginSession();
		
		if(BspIdentityEnum.CORP_TRADINGMANAGER.getIdentity() == session.getBspIdentity()){
			//交易中心，收支明细查询
			view = queryMethod(request,response,excelpage,querycenterpage);
		}else{
			//交易商，收支明细查询
			view = queryMethod(request,response,excelpage,querypage);
		}
		
		Date nowDate =  FormatDate.formatStr(FormatDate.getDay());
		view.addObject("nowDate",nowDate);
		
		Object errorMsg = view.getModelMap().get("errorMsg");
		if(errorMsg == null){
			response.setHeader("Expires", "0");
			response.setHeader("Pragma" ,"public");
			response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Cache-Control", "public");
	
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(("balance"+FormatDate.getDay()+".xls").getBytes("UTF-8"),"ISO8859_1"));
			
			return view;
		}else{
			return view;
		}
	
	}
	
	/*
	 * 查询方法
	 */
	private ModelAndView queryMethod(HttpServletRequest request,
			HttpServletResponse response ,String destView,String srcView) throws Exception {
		ModelAndView mv =  new ModelAndView(destView);
		//(1) 拼条件
		LoginSession session = SessionHelper.getLoginSession();
		String memberCodeStr = session.getMemberCode();
		Long fatherMemberCode =0L;
		if(StringUtils.isNotBlank(memberCodeStr)){
			fatherMemberCode = Long.valueOf(memberCodeStr);
		}
		
		String startTime = ServletRequestUtils.getStringParameter(request, "startTime");
		String endTime = ServletRequestUtils.getStringParameter(request, "endTime");
		String queryName = ServletRequestUtils.getStringParameter(request, "sonMemberName");
		String sonMemberName = queryName == null ? "" : queryName.trim();
		
		String dealType =  request.getParameter("dealType");
		Object operate = request.getAttribute("operate");
		String isquery = ServletRequestUtils.getStringParameter(request, "isquery");
		isquery = StringUtil.isEmpty(isquery) ? "0" : "1";
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		if(StringUtil.isEmpty(startTime)){
			startTime = FormatDate.formatDate(FormatDate.getDateLastestMonth(1),"yyyy-MM-dd");
		}
		
		if(StringUtil.isEmpty(endTime)){
			endTime = DateUtil.formatDateTime("yyyy-MM-dd");
		}
		
		Date sDate = null;
		Date eDate = null;
		//时间格式验证不通过
	    if(!datePattern.matcher(startTime==null?"":startTime).matches() ){
	    	startTime = FormatDate.formatDate(FormatDate.getDateLastestMonth(1),"yyyy-MM-dd");
		}
	    if(!datePattern.matcher(endTime==null?"":endTime).matches() ){
	    	endTime = DateUtil.formatDateTime("yyyy-MM-dd");
		}
	    
	    if(StringUtils.isNotBlank(startTime)){
	    	sDate =  FormatDate.formatStr(startTime);
	    }
	    
	    if(StringUtils.isNotBlank(endTime)){
	    	eDate =  FormatDate.getLastestTimeOfDay(FormatDate.formatStr(endTime));
	    }
	    
	    int pager_offset=1;  //当前页码
		int page_size=10;     //每页条数
		try{
			pager_offset=request.getParameter("pager_offset")==null?1:Integer.parseInt(request.getParameter("pager_offset")); //获取页面传过来的页码	
		}catch(NumberFormatException e){
			pager_offset = 1;//如果页面过来的页面不是数字，则默认1
		}
		if(EXCEL.equals(operate)){
			pager_offset=1;
		}
		
		String queryDealType = "";
		if(!CheckUtil.checkFundTrace(dealType)){
		}else{
			queryDealType = PayForEnum.get(Integer.parseInt(dealType)).getGroupValue();
		}
		Integer accountType =  AcctTypeEnum.BASIC_CNY.getCode();
		
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		if(StringUtils.isBlank(sonMemberName)){
			model.put("sonMemberName", "");
		}else{
			model.put("sonMemberName", sonMemberName);
		}
		model.put("dealType",Integer.parseInt(dealType));
		model.put("accountType",accountType);
		model.put("maxSize",MAX_DOWNLOAD_COUNT);
		model.put("sDate",sDate);
		model.put("eDate",eDate);
		model.put("isquery", isquery);		
		
		//(2) 查询当前交易商账户
		Acct acct = null;
		//判断如果是交易中心，sonMemberName为必填。否则返回报错。
		if(BspIdentityEnum.CORP_TRADINGMANAGER.getIdentity() == session.getBspIdentity()){
			model.put("tradingmanager", "1");
			if(StringUtil.isEmpty(sonMemberName) ){
				return new ModelAndView(srcView,model);
			}
			acct = acctService.getSonAcctByParentMember(fatherMemberCode,accountType,sonMemberName);
		}else{
			model.put("tradingmanager", "0");
			acct = acctService.getByMemberCode(new Long(memberCodeStr),accountType);
		}
		
		MaSumDto sum = null;
		PageUtil pu = null;
		List<QueryBalanceDto> qbd = null;
		/*
		 * 查询统计
		 */
		sum = queryBalanceService.queryHistoryBusinessSum(sDate, eDate,acct,queryDealType,null,null,null);
		
		if(EXCEL.equals(operate)){
			
			if(sum == null || sum.getCount()==0){
				model.put("errorMsg","你选择下载的记录为0条，请选择过滤条件,重新查询！");
				return  new ModelAndView(srcView,model);
			}
			page_size = (sum==null?0:sum.getCount());
			//如果是下载EXCEL动作，并且总数据条数大于阀值
			if( MAX_DOWNLOAD_COUNT<page_size){
				page_size = 10;
				model.put("errorMsg","你选择下载的记录已经超过"+MAX_DOWNLOAD_COUNT+"条，请选择过滤条件,重新查询！");
				return  new ModelAndView(srcView,model);
			}
		}
				
		pu=new PageUtil(pager_offset,page_size,sum==null?0:sum.getCount());//分页处理
		
		/*
		 * 查询余额
		 */
		qbd = queryBalanceService.queryBalanceList(sDate, eDate, acct,queryDealType,null ,pager_offset,page_size,DEFAULT_COLUMNS,null, queryDealType);
		
		if(sum==null){
			sum = new MaSumDto();
		}
		
		/*
		 * 返回页面痕迹、查询结果
		 */
		model.put("canDownload",String.valueOf(MAX_DOWNLOAD_COUNT>=pu.getTotalCount()));
		model.put("pu",pu);
		model.put("sum",sum);
		model.put("qbdList",qbd);

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
	 * @return the querycenterpage
	 */
	public String getQuerycenterpage() {
		return querycenterpage;
	}

	/**
	 * @param querycenterpage the querycenterpage to set
	 */
	public void setQuerycenterpage(String querycenterpage) {
		this.querycenterpage = querycenterpage;
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
	 * @param datePattern the datePattern to set
	 */
	public static void setDatePattern(Pattern datePattern) {
		BalanceQueryController.datePattern = datePattern;
	}

	/**
	 * @param acctService the acctService to set
	 */
	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	/**
	 * @param queryBalanceService the queryBalanceService to set
	 */
	public void setQueryBalanceService(QueryBalanceService queryBalanceService) {
		this.queryBalanceService = queryBalanceService;
	}	
	
}
