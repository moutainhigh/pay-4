package com.pay.txncore.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.dto.PartnerTradeCountDTO;
import com.pay.txncore.dto.TradeCountDTO;
import com.pay.txncore.dto.TradeRespCount;

public interface TradeCountDAO extends BaseDAO{
	Collection<TradeCountDTO> getCountDTOs(Map<String,Object> params);
	Collection<TradeRespCount> getTradeRespCounts(Map<String,Object> params);
	List<PartnerTradeCountDTO> getPartnerTradeCountDTOs(Map<String,Object> params);
}
