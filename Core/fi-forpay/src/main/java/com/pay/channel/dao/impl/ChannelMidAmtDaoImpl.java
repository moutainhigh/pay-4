package com.pay.channel.dao.impl;

import java.util.Map;

import com.pay.channel.dao.ChannelMidAmtDao;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class ChannelMidAmtDaoImpl extends BaseDAOImpl implements ChannelMidAmtDao{

	@Override
	public void createChannelMidAmt(Map<String, String> data) {
		this.create(data);
	}

}
