package com.pay.poss.base.env.data.impl;

import java.util.HashMap;
import java.util.Map;

import com.pay.poss.base.env.data.IDataRepository;

public class DefaultDataRepositoryImpl implements IDataRepository {
	private Map<String,Object> services=new HashMap<String,Object>();
	
	@Override
	public int getOrder() {
		return 5;
	}

	@Override
	public int compareTo(IDataRepository target) {
		return this.getOrder() - target.getOrder();
	}

	@Override
	public Object getService(String busiCode) {
		return services.get(busiCode);
	}

	@Override
	public void addService(String busiCode, Object busiService) {
		services.put(busiCode, busiService);
	}
	

}
