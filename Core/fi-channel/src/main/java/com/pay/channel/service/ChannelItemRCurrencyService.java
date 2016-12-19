package com.pay.channel.service;

import java.util.List;

import com.pay.channel.model.ChannelItemRCurrency;
import com.pay.inf.dao.Page;

public interface ChannelItemRCurrencyService {
	
	List<ChannelItemRCurrency> queryChannelCurrency(ChannelItemRCurrency cc, Page page);
	
	List<ChannelItemRCurrency> queryChannelCurrency(ChannelItemRCurrency cc);
	
	void addChannelItemRCurrency(ChannelItemRCurrency cc) throws Exception;
	
	void validChannelItemRCurrency(ChannelItemRCurrency cc);
}
