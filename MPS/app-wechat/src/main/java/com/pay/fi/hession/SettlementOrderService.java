/**
 * 
 */
package com.pay.fi.hession;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.fi.dto.PartnerSettlementOrder;
import com.pay.inf.dao.Page;
import com.pay.inf.enums.SerCode;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 清算订单管理
 * 
 * @author chaoyue
 *
 */
public class SettlementOrderService {

	private HessianInvokeService invokeService;

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}

	/**
	 * 获取商户配置域名
	 * 
	 * @param partnerId
	 * @param siteId
	 * @return
	 */
	public Map settlementQuery(
			PartnerSettlementOrder partnerSettlementOrder, Page page) {

		Map paraMap = MapUtil.bean2map(partnerSettlementOrder);
		paraMap.put("page", page);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBSITE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_SETTLEMENT_ORDER_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.WEBSITE.getCode(),
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
		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		
		List<PartnerSettlementOrder> list = MapUtil.map2List(
				PartnerSettlementOrder.class, returnMap);
		
		return resultMap;
	}
	
	/**
	 * 获取商户配置域名
	 * 
	 * @param partnerId
	 * @param siteId
	 * @return
	 */
	public Map<String,PartnerSettlementOrder> settlementCoutQuery(
			PartnerSettlementOrder partnerSettlementOrder) {

		Map paraMap = MapUtil.bean2map(partnerSettlementOrder);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBSITE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_SETTLEMENT_COUNT_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.WEBSITE.getCode(),
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

		List<PartnerSettlementOrder> list = MapUtil.map2List(
				PartnerSettlementOrder.class, returnMap);
		Map<String,PartnerSettlementOrder> map = null;
		
		if(list!=null&&list.size()>0){
			map = new HashMap<String, PartnerSettlementOrder>();
			
			for(PartnerSettlementOrder order:list){
				if(order.getSettlementFlg()==1){
					String key = order.getSettlementCurrencyCode()+"-1";
					
					BigDecimal rate =new BigDecimal(order.getSettlementRate());
					BigDecimal amount = order.getAmount().multiply(rate);
					
					if(map.containsKey(key)){
						PartnerSettlementOrder tmp = map.get(key);
						tmp.setAmount(tmp.getAmount().add(amount));
						map.put(key,tmp);
					}else{
						order.setAmount(amount);
						map.put(key,order);
					}
				}else{
					String key = order.getCurrencyCode()+"-0";
					
					if(!map.containsKey(key)){
						map.put(key,order);
					}else{
						PartnerSettlementOrder order_ = map.get(key);
						order_.setAmount(order_.getAmount().add(order.getAmount()));
						map.put(key,order_);
					}		
				}
			}
		}
		return map;
	}

}
