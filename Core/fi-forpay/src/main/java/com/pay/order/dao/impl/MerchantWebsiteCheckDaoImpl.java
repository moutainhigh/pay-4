package com.pay.order.dao.impl;

import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.order.dao.MerchantWebsiteCheckDao;

public class MerchantWebsiteCheckDaoImpl extends BaseDAOImpl  implements MerchantWebsiteCheckDao{

	@Override
	public Boolean updateWebsiteStatus(Map map) {
		boolean update = this.update(map);
		return update;
	}

}
