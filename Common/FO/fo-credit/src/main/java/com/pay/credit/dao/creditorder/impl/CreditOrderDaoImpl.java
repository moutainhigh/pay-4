/**
 *
 */
package com.pay.credit.dao.creditorder.impl;

import java.sql.SQLException;
import java.util.List;

import com.pay.credit.conditon.creditorder.CreditOrderQueryConditon;
import com.pay.credit.dao.creditorder.ICreditOrderDao;
import com.pay.credit.model.creditorder.CreditOrder;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author zhixiang.deng
 *
 * @date 2015年8月4日
 */
public class CreditOrderDaoImpl extends BaseDAOImpl<CreditOrder> implements
ICreditOrderDao {

	/* (non-Javadoc)
	 * @see com.pay.credit.dao.creditorder.ICreditOrderDao#queryCreditOrderByConditon(com.pay.credit.conditon.creditorder.CreditOrderQueryConditon)
	 */
	public List<CreditOrder> queryCreditOrderByConditon(
			final CreditOrderQueryConditon queryConditon) {
		try {
			return this.getSqlMapClient().queryForList(this.getNamespace().concat("queryCreditOrderByConditon"), queryConditon);
		} catch (final SQLException e) {
			e.printStackTrace();
			return null;
		}
	}



}
