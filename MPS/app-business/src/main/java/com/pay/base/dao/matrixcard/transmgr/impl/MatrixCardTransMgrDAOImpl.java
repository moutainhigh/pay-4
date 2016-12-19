package com.pay.base.dao.matrixcard.transmgr.impl;

import java.util.List;
import java.util.Map;

import com.pay.base.dao.matrixcard.transmgr.MatrixCardTransMgrDAO;
import com.pay.base.model.matrixcard.MatrixCardTransMgr;
import com.pay.inf.dao.impl.BaseDAOImpl;



/**
 * @author jim_chen
 * @version 
 * 2010-9-15 
 */
public class MatrixCardTransMgrDAOImpl  extends BaseDAOImpl implements  MatrixCardTransMgrDAO {

	public Class getModelClass() {		
		return MatrixCardTransMgr.class;
	}

	@Override
	public int countMatrixCardTransMgr(Map<String, Object> paramMap) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(this.namespace.concat("count"),paramMap);
	}

	@Override
    public MatrixCardTransMgr selectMatrixCardTransMgrByPamarMap(Map<String, Object> paramMap) {	   
	    return (MatrixCardTransMgr) this.getSqlMapClientTemplate().queryForObject(namespace.concat("selectMatrixCardTransMgrByMap"),paramMap);
    }

    @Override
    public List<MatrixCardTransMgr> selectMatrixCardTransMgrList(Map<String, Object> paramMap) {
	    return this.getSqlMapClientTemplate().queryForList(namespace.concat("selectMatrixCardTransMgrByMap"),paramMap);
    }

}
