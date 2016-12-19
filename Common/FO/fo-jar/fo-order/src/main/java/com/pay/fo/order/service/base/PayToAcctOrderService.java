/**
 * 
 */
package com.pay.fo.order.service.base;

import java.util.List;

import com.pay.fo.order.dto.base.PayToAcctOrderDTO;
import com.pay.fo.order.service.OrderService;

/**
 * @author NEW
 *
 */
public interface PayToAcctOrderService extends OrderService {
	/**
	 * 统计当日付款总金额
	 * @param orderType
	 * @param payerMemberCode
	 * @return
	 */
	Long sumCurrentDayPaymentAmount(Integer orderType,Long payerMemberCode); 
	
	/**
	 * 统计当月付款总金额
	 * @param orderType
	 * @param payerMemberCode
	 * @return
	 */
	Long sumCurrentMonthPaymentAmount(Integer orderType,Long payerMemberCode); 
	
	/**
	 * 统计当天付款次数
	 * @param orderType
	 * @param payerMemberCode
	 * @return
	 */
	Integer countCurrentDayPaymentTimes(Integer orderType,Long payerMemberCode); 
	
	/**
	 * 统计当月付款次数
	 * @param orderType
	 * @param payerMemberCode
	 * @return
	 */
	Integer countCurrentMonthPaymentTimes(Integer orderType,Long payerMemberCode);
	
	/**
	 * 获取指定父订单号关联的所有子订单信息
	 * @param parentOrderId
	 * @return
	 */
	List<PayToAcctOrderDTO> getChildPayToAcctOrderList(Long parentOrderId,Long payerMemberCode);

}
