/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.reconciliation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fi.commons.TransTypeEnum;
import com.pay.fi.exception.BusinessException;
import com.pay.fi.helper.ChannelItemOrgCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.jms.notification.request.TranDailyReportNotifyRequest;
import com.pay.jms.notification.request.AccountingReconcileRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.txncore.commons.ReconcileBatchStatusEnum;
import com.pay.txncore.dto.ChannelOrderDTO;
import com.pay.txncore.dto.ReconcileImportRecordBatchDto;
import com.pay.txncore.dto.ReconcileImportRecordDetailDto;
import com.pay.txncore.dto.ReconciliationDto;
import com.pay.txncore.model.RefundOrder;
import com.pay.txncore.service.ChannelService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;
import com.pay.txncore.service.reconcile.ReconcileBatchService;

/**
 * 渠道对账
 *  File: ReconciliationHandler.java
 *  Description:
 *  Copyright 2016-2030 IPAYLINKS Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2016年4月25日   mmzhang     Changes 添加退款对账的逻辑，退款对账取退款订单号，核对金额 要乘以汇率
 *  2016年6月25日   nico.shao	 对于部分需要和交易日报表进行核对的条目，条用服务，进行核对 
 *  2016年7月04日   nico.shao   更新条目的时候，把批次也放进去 
 *  bug: 原来的代码中, 如果同一个批次中,如果有两条记录, 则最后更新 Reconcile_import_record_detail 的时候，
 *   where ChananelOrderNo = xxx and Batch_no_deail = xxx 
 *   后一个语句会把前面的语句冲掉。 
 *   正确的方法，应该是使用 Reconcile_import_record_detail.ID 来进行更新。
 *  
 *  但是由于原来的代码中 ，是使用一个  List 进行insert, 把id 取出来，再赋值给 List<ReconciliationDto> 比较麻烦。
 *  我们做了一个变通的方式， ReconciliationDto.setAcquirerRef 字段，给了一个按照顺序编号的内容。
 *  
 *  更新的时候
 *    where ChananelOrderNo = xxx and Batch_no_deail = xxx  and  AcquirerRef = xxxxx
 *  这样保证同一个 批次内的 ChannelOrderNo 同样的ChannelOrderNo 也不会被更新掉 
 *  2016-08-10  nico.shao  使用新的对账流程，改成异步了 
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class ReconciliationHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(getClass());
	private ChannelService channelService;
	private JmsSender jmsSender;
	private ReconcileBatchService reconcileBatchService;
	
	public void setReconcileBatchService(ReconcileBatchService reconcileBatchService){
		this.reconcileBatchService = reconcileBatchService;
	}
	
	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	/**
	 * @param jmsSender the jmsSender to set
	 */
	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

	/*
	 * 老的处理流程  
	 * 输入参数：paramMap 参数
	 */
	private String handle_old(Map paraMap){
		if(logger.isDebugEnabled()){
			//logger.info("handle_Old" + paraMap.toString());
		}
		
		Map resultMap = new HashMap();

		List<ChannelOrderDTO> channelOrderDTOs = null;
		List<RefundOrder> refundOrders = null;
		List<ReconciliationDto> ReportsReconcilianList = null;	//给报表服务调用
		
		try {
			String startDate = (String) paraMap.get("startDate");
			String endDate = (String) paraMap.get("endDate");
			List<Map> listMap = (List<Map>) paraMap.get("reconciliationDtos");
			List<String> settlementCurrencyCodesList = (List<String>)paraMap.get("settlementCurrencyCodes");	
			String[] settlementCurrencyCodes =null;
			if( (settlementCurrencyCodesList != null) && (settlementCurrencyCodesList.size()>0) ) {
				settlementCurrencyCodes = new String[settlementCurrencyCodesList.size()] ;
				settlementCurrencyCodesList.toArray(settlementCurrencyCodes);
			}
			
			logger.info("settlementCurrencyCodesArray=[" + settlementCurrencyCodesList
					    +"],settlementCurrencyCodes=[" + settlementCurrencyCodes +"]");
	
			List<ReconciliationDto> reconciliationDtos = MapUtil.map2List(ReconciliationDto.class, listMap);
			Date date = new Date();
			
			String batchNo=this.reconcileBatchService.insertReconcileImportRecordBatch2(reconciliationDtos,paraMap,date);
			if(batchNo == null){
				//最好是直接扔一个异常 
				throw new Exception("产生批次出错");						
			}
			
			//更新的数据 
			List<ReconcileImportRecordDetailDto> importRecordDetailDtos=new ArrayList<ReconcileImportRecordDetailDto>(); 
			ReconcileImportRecordBatchDto importRecordBatch=new ReconcileImportRecordBatchDto();
			
			importRecordBatch.initValue(batchNo,Integer.valueOf(1));
			//importRecordBatch.setSuccessCount(Integer.valueOf(0));
			//importRecordBatch.setStatus(1);
			//importRecordBatch.setBacthNo(batchNo);
			this.channelService.updateReconcileRecordBatch(importRecordBatch);
			
			if (null != reconciliationDtos && !reconciliationDtos.isEmpty()) {
				channelOrderDTOs = new ArrayList<ChannelOrderDTO>();
				refundOrders = new ArrayList<RefundOrder>();
				ReportsReconcilianList = new ArrayList<ReconciliationDto>();
				
				for (ReconciliationDto reconciliationDto : reconciliationDtos) {
					ChannelOrderDTO channelOrderDTO = new ChannelOrderDTO();
					RefundOrder refundOrder = new RefundOrder();

					/**
					 * 添加退款对账，addby mmzhang 2016年4月26日
					 */
					// 106开头的订单问退款订单
					if (reconciliationDto != null
							&& !StringUtil.isEmpty(reconciliationDto.getChannelOrderNo())
							&& reconciliationDto.getChannelOrderNo().startsWith("106")) {
						try {
							logger.info("退款对账开始，退款订单号:" + reconciliationDto.getChannelOrderNo());
							refundOrder = channelService.reconciliationRefundRnTx(startDate,
									endDate,
									reconciliationDto);
							// 回显对账结果
							refundOrder.setBacthNo(batchNo);
							refundOrder.setOperator((String) paraMap.get("operator"));
							refundOrder.setBacthCreateDate(date);
						} catch (BusinessException e) {
							logger.error("BusinessException:", e);
							refundOrder.setErrorMsg(e.getMessage());
						} catch (Exception e) {
							logger.error("Reconciliation error:", e);
							refundOrder.setErrorMsg("未知异常");
						}
						
						if (null != refundOrder) {
							
							this.reconcileBatchService.addResultList_RefundOrderDTO(importRecordBatch, 
									importRecordDetailDtos, refundOrder, batchNo, 
									reconciliationDto.getListIndex());
							
							refundOrders.add(refundOrder);
						}
						/**
						 * 原渠道对账
						 */
					} else {
						try {
							logger.info("原渠道对账开始，渠道订单号:" + reconciliationDto.getChannelOrderNo());
							channelOrderDTO = channelService.reconciliationRnTx(startDate, endDate,
									reconciliationDto,settlementCurrencyCodes);
							// 回显对账结果
							channelOrderDTO.setBacthNo(batchNo);
							channelOrderDTO.setOperator((String) paraMap.get("operator"));
							channelOrderDTO.setBacthCreateDate(date);
							
							//add by sch 2016-06-26 正确对账
							if(reconcileBatchService.checkNeedAddtoReportService(reconciliationDto,channelOrderDTO)){
								reconcileBatchService.normalize2ReportService(reconciliationDto);
								ReportsReconcilianList.add(reconciliationDto);
							}
							
						} catch (BusinessException e) {
							logger.error("BusinessException:", e);
							channelOrderDTO.setErrorMsg(e.getMessage());
						} catch (Exception e) {
							logger.error("Reconciliation error:", e);
							channelOrderDTO.setErrorMsg("未知异常");
							channelOrderDTO.setTransFee(reconciliationDto.getTransFee());
							channelOrderDTO.setSettAmount(reconciliationDto.getSettAmount());
							channelOrderDTO.setSettleDate(reconciliationDto.getSettleDate());
							channelOrderDTO.setChannelOrderNo(Long.valueOf(reconciliationDto
									.getChannelOrderNo()));
						}
						if (null != channelOrderDTO) {
							
							this.reconcileBatchService.addResultList_ChannelOrderDTO(importRecordBatch, 
									importRecordDetailDtos, channelOrderDTO, batchNo, 
									reconciliationDto.getListIndex());
							
							channelOrderDTOs.add(channelOrderDTO);
						}
					}
				}
			}
		    //this.updateReconcileRecord(listMap.size(), channelOrderDTOs,refundOrders,batchNo);
			this.updateReconcileRecord2(importRecordBatch, importRecordDetailDtos);
		    
		    //调用交易日报表，进行核对账目,2016-06-25
		    updateReportService(ReportsReconcilianList);
		    
			resultMap.put("listMap", channelOrderDTOs);
			resultMap.put("listMap2", refundOrders);
			
		}catch(ParseException e){
			logger.error("生成批次错误！！", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("Reconciliation error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}

		resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		return JSonUtil.toJSonString(resultMap);
	}
	
	/* 检查该文件名是否已经存在了，相差时间在 seconds 之内，不允许执行
	 * 返回值： false 表示没有同名文件
	 */
	private boolean checkBatchFileNameExist(String fileName,int seconds){
		seconds = seconds*1000;  //以毫秒为单位 
		logger.info("begin");
		List<ReconcileImportRecordBatchDto> batchList= reconcileBatchService.queryReconcileBatchByFileName(fileName);
		if(batchList == null){
			logger.info("end batchList is null");
			return false;
		}
		
		if(batchList.isEmpty()){
			logger.info("end batchList is empty");
			return false;
		}
		
		Date now = new Date();
		long nowTime = now.getTime();
		
		for (ReconcileImportRecordBatchDto  rbbd:batchList){
			Date createDate = rbbd.getCreateDate();
			long createDateTime = createDate.getTime();
			logger.info("now=[" + nowTime + "],createTime=["+ createDateTime + "]");
			if(Math.abs(nowTime - createDateTime) < seconds){
				return true;
			}
		}
		
		return false;
	}
	/*
	 * 新处理流程 
	 * 输入参数：paramMap 参数
	 */
	private String handle_new(Map paraMap){
		if(logger.isDebugEnabled()){
			//logger.debug("handle_new" + paraMap.toString());
		}
		
		Map resultMap = new HashMap();

		//ist<ChannelOrderDTO> channelOrderDTOs = null;
		//List<RefundOrder> refundOrders = null;
		//List<ReconciliationDto> ReportsReconcilianList = null;	//给报表服务调用
		
		//this.reconcileBatchService.test();
		
		try {
			//(1) 获取参数 
			String command = (String) paraMap.get("command");
			String startDate = (String) paraMap.get("startDate");
			String endDate = (String) paraMap.get("endDate");
			String orgCode = (String) paraMap.get("orgCode");
			List<Map> listMap = (List<Map>) paraMap.get("reconciliationDtos");
			
			List<String> settlementCurrencyCodesList = (List<String>)paraMap.get("settlementCurrencyCodes");	
			String[] settlementCurrencyCodes =null;
			if( (settlementCurrencyCodesList != null) && (settlementCurrencyCodesList.size()>0) ) {
				settlementCurrencyCodes = new String[settlementCurrencyCodesList.size()] ;
				settlementCurrencyCodesList.toArray(settlementCurrencyCodes);
			}
			
			List<ReconciliationDto> reconciliationDtos = MapUtil.map2List(ReconciliationDto.class, listMap);
			
			logger.info("settlementCurrencyCodesArray=[" + settlementCurrencyCodesList
				    +"],settlementCurrencyCodes=[" + settlementCurrencyCodes +"]");
			logger.info("reconciliationDtos size=" + reconciliationDtos.size());
			
			Date date = new Date();
			
			//判断一下文件名，看一下是否有重复
			
			{
				String fileName=(String) paraMap.get("fileName");
				boolean bcheckFileName = checkBatchFileNameExist(fileName,30*60);//30*60，为了方便测试 TODO
				if(bcheckFileName){
					logger.info("已经存在同名文件了 ");
					resultMap.put("responseCode",ResponseCodeEnum.UNDEFINED_ERROR.getCode());
					resultMap.put("responseDesc","短时间之内不允许上传同名文件");
					return JSonUtil.toJSonString(resultMap);
				}
			}
			
			//(3) 如果需要预先处理
			if((command !=null ) && (command.equals("pre"))){
				
				logger.info("do pre ");
				//插入预处理流程 
				String batchNo=this.reconcileBatchService.insertReconcileImportRecordBatch_Pre(reconciliationDtos,paraMap,date);
		
				SendJMS_Batch_Pre(batchNo,startDate,endDate,orgCode,settlementCurrencyCodesList,
						reconciliationDtos);
				
				resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
				resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
				return JSonUtil.toJSonString(resultMap);
			}
			
			//(4)插入批处理 
			logger.info("do process ");
			
			String batchNo=this.reconcileBatchService.insertReconcileImportRecordBatch2(reconciliationDtos,paraMap,date);
			if(batchNo == null){
				//最好是直接扔一个异常 
				throw new Exception("产生批次出错");						
			}
			
			SendJMS_Batch_Start(batchNo,startDate,endDate,orgCode,settlementCurrencyCodesList,
					reconciliationDtos);
		
			
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}
		catch (Exception e) {
			logger.error("Reconciliation error:", e);
			resultMap.put("responseCode",ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}
		return JSonUtil.toJSonString(resultMap);
	}
	
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map paraMap = null;
		
		paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
		String methodString = (String )paraMap.get("method");
		if(methodString==null || methodString.equals("1")){
			return handle_old(paraMap);
		}
				
		return handle_new(paraMap);
	}
	
	
	
	public void updateReconcileRecord2(ReconcileImportRecordBatchDto importRecordBatch,
			List<ReconcileImportRecordDetailDto> detailDtos){
		
		if( (detailDtos !=null) && (detailDtos.size()>0)){
			this.reconcileBatchService.updateReconcileRecordDetail(detailDtos);
		}
		
		if(importRecordBatch != null){
			this.reconcileBatchService.updateReconcileRecordBatch(importRecordBatch);
		}
	}
	

	/*
	 * 调用交易日报表，进行维护
	 */
	private void   updateReportService(List<ReconciliationDto> reportsReconcilianList){
		logger.info("update reportService");
		this.notifyTranDailyReport(reportsReconcilianList);
	}
	
	/**
	 * added by Jiangbo.Peng
	 * 交易日报表数据异步更新
	 * @param str
	 * 注意： 如果要改这里的代码，请同步修改 ReconcilationTaskListner.java 中的代码 
	 */
	private void notifyTranDailyReport(List<ReconciliationDto> reportsReconcilianList) { 
		logger.info("notifyTranDailyReport................");
		try {
			//发送mq消息到forpay
			String reqMsg = JSonUtil.toJSonString(reportsReconcilianList); // 转json发送保存的数据
			
			TranDailyReportNotifyRequest tranDailyReportNotifyRequest = new TranDailyReportNotifyRequest();
			Map<String, String> data = new HashMap<String, String>() ;
			data.put("list", reqMsg) ;
			tranDailyReportNotifyRequest.setData(data);
			jmsSender.send("notify.forpay",tranDailyReportNotifyRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void SendJMS_Batch_Pre(String batchNo,
			String startDate,String endDate,
			String orgCode,
			List<String>settlementCurrencyCodes,
			List<ReconciliationDto> reconciliationDtos){
		logger.info("send jms batch prestart");

		//更新批次状态为 开始预先处理 状态
		reconcileBatchService.updateBatchPreStatus(batchNo,
				ReconcileBatchStatusEnum.PREPROCESS_SENDMSG.getCode());
		
		String reqMsg = JSonUtil.toJSonString(reconciliationDtos); // 转json发送保存的数据
		String settlementCurrencyCodesMsg = JSonUtil.toJSonString(settlementCurrencyCodes);
		AccountingReconcileRequest accountingReconcileRequest = new AccountingReconcileRequest();
		Map<String, String> data = new HashMap<String, String>() ;
		//data.put("list", reqMsg) ;   modify by cuber.huang
		data.put("batchNo", batchNo);
		data.put("command", "pre");		//预处理
		data.put("startDate",startDate);
		data.put("endDate",endDate );
		data.put("orgCode", orgCode);
		data.put("settlementCurrencyCodes", settlementCurrencyCodesMsg);
		
		accountingReconcileRequest.setData(data);
		try {
			
			jmsSender.send("notify.accounting.reconcilelist",accountingReconcileRequest);
			
		} catch (Exception e) {
			logger.error("send jms error ", e);
			
			reconcileBatchService.updateBatchPreStatus(batchNo,
					ReconcileBatchStatusEnum.PREPROCESS_SENDFAILED.getCode());		//设置为发生消息失败
		}
	}
	
	private void SendJMS_Batch_Start(String batchNo,
			String startDate,String endDate,
			String orgCode,
			List<String>settlementCurrencyCodes,
			List<ReconciliationDto> reconciliationDtos){
		logger.info("send_jms_batch_process  begin");

		//更新批次状态为 开始 状态 
		reconcileBatchService.updateBatchStatus(batchNo,
				ReconcileBatchStatusEnum.PROCESS_SENDMSG.getCode());
		
		String reqMsg = JSonUtil.toJSonString(reconciliationDtos); // 转json发送保存的数据
		String settlementCurrencyCodesMsg = JSonUtil.toJSonString(settlementCurrencyCodes);
		
		AccountingReconcileRequest accountingReconcileRequest = new AccountingReconcileRequest();
		Map<String, String> data = new HashMap<String, String>() ;
		//data.put("list", reqMsg) ;
		data.put("batchNo", batchNo);
		data.put("command", "process");	 //正常处理
		data.put("startDate",startDate);
		data.put("endDate",endDate );
		data.put("orgCode", orgCode);
		data.put("settlementCurrencyCodes", settlementCurrencyCodesMsg);
		
		accountingReconcileRequest.setData(data);
		try {
			
			jmsSender.send("notify.accounting.reconcilelist",accountingReconcileRequest);
			
		} catch (Exception e) {
			logger.error("send jms error ", e);			
			
			reconcileBatchService.updateBatchStatus(batchNo,
					ReconcileBatchStatusEnum.PROCESS_SENDFAILED.getCode());		//设置为发生消息失败
		}
		
		logger.info("send_jms_batch_process end");
	}
}
