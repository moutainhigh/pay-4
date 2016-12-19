/**
 *  File: FileProcessService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-13      Sunsea_Li      Changes
 *  
 *
 */
package com.pay.poss.refund.service.impl;

import java.io.File;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pay.fundout.batchinfo.service.parfile.FileParseService;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.base.common.fileprocess.dto.FileInfoDTO;
import com.pay.poss.base.common.properties.CommonConfiguration;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.refund.common.constant.RefundConstants;
import com.pay.poss.refund.common.fileprocess.utils.FileHandleUtil;
import com.pay.poss.refund.model.BatchFileInfo;
import com.pay.poss.refund.model.BatchInfo;
import com.pay.poss.refund.model.RefundImportFile;
import com.pay.poss.refund.model.RefundOrderM;
import com.pay.poss.refund.model.RefundReconcileResult;
import com.pay.poss.refund.model.ResultStatDTO;
import com.pay.poss.refund.model.WebQueryRefundDTO;
import com.pay.poss.refund.model.WebRefundUploadDTO;
import com.pay.poss.refund.schedule.StartTask;
import com.pay.poss.refund.service.FileProcessService;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;
import com.pay.util.JSonUtil;

/**
 * <p>
 * 充退批次文件相关操作服务具体实现类
 * </p>
 * 
 * @author Sunsea_Li
 * 
 */
public class FileProcessServiceImpl extends BaseServiceImpl implements
		FileProcessService {

	private Log log = LogFactory.getLog(getClass());

	private BaseDAO baseDao; // 处理数据库操作的基础DAO
	private String queueName; // 通过配置注入消息名
	private FileParseService fileParserService;
	private NotifyFacadeService notifyFacadeService;

	public void setNotifyFacadeService(NotifyFacadeService notifyFacadeService) {
		this.notifyFacadeService = notifyFacadeService;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public void setBaseDao(BaseDAO<Object> baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * @param fileParserService
	 *            the fileParserService to set
	 */
	public void setFileParserService(FileParseService fileParserService) {
		this.fileParserService = fileParserService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.poss.refund.service.FileProcessService#processUpload(com.pay.
	 * poss.refund.model.WebRefundUploadDTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> processUploadRdTx(
			WebRefundUploadDTO webRefundUploadDTO) throws PossException {
		Map<String, Object> map = new HashMap<String, Object>();
		CommonsMultipartFile orginalFile = webRefundUploadDTO.getOrginalFile();
		RefundImportFile refundImportFile = webRefundUploadDTO
				.getRefundImportFile();
		final String fileName = FileHandleUtil.getFileName(orginalFile);
		final FileInfoDTO fileInfo = new FileInfoDTO();
		fileInfo.setFileName(fileName);
		fileInfo.setFileSize(BigInteger.valueOf(orginalFile.getSize()));
		fileInfo.setFileType(RefundConstants.FILE_TYPE_UPLOAD);
		fileInfo.setFileFolder("refund");

		try {// 此处开始事务控制
				// 设置文件上传路径
			fileInfo.setFilePath(CommonConfiguration
					.getStrProperties("filePath"));
			// 1.上传文件到本地服务器
			// fileHandler.uploadFile(orginalFile.getInputStream(), fileInfo);
			boolean flag = com.pay.poss.base.common.fileprocess.io.FileHandler
					.getFileHandler().uploadFile(orginalFile.getInputStream(),
							fileInfo);
			if (!flag) {
				throw new PossUntxException("系统出现异常,导入文件失败!" + "",
						ExceptionCodeEnum.UN_KNOWN_EXCEPTION);
			}
			// 2.插入本地数据库表:REFUND_IMPORT_FILE
			refundImportFile.setFileName(fileName);
			refundImportFile.setStatus(new Integer(
					RefundConstants.UPLOAD_SUCCESS));
			refundImportFile.setUploadTime(new Date());
			refundImportFile.setOperators(SessionUserHolderUtil.getLoginId());
			refundImportFile.setErrorTips("no error");
			log.debug("refundImportFile  : " + refundImportFile);
			long fileKy = (Long) baseDao.create(
					RefundConstants.CREATE_REFUNDIMPORTFILE, refundImportFile);

			webRefundUploadDTO.getRefundImportFile()
					.setFileKy(new Long(fileKy));

			Map<String, String> paramsFileInfo = new HashMap<String, String>();
			String fullPath = fileInfo.getFilePath() + File.separator
					+ fileInfo.getFileFolder() + File.separator;
			paramsFileInfo.put("FILE_PATH", fullPath);
			paramsFileInfo.put("FILE_NAME", fileName);
			paramsFileInfo.put("BANK_CODE", refundImportFile.getBankCode());
			paramsFileInfo.put("BUSINESS_TYPE", "R_");
			paramsFileInfo.put("FILE_NO", "" + fileKy);
			paramsFileInfo.put("BATCH_NUM", refundImportFile.getBatchNum());

			// 解析文件,并保存信息
			Map<String, Object> result = fileParserService
					.fileParse(paramsFileInfo);
			Integer status = (Integer) result.get("parserStatus");
			if (0 != status.intValue()) {
				List<String> msgList = (List<String>) result.get("msgList");
				StringBuffer sb = new StringBuffer();
				if (null != msgList) {
					for (String string : msgList) {
						sb.append(string).append(";");
					}
				}
				throw new PossUntxException("详细信息：" + sb.toString(),
						ExceptionCodeEnum.UN_KNOWN_EXCEPTION);
			}

			/*
			 * //3 解析文件 ParserReconcile parserReconcile = new
			 * UnionPayPareseReconcileImpl();
			 * //ParserReconcileFactory.createParserReconcile
			 * ("refundUnionPay");//参数见表：params_file_parser
			 * List<RefundImportRecord> list =
			 * parserReconcile.parserFile(webRefundUploadDTO);
			 * 
			 * //4批量保存入库:插入本地数据库表:REFUND_IMPORT_RECORD
			 * baseDao.insertBatch(RefundConstants.CREATE_REFUNDIMPORTRECORD,
			 * list); log.debug("RefundImportRecord list  : "+list);
			 */

			// 5调用存储过程对账
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("p_batch_num",
					String.valueOf(refundImportFile.getBatchNum()));
			params.put("p_bank_code", refundImportFile.getBankCode());
			params.put("p_file_ky", refundImportFile.getFileKy().toString());
			params = (Map<String, Object>) baseDao.findObjectByCriteria(
					RefundConstants.CALL_RECONCILEPROC, params);
			log.info(params);
			refundImportFile.setStatus(new Integer(
					RefundConstants.RECONCILE_SUCCESS));

		} catch (PossUntxException e) {
			refundImportFile.setStatus(new Integer(
					RefundConstants.UPLOAD_FAILURE));
			refundImportFile.setErrorTips(getClass() + "上传文件失败"
					+ e.getMessage());
			e.printStackTrace();
			throw new PossException("上传文件失败：" + e.getMessage(), null);
		} catch (Exception e) {
			refundImportFile.setStatus(new Integer(
					RefundConstants.UPLOAD_FAILURE));
			refundImportFile.setErrorTips(getClass() + "上传文件失败"
					+ e.getMessage());
			e.printStackTrace();
			throw new PossException("上传文件失败：" + e.getMessage(), null);
		}
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.poss.refund.service.FileProcessService#queryResultStatistics(
	 * com.pay.poss.refund.model.WebQueryRefundDTO)
	 */
	@Override
	public Map<String, Object> queryResultStatistics(
			WebQueryRefundDTO webQueryRefundDTO) throws PossException {
		Map<String, Object> map = new HashMap<String, Object>();
		// webQueryRefundDTO.setBankCode("001");//????????????????????????????
		ResultStatDTO resultStatDTO = (ResultStatDTO) baseDao
				.findObjectByCriteria(RefundConstants.QUERY_RESULTSTATISTICS,
						webQueryRefundDTO);
		map.put("resultStatDTO", resultStatDTO);
		map.put("webQueryRefundDTO", webQueryRefundDTO);
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.poss.refund.service.FileProcessService#queryResultList(com.pay
	 * .inf.dao.Page, com.pay.poss.refund.model.WebQueryRefundDTO)
	 */
	@Override
	public Map<String, Object> queryResultList(
			Page<RefundReconcileResult> resultPage,
			WebQueryRefundDTO webQueryRefundDTO) throws PossException {
		Map<String, Object> map = new HashMap<String, Object>();
		// webQueryRefundDTO.setBankCode("M_BANK_1");//????????????????????????????
		resultPage = baseDao.findByQuery(
				RefundConstants.QUERY_REFUNDRECONCILERESULTLIST, resultPage,
				webQueryRefundDTO);
		map.put("page", resultPage);
		map.put("webQueryRefundDTO", webQueryRefundDTO);
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.poss.refund.service.FileProcessService#queryRefundImportFile(
	 * com.pay.inf.dao.Page, com.pay.poss.refund.model.WebQueryRefundDTO)
	 */
	@Override
	public Map<String, Object> queryRefundImportFile(
			Page<BatchFileInfo> resultPage, WebQueryRefundDTO webQueryRefundDTO)
			throws PossException {
		Map<String, Object> map = new HashMap<String, Object>();
		// webQueryRefundDTO.setBankCode("M_BANK_1");//????????????????????????????
		resultPage = baseDao.findByQuery(
				RefundConstants.QUERY_REFUNDIMPORTFILE, resultPage,
				webQueryRefundDTO);
		map.put("page", resultPage);
		map.put("webQueryRefundDTO", webQueryRefundDTO);
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.poss.refund.service.FileProcessService#queryBatchFileInfo(com
	 * .pay.inf.dao.Page, com.pay.poss.refund.model.WebQueryRefundDTO)
	 */
	@Override
	public Map<String, Object> queryBatchFileInfo(
			Page<BatchFileInfo> resultPage, WebQueryRefundDTO webQueryRefundDTO)
			throws PossException {
		Map<String, Object> map = new HashMap<String, Object>();
		// webQueryRefundDTO.setBankCode("001");//????????????????????????????
		webQueryRefundDTO.setBatchType(RefundConstants.BATCH_TYPE_REFUND);
		resultPage = baseDao.findByQuery(RefundConstants.QUERY_BATCHFILEINFO,
				resultPage, webQueryRefundDTO);
		map.put("page", resultPage);
		map.put("webQueryRefundDTO", webQueryRefundDTO);
		return map;
	}

	@Override
	public Map<String, Object> updateBatchFileInfo(BatchFileInfo batchFileInfo)
			throws PossException {
		// 1.修改batch_file_info表
		baseDao.update(RefundConstants.UPDATE_BATCHFILEINFO, batchFileInfo);
		// 2.修改batch_info表
		/*
		 * com.pay.poss.base.model.BatchInfo batchInfo = new
		 * com.pay.poss.base.model.BatchInfo();
		 * batchInfo.setBatchNum(batchFileInfo.getBatchNum());
		 * batchInfo.setStatus(7L);
		 * baseDao.update("schedule.fo-UpdateBatchInfo",batchInfo);
		 */
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.poss.refund.service.FileProcessService#dropImportedFile(com.pay
	 * .poss.refund.model.WebQueryRefundDTO)
	 */
	@Override
	public Map<String, Object> dropImportedFile(
			WebQueryRefundDTO webQueryRefundDTO) throws PossException {
		baseDao.delete(RefundConstants.DELETE_IMPORTEDFILE, webQueryRefundDTO);
		baseDao.delete(RefundConstants.DELETE_IMPORTEDRECORD, webQueryRefundDTO);
		baseDao.delete(RefundConstants.DELETE_IMPORTEDRESULT, webQueryRefundDTO);

		BatchFileInfo batchFileInfo = new BatchFileInfo();
		batchFileInfo.setFileKy(new Long(webQueryRefundDTO.getFileKy()));
		batchFileInfo.setBatchFileStatus(new Integer(
				RefundConstants.FILE_STATUS_DOWNLOADED));
		baseDao.update(RefundConstants.UPDATE_BATCHFILEINFO, batchFileInfo);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.poss.refund.service.FileProcessService#confirmImportedBatch(com
	 * .pay.poss.refund.model.WebQueryRefundDTO)
	 */
	@Override
	public Map<String, Object> confirmImportedBatch(
			WebQueryRefundDTO webQueryRefundDTO) throws PossException {
		// 1.组织处理成功或者失败更新余额记账接口参数,发送消息去处理记账
		List<RefundOrderM> mList = baseDao.findByQuery(
				RefundConstants.QUERY_NEEDACCOUNTINGLIST, webQueryRefundDTO);
		for (RefundOrderM mDto : mList) {
			mDto.setHandler(webQueryRefundDTO.getUserId());
			String jsonStr = JSonUtil.toJSonString(mDto);
			notifyFacadeService.sendRequest(buildNotify2QueueRequest(jsonStr,
					queueName));
		}

		// 3.批量修改文件状态为 已确认导入
		for (String fileKy : webQueryRefundDTO.getFileKys()) {
			BatchFileInfo batchFileInfo = new BatchFileInfo();
			batchFileInfo.setFileKy(new Long(fileKy));
			batchFileInfo.setBatchFileStatus(new Integer(
					RefundConstants.FILE_STATUS_IMPORT_CONFIRMED));
			batchFileInfo.setSureimportTime(new Date());
			baseDao.update(RefundConstants.UPDATE_BATCHFILEINFO, batchFileInfo);
		}
		return null;
	}

	/**
	 * 废除批次信息
	 * 
	 * @param batchNum
	 * @throws PossException
	 */
	@Override
	public void dropBatchInfoRdTx(Map<String, Object> outMap)
			throws PossException {
		String batchNum = (String) outMap.get("batchNum");
		Map<String, String> params = new HashMap<String, String>();
		params.put("batchNum", batchNum);

		Integer downloadedCount = (Integer) baseDao.findObjectByCriteria(
				"REFUND.queryDownloadedCount", batchNum);
		if (downloadedCount.intValue() == 0) {
			outMap.put("infos", "批次废除成功");
		} else {
			outMap.put("infos", "该批不能废除,批次状态异常");
			return;
		}

		try {
			// 更新批次信息为已废除
			baseDao.update("refund.batch.updateRefundBatchInfo", params);
			// 更新批次文件信息表为已废除
			baseDao.update("refund.batch.updateRefundBatchFileInfo", params);

			// 更新工单表状态为已废除
			baseDao.update("refund.batch.updateRefundWorkOrder", params);

		} catch (Exception e) {
			throw new PossException("废除批次错误 [batchNum=" + batchNum + "]",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION, e);
		}

		outMap.put("infos", "批次废除成功");
	}

	/**
	 * 获取节点上一个操作人
	 * 
	 * @param list
	 * @param nodeName
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getAssigner(List<Map<String, Object>> list, String nodeName) {
		String assigner = "".intern();
		if (null != list && !list.isEmpty()) {
			for (Map<String, Object> temp : list) {
				if (null != temp.get("nodeName")
						&& nodeName.equals(temp.get("nodeName").toString())) {
					assigner = (String) temp.get("assignee");
					break;
				}
			}
			return assigner;
		} else {
			return "";
		}
	}

	/**
	 * 重成批次信息
	 * 
	 * @param batchNum
	 * @throws PossException
	 */
	@Override
	public void reBuildBatchInfo(Map<String, Object> outMap)
			throws PossException {
		String oldBatchNum = (String) outMap.get("batchNum");
		BatchInfo info = (BatchInfo) baseDao.findObjectByCriteria(
				"refund.batch.queryBatchInfoByBatchNum", oldBatchNum);
		if (info == null || info.getStatus() != 3) {
			outMap.put("infos", "重成批次失败,该批次已重成");
			return;
		}
		try {
			// 调用重成批次接口
			String newBatchNum = StartTask.getInstance().reBuildBatch(
					oldBatchNum);
			if (newBatchNum == null) {
				outMap.put("infos", "重成批次失败");
			} else {
				outMap.put("batchNum", newBatchNum);
				outMap.put("infos", "重成批次成功");
			}
		} catch (Exception e) {
			outMap.put("infos", "重成批次失败");
		}
	}

	@Override
	public Map<String, Object> processSucess(WebQueryRefundDTO webQueryRefundDTO)
			throws PossException {
		// 1.更新对账结果表状态
		baseDao.update(RefundConstants.UPDATE_REFUNDRECONCILERESULT,
				webQueryRefundDTO);
		// 2.调用记账接口
		// 查询充退信息
		List<RefundOrderM> orderList = baseDao.findByQuery(
				RefundConstants.QUERY_NEEDACCOUNTINGINFO, webQueryRefundDTO);
		// 记账
		for (RefundOrderM mDto : orderList) {
			String jsonStr = JSonUtil.toJSonString(mDto);
			notifyFacadeService.sendRequest(buildNotify2QueueRequest(jsonStr,
					queueName));
		}
		return null;
	}
}
