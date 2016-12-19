/**
 * 
 */
package com.pay.acc.pedealinfo.service.impl;

import com.pay.acc.pedealinfo.dao.PeDealInfoDAO;
import com.pay.acc.pedealinfo.dto.PeDealInfoDto;
import com.pay.acc.pedealinfo.exception.PeDealInfoException;
import com.pay.acc.pedealinfo.model.PeDealInfo;
import com.pay.acc.pedealinfo.service.PeDealInfoService;
import com.pay.util.BeanConvertUtil;

/**
 * @author Administrator
 *
 */
public class PeDealInfoServiceImpl implements PeDealInfoService {
	private PeDealInfoDAO peDealInfoDAO;

	/* (non-Javadoc)
	 * @see com.pay.acc.pedealinfo.service.PeDealInfoService#createPePealInfo(com.pay.acc.pedealinfo.dto.PeDealInfoDto)
	 */
	@Override
	public Long createPeDealInfo(PeDealInfoDto peDealInfoDto) throws PeDealInfoException {
		if(peDealInfoDto==null){
			throw new PeDealInfoException("输入参数不能为空");
		}
		PeDealInfo peDealInfo =BeanConvertUtil.convert(PeDealInfo.class, peDealInfoDto);
		return (Long) this.peDealInfoDAO.create(peDealInfo);
	}

	/**
	 * @param peDealInfoDAO the peDealInfoDAO to set
	 */
	public void setPeDealInfoDAO(PeDealInfoDAO peDealInfoDAO) {
		this.peDealInfoDAO = peDealInfoDAO;
	}
	

}
