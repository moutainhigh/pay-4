package com.pay.poss.base.env.data;

import org.springframework.core.Ordered;

public interface IDataRepository extends Ordered, Comparable<IDataRepository> {
	public final static String BEAN_ID = "POSS.DATAREPOSITORY";

	public Object getService(String busiCode);

	public void addService(String busiCode, Object busiService);

}
