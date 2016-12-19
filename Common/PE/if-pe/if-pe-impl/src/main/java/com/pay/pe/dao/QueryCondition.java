package com.pay.pe.dao;

import java.util.ArrayList;
import java.util.List;

public class QueryCondition {
	private StringBuffer sqlBuffer;
	private List<Object> paramsList;

	public QueryCondition() {
		sqlBuffer = new StringBuffer();
		paramsList = new ArrayList<Object>();
	}

	public QueryCondition(String theSql, Object... theParams) {
		sqlBuffer = new StringBuffer(theSql);
		paramsList = new ArrayList<Object>();
		for (int i = 0; i < theParams.length; i++) {
			paramsList.add(theParams[i]);
		}
	}

	public QueryCondition(StringBuffer theSql, Object... theParams) {
		sqlBuffer = theSql;
		paramsList = new ArrayList<Object>();
		for (int i = 0; i < theParams.length; i++) {
			paramsList.add(theParams[i]);
		}
	}

	public QueryCondition append(String theSql, Object... theParams) {
		sqlBuffer.append(theSql);
		for (int i = 0; i < theParams.length; i++) {
			paramsList.add(theParams[i]);
		}
		return this;
	}

	public QueryCondition append(QueryCondition qc) {
		sqlBuffer.append(qc.getSql());
		paramsList.addAll(qc.paramsList);
		return this;
	}

	public String getSql() {
		return sqlBuffer.toString();
	}

	public Object[] getParams() {
		return paramsList.toArray(new Object[paramsList.size()]);
	}

	public StringBuffer getSqlBuffer() {
		return sqlBuffer;
	}

	public void setSqlBuffer(StringBuffer sqlBuffer) {
		this.sqlBuffer = sqlBuffer;
	}

	public List<Object> getParamsList() {
		return paramsList;
	}

	public void setParamsList(List<Object> params) {
		this.paramsList = params;
	}

}
