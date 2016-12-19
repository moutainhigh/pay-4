/**
 * 
 */
package com.pay.acc.deal.service;

import java.util.Date;
import java.util.List;

import com.pay.acc.deal.dto.BalanceEntryDto;
import com.pay.acc.deal.exception.BalanceException;
import com.pay.acc.deal.exception.BalanceUnkownException;
import com.pay.acc.deal.model.BalanceEntry;

/**
 * @author Administrator
 * 
 */
public interface BalanceEntryService {

	/**
	 * 保存记账分录信息
	 * 
	 * @param balanceEntry
	 * @return
	 * @throws BalanceException
	 * @throws BalanceUnkownException
	 */
	public Long createBalanceEntry(BalanceEntryDto balanceEntryDto)
			throws BalanceException, BalanceUnkownException;

	/**
	 * 查询会员账户一段时间内的交易金额
	 * 
	 * @param acctCode
	 *            ：String 账户号
	 * @param strEndDate
	 *            ：String 结束时间
	 * @return transAmounts:Long 交易金额
	 * @throws BalanceException
	 * @throws BalanceUnkownException
	 */
	public Long queryBalanceEntryForTransAmount(String acctCode,
			String strEndDate) throws BalanceException, BalanceUnkownException;

	/**
	 * 统计两周内所有的入款金额(不包括提现退款)
	 * 
	 * @param acctCode
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public Long querySumTwoWeekAddAmount(String acctCode, Long date);

	/**
	 * 统计两周内除提现外所有出款金额
	 * 
	 * @param acctCode
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public Long querySumTwoWeekMinusValue(String acctCode, Long date);

	/**
	 * 查询14天前的余额（可提现金额）
	 * 
	 * @param acctCode
	 * @return
	 */
	public Long queryWithdrawalBanalce(String acctCode, Long date);

	/**
	 * 查询14天内的提现退款(可直接提现的金额)
	 * 
	 * @param acctCode
	 * @return
	 */
	public Long queryWithdrawalRefund(String acctCode, Long date);

	/**
	 * 统计14天内所有的出款金额
	 * 
	 * @param acctCode
	 * @return
	 */
	public Long sumTwoWeekMinusValue(String acctCode, Long date);

	/**
	 * 根据流水号，dealCode查询分录
	 * 
	 * @param orderId
	 * @param dealCode
	 * @return
	 */
	public List<BalanceEntry> queryBalanceEntryBySerialNo(String orderId,
			Integer dealCode);

	/**
	 * 统计一段时间内借贷发生总额
	 * 
	 * @param acctCode
	 * @param startAt
	 *            开始时间
	 * @param endAt
	 *            结束时间
	 * @return
	 */
	public List<BalanceEntry> queryCrdrSumByAcctCode(String acctCode,
			String startAt, String endAt);

	/**
	 * 查询 时间距离本日最近的余额
	 * 
	 * @param acctCode
	 * @param endAt
	 *            结束时间
	 * @return
	 */
	public Long queryBalanceByAcctCode(String acctCode, String endAt);

	/**
	 * 根据某个时间点账户余额查询
	 * 
	 * @param acctCode
	 * @param date
	 * @return
	 */
	public Long selectBalanceByAcctCodeAndDate(String acctCode, Date date);
}
