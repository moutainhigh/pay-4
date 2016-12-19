package com.pay.rm.blacklistcheck.dao.impl;


import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.rm.blacklistcheck.dao.BlackChecklistDAO;
import com.pay.rm.blacklistcheck.dto.BlackChecklistDTO;


public class BlackChecklistDAOImpl extends BaseDAOImpl<BlackChecklistDTO> implements BlackChecklistDAO{

	@Override
	public Long addBlackChecklist(BlackChecklistDTO dto) {
		return (Long) super.create("insert", dto);
	}

	@Override
	public Page<BlackChecklistDTO> queryBlacklistCheck(
			Page<BlackChecklistDTO> page, Map map) {
		return super	.findByQuery("queryBlacklistCheck", page, map);
	}

	@Override
	public boolean updateBlacklistCheckStatus(BlackChecklistDTO blackChecklist) {
		boolean update = super.update(blackChecklist);
		return update;
	}

	

}
