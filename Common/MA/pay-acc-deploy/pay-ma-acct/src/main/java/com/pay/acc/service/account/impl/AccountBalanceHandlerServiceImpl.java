/**
 * 
 */
package com.pay.acc.service.account.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.acct.exception.AcctServiceException;
import com.pay.acc.acct.exception.AcctServiceUnkownException;
import com.pay.acc.acct.service.AcctService;
import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.acctattrib.exception.AcctAttribException;
import com.pay.acc.acctattrib.exception.AcctAttribUnknowException;
import com.pay.acc.acctattrib.service.AcctAttribService;
import com.pay.acc.commons.AcctEnum;
import com.pay.acc.commons.AmountByEnum;
import com.pay.acc.commons.ChargeUpStatusEnum;
import com.pay.acc.commons.ConstantHelper;
import com.pay.acc.deal.dto.BalanceDealDto;
import com.pay.acc.deal.dto.BalanceEntryDto;
import com.pay.acc.deal.dto.BanlanceDealTempDto;
import com.pay.acc.deal.exception.BalanceException;
import com.pay.acc.deal.exception.BalanceUnkownException;
import com.pay.acc.deal.service.BalanceDealService;
import com.pay.acc.deal.service.BalanceEntryService;
import com.pay.acc.service.account.AccountBalanceHandlerService;
import com.pay.acc.service.account.NegativeBalanceService;
import com.pay.acc.service.account.constantenum.PayForEnum;
import com.pay.acc.service.account.dto.CalFeeDetailDto;
import com.pay.acc.service.account.dto.CalFeeReponseDto;
import com.pay.acc.service.account.dto.CalFeeRequestDto;
import com.pay.acc.service.account.exception.MaAcctBalanceException;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.jms.sender.JmsSender;
import com.pay.pe.dto.AcctSpecDTO;
import com.pay.pe.dto.PaymentServiceType;
import com.pay.pe.helper.CRDRType;
import com.pay.pe.service.PaymentDetailDto;
import com.pay.pe.service.PaymentReqDto;
import com.pay.pe.service.PaymentResponseDto;
import com.pay.pe.service.account.AcctSpecService;
import com.pay.util.BeanConvertUtil;

/**
 * @author Administrator
 * 
 */

public class AccountBalanceHandlerServiceImpl implements
		AccountBalanceHandlerService {

	private final Log log = LogFactory.getLog(getClass());
	private AcctSpecService acctSpecService;
	private AcctService acctService;
	private AcctAttribService acctAttribService;
	private BalanceEntryService balanceEntryService;
	private BalanceDealService balanceDealService;
	private JmsSender jmsSender;
	private NegativeBalanceService negativeBalanceService;

	@Override
	public Long updateAccBalance(CalFeeReponseDto updateBalanceRequestDto,
			Integer payType) throws MaAcctBalanceException {

		log.info("################################################更新开始###################################################");
		log.info("验证传入的参数");
		this.handlerCheckEnterParameter(updateBalanceRequestDto, payType);

		List<CalFeeDetailDto> calFeeDetailDtos = updateBalanceRequestDto
				.getCalFeeDetailDtos();
		if (calFeeDetailDtos == null) {
			log.error("您传入的参数对象" + CalFeeDetailDto.class.getName() + "为空");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.INPUT_PARA_NULL, "输入的参数不能为空");
		}

		log.info("获取提交的流水号为："
				+ updateBalanceRequestDto.getCalFeeRequestDto().getOrderId());
		// 对唯一性进行验证
		this.handlerDealInfoUnique(updateBalanceRequestDto, payType);

		Integer dealCode = updateBalanceRequestDto.getCalFeeRequestDto()
				.getDealCode();
		if (dealCode == null) {
			log.error("输入的流水号["
					+ updateBalanceRequestDto.getCalFeeRequestDto()
							.getOrderId() + "]对应的交易号 deal code 为null");
			throw new MaAcctBalanceException("输入的流水号["
					+ updateBalanceRequestDto.getCalFeeRequestDto()
							.getOrderId() + "]对应的交易号 deal code 为 null");
		}

		String payee = updateBalanceRequestDto.getCalFeeRequestDto()
				.getPayeeFullMemberAcctCode();
		// 获取费用(pe)的List
		List<CalFeeDetailDto> calFeeList = new ArrayList<CalFeeDetailDto>();
		for (CalFeeDetailDto calFeeDetailDto : calFeeDetailDtos) {

			if (calFeeDetailDto.getDealType() != null
					&& calFeeDetailDto.getDealType() == PaymentServiceType.BILLING
							.getValue()) {
				calFeeList.add(calFeeDetailDto);
			}
			this.handlerAcctBalanceTrans(calFeeDetailDto,
					updateBalanceRequestDto.getCalFeeRequestDto().getOrderId(),
					dealCode, payee);
		}

		List<CalFeeDetailDto> calFeeTempList = calFeeList;
		// 需要记费存入deal的List
		List<BanlanceDealTempDto> dealList = new ArrayList<BanlanceDealTempDto>();
		if (calFeeList != null && calFeeList.size() > 0) {
			for (CalFeeDetailDto cf : calFeeList) {
				BanlanceDealTempDto bt = getMergerFeeList(calFeeTempList,
						cf.getValue());
				if (bt != null)
					dealList.add(bt);
			}
		}

		// 保存deal信息
		Long seqId = this.handlerSaveCalFeeResponseInfo(
				updateBalanceRequestDto, payType);
		// 保存费用deal信息
		if (dealList != null && dealList.size() > 0) {
			this.handlerSaveCalFeeTempResponseInfo(updateBalanceRequestDto,
					payType, dealList);
		}

		return seqId;
	}
	
	/**
	 * 新增手工记账
	 */
	@Override
	public Long updateAccBalance_(CalFeeReponseDto updateBalanceRequestDto,
			Integer payType) throws MaAcctBalanceException {

		log.info("################################################更新开始###################################################");
		log.info("验证传入的参数");
		this.handlerCheckEnterParameter(updateBalanceRequestDto, payType);

		List<CalFeeDetailDto> calFeeDetailDtos = updateBalanceRequestDto
				.getCalFeeDetailDtos();
		if (calFeeDetailDtos == null) {
			log.error("您传入的参数对象" + CalFeeDetailDto.class.getName() + "为空");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.INPUT_PARA_NULL, "输入的参数不能为空");
		}

		log.info("获取提交的流水号为："
				+ updateBalanceRequestDto.getCalFeeRequestDto().getOrderId());
		// 对唯一性进行验证
		this.handlerDealInfoUnique(updateBalanceRequestDto, payType);

		Integer dealCode = updateBalanceRequestDto.getCalFeeRequestDto()
				.getDealCode();
		if (dealCode == null) {
			log.error("输入的流水号["
					+ updateBalanceRequestDto.getCalFeeRequestDto()
							.getOrderId() + "]对应的交易号 deal code 为null");
			throw new MaAcctBalanceException("输入的流水号["
					+ updateBalanceRequestDto.getCalFeeRequestDto()
							.getOrderId() + "]对应的交易号 deal code 为 null");
		}

		String payee = updateBalanceRequestDto.getCalFeeRequestDto()
				.getPayeeFullMemberAcctCode();
		// 获取费用(pe)的List
		List<CalFeeDetailDto> calFeeList = new ArrayList<CalFeeDetailDto>();
		for (CalFeeDetailDto calFeeDetailDto : calFeeDetailDtos) {

			if (calFeeDetailDto.getDealType() != null
					&& calFeeDetailDto.getDealType() == PaymentServiceType.BILLING
							.getValue()) {
				calFeeList.add(calFeeDetailDto);
			}
			this.handlerAcctBalanceTrans(calFeeDetailDto,
					updateBalanceRequestDto.getCalFeeRequestDto().getOrderId(),
					dealCode, payee);
		}

		List<CalFeeDetailDto> calFeeTempList = calFeeList;
		// 需要记费存入deal的List
		List<BanlanceDealTempDto> dealList = new ArrayList<BanlanceDealTempDto>();
		if (calFeeList != null && calFeeList.size() > 0) {
			for (CalFeeDetailDto cf : calFeeList) {
				BanlanceDealTempDto bt = getMergerFeeList(calFeeTempList,
						cf.getValue());
				if (bt != null)
					dealList.add(bt);
			}
		}

		// 保存deal信息
		Long seqId = this.handlerSaveCalFeeResponseInfo_(
				updateBalanceRequestDto, payType);
		// 保存费用deal信息
		if (dealList != null && dealList.size() > 0) {
			this.handlerSaveCalFeeTempResponseInfo(updateBalanceRequestDto,
					payType, dealList);
		}

		return seqId;
	}

	public int doUpdateBalanceRntx(CalFeeReponseDto updateBalanceRequestDto,
			Integer payType) throws MaAcctBalanceException {

		if (log.isInfoEnabled()) {
			log.info("update balance begin ....." + System.currentTimeMillis());
		}

		this.handlerCheckEnterParameter(updateBalanceRequestDto, payType);

		List<CalFeeDetailDto> calFeeDetailDtos = updateBalanceRequestDto
				.getCalFeeDetailDtos();
		if (calFeeDetailDtos == null) {
			log.error("您传入的参数对象" + CalFeeDetailDto.class.getName() + "为空");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.INPUT_PARA_NULL, "输入的参数不能为空");
		}

		log.info("获取提交的流水号为："
				+ updateBalanceRequestDto.getCalFeeRequestDto().getOrderId());
		// 对唯一性进行验证
		this.handlerDealInfoUnique(updateBalanceRequestDto, payType);

		Integer dealCode = updateBalanceRequestDto.getCalFeeRequestDto()
				.getDealCode();
		if (dealCode == null) {
			log.error("输入的流水号["
					+ updateBalanceRequestDto.getCalFeeRequestDto()
							.getOrderId() + "]对应的交易号 deal code 为null");
			throw new MaAcctBalanceException("输入的流水号["
					+ updateBalanceRequestDto.getCalFeeRequestDto()
							.getOrderId() + "]对应的交易号 deal code 为 null");
		}

		// 获取费用(pe)的List
		List<CalFeeDetailDto> calFeeList = new ArrayList<CalFeeDetailDto>();
		for (CalFeeDetailDto calFeeDetailDto : calFeeDetailDtos) {

			if (calFeeDetailDto.getDealType() != null
					&& calFeeDetailDto.getDealType() == PaymentServiceType.BILLING
							.getValue()) {
				calFeeList.add(calFeeDetailDto);
			}
			String payee = updateBalanceRequestDto.getCalFeeRequestDto()
					.getPayeeFullMemberAcctCode();
			this.handlerAcctBalanceTrans(calFeeDetailDto,
					updateBalanceRequestDto.getCalFeeRequestDto().getOrderId(),
					dealCode, payee);
		}

		List<CalFeeDetailDto> calFeeTempList = calFeeList;
		// 需要记费存入deal的List
		List<BanlanceDealTempDto> dealList = new ArrayList<BanlanceDealTempDto>();
		if (calFeeList != null && calFeeList.size() > 0) {
			for (CalFeeDetailDto cf : calFeeList) {
				BanlanceDealTempDto bt = getMergerFeeList(calFeeTempList,
						cf.getValue());
				if (bt != null)
					dealList.add(bt);
			}
		}

		// 保存deal信息
		Long seqId = this.handlerSaveCalFeeResponseInfo(
				updateBalanceRequestDto, payType);
		// 保存费用deal信息
		if (dealList != null && dealList.size() > 0) {
			this.handlerSaveCalFeeTempResponseInfo(updateBalanceRequestDto,
					payType, dealList);
		}

		return this.sendMsgToPe(updateBalanceRequestDto, seqId);
	}

	@Override
	public int sendMsgToPe(CalFeeReponseDto updateBalanceRequestDto, Long seqId) {
		log.info("################################################更新结束###################################################");
		log.info("###########通知记账###############");
		// 通知记账,最终要走property形式，获取队列信息，现在暂时这样处理
		// modify by lei.jiangl 2010-10-29
		try {
			this.jmsSender.send("acc.chargeUpMessage",
					this.handlerMakeUpCalFeeReponse(updateBalanceRequestDto));
		} catch (Exception e) {
			log.error("###########通知记账出现错误###############", e);
			return this.SUCCESS;
		}
		// 更新发mq成功状态 3
		log.info("更新序列号[seqId]为" + seqId + "发送记账信息到mq成功状态");
		this.handlerUpdateChargeupStatus(seqId,
				ChargeUpStatusEnum.CHARGEUP_SEND_MQ.getCode());
		log.info("###########通知记账结束###############");
		return this.SUCCESS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.pay.acc.service.account.AccountBalanceHandlerService#
	 * doUpdateAcctBalanceRntx
	 * (com.pay.acc.service.account.dto.CalFeeReponseDto)
	 */
	@Override
	public int doUpdateAcctBalanceRntx(
			CalFeeReponseDto updateBalanceRequestDto, Integer payType)
			throws MaAcctBalanceException {
		Long seqId = this.updateAccBalance(updateBalanceRequestDto, payType);
		return this.sendMsgToPe(updateBalanceRequestDto, seqId);
	}
	
	/*
	 * 
	 * 手工记账
	 * @seecom.pay.acc.service.account.AccountBalanceHandlerService#
	 * doUpdateAcctBalanceRntx
	 * (com.pay.acc.service.account.dto.CalFeeReponseDto)
	 */
	@Override
	public int doUpdateAcctBalanceRntx_(
			CalFeeReponseDto updateBalanceRequestDto, Integer payType)
			throws MaAcctBalanceException {
		Long seqId = this.updateAccBalance_(updateBalanceRequestDto, payType);
		return this.sendMsgToPe(updateBalanceRequestDto, seqId);
	}

	/**
	 * 根据流水号，交易号验证唯一性
	 * 
	 * @param serialNo
	 * @param calFeeRequestDto
	 * @return
	 * @throws MaAcctBalanceException
	 */
	private void handlerDealInfoUnique(CalFeeReponseDto calFeeRequestDto,
			Integer dealType) throws MaAcctBalanceException {

		Integer num = null;
		if (log.isDebugEnabled()) {
			log.debug("查询开始 acc.t_balance_deal 开始，"
					+ System.currentTimeMillis());
		}
		try {
			num = this.balanceDealService.queryDealInfoCountsByVo(dealType,
					calFeeRequestDto.getVoucherCode());
		} catch (BalanceException e) {
			log.error(e.getMessage(), e);
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.ACCT_INVAILD_PARAMETER, e.getMessage(),
					e);
		} catch (BalanceUnkownException e) {
			log.error("未知异常", e);
			throw new MaAcctBalanceException(ErrorExceptionEnum.UNKNOW_ERROR,
					"未知异常", e);
		}

		if (log.isDebugEnabled()) {
			log.debug("查询开始 acc.t_balance_deal 结束，"
					+ System.currentTimeMillis());
		}

		if (num != null && num.intValue() > 0) {
			log.error("对于流水号["
					+ calFeeRequestDto.getCalFeeRequestDto().getOrderId()
					+ "和交易号"
					+ calFeeRequestDto.getCalFeeRequestDto().getDealCode()
					+ "]余额更新重复提交");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.BALANCE_DEAL_SUBMIT_AGIAN, "对于流水号["
							+ calFeeRequestDto.getCalFeeRequestDto()
									.getOrderId()
							+ "和交易号"
							+ calFeeRequestDto.getCalFeeRequestDto()
									.getDealCode() + "]余额更新重复提交");
		}

	}

	private PaymentResponseDto handlerMakeUpCalFeeReponse(
			CalFeeReponseDto updateBalanceRequestDto) {
		PaymentResponseDto payMentReponse = BeanConvertUtil.convert(
				PaymentResponseDto.class, updateBalanceRequestDto);
		PaymentReqDto paymentReq = BeanConvertUtil.convert(PaymentReqDto.class,
				updateBalanceRequestDto.getCalFeeRequestDto());
		payMentReponse.setPaymentReq(paymentReq);
		List<PaymentDetailDto> paymentDetails = new ArrayList<PaymentDetailDto>();
		List<CalFeeDetailDto> calFeeDetailDtos = updateBalanceRequestDto
				.getCalFeeDetailDtos();
		for (CalFeeDetailDto calFeeDetailDto : calFeeDetailDtos) {
			paymentDetails.add(BeanConvertUtil.convert(PaymentDetailDto.class,
					calFeeDetailDto));
		}
		payMentReponse.setPaymentDetails(paymentDetails);
		return payMentReponse;
	}

	private void handlerCheckEnterParameter(
			CalFeeReponseDto updateBalanceRequestDto, Integer payType)
			throws MaAcctBalanceException {

		if (updateBalanceRequestDto == null || payType == null
				|| payType.intValue() < 0) {
			log.error("输入的参数不能为空" + updateBalanceRequestDto + ",payType:"
					+ payType);
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.INPUT_PARA_NULL, "输入的参数不能为空");
		}
	}

	/**
	 * 带有冲正金额处理
	 * 
	 * @param calFeeDetailDto
	 * @param serialNo
	 * @param isNegative
	 *            金额是否为负
	 * @throws MaAcctBalanceException
	 */
	private void handlerAcctBalanceTrans(CalFeeDetailDto calFeeDetailDto,
			String serialNo, Integer dealCode, String payee)
			throws MaAcctBalanceException {

		// TODO UPDATE BY CHAOYUE
		String acctCode = calFeeDetailDto.getAcctcode();
		Long amount = calFeeDetailDto.getValue();
		Long chargeAmount = amount;
		boolean isNegative = false;
		Integer maBlanceBy = calFeeDetailDto.getMaBlanceBy();
		Integer balanceBy = 2;// 默认会员账户余额方向在贷方
		Integer acctType = 2;// 默认会员账户
		
		try{
			AcctDto acctDto = acctService.queryAcctByAcctCode(acctCode);
			if(null == acctDto){
				log.error("账户[" + acctCode + "] 不存在");
				throw new MaAcctBalanceException(
						ErrorExceptionEnum.ACCT_NON_EXIST_ERROR, "账户["
								+ acctCode + "]不存在");
			}
		}catch(Exception e){
			log.error("query error:",e);
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.UNKNOW_ERROR, e.getMessage());
		}
		
		AcctSpecDTO acctSpecDTO = acctSpecService.getAcctSpec(acctCode);
		if (null != acctSpecDTO) {
			isNegative = (acctSpecDTO.getNegativeBalance() == 1);
			balanceBy = acctSpecDTO.getBalanceBy();
			acctType = acctSpecDTO.getAcctType();
		}

		// 判断加减
		if (maBlanceBy == 2) {
			chargeAmount = -amount;
		}

		// 对借方向余额进行处理
		if (calFeeDetailDto.getCrdr().intValue() == CRDRType.DEBIT.getValue()) {

			Long debitAmount = amount;
			Long creditAmount = 0L;

			boolean updateFlg = false;
			if (isNegative) {
				updateFlg = acctService.updateBalance(acctCode, chargeAmount,
						debitAmount, creditAmount);
			} else {
				updateFlg = acctService.updateBalanceCheckNegative(acctCode,
						chargeAmount, debitAmount, creditAmount);
			}

			if (!updateFlg) {
				log.error("对于账户[" + acctCode + "]余额不足"+",isNegative["+isNegative+"], maBlanceBy[ "+ maBlanceBy+"],balanceBy[" +balanceBy+"]" );
				throw new MaAcctBalanceException(
						ErrorExceptionEnum.ACCT_NO_SAVE_ACCOUNT, "对于账户["
								+ acctCode + "]余额不足");
			}

		} else if (calFeeDetailDto.getCrdr().intValue() == CRDRType.CREDIT
				.getValue()) {

			Long debitAmount = 0L;
			Long creditAmount = amount;

			boolean updateFlg = false;
			if (isNegative) {
				updateFlg = acctService.updateBalance(acctCode, chargeAmount,
						debitAmount, creditAmount);
			} else {
				updateFlg = acctService.updateBalanceCheckNegative(acctCode,
						chargeAmount, debitAmount, creditAmount);
			}

			if (!updateFlg) {
				log.error("对于账户[" + acctCode + "]余额不足"+",isNegative["+isNegative+"], maBlanceBy[ "+ maBlanceBy+"],balanceBy[" +balanceBy+"]" );
				throw new MaAcctBalanceException(
						ErrorExceptionEnum.ACCT_NO_SAVE_ACCOUNT, "对于账户["
								+ acctCode + "]余额不足");
			}
		}

		// 保存余额日志
		// 查询已经更新余额保存改余额记录
		Long tBalance = this
				.handlerQueryAcctInfo(calFeeDetailDto.getAcctcode())
				.getBalance();

		this.handlerSaveCalFeeDetailInfo(calFeeDetailDto, tBalance, serialNo,
				dealCode);
	}

	/**
	 * 验证账户是否为中间科目中的可透支账户
	 * 
	 * @param acctCode
	 * @throws MaAcctBalanceException
	 */
	private boolean handlerCheckAcctCodeMemberCode(
			CalFeeDetailDto calFeeDetailDto, String payee, String orderId)
			throws MaAcctBalanceException {

		// 判断余额的加减方向
		// 判断余额正负方向根据余额的方向
		AcctAttribDto acctAttribDto = null;
		if (calFeeDetailDto.getAcctcode().length() > ConstantHelper.SUBJECT_CHECK_LENGTH) {
			acctAttribDto = this.handlerQueryAcctAttribInfo(calFeeDetailDto
					.getAcctcode());
			if (acctAttribDto == null) {
				log.error("账户[" + calFeeDetailDto.getAcctcode() + "]的账户属性不存在");
				throw new MaAcctBalanceException("账户["
						+ calFeeDetailDto.getAcctcode() + "]的账户属性不存在");
			}
			// 余额方向为加
			if (calFeeDetailDto.getMaBlanceBy() == AmountByEnum.ADD.getCode()) {
				// 对余额正负进行判断
				// 输入的金额为正判断是否止入
				if (calFeeDetailDto.getValue() > 0) {
					// 判断用户止入
					if (acctAttribDto.getAllowIn() != ConstantHelper.ALLOW_IN) {
						log.error("账户：[" + calFeeDetailDto.getAcctcode()
								+ "]止入");
						throw new MaAcctBalanceException(
								ErrorExceptionEnum.ACCT_ALLOWIN_ERROR, "对于账户["
										+ calFeeDetailDto.getAcctcode() + "]止入");
					}

				} else {
					// 输入的金额为负，判断用户是否止出
					// if (acctAttribDto.getAllowOut() !=
					// ConstantHelper.ALLOW_OUT) {
					// log.error("账号[" + calFeeDetailDto.getAcctcode() + "]止出");
					// throw new
					// MaAcctBalanceException(ErrorExceptionEnum.ACCT_ALLOWOUT_ERROR,
					// "对于账户[" + calFeeDetailDto.getAcctcode() + "]止出");
					// }
					this.validateAcctAllowOut(acctAttribDto.getAllowOut(),
							payee, calFeeDetailDto.getAcctcode(), orderId);
				}
			}
			if (calFeeDetailDto.getMaBlanceBy() == AmountByEnum.REDUCE
					.getCode()) {
				// 账户止出判断
				if (calFeeDetailDto.getValue() > 0) {
					// if (acctAttribDto.getAllowOut() !=
					// ConstantHelper.ALLOW_OUT) {
					// log.error("账号[" + calFeeDetailDto.getAcctcode() + "]止出");
					// throw new
					// MaAcctBalanceException(ErrorExceptionEnum.ACCT_ALLOWOUT_ERROR,
					// "对于账户[" + calFeeDetailDto.getAcctcode() + "]止出");
					// }
					this.validateAcctAllowOut(acctAttribDto.getAllowOut(),
							payee, calFeeDetailDto.getAcctcode(), orderId);
				}// 用户止入判断
				else {
					if (acctAttribDto.getAllowIn() != ConstantHelper.ALLOW_IN) {
						log.error("账户：[" + calFeeDetailDto.getAcctcode()
								+ "]止入");
						throw new MaAcctBalanceException(
								ErrorExceptionEnum.ACCT_ALLOWIN_ERROR, "对于账户["
										+ calFeeDetailDto.getAcctcode() + "]止入");
					}

				}
			}

		}// 账户为中间科目
		else {
			acctAttribDto = this.handlerQueryAcctAttribInfo(calFeeDetailDto
					.getAcctcode());
			if (acctAttribDto != null
					&& acctAttribDto.getAllowOverdraft() == ConstantHelper.ALLOW_OVERDRAFT) {
				log.info("账户[" + acctAttribDto.getAcctCode() + "]可透支");
				return true;
			}

		}

		return false;

	}

	private void validateAcctAllowOut(int allowOut, String payee,
			String acctCode, String orderId) throws MaAcctBalanceException {

		if (!(null != orderId && orderId.startsWith("105") && acctCode
				.equals(payee)))
			if (allowOut != ConstantHelper.ALLOW_OUT) {
				log.error("账号[" + acctCode + "]止出");
				throw new MaAcctBalanceException(
						ErrorExceptionEnum.ACCT_ALLOWOUT_ERROR, "对于账户["
								+ acctCode + "]止出");
			}
	}

	/**
	 * 保存分录信息
	 * 
	 * @param calFeeDetailDto
	 * @throws MaAcctBalanceException
	 */
	private void handlerSaveCalFeeDetailInfo(CalFeeDetailDto calFeeDetailDto,
			Long balance, String serialNo, Integer dealCode)
			throws MaAcctBalanceException {

		BalanceEntryDto balanceEntryDto = this
				.handlerBeanConvertWithCalFeeDetailDto(calFeeDetailDto,
						dealCode);
		balanceEntryDto.setBalance(balance);
		balanceEntryDto.setDealId(serialNo);
		if (calFeeDetailDto.getDealType() != null
				&& calFeeDetailDto.getDealType() == 2)
			balanceEntryDto.setStatus(1);
		else
			balanceEntryDto.setStatus(0);
		Long seqId = Long.valueOf(0);
		try {
			seqId = this.balanceEntryService
					.createBalanceEntry(balanceEntryDto);
		} catch (BalanceException e) {
			log.error(e.getMessage(), e);
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.INVAILD_PARAMETER, e.getMessage(), e);
		} catch (BalanceUnkownException e) {
			log.error("系统异常", e);
			throw new MaAcctBalanceException(ErrorExceptionEnum.UNKNOW_ERROR,
					"系统异常", e);
		}
		if (seqId == null || seqId.longValue() <= 0) {
			log.error("账户余额日志插入失败");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.BALANCE_LOG_INSERT_ERROR, "账户余额日志插入失败");
		}

	}

	private BalanceEntryDto handlerBeanConvertWithCalFeeDetailDto(
			CalFeeDetailDto calFeeDetailDto, Integer dealCode) {
		BalanceEntryDto balanceEntryDto = new BalanceEntryDto();
		balanceEntryDto.setAcctcode(calFeeDetailDto.getAcctcode());
		balanceEntryDto.setCrdr(calFeeDetailDto.getCrdr());
		balanceEntryDto.setCreatedate(calFeeDetailDto.getCreatedate());
		balanceEntryDto.setCurrencyCode(calFeeDetailDto.getCurrencyCode());
		balanceEntryDto.setEntrycode(calFeeDetailDto.getEntrycode());
		balanceEntryDto.setExchangeRate(calFeeDetailDto.getExchangeRate());
		balanceEntryDto.setMaBlanceBy(calFeeDetailDto.getMaBlanceBy());
		balanceEntryDto.setPaymentServiceId(calFeeDetailDto
				.getPaymentServiceId());
		balanceEntryDto.setStatus(calFeeDetailDto.getStatus());
		balanceEntryDto.setText(calFeeDetailDto.getText());
		balanceEntryDto.setValue(calFeeDetailDto.getValue());
		balanceEntryDto.setVouchercode(calFeeDetailDto.getVouchercode());
		balanceEntryDto
				.setTransactiondate(calFeeDetailDto.getTransactiondate());
		balanceEntryDto.setPostDate(new Date());
		balanceEntryDto.setPayDate(new Date());
		// 设置DEAL_CODE
		balanceEntryDto.setDealCode(dealCode);
		return balanceEntryDto;
	}

	/**
	 * 保存订单处理信息
	 * 
	 * @throws MaAcctBalanceException
	 */
	private Long handlerSaveCalFeeResponseInfo_(
			CalFeeReponseDto updateBalanceRequestDto, Integer dealType)
			throws MaAcctBalanceException {

		List<BalanceDealDto> balanceDealDtos = this
				.handlerBeanConvertWithBalanceDealDto_(updateBalanceRequestDto,
						dealType);
		Long seqId = Long.valueOf(0);
		try {
			for (BalanceDealDto balanceDealDto : balanceDealDtos) {
				balanceDealDto.setMerchantOrderId(updateBalanceRequestDto.getMerchantOrderId());
				seqId = this.balanceDealService.createBalanceDeal(balanceDealDto);
			}
		} catch (BalanceException e) {
			log.error(e.getMessage(), e);
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.INVAILD_PARAMETER, e.getMessage(), e);
		} catch (BalanceUnkownException e) {
			log.error("系统异常", e);
			throw new MaAcctBalanceException(ErrorExceptionEnum.UNKNOW_ERROR,
					"系统异常", e);
		}
		if (seqId == null || seqId.longValue() <= 0) {
			log.error("账户交易插入失败");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.BALANCE_DEAL_INSERT_FAIL, "账户交易插入失败");
		}
		return seqId;
	}
	
	/**
	 * 保存订单处理信息
	 * 
	 * @throws MaAcctBalanceException
	 */
	private Long handlerSaveCalFeeResponseInfo(
			CalFeeReponseDto updateBalanceRequestDto, Integer dealType)
			throws MaAcctBalanceException {

		BalanceDealDto balanceDealDto = this
				.handlerBeanConvertWithBalanceDealDto(updateBalanceRequestDto,
						dealType);
						
		// BalanceDealDto
		// balanceDealDtoTmp=handlerBeanConvertBalanceFeeDealDto(updateBalanceRequestDto);
		Long seqId = Long.valueOf(0);
		try {
			seqId = this.balanceDealService.createBalanceDeal(balanceDealDto);
			// if(null!=balanceDealDtoTmp){
			// this.balanceDealService.createBalanceDeal(balanceDealDtoTmp);
			// }
		} catch (BalanceException e) {
			log.error(e.getMessage(), e);
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.INVAILD_PARAMETER, e.getMessage(), e);
		} catch (BalanceUnkownException e) {
			log.error("系统异常", e);
			throw new MaAcctBalanceException(ErrorExceptionEnum.UNKNOW_ERROR,
					"系统异常", e);
		}
		if (seqId == null || seqId.longValue() <= 0) {
			log.error("账户交易插入失败");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.BALANCE_DEAL_INSERT_FAIL, "账户交易插入失败");
		}
		return seqId;

	}
	

	private void handlerUpdateChargeupStatus(Long seqId, Integer chargeupStatus) {
		Integer result = Integer.valueOf(0);
		try {
			result = this.balanceDealService.updateChargeUpStatus(seqId,
					chargeupStatus);
		} catch (Exception e) {
			log.error("更新记账发送信息状态失败", e);
		}
		if (result.intValue() != 1) {
			log.error("更新记账发送信息状态失败");
		} else {
			log.info("更新记账发送信息状态成功");
		}

	}

	private BalanceDealDto handlerBeanConvertBalanceFeeDealDto(
			CalFeeReponseDto updateBalanceRequestDto, BanlanceDealTempDto bdt) {
		BalanceDealDto balanceDealDto = new BalanceDealDto();
		Integer caculatePrice = updateBalanceRequestDto.isHasCaculatedPrice() == true ? 1
				: 0;
		balanceDealDto.setHasCaculatedPrice(caculatePrice);
		CalFeeRequestDto calFeeRequestDto = updateBalanceRequestDto
				.getCalFeeRequestDto();
		balanceDealDto.setMerchantOrderId(updateBalanceRequestDto.getMerchantOrderId());
		balanceDealDto.setChargeUpStatus(ChargeUpStatusEnum.CHARGEUP.getCode());
		balanceDealDto.setDealCode(calFeeRequestDto.getDealCode());
		balanceDealDto.setExchangeRate(calFeeRequestDto.getExchangeRate());
		balanceDealDto.setOrderId(calFeeRequestDto.getOrderId());
		balanceDealDto.setDealType(PayForEnum.FEE_AMOUNT.getCode());

		balanceDealDto.setAmount(bdt.getAmount());
		balanceDealDto.setOrderAmount(bdt.getAmount());
		balanceDealDto.setPayer(balanceDealDto.getPayer());
		balanceDealDto.setPayerAcctType(calFeeRequestDto.getPayerAcctType());
		balanceDealDto.setPayerCurrencyCode(calFeeRequestDto
				.getPayerCurrencyCode());
		balanceDealDto.setPayerFullMemberAcctCode(bdt
				.getPayerFullMemberAcctCode());
		balanceDealDto.setPayeeFullMemberAcctCode(bdt
				.getPayeeFullMemberAcctCode());
		balanceDealDto.setRemark(updateBalanceRequestDto.getRemark());

		balanceDealDto.setSubmitAcctCode(calFeeRequestDto.getSubmitAcctCode());
		balanceDealDto.setTerminalType(calFeeRequestDto.getTerminalType());
		balanceDealDto.setPayMethod(calFeeRequestDto.getPayMethod());
		balanceDealDto.setPaymentServicePkgCode(calFeeRequestDto
				.getPaymentServicePkgCode());
		balanceDealDto.setRequestDate(calFeeRequestDto.getRequestDate());
		balanceDealDto.setVoucherCode(updateBalanceRequestDto.getVoucherCode());
		return balanceDealDto;
	}
	
	private BalanceDealDto handlerBeanConvertWithBalanceDealDto(
			CalFeeReponseDto updateBalanceRequestDto, Integer dealType) {

		BalanceDealDto balanceDealDto = new BalanceDealDto();
		balanceDealDto.setPayeeFee(updateBalanceRequestDto.getPayeeFee());
		balanceDealDto.setPayerFee(updateBalanceRequestDto.getPayerFee());
		Integer caculatePrice = updateBalanceRequestDto.isHasCaculatedPrice() == true ? 1
				: 0;
		balanceDealDto.setHasCaculatedPrice(caculatePrice);

		CalFeeRequestDto calFeeRequestDto = updateBalanceRequestDto
				.getCalFeeRequestDto();
		balanceDealDto.setAmount(calFeeRequestDto.getAmount());
		balanceDealDto.setChargeUpStatus(ChargeUpStatusEnum.CHARGEUP_NO_SEND_MQ
				.getCode());
		balanceDealDto.setDealCode(calFeeRequestDto.getDealCode());
		balanceDealDto.setOrderAmount(calFeeRequestDto.getOrderAmount());
		balanceDealDto.setExchangeRate(calFeeRequestDto.getExchangeRate());
		balanceDealDto.setOrderId(calFeeRequestDto.getOrderId());
		balanceDealDto.setDealType(dealType);
		balanceDealDto.setPaymentServicePkgCode(calFeeRequestDto
				.getPaymentServicePkgCode());
		balanceDealDto.setMerchantOrderId(updateBalanceRequestDto.getMerchantOrderId());

		balanceDealDto.setOrderCode(calFeeRequestDto.getOrderCode());
		// payee
		balanceDealDto.setPayee(calFeeRequestDto.getPayee());
		balanceDealDto.setPayeeAcctType(calFeeRequestDto.getPayeeAcctType());
		balanceDealDto.setPayeeCurrencyCode(calFeeRequestDto
				.getPayeeCurrencyCode());
		balanceDealDto.setPayeeFullMemberAcctCode(calFeeRequestDto
				.getPayeeFullMemberAcctCode());
		balanceDealDto.setPayeeMemberAcctCode(calFeeRequestDto
				.getPayeeMemberAcctCode());
		balanceDealDto.setPayeeOrgCode(calFeeRequestDto.getPayeeOrgCode());
		balanceDealDto.setPayeeOrgType(calFeeRequestDto.getPayeeOrgType());
		balanceDealDto.setPayeeServiceLevel(calFeeRequestDto
				.getPayeeServiceLevel());
		// payer
		balanceDealDto.setPayer(calFeeRequestDto.getPayer());
		balanceDealDto.setPayerAcctType(calFeeRequestDto.getPayerAcctType());
		balanceDealDto.setPayerCurrencyCode(calFeeRequestDto
				.getPayerCurrencyCode());
		balanceDealDto.setPayerFullMemberAcctCode(calFeeRequestDto
				.getPayerFullMemberAcctCode());
		balanceDealDto.setPayerMemberAcctCode(calFeeRequestDto
				.getPayerMemberAcctCode());
		balanceDealDto.setPayerOrgCode(calFeeRequestDto.getPayerOrgCode());
		balanceDealDto.setPayerOrgType(calFeeRequestDto.getPayerOrgType());
		balanceDealDto.setPayerServiceLevel(calFeeRequestDto
				.getPayerServiceLevel());

		balanceDealDto.setSubmitAcctCode(calFeeRequestDto.getSubmitAcctCode());
		balanceDealDto.setTerminalType(calFeeRequestDto.getTerminalType());
		balanceDealDto.setPayMethod(calFeeRequestDto.getPayMethod());
		balanceDealDto.setPaymentServicePkgCode(calFeeRequestDto
				.getPaymentServicePkgCode());
		balanceDealDto.setRequestDate(calFeeRequestDto.getRequestDate());

		balanceDealDto.setVoucherCode(updateBalanceRequestDto.getVoucherCode());
		// balanceDealDto.set(updateBalanceRequestDto.getPriceStrategyCode());
		return balanceDealDto;

	}

	private List<BalanceDealDto> handlerBeanConvertWithBalanceDealDto_(
			CalFeeReponseDto updateBalanceRequestDto, Integer dealType) {
		
		List<BalanceDealDto> balanceDealDtos = new ArrayList<BalanceDealDto>();
		List<CalFeeDetailDto> calFeeDetailDtos = updateBalanceRequestDto.getCalFeeDetailDtos();
		for (int i = 0; i < calFeeDetailDtos.size(); i++) {
			if(i==1||i==3){
				continue;
			}
			BalanceDealDto balanceDealDto=new BalanceDealDto();
			balanceDealDto.setRemark(calFeeDetailDtos.get(i).getRemark());
			balanceDealDto.setPayeeFee(updateBalanceRequestDto.getPayeeFee());
			balanceDealDto.setPayerFee(updateBalanceRequestDto.getPayerFee());
			Integer caculatePrice = updateBalanceRequestDto.isHasCaculatedPrice() == true ? 1
					: 0;
			balanceDealDto.setHasCaculatedPrice(caculatePrice);

			CalFeeRequestDto calFeeRequestDto = updateBalanceRequestDto
					.getCalFeeRequestDto();
			
			balanceDealDto.setAmount(calFeeDetailDtos.get(i).getValue()); //金额
			balanceDealDto.setChargeUpStatus(ChargeUpStatusEnum.CHARGEUP_NO_SEND_MQ
					.getCode()); //记账
			balanceDealDto.setDealCode(calFeeRequestDto.getDealCode()); //记账 code
			balanceDealDto.setOrderAmount(calFeeDetailDtos.get(i).getValue()); //订单金额
			balanceDealDto.setExchangeRate(calFeeRequestDto.getExchangeRate());
			balanceDealDto.setOrderId(calFeeRequestDto.getOrderId());//交易流水号 
			balanceDealDto.setDealType(dealType);//交易类型（支付类型）
			balanceDealDto.setPaymentServicePkgCode(calFeeRequestDto
					.getPaymentServicePkgCode());

			balanceDealDto.setOrderCode(calFeeRequestDto.getOrderCode());
			// payee
			balanceDealDto.setPayee(calFeeRequestDto.getPayee());
			balanceDealDto.setPayeeAcctType(calFeeRequestDto.getPayeeAcctType());
			balanceDealDto.setPayeeCurrencyCode(calFeeRequestDto
					.getPayeeCurrencyCode());
			balanceDealDto.setPayeeMemberAcctCode(calFeeRequestDto
					.getPayeeMemberAcctCode());
			if(calFeeDetailDtos.get(i).getCrdr()==2){
				balanceDealDto.setPayeeFullMemberAcctCode(calFeeDetailDtos.get(i).getAcctcode());
			}
			balanceDealDto.setPayeeOrgCode(calFeeRequestDto.getPayeeOrgCode());
			balanceDealDto.setPayeeOrgType(calFeeRequestDto.getPayeeOrgType());
			balanceDealDto.setPayeeServiceLevel(calFeeRequestDto
					.getPayeeServiceLevel());
			// payer
			balanceDealDto.setPayer(calFeeRequestDto.getPayer());
			balanceDealDto.setPayerAcctType(calFeeRequestDto.getPayerAcctType());
			balanceDealDto.setPayerCurrencyCode(calFeeRequestDto
					.getPayerCurrencyCode());
			if(calFeeDetailDtos.get(i).getCrdr()==1){
				balanceDealDto.setPayerFullMemberAcctCode(calFeeDetailDtos.get(i).getAcctcode());
			}
			balanceDealDto.setPayerMemberAcctCode(calFeeRequestDto
					.getPayerMemberAcctCode());
			balanceDealDto.setPayerOrgCode(calFeeRequestDto.getPayerOrgCode());
			balanceDealDto.setPayerOrgType(calFeeRequestDto.getPayerOrgType());
			balanceDealDto.setPayerServiceLevel(calFeeRequestDto
					.getPayerServiceLevel());

			balanceDealDto.setSubmitAcctCode(calFeeRequestDto.getSubmitAcctCode());
			balanceDealDto.setTerminalType(calFeeRequestDto.getTerminalType());
			balanceDealDto.setPayMethod(calFeeRequestDto.getPayMethod());
			balanceDealDto.setPaymentServicePkgCode(calFeeRequestDto
					.getPaymentServicePkgCode());
			balanceDealDto.setRequestDate(calFeeRequestDto.getRequestDate());

			balanceDealDto.setVoucherCode(updateBalanceRequestDto.getVoucherCode());
			// balanceDealDto.set(updateBalanceRequestDto.getPriceStrategyCode());
			balanceDealDtos.add(balanceDealDto);	
		}
		return balanceDealDtos;
	}

	private AcctDto handlerQueryAcctInfo(String acctCode)
			throws MaAcctBalanceException {
		AcctDto acctDto = null;
		try {
			acctDto = this.acctService.queryAcctWithAcctCode(acctCode);
		} catch (AcctServiceException e) {
			log.error(e.getMessage(), e);
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.ACCT_QUERY_ERROR, e.getMessage(), e);
		} catch (AcctServiceUnkownException e) {
			log.error("系统异常", e);
			throw new MaAcctBalanceException(ErrorExceptionEnum.UNKNOW_ERROR,
					"系统异常", e);
		}
		if (acctDto == null) {
			log.error("对于" + acctCode + "账户不存在");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.ACCT_NON_EXIST_ERROR, "账户不存在");
		}
		if (acctDto.getStatus() != AcctEnum.VALID.getCode()) {
			log.error("对于账号" + acctCode + "无效");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.ACCT_INVAILD_OR_FREEZE, "对于账号"
							+ acctCode + "无效");
		}
		return acctDto;
	}

	private AcctAttribDto handlerQueryAcctAttribInfo(String acctCode)
			throws MaAcctBalanceException {

		// 验证属性是否存在
		AcctAttribDto acctAttribDto = null;
		try {
			acctAttribDto = this.acctAttribService
					.queryAcctAttribWithAcctCode(acctCode);
		} catch (AcctAttribException e) {
			log.error(e.getMessage(), e);
			throw new MaAcctBalanceException(e.getMessage(),
					ErrorExceptionEnum.ACCT_ATTRIBUTE, e);

		} catch (AcctAttribUnknowException e) {
			log.error("系统异常", e);
			throw new MaAcctBalanceException(ErrorExceptionEnum.UNKNOW_ERROR,
					"系统异常", e);
		}
		if (acctAttribDto != null) {
			if (acctAttribDto.getFrozen().intValue() != ConstantHelper.ACCT_FREEZE_STATUS) {
				log.error("账户[" + acctCode + "]被冻结");
				throw new MaAcctBalanceException(
						ErrorExceptionEnum.ACCT_FROZEN_ERROR, "账户[" + acctCode
								+ "]被冻结");
			}

		}

		return acctAttribDto;
	}

	/**
	 * 更新借方余额
	 * 
	 * @param amount
	 * @param acctCode
	 * @throws MaAcctBalanceException
	 */
	private void handlerUpdateCrebitBalance(Long amount, Long creditAmount,
			String acctCode, boolean isReduce) throws MaAcctBalanceException {
		boolean result = false;
		try {
			log.info("更新借方[" + acctCode + "]余额");
			result = this.acctService.updateAcctCreditBalanceWithAcctCode(
					amount, creditAmount, acctCode, isReduce);
		} catch (AcctServiceException e) {
			log.error(e.getMessage(), e);
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.INPUT_PARA_NULL, e.getMessage(), e);
		} catch (AcctServiceUnkownException e) {
			log.error("系统异常", e);
			throw new MaAcctBalanceException(ErrorExceptionEnum.UNKNOW_ERROR,
					"系统异常", e);
		}
		if (!result) {
			log.error("账户" + acctCode + "余额操作失败");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.ACCT_BALANCE_ERROR, "账户余额操作失败");
		}

	}

	/**
	 * 更新贷方余额
	 * 
	 * @param amount
	 * @param debitAmount
	 * @param acctCode
	 * @throws MaAcctBalanceException
	 */
	private void handlerUpdateDebitBalance(Long amount, Long debitAmount,
			String acctCode, boolean isAdd) throws MaAcctBalanceException {
		boolean result = false;
		try {
			log.info("更新贷方[" + acctCode + "]余额");
			result = this.acctService.updateAcctDebitBalanceWithAcctCode(
					amount, debitAmount, acctCode, isAdd);
		} catch (AcctServiceException e) {
			log.error(e.getMessage(), e);
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.INPUT_PARA_NULL, e.getMessage(), e);
		} catch (AcctServiceUnkownException e) {
			log.error("系统异常", e);
			throw new MaAcctBalanceException(ErrorExceptionEnum.UNKNOW_ERROR,
					"系统异常", e);
		}
		if (!result) {
			log.error("账户" + acctCode + "余额操作失败");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.ACCT_BALANCE_ERROR, "账户余额操作失败");
		}

	}

	// 获取拼凑好的收付方对象集合
	private BanlanceDealTempDto getMergerFeeList(
			List<CalFeeDetailDto> calFeeTempList, Long ammountTemp)
			throws MaAcctBalanceException {

		int feeSize = calFeeTempList.size();
		BanlanceDealTempDto bdt = new BanlanceDealTempDto();

		// TODO
		if (feeSize % 2 == 0) {
			for (CalFeeDetailDto cfd : calFeeTempList) {
				if (ammountTemp.equals(cfd.getValue()) && cfd.getStatus() != -1) {
					if (cfd.getMaBlanceBy() == 1) {
						bdt.setPayeeFullMemberAcctCode(cfd.getAcctcode());
						bdt.setAmount(cfd.getValue());
					} else if (cfd.getMaBlanceBy() == 2) {
						bdt.setPayerFullMemberAcctCode(cfd.getAcctcode());
						bdt.setAmount(cfd.getValue());
					}
					cfd.setStatus(-1);
					if (bdt.getPayeeFullMemberAcctCode() != null
							&& bdt.getPayerFullMemberAcctCode() != null) {
						return bdt;
					}
				} else {
					continue;
				}

			}
		} else {
			throw new MaAcctBalanceException("传入的分录记录为[" + feeSize
					+ "]条,目前不支持合并分录");
		}
		return null;
	}

	private void handlerSaveCalFeeTempResponseInfo(
			CalFeeReponseDto updateBalanceRequestDto, Integer payType,
			List<BanlanceDealTempDto> dealList) throws MaAcctBalanceException {
		Long seqId = 0L;
		try {
			for (BanlanceDealTempDto bdt : dealList) {
				BalanceDealDto balanceFeeDeal = handlerBeanConvertBalanceFeeDealDto(
						updateBalanceRequestDto, bdt);
				seqId = this.balanceDealService
						.createBalanceDeal(balanceFeeDeal);
			}
		} catch (BalanceException e) {
			log.error(e.getMessage(), e);
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.INVAILD_PARAMETER, e.getMessage(), e);
		} catch (BalanceUnkownException e) {
			log.error("系统异常", e);
			throw new MaAcctBalanceException(ErrorExceptionEnum.UNKNOW_ERROR,
					"系统异常", e);
		}
		if (seqId == null || seqId.longValue() <= 0) {
			log.error("账户交易插入失败");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.BALANCE_DEAL_INSERT_FAIL, "账户交易插入失败");
		}
	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	public void setAcctAttribService(AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}

	public void setBalanceEntryService(BalanceEntryService balanceEntryService) {
		this.balanceEntryService = balanceEntryService;
	}

	public void setBalanceDealService(BalanceDealService balanceDealService) {
		this.balanceDealService = balanceDealService;
	}

	public void setNegativeBalanceService(
			NegativeBalanceService negativeBalanceService) {
		this.negativeBalanceService = negativeBalanceService;
	}

	/**
	 * @param jmsSender
	 *            the jmsSender to set
	 */
	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

	public void setAcctSpecService(AcctSpecService acctSpecService) {
		this.acctSpecService = acctSpecService;
	}

}
