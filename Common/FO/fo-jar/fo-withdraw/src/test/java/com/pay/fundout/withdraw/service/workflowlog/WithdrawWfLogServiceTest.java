package com.pay.fundout.withdraw.service.workflowlog;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.dto.workflowlog.WithdrawWfLogDTO;
import com.pay.fundout.withdraw.service.workflowlog.WithdrawWfLogService;


//@ContextConfiguration(locations={"classpath*:config/env/test-dataAccessContext.xml",
//"classpath*:/context/spring/**/*.xml",
//"classpath*:/context/*.xml" })

@ContextConfiguration(locations={"classpath*:config/env/test-dataAccessContext.xml",
		"classpath*:/context/spring/workflowlog/*.xml",
		"classpath*:/context/spring/base/*.xml" })
public class WithdrawWfLogServiceTest extends AbstractTestNGSpringContextTests {
	
	@Resource(name="fundout_withdraw_withdrawWfLogServicee")
	private WithdrawWfLogService withdrawWfLogService;
	//set注入
	public void setWithdrawWfLogService(WithdrawWfLogService withdrawWfLogService) {
		this.withdrawWfLogService = withdrawWfLogService;
	}
	
	@Test
	public void testSaveOperatorLog() {
		WithdrawWfLogDTO dto = new WithdrawWfLogDTO();
		dto.setCreatedate(new Date());
		dto.setNode("aaaa");
		dto.setOperator("adsf");
		dto.setState("1");
		dto.setWorkflowId("ssssss");
		dto.setRemark("remark");
		Long id = withdrawWfLogService.saveWithdrawWfLog(dto);
		System.out.println(id);
	}
	
	@Test
	public void testQueryWLog(){
		List<WithdrawWfLogDTO> list = withdrawWfLogService.queryWithDrawWfLogByWorkOrderKy(2001011231618005871l);
		if(null != list){
			System.out.println(list.size());
			for(WithdrawWfLogDTO dto : list){
				System.out.println(dto.getSequenceId());
			}
			System.out.println("size " + list.size());
		}
	}
}

