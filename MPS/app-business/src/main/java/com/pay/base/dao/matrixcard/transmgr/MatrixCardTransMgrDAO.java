package com.pay.base.dao.matrixcard.transmgr;

import java.util.List;
import java.util.Map;

import com.pay.base.model.matrixcard.MatrixCardTransMgr;
import com.pay.inf.dao.BaseDAO;

/**
 * 口令卡事务表DB操作
 * 
 * @author jim_chen
 * @version 2010-9-15
 */
public interface MatrixCardTransMgrDAO extends BaseDAO {

	Class getModelClass() ;
	
	/**
	 * 统计口令卡事务表的记录
	 * 
	 * @param paramMap
	 * @return
	 */
	int countMatrixCardTransMgr(Map<String, Object> paramMap);

	/**
	 * @param paramMap
	 * @return
	 */
	MatrixCardTransMgr selectMatrixCardTransMgrByPamarMap(
			Map<String, Object> paramMap);

	/**
	 * @param paramMap
	 * @return
	 */
	List<MatrixCardTransMgr> selectMatrixCardTransMgrList(
			Map<String, Object> paramMap);

}
