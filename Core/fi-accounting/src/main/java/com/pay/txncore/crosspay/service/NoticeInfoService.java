package com.pay.txncore.crosspay.service;

import com.pay.inf.dao.Page;
import com.pay.txncore.crosspay.model.NoticeInfo;
import com.pay.txncore.crosspay.model.NoticeInfoCriteria;

public interface NoticeInfoService {

	Page<NoticeInfo> queryNoticInfoForPage(NoticeInfoCriteria noticCriteria,
			Page<NoticeInfo> page);

	NoticeInfo findById(Long id);

	long createNoticeInfo(NoticeInfo noticeInfo);

	boolean updateNoticeInfo(NoticeInfo noticeInfo);

	boolean deleteNoticeInfo(Long id);

	boolean updateStatus(NoticeInfo noticeInfo);

}