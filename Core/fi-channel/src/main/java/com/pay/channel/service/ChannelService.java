/**
 * 
 */
package com.pay.channel.service;

import com.pay.channel.dto.ChannelPreathResponseDto;
import com.pay.channel.dto.ChannelRequestDto;
import com.pay.channel.dto.ChannelResponseDto;
import com.pay.channel.dto.PaymentInfo;
import com.pay.channel.dto.PreauthInfo;
import com.pay.channel.model.PaymentChannelItem;

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
	ChannelResponseDto channelPay(PaymentInfo channelRequest);
	
	/**
	 * 渠道预授权完成
	 * 
	 * @param channelRequest
	 * @return
	 */
	ChannelPreathResponseDto channelPreauth(PreauthInfo channelRequest);
	
	/**
	 * 渠道预授权
	 * 
	 * @param channelRequest
	 * @return
	 */
	ChannelResponseDto channelPreauthCompeleted(PreauthInfo channelRequest);
	
	/**
	 * 渠道预授权撤销
	 * 
	 * @param channelRequest
	 * @return
	 */
	ChannelResponseDto channelPreauthRevoke(PreauthInfo channelRequest);

	/**
	 * 渠道回调
	 * 
	 * @param channelRequest
	 * @return
	 */
	ChannelResponseDto channelCallBack(ChannelRequestDto channelRequest);

	/**
	 * 
	 * @param channelRequest
	 * @return
	 */
	ChannelResponseDto channelQuery(ChannelRequestDto channelRequest);

	/**
	 * 
	 * @param channelRequest
	 * @return
	 */
	ChannelResponseDto channelRefund(ChannelRequestDto channelRequest);
	
	/**
	 * 
	 * @param channelRequest
	 * @return
	 */
	ChannelResponseDto channelRefundStatusQuery(ChannelRequestDto channelRequest);
	
   /**
    * 系统探测服务
    * @param channelRequest
    * @return
    */
   ChannelResponseDto systemDetected(PaymentInfo channelRequest);


	/**
	 * 预授权申请20160901
	 *
	 * @param channelRequest
	 * @return
	 */
	ChannelResponseDto preAuthApply(PaymentInfo channelRequest);

	ChannelResponseDto preAuthCapture(PaymentChannelItem channelItem,
											ChannelRequestDto channelRequestDto,String orgiChannelOrderNo,String orgiReturnNo);

	ChannelResponseDto preAuthVoid(PaymentChannelItem channelItem,
											ChannelRequestDto channelRequestDto,String orgiChannelOrderNo,String orgiReturnNo);

}
