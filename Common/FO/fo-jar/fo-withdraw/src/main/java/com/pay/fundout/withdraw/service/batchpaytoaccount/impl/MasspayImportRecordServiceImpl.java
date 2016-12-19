/**
 *  File: MasspayImportRecordServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-26     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.batchpaytoaccount.impl;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dao.batchpaytoaccount.MasspayImportRecordDAO;
import com.pay.fundout.withdraw.dto.batchpaytoaccount.MasspayImportRecordDTO;
import com.pay.fundout.withdraw.model.batchpaytoaccount.MasspayImportRecord;
import com.pay.fundout.withdraw.service.batchpaytoaccount.MasspayImportRecordService;

/**
 * @author darv
 * 
 */
@SuppressWarnings("unchecked")
public class MasspayImportRecordServiceImpl implements MasspayImportRecordService {
	private MasspayImportRecordDAO masspayImportRecordDAO;

	public void setMasspayImportRecordDAO(MasspayImportRecordDAO masspayImportRecordDAO) {
		this.masspayImportRecordDAO = masspayImportRecordDAO;
	}

	@Override
	public long createMasspayImportRecord(MasspayImportRecord masspayImportRecord) {
		return this.masspayImportRecordDAO.createMasspayImportRecord(masspayImportRecord);
	}

	@Override
	public void batchCreateImporRecord(List list) {
		this.masspayImportRecordDAO.batchCreateImporRecord(list);
	}

	@Override
	public List getImportRecordListByFileKyAll(Map params) {
		return this.masspayImportRecordDAO.getImportRecordListByFileKyAll(params);
	}

	@Override
	public Integer getImportRecordListByFileKyCount(Map params) {
		return this.masspayImportRecordDAO.getImportRecordListByFileKyCount(params);
	}

	@Override
	public List getImportRecordListByFileKyPage(Map params) {
		return this.masspayImportRecordDAO.getImportRecordListByFileKyPage(params);
	}

	@Override
	public Long getErrorAmountByFileKy(Map params) {
		return this.masspayImportRecordDAO.getErrorAmountByFileKy(params);
	}

	@Override
	public int updateStatus(MasspayImportRecordDTO importRecord) {
		return masspayImportRecordDAO.updateStatus(importRecord);
	}
}
