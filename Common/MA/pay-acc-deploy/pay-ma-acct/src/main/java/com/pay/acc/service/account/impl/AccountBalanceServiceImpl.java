/**
 * 
 */
package com.pay.acc.service.account.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

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
import com.pay.acc.balancelog.dto.BalanceLogDto;
import com.pay.acc.balancelog.exception.BalanceLogException;
import com.pay.acc.balancelog.exception.BalanceLogUnknowExcetpion;
import com.pay.acc.balancelog.service.BalanceLogService;
import com.pay.acc.commons.AcctEnum;
import com.pay.acc.commons.AmountByEnum;
import com.pay.acc.commons.ConstantHelper;
import com.pay.acc.pedealinfo.dto.PeDealInfoDto;
import com.pay.acc.pedealinfo.exception.PeDealInfoException;
import com.pay.acc.pedealinfo.service.PeDealInfoService;
import com.pay.acc.service.account.AccountBalanceService;
import com.pay.acc.service.account.dto.AccountBalanceChangeDto;
import com.pay.acc.service.account.dto.AcctDetailBalanceDto;
import com.pay.acc.service.account.dto.ChargeUpPEDto;
import com.pay.acc.service.account.exception.MaAcctBalanceException;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.acc.translog.dto.TransLogDto;
import com.pay.acc.translog.exception.TransLogException;
import com.pay.acc.translog.exception.TransLogUnknowException;
import com.pay.acc.translog.service.TransLogService;
import com.pay.pe.helper.CRDRType;
import com.pay.util.ValidatorUtil;

/**
 * @author Administrator
 * 
 */
@Deprecated
public class AccountBalanceServiceImpl implements AccountBalanceService {

	private AcctService acctService;
	private AcctAttribService acctAttribService;
	private TransLogService transLogService;
	private BalanceLogService balanceLogService;
	private PeDealInfoService peDealInfoService;
	private Log log = LogFactory.getLog(AccountBalanceServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.pay.acc.service.account.AccountBalanceService#
	 * doUpdateAcctBalanceRntx
	 * (com.pay.acc.service.account.dto.AccountBalanceChangeDto)
	 */
	@Override
	@Deprecated
	public int doUpdateAcctBalanceRntx(
			AccountBalanceChangeDto accountBalanceChangeDto)
			throws MaAcctBalanceException {
		log.debug("####################################################更新开始###################################################");
		// 验证入参
		this.log.debug("验证余额变化参数 AccountBalanceChangeDto："
				+ accountBalanceChangeDto.toString());
		this.handlerCheckEnterPara(accountBalanceChangeDto);
		List<AcctDetailBalanceDto> acctDetailBalanceDtos = accountBalanceChangeDto
				.getAcctDetailBalanceDtos();
		this.log.debug("验证余额明细列表");

		if (acctDetailBalanceDtos == null || acctDetailBalanceDtos.size() == 0) {
			this.log.error("余额信息列表数据为空");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.INPUT_PARA_LIST_NULL, "余额信息列表数据为空");
		}
		// 检查ChargeUpPEDto参数
		this.handlerValidateChargeUpPEDto(accountBalanceChangeDto
				.getChargeUpPEDto());
		Long serialNo = new Long(0);
		Integer payType = null;
		Date payDate = null;

		for (AcctDetailBalanceDto acctDetailBalanceDto : acctDetailBalanceDtos) {
			// 验证AcctDetailBalanceDto 参数属性
			this.handlerValidateAcctBalanceDetail(acctDetailBalanceDto);
			// 验证流水号是否唯一
			serialNo = acctDetailBalanceDto.getSerialNo();
			payType = acctDetailBalanceDto.getPayType();
			payDate = acctDetailBalanceDto.getPayDate();
			this.handlerCheckSerialNo(serialNo);
			this.handlerAcctBalanceTrans(acctDetailBalanceDto,
					accountBalanceChangeDto.getChargeUpPEDto());

		}
		this.hanlderSaveTransLogWithChargeUpPEDto(
				accountBalanceChangeDto.getChargeUpPEDto(), serialNo, payType,
				payDate);
		// 保存记账dto信息
		this.handlerSavePeDealInfo(accountBalanceChangeDto.getChargeUpPEDto(),
				serialNo, payType);
		// 异步调用记账接口
		log.debug("####################################################更新结束###################################################");
		return this.SUCCESS;
	}

	private void handlerSavePeDealInfo(ChargeUpPEDto chargeUpPEDto,
			Long serialNo, Integer dealType) throws MaAcctBalanceException {
		PeDealInfoDto peDealInfoDto = new PeDealInfoDto();
		// base data
		peDealInfoDto.setDealCode(chargeUpPEDto.getDealCode());
		peDealInfoDto.setOrderAmount(chargeUpPEDto.getOrderAmount());
		peDealInfoDto.setSubmitAcctCode(chargeUpPEDto.getSubmitAcctCode());
		peDealInfoDto.setOrderCode(chargeUpPEDto.getOrderCode());
		peDealInfoDto.setOrderCode(chargeUpPEDto.getOrderCode());
		peDealInfoDto.setPayMethod(chargeUpPEDto.getPayMethod());
		peDealInfoDto.setSerialNo(serialNo);
		// pay data
		peDealInfoDto.setPayAcctCode(chargeUpPEDto.getPayAcctCode());
		peDealInfoDto.setPayOrgCode(chargeUpPEDto.getPayOrgCode());
		peDealInfoDto.setPayFee(chargeUpPEDto.getPayFee());
		peDealInfoDto.setPayMemberCode(chargeUpPEDto.getPayMemberCode());
		peDealInfoDto.setPayOrgType(chargeUpPEDto.getPayOrgType());
		peDealInfoDto.setPayAcctType(chargeUpPEDto.getPayAcctType());

		// rev data
		peDealInfoDto.setRevAcctCode(chargeUpPEDto.getRevAcctCode());
		peDealInfoDto.setRevOrgCode(chargeUpPEDto.getRevOrgCode());
		peDealInfoDto.setRevFee(chargeUpPEDto.getRevFee());
		peDealInfoDto.setRevMemberCode(chargeUpPEDto.getRevMemberCode());
		peDealInfoDto.setRevOrgType(chargeUpPEDto.getRevOrgType());
		peDealInfoDto.setRevAcctType(chargeUpPEDto.getRevAcctType());

		peDealInfoDto.setDealType(dealType);

		try {
			this.peDealInfoService.createPeDealInfo(peDealInfoDto);
		} catch (PeDealInfoException e) {
			log.error(e.getMessage(), e);
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.INPUT_PARA_NULL, "输入的参数不能为空");

		}

	}

	private void handlerCheckEnterPara(
			AccountBalanceChangeDto accountBalanceChangeDto)
			throws MaAcctBalanceException {
		if (accountBalanceChangeDto == null) {
			log.error("输入的参数不能为空");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.INPUT_PARA_NULL, "输入的参数不能为空");

		}

	}

	private void handlerValidateAcctBalanceDetail(
			AcctDetailBalanceDto acctDetailBalanceDto)
			throws MaAcctBalanceException {
		if (acctDetailBalanceDto == null) {
			log.error("输入的参数不能为空");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.INPUT_PARA_NULL, "输入的参数不能为空");
		}
		// 验证入参DTO属性
		Set<ConstraintViolation<AcctDetailBalanceDto>> set = ValidatorUtil
				.validator(acctDetailBalanceDto);
		for (ConstraintViolation<AcctDetailBalanceDto> constraintViolation : set) {
			log.error(constraintViolation.getMessage());
			throw new MaAcctBalanceException(constraintViolation.getMessage());
		}
	}

	private void handlerValidateChargeUpPEDto(ChargeUpPEDto chargeUpPEDto)
			throws MaAcctBalanceException {
		if (chargeUpPEDto == null) {
			log.error("输入的参数不能为空");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.INPUT_PARA_NULL, "输入的参数不能为空");
		}
		// 验证入参DTO属性
		Set<ConstraintViolation<ChargeUpPEDto>> set = ValidatorUtil
				.validator(chargeUpPEDto);
		for (ConstraintViolation<ChargeUpPEDto> constraintViolation : set) {
			log.error(constraintViolation.getMessage());
			throw new MaAcctBalanceException(constraintViolation.getMessage());
		}

	}

	private void handlerCheckSerialNo(Long serialNo)
			throws MaAcctBalanceException {
		TransLogDto transLogDto = null;
		try {
			transLogDto = this.transLogService
					.queryTransLogDtoWithSerialNo(serialNo);
		} catch (TransLogException e) {
			log.error(e.getMessage(), e);
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.INPUT_PARA_NULL, e.getMessage(), e);
		} catch (TransLogUnknowException e) {
			log.error("未知异常", e);
			throw new MaAcctBalanceException(ErrorExceptionEnum.UNKNOW_ERROR,
					"未知异常", e);
		}
		if (transLogDto != null) {
			log.error("该流水号已经存在");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.TRANS_SERIAL_NO_EXIST, "该流水号已经存在");
		}

	}

	private Long handlerAcctBalanceTrans(
			AcctDetailBalanceDto acctDetailBalanceDto,
			ChargeUpPEDto chargeUpPEDto) throws MaAcctBalanceException {

		String tmpAcctCode = acctDetailBalanceDto.getAcctCode();
		// 初始化变化余额
		Long changeAmount = new Long(0);
		// 借方向
		if (acctDetailBalanceDto.getDebitBy() == CRDRType.DEBIT.getValue()) {
			AcctDto revAcctDto = this.handlerQueryAcctInfo(tmpAcctCode);
			// rev
			AcctAttribDto revAcctAttribDto = this
					.handlerQueryAcctAttribInfo(tmpAcctCode);
			// 为非中间科目账户做属性验证
			if (revAcctAttribDto != null) {
				this.handlerCheckAcctAttrib(revAcctAttribDto, false);
			}
			// 余额为加
			if (acctDetailBalanceDto.getAmountBy() == AmountByEnum.ADD
					.getCode()) {
				changeAmount = acctDetailBalanceDto.getAmount();
			}
			// 余额为减
			else {
				changeAmount = -acctDetailBalanceDto.getAmount();
			}
			// 更新借方余额
			this.handlerUpdateCrebitBalance(changeAmount, changeAmount,
					tmpAcctCode);
			// 记录借方余额日志
			this.handlerSaveBalanceLog(tmpAcctCode,
					acctDetailBalanceDto.getAmount(), revAcctDto.getBalance()
							+ changeAmount, acctDetailBalanceDto.getPayType(),
							CRDRType.DEBIT.getValue(),
					acctDetailBalanceDto.getSerialNo());

		}
		// 贷方
		if (acctDetailBalanceDto.getDebitBy() == CRDRType.CREDIT.getValue()) {

			AcctDto payAcctDto = this.handlerQueryAcctInfo(tmpAcctCode);
			AcctAttribDto payAcctAttribDto = this
					.handlerQueryAcctAttribInfo(tmpAcctCode);
			// 为非中间科目账户做属性验证
			if (payAcctAttribDto != null) {
				this.handlerCheckAcctAttrib(payAcctAttribDto, true);
			}
			// 余额为加
			if (acctDetailBalanceDto.getAmountBy() == AmountByEnum.ADD
					.getCode()) {
				changeAmount = acctDetailBalanceDto.getAmount();

			} else {
				if (payAcctDto.getBalance() < 0
						|| payAcctDto.getBalance() < acctDetailBalanceDto
								.getAmount()) {
					log.error("余额不足");
					throw new MaAcctBalanceException(
							ErrorExceptionEnum.ACCT_NO_SAVE_ACCOUNT, "余额不足");
				}
				changeAmount = -acctDetailBalanceDto.getAmount();
			}
			// 更新贷方余额
			this.handlerUpdateDebitBalance(changeAmount, changeAmount,
					tmpAcctCode);
			// 保存贷方
			this.handlerSaveBalanceLog(tmpAcctCode,
					acctDetailBalanceDto.getAmount(), payAcctDto.getBalance()
							+ changeAmount, acctDetailBalanceDto.getPayType(),
							CRDRType.CREDIT.getValue(),
					acctDetailBalanceDto.getSerialNo());
		}
		return acctDetailBalanceDto.getSerialNo();

	}

	/**
	 * 
	 * 
	 * @param acctCode
	 * @return
	 * @throws MaAcctBalanceException
	 */
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
			log.error("未知异常", e);
			throw new MaAcctBalanceException(ErrorExceptionEnum.UNKNOW_ERROR,
					"未知异常", e);
		}
		if (acctDto == null) {
			log.error("对于" + acctCode + "账户不存在");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.ACCT_NON_EXIST_ERROR, "账户不存在");
		}
		if (acctDto.getStatus() != AcctEnum.VALID.getCode()) {
			log.error("对于账号" + acctCode + "无效或者被冻结");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.ACCT_INVAILD_OR_FREEZE, "对于账号"
							+ acctCode + "无效或者被冻结");
		}
		return acctDto;
	}

	/**
	 * 将 acctCode改为String,数据库有变化，暂时不动，如果是中间科目，没有账户属性
	 * 
	 * @param acctCode
	 * @return
	 * @throws MaAcctBalanceException
	 */
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
			log.error("未知异常", e);
			throw new MaAcctBalanceException(ErrorExceptionEnum.UNKNOW_ERROR,
					"未知异常", e);
		}

		return acctAttribDto;
	}

	/**
	 * @param acctAttribDto
	 * @param payType
	 * @param isPay
	 *            true 为付款方
	 * @throws MaAcctBalanceException
	 */
	private void handlerCheckAcctAttrib(AcctAttribDto acctAttribDto,
			boolean isPay) throws MaAcctBalanceException {

		if (isPay) {
			if (!acctAttribDto.getAllowOut().equals(ConstantHelper.ALLOW_OUT)) {
				log.error("该账户止出");
				throw new MaAcctBalanceException(
						ErrorExceptionEnum.ACCT_ALLOWOUT_ERROR, "该账户止出");
			}
		} else {
			// 对收款方进行判断
			if (!acctAttribDto.getAllowIn().equals(ConstantHelper.ALLOW_IN)) {
				log.error("该账户止入");
				throw new MaAcctBalanceException(
						ErrorExceptionEnum.ACCT_ALLOWOUT_ERROR, "该账户止入");

			}
		}

	}

	/**
	 * 更新借方余额
	 * 
	 * @param amount
	 * @param acctCode
	 * @throws MaAcctBalanceException
	 */
	private void handlerUpdateCrebitBalance(Long amount, Long creditAmount,
			String acctCode) throws MaAcctBalanceException {
		// boolean result = false;
		// try {
		// result = this.acctService.updateAcctCreditBalanceWithAcctCode(amount,
		// creditAmount, acctCode);
		// } catch (AcctServiceException e) {
		// log.error(e.getMessage(), e);
		// throw new MaAcctBalanceException(ErrorExceptionEnum.INPUT_PARA_NULL,
		// e.getMessage(), e);
		// } catch (AcctServiceUnkownException e) {
		// log.error("未知异常", e);
		// throw new MaAcctBalanceException(ErrorExceptionEnum.UNKNOW_ERROR,
		// "未知异常", e);
		// }
		// if (!result) {
		// log.error("账户余额操作失败");
		// throw new
		// MaAcctBalanceException(ErrorExceptionEnum.ACCT_BALANCE_ERROR,
		// "账户余额操作失败");
		// }

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
			String acctCode) throws MaAcctBalanceException {
		boolean result = false;
		// try {
		// result = this.acctService.updateAcctDebitBalanceWithAcctCode(amount,
		// debitAmount, acctCode);
		// } catch (AcctServiceException e) {
		// log.error(e.getMessage(), e);
		// throw new MaAcctBalanceException(ErrorExceptionEnum.INPUT_PARA_NULL,
		// e.getMessage(), e);
		// } catch (AcctServiceUnkownException e) {
		// log.error("未知异常", e);
		// throw new MaAcctBalanceException(ErrorExceptionEnum.UNKNOW_ERROR,
		// "未知异常", e);
		// }
		// if (!result) {
		// log.error("账户余额操作失败");
		// throw new
		// MaAcctBalanceException(ErrorExceptionEnum.ACCT_BALANCE_ERROR,
		// "账户余额操作失败");
		// }

	}

	private Long hanlderSaveTransLogWithChargeUpPEDto(
			ChargeUpPEDto chargeUpPEDto, Long serialNo, Integer payType,
			Date payDate) throws MaAcctBalanceException {
		TransLogDto transLogDto = new TransLogDto();
		transLogDto.setConfirmDate(new Date());
		transLogDto.setSerialNo(serialNo);
		transLogDto.setAcctType(chargeUpPEDto.getPayAcctType());
		transLogDto.setAmount(chargeUpPEDto.getOrderAmount());
		transLogDto.setPayAcct(chargeUpPEDto.getPayAcctCode());
		transLogDto.setRecvAcct(chargeUpPEDto.getRevAcctCode());
		transLogDto.setPayType(payType);
		transLogDto.setPayDate(payDate);
		Long id = new Long(0);
		try {
			id = this.transLogService.createTransLog(transLogDto);
		} catch (TransLogException e) {
			log.error(e.getMessage(), e);
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.TRANS_SAVE_LOG_ERROR, e.getMessage(), e);
		} catch (TransLogUnknowException e) {
			log.error("未知异常", e);
			throw new MaAcctBalanceException(ErrorExceptionEnum.UNKNOW_ERROR,
					"未知异常", e);
		}
		if (id == null || id.longValue() <= 0) {
			log.error("支付日志保存异常");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.TRANS_SAVE_LOG_ERROR, "支付日志保存异常");
		}
		return id;

	}

	private Long handlerSaveBalanceLog(String acctCode, Long amount,
			Long balance, Integer balanceChangeType, Integer balanceDirect,
			Long serialNo) throws MaAcctBalanceException {
		BalanceLogDto balanceLogDto = new BalanceLogDto();
		balanceLogDto.setAcctCode(acctCode);
		balanceLogDto.setAmount(amount);
		balanceLogDto.setBalance(balance);
		balanceLogDto.setBalanceChangeType(balanceChangeType);
		balanceLogDto.setBalanceDirect(balanceDirect);
		balanceLogDto.setPayDate(new Date());
		balanceLogDto.setSerialNo(serialNo);
		Long id = null;
		try {
			id = this.balanceLogService.createBalanceLog(balanceLogDto);
		} catch (BalanceLogException e) {
			log.error(e.getMessage(), e);
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.ACCT_BALANCE_ERROR, e.getMessage(), e);
		} catch (BalanceLogUnknowExcetpion e) {
			log.error("未知异常", e);
			throw new MaAcctBalanceException(ErrorExceptionEnum.UNKNOW_ERROR,
					"未知异常", e);
		}
		if (id == null || id.longValue() <= 0) {
			log.error("账户余额日志插入失败");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.BALANCE_LOG_INSERT_ERROR, "账户余额日志插入失败");
		}
		return id;

	}

	/**
	 * @param peDealInfoService
	 *            the peDealInfoService to set
	 */
	public void setPeDealInfoService(PeDealInfoService peDealInfoService) {
		this.peDealInfoService = peDealInfoService;
	}

	/**
	 * @param acctService
	 *            the acctService to set
	 */
	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	/**
	 * @param acctAttribService
	 *            the acctAttribService to set
	 */
	public void setAcctAttribService(AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}

	/**
	 * @param transLogService
	 *            the transLogService to set
	 */
	public void setTransLogService(TransLogService transLogService) {
		this.transLogService = transLogService;
	}

	/**
	 * @param balanceLogService
	 *            the balanceLogService to set
	 */
	public void setBalanceLogService(BalanceLogService balanceLogService) {
		this.balanceLogService = balanceLogService;
	}

}
