package com.pay.app.dao.user.impl;

import com.pay.app.dao.user.SsoUserVistedDAO;
import com.pay.app.model.Ssouservisted;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author lei.jiangl
 * @version
 * @data 2010-8-20 下午05:21:58
 */
public class SsoUserVistedDAOImpl extends BaseDAOImpl implements
		SsoUserVistedDAO {

	@Override
	public int querySsoUserVistedByUserId(String userId) {
		int num = 0;
		Integer integerNum = (Integer) getSqlMapClientTemplate()
				.queryForObject(namespace.concat("querySsoUserVistedByUserId"),
						userId);
		if (integerNum != null) {
			num = integerNum;
		}
		return num;
	}

}
