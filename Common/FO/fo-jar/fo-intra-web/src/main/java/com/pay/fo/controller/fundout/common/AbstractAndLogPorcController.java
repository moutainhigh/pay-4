/**
 *  File: AbstractAndLogPorcController.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-14      zliner      Changes
 *  
 *
 */
package com.pay.fo.controller.fundout.common;

import org.springframework.web.servlet.mvc.AbstractController;

import com.pay.fundout.withdraw.dto.operatorlog.OperatorlogDTO;
import com.pay.fundout.withdraw.service.operatorlog.OperatorlogService;

/**
 * get方式提交控制器记录日志处理
 * @author zliner
 *
 */
public abstract class AbstractAndLogPorcController extends AbstractController {
	//操作员日志记录服务
	private OperatorlogService operatorlogService;
	//set注入
	public void setOperatorlogService(final OperatorlogService param) {
		this.operatorlogService = param;
	}
	protected void saveOperatorLog() {
		operatorlogService.saveOperatorLog(buildOperatorLog());
	}
	public abstract OperatorlogDTO buildOperatorLog();
}
