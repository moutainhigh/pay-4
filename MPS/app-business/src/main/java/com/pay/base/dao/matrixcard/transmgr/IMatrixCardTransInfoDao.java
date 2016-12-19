package com.pay.base.dao.matrixcard.transmgr;

import java.util.List;
import java.util.Map;

import com.pay.base.model.matrixcard.MatrixCardTransInfo;
import com.pay.inf.dao.BaseDAO;

/**
 * @author jim_chen
 * @version 2010-9-15
 */
public interface IMatrixCardTransInfoDao extends BaseDAO{
	
	public Class getModelClass();

	/**
	 * 统计MatrixCardTransInfo表的记录
	 * 
	 * @return
	 */
	int countMatrixCardTransInfo();

	/**
	 * @param paramMap
	 * @return
	 */
	public MatrixCardTransInfo selectMatrixCardTransInfoByParamMap(
			Map<String, Object> paramMap);

	/**
	 * @param paramMap
	 * @return
	 */
	List<MatrixCardTransInfo> queryListByParamMap(Map<String, Object> paramMap);

}
