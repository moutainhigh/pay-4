package com.pay.fundout.batchinfo.service.genbatch;

import java.util.Map;

import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.model.Task;

public interface BatchFileGenerateService {
	
	/**com.pay.fundout.batchinfo.service.genbatch.BatchFileGenerateService
	 * 生成批次文件
	 * @param fileInfo[
	 * key:BATCH_FILE_PATH  批次文件存储路径
	 * key:BATCH_FILENAME_SUBFIX 批次文件后缀名
	 * key:BATCH_NUM 批次号
	 * key:BATCH_TASK_TYPE 批次任务类型
	 * ]
	 * @throws PossException
	 */
	public void generateBatchFile(Map<String, String> fileInfo) throws PossException;


	//public void createFileRecord(Task task);


	
}
