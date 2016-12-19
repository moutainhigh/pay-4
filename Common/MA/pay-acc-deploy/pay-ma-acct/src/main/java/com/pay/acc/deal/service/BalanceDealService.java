package com.pay.acc.deal.service;

import java.util.Date;
import java.util.List;

import com.pay.acc.deal.dto.BalanceDealDto;
import com.pay.acc.deal.dto.BalanceDealSimpleDto;
import com.pay.acc.deal.exception.BalanceException;
import com.pay.acc.deal.exception.BalanceUnkownException;
import com.pay.acc.deal.model.BalanceDeal;

/**
 * @author Administrator
 * 
 */
public interface BalanceDealService {

	/**
	 * 保存记账信息
	 * 
	 * @param balanceDeal
	 * @return
	 * @throws BalanceException
	 * @throws BalanceUnkownException
	 * 
	 */
	public Long createBalanceDeal(BalanceDealDto balanceDealDto)
			throws BalanceException, BalanceUnkownException;

	/**
	 * 根据流水号查询简单交易信息
	 * 
	 * @param serialNo
	 * @return
	 * @throws BalanceException
	 * @throws BalanceUnkownException
	 */
	public BalanceDealSimpleDto queryBalanceDealSimpleInfoWithSerialNo(
			Long serialNo, Long amount) throws BalanceException,
			BalanceUnkownException;

	/**
	 * 查询充值记录类表
	 * 
	 * @param acctCode
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public List<BalanceDeal> queryWithdrawDealByAcctCodeAndDate(
			String acctCode, Date fromDate, Date toDate);

	/**
	 * 根据流水号，交易号，查询当前记录数
	 * 
	 * @param serialNo
	 *            流水号
	 * @param dealCode
	 *            交易号
	 * @param amount
	 *            交易金额
	 * @return 返回查询的数目，
	 * @throws BalanceException
	 * @throws BalanceUnkownException
	 */
	public Integer queryDealInfoNum(String serialNo, Integer dealCode,
			Long amount) throws BalanceException, BalanceUnkownException;

	public Integer queryDealInfoCountsByVo(Integer dealType, Long voucherNo)
			throws BalanceException, BalanceUnkownException;

	/**
	 * 发现信息到mq，如果发送成功更新 记账发送mq成功状态为3
	 * 
	 * @param seqId
	 *            deal 信息ID
	 * @param chargeUpStatus
	 *            记账状态 （发q的成功后的状态）
	 * @return
	 */
	public Integer updateChargeUpStatus(Long seqId, Integer chargeUpStatus);

	/**
	 * 根据订单编号和交易编码查询交易明细
	 * 
	 * @param orderId
	 * @param dealCode
	 * @return
	 */
	public BalanceDealDto queryBalanceDealForFlushes(String orderId,
			Integer dealCode);

	/**
	 * @param id
	 * @param dealType
	 * @return
	 */
	public boolean updateDealType(Long id, Integer dealType);
}
