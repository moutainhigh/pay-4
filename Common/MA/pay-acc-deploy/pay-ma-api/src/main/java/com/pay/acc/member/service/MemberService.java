package com.pay.acc.member.service;

import java.util.List;

import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.dto.MemberMessageBeanDto;
import com.pay.acc.member.dto.MemberOperateDto;
import com.pay.acc.member.exception.MemberException;
import com.pay.acc.member.exception.MemberUnknowException;

public interface MemberService {

	/**
	 * 创建会员
	 * 
	 * @param Member
	 * @return
	 */
	public Long createMember(MemberDto member);

	/**
	 * key:memberCode 查询会员
	 * 
	 * @param memberCode
	 *            会员号
	 * @return 会员信息
	 */
	public MemberDto queryMemberByMemberCode(Long memberCode);

	/**
	 * 根据登陆名查询会员
	 * 
	 * @param loginName
	 * @return
	 */
	public MemberDto queryMemberByLoginName(String loginName);

	/**
	 * 查询会员信息
	 * 
	 * @param loginName
	 * @param memberCode
	 * @param memberType
	 * @param acctType
	 * @return
	 * @throws MemberUnknowException
	 */
	public MemberMessageBeanDto queryMemberInfo(String loginName,
			Long memberCode, Integer memberType, Integer acctType)
			throws MemberUnknowException;

	/**
	 * 根据会员号查询会员基本信息
	 * 
	 * @param memberCode
	 *            会员号
	 * @return
	 * @throws MemberException
	 * @throws MemberUnknowException
	 */
	public MemberDto queryMemberWithMemberCode(Long memberCode)
			throws MemberException, MemberUnknowException;

	/**
	 * 根据sso关联的用户id查询会员基本信息
	 * 
	 * @param ssoUserId
	 *            与sso关联的用户id
	 * @return
	 * @throws MemberException
	 * @throws MemberUnknowException
	 */
	public MemberDto queryMemberWithSsoUserId(String ssoUserId)
			throws MemberException, MemberUnknowException;

	/**
	 * 更新用户，可以选择具体的用户的属性进行更新
	 * 
	 * @param memberDto
	 * @return
	 * @throws MemberException
	 * @throws MemberUnknowException
	 */
	public boolean updateMember(MemberDto memberDto) throws MemberException,
			MemberUnknowException;

	/**
	 * 会员解锁
	 * 
	 * @param mo
	 *            操作对象
	 * @param frozenMinute
	 *            冻结时间
	 * @return
	 */
	public int unlockMember(MemberOperateDto mo, int frozenMinute);

	/**
	 * 会员锁定
	 * 
	 * @param memberCode
	 *            会员号
	 * @return
	 */
	public boolean lockMember(Long memberCode);

	/**
	 * 会员解锁
	 * 
	 * @param mo
	 * @return
	 */
	int unlockMember(MemberOperateDto mo);

	/**
	 * 会员激活
	 * 
	 * @param mo
	 * @return
	 */
	public boolean active(Long memberCode);

	/**
	 * 会员修改登录密码
	 * 
	 * @param mo
	 * @return
	 */
	public boolean updateLoginPwd(Long memberCode, String loginPwd);

	/**
	 * 
	 * @param name
	 * @return
	 */
	public List<Long> queryMemberByName(String name);

	/**
	 * 更新登录名
	 * 
	 * @param memberDto
	 * @return
	 */
	public boolean updateLoginNameByMemberCode(MemberDto member)
			throws MemberException, MemberUnknowException;
}
