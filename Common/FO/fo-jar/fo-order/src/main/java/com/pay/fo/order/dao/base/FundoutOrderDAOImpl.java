package com.pay.fo.order.dao.base;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.pay.fo.order.model.base.FundoutOrder;
import com.pay.fo.order.model.base.FundoutOrderExample;

public class FundoutOrderDAOImpl extends SqlMapClientDaoSupport implements FundoutOrderDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table FO.FUNDOUT_ORDER
	 * @abatorgenerated  Mon Jun 13 18:02:15 CST 2011
	 */
	public FundoutOrderDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table FO.FUNDOUT_ORDER
	 * @abatorgenerated  Mon Jun 13 18:02:15 CST 2011
	 */
	public Long insert(FundoutOrder record) {
		Object newKey = getSqlMapClientTemplate().insert(
				"FO_FUNDOUT_ORDER.abatorgenerated_insert", record);
		return (Long)newKey;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table FO.FUNDOUT_ORDER
	 * @abatorgenerated  Mon Jun 13 18:02:15 CST 2011
	 */
	public int updateByPrimaryKey(FundoutOrder record) {
		int rows = getSqlMapClientTemplate().update(
				"FO_FUNDOUT_ORDER.abatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table FO.FUNDOUT_ORDER
	 * @abatorgenerated  Mon Jun 13 18:02:15 CST 2011
	 */
	public int updateByPrimaryKeySelective(FundoutOrder record) {
		int rows = getSqlMapClientTemplate().update(
				"FO_FUNDOUT_ORDER.abatorgenerated_updateByPrimaryKeySelective",
				record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table FO.FUNDOUT_ORDER
	 * @abatorgenerated  Mon Jun 13 18:02:15 CST 2011
	 */
	public List selectByExample(FundoutOrderExample example) {
		List list = getSqlMapClientTemplate().queryForList(
				"FO_FUNDOUT_ORDER.abatorgenerated_selectByExample", example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table FO.FUNDOUT_ORDER
	 * @abatorgenerated  Mon Jun 13 18:02:15 CST 2011
	 */
	public FundoutOrder selectByPrimaryKey(Long orderId) {
		FundoutOrder key = new FundoutOrder();
		key.setOrderId(orderId);
		FundoutOrder record = (FundoutOrder) getSqlMapClientTemplate()
				.queryForObject(
						"FO_FUNDOUT_ORDER.abatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table FO.FUNDOUT_ORDER
	 * @abatorgenerated  Mon Jun 13 18:02:15 CST 2011
	 */
	public int deleteByExample(FundoutOrderExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"FO_FUNDOUT_ORDER.abatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table FO.FUNDOUT_ORDER
	 * @abatorgenerated  Mon Jun 13 18:02:15 CST 2011
	 */
	public int deleteByPrimaryKey(Long orderId) {
		FundoutOrder key = new FundoutOrder();
		key.setOrderId(orderId);
		int rows = getSqlMapClientTemplate().delete(
				"FO_FUNDOUT_ORDER.abatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table FO.FUNDOUT_ORDER
	 * @abatorgenerated  Mon Jun 13 18:02:15 CST 2011
	 */
	public int countByExample(FundoutOrderExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"FO_FUNDOUT_ORDER.abatorgenerated_countByExample", example);
		return count.intValue();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table FO.FUNDOUT_ORDER
	 * @abatorgenerated  Mon Jun 13 18:02:15 CST 2011
	 */
	public int updateByExampleSelective(FundoutOrder record,
			FundoutOrderExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"FO_FUNDOUT_ORDER.abatorgenerated_updateByExampleSelective",
				parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table FO.FUNDOUT_ORDER
	 * @abatorgenerated  Mon Jun 13 18:02:15 CST 2011
	 */
	public int updateByExample(FundoutOrder record, FundoutOrderExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"FO_FUNDOUT_ORDER.abatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to the database table FO.FUNDOUT_ORDER
	 * @abatorgenerated  Mon Jun 13 18:02:15 CST 2011
	 */
	private static class UpdateByExampleParms extends FundoutOrderExample {
		private Object record;

		public UpdateByExampleParms(Object record, FundoutOrderExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}

	@Override
	public Long sumCurrentDayPaymentAmount(FundoutOrder record) {
		Object obj = getSqlMapClientTemplate()
		.queryForObject(
				"FO_FUNDOUT_ORDER.SumCurrentDayPaymentAmount",
				record);
		return (Long)obj;
	}

	@Override
	public Long sumCurrentMonthPaymentAmount(FundoutOrder record) {
		Object obj = getSqlMapClientTemplate()
		.queryForObject(
				"FO_FUNDOUT_ORDER.SumCurrentMonthPaymentAmount",
				record);
		return (Long)obj;
	}

	@Override
	public Integer countCurrentDayPaymentTimes(FundoutOrder record) {
		Object obj = getSqlMapClientTemplate()
		.queryForObject(
				"FO_FUNDOUT_ORDER.CountCurrentDayPaymentTimes",
				record);
		return (Integer)obj;
	}

	@Override
	public Integer countCurrentMonthPaymentTimes(FundoutOrder record) {
		Object obj = getSqlMapClientTemplate()
		.queryForObject(
				"FO_FUNDOUT_ORDER.CountCurrentMonthPaymentTimes",
				record);
		return (Integer)obj;
	}
}