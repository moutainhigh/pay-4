/**
 *  File: EbppAccountingServiceTest.java
 *  Description:
 *  Copyright 2006-2010 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2010-10-4   terry_ma     Create
 *
 */
package com.pay.fundout.accounting;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.pe.dto.AccountingFeeRe;
import com.pay.pe.dto.AccountingFeeRes;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.util.CurrencyCodeEnum;

@ContextConfiguration(locations = { "classpath*:context/**/*.xml" })
public class AccountingServiceTest extends AbstractTestNGSpringContextTests {

	private final Log logger = LogFactory.getLog(getClass());
	@Resource(name = "acc-accountQueryService")
	private AccountQueryService accountQueryService;
	@Resource(name = "accounting-100-100")
	private AccountingService accounting_100_100;

	@Resource(name = "accounting-100-101")
	private AccountingService accounting_100_101;
	@Resource(name = "accounting-100-102")
	private AccountingService accounting_100_102;
	@Resource(name = "accounting-100-103")
	private AccountingService accounting_100_103;

	@Resource(name = "accounting-200-200")
	private AccountingService accounting_200_200;
	@Resource(name = "accounting-200-201")
	private AccountingService accounting_200_201;
	@Resource(name = "accounting-200-202")
	private AccountingService accounting_200_202;
	@Resource(name = "accounting-200-203")
	private AccountingService accounting_200_203;

	@Resource(name = "fundout-withdrawFirstAccounting")
	private AccountingService accounting_300_300;
	@Resource(name = "fundout-withdrawSuccessAccounting")
	private AccountingService accounting_300_301;
	@Resource(name = "fundout-withdrawFailAccounting")
	private AccountingService accounting_300_302;
	@Resource(name = "fundout-withdrawReturnAccounting")
	private AccountingService accounting_400_400;

	@Resource(name = "accounting-600-600")
	private AccountingService accounting_600_600;

	// @Test
	public void testAccounting() throws Exception {

		String orgCode = "10076001";
		String orderId = System.currentTimeMillis() + "";
		Long amount = 100000L;
		BigDecimal rate = new BigDecimal("1.1");

		testAccounting_100_100(orgCode, orderId, amount,
				CurrencyCodeEnum.EUR.getCode());
		// testAccounting_100_101(orgCode, orderId,
		// amount,CurrencyCodeEnum.EUR.getCode());
		// testAccounting_100_102(orgCode, orderId,
		// amount,CurrencyCodeEnum.CNY.getCode(), rate);
		// testAccounting_100_103(orgCode, orderId,
		// amount,CurrencyCodeEnum.CNY.getCode(), rate);

		// orderId = "1591505212050000090";
		String currencyCode = CurrencyCodeEnum.CNY.getCode();
		Long partnerId = 10000003593L;
		Long orderAmount = 100000L;

		// doAccounting_200_200(orderId,amount,currencyCode);
		// doAccounting_200_201(orderId,amount,currencyCode,rate.toString());
		// doAccounting_200_203(partnerId,orderId,amount,currencyCode,rate.toString());
		// doAccounting_200_202(partnerId, orderId, orderAmount,
		// amount,currencyCode, rate.toString());

		String bankCode = "10003001";
		Integer acctType = AcctTypeEnum.BASIC_CNY.getCode();
		// testAccounting_300_300(bankCode,orderId,partnerId,acctType,orderAmount,currencyCode);

		// testAccounting_300_301(bankCode,orderId,partnerId,acctType,orderAmount,currencyCode);
		// testAccounting_300_302(bankCode,orderId,partnerId,acctType,orderAmount,currencyCode);
		// testAccounting_400_400(bankCode,orderId,partnerId,acctType,orderAmount,currencyCode);

		System.out.println("记账订单号-OrderId:" + orderId);
	}

	public void testAccounting_100_100(String orgCode, String orderId,
			Long amount, String currencyCode) throws Exception {

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setBankCode(orgCode);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);

		accounting_100_100.doAccounting(accountingDto);

		System.out.println(orderId + "          #######################");
	}

	public void testAccounting_100_101(String orgCode, String orderId,
			Long amount, String currencyCode) throws Exception {

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setBankCode(orgCode);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);

		accounting_100_101.doAccounting(accountingDto);

		System.out.println(orderId + "          #######################");
	}

	public void testAccounting_100_102(String orgCode, String orderId,
			Long amount, String currencyCode, BigDecimal rate) throws Exception {

		BigDecimal aAmount = new BigDecimal(amount).multiply(rate);
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(aAmount);
		accountingDto.setOrderAmount(aAmount);
		accountingDto.setBankCode(orgCode);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);

		accounting_100_102.doAccounting(accountingDto);

		System.out.println(orderId + "          #######################");
	}

	public void testAccounting_100_103(String orgCode, String orderId,
			Long amount, String currencyCode, BigDecimal rate) throws Exception {

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setBankCode(orgCode);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);

		AccountingFeeRes accountingFeeRes = accounting_100_103
				.caculateFee(accountingDto);

		Long fee = 0L;
		if (null != accountingFeeRes) {
			fee = accountingFeeRes.getPayeeFee();
			logger.info("fee:" + fee);
		}

		BigDecimal dAmount = new BigDecimal(amount - fee).multiply(rate);
		BigDecimal fAmount = new BigDecimal(fee).multiply(rate);

		accountingDto.setHasCaculatedPrice(true);
		accountingDto.setPayeeFee(fAmount.longValue());
		accountingDto.setAmount(dAmount);

		accounting_100_103.doAccounting(accountingDto);

		System.out.println(orderId + "          #######################");
	}

	public int doAccounting_200_200(String orderId, Long amount,
			String currencyCode) throws Exception {

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		// accountingDto.setBankCode(orgCode);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);

		return accounting_200_200.doAccountingReturn(accountingDto);
	}

	public int doAccounting_200_201(String orderId, Long amount,
			String currencyCode, String rate) throws Exception {

		BigDecimal aAmount = new BigDecimal(amount).multiply(new BigDecimal(
				rate));
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(aAmount);
		accountingDto.setOrderAmount(aAmount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		return accounting_200_201.doAccountingReturn(accountingDto);
	}

	public int doAccounting_200_203(Long partnerId, String orderId,
			Long amount, String currencyCode, String rate) throws Exception {

		BigDecimal aAmount = new BigDecimal(amount).multiply(new BigDecimal(
				rate));
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(aAmount);
		accountingDto.setOrderAmount(aAmount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setPayee(partnerId);
		Integer acctType = AcctTypeEnum
				.getGuaranteeAcctTypeByCurrency(currencyCode);
		AcctAttribDto acctAttribDto = accountQueryService
				.doQueryAcctAttribNsTx(partnerId, acctType);
		accountingDto.setPayeeAcctType(acctType);
		accountingDto.setPayeeFullMemberAcctCode(acctAttribDto.getAcctCode());
		return accounting_200_203.doAccountingReturn(accountingDto);
	}

	public void doAccounting_200_202(Long partnerId, String orderId,
			Long orderAmount, Long amount, String settlementCurrencyCode,
			String rate) throws Exception {

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(orderAmount);
		accountingDto.setOrderAmount(orderAmount);
		accountingDto.setOrderId(orderId);
		accountingDto.setPayerCurrencyCode(settlementCurrencyCode);
		accountingDto.setPayeeCurrencyCode(settlementCurrencyCode);
		accountingDto.setPayee(partnerId);
		Integer acctType = AcctTypeEnum
				.getBasicAcctTypeByCurrency(settlementCurrencyCode);
		AcctAttribDto acctAttribDto = accountQueryService
				.doQueryAcctAttribNsTx(partnerId, acctType);
		accountingDto.setPayeeAcctType(acctAttribDto.getAcctType());
		accountingDto.setPayeeFullMemberAcctCode(acctAttribDto.getAcctCode());

		AccountingFeeRes accountingFeeRes = accounting_200_202
				.caculateFee(accountingDto);

		Long fee = 0L;
		if (null != accountingFeeRes) {
			fee = accountingFeeRes.getPayeeFee();
			logger.info("fee:" + fee);
		}

		BigDecimal dAmount = new BigDecimal(amount - fee)
				.multiply(new BigDecimal(rate));
		BigDecimal fAmount = new BigDecimal(fee).multiply(new BigDecimal(rate));

		accountingDto.setHasCaculatedPrice(true);
		accountingDto.setPayeeFee(fAmount.longValue());
		accountingDto.setAmount(dAmount);

		accounting_200_202.doAccounting(accountingDto);
	}

	public void testAccounting_300_300(String bankCode, String orderId,
			Long memberCode, Integer acctType, Long amount, String currencyCode)
			throws Exception {

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayer(memberCode);
		accountingDto.setPayerAcctType(acctType);
		accountingDto.setBankCode(bankCode);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);

		accounting_300_300.doAccounting(accountingDto);

		System.out.println(orderId + "          #######################");
	}

	public void testAccounting_300_301(String bankCode, String orderId,
			Long memberCode, Integer acctType, Long amount, String currencyCode)
			throws Exception {

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayer(memberCode);
		accountingDto.setPayerAcctType(acctType);
		accountingDto.setBankCode(bankCode);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);

		accounting_300_301.doAccounting(accountingDto);

		System.out.println(orderId + "          #######################");
	}

	public void testAccounting_300_302(String bankCode, String orderId,
			Long memberCode, Integer acctType, Long amount, String currencyCode)
			throws Exception {

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayee(memberCode);
		accountingDto.setPayeeAcctType(acctType);
		accountingDto.setBankCode(bankCode);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);

		accounting_300_302.doAccounting(accountingDto);

		System.out.println(orderId + "          #######################");
	}

	public void testAccounting_400_400(String bankCode, String orderId,
			Long memberCode, Integer acctType, Long amount, String currencyCode)
			throws Exception {

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayee(memberCode);
		accountingDto.setPayeeAcctType(acctType);
		accountingDto.setBankCode(bankCode);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);

		accounting_400_400.doAccounting(accountingDto);

		System.out.println(orderId + "          #######################");
	}

	@Test
	public void testAccounting_600_600() {

		Long amount = 100000L;
		Long fee = 10L;
		Long memberCode = 10000003593L;
		Integer acctType = AcctTypeEnum.BASIC_CNY.getCode();
		String currencyCode = CurrencyCodeEnum.CNY.getCode();
		String orderId = System.currentTimeMillis() + "";

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayer(memberCode);
		accountingDto.setPayerAcctType(acctType);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);

		accountingDto.setPayerFee(fee);

		accounting_600_600.doAccounting(accountingDto);

		System.out.println(orderId + "          #######################");
	}

}
