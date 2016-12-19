package com.pay.fundout.withdraw.service.balancewarning.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.withdraw.dao.balancewarning.BalanceWarningDAO;
import com.pay.fundout.withdraw.dto.balancewarning.OrgBalanceAlarmInfo;
import com.pay.fundout.withdraw.dto.balancewarning.OrgBalanceAlarmTask;
import com.pay.fundout.withdraw.service.balancewarning.BalanceWarningService;

/**
 * @author Administrator
 * @Description 机构余额预警服务
 */
public class BalanceWarningServiceImpl implements BalanceWarningService {

	protected transient Log log = LogFactory.getLog(getClass());

	private BalanceWarningDAO balanceWarningDAO;

	public void setBalanceWarningDAO(BalanceWarningDAO balanceWarningDAO) {
		this.balanceWarningDAO = balanceWarningDAO;
	}

	@Override
	public List<OrgBalanceAlarmInfo> queryOrgBalanceAlarmInfo() {
		return balanceWarningDAO.queryOrgBalanceAlarmInfo();
	}

	@Override
	public void updateOrgBalanceAlarmInfo(OrgBalanceAlarmInfo dto) {
		balanceWarningDAO.updateOrgBalanceAlarmInfo(dto);
	}

	@Override
	public Long createOrgBalanceAlarmInfo(OrgBalanceAlarmTask dto) {
		return balanceWarningDAO.createOrgBalanceAlarmInfo(dto);
	}

}
