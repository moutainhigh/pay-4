package com.pay.base.dao.matrixcard.impl;

import java.util.List;
import java.util.Map;

import com.pay.base.dao.matrixcard.IMatrixCardDAO;
import com.pay.base.model.matrixcard.MatrixCard;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author jim_chen
 * @version 2010-9-15
 */
public class MatrixCardDAOImpl extends BaseDAOImpl implements IMatrixCardDAO {

	public Class getModelClass() {
		return MatrixCard.class;
	}

	@Override
	public int countMatrixCard() {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("count"));
	}

	@Override
	public MatrixCard selectMatrixCardBySerialNo(Map<String, Object> paramMap) {
		return (MatrixCard) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("selectmatrixcardByMap"), paramMap);
	}

	@Override
	public int countMatrixCardByParamMap(Map<String, Object> paramMap) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("count"), paramMap);
	}

	@Override
	public List<MatrixCard> selectmatrixcardByTransInfoMemberCode(
			Map<String, Object> paramMap) {
		return this.getSqlMapClientTemplate().queryForList(
				namespace.concat("selectmatrixcardByTransInfoMemberCode"),
				paramMap);
	}
}
