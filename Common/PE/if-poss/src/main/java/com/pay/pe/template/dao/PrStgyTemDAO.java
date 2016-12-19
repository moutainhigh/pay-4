package com.pay.pe.template.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.pe.template.dto.PrStgyTemDTO;

public interface PrStgyTemDAO extends BaseDAO<PrStgyTemDTO>{
	/**
	 * 删除配置信息
	 * @param conf
	 * @return
	 */
	public int deleteBySelective(PrStgyTemDTO prStgyTemDTO);
	/**
	 * 综合查询
	 * @param prStgyTemDTO
	 * @return
	 */
	public List<PrStgyTemDTO> findByQuery(PrStgyTemDTO prStgyTemDTO);
	
	/**
	 * 分页查询
	 * @param prStgyTemDTO
	 * @param start
	 * @param end
	 * @return
	 */
	public List<PrStgyTemDTO> findByPageQuery(PrStgyTemDTO prStgyTemDTO,int start,int end);
	
	
	/**
	 * 获得条件查询的记录数
	 * @param prStgyTemDTO 
	 * @return
	 */
	public int findCountByQuery(PrStgyTemDTO prStgyTemDTO);
}
