/**
 * 
 */
package com.pay.fundout.bsp.service.impl;

import com.pay.fundout.bsp.service.FundAdjustmentService;
import com.pay.fundout.withdraw.dto.workorder.WorkOrderDto;
import com.pay.fundout.withdraw.model.paytoaccount.Pay2AcctOrder;
import com.pay.fundout.withdraw.service.paytoaccount.Pay2AcctOrderService;
import com.pay.fundout.withdraw.service.paytoaccount.Pay2AcctService;
import com.pay.fundout.withdraw.service.workorder.WorkorderService;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;

/**
 * @author NEW
 *
 */
public class FundAdjustmentServiceImpl implements FundAdjustmentService {

	private Pay2AcctOrderService pay2AcctOrderService;
	
	private WorkorderService workorderService;
	
	public void setPay2AcctOrderService(Pay2AcctOrderService pay2AcctOrderService) {
		this.pay2AcctOrderService = pay2AcctOrderService;
	}

	@Override
	public void createOrder(Pay2AcctOrder order,WorkOrderDto workorder) {
		if(pay2AcctOrderService.createOrderRnTx(order,Pay2AcctService.FUNDADJUSTMENT_ORDER_REQ)){
			//记账;
			if(pay2AcctOrderService.handleOrderRnTx(order, Pay2AcctService.FUNDADJUSTMENT_ORDER_REQ)){
				//创建复核工单
				workorder.setOrderSeq(order.getSequenceId());
				//资金调拨的订单类型为5
				workorder.setOrderType(5);
				workorderService.createWorkorderRnTx(workorder);
			    //TODO 通知
			}else{
				order.setErrorTips("资金调拨更新余额发生异常");
				LogUtil.error(getClass(), order.toString(), OPSTATUS.START, "", "资金调拨更新余额发生异常 ", null, null, null);
				throw new RuntimeException("资金调拨更新余额发生异常");
			}
			
		}else{
			LogUtil.error(getClass(), order.toString(), OPSTATUS.START, "", "保存资金调拨订单发生异常 ", null, null, null);
			throw new RuntimeException("保存资金调拨订单发生异常 ");
		}
	}

	@Override
	public void auditOrder(Pay2AcctOrder order,String requestFrom,WorkOrderDto workorder) {
		
		if(pay2AcctOrderService.handleOrderRnTx(order, requestFrom)){
			//更新工单状态
			workorderService.updateWorkorderRnTx(workorder);
		    //TODO 通知
		}else{
			order.setErrorTips("资金调拨更新余额发生异常");
			LogUtil.error(getClass(), order.toString(), OPSTATUS.START, "", "资金调拨更新余额发生异常 ", null, null, null);
			throw new RuntimeException("资金调拨更新余额发生异常");
		}
		
		
	}

	public void setWorkorderService(WorkorderService workorderService) {
		this.workorderService = workorderService;
	}
	
	

}
