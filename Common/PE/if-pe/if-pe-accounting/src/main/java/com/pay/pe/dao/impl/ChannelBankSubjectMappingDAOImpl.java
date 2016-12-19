package com.pay.pe.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.pay.pe.dao.ChannelBankSubjectMappingDAO;
import com.pay.pe.model.ChannelBankSubjectMapping;

public class ChannelBankSubjectMappingDAOImpl extends SqlMapClientDaoSupport
		implements ChannelBankSubjectMappingDAO {

	@Override
	/**
	 * 根据类型查找对应的渠道记账科目映射集合
	 * 
	 * @param type 1表示出款，2表示充退
	 */
	public Map<String, String> queryChannelBankSubjectByType(int type) {
		List<ChannelBankSubjectMapping> list = getSqlMapClientTemplate()
				.queryForList(
						"CHANNEL_BANK_SUBJECT_MAPPING.queryChannelBankSubjectByType",
						type);
		Map<String, String> _map = new HashMap<String, String>();
		if (list != null && list.size() > 0) {
			for (ChannelBankSubjectMapping channelMap : list) {
				_map.put(channelMap.getChannelCode(), channelMap.getSubject());
			}
		}
		return _map;
	}
}
