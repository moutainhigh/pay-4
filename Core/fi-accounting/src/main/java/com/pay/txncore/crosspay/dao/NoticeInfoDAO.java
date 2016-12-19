package com.pay.txncore.crosspay.dao;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.crosspay.model.NoticeInfo;

public interface NoticeInfoDAO extends BaseDAO {

	/**
	 * 
	 * @param noticeInfo
	 * @return
	 */
	boolean updateStatus(NoticeInfo noticeInfo);
}