/**
 *  File: RefundAutoBatchFileImpl.java
 *  Description:
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-10-20   Sandy_Yang  create
 *  
 *
 */
package com.pay.poss.refund.schedule.autobatch;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.poss.base.common.Constants;
import com.pay.poss.refund.schedule.RefundAutoBatchFile;
import com.pay.poss.refund.schedule.StartTask;

/**
 * refund自动产生批次文件
 * @author Sandy_Yang
 */
public class RefundAutoBatchFileImpl implements RefundAutoBatchFile {
	
	private Log log = LogFactory.getLog(RefundAutoBatchFileImpl.class);
	
	@Override
	public void disposeBatchFile() {
		log.info("冲退自动产生批次文件开始...");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String currentTime = sdf.format(new Date(System.currentTimeMillis()));
		String manualSeq = String.valueOf(System.currentTimeMillis());
		String taskMsg = manualSeq + ":" + Constants.TASK_TYPE_REFUND  +":" + Constants.BATCH_BUILD_AUTO + ":1:1:" + currentTime;
		StartTask.getInstance().scheduleBuildBatch(taskMsg);
		log.info("冲退自动产生批次文件结束...");
	}

}
