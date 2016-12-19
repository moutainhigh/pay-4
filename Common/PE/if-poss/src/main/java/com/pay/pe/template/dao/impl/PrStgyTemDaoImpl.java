package com.pay.pe.template.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.pe.template.dao.PrStgyTemDAO;
import com.pay.pe.template.dto.PrStgyTemDTO;

public class PrStgyTemDaoImpl extends BaseDAOImpl<PrStgyTemDTO> implements PrStgyTemDAO{

	@Override
	public int deleteBySelective(PrStgyTemDTO prStgyTemDTO) {
		return getSqlMapClientTemplate().delete(namespace.concat("deleteBySelective"), prStgyTemDTO);
	}
	@Override
	public List<PrStgyTemDTO> findByQuery(PrStgyTemDTO prStgyTemDTO) {
		return (List<PrStgyTemDTO>)getSqlMapClientTemplate().queryForList(namespace.concat("findByQuery"),prStgyTemDTO);
	}

	@Override
	public List<PrStgyTemDTO> findByPageQuery(PrStgyTemDTO prStgyTemDTO,
			int start, int end) {
		Map<String,Object> param = new HashMap<String,Object>();
		if(prStgyTemDTO != null){
			if(StringUtils.isNotBlank(prStgyTemDTO.getTemplateName())){
				param.put("templateName", prStgyTemDTO.getTemplateName());
			}
			if(prStgyTemDTO.getPriceStrategyType() != null){
				param.put("priceStrategyType", prStgyTemDTO.getPriceStrategyType());
			}
		}
		param.put("pageStartRow", start);
		param.put("pageEndRow", end);
		
		return (List<PrStgyTemDTO>)getSqlMapClientTemplate().queryForList(namespace.concat("findByPageQuery"),param);
	}

	@Override
	public int findCountByQuery(PrStgyTemDTO prStgyTemDTO) {
		return(Integer) this.getSqlMapClientTemplate().queryForObject(namespace.concat("findCountByQuery"), prStgyTemDTO);
	}

	

}
