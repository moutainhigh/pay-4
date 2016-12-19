package com.pay.channel.service.impl;

import java.util.List;

import com.pay.channel.dao.ChannelItemRCurrencyDAO;
import com.pay.channel.model.ChannelItemRCurrency;
import com.pay.channel.service.ChannelItemRCurrencyService;
import com.pay.inf.dao.Page;

public class ChannelItemRCurrencyServiceImpl implements ChannelItemRCurrencyService{
	private ChannelItemRCurrencyDAO channelItemRCurrencyDAO;

	@Override
	public List<ChannelItemRCurrency> queryChannelCurrency(ChannelItemRCurrency cc, Page page) {
		cc.setStatus("1");//只查询有效的
		return channelItemRCurrencyDAO.queryChannelCurrency(cc, page);
	}

	@Override
	public void addChannelItemRCurrency(ChannelItemRCurrency cc) throws Exception {
			cc.setStatus("1");
			Page page = new Page();
			List<ChannelItemRCurrency> array = this.queryChannelCurrency(cc, page);
			if(array!= null && array.size() > 0){
				throw new Exception("该配置已存在");
			}
			channelItemRCurrencyDAO.addChannelItemRCurrency(cc);
	}

	public void setChannelItemRCurrencyDAO(ChannelItemRCurrencyDAO channelItemRCurrencyDAO) {
		this.channelItemRCurrencyDAO = channelItemRCurrencyDAO;
	}

	@Override
	public void validChannelItemRCurrency(ChannelItemRCurrency cc) {
		cc.setStatus("0");//失效
		channelItemRCurrencyDAO.updateChannelItemRCurrency(cc);
	}

	@Override
	public List<ChannelItemRCurrency> queryChannelCurrency(ChannelItemRCurrency cc) {
		cc.setStatus("1");//只查询有效的
		return channelItemRCurrencyDAO.queryChannelCurrency(cc);
	}

	
}
