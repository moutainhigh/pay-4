/**
 *
 */
package com.pay.credit.dao.creditorder.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.pay.credit.conditon.creditorder.CreditOrderQueryConditon;
import com.pay.credit.dao.creditorder.ICreditOrderDao;
import com.pay.credit.model.creditcurrency.PartnerCreditCurrencyCode;
import com.pay.credit.model.creditorder.CreditOrder;
import com.pay.credit.model.creditorder.CreditOrderDetailParam;
import com.pay.credit.model.creditorder.CreditOrderDetailed;
import com.pay.inf.dao.Page;
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
	@Override
	public List<CreditOrder> queryCreditOrderByConditon(
			final CreditOrderQueryConditon queryConditon) {
		try {
			return this.getSqlMapClient().queryForList(this.getNamespace().concat("queryCreditOrderByConditon"), queryConditon);
		} catch (final SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CreditOrder> queryCreditOrderByConditon(
			final CreditOrderQueryConditon queryConditon, final Page page) {
			return super.findByCriteria("findByCriteria",queryConditon,page);
	}

	@Override
	public CreditOrder queryCreditOrderById(final String creditOrderId) {
		return  this.findById("queryCreditOrderById",creditOrderId);
	}

	@Override
	public List<CreditOrderDetailed> queryCreditOrderDetailById(
			final String creditOrderId) {
		try {
			return this.getSqlMapClient().queryForList(this.getNamespace().concat("queryCreditOrderDetailById"),creditOrderId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void updateCreditOrder(final CreditOrderDetailed creditOrder) {
		try {
			this.getSqlMapClient().update(this.getNamespace().concat("updateCreditOrder"),creditOrder);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateCreditOrderDetailed(final CreditOrderDetailed creditOrder) {
		try {
			this.getSqlMapClient().update(this.getNamespace().concat("updateCreditOrderDetailed"),creditOrder);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/***
	 * 逐条更新  授信订单明细表
	 */
	@Override
	public void updateCreditOrderDetail(final CreditOrderDetailed updateCreditOrderDetail) {
		try {
			this.getSqlMapClient().update(this.getNamespace().concat("updateCreditOrderDetail"),updateCreditOrderDetail);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateCreditOrders(final CreditOrder updateCreditOrders) {
		try {
			this.getSqlMapClient().update(this.getNamespace().concat("updateCreditOrders"),updateCreditOrders);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void updateCreditOrderPassCount(CreditOrder creditOrder) {
		try {
			this.getSqlMapClient().update(this.getNamespace().concat("updateCreditOrderPassCount"),creditOrder);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean saveCreditOrderDetail(final List<CreditOrderDetailParam> list) {
		this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {

			@Override
			public Object doInSqlMapClient(final SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch(); 
				int batch = 0;
				for(CreditOrderDetailParam param : list){
					executor.insert(namespace.concat("saveCreditOrderDetail"), param) ;
					batch ++ ;
					if(batch == 100){
						executor.executeBatch() ;
						batch = 0 ;
					}
					executor.executeBatch() ;
				}
				return null ;
			}
		}) ;
		return true;
		
	}

	@Override
	public void saveCreditOrderDetail2(final Map<String, Object> hMap) {
		this.getSqlMapClientTemplate().insert(this.getNamespace().concat("saveCreditOrderDetail2"), hMap) ;
		
	}

	@Override
	public List<PartnerCreditCurrencyCode> queryPartnerCreditCurrency(
			final Long memberCode) {
		return this.getSqlMapClientTemplate().queryForList(this.getNamespace().concat("queryPartnerCreditCurrency"), memberCode) ;
	}

}
