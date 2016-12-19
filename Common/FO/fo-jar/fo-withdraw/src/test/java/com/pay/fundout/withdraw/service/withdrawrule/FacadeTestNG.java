package com.pay.fundout.withdraw.service.withdrawrule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.service.app.output.WithDrawOrderFacadeService;
import com.pay.fundout.withdraw.service.app.output.WithDrawOrderQueryParam;
import com.pay.fundout.withdraw.service.app.output.WithDrawOrderQueryResult;
import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;

@ContextConfiguration(locations = { "classpath*:config/env/test-dataAccessContext.xml",
		"classpath*:config/spring/platform/*.xml",
		"classpath*:context/spring/withdrawrule/*.xml",
		"classpath*:context/spring/ordercallback/*.xml",
		"classpath*:context/spring/withdraw/*.xml",
		"classpath*:context/spring/withdrawrefund/*.xml",
		"classpath*:context/*.xml",
	//	"classpath*:context/**/*.xml",
		"classpath*:config/*.xml"
		})
//@ContextConfiguration(locations={"classpath*:config/env/test-dataAccessContext.xml", "classpath*:config/spring/platform/*.xml","classpath:/context/*.xml" })
public class FacadeTestNG extends AbstractTestNGSpringContextTests {
	@Autowired
	private WithDrawOrderFacadeService withdrawOrderfacaService;
	@Autowired
	private AccountingService withDrawOrderFailService;
	@Autowired
	private WithdrawOrderService withdrawOrderService;
	//@Test
	public void facadeTest(){
		WithDrawOrderQueryParam queryParam =  new WithDrawOrderQueryParam();
		queryParam.setOrderSeqId("15");
		queryParam.setStartTime("2010-09-10");
		queryParam.setEndTime("2010-09-24");
		WithDrawOrderQueryResult result = withdrawOrderfacaService.queryWithDrawOrderResult(queryParam);
		assert(result.getOrderSeqId().equals("15"));
		System.out.println(result.getSequenceId()+":"+result.getAmount()+":"+result.getStatus());
	}
	@Test
	public void accountingTest(){
		HandlerParam param  = new HandlerParam();
		WithdrawOrderAppDTO dto= withdrawOrderService.getWithdrawOrder(2129L);
		param.setBaseOrderDto(dto);
		param.setOrderStatus(101);
		param.setWithdrawBusinessType("1");
		//withDrawOrderFailService.accounting(param);
	}

	
	
	
}
