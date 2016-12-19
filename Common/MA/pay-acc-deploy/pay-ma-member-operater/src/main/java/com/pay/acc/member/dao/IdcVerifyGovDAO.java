package com.pay.acc.member.dao;

import com.pay.acc.member.model.IdcVerifyGov;
import com.pay.inf.dao.BaseDAO;

public interface IdcVerifyGovDAO extends BaseDAO<IdcVerifyGov> {

	/**
	 * 
	 * @author Sunny Ying
	 * @param idcVerifyGov
	 * @throw null
	 * @return int
	 */
	public int editIdcVerifyGov(IdcVerifyGov idcVerifyGov);

	/**
	 * 
	 * @author Sunny Ying
	 * @param idcVerifyBaseId
	 * @throw null
	 * @return IdcVerifyGov
	 */
	public IdcVerifyGov getIdcVerifyGov(Long idcVerifyBaseId);
}
