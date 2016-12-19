package com.pay.txncore.crosspay.dao.impl;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.crosspay.dao.NoticeInfoDAO;
import com.pay.txncore.crosspay.model.NoticeInfo;

@SuppressWarnings("unchecked")
public class NoticeInfoDAOImpl extends BaseDAOImpl implements NoticeInfoDAO {

	@Override
	public boolean updateStatus(NoticeInfo noticeInfo) {
		return super.getSqlMapClientTemplate().update("updateStatus",
				noticeInfo) == 1;
	}

}
