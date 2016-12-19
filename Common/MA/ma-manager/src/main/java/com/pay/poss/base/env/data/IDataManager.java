package com.pay.poss.base.env.data;

import com.pay.poss.base.exception.PossException;

public interface IDataManager {
	public final static String BEAN_ID = "POSS.DATAMANAGER";

	public boolean load() throws PossException;

	public boolean reload() throws PossException;

	public boolean reload(String busiCode) throws PossException;

	public boolean registData(String busiCode, Object busiObject);

}
