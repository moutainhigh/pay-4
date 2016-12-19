package com.pay.acc.translog.dao.impl;

import java.util.Map;

import com.pay.acc.translog.dao.FlushesLogDAO;
import com.pay.acc.translog.model.FlushesLog;
import com.pay.inf.dao.impl.BaseDAOImpl;

@SuppressWarnings("unchecked")
public class FlushesLogDAOImpl extends BaseDAOImpl<FlushesLog> implements FlushesLogDAO {

	@Override
    public boolean updateFlushesLog(Map<String, Object> paramMap) {
	   return this.getSqlMapClientTemplate().update(namespace.concat("updateFlushesLog"),paramMap)==1?true:false;
	    
    }

	@Override
	public boolean updateFlushesLogByStatus(Map<String,Object> paramMap){
		return this.getSqlMapClientTemplate().update(namespace.concat("updateFlushesLogByStatus"),paramMap)==1?true:false;
	}
	
	@Override
    public int countFlushesStatus(Map<String, Object> paramMap) {
	    Integer obj=(Integer) this.getSqlMapClientTemplate().queryForObject(namespace.concat("countFlushesStatus"),paramMap);
	    if(null==obj){
	    	return 0;
	    }
	    return obj;
    }

	
}
