package com.pay.acc.service.querybalance;

import java.util.Date;
import java.util.List;

import com.pay.acc.service.querybalance.dto.QueryBalanceDto;
import com.pay.acc.service.querybalance.dto.QueryMaSumDto;

/**
 * @author jerry_jin
 * @version
 * @data 2010-10-2 企业账户余额明细
 */
public interface QueryBalanceService {

	/**
	 * 查询账户余额明细,如参数无则传入null值 need parameter : acct & pageNo & pageSize
	 * 
	 * @param startDate
	 *            开始时间(startDate:date)，
	 * @param endDate
	 *            结束时间(endDate:date),
	 * @param acct
	 *            acct 账户
	 * @param dealtype
	 *            交易类型
	 * @param fundTrace
	 *            资金流向 (1支出，2收入)
	 * @param pageNo
	 *            pageNo,
	 * @param pageSize
	 *            pageSize,
	 * @param orderColumns
	 *            排序字段
	 * @return
	 */
	public List<QueryBalanceDto> queryBalanceList(Date startDate, Date endDate,
			Long memberCode, String dealtype, String fundTrace, int pageNo,
			int pageSize, String[][] orderColumns);

	/**
	 * 账户余额统计,,如参数无则传入null值 need parameter : acct
	 * 
	 * @param startDate
	 *            开始时间(startDate:date),
	 * @param endDate
	 *            结束时间(endDate:date),
	 * @param acct
	 *            acct 账户
	 * @param dealtype
	 *            交易类型
	 * @param fundTrace
	 *            资金流向(1支出，2收入)
	 * @param orderStr
	 *            排序字符串
	 * @return
	 */
	public QueryMaSumDto queryHistoryBusinessSum(Date startDate, Date endDate,
			Long memberCode, String dealtype, String fundTrace);

	public List<QueryMaSumDto> queryPayMentSumList(String acctCode);

}
