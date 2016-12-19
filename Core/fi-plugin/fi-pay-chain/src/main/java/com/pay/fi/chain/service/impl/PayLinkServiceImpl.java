/**
 * 
 */
package com.pay.fi.chain.service.impl;



import java.sql.Timestamp;
import java.util.List;

import com.pay.fi.chain.condition.paylink.PayLinkCondition;
import com.pay.fi.chain.dao.PayLinkDao;
import com.pay.fi.chain.model.PayLink;
import com.pay.fi.chain.model.PayLinkAttrib;
import com.pay.fi.chain.model.PayLinkBaseInfo;
import com.pay.fi.chain.service.PayLinkService;
import com.pay.inf.dao.Page;

/**
 * @author PengJiangbo
 *
 */
public class PayLinkServiceImpl implements PayLinkService {

	//注入
	private PayLinkDao<?> payLinkDao ;
	
	public void setPayLinkDao(final PayLinkDao<?> payLinkDao) {
		this.payLinkDao = payLinkDao;
	}
	
	@Override
	public Long baseInfoSave(final PayLinkBaseInfo payLinkBaseInfo) {
		return this.payLinkDao.baseInfoSave(payLinkBaseInfo) ;
	}

	@Override
	public PayLinkBaseInfo payLinkBaseInfoQuery(final Long memberCode) {
		return this.payLinkDao.payLinkBaseInfoQuery(memberCode) ;
	}

	@Override
	public int updateContact(final Long infoId, final Timestamp updateDate, final String contact) {
		return this.payLinkDao.updateContact(infoId, updateDate, contact) ;
	}

	@Override
	public Long shoptermSave(final PayLinkBaseInfo payLinkBaseInfo) {
		return this.payLinkDao.shoptermSave(payLinkBaseInfo) ;
	}

	@Override
	public int updateShopTermById(final Long infoId, final Timestamp updateTime,
			final String shoptermName, final String shoptermUrl) {
		return this.payLinkDao.updateShopTermById(infoId, updateTime, shoptermName, shoptermUrl) ;
	}

	@Override
	public Long payLinkSave(final PayLink payLink) {
		return this.payLinkDao.payLinkSave(payLink) ;
	}

	@Override
	public Long payLinkAttribSave(final PayLinkAttrib payLinkAttrib) {
		return this.payLinkDao.payLinkAttribSave(payLinkAttrib) ;
	}

	@Override
	public boolean payLinkAttribBatchSave(final List<PayLinkAttrib> payLinkAttribs) {
		return this.payLinkDao.payLinkAttribBatchSave(payLinkAttribs) ;
	}

	@Override
	public List<PayLink> queryPayLinkByCondition(
			final PayLinkCondition payLinkCondition, final Page<?> page) {
		return this.payLinkDao.queryPayLinkByCondition(payLinkCondition, page) ;
	}

	@Override
	public int updateStatusByNo(final Long payLinkNo, final int status, final Timestamp invalidTime) {
		return this.payLinkDao.updateStatusByNo(payLinkNo, status, invalidTime) ;
	}

	@Override
	public List<PayLink> queryPayLinkByCondition(
			final PayLinkCondition payLinkCondition) {
		return this.payLinkDao.queryPayLinkByCondition(payLinkCondition) ;
	}

	@Override
	public PayLink findPayLinkByPayLinkNo(final Long payLinkNo) {
		return this.payLinkDao.findPayLinkByPayLinkNo(payLinkNo) ;
	}

	@Override
	public List<PayLinkAttrib> findPayLinkAttribsByPayLinkNo(final Long payLinkNo) {
		return this.payLinkDao.findPayLinkAttribsByPayLinkNo(payLinkNo) ;
	}

	@Override
	public PayLink findPayLinkByName(final String payLinkName) {
		return this.payLinkDao.findPayLinkByName(payLinkName) ;
	}

}
