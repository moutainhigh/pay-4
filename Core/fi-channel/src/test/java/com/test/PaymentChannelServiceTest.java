/**
 * 
 */
package com.test;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.channel.model.PaymentChannelCategory;
import com.pay.channel.service.PaymentChannelService;

/**
 * @author chaoyue
 *
 */
@ContextConfiguration(locations = { "classpath*:context/**/*.xml" })
public class PaymentChannelServiceTest extends AbstractTestNGSpringContextTests {

	@Resource(name = "channel-paymentChannelService")
	private PaymentChannelService paymentChannelService;

	//@Test
	public void testCheckCategoryExists() {

		String categoryCode = "BANK_B2C";
		boolean resultFlag = paymentChannelService
				.checkCategoryExists(categoryCode);
		Assert.assertTrue(resultFlag);
	}

	@Test
	public void testqueryPaymentChannelCategory() {
		List<PaymentChannelCategory> channelCategorys = paymentChannelService
				.queryPaymentChannelCategory();
		for (PaymentChannelCategory channelCategory : channelCategorys) {
			System.out.println(channelCategory.getCode());
		}
	}
}
