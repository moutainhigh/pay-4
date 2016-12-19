package com.pay.txncore.dao.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;






import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.commons.TradeOrderStatusEnum;
import com.pay.txncore.dao.TradeCountDAO;
import com.pay.txncore.dto.PartnerTradeCountDTO;
import com.pay.txncore.dto.TradeCountDTO;
import com.pay.txncore.dto.TradeCountMap;
import com.pay.txncore.dto.TradeRespCount;

public class TradeCountDAOImpl extends BaseDAOImpl implements TradeCountDAO {

	@Override
	public Collection<TradeCountDTO> getCountDTOs(Map<String, Object> params) {
		List<TradeCountMap> list= super.findByCriteria("getTradeCountMap",params);
		
		if(list!=null&&!list.isEmpty()){
			Map<String,TradeCountDTO> map = new HashMap<String,TradeCountDTO>();

			for(TradeCountMap tcm:list){
				String partnerId = tcm.getPartnerId();
				TradeCountDTO dto = null;
				
				if(map.containsKey(partnerId)){
					dto = map.get(partnerId);
				}else{
					dto = new TradeCountDTO();
					dto.setPartnerId(partnerId);
				}
				
				if(TradeOrderStatusEnum.FAILED.getCode()==tcm.getStatus()){
					dto.setFailAmount(tcm.getAmount());
				}else if(TradeOrderStatusEnum.SUCCESS.getCode()==tcm.getStatus()){
					dto.setSucAmount(tcm.getAmount());
				}else if(TradeOrderStatusEnum.PROCESSING.getCode()==tcm.getStatus()){
					dto.setPayAmount(tcm.getAmount());
				}else if(TradeOrderStatusEnum.COMPLETED.getCode()==tcm.getStatus()){
					dto.setRefundAmount(tcm.getAmount());
				}else if(TradeOrderStatusEnum.WAIT_PAY.getCode()==tcm.getStatus()){
					dto.setUnpayAmount(tcm.getAmount());
				}
				
				dto.setTotal(dto.getTotal()+tcm.getAmount());
				map.put(partnerId, dto);
			}
			
			Collection<TradeCountDTO> list_ = map.values();
			return list_;
		}
		
		return null;
	}

	@Override
	public Collection<TradeRespCount> getTradeRespCounts(Map<String, Object> params) {
		
		List<TradeRespCount> list = super.findByCriteria("getTradeRespCount",params);
		
		if(list!=null&&!list.isEmpty()){
			Map<String,TradeRespCount> map = new HashMap<String, TradeRespCount>();
			
			for(TradeRespCount trc:list){
				String key = trc.getPartnerId()+"-"+trc.getRespCode();
				
				TradeRespCount tmp =null;
				if(map.containsKey(key)){
					tmp = map.get(key);
				}else{
					tmp = new TradeRespCount();
					tmp.setPartnerId(trc.getPartnerId());
					tmp.setRespCode(trc.getRespCode());
					tmp.setRespMsg(trc.getRespMsg());
				}
				
				tmp.setTotal(tmp.getTotal()+trc.getTotal());
				map.put(key, tmp);
			}
			
			Collection<TradeRespCount> list_ = map.values();
			return list_;
		}
		
		return null;
	}

	@Override
	public List<PartnerTradeCountDTO> getPartnerTradeCountDTOs(
			Map<String, Object> params) {
		List<PartnerTradeCountDTO> list = super.findByCriteria("getPartnerTradeCount",params);
		return list;
	}



}
