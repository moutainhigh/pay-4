package com.pay.txncore.dao;

import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.dto.ChannelCurrencyDTO;

public interface ChannelCurrencyDAO extends BaseDAO<ChannelCurrencyDTO>{
	ChannelCurrencyDTO getChannelCurrencyDTO(Map<String,Object> params);
	ChannelCurrencyDTO findOneMatch(Map<String,Object> params);
}
