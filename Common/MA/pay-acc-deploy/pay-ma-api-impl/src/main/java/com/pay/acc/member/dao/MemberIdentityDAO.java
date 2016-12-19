package com.pay.acc.member.dao;

import java.util.List;

import com.pay.acc.member.dao.model.MemberIdentity;

/**
 * 操作客户标识对象.
 * 
 * @author peng jiong
 */
public interface MemberIdentityDAO {
	/**
	 * 添加客户标识.
	 * 
	 * @param memberIdentity
	 *            客户标识对象
	 * @return
	 */
	void add(final MemberIdentity memberIdentity);

	/**
	 * 修改客户标识.
	 * 
	 * @param memberIdentity
	 *            客户标识对象
	 * @return
	 */
	boolean change(final MemberIdentity memberIdentity);

	/**
	 * 获取客户标识.
	 * 
	 * @param idContent
	 *            标识内容
	 * @param idType
	 *            标识类型
	 * @return
	 */
	MemberIdentity get(final Long memberCode, final Integer idType);

	/**
	 * 获取客户标识.
	 * 
	 * @param member
	 * @param idContent
	 * @return
	 */
	MemberIdentity get(final Long memberCode, final String idContent);

	/**
	 * 删除客户标识.
	 * 
	 * @param memberIdentity
	 */
	void remove(final MemberIdentity memberIdentity);

	/**
	 * 根据membercode得到所有的 MemberIdentity.
	 * 
	 * @param member
	 *            Member
	 * @return List < MemberIdentity >
	 */
	List<MemberIdentity> getMemberIdentityList(final Long memberCode);

	/**
	 * 
	 * @param idContent
	 * @return
	 */
	MemberIdentity getMemberIdentityByIdContent(final String idContent);
}
