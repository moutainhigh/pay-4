package com.pay.base.service.matrixcard.bind.rules;

import com.pay.base.dto.matrixcard.MatrixCardVfyDto;
import com.pay.base.exception.matrixcard.MatrixCardException;

public interface IMatrixCardBindRuleService {
	
	/**口令卡验证规则
	 * @param matrixCardVfyDto
	 * @throws MatrixCardException
	 */
	public void validate(MatrixCardVfyDto matrixCardVfyDto) throws MatrixCardException;
}
