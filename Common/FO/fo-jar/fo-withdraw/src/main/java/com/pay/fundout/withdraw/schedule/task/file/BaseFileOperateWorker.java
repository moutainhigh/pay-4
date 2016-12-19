package com.pay.fundout.withdraw.schedule.task.file;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.withdraw.common.util.CreatorFileDirUtil;
import com.pay.fundout.withdraw.model.schedule.WithdrawMasterInfo;
import com.pay.inf.dao.BaseDAO;
import com.pay.poss.base.model.BatchFileInfo;
import com.pay.poss.base.model.BatchInfo;
import com.pay.util.StringUtil;

public abstract class BaseFileOperateWorker extends Thread {
	protected Log log = LogFactory.getLog(getClass());
	protected BaseDAO daoService;
	protected Map<String, String> fileInfo;

	public BaseFileOperateWorker(BaseDAO daoService,
			Map<String, String> fileInfo) {
		this.daoService = daoService;
		this.fileInfo = fileInfo;
	}

	protected void saveFileInfo(Map<String, String> fileInfo, String filePath,
			WithdrawMasterInfo masterInfo) {

		BatchInfo batchInfo = new BatchInfo();
		batchInfo.setBatchNum(fileInfo.get("BATCH_NUM")); // 批次号
		batchInfo.setStatus(new Long(4)); // 4.已出批次文件

		BatchFileInfo batchFileInfo = new BatchFileInfo();
		batchFileInfo.setBatchNum(fileInfo.get("BATCH_NUM"));// 批次号
		batchFileInfo.setFilePath(filePath);// 文件路径
		batchFileInfo.setFileName(CreatorFileDirUtil
				.getFileNameWithoutTime(filePath)); // 文件名

		String filetype = fileInfo.get("filetype");
		if (StringUtils.isNotEmpty(filetype))
			batchFileInfo.setFileType(new Long(filetype)); // 文件类型
		else
			batchFileInfo.setFileType(new Long(1)); // 文件类型

		batchFileInfo.setAllAmount(masterInfo.getTotalAmount().longValue());// 总金额
		batchFileInfo.setAllCount(masterInfo.getTotalCount());// 总笔数
		batchFileInfo.setBankCode(masterInfo.getBankCode());// 银行编码

		String userKy = "SYSTEM";
		if (!"".equalsIgnoreCase(StringUtil.null2String(fileInfo
				.get("BATCH_BUILD_AUTO")))) {
			userKy = "SYSTEM";
		} else {
			// TODO userKy = SessionUserHolderUtil.getLoginId();
		}
		batchFileInfo.setOperators(userKy);// 操作人标识
		batchFileInfo.setUpdateTime(new Date()); // 操作时间
		batchFileInfo.setBatchFileStatus(new Long(2));// 文件已生成
		batchFileInfo.setGenerateTime(new Date());// 生成时间

		daoService.create("schedule.insertBatchFileInfo", batchFileInfo);
		daoService.update("schedule.fo-UpdateBatchInfo", batchInfo);
	}

}
