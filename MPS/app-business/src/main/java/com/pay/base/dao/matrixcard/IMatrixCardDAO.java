package com.pay.base.dao.matrixcard;

import java.util.List;
import java.util.Map;

import com.pay.base.model.matrixcard.MatrixCard;
import com.pay.inf.dao.BaseDAO;

/**
 * @author jim_chen
 * @version 2010-9-15
 */
public interface IMatrixCardDAO extends BaseDAO {

	public Class getModelClass();

	/**
	 * 统计口令表纪录
	 * 
	 * @return
	 */
	public int countMatrixCard();

	/**
	 * 根据序列号查询
	 * 
	 * @param SerialNo
	 * @return
	 */
	public MatrixCard selectMatrixCardBySerialNo(Map<String, Object> paramMap);

	/**
	 * 
	 * @param paramMap
	 * @return
	 */
	int countMatrixCardByParamMap(Map<String, Object> paramMap);

	/**
	 * @param paramMap
	 * @return
	 */
	List<MatrixCard> selectmatrixcardByTransInfoMemberCode(
			Map<String, Object> paramMap);

}
