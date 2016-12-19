/**
 * 
 */
package com.pay.acc.deal.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pay.acc.deal.model.BalanceEntry;
import com.pay.inf.dao.BaseDAO;

/**
 * @author Administrator
 * 
 */
public interface BalanceEntryDAO extends BaseDAO<BalanceEntry> {

	/**
	 * 查询会员账户一段时间内的交易金额
	 * 
	 * @param acctCode
	 *            ：String 账户号
	 * @param amount
	 *            ：Long 额度（单位：人民币元）
	 * @param days
	 *            ：Integer 时段（天数）
	 * @return transAmounts:Long 交易金额
	 */
	public Long queryBalanceEntryForTransAmount(String sqlId,
			Map<String, Object> paraMap);

	/**
	 * 查询两周内所有余额增加的金额
	 * 
	 * @param paramMap
	 * @return
	 */
	public Long sumTwoWeekAddValue(String acctCode, Long date);

	/**
	 * 查询收款方两周内所有余额减少的金额（提现除外）
	 * 
	 * @param acctCode
	 * @param date
	 *            天数
	 * @return
	 */
	public Long sumTwoWeekPayerMinusValue(String acctCode, Long date);

	/**
	 * 查询14天前的余额（可提现金额）
	 * 
	 * @param acctCode
	 * @return
	 */
	public Long queryWithdrawalBanalce(String acctCode, Long date);

	/**
	 * 统计14天内所有的出款金额
	 * 
	 * @param acctCode
	 * @return
	 */
	public Long sumTwoWeekMinusValue(String acctCode, Long date);

	/**
	 * 查询付款方两周内所有余额减少的金额（提现除外）
	 * 
	 * @param paramMap
	 * @return
	 */
	public Long sumTwoWeekPayeeMinusValue(String acctCode, Long date);

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
	 * 查询两周内所有的提现退款
	 * 
	 * @param acctCode
	 * @return
	 */
	public Long sumTwoWeekWithdrawRefund(String acctCode, Long date);

	/**
	 * 统计一段时间内借贷发生总额
	 * 
	 * @param acctCode
	 * @return
	 */
	public List<BalanceEntry> queryCrdrSumByAcctCode(String acctCode,
			String startAt, String endAt);

	/**
	 * 查询 时间距离本日最近的余额
	 * 
	 * @param acctCode
	 * @return
	 */
	public Long queryBalanceByAcctCode(String acctCode, String endAt);

	public BalanceEntry selectBalanceByAcctCodeAndDate(String acctCode,
			Date date);
}
