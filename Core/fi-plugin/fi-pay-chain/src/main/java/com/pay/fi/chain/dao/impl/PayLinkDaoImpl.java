/**
 * 
 */
package com.pay.fi.chain.dao.impl;


import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.pay.fi.chain.condition.paylink.PayLinkCondition;
import com.pay.fi.chain.dao.PayLinkDao;
import com.pay.fi.chain.model.PayLink;
import com.pay.fi.chain.model.PayLinkAttrib;
import com.pay.fi.chain.model.PayLinkBaseInfo;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author PengJiangbo
 *
 */
@SuppressWarnings("deprecation")
public class PayLinkDaoImpl<T> extends BaseDAOImpl<T> implements
		PayLinkDao<T> {
	
	@Override
	public Long baseInfoSave(final PayLinkBaseInfo payLinkBaseInfo) {
		return (Long) this.getSqlMapClientTemplate().insert(this.getNamespace().concat("baseInfoSave"), payLinkBaseInfo);
	}

	@Override
	public PayLinkBaseInfo payLinkBaseInfoQuery(final Long memberCode) {
		
		return (PayLinkBaseInfo) this.getSqlMapClientTemplate().queryForObject(this.getNamespace().concat("payLinkBaseInfoQuery"), memberCode) ;
	}

	@Override
	public int updateContact(final Long infoId, final Timestamp updateDate, final String contact) {
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		hMap.put("infoId", infoId) ;
		hMap.put("updateDate", updateDate) ;
		hMap.put("contact", contact) ;
		return this.getSqlMapClientTemplate().update(this.getNamespace().concat("updateContact"), hMap) ;
	}

	@Override
	public Long shoptermSave(final PayLinkBaseInfo payLinkBaseInfo) {
		return (Long) this.getSqlMapClientTemplate().insert(this.getNamespace().concat("shoptermSave"), payLinkBaseInfo) ;
	}

	@Override
	public int updateShopTermById(final Long infoId, final Timestamp updateTime,
			final String shoptermName, final String shoptermUrl) {
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		hMap.put("infoId", infoId) ;
		hMap.put("updateTime", updateTime) ;
		hMap.put("shoptermName", shoptermName) ;
		hMap.put("shoptermUrl", shoptermUrl) ;
		return this.getSqlMapClientTemplate().update(this.getNamespace().concat("updateShopTermById"), hMap) ;
	}

	@Override
	public Long payLinkSave(final PayLink payLink) {
		return (Long) this.getSqlMapClientTemplate().insert(this.getNamespace().concat("payLinkSave"), payLink) ;
	}

	@Override
	public Long payLinkAttribSave(final PayLinkAttrib payLinkAttrib) {
		return (Long) this.getSqlMapClientTemplate().insert(this.getNamespace().concat("payLinkAttribSave"), payLinkAttrib) ;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean payLinkAttribBatchSave(final List<PayLinkAttrib> payLinkAttribs) {
		this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			@Override
			public Object doInSqlMapClient(final SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				int batch = 0;
				for(PayLinkAttrib payLinkAttrib : payLinkAttribs){
					executor.insert(namespace.concat("payLinkAttribSave"), payLinkAttrib) ;
					batch ++ ;
					if(batch == 100){
						executor.executeBatch();
						batch=0;
					}
					executor.executeBatch() ;
				}
				return null;
			}
		}) ;
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayLink> queryPayLinkByCondition(
			final PayLinkCondition payLinkCondition, final Page<?> page) {
		return (List<PayLink>) super.findByCriteria("findByCriteria", payLinkCondition, page) ;
	}

	@Override
	public int updateStatusByNo(final Long payLinkNo, final int status, final Timestamp invalidTime) {
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		hMap.put("payLinkNo", payLinkNo) ;
		hMap.put("status", status) ;
		hMap.put("invalidTime", invalidTime) ;
		return this.getSqlMapClientTemplate().update(this.getNamespace().concat("updateStatusByNo"), hMap) ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayLink> queryPayLinkByCondition(
			final PayLinkCondition payLinkCondition) {
		return (List<PayLink>) super.findByCriteria("findByCriteria", payLinkCondition) ;
	}

	@Override
	public PayLink findPayLinkByPayLinkNo(final Long payLinkNo) {
		return (PayLink) this.getSqlMapClientTemplate().queryForObject(this.getNamespace().concat("findPayLinkByPayLinkNo"), payLinkNo) ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayLinkAttrib> findPayLinkAttribsByPayLinkNo(final Long payLinkNo) {
		return this.getSqlMapClientTemplate().queryForList(this.getNamespace().concat("findPayLinkAttribsByPayLinkNo"), payLinkNo) ;
	}

	@Override
	public PayLink findPayLinkByName(final String payLinkName) {
		return (PayLink) this.getSqlMapClientTemplate().queryForObject(this.getNamespace().concat("findPayLinkByName"), payLinkName) ;
	}

}
