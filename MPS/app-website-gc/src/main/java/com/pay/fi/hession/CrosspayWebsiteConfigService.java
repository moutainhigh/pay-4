/**
 * 
 */
package com.pay.fi.hession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.fi.dto.PartnerWebsiteConfig;
import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.enums.SerCode;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 商户域名授权
 * 
 * @author chaoyue
 *
 */
public class CrosspayWebsiteConfigService {

	private HessianInvokeService invokeService;

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}

	public boolean merchantWebsiteAdd(PartnerWebsiteConfig partnerWebsiteConfig) {

		Map paraMap = MapUtil.bean2map(partnerWebsiteConfig);
		paraMap.put("type", "1");

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBSITE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_MERCHANT_WEBSITE_ADD.getCode(),
				sysTraceNo, SystemCodeEnum.WEBSITE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");

		return ResponseCodeEnum.SUCCESS.getCode().equals(responseCode);
	}
	
	
	public boolean merchantWebsiteAddList(List list) {

		//Map paraMap = MapUtil.bean2map(list);
		Map paraMap= new HashMap();
		paraMap.put("type","2");//2标识批量添加		
		paraMap.put("list", list);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBSITE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_MERCHANT_WEBSITE_ADD.getCode(),
				sysTraceNo, SystemCodeEnum.WEBSITE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");

		return ResponseCodeEnum.SUCCESS.getCode().equals(responseCode);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean merchantWebsiteDel(String id) {

		Map<String, String> paraMap = new HashMap();
		paraMap.put("id", id);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBSITE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_MERCHANT_WEBSITE_DEL.getCode(),
				sysTraceNo, SystemCodeEnum.WEBSITE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		return ResponseCodeEnum.SUCCESS.getCode().equals(responseCode);
	}

	/**
	 * 
	 * @param id
	 * @param partnerId
	 * @param status
	 * @param siteId
	 * @param operator
	 * @param remark
	 * @return
	 */
	public boolean merchantWebsiteUpdate(
			PartnerWebsiteConfig partnerWebsiteConfig) {

		Map paraMap = MapUtil.bean2map(partnerWebsiteConfig);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBSITE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_MERCHANT_WEBSITE_UPDATE.getCode(),
				sysTraceNo, SystemCodeEnum.WEBSITE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		return ResponseCodeEnum.SUCCESS.getCode().equals(responseCode);
	}

	/**
	 * 获取商户配置域名
	 * 
	 * @param partnerId
	 * @param siteId
	 * @return
	 */
	public Map merchantWebsiteQuery(
			PartnerWebsiteConfig partnerWebsiteConfig, Page page) {

		Map paraMap = MapUtil.bean2map(partnerWebsiteConfig);
		paraMap.put("page", page);
		paraMap.put("type", "1");
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBSITE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_MERCHANT_WEBSITE_QUERY.getCode(),
				sysTraceNo, SystemCodeEnum.WEBSITE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		return resultMap;
	}
	
	public List<PartnerWebsiteConfig> merchantWebsiteQuery1(
			PartnerWebsiteConfig partnerWebsiteConfig, Page page) {

		Map paraMap = MapUtil.bean2map(partnerWebsiteConfig);
		paraMap.put("page", page);
		paraMap.put("type", "1");//status 1标识为单个添加
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBSITE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_MERCHANT_WEBSITE_QUERY.getCode(),
				sysTraceNo, SystemCodeEnum.WEBSITE.getCode(),
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
		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);

		List<PartnerWebsiteConfig> resultList = MapUtil.map2List(
				PartnerWebsiteConfig.class, returnMap);

		return resultList;
	}
	
	
	public List<PartnerWebsiteConfig> merchantWebsiteQueryList(
			 List<PartnerWebsiteConfig> list, Page page) {

		//Map paraMap = MapUtil.bean2map(partnerWebsiteConfig);
		Map map= new HashMap();
		map.put("page", page);
		map.put("list", list);
		map.put("type","2");//状态2标识为批量查询
		String reqMsg = JSonUtil.toJSonString(map);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBSITE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_MERCHANT_WEBSITE_QUERY.getCode(),
				sysTraceNo, SystemCodeEnum.WEBSITE.getCode(),
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
		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);

		List<PartnerWebsiteConfig> resultList = MapUtil.map2List(
				PartnerWebsiteConfig.class, returnMap);

		return resultList;
	}
	

}
