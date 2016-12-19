package com.pay.txncore.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.dao.ChannelCurrencyDAO;
import com.pay.txncore.dto.ChannelCurrencyDTO;

public class ChannelCurrencyDAOImpl extends BaseDAOImpl<ChannelCurrencyDTO> 
                               implements ChannelCurrencyDAO{

	@Override
	public ChannelCurrencyDTO getChannelCurrencyDTO(Map<String, Object> params) {
		List<ChannelCurrencyDTO> list = super.findByCriteria(params);
		if(list!=null&&!list.isEmpty()){
			String payType=(String) params.get("payType");
			if(list.size()==1){
				 return list.get(0);
			}else if(list.size()==2){
				for(ChannelCurrencyDTO cc:list){
					if("DCC".equals(payType)){
						if(!"*".equals(cc.getCardCurrencyCode())){
							return cc;
						}
					}else if("EDC".equals(payType)){
						if(!"*".equals(cc.getCurrencyCode())){
							return cc;
						}
					}
				}
			}
		}
		return null;
	}
	public ChannelCurrencyDTO findOneMatch(Map<String,Object> params){
		List<ChannelCurrencyDTO> list = super.findByCriteria("findMatchList",params);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

}
