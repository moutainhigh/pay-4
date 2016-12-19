/**
 * 
 */
package com.pay.txncore.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.enums.SerCode;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.txncore.dto.ChannelOrderDTO;
import com.pay.txncore.dto.ChannelPaymentResult;
import com.pay.txncore.dto.ChannelPreauthResult;
import com.pay.txncore.dto.PaymentChannelItemConfigDTO;
import com.pay.txncore.dto.PaymentChannelItemDto;
import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PreauthInfo;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 交易服务接口
 * 
 * @author chaoyue
 *
 */
public class ChannelClientService {

	private final Log logger = LogFactory.getLog(getClass());
	private HessianInvokeService invokeService;

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}

	/**
	 * 获取指定渠道信息
	 * 
	 * @param partnerId
	 * @return
	 */
	public PaymentChannelItemDto channelQuery(String orgCode,
			String orgMerchantCode) {

		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("orgCode", orgCode);
		paraMap.put("orgMerchantCode", orgMerchantCode);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_SPAIC_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		return MapUtil.map2Object(PaymentChannelItemDto.class, resultMap);
	}

	public ChannelPaymentResult channelPay(PaymentInfo paymentInfo) {

		ChannelPaymentResult channelPaymentResult = new ChannelPaymentResult();
		Map paraMap = MapUtil.bean2map(paymentInfo);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke(SerCode.CHANNEL_PAY.getCode(),
				sysTraceNo, SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		String responseCode = String.valueOf(resultMap.get("responseCode"));
		String responseDesc = String.valueOf(resultMap.get("responseDesc"));

		channelPaymentResult.setErrorCode(responseCode);
		channelPaymentResult.setErrorMsg(responseDesc);
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {

			String orgCode = String.valueOf(resultMap.get("orgCode"));
			String payAmount = String.valueOf(resultMap.get("payAmount"));
			String channelReturnNo = String.valueOf(resultMap
					.get("channelReturnNo"));
			channelPaymentResult.setOrgCode(orgCode);
			if (!StringUtil.isEmpty(payAmount)) {
				channelPaymentResult.setPayAmount(Long.valueOf(payAmount));
			}
			channelPaymentResult.setChannelReturnNo(channelReturnNo);
			channelPaymentResult.setAuthorisation(String.valueOf(resultMap
					.get("authorisation")));
		}

		return channelPaymentResult;
	}
	
	
	/**
	 * 系统存活检查
	 * @param paymentInfo
	 * @return
	 */
	public String systemDetected(PaymentInfo paymentInfo) {

		ChannelPaymentResult channelPaymentResult = new ChannelPaymentResult();
		Map paraMap = MapUtil.bean2map(paymentInfo);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		
		logger.info("TXNCORE-test: "+reqMsg);
		
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke(SerCode.CHANNEL_DETECTED.getCode(),
				sysTraceNo, SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		
		String responseCode = String.valueOf(resultMap.get("responseCode"));
		String responseDesc = String.valueOf(resultMap.get("responseDesc"));
        
		logger.info("responseCode: "+responseCode+" ,responseDesc: "+responseDesc);

		return responseCode;
	}	

	public ChannelPaymentResult channelRefund(Map<String, String> refundPara) {

		ChannelPaymentResult channelPaymentResult = new ChannelPaymentResult();
		String reqMsg = JSonUtil.toJSonString(refundPara);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke(SerCode.CHANNEL_REFUND.getCode(),
				sysTraceNo, SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		String responseCode = String.valueOf(resultMap.get("responseCode"));
		String responseDesc = String.valueOf(resultMap.get("responseDesc"));

		channelPaymentResult.setErrorCode(responseCode);
		channelPaymentResult.setErrorMsg(responseDesc);
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {

			String orgCode = String.valueOf(resultMap.get("orgCode"));
			String channelReturnNo = String.valueOf(resultMap
					.get("channelReturnNo"));
			channelPaymentResult.setOrgCode(orgCode);
			channelPaymentResult.setChannelReturnNo(channelReturnNo);
			
			 //mack add below reference no for migs refund issue 2016年10月8日
		    String referenceNo = String.valueOf(resultMap
							.get("reference_no"));
		    
		    channelPaymentResult.setReferenceNo(referenceNo);
		    
		   logger.info("refund-channelReturnNo=["+channelReturnNo+"],refund-reference_no=["+referenceNo+"]" );
			
		}

		return channelPaymentResult;
	}

	/**
	 * 
	 * @param partnerId
	 * @param memberType
	 * @param paymentType
	 * @return
	 */
	public PaymentChannelItemDto queryChannel(String partnerId,
			String memberType, String paymentType, String transType,
			String currencyCode, String siteId,String vom,String prdtCode) {
		List<PaymentChannelItemDto> channels = queryChannels(partnerId, memberType, paymentType, transType, currencyCode, siteId, vom,prdtCode);
		if(channels!=null && !channels.isEmpty()){
			return channels.get(0);
		}
		return null;
	}
	
	
	/**
	 * 获取所有的通道
	 * @param partnerId
	 * @param memberType
	 * @param paymentType
	 * @return
	 */
	public List<PaymentChannelItemDto> queryChannels(String partnerId,
			String memberType, String paymentType, String transType,
			String currencyCode, String siteId,String vom,String prdtCode) {

		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("memberCode", partnerId);
		paraMap.put("memberType", memberType);
		paraMap.put("paymentType", paymentType);
		paraMap.put("transType", transType);
		paraMap.put("currencyCode", currencyCode);
		paraMap.put("siteId", siteId);
		paraMap.put("vom", vom);
		paraMap.put("prdtCode", prdtCode);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_QUERY_MEMBER_CONFIG.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		List<Map> listMap = (List<Map>) resultMap.get("paymentChannelItems");

		List<PaymentChannelItemDto> list = new ArrayList<PaymentChannelItemDto>(); 
		
		if (null != listMap && !listMap.isEmpty()) {
			
			for(int i=0;i<listMap.size();i++){
				PaymentChannelItemDto paymentChannelItemDto = new PaymentChannelItemDto();
				paymentChannelItemDto = MapUtil.map2Object(
						PaymentChannelItemDto.class, listMap.get(i));
				
				list.add(paymentChannelItemDto);
			}
		}
		return list;
	}
	
	/**
	 * 获取所有的通道
	 * @param partnerId
	 * @param memberType
	 * @param paymentType
	 * @return
	 */
	public List<PaymentChannelItemConfigDTO> queryChannelItemConfs(String partnerId,
			String memberType, String paymentType, String transType,
			String currencyCode, String mcc,String vom) {

		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("memberCode", partnerId);
		paraMap.put("paymentType", paymentType);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke(
				"", sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		List<Map> listMap = (List<Map>) resultMap.get("channelItemConfList");

		List<PaymentChannelItemConfigDTO> list = new ArrayList<PaymentChannelItemConfigDTO>(); 
		
		if (null != listMap && !listMap.isEmpty()) {
			
			for(int i=0;i<listMap.size();i++){
				PaymentChannelItemConfigDTO paymentChannelItemDto = new PaymentChannelItemConfigDTO();
				paymentChannelItemDto = MapUtil.map2Object(
						PaymentChannelItemConfigDTO.class, listMap.get(i));
				
				list.add(paymentChannelItemDto);
			}
		}
		return list;
	}

	/**
	 * 渠道预授权
	 * 
	 * @param partnerId
	 * @return
	 */
	public ChannelPreauthResult channelPreauth(PreauthInfo paymentInfo,
			ChannelOrderDTO channelOrderDTO) {

		ChannelPreauthResult channelPreauthResult = new ChannelPreauthResult();
		Map paraMap = MapUtil.bean2map(paymentInfo);
		paraMap.put("serialNo", channelOrderDTO.getSerialNo());
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke(SerCode.CHANNEL_PREAUTH.getCode(),
				sysTraceNo, SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		logger.info("【result】: " + result);

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		String responseCode = String.valueOf(resultMap.get("responseCode"));
		String responseDesc = String.valueOf(resultMap.get("responseDesc"));
		String authCode = String.valueOf(resultMap.get("authCode"));
		String authorStr = String.valueOf(resultMap.get("authorStr"));
		String refNo = String.valueOf(resultMap.get("refNo"));

		channelPreauthResult.setErrorCode(responseCode);
		channelPreauthResult.setErrorMsg(responseDesc);

		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			String orgCode = String.valueOf(resultMap.get("orgCode"));
			String payAmount = String.valueOf(resultMap.get("orderAmount"));

			if (!StringUtil.isEmpty(payAmount)) {
				channelPreauthResult.setPayAmount(Long.valueOf(payAmount));
			}

			String channelReturnNo = String.valueOf(resultMap
					.get("channelReturnNo"));
			channelPreauthResult.setOrgCode(orgCode);
			channelPreauthResult.setChannelReturnNo(channelReturnNo);
			channelPreauthResult.setAuthCode(authCode);
			channelPreauthResult.setAuthorStr(authorStr);
			channelPreauthResult.setRefNo(refNo);
		}

		return channelPreauthResult;
	}

	/**
	 * 渠道预授权完成
	 * 
	 * @param partnerId
	 * @return
	 */
	public ChannelPreauthResult channelPreauthCompeleted(
			PreauthInfo paymentInfo, ChannelOrderDTO channelOrderDTO) {

		ChannelPreauthResult channelPreauthResult = new ChannelPreauthResult();
		Map paraMap = MapUtil.bean2map(paymentInfo);
		paraMap.put("serialNo", channelOrderDTO.getSerialNo());
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_PREAUTH_COMP.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		String responseCode = String.valueOf(resultMap.get("responseCode"));
		String responseDesc = String.valueOf(resultMap.get("responseDesc"));
		String authCode = String.valueOf(resultMap.get("authCode"));
		String authorStr = String.valueOf(resultMap.get("authorStr"));
		String refNo = String.valueOf(resultMap.get("refNo"));

		channelPreauthResult.setErrorCode(responseCode);
		channelPreauthResult.setErrorMsg(responseDesc);

		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			String orgCode = String.valueOf(resultMap.get("orgCode"));
			String payAmount = String.valueOf(resultMap.get("orderAmount"));

			if (!StringUtil.isEmpty(payAmount)) {
				channelPreauthResult.setPayAmount(Long.valueOf(payAmount));
			}

			String channelReturnNo = String.valueOf(resultMap
					.get("channelReturnNo"));
			channelPreauthResult.setOrgCode(orgCode);
			channelPreauthResult.setChannelReturnNo(channelReturnNo);
			channelPreauthResult.setAuthCode(authCode);
			channelPreauthResult.setAuthorStr(authorStr);
			channelPreauthResult.setRefNo(refNo);
		}

		return channelPreauthResult;
	}

	/**
	 * 渠道预授权撤销
	 * 
	 * @param partnerId
	 * @return
	 */
	public ChannelPreauthResult channelPreauthRevoke(PreauthInfo paymentInfo,
			ChannelOrderDTO channelOrderDTO) {

		ChannelPreauthResult channelPreauthResult = new ChannelPreauthResult();
		Map paraMap = MapUtil.bean2map(paymentInfo);
		paraMap.put("serialNo", channelOrderDTO.getSerialNo());
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_PREAUTH_REVOKE.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		String responseCode = String.valueOf(resultMap.get("responseCode"));
		String responseDesc = String.valueOf(resultMap.get("responseDesc"));
		String authCode = String.valueOf(resultMap.get("authCode"));
		String authorStr = String.valueOf(resultMap.get("authorStr"));
		String refNo = String.valueOf(resultMap.get("refNo"));

		channelPreauthResult.setErrorCode(responseCode);
		channelPreauthResult.setErrorMsg(responseDesc);

		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			String orgCode = String.valueOf(resultMap.get("orgCode"));
			String payAmount = String.valueOf(resultMap.get("orderAmount"));

			if (!StringUtil.isEmpty(payAmount)) {
				channelPreauthResult.setPayAmount(Long.valueOf(payAmount));
			}

			String channelReturnNo = String.valueOf(resultMap
					.get("channelReturnNo"));
			channelPreauthResult.setOrgCode(orgCode);
			channelPreauthResult.setChannelReturnNo(channelReturnNo);
			channelPreauthResult.setAuthCode(authCode);
			channelPreauthResult.setAuthorStr(authorStr);
			channelPreauthResult.setRefNo(refNo);
		}

		return channelPreauthResult;
	}

	public Map<String, String> queryOrgRateInfo(Map<String, String> queryMap) {

		String reqMsg = JSonUtil.toJSonString(queryMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.GATEWAY.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_ORGRATE_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.GATEWAY.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, String> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		return resultMap;
	}
}
