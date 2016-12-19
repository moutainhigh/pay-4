package com.pay.rm.blacklist.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.rm.blacklist.dto.BlacklistLogDTO;

public interface BlacklistLogDAO extends BaseDAO {

	public Long addBlacklistLog(BlacklistLogDTO dto);

	public List<BlacklistLogDTO> queryBlacklistLogList(Long hmdid);

	public List<Long> insertBatchBlacklist(List<BlacklistLogDTO> list);

}
