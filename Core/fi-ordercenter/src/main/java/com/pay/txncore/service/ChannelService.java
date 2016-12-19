/**
 * 
 */
package com.pay.txncore.service;

import com.pay.jms.sender.JmsSender;
import com.pay.txncore.crosspay.service.CurrencyRateService;
import com.pay.txncore.crosspay.service.PartnerRateFloatService;
import com.pay.txncore.dto.ChannelOrderDTO;

/**
 * 渠道服务
 * 
 * @author chaoyue
 *
 */
public interface ChannelService {
	
	CurrencyRateService getCurrencyRateService();
	
	public JmsSender getJmsSender();
	
	PartnerRateFloatService getPartnerRateFloatService();
	
	void  doAccounting(ChannelOrderDTO channelOrderDTO,String merchantOrderId);
}
