package com.pay.rm.blacklistcheck.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.rm.blacklistcheck.dto.BlackChecklistDTO;

public interface BlacklistCheckService {
	public Long addBlackchecklist(BlackChecklistDTO dto);

	public Page<BlackChecklistDTO> queryBlacklistCheck(Map map,
			Page<BlackChecklistDTO> page);

	public boolean updateBlacklistCheckStatus(BlackChecklistDTO blackCheck, List<BlackChecklistDTO> result,String operator);

}
