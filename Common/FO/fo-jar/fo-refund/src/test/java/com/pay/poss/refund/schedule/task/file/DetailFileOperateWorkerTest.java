package com.pay.poss.refund.schedule.task.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.base.common.poi.OperatorPoiInterface;
import com.pay.poss.base.common.properties.CommonConfiguration;
import com.pay.poss.base.env.context.ContextService;
import com.pay.poss.refund.model.RefundBatchDTO;
import com.pay.poss.refund.model.RefundBatchInfoDTO;

@ContextConfiguration(locations = { "classpath*:config/env/test-dataAccessContext.xml", "classpath*:config/spring/platform/*.xml", "classpath*:config/spring/refund/refund-bean.xml" })
public class DetailFileOperateWorkerTest extends AbstractTestNGSpringContextTests {
	protected BaseDAO daoService;
	private OperatorPoiInterface creatorIntraFileExcel;
	private DetailFileOperateWorker worker;
	private Map<String, String> fileInfo = new HashMap<String, String>();
	private RefundBatchInfoDTO masterInfo;

	@BeforeClass
	public void init() {
		fileInfo = new HashMap<String, String>();
		fileInfo.put("BATCH_FILE_PATH", CommonConfiguration.getStrProperties("refundBatchFilePath"));
		fileInfo.put("BATCH_FILENAME_SUBFIX", ".xls");
		fileInfo.put("BATCH_NUM", "SYS_0");
		fileInfo.put("BATCH_TASK_TYPE", "200001");

		daoService = (BaseDAO) ContextService.getBean(BaseDAO.BEAN_ID);
		creatorIntraFileExcel = (OperatorPoiInterface) ContextService.getBean("creatorIntraFileExcel");

	}

	/*
	 * // @Test public void testSaveFileInfo(){
	 * worker.saveFileInfo(fileInfo,"/2010/9/200001128800000358SYS_0.xls"); }
	 * 
	 * // @Test public void testFindBankByBatchNum(){ List<String> list =
	 * worker.findBankByBatchNum(fileInfo.get("BATCH_NUM")); for (String str :
	 * list) { System.out.println(str); } }
	 * 
	 * // @Test public void testCalcBankRefundOrderDInfo(){
	 * List<List<RefundOrderD>> refundOrderDList =
	 * worker.calcBankRefundOrderDInfo(); for (List<RefundOrderD> list :
	 * refundOrderDList) { System.out.println(list.size()); } }
	 * 
	 * // @Test public void testCalcRefundOrderDInfo(){ List<RefundOrderD>
	 * refundOrderD = worker.calcRefundOrderDInfo(fileInfo.get("BATCH_NUM"));
	 * System.out.println(refundOrderD.size()); }
	 * 
	 * // @Test public void testDetailFile(){ worker.start(); try {
	 * Thread.sleep(500000); } catch (InterruptedException e) {
	 * e.printStackTrace(); } }
	 */

	@Test
	public void testcreatorFileExcel() {
		List<RefundBatchDTO> refundBatchList = new ArrayList<RefundBatchDTO>();
		worker = new DetailFileOperateWorker(daoService, fileInfo, refundBatchList, masterInfo, creatorIntraFileExcel);

		try {
			worker.creatorFileExcel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
