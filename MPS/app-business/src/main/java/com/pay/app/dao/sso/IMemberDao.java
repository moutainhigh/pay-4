package com.pay.app.dao.sso;

import com.pay.app.model.Member;

public interface IMemberDao {
	public Member findMemberByUserId(String userid);
    
    public void insertMember(Member member);
}
