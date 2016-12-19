/**
 * 
 */
package com.pay.poss.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.enums.SerCode;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.poss.controller.fi.dto.BouncedFlowVO;
import com.pay.poss.controller.fi.dto.BouncedOrderVO;
import com.pay.poss.controller.fi.dto.ChargeBackOrder;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * @author chaoyue
 *
 */
//清结算核心
public class ChargeBackService {

	private HessianInvokeService orderInvokeService;
	
	private HessianInvokeService settleInvokeService;

	private final Log logger = LogFactory.getLog(getClass());
	
	public void setOrderInvokeService(HessianInvokeService orderInvokeService) {
		this.orderInvokeService = orderInvokeService;
	}

	public void setSettleInvokeService(HessianInvokeService settleInvokeService) {
		this.settleInvokeService = settleInvokeService;
	}

	/**
	 * 查询拒付配置
	 * 
	 * @param paraMap
	 * @return
	 */
	public Map queryChargeBackConfig(String memberCode) {

		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("memberCode", memberCode);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = settleInvokeService.invoke(
				SerCode.TXNCORE_CHARGEBACK_CONFIG_QUERY.getCode(), sysTraceNo,
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
	 * 添加拒付配置
	 * 
	 * @param addMap
	 * @return
	 */
	public Map addChargeBackConfig(Map<String, String> addMap) {

		String reqMsg = JSonUtil.toJSonString(addMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = settleInvokeService.invoke(
				SerCode.TXNCORE_CHARGEBACK_CONFIG_ADD.getCode(), sysTraceNo,
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
	 * 更新拒付配置
	 * 
	 * @param addMap
	 * @return
	 */
	public Map updateChargeBackConfig(Map<String, String> addMap) {

		String reqMsg = JSonUtil.toJSonString(addMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = settleInvokeService.invoke(
				SerCode.TXNCORE_CHARGEBACK_CONFIG_UPDATE.getCode(), sysTraceNo,
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
	 * 拒付订单查询
	 * 
	 * @param chargeBackCondition
	 * @return
	 */
	public Map queryChargeBackOrder(ChargeBackOrder chargeBackCondition,
			Page page) {
		Map paraMap = MapUtil.bean2map(chargeBackCondition);

		if (null != page) {
			paraMap.put("page", page);
		}

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = orderInvokeService.invoke(
				SerCode.TXNCORE_CHARGEBACK_ORDER_QUERY.getCode(), sysTraceNo,
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
	 * 拒付订单查询
	 * 
	 * @param chargeBackCondition
	 * @return
	 */
	public Map queryBouncedOrder(BouncedOrderVO bouncedOrderVO,
			Page page) {
		Map paraMap = MapUtil.bean2map(bouncedOrderVO);
		
		if (null != page) {
			paraMap.put("page", page);
		}
		
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = orderInvokeService.invoke(
				SerCode.TXNCORE_CHARGE_BACK_ORDER_QUERY.getCode(), sysTraceNo,
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
	 * 拒付订单查询
	 * 
	 * @param chargeBackCondition
	 * @return
	 */
	public Map queryChargeBackOrderNew(BouncedOrderVO chargeBackCondition,
			Page page) {
		Map paraMap = MapUtil.bean2map(chargeBackCondition);
		
		if (null != page) {
			paraMap.put("page", page);
		}
		
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = orderInvokeService.invoke(
				SerCode.TXNCORE_CHARGEBACK_ORDER_QUERY.getCode(), sysTraceNo,
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
	 * 拒付订单添加
	 * 
	 * @param chargeBackOrder
	 * @return
	 */
	public Map addChargeBackOrder(List<ChargeBackOrder> chargeBackOrders) {
		Map paraMap = new HashMap();
		paraMap.put("chargeBackOrders", chargeBackOrders);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = orderInvokeService.invoke(
				SerCode.TXNCORE_CHARGEBACK_ORDER_ADD.getCode(), sysTraceNo,
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
	 * 拒付订单添加
	 * 
	 * @param chargeBackOrder
	 * @return
	 */
	public Map addChargeBackOrderNew(List<BouncedOrderVO> chargeBackOrders) {
		Map paraMap = new HashMap();
		paraMap.put("chargeBackOrders", chargeBackOrders);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = orderInvokeService.invoke(
				SerCode.TXNCORE_BOUNCED_REGISTER_SAVE.getCode(), sysTraceNo,
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

	public Map importChargeBackOrder(List<ChargeBackOrder> chargeBackOrders) {
		Map paraMap = new HashMap();
		paraMap.put("chargeBackOrders", chargeBackOrders);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = orderInvokeService.invoke(
				SerCode.TXNCORE_CHARGE_BACK_IMPORT.getCode(), sysTraceNo,
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
	 * 更新拒付订单
	 * @param chargeBackOrders
	 * @return
	 */
	public Map updateChargeBackOrder(List<ChargeBackOrder> chargeBackOrders,String flag) {
		Map paraMap = new HashMap();
		paraMap.put("chargeBackOrders", chargeBackOrders);
		if(null!=flag)
		{
		paraMap.put("flag", flag);
		}
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = orderInvokeService.invoke(
				SerCode.TXNCORE_CHARGEBACK_ORDER_UPDATE.getCode(), sysTraceNo,
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
	public Map updateBouncedFlow(List<BouncedFlowVO> bouncedFlowVOs) {
		Map paraMap = new HashMap();
		paraMap.put("bouncedFlowVOs", bouncedFlowVOs);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = orderInvokeService.invoke(
				SerCode.TXNCORE_BOUNCED_FLOW_ORDER_ADD.getCode(), sysTraceNo,
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
	public Map queryBouncedFlow(BouncedFlowVO bouncedFlowVO) {
		Map paraMap = MapUtil.bean2map(bouncedFlowVO);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = orderInvokeService.invoke(
				SerCode.TXNCORE_BOUNCED_FLOW_ORDER_QUERY.getCode(), sysTraceNo,
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
	 * 统计某个status的数量
	 * @param params
	 * @return
	 */
	public Map countChargeBackOrderByStatus(Map params) {
		String reqMsg = JSonUtil.toJSonString(params);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = orderInvokeService.invoke(
				SerCode.TXNCORE_COUNT_CHARGEBACK_STATUS.getCode(), sysTraceNo,
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
		logger.info("responseCode="+responseCode+",responseDesc="+responseDesc);
		return resultMap;
	}

}
