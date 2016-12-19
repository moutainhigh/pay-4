/**
 * 
 */
package com.pay.gateway.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.gateway.dto.CardBindRequest;
import com.pay.gateway.dto.ChinaLocalPayRequest;
import com.pay.gateway.dto.CrosspayApiRecurringRequest;
import com.pay.gateway.dto.CrosspayApiRequest;
import com.pay.gateway.dto.CrosspayGatewayRequest;
import com.pay.gateway.dto.CrosspayRequest;
import com.pay.gateway.dto.OrderApiQueryRequest;
import com.pay.gateway.dto.PreauthApiRequest;
import com.pay.gateway.dto.PreauthCompApiRequest;
import com.pay.gateway.dto.TokenCrosspayApiRequest;
import com.pay.gateway.dto.TokenCrosspayRequest;
import com.pay.gateway.dto.TokenpayRequest;
import com.pay.gateway.dto.TradeRevokeApiRequest;
import com.pay.gateway.dto.WechatAlipayRequest;
import com.pay.gateway.dto.cashier.CashierRequest;
import com.pay.gateway.dto.refund.RefundApiRequest;
import com.pay.inf.enums.SerCode;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 交易服务接口
 * 
 * @author chaoyue
 *
 */
public class TxncoreClientService {

	private Log logger = LogFactory.getLog(getClass());
	private HessianInvokeService invokeService;

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}

	/**
	 * 跨境API收单
	 * 
	 * @return
	 */
	public Map crosspayApiAcquire(CrosspayApiRequest crosspayApiRequest,Map<String,String> dccMap) {

		Map<String, String> paraMap = MapUtil.bean2map(crosspayApiRequest);
		
		if(dccMap!=null&&!dccMap.isEmpty()){
			paraMap.put("prdtCode",dccMap.get("prdtCode"));
			paraMap.put("dccCurrencyCode",dccMap.get("dccCurrencyCode"));
		}else{
			paraMap.put("prdtCode","EDC");
		}
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_API_ACQUIRE.getCode(), sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
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
	 * 跨境API收单
	 * 
	 * @return
	 */
	public Map<String,Object> crosspayApiAcquire(CrosspayRequest crosspayApiRequest,Map<String,String> dccMap) {

		Map<String, String> paraMap = MapUtil.bean2map(crosspayApiRequest);
		
		if(dccMap!=null&&!dccMap.isEmpty()){
			paraMap.put("prdtCode",dccMap.get("prdtCode"));
			paraMap.put("dccCurrencyCode",dccMap.get("dccCurrencyCode"));
		}else{
			paraMap.put("prdtCode","EDC");
		}
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_API_ACQUIRE.getCode(), sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
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
	 * 跨境-》威富通API收单
	 * 
	 * @return
	 */
	public Map<String,Object> crosspayApiWftAcquire(WechatAlipayRequest crosspayApiRequest,Map<String,String> dccMap) {
		
		Map<String, String> paraMap = MapUtil.bean2map(crosspayApiRequest);
		
		if(dccMap!=null&&!dccMap.isEmpty()){
			paraMap.put("prdtCode",dccMap.get("prdtCode"));
			paraMap.put("dccCurrencyCode",dccMap.get("dccCurrencyCode"));
		}else{
			paraMap.put("prdtCode","EDC");
		}
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				"txncore.crosspayApiWftAcquireHandler", sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
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
	 * 预授权申请
	 * @return
	 */
	public Map<String,Object> crosspayPreauth(CrosspayRequest crosspayApiRequest
			       ,Map<String,String> dccMap) {
		Map<String, String> paraMap = MapUtil.bean2map(crosspayApiRequest);
		if(dccMap!=null&&!dccMap.isEmpty()){
			paraMap.put("prdtCode",dccMap.get("prdtCode"));
			paraMap.put("dccCurrencyCode",dccMap.get("dccCurrencyCode"));
		}else{
			paraMap.put("prdtCode","EDC");
		}
	
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				"txncore.preAuth", sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
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
	 * 预授权完成
	 * @return
	 */
	public Map<String,Object> crosspayPreauthCompleted(Map<String, String> paraMap) {		
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				"txncore.preAuthProcess", sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
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
	 * 预授权撤消
	 * @return
	 */
	public Map<String,Object> crosspayPreauthVoid(Map<String, String> paraMap) {		
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				"txncore.preAuthProcess", sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
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
	 * 创建token及预授权-API
	 * @return
	 */
	public Map<String,Object> crosspayTokenPreauthAPI(CrosspayRequest crosspayApiRequest
			       ,Map<String,String> dccMap) {
		Map<String, String> paraMap = MapUtil.bean2map(crosspayApiRequest);
		if(dccMap!=null&&!dccMap.isEmpty()){
			paraMap.put("prdtCode",dccMap.get("prdtCode"));
			paraMap.put("dccCurrencyCode",dccMap.get("dccCurrencyCode"));
		}else{
			paraMap.put("prdtCode","EDC");
		}
	
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				"txncore.createTokenPreauthAPI", sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
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
	 * token预授权
	 * @return
	 */
	public Map<String,Object> crosspayTokenPreauth(CrosspayRequest crosspayApiRequest
			,Map<String,String> dccMap) {
		Map<String, String> paraMap = MapUtil.bean2map(crosspayApiRequest);
		if(dccMap!=null&&!dccMap.isEmpty()){
			paraMap.put("prdtCode",dccMap.get("prdtCode"));
			paraMap.put("dccCurrencyCode",dccMap.get("dccCurrencyCode"));
		}else{
			paraMap.put("prdtCode","EDC");
		}
		
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				"txncore.tokenPreauth", sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		
		return resultMap;
	}
	
	public Map<String,Object> crosspayApiAcquire(TokenCrosspayRequest crosspayApiRequest,Map<String,String> dccMap) {

		Map<String, String> paraMap = MapUtil.bean2map(crosspayApiRequest);
		
		if(dccMap!=null&&!dccMap.isEmpty()){
			paraMap.put("prdtCode",dccMap.get("prdtCode"));
			paraMap.put("dccCurrencyCode",dccMap.get("dccCurrencyCode"));
		}else{
			paraMap.put("prdtCode","EDC");
		}
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_API_ACQUIRE.getCode(), sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
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
	 * 跨境API 3D收单
	 * 
	 * @return
	 */
	public Map crosspayApi3DAcquire(CrosspayApiRequest crosspayApiRequest,Map<String,String> dccMap) {

		Map<String, String> paraMap = MapUtil.bean2map(crosspayApiRequest);
		
		if(dccMap!=null&&!dccMap.isEmpty()){
			paraMap.put("prdtCode",dccMap.get("prdtCode"));
			paraMap.put("dccCurrencyCode",dccMap.get("dccCurrencyCode"));
		}else{
			paraMap.put("prdtCode","EDC");
		}
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				"30212", sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		return resultMap;
	}
	
	public Map crosspayApi3DAcquire(TokenCrosspayApiRequest crosspayApiRequest,Map<String,String> dccMap) {

		Map<String, String> paraMap = MapUtil.bean2map(crosspayApiRequest);
		
		if(dccMap!=null&&!dccMap.isEmpty()){
			paraMap.put("prdtCode",dccMap.get("prdtCode"));
			paraMap.put("dccCurrencyCode",dccMap.get("dccCurrencyCode"));
		}else{
			paraMap.put("prdtCode","EDC");
		}
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				"30212", sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
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
	 * 跨境API 3D收单
	 * 
	 * @return
	 */
	public Map<String,Object> crosspayApi3DAcquire(CrosspayRequest crosspayApiRequest,Map<String,String> dccMap) {

		Map<String, String> paraMap = MapUtil.bean2map(crosspayApiRequest);
		
		if(dccMap!=null&&!dccMap.isEmpty()){
			paraMap.put("prdtCode",dccMap.get("prdtCode"));
			paraMap.put("dccCurrencyCode",dccMap.get("dccCurrencyCode"));
		}else{
			paraMap.put("prdtCode","EDC");
		}
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				"30212", sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		return resultMap;
	}
	
	public Map<String,Object> crosspayApi3DAcquire(TokenCrosspayRequest crosspayApiRequest,Map<String,String> dccMap) {

		Map<String, String> paraMap = MapUtil.bean2map(crosspayApiRequest);
		
		if(dccMap!=null&&!dccMap.isEmpty()){
			paraMap.put("prdtCode",dccMap.get("prdtCode"));
			paraMap.put("dccCurrencyCode",dccMap.get("dccCurrencyCode"));
		}else{
			paraMap.put("prdtCode","EDC");
		}
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				"30212", sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		return resultMap;
	}
	
		/***
	 * 循环扣款调用核心
	 *
	 * @return map
	 */
	public Map crosspayApiAcquire(CrosspayApiRecurringRequest crosspayApiRequest,Map<String,String> dccMap) {
		
		Map<String, String> paraMap = MapUtil.bean2map(crosspayApiRequest);
		
		if(dccMap!=null&&!dccMap.isEmpty()){
			paraMap.put("prdtCode",dccMap.get("prdtCode"));
			paraMap.put("dccCurrencyCode",dccMap.get("dccCurrencyCode"));
		}
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_API_ACQUIRE.getCode(), sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
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
	 * 跨境本地化收单
	 * @param crosspayRequestDTO
	 * @return
	 * @author  delin.dong
	 */
	
	public Map crosspayLocaleAcquire(CrosspayGatewayRequest crosspayRequestDTO) {

		Map<String, String> paraMap = MapUtil.bean2map(crosspayRequestDTO);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_LOCALE_ACQUIRE.getCode(), sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
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
	 * 跨境API 3D 结果回调
	 * @author liu
	 * @return
	 */
	public Map crosspayApi3DCallBack(Map crosspayApiRequest) {

				
		String reqMsg = JSonUtil.toJSonString(crosspayApiRequest);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				"30213", sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
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
	 * 系统接口存活检测
	 * 
	 * @return
	 */
	public String systemDetection(String memberCode,String orgCode) {
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("memberCode",memberCode);
		paraMap.put("orgCode",orgCode);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_SYSTEM_DETECTED.getCode(), sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		String resultCode = (String) resultMap.get("responseCode");
		return resultCode;
	}
	
	/**
	 * 信用卡预授权Api
	 * 
	 * @return
	 */
	public Map preauthApiAcquire(PreauthApiRequest preauthApiRequest) {
		Map<String, String> paraMap = MapUtil.bean2map(preauthApiRequest);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_PREAUTH_API_ACQUIRE.getCode(), sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
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
	 * 交易撤销
	 * 
	 * @return
	 */
	public Map tradeRevokeApiAcquire(TradeRevokeApiRequest tradeRevokeApiRequest) {
    	Map<String, String> paraMap = MapUtil.bean2map(tradeRevokeApiRequest);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_TRADEREVOKE_API_ACQUIRE.getCode(), sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
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
	 * 信用卡预授权完成Api
	 * 
	 * @return
	 */
	public Map preauthCompApiAcquire(PreauthCompApiRequest preauthCompApiRequest) {

		Map<String, String> paraMap = MapUtil.bean2map(preauthCompApiRequest);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_PREAUTHCOMP_API_ACQUIRE.getCode(), sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
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
	 * 跨境业务收银台收单
	 * 
	 * @param crosspayRequestDTO
	 * @param gatewayResponseDTO
	 */
	public Map crosspayCashierAcquire(CrosspayGatewayRequest crosspayRequestDTO) {

		Map<String, String> paraMap = MapUtil.bean2map(crosspayRequestDTO);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_CASHIER_ACQUIRE.getCode(), sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
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
	 * 跨境业务收银台收单
	 * 
	 * @param crosspayRequestDTO
	 * @param gatewayResponseDTO
	 */
	public Map<String,Object> crosspayCashierAcquire(CrosspayRequest crosspayRequestDTO) {
		Map<String, String> paraMap = MapUtil.bean2map(crosspayRequestDTO);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		//TXNCORE_CROSSPAY_CASHIER_ACQUIRE==30102
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_CASHIER_ACQUIRE.getCode(), sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		return resultMap;
	}
	
	public Map cashierAcquire(CashierRequest crosspayRequestDTO) {
		
		Map<String, String> paraMap = MapUtil.bean2map(crosspayRequestDTO);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_CASHIER_ACQUIRE.getCode(), sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		
		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		
		return resultMap;
	}
	
	public Map<String,String> queryOrgRateInfo(final Map<String, String> queryMap) {

		String reqMsg = JSonUtil.toJSonString(queryMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.GATEWAY.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_ORG_RATE_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.GATEWAY.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String,String> resultMap = JSonUtil.toObject(result,
				new HashMap<String,String>().getClass());

		return resultMap;
	}
	
	/**
	 * 基本汇率获取
	 * 
	 * @return
	 */
	public Map<String,Object> getBaseRate(Map<String, String> paraMap) {

		String reqMsg = JSonUtil.toJSonString(paraMap);
		
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.GATEWAY.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_BASE_RATE_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.GATEWAY.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		
		String hasRate = (String) resultMap.get("hasRate");
		
		if("0".equals(hasRate))
			return null;
		return resultMap;
	}
	

	/**
	 * 跨境业务退款申请
	 * 
	 * @param 
	 */
	public Map crosspayRefund(RefundApiRequest refundApiRequest) {
		Map<String, String> paraMap = MapUtil.bean2map(refundApiRequest);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_ORDER_REFUND.getCode(), sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), 
				param.getDataLength(),
				param.getMsgCompress(), 
				param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		return resultMap;
	}

	/**
	 * 跨境订单查询
	 * 
	 * @param crosspayRequestDTO
	 */
	public Map crosspayOrderQuery(OrderApiQueryRequest orderApiQueryRequest) {

		Map<String, String> paraMap = MapUtil.bean2map(orderApiQueryRequest);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_ORDER_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
		}

		return resultMap;
	}
	
	/**
	 * 跨境订单查询
	 * 
	 * @param crosspayRequestDTO
	 */
	public Map crosspayOrderQuery_(Map<String, String> paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_ORDER_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
		}

		return resultMap;
	}
	
	/**
	 * 查询汇率
	 * 
	 * @return
	 */
	public Map getCurrencyRate(Map<String, String> paraMap) {

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
		
		String hasRate = (String) resultMap.get("hasRate");
		
		if("0".equals(hasRate))
			return null;
		return resultMap;
	}
	
	/**
	 * 查询汇率
	 * 
	 * @return
	 */
	public Map<String,Object> getDccRate(Map<String, String> paraMap) {

		String reqMsg = JSonUtil.toJSonString(paraMap);
		
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.GATEWAY.getCode());
		String result = invokeService.invoke(
				"txncore.dccRateQuery", sysTraceNo,
				SystemCodeEnum.GATEWAY.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		
		String hasRate = (String) resultMap.get("hasRate");
		
		if("0".equals(hasRate))
			return null;
		return resultMap;
	}
	
	/**
	 * 完成订单查询
	 * 
	 * @param crosspayRequestDTO
	 */
	public Map<String,Object> completedOrderQuery(Map<String, String> paraMap) {

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_COMPLETED_ORDER_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
		}

		return resultMap;
	}
	
	/**
	 * 完成订单查询
	 * 
	 * @param crosspayRequestDTO
	 */
	public Map refundOrderQuery(Map<String, String> paraMap) {

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_REFUND_APP_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
		}

		return resultMap;
	}
	

	/**
	 * 获取商户配置域名
	 * 
	 * @param partnerId
	 * @param siteId
	 * @return
	 */
	public List<Map> crosspayMerchantWebsiteQuery(String partnerId,
			String siteId, String status) {

		Map<String, String> paraMap = new HashMap();
		paraMap.put("partnerId", partnerId);
		paraMap.put("url", siteId);
		paraMap.put("statusIn", status); //多个状态查询
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_MERCHANT_WEBSITE_QUERY.getCode(),
				sysTraceNo, SystemCodeEnum.WEBGATE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		List<Map> returnMap = (List<Map>) resultMap.get("result");

		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
			logger.info("returnMap:" + returnMap);
		}

		return returnMap;
	}

	public Map<String,String> crosspayPartnerConfigQuery(String partnerId, String code) {

		Map<String, String> paraMap = new HashMap();
		paraMap.put("partnerId", partnerId);
		paraMap.put("code", code);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_PARTNERCONFIG_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		Map<String, String> returnMap = (Map) resultMap.get("result");

		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
			logger.info("returnMap:" + returnMap);
		}

		return returnMap;
	}
	
	public Map bindCardAcquire(CardBindRequest crosspayRequestDTO) {
		Map<String, String> paraMap = MapUtil.bean2map(crosspayRequestDTO);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				"txncore.cardBindAndUnbindHandler", sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		return resultMap;
	}
	
	public Map tokenpayAcquire(TokenpayRequest crosspayRequestDTO, String status) {
		Map<String, String> paraMap = MapUtil.bean2map(crosspayRequestDTO);
		paraMap.put("status", status);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				"txncore.tokenPayInfoQueryHandler", sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
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
	 * token绑上信息查询-new
	 * @param crosspayRequest
	 * @param status
	 * @return
	 */
	public Map tokenpayAcquire_new(CrosspayRequest crosspayRequest, String status) {
		Map<String, String> paraMap = MapUtil.bean2map(crosspayRequest);
		paraMap.put("status", status);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				"txncore.tokenPayInfoQueryHandler", sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		
		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		
		return resultMap;
	}
	
	public Map addErrorCardBindAcquire(CardBindRequest crosspayRequestDTO, String type) {
		Map<String, String> paraMap = MapUtil.bean2map(crosspayRequestDTO);
		paraMap.put("type", type);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				"txncore.addErrorCardBindOrderHandler", sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		return resultMap;
	}
	
	
	public Map updateErrorCardBindAcquire(CardBindRequest crosspayRequestDTO) {
		Map<String, String> paraMap = MapUtil.bean2map(crosspayRequestDTO);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				"txncore.updateErrorCardBindOrderHandler", sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		return resultMap;
	}
	
	/**Token绑卡并支付
	 * @param crosspayApiRequest 
	 * @param dccMap
	 * @return
	 */
	public Map<String,Object> createTokenAndPay(CrosspayRequest crosspayApiRequest,Map<String,String> dccMap){
		Map<String, String> paraMap = MapUtil.bean2map(crosspayApiRequest);
		if(dccMap!=null&&!dccMap.isEmpty()){
			paraMap.put("prdtCode",dccMap.get("prdtCode"));
			paraMap.put("dccCurrencyCode",dccMap.get("dccCurrencyCode"));
		}else{
			paraMap.put("prdtCode","EDC");
		}
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				"txncore.createTokenAndPayHandler", sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
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
	 * tokenAPI绑卡、解卡
	 * @return
	 */
	public Map<String,Object> bindCardToken(CardBindRequest crosspayRequestDTO) {
		Map<String, String> paraMap = MapUtil.bean2map(crosspayRequestDTO);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				"txncore.cardBindHandler", sysTraceNo,
								SystemCodeEnum.WEBGATE.getCode(),
								SystemCodeEnum.TXNCORE.getCode(),
								SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
								param.getMsgCompress(), param.getDataMsg());
						param.parse(result);
						HessianInvokeHelper.processResponse(param);
						result = param.getDataMsg();
						Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
						return resultMap;
	}

	public Map findByValidate(CrosspayRequest crosspayRequestDTO) {
		Map<String, String> paraMap = MapUtil.bean2map(crosspayRequestDTO);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				"txncore.tokenPayValidation", sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
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
	 * 使用tokenpay
	 * @return
	 */
	public Map<String,Object> tokenPay(CrosspayRequest crosspayApiRequest
			,Map<String,String> dccMap) {
		Map<String, String> paraMap = MapUtil.bean2map(crosspayApiRequest);
		if(dccMap!=null&&!dccMap.isEmpty()){
			paraMap.put("prdtCode",dccMap.get("prdtCode"));
			paraMap.put("dccCurrencyCode",dccMap.get("dccCurrencyCode"));
		}else{
			paraMap.put("prdtCode","EDC");
		}
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				"txncore.tokenPayHandler", sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
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
	 * 使用token预授权
	 * @return
	 */
	public Map<String,Object> tokenPreAuth(CrosspayRequest crosspayApiRequest
			,Map<String,String> dccMap) {
		Map<String, String> paraMap = MapUtil.bean2map(crosspayApiRequest);
		if(dccMap!=null&&!dccMap.isEmpty()){
			paraMap.put("prdtCode",dccMap.get("prdtCode"));
			paraMap.put("dccCurrencyCode",dccMap.get("dccCurrencyCode"));
		}else{
			paraMap.put("prdtCode","EDC");
		}

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				"txncore.tokenPreAuthHandler", sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		return resultMap;
	}

	public Map unBindCard(CrosspayRequest crosspayRequestDTO) {
		Map<String, String> paraMap = MapUtil.bean2map(crosspayRequestDTO);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				"txncore.cardBindAndUnbindHandler", sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
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
	 * 跨境API收单
	 * 
	 * @return
	 */
	public Map<String,Object> localApiAcquire(ChinaLocalPayRequest localpayApiRequest) {
		Map<String, String> paraMap = MapUtil.bean2map(localpayApiRequest);
		paraMap.put("prdtCode","EDC");
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				"txncore.chinaLocalAcquireHandler", sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
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
	 * 跨境API 3D收单
	 * 
	 * @return
	 */
	public Map<String,Object> localpayApi3DAcquire(ChinaLocalPayRequest localpayApiRequest) {

		Map<String, String> paraMap = MapUtil.bean2map(localpayApiRequest);
		paraMap.put("prdtCode","EDC");
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_API_ACQUIRE.getCode(), sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		return resultMap;
	}
}
