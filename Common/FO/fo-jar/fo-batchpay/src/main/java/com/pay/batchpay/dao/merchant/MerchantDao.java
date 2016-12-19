/**
 * 
 */
package com.pay.batchpay.dao.merchant;

/**
 * 商户DAO
 * @author PengJiangbo
 *
 */
public interface MerchantDao {

	/**
	 * 根据会员号获取商户名称
	 * @param memberCode
	 * @return
	 */
	public String findMerchantNameByMemberCode(long memberCode) ;
	
}
