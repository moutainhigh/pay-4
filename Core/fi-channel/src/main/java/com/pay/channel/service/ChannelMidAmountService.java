package com.pay.channel.service;

import java.util.List;

import com.pay.channel.model.ChannelSecondRelation;

public interface ChannelMidAmountService {

	ChannelSecondRelation getLeastOfMids(Long valueOf,
			List<ChannelSecondRelation> relations, String orgMerchantCode, String vom);

	
	
	
}
