package com.pay.base.service.matrixcard.bind.rules.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.base.common.helper.matrixcard.ErrorCodeMatrixExceptionEnum;
import com.pay.base.common.helper.matrixcard.MatrixCardStatus;
import com.pay.base.dto.matrixcard.MatrixCardDto;
import com.pay.base.dto.matrixcard.MatrixCardVfyDto;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.base.service.matrixcard.IMatrixCardService;
import com.pay.base.service.matrixcard.bind.rules.IMatrixCardBindRuleService;

public class MatrixCardBindRuleService implements IMatrixCardBindRuleService {

	private IMatrixCardService matrixCardService;
	private Log log = LogFactory.getLog(MatrixCardBindRuleService.class);

	// 绑定卡验证：验证卡状态及卡绑定使用次数
	@Override
	public void validate(MatrixCardVfyDto matrixCardVfyDto) throws MatrixCardException {
		String serialNo = matrixCardVfyDto.getSerialNo();
		MatrixCardDto dto = matrixCardService.findBySerialNo(serialNo);
		if (null == dto) {
			log.debug("未发现序列号");
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.COMMON_SERIALNO_NOTEXISTS, "matrixcard serialno is not exists");
		}
		if (dto.getStatus() != MatrixCardStatus.CREATE.getValue()) {
			log.debug("卡状态异常");
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.VFY_CARD_STATUS_ERROR, "matrixcard vfy  status error");
		}

	}

	public IMatrixCardService getMatrixCardService() {
		return matrixCardService;
	}

	public void setMatrixCardService(IMatrixCardService matrixCardService) {
		this.matrixCardService = matrixCardService;
	}

}
