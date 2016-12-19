package com.pay.acc.service.account;

import com.pay.acc.service.account.dto.CalFeeReponseDto;
import com.pay.acc.service.account.exception.MaAcctBalanceException;

public interface FlushesBalanceService {
	/**
	 * 冲正交易
	 * 
	 * @param flushesOrderId
	 *            冲正流水号
	 * @param orderId
	 *            原流水号
	 * @param dealCode
	 *            记账code
	 * @param amount
	 *            金额
	 * @param dealType
	 *            交易类型
	 * @return true 成功，false失败 dealType 冲正的为30
	 * @see com.pay.acc.service.account.constantenum.PayForEnum
	 * @throws MaAcctBalanceException
	 */
	public boolean doFlushesBalanceNsTx(String flushesOrderId, String orderId,
			Integer dealCode, Long amount, Integer dealType)
			throws MaAcctBalanceException;

	/**
	 * 根据原交易号查询组装成新的CalFeeReponseDto对象
	 * 
	 * @param flushesOrderId
	 * @param orderId
	 * @param dealCode
	 * @param amount
	 * @param dealType
	 * @return
	 * @throws MaAcctBalanceException
	 */
	public CalFeeReponseDto generateCalFeeReponseDto(String flushesOrderId,
			String orderId, Integer dealCode, Long amount, Integer dealType)
			throws MaAcctBalanceException;

	/**
	 * 冲正，调用更新余额接口，不调用记账
	 * 
	 * @param calFeeReponseDto
	 * @return
	 * @throws MaAcctBalanceException
	 */
	public Long flushesFrzoenBalance(CalFeeReponseDto calFeeReponseDto,
			Integer dealType, Long flushesLogId) throws MaAcctBalanceException;

	/**
	 * 新增日志
	 * 
	 * @param flushesOrderId
	 * @param orderId
	 * @param dealCode
	 * @param amount
	 * @param dealType
	 * @return
	 */
	public Long insertFlushesLogRnTx(String flushesOrderId, String orderId,
			Integer dealCode, Long amount, Integer dealType)
			throws MaAcctBalanceException;

	/**
	 * 验证是否重复
	 * 
	 * @param flushesOrderId
	 * @param orderId
	 * @param dealCode
	 * @param amount
	 * @param dealType
	 * @throws MaAcctBalanceException
	 */
	public void handlerCheckFlushesParameter(String flushesOrderId,
			String orderId, Integer dealCode, Long amount, Integer dealType)
			throws MaAcctBalanceException;

	/**
	 * 更新日志的状态
	 * 
	 * @param id
	 * @param status
	 * @throws MaAcctBalanceException
	 */
	public void updateFlushesLogRnTx(String orderId, Integer dealCode,
			Long amount, Integer status) throws MaAcctBalanceException;
}
