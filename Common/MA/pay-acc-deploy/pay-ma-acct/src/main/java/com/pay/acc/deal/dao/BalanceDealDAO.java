/**
 * 
 */
package com.pay.acc.deal.dao;

import com.pay.acc.deal.dto.BalanceDealSimpleDto;
import com.pay.acc.deal.model.BalanceDeal;
import com.pay.inf.dao.BaseDAO;

/**
 * @author Administrator
 * 
 */
public interface BalanceDealDAO extends BaseDAO<BalanceDeal> {

	/**
	 * 根据流水号查询简单交易信息
	 * 
	 * @param serialNo
	 * @return
	 */
	public BalanceDealSimpleDto queryBalanceDealSimpleInfoWithSerialNo(
			Long serialNo, Long amount);

	/**
	 * 根据流水号，交易号查询当前记录是否正常
	 * 
	 * @param serialNo
	 *            流水号
	 * @param dealCode交易号
	 * @return 返回记录数
	 */
	public Integer queryDealInfoCounts(String serialNo, Integer dealCode,
			Long amount);

	/**
	 * 根据凭证号，交易类型询当前记录是否正常
	 * 
	 * @param dealType
	 *            交易类型
	 * @param voucherNo
	 *            凭证号
	 * @return 返回记录数
	 */
	public Integer queryDealInfoCountsByVo(Integer dealType, Long voucherNo);

	/**
	 * 根据记账交易信息更新记账状态
	 * 
	 * @param seqId
	 * @param charupStatus
	 *            记账状态
	 * @return 返回更新的条数
	 */
	public Integer updateDealInfoChargeStatus(Long seqId, Integer charupStatus);

	/**
	 * 根据订单编号和交易编码查询交易明细
	 * 
	 * @param orderId
	 * @param dealCode
	 * @return
	 */
	public BalanceDealSimpleDto queryBalanceDealByOrderidAndDealCode(
			String orderId, Integer dealCode, Integer dealType);

	/**
	 * 根据订单编号和交易编码查询交易明细
	 * 
	 * @param orderId
	 * @param dealCode
	 * @return
	 */
	public BalanceDeal queryBalanceDealForFlushes(String orderId,
			Integer dealCode);

	/**
	 * @param id
	 * @param dealType
	 * @return
	 */
	public Integer updateDealInfoChargeDealType(Long id, Integer dealType);

}
