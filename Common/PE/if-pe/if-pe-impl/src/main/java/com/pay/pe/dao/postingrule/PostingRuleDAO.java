/**
 * Created on 2006-8-17
 */
package com.pay.pe.dao.postingrule;

import java.util.List;

import com.pay.pe.model.PostingRule;

public interface PostingRuleDAO {

	/**
	 * Merge the given PostingRule object into the current UnitOfWork.
	 * 
	 * @param postingRule
	 *            the PostingRule.
	 * @return PostingRule.
	 */
	PostingRule add(PostingRule postingRule);

	/**
	 * Remove the given PostingRule object from the current UnitOfWork.
	 * 
	 * @param postingRuleCode
	 *            the String.
	 */
	void remove(String postingRuleCode);

	/**
	 * change the given PostingRule object into the current UnitOfWork.
	 * 
	 * @param postingRule
	 *            the PostingRule.
	 * @return PostingRule
	 */
	PostingRule change(PostingRule postingRule);

	/**
	 * Return a copy of the specified PostingRule object.
	 * 
	 * @param postingRuleCode
	 *            the String.
	 * @return PostingRule
	 */
	PostingRule getPostingRuleByCode(String postingRuleCode);

	/**
	 * Return all PostingRule objects from the shared cache.
	 * 
	 * @return List
	 */
	List<PostingRule> getPostingRuleList();

	/**
	 * @得到paymentservice所有的PostingRuleDTO by paymentservicecode
	 * @param paymentservicecode
	 *            String
	 * @return List < PostingRule
	 */
	List<PostingRule> getAllPostingRuleByPCode(Integer paymentservicecode);

}
