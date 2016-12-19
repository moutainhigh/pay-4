/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fi.fill.model.FillRecordInfo;
import com.pay.fi.fill.model.OrderFillBatch;
import com.pay.fi.fill.service.OrderFillBatchService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.service.OrderRepairService;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 系统自动补单
 * 
 * @author chma
 */
public class OrderRepairHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(getClass());
    private OrderFillBatchService orderFillBatchService;
    private OrderRepairService orderRepairService;
    

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map<String,String> resultMap = new HashMap<String,String>();

		paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
		
		String reqBatchNoStr = (String) paraMap.get("reqBatchNo");
		
		if(StringUtil.isEmpty(reqBatchNoStr)){
			resultMap.put("responseCode", ResponseCodeEnum.INVALID_PARAMETER.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.INVALID_PARAMETER.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}
		
		long reqBatchNo = Long.valueOf(reqBatchNoStr);
		
		OrderFillBatch orderFillBatch = orderFillBatchService.getOrderFillBatchById(reqBatchNo);
		
		if(orderFillBatch==null){
			resultMap.put("responseCode","9999");
			resultMap.put("responseDesc", "未找到补单批次");
			return JSonUtil.toJSonString(resultMap);
		}
		
		//只有审核通过才可以补单
		if(orderFillBatch.getAuditStatus()==1){
		     List<FillRecordInfo> fillRecordInfos = orderRepairService.getOrderFillRecordInfoService()
		    		 .findOrderFillRecordByReqBatchNo(reqBatchNo);
		     if(fillRecordInfos!=null&&!fillRecordInfos.isEmpty()){
		    	 for(FillRecordInfo fri:fillRecordInfos){
		    		orderRepairService.repairOrder(fri);
		    	 }
		     }
		}
		
		resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		return JSonUtil.toJSonString(resultMap);
	}

	public void setOrderFillBatchService(OrderFillBatchService orderFillBatchService) {
		this.orderFillBatchService = orderFillBatchService;
	}

	public void setOrderRepairService(OrderRepairService orderRepairService) {
		this.orderRepairService = orderRepairService;
	}
}
