/**
 *  File: WithdrawOrderAuditServiceTest.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-11      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.flowprocess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.common.util.WithDrawConstants;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawAuditDTO;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawAuditQueryDTO;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawWFParameter;
import com.pay.fundout.withdraw.dto.flowprocess.WorkFlowHistory;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;
import com.pay.util.DateUtil;

@ContextConfiguration(locations={
		"classpath*:context/spring/workflowlog/*.xml",
		"classpath*:context/spring/masspaytobank/context-fundout-masspaytobank-dao.xml",
		"classpath*:context/spring/masspaytobank/context-fundout-masspaytobank-rule.xml",
		"classpath*:context/spring/masspaytobank/context-fundout-masspaytobank-service.xml",
		"classpath*:context/spring/pay2bank/context-fundout-pay2bank-validate-service.xml",
		"classpath*:context/spring/masspaytobank/context-fundout-masspaytobank-dao.xml",
		"classpath*:context/spring/masspaytobank/context-fundout-masspaytobank-service.xml",
		"classpath*:context/spring/facade/context-fundout-mafacade-service.xml",
		"classpath*:context/spring/context-fundout-withdraworder-service.xml",
		"classpath*:context/spring/flowprocess/*.xml",
		"classpath*:context/spring/ordercallback/*.xml",
		"classpath*:context/spring/ordercallback/context-fundout-ordercallback-service.xml",
		"classpath*:config/spring/remoting-bean.xml",
		"classpath*:config/spring/systemmanager/systemmanager-bean.xml",
		"classpath*:context/spring/withdrawrefund/context-fundout-withdraw-refund-bean.xml",
		"classpath*:config/env/test-dataAccessContext.xml",
		"classpath*:context/spring/base/*.xml",
		"classpath*:context/framework-context-db.xml",
		"classpath*:context/framework-context-dao.xml",
		"classpath*:context/framework-context-service.xml",
		"classpath*:context/spring/inf/*.xml",
		"classpath*:context/spring/withdraw/context-fundout-withdraw-accounting-service.xml",
		"classpath*:context/spring/withdraw/context-fundout-withdraw-pefacade-service.xml",
		"classpath*:context/spring/workflow/*.xml",
		"classpath*:context/spring/context-fundout-fee-cal-service.xml",
		"classpath*:context/spring/facade/context-fundout-appfacade-service.xml",
		"classpath*:context/spring/configbank/fundout-channel-configbank-service.xml",
		"classpath*:context/spring/facade/context-rm-facade-service.xml",
		"classpath*:context/spring/rmlimit/context-rm-limit-dao.xml",
		"classpath*:context/*.xml" })
public class WithdrawOrderAuditServiceTest extends AbstractTestNGSpringContextTests {
	@Autowired
	@Qualifier("wdOrdAuditService")
	WithdrawOrderAuditService wdOrdAuditService ;

	/**
	 * @param wdOrdAuditService the wdOrdAuditService to set
	 */
	public void setWdOrdAuditService(WithdrawOrderAuditService wdOrdAuditService) {
		this.wdOrdAuditService = wdOrdAuditService;
	}

	public void testWDOrdQuery(){
		Page<WithdrawAuditDTO> page = new Page<WithdrawAuditDTO>();
		WithdrawAuditQueryDTO auditQueryDTO = new WithdrawAuditQueryDTO();
		//TODO 增加测试数据
		//2010- 08-01 14:00:00
		String startDate = "2010-9-09 16:07:54";
		auditQueryDTO.setStartDate(DateUtil.parse("yyyy-MM-dd hh:mm:ss", startDate));
		String endDate = "2010-9-13 09:50:00";
		auditQueryDTO.setEndDate(DateUtil.parse("yyyy-MM-dd hh:mm:ss", endDate));
		String memberCode = "12423689235";
		auditQueryDTO.setMemberCode(memberCode);
		String memberType = "0";
		auditQueryDTO.setMemberType(memberType);
		String memberAccType = "1";
		auditQueryDTO.setMemberAccType(memberAccType);
		String status = null;
		auditQueryDTO.setStatus(status);
		/**
		 * 交易流水号
		 */
		String sequenceId = null;
		auditQueryDTO.setSequenceId(sequenceId);
		String bankAcct = null;
		auditQueryDTO.setBankAcct(bankAcct);
		String bankBranch = "上海市工商银行";
		auditQueryDTO.setBankBranch(bankBranch);
		String prioritys ="5";
		auditQueryDTO.setPrioritys(prioritys);
		String busiType = "0";
		auditQueryDTO.setBusiType(busiType);
		String batchStatus = "0";
		auditQueryDTO.setBatchStatus(batchStatus);
//		wdOrdAuditService.search(page, auditQueryDTO);
		System.out.println("##");
	}
	
	
	public void testliquidateAuditRdTx() throws PossException{
		Map<String, Object> map = new HashMap<String,Object>();
		//previousUser=, processKey=withdraw, userId=qs01, nodeName=liquidate, taskMessage=liquidate##1##结算, assigner=
		map.put("previousUser", "");
		map.put("processKey", "withdraw");
		map.put("userId", "qs01");
		map.put("nodeName", "liquidate");
		map.put("taskMessage", "liquidate##1##结算");
		map.put("assigner", "");
		int auditStatus = 1;	//失败状态
		
		String[] wkKeys={"2001010121615001081"};
		this.wdOrdAuditService.liquidateAudit(map, wkKeys, auditStatus,"");
	}

	
	public void testWithdrawResManualProcReAuditRdTx() throws PossException{
		int auditStatus = 1;	//确定状态
		Long workOrderId=2001011041816002943l;
		//this.wdOrdAuditService.withdrawResManualProcReAuditRdTx(workOrderId, "记账测试", auditStatus);
	}

	public void testAudit() throws PossException{
		//WithdrawWFParameter wfPara, String[] wkKeys, int auditStatus
		WithdrawWFParameter wfPara = new WithdrawWFParameter();
		wfPara.setAssigner("");
		wfPara.setNodeName(WithDrawConstants.AUDIT_NODE);
		wfPara.setProcessKey(WithDrawConstants.PROCESS_NAME);
		wfPara.setTaskMessage("audit##0##审核");
		wfPara.setUserId("sh01");
		
		String[] wkKeys={"2001011240510005989"};
		int auditStatus = 1;
		
		//this.wdOrdAuditService.auditRdTx(wfPara, wkKeys, auditStatus);
	}

	
	public void testfirstAudit() throws Exception{
		for(int i=0;i<5;i++){
			System.out.println("THE TIME IS:"+i+"------");
			Thread th = new Thread(new ThreadTest());
			th.start();
			
			System.out.println(i + "TIME IS END"+"------");
		}
		Thread.sleep(500000000l);
	}
	
	private class ThreadTest implements Runnable{
		@Override
//		public void run() {
//			//WithdrawWFParameter wfPara, String[] wkKeys, int auditStatus
//			WithdrawWFParameter wfPara = new WithdrawWFParameter();
//			wfPara.setAssigner("");
//			wfPara.setNodeName(WithDrawConstants.AUDIT_NODE);
//			wfPara.setProcessKey(WithDrawConstants.PROCESS_NAME);
//			wfPara.setTaskMessage("audit##0##审核");
//			wfPara.setUserId("sh01");
//			
//			String[] wkKeys={"2001011301640006258"};
//			int auditStatus = 1;
//			String auditRemark = "asfasfasfasfasfasf";
//			
//			try {
//				String str = wdOrdAuditService.firstAudit(wfPara, wkKeys, auditStatus, auditRemark);
//				System.out.println(str);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//		}
		
//		public void run() {
//			//withdrawResManualProcAuditRdTx(Long workOrderId, String auditFailReason, int auditStatus)
//			Long workOrderId = 2001011251143006002l;
//			String auditFailReason = "并发测试";
//			int auditStatus = 0;
//			try {
//				String str = wdOrdAuditService.withdrawResManualProcAuditRdTx(workOrderId, auditFailReason, auditStatus);
//				System.out.println(str);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		
		public void run() {
			//withdrawResManualProcAuditRdTx(Long workOrderId, String auditFailReason, int auditStatus)
			Long workOrderId = 2001011231618005871l;
			String auditFailReason = "并发测试";
			int auditStatus = 1;
			try {
				//String str = wdOrdAuditService.withdrawResManualProcReAuditRdTx(workOrderId, auditFailReason, auditStatus);
				//System.out.println(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void queryWFLogService(){
		String orderKy = "2001012221839023717";
		List<WorkFlowHistory> hisList = wdOrdAuditService.queryWorkFlowHisInfoByorderkKy(orderKy);
		System.out.println(hisList.size());
	}
	
	@Test
	public void test(){
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}
	
}
