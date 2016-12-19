package com.pay.base.service.matrixcard.request.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.base.common.helper.matrixcard.ErrorCodeMatrixExceptionEnum;
import com.pay.base.common.helper.matrixcard.MatrixCardStatus;
import com.pay.base.common.helper.matrixcard.MatrixCardTransInfoValStatus;
import com.pay.base.dto.matrixcard.MatrixCardDto;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.base.service.matrixcard.IMatrixCardService;
import com.pay.base.service.matrixcard.bind.impl.MatrixCardBindTxServiceImpl;
import com.pay.base.service.matrixcard.request.IMatrixCardReqTxService;
import com.pay.base.service.matrixcard.request.rules.IMatrixCardReqRuleService;
import com.pay.base.service.matrixcard.transmgr.IMatrixCardTransInfoService;
import com.pay.base.service.matrixcard.transmgr.MatrixCardTransMgrService;

public class MatrixCardReqTxServiceImpl implements IMatrixCardReqTxService {

	MatrixCardTransMgrService matrixCardTransMgrService;

	IMatrixCardReqRuleService matrixCardReqRuleService;

	IMatrixCardService matrixCardService;

	IMatrixCardTransInfoService matrixCardTransInfoService;

	private Log log = LogFactory.getLog(MatrixCardBindTxServiceImpl.class);

	@Override
	public MatrixCardDto showRequestMatrixCard(Long memberCode) throws MatrixCardException {
		MatrixCardDto matrixCard = matrixCardService.selectmatrixcardByTransInfoMemberCode(memberCode);
//		if (null == info || null == info.getMcId()) {
//			log.debug("该用户的口令卡操作信息不存在");
//			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.UNBIND_TRANS_INFO_NOT_FOUND, "transinfo is null");
//		}
//		MatrixCardDto matrixCard = matrixCardService.findById(info.getMcId());
		if (null == matrixCard) {
			log.debug("申请的口令卡不存在");
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.MATRIX_CARD_NOT_FOUND, "matrixcard is null");
		}
		if(MatrixCardStatus.CREATE.getValue()!=matrixCard.getStatus()){
			log.debug("申请的的状态不正确");
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.BIND_MATRIXCARD_STATUS_ERROR, "matrixcard status is error");
		}
		return matrixCard;
	}

	@Override
	public MatrixCardDto requestMatrixCardRdTx(MatrixCardTransInfoDto transInfoDto) throws MatrixCardException {
		try {
			matrixCardReqRuleService.validate(transInfoDto);
			transInfoDto = matrixCardTransMgrService.beginTransInIsolatedRdTx(transInfoDto);
			MatrixCardDto matrixCardDto = matrixCardService.create();
			transInfoDto.setMcId(matrixCardDto.getId());
			transInfoDto.setValStatus(MatrixCardTransInfoValStatus.FINISHED.getValue());
//			matrixCardTransInfoService.updateMatrixCardTransInfo(transInfoDto);
			return matrixCardDto;
		}
		catch (MatrixCardException e) {
			log.debug(e);
			transInfoDto.setValStatus(MatrixCardTransInfoValStatus.FAIL.getValue());
			throw e;
		}
		finally {
			//判断是否有开户申请的事务
			if(null!=transInfoDto.getTransId()){
				matrixCardTransMgrService.finishTransInIsolatedRdTx(transInfoDto.getTransId(), transInfoDto);
			}
		}
	}

	public MatrixCardTransMgrService getMatrixCardTransMgrService() {
		return matrixCardTransMgrService;
	}

	public void setMatrixCardTransMgrService(MatrixCardTransMgrService matrixCardTransMgrService) {
		this.matrixCardTransMgrService = matrixCardTransMgrService;
	}

	public IMatrixCardReqRuleService getMatrixCardReqRuleService() {
		return matrixCardReqRuleService;
	}

	public void setMatrixCardReqRuleService(IMatrixCardReqRuleService matrixCardReqRuleService) {
		this.matrixCardReqRuleService = matrixCardReqRuleService;
	}

	public IMatrixCardService getMatrixCardService() {
		return matrixCardService;
	}

	public void setMatrixCardService(IMatrixCardService matrixCardService) {
		this.matrixCardService = matrixCardService;
	}

	public IMatrixCardTransInfoService getMatrixCardTransInfoService() {
		return matrixCardTransInfoService;
	}

	public void setMatrixCardTransInfoService(IMatrixCardTransInfoService matrixCardTransInfoService) {
		this.matrixCardTransInfoService = matrixCardTransInfoService;
	}

}
