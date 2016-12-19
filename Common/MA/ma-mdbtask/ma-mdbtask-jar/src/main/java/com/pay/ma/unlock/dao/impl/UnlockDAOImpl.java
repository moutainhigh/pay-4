/**
 * 
 */
package com.pay.ma.unlock.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.ma.unlock.dao.UnLockDAO;
import com.pay.ma.unlock.model.Member;

/**
 * @author Administrator
 * 
 */
public class UnlockDAOImpl extends BaseDAOImpl<Member> implements UnLockDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.ma.unlock.dao.UnLockDAO#unlockMemberWithDateTime(java.util
	 * .Date, java.lang.Integer)
	 */
	@Override
	public boolean updateMemberStatusWithDateTime(Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		this.getSqlMapClientTemplate().update(this.namespace.concat("updateMemberStatusWithDateTime"), map);
		return true;
	}

}
