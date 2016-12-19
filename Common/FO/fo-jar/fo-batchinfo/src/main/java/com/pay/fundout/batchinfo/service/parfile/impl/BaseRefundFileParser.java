/**
 *  File: BaseRefundFileParser.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-10      zengli      Changes
 *  
 *
 */
package com.pay.fundout.batchinfo.service.parfile.impl;

import java.util.List;
import java.util.Map;

import com.pay.fundout.bankfile.parser.model.FileParserMode;
import com.pay.fundout.batchinfo.service.parfile.AbstractFileParser;
import com.pay.poss.base.exception.PossException;

/**
 * @author Jason_wang
 *
 */
public class BaseRefundFileParser extends AbstractFileParser{

	private static final int REFUND_IMPORT_FILE_STATUS_4 = 4;	//导入数据库成功
	private static final int REFUND_IMPORT_FILE_STATUS_3 = 3;	//解析失败
	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.service.AbstractFileParser#updateFileInfo(java.lang.String)
	 */
	@Override
	protected void updateFileInfo(FileParserMode fileParser) throws PossException {
		iBaseDao.update("bfrefund.updateRefundImportFileStatus",fileParser);
	}

	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.service.AbstractFileParser#saveFileInfo(java.util.Map, java.util.Map)
	 */
	@Override
	protected FileParserMode saveFileInfoRecord(Map<String,Object> resultList, Map<String, String> fileInfo) throws PossException {
		List<Object> detailList = (List<Object>)resultList.get("detailList");
		FileParserMode result = (FileParserMode)resultList.get("parserFileInfo");
		if(detailList.isEmpty()){
			logger.info("文件信息为空..." + fileInfo);
			result.setStatus(REFUND_IMPORT_FILE_STATUS_3);
		}else{
			iBaseDao.batchCreate("bfrefund.createRefundImportRecord",detailList);
			result.setStatus(REFUND_IMPORT_FILE_STATUS_4);
		}		
		return result;
	}	
}
