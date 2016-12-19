package com.pay.txncore.service.chargeback;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.txncore.model.BouncedReasonMapping;
/**
 * 拒付原因映射
 * 2016年5月24日 15:23:18
 * delin.dong
 */
public interface BouncedReasonMappingService {

	List<BouncedReasonMapping> queryBouncedReasonMapping(
			BouncedReasonMapping bouncedReasonMapping, Page page);
	
	void  addBouncedReasonMapping(BouncedReasonMapping bouncedReasonMapping);
}
