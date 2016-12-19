/**
 * 
 */
package com.pay.poss.client;

import java.util.HashMap;
import java.util.Map;

import com.pay.inf.enums.SerCode;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.util.JSonUtil;

/**
 * @author chaoyue
 *
 */
//除了 orderReSettlement，orderReAssureSettlement 都是调用订单管理核心
public class GatewayOrderQueryService {

	private HessianInvokeService invokeService;

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}

	/**
	 * 查询网关订单
	 * 
	 * @param paraMap
	 * @return
	 */
	public Map queryTradeOrder(Map<String, String> paraMap) {

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_TRADEORDER_QUERY.getCode(), sysTraceNo,
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

	/**
	 * 查询支付订单
	 * 
	 * @param paraMap
	 * @return
	 */
	public Map queryPaymentOrder(Map<String, String> paraMap) {

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_PAYMENTORDER_QUERY.getCode(), sysTraceNo,
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

	/**
	 * 查询渠道订单
	 * 
	 * @param paraMap
	 * @return
	 */
	public Map queryChannelOrder(Map<String, String> paraMap) {

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CHANNEL_ORDER_QUERY.getCode(), sysTraceNo,
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

	/**
	 * 查询清算订单
	 * 
	 * @param paraMap
	 * @return
	 */
	public Map queryPartnerSettlementOrder(Map<String, String> paraMap) {

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_PARTNER_SETTLEMENT_ORDER_QUERY.getCode(),
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
	 * 清算订单
	 * 
	 * @param paraMap
	 * @return
	 */
	//调用清结算核心
	public Map orderReSettlement(Map<String, String> paraMap) {

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_LIQUIDATION.getCode(),
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
	 * 清算保证金
	 * 
	 * @param paraMap
	 * @return
	 */
	//调用清结算核心
	public Map orderReAssureSettlement(Map<String, String> paraMap) {

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_LIQUIDATIONASSURE.getCode(),
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
	

	public Map queryRefundOrder(Map<String, String> paraMap) {

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_REFUND_QUERY.getCode(), sysTraceNo,
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

	/***
	 * 网关订单详情
	 * @param paraMap
	 * @return
	 */
	//订单管理核心
	public Map queryTradeOrderDetails(Map<String, String> paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_TRADE_ORDER_DETAIL_QUERY.getCode(), sysTraceNo,
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
    //订单管理核心
	public Map queryAutoTradeOrder(Map paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_AUTO_QUERY_TRADE_ORDER.getCode(), sysTraceNo,
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
	
	/**
	 * 查询tradeorder的交易金额总计
	 * @author delin.dong
	 * @param paraMap
	 * @return
	 */
	public Map queryTradeOrderSumAmount(Map paraMap) {

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCOR_TRADE_ORDER_SUM_AMOUNT.getCode(), sysTraceNo,
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
	/**
	 * 查询下载网关订单数据
	 * @param paraMap
	 * @return
	 */
	public Map downloadTradeOrder(Map paraMap) {
		
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_TRADEORDER_DOWNLOAD.getCode(), sysTraceNo,
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


// 2016-05-25
	// 查询Migs 的退款订单
	// 设计思路：有两种 分别用参数  findMethod 来表达， 1表示 方法1， 2 表示方法2
	// 方法1： 根据对账单中一条一条信息去查找，可以根据CompleteTime/金额  等去匹配。  
	// 方法2： 给出一批数据 数据，使用ComplteTime/金额 等去匹配， 返回结果也是一批数据。   如果中间有一条，没有找到，则缺失这一条即可
	/*
	 * 
	 * MOCK("00000000", "MOCK"), 
	BOCS("10076001", "中银卡司"),
	BOCM("10079001", "中银MOTO"),
	BOCI("10080001", "中银MIGS"),
	CYBSCTV("10078001", "农行CTV"),
	BOC("10003001", "中国银行"),
	ABC("10002001", "农业银行"),
	CREDORAX("10075001","Credorax"),
	ADYEN("10077001","Adyen"),
	BELTO("10077002","Belto"),
	CASHU("10077003","Cashu"),
	 * 
	 */
	public Map queryMigsRefundOrderByRefernce(Map paraMap) {
		
		    //logger.info("RefundOrderByReference " + paraMap);
		    
			String reqMsg = JSonUtil.toJSonString(paraMap);
			HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
			String sysTraceNo = SysTraceNoService
					.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
			String result = invokeService.invoke(
					SerCode.TXNCORE_REFUNDORDER_QUERY_BYREFNO.getCode(), sysTraceNo,
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

	
	/***
	 * 获取指定时间以后，最大的网关订单号
	 * @author Davis.guo at 2016-09-03
	 * @param paraMap
	 * @return
	 */
	public Map getMaxTradeOrderNo(Map<String, Object> paraMap) {
		
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_MAX_TRADE_ORDER_NO.getCode(), sysTraceNo,
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
	
	
	// 2016-05-25
	// 查询 CTV 的退款订单，暂时实现和Migs 是一样的  
	public Map queryCTVRefundOrderByRequestId(Map paraMap) {
		
	    //logger.info("RefundOrderByReference " + paraMap);
	    
		return queryMigsRefundOrderByRefernce(paraMap);
	}
}
