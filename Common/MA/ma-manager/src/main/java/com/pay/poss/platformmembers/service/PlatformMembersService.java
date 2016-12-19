package com.pay.poss.platformmembers.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.platformmembers.dto.PlatformMembersDTO;

/**
 * @Title: MemberRelationService.java
 * @Package com.pay.poss.memberrelation.service
 * @Description: 保存关联信息
 * @author cf
 * @date 2011-9-23 下午04:15:28
 * @version V1.0
 */
public interface PlatformMembersService {

	void insertPlatformMembers(List<PlatformMembersDTO> dataDtoList);

	Map<String, String> updatePlatformMembers(String id, String srcStatus, String targetStatus, Page page);
	
	List<PlatformMembersDTO> findByCriteria(Map<String, String> criteria, Page  page);
	
	List<PlatformMembersDTO> findByCriteria(Map<String, String> criteria);

}
