/**
 * 
 */
package com.pay.acc.translog.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.pay.acc.translog.dto.TransLogDto;
import com.pay.acc.translog.exception.TransLogException;
import com.pay.acc.translog.exception.TransLogUnknowException;
import com.pay.acc.translog.model.TransLog;

public interface TransLogService {

	/**
	 * 保存支付日志
	 * 
	 * @param transLogDto
	 * @return
	 * @throws TransLogException
	 * @throws TransLogUnknowException
	 */
	public Long createTransLog(TransLogDto transLogDto)
			throws TransLogException, TransLogUnknowException;

	/**
	 * 根据流水号查询交易日志
	 * 
	 * @param serialNo
	 *            支付流水号
	 * @return
	 * @throws TransLogException
	 * @throws TransLogUnknowException
	 */
	public TransLogDto queryTransLogDtoWithSerialNo(Long serialNo)
			throws TransLogException, TransLogUnknowException;

	/**
	 * 
	 * @param acctCode
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public List<TransLog> queryBalanceHistoryByAcctCodeAndDate(String acctCode,
			Date fromDate, Date toDate);

	/**
	 * 根据交易流水号 查询
	 * 
	 * @author Sunny Ying
	 * @param serialNo
	 *            交易流水号
	 * @throw NumberFormatException, SQLException
	 * @return List<TransLog>
	 */
	public List<TransLog> queryTransLogBySerialNo(String serialNo)
			throws NumberFormatException, SQLException;

	/**
	 * 更新 TransLog 的关联id
	 * 
	 * @author Sunny Ying
	 * @param transLog
	 * @param baseId
	 * @throw null
	 * @return int
	 */
	public int editTransLogForLinkId(List<TransLog> transLogList, Long baseId);
}
