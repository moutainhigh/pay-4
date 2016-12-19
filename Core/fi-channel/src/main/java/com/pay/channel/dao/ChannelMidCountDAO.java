package com.pay.channel.dao;

import java.util.List;

import com.pay.channel.model.ChannelMidCount;
import com.pay.inf.dao.BaseDAO;


/**
 * 二级商户号交易次数统计持久化接口
 * @author Bobby Guo
 * @date 2015年10月12日
 */
public interface ChannelMidCountDAO extends BaseDAO<ChannelMidCount>{

	public List<ChannelMidCount> queryChannelMidCount(ChannelMidCount channelMidCount);
	
	public boolean updateChannelMidCount(ChannelMidCount channelMidCount);
	
}
