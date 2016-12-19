package com.pay.acc.service.account.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acct.exception.AcctServiceException;
import com.pay.acc.acct.service.AcctService;
import com.pay.acc.common.MaConstant;
import com.pay.acc.commons.AmountByEnum;
import com.pay.acc.service.account.AccountBalanceHandlerService;
import com.pay.acc.service.account.FlushesBalanceService;
import com.pay.acc.service.account.FrozenAmountService;
import com.pay.acc.service.account.constantenum.FlushesStatus;
import com.pay.acc.service.account.dto.CalFeeDetailDto;
import com.pay.acc.service.account.dto.CalFeeReponseDto;
import com.pay.acc.service.account.exception.MaAcctBalanceException;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.pe.helper.CRDRType;

public class FrozenAmountServiceImpl implements FrozenAmountService {
	
	private AcctService acctService;
	private AccountBalanceHandlerService accountBalanceHandlerService;
	private FlushesBalanceService flushesBalanceService;

	private Log log = LogFactory.getLog(FrozenAmountServiceImpl.class);

	@Override
	public boolean doUnFrozenAmountRnTx(CalFeeReponseDto calRequestDto,
			String flushesOrderId, String orderId, Integer dealCode, Long amount,
			Integer dealType) throws MaAcctBalanceException {

		// 新增日志
		Long flushesLogId = this.flushesBalanceService.insertFlushesLogRnTx(
				flushesOrderId, orderId, dealCode, amount, dealType);
		// 验证传入的参数
		this.flushesBalanceService.handlerCheckFlushesParameter(flushesOrderId,
				orderId, dealCode, amount, dealType);

		// //生成CalFeeReponseDto对象
		// CalFeeReponseDto
		// calRequestDto=this.flushesBalanceService.generateCalFeeReponseDto(flushesOrderId,
		// orderId, dealCode, amount, dealType);
		// 更新余额，如果不是解冻金额，则更新余额已经调过记账
		Long seqId = this.flushesBalanceService.flushesFrzoenBalance(
				calRequestDto, dealType, flushesLogId);

		this.flushesBalanceService.updateFlushesLogRnTx(orderId, dealCode,
				amount, FlushesStatus.SUCCESS.getCode());

		List<CalFeeDetailDto> calFeeDetailDtos = calRequestDto
				.getCalFeeDetailDtos();
		for (CalFeeDetailDto calFeeDetailDto : calFeeDetailDtos) {
			// 如果是中间账户，则不加冻结金额
			if (MaConstant.SELL_ACC_CODE.equals(calFeeDetailDto.getAcctcode())) {
				continue;
			} else {
				// 更新支付冻结金额
				this.handlerUpdateFrozenBalance(calFeeDetailDto.getValue(),
						calFeeDetailDto.getMaBlanceBy(),
						calFeeDetailDto.getAcctcode(),
						calFeeDetailDto.getCrdr(), orderId, dealCode);
			}
		}
		// 调用PE接口记账
		this.accountBalanceHandlerService.sendMsgToPe(calRequestDto, seqId);
		return true;
	}

	@Override
	public boolean doFrozenAmountRnTx(CalFeeReponseDto updateBalanceRequestDto,
			Integer payType) throws MaAcctBalanceException {
		// 先调更新余额接口
		Long seqId = this.accountBalanceHandlerService.updateAccBalance(
				updateBalanceRequestDto, payType);
		List<CalFeeDetailDto> calFeeDetailDtos = updateBalanceRequestDto
				.getCalFeeDetailDtos();
		for (CalFeeDetailDto calFeeDetailDto : calFeeDetailDtos) {
			// 如果是中间账户，则不加冻结金额
			if (MaConstant.SELL_ACC_CODE.equals(calFeeDetailDto.getAcctcode())) {
				continue;
			} else {
				// 更新支付冻结金额
				this.handlerUpdateFrozenBalance(calFeeDetailDto.getValue(),
						calFeeDetailDto.getMaBlanceBy(),
						calFeeDetailDto.getAcctcode(),
						calFeeDetailDto.getCrdr(), null, null);
			}
		}
		// 调用PE接口记账
		this.accountBalanceHandlerService.sendMsgToPe(updateBalanceRequestDto,
				seqId);

		return true;
	}

	@Override
	public boolean doUnFrozenAmountRnTx(
			CalFeeReponseDto updateBalanceRequestDto, Integer payType)
			throws MaAcctBalanceException {
		// 先调更新余额接口
		Long seqId = this.accountBalanceHandlerService.updateAccBalance(
				updateBalanceRequestDto, payType);
		List<CalFeeDetailDto> calFeeDetailDtos = updateBalanceRequestDto
				.getCalFeeDetailDtos();
		for (CalFeeDetailDto calFeeDetailDto : calFeeDetailDtos) {
			// 如果是中间账户，则不加冻结金额
			if (MaConstant.SELL_ACC_CODE.equals(calFeeDetailDto.getAcctcode())) {
				continue;
			} else {
				// 更新支付冻结金额
				this.handlerUpdateFrozenBalance(calFeeDetailDto.getValue(),
						calFeeDetailDto.getMaBlanceBy(),
						calFeeDetailDto.getAcctcode(),
						calFeeDetailDto.getCrdr(), null, null);
			}
		}
		// 调用PE接口记账
		this.accountBalanceHandlerService.sendMsgToPe(updateBalanceRequestDto,
				seqId);

		return true;
	}

	/**
	 * 更新借方余额 冻结金额的方向与余额的方向相反
	 * 
	 * @param amount
	 * @param acctCode
	 * @throws MaAcctBalanceException
	 */
	private void handlerUpdateFrozenBalance(Long chargeAmount,
			Integer maBlanceBy, String acctCode, Integer crdr, String orderId,
			Integer dealCode) throws MaAcctBalanceException {
		boolean result = false;
		try {
			log.info("更新账号[" + acctCode + "]的冻结金额");
			if (crdr == CRDRType.DEBIT.getValue()) {// 借
				// 余额方向为加
				if (maBlanceBy == AmountByEnum.ADD.getCode()) {// 不管是冲正，还是更新都统一的处理
					log.info("关于借方账户[" + acctCode + "]余额方向【" + maBlanceBy + "】");
					result = this.acctService.updateFrozenAmountWithAcctCode(
							acctCode, (-1) * chargeAmount, orderId, dealCode);
					;
				} else if (maBlanceBy == AmountByEnum.REDUCE.getCode()) {// 余额方向为减
					log.info("关于借方账户[" + acctCode + "]余额方向【" + maBlanceBy + "】");
					result = this.acctService.updateFrozenAmountWithAcctCode(
							acctCode, chargeAmount, orderId, dealCode);
					;
				}
			} else if (crdr == CRDRType.CREDIT.getValue()) {// 贷
				// 余额方向为加
				if (maBlanceBy == AmountByEnum.ADD.getCode()) {// 不管是冲正，还是更新都统一的处理
					log.info("关于更新贷方[" + acctCode + "]余额方向【" + maBlanceBy + "】");
					result = this.acctService.updateFrozenAmountWithAcctCode(
							acctCode, (-1) * chargeAmount, orderId, dealCode);
				} else if (maBlanceBy == AmountByEnum.REDUCE.getCode()) {// 余额方向为减
					log.info("关于更新贷方[" + acctCode + "]余额方向【" + maBlanceBy + "】");
					result = this.acctService.updateFrozenAmountWithAcctCode(
							acctCode, chargeAmount, orderId, dealCode);
				}
			}
		} catch (AcctServiceException e) {
			log.error(e.getMessage(), e);
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.INPUT_PARA_NULL, e.getMessage(), e);
		} catch (Exception e) {
			log.error("系统异常", e);
			throw new MaAcctBalanceException(ErrorExceptionEnum.UNKNOW_ERROR,
					"系统异常", e);
		}
		if (!result) {
			log.error("账户" + acctCode + "更新冻结金额操作失败");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.ACCT_BALANCE_ERROR, "更新冻结金额操作失败");
		}

	}

	public AccountBalanceHandlerService getAccountBalanceHandlerService() {
		return accountBalanceHandlerService;
	}

	public void setAccountBalanceHandlerService(
			AccountBalanceHandlerService accountBalanceHandlerService) {
		this.accountBalanceHandlerService = accountBalanceHandlerService;
	}

	public AcctService getAcctService() {
		return acctService;
	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	public FlushesBalanceService getFlushesBalanceService() {
		return flushesBalanceService;
	}

	public void setFlushesBalanceService(
			FlushesBalanceService flushesBalanceService) {
		this.flushesBalanceService = flushesBalanceService;
	}

}
