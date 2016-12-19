/**
 *  <p>File: ExternalFacadeDaoImpl.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.poss.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.dao.ExternalFacadeDao;
import com.pay.poss.dto.fi.IncomeDetailParaDTO;
import com.pay.poss.dto.fi.PayOnlineDetailDTO;
import com.pay.poss.dto.fi.QueryDepositDetalisDTO;
import com.pay.poss.dto.fi.RefundOnlineDTO;

public class ExternalFacadeDaoImpl extends BaseDAOImpl implements
		ExternalFacadeDao {

	@Override
	public List<QueryDepositDetalisDTO> queryDepositDetails(
			Map<String, Object> modelParamMap, int pageSize, int pageNo)
			throws Exception {
		int offset = (pageNo - 1) * pageSize;
		List<QueryDepositDetalisDTO> list = null;
		list = getSqlMapClientTemplate().queryForList(
				"fundout-gatewayFacade.fundout-queryDepositDetail",
				modelParamMap, offset, pageSize);
		return list;
	}

	@Override
	public Integer queryDepositDetailsCount(Map<String, Object> modelParamMap)
			throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"fundout-gatewayFacade.fundout-queryDepositDetailCount",
				modelParamMap);
	}

	@Override
	public Map<String, String> queryAllOrgName() {

		return getSqlMapClientTemplate().queryForMap(
				"fundout-gatewayFacade.fundout-fiBankChannelQuery", null,
				"ALIAS", "ORGNAME");
	}

	@Override
	public List<PayOnlineDetailDTO> findByTradeOrderInfoId(String fiOrderId) {
		return getSqlMapClientTemplate().queryForList(
				"fundout-gatewayFacade.fundout-findByTradeOrderInfoId",
				fiOrderId);
	}

	@Override
	public List<RefundOnlineDTO> findByRefundOrderInfoId(
			String refundOrderInfoId) {

		return getSqlMapClientTemplate().queryForList(
				"fundout-gatewayFacade.fundout-findByRefundOrderInfoId",
				refundOrderInfoId);
	}

	@Override
	public Page<Map<String, Object>> queryIncomeDetailList(
			IncomeDetailParaDTO incomeDetailParaDTO, Integer pageNo,
			Integer pageSize) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageSize);
		page.setPageNo(pageNo);
		return super.findByQuery("fundout-gatewayFacade.queryIncomeDetailList",
				page, incomeDetailParaDTO);
	}

	@Override
	public Map<String, Object> querySingleIncomeDetail(Map<String, Object> map) {
		return (Map<String, Object>) super.findObjectByCriteria(
				"fundout-gatewayFacade.querySingleIncomeDetail", map);
	}

}
