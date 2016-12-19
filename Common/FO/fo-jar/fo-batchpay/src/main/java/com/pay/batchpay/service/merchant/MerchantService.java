/**
 * 
 */
package com.pay.batchpay.service.merchant;

/**
 * @author PengJiangbo
 *
 */
public interface MerchantService {

	/**
	 * 根据会员号获取商户名称
	 * @param memberCode
	 * @return
	 */
	public String findMerchantNameByMemberCode(long memberCode) ;
	
}
