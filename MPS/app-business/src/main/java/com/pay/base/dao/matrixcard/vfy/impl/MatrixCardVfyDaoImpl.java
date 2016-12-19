package com.pay.base.dao.matrixcard.vfy.impl;

import java.util.HashMap;
import java.util.Map;

import com.pay.base.dao.matrixcard.vfy.IMatrixCardVfyDao;
import com.pay.base.model.matrixcard.MatrixCardVfy;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author jim_chen
 * @version 2010-9-15
 */
public class MatrixCardVfyDaoImpl extends BaseDAOImpl implements
		IMatrixCardVfyDao {

	public Class getModelClass() {
		return MatrixCardVfy.class;
	}

	@Override
	public int countMatrixCardVfy() {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("count"));
	}

	@Override
	public MatrixCardVfy getMatrixCardVfyByToken(String token) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("token", token);
		return (MatrixCardVfy) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("selectMatrixcardvfyByMap"), paramMap);
	}

}
