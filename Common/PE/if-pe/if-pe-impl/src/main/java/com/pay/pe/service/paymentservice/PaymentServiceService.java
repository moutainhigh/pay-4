package com.pay.pe.service.paymentservice;

import java.util.List;

import com.pay.pe.dto.PaymentServiceDTO;
import com.pay.pe.dto.PaymentServicePKGDTO;
import com.pay.pe.dto.PaymentServicePkgAssignmentDTO;
import com.pay.pe.dto.PostingRuleDTO;
import com.pay.util.MfDate;

public interface PaymentServiceService {

	/**
	 * 得到所有的支付服务.
	 * 
	 * @return List < PaymentServiceDTO >
	 */
	List<PaymentServiceDTO> getAllPaymentService();

	/**
	 * 得到所有的支付服务根据条件，分页查询.
	 * 
	 * @param dto
	 *            PaymentServiceDTO
	 * @return List < PaymentServiceDTO >
	 */
	List<PaymentServiceDTO> getAllPaymentServiceByDTO(PaymentServiceDTO dto);

	/**
	 * 根据支付code得到支付服务对象.
	 * 
	 * @param paymentServiceCode
	 *            String
	 * @return PaymentService
	 */
	PaymentServiceDTO getPaymentService(final Integer paymentServiceCode);

	/**
	 * 判断是否存在支付名称,存在true，不存在false.
	 * 
	 * @param paymentServiceName
	 *            String
	 * @return Boolean
	 */
	Boolean isPaymentServiceExist(final String paymentServiceName);

	/**
	 * 查询在某一支付组paymentServicePkgCode下，某个支付服务类型的所有支付服务。
	 * 
	 * @param paymentServicePkgCode
	 *            支付组
	 * @param paymentServiceCodeType
	 *            支付服务类型
	 * @return 支付服务列表
	 */
	List<PaymentServiceDTO> getPaymentServiceDtos(
			final Integer paymentServicePkgCode,
			final Integer paymentServiceCodeType);

	List<PaymentServiceDTO> getPaymentServiceDtos(
			final Integer paymentServicePkgCode);

	// ---------------pkg-----------------------//

	/**
	 * @判断服务组名称是否存在
	 * @param paymentServicePKGName
	 *            String
	 * @return Boolean
	 */
	Boolean isPaymentServicePKGExist(String paymentServicePKGName);

	/**
	 * @根据服务组代码得到服务对象
	 * @param paymentServicePkgCode
	 *            String
	 * @return PaymentServicePKGDTO
	 */
	PaymentServicePKGDTO getPaymentServicePKG(Integer paymentServicePkgCode);

	/**
	 * @得到所有的服务对象组
	 * @return List < PaymentServicePKGDTO >
	 */
	List<PaymentServicePKGDTO> getAllPaymentServicePKG();

	/**
	 * @根据PaymentServicePKGDTO参数得到相应的记录
	 * @param dto
	 *            PaymentServicePKGDTO
	 * @return List < PaymentServicePKGDTO >
	 */
	List<PaymentServicePKGDTO> getPaymentServicePKGByDTO(
			PaymentServicePKGDTO dto);

	/**
	 * @根据paymentServicePkgCode得到所有的PaymentSrvPkgAssignment
	 * @param paymentServicePkgCode
	 * @return List < PaymentSrvPkgAssignment >
	 */
	List<PaymentServicePkgAssignmentDTO> getAllByPKGCode(
			final Integer paymentServicePkgCode);

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
	Integer getPymtpkgcodeByMatrix(Integer dealcode, Integer ordercode,
			Integer paymethod);

	// ---------------posting rule-------------------//
	/**
	 * 按记帐规则代码查询记帐规则对象.
	 * 
	 * @param postingRuleCode
	 *            记帐规则代码
	 * @return PostingRuleDTO 记帐规则DTO
	 */
	PostingRuleDTO getPostingRule(String postingRuleCode);

	/**
	 * 得到所有记帐规则对象.
	 * 
	 * @return List
	 */
	List<PostingRuleDTO> getPostingRuleList();

	/**
	 * @得到paymentservice所有的PostingRuleDTO by paymentservicecode
	 * @param paymentservicecode
	 *            String
	 * @return List < PostingRuleDTO >
	 */
	List<PostingRuleDTO> getAllPostingRuleByPCode(Integer paymentserviceCode);

	/**
	 * 根据paymentservicecode和mfdate取得所有的postingrule.
	 * 
	 * @param paymentservicecode
	 *            String
	 * @param mfdate
	 *            MfDate
	 * @return List < PostingRuleDTO >
	 */
	List<PostingRuleDTO> getAllPostingRuleByPCode(Integer paymentserviceCode,
			MfDate mfdate);

	/**
	 * 判断是否存在相同的postingrule.
	 * 
	 * @param dto
	 *            PostingRuleDTO
	 * @return boolean
	 */
	boolean existsPostingRule(PostingRuleDTO dto);

}
