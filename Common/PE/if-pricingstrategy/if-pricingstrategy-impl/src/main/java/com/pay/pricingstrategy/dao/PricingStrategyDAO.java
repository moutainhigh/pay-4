package com.pay.pricingstrategy.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.pricingstrategy.model.PricingStrategy;
import com.pay.pricingstrategy.model.PricingStrategyDetail;
import com.pay.pricingstrategy.model.PricingStrategyDetailReport;

public interface PricingStrategyDAO extends BaseDAO {
	/**
	 * @增加一个价格策略
	 * @param pricingStrategy
	 *            PricingStrategy
	 * @return PricingStrategy
	 */
	PricingStrategy addPricingStrategy(PricingStrategy pricingStrategy);

	/**
	 * @更改一个价格策略
	 * @param pricingStrategy
	 *            PricingStrategy
	 * @return PricingStrategy
	 */
	PricingStrategy changePricingStrategy(PricingStrategy pricingStrategy);

	/**
	 * @delete pricingStrategyCode by PricingStrategyCode
	 * @param pricingStrategyCode
	 *            Integer
	 */
	void removePricingStrategy(Long pricingStrategyCode);

	/**
	 * @get a PricingStrategy by pricingStrategyCode
	 * @param pricingStrategyCode
	 *            Integer
	 * @return PricingStrategy
	 */
	PricingStrategy getPricingStrategy(Long pricingStrategyCode);

	/**
	 * @add PricingStrategyDetail
	 * @param pricingDetail
	 *            PricingStrategyDetail
	 * @return PricingStrategyDetail
	 */
	PricingStrategyDetail addPricingStrategyDetail(
			PricingStrategyDetail pricingDetail);

	/**
	 * @change PricingStrategyDetail
	 * @param pricingDetail
	 *            PricingStrategyDetail
	 * @return PricingStrategyDetail
	 */
	PricingStrategyDetail changePricingStrategyDetail(
			PricingStrategyDetail pricingDetail);

	/**
	 * @根据价格策略明细代码删除一条价格策略明细
	 * @param pricestrategydetailcode
	 *            Integer
	 */
	void removePricingStrategyDetail(Long priceStrategyDetailCode);

	/**
	 * @get PricingStrategyDetail by pricingStrategyCode
	 * @param pricingStrategyDetailCode
	 *            Integer
	 * @return PricingStrategyDetail
	 */
	PricingStrategyDetail getPricingStrategyDetail(
			Long pricingStrategyDetailCode);

	/**
	 * 过滤存在reservedCode中的记录
	 * 
	 * @param pricingStrategyCode
	 * @param transAmount
	 * @param terminalTypeCode
	 * @param reservedCode
	 * @return
	 */
	public List<PricingStrategyDetail> getPricingStrategyDetailByReservedCode(
			Long pricingStrategyCode, Long transAmount,
			Integer terminalTypeCode, String reservedCode);

	/**
	 * @get PricingStrategyDetail by pricingStrategyCode
	 * @param pricingStrategyCode
	 *            Integer
	 * @return List < PricingStrategyDetail >
	 */
	List<PricingStrategyDetail> getAllPricingStrategyDetailByPricingStrategyCode(
			Long pricingStrategyCode);

	/**
	 * @get PricingStrategyDetail by pricingStrategyCode
	 * @param pricingStrategyCode
	 *            Integer
	 * @param transAmount
	 *            Long
	 * @param terminaltype
	 *            Integer
	 * @param reservedCode
	 *            String
	 * @return List < PricingStrategyDetail >
	 */
	List<PricingStrategyDetail> getAllPricingStrategyDetailByParam(
			Long pricingStrategyCode, Long transAmount, Integer terminaltype,
			String reservedCode);

	/**
	 * 获得同一个银行（包括null）下所有同一个价格策略Code，同一个终端类型的所有价格策略
	 * 
	 * @param pricingStrategyCode
	 * @param transAmount
	 * @param terminaltype
	 * @param reservedCode
	 * @return
	 */
	List<PricingStrategyDetail> getAllPricingStrategyDetail(
			Long pricingStrategyCode, Long transAmount, Integer terminaltype,
			String reservedCode);

	/**
	 * @得到global的价格策略
	 * @param paymentServiceCode
	 *            Integer
	 * @param date
	 *            MfDate
	 * @return List < PricingStrategy >
	 */
	List<PricingStrategy> getAllGlobalPricingStrategy(
			Integer paymentServiceCode, Date date);

	/**
	 * @得到PricingStrategy
	 * @param paymentServiceCode
	 *            Integer
	 * @param memberCode
	 *            Long
	 * @param date
	 *            MfDate
	 * @return List < PricingStrategy >
	 */
	List<PricingStrategy> getPricingStrategyByMember(
			Integer paymentServiceCode, Long memberCode, Date date);

	/**
	 * @得到PricingStrategy
	 * @param paymentServiceCode
	 *            Integer
	 * @param serviceLevelCode
	 *            Integer
	 * @param date
	 *            Date
	 * @return List < PricingStrategy >
	 */
	List<PricingStrategy> getPricingStrategyByServiceLevel(
			Integer paymentServiceCode, Integer serviceLevelCode, Date date);

	/**
	 * 根据servicelevel得到所有的价格策略.
	 * 
	 * @param servicelevel
	 *            Integer
	 * @return List < PricingStrategy >
	 */
	List<PricingStrategy> getPricingStrategyByServiceLevel(Integer servicelevel);

	/**
	 * @根据查询map得到所有的价格策略
	 * @param querymap
	 *            Map
	 * @return List < PricingStrategy >
	 */
	List<PricingStrategy> getAllPricingStrategyByDTO(Map querymap);

	/**
	 * @得到所有的及格策略根据paymentservicecode
	 * @param paymentServiceCode
	 *            Integer
	 * @return List < PricingStrategy >
	 */
	List<PricingStrategy> getAllPricingStrategyByPSC(Integer paymentServiceCode);
	/**
	 * @得到渠道对应的手续费率paymentservicecode
	 * @param paymentServiceCode
	 *            Integer
	 * @return List < PricingStrategy >
	 */
	List<PricingStrategyDetailReport> getAllPricingStrategyDetailByPSC(Integer paymentServiceCode);

	/**
	 * @根据参数得到 List < PricingStrategy >
	 * @param paymentServiceCode
	 *            Integer
	 * @param serviceLevelCode
	 *            Integer
	 * @param memberCode
	 *            Long
	 * @param date
	 *            MfDate
	 * @return List < PricingStrategy >
	 */
	List<PricingStrategy> getPricingStrategyByParam(
			final Integer paymentServiceCode, final Integer serviceLevelCode,
			final Long memberCode, final Date date);
}
