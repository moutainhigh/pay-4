/**
 * 
 */
package com.pay.fundout.withdraw.service.paytobank.query;

import java.util.List;

import com.pay.fundout.withdraw.dto.paytobank.PayToBankQueryResult;

/**
 * 付款到银行查询
 * @author zliner
 *
 */
public interface PayToBankQueryService {
	/**
	 * 交易查询服务
	 * @return list    付款到银行查询结果
	 */
	List<PayToBankQueryResult> queryDeals();
}
