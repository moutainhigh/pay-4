/**
 *  File: UpdateAccountInfo.java
 *  Description:
 *  Copyright © 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-23   single_zhang     Create
 *
 */
package com.pay.app.controller.base.accountactive;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.exception.MaAccountQueryUntxException;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.app.base.api.common.constans.CutsConstants;
import com.pay.app.base.api.common.enums.BspIdentityEnum;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.facade.cidverify.CidVerify2GovServiceFacade;
import com.pay.app.facade.dto.MaSumDto;
import com.pay.app.service.announcement.AnnouncementService;
import com.pay.base.dto.MemberCriteria;
import com.pay.base.dto.MemberDto;
import com.pay.base.model.Acct;
import com.pay.base.model.AcctInfo;
import com.pay.base.model.EnterpriseBase;
import com.pay.base.model.EnterpriseContract;
import com.pay.base.model.UserLog;
import com.pay.base.service.acct.AcctService;
import com.pay.base.service.enterprise.EnterpriseBaseService;
import com.pay.base.service.enterprise.EnterpriseRegisterService;
import com.pay.base.service.matrixcard.IMatrixCardService;
import com.pay.base.service.member.MemberService;
import com.pay.base.service.operator.OperatorServiceFacade;
import com.pay.base.service.queryhistory.QueryBalanceService;
import com.pay.base.service.user.UserLogService;
import com.pay.base.service.usercheck.UserCheckService;
import com.pay.fi.dto.Currency;
import com.pay.fi.dto.PartnerSettlementOrder;
import com.pay.fi.dto.QueryDetailDTO;
import com.pay.fi.dto.QueryDetailParaDTO;
import com.pay.fi.hession.SettlementOrderService;
import com.pay.fi.service.OrderQueryService;
import com.pay.util.DateUtil;
import com.pay.util.FormatDate;
import com.pay.util.FormatNumber;
import com.pay.util.JSonUtil;

/**
 * 
 */

public class MyAccountInfoController extends MultiActionController   {

	/**
	 * 我的账户首页
	 */
	private String accountIndex;
    private String corpAccountIndex;
    private String corpAccountDetail;
    /** 账户信息服务 */
	private AcctService acctService;
	
    /**
	 * 账户服务
	 */
    private MemberService baseMemberService;
	

    /**
	 *  账户关联查询
	 */
	private UserCheckService userCheckService;

    private OrderQueryService  orderQueryService;
	/**
	 * 实名认证服务
	 */
	private CidVerify2GovServiceFacade verify2GovService;
	/**
	 * 口令卡的services
	 */
	private IMatrixCardService matrixCardService;
	/** 操作员服务 */
	private OperatorServiceFacade operatorServiceFacade;
	 /** 企业会员基本信息服务 */
    private EnterpriseBaseService  enterpriseBaseService;
	/**用户日志*/
    private UserLogService userLogService;
   
	private AnnouncementService announcementService;
    
   private QueryBalanceService queryBalanceService;
   
   private AccountQueryService accountQueryService;
   
   private EnterpriseRegisterService enterpriseRegisterService;
   
//   private TradeOrderService tradeOrderService;
   private String corpOperAccountIndex ;
   
   private SettlementOrderService settlementOrderService;
   
   private  final static BigDecimal zeroBalance =new BigDecimal(0);
   
   /**
    * 获取账户详细信息
    * 根据账户
    * @param request
    * @param response
    * @return
    */
   public ModelAndView acctDetail(HttpServletRequest request,
			HttpServletResponse response){
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = null == loginSession.getMemberCode()
				? ""
				: loginSession.getMemberCode();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String acctCode  =request.getParameter("acctCode");
		
		AcctInfo info = acctService.getAcctInfByAcctCode(acctCode);
		
		BigDecimal blance = info.getBalance().multiply(new BigDecimal("0.001"));
		info.setBalance(blance);
		
		paraMap.put("acctInfo",info);
		
		if(info.getIsFrozenAcct()==1){
			BigDecimal depBalance = info.getDepBalance();
			if(depBalance!=null){
				BigDecimal depBalance_ = depBalance.multiply(new BigDecimal("0.001"));
				info.setDepBalance(depBalance_);
			}
			
		}
		
		
		ModelAndView mv = new ModelAndView(corpAccountDetail, paraMap);
	    return mv;
   }
   
   
	/**
	 * 进入我的账户首页
	 */
   //@OperatorPermission(operatPermission = "OPERATOR_ACCOUNT_INDEX")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String toDrawPwd = request.getParameter("toDrawPwd") == null ? "" : request.getParameter("toDrawPwd") ;
		
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = null == loginSession.getMemberCode()
				? ""
				: loginSession.getMemberCode();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String userName = loginSession.getLoginName();
		
		// 判断用户支付密码是否设置
		boolean bool = acctService.isHavePayPwd(Long.valueOf(
				memberCode),  AcctTypeEnum.BASIC_CNY.getCode());
		if (bool) {
			paraMap.put("paypwdState", MessageConvertFactory
					.getMessage("seted"));// 设置
			paraMap.put("paypwdState1", MessageConvertFactory
					.getMessage("change"));// 操作类型为修改
			paraMap.put("paypwdurl", "update");
			paraMap.put("found", MessageConvertFactory.getMessage("found"));
		} else {
			paraMap.put("paypwdState", MessageConvertFactory
					.getMessage("unset"));// 未设置
			paraMap
					.put("paypwdState1", MessageConvertFactory
							.getMessage("set"));// 操作类型为设置
			paraMap.put("paypwdurl", "set");

		}
		//判断用户口令卡是否绑定
		boolean isBind = false;//matrixCardService.isBindByMemberCode(Long.valueOf(memberCode));
		paraMap.put("isBind",isBind);
		paraMap.put("memberCode",memberCode);
		// 获取会员账户信息
		Acct acct = acctService.getByMemberCode(Long.valueOf(memberCode),  AcctTypeEnum.BASIC_CNY.getCode());
		BalancesDto balanceDto = null;
		try{
			balanceDto = accountQueryService.doQueryBalancesNsTx(Long.valueOf(memberCode),  AcctTypeEnum.BASIC_CNY.getCode());
		}catch(MaAccountQueryUntxException mque){
			mque.printStackTrace();
		}
		//查询今日交易
		

		BigDecimal balance = new BigDecimal(acct == null ? 0L : (balanceDto==null?0L:balanceDto.getBalance()));
		BigDecimal withdrawBalance =new BigDecimal(acct == null ? 0L : (balanceDto==null?0L:balanceDto.getWithdrawBalance()));
		BigDecimal freezeBalance = new BigDecimal(acct == null ? 0L :(balanceDto==null?0L:balanceDto.getFrozeAmount()));
		
		String createDate = "";
		
		
		List list = announcementService.queryTopAnnouncement(4);
		
		paraMap.put("ggls",list);
		paraMap.put("withdrawBalance", FormatNumber.decimalsRound(withdrawBalance.divide(new BigDecimal(1000)), 2)); // 可提现金额
		paraMap.put("balance", FormatNumber.decimalsRound(balance.divide(new BigDecimal(1000)), 2)); // 余额
		paraMap.put("Freeze", FormatNumber.decimalsRound(freezeBalance.divide(new BigDecimal(1000)), 2)); // 冻结余额
		//List<MaSumDto>  sumList= queryBalanceService.queryPayMentSumList(acct.getAcctCode());
		//paraMap.put("sumList", sumList);
		
		
		//modify by lei.jiangl 增加账户是否通过实名认证的信息
		boolean boolvfy = verify2GovService.checkQueryCidVerify(loginSession.getMemberCode());
		ModelAndView mv = new ModelAndView(accountIndex, paraMap);

		paraMap.put("memberType",loginSession.getScaleType());
		
		//查询最后一次登录是否失败
		UserLog log = userLogService.getLastLog(userName);
		if (log != null) {
			if (log.getLogType()==CutsConstants.USER_LOG_LOGIN_FAILED) {
				paraMap.put("log", log);
			}
		}
		
		/** 获取会员基本信息 */
		if(SessionHelper.isCorpLogin()){ //企业会员
			//--------------------------peiyu.yang added----------------------
			//获取商户名下所有的账户信息
			
			Map<String,List<AcctInfo>> acctInfoMap = 
					            acctService.getAcctInfByMemberCode(Long.valueOf(memberCode));
			List<AcctInfo> acctList = null;
			
			QueryDetailParaDTO qdpDTO = new QueryDetailParaDTO();
			qdpDTO.setMemberCode(memberCode);
			Map<String,BigDecimal> tocList = orderQueryService.queryTradeOrderCount(qdpDTO);
			
			//如何acctInfoMap 中有元素的话，先取出整集
			if(acctInfoMap!=null&&acctInfoMap.size()>0){
				acctList = acctInfoMap.get("all");
				paraMap.put("acctList",acctList);
			}
			
			acctInfoMap.remove("all");
			
            //统计清算金额(包括清算和未清算)
			PartnerSettlementOrder pso = new PartnerSettlementOrder();
			pso.setPartnerId(Long.valueOf(memberCode));
			

			Map<String,PartnerSettlementOrder> parMap= settlementOrderService.settlementCoutQuery(pso);
					               
			AcctInfo acctCny = acctInfoMap.get("CNY").get(0);
			
			String keyCny = "CNY-1";//
			BigDecimal amountCny =new BigDecimal(0);
			if(parMap!=null&&parMap.size()>0){
				PartnerSettlementOrder orderCny = parMap.get(keyCny);
				amountCny = orderCny==null?new BigDecimal(0):orderCny.getAmount();
			}
			
			acctCny.setSettBalance(amountCny.multiply(new BigDecimal("0.001")));
			//取出默认的人民币账户
			paraMap.put("cny",acctCny);
			//如果有美元账户也取出来,没有就算了
			if(acctInfoMap.containsKey("USD")){
				 AcctInfo acctUsd = acctInfoMap.get("USD").get(0);
				 String keyUsd = "USD-1";
				 BigDecimal amountUsd =new BigDecimal(0);
				 if(parMap!=null&&parMap.size()>0){
					 PartnerSettlementOrder orderUsd = parMap.get(keyUsd);
					 amountUsd = orderUsd==null?new BigDecimal(0):orderUsd.getAmount();
				 }
				 //设置账户已清算金额
				 acctUsd.setSettBalance(amountUsd.multiply(new BigDecimal("0.001")));
			     paraMap.put("usd",acctUsd);
			}
			//----------------------------------------------------------------------------------
			System.out.println("----------------------------------------------------------------");
			Set<Entry<String, Object>> entrySet = paraMap.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				System.out.println(entry.getKey());
				System.out.println(entry.getValue());
			}
			System.out.println("----------------------------------------------------------------");
			//----------------------------------------------------------------------------------
		    List<AcctInfo> atiList  = new ArrayList<AcctInfo>();
		    
		    Map<String,Currency> curyMap = Currency.getCurrencyMap();
			
		    Iterator<String> it = curyMap.keySet().iterator();
		    
		    while(it.hasNext()){
		    	String key = it.next();
		    	Currency currency = curyMap.get(key);
		    	
		    	AcctInfo ai = new AcctInfo();
		    	if(parMap!=null&&parMap.size()>0){
		    		//未清算标志
		    		String keyTmp = currency.getCode()+"-0";
		    		PartnerSettlementOrder orderTmp = (parMap==null||parMap.size()<=0)?null:parMap.get(keyTmp);
		    		
		    		BigDecimal setteAmount = orderTmp==null?new BigDecimal(0):orderTmp.getAmount();
		    		ai.setNoSettBalance(setteAmount.multiply(new BigDecimal("0.001")));
		    		ai.setAcctName(currency.getName());
		    	}
				 
				 if(tocList!=null&&tocList.size()>0){
					 BigDecimal tradeAmount = tocList.get(currency.getCode());
					 BigDecimal amount= new BigDecimal(0.00);
					 //没有交易金额
					 if(tradeAmount!=null){
						 amount = tradeAmount.multiply(new BigDecimal(0.001));
					 }
                    ai.setTotalTradeAmount(amount);
                    
				 }
				 
				ai.setAcctName(currency.getName());
		    	atiList.add(ai);
		    }
		    
		    paraMap.put("atiList",atiList);

			QueryDetailParaDTO qDTO = new QueryDetailParaDTO();
			qDTO.setEndTime(getEndTime());
			qDTO.setStartTime(getStartTime());
			qDTO.setMemberCode(memberCode);
			List<QueryDetailDTO> incomeDetailList = orderQueryService
					.queryIncomeDetailList(qDTO,1,
							5);
			Map<String, Object> summRes = orderQueryService
					.countIncomeDetailList(qDTO);
			
			qdpDTO.setOrderStatus("4");
			
			Map<String, Object> summRes2 = orderQueryService
					.countIncomeDetailList(qDTO);
            
			paraMap.put("listSize",((Integer) summRes.get("listSize")).intValue());
			paraMap.put("incList", incomeDetailList);
			paraMap.put("listSize1", ((Integer) summRes2.get("listSize")).intValue());
			//-------------------------------------------------------------------------

			Object str = request.getSession().getAttribute("epLastLoginTime");
			if(str != null){
				// 会员上次登录时间
				paraMap.put("dateLine", (String)str);// 上次登录时间
			}
		    MemberDto memberInfo = baseMemberService.findByMemberCode(Long.valueOf(memberCode));
		    paraMap.put("memberInfo", memberInfo);
		    // 获取操作员总数
		    int operatorNum = operatorServiceFacade.queryCountByMemberCode(Long.valueOf(memberCode));
		    paraMap.put("operatorNum", operatorNum-1);
		    // 企业会员基本信息
		    EnterpriseBase enterpriseBase = enterpriseBaseService.findByMemberCode(Long.valueOf(memberCode));
		    paraMap.put("enterpriseBase", enterpriseBase);
		    // 创建时间
		    createDate = FormatDate.formatDate(enterpriseBase.getCreateDate());
		    paraMap.put("createDate", createDate);// 注册时间
		    //操作员名字
		    String operatorIdentity = loginSession.getVerifyName();
            paraMap.put("operatorIdentity", operatorIdentity);
            EnterpriseContract econtract=enterpriseRegisterService.getContractByMemberCode(Long.valueOf(memberCode));
            paraMap.put("econtract", econtract);
            String bindMobile =  operatorServiceFacade.getBindMobileByMeberCodeOperatorId(Long.valueOf(memberCode),loginSession.getOperatorId());
            paraMap.put("bindMobile", bindMobile!=null? bindMobile.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1****$3"):"");
            boolean corpTrading = false;
            if(loginSession.getBspIdentity()==BspIdentityEnum.CORP_TRADING.getIdentity()){
         	   corpTrading = true;
            }
            	//判断是否是交易商
            paraMap.put("corpTrading", corpTrading);
            if(SessionHelper.getOperatorIdBySession()>0){
            	mv = new ModelAndView(corpOperAccountIndex, paraMap);
            }else{
            	if( !"".equals(toDrawPwd)){
            		mv = new ModelAndView(corpAccountIndex, paraMap);
            	}else{
            		mv = new ModelAndView("/wechat/enterprise_account_query", paraMap) ;
            	}
            	
            }
          
            
		} else { // 个人会员
	      
	        MemberCriteria memberCriteria = baseMemberService
	                .queryMemberCriteriaByMemberCodeNsTx(Long.valueOf(memberCode));
	        if (null != memberCriteria) {// modify by lei.jiangl
	            // memberCriteria.getCreateDate=null
	            memberCriteria.setUserName(userName);
	            if (null == memberCriteria.getWelcomeMsg()) {
	                paraMap.put("Salutatory", MessageConvertFactory
	                        .getMessage("set"));
	            } else {
	                paraMap.put("Salutatory", MessageConvertFactory
	                        .getMessage("change"));
	            }
	            if (memberCriteria.getCreateDate() != null) {
	                createDate = FormatDate.formatDate(memberCriteria
	                        .getCreateDate());
	            }
	            

	        }
	        //获取个人会员账户的上次登录时间
	        Object str = request.getSession().getAttribute("epLastLoginTime");
	        if(str != null){
	            paraMap.put("dateLine", str);// 上次登录时间
	        } else {
	            Date nowDate=new Date();
                String lastLoginTime = DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", nowDate);
                request.getSession().setAttribute("epLastLoginTime",lastLoginTime);
                paraMap.put("dateLine", lastLoginTime);// 上次登录时间
	        }
	        if(boolvfy){//已实名认证
				//mv.addObject("qdbool","true");
				paraMap.put("qdbool","true");
			}else{
				//mv.addObject("qdbool","false");
				paraMap.put("qdbool","false");
			}
	        MemberDto memberInfo = baseMemberService.findByMemberCode(Long.valueOf(memberCode));
	        if(memberInfo != null){
	        	paraMap.put("serviceLevelCode", memberInfo.getServiceLevelCode());
	        }
	        paraMap.put("memberCriteria", memberCriteria);
	        paraMap.put("createDate", createDate);// 注册时间
//	        List<TradeOrder> tradeOrderList=tradeOrderService.getTradeOrderbyPayer(Long.valueOf(memberCode));
//	        int tradeCount=tradeOrderService.queryCountByPayer(Long.valueOf(memberCode));
//	        paraMap.put("tradeOrderList", tradeOrderList);
//	        paraMap.put("tradeCount", tradeCount);
	    	mv = new ModelAndView(accountIndex,paraMap);
		}
		
		
		return mv;

	}
	
	
	
	
	
	public void getSumDto(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode()==null? "": loginSession.getMemberCode();
		Acct acct = acctService.getByMemberCode(Long.valueOf(memberCode),  AcctTypeEnum.BASIC_CNY.getCode());
		String result=null;
		if(acct!=null){
			List<MaSumDto>  sumList= queryBalanceService.queryPayMentSumList(acct.getAcctCode());
			result=JSonUtil.toJSonString(sumList);
		}
		PrintWriter out = null;
        out = response.getWriter();
        out.print(result);
        out.flush();
        out.close();
		

	}
	/******** set方法 **********/
	public void setAccountIndex(String accountIndex) {
		this.accountIndex = accountIndex;
	}
	
	public void setCorpAccountIndex(String corpAccountIndex) {
        this.corpAccountIndex = corpAccountIndex;
    }



	public void setUserCheckService(UserCheckService userCheckService) {
		this.userCheckService = userCheckService;
	}
   
	public void setOperatorServiceFacade(OperatorServiceFacade operatorServiceFacade) {
        this.operatorServiceFacade = operatorServiceFacade;
    }
	
	public void setVerify2GovService(CidVerify2GovServiceFacade verify2GovService) {
		this.verify2GovService = verify2GovService;
	}
	
	public void setMatrixCardService(IMatrixCardService matrixCardService) {
		this.matrixCardService = matrixCardService;
	}
	public void setBaseMemberService(MemberService baseMemberService) {
        this.baseMemberService = baseMemberService;
    }
    public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
        this.enterpriseBaseService = enterpriseBaseService;
    }
    public void setAcctService(AcctService acctService) {
        this.acctService = acctService;
    }
	public void setUserLogService(UserLogService userLogService) {
		this.userLogService = userLogService;
	}

	public void setQueryBalanceService(QueryBalanceService queryBalanceService) {
		this.queryBalanceService = queryBalanceService;
	}
	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}
	public void setAnnouncementService(AnnouncementService announcementService) {
		this.announcementService = announcementService;
	}
	public void setEnterpriseRegisterService(
			EnterpriseRegisterService enterpriseRegisterService) {
		this.enterpriseRegisterService = enterpriseRegisterService;
	}
	public void setCorpOperAccountIndex(String corpOperAccountIndex) {
		this.corpOperAccountIndex = corpOperAccountIndex;
	}
	public void setCorpAccountDetail(String corpAccounttDetail) {
		this.corpAccountDetail = corpAccounttDetail;
	}
	
	public void setOrderQueryService(OrderQueryService orderQueryService) {
		this.orderQueryService = orderQueryService;
	}
	
	
	private Date getStartTime(){
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);
		cal.add(Calendar.HOUR_OF_DAY,-hour);
		cal.add(Calendar.MINUTE,-min);
		cal.add(Calendar.SECOND,-sec);
		return cal.getTime();
	}
	
	private Date getEndTime(){
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		cal.add(Calendar.HOUR_OF_DAY,24-hour);
		cal.add(Calendar.MINUTE,-min);
		
		return cal.getTime();
	}


	public void setSettlementOrderService(
			SettlementOrderService settlementOrderService) {
		this.settlementOrderService = settlementOrderService;
	}
	
	
}
