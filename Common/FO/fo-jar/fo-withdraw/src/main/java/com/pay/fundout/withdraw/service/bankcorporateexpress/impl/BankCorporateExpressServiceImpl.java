package com.pay.fundout.withdraw.service.bankcorporateexpress.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.service.WorkOrderStatus;
import com.pay.fundout.withdraw.dao.bankcorporateexpress.BankCorporateExpressDao;
import com.pay.fundout.withdraw.dao.flowprocess.WithdrawAuditOrderDao;
import com.pay.fundout.withdraw.dao.flowprocess.WithdrawAuditWorkOrderDao;
import com.pay.fundout.withdraw.dto.bankcorporateexpress.BankCorporateExpressReqDTO;
import com.pay.fundout.withdraw.dto.bankcorporateexpress.BankCorporateExpressResDTO;
import com.pay.fundout.withdraw.model.order.WithdrawOrder;
import com.pay.fundout.withdraw.model.work.WithdrawWorkorder;
import com.pay.fundout.withdraw.service.bankcorporateexpress.BankCorporateExpressService;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawService;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;

public class BankCorporateExpressServiceImpl implements BankCorporateExpressService {

	protected Log log = LogFactory.getLog(getClass());

	private BankCorporateExpressDao bankCorporateExpressDao;
	private WithdrawAuditWorkOrderDao withdrawWorkDao;
	private WithdrawAuditOrderDao withdrawOrderDao;
	private WithdrawService withdrawService;

	public void setBankCorporateExpressDao(BankCorporateExpressDao bankCorporateExpressDao) {
		this.bankCorporateExpressDao = bankCorporateExpressDao;
	}

	public void setWithdrawWorkDao(WithdrawAuditWorkOrderDao withdrawWorkDao) {
		this.withdrawWorkDao = withdrawWorkDao;
	}

	public void setWithdrawOrderDao(WithdrawAuditOrderDao withdrawOrderDao) {
		this.withdrawOrderDao = withdrawOrderDao;
	}

	public void setWithdrawService(WithdrawService withdrawService) {
		this.withdrawService = withdrawService;
	}

	@Override
	public Page<BankCorporateExpressResDTO> bankCorporateExpressFailQuery(Page<BankCorporateExpressResDTO> page, BankCorporateExpressReqDTO reqDTO) {
		return bankCorporateExpressDao.bankCorporateExpressFailQuery(page, reqDTO);
	}

	@Override
	public Page<BankCorporateExpressResDTO> bankCorporateExpressReAuditQuery(Page<BankCorporateExpressResDTO> page, BankCorporateExpressReqDTO reqDTO) {
		return bankCorporateExpressDao.bankCorporateExpressReAuditQuery(page, reqDTO);
	}

	/**
	 * 确认失败
	 */
	@Override
	public boolean confirmFailRdTx(String workorders) throws PossException {
		boolean bl = false;
		try {
			String[] workorderArray = workorders.split(",");
			BankCorporateExpressResDTO dto = null;
			if (workorderArray != null && workorderArray.length > 0) {
				for (String workorderId : workorderArray) {
					dto = new BankCorporateExpressResDTO();
					dto.setWorkOrderky(workorderId);
					dto.setStatus(WorkOrderStatus.BANKCORPORATEEXPRESS_CONFIRMFAIL.getValue());

					bankCorporateExpressDao.auditUpdate(dto);
				}
			}
			bl = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new PossException(e.getMessage(), ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
		return bl;
	}

	/**
	 * 同意
	 * 
	 * @param workorders
	 * @return
	 * @throws PossException
	 */
	@Override
	public boolean reAuditPass(String workorders) throws PossException {
		boolean bl = false;
		try {
			String[] workorderArray = workorders.split(",");
			WithdrawWorkorder withdrawWorkorder = new WithdrawWorkorder();
			List<String> workorderIdList = new ArrayList<String>();

			if (workorderArray != null && workorderArray.length > 0) {
				for (String workorderId : workorderArray) {
					workorderIdList.add(workorderId);
				}
			}
			withdrawWorkorder.setSubList(workorderIdList);
			List<WithdrawWorkorder> workOrderList = withdrawWorkDao.findByQuery("WF.queryWorkOrderListById", withdrawWorkorder);

			// 获得订单
			List<WithdrawOrder> orderList = withdrawOrderDao.findByQuery("WF.queryWithdrawOrderListByWorkKy", withdrawWorkorder);
			Map<Long, WithdrawOrder> orderMap = null;
			if (orderList != null) {
				orderMap = new HashMap<Long, WithdrawOrder>();
				for (WithdrawOrder order : orderList) {
					orderMap.put(order.getSequenceId(), order);
				}
			}
			// 获得此批提交审核工单的工作流实例流程号
			for (WithdrawWorkorder workOrder : workOrderList) {
				try {
					workOrder.setStatus(WorkOrderStatus.BANKCORPORATEEXPRESS_FAIL.getValue());
					workOrder.setPreStatus("a.status in(15)");
					WithdrawOrder order = orderMap.get(workOrder.getOrderSeq());
					order.setErrorMessage("失败原因");
					// 更新工单并且记账
					try {
						withdrawService.liquidateProcessRdTx(order, true, workOrder, null, "");
					} catch (Exception e) {
						log.error("update workorderorder drror...,workorderId is:" + workOrder.getWorkOrderky(), e);
						throw new PossException(e.getMessage(), ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
					}
				} catch (Exception e) {
					log.error("reAuditPass error...,workOrderKy is: " + workOrder.getWorkOrderky(), e);
				}
			}

			bl = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new PossException(e.getMessage(), ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
		return bl;
	}

	/**
	 * 复核拒绝
	 */
	@Override
	public boolean reAuditRejectRdTx(String workorders) throws PossException {
		boolean bl = false;
		try {
			String[] workorderArray = workorders.split(",");
			BankCorporateExpressResDTO dto = null;
			if (workorderArray != null && workorderArray.length > 0) {
				for (String workorderId : workorderArray) {
					dto = new BankCorporateExpressResDTO();
					dto.setWorkOrderky(workorderId);
					dto.setStatus(WorkOrderStatus.BANKCORPORATEEXPRESS_PASS.getValue());

					bankCorporateExpressDao.auditUpdate(dto);
				}
			}
			bl = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new PossException(e.getMessage(), ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
		return bl;
	}
	/**
	 * 查询指定条件的所有交易数据
	 * @param reqDTO 查询条件
	 * @return 所有数据的集合
	 */
	@Override
	public List<BankCorporateExpressResDTO> bankCorporateExpressFailQuery(
			BankCorporateExpressReqDTO reqDTO) {
		return bankCorporateExpressDao.bankCorporateExpressFailQuery(reqDTO);
	}

}
