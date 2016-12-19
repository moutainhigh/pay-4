package com.pay.fundout.withdraw.service.withdrawrule;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.dto.bank.WithdrawBankConfigDTO;
import com.pay.fundout.withdraw.dto.busitype.WithdrawBusinessDTO;
import com.pay.fundout.withdraw.dto.type.WithdrawTypeDTO;
import com.pay.fundout.withdraw.dto.withdrawrefundorder.WithdrawRefundOrderDTO;
import com.pay.fundout.withdraw.model.bank.WithdrawBankConfig;
import com.pay.fundout.withdraw.model.busitype.WithdrawBusiness;
import com.pay.fundout.withdraw.model.rule.WithdrawRule;
import com.pay.fundout.withdraw.model.type.WithdrawType;
import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.fundout.withdraw.service.refundorder.WithdrawRefundOrderService;
import com.pay.fundout.withdraw.service.withdrawrule.WithdrawRuleService;

@ContextConfiguration(locations = { "classpath*:config/env/test-dataAccessContext.xml","classpath*:config/spring/platform/*.xml", "classpath*:context/spring/withdrawrule/*.xml", "classpath*:context/spring/withdrawrefund/*.xml"})
//@ContextConfiguration(locations={"classpath*:config/env/test-dataAccessContext.xml", "classpath*:config/spring/platform/*.xml","classpath:/context/*.xml" })
public class RuleTestNG extends AbstractTestNGSpringContextTests {
	@Autowired
	private WithdrawRuleService ruleService;
	@Autowired
	private WithdrawOrderService withdrawOrderService;
	@Autowired
	private WithdrawRefundOrderService withdrawRefundOrderService;
	@Test
	public void wrsTest(){
		WithdrawType wt = new WithdrawType();
		wt.setStatus(1);
		wt.setTypeDesc("备注");
		wt.setTypeName("名字");
		
		//ruleService.createWithdrawType(wt);
		Map<String,String> map = new HashMap<String,String>();
		map.put("sequenceId", "2");
		List<WithdrawTypeDTO> list = ruleService.searchTypes(map);
		//assert(list.size()>0);
		wt.setSequenceId(2);
		wt.setStatus(0);
		ruleService.updateWithdrawType(wt);
		WithdrawBusiness wb = new WithdrawBusiness();
		wb.setBusinessName("普通体现");
		wb.setStatus(1);
		//ruleService.createWithdrawBusiness(wb);
		wb.setSequenceId(3);
		wb.setStatus(0);
		ruleService.updateWithdrawBusiness(wb);
		map.put("sequenceId", "3");
		map.put("status", "0");
		List<WithdrawBusinessDTO> list2 = ruleService.searchBusinesses(map);
		//assert(list2.size()>0);
		WithdrawBankConfig wbc = new WithdrawBankConfig();
		wbc.setBankCode("ICBC");
		wbc.setBankName("工商银行");
		wbc.setStatus(1);
		wbc.setWithdrawBusiId(3);
		wbc.setWithdrawTypeId(3);
		//ruleService.createWithdrawBankConfig(wbc);
		wbc.setSequenceId(2);
		wbc.setStatus(0);
		ruleService.updateWithdrawBankConfig(wbc);
		map.put("status", "1");
		List<WithdrawBankConfigDTO> list3 = ruleService.searchBankConfigs(map);
		//assert(list3.size()>0);
		
		WithdrawRule wr = new WithdrawRule();
		wr.setBankAcctType(3);
		wr.setPriority(1);
		wr.setSinMaxValue(10000L);
		map = new HashMap<String,String>();
		map.put("mtype", "1");
		map.put("bankAccoutType", "1");
		map.put("destBankId", "1");
		map.put("amount", "50");
		WithdrawRule rule = ruleService.getRule(map);
		assert(rule.getSequenceId()>0);
		map = new HashMap<String,String>();
		map.put("WITHDRAW_TYPE", "1");
		map.put("BANK_ACCT_TYPE", "1");
		map.put("TO_BANK_CODE", "1");
		map.put("SIN_MAX_VALUE", "50");
		
		System.out.println(ruleService.getWithdrawType(map)+"--------------------------------");
	}
	
	@Test
	public void updateTest(){
		WithdrawOrderAppDTO dto= withdrawOrderService.getWithdrawOrder(2129L);
		dto.setStatus(121L);
		withdrawOrderService.updateWithdrawOrder(dto);
//		WithdrawRefundOrderDTO rdto =new  WithdrawRefundOrderDTO();
//		rdto.setComments("aa");
//		rdto.setCreationDate(new Date());
//		rdto.setPayeeAcctCode("101");
//		rdto.setPayerAcctCode("391");
//		rdto.setSequenceId(1L);
//		rdto.setStatus(2);
//		rdto.setWithdrawOrderId(100L);
//		
//		withdrawRefundOrderService.updateWithdrawRefundOrder(rdto);
	}
}
