package com.pay.rm.blacklistcheck.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.rm.blacklistcheck.dao.BlackChecklistDAO;
import com.pay.rm.blacklistcheck.dao.MonitorTradelistDAO;
import com.pay.rm.blacklistcheck.dto.BlackChecklistDTO;
import com.pay.rm.blacklistcheck.dto.MonitorTradeDTO;

public class MonitorTradelistDAOImpl extends BaseDAOImpl<MonitorTradeDTO> implements MonitorTradelistDAO{

	@Override
	public Long addMonitorTradelist(MonitorTradeDTO dto, long monitorType) {
		Map param =new HashMap();
		param.put("memberCode", dto.getPartnerId());
		
		Map tradeParm = new HashMap();
		tradeParm.put("tradeOrderNo", dto.getTradeOrderNo());
		tradeParm.put("monitorType", monitorType);
		
		try {
			List traderesult =super.getSqlMapClient().queryForList("ma-monitorTradelist.findbytradeno",tradeParm );
			if(null!=traderesult&&traderesult.size()!=0){
				
				return new Long(0);
			}
			List result =super.getSqlMapClient().queryForList("ma-monitorTradelist.findmembypid",param);
			if(result!=null){
				Map memberinfo =(Map) result.get(0);
				String merchantCode=memberinfo.get("merchantCode").toString();
				String merchantName=memberinfo.get("merchantName").toString();
				dto.setMerchantId(merchantCode);
				dto.setMerchantName(merchantName);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return new Long(0);
		}
		return (Long) super.create("insert", dto);
	}

	

	

}
