package com.pay.base.dao.platformmember.impl;

import java.util.List;

import com.pay.base.dao.platformmember.PlatformMemberDao;
import com.pay.base.model.PlatformMember;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 
 * @author yangjian
 * 平台会员的数据库操作
 * 下午7:37:53
 */
public class PlatformMemberDaoImpl extends BaseDAOImpl<PlatformMember> implements PlatformMemberDao {

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<PlatformMember> findSonMemberInfoByFatherMemberCode(PlatformMember platformMember) {
		return (List<PlatformMember>)getSqlMapClientTemplate().queryForList(
				namespace.concat("getSonInfoByFatherCode"), platformMember);
	}

	@Override
	public PlatformMember findStatusByFaterId(String fater_operator_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updateStatusBySonMemberCode(PlatformMember platformMember) {
		return (Integer)getSqlMapClientTemplate().queryForObject(
				namespace.concat("updatestatus"), platformMember);
	}

	@Override
	public Integer insertRelation(PlatformMember platformMember) {
		// TODO Auto-generated method stub
		return null;
	}
}
