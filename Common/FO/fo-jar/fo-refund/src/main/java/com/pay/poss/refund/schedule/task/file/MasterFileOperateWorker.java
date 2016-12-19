package com.pay.poss.refund.schedule.task.file;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.refund.common.util.PoiExcelUtil;
import com.pay.poss.refund.model.RefundBatchInfoDTO;

public class MasterFileOperateWorker extends BaseFileOperateWorker {

	private static Log logger = LogFactory
			.getLog(MasterFileOperateWorker.class);
	private RefundBatchInfoDTO masterInfo;

	public MasterFileOperateWorker(BaseDAO daoService,
			Map<String, String> fileInfo, RefundBatchInfoDTO masterInfo) {
		super(daoService, fileInfo);
		this.masterInfo = masterInfo;
	}

	public void run() {
		try {
			creatorMasterFile();
		} catch (Exception e) {
			logger.debug("生成批次概要失败" + e);
		}
	}

	public synchronized void creatorMasterFile() throws Exception {
		String filePath = PoiExcelUtil.processExcel(fileInfo, masterInfo);
		if ("TOTAL_BANK".equals(masterInfo.getBankCode()))
			fileInfo.put("filetype", "21");
		else
			fileInfo.put("filetype", "11");
		saveFileInfo(fileInfo, filePath, masterInfo);
	}
}
