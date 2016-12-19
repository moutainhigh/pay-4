package com.pay.channel.dao;

import java.util.List;

import com.pay.channel.model.ChannelMidAmount;
import com.pay.inf.dao.BaseDAO;
/**
 * 二级商户号交易金额统计持久化接口
 * @author delin dong
 * @date 2016年05月18日
 */
public interface ChannelMidAmountDAO extends BaseDAO<ChannelMidAmount>{

	List<ChannelMidAmount> queryChannelMidAmount(
			ChannelMidAmount channelMidAmount);

}
