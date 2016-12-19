 /** @Description 
 * @project 	fo-batchfileinfo
 * @file 		BaseWithdrawFileParser.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-9		zengli			Create 
*/
package com.pay.fundout.batchinfo.service.parfile.impl;

import java.util.List;
import java.util.Map;

import com.pay.fundout.bankfile.parser.model.FileParserMode;
import com.pay.fundout.batchinfo.service.parfile.AbstractFileParser;
import com.pay.poss.base.exception.PossException;

/**
 * <p></p>
 * @author Rick_lv
 * @since 2010-11-9
 * @see 
 */
public class BaseWithdrawFileParser  extends AbstractFileParser{

	private static final int REFUND_IMPORT_FILE_STATUS_4 = 4;	//导入数据库成功
	private static final int REFUND_IMPORT_FILE_STATUS_3 = 3; // 解析文件失败
	
	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.service.AbstractFileParser#saveFileInfo(java.util.Map, java.util.Map)
	 */
	@Override
	protected FileParserMode saveFileInfoRecord(Map<String, Object> resultList, Map<String, String> fileInfo) throws PossException {
		List<Object> detailList = (List<Object>)resultList.get("detailList");
		FileParserMode result = (FileParserMode)resultList.get("parserFileInfo");
		if(detailList.isEmpty()){
			logger.info("文件信息为空...");
			result.setStatus(REFUND_IMPORT_FILE_STATUS_3);
		}else{
			iBaseDao.batchCreate("bfwithdraw.fundout-withdraw-createImportRecord",detailList);
			result.setStatus(REFUND_IMPORT_FILE_STATUS_4);			
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.service.AbstractFileParser#updateFileInfo(com.pay.fundout.bankfile.model.FileParserMode)
	 */
	@Override
	protected void updateFileInfo(FileParserMode fileParser) throws PossException {
		iBaseDao.update("bfwithdraw.updateWithdrawImportFileStatus",fileParser);
	}

}
