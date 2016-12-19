package com.pay.poss.featuremenu.dao;

import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.featuremenu.model.Advertisement;

public interface AdvertiseDAO extends BaseDAO<Advertisement>{
	
	public boolean updateSortById(Map<String ,Object> paraMap);
	
	
}
