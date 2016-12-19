/**
 *  File: MasspayImportRecordDAOImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-26     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.batchpaytoaccount.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.pay.fundout.withdraw.dao.batchpaytoaccount.MasspayImportRecordDAO;
import com.pay.fundout.withdraw.dto.batchpaytoaccount.MasspayImportRecordDTO;
import com.pay.fundout.withdraw.model.batchpaytoaccount.MasspayImportRecord;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author darv
 * 
 */
public class MasspayImportRecordDAOImpl extends
		BaseDAOImpl<MasspayImportRecord> implements MasspayImportRecordDAO {

	@Override
	public long createMasspayImportRecord(
			MasspayImportRecord masspayImportRecord) {
		return (Long) this.create("create",
				masspayImportRecord);
	}

	@Override
	public void batchCreateImporRecord(final List list) {
		this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			@Override
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				for (int i = 0; i < list.size(); i++) {
					MasspayImportRecordDTO record = (MasspayImportRecordDTO) list
							.get(i);
					MasspayImportRecord masspayImportRecord = new MasspayImportRecord();
					try {
						BeanUtils.copyProperties(masspayImportRecord, record);
					} catch (Exception e) {
						e.printStackTrace();
					}
					executor.insert(namespace.concat("create"),
							masspayImportRecord);
					if ((i + 1) % 100 == 0) {
						executor.executeBatch();
					}
				}
				executor.executeBatch();
				return null;
			}
		});
	}

	@Override
	public List getImportRecordListByFileKyAll(Map params) {
		return this
				.getSqlMapClientTemplate()
				.queryForList(
						this.namespace.concat("getImportRecordListByFileKyAll"),
						params);
	}

	@Override
	public Integer getImportRecordListByFileKyCount(Map params) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				this.namespace.concat("getImportRecordListByFileKyCount"),
				params);
	}

	@Override
	public List getImportRecordListByFileKyPage(Map params) {
		return this.getSqlMapClientTemplate().queryForList(
				this.namespace.concat("getImportRecordListByFileKyPage"),
				params);
	}

	@Override
	public Long getErrorAmountByFileKy(Map params) {
		return (Long) this.getSqlMapClientTemplate().queryForObject(
				this.namespace.concat("getErrorAmountByFileKy"), params);
	}

	@Override
	public int updateStatus(MasspayImportRecordDTO importRecord) {
		return getSqlMapClientTemplate().update(
				namespace.concat("updateStatus"), importRecord);
	}
}
