package com.pay.txncore.crosspay.service.impl;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.txncore.crosspay.dao.NoticeInfoDAO;
import com.pay.txncore.crosspay.model.NoticeInfo;
import com.pay.txncore.crosspay.model.NoticeInfoCriteria;
import com.pay.txncore.crosspay.service.NoticeInfoService;

public class NoticeInfoServiceImpl implements NoticeInfoService {

	private NoticeInfoDAO noticeInfoDao;

	public void setNoticeInfoDao(NoticeInfoDAO noticeInfoDao) {
		this.noticeInfoDao = noticeInfoDao;
	}

	@Override
	public NoticeInfo findById(Long id) {
		return (NoticeInfo) noticeInfoDao.findById(id);
	}

	@Override
	public long createNoticeInfo(NoticeInfo noticeInfo) {
		return (Long) noticeInfoDao.create(noticeInfo);
	}

	@Override
	public boolean updateNoticeInfo(NoticeInfo noticeInfo) {
		// return noticeInfoDao.update("updateByPrimaryKeySelective",
		// noticeInfo);
		return noticeInfoDao.update(noticeInfo);
	}

	@Override
	public boolean deleteNoticeInfo(Long id) {
		return noticeInfoDao.delete(id);
	}

	@Override
	public Page<NoticeInfo> queryNoticInfoForPage(
			NoticeInfoCriteria noticCriteria, Page<NoticeInfo> origPage) {
		// 转换成查询page对象
		List<NoticeInfo> resultList = noticeInfoDao.findByCriteria(
				noticCriteria, origPage);
		// 转换成页面对象
		origPage.setResult(resultList);
		return origPage;
	}

	public boolean updateStatus(NoticeInfo noticeInfo) {
		return noticeInfoDao.updateStatus(noticeInfo);
	}
}