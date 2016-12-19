package com.pay.fundout.withdraw.schedule.task.file;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.withdraw.common.util.PoiExcelUtil;
import com.pay.fundout.withdraw.model.schedule.WithdrawMasterInfo;
import com.pay.inf.dao.BaseDAO;

public class MasterFileOperateWorker extends BaseFileOperateWorker {

	private static Log logger = LogFactory
			.getLog(MasterFileOperateWorker.class);
	private WithdrawMasterInfo masterInfo;

	public MasterFileOperateWorker(BaseDAO daoService,
			Map<String, String> fileInfo, WithdrawMasterInfo masterInfo) {
		super(daoService, fileInfo);
		this.masterInfo = masterInfo;
	}

	public void run() {
		try {
			creatorMasterFile();
		} catch (Exception e) {
			logger.debug("生成内部文件概要失败" + e);
		}
	}

	public synchronized void creatorMasterFile() throws Exception {
		String filePath = PoiExcelUtil.processExcel(fileInfo, masterInfo);
		if ("TOTAL_BANK".equals(masterInfo.getBankCode()))
			fileInfo.put("filetype", "11");
		else
			fileInfo.put("filetype", "21");
		saveFileInfo(fileInfo, filePath, masterInfo);
	}
}
