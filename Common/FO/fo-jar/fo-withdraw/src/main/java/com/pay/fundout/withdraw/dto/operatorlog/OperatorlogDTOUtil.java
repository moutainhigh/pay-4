/**
 *  File: OperatorlogDTOUtil.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-14      zliner      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dto.operatorlog;

import org.springframework.beans.BeanUtils;

import com.pay.fundout.withdraw.model.operatorlog.OperatorLog;

/**
 * 操作员日志model和dto工具转换类
 * @author zliner
 *
 */
public class OperatorlogDTOUtil {
	/**
	 * model转换为dto
	 * @param log   model
	 * @return dto  dto对象 
	 */
	public static OperatorlogDTO model2Dto(OperatorLog log) {
		if(log == null) {
			return null;
		}
		OperatorlogDTO dto = new OperatorlogDTO();
		BeanUtils.copyProperties(log,dto);
		return dto;
	}
	/**
	 * dto转换为model
	 * @param dto  dto对象
	 * @return log  model 
	 */
	public static OperatorLog dto2Model(OperatorlogDTO dto) {
		if(dto == null) {
			return null;
		}
		OperatorLog log = new OperatorLog();
		BeanUtils.copyProperties(dto,log);
		return log;
	}
}
