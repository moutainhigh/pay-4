/**
 * 
 */
package com.pay.channel.client;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.pay.fi.commons.PaymentTypeEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.caucho.hessian.client.HessianProxyFactory;
import com.pay.channel.dao.ChannelConfigDAO;
import com.pay.channel.dao.ChannelSecondRelationDAO;
import com.pay.channel.dto.ChannelPreathResponseDto;
import com.pay.channel.dto.ChannelRequestDto;
import com.pay.channel.dto.ChannelResponseDto;
import com.pay.channel.dto.PaymentInfo;
import com.pay.channel.dto.PreauthInfo;
import com.pay.channel.model.ChannelConfig;
import com.pay.channel.model.ChannelSecondRelation;
import com.pay.channel.model.PaymentChannelItem;
import com.pay.fi.commons.TransTypeEnum;
import com.pay.fi.helper.ChannelItemOrgCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.enums.SerCode;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.jms.notification.request.RequestType;
import com.pay.jms.notification.request.WeiXinNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.util.DateUtil;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 调用前置接口
 * 
 * @author chaoyue
 *
 */
public class HandlerClientService {

	private final Log logger = LogFactory.getLog(getClass());
	private ChannelSecondRelationDAO channelSecondRelationDAO;
	private ChannelConfigDAO channelConfigDAO;
	private JmsSender jmsSender;
	private long readTimeOut;
	/**
	 * 调用前置
	 * 
	 * @param
	 * @return
	 */
	public Map<String, String> prePay(PaymentInfo channelRequestDto) {

		Map<String, String> resultMap = new HashMap<String, String>();
		String preServerUrl = channelRequestDto.getPreServerUrl();
		Map paraMap = MapUtil.bean2map(channelRequestDto);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());

		HessianProxyFactory factory = new HessianProxyFactory();
		HessianInvokeService hessianInvokeService = null;
		try {
			factory.setReadTimeout(readTimeOut);
			hessianInvokeService = (HessianInvokeService) factory.create(
					HessianInvokeService.class, preServerUrl);
			
			String result = hessianInvokeService.invoke(SerCode.PRE_PAY.getCode(),
					sysTraceNo, SystemCodeEnum.CHANNEL.getCode(),
					SystemCodeEnum.PRE.getCode(), SystemCodeEnum.PRE.getVersion(),
					param.getDataLength(), param.getMsgCompress(),
					param.getDataMsg());
			param.parse(result);
			HessianInvokeHelper.processResponse(param);
			result = param.getDataMsg();

			resultMap = JSonUtil.toObject(result, new HashMap().getClass());
			
		} catch (Exception e) {

			logger.error("call handler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc","（下单流程）连接银行前置异常！");
			Map<String,String> data = new HashMap<String, String>();

			data.put("first","(支付下单)渠道到银行前置连接异常请赶紧检查!");
			data.put("keyword1","(支付下单)渠道到银行前置连接异常请赶紧检查!");
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
	        data.put("keyword2",formatter.format(new Date()));
	        data.put("remark","请尽快检查！");
			
			this.sendAlertMsg(data);

			return resultMap;
		}

		if (logger.isInfoEnabled()) {
			logger.info("response result:" + resultMap);
		}

		return resultMap;
	}
	
	private void sendAlertMsg(Map<String,String> data){
		WeiXinNotifyRequest request = new WeiXinNotifyRequest();
        request.setBizCode("0015");
        request.setOpenId("0000");
        request.setType(RequestType.WEIXIN);
		
		request.setData(data);
		jmsSender.send(request);
	}
	
	/**
	 * 调用前置
	 * 
	 * @param
	 * @return
	 */
	public Map<String, String> systemDetected(PaymentInfo channelRequestDto) {

		Map<String, String> resultMap = new HashMap<String, String>();
		String preServerUrl = channelRequestDto.getPreServerUrl();
		Map paraMap = MapUtil.bean2map(channelRequestDto);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());

		HessianProxyFactory factory = new HessianProxyFactory();
		HessianInvokeService hessianInvokeService = null;
		try {
			hessianInvokeService = (HessianInvokeService) factory.create(
					HessianInvokeService.class, preServerUrl);
			
			String result = hessianInvokeService.invoke(SerCode.PRE_DETECTED.getCode(),
					sysTraceNo, SystemCodeEnum.CHANNEL.getCode(),
					SystemCodeEnum.PRE.getCode(), SystemCodeEnum.PRE.getVersion(),
					param.getDataLength(), param.getMsgCompress(),
					param.getDataMsg());
			param.parse(result);
			HessianInvokeHelper.processResponse(param);
			result = param.getDataMsg();

			resultMap = JSonUtil.toObject(result, new HashMap().getClass());
			
		} catch (Exception e) {
			logger.error("call handler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc","（系统检测）连接银行前置异常！");
			Map<String,String> data = new HashMap<String, String>();

			data.put("first","(系统检测)渠道到银行前置连接异常请赶紧检查!");
			data.put("keyword1","(系统检测)渠道到银行前置连接异常请赶紧检查!");
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
	        data.put("keyword2",formatter.format(new Date()));
	        data.put("remark","请尽快检查！");
			
			this.sendAlertMsg(data);

			return resultMap;
		}


		if (logger.isInfoEnabled()) {
			logger.info("response result:" + resultMap);
		}

		return resultMap;
	}

	private void setSecondMerchantInfo(PaymentChannelItem channelItem,
			Map<String, String> paraMap, String mcc, String currencyCode,
			String partnerId) {

		String queryCurrencyCode = null;
		if ("10075001".equals(channelItem.getOrgCode())) {
			queryCurrencyCode = currencyCode;
		}
		ChannelSecondRelation channelSecondRelation = channelSecondRelationDAO
				.findRelation(partnerId, channelItem.getOrgCode(),
						TransTypeEnum.EDC.getCode(), queryCurrencyCode);
		if (null != channelSecondRelation) {

			logger.info("找到二级商户配置："
					+ channelSecondRelation.getOrgMerchantCode());
			ChannelConfig channelConfig = new ChannelConfig();
			channelConfig.setOrgCode(channelItem.getOrgCode());
			channelConfig.setOrgMerchantCode(channelSecondRelation
					.getOrgMerchantCode());
			if ("10075001".equals(currencyCode)) {
				channelConfig.setCurrencyCode(currencyCode);
				channelConfig.setMcc(mcc);
			}

			channelConfig = channelConfigDAO
					.findObjectByCriteria(channelConfig);

			Map configMap = MapUtil.bean2map(channelConfig);
			paraMap.putAll(configMap);
		}
	}

	/**
	 * 调用前置预授权
	 * 
	 * @param
	 * @return
	 */
	public ChannelPreathResponseDto preauth(PaymentChannelItem channelItem,
			PreauthInfo channelRequestDto) {

		Map<String, String> paraMap = new HashMap();
		Map channelItemMap = MapUtil.bean2map(channelItem);
		paraMap.putAll(channelItemMap);

		ChannelSecondRelation channelSecondRelation = channelSecondRelationDAO
				.findRelation(channelRequestDto.getPartnerId(),
						channelItem.getOrgCode(), TransTypeEnum.EDC.getCode());
		if (null != channelSecondRelation) {
			ChannelConfig channelConfig = new ChannelConfig();
			channelConfig.setOrgCode(channelItem.getOrgCode());
			channelConfig.setOrgMerchantCode(channelSecondRelation
					.getOrgMerchantCode());
			channelConfig = channelConfigDAO
					.findObjectByCriteria(channelConfig);

			Map configMap = MapUtil.bean2map(channelConfig);
			paraMap.putAll(configMap);
		}

		ChannelPreathResponseDto channelPaymentResult = new ChannelPreathResponseDto();
		Map requestMap = MapUtil.bean2map(channelRequestDto);
		paraMap.putAll(requestMap);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());

		HessianProxyFactory factory = new HessianProxyFactory();
		HessianInvokeService hessianInvokeService = null;
		String result="";
		try {
			hessianInvokeService = (HessianInvokeService) factory.create(
					HessianInvokeService.class, channelItem.getPreServerUrl());
			
			result = hessianInvokeService.invoke(
					SerCode.PRE_PREAUTH.getCode(), sysTraceNo,
					SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.PRE.getCode(),
					SystemCodeEnum.PRE.getVersion(), param.getDataLength(),
					param.getMsgCompress(), param.getDataMsg());
			param.parse(result);
			HessianInvokeHelper.processResponse(param);
			result = param.getDataMsg();
			
		} catch (Exception e) {
			logger.error("call handler error:", e);
			String responseCode = ResponseCodeEnum.UNDEFINED_ERROR.getCode();
			String responseDesc = ResponseCodeEnum.UNDEFINED_ERROR.getDesc();
			channelPaymentResult.setResponseCode(responseCode);
			channelPaymentResult.setResponseDesc("（预授权）连接银行前置异常！");

			Map<String,String> data = new HashMap<String, String>();

			data.put("first","(预授权)渠道到银行前置连接异常请赶紧检查!");
			data.put("keyword1","(预授权)渠道到银行前置连接异常请赶紧检查!");
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
	        data.put("keyword2",formatter.format(new Date()));
	        data.put("remark","请尽快检查！");
			
			this.sendAlertMsg(data);
			return channelPaymentResult;
		}


		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		if (logger.isInfoEnabled()) {
			logger.info("response result:" + resultMap);
		}

		String responseCode =(String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		String authCode = (String) resultMap.get("authCode");
		String authorStr = (String) resultMap.get("authorStr");
		String refNo = (String) resultMap.get("refNo");
		String channelReturnNo = (String) resultMap
				.get("channelReturnNo");
		channelPaymentResult.setResponseCode(responseCode);
		channelPaymentResult.setResponseDesc(responseDesc);
		channelPaymentResult.setAuthCode(authCode);
		channelPaymentResult.setAuthorStr(authorStr);
		channelPaymentResult.setRefNo(refNo);
		
		channelPaymentResult.setOrgCode(channelItem.getOrgCode());
		if (!StringUtil.isEmpty(channelReturnNo)) {
			channelPaymentResult.setChannelReturnNo(channelReturnNo);
		}
		return channelPaymentResult;
	}

	/**
	 * 调用前置预授权完成
	 * 
	 * @param
	 * @return
	 */
	public ChannelResponseDto preauthCompeleted(PaymentChannelItem channelItem,
			PreauthInfo channelRequestDto) {

		Map<String, String> paraMap = new HashMap();
		Map channelItemMap = MapUtil.bean2map(channelItem);
		paraMap.putAll(channelItemMap);

		// 查找二级渠道
		ChannelSecondRelation channelSecondRelation = channelSecondRelationDAO
				.findRelation(channelRequestDto.getPartnerId(),
						channelItem.getOrgCode(), TransTypeEnum.EDC.getCode());
		if (null != channelSecondRelation) {
			ChannelConfig channelConfig = new ChannelConfig();
			channelConfig.setOrgCode(channelItem.getOrgCode());
			channelConfig.setOrgMerchantCode(channelSecondRelation
					.getOrgMerchantCode());
			channelConfig = channelConfigDAO
					.findObjectByCriteria(channelConfig);

			Map configMap = MapUtil.bean2map(channelConfig);
			paraMap.putAll(configMap);
		}

		ChannelResponseDto channelPaymentResult = new ChannelResponseDto();
		Map requestMap = MapUtil.bean2map(channelRequestDto);
		paraMap.putAll(requestMap);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());

		HessianProxyFactory factory = new HessianProxyFactory();
		HessianInvokeService hessianInvokeService = null;
		String result="";
		try {
			hessianInvokeService = (HessianInvokeService) factory.create(
					HessianInvokeService.class, channelItem.getPreServerUrl());
			
			result = hessianInvokeService.invoke(
					SerCode.PRE_PREAUTH_COMP.getCode(), sysTraceNo,
					SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.PRE.getCode(),
					SystemCodeEnum.PRE.getVersion(), param.getDataLength(),
					param.getMsgCompress(), param.getDataMsg());
			param.parse(result);
			HessianInvokeHelper.processResponse(param);
			result = param.getDataMsg();
			
		} catch (Exception e) {
			logger.error("call handler error:", e);
			String responseCode = ResponseCodeEnum.UNDEFINED_ERROR.getCode();
			String responseDesc = ResponseCodeEnum.UNDEFINED_ERROR.getDesc();
			channelPaymentResult.setResponseCode(responseCode);
			channelPaymentResult.setResponseDesc("（预授权完成）连接银行前置异常！");

			Map<String,String> data = new HashMap<String, String>();

			data.put("first","(预授权完成)渠道到银行前置连接异常请赶紧检查!");
			data.put("keyword1","(预授权完成)渠道到银行前置连接异常请赶紧检查!");
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
	        data.put("keyword2",formatter.format(new Date()));
	        data.put("remark","请尽快检查！");
			
			this.sendAlertMsg(data);
			return channelPaymentResult;
		}


		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		if (logger.isInfoEnabled()) {
			logger.info("response result:" + resultMap);
		}

		String responseCode = String.valueOf(resultMap.get("responseCode"));
		String responseDesc = String.valueOf(resultMap.get("responseDesc"));
		String payAmount = String.valueOf(resultMap.get("payAmount"));
		String channelReturnNo = String.valueOf(resultMap
				.get("channelReturnNo"));
		channelPaymentResult.setResponseCode(responseCode);
		channelPaymentResult.setResponseDesc(responseDesc);
		if (!StringUtil.isEmpty(payAmount)) {
			channelPaymentResult.setPayAmount(Long.valueOf(payAmount) * 10);
		}
		channelPaymentResult.setOrgCode(channelItem.getOrgCode());
		if (!StringUtil.isEmpty(channelReturnNo)) {
			channelPaymentResult.setChannelReturnNo(channelReturnNo);
		}
		return channelPaymentResult;
	}

	/**
	 * 调用前置预授权撤销
	 * 
	 * @param
	 * @return
	 */
	public ChannelResponseDto preauthRevoke(PaymentChannelItem channelItem,
			PreauthInfo channelRequestDto) {

		Map<String, String> paraMap = new HashMap();
		Map channelItemMap = MapUtil.bean2map(channelItem);
		paraMap.putAll(channelItemMap);

		ChannelSecondRelation channelSecondRelation = channelSecondRelationDAO
				.findRelation(channelRequestDto.getPartnerId(),
						channelItem.getOrgCode(), TransTypeEnum.EDC.getCode());
		if (null != channelSecondRelation) {
			ChannelConfig channelConfig = new ChannelConfig();
			channelConfig.setOrgCode(channelItem.getOrgCode());
			channelConfig.setOrgMerchantCode(channelSecondRelation
					.getOrgMerchantCode());
			channelConfig = channelConfigDAO
					.findObjectByCriteria(channelConfig);

			Map configMap = MapUtil.bean2map(channelConfig);
			paraMap.putAll(configMap);
		}

		ChannelResponseDto channelPaymentResult = new ChannelResponseDto();
		Map requestMap = MapUtil.bean2map(channelRequestDto);
		paraMap.putAll(requestMap);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());

		HessianProxyFactory factory = new HessianProxyFactory();
		HessianInvokeService hessianInvokeService = null;
		String result="";
		try {
			hessianInvokeService = (HessianInvokeService) factory.create(
					HessianInvokeService.class, channelItem.getPreServerUrl());
			
			result = hessianInvokeService.invoke(
					SerCode.PRE_PREAUTH_REVOKE.getCode(), sysTraceNo,
					SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.PRE.getCode(),
					SystemCodeEnum.PRE.getVersion(), param.getDataLength(),
					param.getMsgCompress(), param.getDataMsg());
			param.parse(result);
			HessianInvokeHelper.processResponse(param);
			result = param.getDataMsg();
			
		} catch (Exception e) {
			logger.error("call handler error:", e);
			String responseCode = ResponseCodeEnum.UNDEFINED_ERROR.getCode();
			String responseDesc = ResponseCodeEnum.UNDEFINED_ERROR.getDesc();
			channelPaymentResult.setResponseCode(responseCode);
			channelPaymentResult.setResponseDesc("(预授权撤销)渠道到银行前置连接异常");
			Map<String,String> data = new HashMap<String, String>();

			data.put("first","(预授权撤销)渠道到银行前置连接异常请赶紧检查!");
			data.put("keyword1","(预授权撤销)渠道到银行前置连接异常请赶紧检查!");
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
	        data.put("keyword2",formatter.format(new Date()));
	        data.put("remark","请尽快检查！");
			
			this.sendAlertMsg(data);
			return channelPaymentResult;
		}


		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		if (logger.isInfoEnabled()) {
			logger.info("response result:" + resultMap);
		}

		String responseCode = String.valueOf(resultMap.get("responseCode"));
		String responseDesc = String.valueOf(resultMap.get("responseDesc"));
		String payAmount = String.valueOf(resultMap.get("payAmount"));
		String channelReturnNo = String.valueOf(resultMap
				.get("channelReturnNo"));
		channelPaymentResult.setResponseCode(responseCode);
		channelPaymentResult.setResponseDesc(responseDesc);
		if (!StringUtil.isEmpty(payAmount)) {
			channelPaymentResult.setPayAmount(Long.valueOf(payAmount) * 10);
		}
		channelPaymentResult.setOrgCode(channelItem.getOrgCode());
		if (!StringUtil.isEmpty(channelReturnNo)) {
			channelPaymentResult.setChannelReturnNo(channelReturnNo);
		}
		return channelPaymentResult;
	}

	public ChannelResponseDto preRefund(PaymentChannelItem channelItem,
			ChannelRequestDto channelRequestDto) {

		ChannelResponseDto channelPaymentResult = new ChannelResponseDto();
		Map<String, String> paraMap = new HashMap();
		Map channelItemMap = MapUtil.bean2map(channelItem);
		paraMap.putAll(channelItemMap);

		ChannelConfig channelConfig = new ChannelConfig();
		channelConfig.setOrgMerchantCode(channelRequestDto.getMerchantNo());
		channelConfig.setOrgCode(channelRequestDto.getOrgCode());
		channelConfig = channelConfigDAO.findObjectByCriteria(channelConfig);

		if (channelConfig != null) {
			Map configMap = MapUtil.bean2map(channelConfig);
			paraMap.putAll(configMap);
		}

		Map requestMap = MapUtil.bean2map(channelRequestDto);
		paraMap.putAll(requestMap);
		paraMap.put("origChannelOrderNo",String.valueOf(channelRequestDto.getChannelOrderNo()));
		if (logger.isInfoEnabled()) {
			logger.info("refund request:" + requestMap);
		}

		// 判断退款还撤消
		String currentDate = DateUtil.formatDateTime(DateUtil.PATTERN,
				new Date());
		String tranDate = channelRequestDto.getTranDate();
		
		String tranDatetime = channelRequestDto.getTranDatetime();
		String transferDatetime = DateUtil.getTime(tranDatetime, 0, -6, 0);
		Date transferDate = DateUtil.parse(DateUtil.DEFAULT_DATE_FROMAT, transferDatetime);
		
		if(ChannelItemOrgCodeEnum.CREDORAX.getCode().equals(channelItem.getOrgCode())){
			tranDate = DateUtil.formatDateTime(DateUtil.PATTERN,
					transferDate);
		}

		String serCode = SerCode.PRE_REFUND.getCode();
		if (currentDate.equals(tranDate)) {// 交易撤消
			serCode = SerCode.PRE_CANCEL.getCode();
			
			BigDecimal refundAmount = new BigDecimal(channelRequestDto.getRefundAmount());
			BigDecimal orderAmount = new BigDecimal(channelRequestDto.getOrderAmount());
			
			if(refundAmount.compareTo(orderAmount)!=0){
				serCode = SerCode.PRE_REFUND.getCode();
			}
			
			paraMap.put("serialNo", channelRequestDto.getDealSerialNo());
		}

		if(SerCode.PRE_CANCEL.getCode().equals(serCode) && null != channelRequestDto.getVoidWay()){//如果是capture Void
			serCode = channelRequestDto.getVoidWay();
			channelPaymentResult.setCompleteStatus("1");
		}else{
			channelPaymentResult.setCompleteStatus("2");
		}

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());

		HessianProxyFactory factory = new HessianProxyFactory();
		HessianInvokeService hessianInvokeService = null;
		try {
			hessianInvokeService = (HessianInvokeService) factory.create(
					HessianInvokeService.class, channelItem.getPreServerUrl());
			
			String result = hessianInvokeService.invoke(serCode, sysTraceNo,
					SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.PRE.getCode(),
					SystemCodeEnum.PRE.getVersion(), param.getDataLength(),
					param.getMsgCompress(), param.getDataMsg());
			param.parse(result);
			HessianInvokeHelper.processResponse(param);
			result = param.getDataMsg();

			Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

			if (logger.isInfoEnabled()) {
				logger.info("前置退款返回信息.......，result:" + resultMap);
			}

			String responseCode = String.valueOf(resultMap.get("responseCode"));
			String responseDesc = String.valueOf(resultMap.get("responseDesc"));
			String channelReturnNo = String.valueOf(resultMap
					.get("channelReturnNo"));
			
			//add by nico.shao 2016-08-17 
			String referenceNo = String.valueOf(resultMap.get("reference_no"));
			if (!StringUtil.isEmpty(referenceNo)) {
				channelPaymentResult.setReferenceNo(referenceNo);
			}
			//end by nico.shao 2016-08-17
			
			channelPaymentResult.setResponseCode(responseCode);
			channelPaymentResult.setResponseDesc(responseDesc);

			channelPaymentResult.setOrgCode(channelItem.getOrgCode());
			if (!StringUtil.isEmpty(channelReturnNo)) {
				channelPaymentResult.setChannelReturnNo(channelReturnNo);
			}
			
			
		} catch (Exception e) {
			logger.error("call handler error:", e);
			String responseCode = ResponseCodeEnum.UNDEFINED_ERROR.getCode();
			channelPaymentResult.setResponseCode(responseCode);
			channelPaymentResult.setResponseDesc("（渠道退款）连接银行前置异常！");

			Map<String,String> data = new HashMap<String, String>();

			data.put("first","(渠道退款)渠道到银行前置连接异常请赶紧检查!");
			data.put("keyword1","(渠道退款)渠道到银行前置连接异常请赶紧检查!");
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
	        data.put("keyword2",formatter.format(new Date()));
	        data.put("remark","请尽快检查！");
			
			this.sendAlertMsg(data);
			
			return channelPaymentResult;
		}
		
		return channelPaymentResult;
	}
	
	/**
	 * 查询退款状态
	 * @param channelItem
	 * @param channelRequestDto
	 * @return
	 */
	public ChannelResponseDto qryRefundSts(PaymentChannelItem channelItem,
			ChannelRequestDto channelRequestDto) {

		ChannelResponseDto channelPaymentResult = new ChannelResponseDto();
		Map<String, String> paraMap = new HashMap();
		Map channelItemMap = MapUtil.bean2map(channelItem);
		paraMap.putAll(channelItemMap);

		ChannelConfig channelConfig = new ChannelConfig();
		channelConfig.setOrgMerchantCode(channelRequestDto.getMerchantNo());
		channelConfig.setOrgCode(channelRequestDto.getOrgCode());
		channelConfig = channelConfigDAO.findObjectByCriteria(channelConfig);

		if (channelConfig != null) {
			Map configMap = MapUtil.bean2map(channelConfig);
			paraMap.putAll(configMap);
		}

		Map requestMap = MapUtil.bean2map(channelRequestDto);
		paraMap.putAll(requestMap);
		paraMap.put("origChannelOrderNo",String.valueOf(channelRequestDto.getChannelOrderNo()));
		if (logger.isInfoEnabled()) {
			logger.info("refund request:" + requestMap);
		}

		// 判断退款还撤消
		String currentDate = DateUtil.formatDateTime(DateUtil.PATTERN,
				new Date());
		String tranDate = channelRequestDto.getTranDate();
		
		String tranDatetime = channelRequestDto.getTranDatetime();
		String transferDatetime = DateUtil.getTime(tranDatetime, 0, -6, 0);
		Date transferDate = DateUtil.parse(DateUtil.DEFAULT_DATE_FROMAT, transferDatetime);
		
		if(ChannelItemOrgCodeEnum.CREDORAX.getCode().equals(channelItem.getOrgCode())){
			tranDate = DateUtil.formatDateTime(DateUtil.PATTERN,
					transferDate);
		}

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());

		HessianProxyFactory factory = new HessianProxyFactory();
		HessianInvokeService hessianInvokeService = null;
		try {
			hessianInvokeService = (HessianInvokeService) factory.create(
					HessianInvokeService.class, channelItem.getPreServerUrl());
			
			String result = hessianInvokeService.invoke("bankser.queryHandler", sysTraceNo,
					SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.PRE.getCode(),
					SystemCodeEnum.PRE.getVersion(), param.getDataLength(),
					param.getMsgCompress(), param.getDataMsg());
			param.parse(result);
			HessianInvokeHelper.processResponse(param);
			result = param.getDataMsg();

			Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

			if (logger.isInfoEnabled()) {
				logger.info("前置退款状态查询返回信息.......，result:" + resultMap);
			}

			String responseCode = String.valueOf(resultMap.get("responseCode"));
			String responseDesc = String.valueOf(resultMap.get("responseDesc"));
			String channelReturnNo = String.valueOf(resultMap
					.get("channelReturnNo"));
			
			//add by nico.shao 2016-08-17 
			String referenceNo = String.valueOf(resultMap.get("reference_no"));
			if (!StringUtil.isEmpty(referenceNo)) {
				channelPaymentResult.setReferenceNo(referenceNo);
			}
			//end by nico.shao 2016-08-17
			
			channelPaymentResult.setResponseCode(responseCode);
			channelPaymentResult.setResponseDesc(responseDesc);

			channelPaymentResult.setOrgCode(channelItem.getOrgCode());
			if (!StringUtil.isEmpty(channelReturnNo)) {
				channelPaymentResult.setChannelReturnNo(channelReturnNo);
			}
			
			
		} catch (Exception e) {
			logger.error("call handler error:", e);
			String responseCode = ResponseCodeEnum.UNDEFINED_ERROR.getCode();
			channelPaymentResult.setResponseCode(responseCode);
			channelPaymentResult.setResponseDesc("（渠道退款状态查询）连接银行前置异常！");

			Map<String,String> data = new HashMap<String, String>();

			data.put("first","(渠道退款状态查询)渠道到银行前置连接异常请赶紧检查!");
			data.put("keyword1","(渠道退款状态查询)渠道到银行前置连接异常请赶紧检查!");
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
	        data.put("keyword2",formatter.format(new Date()));
	        data.put("remark","请尽快检查！");
			
			this.sendAlertMsg(data);
			
			return channelPaymentResult;
		}
		
		return channelPaymentResult;
	}

	public void setChannelSecondRelationDAO(
			ChannelSecondRelationDAO channelSecondRelationDAO) {
		this.channelSecondRelationDAO = channelSecondRelationDAO;
	}

	public void setChannelConfigDAO(ChannelConfigDAO channelConfigDAO) {
		this.channelConfigDAO = channelConfigDAO;
	}

	/**
	 * 查询渠道汇率
	 * 
	 * @param preServerUrl
	 * @param paraMap
	 * @return
	 */
	public Map<String, String> preOrgRateQuery(String preServerUrl,
			Map<String, String> paraMap) {

		Map<String, String> resultMap = new HashMap<String, String>();

		String reqMsg = JSonUtil.toJSonString(paraMap);
		logger.info("reqMsg: "+reqMsg);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());

		HessianProxyFactory factory = new HessianProxyFactory();
		HessianInvokeService hessianInvokeService = null;
		try {
			hessianInvokeService = (HessianInvokeService) factory.create(
					HessianInvokeService.class, preServerUrl);
			
			String result = hessianInvokeService.invoke(
					SerCode.PRE_RATE_QUERY.getCode(), sysTraceNo,
					SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.PRE.getCode(),
					SystemCodeEnum.PRE.getVersion(), param.getDataLength(),
					param.getMsgCompress(), param.getDataMsg());
			param.parse(result);
			HessianInvokeHelper.processResponse(param);
			result = param.getDataMsg();
			resultMap = JSonUtil.toObject(result, new HashMap().getClass());			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("call handler error:", e);
			resultMap.put("responseCode",ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseCode",ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc","（汇率查询）连接银行前置异常！");
			Map<String,String> data = new HashMap<String, String>();
			data.put("first","(汇率查询)渠道到银行前置连接异常请赶紧检查!");
			data.put("keyword1","(汇率查询)渠道到银行前置连接异常请赶紧检查!");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        data.put("keyword2",formatter.format(new Date()));
	        data.put("remark","请尽快检查！");		
			this.sendAlertMsg(data);
			return resultMap;
		}


		if (logger.isInfoEnabled()) {
			logger.info("response result:" + resultMap);
		}

		return resultMap;
	}

	/**
	 * 查询海南新生汇率
	 * 
	 * @param preServerUrl
	 * @param paraMap
	 * @return
	 */
	public Map<String, String> hnaRateQuery(String preServerUrl,
			Map<String, String> paraMap) {

		Map<String, String> resultMap = new HashMap<String, String>();

		String reqMsg = JSonUtil.toJSonString(paraMap);
		logger.info("reqMsg: "+reqMsg);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());

		HessianProxyFactory factory = new HessianProxyFactory();
		HessianInvokeService hessianInvokeService = null;
		try {
			hessianInvokeService = (HessianInvokeService) factory.create(
					HessianInvokeService.class, preServerUrl);
			
			String result = hessianInvokeService.invoke(
					"fi-hnapay-rateQueryHandler", sysTraceNo,
					SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.PRE.getCode(),
					SystemCodeEnum.PRE.getVersion(), param.getDataLength(),
					param.getMsgCompress(), param.getDataMsg());
			param.parse(result);
			HessianInvokeHelper.processResponse(param);
			result = param.getDataMsg();
			resultMap = JSonUtil.toObject(result, new HashMap().getClass());			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("call handler error:", e);
			resultMap.put("responseCode",ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseCode",ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc","（汇率查询）连接银行前置异常！");
			Map<String,String> data = new HashMap<String, String>();
			data.put("first","(汇率查询)渠道到银行前置连接异常请赶紧检查!");
			data.put("keyword1","(汇率查询)渠道到银行前置连接异常请赶紧检查!");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        data.put("keyword2",formatter.format(new Date()));
	        data.put("remark","请尽快检查！");		
			this.sendAlertMsg(data);
			return resultMap;
		}
		if (logger.isInfoEnabled()) {
			logger.info("response result:" + resultMap);
		}

		return resultMap;
	}

	/**
	 * 海南新生支付接口
	 * 
	 * @param preServerUrl
	 * @param paraMap
	 * @return
	 */
	public Map<String, String> hnaPay(String preServerUrl,
			Map<String, Object> paraMap) {

		Map<String, String> resultMap = new HashMap<String, String>();

		String reqMsg = JSonUtil.toJSonString(paraMap);
		logger.info("reqMsg: "+reqMsg);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());

		HessianProxyFactory factory = new HessianProxyFactory();
		HessianInvokeService hessianInvokeService = null;
		try {
			hessianInvokeService = (HessianInvokeService) factory.create(
					HessianInvokeService.class, preServerUrl);
			
			String result = hessianInvokeService.invoke(
					SerCode.PRE_PAY.getCode(), sysTraceNo,
					SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.PRE.getCode(),
					SystemCodeEnum.PRE.getVersion(), param.getDataLength(),
					param.getMsgCompress(), param.getDataMsg());
			param.parse(result);
			HessianInvokeHelper.processResponse(param);
			result = param.getDataMsg();
			resultMap = JSonUtil.toObject(result, new HashMap().getClass());			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("call handler error:", e);
			resultMap.put("responseCode",ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseCode",ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc","（新生支付）连接银行前置异常！");
			Map<String,String> data = new HashMap<String, String>();
			data.put("first","(新生支付)渠道到银行前置连接异常请赶紧检查!");
			data.put("keyword1","(新生支付)渠道到银行前置连接异常请赶紧检查!");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        data.put("keyword2",formatter.format(new Date()));
	        data.put("remark","请尽快检查！");		
			this.sendAlertMsg(data);
			return resultMap;
		}
		if (logger.isInfoEnabled()) {
			logger.info("response result:" + resultMap);
		}

		return resultMap;
	}
	
	
	
	
	
	
	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

	public void setReadTimeOut(long readTimeOut) {
		this.readTimeOut = readTimeOut;
	}

	/**
	 * 预授权申请
	 * @param channelRequestDto
	 * @return
	 */
	public Map<String, String> preAuthApply(PaymentInfo channelRequestDto) {

		Map<String, String> resultMap = new HashMap<String, String>();
		String preServerUrl = channelRequestDto.getPreServerUrl();
		Map paraMap = MapUtil.bean2map(channelRequestDto);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());

		HessianProxyFactory factory = new HessianProxyFactory();
		HessianInvokeService hessianInvokeService = null;
		try {
			factory.setReadTimeout(readTimeOut);
			hessianInvokeService = (HessianInvokeService) factory.create(
					HessianInvokeService.class, preServerUrl);

			String result = hessianInvokeService.invoke(SerCode.PRE_PREAUTH.getCode(),
					sysTraceNo, SystemCodeEnum.CHANNEL.getCode(),
					SystemCodeEnum.PRE.getCode(), SystemCodeEnum.PRE.getVersion(),
					param.getDataLength(), param.getMsgCompress(),
					param.getDataMsg());
			param.parse(result);
			HessianInvokeHelper.processResponse(param);
			result = param.getDataMsg();

			resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		} catch (Exception e) {

			logger.error("call handler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc","（预授权申请）连接银行前置异常！");
			Map<String,String> data = new HashMap<String, String>();

			data.put("first","(预售权申请)渠道到银行前置连接异常请赶紧检查!");
			data.put("keyword1","(预售权申请)渠道到银行前置连接异常请赶紧检查!");
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			data.put("keyword2",formatter.format(new Date()));
			data.put("remark","请尽快检查！");

			this.sendAlertMsg(data);

			return resultMap;
		}

		if (logger.isInfoEnabled()) {
			logger.info("response result:" + resultMap);
		}

		return resultMap;
	}

	/**
	 * 预授权完成20160901
	 * @param channelItem
	 * @param channelRequestDto
	 * @return
	 */
	public Map<String, String> preAuthCapture(PaymentChannelItem channelItem,
											  ChannelRequestDto channelRequestDto,String orgiChannelOrderNo,String orgiReturnNo) {
		Map<String, String> resultMap = new HashMap<String, String>();
		ChannelResponseDto channelPaymentResult = new ChannelResponseDto();
		Map<String, String> paraMap = new HashMap();
		Map channelItemMap = MapUtil.bean2map(channelItem);
		paraMap.putAll(channelItemMap);

		ChannelConfig channelConfig = new ChannelConfig();
		channelConfig.setOrgMerchantCode(channelRequestDto.getMerchantNo());
		channelConfig.setOrgCode(channelRequestDto.getOrgCode());
		channelConfig = channelConfigDAO.findObjectByCriteria(channelConfig);

		if (channelConfig != null) {
			Map configMap = MapUtil.bean2map(channelConfig);
			paraMap.putAll(configMap);
		}

		Map requestMap = MapUtil.bean2map(channelRequestDto);
		paraMap.putAll(requestMap);
		paraMap.put("origChannelOrderNo",orgiChannelOrderNo);
		if (logger.isInfoEnabled()) {
			logger.info("refund request:" + requestMap);
		}
		paraMap.put("returnNo",orgiReturnNo);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());

		HessianProxyFactory factory = new HessianProxyFactory();
		HessianInvokeService hessianInvokeService = null;
		try {
			factory.setReadTimeout(readTimeOut);
			hessianInvokeService = (HessianInvokeService) factory.create(
					HessianInvokeService.class, channelItem.getPreServerUrl());

			String result = hessianInvokeService.invoke(SerCode.PRE_PREAUTH_COMP.getCode(),
					sysTraceNo, SystemCodeEnum.CHANNEL.getCode(),
					SystemCodeEnum.PRE.getCode(), SystemCodeEnum.PRE.getVersion(),
					param.getDataLength(), param.getMsgCompress(),
					param.getDataMsg());
			param.parse(result);
			HessianInvokeHelper.processResponse(param);
			result = param.getDataMsg();

			resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		} catch (Exception e) {

			logger.error("call handler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc","（预授权完成）连接银行前置异常！");
			Map<String,String> data = new HashMap<String, String>();

			data.put("first","(预授权完成)渠道到银行前置连接异常请赶紧检查!");
			data.put("keyword1","(预授权完成)渠道到银行前置连接异常请赶紧检查!");
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			data.put("keyword2",formatter.format(new Date()));
			data.put("remark","请尽快检查！");

			this.sendAlertMsg(data);

			return resultMap;
		}

		if (logger.isInfoEnabled()) {
			logger.info("response result:" + resultMap);
		}

		return resultMap;
	}

	/**
	 * 预授权撤销20160901
	 * @param channelItem
	 * @param channelRequestDto
	 * @return
	 */
	public Map<String, String> preAuthVoid(PaymentChannelItem channelItem,
										   ChannelRequestDto channelRequestDto,String orgiChannelOrderNo,String orgiReturnNo) {

		Map<String, String> resultMap = new HashMap<String, String>();
		Map<String, String> paraMap = new HashMap();
		Map channelItemMap = MapUtil.bean2map(channelItem);
		paraMap.putAll(channelItemMap);

		ChannelConfig channelConfig = new ChannelConfig();
		channelConfig.setOrgMerchantCode(channelRequestDto.getMerchantNo());
		channelConfig.setOrgCode(channelRequestDto.getOrgCode());
		channelConfig = channelConfigDAO.findObjectByCriteria(channelConfig);

		if (channelConfig != null) {
			Map configMap = MapUtil.bean2map(channelConfig);
			paraMap.putAll(configMap);
		}

		Map requestMap = MapUtil.bean2map(channelRequestDto);
		paraMap.putAll(requestMap);
		paraMap.put("origChannelOrderNo",orgiChannelOrderNo);
		if (logger.isInfoEnabled()) {
			logger.info("refund request:" + requestMap);
		}
		paraMap.put("returnNo",orgiReturnNo);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());

		HessianProxyFactory factory = new HessianProxyFactory();
		HessianInvokeService hessianInvokeService = null;
		try {
			factory.setReadTimeout(readTimeOut);
			hessianInvokeService = (HessianInvokeService) factory.create(
					HessianInvokeService.class, channelItem.getPreServerUrl());

			String result = hessianInvokeService.invoke(SerCode.PRE_PREAUTH_REVOKE.getCode(),
					sysTraceNo, SystemCodeEnum.CHANNEL.getCode(),
					SystemCodeEnum.PRE.getCode(), SystemCodeEnum.PRE.getVersion(),
					param.getDataLength(), param.getMsgCompress(),
					param.getDataMsg());
			param.parse(result);
			HessianInvokeHelper.processResponse(param);
			result = param.getDataMsg();

			resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		} catch (Exception e) {

			logger.error("call handler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc","（预授权申请撤销）连接银行前置异常！");
			Map<String,String> data = new HashMap<String, String>();

			data.put("first","(预授权申请撤销)渠道到银行前置连接异常请赶紧检查!");
			data.put("keyword1","(预授权申请撤销)渠道到银行前置连接异常请赶紧检查!");
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			data.put("keyword2",formatter.format(new Date()));
			data.put("remark","请尽快检查！");

			this.sendAlertMsg(data);

			return resultMap;
		}

		if (logger.isInfoEnabled()) {
			logger.info("response result:" + resultMap);
		}

		return resultMap;
	}

}
