package com.pay.acc.member.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.pay.acc.member.dao.MemberIdentityDAO;
import com.pay.acc.member.dao.model.MemberIdentity;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author peng jiong
 */
public class MemberIdentityDAOImpl extends BaseDAOImpl implements
		MemberIdentityDAO {

	private final Log logger = LogFactory.getLog(MemberIdentityDAOImpl.class);

	/**
	 * 添加客户标识.
	 * 
	 * @param memberIdentity
	 *            客户标识对象
	 * @return
	 */
	public void add(final MemberIdentity memberIdentity) {

		Assert.notNull(memberIdentity,
				"MemberIdentity object must be not null ");
		logger.debug("Merge the given MemberIdentity object into the current UnitOfWork.");
		memberIdentity.setCreateDate(new Date());

		this.create(memberIdentity);
	}

	/**
	 * 修改客户标识.
	 * 
	 * @param memberIdentity
	 *            客户标识对象
	 * @return
	 */
	public final boolean change(final MemberIdentity memberIdentity) {

		Assert.notNull(memberIdentity,
				"MemberIdentity object must be not null ");
		return this.update(memberIdentity);

	}

	/**
	 * 获取客户标识.
	 * 
	 * @param idContent
	 *            标识内容
	 * @param idType
	 *            标识类型
	 * @return
	 */
	public final MemberIdentity get(Long memberCode, Integer idType) {
		Assert.notNull(memberCode, "the member must not be null");
		Assert.notNull(idType, "the idType must not be null");

		Map paraMap = new HashMap();
		paraMap.put("memberCode", memberCode);
		paraMap.put("idType", idType);

		return (MemberIdentity) super.findObjectByCriteria(paraMap);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * com.bill99.seashell.technicalservice.dao.memberidentity.MemberIdentityDAO
	 * #get(java.lang.String)
	 */
	public MemberIdentity get(Long memberCode, String idContent) {

		Map paraMap = new HashMap();
		paraMap.put("memberCode", memberCode);
		paraMap.put("idContent", idContent);

		return (MemberIdentity) super.findObjectByCriteria(paraMap);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * com.bill99.seashell.technicalservice.dao.memberidentity.MemberIdentityDAO
	 * #removeMemberIdentity(java.lang.String)
	 */
	public void remove(MemberIdentity memberIdentity) {
		Assert.notNull(memberIdentity, "the MemberIdentity must is existed.");
		this.delete(memberIdentity);
	}

	/**
	 * 根据membercode得到所有的 MemberIdentity.
	 * 
	 * @param membero
	 *            Member
	 * @return List < MemberIdentity >
	 */
	@SuppressWarnings("unchecked")
	public List<MemberIdentity> getMemberIdentityList(Long memberCode) {
		Assert.notNull(memberCode);
		Map paraMap = new HashMap();
		paraMap.put("memberCode", memberCode);
		return super.findByCriteria(paraMap);
	}

	@Override
	public MemberIdentity getMemberIdentityByIdContent(String idContent) {
		Map paraMap = new HashMap();
		paraMap.put("idContent", idContent);

		return (MemberIdentity) super.findObjectByCriteria(paraMap);
	}

}
