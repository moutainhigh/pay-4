package com.pay.acc.member.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.acc.member.dao.MemberDAO;
import com.pay.acc.member.model.Member;
import com.pay.acc.member.model.MemberMessageBean;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.util.StringUtil;


/**
 * 会员基本信息操作
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
public class MemberDAOImpl extends BaseDAOImpl<Member> implements MemberDAO{
	
	public MemberMessageBean findMemberMessageBeanByMap(String sqlId, Map<String ,Object> paraMap){
		return (MemberMessageBean) getSqlMapClientTemplate().queryForObject(namespace.concat(sqlId), paraMap );
	}

	/* (non-Javadoc)
	 * @see com.pay.acc.member.dao.MemberDAO#queryMemberWithMemberCode(java.lang.Long)
	 */
	@Override
	public Member queryMemberWithMemberCode(Long memberCode) {
		
		return (Member) this.getSqlMapClientTemplate().queryForObject(this.namespace.concat("queryMemberByMemberCode"), memberCode);
	}

	
	/* (non-Javadoc)
	 * @see com.pay.acc.member.dao.MemberDAO#queryMemberWithSsoUserId(java.lang.String)
	 */
	public Member queryMemberWithSsoUserId(String ssoUserId) {
		return (Member) this.getSqlMapClientTemplate().queryForObject(this.namespace.concat("queryMemberBySsoUserId"), ssoUserId);
	}

	@Override
    public Member queryMemberByLoginName(String loginName) {
		if(loginName!=null) loginName=loginName.toLowerCase();
	    return (Member) this.getSqlMapClientTemplate().queryForObject(this.namespace.concat("queryMemberByLoginName"),loginName);
    }

	@Override
	public int unlockMember(Map<String,Object> paramMap) {
		int r = this.getSqlMapClientTemplate().update(this.namespace.concat("unlockMember"), paramMap);
		return r;
	}

	@Override
	public boolean lockMember(Map<String,Object> paramMap) {
		return this.getSqlMapClientTemplate().update(this.namespace.concat("lockMember"), paramMap)>0;
	}
	
	@Override
	public boolean active(Long memberCode) {
		return this.getSqlMapClientTemplate().update(this.namespace.concat("active"), memberCode)>0;
	}
	
	@Override
	public boolean updateLoginPwd(Map<String,Object> paramMap) {
		return this.getSqlMapClientTemplate().update(this.namespace.concat("updatepwd"), paramMap)>0;
	}

	@Override
	public List<Long> queryMemberByName(String name) {	
		if(null==name)
			return null;
		return getSqlMapClientTemplate().queryForList(namespace.concat("queryMemberByName"),name);
	}

	@Override
	public boolean updateLoginNameByMemberCode(Member member) {
		if(StringUtil.isEmpty(member.getLoginName())){
			return false;
		}
		return getSqlMapClientTemplate().update(this.namespace.concat("updateLoginNameByMemberCode"), member)>0;
	}
	
} 
