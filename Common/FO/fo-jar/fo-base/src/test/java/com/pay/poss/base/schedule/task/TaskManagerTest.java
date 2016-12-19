package com.pay.poss.base.schedule.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.pay.poss.base.common.Constants;
import com.pay.poss.base.env.context.ContextService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.model.BatchInfo;
import com.pay.poss.base.model.BatchRule;
import com.pay.poss.base.model.Task;
import com.pay.poss.base.schedule.task.TaskManager;

@ContextConfiguration(locations = { "classpath*:config/env/test-dataAccessContext.xml", "classpath*:config/spring/platform/*.xml" })
public class TaskManagerTest extends AbstractTestNGSpringContextTests {
	@Autowired
	private TaskManager taskManager;

	@BeforeMethod
	public void init() {
	}

	@Test
	public void testStoreTaskInfo() {
		try {
			taskManager.storeTaskInfo(buildTask());
		} catch (PossException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Task buildTask() {
		Task result = new Task("", Constants.TASK_TYPE_REFUND);
		result.setBusiCode("T-bc-01");
		result.setBusiParam("");
		result.setCallSeq("T-seq-01");
		result.setBusiPriority(5);
		result.setCallTime("20100915112233555");
		result.setStatus(1);
		return result;
	}

}
