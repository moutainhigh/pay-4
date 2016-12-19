/**
 *
 */
package com.pay.credit.dao.creditorder;

import java.util.List;
import java.util.Map;

import com.pay.credit.conditon.creditorder.CreditOrderQueryConditon;
import com.pay.credit.model.creditcurrency.PartnerCreditCurrencyCode;
import com.pay.credit.model.creditorder.CreditOrder;
import com.pay.credit.model.creditorder.CreditOrderDetailParam;
import com.pay.credit.model.creditorder.CreditOrderDetailed;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;

/**
 * 融资订单
 *
 * @author zhixiang.deng
 *
 * @date 2015年8月4日
 */
public interface ICreditOrderDao extends BaseDAO<CreditOrder> {


	/**
	 * 根据条件查询融资信息
	 *
	 * @param queryConditon
	 * 			查询条件
	 * @return 融资信息列表
	 */
	List<CreditOrder> queryCreditOrderByConditon(CreditOrderQueryConditon queryConditon);

	List<CreditOrder> queryCreditOrderByConditon(
			CreditOrderQueryConditon queryConditon, Page page);

	CreditOrder queryCreditOrderById(String creditOrderId);
	/***
	 * 授信订单查询明细
	 * @param creditOrderId
	 * @return
	 */
	List<CreditOrderDetailed> queryCreditOrderDetailById(String creditOrderId);
	/**
	 * 更新 授信订单表的状态 
	 * @param creditOrder
	 */
	void updateCreditOrder(CreditOrderDetailed creditOrder);
	/***
	 * 更新订单详情表中的状态
	 * @param creditOrder
	 */
	void updateCreditOrderDetailed(CreditOrderDetailed creditOrder);
	/***
	 * 更新订单详情表的状态 逐条
	 * @param creditOrderDetailed
	 */
	void updateCreditOrderDetail(CreditOrderDetailed creditOrderDetailed);
	/***
	 * 逐条 更新授信订单状态
	 * @param creditOrder
	 */
	void updateCreditOrders(CreditOrder creditOrder);
	
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
	void updateCreditOrderPassCount(CreditOrder creditOrder);

	
	List<PartnerCreditCurrencyCode> queryPartnerCreditCurrency(Long memberCode) ;
}
