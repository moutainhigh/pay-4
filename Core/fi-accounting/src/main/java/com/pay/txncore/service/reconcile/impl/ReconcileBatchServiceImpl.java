package com.pay.txncore.service.reconcile.impl;
/*
 * author nico.shao 
 * 两个功能
 * （1）reconcileRecordService 一些功能的封装
 * （2）有些批量处理的逻辑，是task和handle 公用的
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.jms.notification.request.TranDailyReportNotifyRequest;
import com.pay.txncore.commons.ReconcileBatchStatusEnum;
import com.pay.txncore.dto.ChannelOrderDTO;
import com.pay.txncore.dto.ReconcileImportRecordBatchDto;
import com.pay.txncore.dto.ReconcileImportRecordDetailDto;
import com.pay.txncore.dto.ReconciliationDto;
import com.pay.txncore.model.RefundOrder;
import com.pay.txncore.service.ReconcileRecordService;
import com.pay.txncore.service.reconcile.ReconcileBatchService;
import com.pay.util.JSonUtil;

import net.sf.json.util.NewBeanInstanceStrategy;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReconcileBatchServiceImpl implements ReconcileBatchService{
	
	private ReconcileRecordService reconcileRecordService;
	private final Log logger = LogFactory.getLog(getClass());
	
	public void setReconcileRecordService(
			ReconcileRecordService reconcileRecordService) {
		this.reconcileRecordService = reconcileRecordService;
	}
	
	@Override
	public String insertReconcileImportRecordBatch(
			ReconcileImportRecordBatchDto importRecordBatch) {
		return reconcileRecordService.insertReconcileImportRecordBatch(importRecordBatch);
	}
	@Override
	public  void insertReconcileImportRecordDetailDto(
			List<ReconcileImportRecordDetailDto> detailDtos) {
		 reconcileRecordService.insertReconcileImportRecordDetailDto(detailDtos);
	}
	@Override
	public void updateReconcileRecordBatch(
			ReconcileImportRecordBatchDto importRecordBatch) {
		reconcileRecordService.updateReconcileRecordBatch(importRecordBatch);
	}
	
	@Override
	public void updateReconcileRecordDetail(
			List<ReconcileImportRecordDetailDto> detailDtos) {
		reconcileRecordService.updateReconcileRecordDetail(detailDtos);
	}
	
	//2016-08-12 更新详细信息的渠道订单号
	@Override 
	public void updateReconcileRecordDetailChannelOrderNo(
				ReconcileImportRecordDetailDto reconcileImportRecordDetailDto){
		reconcileRecordService.updateReconcileRecordDetailChannelOrderNo(reconcileImportRecordDetailDto);
	}
	
	//更新状态： 和真正的处理相关
	@Override 
	public void updateBatchStatus(String batchNo,int status){
		ReconcileImportRecordBatchDto importRecordBatch= new ReconcileImportRecordBatchDto();
		importRecordBatch.setBacthNo(batchNo);
		importRecordBatch.setStatus(status);
		
		if(status == ReconcileBatchStatusEnum.PROCESS_START.getCode()){
			importRecordBatch.setBatchStartTime(new Date());
		}
		
		if(status== ReconcileBatchStatusEnum.PROCESS_END.getCode()){
			importRecordBatch.setBatchEndTime(new Date());	
		}
		
		this.reconcileRecordService.updateReconcileRecordBatchProcess(importRecordBatch);
	}
	
	//更新状态，和预处理相关的状态
	@Override 
	public void updateBatchPreStatus(String batchNo,int status){
		ReconcileImportRecordBatchDto importRecordBatch= new ReconcileImportRecordBatchDto();
		importRecordBatch.setBacthNo(batchNo);
		importRecordBatch.setStatus(status);
		
		if(status == ReconcileBatchStatusEnum.PREPROCESS_START.getCode()){
			importRecordBatch.setPreProcessStartTime(new Date());
		}
		
		if(status== ReconcileBatchStatusEnum.PREPROCESS_END.getCode()){
			importRecordBatch.setPreProcessEndTime(new Date());	
		}
		
		this.reconcileRecordService.updateReconcileRecordBatchProcess(importRecordBatch);
	}
	
	//2016-08-12 根据文件名查找
		@Override
		public List<ReconcileImportRecordBatchDto> queryReconcileBatchByFileName(String fileName){
			Map<String,String> map = new HashMap<String, String>();
			map.put("fileName", fileName);
			//map.put("startTime",...)
			//map.put("endTime",...)
			return this.reconcileRecordService.queryReconcileBatchByFileName(map);
		}
		
		/*
		 *测试代码 
		 */
		@Override
		public void test(){
			
			//(1)测试  QueryReconcileBatchFileName 
			List<ReconcileImportRecordBatchDto> batchList = queryReconcileBatchByFileName("adfasdf.csv");
			if(batchList != null) {
				logger.info("filename = adfasdf.csv" + batchList.size());
			}
			else {
				logger.info("filename = adfasdf.csv is null" );
			}
			
			List<ReconcileImportRecordBatchDto> batchList1 = queryReconcileBatchByFileName("06.29农行对账单.160629");
			if(batchList != null) {
				logger.info("filename = 06.29农行对账单.160629  size=" + batchList1.size());
				for(ReconcileImportRecordBatchDto rrdto:batchList1){
					logger.info("rrdto=" + rrdto.toString());
				}
			}
			else {
				logger.info("filename = 06.29农行对账单.160629 is null" );
			}
			
			//(2)
			//几个更新 
			updateBatchPreStatus("M_2016031903135", ReconcileBatchStatusEnum.PREPROCESS_START.getCode());
			updateBatchPreStatus("M_2016072303454", ReconcileBatchStatusEnum.PREPROCESS_END.getCode());
			updateBatchStatus("M_2016072303455", ReconcileBatchStatusEnum.PROCESS_START.getCode());
			updateBatchStatus("M_2016072303455", ReconcileBatchStatusEnum.PROCESS_END.getCode());
			
			
			ReconcileImportRecordDetailDto rrdd = new ReconcileImportRecordDetailDto();
			rrdd.setChannelOrderNo(Long.valueOf("1111606010556157800"));
			rrdd.setBatchNoDetail("M_2016080102492");
			rrdd.setAcquirerRef("7");
			
			this.updateReconcileRecordDetailChannelOrderNo(rrdd);
			
			//(3)
			//更新数据 			
			ReconcileImportRecordBatchDto rirbt = new ReconcileImportRecordBatchDto();
			rirbt.setBacthNo("M_2016080102492");
			rirbt.setStatus(10);
			rirbt.setBatchEndTime(new Date());
			rirbt.setBatchStartTime(new Date());
			rirbt.setPreProcessEndTime(new Date());
			rirbt.setPreProcessStartTime(new Date());
			rirbt.setBatchProcessNum(100);
			rirbt.setPaymentOrderFailed(10);
			rirbt.setPaymentOrderSuccess(120);
			rirbt.setPaymentOrderNum(220);
			rirbt.setRefundOrderNum(100);
			rirbt.setRefundOrderSuccess(59);
			rirbt.setRefundOrderFailed(50);
			rirbt.setRefundOrderRemark("test refund");
			rirbt.setSuccessCount(20);
	
			this.updateReconcileRecordBatchProcess(rirbt);
		}	
		
	@Override 
	public void updateReconcileRecordBatchProcess(
				ReconcileImportRecordBatchDto importRecordBatch){
		this.reconcileRecordService.updateReconcileRecordBatchProcess(importRecordBatch);
	}
		
	@Override
	public void addResultList_ChannelOrderDTO(ReconcileImportRecordBatchDto importRecordBatch, 
			List<ReconcileImportRecordDetailDto> detailDtos,
			final ChannelOrderDTO channelOrder,
			final String batchNo,
			final String listIndex){
		
		ReconcileImportRecordDetailDto detailDto=new ReconcileImportRecordDetailDto();
		Integer reconciliationFlg = channelOrder.getReconciliationFlg();
		if(reconciliationFlg==1){
			
			detailDto.setStatus(1);
			
			//各个数字加1 
			Integer SuccessCount = importRecordBatch.getSuccessCount();
			SuccessCount++;
			importRecordBatch.setSuccessCount(SuccessCount);
			
			Integer paymentOrderSuccess = importRecordBatch.getPaymentOrderSuccess();
			paymentOrderSuccess++;
			importRecordBatch.setPaymentOrderSuccess(paymentOrderSuccess);
			
			
		}else{		//reconciliationFlg==4 ? 记账失败，需要特别处理么?
			detailDto.setStatus(2);
			
			Integer paymentOrderFailed = importRecordBatch.getPaymentOrderFailed();
			paymentOrderFailed++;
			importRecordBatch.setPaymentOrderFailed(paymentOrderFailed);	
		}
		detailDto.setStatus(reconciliationFlg);//设置对账结果，add by davis.guo at 2016-08-26
		//System.out.println("#########reconciliationFlg="+reconciliationFlg);
		
		Integer paymentOrderNum=importRecordBatch.getPaymentOrderNum();
		paymentOrderNum++;
		importRecordBatch.setPaymentOrderNum(paymentOrderNum);
		
		detailDto.setChannelOrderNo(channelOrder.getChannelOrderNo());
		detailDto.setRemark(channelOrder.getErrorMsg());
		detailDto.setBatchNoDetail(batchNo);	
		detailDto.setAcquirerRef(listIndex);
		detailDtos.add(detailDto);	
	}
	
	@Override 
	public void addResultList_RefundOrderDTO(ReconcileImportRecordBatchDto importRecordBatch, 
			List<ReconcileImportRecordDetailDto> detailDtos,
			final RefundOrder refundOrder,
			final String batchNo,
			final String listIndex){
		
		ReconcileImportRecordDetailDto detailDto=new ReconcileImportRecordDetailDto();		
		Integer reconciliationFlg = refundOrder.getReconciliationFlg();
		if (reconciliationFlg == 1) {

			detailDto.setStatus(1);
			
			Integer SuccessCount = importRecordBatch.getSuccessCount();
			SuccessCount++;
			importRecordBatch.setSuccessCount(SuccessCount);
			
			Integer refundOrderSuccess = importRecordBatch.getRefundOrderSuccess();
			refundOrderSuccess++;
			importRecordBatch.setRefundOrderSuccess(refundOrderSuccess);
		} else {
			detailDto.setStatus(2);
			
			Integer refundOrderFailed=importRecordBatch.getRefundOrderFailed();
			refundOrderFailed++;
			importRecordBatch.setPaymentOrderNum(refundOrderFailed);		
		}
		
		Integer refundOrderNum=importRecordBatch.getRefundOrderNum();
		refundOrderNum++;
		importRecordBatch.setPaymentOrderNum(refundOrderNum);
		
		detailDto.setChannelOrderNo(refundOrder.getRefundOrderNo());
		detailDto.setRemark(refundOrder.getErrorMsg());
		detailDto.setBatchNoDetail(batchNo); 
		detailDto.setAcquirerRef(listIndex);
		detailDtos.add(detailDto);	
	}
	


	/*
	 * 检查是否需要发送到交易日报表服务
	 * 检查是哪个渠道的对账单数据不在这边处理，交给异步监听服务fopay处理,这边只关心是否对账成功，
	 * 对账成功就加进交易日报表数据更新集合
	 */
	@Override 
	public boolean checkNeedAddtoReportService(
			ReconciliationDto reconciliationDto,
			ChannelOrderDTO   channelOrderDTO){
		Integer reconcilatoinFlg = channelOrderDTO.getReconciliationFlg();
		if(reconcilatoinFlg !=1){
			return false;
		}
		reconciliationDto.setReconciliationFlg(reconcilatoinFlg);
		return true ;
	}
	
	/*
	 * 正规化数据，==>ReportSerivce
	 * 这里可能有问题，所以这个代码需要放在 对账之后，不影响原有的对账流程
	 */
	@Override
	public void normalize2ReportService(ReconciliationDto reconciliationDto){
		
		if(reconciliationDto.getTransCurrency()==null){
			reconciliationDto.setTransCurrency("CNY");  //?
		}

		if(reconciliationDto.getSettlementCurrencyCode()==null){
			reconciliationDto.setSettlementCurrencyCode("CNY");  //?
		}
		
		if(reconciliationDto.getPerFee()==null){
			reconciliationDto.setPerFee(reconciliationDto.getTransFee());
			reconciliationDto.setFixedFee("0");
		}
		
		if(reconciliationDto.getSettlementAmount()==null){
			reconciliationDto.setSettlementAmount(reconciliationDto.getDealAmount());
		}
	}
	
	private String getBatchName(String orgCode,Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");		
		String format = df.format(date);	
		
		ReconcileImportRecordBatchDto importRecordBatch=new ReconcileImportRecordBatchDto();
		if(orgCode.equals("10076001")){//中银卡司
			format+="01";
		}else if(orgCode.equals("10075001")){//CREDORAX
			format+="02";
		}else if(orgCode.equals("10002001")){//农业银行
			format+="03";
		}
		
		return format;
		
	}
	
	
	/*
	 * 插入预处理 批次，返回值 BatchNo, 
	 * 
	 */
	@Override
	public String insertReconcileImportRecordBatch_Pre(List<ReconciliationDto> 
			reconciliationDtos,Map map,Date date) throws ParseException{
		// 获取文件名的Format 格式
		String fileName=(String) map.get("fileName");
	    String orgCode =(String) map.get("orgCode");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");		
		String format = getBatchName(orgCode, date);
	
		ReconcileImportRecordBatchDto importRecordBatch=new ReconcileImportRecordBatchDto();
		importRecordBatch.setFileName(fileName);
		importRecordBatch.setBacthNo("M_"+format);
		importRecordBatch.setApplyCount(reconciliationDtos.size());
		importRecordBatch.setCreateDate(date);
		importRecordBatch.setOrgCode(orgCode);
		importRecordBatch.setStatus(2);
		importRecordBatch.setOperator((String) map.get("operator"));
		
		String batchNo= insertReconcileImportRecordBatch(importRecordBatch);//生成批次
		
		if(reconciliationDtos == null || reconciliationDtos.size() == 0){
			return batchNo;
		}
		
		List<ReconcileImportRecordDetailDto> detailDtos=new ArrayList<ReconcileImportRecordDetailDto>();	
		int acquirerRefIndex= 0;
		for (ReconciliationDto reconciliationDto : reconciliationDtos) {
			Date date1=null;
			Date date2=null;
			try{
				if(StringUtils.isNotEmpty(reconciliationDto.getDealDate())){
					date1=df.parse(reconciliationDto.getDealDate());
				}
				if(StringUtils.isNotEmpty(reconciliationDto.getSettleDate())){
					date2=df.parse(reconciliationDto.getSettleDate());
				}
			}
			catch(Exception e){
				logger.info("parse data error " + e.toString());
			}
			reconciliationDto.setListIndex(String.valueOf(acquirerRefIndex++));

			ReconcileImportRecordDetailDto importRecordDetailDto = new ReconcileImportRecordDetailDto();// 封装credorax对账参数
			
			//这个字段 必须设置
			if(reconciliationDto.getChannelOrderNo()==null)
			{
				importRecordDetailDto.setChannelOrderNo(Long.valueOf(10000000));
			}
			else 
				importRecordDetailDto.setChannelOrderNo(Long.parseLong(reconciliationDto.getChannelOrderNo()));
			
			
			importRecordDetailDto.setStatus(0);
			importRecordDetailDto.setBatchNoDetail(batchNo);
			// 其实这个商户号，应该有用吧？
			// importRecordDetailDto.setMerchantNo(reconciliationDto.get("merchantNo"));
			importRecordDetailDto.setTransactionDate(date1);
			importRecordDetailDto.setPostingDate(date2);

			importRecordDetailDto.setTransCurrency(reconciliationDto
					.getTransCurrency());
			importRecordDetailDto.setTransAmount(reconciliationDto
					.getDealAmount());
			importRecordDetailDto.setAcctTotalCharges(reconciliationDto
					.getTransFee());
			importRecordDetailDto.setMerchTranRef(reconciliationDto
					.getRefeNumber()); // 这个参考号
			
			//授权码
			importRecordDetailDto.setAuthCode(reconciliationDto.getAuthCode() );  //2016-08-09

			importRecordDetailDto.setInterChangeCurrency(reconciliationDto
					.getSettlementCurrencyCode());
			importRecordDetailDto.setInterChangeAmount(reconciliationDto
					.getSettlementAmount());
			importRecordDetailDto
					.setFeeErcentage(reconciliationDto.getPerFee());
			importRecordDetailDto.setAcctAmountGross(reconciliationDto
					.getSettAmount());
			importRecordDetailDto.setFpi(reconciliationDto.getFixedFee());

			// 设置 数组下标
			importRecordDetailDto.setAcquirerRef(reconciliationDto
					.getListIndex());

			detailDtos.add(importRecordDetailDto);
		}
		insertReconcileImportRecordDetailDto(detailDtos);//生成明细
		return batchNo;	
	}


	/*
	 * 明细入库,返回值 BatchNo ，和上面的函数一样，但是使用数组来插入数据
	 * 输出参数： ReconciliationDto.setAcquirerRef 字段会被改掉（这个字段没有什么用）
	 * 
	 */
	@Override 
	public String insertReconcileImportRecordBatch2(List<ReconciliationDto> reconciliationDtos,
			Map map,Date date) throws ParseException{
	
		// 获取文件名的Format 格式
		String fileName=(String) map.get("fileName");
	    String orgCode =(String) map.get("orgCode");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");		
		String format = getBatchName(orgCode, date);
				
		ReconcileImportRecordBatchDto importRecordBatch=new ReconcileImportRecordBatchDto();
	
		importRecordBatch.setFileName(fileName);
		importRecordBatch.setBacthNo("M_"+format);
		importRecordBatch.setApplyCount(reconciliationDtos.size());
		importRecordBatch.setCreateDate(date);
		importRecordBatch.setOrgCode(orgCode);
		importRecordBatch.setStatus(2);
		importRecordBatch.setOperator((String) map.get("operator"));
		
		String batchNo=insertReconcileImportRecordBatch(importRecordBatch);//生成批次
		
		if(reconciliationDtos == null || reconciliationDtos.size() == 0){
			return batchNo;
		}
		
		List<ReconcileImportRecordDetailDto> detailDtos=new ArrayList<ReconcileImportRecordDetailDto>();	
		int acquirerRefIndex= 0;
		for (ReconciliationDto reconciliationDto : reconciliationDtos) {
				Date date1=null;
				Date date2=null;
			try{
				if(StringUtils.isNotEmpty(reconciliationDto.getDealDate())){
					date1=df.parse(reconciliationDto.getDealDate());
				}
				if(StringUtils.isNotEmpty(reconciliationDto.getSettleDate())){
					date2=df.parse(reconciliationDto.getSettleDate());
				}
			}
			catch(Exception e){
				logger.info("parse data error " + e.toString());
			}
				
			reconciliationDto.setListIndex(String.valueOf(acquirerRefIndex++));

			ReconcileImportRecordDetailDto importRecordDetailDto = new ReconcileImportRecordDetailDto();// 封装credorax对账参数
			importRecordDetailDto.setChannelOrderNo(Long
					.parseLong(reconciliationDto.getChannelOrderNo()));
			importRecordDetailDto.setStatus(0);
			importRecordDetailDto.setBatchNoDetail(batchNo);
			// 其实这个商户号，应该有用吧？
			// importRecordDetailDto.setMerchantNo(reconciliationDto.get("merchantNo"));
			importRecordDetailDto.setTransactionDate(date1);
			importRecordDetailDto.setPostingDate(date2);

			importRecordDetailDto.setTransCurrency(reconciliationDto
					.getTransCurrency());
			importRecordDetailDto.setTransAmount(reconciliationDto
					.getDealAmount());
			importRecordDetailDto.setAcctTotalCharges(reconciliationDto
					.getTransFee());
			importRecordDetailDto.setMerchTranRef(reconciliationDto
					.getRefeNumber()); // 这个参考号

			// 2016-06-25 把我们认为重要的一些字段，重新填进了 这个表 ，还差一个固定手续费
			importRecordDetailDto.setInterChangeCurrency(reconciliationDto
					.getSettlementCurrencyCode());
			importRecordDetailDto.setInterChangeAmount(reconciliationDto
					.getSettlementAmount());
			importRecordDetailDto
					.setFeeErcentage(reconciliationDto.getPerFee());
			importRecordDetailDto.setAcctAmountGross(reconciliationDto
					.getSettAmount());
			importRecordDetailDto.setFpi(reconciliationDto.getFixedFee());

			// 设置 数组下标
			importRecordDetailDto.setAcquirerRef(reconciliationDto
					.getListIndex());

			detailDtos.add(importRecordDetailDto);
		}
		insertReconcileImportRecordDetailDto(detailDtos);//生成明细
		return batchNo;	
	}
}
