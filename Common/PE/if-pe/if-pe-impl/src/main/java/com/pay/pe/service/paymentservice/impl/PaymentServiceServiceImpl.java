package com.pay.pe.service.paymentservice.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.pay.pe.dao.paymentservice.PaymentServiceDAO;
import com.pay.pe.dao.paymentservice.PaymentServicePKGDAO;
import com.pay.pe.dao.paymentservice.PaymentSrvPkgAssignmentDAO;
import com.pay.pe.dao.paymentservice.PymtsrvPkgMatrixDAO;
import com.pay.pe.dao.postingrule.PostingRuleDAO;
import com.pay.pe.dto.PaymentServiceDTO;
import com.pay.pe.dto.PaymentServiceDTOUtil;
import com.pay.pe.dto.PaymentServicePKGDTO;
import com.pay.pe.dto.PaymentServicePKGDTOUtil;
import com.pay.pe.dto.PaymentServicePkgADTOUtil;
import com.pay.pe.dto.PaymentServicePkgAssignmentDTO;
import com.pay.pe.dto.PostingRuleDTO;
import com.pay.pe.dto.PostingRuleDTOUtil;
import com.pay.pe.model.PaymentService;
import com.pay.pe.model.PaymentSrvPkgAssignment;
import com.pay.pe.model.PostingRule;
import com.pay.pe.service.paymentservice.PaymentServiceService;
import com.pay.util.DateRange;
import com.pay.util.MfDate;
import com.pay.util.ObjectUtil;
import com.pay.util.StringUtil;

public class PaymentServiceServiceImpl implements PaymentServiceService {

	private Log logger = LogFactory.getLog(getClass());

	private PaymentServicePKGDAO paymentServicePKGDAO;
	private PaymentSrvPkgAssignmentDAO paymentSrvPkgAssignmentDAO;
	private PaymentServiceDAO paymentServiceDAO;
	private PostingRuleDAO postingRuleDAO;

	private PymtsrvPkgMatrixDAO pymtsrvPkgMatrixDAO;

	/*
	 * (non-Javadoc)
	 */
	public List<PaymentServicePkgAssignmentDTO> getAllByPKGCode(
			final Integer paymentServicePkgCode) {
		List<PaymentSrvPkgAssignment> resultList = this.paymentSrvPkgAssignmentDAO
				.getAllPaymentSrvPkgAssignment(paymentServicePkgCode);
		return PaymentServicePkgADTOUtil.convertToPkgADTOList(resultList);
	}

	@Override
	public List<PaymentServiceDTO> getAllPaymentService() {

		List<PaymentService> result = paymentServiceDAO.getAllPaymentService();
		List<PaymentServiceDTO> list = PaymentServiceDTOUtil
				.ConvertToPaymentSerDTOList(result);

		return list;
	}

	@Override
	public List<PaymentServiceDTO> getAllPaymentServiceByDTO(
			PaymentServiceDTO dto) {
		// 转来转去不麻烦吗---dto model 大小写不匹配
		Map queryMap = new HashMap();
		if (!StringUtil.isNull(dto.getPaymentservicecode())) {
			queryMap.put("paymentservicecode", dto.getPaymentservicecode());
		}
		if (!StringUtil.isEmpty(dto.getPaymentservicename())) {
			queryMap.put("paymentservicename", dto.getPaymentservicename());
		}
		if (!StringUtil.isNull(dto.getPaymentServiceType())) {
			queryMap.put("paymentServiceType", dto.getPaymentServiceType());
		}
		queryMap.put("page", dto.getPage());
		List<PaymentService> result = paymentServiceDAO
				.getAllPaymentServiceByDTO(queryMap);
		List<PaymentServiceDTO> list = PaymentServiceDTOUtil
				.ConvertToPaymentSerDTOList(result);

		return list;
	}

	@Override
	public PaymentServiceDTO getPaymentService(final Integer paymentServiceCode) {
		PaymentService model = paymentServiceDAO
				.getPaymentService(paymentServiceCode);
		PaymentServiceDTO dto = PaymentServiceDTOUtil
				.convertPaymentServiceDTO(model);

		return dto;
	}

	@Override
	/**
	 * 查询在某一支付组paymentServicePkgCode下，某个支付服务类型的所有支付服务。
	 * @param paymentServicePkgCode  支付组
	 * @param paymentServiceCodeType 支付服务类型
	 * @return 所以支付服务
	 */
	public List<PaymentServiceDTO> getPaymentServiceDtos(
			final Integer paymentServicePkgCode,
			final Integer paymentServiceCodeType) {
		// 考虑有sql 产生
		// List<PaymentService> result =
		// this.paymentServiceDAO.getPaymentServices(
		// paymentServicePkgCode, paymentServiceCodeType);
		// List<PaymentServiceDTO> list =
		// PaymentServiceDTOUtil.ConvertToPaymentSerDTOList(result);
		// return list;

		List<PaymentServiceDTO> services = new ArrayList<PaymentServiceDTO>();
		// 根据此次支付所使用的支付服务组，得到所有的支付服务.
		List<PaymentServicePkgAssignmentDTO> paymentServices = this
				.getAllByPKGCode(paymentServicePkgCode);
		if (null == paymentServices || paymentServices.size() == 0) {
			return services;
		}
		Iterator<PaymentServicePkgAssignmentDTO> it = paymentServices
				.iterator();
		while (it.hasNext()) {
			PaymentServicePkgAssignmentDTO temp = it.next();
			if (null != temp) {
				PaymentServiceDTO paymentServiceDto = this
						.getPaymentService(temp.getPaymentService());
				if (paymentServiceDto.getPaymentServiceType().intValue() == paymentServiceCodeType) {
					services.add(paymentServiceDto);
				}
			}
		}
		return services;

	}

	@Override
	public List<PaymentServiceDTO> getPaymentServiceDtos(
			Integer paymentServicePkgCode) {

		List<PaymentServiceDTO> services = new ArrayList<PaymentServiceDTO>();
		// 根据此次支付所使用的支付服务组，得到所有的支付服务.
		List<PaymentServicePkgAssignmentDTO> paymentServices = this
				.getAllByPKGCode(paymentServicePkgCode);
		if (null == paymentServices || paymentServices.size() == 0) {
			return services;
		}
		Iterator<PaymentServicePkgAssignmentDTO> it = paymentServices
				.iterator();
		while (it.hasNext()) {
			PaymentServicePkgAssignmentDTO temp = it.next();
			if (null != temp) {
				PaymentServiceDTO paymentServiceDto = this
						.getPaymentService(temp.getPaymentService());
				services.add(paymentServiceDto);
			}
		}
		return services;
	}

	@Override
	public Boolean isPaymentServiceExist(final String paymentServiceName) {
		List result = paymentServiceDAO
				.getAllPaymentServiceByName(paymentServiceName);
		return !result.isEmpty();
	}

	/**
	 * 得到支付服务组代码.
	 * 
	 * @param dealcode
	 *            Integer 支付方式
	 * @param ordercode
	 *            Integer 订单类型
	 * @param paymethod
	 *            Integer 订单到帐方式
	 * @return paymentservicepkgcode Integer
	 */
	public Integer getPymtpkgcodeByMatrix(final Integer dealcode,
			final Integer ordercode, final Integer paymethod) {
		return this.pymtsrvPkgMatrixDAO.getPymtpkgcodeByMatrix(dealcode,
				ordercode, paymethod);
	}

	@Override
	public List<PaymentServicePKGDTO> getAllPaymentServicePKG() {
		return PaymentServicePKGDTOUtil
				.ConvertToPaymentSerPKGList(paymentServicePKGDAO
						.getAllPaymentServicePKG());
	}

	@Override
	public PaymentServicePKGDTO getPaymentServicePKG(
			final Integer paymentServicePkgCode) {
		PaymentServicePKGDTO result = PaymentServicePKGDTOUtil
				.convertToPaymentServicePKGDTO(paymentServicePKGDAO
						.getPaymentServicePKG(paymentServicePkgCode));
		return result;
	}

	@Override
	public List<PaymentServicePKGDTO> getPaymentServicePKGByDTO(
			final PaymentServicePKGDTO dto) {
		Map queryMap = new HashMap();
		if (!StringUtil.isNull(dto.getPaymentservicepackagecode())) {
			queryMap.put("paymentservicepackagecode",
					dto.getPaymentservicepackagecode());
		}
		if (!StringUtil.isEmpty(dto.getPaymentservicepackagename())) {
			queryMap.put("paymentservicepackagename",
					dto.getPaymentservicepackagename());
		}
		// queryMap.put("page",dto.getPage());
		// return (List) this.getPaymentSerPKGDao().getPaymentServicePKGByMap(
		// queryMap);
		return null;
	}

	@Override
	public Boolean isPaymentServicePKGExist(final String paymentServicePKGName) {
		List result = this.paymentServicePKGDAO
				.getPaymentServicePKGByName(paymentServicePKGName);
		if (StringUtil.isNull(result) || result.size() == 0) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	@Override
	public boolean existsPostingRule(PostingRuleDTO dto) {

		List<PostingRuleDTO> dtoList = this.getAllPostingRuleByPCode(dto
				.getPaymentServiceDTO().getPaymentservicecode());
		DateRange dateRange = new DateRange(dto.getValiddate(),
				dto.getInvaliddate());
		for (PostingRuleDTO allDto : dtoList) {
			DateRange currRange = new DateRange(new MfDate(
					allDto.getValiddate()), new MfDate(allDto.getInvaliddate()));
			if (!ObjectUtil.equalsContainNull(dto.getPostingrulecode(),
					allDto.getPostingrulecode())
					&& allDto.equals(dto)
					&& (currRange.coOverlapsNoEnd(dateRange))) {
				return true;
			}
		}

		return false;
	}

	@Override
	public List<PostingRuleDTO> getAllPostingRuleByPCode(
			final Integer paymentservicecode) {

		Assert.notNull(paymentservicecode);
		List<PostingRuleDTO> list = PostingRuleDTOUtil
				.convertPostingRuleToDto(this.postingRuleDAO
						.getAllPostingRuleByPCode(paymentservicecode));

		return list;
	}

	@Override
	/**
	 * 根据paymentservicecode和mfdate取得所有的postingrule.
	 * @param paymentservicecode String
	 * @param mfdate MfDate
	 * @return List < PostingRuleDTO >
	 */
	public List<PostingRuleDTO> getAllPostingRuleByPCode(
			final Integer paymentservicecode, final MfDate mfdate) {

		List<PostingRuleDTO> list = this
				.getAllPostingRuleByPCode(paymentservicecode);
		if (mfdate != null) {
			List<PostingRuleDTO> resultList = new LinkedList<PostingRuleDTO>();
			for (PostingRuleDTO dto : list) {
				DateRange range = new DateRange(new MfDate(dto.getValiddate()),
						new MfDate(dto.getInvaliddate()));
				if (range.includes(mfdate)
						|| new MfDate(range.getStart()).equals(mfdate)
						|| (new MfDate(dto.getInvaliddate()).equals(new MfDate(
								1900, 1, 1)) && new MfDate(dto.getValiddate())
								.compareTo(mfdate) <= 0)) {
					resultList.add(dto);
				}
			}

			return resultList;
		}

		return list;
	}

	@Override
	/**
	 * 按记帐规则代码查询记帐规则对象.
	 * @param postingRuleCode
	 *            记帐规则代码
	 * @return PostingRuleDTO 记帐规则DTO
	 */
	public PostingRuleDTO getPostingRule(final String postingRuleCode) {

		Assert.notNull(postingRuleCode);
		PostingRule account = this.postingRuleDAO
				.getPostingRuleByCode(postingRuleCode);
		PostingRuleDTO result = PostingRuleDTOUtil
				.convertPostingRuleToDto(account);

		return result;
	}

	@Override
	/**
	 * 得到所有记帐规则对象.
	 * @return List
	 */
	public List<PostingRuleDTO> getPostingRuleList() {

		List<PostingRule> postingRuleList = this.postingRuleDAO
				.getPostingRuleList();
		List<PostingRuleDTO> list = PostingRuleDTOUtil
				.convertPostingRuleToDto(postingRuleList);

		return list;
	}

	public PaymentServicePKGDAO getPaymentServicePKGDAO() {
		return paymentServicePKGDAO;
	}

	public void setPaymentServicePKGDAO(
			PaymentServicePKGDAO paymentServicePKGDAO) {
		this.paymentServicePKGDAO = paymentServicePKGDAO;
	}

	public PaymentSrvPkgAssignmentDAO getPaymentSrvPkgAssignmentDAO() {
		return paymentSrvPkgAssignmentDAO;
	}

	public void setPaymentSrvPkgAssignmentDAO(
			PaymentSrvPkgAssignmentDAO paymentSrvPkgAssignmentDAO) {
		this.paymentSrvPkgAssignmentDAO = paymentSrvPkgAssignmentDAO;
	}

	public PaymentServiceDAO getPaymentServiceDAO() {
		return paymentServiceDAO;
	}

	public void setPaymentServiceDAO(PaymentServiceDAO paymentServiceDAO) {
		this.paymentServiceDAO = paymentServiceDAO;
	}

	public PostingRuleDAO getPostingRuleDAO() {
		return postingRuleDAO;
	}

	public void setPostingRuleDAO(PostingRuleDAO postingRuleDAO) {
		this.postingRuleDAO = postingRuleDAO;
	}

	public PymtsrvPkgMatrixDAO getPymtsrvPkgMatrixDAO() {
		return pymtsrvPkgMatrixDAO;
	}

	public void setPymtsrvPkgMatrixDAO(PymtsrvPkgMatrixDAO pymtsrvPkgMatrixDAO) {
		this.pymtsrvPkgMatrixDAO = pymtsrvPkgMatrixDAO;
	}

}
