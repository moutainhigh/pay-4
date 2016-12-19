/**
 * 
 */
package com.pay.ma.chargeup.dao;

import java.util.List;

import com.pay.ma.chargeup.model.BalanceDeal;

/**
 * @author Administrator
 * 
 */
public interface BalanceDealDAO {

	/**
	 * 查询未记账的支付信息，然后进行记账
	 * 
	 * @param chargeUpStatus
	 *            记账状态
	 * @return 成功返回未记账信息列表
	 */
	public List<BalanceDeal> queryChargeUpInfo(Integer chargeUpStatus);

	/**
	 * 根据流水号更新记账状态
	 * 
	 * @param serialNo
	 *            流水号
	 * @param chargeUpStatus
	 *            记账状态
	 * @return 成功返回true
	 */
	public boolean updateChargeUpStatus(String serialNo, Integer dealCode,
			Integer chargeUpStatus);

	/**
	 * 查询记账信息
	 * 
	 * @param serialNo
	 *            流水号
	 * @param dealCode
	 *            交易号
	 * @param amount
	 *            交易金额
	 * @return
	 */
	public List<BalanceDeal> queryBalanceDealInfo(String serialNo, Integer dealCode,
			Long amount);

	/**
	 * 查询记账信息
	 * 
	 * @param voucherCode
	 *            凭证号
	 * @param dealType
	 *            交易类型
	 * @return
	 */
	public List<BalanceDeal> queryBalanceDealInfoByVo(Long voucherCode);

}
