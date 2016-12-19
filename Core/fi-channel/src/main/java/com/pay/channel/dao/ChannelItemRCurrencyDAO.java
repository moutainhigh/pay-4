package com.pay.channel.dao;

import java.util.List;

import com.pay.channel.model.ChannelItemRCurrency;
import com.pay.inf.dao.Page;

public interface ChannelItemRCurrencyDAO {
	List<ChannelItemRCurrency> queryChannelCurrency(ChannelItemRCurrency cc, Page page);
	
	List<ChannelItemRCurrency> queryChannelCurrency(ChannelItemRCurrency cc);
	
	void deleteChannelItemRCurrency(Long id);
	
	void updateChannelItemRCurrency(ChannelItemRCurrency cc);
	
	void addChannelItemRCurrency(ChannelItemRCurrency cc);
}
