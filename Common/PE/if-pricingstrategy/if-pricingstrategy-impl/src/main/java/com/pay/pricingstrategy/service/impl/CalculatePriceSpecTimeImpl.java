/**
 * 
 */
package com.pay.pricingstrategy.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.springframework.util.Assert;

import com.pay.pricingstrategy.dao.PricingStrategyDAO;
import com.pay.pricingstrategy.dto.PricingStrategyDetailDTO;
import com.pay.pricingstrategy.dto.PricingStrategyDetailDTOUtil;
import com.pay.pricingstrategy.helper.CACULATEMETHOD;
import com.pay.pricingstrategy.service.CalFeeFactory;
import com.pay.pricingstrategy.service.CalFeeInnerParam;
import com.pay.pricingstrategy.service.CalPriceInnerParam;
import com.pay.pricingstrategy.service.CalculatePrice;
import com.pay.pricingstrategy.util.PricingStrategyUtil;

/**
 * caculatemethod是特定时间的交易费用计算.
 * 
 * @author
 * 
 */
public class CalculatePriceSpecTimeImpl implements CalculatePrice {
	/**
	 * 注入calpriceFactory.
	 */
	private CalFeeFactory calFeeFactory;
	/**
	 * 注入pricingStrategyDAO.
	 */
	private PricingStrategyDAO pricingStrategyDAO;
	/**
	 * 具体的caculatemethod.
	 */
	private final int caculatemethod = CACULATEMETHOD.SPECIALTIME.getValue();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * 
	 */
	public long calPrice(CalPriceInnerParam param) {
		return this.calPriceDetail(param).getFee();
	}

	public CalPriceFeeResponse calPriceDetail(CalPriceInnerParam param) {
//		assert param.getTransactionAmount() != null
//				&& param.getTransactionAmount() > 0;
		Assert.notNull(param.getTransactionAmount());
		Assert.isTrue(param.getTransactionAmount() > 0);
		List<PricingStrategyDetailDTO> detailDtoList = PricingStrategyDetailDTOUtil
				.convertToPricingDetailList(this.pricingStrategyDAO
						.getAllPricingStrategyDetailByParam(
								param.getPricingStrategyDto()
										.getPriceStrategyCode(), param
										.getTransactionAmount(), param
										.getTerminltype(), param
										.getReservedCode()));
		if (detailDtoList == null || detailDtoList.size() > 1) {
			throw new IllegalStateException("获得价格策略明细错误");
		}
		if (detailDtoList.isEmpty()) {
			return new CalPriceFeeResponse();
		}
		PricingStrategyDetailDTO resultDetailDto = null;
		int i = 0;
		for (PricingStrategyDetailDTO detailDto : detailDtoList) {
			Timestamp effectivefrom = detailDto.getEffectiveFrom();
			Timestamp effectiveto = detailDto.getEffectiveTo();

			// if (MfTimeUtil.isBetweenOrEquals(new MfTime(effectivefrom), param
			// .getMftime(), new MfTime(effectiveto))) {

			// if (MfTimeUtil.isBetweenOrEquals(new MfTime(effectivefrom),
			// new MfTime(new
			// java.sql.Timestamp(param.getMfDateTime().getTime())), new
			// MfTime(effectiveto))) {

		//todo
//			isBetweenEquals			
			
			if (PricingStrategyUtil.isBetweenEquals(effectivefrom.getTime(), param
					.getMfDateTime().getTime(), effectiveto.getTime())) {

				resultDetailDto = detailDto;
				i++;
				continue;
			}

		}
		if (i != 1) {
			throw new RuntimeException(
					"PricingStrategyDetail is not  definited or count > 1");
		}
		CalFeeInnerParam feeParam = new CalFeeInnerParam();
		feeParam.setPricingstategydetaildto(resultDetailDto);
		feeParam.setTransactionAmount(param.getTransactionAmount());
		return calFeeFactory.getCalFee(
				param.getPricingStrategyDto().getPriceStrategyType())
				.calculateFeeDetail(feeParam);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * 
	 * getCaculatemethod()
	 */
	public int getCaculatemethod() {
		// TODO Auto-generated method stub
		return caculatemethod;
	}

	public void setCalFeeFactory(CalFeeFactory calFeeFactory) {
		this.calFeeFactory = calFeeFactory;
	}

	public PricingStrategyDAO getPricingStrategyDAO() {
		return pricingStrategyDAO;
	}

	public void setPricingStrategyDAO(PricingStrategyDAO pricingStrategyDAO) {
		this.pricingStrategyDAO = pricingStrategyDAO;
	}

}
