package com.pay.risk.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.risk.dao.ExpressTrackingDAO;
import com.pay.risk.model.ExpressTracking;

public class ExpressTrackingDAOImpl extends BaseDAOImpl implements
		ExpressTrackingDAO {

	private Log log = LogFactory.getLog(ExpressTrackingDAOImpl.class);

	@Override
	public List<ExpressTracking> queryTrackingDetailList(Map queryDetailPara,
			Integer pageNo, Integer pageSize) {
		int page_offset = (pageNo - 1) * pageSize;
		try {
			List<ExpressTracking> queryDetailList = getSqlMapClientTemplate()
					.queryForList("EXPRESS_TRACKING.queryTrackingDetails",
							queryDetailPara, page_offset, pageSize);
			return queryDetailList;
		} catch (Exception e) {
			log.error("======>:" + e);
			return null;
		}
	}

	@Override
	public Map<String, Object> countTrackingDetailList(Map queryDetailPara) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject(
				"EXPRESS_TRACKING.queryTrackingDetailsCount", queryDetailPara);
	}

	@Override
	public List<ExpressTracking> queryTrackingDetailList(Map queryDetailPara) {
		try {
			List<ExpressTracking> queryDetailList = getSqlMapClientTemplate()
					.queryForList("EXPRESS_TRACKING.queryTrackingDetails",
							queryDetailPara);
			return queryDetailList;
		} catch (Exception e) {
			log.error("======>:" + e);
			return null;
		}
	}

	@Override
	public boolean updateTrackingInfo(String sql,
			ExpressTracking expressTracking) {
		if (getSqlMapClientTemplate().update(namespace.concat(sql),
				expressTracking) == 1) {
			return true;
		} else
			return false;
	}

}
