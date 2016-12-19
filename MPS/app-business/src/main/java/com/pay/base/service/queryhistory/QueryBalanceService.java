package com.pay.base.service.queryhistory;

import java.util.Date;
import java.util.List;

import com.pay.app.facade.dto.MaSumDto;
import com.pay.app.facade.dto.QueryBalanceDto;
import com.pay.base.model.Acct;

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
			Acct acct, String dealtype, String fundTrace, int pageNo,
			int pageSize, String[][] orderColumns,String payNo,String merchantOrderId);

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
	 *      添加商户订单号查询条件      
	 * @return
	 */
	public MaSumDto queryHistoryBusinessSum(Date startDate, Date endDate,
			Acct acct, String dealtype, String fundTrace,String payNo,String merchantOrderId);

	public List<MaSumDto> queryPayMentSumList(String acctCode);

}
