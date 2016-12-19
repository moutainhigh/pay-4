package com.pay.fundout.withdraw.dao.paytoaccount;

import com.pay.inf.dao.BaseDAO;

public interface PayToAcctDao extends BaseDAO {

	/**
	 * 当月金额
	 * 
	 * @return
	 */
	public Long getMonthTotalAmount(Long memberCode);

	/**
	 * 当日金额
	 * 
	 * @return
	 */
	public Long getDayTotalAmount(Long memberCode);

	/**
	 * 当月笔数
	 * 
	 * @return
	 */
	public Integer getMonthTotalCount(Long memberCode);

	/**
	 * 当日笔数
	 * 
	 * @return
	 */
	public Integer getDayTotalCount(Long memberCode);

}
