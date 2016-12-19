/**
 * modify by sch 2016-06-02：  fi-accounting 返回了两个数组，一个是交易，一个是退款
 * 这里没有引入 ClearingRefundOrder 这个数据结构，RefundOrder用的地方比较多，不建议在这里改。
 * 定义了一个  ReconcileRefundOrder , 用来做对账部分退款订单的 交互 
 */
package com.pay.poss.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.enums.SerCode;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.poss.dto.ChannelOrderDTO;
import com.pay.poss.dto.ReconcileRefundOrder;
import com.pay.poss.dto.ReconciliationDto;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 渠道对账服务
 * 
 * @author chaoyue
 * 清结算核心
 */
public class ReconciliationService {

	private HessianInvokeService invokeService;

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}

	public List<ChannelOrderDTO> reconciliation(String startDate,
			String endDate, List<ReconciliationDto> list, String operator,String name,String orgCode,
			List<String> settlmentCurrencyCodes) {

		Map paraMap = new HashMap();
		paraMap.put("reconciliationDtos", list);
		paraMap.put("startDate", startDate);
		paraMap.put("endDate", endDate);
		paraMap.put("operator", operator);
		paraMap.put("fileName", name);
		paraMap.put("orgCode", orgCode);
		paraMap.put("settlementCurrencyCodes", settlmentCurrencyCodes);	//币种
		          
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_RECONCILIATION.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		List<Map> listMap = (List<Map>) resultMap.get("listMap");
		//System.out.println("channelOrders " + listMap);
		
		List<ChannelOrderDTO> channelOrders = MapUtil.map2List(
				ChannelOrderDTO.class, listMap);
		
		if(channelOrders==null) {
			channelOrders = new ArrayList<ChannelOrderDTO>();
		}
		//add by sch 2016-06-02
		List<Map> listMap2 = (List<Map>) resultMap.get("listMap2");

		if(listMap2!=null){
			List<ReconcileRefundOrder> refundOrders = MapUtil.map2List(ReconcileRefundOrder.class, listMap2);
			
			getChannelOrderDTOFromList2(refundOrders,channelOrders);				
			//end sch 2016-06-02
		}
		
		return channelOrders;
	}

	/*
	 * 对账，新方法
	 * commnad: "pre" 表示预处理  “process" 表示正式的处理
	 */
	public Map<String, String> reconciliation_new(String startDate,
			String endDate, List<ReconciliationDto> list, String operator,String name,
			String command, String orgCode,List<String> settlmentCurrencyCodes) {

		Map paraMap = new HashMap();
		paraMap.put("reconciliationDtos", list);
		paraMap.put("startDate", startDate);
		paraMap.put("endDate", endDate);
		paraMap.put("operator", operator);
		paraMap.put("fileName", name);
		paraMap.put("method", "new");		//新方法
		paraMap.put("command", command);	
		paraMap.put("orgCode", orgCode);
		paraMap.put("settlementCurrencyCodes", settlmentCurrencyCodes);	//币种
		
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_RECONCILIATION.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		
		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		return resultMap;
		
	}
	
	private void getChannelOrderDTOFromList2(List<ReconcileRefundOrder>  refundOrders, List<ChannelOrderDTO> channelOrders){
		if(refundOrders==null){
			return;
		}
		
		for(ReconcileRefundOrder refundOrder: refundOrders)
		{
			ChannelOrderDTO channelOrder = new ChannelOrderDTO();
			
			if(refundOrder.getRefundOrderNo() == null){
				//System.out.println("get refundOrder no null");
				continue;
			}
			
			channelOrder.setChannelOrderNo(refundOrder.getRefundOrderNo());
			
			channelOrder.setBacthNo(refundOrder.getBacthNo());
			channelOrder.setReconciliationFlg(refundOrder.getReconciliationFlg());	 
			channelOrder.setErrorMsg(refundOrder.getErrorMsg());
			
			channelOrder.setOperator(refundOrder.getOperator());
			channelOrder.setBacthCreateDate(refundOrder.getBacthCreateDate());
							
			channelOrders.add(channelOrder);
		}
	}
}
