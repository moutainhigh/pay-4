package com.pay.acc.service.account.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.deal.dto.BalanceDealDto;
import com.pay.acc.deal.model.BalanceEntry;
import com.pay.acc.deal.service.BalanceDealService;
import com.pay.acc.deal.service.BalanceEntryService;
import com.pay.acc.service.account.AccountBalanceHandlerService;
import com.pay.acc.service.account.FlushesBalanceService;
import com.pay.acc.service.account.constantenum.FlushesStatus;
import com.pay.acc.service.account.dto.CalFeeDetailDto;
import com.pay.acc.service.account.dto.CalFeeReponseDto;
import com.pay.acc.service.account.dto.CalFeeRequestDto;
import com.pay.acc.service.account.exception.MaAcctBalanceException;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.acc.translog.model.FlushesLog;
import com.pay.acc.translog.service.FlushesLogService;

public class FlushesBalanceServiceImpl implements FlushesBalanceService {
	private FlushesLogService flushesLogService;
	private AccountBalanceHandlerService accountBalanceHandlerService;
	private BalanceEntryService balanceEntryService;
	private BalanceDealService balanceDealService;

	private Log log = LogFactory.getLog(AccountBalanceHandlerServiceImpl.class);

	@Override
	public boolean doFlushesBalanceNsTx(String flushesOrderId, String orderId,
			Integer dealCode, Long amount, Integer dealType)
			throws MaAcctBalanceException {
		// 新增日志
		Long flushesLogId = this.insertFlushesLogRnTx(flushesOrderId, orderId,
				dealCode, amount, dealType);
		// 生成CalFeeReponseDto对象
		CalFeeReponseDto calFeeReponseDto = this.generateCalFeeReponseDto(
				flushesOrderId, orderId, dealCode, amount, dealType);
		// 更新余额
		// this.accountBalanceHandlerService.doUpdateAcctBalanceRntx(calFeeReponseDto,
		// dealType);

		this.flushesFrzoenBalance(calFeeReponseDto, dealType, flushesLogId);
		this.updateFlushesLogRnTx(orderId, dealCode, amount,
				FlushesStatus.SUCCESS.getCode());

		return true;
	}

	@Override
	public Long flushesFrzoenBalance(CalFeeReponseDto calFeeReponseDto,
			Integer dealType, Long flushesLogId) throws MaAcctBalanceException {
		Long seqId = null;
		try {
			// 如果是解冻操作,更新余额不加事务
			// if(PayForEnum.UNFROZEN_AMOUNT.getCode()==dealType){
			// seqId=this.accountBalanceHandlerService.updateAccBalance(calFeeReponseDto,
			// dealType);
			// }else{
			// // 更新余额
			//
			// }
			this.accountBalanceHandlerService.doUpdateAcctBalanceRntx(
					calFeeReponseDto, dealType);
		} catch (MaAcctBalanceException ma) {
			throw ma;
		} catch (Exception e) {
			throw new MaAcctBalanceException(e);
		}
		return seqId;
	}

	@Override
	public void updateFlushesLogRnTx(String orderId, Integer dealCode,
			Long amount, Integer status) throws MaAcctBalanceException {
		// 冲正成功，更新日志的状态为成功
		boolean flag = this.flushesLogService.updateFlushesLog(orderId,
				dealCode, amount, FlushesStatus.SUCCESS.getCode());
		if (flag) {
			log.info("冲正成功，更新日志的状态为成功");
		} else {
			log.error("更新操作日志表" + orderId + "状态异常");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.ACCT_FLUSHES_LOG_ERROR, "更新操作日志表操作失败");
		}
	}

	public void updateFlshesStatus(Long flushesLogId, Integer status)
			throws MaAcctBalanceException {
		// 冲正成功，更新日志的状态为成功
		this.flushesLogService.updateFlushesLog(flushesLogId,
				FlushesStatus.SUCCESS.getCode());
		log.info("冲正成功，更新日志的状态为成功");
	}

	@Override
	public CalFeeReponseDto generateCalFeeReponseDto(String flushesOrderId,
			String orderId, Integer dealCode, Long amount, Integer dealType)
			throws MaAcctBalanceException {
		CalFeeReponseDto calFeeReponse = null;
		try {
			// 验证传入的参数
			this.handlerCheckFlushesParameter(flushesOrderId, orderId,
					dealCode, amount, dealType);

			// 查询原交易的deal信息
			BalanceDealDto balanceDealDto = balanceDealService
					.queryBalanceDealForFlushes(orderId, dealCode);
			if (null == balanceDealDto) {
				log.error("根据orderId : " + orderId + " dealCode : " + dealCode
						+ "查询交易明细对象BalanceDeal为空");
				throw new MaAcctBalanceException(
						ErrorExceptionEnum.INPUT_PARA_LIST_NULL, "余额信息列表数据为空");
			}
			// 查询原交易的分录信息
			List<BalanceEntry> balanceEntryList = balanceEntryService
					.queryBalanceEntryBySerialNo(orderId, dealCode);
			if (null == balanceEntryList || balanceEntryList.size() == 0) {
				log.error("根据orderId : " + orderId + " dealCode : " + dealCode
						+ "查询 分录对象BalanceEntry为空");
				throw new MaAcctBalanceException(
						ErrorExceptionEnum.NOT_ENTRY_LIST_NULL, "分录信息列表数据为空");
			}
			// 组装更新余额需要的参数CalFeeReponseDto,替换流水号,将交易金额反向
			calFeeReponse = this.handlerMakeUpCalFeeRepose(balanceDealDto,
					balanceEntryList, flushesOrderId);

		}

		catch (MaAcctBalanceException ma) {
			throw ma;
		} catch (Exception e) {
			throw new MaAcctBalanceException(e);
		}
		return calFeeReponse;
	}

	// public CalFeeReponseDto flushesFrzoenBalance(CalFeeReponseDto
	// calFeeReponseDto,Integer dealType,Long flushesLogId) throws
	// MaAcctBalanceException {
	// CalFeeReponseDto calFeeReponse = null;
	// try {
	//
	//
	// //如果是解冻操作,更新余额不加事务
	// if(PayForEnum.UNFROZEN_AMOUNT.equals(dealType)){
	// this.accountBalanceHandlerService.updateAccBalance(calFeeReponse,
	// dealType);
	// }else{
	// // 更新余额
	// this.accountBalanceHandlerService.doUpdateAcctBalanceRntx(calFeeReponse,
	// dealType);
	// }
	//
	// // 冲正成功，更新日志的状态为成功
	// this.flushesLogService.updateFlushesLog(flushesLogId,
	// FlushesStatus.SUCCESS.getCode());
	// log.info("冲正成功，更新日志的状态为成功");
	//
	// }
	//
	//
	// catch (MaAcctBalanceException ma) {
	// throw ma;
	// }
	// catch (Exception e) {
	// throw new MaAcctBalanceException(e);
	// }
	// return calFeeReponse;
	//
	// }

	@Override
	public Long insertFlushesLogRnTx(String flushesOrderId, String orderId,
			Integer dealCode, Long amount, Integer dealType)
			throws MaAcctBalanceException {
		Long flushesLogId = null;
		try {
			// 将参数加入冲正日志表
			FlushesLog flushesLog = createFlushesLog(flushesOrderId, orderId,
					dealCode, amount, dealType);
			log.info("新增操作日志");
			flushesLogId = this.flushesLogService.insertFlushesLog(flushesLog);// 新增日志

		} catch (Exception e) {
			throw new MaAcctBalanceException(e);
		}
		return flushesLogId;
	}

	public void handlerCheckFlushesParameter(String flushesOrderId,
			String orderId, Integer dealCode, Long amount, Integer dealType)
			throws MaAcctBalanceException {
		if (null == flushesOrderId || null == orderId || null == dealCode
				|| null == amount || null == dealType) {
			log.error("输入的参数不能为空");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.INPUT_PARA_NULL, "输入的参数不能为空");
		}
		// 交易类型只能为冲正
		// if (!(PayForEnum.FLUSHES.getCode() == dealType ||
		// PayForEnum.UNFROZEN_AMOUNT.getCode() ==dealType)) {
		// log.error("发起的不是冲正交易,请走更新余额的流程");
		// throw new MaAcctBalanceException(ErrorExceptionEnum.NOT_FLUSHES_DEAL,
		// "发起的不是冲正交易,请走更新余额的流程");
		// }
		int flushesStatus = flushesLogService.countFlushesStatus(
				flushesOrderId, orderId, dealCode);
		if (flushesStatus > 0) {
			log.error("流水号orderId : " + orderId + " dealCode : " + dealCode
					+ "已经冲正，不能再发起冲正");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.ORDER_FLUSHES_FINISHED,
					"该订单已经冲正，不能再发起冲正");
		}

	}

	/**
	 * 组装冲正CalFeeReponseDto对所有的 金额反向
	 * 
	 * @param balanceDealDto
	 * @param balanceEntryDtos
	 * @param flushesOrderId
	 * @return
	 */
	private CalFeeReponseDto handlerMakeUpCalFeeRepose(
			BalanceDealDto balanceDealDto, List<BalanceEntry> balanceEntryDtos,
			String flushesOrderId) {
		CalFeeReponseDto calFeeReponse = new CalFeeReponseDto();
		// fee
		calFeeReponse.setPayeeFee(-1 * balanceDealDto.getPayeeFee());
		calFeeReponse.setPayerFee(-1 * balanceDealDto.getPayerFee());
		calFeeReponse.setHasCaculatedPrice(balanceDealDto
				.getHasCaculatedPrice() == 1 ? true : false);
		// calFeeRequest
		CalFeeRequestDto calFeeRequest = new CalFeeRequestDto();
		// base
		// 将原交易的金额作反向操作
		calFeeRequest.setAmount(-1 * balanceDealDto.getAmount());
		calFeeRequest.setDealCode(balanceDealDto.getDealCode());
		calFeeRequest.setOrderAmount(-1 * balanceDealDto.getOrderAmount());
		// 将原交易流水号替换成冲正流水号
		calFeeRequest.setOrderId(flushesOrderId);
		calFeeRequest.setExchangeRate(balanceDealDto.getExchangeRate());
		calFeeRequest
				.setRequestDate(java.util.Calendar.getInstance().getTime());
		calFeeRequest.setTerminalType(balanceDealDto.getTerminalType());
		calFeeRequest.setSubmitAcctCode(balanceDealDto.getSubmitAcctCode());
		calFeeRequest.setPayMethod(balanceDealDto.getPayMethod());
		// payer
		calFeeRequest.setPayer(balanceDealDto.getPayer());
		calFeeRequest.setPayerAcctType(balanceDealDto.getPayerAcctType());
		calFeeRequest.setPayerCurrencyCode(balanceDealDto
				.getPayerCurrencyCode());
		calFeeRequest.setPayerFullMemberAcctCode(balanceDealDto
				.getPayerFullMemberAcctCode());
		calFeeRequest.setPayerMemberAcctCode(balanceDealDto
				.getPayerMemberAcctCode());
		calFeeRequest.setPayerOrgCode(balanceDealDto.getPayerOrgCode());
		calFeeRequest.setPayerOrgType(balanceDealDto.getPayerOrgType());
		calFeeRequest.setPayerServiceLevel(balanceDealDto
				.getPayerServiceLevel());

		// payee
		calFeeRequest.setPayee(balanceDealDto.getPayee());
		calFeeRequest.setPayeeAcctType(balanceDealDto.getPayeeAcctType());
		calFeeRequest.setPayeeCurrencyCode(balanceDealDto
				.getPayeeCurrencyCode());
		calFeeRequest.setPayeeFullMemberAcctCode(balanceDealDto
				.getPayeeFullMemberAcctCode());
		calFeeRequest.setPayeeMemberAcctCode(balanceDealDto
				.getPayeeMemberAcctCode());
		calFeeRequest.setPayeeOrgCode(balanceDealDto.getPayeeOrgCode());
		calFeeRequest.setPayeeOrgType(balanceDealDto.getPayeeOrgType());
		calFeeRequest.setPayeeServiceLevel(balanceDealDto
				.getPayeeServiceLevel());

		calFeeReponse.setCalFeeRequestDto(calFeeRequest);
		List<CalFeeDetailDto> calFeeDetails = new ArrayList<CalFeeDetailDto>();
		// calfeedetail
		CalFeeDetailDto calFeeDetail = null;
		for (BalanceEntry balanceEntryDto : balanceEntryDtos) {
			calFeeDetail = new CalFeeDetailDto();
			calFeeDetail.setAcctcode(balanceEntryDto.getAcctcode());
			calFeeDetail.setCrdr(balanceEntryDto.getCrdr());
			calFeeDetail.setCreatedate(balanceEntryDto.getCreateDate());
			calFeeDetail.setCurrencyCode(balanceEntryDto.getCurrencyCode());
			calFeeDetail.setDealId(balanceEntryDto.getDealId());
			calFeeDetail.setEntrycode(balanceEntryDto.getEntrycode());
			calFeeDetail.setExchangeRate(balanceEntryDto.getExchangeRate());
			calFeeDetail.setMaBlanceBy(balanceEntryDto.getMaBlanceBy());
			calFeeDetail.setPaymentServiceId(balanceEntryDto
					.getPaymentServiceId());
			// 状态默认为0
			// calFeeDetail.setStatus(balanceEntryDto.getStatus());
			calFeeDetail.setText(balanceEntryDto.getText());
			calFeeDetail.setTransactiondate(balanceEntryDto
					.getTransactiondate());
			calFeeDetail.setValue(-1 * balanceEntryDto.getValue());
			calFeeDetail.setVouchercode(balanceEntryDto.getVouchercode());
			calFeeDetails.add(calFeeDetail);
		}
		calFeeReponse.setCalFeeDetailDtos(calFeeDetails);
		return calFeeReponse;
	}

	FlushesLog createFlushesLog(String flushesOrderId, String orderId,
			Integer dealCode, Long amount, Integer dealType) {
		FlushesLog flushesLog = new FlushesLog();
		flushesLog.setFlushesOrderId(flushesOrderId);
		flushesLog.setOrderId(orderId);
		flushesLog.setDealCode(dealCode);
		flushesLog.setAmount(amount);
		flushesLog.setDealType(dealType);
		flushesLog.setStatus(FlushesStatus.CREATE.getCode());
		return flushesLog;
	}

	public void setFlushesLogService(FlushesLogService flushesLogService) {
		this.flushesLogService = flushesLogService;
	}

	public void setAccountBalanceHandlerService(
			AccountBalanceHandlerService accountBalanceHandlerService) {
		this.accountBalanceHandlerService = accountBalanceHandlerService;
	}

	public void setBalanceEntryService(BalanceEntryService balanceEntryService) {
		this.balanceEntryService = balanceEntryService;
	}

	public void setBalanceDealService(BalanceDealService balanceDealService) {
		this.balanceDealService = balanceDealService;
	}

}
