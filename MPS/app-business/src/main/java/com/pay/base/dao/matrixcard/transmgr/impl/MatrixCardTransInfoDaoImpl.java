package com.pay.base.dao.matrixcard.transmgr.impl;

import java.util.List;
import java.util.Map;

import com.pay.base.dao.matrixcard.transmgr.IMatrixCardTransInfoDao;
import com.pay.base.model.matrixcard.MatrixCardTransInfo;
import com.pay.inf.dao.impl.BaseDAOImpl;


/**
 * @author jim_chen
 * @version 
 * 2010-9-15 
 */
public class MatrixCardTransInfoDaoImpl extends BaseDAOImpl implements
		IMatrixCardTransInfoDao {

	public Class getModelClass() {
		return MatrixCardTransInfo.class;
	}

	@Override
	public int countMatrixCardTransInfo() {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(this.namespace.concat("count"),null);
	}

	@Override
    public MatrixCardTransInfo selectMatrixCardTransInfoByParamMap(Map<String, Object> paramMap) {	    
	    return (MatrixCardTransInfo) this.getSqlMapClientTemplate().queryForObject(namespace.concat("selectMatrixCardTransInfoByMap"),paramMap);
    }

	@Override
    public List<MatrixCardTransInfo> queryListByParamMap(Map<String, Object> paramMap) {	    
	    return this.getSqlMapClientTemplate().queryForList(namespace.concat("selectMatrixCardTransInfoByMap"),paramMap);
    }
	

	

}
