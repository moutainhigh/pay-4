package com.pay.channel.dao.impl;

import java.util.List;

import com.pay.channel.dao.ChannelMidCountDAO;
import com.pay.channel.model.ChannelMidCount;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 二级商户号交易次数统计持久化实现
 * @author Bobby Guo
 * @date 2015年10月12日
 */
public class ChannelMidCountDAOImpl extends BaseDAOImpl<ChannelMidCount> implements ChannelMidCountDAO{

	@Override
	public List<ChannelMidCount> queryChannelMidCount(ChannelMidCount channelMidCount) {
		List<ChannelMidCount> list = super.findByCriteria(channelMidCount);
		return list != null ?list : null;
	}

	@Override
	public boolean updateChannelMidCount(ChannelMidCount channelMidCount) {
		return super.update(channelMidCount);
	}
}
