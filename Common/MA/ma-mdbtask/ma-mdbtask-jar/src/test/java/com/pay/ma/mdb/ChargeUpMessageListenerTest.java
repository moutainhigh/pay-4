/**
 * 
 */
package com.pay.ma.mdb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.jms.sender.JmsSender;
import com.pay.pe.helper.OrgType;
import com.pay.pe.service.CalFeeDetail;
import com.pay.pe.service.CalFeeReponse;
import com.pay.pe.service.CalFeeRequest;
import com.pay.pe.service.PEService;

/**
 * @author Administrator
 * 
 */
@ContextConfiguration(locations = { "classpath*:context/*.xml" })
@TransactionConfiguration(transactionManager = "mdbTaskTxManager", defaultRollback = true)
@Transactional
public class ChargeUpMessageListenerTest extends AbstractTransactionalTestNGSpringContextTests {

	@Resource(name = "peService")
	private PEService peService;

	@Resource(name = "jmsSender")
	private JmsSender jmsSender;

	@Resource(name = "dataSourceMdbTask")
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Test
	public void testPEService() {
		Assert.assertNotNull(this.peService);
	}

	@Test
	public void testJmsSender() {
		Assert.assertNotNull(this.jmsSender);
	}

	@Test
	public void testSendChargeUpMessage() {
//		JmsTestObject jmsTestObject = new JmsTestObject();
		// jmsTestObject.setCalFeeReponse(this.initCalFeeReponseData());
		this.jmsSender.send("acc.chargeUpMessage", this.initCalFeeReponseData());
		
//		try {
//			boolean result = this.peService.accountingCalFeeReponse(this.initCalFeeReponseData());
//			Assert.assertTrue(result);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	private CalFeeReponse initCalFeeReponseData() {
		CalFeeReponse calFeeReponse = new CalFeeReponse();

		calFeeReponse.setHasCaculatedPrice(true);
		calFeeReponse.setPayeeFee(0L);
		calFeeReponse.setPayerFee(3L);
		CalFeeRequest calFeeReqDto = new CalFeeRequest();
		// //begin 1个人应用向FO发起支付指令
		calFeeReqDto.setOrderId("1001245640000655");
		calFeeReqDto.setOrderAmount(100L);
		calFeeReqDto.setSubmitAcctCode("1000000010910");
		calFeeReqDto.setAmount(100L);
		calFeeReqDto.setPaymentServicePkgCode(501); // 枚举类由魏峰统一提供
		calFeeReqDto.setDealCode(501); // 枚举类由魏峰统一提供
		calFeeReqDto.setOrderCode(500); // 枚举类由魏峰统一提供
		calFeeReqDto.setPayMethod(1); // 枚举类由魏峰统一提供
		calFeeReqDto.setTerminalType(1);
		calFeeReqDto.setRequestDate(new Date());
		calFeeReqDto.setPayer("10000000126");
		calFeeReqDto.setPayerMemberAcctCode("1000000012610"); // 科目无需设置账户账号全称
		calFeeReqDto.setPayerAcctType(10); // 科目无需设置账户类型
		calFeeReqDto.setPayerOrgType(OrgType.MEMBER.getValue());

		calFeeReponse.setCalFeeRequest(calFeeReqDto);
		calFeeReqDto.setPayerFullMemberAcctCode("20010200100011000000012610"); // 科目无需设置账户全称
		calFeeReqDto.setPayerServiceLevel(-1);

		calFeeReqDto.setPayeeOrgCode("0006"); // 会员账户无需设置orgCode
		calFeeReqDto.setPayeeOrgType(OrgType.BANK.getValue()); // 会员账户无需orgType
		calFeeReqDto.setPayeeServiceLevel(-1); // 无需定义

		List<CalFeeDetail> calFeeDetails = new ArrayList<CalFeeDetail>();
		CalFeeDetail calFeeDetail = new CalFeeDetail();
		calFeeDetail.setAcctcode("20010200100011000000012610");
		calFeeDetail.setCrdr(1);
		calFeeDetail.setValue(3L);
		calFeeDetail.setEntrycode(1);
		calFeeDetails.add(calFeeDetail);

		calFeeDetail = new CalFeeDetail();
		calFeeDetail.setAcctcode("2181012010006");
		calFeeDetail.setCrdr(2);
		calFeeDetail.setEntrycode(2);
		calFeeDetail.setValue(3L);
		calFeeDetails.add(calFeeDetail);

		calFeeReponse.setCalFeeDetails(calFeeDetails);
		return calFeeReponse;

	}

}
