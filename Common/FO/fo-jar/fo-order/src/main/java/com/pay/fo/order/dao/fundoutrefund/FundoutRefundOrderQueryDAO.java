package com.pay.fo.order.dao.fundoutrefund;

import com.pay.fo.order.model.bankrefund.BankRefundOrderQueryModel;
import com.pay.inf.dao.Page;

/**
 * @author Sandy
 * @Date 2011-7-26
 * @Description 退票查询
 * @Copyright Copyright (c) 2004-2013 pay.com. All rights reserved.
 */
public interface FundoutRefundOrderQueryDAO {
	
	/**
	 * 获取可退款订单列表
	 * 
	 * @param page
	 * @param query
	 * @return
	 */
	Page<BankRefundOrderQueryModel> queryAllowRefundList(final Page<BankRefundOrderQueryModel> page, Object query);

	/**
	 * 获取待审核/退款成功订单列表
	 * 
	 * @param page
	 * @param query
	 * @return
	 */
	Page<BankRefundOrderQueryModel> queryHasRefundList(final Page<BankRefundOrderQueryModel> page, Object query);
	
	/**
	 * 查询单笔退票详情 
	 * @param query
	 * @return <code>BankRefundOrderQueryModel</code>
	 */
	BankRefundOrderQueryModel queryHasRefundDetail(Object query);
	
	/**
	 * 查询单笔可申请退票订单详情 
	 * @param query
	 * @return <code>BankRefundOrderQueryModel</code>
	 */
	BankRefundOrderQueryModel queryRefundDetail(Object query);
	
}
