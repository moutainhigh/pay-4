package com.pay.base.service.matrixcard.request.rules;

import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;
import com.pay.base.exception.matrixcard.MatrixCardException;

/**
 * @author jim_chen
 * @version 
 * 2010-9-16 
 */
public interface IMatrixCardReqRuleService {
	
	/** 验证  同一IP地址当天申请数量是否超过上限 100
	 * @param transInfoDto
	 * @throws MatrixCardException
	 */
	public void validate(MatrixCardTransInfoDto transInfoDto) throws MatrixCardException;
}
