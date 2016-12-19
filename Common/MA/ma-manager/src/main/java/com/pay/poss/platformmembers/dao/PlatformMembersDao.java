package com.pay.poss.platformmembers.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.platformmembers.model.PlatformMembers;

public interface PlatformMembersDao extends BaseDAO<PlatformMembers> {

	List<PlatformMembers> findByCriteria(Map<String, Object> paramMap, Page page);
}
