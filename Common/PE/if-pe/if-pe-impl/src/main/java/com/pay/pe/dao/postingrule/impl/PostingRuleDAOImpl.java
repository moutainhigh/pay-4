/**
 * Created on 2006-8-17
 */
package com.pay.pe.dao.postingrule.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.pay.inf.dao.impl.IbatisDAOSupport;
import com.pay.pe.dao.paymentservice.impl.PaymentServiceDAOImpl;
import com.pay.pe.dao.postingrule.PostingRuleDAO;
import com.pay.pe.model.PostingRule;

/**
 * 
 * 
 * <p>
 * PostingRuleDAOImpl.java
 * </p>
 * 
 */
public final class PostingRuleDAOImpl extends IbatisDAOSupport implements
		PostingRuleDAO {

	private PaymentServiceDAOImpl paymentServiceDao;
	/**
	 * logger.
	 */
	private Log logger = LogFactory.getLog(getClass());

	/**
	 * Merge the given PostingRule object into the current UnitOfWork.
	 * 
	 * @param postingRule
	 *            the PostingRule.
	 * @return PostingRule.
	 */
	public PostingRule add(final PostingRule postingRule) {
		Assert.notNull(postingRule, "PostingRule object must be not null");
		logger.debug("" + "Merge the given PostingRule"
				+ " object into the current UnitOfWork.");
		return (PostingRule) this.saveObject(postingRule);
	}

	/**
	 * Remove the given PostingRule object from the current UnitOfWork.
	 * 
	 * @param postingRuleCode
	 *            the String.
	 */
	public void remove(final String postingRuleCode) {
		Assert.notNull(postingRuleCode, "postingRuleCode must be not null");
		// this.deleteObjectById(PostingRule.class, postingRuleCode);
		this.deleteObjectById(postingRuleCode);
	}

	/**
	 * change the given PostingRule object into the current UnitOfWork.
	 * 
	 * @param postingRule
	 *            the PostingRule.
	 * @return PostingRule
	 */
	public PostingRule change(final PostingRule postingRule) {
		Assert.notNull(postingRule, "PostingRule object must be not null");
		// return (PostingRule)this.getTopLinkTemplate().deepMerge(postingRule);
		// return (PostingRule) this.updateObject(PostingRule.class,
		// postingRule, postingRule.getPostingrulecode());
		// return (PostingRule) this.updateOrSaveObject(postingRule);
		return null;
	}

	/**
	 * Return a copy of the specified PostingRule object.
	 * 
	 * @param postingRuleCode
	 *            the String.
	 * @return PostingRule
	 */
	public PostingRule getPostingRuleByCode(final String postingRuleCode) {
		Assert.notNull(postingRuleCode, "postingRuleCode must be not null");
		return (PostingRule) super.findObjectById(postingRuleCode);
		// return
		// (PostingRule)this.getTopLinkTemplate().readAndCopy(PostingRule.class,postingRuleCode);
		// return (PostingRule) super.findObjectById(PostingRule.class,
		// postingRuleCode);
	}

	/**
	 * Return all PostingRule objects from the shared cache.
	 * 
	 * @return List
	 */
	public List<PostingRule> getPostingRuleList() {
		logger.debug("load all PostingRule object");
		// return this.loadAllObjects(PostingRule.class);
		return null;
	}

	/**
	 * @得到paymentservice所有的PostingRuleDTO by paymentservicecode
	 * @param paymentservicecode
	 *            String
	 * @return List < PostingRule >
	 */
	public List<PostingRule> getAllPostingRuleByPCode(
			final Integer paymentservicecode) {
		PostingRule postingRule = new PostingRule();
		postingRule.setPaymentServiceCode(paymentservicecode);
		return super.findBySelective(postingRule);

	}

	public void setPaymentServiceDao(PaymentServiceDAOImpl paymentServiceDao) {
		this.paymentServiceDao = paymentServiceDao;
	}
}
