package com.pay.app.controller.base.queryhistory;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.constantenum.PayForEnum;
import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.facade.dto.MaSumDto;
import com.pay.app.facade.dto.QueryBalanceDto;
import com.pay.base.common.enums.ServiceLevelEnum;
import com.pay.base.model.Acct;
import com.pay.base.service.acct.AccountTypeService;
import com.pay.base.service.acct.AcctService;
import com.pay.base.service.queryhistory.QueryBalanceService;
import com.pay.util.CheckUtil;
import com.pay.util.FormatDate;

/**
 * @author jerry_jin
 * @version 
 * @data 2010-11-11
 * 个人卖家收支明细
 */
public class QueryIndividualSellerBalanceController extends MultiActionController{
	
	/**
	 * 账户收支明细
	 */
	private String queryBalance;
	
	/**
	 * 账户收支明细EXCEL
	 */
	private String excleBalance;
	
	private QueryBalanceService queryBalanceService;
	
	private AccountTypeService accountTypeService;
	
	private AcctService acctService;
	//
	private static final String[][] DEFAULT_COLUMNS = new String[][]{{"bdeal_id","desc"}};
	
	//日期正则
	private static final Pattern DATE_PATTERN = Pattern.compile("^(19|20)\\d{2}-(0?\\d|1[012])-(0?\\d|[12]\\d|3[01])$");
	
	private static final Pattern ORDER_STR_PATTERN = Pattern.compile("^[0-2],[1|0]$");

	private final static Pattern QUERY_PRO_PATTERN = Pattern.compile("^lastestHalfYear|currentDay|lastestWeek|currentMonth|lastestMonth|lastestThreeMonth$");
	
	private static final String EXCEL = "excel";
	
	private static final int MAX_DOWNLOAD_COUNT = 5000;
	
	private static final String DEFAULT_VAL = "0";
	
	private static final int MAX_MONTH = 6;
	
	private static final String[][][] ORDER_COLUMNS = new String[][][]{
			{{"create_date","DESC"},{"create_date","ASC"}},
			{{"amount","DESC"},{"amount","ASC"}},
			{{"amount","DESC"},{"amount","ASC"}}
		};
	
	private static final String DEFAULT_ORDER = "0,0";
	
	/**
	 * 账户余额查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_ACCOUNT_QUERYBALANCE")
	public ModelAndView queryBalance(HttpServletRequest req,
			HttpServletResponse resp){
		LoginSession loginSession = SessionHelper.getLoginSession();
		if(loginSession.getServiceLevel().intValue() == ServiceLevelEnum.INDIVIDUAL_SELLER.getValue().intValue()){
			return this.queryBalanceMethod(req,resp,queryBalance);
		}
		if(SessionHelper.isCorpLogin()){
			return new ModelAndView("redirect:/corp/queryBalanceList.htm?method=queryBalance");
		}
		return new ModelAndView("redirect:/app/queryBalanceList.htm?method=queryBalance");
	}
	/**
	 * 账户余额
	 * @param req
	 * @param resp
	 * @param view
	 * @return
	 * @throws Exception
	 */
	private ModelAndView queryBalanceMethod(HttpServletRequest req,
			HttpServletResponse resp,String view){
		ModelAndView mv =  null;
		LoginSession loginSession = (LoginSession) req.getSession().getAttribute("userSession");
		String memberCode = loginSession.getMemberCode();
		String userName = loginSession.getLoginName();
		/**
		 * 页面条件:开始时间、结束时间、资金流向、明细维度
		 */
		String startTime = req.getParameter("startTime");
		String endTime = req.getParameter("endTime");
		String dealType =  req.getParameter("dealType");
		String queryPro = req.getParameter("queryPro");
		String fundTrace = req.getParameter("fundTrace");
		String orderStr = req.getParameter("order");
		/*
		 * 订单号
		 */
		String payNo = req.getParameter("payNo");
		Integer accountType = null;
		try{
			accountType = Integer.parseInt(req.getParameter("accountType"));
		}catch(NumberFormatException e){
			accountType =  AcctTypeEnum.BASIC_CNY.getCode();
		}
		Object operate = req.getAttribute("operate");
		
		
		/**
		 * 页面条件:开始时间、结束时间、资金流向、交易类型、明细维度
		 */
		int pager_offset=1;  //当前页码
		int page_size=5;     //每页条数
		try{
			pager_offset=req.getParameter("pager_offset")==null?1:Integer.parseInt(req.getParameter("pager_offset")); //获取页面传过来的页码	
		}catch(NumberFormatException e){
			pager_offset = 1;//如果页面过来的页面不是数字，则默认1
		}
	    if(EXCEL.equals(operate)){
			pager_offset=1;
		}
	    //时间格式验证不通过
	    if(!DATE_PATTERN.matcher(startTime==null?"":startTime).matches()){
			startTime = null;
		}
	    
	    if(!DATE_PATTERN.matcher(endTime==null?"":endTime).matches()){
			endTime = null;
		}
	   
	    Date sDate=null;
	    Date eDate=null;
	    if(null != startTime &&  !"".equals(startTime)  && !"null".equals(startTime) ){
	    	sDate = FormatDate.formatStr(startTime);
	    }
	    if(null != endTime && !"".equals(endTime)&& !"null".equals(endTime) ){
	    	eDate =  FormatDate.getNextDay(FormatDate.formatStr(endTime));
	    }
	    
	    if(sDate!=null && eDate!=null){
	    
		    //如果开始时间等于结束时间，则查询以开始时间为准的指定日的数据
		    if(sDate.getTime() == eDate.getTime()){
		    	eDate = FormatDate.getNextDay(sDate);
		    }
		    
		    //如果开始时间小于结束时间，则查询以开始时间为准的指定日的数据
		    if(sDate.getTime()>eDate.getTime()){
		    	eDate = FormatDate.getNextDay(sDate);
		    }
		    
		    //如果结束时间 - 开始时间 > MAX_MONTH个月份的时间
		    if(eDate.getTime()>FormatDate.getDateTimeAfterSeveralMonth(sDate, MAX_MONTH)){
		    	eDate = FormatDate.getDateAfterSeveralMonth(sDate, MAX_MONTH);
		    }
	    
	    }
	    
	    
		if(!checkQueryPro(queryPro)){
			//如果不符合规则，则默认最近一周
			queryPro = "lastestMonth";
		}
		if(queryPro.equals("lastestHalfYear")){//最近半年
			sDate = FormatDate.formatStr(FormatDate.getLastestMonth(6));
			eDate = new Date();
		}
		
		else if(queryPro.equals("lastestThreeMonth")){//最近三个月
			sDate = FormatDate.formatStr(FormatDate.getLastestMonth(3));
			eDate = new Date();
		}
		
		else if(queryPro.equals("lastestMonth")){//最近一个月
			sDate = FormatDate.formatStr(FormatDate.getLastestMonth(1));
			eDate = new Date();
		}
		
		else if(queryPro.equals("lastestWeek")){//最近一周
			sDate = FormatDate.formatStr(FormatDate.getLastestWeek(1));
			eDate = new Date();
		}
		else if(queryPro.equals("currentMonth")){//本月
			sDate = FormatDate.formatStr(FormatDate.getMonthFirstDay());//本月的第一天 例：2010-10-1 00:00:00
			eDate = FormatDate.formatStr(FormatDate.getNextMonthFirstDay());//下个月的第一天 例：2010-11-1 00:00:00
		}
		else if(queryPro.equals("currentDay")){//本日
			sDate = FormatDate.formatStr(FormatDate.getDay());//今天开始: 2010-10-11 00:00:00
			eDate = FormatDate.formatStr(FormatDate.getTomorrow());//明天开始:2010-10-12 00:00:00
		}
		
		
		MaSumDto sum = null;
		PageUtil pu = null;
		List<QueryBalanceDto> qbd = null;
		
		//交易类型是否为空
		if(StringUtils.isEmpty(dealType)){
			dealType = DEFAULT_VAL;
		}
		
		//交易类型格式是否匹配
		if(!CheckUtil.checkFundTrace(dealType)){
			dealType = DEFAULT_VAL;
		}
		
		//资金流向是否为空
		if(StringUtils.isEmpty(fundTrace)){
			fundTrace = DEFAULT_VAL;
		}
		
		//资金流向格式是否正确
		if(!CheckUtil.isNumber(fundTrace)){
			fundTrace = DEFAULT_VAL;
		}
		
		//排序字符串是否匹配
		if(!checkOrderStr(orderStr)){
			orderStr = DEFAULT_ORDER;
		}
		
		String[] orderIndexs = orderStr.split(",");
		String[][] orderArray = new String[1][2];
		int i = Integer.parseInt(orderIndexs[0]);
		int ii = Integer.parseInt(orderIndexs[1]);
		orderArray[0] = ORDER_COLUMNS[i][ii];

		String queryDealType = null;
		Acct acct = acctService.getByMemberCode(Long.valueOf(memberCode),accountType);
		
		//订单号
		sum = queryBalanceService.queryHistoryBusinessSum(sDate, eDate,acct,dealType,null,payNo,null);
		
		if(EXCEL.equals(operate) && sum.getCount()==0){
			page_size = 5;
			mv = new ModelAndView(queryBalance);
			mv.addObject("script","<script type='text/javascript'>$.pay.alert('你选择下载的记录为0条，请选择过滤条件,重新查询');</script>");
		}else{
		
			if(EXCEL.equals(operate)){
				page_size = (sum==null?0:sum.getCount());//((sum==null?0:sum.getCount())>MAX_DOWNLOAD_COUNT?MAX_DOWNLOAD_COUNT:(sum==null?0:sum.getCount()));
			}
			
			mv = new ModelAndView(view);
			
			//如果是下载EXCEL动作，并且总数据条数大于阀值
			if(EXCEL.equals(operate) && MAX_DOWNLOAD_COUNT<page_size){
				page_size = 5;
				mv = new ModelAndView(queryBalance);
				mv.addObject("script","<script type='text/javascript'>$.pay.alert('你选择下载的记录已经超过"+MAX_DOWNLOAD_COUNT+"条，请选择过滤条件,重新查询');</script>");
			}else{
				mv = new ModelAndView(view);
			}
		
		}
		
		pu=new PageUtil(pager_offset,page_size,sum==null?0:sum.getCount());//分页处理
		
		/**
		 * 查询余额
		 */
		qbd = queryBalanceService.queryBalanceList(sDate, eDate, acct,queryDealType,null ,pager_offset,page_size,DEFAULT_COLUMNS,payNo, queryDealType);
		

		if(sum==null){
			sum = new MaSumDto();
		}
		
		//end
		
		
		/**
		 * 返回页面痕迹、查询结果
		 */
		mv.addObject("stValue",startTime);
		mv.addObject("eValue",endTime);
		mv.addObject("dealType",dealType);
		mv.addObject("fundTrace",fundTrace);
		mv.addObject("order",orderStr);
		mv.addObject("qValue",queryPro);
		mv.addObject("dealTypeList",PayForEnum.SEARCH_TYPES);
		mv.addObject("accountTypes",accountTypeService.findAccountTypeByType(1));
		mv.addObject("accountType",accountType);
		mv.addObject("qbdList",qbd);
		mv.addObject("sum",sum);
		mv.addObject("canDownload",String.valueOf(MAX_DOWNLOAD_COUNT>=pu.getTotalCount()));
		mv.addObject("maxSize",MAX_DOWNLOAD_COUNT);
		mv.addObject("pu",pu);
		mv.addObject("sDate",sDate);
		mv.addObject("eDate",eDate);
		mv.addObject("nowDate",new Date());
		mv.addObject("userName",userName);
		return mv;
	}
	
	/**
	 * 导出excle文件的方法
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ModelAndView excelBalance(HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		
		
		req.setAttribute("operate", "excel");
		Date nowDate =  FormatDate.formatStr(FormatDate.getDay());
		
		ModelAndView view = null;
		
		view = queryBalanceMethod(req,resp,excleBalance).addObject("nowDate",nowDate);
		
		Object script = view.getModelMap().get("script");
		if(script==null){
			resp.setHeader("Expires", "0");
			resp.setHeader("Pragma" ,"public");
			resp.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
			resp.setHeader("Cache-Control", "public");
	
			resp.setContentType("application/vnd.ms-excel;charset=utf-8");
			resp.setHeader("Content-Disposition", "attachment;filename="
					+ new String(("balance.xls").getBytes("UTF-8"),
							"ISO8859_1"));
			
			return view;
		}else{
			return view;
		}
	}
	

	
	private boolean checkOrderStr(String orderStr){
		if(orderStr==null){
			return false;
		}
		
		return ORDER_STR_PATTERN.matcher(orderStr).matches();
	}
	
	private boolean checkQueryPro(String queryPro){
		if(null==queryPro){
			return false;
		}
	
		return QUERY_PRO_PATTERN.matcher(queryPro).matches();
	}
	
	/************set****************/
	
	public void setQueryBalance(String queryBalance) {
		this.queryBalance = queryBalance;
	}

	public void setExcleBalance(String excleBalance) {
		this.excleBalance = excleBalance;
	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}
	public void setAccountTypeService(AccountTypeService accountTypeService) {
		this.accountTypeService = accountTypeService;
	}
	public void setQueryBalanceService(QueryBalanceService queryBalanceService) {
		this.queryBalanceService = queryBalanceService;
	}
	
	
}
