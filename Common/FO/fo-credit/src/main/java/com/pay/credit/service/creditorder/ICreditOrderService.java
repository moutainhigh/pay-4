/**
 *
 */
package com.pay.credit.service.creditorder;

import java.util.List;

import com.pay.credit.conditon.creditorder.CreditOrderQueryConditon;
import com.pay.credit.dto.creditorder.CreditOrderDTO;

/**
 * 融资订单
 *
 * @author zhixiang.deng
 *
 * @date 2015年8月4日
 */
public interface ICreditOrderService {


	/**
	 * 保存融资订单
	 *
	 * @param creditOrderDto
	 */
	void saveCreditOrderDto(CreditOrderDTO creditOrderDto);


	/**
	 * 条件查询融资订单
	 *
	 * @param queryConditon
	 * @return
	 */
	List<CreditOrderDTO> queryCreditOrderList(CreditOrderQueryConditon queryConditon);

}
