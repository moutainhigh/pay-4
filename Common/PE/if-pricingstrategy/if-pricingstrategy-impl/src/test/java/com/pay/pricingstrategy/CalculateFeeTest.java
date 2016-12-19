//package com.pay.pricingstrategy;
//
//import javax.annotation.Resource;
//
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import com.pay.pricingstrategy.dto.PricingStrategyDetailDTO;
//import com.pay.pricingstrategy.helper.PRICESTRATEGYTYPE;
//import com.pay.pricingstrategy.service.CalFeeFactory;
//import com.pay.pricingstrategy.service.CalFeeInnerParam;
//
//@ContextConfiguration(locations = { "classpath*:/context/*.xml" })
//@TransactionConfiguration(transactionManager = "pricingstrategyTransactionManager", defaultRollback = true)
//@Transactional
//public class CalculateFeeTest extends
//		AbstractTransactionalTestNGSpringContextTests {
//
//	@Resource
//	private CalFeeFactory calFeeFactory;
//
//	private PricingStrategyDetailDTO getPricingStrategyDetailDTO() {
//		Long chargeRate = 50L;
//		Long maxCharge = 25000L;
//		Long minCharge = 100L;
//		Long fixedCharge = 5000L;
//
//		PricingStrategyDetailDTO dto = new PricingStrategyDetailDTO();
//		dto.setChargeRate(chargeRate);
//		dto.setMaxCharge(maxCharge);
//		dto.setMinCharge(minCharge);
//		dto.setFixedCharge(fixedCharge);
//		return dto;
//	}
//
//	@Test
//	public void testChargeratio() {
//		int pricestrategytype = PRICESTRATEGYTYPE.CHARGERATIO.getValue();
//
//		Long amount = 1000L * 1000;
//
//		PricingStrategyDetailDTO dto = getPricingStrategyDetailDTO();
//		CalFeeInnerParam feeParam = new CalFeeInnerParam();
//		feeParam.setPricingstategydetaildto(dto);
//		feeParam.setTransactionAmount(amount);
//
//		Long fee = calFeeFactory.getCalFee(pricestrategytype)
//				.calculateFeeDetail(feeParam).getFee();
//
//		Assert.assertEquals(fee.longValue(), 5 * 1000);
//	}
//
//	@Test
//	public void testChargeratioLL() {
//		int pricestrategytype = PRICESTRATEGYTYPE.CHARGERATIOANDLOWERLIMIT
//				.getValue();
//		Long amount = 1000L * 1000;
//
//		PricingStrategyDetailDTO dto = getPricingStrategyDetailDTO();
//		CalFeeInnerParam feeParam = new CalFeeInnerParam();
//		feeParam.setPricingstategydetaildto(dto);
//		feeParam.setTransactionAmount(amount);
//
//		Long fee = calFeeFactory.getCalFee(pricestrategytype)
//				.calculateFeeDetail(feeParam).getFee();
//
//		Assert.assertEquals(fee.longValue(), 5 * 1000);
//	}
//
//	@Test
//	public void testChargeratioUL() {
//		int pricestrategytype = PRICESTRATEGYTYPE.CHARGERATIOANDUPPERlIMIT
//				.getValue();
//		Long amount = 1000L * 1000 * 10;
//
//		PricingStrategyDetailDTO dto = getPricingStrategyDetailDTO();
//		CalFeeInnerParam feeParam = new CalFeeInnerParam();
//		feeParam.setPricingstategydetaildto(dto);
//		feeParam.setTransactionAmount(amount);
//
//		Long fee = calFeeFactory.getCalFee(pricestrategytype)
//				.calculateFeeDetail(feeParam).getFee();
//
//		Assert.assertEquals(fee.longValue(), 25 * 1000);
//	}
//
//	@Test
//	public void testChargeratioULLL() {
//		int pricestrategytype = PRICESTRATEGYTYPE.CHARGERATIOANDUPPERlIMITANDLOWERLIMIT
//				.getValue();
//		Long amount = 1000L * 1000 * 10;
//
//		PricingStrategyDetailDTO dto = getPricingStrategyDetailDTO();
//		CalFeeInnerParam feeParam = new CalFeeInnerParam();
//		feeParam.setPricingstategydetaildto(dto);
//		feeParam.setTransactionAmount(amount);
//
//		Long fee = calFeeFactory.getCalFee(pricestrategytype)
//				.calculateFeeDetail(feeParam).getFee();
//
//		Assert.assertEquals(fee.longValue(), 25 * 1000);
//	}
//
//	@Test
//	public void testFixedcharge() {
//		int pricestrategytype = PRICESTRATEGYTYPE.FIXEDCHARGE.getValue();
//		Long amount = 1000L * 1000 * 10;
//
//		PricingStrategyDetailDTO dto = getPricingStrategyDetailDTO();
//		CalFeeInnerParam feeParam = new CalFeeInnerParam();
//		feeParam.setPricingstategydetaildto(dto);
//		feeParam.setTransactionAmount(amount);
//
//		Long fee = calFeeFactory.getCalFee(pricestrategytype)
//				.calculateFeeDetail(feeParam).getFee();
//
//		Assert.assertEquals(fee.longValue(), 5 * 1000);
//	}
//
//	@Test
//	public void testFixedchargeChargeratio() {
//		int pricestrategytype = PRICESTRATEGYTYPE.FIXEDCHARGE_CHARGERATIO
//				.getValue();
//
//		Long amount = 1000L * 1000;
//
//		PricingStrategyDetailDTO dto = getPricingStrategyDetailDTO();
//		CalFeeInnerParam feeParam = new CalFeeInnerParam();
//		feeParam.setPricingstategydetaildto(dto);
//		feeParam.setTransactionAmount(amount);
//
//		Long fee = calFeeFactory.getCalFee(pricestrategytype)
//				.calculateFeeDetail(feeParam).getFee();
//
//		Assert.assertEquals(fee.longValue(), 5000 + 5000);
//	}
//
//	@Test
//	public void testFixedchargeChargeratioLL() {
//		int pricestrategytype = PRICESTRATEGYTYPE.FIXEDCHARGE_CHARGERATIOANDLOWERLIMIT
//				.getValue();
//
//		Long amount = 1000L * 1000;
//
//		PricingStrategyDetailDTO dto = getPricingStrategyDetailDTO();
//		CalFeeInnerParam feeParam = new CalFeeInnerParam();
//		feeParam.setPricingstategydetaildto(dto);
//		feeParam.setTransactionAmount(amount);
//
//		Long fee = calFeeFactory.getCalFee(pricestrategytype)
//				.calculateFeeDetail(feeParam).getFee();
//
//		Assert.assertEquals(fee.longValue(), 5000 + 5000);
//	}
//
//	@Test
//	public void testFixedchargeChargeratioUL() {
//		int pricestrategytype = PRICESTRATEGYTYPE.FIXEDCHARGE_CHARGERATIOANDUPPERlIMIT
//				.getValue();
//
//		Long amount = 1000L * 1000;
//
//		PricingStrategyDetailDTO dto = getPricingStrategyDetailDTO();
//		CalFeeInnerParam feeParam = new CalFeeInnerParam();
//		feeParam.setPricingstategydetaildto(dto);
//		feeParam.setTransactionAmount(amount);
//
//		Long fee = calFeeFactory.getCalFee(pricestrategytype)
//				.calculateFeeDetail(feeParam).getFee();
//
//		Assert.assertEquals(fee.longValue(), 5000 + 5000);
//	}
//
//	@Test
//	public void testFixedchargeChargeratioULLL() {
//		int pricestrategytype = PRICESTRATEGYTYPE.FIXEDCHARGE_CHARGERATIOANDUPPERlIMITANDLOWERLIMIT
//				.getValue();
//		
//		Long amount = 2000L * 1000;
//
//		PricingStrategyDetailDTO dto = getPricingStrategyDetailDTO();
//		CalFeeInnerParam feeParam = new CalFeeInnerParam();
//		feeParam.setPricingstategydetaildto(dto);
//		feeParam.setTransactionAmount(amount);
//
//		Long fee = calFeeFactory.getCalFee(pricestrategytype)
//				.calculateFeeDetail(feeParam).getFee();
//
//		Assert.assertEquals(fee.longValue(), 5000 + 10000);
//	}
//
//}
