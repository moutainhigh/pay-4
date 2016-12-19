package com.pay.base.dao.matrixcard.vfy;

import com.pay.base.model.matrixcard.MatrixCardVfy;
import com.pay.inf.dao.BaseDAO;

/**
 * @author jim_chen
 * @version 2010-9-15
 */
public interface IMatrixCardVfyDao extends BaseDAO{

	Class getModelClass();
	
	/**
	 * 统计MatrixCardVfy记录
	 * 
	 * @return
	 */
	int countMatrixCardVfy();

	/**
	 * 根据token查询
	 * 
	 * @param token
	 * @return
	 */
	MatrixCardVfy getMatrixCardVfyByToken(String token);

}
