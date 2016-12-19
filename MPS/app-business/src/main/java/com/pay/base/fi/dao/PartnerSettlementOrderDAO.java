/**
 * 
 */
package com.pay.base.fi.dao;




import com.pay.inf.dao.BaseDAO;

/**
 * 清算订单DAO
 * @author PengJiangbo
 *
 */
public interface PartnerSettlementOrderDAO extends BaseDAO {

	/**
	 * 根据会员号和交交易币种查询待清算金额
	 * @param memberCode
	 * @param currency
	 * @return
	 */
	Long queryAmountByMemberCodeAndCurrency(Long memberCode, String currency) ;
		
}
