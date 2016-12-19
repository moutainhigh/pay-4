package com.pay.poss.dao.ordercenter.fundin.paygateway;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;
import com.pay.poss.service.ordercenter.dto.relationorder.OrderRelationDTO;

/**
 * <p>
 * </p>
 * 
 * @author Andy.Zhao
 * @since 2010-11-10
 * @see
 */
public interface OrderCenterPayGatewayDao extends BaseDAO {
	/**
	 * 查询网关付款结果分页列表
	 * 
	 * @param page
	 * @param dto
	 * @return
	 */
	Page<OrderCenterResultDTO> queryPayGatewayResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto);

	/**
	 * 网关付款订单详情
	 * 
	 * @param id
	 * @return OrderDetailDTO
	 */
	OrderDetailDTO queryOrderDetail(Long id);

	/**
	 * 查询网关付款关联订单列表
	 * 
	 * @param dto
	 * @return
	 */
	List<OrderRelationDTO> queryRefundRelationList(OrderCenterQueryDTO dto);

	/**
	 * 查询支付扩展信息表
	 * 
	 * @param paymentOrderNo
	 * @return map MAP内容如下 cardNo 卡号 cardType 卡种
	 *         注：卡种的值是ConsumeCardsEnum枚举里面的prefix amountType 实际面值，单位厘
	 *         surplusAmount 结余金额，单位厘 convertFee 结转费用，单位厘 otherNo 结转订单号
	 */
	Map<String, Object> queryPaymentExpandInfo(Long paymentOrderNo);
}
