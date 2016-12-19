/**
 *  File: MasspayImportFileServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-26     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.batchpaytoaccount.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.pay.fundout.withdraw.dao.batchpaytoaccount.MasspayImportFileDAO;
import com.pay.fundout.withdraw.dto.batchpaytoaccount.MasspayImportFileDTO;
import com.pay.fundout.withdraw.model.batchpaytoaccount.MasspayImportFile;
import com.pay.fundout.withdraw.service.batchpaytoaccount.MasspayImportFileService;
import com.pay.fundout.withdraw.service.batchpaytoaccount.MasspayImportRecordService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;

/**
 * @author darv
 * 
 */
@SuppressWarnings("unchecked")
public class MasspayImportFileServiceImpl implements MasspayImportFileService {
	private MasspayImportFileDAO masspayImportFileDAO;

	private MasspayImportRecordService masspayImportRecordService;



	public void setMasspayImportRecordService(MasspayImportRecordService masspayImportRecordService) {
		this.masspayImportRecordService = masspayImportRecordService;
	}

	public void setMasspayImportFileDAO(MasspayImportFileDAO masspayImportFileDAO) {
		this.masspayImportFileDAO = masspayImportFileDAO;
	}

	@Override
	public long createMasspayImportFile(MasspayImportFile masspayImportFile) {
		return this.masspayImportFileDAO.createMasspayImportFile(masspayImportFile);
	}

	@Override
	public Long getSeqId() {
		return this.masspayImportFileDAO.getSeqId();
	}

	@Override
	public Integer isExistForBatchNum(Map params) {
		return this.masspayImportFileDAO.isExistForBatchNum(params);
	}

	@Override
	public List getImportFileByOperatorsAll(Map params) {
		return this.masspayImportFileDAO.getImportFileByOperatorsAll(params);
	}

	@Override
	public Integer getImportFileByOperatorsCount(Map params) {
		return this.masspayImportFileDAO.getImportFileByOperatorsCount(params);
	}

	@Override
	public List getImportFileByOperatorsPage(Map params) {
		return this.masspayImportFileDAO.getImportFileByOperatorsPage(params);
	}

	@Override
	public Long getDayTotalAmount(Long memberCode) {
		Long val = this.masspayImportFileDAO.getDayTotalAmount(memberCode);
		return (val == null) ? 0 : val;
	}

	@Override
	public Long getMonthTotalAmount(Long memberCode) {
		Long val = this.masspayImportFileDAO.getMonthTotalAmount(memberCode);
		return (val == null) ? 0 : val;
	}

	@Override
	public Integer getMonthTotalCount(Long memberCode) {
		Integer val = this.masspayImportFileDAO.getMonthTotalCount(memberCode);
		return (val == null) ? 0 : val;
	}

	@Override
	public Integer getDayTotalCount(Long memberCode) {
		Integer val = this.masspayImportFileDAO.getDayTotalCount(memberCode);
		return (val == null) ? 0 : val;
	}

	@Override
	public void uploadAfterCreateRdTx(MasspayImportFile importFile, List resolveList) throws PossException {
		try {
			this.masspayImportFileDAO.createMasspayImportFile(importFile);
			this.masspayImportRecordService.batchCreateImporRecord(resolveList);
		} catch (Exception e) {
			LogUtil.error(getClass(), e.getMessage(), OPSTATUS.EXCEPTION, "batchNum:" + importFile.getBatchNum(), "", e
					.toString(), "", "");
			throw new PossException(e.getMessage(), ExceptionCodeEnum.UN_DEFINE_EXCEPTIONCODE, e);
		}
	}

	@Override
	public void confirmPayRdTx(MasspayImportFile importFile) throws PossException {
		try {
			masspayImportFileDAO.updateMasspayImportFile(importFile);
		} catch (Exception e) {
			LogUtil.error(getClass(), e.getMessage(), OPSTATUS.EXCEPTION, "uploadKy:" + importFile.getSequenceId(), "", e
					.toString(), "", "");
			String msg = e.getMessage();
			if (msg.contains("SQLIntegrityConstraintViolationException")) {
				msg = "unique";
			}
			throw new PossException(msg, ExceptionCodeEnum.UN_DEFINE_EXCEPTIONCODE, e);
		}
	}

	@Override
	public boolean updateMasspayImportFileRdTx(
			MasspayImportFileDTO dto) {
			MasspayImportFile model = new MasspayImportFile();
			BeanUtils.copyProperties(dto, model);
		return masspayImportFileDAO.updateMasspayImportFile(model);
		 
	}
}
