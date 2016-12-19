/**
 * 
 */
package com.pay.fo.order.service.batchpayment;

import java.util.List;

import com.pay.fo.order.dto.batchpayment.BatchPaymentOrderDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentToAcctReqDetailDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentToBankReqDetailDTO;
import com.pay.fo.order.model.base.FundoutOrder;
import com.pay.fo.order.model.base.PayToAcctOrder;
import com.pay.fo.order.model.batchpayment.BatchPaymentTotalInfo;
import com.pay.fo.order.service.OrderService;

/**
 * @author NEW
 * 
 */
public interface BatchPaymentOrderService extends OrderService {

	/**
	 * 获取批量付款订单信息
	 * 
	 * @param memberCode
	 * @param orderType
	 * @param businessBatchNo
	 * @return
	 */
	BatchPaymentOrderDTO getOrder(Long memberCode, Integer orderType,
			String businessBatchNo);

	/**
	 * 获取处理完成的批付到账户的订单信息
	 * 
	 * @return
	 */
	List getCompleteBatchPay2AcctOrders();

	/**
	 * 获取处理完成的批付到银行的订单信息
	 * 
	 * @return
	 */
	List getCompleteBatchPay2BankOrders();

	/**
	 * 根据总订单号获取指定批量付款到银行订单的完成统计信息
	 * 
	 * @param parentOrderId
	 * @return
	 */
	BatchPaymentTotalInfo getTotalComplateBatchPay2BankOrder(Long parentOrderId);

	/**
	 * 根据总订单号获取指定批量付款到账户订单的完成统计信息
	 * 
	 * @param parentOrderId
	 * @return
	 */
	BatchPaymentTotalInfo getTotalComplateBatchPay2AcctOrder(Long parentOrderId);

	/**
	 * create item
	 * @param batchPaymentOrderId
	 * @param orderType
	 */
	void createDetailOrder(final Long batchPaymentOrderId,
			final Integer orderType);

	/**
	 * 创建批付到账户明细订单
	 * 
	 * @param parentOrderId
	 */
	void createDetailOrder(BatchPaymentOrderDTO order,
			BatchPaymentToAcctReqDetailDTO detail);

	/**
	 * 创建批付到银行明细订单
	 * 
	 * @param parentOrderId
	 */
	void createDetailOrder(BatchPaymentOrderDTO order,
			BatchPaymentToBankReqDetailDTO detail);
	
	/**
	 * 根据总订单号获取到账户子订单
	 * @param parentId
	 * @return
	 */
	List<PayToAcctOrder> getAcctDetailOrderByParentId(final Long parentId);
	
	/**
	 * 根据总订单号获取到银行子订单
	 * @param parentId
	 * @return
	 */
	List<FundoutOrder> getBankDetailOrderByParentId(final Long parentId);
}
