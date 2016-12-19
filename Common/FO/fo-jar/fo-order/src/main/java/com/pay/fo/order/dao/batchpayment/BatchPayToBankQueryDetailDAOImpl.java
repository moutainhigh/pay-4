package com.pay.fo.order.dao.batchpayment;

import java.util.Map;

import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.dto.base.OrderInfoDTO;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author NEW
 * 
 */
public class BatchPayToBankQueryDetailDAOImpl extends BaseDAOImpl implements
		BatchPayToBankQueryDetailDAO {

	/**
	 * 查询批量付款到银行详细
	 * 
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Override
	public Page<FundoutOrderDTO> queryPayToBankBatchDetail(
			Map<String, Object> map, Integer pageNo, Integer pageSize) {
		Page<FundoutOrderDTO> page = new Page<FundoutOrderDTO>(pageSize);
		page.setPageNo(pageNo);
		return super.findByQuery("FO_FUNDOUT_ORDER.queryPayToBankBatchDetail",
				page, map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.fo.order.dao.batchpayment.BatchPayToBankQueryDetailDAO#
	 * queryPayToAcctBatchDetail(java.util.Map, java.lang.Integer,
	 * java.lang.Integer)
	 */
	@Override
	public Page<OrderInfoDTO> queryPayToAcctBatchDetail(
			Map<String, Object> map, Integer pageNo, Integer pageSize) {

		Page<OrderInfoDTO> page = new Page<OrderInfoDTO>(pageSize);
		page.setPageNo(pageNo);
		return super.findByQuery("FO_FUNDOUT_ORDER.queryPayToAcctBatchDetail",
				page, map);
	}

}
