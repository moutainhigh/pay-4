/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.chargeback;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.crosspay.model.ExpressTracking;
import com.pay.txncore.crosspay.service.ExpressTrackingService;
import com.pay.txncore.dao.PaymentOrderExpandDAO;
import com.pay.txncore.dto.ChannelOrderDTO;
import com.pay.txncore.dto.PaymentOrderDTO;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.model.ChargeBackOrder;
import com.pay.txncore.model.PartnerSettlementOrder;
import com.pay.txncore.model.PaymentOrderExpand;
import com.pay.txncore.service.ChannelOrderService;
import com.pay.txncore.service.PartnerSettlementOrderService;
import com.pay.txncore.service.PaymentOrderService;
import com.pay.txncore.service.TradeOrderService;
import com.pay.txncore.service.chargeback.ChargeBackOrderService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 拒付订单查询
 * 
 * @author chma
 */
public class ChargebackOrderImportHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(ChargebackOrderImportHandler.class);
	private ChargeBackOrderService chargeBackOrderService;
	private TradeOrderService tradeOrderService;
	private ExpressTrackingService expressTrackingService;
	private PaymentOrderService paymentOrderService;
	private ChannelOrderService channelOrderService;
	private PaymentOrderExpandDAO paymentOrderExpandDAO;
	private EnterpriseBaseService enterpriseBaseService;
	private PartnerSettlementOrderService partnerSettlementOrderService;

	public void setChargeBackOrderService(
			ChargeBackOrderService chargeBackOrderService) {
		this.chargeBackOrderService = chargeBackOrderService;
	}

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}

	public void setExpressTrackingService(
			ExpressTrackingService expressTrackingService) {
		this.expressTrackingService = expressTrackingService;
	}

	public void setPaymentOrderService(PaymentOrderService paymentOrderService) {
		this.paymentOrderService = paymentOrderService;
	}

	public void setChannelOrderService(ChannelOrderService channelOrderService) {
		this.channelOrderService = channelOrderService;
	}

	public void setPaymentOrderExpandDAO(
			PaymentOrderExpandDAO paymentOrderExpandDAO) {
		this.paymentOrderExpandDAO = paymentOrderExpandDAO;
	}

	public void setEnterpriseBaseService(
			EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

	public void setPartnerSettlementOrderService(
			PartnerSettlementOrderService partnerSettlementOrderService) {
		this.partnerSettlementOrderService = partnerSettlementOrderService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map resultMap = new HashMap();
		Map paraMap = null;

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
		} catch (Exception e) {
			logger.error("ChannelQueryHandler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}

		List<Map> listMap = (List<Map>) paraMap.get("chargeBackOrders");
		List<ChargeBackOrder> chargeBackOrders = MapUtil.map2List(
				ChargeBackOrder.class, listMap);

		String resultCode = null;
		String resultMsg = null;

		List<ChargeBackOrder> orders = new ArrayList<ChargeBackOrder>();

		StringBuilder errMsg = new StringBuilder();
		// check
		for (ChargeBackOrder order : chargeBackOrders) {

			String authorisation = order.getAuthorisation();
			Date tradeDate = order.getTradeDate();

			Long chargeBackOrderAmount = new BigDecimal(
					order.getChargeBackAmount()).multiply(
					new BigDecimal("1000")).longValue();

			ChannelOrderDTO channelOrderDTO = channelOrderService
					.queryByTradeDateAndAuthorisation(order.getOrgCode(),
							new SimpleDateFormat("yyyy-MM-dd")
									.format(tradeDate), authorisation);
			if (null == channelOrderDTO) {
				resultCode = "9999";
				resultMsg = authorisation + "授权码不存在<br/>";

				errMsg.append(resultMsg + "");
				continue;
			}

			ChargeBackOrder chargeBackOrder = new ChargeBackOrder();

			PaymentOrderDTO paymentOrder = paymentOrderService
					.queryByPaymentOrderNo(channelOrderDTO.getPaymentOrderNo());

			// 如果已存在拒付订单，返回提示
			List<ChargeBackOrder> existsChargeBackOrders = chargeBackOrderService
					.queryChargeBackOrderByAuthorAndTradeDate(order.getAuthorisation(),null);
			if (isChargeback(existsChargeBackOrders)) {
				resultCode = "9999";
				resultMsg = authorisation + "已拒付<br/>";

				errMsg.append(resultMsg + "");
				continue;
			}

			TradeOrderDTO tradeOrder = tradeOrderService
					.queryTradeOrderById(paymentOrder.getTradeOrderNo());

			if (tradeOrder == null) {
				resultCode = "9999";
				resultMsg = authorisation + "不存在，不能录拒付<br/>";

				errMsg.append(resultMsg + "");
				continue;
			}

			if (tradeOrder.getStatus() != 3 && tradeOrder.getStatus() != 4) {
				resultCode = "9999";
				resultMsg = authorisation + "未成功，不能录拒付<br/>";

				errMsg.append(resultMsg + "");
				continue;
			}

			// 判断拒付金额
//			if (chargeBackOrderAmount > channelOrderDTO.getPayAmount()) {
//				resultCode = "9999";
//				resultMsg = authorisation + "，拒付金额大于支付金额<br/>";
//
//				errMsg.append(resultMsg + "");
//				continue;
//			}

			// 已全额退款
			if (tradeOrder.getRefundAmount() == 0) {
				resultCode = "9999";
				resultMsg = authorisation + "已全额退款<br/>";

				errMsg.append(resultMsg + "");
				continue;
			}

			chargeBackOrder.setTradeOrderNo(tradeOrder.getTradeOrderNo());

			ExpressTracking expressTracking = expressTrackingService
					.findByTradeOrderNo(tradeOrder.getTradeOrderNo() + "");
			chargeBackOrder.setCardHolderEmail(expressTracking.getBillEmail());

			PaymentOrderExpand paymentOrderExpand = paymentOrderExpandDAO
					.queryPayOrderExpandByPayNO(paymentOrder
							.getPaymentOrderNo());
			chargeBackOrder.setCardNo(paymentOrderExpand.getCardNo());
			chargeBackOrder.setAuthorisation(authorisation);
			chargeBackOrder.setChannelOrderId(channelOrderDTO
					.getChannelOrderNo());
			chargeBackOrder.setChargeBackAmount(chargeBackOrderAmount + "");
			chargeBackOrder.setChargeBackMsg(order.getChargeBackMsg());
			chargeBackOrder.setChargeBackMsg1(order.getChargeBackMsg1());
			chargeBackOrder.setCpCurrencyCode(order.getCpCurrencyCode());
			chargeBackOrder.setCpdDate(order.getCpdDate());
			chargeBackOrder.setCpFlg(order.getCpFlg());
			chargeBackOrder.setCpType(order.getCpType());
			chargeBackOrder.setCreateDate(new Date());
			chargeBackOrder.setAccountingFlg(0);
			chargeBackOrder.setFineFlg(0);
			chargeBackOrder.setOrgCode(channelOrderDTO.getOrgCode());
			chargeBackOrder.setTradeDate(tradeOrder.getCompleteDate());
			chargeBackOrder.setTradeAmount(tradeOrder.getOrderAmount());
			chargeBackOrder.setCurrencyCode(tradeOrder.getCurrencyCode());
			chargeBackOrder.setIp(paymentOrderExpand.getIp());
			chargeBackOrder.setMemberCode(tradeOrder.getPartnerId().toString());

			EnterpriseBaseDto enterpriseBaseDto = enterpriseBaseService
					.queryEnterpriseBaseByMemberCode(tradeOrder.getPartnerId());
			chargeBackOrder.setMerchantCode(enterpriseBaseDto.getMerchantCode()
					.toString());
			chargeBackOrder.setMerchantName(enterpriseBaseDto.getZhName());
			chargeBackOrder.setOldRefNo(order.getOldRefNo());
			chargeBackOrder.setOperator(order.getOperator());
			chargeBackOrder.setRefNo(order.getRefNo());

			List<PartnerSettlementOrder> settlementOrders = partnerSettlementOrderService
					.querySettlementOrder(tradeOrder.getPartnerId(),
							paymentOrder.getPaymentOrderNo());
			Long settlementAmount = 0L;
			for (PartnerSettlementOrder settlementOrder : settlementOrders) {
				settlementAmount += settlementOrder.getAmount();
			}

			chargeBackOrder.setSettlementAmount(settlementAmount);
			chargeBackOrder.setSettlementCurrencyCode(settlementOrders.get(0).getSettlementCurrencyCode());
			chargeBackOrder.setStatus(0);
			orders.add(chargeBackOrder);
		}

		if (!orders.isEmpty()) {
			chargeBackOrderService.batchAddChargeBackOrder(orders);
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());

			if (!StringUtil.isEmpty(errMsg.toString())) {
				resultMap.put("responseDesc", errMsg.toString());
			} else {
				resultMap.put("responseDesc",
						ResponseCodeEnum.SUCCESS.getDesc());
			}

		} else {
			resultMap.put("responseCode", resultCode);
			resultMap.put("responseDesc", errMsg.toString());
		}

		logger.info("process msg:" + errMsg);

		return JSonUtil.toJSonString(resultMap);
	}
	
	private boolean isChargeback(List<ChargeBackOrder> existsChargeBackOrders){
		if(null == existsChargeBackOrders || existsChargeBackOrders.isEmpty()){
			return false;
		}
		for(ChargeBackOrder chargeBackOrder:existsChargeBackOrders){
			if(chargeBackOrder.getStatus() != 2){
				return true;
			}
		}
		return false;
	}
}
