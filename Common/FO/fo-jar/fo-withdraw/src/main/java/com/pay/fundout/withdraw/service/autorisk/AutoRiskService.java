package com.pay.fundout.withdraw.service.autorisk;

/**
 * 出款自动过风控接口
 * @author meng.li
 *
 */
public interface AutoRiskService {

	/**
	 * 判断出款交易是否符合自动过风控规则
	 * @param orderId 出款订单号
	 * @return
	 */
	public boolean autorisk(String orderId);
	
}
