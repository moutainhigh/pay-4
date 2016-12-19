/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.notify;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.crosspay.model.OrderEmailNotify;
import com.pay.txncore.crosspay.model.OrderEmailNotifyCriteria;
import com.pay.txncore.crosspay.service.IOrderEmailNotifyService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 获取商户下单邮件通知配置信息
 * 
 * @author davis.guo at 2016-08-31
 */
public class OrderEmailNotifyQueryHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(OrderEmailNotifyQueryHandler.class);
	
	private IOrderEmailNotifyService orderEmailNotifyService;

	public void setOrderEmailNotifyService(
			IOrderEmailNotifyService orderEmailNotifyService) {
		this.orderEmailNotifyService = orderEmailNotifyService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map resultMap = new HashMap();

		Map paraMap = null;
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
		} catch (Exception e) {
			logger.error(e.getMessage());
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}
		Map queryMap = (Map) paraMap.get("orderEmailNotifyCriteria");
		Map pageMap = (Map) paraMap.get("page");
		Page page = MapUtil.map2Object(Page.class, pageMap);

		OrderEmailNotifyCriteria oenCriteria = MapUtil.map2Object(OrderEmailNotifyCriteria.class, queryMap);//new OrderEmailNotifyCriteria();
		/*if(oenCriteria==null || oenCriteria.getId()==null || oenCriteria.getId()<=0 )
		{
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}*/
		
		
		/*Long memberCode = (Long) paraMap.get("memberCode");
		Long merchantCode = (Long) paraMap.get("merchantCode");
		String merchantName = (String) paraMap.get("merchantName");
		String loginName = (String) paraMap.get("loginName");
		String emailCompany = (String) paraMap.get("emailCompany");
		String emailNotify = (String) paraMap.get("emailNotify");
		
		Integer openFlag = (Integer) paraMap.get("openFlag");
		String startDate = (String) paraMap.get("startDate");
		String endDate = (String) paraMap.get("endDate");
		String operator = (String) paraMap.get("operator") ;
		Long maxTradeOrderNo = (Long) paraMap.get("maxTradeOrderNo") ;

		if (memberCode != null) {
			oenCriteria.setMemberCode(memberCode);
		}
		if (merchantCode != null) {
			oenCriteria.setMerchantCode(merchantCode);
		}
		if (!StringUtil.isEmpty(merchantName)) {
			oenCriteria.setMerchantName(merchantName);
		}
		if (!StringUtil.isEmpty(loginName)) {
			oenCriteria.setLoginName(loginName);
		}
		if (!StringUtil.isEmpty(emailCompany)) {
			oenCriteria.setEmailCompany(emailCompany);
		}
		if (!StringUtil.isEmpty(emailNotify)) {
			oenCriteria.setEmailNotify(emailNotify);
		}
		if (openFlag != null) {
			oenCriteria.setOpenFlag(openFlag);
		}
		if (!StringUtil.isEmpty(startDate)) {
			oenCriteria.setStartDate(startDate);
		}
		if (!StringUtil.isEmpty(endDate)) {
			oenCriteria.setEndDate(endDate);
		}
		if(StringUtils.isNotEmpty(operator)){
			oenCriteria.setOperator(operator);
		}
		if(maxTradeOrderNo != null){
			oenCriteria.setMaxTradeOrderNo(maxTradeOrderNo);
		}*/
		String type = (String)paraMap.get("type");
		if("one".equalsIgnoreCase(type))
		{
			OrderEmailNotify orderEmailNotify = orderEmailNotifyService.findById(oenCriteria.getId());
			if (null != orderEmailNotify ) {
				resultMap.put("result", orderEmailNotify);
			}
		}
		else
		{
			List<OrderEmailNotify> orderEmailNotifyList = orderEmailNotifyService.queryOrderEmailNotify(oenCriteria, page);

			if (null != orderEmailNotifyList && !orderEmailNotifyList.isEmpty()) {
				resultMap.put("result", orderEmailNotifyList);
			}
			resultMap.put("page", page);
		}

		resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());

		return JSonUtil.toJSonString(resultMap);	
	}
}
