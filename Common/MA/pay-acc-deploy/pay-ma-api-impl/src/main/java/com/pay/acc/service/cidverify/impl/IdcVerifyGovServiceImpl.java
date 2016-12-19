package com.pay.acc.service.cidverify.impl;

import com.pay.acc.member.dao.IdcVerifyGovDAO;
import com.pay.acc.member.dto.IdcVerifyGovDto;
import com.pay.acc.member.exception.MemberException;
import com.pay.acc.member.exception.MemberUnknowException;
import com.pay.acc.member.model.IdcVerifyGov;
import com.pay.acc.service.cidverify.IdcVerifyGovService;
import com.pay.inf.dao.BaseDAO;
import com.pay.util.BeanConvertUtil;

/**
 * @author lei.jiangl
 * @version
 * @data 2010-10-2 下午06:34:35
 */
public class IdcVerifyGovServiceImpl implements IdcVerifyGovService {

	private BaseDAO mainDao;
	private IdcVerifyGovDAO idcVerifyGovDAO;

	public void setMainDao(BaseDAO mainDao) {
		this.mainDao = mainDao;
	}

	public IdcVerifyGovDto saveGov(IdcVerifyGovDto idcVerifyGov) {
		Long id = (Long) mainDao.create(BeanConvertUtil.convert(
				IdcVerifyGov.class, idcVerifyGov));
		idcVerifyGov.setId(id);
		return idcVerifyGov;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.ma.service.idcverifygov.IdcVerifyGovService#insertIdcVerifyBase
	 * (com.pay.ma.model.IdcVerifyGov)
	 */
	public long insertIdcVerifyGov(IdcVerifyGovDto idcVerifyGov)
			throws MemberException, MemberUnknowException {
		// return idcVerifyGovDAO.create(idcVerifyGov);
		if (null == idcVerifyGov) {
			throw new MemberException("IdcVerifyGov参数输入 " + idcVerifyGov
					+ " 不正确");
		}
		long id = 0L;
		try {
			id = (Long) idcVerifyGovDAO.create(BeanConvertUtil.convert(
					IdcVerifyGov.class, idcVerifyGov));
		} catch (Exception e) {
			throw new MemberUnknowException(e);
		}
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.ma.service.idcverifygov.IdcVerifyGovService#updateIdcVerifyBase
	 * (com.pay.ma.model.IdcVerifyGov)
	 */
	public boolean updateIdcVerifyGov(IdcVerifyGovDto idcVerifyGov)
			throws MemberException, MemberUnknowException {
		// return idcVerifyGovDAO.update(idcVerifyGov);
		if (null == idcVerifyGov) {
			throw new MemberException("IdcVerifyGov参数输入 " + idcVerifyGov
					+ " 不正确");
		}
		boolean flag = false;
		try {
			flag = idcVerifyGovDAO.update(BeanConvertUtil.convert(
					IdcVerifyGov.class, idcVerifyGov));
		} catch (Exception e) {
			throw new MemberUnknowException(e);
		}
		return flag;
	}

	public IdcVerifyGovDAO getIdcVerifyGovDAO() {
		return idcVerifyGovDAO;
	}

	public void setIdcVerifyGovDAO(IdcVerifyGovDAO idcVerifyGovDAO) {
		this.idcVerifyGovDAO = idcVerifyGovDAO;
	}

}
