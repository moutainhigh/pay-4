package com.pay.ma.unlock.dao;

import com.pay.inf.dao.BaseDAO;
import com.pay.ma.unlock.model.Member;

public interface UnLockDAO extends BaseDAO<Member>{
	/**
	 * 解冻
	 * @param unlockDateTime 解冻时间
	 * @param status 解冻状态
	 * @return
	 */
	public boolean updateMemberStatusWithDateTime(Integer status);

}
