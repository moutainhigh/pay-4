package com.pay.dcc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.pay.dcc.dao.ConfigurationDCCDAO;
import com.pay.dcc.model.PartnerDCCConfig;
import com.pay.dcc.service.ConfigurationDCCService;
import com.pay.inf.dao.Page;

public class ConfigurationDCCServiceImpl implements ConfigurationDCCService {

	private ConfigurationDCCDAO configurationDCCDAO;

	public void setConfigurationDCCDAO(ConfigurationDCCDAO configurationDCCDAO) {
		this.configurationDCCDAO = configurationDCCDAO;
	}

	public List<PartnerDCCConfig> findDCCConfiguration(//查询dcc配置
			PartnerDCCConfig dccConfig,Page page) {
		List<PartnerDCCConfig> findDCCConfiguration = configurationDCCDAO
				.findDCCConfiguration(dccConfig,page);
		return findDCCConfiguration;
	}
	public List<PartnerDCCConfig> findDCCConfiguration(//查询dcc配置
			PartnerDCCConfig dccConfig) {
		List<PartnerDCCConfig> findDCCConfiguration = configurationDCCDAO
				.findDCCConfiguration(dccConfig);
		return findDCCConfiguration;
	}

	@Override
	public void saveDCCConfiguration(PartnerDCCConfig dccConfig,
			String[] currencyCodeMarkup) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		Set<Entry<String, List<String>>> entrySet = map.entrySet();
		for (int i = 0; i < currencyCodeMarkup.length; i++) {
			String markup = "";
			List<String> currencyCode = new ArrayList<String>();
			String[] split2 = currencyCodeMarkup[i].split(":");// usd 10
			for (int j = 0; j < split2.length; j++) {
				String reg = "[0-9]+(.[0-9]+)?";// 正则匹配数字 为markup
				if (split2[j].matches(reg)) {
					markup = split2[j];
				} else {
					currencyCode.add(split2[j]);
				}
			}
			if (!map.containsKey(markup)) {
				map.put(markup, currencyCode);
			} else {
				List<String> list = map.get(markup);
				list.addAll(currencyCode);
				map.put(markup, list);
			}
		}
		for (Entry<String, List<String>> entry : entrySet) {
			dccConfig.setMarkUp(entry.getKey());// markup
			List<String> value = entry.getValue();
			for (String string : value) {
				dccConfig.setCurrencyCode(string);// 对应多个币种
				configurationDCCDAO.saveDCCConfiguration(dccConfig);
			}
		}
	}

	@Override
	public void updateDCCConfiguration(PartnerDCCConfig dccConfig) {
		configurationDCCDAO.updateDCCConfiguration(dccConfig);
	}

	@Override
	public void bulkUpdateDCC(PartnerDCCConfig dccConfig, String[] split) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();// 定义一个map// key为markup// value为多种币种
		Set<Entry<String, List<String>>> entrySet = map.entrySet();// 获取一条数据	// markup :	// currencyCode
		for (int i = 0; i < split.length; i++) {// 遍历 currencyCodeMarkup
			String markup = "";
			List<String> currencyCode = new ArrayList<String>();
			String[] split2 = split[i].split(":");// 以: 切割分出 币种和 markup
			for (int j = 0; j < split2.length; j++) {
				String reg = "[0-9]+(.[0-9]+)?";// 正则匹配数字 为markup
				if (split2[j].matches(reg)) {
					markup = split2[j];
				} else {
					currencyCode.add(split2[j]);// 添加币种进集合
				}
			}
			if (!map.containsKey(markup)) {// 判断Key是否存在
				map.put(markup, currencyCode);// 不存在 直接添加 mark
												// List<currencyCode>
			} else {
				List<String> list = map.get(markup);// 存在 获取markup 的value
				list.addAll(currencyCode);// 添加币种
				map.put(markup, list);// 重新put
			}
		}
		for (Entry<String, List<String>> entry : entrySet) {
			dccConfig.setMarkUp(entry.getKey());// markup
			List<String> value = entry.getValue();
			for (String string : value) {
				dccConfig.setCurrencyCode(string);// 对应多个币种
				configurationDCCDAO.bulkUpdateDCC(dccConfig);
			}
		}
	}

}
