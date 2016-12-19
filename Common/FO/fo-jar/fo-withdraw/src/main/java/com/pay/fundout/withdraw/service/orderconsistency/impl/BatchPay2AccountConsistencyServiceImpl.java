/**
 *  File: WithdrawOrderAuditServicce.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-11      Jonathen Ni      Changes
 *
 */
package com.pay.fundout.withdraw.service.orderconsistency.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.dto.batchpayment.BatchPaymentOrderDTO;
import com.pay.fo.order.service.batchpayment.BatchPaymentOrderService;
import com.pay.fundout.withdraw.dao.orderconsistency.Masspay2AcctImpRecConsistencyDAO;
import com.pay.fundout.withdraw.dto.batchpaytoaccount.MasspayImportRecordDTO;
import com.pay.fundout.withdraw.model.batchpaytoaccount.MasspayImportRecord;
import com.pay.fundout.withdraw.service.orderconsistency.BatchPay2AccountConsistencyService;
import com.pay.fundout.withdraw.service.orderconsistency.RepairMassPay2AcctOrderService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;

public class BatchPay2AccountConsistencyServiceImpl implements
		BatchPay2AccountConsistencyService {
	
	private RepairMassPay2AcctOrderService repMaP2AcctOrderService;
	private Masspay2AcctImpRecConsistencyDAO massPayImpFileRecConsDao;
	private BatchPaymentOrderService batchPaymentOrderService;

	public void setRepMaP2AcctOrderService(
			RepairMassPay2AcctOrderService repairMaP2AcctOrderService) {
		this.repMaP2AcctOrderService = repairMaP2AcctOrderService;
	}

	public void setBatchPaymentOrderService(
			BatchPaymentOrderService batchPaymentOrderService) {
		this.batchPaymentOrderService = batchPaymentOrderService;
	}

	public void setMassPayImpFileRecConsDao(
			Masspay2AcctImpRecConsistencyDAO massPayImpFileRecConsDao) {
		this.massPayImpFileRecConsDao = massPayImpFileRecConsDao;
	}

	public Page<MasspayImportRecordDTO> search(Map paraMap, Page<MasspayImportRecordDTO> page) {
		Page<MasspayImportRecord> pageService = new Page<MasspayImportRecord>();
		PageUtils.setServicePage(pageService, page);

		pageService = this.massPayImpFileRecConsDao
				.queryMassPayImpFileRecByGenOrderFail(
						"masspayimportrecord.getImportFileByGenerateOrderFail",
						pageService, paraMap);

		PageUtils.setServicePage(page, pageService);
		List<MasspayImportRecord> resultList = pageService.getResult();
		List<MasspayImportRecordDTO> list = new ArrayList<MasspayImportRecordDTO>();
		for (MasspayImportRecord record : resultList) {
			MasspayImportRecordDTO dto = new MasspayImportRecordDTO();
			BeanUtils.copyProperties(record, dto);
			list.add(dto);
		}
		page.setResult(list);
		return page;
	}

	/**
	 * 查询批量付款到账户子订单生成失败的明细记录
	 */
	@Override
	public List<MasspayImportRecord> queryMassPayImpFileRecByGenOrderFail(
			String batchPay2AcctBatchNum, String payerMemberId) {
		return this.findbatchPay2AcctBatchNum(batchPay2AcctBatchNum,
				payerMemberId);
	}

	/**
	 * 根据批次号和付款方会员号,将未生成子订单的明细记录重成子订单
	 * 
	 * @param batchPay2AcctBatchNum
	 */
	public void repairMassPay2AcctOrder(String batchPay2AcctBatchNum,
			String payerMemberId, String operator) {
		List<MasspayImportRecord> list = findbatchPay2AcctBatchNum(
				batchPay2AcctBatchNum, payerMemberId);
		if (list != null) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("batchNum", batchPay2AcctBatchNum);
			map.put("payerMember", payerMemberId);
			// 获得批量付款到账户的主订单
			// MasspayBatchOrder batchOrder =
			// this.massPayImpFileRecConsDao.getMassPayBatchOrder("masspaybatchorder.getMassPayBatchOrder",map);
			BatchPaymentOrderDTO batchPaymentOrderDTO = batchPaymentOrderService
					.getOrder(Long.valueOf(payerMemberId),
							OrderType.BATCHPAY2ACCT.getValue(),
							batchPay2AcctBatchNum);
			if (batchPaymentOrderDTO != null) {
				for (MasspayImportRecord impFilerecord : list) {
					try {
						repMaP2AcctOrderService.repairMassPay2AcctOrderRnTx(
								impFilerecord, batchPaymentOrderDTO, operator);
					} catch (PossException e) {
						LogUtil.error(getClass(), "批量付款到账户修补子订单异常...,明细文件ID："
								+ impFilerecord.getSequenceId(), OPSTATUS.FAIL,
								batchPaymentOrderDTO.getBusinessBatchNo(), "",
								e.toString(), "", "");
					}
				}
			}
		}
	}

	private List<MasspayImportRecord> findbatchPay2AcctBatchNum(
			String batchPay2AcctBatchNum, String payerMemberId) {
		List<MasspayImportRecord> list = null;
		if (batchPay2AcctBatchNum != null && !batchPay2AcctBatchNum.equals("")
				&& payerMemberId != null && !payerMemberId.equals("")) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("batchNum", batchPay2AcctBatchNum);
			map.put("payerMember", payerMemberId);
			list = this.massPayImpFileRecConsDao
					.queryMassPayImpFileRecByGenOrderFail(
							"masspayimportrecord.getImportFileByGenerateOrderFail",
							map);
		}
		return list;
	}
}
