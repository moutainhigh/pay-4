/**
 * 
 */
package com.pay.app.client;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fi.dto.PartnerSettlementOrder;
import com.pay.fi.reconcile.PartnerReconcileDto;
import com.pay.fi.reconcile.PartnerRefundDto;
import com.pay.fi.reconcile.PartnerRiskDto;
import com.pay.fi.reconcile.PartnerSettlementDto;
import com.pay.inf.enums.SerCode;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 交易服务接口
 * 
 * @author chaoyue
 *
 */
public class OrderCoreClientService {

	private Log logger = LogFactory.getLog(getClass());
	private HessianInvokeService invokeService;

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
		
	}
	

	/**
	 * 订单管理核心
	 * @param partnerId
	 * @param publicKeyValue
	 * @return
	 */
	public boolean partnerConfigCreate(String partnerId, String publicKeyValue) {

		Map<String, String> paraMap = new HashMap();
		paraMap.put("partnerId", partnerId);
		paraMap.put("publicKeyValue", publicKeyValue);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBSITE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_PARTNERCONFIG_CREATE.getCode(), sysTraceNo,
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
		Boolean resultFlag = (Boolean) resultMap.get("result");

		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
			logger.info("returnFlag:" + resultFlag);
		}

		return resultFlag;
	}
	//清结算核心
	public Map getTransactionRate(String currency, String targetCurrency,
			String memberCode, String status) {

		Map<String, String> paraMap = new HashMap();
		paraMap.put("memberCode",memberCode);
		paraMap.put("currency", currency);
		paraMap.put("type","1");
		paraMap.put("targetCurrency", targetCurrency);
		paraMap.put("status",status);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.GATEWAY.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CURREBCR_RATE_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.GATEWAY.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		String hasRate = (String) resultMap.get("hasRate");
		
		if("0".equals(hasRate))
			return null;

		
		return resultMap;
	}
    
	//订单管理核心
	public Map partnerConfigQuery(String partnerId, String code) {

		Map<String, String> paraMap = new HashMap();
		paraMap.put("partnerId", partnerId);
		paraMap.put("code", code);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBSITE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_PARTNERCONFIG_QUERY.getCode(), sysTraceNo,
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
		Map<String, String> returnMap = (Map) resultMap.get("result");

		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
			logger.info("returnMap:" + returnMap);
		}

		return returnMap;
	}
	//订单管理核心
	public PartnerSettlementOrder getPartnerSettlementOrder(String tradeOrderNo){
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("tradeOrderNo", tradeOrderNo);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBSITE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_PARTNER_SETTLEMENT_ORDER_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.WEBSITE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		List<PartnerSettlementOrder> partnerSettlementOrders = (List<PartnerSettlementOrder>)resultMap.get("result");
		PartnerSettlementOrder partnerSettlementOrder = MapUtil.map2Object(PartnerSettlementOrder.class, (Map)partnerSettlementOrders.get(0));
		return partnerSettlementOrders==null||partnerSettlementOrders.isEmpty()?null:partnerSettlementOrder;
	}

	//
	public List<PartnerReconcileDto> getReconcileData(String partnerId, String monthTime,
			String dayTime) {
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("partnerId", partnerId);
		paraMap.put("monthTime", monthTime);
		paraMap.put("dayTime", dayTime);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBSITE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_PARNTER_RECONCILE.getCode(), sysTraceNo,
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

		List settlementMapList = (List) resultMap.get("settlementList");
		List<PartnerSettlementDto> settlementList = MapUtil.map2List(
				PartnerSettlementDto.class, settlementMapList);

		List riskMapList = (List) resultMap.get("riskList");
		List<PartnerRiskDto> riskList = MapUtil.map2List(PartnerRiskDto.class,
				riskMapList);

		List refundMapList = (List) resultMap.get("refundList");
		List<PartnerRefundDto> refundList = MapUtil.map2List(
				PartnerRefundDto.class, refundMapList);
		List<PartnerReconcileDto> reconcileData = generateReconcileData(settlementList, riskList, refundList);
		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
		}

		return reconcileData;
	}

	private List<PartnerReconcileDto> generateReconcileData(
			List<PartnerSettlementDto> settlementList,
			List<PartnerRiskDto> riskList, List<PartnerRefundDto> refundList) {
		DecimalFormat df = new DecimalFormat("0.000");
		df.setRoundingMode(RoundingMode.DOWN);
		//对账单的三个sheet合并成一个了，要对数据进行整合处理
		List<PartnerReconcileDto> reconcileData = new ArrayList<PartnerReconcileDto>();
		//处理收单清算数据
		if(settlementList!=null){
			for(PartnerSettlementDto psd : settlementList){
				PartnerReconcileDto partnerReconcileDto = new PartnerReconcileDto();
				partnerReconcileDto.setTradeOrderNo(psd.getTradeOrderNo());
				partnerReconcileDto.setOrderId(psd.getOrderId());
				partnerReconcileDto.setTransType("清算");
				partnerReconcileDto.setTransCurrencyCode(psd.getCurrencyCode());
				partnerReconcileDto.setTransAmount(psd.getOrderAmount());
				partnerReconcileDto.setRate(psd.getSettlementRate());
				String fee = "0.000" ;
				if(null != psd.getFee()){
					partnerReconcileDto.setFee("0.000".equals(psd.getFee())?"0.000":"-"+psd.getFee());
				}else{
					partnerReconcileDto.setFee(fee);
				}
				
				String fixedFee = "0.000" ;
				if(null != psd.getFixedFeeSettlementAmount()){
					fixedFee = psd.getFixedFeeSettlementAmount().startsWith(".")?"0"+psd.getFixedFeeSettlementAmount():psd.getFixedFeeSettlementAmount();
				}
				
				partnerReconcileDto.setFixedFee("0.000".equals(fixedFee)?"0.000":"-"+fixedFee);
				partnerReconcileDto.setRefundFee("-");
				partnerReconcileDto.setAssureAmount(psd.getAssureAmount());
				partnerReconcileDto.setCurrencyCode(psd.getSettlementCurrencyCode());
				partnerReconcileDto.setAmount(psd.getAmount());
				partnerReconcileDto.setReconcileDate(psd.getSettlementDate());
				partnerReconcileDto.setSettlementAmount(psd.getSettlementAmount());
				reconcileData.add(partnerReconcileDto);
			}
		}
		//处理风控手续费数据
		if(riskList!=null){
			for(PartnerRiskDto prd : riskList){
				PartnerReconcileDto partnerReconcileDto = new PartnerReconcileDto();
				partnerReconcileDto.setTradeOrderNo(prd.getTradeOrderNo());
				partnerReconcileDto.setOrderId(prd.getOrderId());
				partnerReconcileDto.setTransType("风控手续费");
				partnerReconcileDto.setTransCurrencyCode(prd.getRiskCurrencyCode());
				partnerReconcileDto.setTransAmount("-"+prd.getRiskFee());
				partnerReconcileDto.setRate(prd.getRiskRate());
				partnerReconcileDto.setFee("-");
				partnerReconcileDto.setFixedFee("-");
				partnerReconcileDto.setRefundFee("-");
				partnerReconcileDto.setAssureAmount("-");
				partnerReconcileDto.setCurrencyCode(prd.getSettlementCurrencyCode());
				//风控手续费入账金额
				String amount = df.format(new BigDecimal(prd.getRiskFee()).multiply(new BigDecimal(prd.getRiskRate())));
				partnerReconcileDto.setAmount("-"+amount);
				partnerReconcileDto.setReconcileDate(prd.getSettlementDate());
				partnerReconcileDto.setSettlementAmount("-"+amount);
				reconcileData.add(partnerReconcileDto);
			}
		}
		//处理退款清算数据
		if(refundList!=null){
			for(PartnerRefundDto refund : refundList){
				PartnerReconcileDto partnerReconcileDto = new PartnerReconcileDto();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				PartnerSettlementOrder partnerSettlementOrder = getPartnerSettlementOrder(refund.getTradeOrderNo());
				String fixedFee = "0.000";
				if(!StringUtil.isEmpty(partnerSettlementOrder.getFixedFeeSettlementAmount()) && !"0".equals(partnerSettlementOrder.getFixedFeeSettlementAmount())){
					//先确定退款比例
					double refundPercentage = Double.valueOf(refund.getRefundAmount())/Double.valueOf(refund.getOrderAmount());
					fixedFee = df.format(new BigDecimal(partnerSettlementOrder.getFixedFeeSettlementAmount()).multiply(new BigDecimal(refundPercentage)).divide(new BigDecimal("1000")));
				}
				partnerReconcileDto.setFixedFee(fixedFee);
				partnerReconcileDto.setRefundFee("0.000");
				try {
					//判断退款是否已清算
					if(sdf.parse(refund.getComplateDate()).before(partnerSettlementOrder.getSettlementDate())){
						//未清算的订单
						partnerReconcileDto.setTransType("未清算退款");
						partnerReconcileDto.setRate("-");
						partnerReconcileDto.setSettlementAmount("-");
						partnerReconcileDto.setFee("-");//交易手续费
						partnerReconcileDto.setAssureAmount("-");
						//入账金额
						partnerReconcileDto.setAmount(df.format(new BigDecimal(fixedFee).add(new BigDecimal(partnerReconcileDto.getRefundFee())).negate()));
					}else{
						partnerReconcileDto.setTransType("已清算退款");
						partnerReconcileDto.setRate(refund.getRefundSettlementRate());
						partnerReconcileDto.setSettlementAmount("-"+refund.getRefundSettlementAmount());
						partnerReconcileDto.setFee(refund.getRefundFee());
						partnerReconcileDto.setAssureAmount("0.000".equals(refund.getAssureAmount())?"0.000":"-"+refund.getAssureAmount());
						//计算入账金额
						String amount = df.format(new BigDecimal(refund.getRefundSettlementAmount()).subtract(new BigDecimal(refund.getRefundFee())).subtract(new BigDecimal(fixedFee)).subtract(new BigDecimal(refund.getAssureAmount())));
						partnerReconcileDto.setAmount("0.000".equals(amount)?"0.000":"-"+amount);
						
					}
					Date refundDate = new SimpleDateFormat("yyyy-MM-dd").parse(refund.getComplateDate());
					partnerReconcileDto.setReconcileDate(new SimpleDateFormat("yyyy-MM-dd").format(refundDate));
				} catch (ParseException e) {
					logger.error("日期处理失败", e);
				}
				partnerReconcileDto.setTradeOrderNo(refund.getTradeOrderNo());
				partnerReconcileDto.setOrderId(refund.getOrderId());
				partnerReconcileDto.setTransCurrencyCode(refund.getCurrencyCode());
				partnerReconcileDto.setTransAmount("-"+refund.getRefundAmount());
				partnerReconcileDto.setCurrencyCode(partnerSettlementOrder.getSettlementCurrencyCode());
				reconcileData.add(partnerReconcileDto);
			}
		}
		Comparator<PartnerReconcileDto> comparator = new Comparator<PartnerReconcileDto>() {
			@Override
			public int compare(PartnerReconcileDto o1, PartnerReconcileDto o2) {
				return o2.getTradeOrderNo().compareTo(o1.getTradeOrderNo());
			}
		};
		Collections.sort(reconcileData,comparator);
		return reconcileData;
	}
}
