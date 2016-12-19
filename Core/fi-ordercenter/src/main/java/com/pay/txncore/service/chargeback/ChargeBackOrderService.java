/**
 * 
 */
package com.pay.txncore.service.chargeback;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.model.BouncedOrderVO;
import com.pay.txncore.model.ChargeBackOrder;

/**
 * @author chaoyue
 *
 */
public interface ChargeBackOrderService {

	/**
	 * 添加拒付订单
	 * 
	 * @param chargeBackOrder
	 * @return
	 */
	Long addChargeBackOrder(ChargeBackOrder chargeBackOrder);

	/**
	 * 批量添加拒付订单
	 * 
	 * @param chargeBackOrders
	 */
	void batchAddChargeBackOrder(List<ChargeBackOrder> chargeBackOrders);
	/**
	 * 批量添加拒付订单
	 * 
	 * @param chargeBackOrders
	 */
	void batchAddBouncedOrder(List<BouncedOrderVO> chargeBackOrders);

	/**
	 * 更新
	 * 
	 * @param chargeBackOrder
	 * @return
	 */
	boolean updateChargeBackOrder(ChargeBackOrder chargeBackOrder);

	/**
	 * 
	 * @param chargeBackCondition
	 * @return
	 */
	List<ChargeBackOrder> queryChargeBackOrders(
			ChargeBackOrder chargeBackCondition);

	/**
	 * 
	 * 
	 * @param author
	 * @param tradeDate
	 * @return
	 */
	List<ChargeBackOrder> queryChargeBackOrderByAuthorAndTradeDate(
			String author, String tradeDate);

	/**
	 * 根据订单查询拒付订单
	 * 
	 * @param orderId
	 * @return
	 */
	ChargeBackOrder queryByOrderId(Long orderId);

	/**
	 * 查询拒付订单，分页
	 * 
	 * @param chargeBackCondition
	 * @return
	 */
	List<ChargeBackOrder> queryChargeBackOrders(
			ChargeBackOrder chargeBackCondition, Page page);

	/**
	 * 
	 * @param orderId
	 * @param cpType
	 * @param status
	 * @param chargeBackMsg
	 */
	void doChargeProcess(Long orderId, Integer cpType, Integer status,
			String auditOperator, String auditBackMsg, 
			String appealDbRelativePath,String amountType,Integer accountingFlg);

	/**
	 * 扫描拒付扣款，应扣未扣成功，重新发起扣款
	 */
	void checkBasicAccounting();

	/**
	 * 拒付扣罚款
	 */
	void doFineAccounting();
	/**
	 * 新调单拒付查询
	 * @param bouncedOrderVO
	 * @param page
	 * @return
	 */
	List<BouncedOrderVO> queryBouncedOrders(BouncedOrderVO bouncedOrderVO,
			Page page);
	List<BouncedOrderVO> queryBouncedOrders(Date createDate,String[] amountTypes,Integer accountingFlg);
	List<BouncedOrderVO> queryBouncedOrders(Map map);
	List<BouncedOrderVO> queryBouncedOrdersByOrderId(String orderId);
	void liquidationRnTx(BouncedOrderVO bouncedOrderVO,Boolean flag);

	int countChargeBackByStatus(Map paraMap);
	/**
	 *解冻180天后的不申诉 
	 * @param bouncedOrderVO
	 * 2016年6月1日   mmzhang     add
	 */
	void freeFrozenRnTx(BouncedOrderVO bouncedOrderVO);

	List<BouncedOrderVO> queryBouncedOrders(BouncedOrderVO bouncedOrderVO);

	/**
	 * 更新不申诉
	 * @param bouncedOrderVO
	 * 2016年6月1日   mmzhang     add
	 */
	void alertStatus(BouncedOrderVO bouncedOrderVO);


	/**
	 * 判断 基本户金额是否够扣
	 * @param baseAmount1000
	 * @param partnerId
	 * @param settlementCurrencyCode
	 * @param baseFlag
	 * @author delin 
	 * @date 2016年7月25日10:08:21
	 * @return
	 */
	Boolean baseCheckBalance(BigDecimal baseAmount1000, Long partnerId,
			String settlementCurrencyCode, Boolean baseFlag);
	/**
	 * 拒付罚款
	 * @param map
	 */
	void doFineAccounting(Map<String, Object> map);
	
	/**
	 * 获得拒付订单表主键
	 * @return
	 * 2016年7月25日   mmzhang     add
	 */
	public Long bouncedKeyQuery();
	
}
