/**
 * 
 */
package com.pay.poss.merchantmanager.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.acct.model.Acct;
import com.pay.acc.acct.model.AcctWithdrawFee;
import com.pay.acc.acct.service.AcctService;
import com.pay.acc.comm.LiquidateModeEnum;
import com.pay.acc.comm.MerchantCheckStatusEnum;
import com.pay.acc.comm.MerchantLevelEnum;
import com.pay.acc.comm.MerchantStatusEnum;
import com.pay.acc.comm.MerchantTypeEnum;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.acc.rate.dto.MerchantRateDto;
import com.pay.acc.rate.service.MerchantRateService;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.fundout.autofundout.service.AutoFundoutConfigService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.inf.dto.Bank;
import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.inf.service.BankService;
import com.pay.inf.service.CityService;
import com.pay.inf.service.ProvinceService;
import com.pay.pe.dto.CurrencyDTO;
import com.pay.pe.service.currency.CurrencyService;
import com.pay.poss.enterprisemanager.dto.MerchantDto;
import com.pay.poss.enterprisemanager.enums.DepartmentEnum;
import com.pay.poss.enterprisemanager.enums.NationEnum;
import com.pay.poss.enterprisemanager.formbean.EnterpriseFormBean;
import com.pay.poss.enterprisemanager.model.Relation;
import com.pay.poss.enterprisemanager.service.BouncedFineConfigService;
import com.pay.poss.enterprisemanager.service.IEnterpriseService;
import com.pay.poss.merchantmanager.dto.MerchantSearchDto;
import com.pay.poss.merchantmanager.dto.MerchantSearchListDto;
import com.pay.poss.merchantmanager.formbean.MerchantSearchFormBean;
import com.pay.poss.merchantmanager.model.LiquidateInfo;
import com.pay.poss.merchantmanager.service.IMerchantService;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.rm.facade.RcLimitRuleFacade;
import com.pay.rm.service.model.rmlimit.risklevel.RcRiskLevel;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;
import com.pay.util.WebBindUtils;

/**
 * @Description
 * @project ma-manager
 * @file MerchantRateController.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 woyo Corporation. All rights reserved. Date
 *          Author Changes 2015年8月1日 gungun_zhang Create
 */
public class MerchantRateController extends MultiActionController {

	private final Log logger = LogFactory.getLog(getClass());
	private MerchantRateService merchantRateService;
	private EnterpriseBaseService enterpriseBaseService;
	private IMerchantService merchantService;
	private IEnterpriseService enterpriseService;
	private AutoFundoutConfigService autoFundoutConfigService;
	private CurrencyService currencyService;
	private CityService cityService;
	private ProvinceService provinceService;
	private RcLimitRuleFacade rcLimitRuleFacade;
	private BankService bankService;
	private AccountQueryService accountQueryService;
	private BouncedFineConfigService bouncedFineConfigService;
	private AcctService acctService ;
	
	private String queryIndexView;
	private String merchantListView;
	private String merchantEditView;

	public void setBouncedFineConfigService(
			BouncedFineConfigService bouncedFineConfigService) {
		this.bouncedFineConfigService = bouncedFineConfigService;
	}

	public void setCurrencyService(final CurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	public void setMerchantRateService(final MerchantRateService merchantRateService) {
		this.merchantRateService = merchantRateService;
	}

	public void setMerchantService(final IMerchantService merchantService) {
		this.merchantService = merchantService;
	}

	public void setEnterpriseBaseService(
			final EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

	public void setQueryIndexView(final String queryIndexView) {
		this.queryIndexView = queryIndexView;
	}

	public void setMerchantListView(final String merchantListView) {
		this.merchantListView = merchantListView;
	}

	public void setMerchantEditView(final String merchantEditView) {
		this.merchantEditView = merchantEditView;
	}

	public void setEnterpriseService(final IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	public void setAutoFundoutConfigService(
			final AutoFundoutConfigService autoFundoutConfigService) {
		this.autoFundoutConfigService = autoFundoutConfigService;
	}

	public void setCityService(final CityService cityService) {
		this.cityService = cityService;
	}

	public void setProvinceService(final ProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	public void setRcLimitRuleFacade(final RcLimitRuleFacade rcLimitRuleFacade) {
		this.rcLimitRuleFacade = rcLimitRuleFacade;
	}

	public void setBankService(final BankService bankService) {
		this.bankService = bankService;
	}

	/**
	 * 查询商户信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		MerchantStatusEnum[] merchantStatusEnum = MerchantStatusEnum.values();
		MerchantCheckStatusEnum[] merchantCheckStatusEnum = MerchantCheckStatusEnum
				.values();

		Map<String, Object> dataMap = new Hashtable<String, Object>();
		dataMap.put("merchantStatusEnum", merchantStatusEnum);
		dataMap.put("merchantCheckStatusEnum", merchantCheckStatusEnum);
		return new ModelAndView(queryIndexView, dataMap);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param merchantSearchFormBean
	 * @return
	 * @throws Exception
	 */
	public ModelAndView merchantList(final HttpServletRequest request,
			final HttpServletResponse response,
			final MerchantSearchFormBean merchantSearchFormBean) throws Exception {
		MerchantSearchDto merchantSearchDto = new MerchantSearchDto();

		BeanUtils.copyProperties(merchantSearchFormBean, merchantSearchDto);
		if (StringUtils.isNotEmpty(merchantSearchFormBean.getMerchantName())) {

			if (Pattern.compile("^[a-z].+$")
					.matcher(merchantSearchFormBean.getMerchantName())
					.matches()) {
				merchantSearchDto.setMerchantSearchKey(merchantSearchFormBean
						.getMerchantName());
				merchantSearchDto.setMerchantName(null);
			}
		}
		Page<MerchantSearchListDto> page = PageUtils.getPage(request);
		merchantSearchDto.setPageEndRow((page.getPageNo() * page.getPageSize())
				+ "");
		if ((page.getPageNo() - 1) == 0) {
			merchantSearchDto.setPageStartRow("0");
		} else {
			merchantSearchDto.setPageStartRow((page.getPageNo() - 1)
					* page.getPageSize() + "");
		}
		List<MerchantSearchListDto> merchantList = merchantService
				.queryMerchant(merchantSearchDto);
		merchantList = this.getNameByCode(merchantList);
		Integer merchantListCount = merchantService
				.queryMerchantCount(merchantSearchDto);

		page.setResult(merchantList);
		page.setTotalCount(merchantListCount);
		return new ModelAndView(merchantListView).addObject("page", page);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView merchantEdit(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		logger.debug("MerchantEditForCheckController.referenceData() is running...");
		Map<String, Object> dataMap = this.initData();
		String memberCode = request.getParameter("memberCode");

		List<MerchantDto> merchantList = null;
		boolean hasAutoFo = false;
		if (StringUtils.isNotEmpty(memberCode)) {
			merchantList = this.enterpriseService
					.getMerchantListByMemberCode(memberCode);
			if (merchantList.size() > 0) {
				for (MerchantDto merchantDto : merchantList) {
					merchantDto.setCityName(this.cityService.findByCityCode(
							merchantDto.getCity().intValue())
							.getCityname());
					if (StringUtils.isNotEmpty(merchantDto.getCityBank())) {
						merchantDto.setCityBankName(this.cityService
								.findByCityCode(
										Integer.valueOf(merchantDto
												.getCityBank())).getCityname());
					}
					boolean isAutoFo = autoFundoutConfigService
							.findByMemberCodeAndBankCard(
									Long.valueOf(memberCode),
									merchantDto.getBankAcct()==null?"":merchantDto.getBankAcct(),
									merchantDto.getBankId()==null?"":merchantDto.getBankId());
					if (!isAutoFo) {
						hasAutoFo = true;
						merchantDto.setAutoFundout(1);
					} else {
						merchantDto.setAutoFundout(0);
					}

				}
				dataMap.put("merchantDto", (merchantList.get(0)));
			}
		}

		MerchantRateDto merchantRate = new MerchantRateDto();
		merchantRate.setMemberCode(Long.valueOf(memberCode));
		List<MerchantRateDto> merchantRateList = merchantRateService
				.queryMerchantRate(merchantRate);
		if (null != merchantRateList && !merchantRateList.isEmpty()) {
			dataMap.put("merchantRateList", merchantRateList);
		}
		//---added PengJiangbo
		//查询账户列表，获取可提现余额
		List<BalancesDto> balancesDtos = accountQueryService
				.doQueryBalancesBasicNsTx(Long.valueOf(memberCode));
		//查询已设置提现手续费的账户
		List<Acct> feeAccts = this.acctService.queryAcctWithFeeByMemberCode(Long.valueOf(memberCode)) ;
		//查询拒付罚款可配置的币种       add delin 2016年7月18日14:39:33
		List<Map>  chargebackFeeCurCodes = this.acctService.queryAcctAttribCurCode(Long.valueOf(memberCode)) ;
		//查询拒付配置
		Map<String,String> paraMap=new HashMap<String,String>();
		paraMap.put("memberCode", memberCode);
		List<Map<String,Object>> bouncedConfigs=this.bouncedFineConfigService.findBouncedFineConfig(paraMap);
		for (Map map : bouncedConfigs) {
			if(!map.get("ruleNo").equals("1")){
				Long fineAmount1 = Long.valueOf(map.get("fineAmount1").toString());
				Long fineAmount2 = Long.valueOf(map.get("fineAmount2").toString());
				Long fineAmount3 = Long.valueOf(map.get("fineAmount3").toString());
				BigDecimal fineAmount1B = new BigDecimal(fineAmount1).divide(new BigDecimal(1000));
				BigDecimal fineAmount2B = new BigDecimal(fineAmount2).divide(new BigDecimal(1000));
				BigDecimal fineAmount3B = new BigDecimal(fineAmount3).divide(new BigDecimal(1000));
				map.put("fineAmount1", fineAmount1B.toString());
				map.put("fineAmount2", fineAmount2B.toString());
				map.put("fineAmount3", fineAmount3B.toString());
			}
		}
		//---added PengJiangbo
		dataMap.put("bouncedConfigs", bouncedConfigs);
		dataMap.put("curCodes", chargebackFeeCurCodes);
		dataMap.put("merchantList", merchantList);
		dataMap.put("hasAutoFo", hasAutoFo);
		dataMap.put("currencyCodeEnum", CurrencyCodeEnum.values());
		dataMap.put("accts", balancesDtos) ;
		dataMap.put("feeAccts", feeAccts) ;
		return new ModelAndView(merchantEditView, dataMap);
	}

	/**
	 * 添加费率
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView addRate(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		MerchantRateDto merchantRate = new MerchantRateDto();
		bind(request, merchantRate, "", "");
		merchantRate.setCreateDate(new Date());
		merchantRate.setStatus(1);
		String userId = SessionUserHolderUtil.getLoginId();
		merchantRate.setOperator(userId);
		try {
			Long id = null;
			if(!existsMerchantRate(merchantRate)){
				id = merchantRateService.addMerchantRate(merchantRate);
				//最低月交易量的币种要保持一致
				merchantRateService.updateLevelCurrencyCode(merchantRate);
			}else{
				id=1000L;
			}
			String json = JSonUtil.toJSonString(id);
			response.setContentType("text/plain;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			out.write(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error("add error:", e);
		}
		return null;
	}
	
	private boolean existsMerchantRate(final MerchantRateDto merchantRate){
		MerchantRateDto merchantRateDto = new MerchantRateDto();
		merchantRateDto.setMemberCode(merchantRate.getMemberCode());
		merchantRateDto.setCountryCode(merchantRate.getCountryCode());
		merchantRateDto.setOrganisation(merchantRate.getOrganisation());
		merchantRateDto.setTransType(merchantRate.getTransType());
		merchantRateDto.setCardType(merchantRate.getCardType());
		merchantRateDto.setTransMode(merchantRate.getTransMode());
		merchantRateDto.setMonthAmountLevel1(merchantRate.getMonthAmountLevel1());
		merchantRateDto.setLevel1CurrencyCode(merchantRate.getLevel1CurrencyCode());
		merchantRateDto.setLocalPayCode(merchantRate.getLocalPayCode());
		List<MerchantRateDto> queryMerchantRate = merchantRateService.queryMerchantRate(merchantRateDto);
		return queryMerchantRate!=null&&!queryMerchantRate.isEmpty();
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView updateRate(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		MerchantRateDto merchantRate = new MerchantRateDto();
		bind(request, merchantRate, "", "");
		merchantRate.setCreateDate(new Date());
		merchantRate.setStatus(1);
		String userId = SessionUserHolderUtil.getLoginId();
		merchantRate.setOperator(userId);
		try {
			boolean id = merchantRateService.updateMerchantRate(merchantRate);
			//最低月交易量的币种要保持一致
			merchantRateService.updateLevelCurrencyCode(merchantRate);
			String json = JSonUtil.toJSonString(id);
			response.setContentType("text/plain;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			out.write(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error("update error:", e);
		}
		return null;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView del(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");

		try {
			merchantRateService.delMerchantRate(Long.valueOf(id));
			String json = JSonUtil.toJSonString(1);
			response.setContentType("text/plain;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			out.write(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error("add error:", e);
		}
		return null;
	}

	/**
	 * 保存企业结算信息和配率配置
	 * 
	 * @param request
	 * @param response
	 * @param command
	 * @param errors
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView onSubmit(final HttpServletRequest request,
			final HttpServletResponse response)
			throws Exception {
		
		EnterpriseFormBean enterpriseFormBean = new EnterpriseFormBean();
		bind(request, enterpriseFormBean, "", "");

		 onBind1(request, enterpriseFormBean, null);

		List<LiquidateInfo> liquidateInfos = enterpriseFormBean.getLiquidates();
		
		 String accountMode = ServletRequestUtils.getStringParameter(request,
				"accountMode", null) ;
		String percentage = ServletRequestUtils.getStringParameter(request,
				"percentage", null);
		String secondSettlementCycle = ServletRequestUtils.getStringParameter(
				request, "secondSettlementCycle", null);
		String assurePercentage = ServletRequestUtils.getStringParameter(
				request, "assurePercentage", null);
		String assureSettlementCycle = ServletRequestUtils.getStringParameter(
				request, "assureSettlementCycle", null);
		
		//拒付配置
		String firstPercent = ServletRequestUtils.getStringParameter(
				request, "firstPercent", null);
		String firstCost = ServletRequestUtils.getStringParameter(
				request, "firstCost", null);
		String secondPercent=ServletRequestUtils.getStringParameter(
				request, "secondPercent", null);
		String secondCost = ServletRequestUtils.getStringParameter(
				request, "secondCost", null);
		String thirdPercent = ServletRequestUtils.getStringParameter(
				request, "thirdPercent", null);
		String fourPercent = ServletRequestUtils.getStringParameter(
				request, "fourPercent", null);
		String fourCost = ServletRequestUtils.getStringParameter(
				request, "fourCost", null);
		String refundPerFeeConf = ServletRequestUtils.getStringParameter(
				request, "refundPerFeeConf", null);
		String refundFixedFeeConf = ServletRequestUtils.getStringParameter(
				request, "refundFixedFeeConf", null);
		
		//------------------added by PengJiangbo Sta
		List<AcctWithdrawFee> acctWithdrawFees = new ArrayList<AcctWithdrawFee>(1) ;
		try {
			String dataArr = URLDecoder.decode(URLDecoder.decode(ServletRequestUtils.getStringParameter(request,
					"dataArr", "[]"), "UTF-8"), "UTF-8");
			JSONArray jsonArray = JSONArray.fromObject(dataArr) ;
			for(int i=0; i<jsonArray.size(); i++){
				JSONObject jo = jsonArray.getJSONObject(i) ;
				String acctWithDrawFee = jo.getString("acctWithDrawFee") ;
				jo.put("acctWithDrawFee", new BigDecimal(acctWithDrawFee).multiply(new BigDecimal(1000))) ;
			}
			acctWithdrawFees = JSONArray.toList(jsonArray, AcctWithdrawFee.class) ;
		} catch (Exception e1) {
			if(logger.isInfoEnabled()){
				logger.info("获取提现手续费JSONArray出错了！");
			}
			e1.printStackTrace();
		}
		//------------------added by PengJiangbo End
		
		Boolean isSuccess = false;
		MerchantDto merchantDto = new MerchantDto();
		BeanUtils.copyProperties(enterpriseFormBean, merchantDto);
		merchantDto.setLiquidates(enterpriseFormBean.getLiquidates());
		merchantDto.setSettlementCycle(Integer.valueOf(accountMode));
		merchantDto.setPercentage(Integer.valueOf(percentage));
		if(StringUtils.isEmpty(refundFixedFeeConf)){
			merchantDto.setRefundFixedFeeConf(0L);	
		}else{
			merchantDto.setRefundFixedFeeConf(1L);
		}
		if(StringUtils.isEmpty(refundPerFeeConf)){
			merchantDto.setRefundPerFeeConf(0L);	
		}else{
			merchantDto.setRefundPerFeeConf(1L);
		}
		if (!StringUtil.isEmpty(secondSettlementCycle)) {
			merchantDto.setSecondSettlementCycle(Integer
					.valueOf(secondSettlementCycle));
		}
		merchantDto.setAssurePercentage(Integer.valueOf(assurePercentage));
		merchantDto.setAssureSettlementCycle(Integer
				.valueOf(assureSettlementCycle));

		try {
			merchantDto.setLiquidates(liquidateInfos);
			isSuccess = enterpriseService.updateMerchant(merchantDto);
			if(isSuccess){
				this.acctService.updateAcctWithdrawFee(acctWithdrawFees) ;
			}
			String json = JSonUtil.toJSonString(isSuccess);
			response.setContentType("text/plain;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			out.write(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error("add error:", e);
		}
		return null;
	}
	
	/**
	 * 删除已配置的提现手续费信息
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView deleteWithdrawAcctFee(HttpServletRequest request, HttpServletResponse response){
		try {
			PrintWriter out = response.getWriter() ;
			String acctCode = StringUtil.null2String(request.getParameter("acctCode")) ;
			String responseMessage = "" ;
			if(StringUtils.isNotEmpty(acctCode)){
				List<AcctWithdrawFee> acctWithdrawFees = new ArrayList<AcctWithdrawFee>() ;
				AcctWithdrawFee acctWithdrawFee = new AcctWithdrawFee() ;
				acctWithdrawFee.setAcctCode(acctCode);
				acctWithdrawFees.add(acctWithdrawFee) ;
				this.acctService.updateAcctWithdrawFee(acctWithdrawFees) ;
				responseMessage = JSONObject.fromObject("{'S': '成功'}").toString() ;
				out.write(responseMessage);
			}else{
				responseMessage = JSONObject.fromObject("{'N':'账户号为空'}").toString() ;
				out.write(responseMessage); //账户号为空
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除提现手续费失败！");
		}
		return null ;
	}
	
	public ModelAndView getCurrencyCodes(final HttpServletRequest request,
			final HttpServletResponse response, final EnterpriseFormBean enterpriseFormBean)
			throws Exception {
		CurrencyCodeEnum[] values = CurrencyCodeEnum.values();
		values[0]=CurrencyCodeEnum.USD;
		values[1]=CurrencyCodeEnum.CNY;
		String jSonString = JSonUtil.toJSonString(values);
		response.getWriter().print(jSonString);
		return null;
	}
	
	/***
	 * 异步从数据库获取卡组织类型
	 * 以json格式返回页面，填充localPayCode
	 * @author Davis.guo at 2016-07-28
	 * @param request
	 * @param response
	 * @param enterpriseFormBean
	 * @return
	 * @throws Exception
	 */
	public ModelAndView getLocalPayWay(final HttpServletRequest request,
			final HttpServletResponse response, final EnterpriseFormBean enterpriseFormBean)
			throws Exception {
		 
		/*ChannelItemOrgCodeEnum[] values = ChannelItemOrgCodeEnum.values();
		String jSonString = JSonUtil.toJSonString(values);
		response.getWriter().print(jSonString);*/
		
		List<Map<String,String>> organizationItems = new ArrayList<Map<String,String>>();//channelClientService.queryChannelItem(channelItem);ReconcileFileServiceController.getFundinChannels()
		//TODO 从数据库获取 FI.PAYMENT_CHANNEL
		//不调用此方式，直接在写到enterpriseView.jsp页面中 remark by davis.guo at 2016-07-28
		Map<String, String> item = new HashMap<String, String>();
		item.put("orgCode", "10081001");
		item.put("orgName", "CTBoleto");
		organizationItems.add(item);
		
		item = new HashMap<String, String>();
		item.put("orgCode", "10081002");
		item.put("orgName", "CTSafetyPay");
		organizationItems.add(item);
		
		/*item = new HashMap<String, String>();
		item.put("orgCode", "10081003");
		item.put("orgName", "CTDirectDebitsSSL");
		organizationItems.add(item);*/
		
		item = new HashMap<String, String>();
		item.put("orgCode", "10081004");
		item.put("orgName", "CTDirectEbanking");
		organizationItems.add(item);
		
		item = new HashMap<String, String>();
		item.put("orgCode", "10081005");
		item.put("orgName", "CTEPS");
		organizationItems.add(item);
		
		item = new HashMap<String, String>();
		item.put("orgCode", "10081006");
		item.put("orgName", "CTGiropay");
		organizationItems.add(item);
		
		item = new HashMap<String, String>();
		item.put("orgCode", "10081007");
		item.put("orgName", "CTPagBrailDebitCard");
		organizationItems.add(item);
		
		item = new HashMap<String, String>();
		item.put("orgCode", "10081008");
		item.put("orgName", "CTPagBrasilOTF");
		organizationItems.add(item);
		
		item = new HashMap<String, String>();
		item.put("orgCode", "10081009");
		item.put("orgName", "CTPoli");
		organizationItems.add(item);
		
		item = new HashMap<String, String>();
		item.put("orgCode", "10081010");
		item.put("orgName", "CTPrzelewy24");
		organizationItems.add(item);
		
		item = new HashMap<String, String>();
		item.put("orgCode", "10081011");
		item.put("orgName", "CTQIWI");
		organizationItems.add(item);
		
		item = new HashMap<String, String>();
		item.put("orgCode", "10081012");
		item.put("orgName", "CTSEPA");
		organizationItems.add(item);
		
		item = new HashMap<String, String>();
		item.put("orgCode", "10081013");
		item.put("orgName", "CTTeleingreso");
		organizationItems.add(item);
		
		item = new HashMap<String, String>();
		item.put("orgCode", "10081014");
		item.put("orgName", "CTTrustPay");
		organizationItems.add(item);
		
		item = new HashMap<String, String>();
		item.put("orgCode", "10081015");
		item.put("orgName", "CTiDeal");
		organizationItems.add(item);
		
		String organizationJSonStr = JSonUtil.toJSonString(organizationItems);
		System.out.println("###organizationJSonStr="+organizationJSonStr);
		response.getWriter().print(organizationJSonStr);
		return null;
	}

	private List<MerchantSearchListDto> getNameByCode(
			final List<MerchantSearchListDto> merchantList) {
		if (merchantList != null && merchantList.size() != 0) {
			for (int i = 0; i < merchantList.size(); i++) {
				MerchantSearchListDto merchant = (MerchantSearchListDto) merchantList
						.get(i);
				if (StringUtils.isNotEmpty(merchant.getMerchantStatus())) {
					MerchantStatusEnum merchantStatusEnum = MerchantStatusEnum
							.getByCode(Integer.valueOf(merchant
									.getMerchantStatus()));
					if (merchantStatusEnum != null) {
						merchant.setMerchantStatus(merchantStatusEnum
								.getDescription());
					}

				}
				if (StringUtils.isNotEmpty(merchant.getMerchantCheckStatus())) {
					MerchantCheckStatusEnum merchantCheckStatusEnum = MerchantCheckStatusEnum
							.getByCode(Integer.valueOf(merchant
									.getMerchantCheckStatus()));
					if (merchantCheckStatusEnum != null) {
						merchant.setMerchantCheckStatus(merchantCheckStatusEnum
								.getDescription());
					}

				}
				if (StringUtils.isNotEmpty(merchant.getMerchantType())) {
					MerchantTypeEnum merchantTypeEnum = MerchantTypeEnum
							.getByCode(Integer.valueOf(merchant
									.getMerchantType()));
					if (merchantTypeEnum != null) {
						merchant.setMerchantType(merchantTypeEnum
								.getDescription());
					}

				}
				if (StringUtils.isNotEmpty(merchant.getServiceLevel())) {
					MerchantLevelEnum merchantLevelEnum = MerchantLevelEnum
							.getByCode(Integer.valueOf(merchant
									.getServiceLevel()));
					if (merchantLevelEnum != null) {
						merchant.setServiceLevel(merchantLevelEnum
								.getDescription());
					}

				}

			}
		}
		return merchantList;

	}

	private Map<String, Object> initData() {

		List<ProvinceDTO> provinceList = provinceService.findAll();
		List<Relation> relationList = new ArrayList<Relation>();

		for (int i = 0; i < provinceList.size(); i++) {
			ProvinceDTO province = (ProvinceDTO) provinceList.get(i);
			List<CityDTO> cityList = cityService.findByProvinceId(province
					.getProvincecode());

			for (int j = 0; j < cityList.size(); j++) {
				CityDTO city = (CityDTO) cityList.get(j);
				Relation relation = new Relation();
				relation.setFatherCode(province.getProvincecode().toString());
				relation.setCode(city.getCitycode().toString());
				relation.setName(city.getCityname());
				relationList.add(relation);
			}
		}

		DepartmentEnum[] departmentEnum = DepartmentEnum.values();
		LiquidateModeEnum[] liquidateModeEnum = LiquidateModeEnum.values();
		MerchantLevelEnum[] merchantLevelEnum = MerchantLevelEnum.values();
		MerchantTypeEnum[] merchantTypeEnum = MerchantTypeEnum.values();

		NationEnum[] nationEnum = NationEnum.values();
		List<Bank> bankList = bankService.getWithdrawBanks();

		Map<String, Object> dataMap = new Hashtable<String, Object>();
		dataMap.put("bankList", bankList);
		dataMap.put("provinceList", provinceList);
		dataMap.put("relationList", relationList);
		dataMap.put("departmentEnum", departmentEnum);
		dataMap.put("liquidateModeEnum", liquidateModeEnum);
		dataMap.put("merchantLevelEnum", merchantLevelEnum);
		dataMap.put("merchantTypeEnum", merchantTypeEnum);
		List<RcRiskLevel> riskLevelList = rcLimitRuleFacade.getRiskLevelList();
		dataMap.put("riskLevelList", riskLevelList);
		dataMap.put("nationEnum", nationEnum);

		return dataMap;
	}

	/**
	 * 页面request参与与Model对象绑定
	 * 
	 * @param request
	 * @param object
	 * @param objectName
	 * @param dateFormat
	 * @throws ServletException
	 * @author Henry.Zeng
	 * @see
	 */
	protected void bind(final HttpServletRequest request, final Object object,
			final String objectName, final String dateFormat) {
		try {
			WebBindUtils.bind(request, object, objectName, true, dateFormat);
		} catch (ServletException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	protected void onBind1(final HttpServletRequest request, final Object command,
			final BindException errors) throws Exception {
		EnterpriseFormBean enterpriseFormBean = (EnterpriseFormBean) command;

		String[] liquidateIds = ServletRequestUtils.getStringParameters(
				request, "liquidateId");
		String[] bankAccts = ServletRequestUtils.getStringParameters(request,
				"bankAcct");
		String[] acctNames = ServletRequestUtils.getStringParameters(request,
				"acctName");

		String[] regionBanks = ServletRequestUtils.getStringParameters(request,
				"province");
		String[] cityBanks = ServletRequestUtils.getStringParameters(request,
				"city");
		String[] bankIds = ServletRequestUtils.getStringParameters(request,
				"bankId");
		String[] bankAddresses = ServletRequestUtils.getStringParameters(
				request, "bankAddress");
		String[] bankNames = ServletRequestUtils.getStringParameters(request,
				"bankName");
		String[] branchBankIds = ServletRequestUtils.getStringParameters(
				request, "branchBankId");
		String[] swiftCodes = ServletRequestUtils.getStringParameters(
				request, "swiftCode");

		List<LiquidateInfo> list = new ArrayList<LiquidateInfo>();
		for (int i = 0; i < bankAccts.length; i++) {

			LiquidateInfo info = new LiquidateInfo();
			info.setLiquidateId(Long.valueOf(liquidateIds[i]));
			info.setBankAcct(bankAccts[i]);
			info.setAcctName(acctNames[i]);
			info.setProvince(Long.valueOf(regionBanks[i]));
			info.setCity(Long.valueOf(cityBanks[i]));
			info.setBankId(bankIds[i]);
			info.setSwiftCode(swiftCodes[i]);
			info.setBankAddress((bankAddresses[i]));
			info.setBranchBankId(Long.valueOf(branchBankIds[i]));
			info.setBankName(bankNames[i]);
			// info.setAccountMode(accountModes);
			list.add(info);
		}
		enterpriseFormBean.setLiquidates(list);
	}
	
	
	
	/****
	 * 获取退款手续费币种
	 * @param request
	 * @param response
	 */
	public void getRefundCurrency(HttpServletRequest request,HttpServletResponse response){
		Map currency = getCurrency(request, response);
		String string = new String(JSonUtil.toJSonString(currency));
		response.setContentType("text/html;charset=UTF-8");
		try {
			response.getWriter().write(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Map getCurrency(final HttpServletRequest request,
			final HttpServletResponse response){
		CurrencyDTO currencyDTO = new CurrencyDTO();
		currencyDTO.setFlag("6");
		Page page = PageUtils.getPage(request);
		page.setPageSize(200);
		Map<String,String> map=new LinkedHashMap<String, String>();
		List<CurrencyDTO> findByCriteria = currencyService.findByCriteria(
				currencyDTO, page);
		LinkedList<CurrencyDTO> result = new LinkedList<CurrencyDTO>();
		if(findByCriteria!=null&&findByCriteria.size()!=0){
			result.addAll(findByCriteria);
		}
		Iterator<CurrencyDTO> iterator = result.iterator();
		CurrencyDTO usd = null;
		CurrencyDTO cny = null;
		while(iterator.hasNext()){
			CurrencyDTO next = iterator.next();
			if(next.getCurrencyCode().equals("USD")){
				usd = next;
				iterator.remove();
			}
			if(next.getCurrencyCode().equals("CNY")){
				cny = next;
				iterator.remove();
			}
		}
		result.addFirst(cny);
		result.addFirst(usd);
		for (CurrencyDTO currencyDTO2 : result) {
			map.put(currencyDTO2.getCurrencyCode(), currencyDTO2.getCurrencyName());
		}
		return map;
	}

	/**
	 * 新增拒付罚款规则配置
	 * @author delin
	 * @date 2016年7月19日15:54:02
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView addConfig(HttpServletRequest request,HttpServletResponse response){
		Map map = request.getParameterMap();
		
		
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//------------------
	public void setAccountQueryService(final AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

	public void setAcctService(final AcctService acctService) {
		this.acctService = acctService;
	}

}
