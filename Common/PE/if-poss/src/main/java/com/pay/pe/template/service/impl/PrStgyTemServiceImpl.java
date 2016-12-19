package com.pay.pe.template.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.exception.AppException;
import com.pay.inf.exception.AppUnTxException;
import com.pay.pe.template.dao.PrStgyTemDAO;
import com.pay.pe.template.dto.PrStgyTemDTO;
import com.pay.pe.template.service.PrStgyTemService;

public class PrStgyTemServiceImpl implements PrStgyTemService{
	private Log log = LogFactory.getLog(PrStgyTemServiceImpl.class);
	private PrStgyTemDAO prStgyTemDAO;
	public PrStgyTemDAO getPrStgyTemDAO() {
		return prStgyTemDAO;
	}

	public void setPrStgyTemDAO(PrStgyTemDAO prStgyTemDAO) {
		this.prStgyTemDAO = prStgyTemDAO;
	}

	@Override
	public void createPrStgyTem(PrStgyTemDTO prStgyTemDTO) throws AppException {
		try {
			prStgyTemDTO.setCreateDate(new Date());
			prStgyTemDTO.setUpdateDate(new Date());
			prStgyTemDAO.create(prStgyTemDTO);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new AppException(e.getMessage(),e);
		}
		
	}

	@Override
	public PrStgyTemDTO findTemById(Long id) {
		return prStgyTemDAO.findById(id);
	}
	@Override
	public boolean deleteTemById(Long id) {
		return prStgyTemDAO.delete(id);
	}
	@Override
	public void deletePrStgyTem(PrStgyTemDTO prStgyTemDTO) throws AppException{
		try {
			prStgyTemDAO.deleteBySelective(prStgyTemDTO);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new AppException(e.getMessage(),e);
		}
	}

	@Override
	public void updatePrStgyTem(PrStgyTemDTO prStgyTemDTO) throws AppException {
		try {
			prStgyTemDTO.setUpdateDate(new Date());
			prStgyTemDAO.update(prStgyTemDTO);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new AppException(e.getMessage(),e);
		}
	}

	@Override
	public List<PrStgyTemDTO> query(PrStgyTemDTO prStgyTemDTO) throws AppUnTxException {
		try {
			return prStgyTemDAO.findByQuery(prStgyTemDTO);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new AppUnTxException(e.getMessage(),e);
		}
	}

	@Override
	public List<PrStgyTemDTO> queryByPage(PrStgyTemDTO prStgyTemDTO, int start,
			int end) {
		return prStgyTemDAO.findByPageQuery(prStgyTemDTO, start, end);
	}

	@Override
	public int queryCount(PrStgyTemDTO prStgyTemDTO) {
		return prStgyTemDAO.findCountByQuery(prStgyTemDTO);
	}

	


}
