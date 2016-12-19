package com.pay.app.dao.sso.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.pay.app.dao.sso.IMemberDao;
import com.pay.app.model.Member;

public class MemberDaoImpl extends SqlMapClientDaoSupport implements IMemberDao{
	
      public Member findMemberByUserId(String userid){
    	  Member member =(Member)getSqlMapClientTemplate().queryForObject("getMemberById", userid);
    	  return member;
      }
      
      public void insertMember(Member member){
    	  getSqlMapClientTemplate().insert("insertMember", member);
      }

}
