/**
 * 
 */
package com.pay.acc.service.account.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.acct.exception.AcctServiceException;
import com.pay.acc.acct.exception.AcctServiceUnkownException;
import com.pay.acc.acct.model.PseudoAcct;
import com.pay.acc.acct.service.AcctService;
import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.acctattrib.exception.AcctAttribException;
import com.pay.acc.acctattrib.exception.AcctAttribUnknowException;
import com.pay.acc.acctattrib.service.AcctAttribService;
import com.pay.acc.common.config.WithdrawalsConfig;
import com.pay.acc.constant.AcctStatusEnum;
import com.pay.acc.constant.MemberInfoStatusEnum;
import com.pay.acc.deal.exception.BalanceException;
import com.pay.acc.deal.exception.BalanceUnkownException;
import com.pay.acc.deal.model.BalanceEntry;
import com.pay.acc.deal.service.BalanceDealService;
import com.pay.acc.deal.service.BalanceEntryService;
import com.pay.acc.deal.service.WithdrawalsRuleService;
import com.pay.acc.exception.MaAccountQueryUntxException;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.exception.MemberException;
import com.pay.acc.member.exception.MemberUnknowException;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.constantenum.AcctAttribEnum;
import com.pay.acc.service.account.constantenum.AcctLockTypeEnum;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.acc.service.account.constantenum.OperateTypeEnum;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.acc.service.account.dto.BorrowBalanceDto;
import com.pay.acc.service.account.dto.MaResultDto;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.rm.blackwhitelist.dto.BlackWhiteListDto;
import com.pay.rm.blackwhitelist.service.BlackWhiteListService;
import com.pay.util.BeanConvertUtil;
import com.pay.util.DateUtil;

/**
 * @author wolf_huang
 * 
 * @date 2010-7-16
 */
public class AccountQueryServiceImpl implements AccountQueryService {

	private AcctService acctService;

	private AcctAttribService acctAttribService;

	private MemberService memberService;

	private BalanceEntryService balanceEntryService;

	private BalanceDealService balanceDealService;

	private BlackWhiteListService blackWhiteListService;

	private WithdrawalsRuleService withdrawalsRuleService;

	private Log log = LogFactory.getLog(AccountQueryServiceImpl.class);

	@Override
	public Long queryBalanceByAcctCode(final String acctCode) {
		try {
			AcctDto acctDto = acctService.queryAcctWithAcctCode(acctCode);
			if (null != acctDto) {
				return acctDto.getBalance();
			}
		} catch (AcctServiceException e) {
			log.error(e);
		} catch (AcctServiceUnkownException e) {
			log.error(e);
		}
		return 0L;
	}

	@Override
	public AcctAttribDto doQueryDefaultAcctAttribNsTx(final Long memberCode) {
		return acctAttribService.doQueryDefaultAcctAttribNsTx(memberCode);
	}

	@Override
	public List<AcctAttribDto> doQueryAcctAttribByMemberCodeAndCurrencyCodeNsTx(
			final Long memberCode, final String currencyCode) {
		return acctAttribService
				.doQueryAcctAttribByMemberCodeAndCurrencyCodeNsTx(memberCode,
						currencyCode);
	}

	@Override
	public boolean isAllowAcctrib(final String acctCode, final AcctAttribEnum acctEnum) {
		Map<String, Object> paramMap = new HashMap<String, Object>(2);
		paramMap.put(acctEnum.getCode(), acctEnum.getVale());
		paramMap.put("acctCode", acctCode);
		return acctAttribService.isAllowAcctrib(paramMap);
	}

	@Override
	public boolean countIsAllowAcctribByMemberCode(final Long memberCode,
			final AcctAttribEnum acctEnum) {
		Map<String, Object> paramMap = new HashMap<String, Object>(2);
		paramMap.put(acctEnum.getCode(), acctEnum.getVale());
		paramMap.put("memberCode", memberCode);
		return acctAttribService.countIsAllowAcctribByMemberCode(paramMap);
	}

	/**
	 * 查询余额 (non-Javadoc)
	 * 
	 * @see com.pay.ma.service.balance.AccountQueryService#doQueryWithdrawBalanceNsTx(long,
	 *      int)
	 */
	@Override
	public BalancesDto doQueryBalancesNsTx(final Long memberCode, final Integer accountType)
			throws MaAccountQueryUntxException {
		if (null == memberCode || memberCode.longValue() <= 0) {
			log.error(" invaild parameter , memberCode is null ! ");
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.INVAILD_PARAMETER,
					"invaild parameter , memberCode is null ! ");
		}
		MemberDto member = memberService.queryMemberByMemberCode(memberCode);
		if (null == member) {
			log.error(" member is null ! ");
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR,
					"member is null ! ");
		}
		log.info("memberCode:" + memberCode + ",accountType:" + accountType);
		AcctDto acctDto = null;
		log.info("memberCode:" + memberCode);
		try {
			// 查询账户余额
			acctDto = this.acctService.queryAcctByMemberCodeAndAcctTypeId(
					memberCode, accountType);
			log.info("acctDto: "+acctDto);
		} catch (Exception e) {
			log.error("acct unknown error !", e);
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.ACCT_UNKNOWN_ERROR,
					"acct unknown error !", e);
		}
		if (acctDto == null) {
			log.error("会员号[" + memberCode + "]的账户不存在 ");
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.ACCT_NON_EXIST_ERROR, "会员号["
							+ memberCode + "]的账户不存在 : " +accountType);
		}
		log.info("Balance:" + acctDto.getBalance());
		Long withdrawBalance = 0L;
		if (member.getType() == MemberTypeEnum.MERCHANT.getCode()) {// 企业会员提现金额就为当前余额
			withdrawBalance = acctDto.getBalance();
		} else {
			// 个人用户提现规则
			try {
				// 是否计算可提现金额
				boolean isWithdrawBalance = false;
				// 查询是否白名单
				List<BlackWhiteListDto> whiteList = blackWhiteListService
						.queryMemberWhiteOrBlackList(memberCode, 301);
				// 白名单
				if (null != whiteList && whiteList.size() > 0) {
					long whiteDate = WithdrawalsConfig.getInstance()
							.getWhiteDate();
					int whiteRule = WithdrawalsConfig.getInstance()
							.getWhiteRule();
					withdrawBalance = withdrawalsRuleService
							.withdrawRuleConfig(whiteRule, memberCode, acctDto,
									whiteDate);
					isWithdrawBalance = true;
				}
				if (!isWithdrawBalance) {
					// 查询是否黑名单
					List<BlackWhiteListDto> blackList = blackWhiteListService
							.queryMemberWhiteOrBlackList(memberCode, 302);
					if (null != blackList && blackList.size() > 0) {
						long blackDate = WithdrawalsConfig.getInstance()
								.getBlackDate();
						int blackRule = WithdrawalsConfig.getInstance()
								.getBlackRule();
						withdrawBalance = withdrawalsRuleService
								.withdrawRuleConfig(blackRule, memberCode,
										acctDto, blackDate);
						isWithdrawBalance = true;

					}
				}
				// 普通用户
				if (!isWithdrawBalance) {
					long commonDate = WithdrawalsConfig.getInstance()
							.getCommonDate();
					int commonRule = WithdrawalsConfig.getInstance()
							.getCommonRule();
					withdrawBalance = withdrawalsRuleService
							.withdrawRuleConfig(commonRule, memberCode,
									acctDto, commonDate);
				}

			} catch (Exception e) {
				log.error("query blackWhite is error!", e);
				throw new MaAccountQueryUntxException(e);
			}
		}
		BalancesDto balancesDto = new BalancesDto();
		balancesDto.setStatus(acctDto.getStatus());
		balancesDto.setAcctCode(acctDto.getAcctCode());
		balancesDto.setBalance(acctDto.getBalance());
		balancesDto.setFrozeAmount(acctDto.getFrozeAmount());
		balancesDto
				.setWithdrawBalance(withdrawBalance > acctDto.getBalance() ? acctDto
						.getBalance() : withdrawBalance);
		return balancesDto;
	}

	public BalanceDealService getBalanceDealService() {
		return balanceDealService;
	}

	public void setBalanceDealService(final BalanceDealService balanceDealService) {
		this.balanceDealService = balanceDealService;
	}

	@Override
	public AcctAttribDto doQueryAcctAttribNsTx(final Long memberCode,
			final Integer accountType) throws MaAccountQueryUntxException {
		// 验证会员
		log.debug("根据会员号" + memberCode + "验证会员");
		this.handlerCheckMemberWithMemberCode(memberCode);

		AcctDto acctDto = null;
		try {
			acctDto = this.acctService.queryAcctByMemberCodeAndAcctTypeId(
					memberCode, accountType);
		} catch (Exception e) {
			log.error("acct unknown error !", e);
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.ACCT_UNKNOWN_ERROR,
					"acct unknown error !", e);
		}
		if (acctDto == null) {
			log.error("会员号[" + memberCode + "]的账户不存在 ");
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.ACCT_NON_EXIST_ERROR, "会员号["
							+ memberCode + "]的账户不存在 ");
		}

		String acctCode = acctDto.getAcctCode();

		// 查询账户属性
		com.pay.acc.acctattrib.dto.AcctAttribDto acd = null;

		try {
			acd = this.acctAttribService.queryAcctAttribWithAcctCode(acctCode);
			if (null != acd) {
				if (AcctLockTypeEnum.FREEZE_IN.getLockValue() == acd
						.getAllowIn().intValue()) {// 如果当前属性为止入
					int possAllowIn = this.acctAttribService
							.countPossAcctAttrib(acctCode,
									AcctLockTypeEnum.FREEZE_IN
											.getAcctLockTypeCode(),
									OperateTypeEnum.FREEZE_IN.getCode());
					if (possAllowIn > 0) {
						acd.setPossAllowIn(true);
					}
				}
				if (AcctLockTypeEnum.FREEZE_OUT.getLockValue() == acd
						.getAllowOut().intValue()) {// 如果当前属性为止出
					int possAllowOut = this.acctAttribService
							.countPossAcctAttrib(acctCode,
									AcctLockTypeEnum.FREEZE_OUT
											.getAcctLockTypeCode(),
									OperateTypeEnum.FREEZE_OUT.getCode());
					if (possAllowOut > 0) {
						acd.setPossAllowOut(true);
					}
				}
				if (AcctLockTypeEnum.FREEZE_ACCOUNT.getLockValue() == acd
						.getFrozen().intValue()) {// 如果当前属性为冻结
					int possFrozen = this.acctAttribService
							.countPossAcctAttrib(acctCode,
									AcctLockTypeEnum.FREEZE_ACCOUNT
											.getAcctLockTypeCode(),
									OperateTypeEnum.FREEZE.getCode());
					if (possFrozen > 0) {
						acd.setPossFrozen(true);
					}
				}
			}

		} catch (AcctAttribException e) {
			log.error(e.getMessage(), e);
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.INPUT_PARA_NULL, e.getMessage(), e);
		} catch (AcctAttribUnknowException e) {
			log.error("未知异常", e);
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.UNKNOW_ERROR, "未知异常", e);
		}
		if (acd == null) {
			log.error("会员号[" + memberCode + "]的账户属性不存在");
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.ACCT_ATTRI_NO_EXIST, "会员号[" + memberCode
							+ "]的账户属性不存在");
		}
		return BeanConvertUtil.convert(AcctAttribDto.class, acd);

	}

	@Override
	public MaResultDto doQueryCrdrSumByMemberCodeRntx(final Long memberCode,
			final Integer accountType, final String startAt, final String endAt) {
		MaResultDto mresultDto = new MaResultDto();
		BorrowBalanceDto bbDto = null;

		try {
			AcctDto acctDto = this.acctService
					.queryAcctByMemberCodeAndAcctTypeId(memberCode, accountType);
			if (acctDto != null) {
				String acctCode = acctDto.getAcctCode();
				com.pay.acc.acctattrib.dto.AcctAttribDto acd = this.acctAttribService
						.queryAcctAttribWithAcctCode(acctCode);
				if (acd != null) {
					List<BalanceEntry> beList = this.balanceEntryService
							.queryCrdrSumByAcctCode(acctCode, startAt, endAt);
					if (beList != null && beList.size() > 0) {
						Long creditSumBalance = 0L;
						Long debitSumBalance = 0L;
						int k = 0;
						for (BalanceEntry be : beList) {
							if (k == 0) {
								creditSumBalance = be.getValue() == null ? 0L
										: be.getValue();
							} else {
								debitSumBalance = be.getValue() == null ? 0L
										: be.getValue();
							}
							k++;
						}
						bbDto = new BorrowBalanceDto(memberCode, acctCode,
								creditSumBalance, debitSumBalance);
						mresultDto.setObject(bbDto);
					}
				} else {
					log.error("会员号[" + memberCode + "]的账户属性不存在");
					mresultDto
							.setErrorEnum(ErrorExceptionEnum.ACCT_ATTRI_NO_EXIST);
				}

			} else {
				log.error("会员号[" + memberCode + "]的账户不存在 ");
				mresultDto
						.setErrorEnum(ErrorExceptionEnum.ACCT_NON_EXIST_ERROR);
			}

		} catch (AcctServiceException e) {
			log.error(e.getMessage(), e);
			mresultDto.setErrorEnum(ErrorExceptionEnum.ACCT_UNKNOWN_ERROR);
		} catch (AcctServiceUnkownException e) {
			log.error(e.getMessage(), e);
			mresultDto.setErrorEnum(ErrorExceptionEnum.UNKNOW_ERROR);
		} catch (AcctAttribException e) {
			log.error(e.getMessage(), e);
			mresultDto.setErrorEnum(ErrorExceptionEnum.INPUT_PARA_NULL);
		} catch (Exception e) {
			log.error("未知异常", e);
			mresultDto.setErrorEnum(ErrorExceptionEnum.UNKNOW_ERROR);

		}
		return mresultDto;
	}

	@Override
	public Long doQueryBalanceByEntryRntx(final Long memberCode, final Integer accountType) {
		Long balance = 0L;
		try {
			AcctDto acctDto = this.acctService
					.queryAcctByMemberCodeAndAcctTypeId(memberCode, accountType);
			if (acctDto != null) {
				String acctCode = acctDto.getAcctCode();
				com.pay.acc.acctattrib.dto.AcctAttribDto acd = this.acctAttribService
						.queryAcctAttribWithAcctCode(acctCode);
				if (acd != null) {
					java.text.SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd 00:00:00");
					String defaultAt = sdf.format(new Date());
					balance = this.balanceEntryService.queryBalanceByAcctCode(
							acctCode, defaultAt);

				} else {
					log.error("会员号[" + memberCode + "]的账户属性不存在");

				}

			} else {
				log.error("会员号[" + memberCode + "]的账户不存在 ");

			}

		} catch (AcctServiceException e) {
			log.error(e.getMessage(), e);
		} catch (AcctServiceUnkownException e) {
			log.error(e.getMessage(), e);
		} catch (AcctAttribException e) {
			log.error(e.getMessage(), e);

		} catch (Exception e) {
			log.error("未知异常", e);

		}
		return balance;
	}

	private void handlerCheckMemberWithMemberCode(final Long memberCode)
			throws MaAccountQueryUntxException {
		MemberDto memberDto = null;
		try {
			memberDto = this.memberService
					.queryMemberWithMemberCode(memberCode);
		} catch (MemberException e) {
			log.error(e.getMessage(), e);
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.INPUT_PARA_NULL, e.getMessage(), e);
		} catch (MemberUnknowException e) {
			log.error("未知异常", e);
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.UNKNOW_ERROR, "未知异常", e);
		}
		if (memberDto == null) {
			log.error("不存在会员号为[" + memberCode + "]的会员");
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR, "不存在会员号为["
							+ memberCode + "]的会员");
		}
		if (null == memberDto.getStatus()
				|| (memberDto.getStatus().intValue() != MemberInfoStatusEnum.NORMAL
						.getCode() && memberDto.getStatus().intValue() != MemberInfoStatusEnum.TEMP
						.getCode())) {

			log.error("会员号为[" + memberCode + "]处于非"
					+ MemberInfoStatusEnum.NORMAL.getDescription());
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.MEMBER_INVALID, "会员号为[" + memberCode
							+ "]处于非"
							+ MemberInfoStatusEnum.NORMAL.getDescription());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.pay.acc.service.account.AccountQueryService#
	 * doQueryBalanceEntryForTransAmountRntx(java.lang.Long, java.lang.Integer,
	 * java.lang.Long, java.lang.Integer)
	 */
	@Override
	public boolean doQueryBalanceEntryForTransAmountRntx(final Long memberCode,
			final Integer acctType, final Long amount, final Integer days)
			throws MaAccountQueryUntxException {

		boolean flag = false;
		// 验证参数
		this.checkQueryParameter(memberCode, acctType, amount, days);
		this.checkQueryMember(memberCode);

		// 查询账户是否存在
		AcctDto bcctDto = this.checkAcctDto(memberCode, acctType);
		if (null == bcctDto) {
			// 账户不存在
			log.error("会员号[" + memberCode + "]的账户不存在 ");
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.ACCT_NON_EXIST_ERROR, "会员号["
							+ memberCode + "]的账户不存在 ");
			// 需要判断用户是否有效，如果该用户无效或者冻结，就不能进行查询交易
		}
		if (bcctDto.getStatus() == AcctStatusEnum.INVALID.getCode()) {
			log.error("会员号[" + memberCode + "]的账户无效");
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.ACCT_INVALID_ERROR, "会员号[" + memberCode
							+ "]的账户无效");
		}

		// 根据天数得到查询结束日期
		String pattern = "yyyy-MM-dd";
		Date toDate = new Date();
		Date endDate = DateUtil.skipDateTime(toDate, (-1) * days);
		String strEndDate = DateUtil.formatDateTime(pattern, endDate);
		Long transAmounts = 0L;
		try {
			transAmounts = balanceEntryService.queryBalanceEntryForTransAmount(
					bcctDto.getAcctCode(), strEndDate);
		} catch (BalanceException e) {
			log.error(e.getMessage(), e);
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter",
					e);
		} catch (BalanceUnkownException e) {
			log.error("unknow error", e);
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.UNKNOW_ERROR, "unknow error", e);
		}

		// 判断金额与交易额度
		if (transAmounts <= amount) {
			flag = true;
		}
		return flag;
	}

	@Override
	public Long selectBalanceByAcctCodeAndDate(final String acctCode, final Date date) {
		return this.balanceEntryService.selectBalanceByAcctCodeAndDate(
				acctCode, date);
	}

	// 查询账户是否存在
	private AcctDto checkAcctDto(final Long memberCode, final Integer acctType)
			throws MaAccountQueryUntxException {

		AcctDto acctDto = null;
		try {
			acctDto = acctService.queryAcctByMemberCodeAndAcctTypeId(
					memberCode, acctType);
		} catch (AcctServiceException e) {
			log.error(e.getMessage(), e);
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter",
					e);
		} catch (AcctServiceUnkownException e) {
			log.error("unknow error", e);
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.UNKNOW_ERROR, "unknow error", e);
		}

		return acctDto;
	}

	// 验证参数
	private boolean checkQueryParameter(final Long memberCode, final Integer accountType,
			final Long amount, final Integer days) throws MaAccountQueryUntxException {
		if (null == memberCode || memberCode.longValue() <= 0) {
			log.error(" invaild parameter , memberCode is null or  invaild! ");
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.INVAILD_PARAMETER,
					"invaild parameter , memberCode is null or invaild ! ");
		} else if (null == accountType || accountType.intValue() <= 0) {
			log.error(" invaild parameter , accountType is null or  invaild! ");
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.INVAILD_PARAMETER,
					"invaild parameter , accountType is null or  invaild! ");
		} else if (null == amount || amount.longValue() <= 0) {
			log.error(" invaild parameter , amount is null or  invaild! ");
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.INVAILD_PARAMETER,
					"invaild parameter , amount is null or  invaild! ");
		} else if (null == days || days.intValue() <= 0) {
			log.error(" invaild parameter , days is null or  invaild! ");
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.INVAILD_PARAMETER,
					"invaild parameter , days is null or  invaild! ");
		}
		return true;

	}

	// 会员验证参数
	private MemberDto checkQueryMember(final Long memberCode)
			throws MaAccountQueryUntxException {
		if (null == memberCode || memberCode.longValue() <= 0) {
			log.error(" invaild parameter , memberCode is null or  invaild! ");
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.INVAILD_PARAMETER,
					"invaild parameter , memberCode is null or  invaild! ");
		}
		MemberDto memberDto = null;
		try {
			memberDto = this.memberService
					.queryMemberWithMemberCode(memberCode);
		} catch (MemberException e) {
			log.error(e.getMessage(), e);
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter",
					e);
		} catch (MemberUnknowException e) {
			log.error("unknow error", e);
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.UNKNOW_ERROR, "unknow error", e);
		}
		if (memberDto == null) {
			log.error("不存在会员号为[" + memberCode + "]的会员");
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR, "不存在会员号为["
							+ memberCode + "]的会员");
		}
		if (null == memberDto.getStatus()
				|| memberDto.getStatus().intValue() != MemberInfoStatusEnum.NORMAL
						.getCode()) {
			log.error("会员号为[" + memberCode + "]处于非"
					+ MemberInfoStatusEnum.NORMAL.getDescription());
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.MEMBER_INVALID, "会员号为[" + memberCode
							+ "]处于非"
							+ MemberInfoStatusEnum.NORMAL.getDescription());
		}
		return memberDto;

	}

	/**
	 * @param memberService
	 *            the memberService to set
	 */
	public void setMemberService(final MemberService memberService) {
		this.memberService = memberService;
	}

	public AcctService getAcctService() {
		return acctService;
	}

	public void setAcctService(final AcctService acctService) {
		this.acctService = acctService;
	}

	public AcctAttribService getAcctAttribService() {
		return acctAttribService;
	}

	public void setAcctAttribService(final AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}

	public BalanceEntryService getBalanceEntryService() {
		return balanceEntryService;
	}

	public void setBalanceEntryService(final BalanceEntryService balanceEntryService) {
		this.balanceEntryService = balanceEntryService;
	}

	public void setWithdrawalsRuleService(
			final WithdrawalsRuleService withdrawalsRuleService) {
		this.withdrawalsRuleService = withdrawalsRuleService;
	}

	public MemberService getMemberService() {
		return memberService;
	}

	@Override
	public List<BalancesDto> doQueryBalancesNsTx(final Long memberCode)
			throws MaAccountQueryUntxException {
		if (null == memberCode || memberCode.longValue() <= 0) {
			log.error(" invaild parameter , memberCode is null ! ");
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.INVAILD_PARAMETER,
					"invaild parameter , memberCode is null ! ");
		}
		MemberDto member = memberService.queryMemberByMemberCode(memberCode);
		if (null == member) {
			log.error(" member is null ! ");
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR,
					"member is null ! ");
		}
		log.info("memberCode:" + memberCode);
		List<AcctDto> acctDtos = null;
		log.info("memberCode:" + memberCode);
		try {
			// 查询账户余额
			acctDtos = this.acctService.queryAcctByMemberCode(memberCode);
		} catch (Exception e) {
			log.error("acct unknown error !", e);
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.ACCT_UNKNOWN_ERROR,
					"acct unknown error !", e);
		}
		if (acctDtos == null) {
			log.error("会员号[" + memberCode + "]的账户不存在 ");
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.ACCT_NON_EXIST_ERROR, "会员号["
							+ memberCode + "]的账户不存在 ");
		}
		List<BalancesDto> balancesDtos = new ArrayList<BalancesDto>();
		for (AcctDto acctDto : acctDtos) {

			Long withdrawBalance = 0L;
			if (member.getType() == MemberTypeEnum.MERCHANT.getCode()) {// 企业会员提现金额就为当前余额
				withdrawBalance = acctDto.getBalance();

			} else {
				// 个人用户提现规则
				try {
					// 是否计算可提现金额
					boolean isWithdrawBalance = false;
					// 查询是否白名单
					List<BlackWhiteListDto> whiteList = blackWhiteListService
							.queryMemberWhiteOrBlackList(memberCode, 301);
					// 白名单
					if (null != whiteList && whiteList.size() > 0) {
						long whiteDate = WithdrawalsConfig.getInstance()
								.getWhiteDate();
						int whiteRule = WithdrawalsConfig.getInstance()
								.getWhiteRule();
						withdrawBalance = withdrawalsRuleService
								.withdrawRuleConfig(whiteRule, memberCode,
										acctDto, whiteDate);
						isWithdrawBalance = true;
					}
					if (!isWithdrawBalance) {
						// 查询是否黑名单
						List<BlackWhiteListDto> blackList = blackWhiteListService
								.queryMemberWhiteOrBlackList(memberCode, 302);
						if (null != blackList && blackList.size() > 0) {
							long blackDate = WithdrawalsConfig.getInstance()
									.getBlackDate();
							int blackRule = WithdrawalsConfig.getInstance()
									.getBlackRule();
							withdrawBalance = withdrawalsRuleService
									.withdrawRuleConfig(blackRule, memberCode,
											acctDto, blackDate);
							isWithdrawBalance = true;

						}
					}
					// 普通用户
					if (!isWithdrawBalance) {
						long commonDate = WithdrawalsConfig.getInstance()
								.getCommonDate();
						int commonRule = WithdrawalsConfig.getInstance()
								.getCommonRule();
						withdrawBalance = withdrawalsRuleService
								.withdrawRuleConfig(commonRule, memberCode,
										acctDto, commonDate);
					}

				} catch (Exception e) {
					log.error("query blackWhite is error!", e);
					throw new MaAccountQueryUntxException(e);
				}
			}

			AcctAttribDto acctAttribDto = acctAttribService
					.queryAcctAttribByAcctCode(acctDto.getAcctCode());
			BalancesDto balancesDto = new BalancesDto();
			balancesDto.setAcctName(AcctTypeEnum
					.getAcctNameByCode(acctAttribDto.getAcctType()));
			balancesDto.setAcctCode(acctDto.getAcctCode());
			balancesDto.setAcctType(acctAttribDto.getAcctType());
			balancesDto.setMemberAcctCode(acctAttribDto.getMemberAcctCode());
			balancesDto.setMemberCode(memberCode);
			balancesDto.setCurrencyCode(acctAttribDto.getCurCode());
			balancesDto.setStatus(acctDto.getStatus());
			balancesDto.setBalance(acctDto.getBalance());
			balancesDto.setFrozeAmount(acctDto.getFrozeAmount());
			balancesDto.setWithdrawBalance(withdrawBalance > acctDto
					.getBalance() ? acctDto.getBalance() : withdrawBalance);

			balancesDtos.add(balancesDto);
		}

		return balancesDtos;
	}

	@Override
	public List<BalancesDto> doQueryBalancesBasicNsTx(final Long memberCode)
			throws MaAccountQueryUntxException {
		if (null == memberCode || memberCode.longValue() <= 0) {
			log.error(" invaild parameter , memberCode is null ! ");
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.INVAILD_PARAMETER,
					"invaild parameter , memberCode is null ! ");
		}
		MemberDto member = memberService.queryMemberByMemberCode(memberCode);
		if (null == member) {
			log.error(" member is null ! ");
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR,
					"member is null ! ");
		}
		log.info("memberCode:" + memberCode);
		List<AcctDto> acctDtos = null;
		log.info("memberCode:" + memberCode);
		try {
			// 查询账户余额
			acctDtos = this.acctService.queryAcctByMemberCode(memberCode);
		} catch (Exception e) {
			log.error("acct unknown error !", e);
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.ACCT_UNKNOWN_ERROR,
					"acct unknown error !", e);
		}
		if (acctDtos == null) {
			log.error("会员号[" + memberCode + "]的账户不存在 ");
			throw new MaAccountQueryUntxException(
					ErrorExceptionEnum.ACCT_NON_EXIST_ERROR, "会员号["
							+ memberCode + "]的账户不存在 ");
		}
		List<BalancesDto> balancesDtos = new ArrayList<BalancesDto>();
		for (AcctDto acctDto : acctDtos) {

			Long withdrawBalance = 0L;
			if (member.getType() == MemberTypeEnum.MERCHANT.getCode()) {// 企业会员提现金额就为当前余额
				withdrawBalance = acctDto.getBalance();

			} else {
				// 个人用户提现规则
				try {
					// 是否计算可提现金额
					boolean isWithdrawBalance = false;
					// 查询是否白名单
					List<BlackWhiteListDto> whiteList = blackWhiteListService
							.queryMemberWhiteOrBlackList(memberCode, 301);
					// 白名单
					if (null != whiteList && whiteList.size() > 0) {
						long whiteDate = WithdrawalsConfig.getInstance()
								.getWhiteDate();
						int whiteRule = WithdrawalsConfig.getInstance()
								.getWhiteRule();
						withdrawBalance = withdrawalsRuleService
								.withdrawRuleConfig(whiteRule, memberCode,
										acctDto, whiteDate);
						isWithdrawBalance = true;
					}
					if (!isWithdrawBalance) {
						// 查询是否黑名单
						List<BlackWhiteListDto> blackList = blackWhiteListService
								.queryMemberWhiteOrBlackList(memberCode, 302);
						if (null != blackList && blackList.size() > 0) {
							long blackDate = WithdrawalsConfig.getInstance()
									.getBlackDate();
							int blackRule = WithdrawalsConfig.getInstance()
									.getBlackRule();
							withdrawBalance = withdrawalsRuleService
									.withdrawRuleConfig(blackRule, memberCode,
											acctDto, blackDate);
							isWithdrawBalance = true;

						}
					}
					// 普通用户
					if (!isWithdrawBalance) {
						long commonDate = WithdrawalsConfig.getInstance()
								.getCommonDate();
						int commonRule = WithdrawalsConfig.getInstance()
								.getCommonRule();
						withdrawBalance = withdrawalsRuleService
								.withdrawRuleConfig(commonRule, memberCode,
										acctDto, commonDate);
					}

				} catch (Exception e) {
					log.error("query blackWhite is error!", e);
					throw new MaAccountQueryUntxException(e);
				}
			}

			AcctAttribDto acctAttribDto = acctAttribService
					.queryAcctAttribByAcctCode(acctDto.getAcctCode());

			if (AcctTypeEnum.isBasicCode(acctAttribDto.getAcctType())) {
				BalancesDto balancesDto = new BalancesDto();
				balancesDto.setAcctName(AcctTypeEnum
						.getAcctNameByCode(acctAttribDto.getAcctType()));
				balancesDto.setAcctCode(acctDto.getAcctCode());
				balancesDto.setAcctType(acctAttribDto.getAcctType());
				balancesDto
						.setMemberAcctCode(acctAttribDto.getMemberAcctCode());
				balancesDto.setMemberCode(memberCode);
				balancesDto.setCurrencyCode(acctAttribDto.getCurCode());
				balancesDto.setStatus(acctDto.getStatus());
				balancesDto.setBalance(acctDto.getBalance());
				balancesDto.setFrozeAmount(acctDto.getFrozeAmount());
				balancesDto.setWithdrawBalance(withdrawBalance > acctDto
						.getBalance() ? acctDto.getBalance() : withdrawBalance);
				balancesDtos.add(balancesDto);
			}

		}

		return balancesDtos;
	}

	public void setBlackWhiteListService(
			final BlackWhiteListService blackWhiteListService) {
		this.blackWhiteListService = blackWhiteListService;
	}

	@Override
	public List<AcctDto> doQueryAcctDtoList(final Long memberCode, final Integer acctType) throws Exception {
		return acctService.queryAcctByMemberCode(memberCode, acctType);
	}

	/* (non-Javadoc)
	 * @see com.pay.acc.service.account.AccountQueryService#doQueryBalancesNsTx(java.lang.Long, java.lang.String)
	 */
	@Override
	public Map<String, PseudoAcct> doQueryAcctsNsTx(final Long memberCode, final String currency)
			throws MaAccountQueryUntxException {
		List<PseudoAcct> pseudoAccts = this.acctService.queryAcctByMemberCodeAndCurrency(memberCode, currency) ;
		Map<String, PseudoAcct> map = null ;
		if(CollectionUtils.isNotEmpty(pseudoAccts)){
			map = new HashMap<String, PseudoAcct>() ;
			for(PseudoAcct pseudoAcct : pseudoAccts){
				if(AcctTypeEnum.isBasicCode(pseudoAcct.getAcctType())){
					map.put("basic", pseudoAcct) ;
				}else{
					map.put("guarantee", pseudoAcct) ;
				}
			}
		}
		//对于一些商户可能只开通了基本账户或是保证金账户，特做如下处理
		if(null != map){
			if(!map.containsKey("basic"))
				map.put("basic", new PseudoAcct()) ;
			else if(!map.containsKey("guarantee"))
				map.put("guarantee", new PseudoAcct()) ;
		}
		return map ;
	}

	@Override
	public BigDecimal doUpdateAcctsNsTx(Long memberCode, String currency,String acctType)
			throws MaAccountQueryUntxException{
		BigDecimal frozenAmount=BigDecimal.ZERO;
		BigDecimal unfrozenAmount=BigDecimal.ZERO;
		
		//保证金冻结
		if("2".equals(acctType))
		{
			frozenAmount=this.acctService.queryFrozenAmountByMemberCodeAndCurrency(memberCode, currency,acctType,"901");
			unfrozenAmount=this.acctService.queryUnFrozenAmountByMemberCodeAndCurrency(memberCode, currency,acctType,"903");
		
		//基本户冻结
		}else{
			frozenAmount=this.acctService.queryFrozenAmountByMemberCodeAndCurrency(memberCode, currency,acctType,"900");
			unfrozenAmount=this.acctService.queryUnFrozenAmountByMemberCodeAndCurrency(memberCode, currency,acctType,"902");
		}
		if(null==frozenAmount)
		{
			frozenAmount=BigDecimal.ZERO;
		}
		if(null==unfrozenAmount)
		{
			unfrozenAmount=BigDecimal.ZERO;
		}
		BigDecimal updateAmount=frozenAmount.subtract(unfrozenAmount);
		if(null==updateAmount || updateAmount.compareTo(BigDecimal.ZERO)==-1){
			updateAmount=BigDecimal.ZERO;
		}
		//acctService.unFrozenAmount(unFrozenAmountDto)
		acctService.updateFrozenAmountByMemberCodeAndCurrency(memberCode, currency,acctType,updateAmount);
		return updateAmount;
	}
}
