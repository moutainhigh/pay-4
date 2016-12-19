package com.pay.rm.blacklist.service;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.rm.blacklist.dto.BlacklistDTO;
import com.pay.rm.blacklist.dto.BlacklistLogDTO;

public interface BlacklistService {

	public Page<BlacklistDTO> queryBlacklist2P(Page<BlacklistDTO> page,
			BlacklistDTO dto);

	public BlacklistDTO queryById(BlacklistDTO dto);

	public Page<BlacklistDTO> queryBlacklist2E(Page<BlacklistDTO> page,
			BlacklistDTO dto);

	public Long addBlacklist2P(BlacklistDTO dto);

	public Long addBlacklist2E(BlacklistDTO dto);

	public boolean updateBlacklist2P(BlacklistDTO dto);

	public String batchUploadBlacklist2E(BlacklistDTO dto) throws Exception;

	public String batchUploadBlacklist2P(BlacklistDTO dto) throws Exception;

	public boolean updateBlacklist2E(BlacklistDTO dto);

	public boolean deleteBlacklist(BlacklistDTO dto);

	public boolean auditBlacklist(BlacklistDTO dto);

	public boolean batchAuditBlacklist(BlacklistDTO dto) throws Exception;

	public String uploadBlacklist2P(BlacklistDTO dto) throws Exception;

	public String uploadBlacklist2E(BlacklistDTO dto) throws Exception;

	public Long addBlacklistLog(BlacklistLogDTO dto);

	public List<BlacklistLogDTO> queryBlacklistLogList(Long hmdid);

	public List<Long> insertBatchBlacklist(List<BlacklistLogDTO> list);

}
