/**
 *  File: AutoFundoutController.java
 *  Description:
 *  Copyright 2010 -2010 Corporation. All rights reserved.
 *  2010-12-10     darv      Changes
 *  
 *
 */
package com.pay.app.controller.fo.autofundout;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.exception.MaBankCardBindException;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.acc.service.member.BankCardBindService;
import com.pay.acc.service.member.MemberProductService;
import com.pay.acc.service.member.dto.BankCardBindBO;
import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.api.wrapper.SafeControllerWrapper;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.fundout.autofundout.custom.model.AutoFundoutConfig;
import com.pay.fundout.autofundout.custom.model.AutoQuotaConfig;
import com.pay.fundout.autofundout.custom.model.AutoTimeConfig;
import com.pay.fundout.autofundout.custom.service.AutoWithdrawService;
import com.pay.fundout.channel.service.configbank.ConfigBankService;
import com.pay.fundout.withdraw.service.payment.PaymentPasswordValidateService;
import com.pay.rm.FoRcLimitFacade;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.rm.facade.util.RCLIMITCODE;
import com.pay.util.StringUtil;
import com.pay.util.UUIDUtil;

/**
 * @author darv
 * 
 */
public class AutoFundoutController extends MultiActionController {
	private Log log = LogFactory.getLog(getClass());

	private Map<String, String> urlMap;

	private FoRcLimitFacade foRcLimitFacade;

	private BankCardBindService bankCardBindService;

	private PaymentPasswordValidateService paymentPasswordValidateService;

	private AutoWithdrawService autoWithdrawService;
	
	/** 会员产品开通权限**/
	private MemberProductService memberProductService;
	
	/** 检索出款渠道 **/
	private ConfigBankService configBankService;
	
	public void setConfigBankService(ConfigBankService configBankService) {
		this.configBankService = configBankService;
	}

	public void setAutoWithdrawService(AutoWithdrawService autoWithdrawService) {
		this.autoWithdrawService = autoWithdrawService;
	}

	public void setPaymentPasswordValidateService(PaymentPasswordValidateService paymentPasswordValidateService) {
		this.paymentPasswordValidateService = paymentPasswordValidateService;
	}

	public void setBankCardBindService(BankCardBindService bankCardBindService) {
		this.bankCardBindService = bankCardBindService;
	}

	public void setUrlMap(Map<String, String> urlMap) {
		this.urlMap = urlMap;
	}

	public void setMemberProductService(MemberProductService memberProductService) {
		this.memberProductService = memberProductService;
	}
	
	public void setFoRcLimitFacade(FoRcLimitFacade foRcLimitFacade) {
		this.foRcLimitFacade = foRcLimitFacade;
	}

	/**
	 * 进入简介页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "FO_AUTOFUNDOUT")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> config = null;
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		try {
			Long memberCode = Long.valueOf(loginSession.getMemberCode());
			if(!isHaveProduct(memberCode)){
				return new ModelAndView(urlMap.get("autofundoutnotexist"));
			}
			config = autoWithdrawService.existsConfig(memberCode);
			if(log.isInfoEnabled()) {
				log.info("index.config========="+config);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		if (config == null || config.size() == 0) {
			return new ModelAndView(urlMap.get("summary"));
		}
		AutoFundoutCommon fundoutCommon = getFundoutCommon(config);
		return new ModelAndView(urlMap.get("view")).addObject("common", fundoutCommon);
	}
	

	/**
	 * 进入添加页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "FO_AUTOFUNDOUT")
	public ModelAndView addPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView addModelView = new ModelAndView(urlMap.get("add"));
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		Long memberCode = Long.valueOf(loginSession.getMemberCode());
		
		request.getSession().setAttribute("uuid", UUIDUtil.uuid());
		
		if(!isHaveProduct(memberCode)){
			return new ModelAndView(urlMap.get("autofundoutnotexist"));
		}
		List<BankCardBindBO> bankList = getBankList(Long.valueOf(request.getSession().getAttribute("memberCode")
				.toString()));
		Long singleLimit = getSingleLimit(Long.valueOf(request.getSession().getAttribute("memberCode").toString()));
		
		if(log.isInfoEnabled()) {
			log.info("addPage.bankList========="+bankList);
		}
		
		if(log.isInfoEnabled()) {
			log.info("addPage.singleLimit========="+singleLimit);
		}
		addModelView.addObject("defualtBankId", request.getParameter("id") == null ? getDefualtBankAttr(bankList) : "");
		
		return addModelView.addObject("common", new AutoFundoutCommon()).addObject("bankList",
				bankList).addObject("singleLimit", singleLimit);
	}

	/**
	 * 添加操作
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "FO_AUTOFUNDOUT")
	public ModelAndView addConfig(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		try {
			Long memberCode = Long.valueOf(loginSession.getMemberCode());
			if(!isHaveProduct(memberCode)){
				return new ModelAndView(urlMap.get("autofundoutnotexist"));
			}
			
			Map<String, Object> config = new HashMap<String, Object>();
			AutoFundoutConfig fundoutConfig = new AutoFundoutConfig();
			fundoutConfig.setBankCode(request.getParameter("bankCode"));
			fundoutConfig.setBankName(request.getParameter("bankName"));
			fundoutConfig.setBankAccCode(request.getParameter("bankAcctCode"));
			if(StringUtils.isNotEmpty(request.getParameter("retainedAmount"))){
				fundoutConfig.setRetainedAmount(new BigDecimal(request.getParameter("retainedAmount")).multiply(new BigDecimal(1000)).longValue());
			}else{
				fundoutConfig.setRetainedAmount(0l);
			}
			fundoutConfig.setRemark(request.getParameter("remarks"));
			fundoutConfig.setAutoType(Integer.valueOf(request.getParameter("autoType")));
			fundoutConfig.setBusiType(0);
			fundoutConfig.setMemberCode(memberCode);
			fundoutConfig.setMemberType(MemberTypeEnum.MERCHANT.getCode());
			fundoutConfig.setStatus(0);
			fundoutConfig.setCreateUser(request.getSession().getAttribute("verifyName").toString());
			fundoutConfig.setCreateDate(new Date());

			config.put("autoFunoutConfig", fundoutConfig);

			AutoTimeConfig timeConfig = null;
			AutoQuotaConfig quotaConfig = null;
			List<AutoTimeConfig> timeConfigs = new ArrayList<AutoTimeConfig>();
			if (fundoutConfig.getAutoType().equals(1)) {
				
				
				
				Integer timeType = Integer.valueOf(request.getParameter("timeType"));
				if(timeType!=4){
					timeConfig = new AutoTimeConfig();
					timeConfig.setTimeType(timeType);
					timeConfig.setTimeSource(request.getParameter("timeSource"));
					timeConfig.setCreateDate(new Date());
					timeConfig.setSettleFlag(Integer.valueOf("1".equals(request.getParameter("settleFlag")) ? "1" : "0"));
					timeConfigs.add(timeConfig);
				}else{
					String[] hours = request.getParameterValues("pointH");
					String[] minutes = request.getParameterValues("pointM");
					for (int i = 0; i < hours.length; i++) {
						if(StringUtil.isNull(hours[i])){
							continue;
						}
						timeConfig = new AutoTimeConfig();
						timeConfig.setTimeType(timeType);
						timeConfig.setTimeSource(fmtTimeData(hours[i])+":"+fmtTimeData(minutes[i]));
						timeConfig.setCreateDate(new Date());
						timeConfig.setSettleFlag(0);
						timeConfigs.add(timeConfig);
					}
				}
				config.put("autoTimeConfig", timeConfigs);
			} else if (fundoutConfig.getAutoType().equals(2)) {
				quotaConfig = new AutoQuotaConfig();
				quotaConfig.setBaseAmount(new BigDecimal(request.getParameter("baseAmount")).multiply(new BigDecimal(1000)).longValue());
				quotaConfig.setCreateDate(new Date());
				config.put("autoQuotaConfig", quotaConfig);
			}else{
				throw new RuntimeException("添加委托提现配置发生异常[委托提现类型不支持:"+fundoutConfig.getAutoType()+"]");
			}

			String pwdError = checkPayPassWord(request, memberCode,loginSession.getOperatorId());
			if (pwdError != null) {
				AutoFundoutCommon fundoutCommon = getFundoutCommon(fundoutConfig, timeConfig, quotaConfig);
				List<BankCardBindBO> bankList = getBankList(Long.valueOf(request.getSession().getAttribute(
						"memberCode").toString()));
				Long singleLimit = getSingleLimit(memberCode);
				return new ModelAndView(urlMap.get("add")).addObject("common", fundoutCommon).addObject("bankList",
						bankList).addObject("pwdError", pwdError).addObject("singleLimit", singleLimit).
						addObject("defualtBankId", request.getParameter("id") == null ? getDefualtBankAttr(bankList) : "");
			}
			
			String uuid = request.getParameter("uuid");
			if (uuid != null && request.getSession().getAttribute("uuid") != null
					&& uuid.equalsIgnoreCase(request.getSession().getAttribute("uuid").toString())) {
				request.getSession().removeAttribute("uuid");
				autoWithdrawService.createRdTx(config);
			}
			return new ModelAndView(urlMap.get("result")).addObject("result", "设置委托提现成功").addObject("nextProcess", "查看委托提现").addObject("msgType", 1);
		} catch (Exception e) {
			log.error("设置委托提现发生异常",e);
			return new ModelAndView(urlMap.get("result")).addObject("result", "设置委托提现失败").addObject("nextProcess", "重设委托提现").addObject("msgType", 0);
		}
	}
	
	private String fmtTimeData(String src){
		if(src.length()==1){
			src = '0'+src;
		}
		return src;
	}

	/**
	 * 进入修改页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "FO_AUTOFUNDOUT")
	public ModelAndView modifyPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		Long memberCode = Long.valueOf(loginSession.getMemberCode());
		if(!isHaveProduct(Long.valueOf(memberCode))){
			return new ModelAndView(urlMap.get("autofundoutnotexist"));
		}
		request.getSession().setAttribute("uuid", UUIDUtil.uuid());
		List<BankCardBindBO> bankList = getBankList(memberCode);
		Long singleLimit = getSingleLimit(memberCode);
		Map<String, Object> config = null;
		
		
		
		try {
			config = autoWithdrawService.existsConfig(memberCode);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}

		if(log.isInfoEnabled()) {
			log.info("index.config========="+config);
		}
		
		AutoFundoutCommon fundoutCommon = new AutoFundoutCommon();
		if (config != null && config.size() != 0) {
			fundoutCommon = getFundoutCommon(config);
		}
		return new ModelAndView(urlMap.get("modify")).addObject("common", fundoutCommon)
				.addObject("bankList", bankList).addObject("singleLimit", singleLimit);
	}

	/**
	 * 修改配置　
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "FO_AUTOFUNDOUT")
	public ModelAndView modifyConfig(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		try {
			Long memberCode = Long.valueOf(loginSession.getMemberCode());
			Map<String, Object> config = new HashMap<String, Object>();
			AutoFundoutConfig fundoutConfig = new AutoFundoutConfig();
			fundoutConfig.setSequenceid(Long.valueOf(request.getParameter("configId")));
			fundoutConfig.setBankCode(request.getParameter("bankCode"));
			fundoutConfig.setBankName(request.getParameter("bankName"));
			fundoutConfig.setBankAccCode(request.getParameter("bankAcctCode"));
			fundoutConfig.setRetainedAmount(new BigDecimal(request.getParameter("retainedAmount")).multiply(new BigDecimal(1000)).longValue());
			fundoutConfig.setRemark(request.getParameter("remarks"));
			fundoutConfig.setAutoType(Integer.valueOf(request.getParameter("autoType")));
			fundoutConfig.setBusiType(0);
			fundoutConfig.setMemberCode(memberCode);
			fundoutConfig.setMemberType(MemberTypeEnum.MERCHANT.getCode());
			fundoutConfig.setStatus(0);
			fundoutConfig.setUpdateUser(request.getSession().getAttribute("verifyName").toString());
			fundoutConfig.setUpdateDate(new Date());

			config.put("autoFunoutConfig", fundoutConfig);

			
			AutoTimeConfig timeConfig = null;
			AutoQuotaConfig quotaConfig = null;
			List<AutoTimeConfig> timeConfigs = new ArrayList<AutoTimeConfig>();
			if (fundoutConfig.getAutoType().equals(1)) {
				Integer timeType = Integer.valueOf(request.getParameter("timeType"));
				if(timeType!=4){
					timeConfig = new AutoTimeConfig();
					timeConfig.setTimeType(timeType);
					timeConfig.setTimeSource(request.getParameter("timeSource"));
					timeConfig.setCreateDate(new Date());
					timeConfig.setSettleFlag(Integer.valueOf("1".equals(request.getParameter("settleFlag")) ? "1" : "0"));
					timeConfigs.add(timeConfig);
				}else{
					String[] hours = request.getParameterValues("pointH");
					String[] minutes = request.getParameterValues("pointM");
					for (int i = 0; i < hours.length; i++) {
						if(StringUtil.isNull(hours[i])){
							continue;
						}
						timeConfig = new AutoTimeConfig();
						timeConfig.setTimeType(timeType);
						timeConfig.setTimeSource(fmtTimeData(hours[i])+":"+fmtTimeData(minutes[i]));
						timeConfig.setCreateDate(new Date());
						timeConfig.setSettleFlag(0);
						timeConfigs.add(timeConfig);
					}
				}
				config.put("autoTimeConfig", timeConfigs);
			} else if (fundoutConfig.getAutoType().equals(2)) {
				quotaConfig = new AutoQuotaConfig();
				quotaConfig.setSequenceid(Long.valueOf(request.getParameter("typeId")));
				quotaConfig.setConfigId(fundoutConfig.getSequenceid());
				quotaConfig.setBaseAmount(new BigDecimal(request.getParameter("baseAmount")).multiply(new BigDecimal(1000)).longValue());
				config.put("autoQuotaConfig", quotaConfig);
			}else{
				throw new RuntimeException("修改委托提现配置发生异常[委托提现类型不支持:"+fundoutConfig.getAutoType()+"]");
			}

			String pwdError = checkPayPassWord(request, memberCode,loginSession.getOperatorId());
			if (pwdError != null) {
				AutoFundoutCommon fundoutCommon = getFundoutCommon(fundoutConfig, timeConfig, quotaConfig);
				List<BankCardBindBO> bankList = getBankList(Long.valueOf(request.getSession().getAttribute(
						"memberCode").toString()));
				Long singleLimit = getSingleLimit(memberCode);
				return new ModelAndView(urlMap.get("modify")).addObject("common", fundoutCommon).addObject(
						"bankList", bankList).addObject("singleLimit", singleLimit).addObject("pwdError", pwdError);
			}
			String uuid = request.getParameter("uuid");
			if (uuid != null && request.getSession().getAttribute("uuid") != null
					&& uuid.equalsIgnoreCase(request.getSession().getAttribute("uuid").toString())) {
				request.getSession().removeAttribute("uuid");
				// 更新
				autoWithdrawService.updateRdTx(config);
			}

			return new ModelAndView(urlMap.get("result")).addObject("result", "修改委托提现成功").addObject("nextProcess", "查看委托提现").addObject("msgType", 1);
		} catch (Exception e) {
			log.error("修改委托提现发生异常",e);
			return new ModelAndView(urlMap.get("result")).addObject("result", "修改委托提现失败").addObject("nextProcess", "查看委托提现").addObject("msgType", 0);
		}
	}

	/**
	 * 查看配置　
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "FO_AUTOFUNDOUT")
	public ModelAndView viewPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> config = null;
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		try {
			Long memberCode = Long.valueOf(loginSession.getMemberCode());
			config = autoWithdrawService.existsConfig(memberCode);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}

		if(log.isInfoEnabled()) {
			log.info("viewPage.config========="+config);
		}
		AutoFundoutCommon fundoutCommon = new AutoFundoutCommon();
		if (config != null && config.size() != 0) {
			fundoutCommon = getFundoutCommon(config);
		}
		return new ModelAndView(urlMap.get("view")).addObject("common", fundoutCommon);
	}

	/**
	 * 进入终止配置页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "FO_AUTOFUNDOUT")
	public ModelAndView cancelPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		Long memberCode = Long.valueOf(loginSession.getMemberCode());
		if(!isHaveProduct(memberCode)){
			return new ModelAndView(urlMap.get("autofundoutnotexist"));
		}
		
		request.getSession().setAttribute("uuid", UUIDUtil.uuid());
		Map<String, Object> config = null;
		try {
			config = autoWithdrawService.existsConfig(memberCode);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}

		if(log.isInfoEnabled()) {
			log.info("cancelPage.config========="+config);
		}
		AutoFundoutCommon fundoutCommon = new AutoFundoutCommon();
		if (config != null && config.size() != 0) {
			fundoutCommon = getFundoutCommon(config);
		}
		return new ModelAndView(urlMap.get("cancel")).addObject("common", fundoutCommon);
	}

	/**
	 * 终止配置操作
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "FO_AUTOFUNDOUT")
	public ModelAndView cancelConfig(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		Long memberCode = Long.valueOf(loginSession.getMemberCode());
		if(!isHaveProduct(memberCode)){
			return new ModelAndView(urlMap.get("autofundoutnotexist"));
		}
		try {
			String pwdError = checkPayPassWord(request, memberCode,loginSession.getOperatorId());
			if (pwdError != null) {
				Map<String, Object> config = autoWithdrawService.existsConfig(memberCode);
				AutoFundoutCommon fundoutCommon = new AutoFundoutCommon();
				if (config != null && config.size() != 0) {
					fundoutCommon = getFundoutCommon(config);
				}
				return new ModelAndView(urlMap.get("cancel")).addObject("pwdError", pwdError).addObject("common",
						fundoutCommon);
			}
			String uuid = request.getParameter("uuid");
			if (uuid != null && request.getSession().getAttribute("uuid") != null
					&& uuid.equalsIgnoreCase(request.getSession().getAttribute("uuid").toString())) {
				request.getSession().removeAttribute("uuid");
				autoWithdrawService.disable(Long.valueOf(request.getParameter("configId")));
			}
			return new ModelAndView(urlMap.get("result")).addObject("result", "终止委托提现成功").addObject("nextProcess", "委托提现").addObject("msgType", 1);
		} catch (Exception e) {
			log.error("终止委托提现失败：",e);
			return new ModelAndView(urlMap.get("result")).addObject("result", "终止委托提现失败").addObject("nextProcess", "查看委托提现").addObject("msgType", 0);
		}
	}

	/**
	 * 组装对象
	 * 
	 * @param fundoutConfig
	 * @param timeConfig
	 * @param quotaConfig
	 * @return
	 * @throws Exception
	 */
	private AutoFundoutCommon getFundoutCommon(AutoFundoutConfig fundoutConfig, AutoTimeConfig timeConfig,
			AutoQuotaConfig quotaConfig) throws Exception {
		AutoFundoutCommon fundoutCommon = new AutoFundoutCommon();
		BeanUtils.copyProperties(fundoutConfig, fundoutCommon);
		if (timeConfig != null) {
			fundoutCommon.setTypeId(timeConfig.getSequenceid());
			fundoutCommon.setTimeType(timeConfig.getTimeType());
			fundoutCommon.setSettleFlag(timeConfig.getSettleFlag());
			fundoutCommon.setTimeSource(timeConfig.getTimeSource());
		}
		if (quotaConfig != null) {
			fundoutCommon.setTypeId(quotaConfig.getSequenceid());
			fundoutCommon.setBaseAmount(quotaConfig.getBaseAmount());
		}
		return fundoutCommon;
	}

	private AutoFundoutCommon getFundoutCommon(Map<String, Object> config) throws Exception {
		AutoFundoutCommon fundoutCommon = new AutoFundoutCommon();
		AutoFundoutConfig fundoutConfig = (AutoFundoutConfig) config.get("autoFundoutConfig");
		BeanUtils.copyProperties(fundoutConfig, fundoutCommon);
		if (config.get("autoTimeConfig") != null) {
			List<AutoTimeConfig> timeConfig = (List<AutoTimeConfig>)config.get("autoTimeConfig");
			String[] timeSources = new String[timeConfig.size()];
			int i = 0;
			for (AutoTimeConfig autoTimeConfig : timeConfig) {
				fundoutCommon.setTypeId(autoTimeConfig.getSequenceid());
				fundoutCommon.setTimeType(autoTimeConfig.getTimeType());
				fundoutCommon.setSettleFlag(autoTimeConfig.getSettleFlag());
				if(autoTimeConfig.getTimeType()==4){
					timeSources[i++] = autoTimeConfig.getTimeSource();
				}else{
					fundoutCommon.setTimeSource(autoTimeConfig.getTimeSource());
				}
				
			}
			if(fundoutCommon.getTimeType()==4){
				fundoutCommon.setTimeSources(timeSources);
			}
		}
		if (config.get("autoQuotaConfig") != null) {
			AutoQuotaConfig quotaConfig = (AutoQuotaConfig) config.get("autoQuotaConfig");
			fundoutCommon.setTypeId(quotaConfig.getSequenceid());
			fundoutCommon.setBaseAmount(quotaConfig.getBaseAmount());
		}
		return fundoutCommon;
	}
	
	/**
	 * 获取默认银行卡ID
	 * @return
	 */
	private String getDefualtBankAttr(List<BankCardBindBO> list) {
		if (list == null) {
			return null;
		}
		for (BankCardBindBO bankCardBindBO : list) {
			if(1 == bankCardBindBO.getIsPrimaryBankacct()){
				return String.valueOf(bankCardBindBO.getId());
			}
		}
		return null;
	}
	
	//用户产品开通权限
	private boolean isHaveProduct(Long memberCode){
		return memberProductService.isHaveProduct(Long.valueOf(memberCode), "AUTO_WITHDRAW");
	}

	/**
	 * 检查支付密码
	 * 
	 * @param request
	 * @param memberCode
	 * @return
	 */
	private String checkPayPassWord(HttpServletRequest request, Long memberCode,Long operatorId) {
		SafeControllerWrapper safeControllerWrapper = new SafeControllerWrapper(request, new String[] { "passWord" });
		String payPwd = safeControllerWrapper.getParameter("passWord");
		Integer accountType = AcctTypeEnum.BASIC_CNY.getCode();
		String pwdError = paymentPasswordValidateService.validatePaymentPassword(memberCode, accountType,operatorId, payPwd);
		return pwdError;
	}

	/**
	 * 返回用户绑定的银行列表
	 * 
	 * @param memberCode
	 * @return
	 */
	private List<BankCardBindBO> getBankList(Long memberCode) {
		List<BankCardBindBO> bankList = null;
		try {
			bankList = bankCardBindService.doQueryBankCardBindInfoForVerifyNsTx(memberCode);
		} catch (MaBankCardBindException e) {
			log.error("获取银行卡失败:" + e);
			e.printStackTrace();
		} finally {
			if (bankList == null) {
				bankList = new ArrayList<BankCardBindBO>();
			}
		}
		return bankList;
	}

	/**
	 * 得到单笔限额
	 * 
	 * @param memberCode
	 * @return
	 */
	private Long getSingleLimit(Long memberCode) {
		try {
			RCLimitResultDTO rcLimitResultDTO = foRcLimitFacade.getBusinessRcLimit(RCLIMITCODE.FO_ENTERPRISE_WITHDRAW.getKey(), null, memberCode);//企业用户
			return rcLimitResultDTO.getSingleLimit();
		} catch (Exception e) {
			log.error("获取风控参数出错:" + e);
			e.printStackTrace();
			return 0l;
		}
	}
	
	/**
	 * 查询该银行是否支持提现
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "FO_AUTOFUNDOUT")
	public ModelAndView getWithdrawChannel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String targetBankId = request.getParameter("targetBankId");
		if(StringUtils.isNotEmpty(targetBankId)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("targetBankId", targetBankId);//目的银行编号
			map.put("foMode", "1");//出款方式:暂时为1手工,以后直连接入在进行修改
			map.put("fobusiness", String.valueOf(0));//出款业务:提现0
			String outBankCode = configBankService.queryFundOutBank2Withdraw(map);
			String supportWithdraw = "1";
			if (StringUtils.isEmpty(outBankCode)) {
				supportWithdraw = "0";
			}
			try {
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(supportWithdraw);
			} catch (IOException e) {
				log.info("获取输出流出错");
			}
		}
		return null;
	}

	
	
}
