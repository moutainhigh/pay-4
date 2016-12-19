/**
 * 
 */
package com.pay.poss.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.enums.SerCode;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.poss.dto.ChannelConfig;
import com.pay.poss.dto.PaymentChannelItemConfigDto;
import com.pay.poss.dto.PaymentChannelItemDto;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 渠道服务接口
 * 
 * @author chaoyue
 *
 */
public class ChannelClientService {

	private HessianInvokeService invokeService;
	private MemberService memberService;

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	/**
	 * 查询渠道类别
	 * 
	 * @return
	 */
	public List queryChannelCategory() {

		Map paraMap = new HashMap();
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_CATEGORY_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result,
				new HashMap<String, Object>().getClass());

		List paymentChannelCategorys = (List) resultMap
				.get("paymentChannelCategorys");
		return paymentChannelCategorys;
	}

	public List queryChannelCategory(Map map) {
		String reqMsg = JSonUtil.toJSonString(map);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_CATEGORY_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		
		Map resultMap = JSonUtil.toObject(result,
				new HashMap<String, Object>().getClass());
		
		List paymentChannelCategorys = (List) resultMap
				.get("paymentChannelCategorys");
		return paymentChannelCategorys;
	}

	/**
	 * 查询渠道信息
	 * 
	 * @param
	 * @param channelCode
	 * @param channelName
	 * @param status
	 * @return
	 */
	public List queryPaymentChannel(String channelCode, String channelName,
			String status ,Long id) {

		Map paraMap = new HashMap();

		if (!StringUtil.isEmpty(channelCode)) {
			paraMap.put("channelCode", channelCode);
		}

		if (!StringUtil.isEmpty(channelName)) {
			paraMap.put("channelName", channelName.trim());
		}

		if (!StringUtil.isEmpty(status)) {
			paraMap.put("status", status);
		}
		if(id!=null){
			paraMap.put("id", id);
		}
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(SerCode.CHANNEL_QUERY.getCode(),
				sysTraceNo, SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result,
				new HashMap<String, Object>().getClass());

		List paymentChannels = (List) resultMap.get("paymentChannels");
		return paymentChannels;
	}
			
	
	
	
	/**
	 * 
	 * @param
	 * @param
	 * @param
	 * @param
	 * @param
	 * @param
	 * @param
	 * @return
	 */
	public List queryChannelItem(PaymentChannelItemDto paymentChannelItem) {

		Map paraMap = MapUtil.bean2map(paymentChannelItem);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_ITEM_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result,
				new HashMap<String, Object>().getClass());

		List paymentChannels = (List) resultMap.get("paymentChannelItems");
		return paymentChannels;
	}

	/**
	 * 添加通道
	 * 
	 * @param
	 * @return
	 */
	public String addChannelItem(PaymentChannelItemDto paymentChannelItem) {

		String reqMsg = JSonUtil.toJSonString(MapUtil
				.bean2map(paymentChannelItem));
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_ITEM_ADD.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, String> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		String responseCode = resultMap.get("responseCode");
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			return null;
		} else {
			return resultMap.get("responseDesc");
		}
	}

	/**
	 * 更新通道信息
	 * 
	 * @param
	 * @return
	 */
	public String updateChannelItem(PaymentChannelItemDto paymentChannelItemDto) {

		String reqMsg = JSonUtil.toJSonString(MapUtil
				.bean2map(paymentChannelItemDto));
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_ITEM_UPDATE.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, String> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		String responseCode = resultMap.get("responseCode");
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			return null;
		} else {
			return resultMap.get("responseDesc");
		}
	}
	
	public String updatePaymentChannelItemConfig(PaymentChannelItemConfigDto paymentChannelItemConfigDto){
		String reqMsg = JSonUtil.toJSonString(MapUtil
				.bean2map(paymentChannelItemConfigDto));
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		//暂时先hardcode，因为没有拿到枚举的源码
		String result = invokeService.invoke(
				"40136", sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, String> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());
		String responseCode = resultMap.get("responseCode");
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			return null;
		} else {
			return resultMap.get("responseDesc");
		}
	}

	/**
	 * 获取会员配置通道
	 * 
	 * @param
	 * @return
	 */
	public List queryMemberChannelItem(PaymentChannelItemDto paymentChannelItem) {

		Map paraMap = MapUtil.bean2map(paymentChannelItem);

		String memberCode = paymentChannelItem.getMemberCode();

		if (!StringUtil.isEmpty(memberCode)) {
			paraMap.put("memberCode", memberCode.trim());
			MemberDto member = memberService.queryMemberByMemberCode(Long
					.valueOf(memberCode.trim()));
			if (null != member) {
				paraMap.put("memberType", member.getType().toString());
			}
		}

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_QUERY_MEMBER_CONFIG.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result,
				new HashMap<String, Object>().getClass());
		List paymentChannels = (List) resultMap.get("paymentChannelItems");
		return paymentChannels;
	}

	/**
	 * 批量配置商户渠道
	 * 
	 * @param memberCode
	 * @param paymentType
	 * @param ids
	 * @return
	 */
	public String configMemberChannelItem(String memberCode,
			String paymentType, String[] ids, String operator,String auto) {

		Map paraMap = new HashMap();

		paraMap.put("memberCode", memberCode.trim());
		paraMap.put("paymentType", paymentType);
		paraMap.put("operator", operator);
		paraMap.put("paymentChannelItemIds", ids);
		paraMap.put("auto", auto);

		if (!StringUtil.isEmpty(memberCode)) {
			MemberDto member = memberService.queryMemberByMemberCode(Long
					.valueOf(memberCode));
			if (null != member) {
				paraMap.put("memberType", member.getType());
			}
		}

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_ITEM_CONFIG_ADD.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, String> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		String responseCode = resultMap.get("responseCode");
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			return null;
		} else {
			return resultMap.get("responseDesc");
		}
	}

	/**
	 * 删除商户渠道配置
	 * 
	 * @param memberCode
	 * @param paymentChannelItemId
	 * @param paymentType
	 * @return
	 */
	public String delMemberChannelItem(String memberCode,
			String paymentChannelItemId, String paymentType) {

		Map paraMap = new HashMap();

		paraMap.put("memberCode", memberCode.trim());
		paraMap.put("paymentType", paymentType);
		paraMap.put("paymentChannelItemId", paymentChannelItemId);

		if (!StringUtil.isEmpty(memberCode)) {
			MemberDto member = memberService.queryMemberByMemberCode(Long
					.valueOf(memberCode));
			if (null != member) {
				paraMap.put("memberType", member.getType());
			}
		}

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_ITEM_CONFIG_DEL.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, String> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		String responseCode = resultMap.get("responseCode");
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			return null;
		} else {
			return resultMap.get("responseDesc");
		}
	}

	/**
	 * 查询默认通道配置
	 * 
	 * @param paymentChannelItem
	 * @return
	 */
	public List queryDefaultChannelItem(PaymentChannelItemDto paymentChannelItem) {

		Map paraMap = MapUtil.bean2map(paymentChannelItem);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_QUERY_DEFAULT.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result,
				new HashMap<String, Object>().getClass());
		List paymentChannels = (List) resultMap.get("paymentChannelItems");
		return paymentChannels;
	}

	/**
	 * 配置默认通道
	 * 
	 * @param memberType
	 * @param paymentType
	 * @param ids
	 * @param operator
	 * @return
	 */
	public String configDefaultChannelItem(String memberType,
			String paymentType, String[] ids, String operator) {

		Map paraMap = new HashMap();

		paraMap.put("memberType", memberType);
		paraMap.put("paymentType", paymentType);
		paraMap.put("operator", operator);
		paraMap.put("paymentChannelItemIds", ids);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_ITEM_DEFAULT_ADD.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, String> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		String responseCode = resultMap.get("responseCode");
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			return null;
		} else {
			return resultMap.get("responseDesc");
		}
	}

	public String delDefaultChannelItem(String memberType,
			List<String> paymentChannelItemIds, String paymentType) {

		Map paraMap = new HashMap();

		paraMap.put("memberType", memberType);
		paraMap.put("paymentType", paymentType);
		paraMap.put("paymentChannelItemIds", paymentChannelItemIds);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_ITEM_DEFAULT_DEL.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, String> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		String responseCode = resultMap.get("responseCode");
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			return null;
		} else {
			return resultMap.get("responseDesc");
		}
	}

	/**
	 * 查询二级商户配置
	 * 
	 * @param memberCode
	 * @param
	 * @return
	 */
	public List querySecondChannelItem(String memberCode, String paymentChannelItemId,
			String orgMerchantCode) {

		Map paraMap = new HashMap();
		paraMap.put("memberCode", memberCode);
		paraMap.put("paymentChannelItemId", paymentChannelItemId);
		paraMap.put("orgMerchantCode", orgMerchantCode);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_ITEM_SECOND_CONFIG.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result,
				new HashMap<String, Object>().getClass());
		List paymentChannels = (List) resultMap.get("secondRelations");
		return paymentChannels;
	}

	/**
	 * 配置二级商户
	 * 
	 * @param memberCode
	 * @param
	 * @param
	 * @param operator
	 * @return
	 */
	public String configSecondMemberChannelItem(String memberCode,
			String orgCode, String orgMerchantCode, String operator,
			String transType,String currencyCode,String payChannelItemId,String cardOrg,String channelConfigId) {

		Map paraMap = new HashMap();

		paraMap.put("memberCode", memberCode.trim());
		paraMap.put("orgCode", orgCode);
		paraMap.put("operator", operator);
		paraMap.put("orgMerchantCode", orgMerchantCode);
		paraMap.put("transType", transType);
		paraMap.put("currencyCode", currencyCode);
		paraMap.put("payChannelItemId", payChannelItemId);
		paraMap.put("cardOrg", cardOrg);
		paraMap.put("channelConfigId", channelConfigId);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_ITEM_SECOND_CONFIG_ADD.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, String> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		String responseCode = resultMap.get("responseCode");
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			return null;
		} else {
			return resultMap.get("responseDesc");
		}
	}

	public String delSecondMemberChannelItem(String id, String orgCode,
			String orgMerchantCode,String cardOrg) {

		Map paraMap = new HashMap();

		paraMap.put("id", id);
		paraMap.put("orgCode", orgCode);
		paraMap.put("orgMerchantCode", orgMerchantCode);
		paraMap.put("cardOrg",cardOrg);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_ITEM_SECOND_CONFIG_DEL.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, String> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		String responseCode = resultMap.get("responseCode");
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			return null;
		} else {
			return resultMap.get("responseDesc");
		}
	}

	/**
	 * 查询渠道配置
	 * 
	 * @param
	 * @return
	 */
	private String addChannelConfig(String orgCode, String orgMerchantCode,
			String orgKey, String operator, String keyFilePath,
			String terminalCode, String transType, String accessCode,
			String mcc, String currencyCode,String pattern,String requestMerchantName,String merchantBillName,String fitMerchantType) {

		Map paraMap = new HashMap();

		if (!StringUtil.isEmpty(orgCode)) {
			paraMap.put("orgCode", orgCode);
		}
		if (!StringUtil.isEmpty(orgMerchantCode)) {
			paraMap.put("orgMerchantCode", orgMerchantCode);
		}
		if (!StringUtil.isEmpty(orgKey)) {
			paraMap.put("orgKey", orgKey);
		}
		if (!StringUtil.isEmpty(operator)) {
			paraMap.put("operator", operator);
		}
		if (!StringUtil.isEmpty(keyFilePath)) {
			paraMap.put("keyFilePath", keyFilePath);
		}
		if (!StringUtil.isEmpty(mcc)) {
			paraMap.put("mcc", mcc);
		}
		if (!StringUtil.isEmpty(currencyCode)) {
			paraMap.put("currencyCode", currencyCode);
		}
		if (!StringUtil.isEmpty(pattern)) {
			paraMap.put("pattern", pattern);
		}
		if(!StringUtil.isEmpty(requestMerchantName)){
			paraMap.put("requestMerchantName", requestMerchantName);
		}
		
		if(!StringUtil.isEmpty(merchantBillName)){
			paraMap.put("merchantBillName", merchantBillName);
		}

		if(!StringUtil.isEmpty(fitMerchantType)){
			paraMap.put("fitMerchantType", fitMerchantType);
		}

		paraMap.put("terminalCode", terminalCode);
		paraMap.put("transType", transType);
		paraMap.put("accessCode", accessCode);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_CONFIG_ADD.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, String> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		String responseCode = resultMap.get("responseCode");
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			return null;
		} else {
			return resultMap.get("responseDesc");
		}
	}


	/**
	 * 新增渠道配置
	 * 添加“映射网址”为了不影响其他模块的调用，重载一个方法 add by davis.guo at 2016-08-16 
	 * @param orgCode
	 * @param orgMerchantCode
	 * @param orgKey
	 * @param operator
	 * @param keyFilePath
	 * @param terminalCode
	 * @param transType
	 * @param accessCode
	 * @param mcc
	 * @param currencyCode
	 * @param pattern
	 * @param requestMerchantName
	 * @param merchantBillName
	 * @param fitMerchantType
	 * @param supportWebsite
	 * @return
	 */
	public String addChannelConfig(String orgCode, String orgMerchantCode,
			String orgKey, String operator, String keyFilePath,
			String terminalCode, String transType, String accessCode,
			String mcc, String currencyCode,String pattern,String requestMerchantName,String merchantBillName,String fitMerchantType,String supportWebsite) {

		Map paraMap = new HashMap();

		if (!StringUtil.isEmpty(orgCode)) {
			paraMap.put("orgCode", orgCode);
		}
		if (!StringUtil.isEmpty(orgMerchantCode)) {
			paraMap.put("orgMerchantCode", orgMerchantCode);
		}
		if (!StringUtil.isEmpty(orgKey)) {
			paraMap.put("orgKey", orgKey);
		}
		if (!StringUtil.isEmpty(operator)) {
			paraMap.put("operator", operator);
		}
		if (!StringUtil.isEmpty(keyFilePath)) {
			paraMap.put("keyFilePath", keyFilePath);
		}
		if (!StringUtil.isEmpty(mcc)) {
			paraMap.put("mcc", mcc);
		}
		if (!StringUtil.isEmpty(currencyCode)) {
			paraMap.put("currencyCode", currencyCode);
		}
		if (!StringUtil.isEmpty(pattern)) {
			paraMap.put("pattern", pattern);
		}
		if(!StringUtil.isEmpty(requestMerchantName)){
			paraMap.put("requestMerchantName", requestMerchantName);
		}
		
		if(!StringUtil.isEmpty(merchantBillName)){
			paraMap.put("merchantBillName", merchantBillName);
		}
		if(!StringUtil.isEmpty(fitMerchantType)){
			paraMap.put("fitMerchantType", fitMerchantType);
		}
		if(!StringUtil.isEmpty(supportWebsite)){
			paraMap.put("supportWebsite", supportWebsite);
		}

		paraMap.put("terminalCode", terminalCode);
		paraMap.put("transType", transType);
		paraMap.put("accessCode", accessCode);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_CONFIG_ADD.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, String> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		String responseCode = resultMap.get("responseCode");
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			return null;
		} else {
			return resultMap.get("responseDesc");
		}
	}

	public String delChannelConfig(String id) {

		Map paraMap = new HashMap();
		paraMap.put("id", id);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_CONFIG_DEL.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, String> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		String responseCode = resultMap.get("responseCode");
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			return null;
		} else {
			return resultMap.get("responseDesc");
		}
	}

	private String updateChannelConfig(String id, String orgCode,
			String orgMerchantCode, String orgKey, String operator,
			String keyFilePath, String status, String terminalCode,
			String transType, String accessCode, String mcc, String currencyCode,String pattern
			,String requestMerchantName,String merchantBillName, String fitMerchantType) {

		Map paraMap = new HashMap();

		if (!StringUtil.isEmpty(orgCode)) {
			paraMap.put("orgCode", orgCode);
		}
		if (!StringUtil.isEmpty(orgMerchantCode)) {
			paraMap.put("orgMerchantCode", orgMerchantCode);
		}
		if (!StringUtil.isEmpty(orgKey)) {
			paraMap.put("orgKey", orgKey);
		}
		if (!StringUtil.isEmpty(operator)) {
			paraMap.put("operator", operator);
		}
		if (!StringUtil.isEmpty(keyFilePath)) {
			paraMap.put("keyFilePath", keyFilePath);
		}
		if (!StringUtil.isEmpty(status)) {
			paraMap.put("status", status);
		}
		if (!StringUtil.isEmpty(mcc)) {
			paraMap.put("mcc", mcc);
		}
		if (!StringUtil.isEmpty(currencyCode)) {
			paraMap.put("currencyCode", currencyCode);
		}
		if (!StringUtil.isEmpty(pattern)) {
			paraMap.put("pattern", pattern);
		}
		if(!StringUtil.isEmpty(requestMerchantName)){
			paraMap.put("requestMerchantName", requestMerchantName);
		}
		
		if(!StringUtil.isEmpty(merchantBillName)){
			paraMap.put("merchantBillName",merchantBillName);
		}
		if(!StringUtil.isEmpty(fitMerchantType)){
			paraMap.put("fitMerchantType",fitMerchantType);
		}
		
		paraMap.put("id", id);
		paraMap.put("terminalCode", terminalCode);
		paraMap.put("transType", transType);
		paraMap.put("accessCode", accessCode);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_CONFIG_UPDATE.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, String> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		String responseCode = resultMap.get("responseCode");
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			return null;
		} else {
			return resultMap.get("responseDesc");
		}
	}

	/**
	 * 
	 * 修改渠道配置
	 * 添加“映射网址”为了不影响其他模块的调用，重载一个方法 add by davis.guo at 2016-08-16
	 * @param id
	 * @param orgCode
	 * @param orgMerchantCode
	 * @param orgKey
	 * @param operator
	 * @param keyFilePath
	 * @param status
	 * @param terminalCode
	 * @param transType
	 * @param accessCode
	 * @param mcc
	 * @param currencyCode
	 * @param pattern
	 * @param requestMerchantName
	 * @param merchantBillName
	 * @param fitMerchantType
	 * @param supportWebsite
	 * @return
	 */
	public String updateChannelConfig(String id, String orgCode,
			String orgMerchantCode, String orgKey, String operator,
			String keyFilePath, String status, String terminalCode,
			String transType, String accessCode, String mcc, String currencyCode,String pattern
			,String requestMerchantName,String merchantBillName,String fitMerchantType,String supportWebsite) {

		Map paraMap = new HashMap();

		if (!StringUtil.isEmpty(orgCode)) {
			paraMap.put("orgCode", orgCode);
		}
		if (!StringUtil.isEmpty(orgMerchantCode)) {
			paraMap.put("orgMerchantCode", orgMerchantCode);
		}
		if (!StringUtil.isEmpty(orgKey)) {
			paraMap.put("orgKey", orgKey);
		}
		if (!StringUtil.isEmpty(operator)) {
			paraMap.put("operator", operator);
		}
		if (!StringUtil.isEmpty(keyFilePath)) {
			paraMap.put("keyFilePath", keyFilePath);
		}
		if (!StringUtil.isEmpty(status)) {
			paraMap.put("status", status);
		}
		if (!StringUtil.isEmpty(mcc)) {
			paraMap.put("mcc", mcc);
		}
		if (!StringUtil.isEmpty(currencyCode)) {
			paraMap.put("currencyCode", currencyCode);
		}
		if (!StringUtil.isEmpty(pattern)) {
			paraMap.put("pattern", pattern);
		}
		if(!StringUtil.isEmpty(requestMerchantName)){
			paraMap.put("requestMerchantName", requestMerchantName);
		}
		if(!StringUtil.isEmpty(merchantBillName)){
			paraMap.put("merchantBillName",merchantBillName);
		}
		if(!StringUtil.isEmpty(fitMerchantType)){
			paraMap.put("fitMerchantType",fitMerchantType);
		}
		if(!StringUtil.isEmpty(supportWebsite)){
			paraMap.put("supportWebsite",supportWebsite);
		}
		
		paraMap.put("id", id);
		paraMap.put("terminalCode", terminalCode);
		paraMap.put("transType", transType);
		paraMap.put("accessCode", accessCode);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_CONFIG_UPDATE.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, String> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		String responseCode = resultMap.get("responseCode");
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			return null;
		} else {
			return resultMap.get("responseDesc");
		}
	}

	public Map queryChannelConfig(String id, String orgCode,
			String orgMerchantCode, String status, String mcc,
			String currencyCode,String merchantBillName,String fitMerchantType,String aucrFlag,Page page) {

		Map paraMap = new HashMap();

		if(page!=null){
			paraMap.put("page", page);
		}
		if (!StringUtil.isEmpty(orgCode)) {
			paraMap.put("orgCode", orgCode);
		}
		if (!StringUtil.isEmpty(orgMerchantCode)) {
			paraMap.put("orgMerchantCode", orgMerchantCode);
		}
		if (!StringUtil.isEmpty(status)) {
			paraMap.put("status", status);
		}

		if (!StringUtil.isEmpty(id)) {
			paraMap.put("id", id);
		}
		if (!StringUtil.isEmpty(mcc)) {
			paraMap.put("mcc", mcc);
		}
		if (!StringUtil.isEmpty(currencyCode)) {
			paraMap.put("currencyCode", currencyCode);
		}
		
		if(!StringUtil.isEmpty(fitMerchantType)){
			paraMap.put("fitMerchantType",fitMerchantType);
		}
		if(!StringUtil.isEmpty(aucrFlag)){
			paraMap.put("aucrFlag",aucrFlag);
		}


		
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_CONFIG_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		return resultMap;
	}

	public String delPaymentChannel(String id) {
		Map paraMap = new HashMap();
		paraMap.put("id", id);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_DEL.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, String> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		String responseCode = resultMap.get("responseCode");
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			return null;
		} else {
			return resultMap.get("responseDesc");
		}
	}

	public String addPaymentChannel(String name, String code, String description,String loginId) {
		Map paraMap = new HashMap();
		if(!StringUtil.isEmpty(name)){
			paraMap.put("name", name);
		}
		
		if(!StringUtil.isEmpty(code)){
			paraMap.put("code", code);
		}
		if(!StringUtil.isEmpty(description)){
			paraMap.put("description", description);
		}
		if(!StringUtil.isEmpty(loginId)){
			paraMap.put("operator", loginId);
		}

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_ADD.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, String> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		String responseCode = resultMap.get("responseCode");
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			return null;
		} else {
			return resultMap.get("responseDesc");
		}
	}

	public String updatePaymentChannel(String name, String code,
			String description, String loginId, String status,String id) {
		Map paraMap = new HashMap();
		if(!StringUtil.isEmpty(name)){
			paraMap.put("name", name);
		}
		
		if(!StringUtil.isEmpty(code)){
			paraMap.put("code", code);
		}
		if(!StringUtil.isEmpty(description)){
			paraMap.put("description", description);
		}
		if(!StringUtil.isEmpty(loginId)){
			paraMap.put("operator", loginId);
		}
		if(!StringUtil.isEmpty(status)){
			paraMap.put("status", status);
		}
		if(!StringUtil.isEmpty(id)){
			paraMap.put("id", id);
		}

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_UPDATE.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, String> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		String responseCode = resultMap.get("responseCode");
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			return null;
		} else {
			return resultMap.get("responseDesc");
		}
	}

	public Map removeChannelCategory(
			String id) {
		Map paraMap = new HashMap();
		if(!StringUtil.isEmpty(id)){
			paraMap.put("id", id);
		}
		
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_CATEGORY_DEL.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
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

	public Map addChannelCategory(Map<String, String> map) {
		String reqMsg = JSonUtil.toJSonString(map);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_CATEGORY_ADD.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
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

	public Map updateChannelCategory(Map<String, String> map) {
		
		String reqMsg = JSonUtil.toJSonString(map);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_CATEGORY_UPDATE.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
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

	/***
	 * 查询渠道币种配置
	 * @param paraMap
	 * @return
	 */
	public Map queryChannelCurrency(Map paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_CURRENCY_CONF_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
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
	/***
	 * 删除渠道币种配置
	 * @param paraMap
	 * @return
	 */
	public Map deleteChannelCurrency(Map paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_CURRENCY_CONF_DETELE.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
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
	/***
	 * 修改渠道币种配置
	 * @param paraMap
	 * @return
	 */
	public Map updateChannelCurrency(Map paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_CURRENCY_CONF_UPDATE.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
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
	/***
	 * 添加渠道币种配置
	 * @param
	 * @return
	 */
	public Map addChannelCurrency(List paras) {
		Map paraMap=new HashMap();
		paraMap.put("list", paras);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_CURRENCY_CONF_ADD.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
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
	/***
	 * 添加渠道币种配置到审核表
	 * @param
	 * @return
	 */
	public Map addChannelCurrencyReviewed(List paras) {
		Map paraMap=new HashMap();
		paraMap.put("list", paras);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_CURRENCY_REVIEWED_ADD.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
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
	/**
	 * 批量添加 渠道配置
	 * @param channelConfigs
	 * @return
	 */
	public Map addBatchChannelConfig(List<ChannelConfig> channelConfigs) {
		Map paraMap=new HashMap();
		paraMap.put("list", channelConfigs);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_CONFIG_BATCH_ADD.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
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
	
	/**
	 * 增加支付渠道币种配置
	 * @param paraMap
	 * @return
	 */
	public Map addChannelItemRCurrency(Map paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNELITEM_R_CURRENCY_ADD.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
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
	/**
	 * 通道二级商户号添加的时候查询那些可用的二级商户号
	 * @param
	 * @return
	 */
	public Map batchQuery(Map<String, String> paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_ITEM_SECOND_BATCH_CONFIG_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
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
	
	/**
	 * 查询支付渠道币种配置
	 * @param paraMap
	 * @return
	 */
	public Map queryChannelItemRCurrency(Map paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNELITEM_R_CURRENCY_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
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
	
	
	/**
	 * 失效支付渠道币种配置
	 * @param paraMap
	 * @return
	 */
	public Map validChannelItemRCurrency(Map paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNELITEM_R_CURRENCY_UPDATE.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
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
	/**
	 * 查询已提交待审核的数据
	 * @param paraMap
	 * @return
	 */
	public Map<String, Object> queryReviewedChannelCurrency(
			Map<String, String> paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_CURRENCY_REVIEWED_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, Object> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());
		return resultMap;
	}
	/**
	 *  新增审核
	 * @param paraMap
	 * @return
	 */
	public Map<String, Object> reviewedChannelCurrency(
			Map<String, String> paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_CURRENCY_REVIEWED.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, Object> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());
		return resultMap;
	}

	/**
	 * 查询会员渠道国家配置
	 * @param paraMap
	 * @return
	 */
	public Map<String, Object> queryMemVorgVcountry(
			Map<String, Object> paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				"fi-channel.parnterChannelCountryQueryHandler", sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, Object> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());
		return resultMap;
	}

	/**
	 * 修改会员渠道国家配置
	 * @param paraMap
	 * @return
	 */
	public Map<String, Object> updateMemVorgVcountry(
			Map<String, Object> paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				"fi-channel.parnterChannelCountryUpdateHandler", sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, Object> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());
		return resultMap;
	}

	/**
	 * 增加会员渠道国家配置
	 * @param paraMap
	 * @return
	 */
	public Map<String, Object> addMemVorgVcountry(
			Map<String, Object> paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				"fi-channel.parnterChannelCountryAddHandler", sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, Object> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());
		return resultMap;
	}


	/**
	 * 删除会员渠道国家配置
	 * @param paraMap
	 * @return
	 */
	public Map<String, Object> delMemVorgVcountry(
			Map<String, String> paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				"fi-channel.parnterChannelCountryDelHandler", sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, Object> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());
		return resultMap;
	}

	public boolean addToFree(String[] ids){
		Map<String, Object> paraMap =  new HashMap<String, Object>();
		paraMap.put("ids",ids);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				"fi-channel.add2FreeHanlder", sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map<String, Object> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		return ResponseCodeEnum.SUCCESS.getCode().equals(resultMap.get("responseCode"));
	}

	public boolean delMemberConChannelConfigHandler(String id){
		Map<String, Object> paraMap =  new HashMap<String, Object>();
		paraMap.put("id",id);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				"fi-channel.delMemberConChannelConfigHandler", sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map<String, Object> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		return ResponseCodeEnum.SUCCESS.getCode().equals(resultMap.get("responseCode"));
	}


	public boolean memberSecondLimitDelHandler(String id){
		Map<String, Object> paraMap =  new HashMap<String, Object>();
		paraMap.put("id",id);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				"fi-channel.memberSecondLimitDelHandler", sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map<String, Object> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		return ResponseCodeEnum.SUCCESS.getCode().equals(resultMap.get("responseCode"));
	}

	public Map memberSecondLimitInsertHandler(String memberCode,String cardOrg, String limitTimes,
												  String limitAmount, String limitDay, String switchFlag, String operator){
		Map<String, Object> paraMap =  new HashMap<String, Object>();
		paraMap.put("partnerId",memberCode);
		paraMap.put("cardOrg",cardOrg);
		paraMap.put("limitTimes",limitTimes);
		paraMap.put("limitAmount",limitAmount);
		paraMap.put("limitDay",limitDay);
		paraMap.put("switchFlag",switchFlag);
		paraMap.put("operator",operator);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				"fi-channel.memberSecondLimitInsertHandler", sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map<String, Object> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		return resultMap;
	}

	public Map memberSecondLimitQueryHandler(Map<String, Object> paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				"fi-channel.memberSecondLimitQueryHandler", sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result,
				new HashMap<String, Object>().getClass());
		return resultMap;
	}
	public Map memberSecondLimitUpdateHandler(String limitTimes,String id, String limitAmount, String limitDay, String switchFlag, String operator){
		Map<String, Object> paraMap =  new HashMap<String, Object>();
		paraMap.put("limitTimes",limitTimes);
		paraMap.put("limitAmount",limitAmount);
		paraMap.put("limitDay",limitDay);
		paraMap.put("switchFlag",switchFlag);
		paraMap.put("operator",operator);
		paraMap.put("id",id);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				"fi-channel.memberSecondLimitUpdateHandler", sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map<String, Object> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		return resultMap;
	}

	public boolean pollOutFreePoolHandler(String id){
		Map<String, Object> paraMap =  new HashMap<String, Object>();
		paraMap.put("id",id);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				"fi-channel.pollOutFreePoolHandler", sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map<String, Object> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		return ResponseCodeEnum.SUCCESS.getCode().equals(resultMap.get("responseCode"));
	}

	public List queryMemberConChannelConfigHandler(String memberCode, String paymentChannelItemId,
											 String orgMerchantCode,String channelConfigId) {

		Map paraMap = new HashMap();
		paraMap.put("partnerId", memberCode);
		paraMap.put("paymentChannelItemId", paymentChannelItemId);
		paraMap.put("orgMerchantCode", orgMerchantCode);
		paraMap.put("channelConfigId", channelConfigId);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				"fi-channel.queryMemberConChannelConfigHandler", sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result,
				new HashMap<String, Object>().getClass());
		return (List) resultMap.get("result");
	}

	public Map memberConnectHisQueryHandler(Map<String, Object> paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				"fi-channel.memberConnectHisQueryHandler", sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result,
				new HashMap<String, Object>().getClass());
		return resultMap;
	}


	/**分页查找结算币种
	 * @param paraMap
	 * @return
	 */
	public Map querySettlementCurrencyConfig(Map<String, Object> paraMap){
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
		return resultMap;
	}
	
	
	/**添加结算币种对象
	 * @param paraMap
	 * @return
	 */
	public Map<String,Object> addSettlementCurrencyConfig(Map<String, Object> paraMap){
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				"fi-channel.settlementCurrencyConfigAddHandler", sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map<String, Object> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		return resultMap;
	}
	
	/**删除结算币种对象
	 * @param paraMap
	 * @return
	 */
	public boolean deleteSettlementCurrencyConfig(String id){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("configId", id);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				"fi-channel.settlementCurrencyConfigDelHandler", sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map<String, Object> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		return ResponseCodeEnum.SUCCESS.getCode().equals(resultMap.get("responseCode"));
	}
	
	/**更新结算币种对象
	 * @param paraMap
	 * @return
	 */
	public Map<String,Object> updateSettlementCurrencyConfig(Map<String, Object> paraMap){
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				"fi-channel.settlementCurrencyConfigUpdateHandler", sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map<String, Object> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		return resultMap;
	}

	public Map channelCurrencyProcess(Map<String, Object> paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String result = invokeService.invoke(
				"fi-channel.channelCurrencyProcessHandler", sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
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
