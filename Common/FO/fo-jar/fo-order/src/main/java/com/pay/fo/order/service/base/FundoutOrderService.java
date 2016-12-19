/**
 * 
 */
package com.pay.fo.order.service.base;

import java.util.List;
import java.util.Map;

import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.dto.base.OrderInfoDTO;
import com.pay.fo.order.service.OrderService;
import com.pay.inf.dao.Page;

/**
 * @author NEW
 * 
 */
public interface FundoutOrderService extends OrderService {
	/**
	 * 统计当日付款总金额
	 * 
	 * @param orderType
	 * @param payerMemberCode
	 * @return
	 */
	Long sumCurrentDayPaymentAmount(Integer orderType, Long payerMemberCode);

	/**
	 * 统计当月付款总金额
	 * 
	 * @param orderType
	 * @param payerMemberCode
	 * @return
	 */
	Long sumCurrentMonthPaymentAmount(Integer orderType, Long payerMemberCode);

	/**
	 * 统计当天付款次数
	 * 
	 * @param orderType
	 * @param payerMemberCode
	 * @return
	 */
	Integer countCurrentDayPaymentTimes(Integer orderType, Long payerMemberCode);

	/**
	 * 统计当月付款次数
	 * 
	 * @param orderType
	 * @param payerMemberCode
	 * @return
	 */
	Integer countCurrentMonthPaymentTimes(Integer orderType, Long payerMemberCode);

	/**
	 * 获取指定父订单号关联的所有子订单信息
	 * 
	 * @param parentOrderId
	 * @return
	 */
	List<FundoutOrderDTO> getChildFundoutOrderList(Long parentOrderId, Long payerMemberCode);

	/**
	 * 查询批量付款到银行详细
	 * 
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page<FundoutOrderDTO> queryPayToBankBatchDetail(Map<String, Object> map, Integer pageNo, Integer pageSize);
	
	/**
	 * 查询批量付款到账户详细
	 * 
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page<OrderInfoDTO> queryPayToAcctBatchDetail(Map<String, Object> map, Integer pageNo, Integer pageSize);
}
