package com.pay.channel.dao.impl;

import java.util.List;

import com.pay.channel.dao.ChannelMidAmountDAO;
import com.pay.channel.model.ChannelMidAmount;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 二级商户号交易金额统计持久化实现
 * @author delin dong
 * @date 2016年05月18日
 */
public class ChannelMidAmountDAOImpl extends BaseDAOImpl<ChannelMidAmount> implements ChannelMidAmountDAO{

	public List<ChannelMidAmount> queryChannelMidAmount(
			ChannelMidAmount channelMidAmount){
		List<ChannelMidAmount> list = super.findByCriteria(channelMidAmount);
		return list != null ?list : null;
	};

}
