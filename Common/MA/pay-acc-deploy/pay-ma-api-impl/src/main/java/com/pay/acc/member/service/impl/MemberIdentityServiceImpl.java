/*
 * created on 2006-09-09
 */
package com.pay.acc.member.service.impl;

import java.util.List;

import org.springframework.util.Assert;

import com.pay.acc.member.dao.MemberIdentityDAO;
import com.pay.acc.member.dao.model.MemberIdentity;
import com.pay.acc.member.dto.MemberIdentityDto;
import com.pay.acc.member.service.MemberIdentityService;
import com.pay.util.BeanConvertUtil;

/**
 * @author peng jiong
 */
public class MemberIdentityServiceImpl implements MemberIdentityService {

	private MemberIdentityDAO memberIdentityDAO;

	public void setMemberIdentityDAO(MemberIdentityDAO memberIdentityDAO) {
		this.memberIdentityDAO = memberIdentityDAO;
	}

	/**
	 * 根据membercode和idtype得到一个MemberIdentityDTO.
	 * 
	 * @param idtype
	 *            int
	 * @param membercode
	 *            String
	 * @return MemberIdentityDto
	 * @author jack liu
	 */
	public MemberIdentityDto getMemberIdentityBytype(final int idtype,
			final Long memberCode) {
		Assert.notNull(idtype);
		Assert.notNull(memberCode);
		List<MemberIdentityDto> list = null;
		try {
			list = this.getMemberIdentityList(memberCode);
			for (MemberIdentityDto dto : list) {
				if (dto.getIdType().intValue() == idtype) {
					return dto;
				}
			}
		} finally {
			list = null;
		}
		return null;
	}

	/**
	 * 根据membercode获取主标识，如果没有return null.
	 * 
	 * @param membercode
	 *            String
	 * @return MemberIdentityDto
	 */
	public MemberIdentityDto getPrimaryMemberIdentity(final Long memberCode) {
		List<MemberIdentityDto> result = null;
		MemberIdentityDto tmpDto = null;
		try {
			result = this.getMemberIdentityList(memberCode);
			if (result != null) {
				for (MemberIdentityDto dto : result) {
					if (dto.getIsPrimaryId().intValue() == 1) {
						tmpDto = dto;
						break;
					}
				}
			}
			return tmpDto;
		} finally {
			result = null;
		}

	}

	/**
	 * 根据membercode得到所有的 MemberIdentityDto.
	 * 
	 * @param membercode
	 *            String
	 * @return List < MemberIdentityDto >
	 * @author jack liu
	 */
	public List<MemberIdentityDto> getMemberIdentityList(final Long memberCode) {
		Assert.notNull(memberCode);

		List<MemberIdentity> tmpList = null;
		try {
			tmpList = memberIdentityDAO.getMemberIdentityList(memberCode);

			List<MemberIdentityDto> result = (List<MemberIdentityDto>) BeanConvertUtil
					.convert(MemberIdentityDto.class, tmpList);
			if (result == null || result.isEmpty()) {
				return null;
			}
			return result;
		} finally {
			tmpList = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bill99.seashell.domain.service.memberidentity.MemberIdentityService
	 * #getMemberIdentity(java.lang.String, java.lang.String)
	 */
	public MemberIdentityDto getMemberIdentity(Long memberCode, String idContent) {
		MemberIdentity memberIdentity = null;
		try {
			memberIdentity = memberIdentityDAO.get(memberCode, idContent);
			return BeanConvertUtil.convert(MemberIdentityDto.class,
					memberIdentity);
		} finally {
			memberIdentity = null;

		}
	}

	/**
	 * 检查标识是否已经验证过
	 * 
	 * @param idContent
	 * @return
	 */
	public boolean isIdentityExisted(String idContent) {
		Assert.notNull(idContent, "idContent must not be null");
		MemberIdentity memberIdentity = null;
		try {
			memberIdentity = memberIdentityDAO
					.getMemberIdentityByIdContent(idContent.toLowerCase());

			if (memberIdentity == null) {
				return false;
			}

			if (memberIdentity.getStatus() == STATUS_VALIDATED) {
				return true;
			}

			return false;
		} finally {
			memberIdentity = null;
		}
	}

	/**
	 * 根据idContent得到一个已经验证 MemberIdentity.
	 * 
	 * @return MemberIdentity
	 */
	public MemberIdentityDto getMemberIdentity(final String idContent) {
		MemberIdentity memberIdentity = memberIdentityDAO
				.getMemberIdentityByIdContent(idContent);

		return BeanConvertUtil.convert(MemberIdentityDto.class, memberIdentity);

	}

	public boolean updateMemberIdentity(MemberIdentityDto dto) {
		return memberIdentityDAO.change(BeanConvertUtil.convert(
				MemberIdentity.class, dto));
	}

	@Override
	public void createMemberIdentity(MemberIdentityDto memberIdentityDto) {

		memberIdentityDAO.add(BeanConvertUtil.convert(MemberIdentity.class,
				memberIdentityDto));
	}

}