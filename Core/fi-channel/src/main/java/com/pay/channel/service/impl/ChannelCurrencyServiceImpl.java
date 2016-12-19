package com.pay.channel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pay.channel.dao.ChannelCurrencyDao;
import com.pay.channel.model.ChannelCrrencyReviewed;
import com.pay.channel.model.ChannelCurrency;
import com.pay.channel.service.ChannelCurrencyService;
import com.pay.inf.dao.Page;
import com.pay.util.StringUtil;

/**
 * 渠道币种配置增删改查
 * @author delin
 *	@date 2016年6月21日17:44:32
 */
public class ChannelCurrencyServiceImpl implements ChannelCurrencyService{

	private ChannelCurrencyDao channelCurrencyDao;
	@Override
	public List<ChannelCurrency> queryChannelCurrency(ChannelCurrency cc,Page page) {
		return channelCurrencyDao.queryChannelCurrency(cc,page);
	}
	@Override
	public void addChannelCurrency(List<Map> list) {
		channelCurrencyDao.addChannelCurrency(list);
	}
	@Override
	public void deleteChannelCurrency(String id) {
		channelCurrencyDao.deleteChannelCurrency(id);
	}

	public void updateChannelCurrency(ChannelCurrency cc) {
		channelCurrencyDao.updateChannelCurrency(cc);
	}

	public void setChannelCurrencyDao(ChannelCurrencyDao channelCurrencyDao) {
		this.channelCurrencyDao = channelCurrencyDao;
	}
	@Override
	public List<ChannelCrrencyReviewed> queryChannelCurrencyReviewed(
			ChannelCrrencyReviewed cc, Page page) {
		return channelCurrencyDao.queryChannelCurrencyReviewed(cc,page);
	}
	@Override
	public void addChannelCurrencyReviewed(List<Map> list) {
		channelCurrencyDao.addChannelCurrencyReviewed(list);
	}
	@Override
	public void updateChannelCurrencyRev(Map<String, String> paraMap) {
		channelCurrencyDao.updateChannelCurrencyRev(paraMap);
	}
	@Override
	public void addChannelCurrency(Map map) {
		channelCurrencyDao.addChannelCurrency(map);
	}
	@Override
	public void updateChannelCurrency(Map map) {
		channelCurrencyDao.updateChannelCurrency(map);
	}

	public void processChannelCurrencyRev(Map params) throws Exception{
		int checkResult = channelCurrencyDao.checkSameSetting(params);
		if(checkResult / 10 > 0){
			throw new  Exception("已存在相同配置");
		}
		if(checkResult > 0){
			throw new  Exception("相同配置已提交审核");
		}
		List<Map> process = new ArrayList<Map>();
		if(StringUtil.isEmpty((String)params.get("id"))){
			params.put("flag","1");
			process.add(params);
			channelCurrencyDao.addChannelCurrencyReviewed(process);
		}else{
			params.put("flag","2");
			params.put("channelCurrencyId",params.get("id"));
			process.add(params);
			channelCurrencyDao.addChannelCurrencyReviewed(process);
		}
	}
}
