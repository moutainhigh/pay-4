package com.pay.pe.dao.log.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.util.Assert;

import com.pay.inf.dao.impl.IbatisDAOSupport;
import com.pay.pe.dao.log.AccountingExceptionLogDAO;
import com.pay.pe.model.AccountingExceptionLog;


public class AccountingExceptionLogDAOImpl extends IbatisDAOSupport
		implements AccountingExceptionLogDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public List<AccountingExceptionLog> getAccountingExceptionLogByCreationDate(
			Timestamp beginTime, Timestamp endTime) {
//		ExpressionType expressionType = ExpressionTypeFactory.between(
//				"creationDate", beginTime, endTime);
//		return findObjectsByExpType(AccountingExceptionLog.class, expressionType);
		return null ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see 
	 */
	public AccountingExceptionLog getAccountingExceptionLogByDealId(String dealId) {
//		ExpressionType expressionType = ExpressionTypeFactory.equal("dealId",
//				dealId);
//		return (AccountingExceptionLog) findObjectByExpTypes(
//				AccountingExceptionLog.class,
//				new ExpressionType[] { expressionType });
		return null ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see 
	 */
	public AccountingExceptionLog getAccountingExceptionLogByType(
			Long type) {
//		ExpressionType expressionType = ExpressionTypeFactory.equal("type",
//				type);
//		return (AccountingExceptionLog) findObjectByExpTypes(
//				AccountingExceptionLog.class,
//				new ExpressionType[] { expressionType });
		return null ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 */
	public AccountingExceptionLog getAccountingExceptionLogById(Long sequenceId) {
//		ExpressionType expressionType = ExpressionTypeFactory.equal(
//				"sequenceId", sequenceId);
//		return (AccountingExceptionLog) findObjectByExpTypes(
//				AccountingExceptionLog.class,
//				new ExpressionType[] { expressionType });
		return null ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 */
	public AccountingExceptionLog getAccountingExceptionLogByOrderId(String orderId) {
//		ExpressionType expressionType = ExpressionTypeFactory.equal("orderId",
//				orderId);
//		return (AccountingExceptionLog) findObjectByExpTypes(
//				AccountingExceptionLog.class,
//				new ExpressionType[] { expressionType });
		return null ;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public List<AccountingExceptionLog> getAccountingExceptionLogLikeMessage(
			String likeMessage) {

//		ExpressionType expressionType = ExpressionTypeFactory.like("message",
//				likeMessage);
//		return findObjectsByExpType(AccountingExceptionLog.class, expressionType);
		return null ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see 
	 */
	public void insertAccountingExceptionLog(
			List<AccountingExceptionLog> accountingExceptionLogList) {
		Assert.notNull(accountingExceptionLogList);
		for (AccountingExceptionLog accountingException : accountingExceptionLogList) {
			insertAccountingExceptionLog(accountingException);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * 
	 */
	public AccountingExceptionLog insertAccountingExceptionLog(
			AccountingExceptionLog accountingExceptionLog) {
		Assert.notNull(accountingExceptionLog);
		return (AccountingExceptionLog) super.saveObject(accountingExceptionLog);
	}

}
