package com.pay.acc.member.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.acc.member.dao.IdcVerifyBaseDAO;
import com.pay.acc.member.dto.IdcVerifyDto;
import com.pay.acc.member.model.IdcVerifyBase;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.util.DESUtil;

@SuppressWarnings("unchecked")
public class IdcVerifyBaseDAOImpl extends BaseDAOImpl implements
		IdcVerifyBaseDAO {
	//
	private final int num = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ma.dao.idcverifybase.IdcVerifyBaseDAO#findIdcVerifyDtoList
	 * (java.lang.String, java.util.Map)
	 */
	public boolean findIdcVerifyDtoList(String sqlId,
			Map<String, Object> paraMap) {
		boolean bool = false;
		Long count = (Long) getSqlMapClientTemplate().queryForObject(
				this.namespace.concat(sqlId), paraMap);
		if (count != null && count >= num) {
			bool = true;
		}
		return bool;
	}

	public IdcVerifyDto findIdcVerifyDto(String sqlId,
			Map<String, Object> paraMap) {

		return null;
	}

	@Override
	public IdcVerifyBase QueryMemberVerifyByMemberCode(Long memberCode) {
		Map paraMap = new HashMap();
		paraMap.put("memberCode", memberCode);
		IdcVerifyBase idcVerifyDto = (IdcVerifyBase) getSqlMapClientTemplate()
				.queryForObject(
						this.namespace.concat("querymemberverifybymembercode"),
						paraMap);
		return idcVerifyDto;
	}

	public IdcVerifyBase getIdcVerifyBaseById(Long id) {
		return (IdcVerifyBase) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("queryIdcById"), id);
	}

	public int editIdcVerifyBase(IdcVerifyBase idcVerifyBase) {
		return this.getSqlMapClientTemplate().update(
				namespace.concat("updateIdc"), idcVerifyBase);
	}

	public List<IdcVerifyBase> getVerifyList(Long memberCode) {
		return this.getSqlMapClientTemplate().queryForList(
				namespace.concat("getVerifyListByMemberCode"), memberCode);
	}

	@Override
	public IdcVerifyBase querylastIdcVerify(String idCardNO, String name) {

		IdcVerifyBase param = new IdcVerifyBase();
		param.setName(name);
		param.setPaperNo(DESUtil.encrypt(idCardNO));

		return (IdcVerifyBase) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("querylastIdcVerify"), param);
	}

}
