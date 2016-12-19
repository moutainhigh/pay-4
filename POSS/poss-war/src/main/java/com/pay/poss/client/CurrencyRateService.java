/**
 * 
 */
package com.pay.poss.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.enums.SerCode;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.poss.dto.CurrencyExchangeRate;
import com.pay.poss.dto.CurrencyRateAdjust;
import com.pay.poss.dto.PartnerCurrencyRate;
import com.pay.util.JSonUtil;

/**
 * 货币汇率服务
 * 
 * @author chaoyue
 * 清结算核心
 */
public class CurrencyRateService {

	private HessianInvokeService invokeService;

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}

	public Map<String, String> currencyRateAdd(List<CurrencyExchangeRate> list,
			String currencyUnit, String targetCurrency, String operator) {

		Map paraMap = new HashMap();
		paraMap.put("list", list);
		paraMap.put("currencyUnit", currencyUnit);
		paraMap.put("targetCurrency", targetCurrency);
		paraMap.put("operator", operator);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CURRENCYRATE_CREATE.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		return resultMap;
	}
	
	/***
	 * 查询清算汇率
	 * @param currency
	 * @param targetCurrency
	 * @param memberCode
	 * @param status
	 * @return
	 */
	public Map getTransactionRate(String currency, String targetCurrency,
			String memberCode, String status) {

		Map<String, String> paraMap = new HashMap();
		paraMap.put("memberCode",memberCode);
		paraMap.put("currency", currency);
		paraMap.put("type","2");
		paraMap.put("targetCurrency", targetCurrency);
		paraMap.put("status",status);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.GATEWAY.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CURREBCR_RATE_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.GATEWAY.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
	/*	String hasRate = (String) resultMap.get("hasRate");
		
		if("0".equals(hasRate))
			return null;
*/
		
		return resultMap;
	}

	/**
	 * 清算基本汇率添加
	 * @param list
	 * @param currencyUnit
	 * @param targetCurrency
	 * @param operator
	 * @return
	 */
	public Map<String, String> settlementBaseRateAdd(List<CurrencyExchangeRate> list,
			String currencyUnit, String targetCurrency, String operator) {

		Map paraMap = new HashMap();
		paraMap.put("list", list);
		paraMap.put("currencyUnit", currencyUnit);
		paraMap.put("operator", operator);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_SETTLEMENTRATE_BASE_CREATE.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		return resultMap;
	}
	
	/**
	 * 清算汇率添加
	 * @param list
	 * @param currencyUnit
	 * @param targetCurrency
	 * @param operator
	 * @return
	 */
	public Map<String, String> settlementRateAdd(List<PartnerCurrencyRate> list,
			String currencyUnit, String operator) {

		Map paraMap = new HashMap();
		paraMap.put("list", list);
		paraMap.put("currencyUnit", currencyUnit);
		paraMap.put("operator", operator);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_SETTLEMENTRATE_CREATE.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		return resultMap;
	}
	
	
	public Map<String, Object> partnerRateFloatAdd(Map<String,String> paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_PARTNER_RATE_FLOAT_CREATE.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		return resultMap;
	}
	
	
	/**
	 * 清算汇率微调添加
	 * @param list
	 * @param currencyUnit
	 * @param targetCurrency
	 * @param operator
	 * @return
	 */
	public Map<String, String> settlementRateAdjustAdd(List<CurrencyRateAdjust> list,
			 String operator) {

		Map paraMap = new HashMap();
		paraMap.put("list", list);
		paraMap.put("operator", operator);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_SETTLEMENTRATE_ADJUST_CREATE.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		return resultMap;
	}
	
	/**
	 * 交易基本汇率添加
	 * @param list
	 * @param currencyUnit
	 * @param targetCurrency
	 * @param operator
	 * @return
	 */
	public Map<String, String> transactionBaseRateAdd(List<CurrencyExchangeRate> list,
			String currencyUnit, String targetCurrency, String operator) {

		Map paraMap = new HashMap();
		paraMap.put("list", list);
		paraMap.put("currencyUnit", currencyUnit);
		paraMap.put("operator", operator);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_TRANSACTIONRATE_BASE_CREATE.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		return resultMap;
	}
	
	/**
	 * 交易汇率添加
	 * @param list
	 * @param currencyUnit
	 * @param targetCurrency
	 * @param operator
	 * @return
	 */
	public Map<String, String> transactionRateAdd(List<PartnerCurrencyRate> list,
			String currencyUnit, String targetCurrency, String operator) {

		Map paraMap = new HashMap();
		paraMap.put("list", list);
		paraMap.put("currencyUnit", currencyUnit);
		paraMap.put("operator", operator);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_TRANSACTIONRATE_CREATE.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		return resultMap;
	}
	
	/**
	 * 交易汇率添加
	 * @param list
	 * @param currencyUnit
	 * @param targetCurrency
	 * @param operator
	 * @return
	 */
	public Map<String, String> transactionRateAdjustAdd(List<CurrencyRateAdjust> list,String operator) {

		Map paraMap = new HashMap();
		paraMap.put("list", list);
		paraMap.put("operator", operator);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_TRANSACTIONRATE_ADJUST_CREATE.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		return resultMap;
	}
	
	

	/**
	 * 获取商户配置域名
	 * String currency, String targetCurrency,
			String effectDate, String expireDate,String status
	 * @param partnerId
	 * @param siteId
	 * @return
	 */
	public Map<String,Object> currencyRateQuery(Map<String,Object> params) {

		String reqMsg = JSonUtil.toJSonString(params);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_SETTLEMENTRATE_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		return resultMap;
	}
	
	/**
	 * 交易基本汇率查询
	 * @param partnerId
	 * @param siteId
	 * @return
	 */
	public Map<String,Object> transactionBaseRateQuery(Map<String,Object> params) {

		String reqMsg = JSonUtil.toJSonString(params);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_TRANSACTIONRATE_BASE_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		return resultMap;
	}
	
	/**
	 * 交易汇率查询
	 * @param partnerId
	 * @param siteId
	 * @return
	 */
	public Map<String,Object> transactionRateQuery(Map<String,Object> params) {

		String reqMsg = JSonUtil.toJSonString(params);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_TRANSACTIONRATE_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		return resultMap;
	}
	
	/**
	 * 交易汇率查询
	 * @param partnerId
	 * @param siteId
	 * @return
	 */
	public Map<String,Object> transRateMarkupQuery(Map<String,Object> params) {

		String reqMsg = JSonUtil.toJSonString(params);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_TRANSRATEMARKUP_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		return resultMap;
	}
	
	/**
	 * 交易汇率查询
	 * @param partnerId
	 * @param siteId
	 * @return
	 */
	public Map<String,Object> transRateMarkupAdd(Map<String,Object> params) {
		String reqMsg = JSonUtil.toJSonString(params);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_TRANSRATEMARKUP_CREATE.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		return resultMap;
	}
	
	/**
	 * 交易汇率查询
	 * @param partnerId
	 * @param siteId
	 * @return
	 */
	public Map<String,Object> transRateMarkupUpdate(Map<String,String> params) {
		String reqMsg = JSonUtil.toJSonString(params);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_TRANSRATEMARKUP_UPDATE.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		return resultMap;
	}
	
	/**
	 * 交易汇率查询
	 * @param partnerId
	 * @param siteId
	 * @return
	 */
	public Map<String,Object> transactionRateAdjustQuery(Map<String,Object> params) {

		String reqMsg = JSonUtil.toJSonString(params);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_TRANSACTIONRATE_ADJUST_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		return resultMap;
	}
	
	/**
	 * 清算基本汇率查询
	 * @param partnerId
	 * @param siteId
	 * @return
	 */
	public Map<String,Object> settlementBaseRateQuery(Map<String,Object> params) {

		String reqMsg = JSonUtil.toJSonString(params);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_SETTLEMENTRATE_BASE_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		return resultMap;
	}
	
	/**
	 * 清算汇率查询
	 * @param partnerId
	 * @param siteId
	 * @return
	 */
	public Map<String,Object> settlementRateQuery(Map<String,Object> params) {
		String reqMsg = JSonUtil.toJSonString(params);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_SETTLEMENTRATE_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		return resultMap;
	}
	
	/**
	 * 清算汇率查询
	 * @param partnerId
	 * @param siteId
	 * @return
	 */
	public Map<String,Object> partnerRateFloatQuery(Map<String,Object> params) {
		String reqMsg = JSonUtil.toJSonString(params);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_PARTNER_RATE_FLOAT_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		return resultMap;
	}
	
	/**
	 * 清算汇率查询
	 * @param partnerId
	 * @param siteId
	 * @return
	 */
	public Map<String,Object> partnerRateFloatDelete(Map<String,Object> params) {
		String reqMsg = JSonUtil.toJSonString(params);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_PARTNER_RATE_FLOAT_DELETE.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		return resultMap;
	}
	
	/**
	 * 清算汇率查询
	 * @param partnerId
	 * @param siteId
	 * @return
	 */
	public Map<String,Object> settlementRateAdjustQuery(Map<String,Object> params) {
		String reqMsg = JSonUtil.toJSonString(params);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_SETTLEMENTRATE_ADJUST_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		return resultMap;
	}
	

	/**
	 * 创建机构汇率
	 * 
	 * @param list
	 * @param currencyUnit
	 * @param targetCurrency
	 * @param operator
	 * @return
	 */
	public Map<String, String> orgCurrencyRateAdd(String orgCode,
			List<CurrencyExchangeRate> list, String currencyUnit,
			String targetCurrency, String operator) {

		Map paraMap = new HashMap();
		paraMap.put("list", list);
		paraMap.put("orgCode", orgCode);
		paraMap.put("currencyUnit", currencyUnit);
		paraMap.put("targetCurrency", targetCurrency);
		paraMap.put("operator", operator);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_ORG_CURRENCYRATE_CREATE.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		return resultMap;
	}

	/**
	 * 获取机构配置汇率
	 * 
	 * @param partnerId
	 * @param siteId
	 * @return
	 */
	public List<Map> orgCurrencyRateQuery(String orgCode, String currency,
			String targetCurrency, String effectDate, String expireDate,String status) {

		Map<String, String> paraMap = new HashMap();
		paraMap.put("orgCode", orgCode);
		paraMap.put("currency", currency);
		paraMap.put("targetCurrency", targetCurrency);
		paraMap.put("effectDate", effectDate);
		paraMap.put("expireDate", expireDate);
		paraMap.put("status",status);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_ORG_CURRENCYRATE_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		List<Map> returnMap = (List<Map>) resultMap.get("list");

		return returnMap;
	}

	/**
	 * 创建商户汇率
	 * 
	 * @param list
	 * @param currencyUnit
	 * @param targetCurrency
	 * @param operator
	 * @return
	 */
	public Map<String, String> partnerCurrencyRateAdd(String partnerId,
			List<CurrencyExchangeRate> list, String currencyUnit,
			String targetCurrency, String operator) {

		Map paraMap = new HashMap();
		paraMap.put("list", list);
		paraMap.put("partnerId", partnerId);
		paraMap.put("currencyUnit", currencyUnit);
		paraMap.put("targetCurrency", targetCurrency);
		paraMap.put("operator", operator);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_PARTNER_CURRENCYRATE_CREATE.getCode(),
				sysTraceNo, SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		return resultMap;
	}

	/**
	 * 获取商户配置汇率
	 * 
	 * @param partnerId
	 * @param siteId
	 * @return
	 */
	public List<Map> partnerCurrencyRateQuery(String partnerId,
			String currency, String targetCurrency, String effectDate,
			String expireDate) {

		Map<String, String> paraMap = new HashMap();
		paraMap.put("partnerId", partnerId);
		paraMap.put("currency", currency);
		paraMap.put("targetCurrency", targetCurrency);
		paraMap.put("effectDate", effectDate);
		paraMap.put("expireDate", expireDate);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_PARTNER_CURRENCYRATE_QUERY.getCode(),
				sysTraceNo, SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		List<Map> returnMap = (List<Map>) resultMap.get("list");

		return returnMap;
	}

}
