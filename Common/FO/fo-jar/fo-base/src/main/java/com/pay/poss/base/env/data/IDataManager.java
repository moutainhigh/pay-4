package com.pay.poss.base.env.data;

import java.util.Map;

import com.pay.poss.base.exception.PossException;

public interface IDataManager {
	public final static String BEAN_ID = "POSS.DATAMANAGER";

	public boolean load() throws PossException;

	public boolean reload(Map<String, String> params, boolean reInstance);

}
