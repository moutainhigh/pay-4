 /** @Description 
 * @project 	poss-reconcile
 * @file 		UploadWrapper.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-7-27		Henry.Zeng			Create 
*/
package com.pay.poss.refund.common.parser;

import java.util.List;

import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.refund.model.RefundImportRecord;
import com.pay.poss.refund.model.WebRefundUploadDTO;

/**
 * <p></p>
 * @author Henry.Zeng
 * @since 2010-7-27
 * @see 
 */
public interface ParserReconcile {
	
	public  List<RefundImportRecord> parserFile(final WebRefundUploadDTO webRefundUploadDTO) throws PossUntxException;
	
}