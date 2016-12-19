package com.pay.fundout.withdraw.dao.autorisk.impl;

import java.util.List;

import com.pay.fundout.withdraw.dao.autorisk.CheckLogDAO;
import com.pay.fundout.withdraw.dto.autorisk.AutoRiskDto;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class CheckLogDAOImpl extends BaseDAOImpl<AutoRiskDto> implements
		CheckLogDAO {

	@Override
	public Long createCheckLog(AutoRiskDto dto) {
		return (Long) this.create("checklog.create", dto);
	}

	@Override
	public void updateCheckLog(AutoRiskDto dto) {
		this.getSqlMapClientTemplate().update("checklog.update", dto);
	}

	@Override
	public Page<AutoRiskDto> getCheckLogList(Page<AutoRiskDto> page,
			AutoRiskDto dto) {
		return this.findByQuery("checklog.getCheckLogList", page, dto);
	}

	@Override
	public List<AutoRiskDto> queryCheckLogList(AutoRiskDto dto) {
		return this.getSqlMapClientTemplate().queryForList(
				"checklog.getCheckLogList", dto);
	}

	@Override
	public AutoRiskDto findById(Long recordNo) {
		return (AutoRiskDto) this.getSqlMapClientTemplate().queryForObject(
				"checklog.findByRecordNo", recordNo);
	}

}
