package com.pay.channel.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.channel.dao.ChannelCurrencyDao;
import com.pay.channel.model.ChannelCrrencyReviewed;
import com.pay.channel.model.ChannelCurrency;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
/**
 * 渠道币种配置增删改查
 * @author delin
 *	@date 2016年6月21日17:44:32
 */
public class ChannelCurrencyDaoImpl extends BaseDAOImpl implements ChannelCurrencyDao {

	@Override
	public List<ChannelCurrency> queryChannelCurrency(ChannelCurrency cc,Page page) {
		return super.findByCriteria(cc,page);
	}

	@Override
	public void deleteChannelCurrency(String id) {
		Map map=new HashMap();
		map.put("id", id);
		super.delete(map);
	}

	@Override
	public void addChannelCurrency(List<Map> list) {
		for (Map map : list) {
			super.create(map);
		}
	}

	@Override
	public void updateChannelCurrency(ChannelCurrency cc) {
		super.update(cc);
	}

	@Override
	public List<ChannelCrrencyReviewed> queryChannelCurrencyReviewed(
			ChannelCrrencyReviewed cc, Page page) {
		return super.findByCriteria("queryChannelCurrencyReviewed",cc,page);
	}

	@Override
	public void addChannelCurrencyReviewed(List<Map> list) {
		for (Map map : list) {
			super.create("addChannelCurrencyReviewed",map);
		}
	}

	@Override
	public void updateChannelCurrencyRev(Map<String, String> paraMap) {
		super.update("updateChannelCurrencyRev", paraMap);
	}

	@Override
	public void addChannelCurrency(Map map) {
		super.create("addChannelCurrency", map);
	}

	@Override
	public void updateChannelCurrency(Map map) {
		super.update("updateChannelCurrency",map);
	}

	public int checkSameSetting(Map map){
		return (Integer) super.findObjectByCriteria("checkSameSetting",map);
	}
	
}
