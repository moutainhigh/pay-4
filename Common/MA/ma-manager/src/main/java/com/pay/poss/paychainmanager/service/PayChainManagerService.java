/**
 * 
 */
package com.pay.poss.paychainmanager.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.pay.inf.dao.Page;
import com.pay.poss.paychainmanager.dto.PayChainExternalLog;
import com.pay.poss.paychainmanager.dto.PayChainManagerDto;
import com.pay.poss.paychainmanager.dto.PayChainStatDto;
import com.pay.poss.paychainmanager.enums.EffectiveTypeEnum;
import com.pay.poss.paychainmanager.enums.ResultTooMuchException;

/**
 * @Description
 * @project ma-manager
 * @file PayChainManagerService.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 *          Date Author Changes 2011-09-20 tianqing_wang Create
 */
public interface PayChainManagerService {

	/**
	 * 支付链管理分页查询
	 * 
	 * @param paramDto
	 * @param page
	 * @return List<PayChainManagerDto>
	 */
	@SuppressWarnings("unchecked")
	public List<PayChainManagerDto> queryPayChainByCondition(
			PayChainManagerDto paramDto, Page page);

	/**
	 * 修改支付链状态为生效或者关闭
	 * 
	 * @param paramMap
	 * @return Integer
	 */
	@SuppressWarnings("unchecked")
	public Integer updatePayChainStatus(Map paramMap);

	/**
	 * 修改支付链结束时间
	 * 
	 * @param paramMap
	 * @return
	 */
	public Integer modifyPayChainDate(Long id, EffectiveTypeEnum typeEnum);

	/**
	 * 统计支付总金额
	 * 
	 * @param paramDto
	 * @return
	 */
	public PayChainStatDto queryPayChainSum(PayChainManagerDto paramDto);

	List<PayChainExternalLog> queryPayChainExternalLog(
			PayChainExternalLog externalLog, Page page);

	/**
	 * 下载excel
	 * 
	 * @param externalLog
	 */
	HSSFWorkbook downExcelPayChainDetail(PayChainExternalLog externalLog,
			PayChainManagerDto paramDto, String templatePath)
			throws ResultTooMuchException, Exception;

	public String getEnterMemberAdress(Integer regions, Integer city,
			String addr, Integer zip);

	public PayChainManagerDto queryPayChainManagerDto(
			PayChainManagerDto paramDto);
}
