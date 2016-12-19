package com.pay.acc.member.dao;

import java.util.List;
import java.util.Map;

import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.model.Member;
import com.pay.acc.member.model.MemberMessageBean;
import com.pay.inf.dao.BaseDAO;

public interface MemberDAO extends BaseDAO<Member> {

	/**
	 * 
	 * @param sqlId
	 * @param paraMap
	 * @return
	 */
	public MemberMessageBean findMemberMessageBeanByMap(String sqlId,
			Map<String, Object> paraMap);

	/**
	 * 根据会员号查询会员
	 * 
	 * @param memberCode
	 * @return
	 */
	public Member queryMemberWithMemberCode(Long memberCode);

	/**
	 * 根据登陆名查询会员
	 * 
	 * @param memberCode
	 * @return
	 */
	public Member queryMemberByLoginName(String loginName);

	/**
	 * 根据与sso关联的用户id查询会员
	 * 
	 * @param ssoUserId
	 *            与sso关联的用户id
	 * @return
	 */
	public Member queryMemberWithSsoUserId(String ssoUserId);

	/**
	 * 账户解锁
	 * 
	 * @param paramMap
	 * @return
	 */
	public int unlockMember(Map<String, Object> paramMap);

	/**
	 * 账户锁定
	 * 
	 * @param paramMap
	 * @return
	 */
	public boolean lockMember(Map<String, Object> paramMap);

	/**
	 * 激活
	 * 
	 * @param paramMap
	 * @return
	 */
	public boolean active(Long memberCode);

	/**
	 * 修改登录密码
	 * 
	 * @param paramMap
	 * @return
	 */
	public boolean updateLoginPwd(Map<String, Object> paramMap);

	/**
	 * 
	 * @param name
	 * @return
	 */
	List<Long> queryMemberByName(String name);
	
	/**
	 * 更新登录名
	 * @param memberDto
	 * @return
	 */
	public boolean updateLoginNameByMemberCode(Member member);
}
