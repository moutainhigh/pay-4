/**
 * 
 */
package com.pay.poss.accounting.service;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.pay.inf.dao.Page;
import com.pay.poss.accounting.dto.AccountingFeeDto;
import com.pay.poss.accounting.dto.AccountingFeeParamDto;
import com.pay.poss.paychainmanager.enums.ResultTooMuchException;

/**
 * @Description
 * @project ma-manager
 * @file AccountingFeeService.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 *          Date Author Changes 2012-3-2 DDR Create
 */
public interface AccountingFeeService {
	/**
	 * 分页查询
	 * 
	 * @param paramPage
	 * @param dto
	 * @return
	 */
	public Page<AccountingFeeDto> search(Page<AccountingFeeDto> paramPage,
			AccountingFeeParamDto dto);

	/**
	 * 生成excel工作表导出
	 * 
	 * @param dto
	 * @param templatePath
	 * @return HSSFWorkbook
	 */
	public HSSFWorkbook exportFeeListExcel(AccountingFeeParamDto dto,
			String templatePath) throws ResultTooMuchException, IOException;

}
