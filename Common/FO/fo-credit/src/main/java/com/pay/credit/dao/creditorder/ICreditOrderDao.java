/**
 *
 */
package com.pay.credit.dao.creditorder;

import java.util.List;

import com.pay.credit.conditon.creditorder.CreditOrderQueryConditon;
import com.pay.credit.model.creditorder.CreditOrder;
import com.pay.inf.dao.BaseDAO;

/**
 * 融资订单
 *
 * @author zhixiang.deng
 *
 * @date 2015年8月4日
 */
public interface ICreditOrderDao extends BaseDAO<CreditOrder> {


	/**
	 * 根据条件查询融资信息
	 *
	 * @param queryConditon
	 * 			查询条件
	 * @return 融资信息列表
	 */
	List<CreditOrder> queryCreditOrderByConditon(CreditOrderQueryConditon queryConditon);
}
