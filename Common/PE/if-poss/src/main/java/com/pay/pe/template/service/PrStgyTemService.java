package com.pay.pe.template.service;

import java.util.List;

import com.pay.inf.exception.AppException;
import com.pay.inf.exception.AppUnTxException;
import com.pay.pe.template.dto.PrStgyTemDTO;

public interface PrStgyTemService {
	/**
	 * 新建一个價格策略模板
	 * @param prStgyTemDTO
	 */
	public void createPrStgyTem(PrStgyTemDTO prStgyTemDTO) throws AppException;
	/**
	 * 删除價格策略模板
	 * @param id
	 */
	public boolean deleteTemById(Long id);
	/**
	 * 删除價格策略模板
	 * @param prStgyTemDTO
	 */
	public void deletePrStgyTem(PrStgyTemDTO prStgyTemDTO) throws AppException;
	/**
	 * 更新一个價格策略模板
	 * @param key
	 * @param value
	 */
	public void updatePrStgyTem(PrStgyTemDTO prStgyTemDTO) throws AppException;
	/**
	 * 通過id查找價格策略模板
	 * @param key
	 * @param value
	 */
	public PrStgyTemDTO findTemById(Long id) ;
	/**
	 * 综合条件查询
	 * @param PrStgyTemDTO
	 * @return
	 */
	public List<PrStgyTemDTO> query(PrStgyTemDTO prStgyTemDTO) throws AppUnTxException;
	/**
	 * 分页查询
	 * @param prStgyTemDTO 条件
	 * @param start
	 * @param end
	 * @return
	 */
	public List<PrStgyTemDTO> queryByPage(PrStgyTemDTO prStgyTemDTO ,int start ,int end);
	
	/**
	 * 根据条件查询获得记录数
	 * @param prStgyTemDTO
	 * @return
	 */
	public int queryCount(PrStgyTemDTO prStgyTemDTO);
}
