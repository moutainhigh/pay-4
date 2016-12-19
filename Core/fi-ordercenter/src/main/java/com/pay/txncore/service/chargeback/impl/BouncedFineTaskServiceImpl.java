package com.pay.txncore.service.chargeback.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.model.BouncedRateListVO;
import com.pay.txncore.service.chargeback.BouncedFineTaskService;

public class BouncedFineTaskServiceImpl implements BouncedFineTaskService{
	
	private BaseDAO bouncedFineDAO;
	
	@Override
	public List<Map<String,Object>> queryFineRuleAndRate() {
		return bouncedFineDAO.findAll("queryFineRuleAndRate");
	}

	public void setBouncedFineDAO(BaseDAO bouncedFineDAO) {
		this.bouncedFineDAO = bouncedFineDAO;
	}

	@Override
	public void insertBouncedFine(Map<String, Object> fineRuleAndRate) {
		Object create = bouncedFineDAO.create(fineRuleAndRate);
	}

	@Override
	public List<Map> findBouncedFineOrder(Map<String, String> params) {
		return bouncedFineDAO.findByCriteria("findBouncedFineOrder", params);
	}

	@Override
	public void updateBouncedFine(Map map) {
		 bouncedFineDAO.update("updateBouncedFine", map);
	}

	@Override
	public void batchCreate(List<BouncedRateListVO> list) {
		bouncedFineDAO.batchCreate("createRateList", list);
		
	}

	@Override
	public void updateFineRuleAndRate(Map map) {
		bouncedFineDAO.update("updateFineRuleAndRate",map);
	}

	@Override
	public Long queryBouncedFineOrderNo() {
		List<Map> bouncedFine = bouncedFineDAO.findAll("queryBouncedFineNo");
		return Long.valueOf((String)bouncedFine.get(0).get("id"));
	}
	
	/**
	 
	 public void batchCreate(final List<BouncedRateListVO> list)
			throws DataAccessException {
		super.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				int batch = 0;
				for (BouncedRateListVO acct : list) {
					executor.insert(namespace.concat("createRateList"), acct);
					batch++;
					if (batch == 100) {
						executor.executeBatch();
						batch = 0;
					}
				}
				executor.executeBatch();
				return "";
			}
		});
	}
	 */
	
}
