package com.pay.poss.refund.schedule.task.file;

import java.util.HashMap;
import java.util.Map;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.base.common.poi.OperatorPoiInterface;

@ContextConfiguration(locations = {
		"classpath*:config/env/test-dataAccessContext.xml",
		"classpath*:config/spring/platform/*.xml",
		"classpath*:config/spring/refund/refund-bean.xml" })
public class MasterFileOperateWorkerTest extends
		AbstractTestNGSpringContextTests {
	protected BaseDAO daoService;
	private OperatorPoiInterface masterFileHandler;
	private MasterFileOperateWorker worker;
	private Map<String, String> fileInfo = new HashMap<String, String>();

	/*
	 * @BeforeClass public void init() { fileInfo = new HashMap<String,
	 * String>(); fileInfo.put("BATCH_FILE_PATH",
	 * CommonConfiguration.getStrProperties("refundBatchFilePath"));
	 * fileInfo.put("BATCH_FILENAME_SUBFIX", ".xls"); fileInfo.put("BATCH_NUM",
	 * "SYS_0"); fileInfo.put("BATCH_TASK_TYPE", "200001");
	 * 
	 * ContextService.getInstance().setContext(applicationContext); daoService =
	 * (IBaseDao) ContextService.getInstance().getBean(IBaseDao.BEAN_ID);
	 * masterFileHandler = (OperatorPoiInterface)
	 * ContextService.getInstance().getBean("creatorIntraFileSummaryExcel");
	 * worker = new
	 * MasterFileOperateWorker(daoService,fileInfo,masterFileHandler); }
	 * 
	 * // @Test public void testMasterFileOperate(){
	 * 
	 * worker.start();
	 * 
	 * try { Thread.sleep(500000); } catch (InterruptedException e) {
	 * e.printStackTrace(); } }
	 * 
	 * 
	 * // @Test public void testFindBankByBatchNum(){ List<String> result =
	 * worker.findBankByBatchNum("MANU-BATCH"); for (String bank : result) {
	 * System.out.println(bank); } }
	 * 
	 * @Test public void testCalcMasterInfo(){ List<RefundBatchInfoDTO>
	 * refundBatchInfoDTOList = worker.calcMasterInfo();
	 * System.out.println(refundBatchInfoDTOList.size()); for
	 * (RefundBatchInfoDTO refundBatchInfoDTO : refundBatchInfoDTOList) {
	 * System.out.println(refundBatchInfoDTO.getTotalAmount());
	 * System.out.println(refundBatchInfoDTO.getTotalCount());
	 * System.out.println(refundBatchInfoDTO.getBankCode());
	 * System.out.println(refundBatchInfoDTO.getBatchNum());
	 * System.out.println("--------------------------------");
	 * 
	 * } }
	 * 
	 * // @Test public void testFileOperate(){ try { worker.fileOperate(); }
	 * catch (Exception e) { e.printStackTrace(); } }
	 */

}
