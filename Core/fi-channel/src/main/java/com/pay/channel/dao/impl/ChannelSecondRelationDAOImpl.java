/**
 * 
 */
package com.pay.channel.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.pay.channel.dao.ChannelSecondRelationDAO;
import com.pay.channel.model.ChannelSecondRelation;
import com.pay.fi.helper.ChannelItemOrgCodeEnum;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.util.StringUtil;

/**
 * @author chaoyue
 *
 */
public class ChannelSecondRelationDAOImpl extends BaseDAOImpl implements
		ChannelSecondRelationDAO {

	private final Log logger = LogFactory.getLog(getClass());

	@Override
	public List<ChannelSecondRelation> findRelation(String memberCode,
			String orgCode, String transType, String currencyCode, String mcc,Long paymentChannelItemId) {

		if (logger.isDebugEnabled()) {
			logger.debug("memberCode:" + memberCode);
			logger.debug("orgCode:" + orgCode);
			logger.debug("transType:" + transType);
		}

		if (StringUtil.isEmpty(memberCode) || StringUtil.isEmpty(orgCode)) {

			return null;
		}

		Assert.notNull(memberCode, "memberCode must not be null");
		Assert.notNull(orgCode, "orgCode must not be null");

		Map<String, String> paraMap = new HashMap();
		paraMap.put("memberCode", memberCode);
		paraMap.put("orgCode", orgCode);
		paraMap.put("paymentChannelItemId", paymentChannelItemId+"");
		if (!StringUtil.isEmpty(transType)) {
			paraMap.put("transType", transType);
		}

		if (ChannelItemOrgCodeEnum.CREDORAX.getCode().equals(orgCode)) {
			if (!StringUtil.isEmpty(currencyCode)) {
				paraMap.put("currencyCode", currencyCode);
			}
			if (!StringUtil.isEmpty(mcc)) {
				paraMap.put("mcc", mcc);
			}
		}

		return super.findByCriteria("findByOrgCode", paraMap);
	}

	@Override
	public ChannelSecondRelation findRelation(String memberCode,
			String orgCode, String transType) {

		if (logger.isDebugEnabled()) {
			logger.debug("memberCode:" + memberCode);
			logger.debug("orgCode:" + orgCode);
			logger.debug("transType:" + transType);
		}

		Assert.notNull(memberCode, "memberCode must not be null");
		Assert.notNull(orgCode, "orgCode must not be null");

		Map<String, String> paraMap = new HashMap();
		paraMap.put("memberCode", memberCode);
		paraMap.put("orgCode", orgCode);

		if (!StringUtil.isEmpty(transType)) {
			paraMap.put("transType", transType);
		}

		return (ChannelSecondRelation) super.findObjectByCriteria(
				"findByOrgCode", paraMap);
	}

	@Override
	public ChannelSecondRelation findByOrgCode(String memberCode,
			String orgCode, String currencyCode) {
		if (logger.isDebugEnabled()) {
			logger.debug("memberCode:" + memberCode);
			logger.debug("orgCode:" + orgCode);
		}

		Assert.notNull(memberCode, "memberCode must not be null");
		Assert.notNull(orgCode, "orgCode must not be null");

		Map<String, String> paraMap = new HashMap();
		paraMap.put("memberCode", memberCode);
		paraMap.put("orgCode", orgCode);
		paraMap.put("currencyCode", currencyCode);
		return (ChannelSecondRelation) super.findObjectByCriteria(
				"findByOrgCode", paraMap);
	}

	@Override
	public List<ChannelSecondRelation> findByMemberCode(String memberCode) {
		Map<String, String> paraMap = new HashMap();
		paraMap.put("memberCode", memberCode);
		return super.findByCriteria(paraMap);
	}

	@Override
	public List<ChannelSecondRelation> findByMemberCode(String memberCode,
			String orgCode) {
		Map<String, String> paraMap = new HashMap();
		paraMap.put("memberCode", memberCode);
		paraMap.put("orgCode", orgCode);
		return super.findByCriteria(paraMap);
	}

	@Override
	public ChannelSecondRelation findRelation(String memberCode,
			String orgCode, String transType, String currencyCode) {
		Assert.notNull(memberCode, "memberCode must not be null");
		Assert.notNull(orgCode, "orgCode must not be null");

		Map<String, String> paraMap = new HashMap();
		paraMap.put("memberCode", memberCode);
		paraMap.put("orgCode", orgCode);
		paraMap.put("transType", transType);
		if (!StringUtil.isEmpty(currencyCode)) {
			paraMap.put("currencyCode", currencyCode);
		}
		return (ChannelSecondRelation) super.findObjectByCriteria(
				"findByOrgCode", paraMap);
	}

	@Override
	public List<ChannelSecondRelation> findByChannelSecondRelation(
			ChannelSecondRelation csr) {
		return super.findByCriteria(csr);
	}

}
