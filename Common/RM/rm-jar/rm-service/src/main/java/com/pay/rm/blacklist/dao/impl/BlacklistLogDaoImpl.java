package com.pay.rm.blacklist.dao.impl;

import java.util.List;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.rm.blacklist.dao.BlacklistLogDAO;
import com.pay.rm.blacklist.dto.BlacklistLogDTO;

@SuppressWarnings("unchecked")
public class BlacklistLogDaoImpl extends BaseDAOImpl implements BlacklistLogDAO {

	@Override
	public Long addBlacklistLog(BlacklistLogDTO dto) {
		return (Long) super.create(namespace.concat("insertBlacklistLog"), dto);
	}

	@Override
	public List<BlacklistLogDTO> queryBlacklistLogList(Long hmdid) {
		return super.findByQuery(namespace.concat("queryBlacklistLog"), hmdid);
	}

	@Override
	public List<Long> insertBatchBlacklist(List<BlacklistLogDTO> list) {
		return super.batchCreate(namespace.concat("insertBlacklistLog"), list);
	}

}
