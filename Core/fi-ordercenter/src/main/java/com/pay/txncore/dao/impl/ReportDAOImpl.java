/**
 * 
 */
package com.pay.txncore.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.dao.ReportDAO;

/**
 * @author chaoyue
 *
 */
public class ReportDAOImpl extends BaseDAOImpl implements ReportDAO {

	@Override
	public List<Map> queryParnterReport(Map<String, String> paraMap) {

		return super.findByCriteria("queryPartnerReport", paraMap);
	}

}
