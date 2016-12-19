/**
 * create on 2006-09-12
 */
package com.pay.acc.member.service;

import java.util.List;

import com.pay.acc.member.dto.MemberIdentityDto;

/**
 * @author peng jiong
 */
public interface MemberIdentityService {

	// 验证状态-未验证
	int STATUS_NOT_VALIDATE = 0;

	// 验证状态-验证通过
	int STATUS_VALIDATED = 1;

	// 验证状态-验证中
	int STATUS_VALIDATING = 2;

	int ALERT_APPLY = 1;

	// 更改已经验证过的标识alert通知类型
	int ALERT_CHANGE = 2;

	/**
	 * 
	 * @param memberIdentityDto
	 */
	void createMemberIdentity(MemberIdentityDto memberIdentityDto);

	/**
	 * 根据会员标识获取一个MemberIdentity对象.
	 * 
	 * @param idContent
	 * @return
	 */
	MemberIdentityDto getMemberIdentity(final Long memberCode,
			final String idContent);

	/**
	 * 根据membercode和idtype得到一个MemberIdentityDTO,如果不存在 return null.
	 * 
	 * @param idtype
	 *            int
	 * @param membercode
	 *            String
	 * @return MemberIdentityDTO
	 * @author jack liu
	 */
	MemberIdentityDto getMemberIdentityBytype(int idtype, Long memberCode);

	/**
	 * 根据membercode获取主标识，如果没有return null.
	 * 
	 * @param membercode
	 *            String
	 * @return MemberIdentityDTO
	 */
	MemberIdentityDto getPrimaryMemberIdentity(final Long memberCode);

	/**
	 * 根据membercode得到所有的 MemberIdentityDTO.
	 * 
	 * @param membercode
	 *            String
	 * @return List < MemberIdentityDTO >
	 * @author jack liu
	 */
	List<MemberIdentityDto> getMemberIdentityList(Long memberCode);

	/**
	 * 检查标识是否已经验证过
	 * 
	 * @param idContent
	 * @return
	 */
	boolean isIdentityExisted(String idContent);

	/**
	 * 根据idContent得到一个已经验证 MemberIdentityDTO.
	 * 
	 * @param idContent
	 *            String
	 * @return MemberIdentityDTO
	 * @author jack liu
	 */
	MemberIdentityDto getMemberIdentity(String idContent);

	/**
	 * 
	 * @param dto
	 * @return
	 */
	boolean updateMemberIdentity(MemberIdentityDto dto);

}