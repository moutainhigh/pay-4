package com.pay.poss.base.env.data;

public interface IDataService {
	public final static String BEAN_ID = "POSS.DATASERVICE";

	public Object getData(String busiCode);
}
