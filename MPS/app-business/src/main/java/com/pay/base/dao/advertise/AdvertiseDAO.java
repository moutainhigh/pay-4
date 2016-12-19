package com.pay.base.dao.advertise;

import java.util.Map;

import com.pay.inf.dao.BaseDAO;

public interface AdvertiseDAO extends BaseDAO {

	public boolean updateSortById(Map<String, Object> paraMap);

}
