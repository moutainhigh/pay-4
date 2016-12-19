/**
 * modify history: 
 * 2016-05-17 sch : 增加了退款订单的扩充信息 
 * 2016-08-05 sch : 退款订单临时增加了一个判断，如果保证金已经归还的话，则修改一下保证金的金额 
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

import javax.validation.constraints.Null;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fi.dto.PartnerSettlementOrder;
import com.pay.fi.dto.RefundOrderExtendDTO;
import com.pay.fi.reconcile.PartnerFixFeeDto;
import com.pay.fi.reconcile.PartnerReconcileDto;
import com.pay.fi.reconcile.PartnerRefundDto;
import com.pay.fi.reconcile.PartnerRefundFeeDto;
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
public class TxncoreClientService {

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
		
		if(partnerSettlementOrders == null || partnerSettlementOrders.isEmpty()){
			return null;
		}
		
		PartnerSettlementOrder partnerSettlementOrder = MapUtil.map2Object(PartnerSettlementOrder.class, (Map)partnerSettlementOrders.get(0));
		return partnerSettlementOrders==null||partnerSettlementOrders.isEmpty()?null:partnerSettlementOrder;
	}

	// 订单管理核心,获取退款订单的扩充信息
	// 问题：如果是异常引起的 RefundOrderExtendDTO = null,调用者怎么处理？
	public RefundOrderExtendDTO getRefundOrderExtend(String refundOrderNo) {

		//logger.info("getRefundOrderExtend begin");
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("refundOrderNo", refundOrderNo);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBSITE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_REFUND_FEE_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.WEBSITE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		List<RefundOrderExtendDTO> refundOrderExtendDTOs = (List<RefundOrderExtendDTO>) resultMap
				.get("result");
		if (refundOrderExtendDTOs == null || refundOrderExtendDTOs.isEmpty()) {
			//logger.info("getRefundOrderExtend return NULL");
			return null;
		}

		RefundOrderExtendDTO refundOrderExtendDTO = MapUtil.map2Object(
				RefundOrderExtendDTO.class, (Map) refundOrderExtendDTOs.get(0));

		//logger.info("getRefundOrderExtend end");
		return refundOrderExtendDTO;
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
		
		List refundFeeMapList = (List) resultMap.get("refundFeeList");
		List<PartnerRefundFeeDto> refundFeeList = MapUtil.map2List(
				PartnerRefundFeeDto.class, refundFeeMapList);
		
		//Mack add for 固定手续费手续
		List fixFeeMapList = (List) resultMap.get("fixFeeList");
		List<PartnerFixFeeDto> fixFeeList = MapUtil.map2List(
				PartnerFixFeeDto.class, fixFeeMapList);
		
		
		
		List<PartnerReconcileDto> reconcileData = generateReconcileData(settlementList, riskList, refundList,refundFeeList,fixFeeList);
		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
		}

		return reconcileData;
	}

	private List<PartnerReconcileDto> generateReconcileData(
			List<PartnerSettlementDto> settlementList,
			List<PartnerRiskDto> riskList, List<PartnerRefundDto> refundList,List<PartnerRefundFeeDto> refundFeeList,
			List<PartnerFixFeeDto> fixFeeList ) {
		DecimalFormat df = new DecimalFormat("0.000");
		df.setRoundingMode(RoundingMode.DOWN);
		//对账单的三个sheet合并成一个了，要对数据进行整合处理
		List<PartnerReconcileDto> reconcileData = new ArrayList<PartnerReconcileDto>();
	
		/***
		 *新增退款手续费对账单
		 *delin.dong
		 *2016年5月23日20:35:40 
		 */
		if(refundFeeList!=null){
			for (PartnerRefundFeeDto partnerRefundFee : refundFeeList) {
				PartnerReconcileDto partnerReconcileDto = new PartnerReconcileDto();
				partnerReconcileDto.setTradeOrderNo(partnerRefundFee.getTradeOrderNo());
				partnerReconcileDto.setOrderId(partnerRefundFee.getOrderId());
				partnerReconcileDto.setTransType("退款手续费");
				partnerReconcileDto.setTransCurrencyCode(partnerRefundFee.getFeeCurrencyCode());
				partnerReconcileDto.setTransAmount(partnerRefundFee.getBaseAmount());
				partnerReconcileDto.setRate(partnerRefundFee.getSettlerate());
				
				partnerReconcileDto.setFee("-");
				partnerReconcileDto.setRefundFee(partnerRefundFee.getPerFee());
				partnerReconcileDto.setAssureAmount("-");
				partnerReconcileDto.setCurrencyCode(partnerRefundFee.getSettlementCurrency());
				partnerReconcileDto.setAmount(partnerRefundFee.getSettlementAmount());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String settlementDate = partnerRefundFee.getSettlementDate();
				if(settlementDate !=null){
					try {
						partnerReconcileDto.setReconcileDate(sdf.format(sdf.parse(settlementDate)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				partnerReconcileDto.setFixedFee("-");
				partnerReconcileDto.setSettlementAmount(partnerRefundFee.getSettlementAmount());
				reconcileData.add(partnerReconcileDto);
			}
		}
		
		//处理收单清算数据
		if(settlementList!=null){
			for(PartnerSettlementDto psd : settlementList){
				PartnerReconcileDto partnerReconcileDto = new PartnerReconcileDto();
				partnerReconcileDto.setTradeOrderNo(psd.getTradeOrderNo());
				partnerReconcileDto.setOrderId(psd.getOrderId());
				partnerReconcileDto.setTransType(psd.getDealType());
				partnerReconcileDto.setTransCurrencyCode(psd.getCurrencyCode());
				partnerReconcileDto.setTransAmount(psd.getOrderAmount());
				partnerReconcileDto.setRate(psd.getSettlementRate());
				String fee = "0.000" ;
				if(null != psd.getFee()){
					partnerReconcileDto.setFee("0.000".equals(psd.getFee())?"0.000":"-"+psd.getFee());
				}else{
					partnerReconcileDto.setFee(fee);
				}
				String charge = "0.000" ;
				if(null != psd.getCharge()){
					partnerReconcileDto.setCharge("0.000".equals(psd.getCharge())?"0.000":"-"+psd.getCharge());
				}else{
					partnerReconcileDto.setCharge(charge);
				}
				
				String fixedFee = "0.000" ;
				if(null != psd.getFixedFeeSettlementAmount()){
					fixedFee = psd.getFixedFeeSettlementAmount().startsWith(".")?"0"+psd.getFixedFeeSettlementAmount():psd.getFixedFeeSettlementAmount();
				}
				
				partnerReconcileDto.setFixedFee("0.000".equals(fixedFee)?"0.000":"-"+fixedFee);
				//partnerReconcileDto.setRefundFee("-");
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
				
				//modify by sch 2016-05-17 把一大段代码 挪到下面的函数中去了  
				//if(getPartnerReconcileData_Refund_old(refund,df,partnerReconcileDto)){  //如果切回老版本，就用这句 
				if(getPartnerReconcileData_Refund(refund,df,partnerReconcileDto)){
					reconcileData.add(partnerReconcileDto);
				}
				else{
					logger.info("get refund info failed");
				}		
			}
		}
		
		// add by mack on 2016年7月6日 针对206/518的记账
		if(fixFeeList!=null){
			for (PartnerFixFeeDto partnerFixFee : fixFeeList) {
			 
				PartnerReconcileDto partnerReconcileDto = new PartnerReconcileDto();
				partnerReconcileDto.setTradeOrderNo(partnerFixFee.getTradeOrderNo());
				partnerReconcileDto.setOrderId(partnerFixFee.getOrderId());
				partnerReconcileDto.setTransType(partnerFixFee.getTransType());
				partnerReconcileDto.setTransCurrencyCode(partnerFixFee.getFixCurrencyCode());
				partnerReconcileDto.setTransAmount( partnerFixFee.getFixFee());
				partnerReconcileDto.setRate(partnerFixFee.getFixRate());
				partnerReconcileDto.setFee("-");
				partnerReconcileDto.setFixedFee("-");
				partnerReconcileDto.setRefundFee("-");
				partnerReconcileDto.setAssureAmount("-");
				partnerReconcileDto.setCurrencyCode(partnerFixFee.getSettlementCurrencyCode());
				//风控手续费入账金额
				partnerReconcileDto.setAmount( partnerFixFee.getAmount() );
				partnerReconcileDto.setReconcileDate(partnerFixFee.getSettlementDate());
				partnerReconcileDto.setSettlementAmount( partnerFixFee.getAmount());
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
	
	/*
	 * 获取退款订单的信息，老代码:根据清算订单的值来设置  退款中手续费和固定手续费 ，这个函数是2016-05-17 之前的代码  
	 * 输出参数： partnerReconcileDto
	 */
	private boolean getPartnerReconcileData_Refund_old(PartnerRefundDto refund, DecimalFormat df, PartnerReconcileDto partnerReconcileDto){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PartnerSettlementOrder partnerSettlementOrder = getPartnerSettlementOrder(refund.getTradeOrderNo());
		if(partnerSettlementOrder==null){
			return false;
		}
		
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
		return true;
	}
	
	/*
	 * 获取退款订单的信息 。 如果存在退款扩充信息，就使用退款扩充信息里的值，否则就调用老代码，使用清算订单里的值  
	 * 输出参数：partnerReconcileDto 
	 */
	private boolean getPartnerReconcileData_Refund(PartnerRefundDto refund, DecimalFormat df, PartnerReconcileDto partnerReconcileDto){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		RefundOrderExtendDTO refundOrderExtend = getRefundOrderExtend(refund.getRefundOrderNo());
			
		if (refundOrderExtend == null) {
			//logger.info("old refundOrder begin");
			return getPartnerReconcileData_Refund_old(refund,df,partnerReconcileDto);
		}
		
		else{
			
			//logger.info("new refundOrder begin");
			
			//新的代码 
			String fixedFee = "0.000";
			String perFee= "0.000";
			
			//计算固定手续费
			long feeFlg = refundOrderExtend.getFeeFlg().longValue();
			/*
			final int FEE_FLG_UNSTTLEMENT    	 		 = 0;		//未清算订单，不还手续费
			final int FEE_FLG_RETURN_PERPART			 = 1;       //百分比手续费要还
			final int FEE_FLG_RETURN_FIXED				 = 2;		//固定手续费要还
			final int FEE_FLG_RETURN_PERPART_FIXED  	 = 3;		//所有的手续费，都按照比例还给用户
			*/
			
			//这里，我们使用  清算金额 来作为退款手续费的计算比例
			//note: 这里改的这么别扭，应该直接把两个计算好的值 存入 refundOrderExtend ，以方便查询
			double refundPercentage = 1.0;
			
			{
				Double settleAmount_d1000 = Double.valueOf(refundOrderExtend.getSettlementAmount());
				Double settleAmount = Double.valueOf( settleAmount_d1000/1000);  //这是清算金额(可能先发生过清算前退款)
				Double orderAmount =  Double.valueOf(refund.getOrderAmount());	 //这是订单金额  88.88 单位为元  
				Double refundAmount = Double.valueOf(refund.getRefundAmount());  //单位为元 
				
				//logger.info("settleAmount = " + settleAmount + " exten.Settleamount= " + refundOrderExtend.getSettlementAmount()); 
				if ((settleAmount>= refundAmount) && (settleAmount>0)){
					
					refundPercentage = Double.valueOf(refund
							.getRefundAmount())
							/ Double.valueOf(settleAmount);
				}
				else {
					//这是原来的代码 
					refundPercentage = Double.valueOf(refund
							.getRefundAmount())
							/ Double.valueOf(refund.getOrderAmount());
				}
			}	
			

			
			if ((feeFlg & 2)==2) {	
				fixedFee = df.format(new BigDecimal(
						refundOrderExtend.getFixedFeeAmount())
						.multiply(new BigDecimal(refundPercentage))
						.divide(new BigDecimal("1000")));
			}
			
			if((feeFlg & 1)==1) {
				perFee = df.format(new BigDecimal(
						refundOrderExtend.getPerFeeAmount())
						.multiply(new BigDecimal(refundPercentage))
						.divide(new BigDecimal("10000")));		//注意,这里是1万
			}
			
			partnerReconcileDto.setRefundFee("0.000");		//这个值是0.0  ，如果要改的话，下面的代码，需要改掉 
			
			/*
			final int REFUND_FLG_UNSTTLEMENT_ALL   =1;		//未清算全额退款 
			final int REFUND_FLG_UNSTTLEMENT_PART  =2;		//未清算部分退款
			final int REFUND_FLG_SETTLEMENTED_ALL  =3;		//已清算全部退款  
			final int REFUND_FLG_SETTLEMENTED_PART =4;		//已清算部分退款
			*/
			long refundFlg = refundOrderExtend.getRefundFlg().longValue();
			
			if (refundFlg <=2) {
				// 未清算的订单
				partnerReconcileDto.setTransType("未清算退款");
				partnerReconcileDto.setRate("-");
				partnerReconcileDto.setSettlementAmount("-");
				partnerReconcileDto.setFixedFee("-");
				partnerReconcileDto.setFee("-");// 交易手续费
				partnerReconcileDto.setAssureAmount("-");
				
				// 入账金额,这个值的取值 逻辑很奇怪
				partnerReconcileDto 
						.setAmount(df.format(new BigDecimal(
								fixedFee).add(
								new BigDecimal(partnerReconcileDto
										.getRefundFee())).negate()));	
				
			} else {
				partnerReconcileDto.setTransType("已清算退款");
				partnerReconcileDto.setRate(refund
						.getRefundSettlementRate());
				partnerReconcileDto.setSettlementAmount("-"
						+ refund.getRefundSettlementAmount());
				
				partnerReconcileDto.setFixedFee(fixedFee);
				partnerReconcileDto.setFee(perFee);
				
				/* modify by sch 2016-08-05
				partnerReconcileDto.setAssureAmount("0.000"
						.equals(refund.getAssureAmount()) ? "0.000"
						: "-" + refund.getAssureAmount());
					
				String amount = df.format(new BigDecimal(refund
						.getRefundSettlementAmount())
						.subtract(new BigDecimal(perFee))
						.subtract(new BigDecimal(fixedFee))
						.subtract(new BigDecimal(refund.getAssureAmount())));
				*/
				
				String assureAmount = refund.getAssureAmount();
				Long assureAcctDelta = refundOrderExtend.getAssureAcctDelta();
				if(StringUtil.isEmpty(assureAmount) || assureAcctDelta == 0){
					assureAmount = "0.000";
				}
					
				partnerReconcileDto.setAssureAmount("0.000"
						.equals(assureAmount) ? "0.000"
						: "-" + assureAmount);
					
				String amount = df.format(new BigDecimal(refund
						.getRefundSettlementAmount())
						.subtract(new BigDecimal(perFee))
						.subtract(new BigDecimal(fixedFee))
						.subtract(new BigDecimal(assureAmount)));
					
				partnerReconcileDto.setAmount("0.000"
						.equals(amount) ? "0.000" : "-" + amount);
			}
			
			try{
			Date refundDate = new SimpleDateFormat("yyyy-MM-dd")
					.parse(refund.getComplateDate());
			partnerReconcileDto
					.setReconcileDate(new SimpleDateFormat(
							"yyyy-MM-dd").format(refundDate));
			}
			catch (ParseException e) {
					logger.error("日期处理失败", e);
			}
			
			//logger.info("new refundOrder end");
			
			partnerReconcileDto.setCurrencyCode(refundOrderExtend.getSettlementCurrency());		
	
			partnerReconcileDto.setTradeOrderNo(refund.getTradeOrderNo());
			partnerReconcileDto.setOrderId(refund.getOrderId());
			partnerReconcileDto.setTransCurrencyCode(refund.getCurrencyCode());
			partnerReconcileDto.setTransAmount("-"+refund.getRefundAmount());
		}
		return true;
	}
	

 	/**
	 * 获取商户配置域名
	 * 
	 * @param partnerId
	 * @param siteId
	 * @return
	 */
	public List<Map> crosspayMerchantWebsiteQuery(String partnerId,
			String siteId, String status) {

		Map<String, String> paraMap = new HashMap();
		paraMap.put("partnerId", partnerId);
		paraMap.put("url", siteId);
		paraMap.put("statusIn", status);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_MERCHANT_WEBSITE_QUERY.getCode(),
				sysTraceNo, SystemCodeEnum.WEBGATE.getCode(),
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

		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
			logger.info("returnMap:" + returnMap);
		}

		return returnMap;
	}
	
	/***
	 * 查询拒付订单表
	 * @param paraMap
	 * @return
	 */
	
	public Map queryBouncedOrder(Map paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBSITE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CHARGE_BACK_ORDER_QUERY.getCode(), sysTraceNo,
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
		List<Map> returnMap = (List<Map>) resultMap.get("result");

		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
			logger.info("returnMap:" + returnMap);
		}
		return resultMap;
	}

	public Map bacthNoAppeal(List<Map> list,String flag) {
		Map paraMap=new HashMap();
		paraMap.put("chargeBackOrders", list);
		if(null!=flag)
		{
		paraMap.put("flag", flag);
		}
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBSITE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CHARGEBACK_ORDER_UPDATE.getCode(), sysTraceNo,
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
		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
		}
		return resultMap;
	}
	
	/**
	 * 拒付订单批量更新
	 * @param list
	 * @return
	 */
	public Map chargeBackOrderBatchUpdate(List<Map> list) {
		Map paraMap=new HashMap();
		paraMap.put("chargeBackOrders", list);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBSITE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CHARGEBACK_ORDER_UPDATE.getCode(), sysTraceNo,
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
		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
		}
		return resultMap;
	}

	public Map bouncedFlowAddHandler(List<Map> bouncedFlow) {
		Map paraMap=new HashMap();
		paraMap.put("bouncedFlowVOs", bouncedFlow);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBSITE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_BOUNCED_FLOW_ORDER_ADD.getCode(), sysTraceNo,
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
		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
		}
		return resultMap;
	}
	
	
	
	/**
	 * 清算汇率查询
	 * @return
	 */
	public Map getSettlementRate(String currency, String targetCurrency,
			String type, String memberCode, String status) {

		Map<String, String> paraMap = new HashMap();
		paraMap.put("memberCode",memberCode);
		paraMap.put("currency", currency);
		paraMap.put("type",type);
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
	
	/**
	 * 拒付罚款查询
	 * @param requetParam
	 * @return
	 */
	public Map bouncedFineQuery(Map<String, Object> requetParam) {
		String reqMsg = JSonUtil.toJSonString(requetParam);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.GATEWAY.getCode());
		String result = invokeService.invoke(
				"ordercenter.bouncedfine.query", sysTraceNo,
				SystemCodeEnum.GATEWAY.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		
		return resultMap;
	}
	
	
}
