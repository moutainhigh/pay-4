/**
 *  File: ReconcileFileServiceImpl.java
 *  Description:对账文件管理实现
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-17    jason_wang      Changes
 *  
 *
 */
package com.pay.txncore.service.reconcile.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pay.inf.dao.Page;
import com.pay.inf.dto.PageMsgDto;
import com.pay.inf.service.ValidateMessageService;
import com.pay.rm.base.exception.PossUntxException;
import com.pay.txncore.dao.QueryReconcileFileDAO;
import com.pay.txncore.dao.QueryReconcileRecordDAO;
import com.pay.txncore.model.ReconcileImportFile;
import com.pay.txncore.model.ReconcileImportRecord;
import com.pay.txncore.model.WebQueryFile;
import com.pay.txncore.service.reconcile.ReconcileFileService;
import com.pay.txncore.service.reconcile.common.ReconcileConstants;
import com.pay.util.StringUtil;

public class ReconcileFileServiceImpl implements ReconcileFileService {

	private QueryReconcileFileDAO reconcileFileDAO; // 对账文件数据操作

	private QueryReconcileRecordDAO reconcileRecordDAO; // 对账文件明细数据操作

	private ValidateMessageService validateMessageService; // 校验信息

	private final Log log = LogFactory.getLog(ReconcileFileServiceImpl.class);

	public void setValidateMessageService(
			final ValidateMessageService validateMessageService) {
		this.validateMessageService = validateMessageService;
	}

	@Override
	public Map<String, Object> reconcileFileUpload(Map<String, Object> params) {
		String orgCode = (String) params.get("orgCode");
		String startDate = (String) params.get("startDate");
		CommonsMultipartFile fileInfo = (CommonsMultipartFile) params
				.get("fileInfo");
		String user = (String) params.get("user");
		Map<String, Object> result = new HashMap<String, Object>();

		ReconcileImportFile importFile = new ReconcileImportFile();

		Map<String, PageMsgDto> msgMap = validateMessageService
				.getPageMsgByPagecodeAndScenarioId("foparserfile",
						"parserError");

		PageMsgDto PageMsgDto;
		// 校验银行科目、业务日期
		if (StringUtil.isEmpty(orgCode) || StringUtil.isEmpty(startDate)) {
			PageMsgDto = msgMap.get("reconcileFileUploadParamNull");
			result.put("validDataInfo", PageMsgDto.getMsg());
			return result;
		}
		// importFile.setBusiDate(DateUtil.strToDate(startDate, "yyyy-MM-dd"));
		// importFile.setWithdrawBankId(orgCode);
		importFile.setFileName(fileInfo.getOriginalFilename());
		importFile.setOperator(user);
		importFile.setStatus(new Long(1));

		boolean flag = reconcileFileDAO.reconcileFileNameExist(importFile);

		// 如果出款银行和业务日期对应已经有文件存在,则不能在上传
		if (flag) {
			// PageMsgDto = msgMap.get("busiReconcileFileExist");
			// result.put("validDataInfo",PageMsgDto.getMessage());
			result.put("validDataInfo", "出款银行和业务日期对应已经有文件存在,不能再上传!");
			return result;
		}

		try {
			// 解析文件内容
			//result = parseFileHandle.parserFile(fileInfo, importFile, msgMap);
		} catch (PossUntxException e) {
			log.error("解析文件出现异常:", e);
		}

		return result;
	}

	@Override
	public Map<String, Object> queryReconcileUploadFile(
			Page<ReconcileImportFile> page, WebQueryFile webQueryFile) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Page<ReconcileImportFile> resultPage = reconcileFileDAO.query(page,
					webQueryFile);
			model.put("page", resultPage);
			model.put("webQueryFile", webQueryFile);
		} catch (Exception e) {
			log.error("查询银行对账文件信息出现异常!", e);
		}
		return model;
	}

	@Override
	public InputStream downloadReconcileFile(Map<String, Object> params) {
		String fileName = (String) params.get("fileName");
		String uploadDate = (String) params.get("uploadDate");
		String filePath = File.separator + uploadDate
				//+ CommonConfiguration.getStrProperties("filePath")
				+ File.separator + fileName;

		try {
			log.info("下载文件路径:" + filePath);
			FileInputStream fis = new FileInputStream(filePath);
			return fis;
		} catch (FileNotFoundException e) {
			log.error("文件读取失败!", e);
			return null;
		}
	}

	@Override
	public Map<String, Object> queryReconcileFileDetailInfo(
			Page<ReconcileImportRecord> page, Map<String, Object> params) {

		Page<ReconcileImportRecord> resultPage = reconcileRecordDAO.query(page,
				params);
		params.put("page", resultPage);
		return params;
	}

	@Override
	public Map<String, Object> revokeReconcileFile(Map<String, Object> params) {
		String fileId = (String) params.get("fileId");

		if (StringUtil.isEmpty(fileId)) {
			params.put("resultMsg", "paramNull");
			return params;
		}

		ReconcileImportFile importFile = new ReconcileImportFile();
		importFile.setStatus(new Long(
				ReconcileConstants.RECONCILE_FILE_STATUS_9));
		importFile.setFileId(new Long(fileId));
		// 更新对账文件信息
		reconcileFileDAO.updateImportFile(importFile);

		// 删除对账明细信息
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fileId", importFile.getFileId());
		reconcileRecordDAO.deleteReconcileRecordInfo(paramMap);

		// 删除对账结果信息
		reconcileRecordDAO.deleteReconcileResultInfo(paramMap);

		params.put("resultMsg", "success");
		return params;
	}

	@Override
	public Map<String, Object> batchReconcileInfos(
			Page<ReconcileImportFile> page, WebQueryFile webQueryFile) {
		String[] tempFileIds = webQueryFile.getFileIds().split(
				ReconcileConstants.PARAMETER_SEPARATOR_1);
		Map<String, Object> params = null;
		String[] tempFileNo = null;
		// 组装参数
		for (String fileIds : tempFileIds) {
			tempFileNo = fileIds
					.split(ReconcileConstants.PARAMETER_SEPARATOR_2);
			if (null != tempFileNo && 3 < tempFileNo.length) {
				// 调用存储过程对账储
				params = new HashMap<String, Object>();
				params.put("fileId", tempFileNo[0]);
				params.put("startTime", tempFileNo[1]
						+ ReconcileConstants.START_TIME);
				params.put("endTime", tempFileNo[1]
						+ ReconcileConstants.END_TIME);
				params.put("busiType", ReconcileConstants.WITHDRAW_BUSI_TYPE);
				params.put("orgCode", tempFileNo[2]);
				params.put("busiDate", tempFileNo[1]);
				params.put("resVal", "");
				reconcileFileDAO.executeReconcileInfo(params);
			}
		}
		// 查询结果
		return queryReconcileUploadFile(page, webQueryFile);
	}

	@Override
	public Map<String, Object> singleReconcileInfo(Map<String, Object> params) {
		// 调用存储过程对账
		reconcileFileDAO.executeReconcileInfo(params);
		String result = (String) params.get("resVal");
		if (null != result
				&& ReconcileConstants.RECONCILE_PROCEDURE_STAUTS_SUCCESS
						.equals(result)) {
			params.put("resultMsg", "success");
		} else {
			params.put("resultMsg", "failed");
		}
		return params;
	}

	public void setReconcileFileDAO(QueryReconcileFileDAO reconcileFileDAO) {
		this.reconcileFileDAO = reconcileFileDAO;
	}

	public void setReconcileRecordDAO(QueryReconcileRecordDAO reconcileRecordDAO) {
		this.reconcileRecordDAO = reconcileRecordDAO;
	}

}
