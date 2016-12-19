package com.pay.fundout.withdraw.dao.balancewarning;

import java.util.List;

import com.pay.fundout.withdraw.dto.balancewarning.OrgBalanceAlarmInfo;
import com.pay.fundout.withdraw.dto.balancewarning.OrgBalanceAlarmTask;
import com.pay.inf.dao.BaseDAO;

public interface BalanceWarningDAO extends BaseDAO {

	/**
	 * 查询机构余额预警信息
	 * 
	 * @param memberCode
	 * @return
	 */
	List<OrgBalanceAlarmInfo> queryOrgBalanceAlarmInfo();

	/**
	 * 更新机构余额预警信息
	 * 
	 * @param dto
	 */
	void updateOrgBalanceAlarmInfo(OrgBalanceAlarmInfo dto);

	/**
	 * 记录机构余额预警任务
	 * 
	 * @param dto
	 * @return
	 */
	Long createOrgBalanceAlarmInfo(OrgBalanceAlarmTask dto);
}
