package com.pay.poss.refund.schedule.task.file;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.refund.common.util.CreatorFileDirUtil;
import com.pay.poss.refund.model.BatchFileInfo;
import com.pay.poss.refund.model.BatchInfo;
import com.pay.poss.refund.model.RefundBatchInfoDTO;
import com.pay.poss.security.util.SessionUserHolderUtil;

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
			RefundBatchInfoDTO masterInfo) {

		BatchInfo batchInfo = new BatchInfo();
		batchInfo.setBatchNum(fileInfo.get("BATCH_NUM")); // 批次号
		batchInfo.setStatus(4); // 4.已出批次文件

		BatchFileInfo batchFileInfo = new BatchFileInfo();
		batchFileInfo.setBatchNum(fileInfo.get("BATCH_NUM"));// 批次号
		batchFileInfo.setFilePath(filePath);// 文件路径
		batchFileInfo.setFileName(CreatorFileDirUtil
				.getFileNameWithoutTime(filePath)); // 文件名

		String filetype = fileInfo.get("filetype");
		if (StringUtils.isNotEmpty(filetype))
			batchFileInfo.setFileType(new Integer(filetype)); // 文件类型
		else
			batchFileInfo.setFileType(new Integer(1)); // 文件类型

		batchFileInfo.setAllAmount(masterInfo.getTotalAmount());// 总金额
		batchFileInfo.setAllCount(masterInfo.getTotalCount());// 总笔数
		batchFileInfo.setBankCode(masterInfo.getBankCode());// 银行编码

		// 操作人 当前登录用户
		String userKy = SessionUserHolderUtil.getLoginId();

		batchFileInfo.setOperators(userKy);// 操作人标识
		batchFileInfo.setUpdateTime(new Date()); // 操作时间
		batchFileInfo.setBatchFileStatus(2);// 文件已生成

		batchFileInfo.setUpdateTime(Calendar.getInstance().getTime()); // 操作时间
		batchFileInfo.setGenerateTime(Calendar.getInstance().getTime());// 生成时间

		daoService.update("refund.batch.updateBatchInfo", batchInfo);
		daoService.create("refund.batch.insertBatchFileInfo", batchFileInfo);

	}

}
