package com.pay.fundout.withdraw.schedule.task.file;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.pay.fundout.withdraw.common.util.CreatorFileDirUtil;
import com.pay.fundout.withdraw.model.schedule.WithdrawDetailInfo;
import com.pay.fundout.withdraw.model.schedule.WithdrawMasterInfo;
import com.pay.inf.dao.BaseDAO;
import com.pay.poss.base.common.poi.OperatorPoiInterface;


public class DetailFileOperateWorker extends BaseFileOperateWorker {
	
	public DetailFileOperateWorker(BaseDAO daoService,Map<String, String> fileInfo) {
		super(daoService, fileInfo);
	}

	private static Log logger = LogFactory.getLog(DetailFileOperateWorker.class);
	private OperatorPoiInterface detailFileHandler;
	private WithdrawMasterInfo masterInfo;
	private List<WithdrawDetailInfo> withdrawList;

	public DetailFileOperateWorker(BaseDAO daoService,Map<String, String> fileInfo,List<WithdrawDetailInfo> withdrawList,WithdrawMasterInfo masterInfo,
			OperatorPoiInterface detailFileHandler) {
		super(daoService, fileInfo);
		this.detailFileHandler = detailFileHandler;
		this.masterInfo = masterInfo;
		this.withdrawList = withdrawList;
	}

	public void run() {
		try {
			fileOperate();
		} catch (Exception e) {
			logger.debug("生成批次详细失败" + e);
		}
	}

	/**
	 * 产生文件
	 * @throws Exception 
	 */
	public synchronized void fileOperate() throws Exception {
		creatorFileTxt();
		creatorFileExcel();
	}

	/**
	 * 银行文件详细
	 * 
	 * @param refundBatchList
	 */
	private synchronized void creatorFileTxt() {
		String filePath = CreatorFileDirUtil.writeFile(withdrawList,fileInfo);
		fileInfo.put("filetype", "22");
		saveFileInfo(fileInfo, filePath, masterInfo);
	}

	/**
	 * 内部文件详细
	 * 
	 * @param refundAcceptDs
	 * @throws Exception 
	 * @throws Exception
	 */
	public synchronized void creatorFileExcel() throws Exception {
		HSSFWorkbook workbook = detailFileHandler.buildExcel(withdrawList);
		String filePath = CreatorFileDirUtil.writeFile(workbook, fileInfo);
		fileInfo.put("filetype", "12");
		saveFileInfo(fileInfo, filePath, masterInfo);
	}
}
