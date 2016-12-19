package com.pay.base.service.matrixcard.unbind.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.base.common.helper.matrixcard.ErrorCodeMatrixExceptionEnum;
import com.pay.base.common.helper.matrixcard.MatrixCardTransInfoValStatus;
import com.pay.base.common.helper.matrixcard.MatrixCardTransType;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.matrixcard.MatrixCardDto;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.base.service.matrixcard.IMatrixCardService;
import com.pay.base.service.matrixcard.bind.MatrixCardBindTxService;
import com.pay.base.service.matrixcard.reset.MatrixCardResetTxService;
import com.pay.base.service.matrixcard.transmgr.IMatrixCardTransInfoService;
import com.pay.base.service.matrixcard.transmgr.MatrixCardTransMgrService;
import com.pay.base.service.matrixcard.unbind.MatrixCardUnBindTxService;

public class MatrixCardUnBindTxServiceImpl implements MatrixCardUnBindTxService {

	private MatrixCardTransMgrService matrixCardTransMgrService;
	private IMatrixCardService matrixCardService;
	private MatrixCardBindTxService matrixCardBindTxService;
	private MatrixCardResetTxService matrixCardResetTxService;
	private IMatrixCardTransInfoService matrixCardTransInfoService;	
	private Log log = LogFactory.getLog(MatrixCardUnBindTxServiceImpl.class);

	
	@Override
	public void unBindRdTx(String serialNo, OperatorInfo operatorInfo) throws MatrixCardException {
		MatrixCardTransInfoDto infoDto = matrixCardResetTxService.getValidResetTrans(operatorInfo.getMemberCode(), MatrixCardTransType.UNBIND.getValue());
		MatrixCardDto matrixCardDto = matrixCardService.findByBindMemberCode(operatorInfo.getMemberCode());
		if (null == infoDto) {
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.UNBIND_TRANS_INFO_NOT_FOUND, ErrorCodeMatrixExceptionEnum.UNBIND_TRANS_INFO_NOT_FOUND.getMessage());
		}
		try {
			// 执行解绑
			if (matrixCardDto != null) {
				matrixCardBindTxService.unBind(matrixCardDto.getSerialNo(), operatorInfo);
				// 完成该事务
				infoDto.setValStatus(MatrixCardTransInfoValStatus.FINISHED.getValue());
//				matrixCardTransInfoService.updateMatrixCardTransInfo(infoDto);
			}
		}
		catch (MatrixCardException e) {
			infoDto.setValStatus(MatrixCardTransInfoValStatus.FAIL.getValue());
			log.debug(e);
			throw e;
		}
		finally {
			// 结束解绑事务
			matrixCardTransMgrService.finishTransInIsolatedRnTx(infoDto.getTransId(),infoDto);
		}
	}

	@Override
	public MatrixCardTransInfoDto processUnBindRnTx(MatrixCardTransInfoDto transDto) throws MatrixCardException {
		MatrixCardTransInfoDto transInfoDto = null;
		try {
			transInfoDto = matrixCardTransMgrService.beginTransInIsolatedRdTx(transDto);
		}
		catch (MatrixCardException e) {
			log.error(e);
			throw e;
		}
		return transInfoDto;

	}
	
	public void setMatrixCardTransMgrService(MatrixCardTransMgrService matrixCardTransMgrService) {
    	this.matrixCardTransMgrService = matrixCardTransMgrService;
    }

	public void setMatrixCardService(IMatrixCardService matrixCardService) {
    	this.matrixCardService = matrixCardService;
    }

	public void setMatrixCardBindTxService(MatrixCardBindTxService matrixCardBindTxService) {
    	this.matrixCardBindTxService = matrixCardBindTxService;
    }

	public void setMatrixCardResetTxService(MatrixCardResetTxService matrixCardResetTxService) {
    	this.matrixCardResetTxService = matrixCardResetTxService;
    }

	public void setMatrixCardTransInfoService(IMatrixCardTransInfoService matrixCardTransInfoService) {
    	this.matrixCardTransInfoService = matrixCardTransInfoService;
    }

}
