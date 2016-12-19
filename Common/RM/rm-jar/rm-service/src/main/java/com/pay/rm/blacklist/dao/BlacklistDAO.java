package com.pay.rm.blacklist.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.rm.blacklist.dto.BlacklistDTO;

public interface BlacklistDAO extends BaseDAO<BlacklistDTO> {

	public Page<BlacklistDTO> queryBlacklist2P(Page<BlacklistDTO> page,
			BlacklistDTO dto);

	public BlacklistDTO queryById(BlacklistDTO dto);

	public Page<BlacklistDTO> queryBlacklist2E(Page<BlacklistDTO> page,
			BlacklistDTO dto);

	public Long addBlacklist2P(BlacklistDTO dto);

	public Long addBlacklist2E(BlacklistDTO dto);

	public boolean updateBlacklist2P(BlacklistDTO dto);

	public boolean batchUpdateBlacklist2P(List<BlacklistDTO> list);

	public boolean batchUpdateBlacklist2E(List<BlacklistDTO> list);

	public boolean updateBlacklist2E(BlacklistDTO dto);

	public boolean deleteBlacklist(BlacklistDTO dto);

	public boolean auditBlacklist(BlacklistDTO dto);

	public Integer batchAuditBlacklist(List<BlacklistDTO> list);

	public List<BlacklistDTO> queryListByIds(BlacklistDTO dto);

}
