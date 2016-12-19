package com.pay.acc.member.dao.impl;

import com.pay.acc.member.dao.IdcVerifyGovDAO;
import com.pay.acc.member.model.IdcVerifyGov;
import com.pay.inf.dao.impl.BaseDAOImpl;

@SuppressWarnings("unchecked")
public class IdcVerifyGovDAOImpl extends BaseDAOImpl<IdcVerifyGov> implements
		IdcVerifyGovDAO {

	public int editIdcVerifyGov(IdcVerifyGov idcVerifyGov) {
		return this.getSqlMapClientTemplate().update(
				namespace.concat("updateGov"), idcVerifyGov);
	}

	public IdcVerifyGov getIdcVerifyGov(Long idcVerifyBaseId) {
		return (IdcVerifyGov) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("queryGov"), idcVerifyBaseId);
	}
}
