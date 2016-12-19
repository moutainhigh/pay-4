package com.pay.base.service.platformmember;

import java.util.List;

import com.pay.base.model.PlatformMember;

public interface PlatformMemberService {

	List<PlatformMember> getSonMemberByFatherCode(PlatformMember platformMember) ;
	
	void createPlatformMembers(PlatformMember platformMember);
}
