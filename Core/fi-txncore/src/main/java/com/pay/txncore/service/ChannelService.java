/**
 * 
 */
package com.pay.txncore.service;

import com.pay.jms.sender.JmsSender;
import com.pay.txncore.crosspay.service.CurrencyRateService;
import com.pay.txncore.crosspay.service.PartnerRateFloatService;
import com.pay.txncore.dto.ChannelOrderDTO;
import com.pay.txncore.dto.ChannelPreauthResponseDto;
import com.pay.txncore.dto.ChannelResponseDto;
import com.pay.txncore.dto.PaymentChannelItemDto;
import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentOrderDTO;
import com.pay.txncore.dto.PreauthInfo;
import com.pay.txncore.dto.PreauthOrderDTO;
import com.pay.txncore.model.AuthChangeOrder;
import com.pay.txncore.model.ChannelOrder;
import com.pay.txncore.model.PreController;

/**
 * 渠道服务
 * 
 * @author chaoyue
 *
 */
public interface ChannelService {

	/**
	 * 渠道支付
	 * 
	 * @param channelRequest
	 * @return
	 */
	ChannelResponseDto channelPay(PaymentOrderDTO paymentOrderDTO,
			PaymentInfo paymentInfo);
	
	//Add by : Bobby Guo
	public ChannelResponseDto channelPay(PaymentOrderDTO paymentOrderDTO,
			PaymentInfo paymentInfo,PaymentChannelItemDto paymentChannelItemDto);
	
	/**
	 * 渠道预授权
	 * 
	 * @param channelRequest
	 * @return
	 */
	ChannelPreauthResponseDto channelPreauth(PreauthOrderDTO paymentOrderDTO,
			PreauthInfo paymentInfo);
	
	/**
	 * 渠道预授权完成
	 * 
	 * @param channelRequest
	 * @return
	 */
	ChannelPreauthResponseDto channelPreauthCompeleted(PreauthOrderDTO paymentOrderDTO,
			PreauthInfo paymentInfo);
	
	/**
	 * 渠道预授权撤销
	 * 
	 * @param channelRequest
	 * @return
	 */
	ChannelPreauthResponseDto channelPreauthRevoke(PreauthOrderDTO paymentOrderDTO,
			PreauthInfo paymentInfo);
	
	CurrencyRateService getCurrencyRateService();
	
	ChannelResponseDto systemDetected(PaymentOrderDTO paymentOrderDTO,
			PaymentInfo paymentInfo);
	
	public JmsSender getJmsSender();
	

	
	PartnerRateFloatService getPartnerRateFloatService();
	
	void  doAccounting(ChannelOrderDTO channelOrderDTO,String merchantOrderId);


	/**
	 * 3D渠道支付
	 * 
	 * @param channelRequest
	 * @return
	 */
	PaymentInfo channel3DPay(PaymentOrderDTO paymentOrderDTO,
			PaymentInfo paymentInfo, PaymentChannelItemDto paymentChannelItemDto);
	
	/**
	 * 3D渠道支付
	 * 
	 * @param channelRequest
	 * @return
	 */
	ChannelResponseDto channelChinaLocalPay(PaymentOrderDTO paymentOrderDTO,
			PaymentInfo paymentInfo, PaymentChannelItemDto paymentChannelItemDto);
	
	/**
	 *  新收银台
	 *
	 * @param channelRequest
	 * @return
	 */
	PaymentInfo channelPay4Return(PaymentOrderDTO paymentOrderDTO,
							 PaymentInfo paymentInfo, PaymentChannelItemDto paymentChannelItemDto);
	
	/**
	 * 威富通【微信｜支付宝】
	 *
	 * @param channelRequest
	 * @return
	 */
	PaymentInfo channelPay4Wft(PaymentOrderDTO paymentOrderDTO,
			PaymentInfo paymentInfo, PaymentChannelItemDto paymentChannelItemDto);

	/**
	 * 渠道预授权
	 * @param paymentOrderDTO
	 * @param paymentInfo
	 * @param paymentChannelItemDto
	 * @param controller
	 * @return
	 */
	ChannelResponseDto preAuthPayChannel(PaymentOrderDTO paymentOrderDTO,
								  PaymentInfo paymentInfo, PaymentChannelItemDto paymentChannelItemDto, final PreController controller);

	/**
	 * 预授权消费
	 * @param paymentOrderDTO
	 * @param paymentInfo
	 * @param controller
	 * @return
	 */
	ChannelResponseDto preAuthCapture(PaymentOrderDTO paymentOrderDTO,ChannelOrder origChannelOrder,
										 PaymentInfo paymentInfo,final PreController controller);

	/**
	 * 预授权void
	 * @param paymentInfo
	 * @param controller
	 * @return
	 */
	ChannelResponseDto preAuthVoid(PaymentInfo paymentInfo, final PreController controller,ChannelOrder origChannelOrder,final AuthChangeOrder saveAuthChangeOrder);
}
