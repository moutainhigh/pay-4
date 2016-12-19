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

import com.caucho.hessian.client.HessianProxyFactory;
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
import com.pay.txncore.model.PartnerChannelCountry;
import com.pay.txncore.model.SettlementCurrencyConfig;
import com.pay.txncore.newpaymentprocess.processvo.BackfromChannelVo;
import com.pay.txncore.newpaymentprocess.processvo.SendChannelVo;
import com.pay.txncore.newpaymentprocess.processvo.WftBackVo;
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
	 * 新生支付接口
	 * @param paraMap
	 * @return
	 */
	public Map<String, Object> hnaPay(Map<String, Object> paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke("fi-channel.hnaPayHandler", sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(), SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.CHANNEL.getVersion(),
				param.getDataLength(), param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		Map<String, Object> returnMap = (Map) resultMap.get("result");
		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
			logger.info("returnMap:" + returnMap);
		}
		return returnMap;
	}

	/**
	 * 获取指定渠道信息
	 * 
	 * @param partnerId
	 * @return
	 */
	public PaymentChannelItemDto channelQuery(String orgCode, String orgMerchantCode) {

		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("orgCode", orgCode);
		paraMap.put("orgMerchantCode", orgMerchantCode);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke(SerCode.CHANNEL_SPAIC_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(), SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.CHANNEL.getVersion(),
				param.getDataLength(), param.getMsgCompress(), param.getDataMsg());

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
		String sysTraceNo = SysTraceNoService.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke(SerCode.CHANNEL_PAY.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(), SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.CHANNEL.getVersion(),
				param.getDataLength(), param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		String responseCode = String.valueOf(resultMap.get("responseCode"));
		String responseDesc = String.valueOf(resultMap.get("responseDesc"));
		Map<String, String> dataMap = (Map) resultMap.get("dataMap");
		channelPaymentResult.setErrorCode(responseCode);
		channelPaymentResult.setErrorMsg(responseDesc);
		channelPaymentResult.setDataMap(dataMap);
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {

			String orgCode = String.valueOf(resultMap.get("orgCode"));
			String payAmount = String.valueOf(resultMap.get("payAmount"));
			String channelReturnNo = String.valueOf(resultMap.get("channelReturnNo"));
			String referenceNo = String.valueOf(resultMap.get("referenceNo"));
			channelPaymentResult.setOrgCode(orgCode);
			if (!StringUtil.isEmpty(payAmount)) {
				channelPaymentResult.setPayAmount(Long.valueOf(payAmount));
			}
			channelPaymentResult.setChannelReturnNo(channelReturnNo);
			// 参考号的获取
			channelPaymentResult.setReferenceNo(referenceNo);
			logger.info("获取参考号referenceNo:" + referenceNo);
			channelPaymentResult.setAuthorisation(String.valueOf(resultMap.get("authorisation")));
		}

		return channelPaymentResult;
	}

	// addby liu 2016-04-29 3D渠道处理，并未真正支付
	public Map channel3dPay(PaymentInfo paymentInfo) {

		ChannelPaymentResult channelPaymentResult = new ChannelPaymentResult();
		Map paraMap = MapUtil.bean2map(paymentInfo);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke(SerCode.CHANNEL_PAY.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(), SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.CHANNEL.getVersion(),
				param.getDataLength(), param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		return resultMap;
	}

	/**
	 * 系统存活检查
	 * 
	 * @param paymentInfo
	 * @return
	 */
	public String systemDetected(PaymentInfo paymentInfo) {

		ChannelPaymentResult channelPaymentResult = new ChannelPaymentResult();
		Map paraMap = MapUtil.bean2map(paymentInfo);
		String reqMsg = JSonUtil.toJSonString(paraMap);

		logger.info("TXNCORE-test: " + reqMsg);

		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke(SerCode.CHANNEL_DETECTED.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(), SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.CHANNEL.getVersion(),
				param.getDataLength(), param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		String responseCode = String.valueOf(resultMap.get("responseCode"));
		String responseDesc = String.valueOf(resultMap.get("responseDesc"));

		logger.info("responseCode: " + responseCode + " ,responseDesc: " + responseDesc);

		return responseCode;
	}

	public ChannelPaymentResult channelRefund(Map<String, String> refundPara) {

		ChannelPaymentResult channelPaymentResult = new ChannelPaymentResult();
		String reqMsg = JSonUtil.toJSonString(refundPara);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke(SerCode.CHANNEL_REFUND.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(), SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.CHANNEL.getVersion(),
				param.getDataLength(), param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		logger.info("channel refund return String:" + result);
		String responseCode = String.valueOf(resultMap.get("responseCode"));
		String responseDesc = String.valueOf(resultMap.get("responseDesc"));

		channelPaymentResult.setErrorCode(responseCode);
		channelPaymentResult.setErrorMsg(responseDesc);
		channelPaymentResult.setCompleteStatus(String.valueOf(resultMap.get("completeStatus")));
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			String orgCode = String.valueOf(resultMap.get("orgCode"));
			String channelReturnNo = String.valueOf(resultMap.get("channelReturnNo"));
			channelPaymentResult.setOrgCode(orgCode);
			channelPaymentResult.setChannelReturnNo(channelReturnNo);
		}

		return channelPaymentResult;
	}
	
	public ChannelPaymentResult channelQueryRefundSts(Map<String, String> refundPara) {

		ChannelPaymentResult channelPaymentResult = new ChannelPaymentResult();
		String reqMsg = JSonUtil.toJSonString(refundPara);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke("fi-channel.refundStatusQueryHandler", sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(), SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.CHANNEL.getVersion(),
				param.getDataLength(), param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		logger.info("channel query refund status return String:" +result);
		String responseCode = String.valueOf(resultMap.get("responseCode"));
		String responseDesc = String.valueOf(resultMap.get("responseDesc"));

		channelPaymentResult.setErrorCode(responseCode);
		channelPaymentResult.setErrorMsg(responseDesc);
		channelPaymentResult.setCompleteStatus(String.valueOf(resultMap.get("completeStatus")));
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			String orgCode = String.valueOf(resultMap.get("orgCode"));
			String channelReturnNo = String.valueOf(resultMap.get("channelReturnNo"));
			channelPaymentResult.setOrgCode(orgCode);
			channelPaymentResult.setChannelReturnNo(channelReturnNo);
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
	public PaymentChannelItemDto queryChannel(String partnerId, String memberType, String paymentType, String transType,
			String currencyCode, String siteId, String vom, String prdtCode) {
		List<PaymentChannelItemDto> channels = queryChannels(partnerId, memberType, paymentType, transType,
				currencyCode, siteId, vom, prdtCode);
		if (channels != null && !channels.isEmpty()) {
			return channels.get(0);
		}
		return null;
	}

	/**
	 * 获取所有的通道
	 * 
	 * @param partnerId
	 * @param memberType
	 * @param paymentType
	 * @return
	 */
	public List<PaymentChannelItemDto> queryChannels(String partnerId, String memberType, String paymentType,
			String transType, String currencyCode, String siteId, String vom, String prdtCode) {

		Map<String, String> paraMap = new HashMap<String, String>();
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
		String sysTraceNo = SysTraceNoService.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke(SerCode.CHANNEL_QUERY_MEMBER_CONFIG.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(), SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.CHANNEL.getVersion(),
				param.getDataLength(), param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		List<Map> listMap = (List<Map>) resultMap.get("paymentChannelItems");

		List<PaymentChannelItemDto> list = new ArrayList<PaymentChannelItemDto>();

		if (null != listMap && !listMap.isEmpty()) {

			for (int i = 0; i < listMap.size(); i++) {
				PaymentChannelItemDto paymentChannelItemDto = new PaymentChannelItemDto();
				paymentChannelItemDto = MapUtil.map2Object(PaymentChannelItemDto.class, listMap.get(i));

				list.add(paymentChannelItemDto);
			}
		}
		return list;
	}

	/**
	 * 获取所有的通道
	 * 
	 * @param partnerId
	 * @param memberType
	 * @param paymentType
	 * @return
	 */
	public Map<String, List<PartnerChannelCountry>> queryPartnerCountryChannels(Map<String, String> paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke(SerCode.CHANNEL_PARTNER_COUNTRY_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(), SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.CHANNEL.getVersion(),
				param.getDataLength(), param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		List<Map> listMap = (List<Map>) resultMap.get("pccList");
		Map<String, List<PartnerChannelCountry>> pccMap = null;

		if (null != listMap && !listMap.isEmpty()) {
			pccMap = new HashMap<String, List<PartnerChannelCountry>>();
			for (int i = 0; i < listMap.size(); i++) {
				PartnerChannelCountry pcc = new PartnerChannelCountry();
				pcc = MapUtil.map2Object(PartnerChannelCountry.class, listMap.get(i));

				if (pccMap.containsKey(pcc.getOrgCode())) {
					List<PartnerChannelCountry> tmp = pccMap.get(pcc.getOrgCode());
					tmp.add(pcc);
					pccMap.put(pcc.getOrgCode(), tmp);
				} else {
					List<PartnerChannelCountry> list = new ArrayList<PartnerChannelCountry>();
					list.add(pcc);
					pccMap.put(pcc.getOrgCode(), list);
				}
			}
		}
		return pccMap;
	}

	/**
	 * 获取所有的通道
	 * 
	 * @param partnerId
	 * @param memberType
	 * @param paymentType
	 * @return
	 */
	public List<PaymentChannelItemConfigDTO> queryChannelItemConfs(String partnerId, String memberType,
			String paymentType, String transType, String currencyCode, String mcc, String vom) {

		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("memberCode", partnerId);
		paraMap.put("paymentType", paymentType);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke("", sysTraceNo, SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		List<Map> listMap = (List<Map>) resultMap.get("channelItemConfList");

		List<PaymentChannelItemConfigDTO> list = new ArrayList<PaymentChannelItemConfigDTO>();

		if (null != listMap && !listMap.isEmpty()) {

			for (int i = 0; i < listMap.size(); i++) {
				PaymentChannelItemConfigDTO paymentChannelItemDto = new PaymentChannelItemConfigDTO();
				paymentChannelItemDto = MapUtil.map2Object(PaymentChannelItemConfigDTO.class, listMap.get(i));

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
	public ChannelPreauthResult channelPreauth(PreauthInfo paymentInfo, ChannelOrderDTO channelOrderDTO) {

		ChannelPreauthResult channelPreauthResult = new ChannelPreauthResult();
		Map paraMap = MapUtil.bean2map(paymentInfo);
		paraMap.put("serialNo", channelOrderDTO.getSerialNo());
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke(SerCode.CHANNEL_PREAUTH.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(), SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.CHANNEL.getVersion(),
				param.getDataLength(), param.getMsgCompress(), param.getDataMsg());

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

			String channelReturnNo = String.valueOf(resultMap.get("channelReturnNo"));
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
	public ChannelPreauthResult channelPreauthCompeleted(PreauthInfo paymentInfo, ChannelOrderDTO channelOrderDTO) {

		ChannelPreauthResult channelPreauthResult = new ChannelPreauthResult();
		Map paraMap = MapUtil.bean2map(paymentInfo);
		paraMap.put("serialNo", channelOrderDTO.getSerialNo());
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke(SerCode.CHANNEL_PREAUTH_COMP.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(), SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.CHANNEL.getVersion(),
				param.getDataLength(), param.getMsgCompress(), param.getDataMsg());

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

			String channelReturnNo = String.valueOf(resultMap.get("channelReturnNo"));
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
	public ChannelPreauthResult channelPreauthRevoke(PreauthInfo paymentInfo, ChannelOrderDTO channelOrderDTO) {

		ChannelPreauthResult channelPreauthResult = new ChannelPreauthResult();
		Map paraMap = MapUtil.bean2map(paymentInfo);
		paraMap.put("serialNo", channelOrderDTO.getSerialNo());
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke(SerCode.CHANNEL_PREAUTH_REVOKE.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(), SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.CHANNEL.getVersion(),
				param.getDataLength(), param.getMsgCompress(), param.getDataMsg());

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

			String channelReturnNo = String.valueOf(resultMap.get("channelReturnNo"));
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
		String sysTraceNo = SysTraceNoService.generateSysTraceNo(SystemCodeEnum.GATEWAY.getCode());
		String result = invokeService.invoke(SerCode.CHANNEL_ORGRATE_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.GATEWAY.getCode(), SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.CHANNEL.getVersion(),
				param.getDataLength(), param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, String> resultMap = JSonUtil.toObject(result, new HashMap<String, String>().getClass());

		return resultMap;
	}

	/** delin.dong 本地化查找渠道 ***/
	public List<PaymentChannelItemDto> getChannel(String tradeType, String partnerId, String memberType,
			String paymentType, String transType, String currencyCode, String siteId, String prdtCode) {

		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("memberCode", partnerId);
		paraMap.put("memberType", memberType);
		paraMap.put("paymentType", paymentType);
		paraMap.put("transType", transType);
		paraMap.put("tradeType", tradeType);
		paraMap.put("currencyCode", currencyCode);
		paraMap.put("siteId", siteId);
		paraMap.put("prdtCode", prdtCode);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke(SerCode.CHANNEL_QUERY_MEMBER_CONFIG_LOCALE.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(), SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.CHANNEL.getVersion(),
				param.getDataLength(), param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		List<Map> listMap = (List<Map>) resultMap.get("paymentChannelItems");

		List<PaymentChannelItemDto> list = new ArrayList<PaymentChannelItemDto>();

		if (null != listMap && !listMap.isEmpty()) {

			for (int i = 0; i < listMap.size(); i++) {
				PaymentChannelItemDto paymentChannelItemDto = new PaymentChannelItemDto();
				paymentChannelItemDto = MapUtil.map2Object(PaymentChannelItemDto.class, listMap.get(i));

				list.add(paymentChannelItemDto);
			}
		}
		return list;
	}

	public List<PaymentChannelItemDto> getChannel(String paymentType, String partnerId, String orgCode) {

		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("memberCode", partnerId);
		paraMap.put("paymentType", paymentType);
		paraMap.put("orgCode", orgCode);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke("fi-channel.channelQueryDirectHandler", sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(), SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.CHANNEL.getVersion(),
				param.getDataLength(), param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		List<Map> listMap = (List<Map>) resultMap.get("paymentChannelItems");
		List<PaymentChannelItemDto> list = new ArrayList<PaymentChannelItemDto>();

		if (null != listMap && !listMap.isEmpty()) {

			for (int i = 0; i < listMap.size(); i++) {
				PaymentChannelItemDto paymentChannelItemDto = new PaymentChannelItemDto();
				paymentChannelItemDto = MapUtil.map2Object(PaymentChannelItemDto.class, listMap.get(i));

				list.add(paymentChannelItemDto);
			}
		}
		return list;
	}

	public BackfromChannelVo channelPay(SendChannelVo vo) {
		String preServerUrl = vo.getPreServerUrl();
		String reqMsg = JSonUtil.toJSonString(vo);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		BackfromChannelVo backVo = new BackfromChannelVo();
		try {
			HessianProxyFactory factory = new HessianProxyFactory();
			HessianInvokeService hessianInvokeService = null;
			hessianInvokeService = (HessianInvokeService) factory.create(HessianInvokeService.class, preServerUrl);
			String result = hessianInvokeService.invoke(SerCode.PRE_PAY.getCode(), sysTraceNo,
					SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.PRE.getCode(), SystemCodeEnum.PRE.getVersion(),
					param.getDataLength(), param.getMsgCompress(), param.getDataMsg());
			param.parse(result);
			HessianInvokeHelper.processResponse(param);
			result = param.getDataMsg();

			Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
			backVo = MapUtil.map2Object(BackfromChannelVo.class, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			backVo.setReturnCode("9999");
			backVo.setReturnDesc("send channel failed");
		}
		return backVo;
	}
	
	/**
	 * 威富通渠道支付，实际直接调用前置支付，并不是真正支付，生成扫码支付所需要的二维码信息或WAP支付时所需要的链接信息
	 * @param vo
	 * @return
	 */
	public WftBackVo channelPay4Wft(PaymentInfo vo) {
		String preServerUrl = vo.getPreServerUrl();
		String reqMsg = JSonUtil.toJSonString(vo);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		WftBackVo backVo = new WftBackVo();
		try {
			HessianProxyFactory factory = new HessianProxyFactory();
			HessianInvokeService hessianInvokeService = null;
			hessianInvokeService = (HessianInvokeService) factory.create(HessianInvokeService.class, preServerUrl);
			String result = hessianInvokeService.invoke(SerCode.PRE_PAY.getCode(), sysTraceNo,
					SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.PRE.getCode(), SystemCodeEnum.PRE.getVersion(),
					param.getDataLength(), param.getMsgCompress(), param.getDataMsg());
			param.parse(result);
			HessianInvokeHelper.processResponse(param);
			result = param.getDataMsg();
			
			Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
			backVo = MapUtil.map2Object(WftBackVo.class, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			backVo.setResponseCode("9999");
			backVo.setResponseDesc("send channel failed");
		}
		return backVo;
	}

	public ChannelPaymentResult preAuthPayChannel(PaymentInfo paymentInfo) {
		ChannelPaymentResult channelPaymentResult = new ChannelPaymentResult();
		Map paraMap = MapUtil.bean2map(paymentInfo);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke("fi-channel.preAuthApply", sysTraceNo, SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
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
			String channelReturnNo = String.valueOf(resultMap.get("channelReturnNo"));
			String referenceNo = String.valueOf(resultMap.get("referenceNo"));
			channelPaymentResult.setOrgCode(orgCode);
			if (!StringUtil.isEmpty(payAmount)) {
				channelPaymentResult.setPayAmount(Long.valueOf(payAmount));
			}
			channelPaymentResult.setChannelReturnNo(channelReturnNo);
			// 参考号的获取
			channelPaymentResult.setReferenceNo(referenceNo);
			logger.info("获取参考号referenceNo:" + referenceNo);
			channelPaymentResult.setAuthorisation(String.valueOf(resultMap.get("authorisation")));
		}

		return channelPaymentResult;
	}

	public ChannelPaymentResult preAuthProcess(PaymentInfo paymentInfo, String origReturnNo, String origChannelOrderNo,
			String modelType, String paymentChannelId, String authorisation) {
		ChannelPaymentResult channelPaymentResult = new ChannelPaymentResult();
		Map paraMap = MapUtil.bean2map(paymentInfo);
		paraMap.put("origReturnNo", origReturnNo);
		paraMap.put("origChannelOrderNo", origChannelOrderNo);
		paraMap.put("origChannelOrderNo", origChannelOrderNo);
		paraMap.put("paymentChannelId", paymentChannelId);
		paraMap.put("modelType", modelType);
		paraMap.put("authorisation", authorisation);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke("fi-channel.preAuthProcess", sysTraceNo, SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
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
			String channelReturnNo = String.valueOf(resultMap.get("channelReturnNo"));
			String referenceNo = String.valueOf(resultMap.get("referenceNo"));
			channelPaymentResult.setOrgCode(orgCode);
			if (!StringUtil.isEmpty(payAmount)) {
				channelPaymentResult.setPayAmount(Long.valueOf(payAmount));
			}
			channelPaymentResult.setChannelReturnNo(channelReturnNo);
			// 参考号的获取
			channelPaymentResult.setReferenceNo(referenceNo);
			logger.info("获取参考号referenceNo:" + referenceNo);
			channelPaymentResult.setAuthorisation(String.valueOf(resultMap.get("authorisation")));
		}

		return channelPaymentResult;
	}
	
	/**根据用户号、交易类型、交易币种、支付币种查找结算币种
	 * @param paraMap
	 * @return
	 * add by zhaoyang at 20160908
	 */
	public List<SettlementCurrencyConfig> querySettlementCurrencyConfig(Map<String, Object> paraMap){
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke("fi-channel.settlementCurrencyConfigQueryHandler",
				sysTraceNo, SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result,
				new HashMap<String, Object>().getClass());
		List<SettlementCurrencyConfig> sccList = new ArrayList<SettlementCurrencyConfig>();
		List<Map> maps = (List<Map>)resultMap.get("result");
		if(maps != null){
			for(Map map:maps){
				SettlementCurrencyConfig scc = MapUtil.map2Object(SettlementCurrencyConfig.class, map);
				sccList.add(scc);
			}
		}
		
		return sccList;
	}
}
