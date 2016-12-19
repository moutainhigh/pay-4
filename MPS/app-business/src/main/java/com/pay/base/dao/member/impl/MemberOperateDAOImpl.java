package com.pay.base.dao.member.impl;

import java.util.Date;
import java.util.HashMap;

import com.pay.base.dao.member.MemberOperateDAO;
import com.pay.base.model.MemberOperate;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class MemberOperateDAOImpl extends BaseDAOImpl implements
		MemberOperateDAO {

	private final static Long memberType = 2L;

	/***************************************************************
	 * 描 述： 方法名: findMemberOperateByOperatorId (MemberOperateDAOImpl)
	 * (non-Javadoc)
	 * 
	 * @see com.pay.base.dao.member.MemberOperateDAO#findMemberOperateByOperatorId(java.lang.Long)
	 * @param operatorId
	 * @return
	 */
	@Override
	public MemberOperate findMemberOperateByOperatorId(Long operatorId,
			Long memberCode) {
		if (operatorId != null) {
			HashMap<Object, Object> map = new HashMap<Object, Object>(3);
			map.put("operatorId", String.valueOf(operatorId));
			map.put("memberType", memberType);
			map.put("memberCode", memberCode);
			return (MemberOperate) this.getSqlMapClientTemplate()
					.queryForObject(
							getNamespace().concat("selectMemberOperateByMap"),
							map);
		}
		return null;
	}

	/***************************************************************
	 * 描 述： 方法名: createMemberOperate (MemberOperateDAOImpl) (non-Javadoc)
	 * 
	 * @see com.pay.base.dao.member.MemberOperateDAO#createMemberOperate(com.pay.base.model.MemberOperate)
	 * @param mo
	 * @return
	 */
	@Override
	public Long createMemberOperate(MemberOperate mo) {
		mo.setType(0L);
		mo.setMemberType(memberType);
		return (Long) super.create(mo);
	}

	/***************************************************************
	 * 描 述： 方法名: updateMemberOperate (MemberOperateDAOImpl) (non-Javadoc)
	 * 
	 * @see com.pay.base.dao.member.MemberOperateDAO#updateMemberOperate(java.lang.Long)
	 * @param id
	 * @return
	 */
	public int updateMemberOperate(Long id) {
		if (id != null) {
			MemberOperate mo = new MemberOperate();
			mo.setId(id);
			mo.setLastLoginTime(new Date());
			return this.getSqlMapClientTemplate().update(
					getNamespace().concat("update"), mo);
		}
		return 0;
	}

}
