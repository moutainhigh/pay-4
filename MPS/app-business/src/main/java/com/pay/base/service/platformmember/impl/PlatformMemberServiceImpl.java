package com.pay.base.service.platformmember.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pay.base.dao.platformmember.PlatformMemberDao;
import com.pay.base.model.PlatformMember;
import com.pay.base.service.platformmember.PlatformMemberService;

@Service("platformMemberService")
public class PlatformMemberServiceImpl implements PlatformMemberService{

	@Autowired
	@Qualifier(value="platformMemberDao")
	private PlatformMemberDao platformMemberDao;
	
	@Override
	public List<PlatformMember> getSonMemberByFatherCode(PlatformMember platformMember){
        return (List<PlatformMember>)platformMemberDao.findSonMemberInfoByFatherMemberCode(platformMember);
    }
	@Transactional(value="base-transactionManager",rollbackFor={Exception.class},propagation=Propagation.REQUIRED)
	public void createPlatformMembers(PlatformMember platformMember) {
		Object platform = this.platformMemberDao.create(platformMember);
	}

}
