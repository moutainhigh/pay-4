package com.pay.app.controller.base.queryhistory;

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
import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.facade.dto.MaSumDto;
import com.pay.app.facade.dto.QueryBalanceDto;
import com.pay.base.common.enums.ScaleEnum;
import com.pay.base.model.Acct;
import com.pay.base.model.AcctInfo;
import com.pay.base.model.EnterpriseBase;
import com.pay.base.service.acct.AccountTypeService;
import com.pay.base.service.acct.AcctService;
import com.pay.base.service.enterprise.EnterpriseBaseService;
import com.pay.base.service.queryhistory.QueryBalanceService;
import com.pay.util.DateUtil;
import com.pay.util.FormatDate;
import com.pay.util.StringUtil;

/**
 * @author lei.jiangl 
 * @version 
 * @data 2010-7-28 下午03:25:15
 * 账户余额明细
 */
public class QueryBalanceController extends MultiActionController{
	/**
	 * 账户余额明细
	 */
	private String queryBalance;
	
	private String corpQueryBalance;
	
	private QueryBalanceService queryBalanceService;
	
	private AccountTypeService accountTypeService;
	
	private AcctService acctService;
	

	 /** 企业会员基本信息服务 */
    private EnterpriseBaseService  enterpriseBaseService;
	
	private String excleBalance;
	
	private String corpExcelBalance;
	
	private static Pattern datePattern = Pattern.compile("^(19|20)\\d{2}-(0?\\d|1[012])-(0?\\d|[12]\\d|3[01])$");
	private static Pattern lngDatePattern = Pattern.compile("^(19|20)\\d{2}-(0?\\d|1[012])-(0?\\d|[12]\\d|3[01]) (20|21|22|23|[0-1]?\\d):[0-5]?\\d$");
	private static String pattenDateTime = "yyyy-MM-dd HH:mm";
	
	private static final String EXCEL = "excel";
	
	private static final int MAX_DOWNLOAD_COUNT = 20000;
	
	private static final PayForEnum DEFAULT_VAL = PayForEnum.ALL_SEARCH_TYPE;
	
	private static final String[][] DEFAULT_COLUMNS = new String[][]{{"bdeal_id","desc"}};
	
	
	public ModelAndView index(HttpServletRequest req,
			HttpServletResponse resp) throws Exception{
		return this.queryBalance(req,resp);
	}
	/**
	 * 账户余额查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	//20160423 Mack MPS3期 权限控制改为菜单控制，移除注解控制 @OperatorPermission(operatPermission = "OPERATOR_ACCOUNT_QUERYBALANCE")
	public ModelAndView queryBalance(HttpServletRequest req,
			HttpServletResponse resp) throws Exception{
		LoginSession loginSession = SessionHelper.getLoginSession();

		if(loginSession.getScaleType()==ScaleEnum.INDIVIDUAL.getValue()){
			return this.queryBalanceMethod(req,resp,queryBalance);
		}
		return this.queryBalanceMethod(req,resp,corpQueryBalance);
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
			HttpServletResponse resp,String view) throws Exception{
		Map<String, Object> model = new HashMap<String, Object>();
		//获取保证金查询标示的请求参数
		String marginFlag =  req.getParameter("margin");	
		
		Integer margin;
		if(marginFlag!=null){
			 margin=Integer.valueOf(marginFlag);
			 model.put("dealTypeList",PayForEnum.MARGIN_SEARCH_TYPES);
		}else{
			model.put("dealTypeList",PayForEnum.SEARCH_TYPES);
			margin=0;
		}
		
		ModelAndView mv =  null;
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();
		String userName = loginSession.getLoginName();
		
		/**
		 * 页面条件:开始时间、结束时间、资金流向、明细维度
		 */
		String startTime = ServletRequestUtils.getStringParameter(req, "startTime");
		String endTime = ServletRequestUtils.getStringParameter(req, "endTime");
		
		String dealType =  req.getParameter("dealType");	//交易类型
		//String queryPro = req.getParameter("queryPro");
		String acctCode = req.getParameter("acctCode");	
		
		String payNo = req.getParameter("payNo");			//交易流水号
		
		String merchantOrderId =req.getParameter("merchantOrderId");
		model.put("maxSize",MAX_DOWNLOAD_COUNT);			//最大页数
		
		
		model.put("acctCode",acctCode);
		if(acctCode!=null){
			//获取acctcode对应的币种 by tom.wang 2016年5月10日13:33:03
			model.put("currencyCode", AcctTypeEnum.getAcctCurrencyByCode(Integer.valueOf(acctCode.substring(acctCode.length()-3, acctCode.length()))));
		}		
		model.put("payNo", payNo);
		model.put("margin",margin);		
		//如果页面时间为空
		//指定当前时间为最大值
		String maxDate = DateUtil.formatDateTime("yyyy-MM-dd");
		String maxEndDate = maxDate;//最大结束时间，个人版为 maxDate
		String minDate = FormatDate.formatDate(FormatDate.getDateLastestMonth(6),"yyyy-MM-dd");//DateUtil.skipDate( maxDate,-180);/6个月;
		if(corpQueryBalance.equals(view)){
			String suffer = " 00:00"; 
			maxDate = DateUtil.formatDateTime("yyyy-MM-dd",DateUtil.skipDateTime(new Date(),1))+ suffer;
			//最大的结束时间，企业为maxDate + 1 天
			maxEndDate = FormatDate.formatDate(FormatDate.getDateTime(new Date(),1,0,0),"yyyy-MM-dd")+ suffer;
			minDate = FormatDate.formatDate(FormatDate.getDateLastestMonth(6),"yyyy-MM-dd")+ suffer;//DateUtil.skipDate( maxDate,-180);/6个月;
		}
		model.put("maxDate", maxEndDate);
		model.put("minDate", minDate);
		
		//查询企业会员信息
	    // 企业会员基本信息
	    EnterpriseBase enterpriseBase = enterpriseBaseService.findByMemberCode(Long.valueOf(memberCode));
	    model.put("enterpriseBase", enterpriseBase);
	    
	    //获取商户名下所有的交易账户
	    List<AcctInfo> acctList =null; 
	    
	    Map<String,List<AcctInfo>> acctMap = acctService.getAcctInfByMemberCode(Long.valueOf(memberCode));
	    
	    if(acctMap!=null&&acctMap.containsKey("all")){
	    	 acctList = acctMap.get("all");
	    	 model.put("acctList",acctList);
	    }
		if(StringUtil.isEmpty(dealType)& StringUtil.isEmpty(endTime) && StringUtil.isEmpty(merchantOrderId)){
			if(corpQueryBalance.equals(view)){
				startTime=FormatDate.skipDateTime(maxDate, -3);
			}else{
				startTime=DateUtil.skipDate(maxDate, -3);
			}
				endTime=maxDate;
				model.put("stValue",startTime);
				model.put("eValue",endTime);
			return new ModelAndView(view,model);
		}
		
		Object operate = req.getAttribute("operate");
		
		/**
		 * 页面条件:开始时间、结束时间、资金流向、明细维度
		 */
		int pager_offset=1;  //当前页码
		int page_size=20;     //每页条数
		try{
			pager_offset=req.getParameter("pager_offset")==null?1:Integer.parseInt(req.getParameter("pager_offset")); //获取页面传过来的页码	
		}catch(NumberFormatException e){
			pager_offset = 1;//如果页面过来的页面不是数字，则默认1
		}
	    if(EXCEL.equals(operate)){
			pager_offset=1;
		}
	    
	   
	    Date sDate=null;
	    Date eDate=null;
	    if(!StringUtil.isEmpty(startTime)){
	    	sDate = DateUtil.parse(DateUtil.DEFAULT_DATE_FROMAT, startTime+":00");
	    }
	    if(!StringUtil.isEmpty(endTime)){
	    	eDate = DateUtil.parse(DateUtil.DEFAULT_DATE_FROMAT, endTime+":00");
	    }
	    
	    if(sDate!=null && eDate!=null){
	    
		    //如果开始时间等于结束时间，则查询以开始时间为准的指定日的数据
	    	//个人页面的时候会把日期变到 23:59:59
		    if(queryBalance.equals(view) && sDate.getTime() == eDate.getTime() ){
		    	eDate = FormatDate.getLastestTimeOfDay(sDate);
		    }
		    
		    //如果开始时间大于结束时间，则查询以开始时间为准的指定日的数据
		    if(sDate.getTime()>eDate.getTime()){
		    	eDate = FormatDate.getLastestTimeOfDay(sDate);
		    }
		    
		    //如果开始时间超过6个月或者结束时间大于今天(加5分钟）直接返回。
		    if(sDate.before(FormatDate.formatStr(minDate)) || eDate.after(FormatDate.getDateTime(FormatDate.getLastestTimeOfDay(new Date()),1,0,0))){
		    	
		    	startTime=DateUtil.skipDate(maxDate, -3);
				endTime=maxDate;
				model.put("stValue",startTime);
				model.put("eValue",endTime);
				model.put("errorMsg", "查询时间跨度只能从： " + minDate + " 到 " + maxEndDate);
				return new ModelAndView(view,model);
		    }
	    }
	    
		
		MaSumDto sum = null;
		PageUtil pu = null;
		List<QueryBalanceDto> qbd = null;
		
		String queryDealType = null;
		if(!StringUtil.isEmpty(dealType)){
			queryDealType = PayForEnum.get(Integer.parseInt(dealType)).getGroupValue();
			//对于拒付扣款－》特殊code给于特殊处理
			if(StringUtils.isNotEmpty(queryDealType)){
				if("6034678".equals(queryDealType)){
					queryDealType = "603,604,606,607,608" ;
				}else if("60259".equals(queryDealType)){
					queryDealType = "602,605,609" ;
				}
			}
		}

		Acct acct = null;
		
		if(acctCode!=null&&acctCode.length()>0){
			acct = new Acct();
			acct.setAcctCode(acctCode);
		}

		/**
		 * 查询统计
		 */
		sum = queryBalanceService.queryHistoryBusinessSum(sDate, eDate,acct,queryDealType,null,payNo,merchantOrderId);
		
		if(EXCEL.equals(operate) && sum.getCount()==0){
			page_size = 20;
			
			if(SessionHelper.isCorpLogin()){
				mv=new ModelAndView(corpQueryBalance);
			}else{
				mv=new ModelAndView(queryBalance);
			}
			model.put("script","你选择下载的记录为0条，请选择过滤条件,重新查询");
		}else{
		
			if(EXCEL.equals(operate)){
				page_size = (sum==null?0:sum.getCount());//((sum==null?0:sum.getCount())>MAX_DOWNLOAD_COUNT?MAX_DOWNLOAD_COUNT:(sum==null?0:sum.getCount()));
			}
			
			//如果是下载EXCEL动作，并且总数据条数大于阀值
			if(EXCEL.equals(operate) && MAX_DOWNLOAD_COUNT<page_size){
				if(SessionHelper.isCorpLogin()){
					mv=new ModelAndView(corpQueryBalance);
				}else{
					mv=new ModelAndView(queryBalance);
				}
				page_size = 20;
				model.put("script","你选择下载的记录已经超过"+MAX_DOWNLOAD_COUNT+"条，请选择过滤条件,重新查询");
			}else{
				mv = new ModelAndView(view,model);
			}
		
		}
		
		pu=new PageUtil(pager_offset,page_size,sum==null?0:sum.getCount());//分页处理
		
		/**
		 * @author yx.chen 
		 * @version 
		 * @data 2015-10-22 下午15:25:15
		 * 查询余额
		 */
		qbd = queryBalanceService.queryBalanceList(sDate, eDate, acct,queryDealType,null ,pager_offset,page_size,DEFAULT_COLUMNS,payNo,merchantOrderId);
		if(sum==null){
			sum = new MaSumDto();
		}
		
		//end
		
		
		/**
		 * 返回页面痕迹、查询结果
		 */
		model.put("stValue",startTime);
		model.put("eValue",endTime);
		if(!StringUtil.isEmpty(dealType)){
			model.put("dealType",Integer.valueOf(dealType));
		}
		
		model.put("merchantOrderId", merchantOrderId);
		//交易流水
		model.put("payNo", payNo);
		//model.put("dealTypeList",PayForEnum.SEARCH_TYPES);
		
		//如果是企业账户或者个人卖家账户
		if(SessionHelper.isCorpLogin()){
			model.put("accountTypes",accountTypeService.findAccountTypeByType(1));
		}
		model.put("qbdList",qbd);
		model.put("sum",sum);
		model.put("canDownload",String.valueOf(MAX_DOWNLOAD_COUNT>=pu.getTotalCount()));
		model.put("maxSize",MAX_DOWNLOAD_COUNT);
		model.put("pu",pu);
		model.put("sDate",sDate);
		model.put("eDate",eDate);
		model.put("nowDate",new Date());
		model.put("userName",userName);
		model.put("payNo", payNo);
		model.put("merchantOrderId", merchantOrderId);
		return mv.addAllObjects(model).addObject("payNo", payNo);
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
		LoginSession loginSession = SessionHelper.getLoginSession();
		//获取保证金查询标示的请求参数 by tom.wang
		String marginFlag =  req.getParameter("margin");	
		String acctName =  req.getParameter("acctName");
		if(StringUtil.isEmpty(acctName)){
			acctName="";
		}
		Integer margin;
		if(marginFlag!=null){
			 margin=Integer.valueOf(marginFlag);
		}else{
			margin=0;
		}
		
		ModelAndView view = null;
		      
		if(SessionHelper.isCorpLogin()){
			view = queryBalanceMethod(req,resp,corpExcelBalance).addObject("nowDate",nowDate).addObject("acctName",acctName);
		}else{      
			view = queryBalanceMethod(req,resp,excleBalance).addObject("nowDate",nowDate);
		}
		
		Object script = view.getModelMap().get("script");
		if(script==null){
			resp.setHeader("Expires", "0");
			resp.setHeader("Pragma" ,"public");
			resp.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
			resp.setHeader("Cache-Control", "public");
	
			resp.setContentType("application/vnd.ms-excel;charset=utf-8");
			//区分余额下载和保证金下载  修改下载文件的名称by tom.wang
			if(margin!=1){
				resp.setHeader("Content-Disposition", "attachment;filename="
						+ new String(("基本账户余额明细列表.xls").getBytes("UTF-8"),
								"ISO8859-1"));
			}else{				
				resp.setHeader("Content-Disposition", "attachment;filename="
						+ new String(("保证金账户余额明细列表.xls").getBytes("UTF-8"),
								"ISO8859-1"));
			}
			return view;
		}else{
			return view;
		}
	}
	
	/************set****************/
	
	public void setQueryBalance(String queryBalance) {
		this.queryBalance = queryBalance;
	}
	

	public void setExcleBalance(String excleBalance) {
		this.excleBalance = excleBalance;
	}
	public void setCorpQueryBalance(String corpQueryBalance) {
		this.corpQueryBalance = corpQueryBalance;
	}
	public void setCorpExcelBalance(String corpExcelBalance) {
		this.corpExcelBalance = corpExcelBalance;
	}
	public void setQueryBalanceService(
			QueryBalanceService queryBalanceService) {
		this.queryBalanceService = queryBalanceService;
	}
	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}
	public void setAccountTypeService(AccountTypeService accountTypeService) {
		this.accountTypeService = accountTypeService;
	}
	public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}
	
}
