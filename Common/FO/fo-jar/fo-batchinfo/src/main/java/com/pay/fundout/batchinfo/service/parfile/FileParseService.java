package com.pay.fundout.batchinfo.service.parfile;

import java.util.Map;

import com.pay.poss.base.exception.PossException;

public interface FileParseService {
	
	/**
	 * 文件解析
	 * @param Map<String, String> fileInfo[
	 * key:FILE_PATH 解析文件的存放路径
	 * key:FILE_NAME 解析文件名称
	 * key:BANK_CODE 解析文件所属银行
	 * key:BUSINESS_TYPE 业务类型
	 * key:FILE_NO 解析文件编号
	 * key:BATCH_NUM 批次号
	 * ]
	 * 
	 * @return Map<String,Object>[
	 * key:parserStatus & value:String 解析结果状态 0:解析成功
	 * key:resultCount & value:Long 解析结果统计条数
	 * key:msgList & value: List<String> 解析错误信息集合
	 * ]
	 * @throws PossException
	 */
	Map<String,Object> fileParse(Map<String, String> fileInfo) throws PossException;
}
