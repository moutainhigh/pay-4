package com.pay.fundout.withdraw.service.fileservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.service.fileservice.HandBatchFileService;
import com.pay.poss.base.exception.PossException;

@ContextConfiguration(locations={
		"classpath*:context/spring/ordercallback/*.xml",
		"classpath*:config/spring/remoting-bean.xml",
		"classpath*:config/spring/systemmanager/systemmanager-bean.xml",
		"classpath*:context/spring/withdrawrefund/context-fundout-withdraw-refund-bean.xml",
		"classpath*:config/env/test-dataAccessContext.xml",
		"classpath*:context/spring/base/*.xml",
		"classpath*:context/framework-context-db.xml",
		"classpath*:context/framework-context-dao.xml",
		"classpath*:context/framework-context-service.xml",
		"classpath*:context/spring/inf/*.xml",
		"classpath*:context/spring/flowprocess/*.xml",
		"classpath*:context/spring/fileservice/context-fundout-withdraw-fileservice-dao.xml",
		"classpath*:context/spring/fileservice/context-fundout-withdraw-fileservice-service.xml",
		"classpath*:context/spring/withdraw/context-fundout-withdraw-accounting-service.xml",
		"classpath*:context/spring/withdraw/context-fundout-withdraw-pefacade-service.xml",
		"classpath*:context/spring/workflow/*.xml",
		"classpath*:context/spring/context-fundout-fee-cal-service.xml",
		"classpath*:context/spring/appout/context-fundout-appout-service.xml",
		"classpath*:context/*.xml" })
public class HandBatchFileServiceTest extends AbstractTestNGSpringContextTests {
	@Autowired
	@Qualifier("fundout-withdraw-handgeneratebatch")
	HandBatchFileService handBatchFileService ;

	public void setHandBatchFileService(HandBatchFileService handBatchFileService) {
		this.handBatchFileService = handBatchFileService;
	}

	@Test
	public void testHandBatchFileService() throws PossException{
		/*[
		 * WORKORDERID=12345678
		 * ORDERID=12345678
		 * ISSUCC=1(1:成功,0：失败)
		 * ]
		*/
		
		
		Map<String,String> map = new HashMap<String,String>();
		//map.put("WORKORDERID", "2001010121615001081");
		map.put("orderId", "2001010131938001349");
		map.put("issucc", "1");
		this.handBatchFileService.importConfirmRdTx(map);
	}
}
