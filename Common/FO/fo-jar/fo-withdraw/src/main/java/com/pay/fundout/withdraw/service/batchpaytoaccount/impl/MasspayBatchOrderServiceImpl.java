/**
 *  File: MasspayBatchOrderServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-8     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.batchpaytoaccount.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.fundout.service.OrderStatus;
import com.pay.fundout.withdraw.dao.batchpaytoaccount.MasspayBatchOrderDAO;
import com.pay.fundout.withdraw.dao.batchpaytoaccount.MasspayImportFileDAO;
import com.pay.fundout.withdraw.dto.batchpaytoaccount.MasspayImportFileDTO;
import com.pay.fundout.withdraw.dto.batchpaytoaccount.MasspayImportRecordDTO;
import com.pay.fundout.withdraw.model.batchpaytoaccount.MasspayBatchOrder;
import com.pay.fundout.withdraw.model.batchpaytoaccount.MasspayImportFile;
import com.pay.fundout.withdraw.model.paytoaccount.Pay2AcctOrder;
import com.pay.fundout.withdraw.model.paytoaccount.Pay2AcctResponse;
import com.pay.fundout.withdraw.service.batchpaytoaccount.MasspayBatchOrderService;
import com.pay.fundout.withdraw.service.batchpaytoaccount.MasspayImportRecordService;
import com.pay.fundout.withdraw.service.paytoaccount.Pay2AcctService;
import com.pay.inf.exception.AppException;
import com.pay.poss.base.common.Constants;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.service.ma.batchpaytoaccount.MassPayService;

/**
 * @author darv
 * 
 */
@SuppressWarnings("unchecked")
public class MasspayBatchOrderServiceImpl implements MasspayBatchOrderService {
	Log log = LogFactory.getLog(getClass());

	private MasspayBatchOrderDAO masspayBatchOrderDAO;

	private Pay2AcctService pay2AcctService;


	private MassPayService massPayService;



	private MasspayImportRecordService masspayImportRecordService;
	
	private MasspayImportFileDAO masspayImportFileDAO;

	public void setMasspayImportRecordService(MasspayImportRecordService masspayImportRecordService) {
		this.masspayImportRecordService = masspayImportRecordService;
	}



	public void setMassPayService(MassPayService massPayService) {
		this.massPayService = massPayService;
	}

	public void setPay2AcctService(Pay2AcctService pay2AcctService) {
		this.pay2AcctService = pay2AcctService;
	}


	public void setMasspayBatchOrderDAO(MasspayBatchOrderDAO masspayBatchOrderDAO) {
		this.masspayBatchOrderDAO = masspayBatchOrderDAO;
	}
	

	public void setMasspayImportFileDAO(MasspayImportFileDAO masspayImportFileDAO) {
		this.masspayImportFileDAO = masspayImportFileDAO;
	}

	@Override
	public long createMasspayBatchOrder(MasspayBatchOrder masspayBatchOrder) {
		return this.masspayBatchOrderDAO.createMasspayBatchOrder(masspayBatchOrder);
	}

	@Override
	public Long getSeqId() {
		return this.masspayBatchOrderDAO.getSeqId();
	}

	@Override
	public Long getDayTotalAmount(Long memberCode) {
		Long val = this.masspayBatchOrderDAO.getDayTotalAmount(memberCode);
		return (val == null) ? 0 : val;
	}

	@Override
	public Long getMonthTotalAmount(Long memberCode) {
		Long val = this.masspayBatchOrderDAO.getMonthTotalAmount(memberCode);
		return (val == null) ? 0 : val;
	}

	@Override
	public Integer getMonthTotalCount(Long memberCode) {
		Integer val = this.masspayBatchOrderDAO.getMonthTotalCount(memberCode);
		return (val == null) ? 0 : val;
	}

	@Override
	public Integer getDayTotalCount(Long memberCode) {
		Integer val = this.masspayBatchOrderDAO.getDayTotalCount(memberCode);
		return (val == null) ? 0 : val;
	}

	@Override
	public void createBatchOrderRnTx(MasspayBatchOrder batchOrder, MasspayImportFileDTO importFile) throws PossException {
		try {
			Long seq = masspayBatchOrderDAO.createMasspayBatchOrder(batchOrder);
			if(seq!=null){
				MasspayImportFile model = new MasspayImportFile();
				model.setSequenceId(importFile.getSequenceId());
				model.setStatus(2);//复核通过
				model.setUpdateDate(new Date());
				model.setOldStatus(1);
				model.setAuditOperator(batchOrder.getOperators());
				model.setAuditRemark(importFile.getAuditRemark());
				if(!masspayImportFileDAO.updateMasspayImportFile(model)){
					throw new RuntimeException("更新批量付款到账户上传基本信息状态失败");
				
				}
			}else{
				throw new RuntimeException("存储批量订单失败");
			}
		} catch (Exception e) {
			LogUtil.error(MasspayBatchOrderServiceImpl.class, "批量订单生成错误", OPSTATUS.EXCEPTION, "batchNum:"
					+ batchOrder.getBatchNum(), "批量订单生成错误", e.getMessage(), null, null);
			throw new PossException(e.toString(), ExceptionCodeEnum.UN_DEFINE_EXCEPTIONCODE, e);
		}
	}

	@Override
	public Integer updateAmountRdTx(MasspayBatchOrder batchOrder, MasspayImportFileDTO importFile) throws PossException {
		try {
			Pay2AcctOrder pay2AcctOrder = new Pay2AcctOrder();
			pay2AcctOrder.setAmount(importFile.getPayTotalAmount());
			pay2AcctOrder.setCreateDate(new Date());
			pay2AcctOrder.setUpdateDate(new Date());
			pay2AcctOrder.setPayeeAcctCode("2181010010005");
			pay2AcctOrder.setPayerAcctCode(importFile.getPayerAcctCode());
			pay2AcctOrder.setPayerAcctType(importFile.getPayerAcctType());
			pay2AcctOrder.setPayerMember(importFile.getPayerMember());
			pay2AcctOrder.setRequestFrom(Pay2AcctService.REQUEST_CODE_FOR_BATCH_FIRST);
			pay2AcctOrder.setSequenceId(batchOrder.getSequenceId());
			pay2AcctOrder.setPayerMemType(MemberTypeEnum.MERCHANT.getCode());
			Pay2AcctResponse res = pay2AcctService.pay2Acct(pay2AcctOrder);
			batchOrder.setStatus(res.getStatus());

			if (res.getStatus() == Constants.ORDER_STATUS_FAIL) {
				LogUtil.error(getClass(), "批量付款到中间科目错误", OPSTATUS.FAIL, "batchNum:" + batchOrder.getBatchNum(), "batchOrderId:"
						+ batchOrder.getSequenceId(), "", "", "");
			}else{
				MasspayBatchOrder order = new MasspayBatchOrder();
				order.setSequenceId(batchOrder.getSequenceId());
				order.setStatus(OrderStatus.PROCESSING.getValue());
				order.setUpdateTime(new Date());
				masspayBatchOrderDAO.updateBatchOrderStatus(order);
			}
			return res.getStatus();
		} catch (Exception e) {
			LogUtil.error(getClass(), "系统错误" + e.getMessage(), OPSTATUS.FAIL, batchOrder.getBatchNum(), "", e.toString(), "", "");
			throw new PossException(e.toString(), ExceptionCodeEnum.UN_DEFINE_EXCEPTIONCODE, e);
		}
	}
	@Override
	public void createMassPayToAccountDetialOrder(Long batchOrderSeq){
		LogUtil.info(getClass(), "创建子订单线程开始", OPSTATUS.START, "batchOrderId:" + batchOrderSeq, "");
		MasspayBatchOrder batchOrder = masspayBatchOrderDAO.findById(batchOrderSeq);
		if(null==batchOrder){
			log.error("not found MasspayBatchOrder by batchOrderId:"+batchOrderSeq);
			return;
		}
		
		String payerName = "";
		try {
			MemberInfoDto memberInfo = massPayService.getMemberInfo(null, batchOrder.getPayerMember(), null, null);
			if (memberInfo != null) {
				payerName = memberInfo.getMemberName();
			}
		} catch (Exception e2) {
			log.error(e2);
			return;
		}
		//获取上传基本信息
		Map params = new HashedMap();
		params.put("batchNum", batchOrder.getBatchNum());
		params.put("payerMember", batchOrder.getPayerMember());
		MasspayImportFile importFile = masspayImportFileDAO.getImportFileByBatchNumAndPayer(params);
		
		if(importFile == null){
			log.error("not found MasspayImportFile by [butchNum,payerMember:"+batchOrder.getBatchNum()+","+batchOrder.getPayerMember()+"]");
			return;
		}
		
		params.clear();
		params.put("fileKy", importFile.getSequenceId());
		params.put("orderly", "N");
		List importRecordList = masspayImportRecordService.getImportRecordListByFileKyAll(params);
		Pay2AcctResponse res = null;
		for (int i = 0; i < importRecordList.size(); i++) {
			try {
				MasspayImportRecordDTO importRecord = (MasspayImportRecordDTO) importRecordList.get(i);
				Pay2AcctOrder pay2AcctOrder = new Pay2AcctOrder();
				pay2AcctOrder.setAmount(importRecord.getAmount());
				pay2AcctOrder.setParentOrder(batchOrder.getSequenceId());
				pay2AcctOrder.setCreateDate(new Date());
				pay2AcctOrder.setUpdateDate(new Date());
				pay2AcctOrder.setPayeeAcctCode(importRecord.getPayeeAcctCode());
				pay2AcctOrder.setPayeeAcctType(importRecord.getPayeeAcctType());
				pay2AcctOrder.setPayeeCode(importRecord.getPayeeCode());
				pay2AcctOrder.setPayeeMember(importRecord.getPayeeMember());
				//TODO 付款账户
				pay2AcctOrder.setPayerAcctCode("2181010010005");
				pay2AcctOrder.setPayerAcctType(importFile.getPayerAcctType());
				pay2AcctOrder.setPayerMember(importFile.getPayerMember());
				pay2AcctOrder.setPayerCode(importFile.getPayerAcctCode());
				pay2AcctOrder.setRemarks(importRecord.getRemark());
				pay2AcctOrder.setRequestFrom(Pay2AcctService.REQUEST_CODE_FOR_BATCH_SECOND);
				pay2AcctOrder.setPayeeMemType(MemberTypeEnum.INDIVIDUL.getCode());//TODO 收付款放会员类型定义有什么作用
				pay2AcctOrder.setPayerMemType(MemberTypeEnum.MERCHANT.getCode());
				pay2AcctOrder.setPayeeName(importRecord.getPayeeName());
				if (importRecord.getPayeeCode().matches("\\d{11}")) {
					pay2AcctOrder.setPayeePhone(importRecord.getPayeeCode());
				} else {
					pay2AcctOrder.setPayeeMail(importRecord.getPayeeCode());
				}
				pay2AcctOrder.setPayerName(payerName);
				if (batchOrder.getPayerCode().matches("\\d{11}")) {
					pay2AcctOrder.setPayerPhone(batchOrder.getPayerCode());
				} else {
					pay2AcctOrder.setPayerMail(batchOrder.getPayerCode());
				}
				if (masspayImportRecordService.updateStatus(importRecord) == 1) {
					res = pay2AcctService.pay2Acct(pay2AcctOrder);
					if (res.getStatus() != Constants.ORDER_STATUS_SUCC) {
						LogUtil.error(getClass(), res.getErrotTip(), OPSTATUS.FAIL, "batchNum:"
								+ batchOrder.getBatchNum(), "batchOrderId:" + batchOrder.getSequenceId(),
								pay2AcctOrder.toString(), "", "");
					}
				}
			} catch (Exception e) {
				LogUtil.error(getClass(), "创建批量子订单有误", OPSTATUS.FAIL, "batchNum:" + batchOrder.getBatchNum(),
						"batchOrderId:" + batchOrder.getSequenceId(), e.toString(), "", "");
			}
		}
		LogUtil.info(getClass(), "创建子订单线程结束", OPSTATUS.SUCCESS, "batchOrderId:" + batchOrder.getSequenceId(), "");
	}



	@Override
	public Integer passRequestRdTx(MasspayImportFileDTO importFile,
			MasspayBatchOrder batchOrder) throws AppException {
		createBatchOrderRnTx(batchOrder, importFile);
	    return updateAmountRdTx(batchOrder, importFile);
	}

	@Override
	public boolean rejectRequestRdTx(MasspayImportFileDTO importFile)
			throws AppException {
		try{
			MasspayImportFile model = new MasspayImportFile();
			model.setSequenceId(importFile.getSequenceId());
			model.setStatus(3);//复核拒绝
			model.setOldStatus(1);
			model.setUpdateDate(new Date());
			model.setAuditOperator(importFile.getAuditOperator());
			model.setAuditRemark(importFile.getAuditRemark());
			boolean result = masspayImportFileDAO.updateMasspayImportFile(model);
			if(!result){
				throw new AppException();
			}
			return result;
		}catch(Throwable e){
			e.printStackTrace();
			throw new AppException();
		}
		
		
	}

	@Override
	public void processCompleteMassPaytoAcctOrder() {
		//获取已处理完成单未更新为完成状态的总订单列表
		List<MasspayBatchOrder> list = masspayBatchOrderDAO.getCompleteMassPaytoAcctOrder();
		for (MasspayBatchOrder masspayBatchOrder : list) {
			MasspayBatchOrder model = new MasspayBatchOrder();
			model.setSequenceId(masspayBatchOrder.getSequenceId());
			model.setUpdateTime(new Date());
			model.setStatus(OrderStatus.PROCESSED_SUCCESS.getValue());
			model.setOldStatus(OrderStatus.PROCESSING.getValue());
			//将总订单状态为完成状态
			if(!masspayBatchOrderDAO.updateBatchOrderStatus(model)){
				return;
			}
			//TODO 发送处理完成的通知
		}
		
		
	
		
	}
}
