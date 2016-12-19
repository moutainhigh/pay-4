/**
 * 
 */
package com.pay.poss.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.enums.SerCode;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 商户域名授权
 * 
 * @author chaoyue
 * 订单管理核心
 */
public class CrosspayWebsiteConfigService {

	private HessianInvokeService invokeService;

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}

	public boolean merchantWebsiteAdd(String partnerId, String siteId,
			String operator, String remark) {

		Map<String, String> paraMap = new HashMap();
		paraMap.put("partnerId", partnerId);
		paraMap.put("url", siteId);
		paraMap.put("operator", operator);
		paraMap.put("remark", remark);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_MERCHANT_WEBSITE_ADD.getCode(),
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

		return ResponseCodeEnum.SUCCESS.getCode().equals(responseCode);
	}

	public boolean merchantWebsiteDel(String id) {

		Map<String, String> paraMap = new HashMap();
		paraMap.put("id", id);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_MERCHANT_WEBSITE_DEL.getCode(),
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
		return ResponseCodeEnum.SUCCESS.getCode().equals(responseCode);
	}

	public boolean merchantWebsiteUpdate(String id, String partnerId,
			String status, String siteId, String operator, String remark,String sendCredorax,String category) {

		Map<String, String> paraMap = new HashMap();
		paraMap.put("id", id);
		paraMap.put("partnerId", partnerId);
		paraMap.put("url", siteId);
		paraMap.put("status", status);
		paraMap.put("operator", operator);
		paraMap.put("remark", remark);
		paraMap.put("sendCredorax", sendCredorax);
		paraMap.put("category", category);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_MERCHANT_WEBSITE_UPDATE.getCode(),
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
		return ResponseCodeEnum.SUCCESS.getCode().equals(responseCode);
	}

	/**
	 * 获取商户配置域名
	 * 
	 * @param partnerId
	 * @param siteId
	 * @return
	 */
	public Map crosspayMerchantWebsiteQuery(String partnerId,
			String siteId, String status, Page page,String url,String statusIn) {

		Map paraMap = new HashMap();

		if (!StringUtil.isEmpty(partnerId)) {
			paraMap.put("memberCode", partnerId);//模糊查询会员号
		}
		if (!StringUtil.isEmpty(siteId)) {
			paraMap.put("url", siteId);
		}//urlQueryModel
		if(!StringUtil.isEmpty(url)){
			paraMap.put("siteId", url);
		}
		if (!StringUtil.isEmpty(status)) {
			paraMap.put("status", status);
		}
		if (!StringUtil.isEmpty(statusIn)) {
			paraMap.put("statusIn", statusIn);
		}

		paraMap.put("page", page);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_MERCHANT_WEBSITE_QUERY.getCode(),
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
	 * 批量审核域名
	 * @param map
	 * @return
	 */
	public boolean updateBacthSiteSetStatus(Map map) {
		String reqMsg = JSonUtil.toJSonString(map);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_MERCHANT_WEBSITE_UPDATE.getCode(),
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
		return ResponseCodeEnum.SUCCESS.getCode().equals(responseCode);
	}
	/***
	 * 通过转态查询域名的数量
	 * @author delin.dong
	 * @date 2016年6月8日15:01:47
	 * @param map
	 * @return
	 */
	public Map countWebsiteByStatus(Map<String, String> map) {
		String reqMsg = JSonUtil.toJSonString(map);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_COUNT_WEBSITE_BY_STATUS.getCode(), sysTraceNo,
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
		return resultMap;		
	}

}
