package com.pay.rm.blacklist.dao.impl;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.rm.blacklist.dao.BlacklistDAO;
import com.pay.rm.blacklist.dto.BlacklistDTO;

@SuppressWarnings("unchecked")
public class BlacklistDaoImpl extends BaseDAOImpl<BlacklistDTO> implements
		BlacklistDAO {

	@Override
	public Page<BlacklistDTO> queryBlacklist2P(Page<BlacklistDTO> page,
			BlacklistDTO dto) {
		return super
				.findByQuery(namespace.concat("queryBlacklist2"), page, dto);
	}

	@Override
	public Page<BlacklistDTO> queryBlacklist2E(Page<BlacklistDTO> page,
			BlacklistDTO dto) {
		return super
				.findByQuery(namespace.concat("queryBlacklist2"), page, dto);
	}

	@Override
	public Long addBlacklist2P(BlacklistDTO dto) {
		return (Long) super.create(namespace.concat("insert"), dto);
	}

	@Override
	public Long addBlacklist2E(BlacklistDTO dto) {
		return (Long) super.create(namespace.concat("insert"), dto);
	}

	@Override
	public boolean updateBlacklist2P(BlacklistDTO dto) {
		return super.update(namespace.concat("updateBlacklist"), dto);
	}

	public boolean batchUpdateBlacklist2P(List<BlacklistDTO> list) {
		return super.updateBatch(namespace.concat("updateBlacklist"), list) >= 1;
	}

	@Override
	public boolean updateBlacklist2E(BlacklistDTO dto) {
		return super.update(namespace.concat("updateBlacklist"), dto);
	}

	public boolean batchUpdateBlacklist2E(List<BlacklistDTO> list) {
		return super.updateBatch(namespace.concat("updateBlacklist"), list) >= 1;
	}

	@Override
	public boolean deleteBlacklist(BlacklistDTO dto) {
		super.delete(namespace.concat("deleteBlacklist"), dto);
		return true;
	}

	@Override
	public boolean auditBlacklist(BlacklistDTO dto) {
		return super.update(namespace.concat("updateBlacklist"), dto);
	}

	@Override
	public BlacklistDTO queryById(BlacklistDTO dto) {
		return super.findObjectByCriteria(namespace.concat("queryById"), dto);
	}

	@Override
	public Integer batchAuditBlacklist(List<BlacklistDTO> list) {
		return super.updateBatch(namespace.concat("updateBlacklist"), list);
	}

	@Override
	public List<BlacklistDTO> queryListByIds(BlacklistDTO dto) {
		return super.findByQuery(namespace.concat("queryListByIds"), dto);
	}

}
