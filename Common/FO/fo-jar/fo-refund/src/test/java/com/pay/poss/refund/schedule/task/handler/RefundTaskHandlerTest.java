package com.pay.poss.refund.schedule.task.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.base.env.context.ContextService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.model.BatchRule;
import com.pay.poss.base.model.Task;

@ContextConfiguration(locations = { "classpath*:config/env/test-dataAccessContext.xml", "classpath*:config/spring/platform/*.xml"})
public class RefundTaskHandlerTest extends AbstractTestNGSpringContextTests {
	private BaseDAO daoService = (BaseDAO) ContextService.getBean(BaseDAO.BEAN_ID);
	private Map<String, String> fileInfo = new HashMap<String, String>();

	@BeforeMethod
	public void init() {
		fileInfo = new HashMap<String, String>();
		fileInfo.put("BATCH_FILE_PATH", "c:/batch");
		fileInfo.put("BATCH_FILENAME_SUBFIX", ".xls");
		fileInfo.put("BATCH_NUM", "SYS_0");
		fileInfo.put("BATCH_TASK_TYPE", "200001");

	}

	// @Test
	protected void testCreateFile() throws PossException {

		RefundTaskHandler.getInstance().createFile(daoService, fileInfo);
	}

	// @Test
	protected void testUpdateWOBatchNumWithAuto() throws PossException {
		RefundTaskHandler.getInstance().updateWOBatchNumWithAuto(getRule(), getTask());
	}

//	@Test
	protected void testUpdateWOBatchNumWithManual() throws PossException {
		String batchNum = "MANU-BATCH";
		String orderSeqsSql = "(1,2)";
		RefundTaskHandler.getInstance().updateWOBatchNumWithManual(batchNum, orderSeqsSql);
	}

	// @Test
	protected void testUpdateWOBatchNumWithRebuild() throws PossException {
		String batchNum = "SYS_0";
		String newBatchNum = "REBUILD-BATCH";
		RefundTaskHandler.getInstance().updateWOBatchNumWithRebuild(batchNum, newBatchNum);
	}

	public void testCalcMasterInfo() {
		try {
			RefundTaskHandler.getInstance().calcMasterInfo("SYS_0");
		} catch (PossException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private BatchRule getRule() {
		BatchRule rule = new BatchRule();
		return rule;
	}

	private Task getTask() {
		Task task = new Task("", "200001");
		task.setBusiCode("11");
		task.setBusiParam("11");
		task.setBusiPriority(11);
		task.setCallSeq("1");
		task.setCallTime("20100810051001555");
		task.setStatus(1);
		task.setBatchNum("SYS_0");
		task.setNewBatchNum("NEW_SYS_0");
		return task;
	}
}
