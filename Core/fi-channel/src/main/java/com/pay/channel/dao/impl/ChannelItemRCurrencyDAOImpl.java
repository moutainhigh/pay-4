package com.pay.channel.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.channel.dao.ChannelItemRCurrencyDAO;
import com.pay.channel.model.ChannelItemRCurrency;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class ChannelItemRCurrencyDAOImpl extends BaseDAOImpl<ChannelItemRCurrency> implements ChannelItemRCurrencyDAO{

	@Override
	public List<ChannelItemRCurrency> queryChannelCurrency(ChannelItemRCurrency cc, Page page) {
		return super.findByCriteria(cc,page);
	}

	@Override
	public void deleteChannelItemRCurrency(Long id) {
		Map map=new HashMap();
		map.put("id", id);
		super.delete(map);
	}


	@Override
	public void updateChannelItemRCurrency(ChannelItemRCurrency cc) {
		super.update(cc);
	}
	@Override
	public void addChannelItemRCurrency(ChannelItemRCurrency cc) {
		super.create(cc);
	}

	@Override
	public List<ChannelItemRCurrency> queryChannelCurrency(
			ChannelItemRCurrency cc) {
		return super.findByCriteria(cc);
	}
	
	
	
	
}
