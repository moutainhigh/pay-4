/**
 * 
 */
package com.pay.ma.chargeup.service;

import java.util.List;

import com.pay.ma.chargeup.dto.BalanceDealDto;

/**
 * 记账服务，调用pe进行记账
 * 
 * @author Administrator
 * 
 */
public interface BalanceDealService {

	/**
	 * 更新记账状态
	 * 
	 * @param serialNo
	 *            流水号
	 * @param dealCode
	 *            交易号
	 * @param chargeUpStatus
	 *            记账状态
	 * @return
	 */
	public boolean callUpdateBalanceDealStatus(String serialNo,
			Integer dealCode, Integer chargeUpStatus);

	// /**
	// * 记账失败后，再次发起记账，
	// *
	// * @param chargeUpstatus
	// * 记账状态
	// * @return 记账成功返回1
	// * @throws ChargeUpException
	// */
	// public boolean callUpdateBalanceDealFailCallBackWithSerialNo(String
	// serialNo, Integer chargeUpstatus) ;

	/**
	 * 查询记账失败信息，然后进行补账
	 * 
	 * @return
	 */
	public List<BalanceDealDto> callQueryBalanceChargeup(Integer status);

	/**
	 * 查询记账交易信息
	 * 
	 * @param serialNo
	 * @param dealCode
	 * @param amount
	 * @return
	 */
	public List<BalanceDealDto> callQueryBalanceDealInfo(String serialNo,
			Integer dealCode, Long amount);

	public List<BalanceDealDto> callQueryBalanceDealInfoByVo(Long voucherCode);
}
