package com.pay.channel.dao;

import java.util.List;
import java.util.Map;

import com.pay.channel.model.ChannelCrrencyReviewed;
import com.pay.channel.model.ChannelCurrency;
import com.pay.inf.dao.Page;
/**
 * 渠道币种配置增删改查
 * @author delin
 *	@date 2016年6月21日17:44:32
 */
public interface ChannelCurrencyDao {

	List<ChannelCurrency> queryChannelCurrency(ChannelCurrency cc, Page page);

	void deleteChannelCurrency(String id);

	void addChannelCurrency(List<Map> list);

	void updateChannelCurrency(ChannelCurrency cc);

	List<ChannelCrrencyReviewed> queryChannelCurrencyReviewed(
			ChannelCrrencyReviewed cc, Page page);

	void addChannelCurrencyReviewed(List<Map> list);

	void updateChannelCurrencyRev(Map<String, String> paraMap);

	void addChannelCurrency(Map map);

	void updateChannelCurrency(Map map);

	int checkSameSetting(Map map);
	
}
