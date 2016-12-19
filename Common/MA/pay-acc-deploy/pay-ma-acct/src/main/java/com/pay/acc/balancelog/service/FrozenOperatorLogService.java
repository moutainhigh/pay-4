/**
 * 
 */
package com.pay.acc.balancelog.service;

import java.math.BigDecimal;
import java.util.List;

import com.pay.acc.balancelog.dto.FrozenAmountDto;
import com.pay.acc.balancelog.dto.UnFrozenAmountDto;

/**
 * 增加冻结或解冻日志
 * @author ddr
 * @version 1.0 2012-6-6
 *
 */
public interface FrozenOperatorLogService {

//	/**
//	 * 批量增加冻结日志
//	 * @return List<Long> id号列表
//	 * @author ddr
//	 */
//	public List<Long> batchAddFrozenLog(List<FrozenAmountDto> frozenAmountDtos);
	
//	/**
//	 * 批量增加解冻日志
//	 * @return List<Long> id号列表
//	 * @author ddr
//	 */
//	public List<Long> batchUnFrozenLog(List<UnFrozenAmountDto> unFrozenAmountDtos);
	
	
	/**
	 * 单个增加冻结日志
	 * @param frozenAmountDto
	 * @param oldBlance 冻结前的余额
	 * @return Long id号
	 */
	public Long addFrozenLog(FrozenAmountDto frozenAmountDto,BigDecimal oldBlance);
	
	
	/**
	 * 单个增加解结日志
	 * @return Long id号
	 * @param oldBlance 冻结前的余额
	 * @author ddr
	 */
	public Long addUnFrozenLog(UnFrozenAmountDto unFrozenAmountDto,BigDecimal oldBlance);
}
