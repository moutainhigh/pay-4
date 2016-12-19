package com.pay.base.dao.platformmember;

import java.util.List;

import com.pay.base.model.PlatformMember;
import com.pay.inf.dao.BaseDAO;

/**
 * 
 * @author yangjian
 *
 *         下午7:13:55
 */

public interface PlatformMemberDao extends BaseDAO<PlatformMember> {

	/**
	 * 
	 * @param fatherMemberCode
	 * 根据父的会员号查出挂在父会员号下面审核通过的子用户
	 * @return
	 */
	List<PlatformMember> findSonMemberInfoByFatherMemberCode(PlatformMember platformMember);
	
	/**
	 * 
	 * @param fater_operator_id
	 * 
	 * @return
	 */
	PlatformMember findStatusByFaterId(String fater_operator_id);
	
	
	/**
	 * 
	 * @param sonMemberCode
	 * 通过普通会员号更新审核状态
	 * @return
	 */
	Integer updateStatusBySonMemberCode(PlatformMember platformMember);
	
	/**
	 * 
	 * @param platformMember
	 * 插入平台会员和普通会员的关系
	 * @return
	 */
	Integer insertRelation(PlatformMember platformMember);
}
