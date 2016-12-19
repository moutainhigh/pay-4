/**
 * 
 */
package com.pay.poss.amountma.service.impl;

import java.math.BigDecimal;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.inf.dao.Page;
import com.pay.pe.dto.AccountingDto;
import com.pay.pe.dto.AccountingFeeRe;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.amountma.dao.IFrozenLogDao;
import com.pay.poss.amountma.dto.FrozenLogDto;
import com.pay.poss.amountma.service.IFrozenLogService;
import com.pay.poss.security.util.SessionUserHolderUtil;

/**
 * @Description
 * @project ma-manager
 * @file FrozenLogServiceImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 *          Date Author Changes 2011-5-2 DDR Create
 */
public class FrozenLogServiceImpl implements IFrozenLogService {

	private static final Log logger = LogFactory
			.getLog(FrozenLogServiceImpl.class);

	private IFrozenLogDao frozenLogDao;

	private MemberQueryService memberQueryService;

	private AccountQueryService accountQueryService;

	/**
	 * 基本户冻结记账
	 */
	private AccountingService accounting_900_900;
	/**
	 * 基本户解冻记账
	 */
	private AccountingService accounting_900_902;
	/**
	 * 保证金户冻结记账
	 */
	private AccountingService accounting_900_901;
	/**
	 * 保证金户解冻记账
	 */
	private AccountingService accounting_900_903;

	// private AccountBalanceService accountBalanceService;

	@Override
	public Page<FrozenLogDto> search(Page<FrozenLogDto> paramPage,
			FrozenLogDto dto) {
		return frozenLogDao.findPage(paramPage, dto);
	}

	@Override
	public BigDecimal getBlanceByLongNameOrCode(String nameOrCode,Integer acctType)
			throws Exception {

		Long memberCode = getMemberCodeByLongNameOrCode(nameOrCode);
		if (memberCode == null) {
			return null;
		}
		BalancesDto bo = accountQueryService
				.doQueryBalancesNsTx(memberCode,acctType);
		return BigDecimal.valueOf(bo.getBalance()).divide(
				BigDecimal.valueOf(1000));

	}

	@Override
	public boolean addFrozenLogRnTx(String memberCode, BigDecimal amount,
			String desc,Integer acctType) {
		// 得到最新的数据库的金额
		BigDecimal blance = null;
		String acctCode=null;
		try {
			BalancesDto bo = accountQueryService
					.doQueryBalancesNsTx(Long.valueOf(memberCode),acctType);
			
			blance = BigDecimal.valueOf(bo.getBalance()).divide(
					BigDecimal.valueOf(1000));
			acctCode = bo.getAcctCode();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询memberCode时报错：" + memberCode, e);
			return false;
		}
		// 得到减去 后的 新的可用额度
		BigDecimal newBlance = blance.subtract(amount);
		// 如果newBlance比0小说明余额不足
		if (newBlance.compareTo(BigDecimal.ZERO) == -1) {
			logger.info("余额不足，" + "blance:" + blance + ",-amount:" + amount);
			return false;
		}

		long amountLong = amount.multiply(BigDecimal.valueOf(1000)).longValue();
		// 通过验证以后做更新操作
		logger.info("冻结操作开始----------------------");
		Long memberCodeLong = getMemberCodeByLongNameOrCode(memberCode);
		// 添加冻结操作addFrozenAmount与记账操作doAccounting_900_900都更新t_acct账户冻结金额和账户余额造成死锁，这里直接用记账
		// by tom.wang 2016年5月6日11:26:03
		/*
		 * boolean isUpdateOk = frozenLogDao.addFrozenAmount(memberCodeLong,
		 * amount,acctCode) == 1;
		 */
		// 生成6位随机数
		boolean isUpdateOk=false;
		Random rnd = new Random();
		int n = 100 + rnd.nextInt(900);
		String serialNo = "300" + System.currentTimeMillis() + n;// 冻结操作订单号
		try {
			String currencyCode = AcctTypeEnum.getAcctCurrencyByCode(acctType);
			logger.info("冻结操作开始记账----------------------");
			boolean flag = acctType > 200;// 基本户还是保证金户标示 true:保证金户
			doAccounting_900_900_901(serialNo, memberCodeLong,
					amountLong, currencyCode, flag);
			logger.info("冻结操作更新冻结金额----------------------");
			isUpdateOk =frozenLogDao.addFrozenAmount(memberCodeLong, amount,acctCode)==1;
			logger.info("冻结金额：" + (isUpdateOk ? "成功" : "失败") + "，但是未提交");
		} catch (Exception e) {
			logger.info("冻结记账失败----------------------");
			e.printStackTrace();
		}

		FrozenLogDto dto = new FrozenLogDto();
		//dto.setAmount(amount);
		dto.setAcctType(acctType);
		dto.setFrozenType(1);// 1冻结
		dto.setAmount(amount.multiply(BigDecimal.valueOf(1000)));//
		dto.setMemberCode(Long.valueOf(memberCodeLong));
		String loginId = SessionUserHolderUtil.getLoginId();
		dto.setOperatorName(loginId);
		dto.setSerialNo(Long.valueOf(serialNo));
		dto.setStatus(isUpdateOk ? 1 : 0);
		dto.setDescription(desc);
		dto.setBalance(newBlance.multiply(BigDecimal.valueOf(1000)));
		frozenLogDao.create(dto);
		logger.info("创建冻结日志成功");
		logger.info("冻结结果：-" + (isUpdateOk ? "成功" : "失败"));
		logger.info("冻结操作正常结束----------------------");
		return isUpdateOk;

	}

	/**
	 * 
	 * @createTime : 2016年5月7日 上午11:44:21 
	 * @method:doAccounting_900_900_901 
	 * @description 基本户和保证金户的冻结记账方法
	 * @param orderId
	 * @param partnerId
	 * @param amount
	 * @param currencyCode
	 * @param flag   是否是保证金户的标识 true:保证金户
	 * @throws Exception
	 * @return void
	 * @throws
	 * @author tom.wang
	 */
	public void doAccounting_900_900_901(String orderId, Long partnerId,
			Long amount, String currencyCode, boolean flag) throws Exception {
	
		AccountingDto accountingDto = new AccountingDto();
		accountingDto.setOrderId(orderId);
		accountingDto.setPayer(partnerId);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		logger.info("in first doAccounting_900_900_901");
		Integer acctType = flag ? AcctTypeEnum
				.getGuaranteeAcctTypeByCurrency(currencyCode) : AcctTypeEnum
				.getBasicAcctTypeByCurrency(currencyCode);
		AcctAttribDto acctAttribDto = accountQueryService
				.doQueryAcctAttribNsTx(partnerId, acctType);
		accountingDto.setPayerAcctType(acctAttribDto.getAcctType());
		accountingDto.setPayerFullMemberAcctCode(acctAttribDto.getAcctCode());

		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		logger.info("in second doAccounting_900_900_901");
		if (flag) {
			accounting_900_901.doAccounting(accountingDto);
		} else {
			accounting_900_900.doAccounting(accountingDto);
		}
	}

	/**
	 * 
	 * createTime : 2016年5月7日 上午11:42:49 method:doAccounting_900_902_903 TODO
	 * 基本户和保证金户的解冻记账方法
	 * 
	 * @throws
	 * @author tom.wang
	 */
	public void doAccounting_900_902_903(String orderId, Long partnerId,
			Long amount, String currencyCode, boolean flag) throws Exception {
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setOrderId(orderId);
		accountingDto.setPayee(partnerId);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		Integer acctType = flag ? AcctTypeEnum
				.getGuaranteeAcctTypeByCurrency(currencyCode) : AcctTypeEnum
				.getBasicAcctTypeByCurrency(currencyCode);
		AcctAttribDto acctAttribDto = accountQueryService
				.doQueryAcctAttribNsTx(partnerId, acctType);
		accountingDto.setPayeeAcctType(acctAttribDto.getAcctType());
		accountingDto.setPayeeFullMemberAcctCode(acctAttribDto.getAcctCode());
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);

		if (flag) {
			accounting_900_903.doAccounting(accountingDto);
		} else {
			accounting_900_902.doAccounting(accountingDto);
		}
	}

	public AccountQueryService getAccountQueryService() {
		return accountQueryService;
	}

	@Override
	public synchronized boolean freeFrozenLogRnTx(String memberCode, Long id) {

		// 解冻操作
		// 查询原来的冻结的id号
		FrozenLogDto oldDto = frozenLogDao.findById(id);
		
		System.out.println("oldDto: "+oldDto);
		
		// 只有oldDto.getStatus()为1的时候才是有效的
		if (oldDto != null && oldDto.getStatus() != 1) {
			logger.error("原流水=" + oldDto.getSerialNo() + "，状态"
					+ oldDto.getStatus() + ";状态或不正确，或为空");
			return false;
		}
		// 得到最新的数据库的金额
		BigDecimal blance = null;
		String acctCode=null;
		try {
			BalancesDto bo = accountQueryService
					.doQueryBalancesNsTx(Long.valueOf(memberCode),oldDto.getAcctType());
			
			blance = BigDecimal.valueOf(bo.getBalance()).divide(
					BigDecimal.valueOf(1000));
			acctCode = bo.getAcctCode();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询memberCode时报错：" + memberCode, e);
			return false;
		}
		// 得到最新的blance
		BigDecimal newBlance = blance.add(oldDto.getAmount().divide(
				BigDecimal.valueOf(1000)));
		logger.info("解冻操作开始----------------------");
		// 生成6位随机数
		Random rnd = new Random();
		int n = 100 + rnd.nextInt(900);
		String serialNo = "300" + System.currentTimeMillis() + n;// 冻结操作订单号
		boolean flag = oldDto.getAcctType() > 200;// 基本户还是保证金户标示 true:保证金户
		boolean isUpdateOk=false;
		try {
			logger.info("解冻开始记账----------------------");
			String currencyCode = AcctTypeEnum.getAcctCurrencyByCode(oldDto
					.getAcctType());
			doAccounting_900_902_903(serialNo, Long.valueOf(memberCode), oldDto
					.getAmount().longValue(),
					currencyCode, flag);
			frozenLogDao.updateFrozenLogStatus(id, 2);// 将状态更新为解冻
			logger.info("解冻开始记账成功更新冻结金额----------------------");
			isUpdateOk = frozenLogDao.freeFrozenAmount(Long.valueOf(memberCode),oldDto.getAmount().divide(BigDecimal.valueOf(1000)),acctCode) == 1;
			 logger.info("更新" + id + "在状态为2(已解冻)成功，" + "但未提交");
		} catch (Exception e) {
			logger.info("解冻记账失败----------------------");
			e.printStackTrace();
		}
		FrozenLogDto dto = new FrozenLogDto();
		dto.setAmount(oldDto.getAmount());
		dto.setAcctType(oldDto.getAcctType());
		dto.setFrozenType(2);// 1冻结
		dto.setMemberCode(oldDto.getMemberCode());
		String loginId = SessionUserHolderUtil.getLoginId();
		dto.setOperatorName(loginId);
		dto.setSerialNo(Long.valueOf(serialNo));
		dto.setStatus(isUpdateOk ? 1 : 0);
		dto.setOldSerialId(oldDto.getSerialNo());
		dto.setBalance(newBlance.multiply(BigDecimal.valueOf(1000)));
		long id1 = (Long) frozenLogDao.create(dto);
		logger.info("创建解冻日志成功");
		logger.info("解冻结果：" + (isUpdateOk ? "成功" : "失败"));
		logger.info("解冻操作正常结束----------------------");
		return isUpdateOk;

	}

	@Override
	public Long getMemberCodeByLongNameOrCode(String nameOrCode) {
		Long memberCode = null;
		try {
			if (nameOrCode.indexOf("@") >= 0 || (nameOrCode.matches("\\d{11}"))) {
				MemberInfoDto dto = memberQueryService.doQueryMemberInfoNsTx(
						nameOrCode, null, null, null);
				if (dto == null) {
					dto = memberQueryService.doQueryMemberInfoNsTx(null,
							Long.valueOf(nameOrCode), null, null);
				}
				if (dto != null) {
					memberCode = dto.getMemberCode();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return memberCode;
	}

	@Override
	public FrozenLogDto getById(Long id) {
		return frozenLogDao.findById(id);
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

	public void setFrozenLogDao(IFrozenLogDao frozenLogDao) {
		this.frozenLogDao = frozenLogDao;
	}

	public void setAccounting_900_902(AccountingService accounting_900_902) {
		this.accounting_900_902 = accounting_900_902;
	}

	public void setAccounting_900_900(AccountingService accounting_900_900) {
		this.accounting_900_900 = accounting_900_900;
	}

	public void setAccounting_900_901(AccountingService accounting_900_901) {
		this.accounting_900_901 = accounting_900_901;
	}

	public void setAccounting_900_903(AccountingService accounting_900_903) {
		this.accounting_900_903 = accounting_900_903;
	}

}
