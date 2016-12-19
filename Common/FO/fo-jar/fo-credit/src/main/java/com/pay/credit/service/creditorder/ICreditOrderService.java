/**
 *
 */
package com.pay.credit.service.creditorder;

import java.util.List;
import java.util.Map;

import com.pay.credit.conditon.creditorder.CreditOrderQueryConditon;
import com.pay.credit.dto.creditorder.CreditOrderDTO;
import com.pay.credit.model.creditcurrency.PartnerCreditCurrencyCode;
import com.pay.credit.model.creditorder.CreditOrder;
import com.pay.credit.model.creditorder.CreditOrderDetailParam;
import com.pay.credit.model.creditorder.CreditOrderDetailed;
import com.pay.inf.dao.Page;

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
	Long saveCreditOrderDto(CreditOrderDTO creditOrderDto);


	/**
	 * 条件查询融资订单
	 *
	 * @param queryConditon
	 * @return
	 */
	List<CreditOrderDTO> queryCreditOrderList(CreditOrderQueryConditon queryConditon);


	List<CreditOrderDTO> queryCreditOrderList(
			CreditOrderQueryConditon conditon, Page page);


	CreditOrder queryCreditOrderById(String creditOrderId);
	/***
	 * 查询授信订单明细
	 * @param creditOrderId
	 * @return
	 */
	List<CreditOrderDetailed> queryCreditOrderDetailById(String creditOrderId);

	/***审核*****/
	void subCreditCheck(CreditOrderDetailed creditOrder);

	/***逐条审核***/
	void subCreditCheck(List<CreditOrderDetailed> list);
	
	/**
	 * 融资订单详情保存
	 * @param list
	 */
	boolean saveCreditOrderDetail(List<CreditOrderDetailParam> list) ;
	/**
	 * 融资订单详情保存
	 * @param hMap
	 */
	void saveCreditOrderDetail2(Map<String, Object> hMap) ;
	/**
	 * 查询商户的授信币种
	 * @param memberCode
	 * @return
	 */
	List<PartnerCreditCurrencyCode> queryPartnerCreditCurrency(Long memberCode) ;

	/**
	 * 更新及格笔数
	 * @param passCount
	 */
	void updateCreditOrderPassCount(CreditOrder creditOrder);

}
