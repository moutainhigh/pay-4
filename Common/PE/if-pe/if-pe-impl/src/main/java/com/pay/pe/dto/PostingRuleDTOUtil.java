package com.pay.pe.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.pay.pe.model.PaymentService;
import com.pay.pe.model.PostingRule;

public final class PostingRuleDTOUtil {
	/**
	 * Default constructor.
	 */
	public PostingRuleDTOUtil() {

	}

	/**
	 * 将PostingRule对象转换为PostingRuleDTO对象.
	 * 
	 * @param postingRuleList
	 *            The PostingRuleList list object.
	 * @return List
	 */
	public static List<PostingRuleDTO> convertPostingRuleToDto(
			final List<PostingRule> postingRuleList) {
		List<PostingRuleDTO> result = new ArrayList<PostingRuleDTO>();
		if (null == postingRuleList || 0 == postingRuleList.size()) {
			return result;
		} else {
			Iterator it = postingRuleList.iterator();
			while (it.hasNext()) {
				PostingRule postingRule = (PostingRule) it.next();
				PostingRuleDTO postingRuleDTO = convertPostingRuleToDto(postingRule);
				result.add(postingRuleDTO);
			}
		}

		return result;
	}

	/**
	 * 将PostingRuleDTO对象转换为PostingRule对象.
	 * 
	 * @param dtoList
	 *            The PostingRuleDTO list object.
	 * @return List
	 */
	public static List<PostingRule> convertDtoToPostingRule(
			final List<PostingRuleDTO> dtoList) {
		List<PostingRule> result = new ArrayList<PostingRule>();
		if (null == dtoList || 0 == dtoList.size()) {
			return result;
		} else {
			Iterator it = dtoList.iterator();
			while (it.hasNext()) {
				PostingRuleDTO dto = (PostingRuleDTO) it.next();
				PostingRule postingRule = convertDtoToPostingRule(dto);
				result.add(postingRule);
			}
		}
		return result;
	}

	/**
	 * 将PostingRule对象转换为PostingRuleDTO对象.
	 * 
	 * @param postingRule
	 *            The PostingRule object.
	 * @return PostingRuleDTO
	 */
	public static PostingRuleDTO convertPostingRuleToDto(
			final PostingRule postingRule) {
		if (null == postingRule) {
			return null;
		}
		PostingRuleDTO dto = new PostingRuleDTO();
		dto.setAcctcode(postingRule.getAcctCode());
		dto.setAcctAliasAcctType(postingRule.getAcctAliasAcctTypeCode());
		dto.setOrgType(postingRule.getOrgType());
		dto.setCrdr(postingRule.getCrDr());
		dto.setInvaliddate(postingRule.getInvalidDate());
		dto.setParty(postingRule.getParty());
		dto.setPaymentServiceDTO(PaymentServiceDTOUtil
				.convertPaymentServiceDTO(postingRule.getPaymentService()));
		dto.setPostingrulecode(postingRule.getPostingRuleCode());
		dto.setStatus(postingRule.getStatus());
		dto.setValiddate(postingRule.getValidDate());
		dto.setAsynchronousPosting(postingRule.getAsynchronousPosting());
		dto.setAcctAliasBizMbr(postingRule.getAcctAliasBizMbr());
		dto.setAcctAliasIndMbr(postingRule.getAcctAliasIndMbr());
		dto.setMbrAcctSpecific(postingRule.getMbrAcctSpecific());
		return dto;
	}

	/**
	 * 将PostingRuleDTO转换为PostingRule对象.
	 * 
	 * @param dto
	 *            PostingRuleDTO
	 * @return PostingRule
	 */
	public static PostingRule convertDtoToPostingRule(final PostingRuleDTO dto) {
		if (null == dto) {
			return null;
		}
		PostingRule postingRule = new PostingRule();
		postingRule.setAcctCode(dto.getAcctcode());
		postingRule.setAcctAliasAcctTypeCode(dto.getAcctAliasAcctType());
		postingRule.setOrgType(dto.getOrgType());
		postingRule.setCrDr(dto.getCrdr());
		postingRule.setInvalidDate(dto.getInvaliddate());
		postingRule.setParty(dto.getParty());
		postingRule.setPaymentService(PaymentServiceDTOUtil
				.convertToPaymentService(dto.getPaymentServiceDTO()));
		postingRule.setPostingRuleCode(dto.getPostingrulecode());
		postingRule.setStatus(dto.getStatus());
		postingRule.setValidDate(dto.getValiddate());
		postingRule.setAsynchronousPosting(dto.getAsynchronousPosting());
		postingRule.setAcctAliasBizMbr(dto.getAcctAliasBizMbr());
		postingRule.setAcctAliasIndMbr(dto.getAcctAliasIndMbr());
		postingRule.setMbrAcctSpecific(dto.getMbrAcctSpecific());
		return postingRule;
	}

	/**
	 * 将PostingRule对象转换为PostingRuleDTO对象.
	 * 
	 * @param postingRuleList
	 *            The PostingRuleList list object.
	 * @return List
	 */
	public static List<PostingRuleDTO> convertPostingRuleToDto(
			final List<PostingRule> postingRuleList,
			final PaymentServiceDTO paymentServiceDTO) {
		List<PostingRuleDTO> result = new ArrayList<PostingRuleDTO>();
		if (null == postingRuleList || 0 == postingRuleList.size()) {
			return result;
		} else {
			Iterator it = postingRuleList.iterator();
			while (it.hasNext()) {
				PostingRule postingRule = (PostingRule) it.next();
				PostingRuleDTO postingRuleDTO = convertPostingRuleToDto(
						postingRule, paymentServiceDTO);
				result.add(postingRuleDTO);
			}
		}

		return result;
	}

	/**
	 * 将PostingRuleDTO对象转换为PostingRule对象.
	 * 
	 * @param dtoList
	 *            The PostingRuleDTO list object.
	 * @return List
	 */
	public static List<PostingRule> convertDtoToPostingRule(
			final List<PostingRuleDTO> dtoList,
			final PaymentService paymentService) {
		List<PostingRule> result = new ArrayList<PostingRule>();
		if (null == dtoList || 0 == dtoList.size()) {
			return result;
		} else {
			Iterator it = dtoList.iterator();
			while (it.hasNext()) {
				PostingRuleDTO dto = (PostingRuleDTO) it.next();
				PostingRule postingRule = convertDtoToPostingRule(dto,
						paymentService);
				result.add(postingRule);
			}
		}
		return result;
	}

	/**
	 * 将PostingRule对象转换为PostingRuleDTO对象.
	 * 
	 * @param postingRule
	 *            The PostingRule object.
	 * @return PostingRuleDTO
	 */
	public static PostingRuleDTO convertPostingRuleToDto(
			final PostingRule postingRule,
			final PaymentServiceDTO paymentServiceDTO) {
		if (null == postingRule) {
			return null;
		}
		PostingRuleDTO dto = new PostingRuleDTO();
		dto.setAcctcode(postingRule.getAcctCode());
		dto.setAcctAliasAcctType(postingRule.getAcctAliasAcctTypeCode());
		dto.setOrgType(postingRule.getOrgType());
		dto.setCrdr(postingRule.getCrDr());
		dto.setInvaliddate(postingRule.getInvalidDate());
		dto.setParty(postingRule.getParty());
		dto.setPaymentServiceDTO(paymentServiceDTO);
		dto.setPostingrulecode(postingRule.getPostingRuleCode());
		dto.setStatus(postingRule.getStatus());
		dto.setValiddate(postingRule.getValidDate());
		return dto;
	}

	/**
	 * 将PostingRuleDTO转换为PostingRule对象.
	 * 
	 * @param dto
	 *            PostingRuleDTO
	 * @return PostingRule
	 */
	public static PostingRule convertDtoToPostingRule(final PostingRuleDTO dto,
			final PaymentService paymentService) {
		if (null == dto) {
			return null;
		}
		PostingRule postingRule = new PostingRule();
		postingRule.setAcctCode(dto.getAcctcode());
		postingRule.setAcctAliasAcctTypeCode(dto.getAcctAliasAcctType());
		postingRule.setOrgType(dto.getOrgType());
		postingRule.setCrDr(dto.getCrdr());
		postingRule.setInvalidDate(dto.getInvaliddate());
		postingRule.setParty(dto.getParty());
		postingRule.setPaymentService(paymentService);
		postingRule.setPostingRuleCode(dto.getPostingrulecode());
		postingRule.setStatus(dto.getStatus());
		postingRule.setValidDate(dto.getValiddate());
		return postingRule;
	}
}
