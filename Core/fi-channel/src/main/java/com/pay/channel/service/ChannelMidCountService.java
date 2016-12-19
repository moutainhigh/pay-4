package com.pay.channel.service;

import java.util.List;

import com.pay.channel.model.ChannelConfig;
import com.pay.channel.model.ChannelMidCount;
import com.pay.channel.model.ChannelSecondRelation;

public interface ChannelMidCountService {
	
	public ChannelSecondRelation getLeastOfMids(long memberCode,List<ChannelSecondRelation> channelSecondRelations,String defaultMid);
	
	public boolean updateChannelMidCount(ChannelMidCount channelMidCount);
	/**
	 * 初始化二级商户号月统计数据
	 * 如果不存在就新增一条
	 * @param channelMidCount
	 */
	public void initChannelMidCount(ChannelMidCount channelMidCount);
}
