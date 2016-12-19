/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.RepairOrderDTO;
import com.pay.txncore.service.OrderRepairService;
import com.pay.util.JSonUtil;

/**
 * 逆向补单
 * 
 * @author chma
 */
public class ReverseRepairOrderHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(getClass());
	private OrderRepairService orderRepairService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map<String,Object> resultMap = new HashMap<String,Object>();

		paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
		
		List<Map> listMap = (List<Map>) paraMap.get("list");
		String operator = (String) paraMap.get("operator");
	
		List<String> resultList=null;
		
		if(listMap!=null&&!listMap.isEmpty()){
			resultList = new ArrayList<String>();
			for(Map<String,String> map:listMap){
				String channelOrderNo = map.get("channelOrderNo");
				String paymentOrderNo = map.get("paymentOrderNo");
				String authCode = map.get("authCode");
				String refNo = map.get("refNo");
				String tradeOrderNo = map.get("tradeOrderNo");
				String memberCode = map.get("memberCode");
				
				RepairOrderDTO orderDTO = new RepairOrderDTO();
				
				orderDTO.setAuthCode(authCode);
				orderDTO.setChannelOrderNo(channelOrderNo);
				orderDTO.setPaymentOrderNo(paymentOrderNo);
				orderDTO.setRefNo(refNo);
				orderDTO.setTradeOrderNo(tradeOrderNo);
				orderDTO.setOperator(operator);
				orderDTO.setMemberCode(memberCode);

			    orderRepairService.repairOrder(orderDTO);
			}
		}
		
		resultMap.put("resultList",resultList);
		resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		return JSonUtil.toJSonString(resultMap);
	}

	
	public void setOrderRepairService(OrderRepairService orderRepairService) {
		this.orderRepairService = orderRepairService;
	}
	
	
	

}
