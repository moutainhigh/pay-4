package com.pay.fundout.withdraw.dao.balancewarning.impl;

import java.util.List;

import com.pay.fundout.withdraw.dao.balancewarning.BalanceWarningDAO;
import com.pay.fundout.withdraw.dto.balancewarning.OrgBalanceAlarmInfo;
import com.pay.fundout.withdraw.dto.balancewarning.OrgBalanceAlarmTask;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class BalanceWarningDAOImpl extends BaseDAOImpl implements
		BalanceWarningDAO {

	@Override
	public List<OrgBalanceAlarmInfo> queryOrgBalanceAlarmInfo() {
		return this.getSqlMapClientTemplate().queryForList(
				"orgBalanceAlarm.findBySelective", null);
	}

	@Override
	public void updateOrgBalanceAlarmInfo(OrgBalanceAlarmInfo dto) {
		this.getSqlMapClientTemplate().update(
				"orgBalanceAlarm.updateOrgBalanceAlarmInfo", dto);

	}

	@Override
	public Long createOrgBalanceAlarmInfo(OrgBalanceAlarmTask dto) {
		return (Long) this.create("orgBalanceAlarm.createOrgBalanceAlarmTask",
				dto);
	}

}
