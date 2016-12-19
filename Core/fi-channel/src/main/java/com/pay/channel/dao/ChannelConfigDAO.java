/**
 * 
 */
package com.pay.channel.dao;

import com.pay.channel.model.ChannelConfig;
import com.pay.inf.dao.BaseDAO;

/**
 * @author chaoyue
 *
 */
public interface ChannelConfigDAO extends BaseDAO<ChannelConfig> {

	/**
	 * 
	 * @param merchantCode
	 * @param orgCode
	 * @return
	 */
	//change method name by Mack for ABC and CTV share the same Merchantcode
	/*public ChannelConfig findByMerchantCode(String orgMerchantCode) {*/
	public ChannelConfig findByMerchantCodeOrgCode(String orgMerchantCode,String orgCode);

	ChannelConfig findByMerchantCode(String orgCode, String currencyCode,
			String mcc,String vom);
	
	ChannelConfig findByMerchantCode(String orgCode, String siteId);
}
