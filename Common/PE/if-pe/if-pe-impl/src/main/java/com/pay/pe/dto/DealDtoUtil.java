package com.pay.pe.dto;

import com.pay.inf.dto.AbstractDtoUtil;
import com.pay.inf.dto.Dto;
import com.pay.inf.dto.DtoUtil;
import com.pay.inf.model.Model;
import com.pay.pe.model.Deal;
import com.pay.pe.model.PaymentOrder;
import com.pay.util.BeanConvertUtil;

public class DealDtoUtil extends AbstractDtoUtil implements DtoUtil {

	/**
	 * Default constructor.
	 */
	public DealDtoUtil() {
		super();
	}

	/*
	 * (non-Javadoc)
	 */
	public Class getModelClass() {
		return Deal.class;
	}

	/*
	 * (non-Javadoc)
	 */
	public Class getDtoClass() {
		return DealDto.class;
	}

	@Override
	public Dto convert2Dto(Model model) {
		
		DealDto dto = new DealDto();
		BeanUtils.copyProperties(model, dto, new String[]{"order"});
		//DealDto dto = BeanConvertUtil.convert(DealDto.class, model);
		if (null != dto && model instanceof Deal) {
			Deal deal = (Deal) model;
			PaymentOrder paymentOrder = deal.getOrder();
			if (null != paymentOrder) {
				dto.setOrder((PaymentOrderDto) new PaymentOrderDtoUtil()
						.convert2Dto(deal.getOrder()));
			}
		}
		return dto;
	}

	@Override
	public Model convert2Model(Dto dto) {

		Deal deal = new Deal();

		if (dto instanceof DealDto) {
			DealDto dealDto = (DealDto) dto;
			deal.setAsynAccounting(dealDto.getAsynAccounting());
			deal.setDealAmount(dealDto.getDealAmount());
			deal.setDealBeginDate(dealDto.getDealBeginDate());
			deal.setDealCode(dealDto.getDealCode());
			deal.setDealEndDate(dealDto.getDealEndDate());
			deal.setDealId(dealDto.getDealId());
			deal.setDealStatus(dealDto.getDealStatus());
			deal.setDealType(dealDto.getDealType());
			deal.setExchangeRate(dealDto.getExchangeRate());
			deal.setHasCaculatedPayeePrice(dealDto.isHasCaculatedPayeePrice());
			deal.setHasCaculatedPayerPrice(dealDto.isHasCaculatedPayerPrice());
			deal.setHasCaculatedPrice(dealDto.getHasCaculatedPrice());
			deal.setOrderSeqId(dealDto.getOrderSeqId());
			deal.setOrderTime(dealDto.getOrderTime());
			deal.setOrgOrderId(dealDto.getOrgOrderId());
			deal.setPayeeAcctCode(dealDto.getPayeeAcctCode());
			deal.setPayeeAcctType(dealDto.getPayeeAcctType());
			deal.setPayeeCurrencyCode(dealDto.getPayeeCurrencyCode());
			deal.setPayeeFee(dealDto.getPayeeFee());
			deal.setPayeeOrgCode(dealDto.getPayeeOrgCode());
			deal.setPayeeOrgType(dealDto.getPayeeOrgType());
			deal.setPayeePriceStrategy(dealDto.getPayeePriceStrategy());
			deal.setPayeeServiceLevel(dealDto.getPayeeServiceLevel());
			deal.setPayerAcctCode(dealDto.getPayerAcctCode());
			deal.setPayerAcctType(dealDto.getPayerAcctType());
			deal.setPayerCurrencyCode(dealDto.getPayerCurrencyCode());
			deal.setPayerFee(dealDto.getPayerFee());
			deal.setPayerOrgCode(dealDto.getPayerOrgCode());
			deal.setPayerOrgType(dealDto.getPayerOrgType());
			deal.setPayerPriceStrategy(dealDto.getPayerPriceStrategy());
			deal.setPaymentServicePkgCode(dealDto.getPaymentServicePkgCode());
			deal.setPayMethod(dealDto.getPayMethod());
			deal.setPriceStrategyCode(dealDto.getPriceStrategyCode());
			deal.setSubmitAcctCode(dealDto.getSubmitAcctCode());
			deal.setSyncToken(dealDto.getSyncToken());
			deal.setTransactionDate(dealDto.getTransactionDate());
			deal.setVoucherCode(dealDto.getVoucherCode());
			deal.setOrder((PaymentOrder) new PaymentOrderDtoUtil()
					.convert2Model(dealDto.getOrder()));
		}
		return deal;
	}

}
