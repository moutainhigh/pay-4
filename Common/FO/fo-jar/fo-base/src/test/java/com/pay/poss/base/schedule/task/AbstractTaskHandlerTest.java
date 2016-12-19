package com.pay.poss.base.schedule.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.base.common.Constants;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.model.BatchRule;
import com.pay.poss.base.model.Task;

@ContextConfiguration(locations = {
		"classpath*:config/env/test-dataAccessContext.xml",
		"classpath*:context/spring/base/*.xml" })
public class AbstractTaskHandlerTest extends AbstractTestNGSpringContextTests {
	
	private class AbstractTaskHandlerTestImpl extends AbstractTaskHandler {

		@Override
		protected void createFile(BaseDAO daoService,
				Map<String, String> fileInfo) throws PossException {
			System.out.println("-----createFile-------");
			// TODO Auto-generated method stub
			throw new PossException("事务回滚测试异常",
					ExceptionCodeEnum.CONTEXT_EXCEPTION);
		}

		@Override
		protected void updateWOBatchNumWithAuto(BatchRule batchRule, Task task)
				throws PossException {
			// TODO Auto-generated method stub

		}

		@Override
		protected void updateWOBatchNumWithManual(String batchNum,
				String orderSeqsSql) throws PossException {
			System.out.println("-----updateWOBatchNumWithManual-------");
			daoService.update("schedule.fo-updateWO4TestTrans", "t_trans_lv2");
		}

		@Override
		protected void updateWOBatchNumWithRebuild(String batchNum,
				String newBatchNum) throws PossException {
			// TODO Auto-generated method stub

		}

	}

	private AbstractTaskHandler abstractTaskHandler;

	@BeforeMethod
	public void init() {
		abstractTaskHandler = new AbstractTaskHandlerTestImpl();
	}

	@Test
	public void testExecute() {
		try{
			abstractTaskHandler.execute(buildTask());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	// @Test
	public void testSaveBatchInfo() {
		BatchRule batchRule = buildBatchRule();
		Task task = buildTask();
		try {
			abstractTaskHandler.saveBatchInfo(batchRule, task);
		} catch (PossException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// @Test
	public void testBuildSqlFromManualTask() {
		Task task = buildTask();
		System.out.println(abstractTaskHandler.buildSqlFromManualTask(task));
	}

	// @Test
	public void testUpdateScheduleState() {
		Task task = buildTask();
		abstractTaskHandler.updateScheduleState(task, "2");
	}

	// @Test
	public void testSaveBatchRelation() {
		Task task = buildTask();
		try {
			abstractTaskHandler.saveBatchRelation(task);
		} catch (PossException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Task buildTask() {
		Task task = new Task("", Constants.TASK_TYPE_REFUND);
		task.setType(Constants.BATCH_BUILD_MANUAL);
		task.setScheduleKY(1317L);
		task.setBatchNum("T-bn-01");
		task.setBusiCode("1");
		task.setCallSeq("T-seq-01");
		task.setCallTime("20100915172930555");
		task.setNewBatchNum("T-nbn-01");
		List<String> seqs = new ArrayList<String>();
		seqs.add("2001010111903000921");
		seqs.add("2001010111835000909");
		task.addManualWorkorder(seqs);
		return task;
	}

	private BatchRule buildBatchRule() {
		BatchRule result = new BatchRule();
		result.setRuleKy(9000L);
		result.setRuleName("测试规则1");
		return result;
	}
}
