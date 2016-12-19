/**
 *  File: UpdateAccountInfo.java
 *  Description:
 *  Copyright © 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-23   single_zhang     Create
 *
 */
package com.pay.app.controller.base.accountactive;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.constantenum.PseudoAcctTypeEnum;
import com.pay.acc.service.member.MemberProductService;
import com.pay.app.base.api.common.constans.CutsConstants;
import com.pay.app.base.api.common.enums.BspIdentityEnum;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.client.SettleCoreClientService;
import com.pay.app.client.TxncoreClientService;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.controller.base.credit.NumberConstants;
import com.pay.app.facade.cidverify.CidVerify2GovServiceFacade;
import com.pay.app.service.announcement.AnnouncementService;
import com.pay.base.dto.MemberCriteria;
import com.pay.base.dto.MemberDto;
import com.pay.base.fi.model.TransactionSomeDaysSum;
import com.pay.base.fi.model.TransactionYesterdaySum;
import com.pay.base.fi.service.ChargeBackOrderService;
import com.pay.base.fi.service.PartnerSettlementOrderService;
import com.pay.base.fi.service.TradeOrderService;
import com.pay.base.model.EnterpriseBase;
import com.pay.base.model.EnterpriseContract;
import com.pay.base.model.PseudoAcct;
import com.pay.base.model.UserLog;
import com.pay.base.service.acct.AcctService;
import com.pay.base.service.acctatrrib.AcctAttribService;
import com.pay.base.service.enterprise.EnterpriseBaseService;
import com.pay.base.service.enterprise.EnterpriseRegisterService;
import com.pay.base.service.member.MemberService;
import com.pay.base.service.operator.OperatorServiceFacade;
import com.pay.base.service.user.UserLogService;
import com.pay.fi.dto.PartnerSettlementOrder;
import com.pay.util.DateUtil;
import com.pay.util.FormatDate;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 
 */

public class MyAccountInfoController extends MultiActionController   {

	/** 我的账户首页 */
	private String accountIndex;
	private String corpAccountIndex;
	private String corpAccountIndexInfo;
	private String corpAccountDetail;
	/** 账户信息服务 */
	private AcctService acctService;
	/** 账户属性信息服务 */
	private AcctAttribService acctAttribService;
	private MemberService baseMemberService;
	/** 实名认证服务 */
	private CidVerify2GovServiceFacade verify2GovService;
	/** 操作员服务 */
	private OperatorServiceFacade operatorServiceFacade;
	/** 企业会员基本信息服务 */
	private EnterpriseBaseService enterpriseBaseService;
	/** 用户日志 */
	private UserLogService userLogService;
	private AccountQueryService accountQueryService;
	private EnterpriseRegisterService enterpriseRegisterService;
	private TradeOrderService tradeOrderService;
	private String corpOperAccountIndex;
	private String corpOperAccountIndexInfo;
	private PartnerSettlementOrderService partnerSettlementOrderService ;
	private AnnouncementService announcementService ;
	
	private TxncoreClientService txncoreClientService ;
	private ChargeBackOrderService chargeBackOrderService ;
	
	private SettleCoreClientService settleCoreClientService;
	private MemberProductService memberProductService;


public void setMemberProductService(MemberProductService memberProductService) {
		this.memberProductService = memberProductService;
	}

/**
    * 获取账户详细信息
    * 根据账户
    * @param request
    * @param response
    * @return
    */
   public ModelAndView acctDetail(final HttpServletRequest request,
			final HttpServletResponse response){
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = null == loginSession.getMemberCode() ? "" : loginSession.getMemberCode();
		String currency = StringUtil.null2String(request.getParameter("currency")) ;
		//企业会员基本信息
	    EnterpriseBase enterpriseBase = enterpriseBaseService.findByMemberCode(Long.valueOf(memberCode));
		Map<String, Object> paraMap = new HashMap<String, Object>() ;
		this.extractAcctInfo(memberCode, paraMap, currency);
		this.extractPseudoAccts(memberCode, paraMap) ;
		Long sumAmount = this.recSettlementAmount(Long.valueOf(memberCode), currency) ;
		paraMap.put("enterpriseBase", enterpriseBase) ;
		paraMap.put("currency", currency) ;
		paraMap.put("memberCode", memberCode) ;
		paraMap.put("sumAmount", sumAmount) ;
		ModelAndView mv = new ModelAndView(corpAccountDetail).addAllObjects(paraMap) ;
	    return mv;
   }
   
	/**
	 * 进入我的账户首页
	 */
   //@OperatorPermission(operatPermission = "OPERATOR_ACCOUNT_INDEX")
	public ModelAndView index(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String acctInfo = StringUtil.null2String(request.getParameter("info")) ;
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = null == loginSession.getMemberCode() ? "" : loginSession.getMemberCode();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String userName = loginSession.getLoginName();
		// 判断用户支付密码是否设置
		boolean bool = acctService.isHavePayPwd(Long.valueOf(memberCode), AcctTypeEnum.BASIC_CNY.getCode());
		if (bool) {
			paraMap.put("paypwdState", MessageConvertFactory.getMessage("seted"));// 设置
			paraMap.put("paypwdState1", MessageConvertFactory.getMessage("change"));// 操作类型为修改
			paraMap.put("paypwdurl", "update");
			paraMap.put("found", MessageConvertFactory.getMessage("found"));
		} else {
			paraMap.put("paypwdState", MessageConvertFactory.getMessage("unset"));// 未设置
			paraMap.put("paypwdState1", MessageConvertFactory.getMessage("set"));// 操作类型为设置
			paraMap.put("paypwdurl", "set");
		}
		//判断用户口令卡是否绑定
		boolean isBind = false;//matrixCardService.isBindByMemberCode(Long.valueOf(memberCode));
		paraMap.put("isBind",isBind);
		paraMap.put("memberCode",memberCode);
		//------------------------modified by PengJiangbo Sta
		//获取会员伪账户列表
		List<PseudoAcct> pseudoAccts = this.extractPseudoAccts(memberCode, paraMap);
		//获取会员账户信息，默认取出伪账户列表里的第一条
		String currency = "" ;
		if(CollectionUtils.isNotEmpty(pseudoAccts)){
			currency = pseudoAccts.get(0).getCurrency() ;
		}
		this.extractAcctInfo(memberCode, paraMap, currency);
		//取出待清算金额信息
		Long sumAmount = this.recSettlementAmount(Long.valueOf(memberCode), currency) ;
		paraMap.put("sumAmount", sumAmount) ;
		//昨日交易查询
		String startTime = DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", DateUtil.skipDateTime(DateUtil.getTheDate(new Date()), -1)) ;
		String endTime = DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", DateUtil.skipDateTime(DateUtil.getTheDate(new Date()), 0)) ;
		TransactionYesterdaySum transactionYesterdaySum = this.tradeOrderService.queryCountByConditions(startTime, endTime, Long.valueOf(memberCode)) ;
		transactionYesterdaySum = transactionYesterdaySum == null ? new TransactionYesterdaySum() : transactionYesterdaySum ;
		paraMap.put("yesterday", transactionYesterdaySum) ;
		//系统公告
		//Integer countAnnouncement = this.announcementService.countAnnouncement() ;
		//系统公告未读条数
		Map<String, Object> announcementMap = new HashMap<String, Object>() ;
		announcementMap.put("memberCode", memberCode) ;
		Integer countAnnouncement = this.announcementService.countAnnouncement(announcementMap) ;
		paraMap.put("countAnnouncement", countAnnouncement) ;
		//商户未处理调单条数
		Integer countBounced = this.countChargeOrBounced(memberCode, "1,2");
		paraMap.put("countBounced", countBounced) ;
		//未处理拒付条数
		Integer countCharge = this.countChargeOrBounced(memberCode, "0");
		paraMap.put("countCharge", countCharge) ;
		//------------------------modified by PengJiangbo End
		//添加订单授信总笔数和总金额展示 add by tom 2016年6月24日10:18:33
		
		boolean flag=memberProductService.isHaveProduct(Long.valueOf(memberCode), "ordercredit");
		if (flag) {
			// add by mmzhang 添加判断“原清算时间距task执行时间小于2天的订单，不予授信”
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			Calendar c1 = Calendar.getInstance();
			c1.setTime(new Date());
			c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 2);
			String settleStart = df.format(c1.getTime());
			// end by mmzhang add
			PartnerSettlementOrder partnerSettlementOrder = new PartnerSettlementOrder();
			partnerSettlementOrder.setPartnerId(Long.valueOf(memberCode));
			partnerSettlementOrder.setSettlementFlg(NumberConstants.ZERO);// 未结算的
			partnerSettlementOrder.setCreditFlag("3");
			partnerSettlementOrder.setSettleStart(settleStart);
			Map map = settleCoreClientService.settlementOrderQueryForMyAcc(
					partnerSettlementOrder, null);
			List<Map> maplist = (List<Map>) map.get("list");
			List<PartnerSettlementOrder> infoList = MapUtil.map2List(
					PartnerSettlementOrder.class, maplist);
			if (null != infoList && infoList.size() > 0) {
				paraMap.put("totalCredit", infoList.size());
				paraMap.put("totalCreditAmount", getAmount(infoList));
			} else {
				paraMap.put("totalCredit", 0);
				paraMap.put("totalCreditAmount", 0);
			}
		}	
		
		//------------------------modified by ZhangMingming Sta
		//近30日交易量统计
		String startTime2 = DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", DateUtil.skipDateTime(DateUtil.getTheDate(new Date()), -30));
		String endTime2 = DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", DateUtil.skipDateTime(DateUtil.getTheDate(new Date()), 0)) ;
		
		List<TransactionSomeDaysSum> someDaysList=this.tradeOrderService.queryCountBySomeDaysTranSum(startTime2, endTime2, Long.valueOf(memberCode));
		HashMap<String, Long> transumMap= new HashMap<String, Long>();
		for (TransactionSomeDaysSum transactionSomeDaysSum : someDaysList) {
			transumMap.put(transactionSomeDaysSum.getCreateDate(), transactionSomeDaysSum.getTranSum());
		}
		List<String> list= new ArrayList<String>();
		List<Long> sumlist= new ArrayList<Long>();
		
		for(int i=29;i>=0;i=i-4)
		{
			String startTime1 = "'"+DateUtil.formatDateTime("MM-dd",DateUtil.skipDateTime(DateUtil.getTheDate(new Date()), -i))+"'" ;
			list.add(startTime1);
			list.add("''");
			list.add("''");
			list.add("''");
		}
		for(int i=29;i>=0;i--)
		{
			String createDate = DateUtil.formatDateTime("MM-dd",DateUtil.skipDateTime(DateUtil.getTheDate(new Date()), -i)) ;
			Long TranSum=(long) 0;
			if(transumMap.containsKey(createDate))
			{
				TranSum=transumMap.get(createDate);
			}
			sumlist.add(TranSum);
		}
		list.add("''");
		if(list.size()>30)
		{
			list=list.subList(0, 30);
		}
		paraMap.put("dateList", list.toString()) ;
		paraMap.put("sumlist", sumlist.toString()) ;
		//------------------------modified by ZhangMingming End
		
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
		//创建时间
		String createDate = "";
		/** 获取会员基本信息 */
		if(SessionHelper.isCorpLogin()){ //企业会员
			//--------------------------peiyu.yang added----------------------
			
			//----------------------------------------------------------------
			Object str = request.getSession().getAttribute("epLastLoginTime");
			if(str != null){
				// 会员上次登录时间
				paraMap.put("dateLine", str);// 上次登录时间
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
            	if(null != acctInfo && acctInfo.equals("true")){
            		mv = new ModelAndView(corpOperAccountIndexInfo, paraMap) ;
            	}else{
            		//mv = new ModelAndView(corpOperAccountIndex, paraMap);
            		mv = new ModelAndView(corpAccountIndex, paraMap);
            	}
            }else{
            	if(null != acctInfo && acctInfo.equals("true")){
            		mv = new ModelAndView(corpAccountIndexInfo, paraMap) ;
            	}else{
            		mv = new ModelAndView(corpAccountIndex, paraMap);
            	}
            }
		} else { // 个人会员
	        MemberCriteria memberCriteria = baseMemberService.queryMemberCriteriaByMemberCodeNsTx(Long.valueOf(memberCode));
	        if (null != memberCriteria) {// modify by lei.jiangl
	            memberCriteria.setUserName(userName);
	            if (null == memberCriteria.getWelcomeMsg()) {
	                paraMap.put("Salutatory", MessageConvertFactory.getMessage("set"));
	            } else {
	                paraMap.put("Salutatory", MessageConvertFactory.getMessage("change"));
	            }
	            if (memberCriteria.getCreateDate() != null) {
	                createDate = FormatDate.formatDate(memberCriteria.getCreateDate());
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
				paraMap.put("qdbool","true");
			}else{
				paraMap.put("qdbool","false");
			}
	        MemberDto memberInfo = baseMemberService.findByMemberCode(Long.valueOf(memberCode));
	        if(memberInfo != null){
	        	paraMap.put("serviceLevelCode", memberInfo.getServiceLevelCode());
	        }
	        paraMap.put("memberCriteria", memberCriteria);
	        paraMap.put("createDate", createDate);// 注册时间
	    	mv = new ModelAndView(accountIndex,paraMap);
		}
		return mv;
	}
	
	/**
	 * add by tom
	 * 获取授信总金额
	 * @param infoList
	 * @return
	 */
	private String getAmount(final List<PartnerSettlementOrder> infoList) {
		Long amount = 0L;
		if (CollectionUtils.isEmpty(infoList))
			return amount+"";
		for (final PartnerSettlementOrder partnerSettlementOrder : infoList) {
				amount+=partnerSettlementOrder.getOrderAmountCny();
			}
		return amountFormat2Str(amount);
	}
	/**
	 * 未处理拒付or调单统计
	 * @param memberCode
	 * @return
	 */
	private Integer countChargeOrBounced(String memberCode, String cpType) {
		Map<String, Object> bouncedMap = new HashMap<String, Object>() ;
		if(StringUtils.isNotEmpty(cpType)){
			if(cpType.contains(",")){
				//调单通知 1，2使用in查询
				bouncedMap.put("mpscpTypes", cpType) ;
			}else{
				//拒付通知
				bouncedMap.put("bouncedFlag", cpType) ;
			}
		}
		bouncedMap.put("memberCode", memberCode) ;
		bouncedMap.put("status", 0) ;//未处理
		Integer countBounced = this.chargeBackOrderService.countStatus(bouncedMap) ;
		return countBounced;
	}

	//--------------------------added by PengJiangbo Sta--------------
	/**
	 * 获取待清算金额
	 * @param memberCode
	 * @param currency
	 * @return
	 */
	private Long recSettlementAmount(final Long memberCode, final String currency){
		Long sumAmount = this.partnerSettlementOrderService.queryAmountByMemberCodeAndCurrency(memberCode, currency) ;
		return sumAmount ;
	}
	/**
	 * 获取伪账户列表
	 * @param memberCode
	 * @param paraMap
	 * @return
	 */
	private List<PseudoAcct> extractPseudoAccts(final String memberCode,
			final Map<String, Object> paraMap) {
		List<PseudoAcct> pseudoAccts = this.acctAttribService.queryAcctCurrencyByMemberCode(Long.valueOf(memberCode)) ;
		if(CollectionUtils.isNotEmpty(pseudoAccts)){
			for(PseudoAcct pseudoAcct : pseudoAccts){
				pseudoAcct.setAcctName(PseudoAcctTypeEnum.getDisplayNameByCurrency(pseudoAcct.getCurrency()));
			}
		}
		paraMap.put("pseudoAccts", pseudoAccts) ;
		return pseudoAccts;
	}
	/**
	 * 获取账户信息
	 * @param memberCode
	 * @param paraMap
	 * @param currency
	 */
	private void extractAcctInfo(final String memberCode,
			final Map<String, Object> paraMap, final String currency) {
		com.pay.acc.acct.model.PseudoAcct basicPseudoAcct = new com.pay.acc.acct.model.PseudoAcct() ;
		com.pay.acc.acct.model.PseudoAcct guaranteePseudoAcct = new com.pay.acc.acct.model.PseudoAcct() ;
		Map<String, com.pay.acc.acct.model.PseudoAcct> acctMaps = null ;
		if(StringUtils.isNotEmpty(currency)){
			//取出基本户冻结金额，并保存
			//BigDecimal FrozenBaseAmount = this.accountQueryService.doUpdateAcctsNsTx(Long.valueOf(memberCode), currency,"1") ;
			//取出保证金冻结金额，并保存
			//BigDecimal FrozenGuaranteeAmount = this.accountQueryService.doUpdateAcctsNsTx(Long.valueOf(memberCode), currency,"2") ;
		
			acctMaps = this.accountQueryService.doQueryAcctsNsTx(Long.valueOf(memberCode), currency) ;
			if(null != acctMaps){
				for(Map.Entry<String, com.pay.acc.acct.model.PseudoAcct> entry : acctMaps.entrySet()){
					String key = entry.getKey() ;
					if("basic".equals(key)){
						basicPseudoAcct = acctMaps.get("basic") ;
					}else if("guarantee".equals(key)){
						guaranteePseudoAcct = acctMaps.get("guarantee") ;
					}
				}
			}
		}
		paraMap.put("basicPseudoAcct", basicPseudoAcct) ;
		paraMap.put("guaranteePseudoAcct", guaranteePseudoAcct) ;
	}
	
	/**
	 * 获取账户信息
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView acct(final HttpServletRequest request, final HttpServletResponse response){
		PrintWriter out = null ;
		try {
			request.setCharacterEncoding("UTF-8") ;
			response.setCharacterEncoding("UTF-8") ;
			String currency = StringUtil.null2String(request.getParameter("currency")) ;
			String memberCode = SessionHelper.getMemeberCodeBySession() ;
			
			//取出基本户冻结金额，并保存
			//BigDecimal FrozenBaseAmount = this.accountQueryService.doUpdateAcctsNsTx(Long.valueOf(memberCode), currency,"1") ;
			//取出保证金冻结金额，并保存
			//BigDecimal FrozenGuaranteeAmount = this.accountQueryService.doUpdateAcctsNsTx(Long.valueOf(memberCode), currency,"2") ;
			Map<String, com.pay.acc.acct.model.PseudoAcct> acctMaps = this.accountQueryService.doQueryAcctsNsTx(Long.valueOf(memberCode), currency) ;
			com.pay.acc.acct.model.PseudoAcct basicPseudoAcct = new com.pay.acc.acct.model.PseudoAcct() ;
			com.pay.acc.acct.model.PseudoAcct guaranteePseudoAcct = new com.pay.acc.acct.model.PseudoAcct() ;
			if(null != acctMaps){
				for(Map.Entry<String, com.pay.acc.acct.model.PseudoAcct> entry : acctMaps.entrySet()){
					String key = entry.getKey() ;
					if("basic".equals(key)){
						basicPseudoAcct = acctMaps.get("basic") ;
					}else if("guarantee".equals(key)){
						guaranteePseudoAcct = acctMaps.get("guarantee") ;
					}
				}
			}
			//----------------------使用JSONObject.fromObject时，如果Integer类型的属性值为null时，会被转成0,故采用取statusStr的形式
			String basicStatusStr = null == basicPseudoAcct.getStatus() ? "" : basicPseudoAcct.getStatus().toString() ;
			String guaranteeStatusStr = null == guaranteePseudoAcct.getStatus() ? "" : guaranteePseudoAcct.getStatus().toString() ;
			basicPseudoAcct.setStatusStr(basicStatusStr);
			guaranteePseudoAcct.setStatusStr(guaranteeStatusStr);
			//-----------------ajax请求直接后台处理基本账户｜保证金账户金额，保留到小数点后3位
			if(null != acctMaps){
				basicPseudoAcct = acctMaps.get("basic") ;
				guaranteePseudoAcct = acctMaps.get("guarantee") ;
				//基本账户属性
				Long basicBalance = basicPseudoAcct.getBalance() ;
				Long basicFrozenAmount = basicPseudoAcct.getFrozenAmount() ;
				//保证金账户属性
				Long guaranteeBalance = guaranteePseudoAcct.getBalance() ;
				Long guaranteeFrozenAmount = guaranteePseudoAcct.getFrozenAmount() ;
				//冻结资金总和
				Long totalFrozenAmount = this.null2Long(basicFrozenAmount) + this.null2Long(guaranteeFrozenAmount) ;
				//处理金额
				basicPseudoAcct.setBalanceStr(this.amountFormat2Str(basicBalance));
				basicPseudoAcct.setTotalFrozenAmountStr(this.amountFormat2Str(basicFrozenAmount));
				guaranteePseudoAcct.setBalanceStr(this.amountFormat2Str(guaranteeBalance));
				guaranteePseudoAcct.setTotalFrozenAmountStr(this.amountFormat2Str(guaranteeFrozenAmount));
			}
			//取清算信息(待清算金额)
			Long sumAmount = this.recSettlementAmount(Long.valueOf(memberCode), currency) ;
			//acctMaps.put("sumAmount", sumAmount) ;
			JSONObject jsonObject = JSONObject.fromObject(acctMaps) ;
			jsonObject.put("sumAmount", this.amountFormat2Str(sumAmount)) ;
			out = response.getWriter() ;
			out.print(jsonObject);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null != out){
				out.flush() ;
				out.close();
			}
		}
		return null ;
	}
	/**
	 * 长整型非Null判断
	 * @param longNum
	 * @return
	 */
	private Long null2Long(final Long longNum){
		Long result = 0L ;
		if(null == longNum)
			return result ;
		else
			return longNum ;
	}
	/**
	 * 金额格式化，以字符串形式返回
	 * @param amount
	 * @return
	 */
	private String amountFormat2Str(final Long amount){
		if(null != amount){
			BigDecimal bigDecimal = new BigDecimal(amount).divide(new BigDecimal(1000)) ;
			//不四舍五入的方法  
			//,代表分隔符    
			//.后面的##代表位数 如果换成0 效果就是位数不足0补齐
		    DecimalFormat df =new DecimalFormat("#,##0.000");
		    // 设置舍入模式
		    df.setRoundingMode(RoundingMode.FLOOR); 
		    String formatStr = df.format(bigDecimal) ;
		  	return formatStr ;
		}
		return "0.000" ;
	}
	//--------------------------added by PengJiangbo End--------------
	
	/******** set方法 **********/
	public void setAccountIndex(final String accountIndex) {
		this.accountIndex = accountIndex;
	}
	public void setCorpAccountIndex(final String corpAccountIndex) {
        this.corpAccountIndex = corpAccountIndex;
    }
	public void setOperatorServiceFacade(final OperatorServiceFacade operatorServiceFacade) {
        this.operatorServiceFacade = operatorServiceFacade;
    }
	public void setVerify2GovService(final CidVerify2GovServiceFacade verify2GovService) {
		this.verify2GovService = verify2GovService;
	}
	public void setBaseMemberService(final MemberService baseMemberService) {
        this.baseMemberService = baseMemberService;
    }
    public void setEnterpriseBaseService(final EnterpriseBaseService enterpriseBaseService) {
        this.enterpriseBaseService = enterpriseBaseService;
    }
    public void setAcctService(final AcctService acctService) {
        this.acctService = acctService;
    }
	public void setUserLogService(final UserLogService userLogService) {
		this.userLogService = userLogService;
	}
	public void setAccountQueryService(final AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}
	public void setEnterpriseRegisterService(
			final EnterpriseRegisterService enterpriseRegisterService) {
		this.enterpriseRegisterService = enterpriseRegisterService;
	}
	public void setCorpOperAccountIndex(final String corpOperAccountIndex) {
		this.corpOperAccountIndex = corpOperAccountIndex;
	}
	public void setCorpAccountDetail(final String corpAccounttDetail) {
		this.corpAccountDetail = corpAccounttDetail;
	}
	
	public void setCorpAccountIndexInfo(final String corpAccountIndexInfo) {
		this.corpAccountIndexInfo = corpAccountIndexInfo;
	}
	public void setCorpOperAccountIndexInfo(final String corpOperAccountIndexInfo) {
		this.corpOperAccountIndexInfo = corpOperAccountIndexInfo;
	}
	public void setAcctAttribService(final AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}
	public void setPartnerSettlementOrderService(
			final PartnerSettlementOrderService partnerSettlementOrderService) {
		this.partnerSettlementOrderService = partnerSettlementOrderService;
	}
	public void setTradeOrderService(final TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}
	public void setAnnouncementService(final AnnouncementService announcementService) {
		this.announcementService = announcementService;
	}

	public void setTxncoreClientService(TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}

	public void setChargeBackOrderService(
			ChargeBackOrderService chargeBackOrderService) {
		this.chargeBackOrderService = chargeBackOrderService;
	}

	public void setSettleCoreClientService(
			SettleCoreClientService settleCoreClientService) {
		this.settleCoreClientService = settleCoreClientService;
	}

	
	
	
}
