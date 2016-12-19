package com.pay.fo.order.dao.batchpayment;

import java.util.Map;

import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.dto.base.OrderInfoDTO;
import com.pay.inf.dao.Page;

/**
 * @author NEW
 * 
 */
public interface BatchPayToBankQueryDetailDAO {

	/**
	 * 查询批量付款到银行详细
	 * 
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page<FundoutOrderDTO> queryPayToBankBatchDetail(Map<String, Object> map,
			Integer pageNo, Integer pageSize);

	Page<OrderInfoDTO> queryPayToAcctBatchDetail(Map<String, Object> map,
			Integer pageNo, Integer pageSize);

}
