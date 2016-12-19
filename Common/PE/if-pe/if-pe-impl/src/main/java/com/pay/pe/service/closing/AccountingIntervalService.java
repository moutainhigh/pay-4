package com.pay.pe.service.closing;

import java.util.Date;
import java.util.List;

import com.pay.pe.dto.AccountEntryDTO;

public interface AccountingIntervalService {
	/**
	 * 会计日期是否有效。
	 * 看该会计日期是否在最后一次轧账区间之后。有效true, 无效false.
	 * 如果没有最后一次轧账区间，则返回true.
	 * @param date
	 * @return
	 */
	boolean isAvailable(Date date);
	
	/**
	 * 检查list中的交易时间是否大于关帐时间
	 * @param transcationTimes  交易时间列表
	 * @return 判断结果
	 */
	boolean isAvailable(Date[] transcationTimes);
	
	/**
	 * 检查list中的交易时间是否大于关帐时间
	 * @param entries  分录集合
	 * @return 判断结果
	 */
	boolean isAvailable(List<AccountEntryDTO> entries);
	
}
