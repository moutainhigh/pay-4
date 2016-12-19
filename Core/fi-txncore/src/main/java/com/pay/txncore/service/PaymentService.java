/**
 * 
 */
package com.pay.txncore.service;

import java.util.Map;

import com.pay.jms.sender.JmsSender;
import com.pay.txncore.dto.ChannelPaymentResult;
import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;
import com.pay.txncore.dto.TradeBaseDTO;
import com.pay.txncore.dto.TradeExtendsDTO;
import com.pay.txncore.dto.TradeOrderDTO;

/**
 * 收单、支付服务
 * 
 * @author chaoyue
 *
 */
public interface PaymentService {
	
	/**
	 * 支付回调
	 * @return
	 */
	PaymentResult paymentCallback(ChannelPaymentResult channelPaymentResult);
	
	
	/**
	 * 3D支付回调
	 */
	
	public PaymentResult payment3DCallback(	Map requestParam);
	
	
	/**
	 * 查询并设置清算币种
	 * @param paymentInfo
	 */
	void setSettlementCurrencyCode(PaymentInfo paymentInfo);
	
	/**
	 * 保存支付扩展信息
	 * @param paymentInfo
	 * @param tradeOrderDTO
	 */
	void saveExtendsInfo(PaymentInfo paymentInfo,
			TradeOrderDTO tradeOrderDTO);
	
	/**
	 * 
	 * @param paymentInfo
	 * @param tradeOrderDTO
	 * @param paymentResult
	 */
	void riskValidate(PaymentInfo paymentInfo,
            TradeOrderDTO tradeOrderDTO,PaymentResult paymentResult);
	
	/**
	 * 返回网关订单service
	 * @return
	 */
	TradeOrderService getTradeOrderService();
	
	/**
	 * 
	 * @return
	 */
	JmsSender getJmsSender();
	
	/**
	 * 普通支付
	 * @param paymentInfo
	 * @param tradeOrderDTO
	 * @param tradeBaseDTO
	 * @param paymentWay
	 * @return
	 */
	PaymentResult payByChannels(PaymentInfo paymentInfo,
			TradeOrderDTO tradeOrderDTO, TradeBaseDTO tradeBaseDTO,
			String paymentWay);
	/**
	 * 3D支付
	 * @param paymentInfo
	 * @param tradeOrderDTO
	 * @param tradeBaseDTO
	 * @param paymentWay
	 * @return
	 */
	PaymentResult payBy3DChannels(PaymentInfo paymentInfo,
			TradeOrderDTO tradeOrderDTO, TradeBaseDTO tradeBaseDTO,
			String paymentWay);
	
	/**
	 * 威富通【微信｜支付宝】
	 * @param paymentInfo
	 * @param tradeOrderDTO
	 * @param tradeBaseDTO
	 * @param paymentWay
	 * @return
	 */
	PaymentResult payByWft(PaymentInfo paymentInfo,
			TradeOrderDTO tradeOrderDTO, TradeBaseDTO tradeBaseDTO,
			String paymentWay);
	
	/**
	 * 本地化支付
	 * @param paymentInfo
	 * @param tradeOrderDTO
	 * @param tradeBaseDTO
	 * @param paymentWay
	 * @return
	 */
	PaymentResult payByLocaleChannels(PaymentInfo paymentInfo,
			TradeOrderDTO tradeOrderDTO, TradeBaseDTO tradeBaseDTO,
			String paymentWay);
	
	/**
	 * 中国本地化支付
	 * @param paymentInfo
	 * @param tradeOrderDTO
	 * @param tradeBaseDTO
	 * @param paymentWay
	 * @return
	 */
	PaymentResult payByChinaLocaleChannels(PaymentInfo paymentInfo,
			TradeOrderDTO tradeOrderDTO, TradeBaseDTO tradeBaseDTO,
			String paymentWay);
	
	/**
	 * 更新扩展信息
	 * @param tradeExtendsDTO
	 * @param paymentInfo
	 */
	void updateTradesExtendsDTO(TradeExtendsDTO tradeExtendsDTO,
				PaymentInfo paymentInfo);
	
	/**
	 * 
	 * @param paymentInfo
	 * @param tradeOrderDTO
	 */
	void updateTradeData(PaymentInfo paymentInfo,
			TradeOrderDTO tradeOrderDTO);
	
	/**
	 * 
	 * @return
	 */
	TradeExtendsService getTradeExtendsService();
	
	TradeDataService getTradeDataService();
	
	void updateTradeDataNew(PaymentInfo paymentInfo,TradeOrderDTO tradeOrderDTO);

	PaymentResult pay4All(PaymentInfo paymentInfo,
						  TradeOrderDTO tradeOrderDTO, TradeBaseDTO tradeBaseDTO,
						  String paymentWay);
	
	/**确认结算币种
	 * @param tradeBaseDTO
	 * @param payCurrencyCode
	 * @return
	 */
	TradeBaseDTO confirmTheSettlementCurrency(TradeBaseDTO tradeBaseDTO,String payCurrencyCode);
	/**根据结算币种获得对应收款人的结算账户类型
	 * @param settlementCurrencyCode 结算币种
	 * @param memberCode 会员号
	 * @return 收款人账户类型
	 */
	Integer getPayeeTypeBySettlementCurrency(String settlementCurrencyCode,Long memberCode);
}
