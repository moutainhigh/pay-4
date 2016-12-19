/**
 * 
 */
package com.pay.acc.rate.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.pay.acc.rate.dao.MerchantRateDAO;
import com.pay.acc.rate.dao.model.MerchantRate;
import com.pay.acc.rate.dto.MerchantRateDto;
import com.pay.acc.rate.service.MerchantRateService;
import com.pay.util.BeanConvertUtil;
import com.pay.util.MapUtil;

/**
 * @author chaoyue
 *
 */
public class MerchantRateServiceImpl implements MerchantRateService {

	private MerchantRateDAO merchantRateDAO;

	public void setMerchantRateDAO(final MerchantRateDAO merchantRateDAO) {
		this.merchantRateDAO = merchantRateDAO;
	}

	@Override
	public Long addMerchantRate(final MerchantRateDto merchantRate) {

		return (Long) merchantRateDAO.create(BeanConvertUtil.convert(
				MerchantRate.class, merchantRate));
	}

	/**
	 * 
	 */
	@Override
	public boolean delMerchantRate(final Long id) {
		return merchantRateDAO.delete(id);
	}

	@Override
	public List<MerchantRateDto> queryMerchantRate(final MerchantRateDto merchantRate) {
		// TODO Auto-generated method stub
		List<MerchantRate> modelList = merchantRateDAO
				.findByCriteria(merchantRate);
		return (List<MerchantRateDto>) BeanConvertUtil.convert(
				MerchantRateDto.class, modelList);
	}

	@Override
	public boolean updateMerchantRate(final MerchantRateDto merchantRate) {
		return merchantRateDAO.update(BeanConvertUtil.convert(
				MerchantRate.class, merchantRate));
	}

	@Override
	public MerchantRateDto queryMerchantRate(final Long merchantCode,
			final Integer dealCode, final MerchantRateDto merchantRateDto) {

		Assert.notNull(merchantCode, "商户号不能为空！");
		Assert.notNull(dealCode, "交易码不能为空！");
		Map paraMap = MapUtil.bean2map(merchantRateDto);
		paraMap.put("merchantCode", merchantCode);
		paraMap.put("dealCode", dealCode);
		MerchantRate merchantRate = (MerchantRate) merchantRateDAO
				.findByCriteria(paraMap);
		return BeanConvertUtil.convert(MerchantRateDto.class, merchantRate);
	}

	@Override
	public boolean updateLevelCurrencyCode(final MerchantRateDto merchantRate) {
		return merchantRateDAO.update("updateCurrencyCode", BeanConvertUtil.convert(
				MerchantRate.class, merchantRate));
	}
}
