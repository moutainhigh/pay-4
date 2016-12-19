package com.pay.pe.dao.log;

import java.sql.Timestamp;
import java.util.List;

import com.pay.pe.model.AccountingExceptionLog;



public interface AccountingExceptionLogDAO {

	/**
	 * 批量插入AccountingException
	 * 
	 * @param accountingExceptionLogList
	 */
	public void insertAccountingExceptionLog(
			List<AccountingExceptionLog> accountingExceptionLogList);

	/**
	 * 一次插入一条AccountingException记录
	 * 
	 * @param accountingException
	 * @return AccountingException
	 */
	public AccountingExceptionLog insertAccountingExceptionLog(
			AccountingExceptionLog accountingExceptionLog);

	/**
	 * 根据序列号查找AccountingException
	 * 
	 * @param sequenceId
	 * @return AccountingException
	 */
	public AccountingExceptionLog getAccountingExceptionLogById(Long sequenceId);

	/**
	 * 根据异常类型，查找AccountingException对象
	 * 
	 * @param exceptionType
	 * @return AccountingException
	 */
	public AccountingExceptionLog getAccountingExceptionLogByType(
			Long type);

	/**
	 * 根据dealId来查找
	 * 
	 * @param dealId
	 * @return AccountingException
	 */
	public AccountingExceptionLog getAccountingExceptionLogByDealId(String dealId);

	/**
	 * 根据orderId来查找
	 * 
	 * @param orderId
	 * @return AccountingException
	 */
	public AccountingExceptionLog getAccountingExceptionLogByOrderId(String orderId);

	/**
	 * 根据类似的文本查找，具有类似文本的日志
	 * 
	 * @param likeMessage
	 * @return 返回0个或多个AccountingException对象
	 */
	public List<AccountingExceptionLog> getAccountingExceptionLogLikeMessage(
			String likeMessage);

	/**
	 * 根据创建时间查找
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 返回0个或多个AccountingException对象
	 */
	public List<AccountingExceptionLog> getAccountingExceptionLogByCreationDate(
			Timestamp beginTime, Timestamp endTime);
}
