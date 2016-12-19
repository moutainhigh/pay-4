/*
 * modify history:
 * 2016-06-28:  启动渠道退款的时候，把状态更新一下，以防止重复启动 
 * 
 */
package com.pay.txncore.handler.refund;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.txncore.model.PreController;
import com.pay.txncore.service.PreControllerService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.client.ChannelClientService;
import com.pay.txncore.commons.RefundConfirmStatusEnum;
import com.pay.txncore.commons.RefundStatusEnum;
import com.pay.txncore.crosspay.service.CurrencyRateService;
import com.pay.txncore.dao.PaymentOrderExpandDAO;
import com.pay.txncore.dto.ChannelOrderDTO;
import com.pay.txncore.dto.ChannelPaymentResult;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.dto.refund.RefundOrderConfirmDTO;
import com.pay.txncore.dto.refund.RefundOrderDTO;
import com.pay.txncore.model.PaymentOrderExpand;
import com.pay.txncore.model.SettlementRate;
import com.pay.txncore.model.TradeAmountCount;
import com.pay.txncore.service.ChannelOrderService;
import com.pay.txncore.service.TradeAmountCountService;
import com.pay.txncore.service.TradeOrderService;
import com.pay.txncore.service.refund.RefundOrderConfirmService;
import com.pay.txncore.service.refund.RefundOrderService;
import com.pay.txncore.service.refund.RefundService;
import com.pay.util.DateUtil;
import com.pay.util.JSonUtil;

/**
 * 退款查询状态
 * 
 * @author LIBO
 */
public class QueryRefundStatusHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(getClass());
	private RefundService refundService;
	private ChannelClientService channelClientService;
	private RefundOrderService refundOrderService;
	private ChannelOrderService channelOrderService;
	private PaymentOrderExpandDAO paymentOrderExpandDAO;
	private TradeOrderService tradeOrderService;
	private TradeAmountCountService tradeAmountCountService;
	private CurrencyRateService currencyRateService;
	private RefundOrderConfirmService refundOrderConfirmService;

	private PreControllerService preControllerService;

	public void setPreControllerService(PreControllerService preControllerService) {
		this.preControllerService = preControllerService;
	}

	public void setCurrencyRateService(CurrencyRateService currencyRateService) {
		this.currencyRateService = currencyRateService;
	}

	public void setTradeAmountCountService(
			TradeAmountCountService tradeAmountCountService) {
		this.tradeAmountCountService = tradeAmountCountService;
	}

	public void setRefundService(RefundService refundService) {
		this.refundService = refundService;
	}

	public void setChannelClientService(
			ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}

	public void setPaymentOrderExpandDAO(
			PaymentOrderExpandDAO paymentOrderExpandDAO) {
		this.paymentOrderExpandDAO = paymentOrderExpandDAO;
	}

	public void setRefundOrderService(RefundOrderService refundOrderService) {
		this.refundOrderService = refundOrderService;
	}

	public void setChannelOrderService(ChannelOrderService channelOrderService) {
		this.channelOrderService = channelOrderService;
	}

	public void setRefundOrderConfirmService(
			RefundOrderConfirmService refundOrderConfirmService) {
		this.refundOrderConfirmService = refundOrderConfirmService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = null;
		Map resultMap = new HashMap();

		paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());

		if (logger.isInfoEnabled()) {
			logger.info("query refund para:" + paraMap);
		}

		String refundOrderNo = paraMap.get("refundOrderNo");
		
		if( !StringUtils.isEmpty(refundOrderNo)){ //QUERY POSS
			List<RefundOrderConfirmDTO> refundOrdConDtoList = 
					refundOrderConfirmService.findByRefundOrderNo(Long.parseLong(refundOrderNo));
			if(refundOrdConDtoList != null ){
				for(RefundOrderConfirmDTO rocDto: refundOrdConDtoList){
					if(String.valueOf(RefundConfirmStatusEnum.INIT.getCode()).equals( rocDto.getStatus()) ||
							(String.valueOf(RefundConfirmStatusEnum.TIMEOUT.getCode()).equals( rocDto.getStatus()) &&
									rocDto.getConNum() < 3)
									){  //等待查询任务完成
						resultMap.put("responseCode", ResponseCodeEnum.UNDEFINED_ERROR.getCode());
						resultMap.put("responseDesc", ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
						return JSonUtil.toJSonString(resultMap);
					}
				}
			}
			resultMap = beforeQryRefund(refundOrderNo, null);
		}else if(StringUtils.isEmpty(refundOrderNo) && "1".equals(paraMap.get("auto"))){ //QUERY TASK
			List<RefundOrderConfirmDTO> refundOrdConDtoList = 
					refundOrderConfirmService.queryConfirmRefundList(new Date());
			if(refundOrdConDtoList != null){
				logger.info("QueryRefundStatusHandler RefundOrdConDtoList Size[" + refundOrdConDtoList.size() + "]" );
				for(RefundOrderConfirmDTO rocDto: refundOrdConDtoList){
					refundOrderNo = rocDto.getRefundOrderNo().toString();
					beforeQryRefund(refundOrderNo, rocDto);
				}
			}else{
				logger.info("QueryRefundStatusHandler - No confirm refundOrder" );
			}
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		}else{
			resultMap.put("responseCode", ResponseCodeEnum.INVALID_PARAMETER.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.INVALID_PARAMETER.getDesc());
		}

		return JSonUtil.toJSonString(resultMap);
	}
	
	/**
	 * 退款查询处理
	 * @param refundOrderNo
	 * @return
	 */
	private Map beforeQryRefund(String refundOrderNo, RefundOrderConfirmDTO refundOrderConfirmDto){
		Map resultMap = new HashMap();
		try{
			RefundOrderDTO refundOrderDTO = refundOrderService.queryByPk(Long
					.valueOf(refundOrderNo));
			ChannelOrderDTO channelOrder = channelOrderService
					.queryByTradeOrderNo(refundOrderDTO.getPaymentOrderNo());
			
			Long tradeOrderNo = refundOrderDTO.getTradeOrderNo();
			String transferRate = channelOrder.getTransferRate();
			
			/*
			 * 判断退款订单的状态
			 */
			if(refundOrderDTO != null && !String.valueOf(RefundStatusEnum.TIMEOUT.getCode())
			        .equals(refundOrderDTO.getStatus())){
				resultMap.put("responseCode", ResponseCodeEnum.FAIL.getCode());
				resultMap.put("responseDesc","该订单不是超时状态，不允许查询操作。");
				
				return resultMap;
			}
			
			Map<String, String> refundPara = new HashMap<String, String>();
			refundPara.put("orgCode", channelOrder.getOrgCode());
			refundPara.put("partnerId", refundOrderDTO.getPartnerId());
			refundPara.put("merchantNo", channelOrder.getMerchantNo());
			refundPara.put("authorisation", channelOrder.getAuthorisation());
			refundPara.put("channelOrderNo", channelOrder
					.getChannelOrderNo().toString());
			refundPara.put("orderAmount", channelOrder.getPayAmount()
					.toString());
			refundPara.put("currencyCode", channelOrder.getCurrencyCode());
	
			//long serialNo = refundOrderService.getMaxChannelSerialNo();
			//refundOrderDTO.setSerialNo(getSerial(serialNo));
			//refundOrderDTO.setStatus(RefundStatusEnum.REFUNDING.getCode()+""); //2016-06-28 
			//refundOrderService.updateRefundOrderByPkRnTx(refundOrderDTO);
			refundPara.put("serialNo", refundOrderDTO.getSerialNo());
			refundPara.put("dealSerialNo", channelOrder.getSerialNo());
			refundPara.put("returnNo", channelOrder.getReturnNo());
			refundPara.put("refundOrderNo", refundOrderNo);
			String refundAmount = refundOrderDTO.getRefundAmount().toString();
				
			doRefundQuery(refundOrderConfirmDto, resultMap, refundOrderNo, refundOrderDTO,
					channelOrder, tradeOrderNo, transferRate, refundPara,
					refundAmount);
		}catch (Exception e) {
			logger.error("api pay error:", e);
			if("".equals(resultMap.get("responseDesc")) || null==resultMap.get("responseDesc")) {
				resultMap.put("responseCode", ResponseCodeEnum.UNDEFINED_ERROR.getCode());
				resultMap.put("responseDesc", ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			}
		}
		
		return resultMap;
		
	}

	private void doRefundQuery(RefundOrderConfirmDTO refundOrderConfirmDto, Map resultMap, String refundOrderNo,
			RefundOrderDTO refundOrderDTO, ChannelOrderDTO channelOrder,
			Long tradeOrderNo, String transferRate,
			Map<String, String> refundPara, String refundAmount)
			throws Exception {
		ChannelPaymentResult refundResult;
		String refundTransferAmount = refundOrderDTO.getTransferAmount() +"";
		refundPara.put("refundAmount", refundTransferAmount);
		refundPara.put(
				"tranDate",
				DateUtil.formatDateTime(DateUtil.PATTERN,
						channelOrder.getCompleteDate()));
		refundPara.put(
				"tranDatetime",
				DateUtil.formatDateTime(DateUtil.DEFAULT_DATE_FROMAT,
						channelOrder.getCompleteDate()));
		
		refundPara.put("currencyCode", channelOrder.getTransferCurrencyCode());

		PaymentOrderExpand paymentOrderExpand = paymentOrderExpandDAO
				.queryPayOrderExpandByPayNO(refundOrderDTO
						.getPaymentOrderNo());
		refundPara.put("customerIP", paymentOrderExpand.getIp());
		refundPara.put("cardHolderNumber",
				paymentOrderExpand.getCardNo());
		refundPara.put("accessCode",channelOrder.getAccessCode());
		refundPara.put("orgKey",channelOrder.getOrgKey());
		refundPara.put("merchantNo",channelOrder.getMerchantNo());
		TradeOrderDTO tradeOrderDTO = tradeOrderService.queryTradeOrderById(refundOrderDTO.getTradeOrderNo());
		if(tradeOrderDTO.getTradeType() != null && tradeOrderDTO.getTradeType().startsWith("210")){
			refundPara.put("voidWay","70109");
		}
		refundResult = channelClientService.channelQueryRefundSts(refundPara);
		logger.info("channelClientService channelRefund retun :" + refundResult.getCompleteStatus());
		
		RefundOrderConfirmDTO refundOc = refundOrderConfirmDto;
		RefundConfirmStatusEnum state = RefundConfirmStatusEnum.INIT;
		
		if (ResponseCodeEnum.SUCCESS.getCode().equals(
				refundResult.getErrorCode())) {
			// 调用渠道退款
			refundService.refundHandle(refundOrderNo, refundResult);
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
			
			//updateTradeAmountCount(refundOrderDTO,tradeOrderDTO,refundResult.getCompleteStatus(),refundTransferAmount);
			state = RefundConfirmStatusEnum.SUCCESS;
			
		} else if(ResponseCodeEnum.UNDEFINED_ERROR.getCode().equals(
				refundResult.getErrorCode())){
			//超时处理
			if(refundOc != null){
				Date preStartTime = refundOc.getPreStartTime();
				int conNum = refundOc.getConNum();
				if(conNum == 0){
					preStartTime = new Date(preStartTime.getTime()+ 60*1000);
				}else if(conNum == 1){
					preStartTime = new Date(preStartTime.getTime()+ 120*1000);
				}else if(conNum == 2){
					//结束
				}
				refundOc.setPreStartTime(preStartTime);
			}
			state = RefundConfirmStatusEnum.TIMEOUT;
		}else {
			// 调用渠道退款
			refundResult = new ChannelPaymentResult();
			refundResult.setErrorCode(ResponseCodeEnum.FAIL.getCode());
			refundResult.setErrorMsg(ResponseCodeEnum.FAIL.getDesc());
			refundService.refundHandle(refundOrderNo, refundResult);
			refundOrderService.updateRefundOrderState(Long.parseLong(refundOrderNo) , RefundStatusEnum.FINAL_FAIL,null);
			state = RefundConfirmStatusEnum.FAIL;
		}
		
		if(refundOc != null){ //更新退款确认记录状态
			refundOrderConfirmService.updateRefundOrderStateAfter(refundOc.getRefundConfirmId(), 
					state, refundOc.getPreStartTime());
		}

	}

	private void updateTradeAmountCount(RefundOrderDTO refundOrderDTO,TradeOrderDTO tradeOrderDTO, String completeStatus,String refundTransferAmount) {
		//获取交易统计的币种
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		TradeAmountCount tradeAmountCount = new TradeAmountCount();
		tradeAmountCount.setPartnerId(tradeOrderDTO.getPartnerId()+"");
		tradeAmountCount.setCountMonth(sdf.format(tradeOrderDTO.getCreateDate()));
		TradeAmountCount query = tradeAmountCountService.query(tradeAmountCount);
		if(query!=null){
			//先转汇
			tradeAmountCount = query;
			SettlementRate settlementRete = currencyRateService.getSettlementRate(tradeOrderDTO.getCurrencyCode(),tradeAmountCount.getTotalCurrencyCode(),"1"
							,String.valueOf(tradeOrderDTO.getPartnerId()),null);
			//计算金额
			long refundAmountYuan = new BigDecimal(refundOrderDTO.getRefundAmount()).multiply(new BigDecimal(settlementRete.getExchangeRate()))
			.divide(new BigDecimal("1000")).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
			tradeAmountCount.setTotalAmount(tradeAmountCount.getTotalAmount()-refundAmountYuan);
			tradeAmountCount.setUpdateDate(Calendar.getInstance().getTime());
			tradeAmountCountService.update(tradeAmountCount);
		}

		if("1".equals(completeStatus)){
			PreController controller = preControllerService.getAuthControllerByPreAuthTradeNo(tradeOrderDTO.getLinkTradeNo());
			controller.setCaptureAmountTotal(controller.getCaptureAmountTotal() - tradeOrderDTO.getOrderAmount());
			controller.setActCapAmountTotal(controller.getActCapAmountTotal() - Long.parseLong(refundTransferAmount));
			preControllerService.updateAuthControllerRnTx(controller);
		}
	}

	private String getSerial(long serialNo) {
		String str = String.valueOf(serialNo);
		int l = str.length();
		if (str.length() < 6) {
			for (int i = 0; i < 6 - l; i++) {
				str = "0" + str;
			}
		}
		return str;
	}

	private long calRefundAmt(Long refundAmount,String transRate){
		Long amount = new BigDecimal(refundAmount).multiply(new BigDecimal(transRate)).longValue();

		if (!amount.toString().endsWith("0")) {
			long tAmount = new BigDecimal(amount).divide(new BigDecimal(10)).longValue() * 10 + 10;// 厘如果不为0，则向前进1
			return new BigDecimal(amount).divide(new BigDecimal(10)).longValue() * 10+10;
		}else{
			return amount;
		}
	}
	
	public static void main(String[] args){
		
		QueryRefundStatusHandler handler = new QueryRefundStatusHandler();
		
		long m = handler.calRefundAmt(1750L, "1.1");
		
		System.out.println(m);
	}
}
