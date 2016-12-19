/**
 * LocalOrderQueryServiceApiImpl.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.service.batchrepair.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hnapay.fi.dto.batchrepair.api.LocalOrderDTO;

/**
 * 测试用
 * latest modified time : 2011-8-24 下午5:48:30
 * @author bigknife
 */
public class ABCLocalOrderQueryServiceApiImpl implements LocalOrderQueryServiceApi {

	/* (non-Javadoc)
	 * @see com.hnapay.fi.service.batchrepair.api.LocalOrderQueryServiceApi#queryLocalOrderToRepair(java.lang.String, java.util.Date, java.util.Date)
	 */
	@Override
	public List<LocalOrderDTO> queryLocalOrderToRepair(String channel,
			Date start, Date end) {
		
		LocalOrderDTO localOrder = new LocalOrderDTO();
		localOrder.setDepositProtocolNo("1111108261311079449");
		
		List<LocalOrderDTO> result = new ArrayList<LocalOrderDTO>();
		for(int i = 0; i < 1; i ++){
			result.add(localOrder);
		}
		
		return result;
	}

}
