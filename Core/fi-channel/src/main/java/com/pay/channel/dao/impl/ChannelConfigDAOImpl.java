/**
 * 
 */
package com.pay.channel.dao.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.channel.dao.ChannelConfigDAO;
import com.pay.channel.model.ChannelConfig;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.util.StringUtil;

/**
 * @author chaoyue
 *
 */
public class ChannelConfigDAOImpl extends BaseDAOImpl<ChannelConfig> implements
		ChannelConfigDAO {

	@Override
	//change method name by Mack for ABC and CTV share the same Merchantcode
	/*public ChannelConfig findByMerchantCode(String orgMerchantCode) {*/
	public ChannelConfig findByMerchantCodeOrgCode(String orgMerchantCode,String orgCode) {
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("orgMerchantCode", orgMerchantCode);
		paraMap.put("orgCode", orgCode);
		List<ChannelConfig> channelConfigs = getSqlMapClientTemplate().queryForList("channelConfig.findObjectByCriteria", paraMap);
		return channelConfigs!=null&&!channelConfigs.isEmpty()?channelConfigs.get(0):null;
	}

	@Override
	public ChannelConfig findByMerchantCode(String orgCode,
			String currencyCode, String mcc,String vom) {
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("orgCode", orgCode);
		paraMap.put("currencyCode", currencyCode);
		paraMap.put("mcc", mcc);
		
		List<ChannelConfig> list = super.findByCriteria(paraMap);
		
		if(StringUtil.isEmpty(vom)){
			if(list!=null&&list.size()>0)
				return list.get(0);
		}else{
			if("SGD".equals(currencyCode)){
				for(ChannelConfig cc:list){
					if(cc.getOrgMerchantCode().endsWith(vom.substring(0, 1))){
						return cc;
					}
				}
			}else{
				if(list!=null&&list.size()>0)
					return list.get(0);
			}
		}
		
		return null;
	}
	
	@Override
	public ChannelConfig findByMerchantCode(String orgCode, String siteId) {
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("orgCode", orgCode);
		paraMap.put("supportWebsite", siteId);
		List<ChannelConfig> list = super.findByCriteria(paraMap);
		return list!=null&&!list.isEmpty()?list.get(0):null;
	}

}
