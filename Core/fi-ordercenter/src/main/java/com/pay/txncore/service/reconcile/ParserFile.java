/** @Description 
 * @project 	poss-withdraw
 * @file 		FoParserFile.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-21		Henry.Zeng			Create 
 */
package com.pay.txncore.service.reconcile;

import java.io.InputStream;
import java.util.List;

import com.pay.rm.base.exception.PossUntxException;
import com.pay.txncore.model.ReconcileImportFile;
import com.pay.txncore.model.ReconcileImportRecord;

/**
 * <p>
 * 解析文件
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-9-21
 * @see
 */
public interface ParserFile {
	/**
	 * 文件解析
	 * 
	 * @param <T>
	 * @param orginalFile
	 * @param pojo
	 * @throws PossUntxException
	 */
	public List<ReconcileImportRecord> parserFile2List(InputStream inputStream,
			ReconcileImportFile importFile) throws PossUntxException;
}
