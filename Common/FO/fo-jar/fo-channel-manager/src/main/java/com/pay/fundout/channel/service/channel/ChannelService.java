package com.pay.fundout.channel.service.channel;

import java.util.List;

import com.pay.fundout.channel.model.channel.ChannelObject;
import com.pay.fundout.channel.model.channel.ChannelRequest;

/**
 * 出款渠道提供的服务接口
 * @author meng.li
 *
 */
public interface ChannelService {

	/**
	 * 获取渠道编码
	 * @param request
	 * @return
	 */
	String getFundoutChannel(ChannelRequest request);
	
	/**
	 * 获取可用渠道列表
	 * @param request
	 * @return
	 */
	List<ChannelObject> getValidChannels(ChannelRequest request);
	
	/**
	 * 获取对应渠道信息
	 * @param request
	 * @return
	 */
	ChannelObject getChannelObject(ChannelRequest request);
}
