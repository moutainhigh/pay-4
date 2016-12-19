package com.pay.poss.platformmembers.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.platformmembers.dao.PlatformMembersDao;
import com.pay.poss.platformmembers.model.PlatformMembers;

public class PlatformMembersDaoImpl extends BaseDAOImpl<PlatformMembers> implements PlatformMembersDao {

	public List<PlatformMembers> findByCriteria(Map<String, Object> paramMap, Page page) {
		return super.findByCriteria("findByCriteria", paramMap, page);//.findByCriteria(paramMap, page);
	}
	
//	public boolean update(PlatformMembers platformMembers) {
//		return super.update(platformMembers);
//	}
	
//	public Object create(PlatformMembers platformMembers) {
//		return super.create(platformMembers);
//	}

}
